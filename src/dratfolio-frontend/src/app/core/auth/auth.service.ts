import { Injectable, signal, computed, inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, BehaviorSubject, throwError, EMPTY } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';

/**
 * User information interface matching the backend UserInfoResponse
 */
export interface UserInfo
{
	id: number;
	subject: string;
	cpid: string;
	displayName?: string;
	email?: string;
	active: boolean;
	createdAt: string;
	updatedAt: string;
	lastLoginAt?: string;
	authenticationInfo: {
		authenticated: boolean;
		accessTokenExpiresAt?: string;
		scopes?: string[];
		provider: string;
	};
}

/**
 * Authentication status interface
 */
export interface AuthStatus
{
	authenticated: boolean;
	subject?: string;
	cpid?: string;
	authorities?: string[];
	message?: string;
}

/**
 * Authentication service for Riot Sign-On (RSO) integration.
 *
 * This service manages user authentication state, handles login/logout operations,
 * and provides authentication status information using Angular signals.
 */
@Injectable({
	providedIn: 'root'
})
export class AuthService
{
	private readonly http = inject(HttpClient);
	private readonly router = inject(Router);

	// Authentication state signals
	private readonly _isAuthenticated = signal<boolean>(false);
	private readonly _currentUser = signal<UserInfo | null>(null);
	private readonly _isLoading = signal<boolean>(false);
	private readonly _error = signal<string | null>(null);

	// Public readonly signals
	readonly isAuthenticated = this._isAuthenticated.asReadonly();
	readonly currentUser = this._currentUser.asReadonly();
	readonly isLoading = this._isLoading.asReadonly();
	readonly error = this._error.asReadonly();

	// Computed signals
	readonly userDisplayName = computed(() =>
	{
		const user = this._currentUser();
		return user?.displayName || user?.subject || 'Unknown User';
	});

	readonly userRegion = computed(() =>
	{
		const user = this._currentUser();
		return this.getRegionDisplayName(user?.cpid);
	});

	readonly isAdmin = computed(() =>
	{
		const user = this._currentUser();
		// Add admin role logic here if needed
		return false;
	});

	constructor()
	{
		// Check authentication status on service initialization
		this.checkAuthStatus();
	}

	/**
	 * Initiate login with Riot Sign-On
	 */
	login(): void
	{
		this._isLoading.set(true);
		this._error.set(null);

		// Redirect to backend login endpoint which will redirect to RSO
		window.location.href = '/auth/login';
	}

	/**
	 * Logout the current user
	 */
	logout(): Observable<void>
	{
		this._isLoading.set(true);
		this._error.set(null);

		return this.http.post<{ message: string; redirectUrl: string }>('/auth/logout', {}).pipe(
			tap(() =>
			{
				this._isAuthenticated.set(false);
				this._currentUser.set(null);
				this._isLoading.set(false);
				this.router.navigate([ '/login' ]);
			}),
			map(() => void 0),
			catchError(error =>
			{
				console.error('Logout failed:', error);
				this._error.set('Logout failed');
				this._isLoading.set(false);

				// Even if logout fails on backend, clear local state
				this._isAuthenticated.set(false);
				this._currentUser.set(null);
				this.router.navigate([ '/login' ]);

				return EMPTY;
			})
		);
	}

	/**
	 * Get current user information
	 */
	getCurrentUser(): Observable<UserInfo>
	{
		this._isLoading.set(true);
		this._error.set(null);

		return this.http.get<UserInfo>('/auth/user/me').pipe(
			tap(user =>
			{
				this._currentUser.set(user);
				this._isAuthenticated.set(user.authenticationInfo.authenticated);
				this._isLoading.set(false);
			}),
			catchError(error =>
			{
				console.error('Failed to get current user:', error);
				this._error.set('Failed to load user information');
				this._isAuthenticated.set(false);
				this._currentUser.set(null);
				this._isLoading.set(false);
				return throwError(() => error);
			})
		);
	}

