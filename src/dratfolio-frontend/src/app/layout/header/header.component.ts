import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { MenuModule } from 'primeng/menu';
import { MenuItem } from 'primeng/api';
import { AuthService } from '../../core/auth/auth.service';

@Component({
	selector: 'app-header',
	imports: [
		CommonModule,
		RouterLink,
		RouterLinkActive,
		ButtonModule,
		MenuModule
	],
	templateUrl: './header.component.html',
	styleUrl: './header.component.css',
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent
{
	private readonly authService = inject(AuthService);
	private readonly router = inject(Router);

	// Expose auth signals to template
	readonly user = this.authService.user;
	readonly isAuthenticated = this.authService.isAuthenticated;
	readonly isLoading = this.authService.isLoading;

	// User menu items
	readonly userMenuItems: MenuItem[] = [
		{
			label: 'View Profile',
			icon: 'pi pi-user',
			command: () => this.navigateToProfile()
		},
		{
			separator: true
		},
		{
			label: 'Sign Out',
			icon: 'pi pi-sign-out',
			command: () => this.signOut()
		}
	];

	onLoginClick(): void
	{
		this.authService.login();
	}

	private navigateToProfile(): void
	{
		this.router.navigate([ '/profile' ]);
	}

	private signOut(): void
	{
		this.authService.logout().subscribe({
			next: () =>
			{
				this.router.navigate([ '/' ]);
			},
			error: (error) =>
			{
				console.error('Logout failed:', error);
			}
		});
	}
}
