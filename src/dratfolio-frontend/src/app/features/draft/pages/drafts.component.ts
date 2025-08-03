import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { DraftCreationFormComponent } from '../components/draft-creation-form.component';
import { CreateDraftRequest } from '../models/create-draft-request.model';
import { DraftService } from '../services/draft.service';

/**
 * Main drafts page component.
 * Implements US-1 AC1: User can navigate to Drafts section and see "Create Draft" button.
 */
@Component({
	selector: 'app-drafts',
	imports: [
		ButtonModule,
		CardModule,
		ToastModule,
		DraftCreationFormComponent
	],
	providers: [ MessageService ],
	templateUrl: './drafts.component.html',
	styleUrl: './drafts.component.css',
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class DraftsComponent
{

	// Component state
	protected readonly showCreateForm = signal(false);
	protected readonly isCreatingDraft = signal(false);

	constructor(
		private draftService: DraftService,
		private messageService: MessageService
	)
	{}

	/**
	 * Shows the draft creation form.
	 * US-1 AC1: User clicks "Create Draft" button.
	 */
	protected showCreateDraftForm(): void
	{
		this.showCreateForm.set(true);
	}

	/**
	 * Hides the draft creation form.
	 */
	protected hideCreateDraftForm(): void
	{
		this.showCreateForm.set(false);
	}

	/**
	 * Handles draft creation request from the form.
	 * US-1 AC2-6: Processes the draft creation with all acceptance criteria.
	 */
	protected onDraftCreated(request: CreateDraftRequest): void
	{
		this.isCreatingDraft.set(true);

		this.draftService.createDraft(request).subscribe({
			next: (response) =>
			{
				this.isCreatingDraft.set(false);
				this.showCreateForm.set(false);

				// Show success message
				this.messageService.add({
					severity: 'success',
					summary: 'Draft Created',
					detail: `Draft "${response.name}" has been created successfully!`,
					life: 5000
				});

				// Show spectator link if available
				if (response.spectateUrl)
				{
					this.messageService.add({
						severity: 'info',
						summary: 'Spectator Link Generated',
						detail: `Spectator link: ${window.location.origin}${response.spectateUrl}`,
						life: 10000
					});
				}

				console.log('Draft created:', response);
			},
			error: (error) =>
			{
				this.isCreatingDraft.set(false);

				// Show error message
				this.messageService.add({
					severity: 'error',
					summary: 'Draft Creation Failed',
					detail: error.error?.message || 'An error occurred while creating the draft.',
					life: 5000
				});

				console.error('Error creating draft:', error);
			}
		});
	}

	/**
	 * Handles form cancellation.
	 */
	protected onFormCancelled(): void
	{
		this.hideCreateDraftForm();
	}
}
