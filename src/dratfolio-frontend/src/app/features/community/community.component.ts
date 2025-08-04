import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { AvatarModule } from 'primeng/avatar';
import { ChipModule } from 'primeng/chip';
import { TabsModule } from 'primeng/tabs';

@Component({
	selector: 'app-community',
	imports: [
		CommonModule,
		ButtonModule,
		CardModule,
		AvatarModule,
		ChipModule,
		TabsModule
	],
	template: `
		<div class="min-h-screen bg-gradient-to-br from-purple-50 to-blue-100">
			<div class="container mx-auto px-4 py-16">

				<!-- Hero Section -->
				<div class="text-center mb-16">
					<h1 class="text-5xl font-bold text-gray-900 mb-6">
						Join Our <span class="text-purple-600">Community</span>
					</h1>
					<p class="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
						Connect with thousands of League of Legends players, share strategies,
						get help from experts, and stay updated with the latest meta trends.
					</p>
					<div class="flex flex-col sm:flex-row gap-4 justify-center">
						<p-button
							label="Join Discord Server"
							icon="pi pi-discord"
							severity="primary"
							size="large"
						></p-button>
						<p-button
							label="Browse Forums"
							icon="pi pi-comments"
							severity="secondary"
							[outlined]="true"
							size="large"
						></p-button>
					</div>
				</div>

				<!-- Community Stats -->
				<div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-16">
					<p-card class="text-center">
						<ng-template pTemplate="header">
							<div class="p-4">
								<i class="pi pi-users text-3xl text-blue-600 mb-2"></i>
								<h3 class="text-2xl font-bold text-gray-900">15,000+</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 font-medium">Active Members</p>
					</p-card>

					<p-card class="text-center">
						<ng-template pTemplate="header">
							<div class="p-4">
								<i class="pi pi-comments text-3xl text-green-600 mb-2"></i>
								<h3 class="text-2xl font-bold text-gray-900">50,000+</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 font-medium">Forum Posts</p>
					</p-card>

					<p-card class="text-center">
						<ng-template pTemplate="header">
							<div class="p-4">
								<i class="pi pi-star text-3xl text-yellow-600 mb-2"></i>
								<h3 class="text-2xl font-bold text-gray-900">1,200+</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 font-medium">Expert Contributors</p>
					</p-card>

					<p-card class="text-center">
						<ng-template pTemplate="header">
							<div class="p-4">
								<i class="pi pi-globe text-3xl text-purple-600 mb-2"></i>
								<h3 class="text-2xl font-bold text-gray-900">25+</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 font-medium">Countries</p>
					</p-card>
				</div>

				<!-- Community Features -->
				<div class="mb-16">
					<h2 class="text-3xl font-bold text-gray-900 text-center mb-12">Community Features</h2>
					<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">

						<p-card class="h-full text-center">
							<ng-template pTemplate="header">
								<div class="p-6">
									<i class="pi pi-discord text-4xl text-purple-600 mb-4"></i>
									<h3 class="text-xl font-semibold text-gray-900">Discord Server</h3>
								</div>
							</ng-template>
							<p class="text-gray-600 mb-4">
								Join our active Discord community for real-time discussions, voice channels for team coordination, and instant help from
								experienced players.
							</p>
							<ng-template pTemplate="footer">
								<p-button
									label="Join Discord"
									severity="primary"
									size="small"
								></p-button>
							</ng-template>
						</p-card>

						<p-card class="h-full text-center">
							<ng-template pTemplate="header">
								<div class="p-6">
									<i class="pi pi-comments text-4xl text-blue-600 mb-4"></i>
									<h3 class="text-xl font-semibold text-gray-900">Discussion Forums</h3>
								</div>
							</ng-template>
							<p class="text-gray-600 mb-4">
								Participate in detailed discussions about strategies, champion guides, meta analysis, and get feedback on your gameplay.
							</p>
							<ng-template pTemplate="footer">
								<p-button
									label="Browse Forums"
									severity="secondary"
									size="small"
								></p-button>
							</ng-template>
						</p-card>

						<p-card class="h-full text-center">
							<ng-template pTemplate="header">
								<div class="p-6">
									<i class="pi pi-trophy text-4xl text-yellow-600 mb-4"></i>
									<h3 class="text-xl font-semibold text-gray-900">Tournaments</h3>
								</div>
							</ng-template>
							<p class="text-gray-600 mb-4">
								Participate in community tournaments, scrimmages, and events. Test your skills and win prizes while having fun.
							</p>
							<ng-template pTemplate="footer">
								<p-button
									label="View Events"
									severity="warn"
									size="small"
								></p-button>
							</ng-template>
						</p-card>

						<p-card class="h-full text-center">
							<ng-template pTemplate="header">
								<div class="p-6">
									<i class="pi pi-graduation-cap text-4xl text-green-600 mb-4"></i>
									<h3 class="text-xl font-semibold text-gray-900">Coaching & Mentorship</h3>
								</div>
							</ng-template>
							<p class="text-gray-600 mb-4">
								Connect with experienced coaches and mentors who can help you improve your gameplay and climb the ranked ladder.
							</p>
							<ng-template pTemplate="footer">
								<p-button
									label="Find Coaches"
									severity="success"
									size="small"
								></p-button>
							</ng-template>
						</p-card>

						<p-card class="h-full text-center">
							<ng-template pTemplate="header">
								<div class="p-6">
									<i class="pi pi-users text-4xl text-red-600 mb-4"></i>
									<h3 class="text-xl font-semibold text-gray-900">Team Finder</h3>
								</div>
							</ng-template>
							<p class="text-gray-600 mb-4">
								Looking for teammates? Use our team finder to connect with players of similar skill level and playstyle preferences.
							</p>
							<ng-template pTemplate="footer">
								<p-button
									label="Find Teams"
									severity="danger"
									size="small"
								></p-button>
							</ng-template>
						</p-card>

						<p-card class="h-full text-center">
							<ng-template pTemplate="header">
								<div class="p-6">
									<i class="pi pi-book text-4xl text-indigo-600 mb-4"></i>
									<h3 class="text-xl font-semibold text-gray-900">Strategy Guides</h3>
								</div>
							</ng-template>
							<p class="text-gray-600 mb-4">
								Access community-created guides, champion builds, and strategic insights shared by high-ranking players and coaches.
							</p>
							<ng-template pTemplate="footer">
								<p-button
									label="Read Guides"
									severity="help"
									size="small"
								></p-button>
							</ng-template>
						</p-card>
					</div>
				</div>

				<!-- Community Content -->
				<p-tablist>
					<p-tab>
						<i class="pi pi-comments mr-2"></i>
						Recent Discussions
					</p-tab>
					<p-tab>
						<i class="pi pi-star mr-2"></i>
						Top Contributors
					</p-tab>
					<p-tab>
						<i class="pi pi-calendar mr-2"></i>
						Upcoming Events
					</p-tab>
				</p-tablist>

				<p-tabpanels>
					<p-tabpanel>
						<div class="space-y-6">
							@for (discussion of recentDiscussions; track discussion.id) {
								<p-card class="hover:shadow-lg transition-shadow duration-200">
									<div class="flex items-start space-x-4">
										<p-avatar
											[image]="discussion.author.avatar"
											size="large"
											shape="circle"
										></p-avatar>
										<div class="flex-1">
											<div class="flex items-center space-x-2 mb-2">
												<h3 class="text-lg font-semibold text-gray-900">{{ discussion.title }}</h3>
												<p-chip
													[label]="discussion.category"
													[style]="{'background-color': discussion.categoryColor}"
													class="text-white"
													size="small"
												></p-chip>
											</div>
											<p class="text-gray-600 mb-3">{{ discussion.excerpt }}</p>
											<div class="flex items-center justify-between text-sm text-gray-500">
												<div class="flex items-center space-x-4">
													<span>by {{ discussion.author.name }}</span>
													<span>{{ discussion.timeAgo }}</span>
												</div>
												<div class="flex items-center space-x-4">
													<span><i class="pi pi-eye mr-1"></i>{{ discussion.views }}</span>
													<span><i class="pi pi-comments mr-1"></i>{{ discussion.replies }}</span>
													<span><i class="pi pi-heart mr-1"></i>{{ discussion.likes }}</span>
												</div>
											</div>
										</div>
									</div>
								</p-card>
							}
						</div>
					</p-tabpanel>

					<p-tabpanel>
						<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
							@for (contributor of topContributors; track contributor.id) {
								<p-card class="text-center">
									<ng-template pTemplate="header">
										<div class="p-6">
											<p-avatar
												[image]="contributor.avatar"
												size="xlarge"
												shape="circle"
												class="mb-4"
											></p-avatar>
											<h3 class="text-lg font-semibold text-gray-900">{{ contributor.name }}</h3>
											<p class="text-sm text-gray-600">{{ contributor.title }}</p>
										</div>
									</ng-template>
									<div class="space-y-2 mb-4">
										<div class="flex justify-between">
											<span class="text-gray-600">Rank:</span>
											<span class="font-semibold text-blue-600">{{ contributor.rank }}</span>
										</div>
										<div class="flex justify-between">
											<span class="text-gray-600">Posts:</span>
											<span class="font-semibold">{{ contributor.posts }}</span>
										</div>
										<div class="flex justify-between">
											<span class="text-gray-600">Reputation:</span>
											<span class="font-semibold text-green-600">{{ contributor.reputation }}</span>
										</div>
									</div>
									<ng-template pTemplate="footer">
										<p-button
											label="View Profile"
											severity="secondary"
											size="small"
											[outlined]="true"
										></p-button>
									</ng-template>
								</p-card>
							}
						</div>
					</p-tabpanel>

					<p-tabpanel>
						<div class="space-y-6">
							@for (event of upcomingEvents; track event.id) {
								<p-card>
									<div class="flex flex-col md:flex-row justify-between items-start md:items-center">
										<div class="flex-1 mb-4 md:mb-0">
											<div class="flex items-center space-x-3 mb-2">
												<i [class]="event.icon + ' text-2xl text-blue-600'"></i>
												<h3 class="text-xl font-semibold text-gray-900">{{ event.title }}</h3>
											</div>
											<p class="text-gray-600 mb-2">{{ event.description }}</p>
											<div class="flex items-center space-x-4 text-sm text-gray-500">
												<span><i class="pi pi-calendar mr-1"></i>{{ event.date }}</span>
												<span><i class="pi pi-clock mr-1"></i>{{ event.time }}</span>
												<span><i class="pi pi-users mr-1"></i>{{ event.participants }} participants</span>
											</div>
										</div>
										<div class="flex flex-col space-y-2">
											<p-button
												[label]="event.registered ? 'Registered' : 'Join Event'"
												[severity]="event.registered ? 'success' : 'primary'"
												size="small"
												[disabled]="event.registered"
											></p-button>
											@if (event.prize) {
												<p class="text-sm text-center text-green-600 font-semibold">
													Prize: {{ event.prize }}
												</p>
											}
										</div>
									</div>
								</p-card>
							}
						</div>
					</p-tabpanel>
				</p-tabpanels>

				<!-- CTA Section -->
				<div class="mt-16 text-center bg-white rounded-lg shadow-lg p-12">
					<h2 class="text-3xl font-bold text-gray-900 mb-4">
						Ready to Join the Community?
					</h2>
					<p class="text-lg text-gray-600 mb-8 max-w-2xl mx-auto">
						Connect with like-minded players, improve your skills, and be part of the most active
						League of Legends community focused on strategic gameplay.
					</p>
					<div class="flex flex-col sm:flex-row gap-4 justify-center">
						<p-button
							label="Join Discord Now"
							icon="pi pi-discord"
							severity="primary"
							size="large"
						></p-button>
						<p-button
							label="Create Account"
							icon="pi pi-user-plus"
							severity="secondary"
							[outlined]="true"
							size="large"
						></p-button>
					</div>
				</div>
			</div>
		</div>
	`,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class CommunityComponent
{
	readonly recentDiscussions = [
		{
			id: 1,
			title: 'Best ADC Champions for Current Meta',
			excerpt: 'Discussion about the strongest ADC picks in the current patch and their optimal builds...',
			author: {
				name: 'ProPlayer123',
				avatar: 'https://via.placeholder.com/50/3B82F6/FFFFFF?text=PP'
			},
			category: 'Strategy',
			categoryColor: '#3B82F6',
			timeAgo: '2 hours ago',
			views: 1247,
			replies: 23,
			likes: 45
		},
		{
			id: 2,
			title: 'Team Composition Guide for Ranked',
			excerpt: 'Comprehensive guide on building effective team compositions for different ranks...',
			author: {
				name: 'CoachMaster',
				avatar: 'https://via.placeholder.com/50/10B981/FFFFFF?text=CM'
			},
			category: 'Guide',
			categoryColor: '#10B981',
			timeAgo: '5 hours ago',
			views: 892,
			replies: 17,
			likes: 67
		},
		{
			id: 3,
			title: 'Looking for Duo Partner (Gold Rank)',
			excerpt: 'Gold II ADC main looking for a support duo partner for ranked climbing...',
			author: {
				name: 'GoldClimber',
				avatar: 'https://via.placeholder.com/50/F59E0B/FFFFFF?text=GC'
			},
			category: 'LFG',
			categoryColor: '#F59E0B',
			timeAgo: '1 day ago',
			views: 234,
			replies: 8,
			likes: 12
		}
	];

	readonly topContributors = [
		{
			id: 1,
			name: 'StrategicMind',
			title: 'Master Tier Coach',
			avatar: 'https://via.placeholder.com/100/8B5CF6/FFFFFF?text=SM',
			rank: 'Master 200 LP',
			posts: 1247,
			reputation: 9850
		},
		{
			id: 2,
			name: 'MetaAnalyst',
			title: 'Data Scientist',
			avatar: 'https://via.placeholder.com/100/EF4444/FFFFFF?text=MA',
			rank: 'Diamond I',
			posts: 892,
			reputation: 7420
		},
		{
			id: 3,
			name: 'ChampionGuru',
			title: 'Guide Creator',
			avatar: 'https://via.placeholder.com/100/06B6D4/FFFFFF?text=CG',
			rank: 'Diamond II',
			posts: 634,
			reputation: 5680
		}
	];

	readonly upcomingEvents = [
		{
			id: 1,
			title: 'Weekly Community Tournament',
			description: 'Join our weekly 5v5 tournament for players of all skill levels. Great prizes and fun guaranteed!',
			date: 'Saturday, Jan 6',
			time: '2:00 PM PST',
			participants: 64,
			prize: '$500 Prize Pool',
			icon: 'pi pi-trophy',
			registered: false
		},
		{
			id: 2,
			title: 'Meta Discussion: Patch 14.1 Analysis',
			description: 'Live discussion with pro players and analysts about the latest patch changes and meta shifts.',
			date: 'Sunday, Jan 7',
			time: '6:00 PM PST',
			participants: 156,
			prize: null,
			icon: 'pi pi-comments',
			registered: true
		},
		{
			id: 3,
			title: 'Coaching Workshop: Team Fighting',
			description: 'Learn advanced team fighting techniques from Master+ coaches in this interactive workshop.',
			date: 'Wednesday, Jan 10',
			time: '7:00 PM PST',
			participants: 32,
			prize: null,
			icon: 'pi pi-graduation-cap',
			registered: false
		}
	];
}
