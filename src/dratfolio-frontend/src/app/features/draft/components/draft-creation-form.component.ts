import { ChangeDetectionStrategy, Component, output, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { SelectModule } from 'primeng/select';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { MessageModule } from 'primeng/message';
import { DraftMode } from '../models/draft-mode.model';
import { CreateDraftRequest } from '../models/create-draft-request.model';

/**
 * Component for creating a new draft.
 * Implements US-1 acceptance criteria for draft creation.
 */
@Component({
	selector: 'app-draft-creation-form',
	imports: [
		ReactiveFormsModule,
		ButtonModule,
		CardModule,
		SelectModule,
		InputTextModule,
		TextareaModule,
		InputNumberModule,
		MessageModule
	],
	templateUrl: './draft-creation-form.component.html',
	styleUrl: './draft-creation-form.component.css',
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class DraftCreationFormComponent
{

	// Output events
	readonly draftCreated = output<CreateDraftRequest>();
	readonly cancelled = output<void>();

	// Form state
	protected readonly form: FormGroup;
	protected readonly isSubmitting = signal(false);
	protected readonly errorMessage = signal<string | null>(null);

	// Draft modes with descriptions for US-1 AC3
	protected readonly draftModes = signal<DraftMode[]>([
		{
			value: 'TOURNAMENT',
			label: 'Tournament Draft',
			description: 'Standard competitive draft format with alternating bans and picks.'
		},
		{
			value: 'FEARLESS',
			label: 'Fearless Draft',
			description: 'Champions picked in previous games are automatically banned for subsequent games.'
		},
		{
			value: 'FULL_FEARLESS',
			label: 'Full Fearless Draft',
			description: 'Both champions picked and banned in previous games are automatically banned for subsequent games.'
		}
	]);

	protected readonly selectedModeDescription = signal<string>('');

	constructor(private fb: FormBuilder)
	{
		// Initialize form with validation
		this.form = this.fb.group({
			name: [ '', [ Validators.maxLength(255) ] ],
			description: [ '', [ Validators.maxLength(1000) ] ],
			blueTeamName: [ '', [ Validators.required, Validators.maxLength(255) ] ],
			redTeamName: [ '', [ Validators.required, Validators.maxLength(255) ] ],
			mode: [ null, [ Validators.required ] ],
			banPhaseTimerDuration: [ 30, [ Validators.min(10), Validators.max(300) ] ],
			pickPhaseTimerDuration: [ 60, [ Validators.min(10), Validators.max(300) ] ]
		});

		// Watch for mode changes to update description - US-1 AC3
		this.form.get('mode')?.valueChanges.subscribe(mode =>
		{
			const selectedMode = this.draftModes().find(m => m.value === mode);
			this.selectedModeDescription.set(selectedMode?.description || '');
		});
	}

	/**
	 * Handles form submission - US-1 AC2, AC4, AC5, AC6
	 */
	protected onSubmit(): void
	{
		if (this.form.valid && !this.isSubmitting())
		{
			this.isSubmitting.set(true);
			this.errorMessage.set(null);

			const formValue = this.form.value;

			// Create the draft request
			const request: CreateDraftRequest = {
				name: formValue.name?.trim() || undefined,
				description: formValue.description?.trim() || undefined,
				blueTeamName: formValue.blueTeamName.trim(),
				redTeamName: formValue.redTeamName.trim(),
				mode: formValue.mode,
				banPhaseTimerDuration: formValue.banPhaseTimerDuration || 30,
				pickPhaseTimerDuration: formValue.pickPhaseTimerDuration || 60
			};

			// Emit the draft creation request
			this.draftCreated.emit(request);

			// Reset submitting state (parent component should handle success/error)
			setTimeout(() => this.isSubmitting.set(false), 100);
		}
		else
		{
			this.markFormGroupTouched();
		}
	}

	/**
	 * Handles form cancellation
	 */
	protected onCancel(): void
	{
		this.form.reset();
		this.selectedModeDescription.set('');
		this.errorMessage.set(null);
		this.cancelled.emit();
	}

	/**
	 * Resets the form to initial state
	 */
	public resetForm(): void
	{
		this.form.reset({
			banPhaseTimerDuration: 30,
			pickPhaseTimerDuration: 60
		});
		this.selectedModeDescription.set('');
		this.errorMessage.set(null);
		this.isSubmitting.set(false);
	}

	/**
	 * Sets an error message to display
	 */
	public setError(message: string): void
	{
		this.errorMessage.set(message);
		this.isSubmitting.set(false);
	}

	/**
	 * Checks if a form field has an error and is touched
	 */
	protected hasFieldError(fieldName: string): boolean
	{
		const field = this.form.get(fieldName);
		return !!(field && field.invalid && (field.dirty || field.touched));
	}

	/**
	 * Gets the error message for a form field
	 */
	protected getFieldError(fieldName: string): string
	{
		const field = this.form.get(fieldName);
		if (!field || !field.errors)
		{
			return '';
		}

		const errors = field.errors;

		if (errors['required'])
		{
			return `${this.getFieldLabel(fieldName)} is required`;
		}
		if (errors['maxlength'])
		{
			return `${this.getFieldLabel(fieldName)} is too long`;
		}
		if (errors['min'])
		{
			return `${this.getFieldLabel(fieldName)} must be at least ${errors['min'].min}`;
		}
		if (errors['max'])
		{
			return `${this.getFieldLabel(fieldName)} must be at most ${errors['max'].max}`;
		}

		return 'Invalid value';
	}

	/**
	 * Gets the display label for a form field
	 */
	private getFieldLabel(fieldName: string): string
	{
		const labels: Record<string, string> = {
			name: 'Draft name',
			description: 'Description',
			blueTeamName: 'Blue team name',
			redTeamName: 'Red team name',
			mode: 'Draft mode',
			banPhaseTimerDuration: 'Ban phase timer',
			pickPhaseTimerDuration: 'Pick phase timer'
		};
		return labels[fieldName] || fieldName;
	}

	/**
	 * Marks all form fields as touched to show validation errors
	 */
	private markFormGroupTouched(): void
	{
		Object.keys(this.form.controls).forEach(key =>
		{
			const control = this.form.get(key);
			control?.markAsTouched();
		});
	}
}
