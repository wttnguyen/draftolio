import { Routes } from '@angular/router';

export const routes: Routes = [
	{
		path: '',
		loadComponent: () => import('./features/home/home.component').then(m => m.HomeComponent)
	},
	{
		path: 'teams',
		loadComponent: () => import('./features/teams/teams.component').then(m => m.TeamsComponent)
	},
	{
		path: 'drafts',
		loadComponent: () => import('./features/drafts/drafts.component').then(m => m.DraftsComponent)
	},
	{
		path: 'profile',
		loadComponent: () => import('./features/user/profile/profile.component').then(m => m.ProfileComponent)
	},
	{
		path: 'about',
		loadComponent: () => import('./features/about/about.component').then(m => m.AboutComponent)
	},
	{
		path: 'contact',
		loadComponent: () => import('./features/contact/contact.component').then(m => m.ContactComponent)
	},
	{
		path: 'privacy',
		loadComponent: () => import('./features/legal/privacy/privacy.component').then(m => m.PrivacyComponent)
	},
	{
		path: 'terms',
		loadComponent: () => import('./features/legal/terms/terms.component').then(m => m.TermsComponent)
	},
	{
		path: 'help',
		loadComponent: () => import('./features/help/help.component').then(m => m.HelpComponent)
	},
	{
		path: 'careers',
		loadComponent: () => import('./features/careers/careers.component').then(m => m.CareersComponent)
	},
	{
		path: 'cookies',
		loadComponent: () => import('./features/legal/cookies/cookies.component').then(m => m.CookiesComponent)
	},
	{
		path: 'faq',
		loadComponent: () => import('./features/faq/faq.component').then(m => m.FaqComponent)
	},
	{
		path: 'community',
		loadComponent: () => import('./features/community/community.component').then(m => m.CommunityComponent)
	},
	{
		path: '**',
		redirectTo: ''
	}
];
