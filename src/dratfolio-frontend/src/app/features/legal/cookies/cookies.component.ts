import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CardModule } from 'primeng/card';
import { AccordionModule } from 'primeng/accordion';
import { ButtonModule } from 'primeng/button';

@Component({
	selector: 'app-cookies',
	imports: [
		CommonModule,
		RouterLink,
		CardModule,
		AccordionModule,
		ButtonModule
	],
	template: `
		<div class="min-h-screen bg-gray-50">
			<div class="container mx-auto px-4 py-16 max-w-4xl">

				<!-- Header -->
				<div class="text-center mb-12">
					<h1 class="text-4xl font-bold text-gray-900 mb-4">Cookie Policy</h1>
					<p class="text-lg text-gray-600 mb-2">
						Last updated: January 1, 2024
					</p>
					<p class="text-gray-600">
						This Cookie Policy explains how Draftolio AI uses cookies and similar technologies.
					</p>
				</div>

				<!-- Quick Summary -->
				<p-card class="mb-8">
					<ng-template pTemplate="header">
						<div class="p-6">
							<h2 class="text-2xl font-bold text-gray-900 mb-2">Cookie Summary</h2>
							<p class="text-gray-600">What you need to know about our cookie usage</p>
						</div>
					</ng-template>

					<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
						<div class="flex items-start space-x-3">
							<i class="pi pi-check-circle text-green-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">Essential Only</h3>
								<p class="text-sm text-gray-600">We only use cookies that are necessary for the website to function properly.</p>
							</div>
						</div>

						<div class="flex items-start space-x-3">
							<i class="pi pi-cog text-blue-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">Your Control</h3>
								<p class="text-sm text-gray-600">You can manage your cookie preferences through your browser settings.</p>
							</div>
						</div>

						<div class="flex items-start space-x-3">
							<i class="pi pi-shield text-purple-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">Privacy First</h3>
								<p class="text-sm text-gray-600">We don't use tracking cookies for advertising or sell your data to third parties.</p>
							</div>
						</div>

						<div class="flex items-start space-x-3">
							<i class="pi pi-info-circle text-orange-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">Transparent</h3>
								<p class="text-sm text-gray-600">We clearly explain what cookies we use and why we need them.</p>
							</div>
						</div>
					</div>
				</p-card>

				<!-- Detailed Cookie Policy -->
				<p-card>
					<ng-template pTemplate="header">
						<div class="p-6">
							<h2 class="text-2xl font-bold text-gray-900">Detailed Cookie Policy</h2>
						</div>
					</ng-template>

					<p-accordion>
						<p-accordion-panel>
							<p-accordion-header>What Are Cookies?</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										Cookies are small text files that are stored on your device (computer, tablet, or mobile) when you visit a website.
										They help websites remember information about your visit, which can make your next visit easier and the site more useful
										to you.
									</p>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Types of Cookies</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li><strong>Session Cookies:</strong> Temporary cookies that are deleted when you close your browser</li>
											<li><strong>Persistent Cookies:</strong> Cookies that remain on your device for a set period or until you delete
												them
											</li>
											<li><strong>First-Party Cookies:</strong> Cookies set by the website you're visiting</li>
											<li><strong>Third-Party Cookies:</strong> Cookies set by external services used on the website</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Similar Technologies</h4>
										<p class="text-gray-600">
											We may also use similar technologies such as web beacons, pixels, and local storage.
											These technologies serve similar purposes to cookies and are covered by this policy.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>Cookies We Use</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-6">
									<div>
										<h4 class="font-semibold text-gray-900 mb-3">Essential Cookies</h4>
										<p class="text-gray-600 mb-3">
											These cookies are necessary for the website to function and cannot be switched off in our systems.
											They are usually only set in response to actions made by you.
										</p>

										<div class="bg-gray-50 p-4 rounded-lg">
											<div class="space-y-3">
												<div class="flex justify-between items-start">
													<div>
														<div class="font-medium text-gray-900">Authentication Cookies</div>
														<div class="text-sm text-gray-600">Remember your login status and session information</div>
													</div>
													<span class="text-xs bg-red-100 text-red-800 px-2 py-1 rounded">Required</span>
												</div>

												<div class="flex justify-between items-start">
													<div>
														<div class="font-medium text-gray-900">Security Cookies</div>
														<div class="text-sm text-gray-600">Protect against cross-site request forgery and other security
															threats
														</div>
													</div>
													<span class="text-xs bg-red-100 text-red-800 px-2 py-1 rounded">Required</span>
												</div>

												<div class="flex justify-between items-start">
													<div>
														<div class="font-medium text-gray-900">Preference Cookies</div>
														<div class="text-sm text-gray-600">Remember your settings and preferences</div>
													</div>
													<span class="text-xs bg-red-100 text-red-800 px-2 py-1 rounded">Required</span>
												</div>
											</div>
										</div>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-3">Analytics Cookies</h4>
										<p class="text-gray-600 mb-3">
											These cookies help us understand how visitors interact with our website by collecting and reporting information
											anonymously.
										</p>

										<div class="bg-blue-50 p-4 rounded-lg">
											<div class="space-y-3">
												<div class="flex justify-between items-start">
													<div>
														<div class="font-medium text-gray-900">Usage Analytics</div>
														<div class="text-sm text-gray-600">Track page views, user interactions, and site performance</div>
													</div>
													<span class="text-xs bg-blue-100 text-blue-800 px-2 py-1 rounded">Optional</span>
												</div>

												<div class="flex justify-between items-start">
													<div>
														<div class="font-medium text-gray-900">Error Tracking</div>
														<div class="text-sm text-gray-600">Help us identify and fix technical issues</div>
													</div>
													<span class="text-xs bg-blue-100 text-blue-800 px-2 py-1 rounded">Optional</span>
												</div>
											</div>
										</div>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-3">Functional Cookies</h4>
										<p class="text-gray-600 mb-3">
											These cookies enable enhanced functionality and personalization, such as remembering your preferences.
										</p>

										<div class="bg-green-50 p-4 rounded-lg">
											<div class="space-y-3">
												<div class="flex justify-between items-start">
													<div>
														<div class="font-medium text-gray-900">Theme Preferences</div>
														<div class="text-sm text-gray-600">Remember your chosen theme and display preferences</div>
													</div>
													<span class="text-xs bg-green-100 text-green-800 px-2 py-1 rounded">Optional</span>
												</div>

												<div class="flex justify-between items-start">
													<div>
														<div class="font-medium text-gray-900">Language Settings</div>
														<div class="text-sm text-gray-600">Remember your preferred language and region</div>
													</div>
													<span class="text-xs bg-green-100 text-green-800 px-2 py-1 rounded">Optional</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>Third-Party Services</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										We use certain third-party services that may set their own cookies. We have no control over these cookies,
										but we've chosen services that respect user privacy.
									</p>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Riot Games API</h4>
										<p class="text-gray-600 mb-2">
											We integrate with Riot Games' services to provide League of Legends data and authentication.
											Riot Games may set cookies according to their own privacy policy.
										</p>
										<a href="https://www.riotgames.com/en/privacy-notice"
										   target="_blank"
										   class="text-blue-600 hover:text-blue-800 underline text-sm">
											View Riot Games Privacy Policy
										</a>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Content Delivery Network (CDN)</h4>
										<p class="text-gray-600">
											We use CDN services to deliver content faster and more reliably. These services may set performance-related cookies.
										</p>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Analytics Services</h4>
										<p class="text-gray-600">
											We may use privacy-focused analytics services to understand how our website is used.
											These services are configured to respect user privacy and don't track individuals.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>Managing Your Cookie Preferences</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Browser Settings</h4>
										<p class="text-gray-600 mb-3">
											You can control and manage cookies through your browser settings. Here's how to do it in popular browsers:
										</p>

										<div class="space-y-2 text-gray-600">
											<div><strong>Chrome:</strong> Settings → Privacy and security → Cookies and other site data</div>
											<div><strong>Firefox:</strong> Settings → Privacy & Security → Cookies and Site Data</div>
											<div><strong>Safari:</strong> Preferences → Privacy → Manage Website Data</div>
											<div><strong>Edge:</strong> Settings → Cookies and site permissions → Cookies and site data</div>
										</div>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Cookie Preferences</h4>
										<p class="text-gray-600 mb-3">
											You can manage your cookie preferences for this website:
										</p>

										<div class="bg-gray-50 p-4 rounded-lg">
											<div class="space-y-3">
												<div class="flex justify-between items-center">
													<div>
														<div class="font-medium text-gray-900">Essential Cookies</div>
														<div class="text-sm text-gray-600">Required for basic website functionality</div>
													</div>
													<span class="text-sm text-gray-500">Always Active</span>
												</div>

												<div class="flex justify-between items-center">
													<div>
														<div class="font-medium text-gray-900">Analytics Cookies</div>
														<div class="text-sm text-gray-600">Help us improve the website</div>
													</div>
													<label class="relative inline-flex items-center cursor-pointer">
														<input type="checkbox"
														       class="sr-only peer"
														       checked>
														<div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
													</label>
												</div>

												<div class="flex justify-between items-center">
													<div>
														<div class="font-medium text-gray-900">Functional Cookies</div>
														<div class="text-sm text-gray-600">Remember your preferences</div>
													</div>
													<label class="relative inline-flex items-center cursor-pointer">
														<input type="checkbox"
														       class="sr-only peer"
														       checked>
														<div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
													</label>
												</div>
											</div>

											<div class="mt-4 pt-4 border-t border-gray-200">
												<p-button
													label="Save Preferences"
													severity="primary"
													size="small"
												></p-button>
											</div>
										</div>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Impact of Disabling Cookies</h4>
										<p class="text-gray-600 mb-2">
											If you disable cookies, some features of our website may not work properly:
										</p>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>You may need to log in repeatedly</li>
											<li>Your preferences and settings won't be remembered</li>
											<li>Some personalized features may not be available</li>
											<li>We won't be able to improve the website based on usage data</li>
										</ul>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>Updates to This Policy</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										We may update this Cookie Policy from time to time to reflect changes in our practices or for other operational,
										legal, or regulatory reasons.
									</p>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">How We Notify You</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>We will update the "Last updated" date at the top of this policy</li>
											<li>For significant changes, we will notify you via email or website banner</li>
											<li>We encourage you to review this policy periodically</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Your Continued Use</h4>
										<p class="text-gray-600">
											Your continued use of our website after any changes to this Cookie Policy will constitute your acceptance of such
											changes.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>
					</p-accordion>
				</p-card>

				<!-- Contact Information -->
				<div class="mt-12 text-center">
					<h3 class="text-xl font-semibold text-gray-900 mb-4">Questions About Cookies?</h3>
					<p class="text-gray-600 mb-6">
						If you have any questions about our use of cookies or this Cookie Policy, please contact us.
					</p>
					<div class="flex flex-col sm:flex-row gap-4 justify-center">
						<p-button
							label="Contact Us"
							icon="pi pi-envelope"
							severity="primary"
							routerLink="/contact"
						></p-button>
						<p-button
							label="Privacy Policy"
							icon="pi pi-shield"
							severity="secondary"
							[outlined]="true"
							routerLink="/privacy"
						></p-button>
					</div>
				</div>
			</div>
		</div>
	`,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class CookiesComponent {}
