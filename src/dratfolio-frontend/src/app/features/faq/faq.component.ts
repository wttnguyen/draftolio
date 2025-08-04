import { Component, ChangeDetectionStrategy, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { AccordionModule } from 'primeng/accordion';
import { InputTextModule } from 'primeng/inputtext';
import { ChipModule } from 'primeng/chip';

@Component({
	selector: 'app-faq',
	imports: [
		CommonModule,
		FormsModule,
		RouterLink,
		ButtonModule,
		CardModule,
		AccordionModule,
		InputTextModule,
		ChipModule
	],
	template: `
    <div class="min-h-screen bg-gray-50">
      <div class="container mx-auto px-4 py-16 max-w-6xl">
        
        <!-- Header -->
        <div class="text-center mb-12">
          <h1 class="text-4xl font-bold text-gray-900 mb-4">Frequently Asked Questions</h1>
          <p class="text-lg text-gray-600 mb-8">
            Find quick answers to the most common questions about Draftolio AI.
          </p>
          
          <!-- Search Bar -->
          <div class="max-w-2xl mx-auto mb-8">
            <div class="relative">
              <input 
                pInputText
                [(ngModel)]="searchQuery"
                placeholder="Search FAQ..."
                class="w-full pl-12 pr-4 py-3 text-lg"
              />
              <i class="pi pi-search absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
            </div>
          </div>

          <!-- Category Filters -->
          <div class="flex flex-wrap justify-center gap-2 mb-8">
            <p-chip 
              label="All" 
              [class]="selectedCategory() === 'all' ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-700 cursor-pointer hover:bg-gray-300'"
              (click)="setCategory('all')"
            ></p-chip>
            @for (category of categories; track category.id) {
              <p-chip 
                [label]="category.name" 
                [class]="selectedCategory() === category.id ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-700 cursor-pointer hover:bg-gray-300'"
                (click)="setCategory(category.id)"
              ></p-chip>
            }
          </div>
        </div>

        <!-- Quick Stats -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          <p-card class="text-center">
            <ng-template pTemplate="header">
              <div class="p-4">
                <i class="pi pi-question-circle text-3xl text-blue-600 mb-2"></i>
                <h3 class="text-lg font-semibold text-gray-900">{{ totalQuestions }} Questions</h3>
              </div>
            </ng-template>
            <p class="text-gray-600 text-sm">
              Comprehensive answers to help you get the most out of Draftolio AI.
            </p>
          </p-card>

          <p-card class="text-center">
            <ng-template pTemplate="header">
              <div class="p-4">
                <i class="pi pi-clock text-3xl text-green-600 mb-2"></i>
                <h3 class="text-lg font-semibold text-gray-900">Updated Daily</h3>
              </div>
            </ng-template>
            <p class="text-gray-600 text-sm">
              Our FAQ is regularly updated with new questions and improved answers.
            </p>
          </p-card>

          <p-card class="text-center">
            <ng-template pTemplate="header">
              <div class="p-4">
                <i class="pi pi-headphones text-3xl text-purple-600 mb-2"></i>
                <h3 class="text-lg font-semibold text-gray-900">Still Need Help?</h3>
              </div>
            </ng-template>
            <p class="text-gray-600 text-sm">
              Can't find what you're looking for? Contact our support team.
            </p>
            <ng-template pTemplate="footer">
              <p-button 
                label="Contact Support" 
                severity="primary"
                size="small"
                routerLink="/contact"
              ></p-button>
            </ng-template>
          </p-card>
        </div>

        <!-- FAQ Content -->
        <div class="space-y-8">
          @for (category of filteredCategories(); track category.id) {
            <div>
              @if (selectedCategory() === 'all') {
                <h2 class="text-2xl font-bold text-gray-900 mb-6 flex items-center">
                  <i [class]="category.icon + ' text-blue-600 mr-3'"></i>
                  {{ category.name }}
                </h2>
              }
              
              <p-accordion>
                @for (faq of category.questions; track faq.question) {
                  <p-accordion-panel>
                    <p-accordion-header>{{ faq.question }}</p-accordion-header>
                    <p-accordion-content>
                      <div class="space-y-4">
                        <div [innerHTML]="faq.answer" class="text-gray-600"></div>
                        
                        @if (faq.tags && faq.tags.length > 0) {
                          <div class="flex flex-wrap gap-2 pt-3 border-t border-gray-200">
                            <span class="text-sm text-gray-500 mr-2">Tags:</span>
                            @for (tag of faq.tags; track tag) {
                              <p-chip [label]="tag" styleClass="p-chip-outlined" size="small"></p-chip>
                            }
                          </div>
                        }
                        
                        @if (faq.relatedLinks && faq.relatedLinks.length > 0) {
                          <div class="pt-3 border-t border-gray-200">
                            <h5 class="text-sm font-medium text-gray-900 mb-2">Related:</h5>
                            <div class="space-y-1">
                              @for (link of faq.relatedLinks; track link.url) {
                                <a 
                                  [routerLink]="link.url" 
                                  class="block text-sm text-blue-600 hover:text-blue-800 underline"
                                >
                                  {{ link.title }}
                                </a>
                              }
                            </div>
                          </div>
                        }
                      </div>
                    </p-accordion-content>
                  </p-accordion-panel>
                }
              </p-accordion>
            </div>
          }
        </div>

        <!-- No Results -->
        @if (filteredCategories().length === 0) {
          <div class="text-center py-12">
            <i class="pi pi-search text-6xl text-gray-400 mb-4"></i>
            <h3 class="text-xl font-semibold text-gray-900 mb-2">No results found</h3>
            <p class="text-gray-600 mb-6">
              We couldn't find any questions matching your search. Try different keywords or browse all categories.
            </p>
            <div class="flex flex-col sm:flex-row gap-4 justify-center">
              <p-button 
                label="Clear Search" 
                severity="secondary"
                (onClick)="clearSearch()"
              ></p-button>
              <p-button 
                label="Ask a Question" 
                severity="primary"
                routerLink="/contact"
              ></p-button>
            </div>
          </div>
        }

        <!-- Popular Questions -->
        @if (searchQuery() === '' && selectedCategory() === 'all') {
          <div class="mt-16">
            <h2 class="text-2xl font-bold text-gray-900 text-center mb-8">Most Popular Questions</h2>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              @for (faq of popularQuestions; track faq.question) {
                <p-card class="cursor-pointer hover:shadow-lg transition-shadow duration-200">
                  <div class="flex items-start space-x-3">
                    <i class="pi pi-star text-yellow-500 text-lg mt-1"></i>
                    <div class="flex-1">
                      <h4 class="font-semibold text-gray-900 mb-2">{{ faq.question }}</h4>
                      <p class="text-gray-600 text-sm line-clamp-2">{{ faq.shortAnswer }}</p>
                    </div>
                  </div>
                </p-card>
              }
            </div>
          </div>
        }

        <!-- CTA Section -->
        <div class="mt-16 text-center bg-white rounded-lg shadow-lg p-12">
          <h2 class="text-3xl font-bold text-gray-900 mb-4">
            Still Have Questions?
          </h2>
          <p class="text-lg text-gray-600 mb-8 max-w-2xl mx-auto">
            Our support team is here to help you get the most out of Draftolio AI. 
            Don't hesitate to reach out if you need assistance.
          </p>
          <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <p-button 
              label="Contact Support" 
              icon="pi pi-envelope"
              severity="primary"
              size="large"
              routerLink="/contact"
            ></p-button>
            <p-button 
              label="Visit Help Center" 
              icon="pi pi-book"
              severity="secondary"
              [outlined]="true"
              size="large"
              routerLink="/help"
            ></p-button>
          </div>
        </div>
      </div>
    </div>
  `,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class FaqComponent
{
	readonly searchQuery = signal('');
	readonly selectedCategory = signal('all');

	readonly categories = [
		{ id: 'general', name: 'General', icon: 'pi pi-info-circle' },
		{ id: 'account', name: 'Account & Setup', icon: 'pi pi-user' },
		{ id: 'features', name: 'Features & Usage', icon: 'pi pi-cog' },
		{ id: 'technical', name: 'Technical Issues', icon: 'pi pi-wrench' },
		{ id: 'billing', name: 'Billing & Subscription', icon: 'pi pi-credit-card' },
		{ id: 'privacy', name: 'Privacy & Security', icon: 'pi pi-shield' }
	];

	readonly faqData = [
		{
			id: 'general',
			name: 'General',
			icon: 'pi pi-info-circle',
			questions: [
				{
					question: 'What is Draftolio AI?',
					answer: 'Draftolio AI is an advanced strategic analysis platform for League of Legends that uses artificial intelligence to provide draft recommendations, team composition optimization, and performance insights. Our AI analyzes millions of matches to help you make better decisions during champion select.',
					tags: [ 'overview', 'ai', 'league of legends' ],
					relatedLinks: [
						{ title: 'Getting Started Guide', url: '/help' },
						{ title: 'About Us', url: '/about' }
					]
				},
				{
					question: 'How accurate are the AI predictions?',
					answer: 'Our AI models achieve 75-80% accuracy in predicting draft outcomes. The accuracy varies based on factors like rank, region, and patch version. We continuously improve our models by analyzing new match data and incorporating feedback from the community.',
					tags: [ 'accuracy', 'ai', 'predictions' ],
					relatedLinks: []
				},
				{
					question: 'Which regions and game modes are supported?',
					answer: 'We support all major League of Legends regions including NA, EUW, EUNE, KR, CN, BR, LAN, LAS, OCE, RU, TR, and JP. Our analysis works for Ranked Solo/Duo, Ranked Flex, Normal Draft, and Tournament Draft modes.',
					tags: [ 'regions', 'game modes', 'support' ],
					relatedLinks: []
				},
				{
					question: 'Is Draftolio AI approved by Riot Games?',
					answer: 'While we are not officially endorsed by Riot Games, Draftolio AI complies with their Terms of Service. We only use publicly available data and provide strategic insights without automating gameplay or providing unfair advantages.',
					tags: [ 'riot games', 'terms of service', 'compliance' ],
					relatedLinks: [
						{ title: 'Terms of Service', url: '/terms' }
					]
				}
			]
		},
		{
			id: 'account',
			name: 'Account & Setup',
			icon: 'pi pi-user',
			questions: [
				{
					question: 'How do I create an account?',
					answer: 'Creating an account is simple! Click the "Login with Riot" button on our homepage and authenticate with your Riot Games account. Your Draftolio AI account will be created automatically, and your summoner data will be imported.',
					tags: [ 'account creation', 'riot login', 'setup' ],
					relatedLinks: [
						{ title: 'Getting Started Guide', url: '/help' }
					]
				},
				{
					question: 'Can I link multiple League of Legends accounts?',
					answer: 'Currently, each Draftolio AI account is linked to one Riot Games account. However, you can change your linked account in your profile settings. We\'re working on supporting multiple accounts in a future update.',
					tags: [ 'multiple accounts', 'account linking', 'profile' ],
					relatedLinks: [
						{ title: 'Profile Settings', url: '/profile' }
					]
				},
				{
					question: 'Why can\'t I see my match history?',
					answer: 'If your match history isn\'t showing up, make sure your League of Legends profile is set to public in the Riot client. Go to Settings → Privacy → Match History and set it to "Public". It may take up to 30 minutes for data to sync after making this change.',
					tags: [ 'match history', 'privacy settings', 'data sync' ],
					relatedLinks: []
				},
				{
					question: 'How do I delete my account?',
					answer: 'You can delete your account at any time by going to your Profile Settings and clicking "Delete Account". This will permanently remove all your data from our servers. Please note that this action cannot be undone.',
					tags: [ 'delete account', 'data removal', 'privacy' ],
					relatedLinks: [
						{ title: 'Privacy Policy', url: '/privacy' }
					]
				}
			]
		},
		{
			id: 'features',
			name: 'Features & Usage',
			icon: 'pi pi-cog',
			questions: [
				{
					question: 'How does the draft analysis work?',
					answer: 'Our AI analyzes team compositions in real-time, considering champion synergies, counter-picks, current meta trends, and your personal performance history. It provides recommendations for champion picks, bans, and role assignments to maximize your team\'s win probability.',
					tags: [ 'draft analysis', 'ai recommendations', 'team composition' ],
					relatedLinks: [
						{ title: 'Help Center', url: '/help' }
					]
				},
				{
					question: 'Can I use this during ranked games?',
					answer: 'Yes! Draftolio AI is designed to help you make better decisions during champion select in all game modes, including ranked. Our recommendations are based on strategic analysis and don\'t violate Riot\'s Terms of Service.',
					tags: [ 'ranked games', 'champion select', 'strategy' ],
					relatedLinks: []
				},
				{
					question: 'How often is the data updated?',
					answer: 'Our database is updated in real-time with new match data from all supported regions. Patch changes and meta shifts are incorporated within hours of release. Champion statistics and win rates are refreshed every 15 minutes.',
					tags: [ 'data updates', 'real-time', 'patch changes' ],
					relatedLinks: []
				},
				{
					question: 'What makes your AI different from other tools?',
					answer: 'Our AI uses advanced machine learning models trained on millions of matches, including professional games. We consider not just champion statistics, but also team synergies, player performance history, and current meta trends to provide personalized recommendations.',
					tags: [ 'ai technology', 'machine learning', 'personalization' ],
					relatedLinks: [
						{ title: 'About Our Technology', url: '/about' }
					]
				}
			]
		},
		{
			id: 'technical',
			name: 'Technical Issues',
			icon: 'pi pi-wrench',
			questions: [
				{
					question: 'The website is loading slowly. What can I do?',
					answer: 'Try these steps: 1) Clear your browser cache and cookies, 2) Disable browser extensions temporarily, 3) Try a different browser, 4) Check your internet connection. If the problem persists, it may be due to high server load during peak hours.',
					tags: [ 'performance', 'loading issues', 'troubleshooting' ],
					relatedLinks: [
						{ title: 'Troubleshooting Guide', url: '/help' }
					]
				},
				{
					question: 'I\'m getting login errors. How do I fix this?',
					answer: 'Login issues are usually caused by: 1) Incorrect Riot ID format (should be username#tagline), 2) Browser cache issues, 3) Riot Games service outages. Try clearing your browser data and logging in from an incognito window.',
					tags: [ 'login errors', 'authentication', 'riot id' ],
					relatedLinks: []
				},
				{
					question: 'The AI recommendations seem inaccurate. Why?',
					answer: 'AI recommendations may seem off if: 1) Your team composition isn\'t properly set up, 2) You\'re playing an unsupported game mode, 3) The current patch data is still being processed. Try refreshing the analysis or creating a new team setup.',
					tags: [ 'ai accuracy', 'recommendations', 'team setup' ],
					relatedLinks: []
				},
				{
					question: 'Which browsers are supported?',
					answer: 'Draftolio AI works best on modern browsers including Chrome 90+, Firefox 88+, Safari 14+, and Edge 90+. We recommend keeping your browser updated for the best experience. Internet Explorer is not supported.',
					tags: [ 'browser support', 'compatibility', 'requirements' ],
					relatedLinks: []
				}
			]
		},
		{
			id: 'billing',
			name: 'Billing & Subscription',
			icon: 'pi pi-credit-card',
			questions: [
				{
					question: 'Is Draftolio AI free to use?',
					answer: 'Yes! We offer a free tier with basic features including draft analysis and team management. Premium features like advanced analytics, detailed performance insights, and priority support are available with a subscription starting at $9.99/month.',
					tags: [ 'free tier', 'pricing', 'premium features' ],
					relatedLinks: []
				},
				{
					question: 'What payment methods do you accept?',
					answer: 'We accept all major credit cards (Visa, MasterCard, American Express), PayPal, and various regional payment methods. All payments are processed securely through our payment partners.',
					tags: [ 'payment methods', 'credit cards', 'paypal' ],
					relatedLinks: []
				},
				{
					question: 'Can I cancel my subscription anytime?',
					answer: 'Yes, you can cancel your subscription at any time from your account settings. Cancellation takes effect at the end of your current billing period, and you\'ll retain access to premium features until then.',
					tags: [ 'cancellation', 'subscription management', 'billing' ],
					relatedLinks: [
						{ title: 'Manage Subscription', url: '/profile' }
					]
				},
				{
					question: 'Do you offer refunds?',
					answer: 'We offer a 30-day money-back guarantee for new subscribers. If you\'re not satisfied with our premium features, contact our support team within 30 days of your first payment for a full refund.',
					tags: [ 'refunds', 'money-back guarantee', 'support' ],
					relatedLinks: [
						{ title: 'Contact Support', url: '/contact' }
					]
				}
			]
		},
		{
			id: 'privacy',
			name: 'Privacy & Security',
			icon: 'pi pi-shield',
			questions: [
				{
					question: 'What data do you collect?',
					answer: 'We collect publicly available League of Legends match data, your Riot ID, and basic usage analytics. We never access private information like chat logs, friend lists, or payment details from your Riot account.',
					tags: [ 'data collection', 'privacy', 'riot data' ],
					relatedLinks: [
						{ title: 'Privacy Policy', url: '/privacy' }
					]
				},
				{
					question: 'Do you sell my data to third parties?',
					answer: 'No, we never sell your personal data to third parties. We may share aggregated, anonymized statistics for research purposes, but individual player data is never sold or shared without your explicit consent.',
					tags: [ 'data selling', 'third parties', 'privacy' ],
					relatedLinks: [
						{ title: 'Privacy Policy', url: '/privacy' }
					]
				},
				{
					question: 'How do you protect my account security?',
					answer: 'We use industry-standard security measures including SSL encryption, secure authentication through Riot Games, and regular security audits. We never store your Riot Games password or other sensitive credentials.',
					tags: [ 'security', 'encryption', 'authentication' ],
					relatedLinks: []
				},
				{
					question: 'Can I request my data to be deleted?',
					answer: 'Yes, you have the right to request deletion of your personal data. You can delete your account in your profile settings, or contact our support team for assistance. We\'ll remove all your data within 30 days.',
					tags: [ 'data deletion', 'gdpr', 'privacy rights' ],
					relatedLinks: [
						{ title: 'Privacy Policy', url: '/privacy' },
						{ title: 'Contact Support', url: '/contact' }
					]
				}
			]
		}
	];

	readonly popularQuestions = [
		{
			question: 'How accurate are the AI predictions?',
			shortAnswer: 'Our AI models achieve 75-80% accuracy in predicting draft outcomes, continuously improving with more data.'
		},
		{
			question: 'Is Draftolio AI free to use?',
			shortAnswer: 'Yes! We offer a free tier with basic features. Premium features start at $9.99/month.'
		},
		{
			question: 'How do I create an account?',
			shortAnswer: 'Simply click "Login with Riot" and authenticate with your Riot Games account.'
		},
		{
			question: 'Can I use this during ranked games?',
			shortAnswer: 'Yes! Our strategic insights are designed to help you in all game modes, including ranked.'
		}
	];

	readonly totalQuestions = this.faqData.reduce((total, category) => total + category.questions.length, 0);

	readonly filteredCategories = computed(() =>
	{
		const query = this.searchQuery().toLowerCase();
		const category = this.selectedCategory();

		let categories = this.faqData;

		// Filter by category
		if (category !== 'all')
		{
			categories = categories.filter(cat => cat.id === category);
		}

		// Filter by search query
		if (query)
		{
			categories = categories.map(cat => ({
				...cat,
				questions: cat.questions.filter(faq =>
					faq.question.toLowerCase().includes(query) ||
					faq.answer.toLowerCase().includes(query) ||
					faq.tags?.some(tag => tag.toLowerCase().includes(query))
				)
			})).filter(cat => cat.questions.length > 0);
		}

		return categories;
	});

	setCategory(categoryId: string): void
	{
		this.selectedCategory.set(categoryId);
	}

	clearSearch(): void
	{
		this.searchQuery.set('');
		this.selectedCategory.set('all');
	}
}
