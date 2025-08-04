import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TimelineModule } from 'primeng/timeline';

@Component({
	selector: 'app-about',
	imports: [
		CommonModule,
		RouterLink,
		ButtonModule,
		CardModule,
		TimelineModule
	],
	template: `
    <div class="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      <div class="container mx-auto px-4 py-16">
        
        <!-- Hero Section -->
        <div class="text-center mb-16">
          <h1 class="text-5xl font-bold text-gray-900 mb-6">
            About <span class="text-blue-600">Draftolio AI</span>
          </h1>
          <p class="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
            We're revolutionizing League of Legends strategy with cutting-edge AI technology, 
            helping players make smarter decisions in champion select and team composition.
          </p>
        </div>

        <!-- Mission Section -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-12 mb-16">
          <div>
            <h2 class="text-3xl font-bold text-gray-900 mb-6">Our Mission</h2>
            <p class="text-lg text-gray-600 mb-4">
              At Draftolio AI, we believe that every League of Legends player deserves access to 
              professional-level strategic insights. Our mission is to democratize competitive 
              knowledge through advanced artificial intelligence.
            </p>
            <p class="text-lg text-gray-600 mb-6">
              We analyze millions of matches, professional gameplay patterns, and meta trends to 
              provide real-time recommendations that help players of all skill levels improve 
              their draft decisions and climb the ranked ladder.
            </p>
            <p-button 
              label="Get Started Today" 
              icon="pi pi-arrow-right"
              severity="primary"
              routerLink="/"
            ></p-button>
          </div>
          <div class="flex justify-center items-center">
            <div class="bg-white rounded-lg shadow-lg p-8 max-w-md">
              <div class="text-center">
                <i class="pi pi-chart-line text-6xl text-blue-600 mb-4"></i>
                <h3 class="text-xl font-semibold text-gray-900 mb-2">AI-Powered Analysis</h3>
                <p class="text-gray-600">
                  Our advanced algorithms process real-time data to give you the competitive edge.
                </p>
              </div>
            </div>
          </div>
        </div>

        <!-- Features Grid -->
        <div class="mb-16">
          <h2 class="text-3xl font-bold text-gray-900 text-center mb-12">What Makes Us Different</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            
            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-brain text-4xl text-purple-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Machine Learning</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Our ML models continuously learn from professional matches and adapt to meta changes, 
                ensuring you always get the most current strategic advice.
              </p>
            </p-card>

            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-clock text-4xl text-green-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Real-Time Analysis</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Get instant feedback during champion select with live win probability calculations 
                and counter-pick suggestions based on current team compositions.
              </p>
            </p-card>

            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-users text-4xl text-blue-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Team Optimization</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Analyze team synergies, identify weaknesses, and optimize role assignments 
                to maximize your team's potential before the game even starts.
              </p>
            </p-card>

            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-shield text-4xl text-red-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Privacy First</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Your data is secure with us. We only use publicly available match data and 
                never store sensitive personal information beyond what's necessary.
              </p>
            </p-card>

            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-mobile text-4xl text-yellow-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Cross-Platform</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Access Draftolio AI from any device - desktop, tablet, or mobile. 
                Our responsive design ensures a seamless experience everywhere.
              </p>
            </p-card>

            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-heart text-4xl text-pink-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Community Driven</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Built by players, for players. We actively listen to community feedback 
                and continuously improve based on what you need most.
              </p>
            </p-card>
          </div>
        </div>

        <!-- Timeline Section -->
        <div class="mb-16">
          <h2 class="text-3xl font-bold text-gray-900 text-center mb-12">Our Journey</h2>
          <div class="max-w-4xl mx-auto">
            <p-timeline [value]="timelineEvents" layout="vertical">
              <ng-template pTemplate="marker" let-event>
                <span class="flex w-8 h-8 items-center justify-center text-white rounded-full z-10 shadow-lg"
                      [style.background-color]="event.color">
                  <i [class]="event.icon"></i>
                </span>
              </ng-template>
              <ng-template pTemplate="content" let-event>
                <p-card>
                  <ng-template pTemplate="header">
                    <div class="p-4">
                      <h3 class="text-lg font-semibold text-gray-900">{{ event.title }}</h3>
                      <p class="text-sm text-gray-500">{{ event.date }}</p>
                    </div>
                  </ng-template>
                  <p class="text-gray-600">{{ event.description }}</p>
                </p-card>
              </ng-template>
            </p-timeline>
          </div>
        </div>

        <!-- Team Section -->
        <div class="mb-16">
          <h2 class="text-3xl font-bold text-gray-900 text-center mb-12">Meet the Team</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 max-w-4xl mx-auto">
            @for (member of teamMembers; track member.name) {
              <p-card class="text-center">
                <ng-template pTemplate="header">
                  <div class="p-6">
                    <img 
                      [src]="member.avatar" 
                      [alt]="member.name"
                      class="w-24 h-24 rounded-full mx-auto mb-4 object-cover"
                    >
                    <h3 class="text-lg font-semibold text-gray-900">{{ member.name }}</h3>
                    <p class="text-sm text-blue-600 font-medium">{{ member.role }}</p>
                  </div>
                </ng-template>
                <p class="text-gray-600 text-sm">{{ member.description }}</p>
              </p-card>
            }
          </div>
        </div>

        <!-- CTA Section -->
        <div class="text-center bg-white rounded-lg shadow-lg p-12">
          <h2 class="text-3xl font-bold text-gray-900 mb-4">
            Ready to Elevate Your Game?
          </h2>
          <p class="text-lg text-gray-600 mb-8 max-w-2xl mx-auto">
            Join thousands of players who are already using Draftolio AI to climb the ranked ladder 
            and improve their strategic gameplay.
          </p>
          <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <p-button 
              label="Start Your Journey" 
              icon="pi pi-play"
              severity="primary"
              size="large"
              routerLink="/"
            ></p-button>
            <p-button 
              label="Contact Us" 
              icon="pi pi-envelope"
              severity="secondary"
              [outlined]="true"
              size="large"
              routerLink="/contact"
            ></p-button>
          </div>
        </div>
      </div>
    </div>
  `,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class AboutComponent
{
	readonly timelineEvents = [
		{
			title: 'Project Inception',
			date: 'January 2024',
			description: 'Started development with the vision of bringing AI-powered strategic analysis to League of Legends players.',
			icon: 'pi pi-flag',
			color: '#3B82F6'
		},
		{
			title: 'Alpha Release',
			date: 'March 2024',
			description: 'Launched our first alpha version with basic draft analysis capabilities for a select group of beta testers.',
			icon: 'pi pi-code',
			color: '#10B981'
		},
		{
			title: 'Machine Learning Integration',
			date: 'June 2024',
			description: 'Integrated advanced ML models trained on millions of professional and ranked matches for better predictions.',
			icon: 'pi pi-brain',
			color: '#8B5CF6'
		},
		{
			title: 'Public Beta',
			date: 'September 2024',
			description: 'Opened public beta with team management features and real-time draft recommendations.',
			icon: 'pi pi-users',
			color: '#F59E0B'
		},
		{
			title: 'Full Launch',
			date: 'December 2024',
			description: 'Official launch with complete feature set including advanced analytics and mobile support.',
			icon: 'pi pi-star',
			color: '#EF4444'
		}
	];

	readonly teamMembers = [
		{
			name: 'Alex Chen',
			role: 'Lead Developer & Founder',
			description: 'Former Riot Games engineer with 8+ years of experience in game analytics and machine learning.',
			avatar: 'https://via.placeholder.com/150/3B82F6/FFFFFF?text=AC'
		},
		{
			name: 'Sarah Kim',
			role: 'AI/ML Engineer',
			description: 'PhD in Computer Science specializing in predictive modeling and data analysis for competitive gaming.',
			avatar: 'https://via.placeholder.com/150/10B981/FFFFFF?text=SK'
		},
		{
			name: 'Marcus Johnson',
			role: 'Product Designer',
			description: 'UX/UI designer focused on creating intuitive interfaces for complex data visualization and analysis.',
			avatar: 'https://via.placeholder.com/150/8B5CF6/FFFFFF?text=MJ'
		}
	];
}
