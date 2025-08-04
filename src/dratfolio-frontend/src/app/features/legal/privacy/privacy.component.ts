import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CardModule } from 'primeng/card';
import { AccordionModule } from 'primeng/accordion';

@Component({
	selector: 'app-privacy',
	imports: [
		CommonModule,
		RouterLink,
		CardModule,
		AccordionModule
	],
	template: `
		<div class="min-h-screen bg-gray-50">
			<div class="container mx-auto px-4 py-16 max-w-4xl">

				<!-- Header -->
				<div class="text-center mb-12">
					<h1 class="text-4xl font-bold text-gray-900 mb-4">Privacy Policy</h1>
					<p class="text-lg text-gray-600 mb-2">
						Last updated: January 1, 2024
					</p>
					<p class="text-gray-600">
						This Privacy Policy describes how Draftolio AI collects, uses, and protects your information.
					</p>
				</div>

				<!-- Quick Summary -->
				<p-card class="mb-8">
					<ng-template pTemplate="header">
						<div class="p-6">
							<h2 class="text-2xl font-bold text-gray-900 mb-2">Privacy at a Glance</h2>
							<p class="text-gray-600">Key points about how we handle your data</p>
						</div>
					</ng-template>

					<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
						<div class="flex items-start space-x-3">
							<i class="pi pi-shield text-green-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">Data Protection</h3>
								<p class="text-sm text-gray-600">We use industry-standard encryption and security measures to protect your data.</p>
							</div>
						</div>

						<div class="flex items-start space-x-3">
							<i class="pi pi-eye-slash text-blue-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">No Selling</h3>
								<p class="text-sm text-gray-600">We never sell your personal information to third parties.</p>
							</div>
						</div>

						<div class="flex items-start space-x-3">
							<i class="pi pi-user text-purple-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">Your Control</h3>
								<p class="text-sm text-gray-600">You can access, modify, or delete your data at any time.</p>
							</div>
						</div>

						<div class="flex items-start space-x-3">
							<i class="pi pi-globe text-orange-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">Public Data Only</h3>
								<p class="text-sm text-gray-600">We only use publicly available League of Legends match data.</p>
							</div>
						</div>
					</div>
				</p-card>

				<!-- Detailed Privacy Policy -->
				<p-card>
					<ng-template pTemplate="header">
						<div class="p-6">
							<h2 class="text-2xl font-bold text-gray-900">Detailed Privacy Policy</h2>
						</div>
					</ng-template>

					<p-accordion>
						<p-accordion-panel>
							<p-accordion-header>1. Information We Collect</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Account Information</h4>
										<p class="text-gray-600 mb-2">When you create an account, we collect:</p>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Riot Games account information (Riot ID, summoner name)</li>
											<li>Email address (if provided)</li>
											<li>Profile preferences and settings</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Game Data</h4>
										<p class="text-gray-600 mb-2">We collect publicly available League of Legends data:</p>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Match history and game statistics</li>
											<li>Champion performance data</li>
											<li>Rank and league information</li>
											<li>Team composition and draft data</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Usage Information</h4>
										<p class="text-gray-600 mb-2">We automatically collect:</p>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>How you use our service and features</li>
											<li>Device and browser information</li>
											<li>IP address and location data</li>
											<li>Cookies and similar tracking technologies</li>
										</ul>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>2. How We Use Your Information</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Service Provision</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Provide AI-powered draft analysis and recommendations</li>
											<li>Track your performance and improvement over time</li>
											<li>Customize your experience based on your preferences</li>
											<li>Enable team management and collaboration features</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Service Improvement</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Improve our AI models and prediction accuracy</li>
											<li>Develop new features and functionality</li>
											<li>Analyze usage patterns to optimize performance</li>
											<li>Conduct research and analytics</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Communication</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Send important service updates and notifications</li>
											<li>Respond to your questions and support requests</li>
											<li>Send marketing communications (with your consent)</li>
										</ul>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>3. Information Sharing</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										We do not sell, trade, or otherwise transfer your personal information to third parties, except in the following
										circumstances:
									</p>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Service Providers</h4>
										<p class="text-gray-600">
											We may share information with trusted third-party service providers who assist us in operating our service, such as:
										</p>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Cloud hosting and storage providers</li>
											<li>Analytics and monitoring services</li>
											<li>Customer support platforms</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Legal Requirements</h4>
										<p class="text-gray-600">
											We may disclose information when required by law or to protect our rights, property, or safety.
										</p>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Aggregated Data</h4>
										<p class="text-gray-600">
											We may share aggregated, non-personally identifiable information for research, analytics, or marketing purposes.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>4. Data Security</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										We implement appropriate technical and organizational measures to protect your personal information:
									</p>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Technical Safeguards</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Encryption of data in transit and at rest</li>
											<li>Secure server infrastructure and access controls</li>
											<li>Regular security audits and vulnerability assessments</li>
											<li>Multi-factor authentication for administrative access</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Organizational Measures</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Employee training on data protection practices</li>
											<li>Limited access to personal information on a need-to-know basis</li>
											<li>Regular review and update of security policies</li>
											<li>Incident response procedures</li>
										</ul>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>5. Your Rights and Choices</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										You have the following rights regarding your personal information:
									</p>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Access and Portability</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Request a copy of your personal information</li>
											<li>Export your data in a machine-readable format</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Correction and Deletion</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Correct inaccurate or incomplete information</li>
											<li>Request deletion of your personal information</li>
											<li>Deactivate your account at any time</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Communication Preferences</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Opt out of marketing communications</li>
											<li>Manage notification preferences</li>
											<li>Control cookie settings</li>
										</ul>
									</div>

									<p class="text-gray-600">
										To exercise these rights, please contact us at
										<a routerLink="/contact"
										   class="text-blue-600 hover:text-blue-800 underline">our contact page</a>
										or email privacy{{ '@' }}draftolioai.com.
									</p>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>6. Cookies and Tracking</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										We use cookies and similar technologies to enhance your experience:
									</p>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Essential Cookies</h4>
										<p class="text-gray-600">
											Required for the service to function properly, including authentication and security features.
										</p>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Analytics Cookies</h4>
										<p class="text-gray-600">
											Help us understand how you use our service to improve performance and user experience.
										</p>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Preference Cookies</h4>
										<p class="text-gray-600">
											Remember your settings and preferences for a personalized experience.
										</p>
									</div>

									<p class="text-gray-600">
										You can manage your cookie preferences through your browser settings or our
										<a routerLink="/cookies"
										   class="text-blue-600 hover:text-blue-800 underline">Cookie Policy</a>.
									</p>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>7. Changes to This Policy</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										We may update this Privacy Policy from time to time. When we make changes:
									</p>

									<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
										<li>We will update the "Last updated" date at the top of this policy</li>
										<li>We will notify you of significant changes via email or in-app notification</li>
										<li>We will provide a summary of changes for your review</li>
										<li>Your continued use of the service constitutes acceptance of the updated policy</li>
									</ul>

									<p class="text-gray-600 mt-4">
										We encourage you to review this Privacy Policy periodically to stay informed about how we protect your information.
									</p>
								</div>
							</p-accordion-content>
						</p-accordion-panel>
					</p-accordion>
				</p-card>

				<!-- Contact Information -->
				<div class="mt-12 text-center">
					<h3 class="text-xl font-semibold text-gray-900 mb-4">Questions About This Policy?</h3>
					<p class="text-gray-600 mb-6">
						If you have any questions about this Privacy Policy or our data practices, please don't hesitate to contact us.
					</p>
					<div class="flex flex-col sm:flex-row gap-4 justify-center">
						<a
							routerLink="/contact"
							class="inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 transition-colors duration-200"
						>
							<i class="pi pi-envelope mr-2"></i>
							Contact Us
						</a>
						<a
							href="mailto:privacy{{ '@' }}draftolioai.com"
							class="inline-flex items-center px-6 py-3 border border-gray-300 text-base font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 transition-colors duration-200"
						>
							<i class="pi pi-at mr-2"></i>
							Email Privacy Team
						</a>
					</div>
				</div>
			</div>
		</div>
	`,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class PrivacyComponent {}
