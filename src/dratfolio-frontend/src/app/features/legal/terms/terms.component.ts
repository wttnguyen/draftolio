import { ChangeDetectionStrategy, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CardModule } from 'primeng/card';
import { AccordionModule } from 'primeng/accordion';

@Component({
	selector: 'app-terms',
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
					<h1 class="text-4xl font-bold text-gray-900 mb-4">Terms of Service</h1>
					<p class="text-lg text-gray-600 mb-2">
						Last updated: January 1, 2024
					</p>
					<p class="text-gray-600">
						These Terms of Service govern your use of Draftolio AI and our services.
					</p>
				</div>

				<!-- Quick Summary -->
				<p-card class="mb-8">
					<ng-template pTemplate="header">
						<div class="p-6">
							<h2 class="text-2xl font-bold text-gray-900 mb-2">Terms Summary</h2>
							<p class="text-gray-600">Key points about using our service</p>
						</div>
					</ng-template>

					<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
						<div class="flex items-start space-x-3">
							<i class="pi pi-check-circle text-green-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">Free to Use</h3>
								<p class="text-sm text-gray-600">Basic features are available at no cost with optional premium upgrades.</p>
							</div>
						</div>

						<div class="flex items-start space-x-3">
							<i class="pi pi-shield text-blue-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">Fair Use</h3>
								<p class="text-sm text-gray-600">Use our service responsibly and in accordance with our guidelines.</p>
							</div>
						</div>

						<div class="flex items-start space-x-3">
							<i class="pi pi-ban text-red-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">No Cheating</h3>
								<p class="text-sm text-gray-600">Our service provides strategic insights, not game automation or cheating tools.</p>
							</div>
						</div>

						<div class="flex items-start space-x-3">
							<i class="pi pi-exclamation-triangle text-yellow-600 text-xl mt-1"></i>
							<div>
								<h3 class="font-semibold text-gray-900 mb-1">No Guarantees</h3>
								<p class="text-sm text-gray-600">We provide analysis and recommendations but cannot guarantee game outcomes.</p>
							</div>
						</div>
					</div>
				</p-card>

				<!-- Detailed Terms -->
				<p-card>
					<ng-template pTemplate="header">
						<div class="p-6">
							<h2 class="text-2xl font-bold text-gray-900">Detailed Terms of Service</h2>
						</div>
					</ng-template>

					<p-accordion>
						<p-accordion-panel>
							<p-accordion-header>1. Acceptance of Terms</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										By accessing or using Draftolio AI ("the Service"), you agree to be bound by these Terms of Service ("Terms").
										If you do not agree to these Terms, you may not use the Service.
									</p>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Eligibility</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>You must be at least 13 years old to use the Service</li>
											<li>If you are under 18, you must have parental consent</li>
											<li>You must have a valid League of Legends account</li>
											<li>You must comply with all applicable laws and regulations</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Changes to Terms</h4>
										<p class="text-gray-600">
											We reserve the right to modify these Terms at any time. We will notify you of significant changes
											via email or through the Service. Your continued use of the Service after changes constitutes acceptance
											of the new Terms.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>2. Description of Service</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										Draftolio AI is a strategic analysis platform for League of Legends that provides:
									</p>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Core Features</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>AI-powered draft analysis and recommendations</li>
											<li>Team composition optimization tools</li>
											<li>Performance tracking and statistics</li>
											<li>Champion and meta analysis</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Service Availability</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>We strive for 99.9% uptime but cannot guarantee uninterrupted service</li>
											<li>Scheduled maintenance will be announced in advance when possible</li>
											<li>Emergency maintenance may occur without prior notice</li>
											<li>Service availability may vary by region</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Third-Party Dependencies</h4>
										<p class="text-gray-600">
											Our Service relies on data from Riot Games' API and other third-party services.
											We are not responsible for disruptions caused by third-party service outages or changes.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>3. User Accounts and Responsibilities</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Account Creation</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>You must provide accurate and complete information</li>
											<li>You are responsible for maintaining account security</li>
											<li>You must notify us immediately of any unauthorized access</li>
											<li>One account per person; multiple accounts are prohibited</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Acceptable Use</h4>
										<p class="text-gray-600 mb-2">You agree to use the Service only for lawful purposes and in accordance with these Terms.
											You
											may not:</p>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Use the Service to cheat, exploit, or gain unfair advantages in League of Legends</li>
											<li>Attempt to reverse engineer, decompile, or hack the Service</li>
											<li>Use automated tools, bots, or scripts to access the Service</li>
											<li>Share, sell, or transfer your account to others</li>
											<li>Violate any applicable laws or regulations</li>
											<li>Harass, abuse, or harm other users</li>
											<li>Upload malicious content or spam</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Content and Conduct</h4>
										<p class="text-gray-600">
											You are responsible for all content you submit and activities that occur under your account.
											We reserve the right to remove content and suspend accounts that violate these Terms.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>4. Subscription and Payment Terms</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Free and Premium Services</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Basic features are available free of charge</li>
											<li>Premium features require a paid subscription</li>
											<li>Subscription fees are billed in advance</li>
											<li>All fees are non-refundable unless required by law</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Billing and Cancellation</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Subscriptions automatically renew unless cancelled</li>
											<li>You can cancel your subscription at any time</li>
											<li>Cancellation takes effect at the end of the current billing period</li>
											<li>We may change subscription prices with 30 days notice</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Payment Processing</h4>
										<p class="text-gray-600">
											Payments are processed by third-party payment providers. We do not store your payment information.
											You agree to pay all charges incurred under your account.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>5. Intellectual Property Rights</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Our Rights</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Draftolio AI owns all rights to the Service and its content</li>
											<li>Our trademarks, logos, and branding are protected</li>
											<li>The Service is protected by copyright and other intellectual property laws</li>
											<li>You may not copy, modify, or distribute our content without permission</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Your Rights</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>You retain ownership of content you create and submit</li>
											<li>You grant us a license to use your content to provide the Service</li>
											<li>You can delete your content at any time</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Third-Party Rights</h4>
										<p class="text-gray-600">
											League of Legends and related content are owned by Riot Games. We use this content under
											Riot Games' legal policies. All champion names, images, and game data belong to Riot Games.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>6. Privacy and Data Protection</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<p class="text-gray-600">
										Your privacy is important to us. Our data practices are governed by our
										<a routerLink="/privacy"
										   class="text-blue-600 hover:text-blue-800 underline">Privacy Policy</a>,
										which is incorporated into these Terms by reference.
									</p>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Data Collection</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>We collect only necessary data to provide the Service</li>
											<li>We use publicly available League of Legends match data</li>
											<li>We implement appropriate security measures to protect your data</li>
											<li>You can request deletion of your personal data</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Data Usage</h4>
										<p class="text-gray-600">
											We use your data to provide and improve the Service, communicate with you,
											and comply with legal obligations. We do not sell your personal information to third parties.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>7. Disclaimers and Limitations</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Service Disclaimers</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>The Service is provided "as is" without warranties of any kind</li>
											<li>We do not guarantee the accuracy of predictions or recommendations</li>
											<li>Game outcomes depend on many factors beyond our analysis</li>
											<li>The Service may contain bugs, errors, or inaccuracies</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Limitation of Liability</h4>
										<p class="text-gray-600">
											To the maximum extent permitted by law, Draftolio AI shall not be liable for any indirect,
											incidental, special, consequential, or punitive damages, including but not limited to loss of
											profits, data, or use, arising out of or relating to your use of the Service.
										</p>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Indemnification</h4>
										<p class="text-gray-600">
											You agree to indemnify and hold harmless Draftolio AI from any claims, damages, or expenses
											arising from your use of the Service or violation of these Terms.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>8. Termination</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Termination by You</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>You may terminate your account at any time</li>
											<li>Termination does not entitle you to a refund</li>
											<li>You remain responsible for all charges incurred before termination</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Termination by Us</h4>
										<p class="text-gray-600 mb-2">We may terminate or suspend your account immediately if you:</p>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>Violate these Terms or our policies</li>
											<li>Engage in fraudulent or illegal activities</li>
											<li>Fail to pay required fees</li>
											<li>Pose a security risk to the Service or other users</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Effect of Termination</h4>
										<p class="text-gray-600">
											Upon termination, your right to use the Service ceases immediately. We may delete your
											account and data, though some information may be retained as required by law or for
											legitimate business purposes.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>

						<p-accordion-panel>
							<p-accordion-header>9. Governing Law and Disputes</p-accordion-header>
							<p-accordion-content>
								<div class="space-y-4">
									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Governing Law</h4>
										<p class="text-gray-600">
											These Terms are governed by the laws of [Jurisdiction], without regard to conflict of law principles.
											Any disputes will be resolved in the courts of [Jurisdiction].
										</p>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Dispute Resolution</h4>
										<ul class="list-disc list-inside text-gray-600 space-y-1 ml-4">
											<li>We encourage resolving disputes through direct communication</li>
											<li>Contact us at <a routerLink="/contact"
											                     class="text-blue-600 hover:text-blue-800 underline">our contact page</a> for assistance
											</li>
											<li>Formal disputes may be subject to binding arbitration</li>
											<li>Class action lawsuits are waived to the extent permitted by law</li>
										</ul>
									</div>

									<div>
										<h4 class="font-semibold text-gray-900 mb-2">Severability</h4>
										<p class="text-gray-600">
											If any provision of these Terms is found to be unenforceable, the remaining provisions
											will continue in full force and effect.
										</p>
									</div>
								</div>
							</p-accordion-content>
						</p-accordion-panel>
					</p-accordion>
				</p-card>

				<!-- Contact Information -->
				<div class="mt-12 text-center">
					<h3 class="text-xl font-semibold text-gray-900 mb-4">Questions About These Terms?</h3>
					<p class="text-gray-600 mb-6">
						If you have any questions about these Terms of Service, please contact us.
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
							href="mailto:legal{{ '@' }}draftolioai.com"
							class="inline-flex items-center px-6 py-3 border border-gray-300 text-base font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 transition-colors duration-200"
						>
							<i class="pi pi-at mr-2"></i>
							Email Legal Team
						</a>
					</div>
				</div>
			</div>
		</div>
	`,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class TermsComponent {}
