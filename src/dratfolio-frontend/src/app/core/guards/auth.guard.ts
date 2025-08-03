import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivateFn, CanMatchFn } from '@angular/router';
import { map, take } from 'rxjs/operators';
import { AuthService } from '../auth/auth.service';

/**
 * Authentication guard that protects routes requiring user authentication.
 *
 * This guard checks if the user is authenticated before allowing access to protected routes.
 * If the user is not authenticated, they are redirected to the login page.
 */
export const authGuard: CanActivateFn = (route, state) =>
{
	const authService = inject(AuthService);
	const router = inject(Router);

	return authService.checkAuthStatus().pipe(
		take(1),
		map(status =>
		{
			if (status.authenticated)
			{
				return true;
			}
			else
			{
				// Store the attempted URL for redirecting after login
				const returnUrl = state.url;
				router.navigate([ '/login' ], {
					queryParams: { returnUrl },
					replaceUrl: true
				});
				return false;
			}
		})
	);
};

/**
 * Authentication guard for route matching.
 *
 * This guard is used with lazy-loaded modules to check authentication
 * before loading the module.
 */
export const authMatchGuard: CanMatchFn = (route, segments) =>
{
	const authService = inject(AuthService);
	const router = inject(Router);

	return authService.checkAuthStatus().pipe(
		take(1),
		map(status =>
		{
			if (status.authenticated)
			{
				return true;
			}
			else
			{
				// Redirect to login page
				router.navigate([ '/login' ], { replaceUrl: true });
				return false;
			}
		})
	);
};

/**
 * Guest guard that redirects authenticated users away from login/register pages.
 *
 * This guard is used to prevent authenticated users from accessing
 * login or registration pages.
 */
export const guestGuard: CanActivateFn = (route, state) =>
{
	const authService = inject(AuthService);
	const router = inject(Router);

	return authService.checkAuthStatus().pipe(
		take(1),
		map(status =>
		{
			if (!status.authenticated)
			{
				return true;
			}
			else
			{
				// User is already authenticated, redirect to home page
				const returnUrl = route.queryParams?.['returnUrl'] || '/';
				router.navigate([ returnUrl ], { replaceUrl: true });
				return false;
			}
		})
	);
};

/**
 * Admin guard that protects admin-only routes.
 *
 * This guard checks if the user is authenticated and has admin privileges.
 */
export const adminGuard: CanActivateFn = (route, state) =>
{
	const authService = inject(AuthService);
	const router = inject(Router);

	return authService.checkAuthStatus().pipe(
		take(1),
		map(status =>
		{
			if (!status.authenticated)
			{
				// User is not authenticated, redirect to login
				router.navigate([ '/login' ], {
					queryParams: { returnUrl: state.url },
					replaceUrl: true
				});
				return false;
			}

			// Check if user has admin role
			const hasAdminRole = status.authorities?.some(auth =>
				auth.includes('ROLE_ADMIN') || auth.includes('ADMIN')
			);

			if (hasAdminRole)
			{
				return true;
			}
			else
			{
				// User doesn't have admin privileges, redirect to unauthorized page
				router.navigate([ '/unauthorized' ], { replaceUrl: true });
				return false;
			}
		})
	);
};

/**
 * Region-specific guard that checks if user is from a specific region.
 *
 * This guard can be used to restrict access to region-specific features.
 */
export const regionGuard = (allowedRegions: string[]): CanActivateFn =>
{
	return (route, state) =>
	{
		const authService = inject(AuthService);
		const router = inject(Router);

		return authService.checkAuthStatus().pipe(
			take(1),
			map(status =>
			{
				if (!status.authenticated)
				{
					// User is not authenticated, redirect to login
					router.navigate([ '/login' ], {
						queryParams: { returnUrl: state.url },
						replaceUrl: true
					});
					return false;
				}

				// Check if user's region is allowed
				const userRegion = status.cpid;
				if (userRegion && allowedRegions.includes(userRegion.toUpperCase()))
				{
					return true;
				}
				else
				{
					// User's region is not allowed, redirect to region restriction page
					router.navigate([ '/region-restricted' ], {
						queryParams: {
							userRegion,
							allowedRegions: allowedRegions.join(',')
						},
						replaceUrl: true
					});
					return false;
				}
			})
		);
	};
};

/**
 * Token expiration guard that checks if the user's token is about to expire.
 *
 * This guard can be used to prompt token refresh before accessing sensitive routes.
 */
export const tokenExpirationGuard: CanActivateFn = (route, state) =>
{
	const authService = inject(AuthService);
	const router = inject(Router);

	if (!authService.isAuthenticated())
	{
		router.navigate([ '/login' ], {
			queryParams: { returnUrl: state.url },
			replaceUrl: true
		});
		return false;
	}

	// Check if token is expiring soon
	if (authService.isTokenExpiringSoon())
	{
		// Attempt to refresh token
		return authService.refreshToken().pipe(
			take(1),
			map(() => true),
			// If refresh fails, the AuthService will handle redirecting to login
		);
	}

	return true;
};
