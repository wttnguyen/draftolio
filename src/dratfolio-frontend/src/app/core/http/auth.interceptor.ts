import { HttpInterceptorFn, HttpErrorResponse, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, switchMap, throwError, EMPTY } from 'rxjs';
import { AuthService } from '../auth/auth.service';

/**
 * Authentication interceptor for handling HTTP requests with authentication.
 *
 * This interceptor automatically handles:
 * - Adding authentication headers to requests
 * - Token refresh on 401 errors
 * - Error handling for authentication failures
 * - Redirecting to login on authentication errors
 */
export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: any) =>
{
	const authService = inject(AuthService);

	// Skip authentication for certain URLs
	const skipAuthUrls = [
		'/auth/login',
		'/auth/status',
		'/auth/error',
		'/oauth2',
		'/login/oauth2'
	];

	const shouldSkipAuth = skipAuthUrls.some(url => req.url.includes(url));

	if (shouldSkipAuth)
	{
		return next(req);
	}

	// Clone the request and add authentication headers if user is authenticated
	let authReq = req;

	if (authService.isAuthenticated())
	{
		// For session-based authentication, we rely on cookies
		// The browser will automatically include session cookies
		authReq = req.clone({
			setHeaders: {
				'X-Requested-With': 'XMLHttpRequest'
			}
		});
	}

	return next(authReq).pipe(
		catchError((error: HttpErrorResponse) =>
		{
			if (error.status === 401)
			{
				return handle401Error(authReq, next, authService);
			}
			else if (error.status === 403)
			{
				return handle403Error(error, authService);
			}
			else
			{
				return handleOtherErrors(error, authService);
			}
		})
	);
};

/**
 * Handle 401 Unauthorized errors by attempting token refresh
 */
function handle401Error(req: any, next: any, authService: AuthService)
{
	// Check if user was previously authenticated
	if (authService.isAuthenticated())
	{
		// Attempt to refresh token
		return authService.refreshToken().pipe(
			switchMap(() =>
			{
				// Retry the original request after successful token refresh
				return next(req);
			}),
			catchError((refreshError) =>
			{
				// Token refresh failed, handle authentication error
				authService.handleAuthError(refreshError);
				return throwError(() => refreshError);
			})
		);
	}
	else
	{
		// User was not authenticated, handle as authentication error
		authService.handleAuthError(new HttpErrorResponse({
			status: 401,
			statusText: 'Unauthorized',
			error: { message: 'Authentication required' }
		}));
		return throwError(() => new Error('Authentication required'));
	}
}

/**
 * Handle 403 Forbidden errors
 */
function handle403Error(error: HttpErrorResponse, authService: AuthService)
{
	console.warn('Access forbidden:', error);

	// Handle the error through AuthService
	authService.handleAuthError(error);

	return throwError(() => error);
}

/**
 * Handle other HTTP errors
 */
function handleOtherErrors(error: HttpErrorResponse, authService: AuthService)
{
	// Log the error for debugging
	console.error('HTTP Error:', error);

	// Handle network errors or server errors
	if (error.status === 0)
	{
		// Network error
		console.error('Network error - check your internet connection');
	}
	else if (error.status >= 500)
	{
		// Server error
		console.error('Server error - please try again later');
	}

	return throwError(() => error);
}

/**
 * CSRF Token interceptor for adding CSRF protection to requests
 */
export const csrfInterceptor: HttpInterceptorFn = (req, next) =>
{
	// Skip CSRF for GET requests and certain URLs
	const skipCsrfMethods = [ 'GET', 'HEAD', 'OPTIONS' ];
	const skipCsrfUrls = [
		'/oauth2',
		'/login/oauth2'
	];

	const shouldSkipCsrf = skipCsrfMethods.includes(req.method) ||
		skipCsrfUrls.some(url => req.url.includes(url));

	if (shouldSkipCsrf)
	{
		return next(req);
	}

	// Get CSRF token from cookie
	const csrfToken = getCsrfTokenFromCookie();

	if (csrfToken)
	{
		const csrfReq = req.clone({
			setHeaders: {
				'X-XSRF-TOKEN': csrfToken
			}
		});
		return next(csrfReq);
	}

	return next(req);
};

/**
 * Extract CSRF token from cookie
 */
function getCsrfTokenFromCookie(): string | null
{
	const cookies = document.cookie.split(';');

	for (let cookie of cookies)
	{
		const [ name, value ] = cookie.trim().split('=');
		if (name === 'XSRF-TOKEN')
		{
			return decodeURIComponent(value);
		}
	}

	return null;
}

/**
 * Loading interceptor to show/hide loading indicators
 */
export const loadingInterceptor: HttpInterceptorFn = (req, next) =>
{
	// Skip loading indicator for certain requests
	const skipLoadingUrls = [
		'/auth/status',
		'/auth/refresh'
	];

	const shouldSkipLoading = skipLoadingUrls.some(url => req.url.includes(url));

	if (shouldSkipLoading)
	{
		return next(req);
	}

	// You can implement loading service here if needed
	// const loadingService = inject(LoadingService);
	// loadingService.show();

	return next(req).pipe(
		// Hide loading indicator when request completes
		// finalize(() => loadingService.hide())
	);
};

/**
 * Error logging interceptor for centralized error logging
 */
export const errorLoggingInterceptor: HttpInterceptorFn = (req, next) =>
{
	return next(req).pipe(
		catchError((error: HttpErrorResponse) =>
		{
			// Log error details for debugging
			const errorDetails = {
				url: req.url,
				method: req.method,
				status: error.status,
				statusText: error.statusText,
				message: error.message,
				timestamp: new Date().toISOString()
			};

			console.error('[HTTP Error]', errorDetails);

			// You can send error details to logging service here
			// const loggingService = inject(LoggingService);
			// loggingService.logError(errorDetails);

			return throwError(() => error);
		})
	);
};
