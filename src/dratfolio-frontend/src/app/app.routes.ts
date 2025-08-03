import { Routes } from '@angular/router';

export const routes: Routes = [
	{
		path: '',
		redirectTo: '/drafts',
		pathMatch: 'full'
	},
	{
		path: 'drafts',
		loadComponent: () => import('./features/draft/pages/drafts.component').then(m => m.DraftsComponent)
	},
	{
		path: 'login',
		loadComponent: () => import('./features/user/components/login.component').then(m => m.LoginComponent)
	}
];
