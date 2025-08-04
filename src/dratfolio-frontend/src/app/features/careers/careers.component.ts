import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ChipModule } from 'primeng/chip';
import { TagModule } from 'primeng/tag';

@Component({
	selector: 'app-careers',
	imports: [
		CommonModule,
		RouterLink,
		ButtonModule,
		CardModule,
		ChipModule,
		TagModule
	],
	template: `
    <div class="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      <div class="container mx-auto px-4 py-16">
        
        <!-- Hero Section -->
        <div class="text-center mb-16">
          <h1 class="text-5xl font-bold text-gray-900 mb-6">
            Join Our <span class="text-blue-600">Team</span>
          </h1>
          <p class="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
            Help us revolutionize League of Legends strategy with cutting-edge AI technology. 
            Build the future of competitive gaming analytics with passionate gamers and technologists.
          </p>
          <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <p-button 
              label="View Open Positions" 
              icon="pi pi-briefcase"
              severity="primary"
              size="large"
            ></p-button>
            <p-button 
              label="Learn About Our Culture" 
              icon="pi pi-heart"
              severity="secondary"
              [outlined]="true"
              size="large"
            ></p-button>
          </div>
        </div>

        <!-- Why Work With Us -->
        <div class="mb-16">
          <h2 class="text-3xl font-bold text-gray-900 text-center mb-12">Why Work at Draftolio AI?</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            
            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-rocket text-4xl text-blue-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Innovation First</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Work on cutting-edge AI and machine learning projects that directly impact millions of League of Legends players worldwide.
              </p>
            </p-card>

            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-users text-4xl text-green-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Gaming Culture</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Join a team of passionate gamers who understand the competitive scene and are dedicated to improving the player experience.
              </p>
            </p-card>

            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-chart-line text-4xl text-purple-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Growth Opportunities</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Rapid career advancement in a fast-growing startup with opportunities to lead projects and shape the product direction.
              </p>
            </p-card>

            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-home text-4xl text-orange-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Remote-First</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Work from anywhere with flexible hours and a strong remote culture that values work-life balance and productivity.
              </p>
            </p-card>

            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-dollar text-4xl text-red-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Competitive Package</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Competitive salary, equity options, comprehensive health benefits, and generous PTO policy.
              </p>
            </p-card>

            <p-card class="h-full text-center">
              <ng-template pTemplate="header">
                <div class="p-6">
                  <i class="pi pi-graduation-cap text-4xl text-pink-600 mb-4"></i>
                  <h3 class="text-xl font-semibold text-gray-900">Learning & Development</h3>
                </div>
              </ng-template>
              <p class="text-gray-600">
                Annual learning budget, conference attendance, and mentorship programs to help you grow professionally.
              </p>
            </p-card>
          </div>
        </div>

        <!-- Open Positions -->
        <div class="mb-16">
          <h2 class="text-3xl font-bold text-gray-900 text-center mb-12">Open Positions</h2>
          <div class="space-y-6 max-w-4xl mx-auto">
            @for (job of openPositions; track job.title) {
              <p-card class="hover:shadow-lg transition-shadow duration-200">
                <div class="flex flex-col md:flex-row justify-between items-start md:items-center">
                  <div class="flex-1 mb-4 md:mb-0">
                    <div class="flex items-center mb-2">
                      <h3 class="text-xl font-semibold text-gray-900 mr-3">{{ job.title }}</h3>
                      <p-tag 
                        [value]="job.type" 
                        [severity]="job.type === 'Full-time' ? 'success' : 'info'"
                      ></p-tag>
                    </div>
                    <p class="text-gray-600 mb-3">{{ job.description }}</p>
                    <div class="flex flex-wrap gap-2">
                      @for (skill of job.skills; track skill) {
                        <p-chip [label]="skill" styleClass="p-chip-outlined"></p-chip>
                      }
                    </div>
                  </div>
                  <div class="flex flex-col items-end space-y-2">
                    <div class="text-right">
                      <div class="text-lg font-semibold text-gray-900">{{ job.salary }}</div>
                      <div class="text-sm text-gray-500">{{ job.location }}</div>
                    </div>
                    <p-button 
                      label="Apply Now" 
                      icon="pi pi-send"
                      severity="primary"
                      size="small"
                    ></p-button>
                  </div>
                </div>
              </p-card>
            }
          </div>
        </div>

        <!-- Company Values -->
        <div class="mb-16">
          <h2 class="text-3xl font-bold text-gray-900 text-center mb-12">Our Values</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-4xl mx-auto">
            @for (value of companyValues; track value.title) {
              <div class="flex items-start space-x-4">
                <div class="flex-shrink-0">
                  <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                    <i [class]="value.icon + ' text-blue-600 text-xl'"></i>
                  </div>
                </div>
                <div>
                  <h3 class="text-lg font-semibold text-gray-900 mb-2">{{ value.title }}</h3>
                  <p class="text-gray-600">{{ value.description }}</p>
                </div>
              </div>
            }
          </div>
        </div>

        <!-- Application Process -->
        <div class="mb-16">
          <h2 class="text-3xl font-bold text-gray-900 text-center mb-12">Our Hiring Process</h2>
          <div class="max-w-4xl mx-auto">
            <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
              @for (step of hiringProcess; track step.step) {
                <div class="text-center">
                  <div class="w-16 h-16 bg-blue-600 text-white rounded-full flex items-center justify-center mx-auto mb-4 text-xl font-bold">
                    {{ step.step }}
                  </div>
                  <h3 class="text-lg font-semibold text-gray-900 mb-2">{{ step.title }}</h3>
                  <p class="text-gray-600 text-sm">{{ step.description }}</p>
                </div>
              }
            </div>
          </div>
        </div>

        <!-- CTA Section -->
        <div class="text-center bg-white rounded-lg shadow-lg p-12">
          <h2 class="text-3xl font-bold text-gray-900 mb-4">
            Ready to Shape the Future of Gaming?
          </h2>
          <p class="text-lg text-gray-600 mb-8 max-w-2xl mx-auto">
            Don't see a position that fits? We're always looking for talented individuals who share our passion for gaming and technology.
          </p>
          <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <p-button 
              label="Send Us Your Resume" 
              icon="pi pi-upload"
              severity="primary"
              size="large"
              routerLink="/contact"
            ></p-button>
            <p-button 
              label="Follow Us on LinkedIn" 
              icon="pi pi-linkedin"
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
export class CareersComponent
{
	readonly openPositions = [
		{
			title: 'Senior AI/ML Engineer',
			type: 'Full-time',
			description: 'Lead the development of our machine learning models for draft prediction and player analysis.',
			skills: [ 'Python', 'TensorFlow', 'PyTorch', 'AWS', 'Docker' ],
			salary: '$120k - $160k',
			location: 'Remote'
		},
		{
			title: 'Frontend Developer',
			type: 'Full-time',
			description: 'Build beautiful and responsive user interfaces using Angular and modern web technologies.',
			skills: [ 'Angular', 'TypeScript', 'TailwindCSS', 'RxJS', 'Jest' ],
			salary: '$90k - $130k',
			location: 'Remote'
		},
		{
			title: 'Backend Engineer',
			type: 'Full-time',
			description: 'Design and implement scalable APIs and microservices for our gaming analytics platform.',
			skills: [ 'Java', 'Spring Boot', 'PostgreSQL', 'Redis', 'Kubernetes' ],
			salary: '$100k - $140k',
			location: 'Remote'
		},
		{
			title: 'Data Scientist',
			type: 'Full-time',
			description: 'Analyze gaming data to extract insights and improve our AI recommendation algorithms.',
			skills: [ 'Python', 'R', 'SQL', 'Pandas', 'Jupyter' ],
			salary: '$110k - $150k',
			location: 'Remote'
		},
		{
			title: 'DevOps Engineer',
			type: 'Contract',
			description: 'Manage our cloud infrastructure and implement CI/CD pipelines for rapid deployment.',
			skills: [ 'AWS', 'Terraform', 'Jenkins', 'Docker', 'Monitoring' ],
			salary: '$80k - $120k',
			location: 'Remote'
		}
	];

	readonly companyValues = [
		{
			title: 'Player-Centric',
			description: 'Every decision we make is focused on improving the player experience and helping gamers achieve their goals.',
			icon: 'pi pi-heart'
		},
		{
			title: 'Innovation',
			description: 'We embrace new technologies and creative solutions to solve complex problems in the gaming industry.',
			icon: 'pi pi-lightbulb'
		},
		{
			title: 'Transparency',
			description: 'We believe in open communication, honest feedback, and sharing knowledge across the team.',
			icon: 'pi pi-eye'
		},
		{
			title: 'Excellence',
			description: 'We strive for the highest quality in everything we do, from code to customer support.',
			icon: 'pi pi-star'
		},
		{
			title: 'Collaboration',
			description: 'We work together as a team, supporting each other and celebrating collective achievements.',
			icon: 'pi pi-users'
		},
		{
			title: 'Growth Mindset',
			description: 'We embrace challenges, learn from failures, and continuously improve our skills and processes.',
			icon: 'pi pi-chart-line'
		}
	];

	readonly hiringProcess = [
		{
			step: 1,
			title: 'Application',
			description: 'Submit your resume and cover letter through our application portal.'
		},
		{
			step: 2,
			title: 'Phone Screen',
			description: 'Initial conversation with our recruiting team to discuss your background and interests.'
		},
		{
			step: 3,
			title: 'Technical Interview',
			description: 'Technical assessment and coding challenge relevant to the position.'
		},
		{
			step: 4,
			title: 'Final Interview',
			description: 'Meet with team members and leadership to discuss culture fit and role expectations.'
		}
	];
}