	/**
	 * Check authentication status
	 */
	checkAuthStatus(): Observable<AuthStatus>
	{
		return this.http.get<AuthStatus>('/auth/status').pipe(
			tap(status =>
			{
				this._isAuthenticated.set(status.authenticated);
				if (status.authenticated && status.subject)
				{
					// If authenticated, fetch full user info
					this.getCurrentUser().subscribe();
				}
				else
				{
					this._currentUser.set(null);
				}
			}),
			catchError(error =>
			{
				console.error('Failed to check auth status:', error);
				this._isAuthenticated.set(false);
				this._currentUser.set(null);
				return throwError(() => error);
			})
		);
	}

	/**
	 * Refresh authentication token
	 */
	refreshToken(): Observable<{ message: string; expiresAt: string }>
	{
		return this.http.post<{ message: string; expiresAt: string }>('/auth/refresh', {}).pipe(
			tap(response =>
			{
				console.log('Token refreshed:', response);
				// Update user info after token refresh
				this.getCurrentUser().subscribe();
			}),
			catchError(error =>
			{
				console.error('Token refresh failed:', error);
				if (error.status === 401)
				{
					// Token refresh failed, user needs to re-authenticate
					this._isAuthenticated.set(false);
					this._currentUser.set(null);
					this.router.navigate([ '/login' ]);
				}
				return throwError(() => error);
			})
		);
	}

	/**
	 * Handle authentication errors
	 */
	handleAuthError(error: HttpErrorResponse): void
	{
		let errorMessage = 'Authentication failed';

		if (error.status === 401)
		{
			errorMessage = 'Authentication required';
			this._isAuthenticated.set(false);
			this._currentUser.set(null);
			this.router.navigate([ '/login' ]);
		}
		else if (error.status === 403)
		{
			errorMessage = 'Access denied';
		}
		else if (error.status === 0)
		{
			errorMessage = 'Network error - please check your connection';
		}
		else if (error.error?.message)
		{
			errorMessage = error.error.message;
		}

		this._error.set(errorMessage);
		console.error('Authentication error:', error);
	}

	/**
	 * Clear error state
	 */
	clearError(): void
	{
		this._error.set(null);
	}

	/**
	 * Check if user is from a specific region
	 */
	isFromRegion(region: string): boolean
	{
		const user = this._currentUser();
		return user?.cpid?.toLowerCase() === region.toLowerCase();
	}

	/**
	 * Get human-readable region name from CPID
	 */
	private getRegionDisplayName(cpid?: string): string
	{
		if (!cpid)
		{
			return 'Unknown';
		}

		const regionMap: { [key: string]: string } = {
			'NA1': 'North America',
			'EUW1': 'Europe West',
			'EUN1': 'Europe Nordic & East',
			'KR': 'Korea',
			'JP1': 'Japan',
			'BR1': 'Brazil',
			'LA1': 'Latin America North',
			'LA2': 'Latin America South',
			'OC1': 'Oceania',
			'TR1': 'Turkey',
			'RU': 'Russia',
			'PH2': 'Philippines',
			'SG2': 'Singapore',
			'TH2': 'Thailand',
			'TW2': 'Taiwan',
			'VN2': 'Vietnam'
		};

		return regionMap[cpid.toUpperCase()] || cpid;
	}

	/**
	 * Get authentication token expiration time
	 */
	getTokenExpirationTime(): Date | null
	{
		const user = this._currentUser();
		if (!user?.authenticationInfo.accessTokenExpiresAt)
		{
			return null;
		}
		return new Date(user.authenticationInfo.accessTokenExpiresAt);
	}

	/**
	 * Check if token is about to expire (within 5 minutes)
	 */
	isTokenExpiringSoon(): boolean
	{
		const expirationTime = this.getTokenExpirationTime();
		if (!expirationTime)
		{
			return false;
		}

		const fiveMinutesFromNow = new Date(Date.now() + 5 * 60 * 1000);
		return expirationTime <= fiveMinutesFromNow;
	}
}
