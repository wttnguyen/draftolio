import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
	selector: 'app-footer',
	imports: [
		CommonModule,
		RouterLink
	],
	templateUrl: './footer.component.html',
	styleUrl: './footer.component.css',
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class FooterComponent
{
	readonly currentYear = new Date().getFullYear();

	readonly footerLinks = {
		company: [
			{ label: 'About Us', route: '/about' },
			{ label: 'Contact', route: '/contact' },
			{ label: 'Careers', route: '/careers' }
		],
		legal: [
			{ label: 'Privacy Policy', route: '/privacy' },
			{ label: 'Terms of Service', route: '/terms' },
			{ label: 'Cookie Policy', route: '/cookies' }
		],
		support: [
			{ label: 'Help Center', route: '/help' },
			{ label: 'FAQ', route: '/faq' },
			{ label: 'Community', route: '/community' }
		]
	};
}
