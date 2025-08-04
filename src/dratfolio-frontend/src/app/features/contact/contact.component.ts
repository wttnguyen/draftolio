import { Component, ChangeDetectionStrategy, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { SelectModule } from 'primeng/select';
import { MessageModule } from 'primeng/message';

@Component({
	selector: 'app-contact',
	imports: [
		CommonModule,
		FormsModule,
		ButtonModule,
		CardModule,
		InputTextModule,
		TextareaModule,
		SelectModule,
		MessageModule
	],
	template: `
		<div class="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
			<div class="container mx-auto px-4 py-16">

				<!-- Hero Section -->
				<div class="text-center mb-16">
					<h1 class="text-5xl font-bold text-gray-900 mb-6">
						Contact <span class="text-blue-600">Us</span>
					</h1>
					<p class="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
						Have questions, feedback, or need support? We'd love to hear from you.
						Our team is here to help you get the most out of Draftolio AI.
					</p>
				</div>

				<div class="grid grid-cols-1 lg:grid-cols-2 gap-12 max-w-6xl mx-auto">

					<!-- Contact Form -->
					<div>
						<p-card>
							<ng-template pTemplate="header">
								<div class="p-6">
									<h2 class="text-2xl font-bold text-gray-900 mb-2">Send us a Message</h2>
									<p class="text-gray-600">Fill out the form below and we'll get back to you within 24 hours.</p>
								</div>
							</ng-template>

							@if (showSuccessMessage()) {
								<p-message
									severity="success"
									text="Thank you for your message! We'll get back to you soon."
									class="mb-4"
								></p-message>
							}

							<form (ngSubmit)="onSubmit()"
							      class="space-y-6">
								<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
									<div>
										<label for="firstName"
										       class="block text-sm font-medium text-gray-700 mb-2">
											First Name *
										</label>
										<input
											pInputText
											id="firstName"
											[(ngModel)]="contactForm.firstName"
											name="firstName"
											required
											class="w-full"
											placeholder="Enter your first name"
										/>
									</div>
									<div>
										<label for="lastName"
										       class="block text-sm font-medium text-gray-700 mb-2">
											Last Name *
										</label>
										<input
											pInputText
											id="lastName"
											[(ngModel)]="contactForm.lastName"
											name="lastName"
											required
											class="w-full"
											placeholder="Enter your last name"
										/>
									</div>
								</div>

								<div>
									<label for="email"
									       class="block text-sm font-medium text-gray-700 mb-2">
										Email Address *
									</label>
									<input
										pInputText
										id="email"
										type="email"
										[(ngModel)]="contactForm.email"
										name="email"
										required
										class="w-full"
										placeholder="Enter your email address"
									/>
								</div>

								<div>
									<label for="riotTag"
									       class="block text-sm font-medium text-gray-700 mb-2">
										Riot Tag (Optional)
									</label>
									<input
										pInputText
										id="riotTag"
										[(ngModel)]="contactForm.riotTag"
										name="riotTag"
										class="w-full"
										placeholder="e.g., Player#TAG"
									/>
								</div>

								<div>
									<label for="subject"
									       class="block text-sm font-medium text-gray-700 mb-2">
										Subject *
									</label>
									<p-select
										[options]="subjectOptions"
										[(ngModel)]="contactForm.subject"
										name="subject"
										placeholder="Select a subject"
										optionLabel="label"
										optionValue="value"
										class="w-full"
									></p-select>
								</div>

								<div>
									<label for="message"
									       class="block text-sm font-medium text-gray-700 mb-2">
										Message *
									</label>
									<textarea
										pTextarea
										id="message"
										[(ngModel)]="contactForm.message"
										name="message"
										required
										rows="6"
										class="w-full"
										placeholder="Tell us how we can help you..."
									></textarea>
								</div>

								<div class="flex items-center">
									<input
										type="checkbox"
										id="newsletter"
										[(ngModel)]="contactForm.newsletter"
										name="newsletter"
										class="mr-2"
									/>
									<label for="newsletter"
									       class="text-sm text-gray-600">
										I'd like to receive updates about new features and improvements
									</label>
								</div>

								<p-button
									type="submit"
									label="Send Message"
									icon="pi pi-send"
									severity="primary"
									size="large"
									class="w-full"
									[loading]="isSubmitting()"
								></p-button>
							</form>
						</p-card>
					</div>

					<!-- Contact Information -->
					<div class="space-y-8">

						<!-- Contact Methods -->
						<p-card>
							<ng-template pTemplate="header">
								<div class="p-6">
									<h2 class="text-2xl font-bold text-gray-900 mb-2">Get in Touch</h2>
									<p class="text-gray-600">Choose the best way to reach us</p>
								</div>
							</ng-template>

							<div class="space-y-6">
								<div class="flex items-start space-x-4">
									<div class="flex-shrink-0">
										<div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
											<i class="pi pi-envelope text-blue-600 text-xl"></i>
										</div>
									</div>
									<div>
										<h3 class="text-lg font-semibold text-gray-900 mb-1">Email Support</h3>
										<p class="text-gray-600 mb-2">For general inquiries and support</p>
										<a href="mailto:support{{ '@' }}draftolioai.com"
										   class="text-blue-600 hover:text-blue-800 font-medium">
											support{{ '@' }}draftolioai.com
										</a>
									</div>
								</div>

								<div class="flex items-start space-x-4">
									<div class="flex-shrink-0">
										<div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
											<i class="pi pi-discord text-purple-600 text-xl"></i>
										</div>
									</div>
									<div>
										<h3 class="text-lg font-semibold text-gray-900 mb-1">Discord Community</h3>
										<p class="text-gray-600 mb-2">Join our community for real-time help</p>
										<a href="#"
										   class="text-purple-600 hover:text-purple-800 font-medium">
											Join Discord Server
										</a>
									</div>
								</div>

								<div class="flex items-start space-x-4">
									<div class="flex-shrink-0">
										<div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
											<i class="pi pi-question-circle text-green-600 text-xl"></i>
										</div>
									</div>
									<div>
										<h3 class="text-lg font-semibold text-gray-900 mb-1">Help Center</h3>
										<p class="text-gray-600 mb-2">Browse our knowledge base</p>
										<a href="/help"
										   class="text-green-600 hover:text-green-800 font-medium">
											Visit Help Center
										</a>
									</div>
								</div>
							</div>
						</p-card>

						<!-- FAQ Section -->
						<p-card>
							<ng-template pTemplate="header">
								<div class="p-6">
									<h2 class="text-2xl font-bold text-gray-900 mb-2">Quick Answers</h2>
									<p class="text-gray-600">Common questions we receive</p>
								</div>
							</ng-template>

							<div class="space-y-4">
								@for (faq of frequentQuestions; track faq.question) {
									<div class="border-b border-gray-200 pb-4 last:border-b-0 last:pb-0">
										<h4 class="font-semibold text-gray-900 mb-2">{{ faq.question }}</h4>
										<p class="text-gray-600 text-sm">{{ faq.answer }}</p>
									</div>
								}
							</div>
						</p-card>

						<!-- Response Time -->
						<p-card>
							<ng-template pTemplate="header">
								<div class="p-6">
									<h2 class="text-2xl font-bold text-gray-900 mb-2">Response Times</h2>
									<p class="text-gray-600">When you can expect to hear from us</p>
								</div>
							</ng-template>

							<div class="space-y-3">
								<div class="flex justify-between items-center">
									<span class="text-gray-700">General Inquiries</span>
									<span class="text-blue-600 font-semibold">24 hours</span>
								</div>
								<div class="flex justify-between items-center">
									<span class="text-gray-700">Technical Support</span>
									<span class="text-green-600 font-semibold">12 hours</span>
								</div>
								<div class="flex justify-between items-center">
									<span class="text-gray-700">Bug Reports</span>
									<span class="text-orange-600 font-semibold">6 hours</span>
								</div>
								<div class="flex justify-between items-center">
									<span class="text-gray-700">Critical Issues</span>
									<span class="text-red-600 font-semibold">2 hours</span>
								</div>
							</div>
						</p-card>
					</div>
				</div>
			</div>
		</div>
	`,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class ContactComponent
{
	readonly showSuccessMessage = signal(false);
	readonly isSubmitting = signal(false);

	readonly contactForm = {
		firstName: '',
		lastName: '',
		email: '',
		riotTag: '',
		subject: '',
		message: '',
		newsletter: false
	};

	readonly subjectOptions = [
		{ label: 'General Question', value: 'general' },
		{ label: 'Technical Support', value: 'support' },
		{ label: 'Bug Report', value: 'bug' },
		{ label: 'Feature Request', value: 'feature' },
		{ label: 'Account Issues', value: 'account' },
		{ label: 'Partnership Inquiry', value: 'partnership' },
		{ label: 'Press & Media', value: 'media' },
		{ label: 'Other', value: 'other' }
	];

	readonly frequentQuestions = [
		{
			question: 'How accurate are the AI predictions?',
			answer: 'Our AI models achieve 75-80% accuracy in predicting draft outcomes, continuously improving with more data.'
		},
		{
			question: 'Is Draftolio AI free to use?',
			answer: 'We offer a free tier with basic features. Premium features require a subscription starting at $9.99/month.'
		},
		{
			question: 'Which regions are supported?',
			answer: 'We currently support all major League of Legends regions including NA, EUW, EUNE, KR, CN, and more.'
		},
		{
			question: 'How often is the data updated?',
			answer: 'Our database is updated in real-time with new match data and patch changes within hours of release.'
		}
	];

	onSubmit(): void
	{
		this.isSubmitting.set(true);

		// Simulate form submission
		setTimeout(() =>
		{
			this.isSubmitting.set(false);
			this.showSuccessMessage.set(true);

			// Reset form
			Object.keys(this.contactForm).forEach(key =>
			{
				if (key === 'newsletter')
				{
					(this.contactForm as any)[key] = false;
				}
				else
				{
					(this.contactForm as any)[key] = '';
				}
			});

			// Hide success message after 5 seconds
			setTimeout(() =>
			{
				this.showSuccessMessage.set(false);
			}, 5000);
		}, 2000);
	}
}
