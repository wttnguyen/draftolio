import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
	selector: 'app-home',
	imports: [
		CommonModule,
		RouterLink,
		ButtonModule,
		CardModule
	],
	template: `
		<div class="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
			<div class="container mx-auto px-4 py-16">

				<!-- Hero Section -->
				<div class="text-center mb-16">
					<h1 class="text-5xl font-bold text-gray-900 mb-6">
						Welcome to <span class="text-blue-600">Draftolio AI</span>
					</h1>
					<p class="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
						Advanced AI-powered draft analysis and team composition optimization for League of Legends.
						Elevate your gameplay with intelligent insights and strategic recommendations.
					</p>
					<div class="flex flex-col sm:flex-row gap-4 justify-center">
						<p-button
							label="Get Started"
							icon="pi pi-arrow-right"
							severity="primary"
							size="large"
							routerLink="/teams"
						></p-button>
						<p-button
							label="Learn More"
							icon="pi pi-info-circle"
							severity="secondary"
							[outlined]="true"
							size="large"
							routerLink="/about"
						></p-button>
					</div>
				</div>

				<!-- Features Grid -->
				<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 mb-16">

					<p-card class="h-full">
						<ng-template pTemplate="header">
							<div class="p-6 text-center">
								<i class="pi pi-users text-4xl text-blue-600 mb-4"></i>
								<h3 class="text-xl font-semibold text-gray-900">Team Management</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 text-center">
							Create and manage your teams with intelligent composition analysis and role optimization.
						</p>
					</p-card>

					<p-card class="h-full">
						<ng-template pTemplate="header">
							<div class="p-6 text-center">
								<i class="pi pi-file-edit text-4xl text-green-600 mb-4"></i>
								<h3 class="text-xl font-semibold text-gray-900">Draft Analysis</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 text-center">
							Get real-time draft recommendations and counter-pick suggestions powered by AI.
						</p>
					</p-card>

					<p-card class="h-full">
						<ng-template pTemplate="header">
							<div class="p-6 text-center">
								<i class="pi pi-chart-line text-4xl text-purple-600 mb-4"></i>
								<h3 class="text-xl font-semibold text-gray-900">Performance Insights</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 text-center">
							Track your progress with detailed analytics and performance metrics.
						</p>
					</p-card>
				</div>

				<!-- Call to Action -->
				<div class="text-center bg-white rounded-lg shadow-lg p-8">
					<h2 class="text-3xl font-bold text-gray-900 mb-4">
						Ready to Dominate the Rift?
					</h2>
					<p class="text-lg text-gray-600 mb-6">
						Join thousands of players who are already using Draftolio AI to improve their gameplay.
					</p>
					<p-button
						label="Login with Riot Games"
						icon="pi pi-sign-in"
						severity="primary"
						size="large"
					></p-button>
				</div>
			</div>
		</div>
	`,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomeComponent {}
