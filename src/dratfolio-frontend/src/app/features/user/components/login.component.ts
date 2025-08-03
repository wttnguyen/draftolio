import { Component, inject, signal, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../core/auth/auth.service';

/**
 * Login component for Riot Sign-On (RSO) authentication.
 *
 * This component provides the login interface and handles authentication
 * with Riot Games through the RSO OAuth 2.0 flow.
 */
@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: [ './login.component.css' ],
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginComponent implements OnInit
{
	private readonly authService = inject(AuthService);
	private readonly route = inject(ActivatedRoute);

	// Component state signals
	protected readonly isLoading = signal<boolean>(false);
	protected readonly errorMessage = signal<string | null>(null);
	protected readonly successMessage = signal<string | null>(null);

	// Auth service signals
	protected readonly authError = this.authService.error;
	protected readonly authLoading = this.authService.isLoading;

	ngOnInit(): void
	{
		// Check for URL parameters that indicate authentication status
		this.route.queryParams.subscribe(params =>
		{
			if (params['error'])
			{
				this.handleAuthError(params['error'], params['error_description']);
			}
			else if (params['logout'])
			{
				this.successMessage.set('You have been logged out successfully.');
			}
		});
	}

	/**
	 * Initiate login with Riot Sign-On
	 */
	protected login(): void
	{
		this.clearMessages();
		this.isLoading.set(true);

		try
		{
			this.authService.login();
		}
		catch (error)
		{
			console.error('Login initiation failed:', error);
			this.errorMessage.set('Failed to initiate login. Please try again.');
			this.isLoading.set(false);
		}
	}

	/**
	 * Handle authentication errors from URL parameters
	 */
	private handleAuthError(error: string, description?: string): void
	{
		let message = 'Authentication failed';

		switch (error)
		{
			case 'access_denied':
				message = 'Access was denied. You need to authorize the application to continue.';
				break;
			case 'invalid_request':
				message = 'Invalid authentication request. Please contact support if this problem persists.';
				break;
			case 'server_error':
				message = 'Authentication service is temporarily unavailable. Please try again in a few minutes.';
				break;
			case 'temporarily_unavailable':
				message = 'Authentication service is temporarily unavailable. Please try again later.';
				break;
			default:
				if (description)
				{
					message = `Authentication failed: ${description}`;
				}
				break;
		}

		this.errorMessage.set(message);
	}

	/**
	 * Clear all messages
	 */
	protected clearMessages(): void
	{
		this.errorMessage.set(null);
		this.successMessage.set(null);
		this.authService.clearError();
	}

	/**
	 * Check if there are any error messages to display
	 */
	protected hasError(): boolean
	{
		return this.errorMessage() !== null || this.authError() !== null;
	}

	/**
	 * Get the current error message to display
	 */
	protected getCurrentError(): string | null
	{
		return this.errorMessage() || this.authError();
	}
}
