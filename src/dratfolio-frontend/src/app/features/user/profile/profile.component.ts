import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TabsModule } from 'primeng/tabs';
import { AvatarModule } from 'primeng/avatar';
import { ChipModule } from 'primeng/chip';
import { AuthService } from '../../../core/auth/auth.service';

@Component({
	selector: 'app-profile',
	imports: [
		CommonModule,
		ButtonModule,
		CardModule,
		TabsModule,
		AvatarModule,
		ChipModule
	],
	template: `
		<div class="container mx-auto px-4 py-8">
			<div class="text-center mb-8">
				<h1 class="text-4xl font-bold text-gray-900 mb-4">My Profile</h1>
				<p class="text-lg text-gray-600">
					Manage your account settings and view your League of Legends statistics.
				</p>
			</div>

			<!-- Profile Header -->
			<p-card class="mb-8">
				<div class="flex flex-col md:flex-row items-center md:items-start gap-6">
					<div class="text-center md:text-left">
						<p-avatar
							[image]="profileData.avatarUrl"
							size="xlarge"
							shape="circle"
							class="mb-4"
						></p-avatar>
						<div class="flex justify-center md:justify-start gap-2 mb-4">
							<p-chip
								[label]="profileData.rank"
								[style]="{'background-color': getRankColor(profileData.rank)}"
								class="text-white"
							></p-chip>
							<p-chip
								[label]="profileData.region"
								styleClass="p-chip-outlined"
							></p-chip>
						</div>
					</div>

					<div class="flex-1">
						<h2 class="text-2xl font-bold text-gray-900 mb-2">
							{{ user()?.riotTag || 'Player#TAG' }}
						</h2>
						<p class="text-gray-600 mb-4">
							Level {{ profileData.summonerLevel }} • {{ profileData.region }}
						</p>

						<div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-4">
							<div class="text-center">
								<div class="text-2xl font-bold text-blue-600">{{ profileData.wins }}</div>
								<div class="text-sm text-gray-600">Wins</div>
							</div>
							<div class="text-center">
								<div class="text-2xl font-bold text-red-600">{{ profileData.losses }}</div>
								<div class="text-sm text-gray-600">Losses</div>
							</div>
							<div class="text-center">
								<div class="text-2xl font-bold text-green-600">{{ profileData.winRate }}%</div>
								<div class="text-sm text-gray-600">Win Rate</div>
							</div>
							<div class="text-center">
								<div class="text-2xl font-bold text-purple-600">{{ profileData.lp }}</div>
								<div class="text-sm text-gray-600">LP</div>
							</div>
						</div>

						<p-button
							label="Refresh Data"
							icon="pi pi-refresh"
							severity="secondary"
							size="small"
						></p-button>
					</div>
				</div>
			</p-card>

			<!-- Profile Tabs -->
			<p-tablist>
				<p-tab>
					<i class="pi pi-chart-bar mr-2"></i>
					Statistics
				</p-tab>
				<p-tab>
					<i class="pi pi-cog mr-2"></i>
					Settings
				</p-tab>
			</p-tablist>

			<p-tabpanels>
				<p-tabpanel>
					<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
						<p-card>
							<ng-template pTemplate="header">
								<div class="p-4 text-center">
									<i class="pi pi-trophy text-3xl text-yellow-600 mb-2"></i>
									<h3 class="text-lg font-semibold">Favorite Champions</h3>
								</div>
							</ng-template>
							<div class="space-y-3">
								@for (champion of profileData.favoriteChampions; track champion.name) {
									<div class="flex justify-between items-center">
										<span class="font-medium">{{ champion.name }}</span>
										<div class="text-right">
											<div class="text-sm font-semibold text-green-600">{{ champion.winRate }}%</div>
											<div class="text-xs text-gray-500">{{ champion.games }} games</div>
										</div>
									</div>
								}
							</div>
						</p-card>

						<p-card>
							<ng-template pTemplate="header">
								<div class="p-4 text-center">
									<i class="pi pi-users text-3xl text-blue-600 mb-2"></i>
									<h3 class="text-lg font-semibold">Role Performance</h3>
								</div>
							</ng-template>
							<div class="space-y-3">
								@for (role of profileData.rolePerformance; track role.name) {
									<div class="flex justify-between items-center">
										<span class="font-medium">{{ role.name }}</span>
										<div class="text-right">
											<div class="text-sm font-semibold text-blue-600">{{ role.winRate }}%</div>
											<div class="text-xs text-gray-500">{{ role.games }} games</div>
										</div>
									</div>
								}
							</div>
						</p-card>

						<p-card>
							<ng-template pTemplate="header">
								<div class="p-4 text-center">
									<i class="pi pi-calendar text-3xl text-purple-600 mb-2"></i>
									<h3 class="text-lg font-semibold">Recent Activity</h3>
								</div>
							</ng-template>
							<div class="space-y-2">
								<div class="text-sm text-gray-600">Last game: 2 hours ago</div>
								<div class="text-sm text-gray-600">Games today: 5</div>
								<div class="text-sm text-gray-600">Games this week: 23</div>
								<div class="text-sm text-gray-600">Peak rank: Diamond II</div>
							</div>
						</p-card>
					</div>
				</p-tabpanel>

				<p-tabpanel>
					<div class="max-w-2xl">
						<p-card>
							<ng-template pTemplate="header">
								<div class="p-4">
									<h3 class="text-lg font-semibold">Account Settings</h3>
								</div>
							</ng-template>
							<div class="space-y-6">
								<div>
									<label class="block text-sm font-medium text-gray-700 mb-2">
										Display Name
									</label>
									<div class="flex gap-2">
										<input
											type="text"
											[value]="user()?.riotTag || 'Player#TAG'"
											class="flex-1 px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
											readonly
										>
										<p-button
											label="Update"
											severity="secondary"
											size="small"
											[disabled]="true"
										></p-button>
									</div>
									<p class="text-xs text-gray-500 mt-1">
										Display name is synced with your Riot account
									</p>
								</div>

								<div>
									<label class="block text-sm font-medium text-gray-700 mb-2">
										Privacy Settings
									</label>
									<div class="space-y-2">
										<label class="flex items-center">
											<input type="checkbox"
											       class="mr-2"
											       checked>
											<span class="text-sm">Make profile public</span>
										</label>
										<label class="flex items-center">
											<input type="checkbox"
											       class="mr-2"
											       checked>
											<span class="text-sm">Allow team invitations</span>
										</label>
										<label class="flex items-center">
											<input type="checkbox"
											       class="mr-2">
											<span class="text-sm">Share match history</span>
										</label>
									</div>
								</div>

								<div class="pt-4 border-t">
									<p-button
										label="Save Settings"
										severity="primary"
									></p-button>
								</div>
							</div>
						</p-card>
					</div>
				</p-tabpanel>
			</p-tabpanels>
		</div>
	`,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileComponent
{
	private readonly authService = inject(AuthService);

	readonly user = this.authService.user;

	readonly profileData = {
		avatarUrl: 'https://via.placeholder.com/150/4F46E5/FFFFFF?text=Player',
		rank: 'Gold II',
		region: 'NA1',
		summonerLevel: 147,
		wins: 68,
		losses: 42,
		winRate: 62,
		lp: 1247,
		favoriteChampions: [
			{ name: 'Jinx', winRate: 75, games: 20 },
			{ name: 'Caitlyn', winRate: 68, games: 15 },
			{ name: 'Ezreal', winRate: 60, games: 12 }
		],
		rolePerformance: [
			{ name: 'ADC', winRate: 70, games: 35 },
			{ name: 'Support', winRate: 58, games: 12 },
			{ name: 'Mid', winRate: 55, games: 8 }
		]
	};

	getRankColor(rank: string): string
	{
		const rankColors: { [key: string]: string } = {
			'Iron': '#8B4513',
			'Bronze': '#CD7F32',
			'Silver': '#C0C0C0',
			'Gold': '#FFD700',
			'Platinum': '#00CED1',
			'Diamond': '#B9F2FF',
			'Master': '#9932CC',
			'Grandmaster': '#FF6347',
			'Challenger': '#F0E68C'
		};

		const rankTier = rank.split(' ')[0];
		return rankColors[rankTier] || '#6B7280';
	}
}
