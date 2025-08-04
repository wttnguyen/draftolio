import { Component, ChangeDetectionStrategy, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { AccordionModule } from 'primeng/accordion';
import { InputTextModule } from 'primeng/inputtext';
import { TabsModule } from 'primeng/tabs';

@Component({
	selector: 'app-help',
	imports: [
		CommonModule,
		FormsModule,
		RouterLink,
		ButtonModule,
		CardModule,
		AccordionModule,
		InputTextModule,
		TabsModule
	],
	template: `
		<div class="min-h-screen bg-gray-50">
			<div class="container mx-auto px-4 py-16 max-w-6xl">

				<!-- Header -->
				<div class="text-center mb-12">
					<h1 class="text-4xl font-bold text-gray-900 mb-4">Help Center</h1>
					<p class="text-lg text-gray-600 mb-8">
						Find answers to common questions and get the help you need to make the most of Draftolio AI.
					</p>

					<!-- Search Bar -->
					<div class="max-w-2xl mx-auto">
						<div class="relative">
							<input
								pInputText
								[(ngModel)]="searchQuery"
								placeholder="Search for help articles, guides, and FAQs..."
								class="w-full pl-12 pr-4 py-3 text-lg"
							/>
							<i class="pi pi-search absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
							<p-button
								label="Search"
								severity="primary"
								class="absolute right-2 top-1/2 transform -translate-y-1/2"
								size="small"
							></p-button>
						</div>
					</div>
				</div>

				<!-- Quick Help Cards -->
				<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-12">
					<p-card class="text-center cursor-pointer hover:shadow-lg transition-shadow duration-200">
						<ng-template pTemplate="header">
							<div class="p-6">
								<i class="pi pi-play text-4xl text-blue-600 mb-4"></i>
								<h3 class="text-lg font-semibold text-gray-900">Getting Started</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 text-sm">
							Learn the basics of using Draftolio AI for draft analysis and team optimization.
						</p>
					</p-card>

					<p-card class="text-center cursor-pointer hover:shadow-lg transition-shadow duration-200">
						<ng-template pTemplate="header">
							<div class="p-6">
								<i class="pi pi-users text-4xl text-green-600 mb-4"></i>
								<h3 class="text-lg font-semibold text-gray-900">Team Management</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 text-sm">
							Discover how to create, manage, and optimize your League of Legends teams.
						</p>
					</p-card>

					<p-card class="text-center cursor-pointer hover:shadow-lg transition-shadow duration-200">
						<ng-template pTemplate="header">
							<div class="p-6">
								<i class="pi pi-chart-line text-4xl text-purple-600 mb-4"></i>
								<h3 class="text-lg font-semibold text-gray-900">AI Analysis</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 text-sm">
							Understand how our AI provides draft recommendations and performance insights.
						</p>
					</p-card>

					<p-card class="text-center cursor-pointer hover:shadow-lg transition-shadow duration-200">
						<ng-template pTemplate="header">
							<div class="p-6">
								<i class="pi pi-cog text-4xl text-orange-600 mb-4"></i>
								<h3 class="text-lg font-semibold text-gray-900">Account Settings</h3>
							</div>
						</ng-template>
						<p class="text-gray-600 text-sm">
							Manage your profile, privacy settings, and subscription preferences.
						</p>
					</p-card>
				</div>

				<!-- Main Help Content -->
				<p-tablist>
					<p-tab>
						<i class="pi pi-question-circle mr-2"></i>
						Frequently Asked Questions
					</p-tab>
					<p-tab>
						<i class="pi pi-book mr-2"></i>
						Getting Started Guide
					</p-tab>
					<p-tab>
						<i class="pi pi-wrench mr-2"></i>
						Troubleshooting
					</p-tab>
					<p-tab>
						<i class="pi pi-headphones mr-2"></i>
						Contact Support
					</p-tab>
				</p-tablist>

				<p-tabpanels>
					<p-tabpanel>
						<div class="space-y-6">
							<p-accordion>
								@for (category of faqCategories; track category.title) {
									<p-accordion-panel>
										<p-accordion-header>{{ category.title }}</p-accordion-header>
										<p-accordion-content>
											<div class="space-y-4">
												@for (faq of category.questions; track faq.question) {
													<div class="border-b border-gray-200 pb-4 last:border-b-0 last:pb-0">
														<h4 class="font-semibold text-gray-900 mb-2">{{ faq.question }}</h4>
														<p class="text-gray-600"
														   [innerHTML]="faq.answer"></p>
													</div>
												}
											</div>
										</p-accordion-content>
									</p-accordion-panel>
								}
							</p-accordion>
						</div>
					</p-tabpanel>

					<p-tabpanel>
						<div class="space-y-8">
							<div>
								<h3 class="text-2xl font-bold text-gray-900 mb-4">Welcome to Draftolio AI</h3>
								<p class="text-gray-600 mb-6">
									Follow this step-by-step guide to get started with our AI-powered League of Legends draft analysis platform.
								</p>
							</div>

							@for (step of gettingStartedSteps; track step.title) {
								<p-card>
									<ng-template pTemplate="header">
										<div class="p-4 flex items-center">
											<div class="w-8 h-8 bg-blue-600 text-white rounded-full flex items-center justify-center mr-4 font-bold">
												{{ step.step }}
											</div>
											<h4 class="text-lg font-semibold text-gray-900">{{ step.title }}</h4>
										</div>
									</ng-template>
									<div class="space-y-3">
										<p class="text-gray-600">{{ step.description }}</p>
										@if (step.tips.length > 0) {
											<div>
												<h5 class="font-medium text-gray-900 mb-2">Tips:</h5>
												<ul class="list-disc list-inside text-gray-600 space-y-1">
													@for (tip of step.tips; track tip) {
														<li>{{ tip }}</li>
													}
												</ul>
											</div>
										}
									</div>
								</p-card>
							}
						</div>
					</p-tabpanel>

					<p-tabpanel>
						<div class="space-y-6">
							<div>
								<h3 class="text-2xl font-bold text-gray-900 mb-4">Common Issues and Solutions</h3>
								<p class="text-gray-600 mb-6">
									Having trouble? Check out these common issues and their solutions.
								</p>
							</div>

							<p-accordion>
								@for (issue of troubleshootingIssues; track issue.title) {
									<p-accordion-panel>
										<p-accordion-header>{{ issue.title }}</p-accordion-header>
										<p-accordion-content>
											<div class="space-y-4">
												<div>
													<h4 class="font-semibold text-gray-900 mb-2">Problem:</h4>
													<p class="text-gray-600">{{ issue.problem }}</p>
												</div>
												<div>
													<h4 class="font-semibold text-gray-900 mb-2">Solution:</h4>
													<div class="text-gray-600"
													     [innerHTML]="issue.solution"></div>
												</div>
												@if (issue.additionalHelp) {
													<div class="bg-blue-50 p-4 rounded-lg">
														<h5 class="font-medium text-blue-900 mb-2">Additional Help:</h5>
														<p class="text-blue-800 text-sm">{{ issue.additionalHelp }}</p>
													</div>
												}
											</div>
										</p-accordion-content>
									</p-accordion-panel>
								}
							</p-accordion>
						</div>
					</p-tabpanel>

					<p-tabpanel>
						<div class="space-y-8">
							<div>
								<h3 class="text-2xl font-bold text-gray-900 mb-4">Need More Help?</h3>
								<p class="text-gray-600 mb-6">
									Can't find what you're looking for? Our support team is here to help you.
								</p>
							</div>

							<div class="grid grid-cols-1 md:grid-cols-2 gap-8">
								<p-card>
									<ng-template pTemplate="header">
										<div class="p-6 text-center">
											<i class="pi pi-envelope text-4xl text-blue-600 mb-4"></i>
											<h4 class="text-xl font-semibold text-gray-900">Email Support</h4>
										</div>
									</ng-template>
									<div class="text-center space-y-4">
										<p class="text-gray-600">
											Get detailed help via email. We typically respond within 24 hours.
										</p>
										<p-button
											label="Send Email"
											icon="pi pi-envelope"
											severity="primary"
											routerLink="/contact"
										></p-button>
									</div>
								</p-card>

								<p-card>
									<ng-template pTemplate="header">
										<div class="p-6 text-center">
											<i class="pi pi-discord text-4xl text-purple-600 mb-4"></i>
											<h4 class="text-xl font-semibold text-gray-900">Discord Community</h4>
										</div>
									</ng-template>
									<div class="text-center space-y-4">
										<p class="text-gray-600">
											Join our Discord server for real-time help from our community and team.
										</p>
										<p-button
											label="Join Discord"
											icon="pi pi-discord"
											severity="secondary"
										></p-button>
									</div>
								</p-card>
							</div>

							<!-- Support Hours -->
							<p-card>
								<ng-template pTemplate="header">
									<div class="p-6">
										<h4 class="text-xl font-semibold text-gray-900">Support Hours & Response Times</h4>
									</div>
								</ng-template>
								<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
									<div>
										<h5 class="font-semibold text-gray-900 mb-3">Support Hours</h5>
										<div class="space-y-2 text-gray-600">
											<div class="flex justify-between">
												<span>Monday - Friday:</span>
												<span>9:00 AM - 6:00 PM PST</span>
											</div>
											<div class="flex justify-between">
												<span>Saturday:</span>
												<span>10:00 AM - 4:00 PM PST</span>
											</div>
											<div class="flex justify-between">
												<span>Sunday:</span>
												<span>Closed</span>
											</div>
										</div>
									</div>
									<div>
										<h5 class="font-semibold text-gray-900 mb-3">Response Times</h5>
										<div class="space-y-2 text-gray-600">
											<div class="flex justify-between">
												<span>General Questions:</span>
												<span class="text-blue-600 font-medium">24 hours</span>
											</div>
											<div class="flex justify-between">
												<span>Technical Issues:</span>
												<span class="text-green-600 font-medium">12 hours</span>
											</div>
											<div class="flex justify-between">
												<span>Critical Problems:</span>
												<span class="text-red-600 font-medium">2 hours</span>
											</div>
										</div>
									</div>
								</div>
							</p-card>
						</div>
					</p-tabpanel>
				</p-tabpanels>
			</div>
		</div>
	`,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class HelpComponent
{
	readonly searchQuery = signal('');

	readonly faqCategories = [
		{
			title: 'General Questions',
			questions: [
				{
					question: 'What is Draftolio AI?',
					answer: 'Draftolio AI is an advanced strategic analysis platform for League of Legends that uses artificial intelligence to provide draft recommendations, team composition optimization, and performance insights.'
				},
				{
					question: 'Is Draftolio AI free to use?',
					answer: 'Yes! We offer a free tier with basic features. Premium features are available with a subscription starting at $9.99/month.'
				},
				{
					question: 'Which regions are supported?',
					answer: 'We support all major League of Legends regions including NA, EUW, EUNE, KR, CN, BR, LAN, LAS, OCE, RU, TR, and JP.'
				},
				{
					question: 'How accurate are the AI predictions?',
					answer: 'Our AI models achieve 75-80% accuracy in predicting draft outcomes. Accuracy continuously improves as we gather more data and refine our algorithms.'
				}
			]
		},
		{
			title: 'Account & Setup',
			questions: [
				{
					question: 'How do I create an account?',
					answer: 'Click the "Login with Riot" button and authenticate with your Riot Games account. Your Draftolio AI account will be created automatically.'
				},
				{
					question: 'Can I use multiple League accounts?',
					answer: 'Currently, each Draftolio AI account is linked to one Riot Games account. You can change your linked account in your profile settings.'
				},
				{
					question: 'How do I update my profile information?',
					answer: 'Go to your <a href="/profile" class="text-blue-600 hover:text-blue-800 underline">Profile page</a> to update your settings, privacy preferences, and account information.'
				},
				{
					question: 'I forgot my password. How do I reset it?',
					answer: 'Since we use Riot Games authentication, you don\'t have a separate password for Draftolio AI. If you have issues with your Riot account, please contact Riot Games support.'
				}
			]
		},
		{
			title: 'Features & Usage',
			questions: [
				{
					question: 'How does the draft analysis work?',
					answer: 'Our AI analyzes team compositions, champion synergies, counter-picks, and current meta trends to provide real-time recommendations during champion select.'
				},
				{
					question: 'Can I use this during ranked games?',
					answer: 'Yes! Draftolio AI is designed to help you make better decisions during champion select in all game modes, including ranked.'
				},
				{
					question: 'How often is the data updated?',
					answer: 'Our database is updated in real-time with new match data. Patch changes and meta shifts are incorporated within hours of release.'
				},
				{
					question: 'Does this violate Riot Games\' Terms of Service?',
					answer: 'No. Draftolio AI only uses publicly available data and provides strategic insights. We do not automate gameplay or provide unfair advantages that violate Riot\'s ToS.'
				}
			]
		}
	];

	readonly gettingStartedSteps = [
		{
			step: 1,
			title: 'Create Your Account',
			description: 'Sign up using your Riot Games account to get started with Draftolio AI.',
			tips: [
				'Make sure your League of Legends account is active',
				'Your summoner data will be automatically imported',
				'You can update privacy settings after registration'
			]
		},
		{
			step: 2,
			title: 'Explore Your Dashboard',
			description: 'Familiarize yourself with the main dashboard and navigation.',
			tips: [
				'Check out your performance statistics',
				'Review recent match analysis',
				'Explore the different sections and features'
			]
		},
		{
			step: 3,
			title: 'Create Your First Team',
			description: 'Set up a team composition to start receiving AI recommendations.',
			tips: [
				'Add your regular teammates if you play in a group',
				'Specify preferred roles for each player',
				'Save multiple team configurations for different scenarios'
			]
		},
		{
			step: 4,
			title: 'Use Draft Analysis',
			description: 'Start using our AI-powered draft recommendations in your games.',
			tips: [
				'Keep the app open during champion select',
				'Pay attention to counter-pick suggestions',
				'Consider team synergy recommendations'
			]
		},
		{
			step: 5,
			title: 'Review and Improve',
			description: 'Analyze your performance and use insights to improve your gameplay.',
			tips: [
				'Check post-game analysis for learning opportunities',
				'Track your improvement over time',
				'Adjust your champion pool based on AI recommendations'
			]
		}
	];

	readonly troubleshootingIssues = [
		{
			title: 'Login Issues',
			problem: 'I can\'t log in with my Riot Games account.',
			solution: `
        <ol class="list-decimal list-inside space-y-2">
          <li>Make sure you're using the correct Riot ID (username#tagline)</li>
          <li>Clear your browser cache and cookies</li>
          <li>Try logging in from an incognito/private browser window</li>
          <li>Check if Riot Games services are experiencing issues</li>
          <li>Disable browser extensions that might interfere with authentication</li>
        </ol>
      `,
			additionalHelp: 'If you continue having issues, please contact our support team with your Riot ID and browser information.'
		},
		{
			title: 'Data Not Loading',
			problem: 'My match history or statistics are not showing up.',
			solution: `
        <ol class="list-decimal list-inside space-y-2">
          <li>Refresh the page and wait a few moments for data to load</li>
          <li>Check if your League of Legends profile is set to public</li>
          <li>Ensure you've played recent matches (data may take time to sync)</li>
          <li>Try logging out and logging back in</li>
          <li>Check your internet connection</li>
        </ol>
      `,
			additionalHelp: 'Data synchronization can take up to 30 minutes for new matches to appear.'
		},
		{
			title: 'AI Recommendations Not Working',
			problem: 'I\'m not receiving draft recommendations or they seem inaccurate.',
			solution: `
        <ol class="list-decimal list-inside space-y-2">
          <li>Ensure your team composition is properly set up</li>
          <li>Check that all team members have valid summoner names</li>
          <li>Verify that the current patch data has been loaded</li>
          <li>Make sure you're in a supported game mode</li>
          <li>Try refreshing the analysis or creating a new team setup</li>
        </ol>
      `,
			additionalHelp: 'AI recommendations improve over time as we gather more data about your playstyle and preferences.'
		},
		{
			title: 'Performance Issues',
			problem: 'The website is loading slowly or not responding.',
			solution: `
        <ol class="list-decimal list-inside space-y-2">
          <li>Check your internet connection speed</li>
          <li>Close unnecessary browser tabs and applications</li>
          <li>Clear your browser cache and cookies</li>
          <li>Try using a different browser</li>
          <li>Disable browser extensions temporarily</li>
          <li>Check if other websites are loading normally</li>
        </ol>
      `,
			additionalHelp: 'If performance issues persist, it may be due to high server load. Please try again later or contact support.'
		}
	];
}
