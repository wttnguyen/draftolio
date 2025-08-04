import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';

export interface User
{
	id: string;
	riotTag: string;
	gameName: string;
	tagLine: string;
	profileIconId?: number;
	summonerLevel?: number;
}

@Injectable({
	providedIn: 'root'
})
export class AuthService
{
	private readonly _user = signal<User | null>(null);
	private readonly _isLoading = signal(false);

	// Public readonly signals
	readonly user = this._user.asReadonly();
	readonly isLoading = this._isLoading.asReadonly();
	readonly isAuthenticated = computed(() => this._user() !== null);

	constructor(private http: HttpClient)
	{
		this.checkAuthStatus();
	}

	/**
	 * Check if user is currently authenticated by calling the backend
	 */
	private checkAuthStatus(): void
	{
		this._isLoading.set(true);

		this.http.get<User>('/auth/me').subscribe({
			next: (user) =>
			{
				this._user.set(user);
				this._isLoading.set(false);
			},
			error: (err) =>
			{
				this._user.set(null);
				this._isLoading.set(false);
			}
		});
	}

	/**
	 * Initiate login via RSO (Riot Single Sign-On)
	 */
	login(): void
	{
		this._isLoading.set(true);

		// Call the backend login endpoint to get the authorization URL
		this.http.get<{ authorizationUrl: string, error?: string }>('/auth/login').subscribe({
			next: (response) =>
			{
				this._isLoading.set(false);
				if (response.authorizationUrl)
				{
					// Redirect to the RSO authorization URL
					window.location.href = response.authorizationUrl;
				}
				else if (response.error)
				{
					console.error('Login failed:', response.error);
				}
			},
			error: (error) =>
			{
				this._isLoading.set(false);
				console.error('Failed to initiate login:', error);
			}
		});
	}

	/**
	 * Logout the current user
	 */
	logout(): Observable<void>
	{
		return new Observable(observer =>
		{
			this.http.post<void>('/auth/logout', {}).subscribe({
				next: () =>
				{
					this._user.set(null);
					observer.next();
					observer.complete();
				},
				error: (error) =>
				{
					observer.error(error);
				}
			});
		});
	}

	/**
	 * Refresh user data
	 */
	refreshUser(): void
	{
		this.checkAuthStatus();
	}
}
