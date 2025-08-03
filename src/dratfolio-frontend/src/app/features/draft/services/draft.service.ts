import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { CreateDraftRequest } from '../models/create-draft-request.model';
import { DraftResponse } from '../models/draft-response.model';
import { environment } from '../../../../environments/environment';

/**
 * Service for managing draft operations.
 * Handles API communication with the backend for US-1 implementation.
 */
@Injectable({
	providedIn: 'root'
})
export class DraftService
{

	private readonly http = inject(HttpClient);
	private readonly baseUrl = `${environment.apiUrl}/api/v1/drafts`;

	/**
	 * Creates a new draft.
	 * US-1 AC2-6: Handles draft creation with all acceptance criteria.
	 */
	createDraft(request: CreateDraftRequest): Observable<DraftResponse>
	{
		const headers = this.getHeaders();

		return this.http.post<DraftResponse>(this.baseUrl, request, { headers })
			.pipe(
				map(response => this.mapDraftResponse(response)),
				catchError(error => this.handleError(error))
			);
	}

	/**
	 * Retrieves a draft by its ID.
	 */
	getDraftById(draftId: string): Observable<DraftResponse>
	{
		const headers = this.getHeaders();

		return this.http.get<DraftResponse>(`${this.baseUrl}/${draftId}`, { headers })
			.pipe(
				map(response => this.mapDraftResponse(response)),
				catchError(error => this.handleError(error))
			);
	}

	/**
	 * Retrieves all drafts for a user.
	 */
	getDraftsByUser(userId: string): Observable<DraftResponse[]>
	{
		const headers = this.getHeaders();

		return this.http.get<DraftResponse[]>(`${this.baseUrl}/user/${userId}`, { headers })
			.pipe(
				map(response => response.map(draft => this.mapDraftResponse(draft))),
				catchError(error => this.handleError(error))
			);
	}

	/**
	 * Retrieves a draft by its spectate token.
	 */
	getDraftBySpectateToken(spectateToken: string): Observable<DraftResponse>
	{
		return this.http.get<DraftResponse>(`${this.baseUrl}/spectate/${spectateToken}`)
			.pipe(
				map(response => this.mapDraftResponse(response)),
				catchError(error => this.handleError(error))
			);
	}

	/**
	 * Generates a spectator URL for a draft.
	 * US-1 AC6: User can generate a shareable spectator link.
	 */
	generateSpectateUrl(draftId: string): Observable<{ spectateUrl: string; message: string }>
	{
		const headers = this.getHeaders();

		return this.http.post<{ spectateUrl: string; message: string }>(
			`${this.baseUrl}/${draftId}/spectate`,
			{},
			{ headers }
		).pipe(
			catchError(error => this.handleError(error))
		);
	}

	/**
	 * Gets available draft modes with their descriptions.
	 * US-1 AC3: User sees explanation of mode rules when selecting a draft mode.
	 */
	getDraftModes(): Observable<any>
	{
		return this.http.get<any>(`${this.baseUrl}/modes`)
			.pipe(
				catchError(error => this.handleError(error))
			);
	}

	/**
	 * Gets the count of active drafts for a user.
	 */
	getActiveDraftsCount(userId: string): Observable<{ activeCount: number }>
	{
		const headers = this.getHeaders();

		return this.http.get<{ activeCount: number }>(
			`${this.baseUrl}/user/${userId}/active-count`,
			{ headers }
		).pipe(
			catchError(error => this.handleError(error))
		);
	}

	/**
	 * Gets HTTP headers with authentication.
	 * For now, uses a mock user ID since we don't have authentication yet.
	 */
	private getHeaders(): HttpHeaders
	{
		// TODO: Replace with actual authentication token when auth is implemented
		const mockUserId = this.getMockUserId();

		return new HttpHeaders({
			'Content-Type': 'application/json',
			'X-User-Id': mockUserId
		});
	}

	/**
	 * Gets a mock user ID for development.
	 * In a real implementation, this would come from the authentication service.
	 */
	private getMockUserId(): string
	{
		// Use a consistent mock user ID for development
		let userId = localStorage.getItem('mockUserId');
		if (!userId)
		{
			userId = this.generateUUID();
			localStorage.setItem('mockUserId', userId);
		}
		return userId;
	}

	/**
	 * Generates a simple UUID for mock purposes.
	 */
	private generateUUID(): string
	{
		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c)
		{
			const r = Math.random() * 16 | 0;
			const v = c === 'x' ? r : (r & 0x3 | 0x8);
			return v.toString(16);
		});
	}

	/**
	 * Maps the draft response from the backend to ensure proper typing.
	 */
	private mapDraftResponse(response: any): DraftResponse
	{
		return {
			id: response.id,
			name: response.name,
			description: response.description,
			blueTeamName: response.blueTeamName,
			redTeamName: response.redTeamName,
			status: response.status,
			mode: response.mode,
			blueSideCaptainId: response.blueSideCaptainId,
			redSideCaptainId: response.redSideCaptainId,
			blueSideTeamId: response.blueSideTeamId,
			redSideTeamId: response.redSideTeamId,
			currentPhase: response.currentPhase,
			currentTurn: response.currentTurn,
			gameNumber: response.gameNumber,
			banPhaseTimerDuration: response.banPhaseTimerDuration,
			pickPhaseTimerDuration: response.pickPhaseTimerDuration,
			timerEndTime: response.timerEndTime ? new Date(response.timerEndTime) : undefined,
			spectateUrl: response.spectateUrl,
			blueCaptainUrl: response.blueCaptainUrl,
			redCaptainUrl: response.redCaptainUrl,
			createdAt: new Date(response.createdAt),
			updatedAt: new Date(response.updatedAt),
			completedAt: response.completedAt ? new Date(response.completedAt) : undefined
		};
	}

	/**
	 * Handles HTTP errors.
	 */
	private handleError(error: any): Observable<never>
	{
		console.error('DraftService error:', error);

		let errorMessage = 'An unexpected error occurred';

		if (error.error?.message)
		{
			errorMessage = error.error.message;
		}
		else if (error.message)
		{
			errorMessage = error.message;
		}
		else if (error.status)
		{
			switch (error.status)
			{
				case 400:
					errorMessage = 'Invalid request data';
					break;
				case 401:
					errorMessage = 'Authentication required';
					break;
				case 403:
					errorMessage = 'Access denied';
					break;
				case 404:
					errorMessage = 'Draft not found';
					break;
				case 409:
					errorMessage = 'Conflict - resource already exists';
					break;
				case 500:
					errorMessage = 'Server error - please try again later';
					break;
				default:
					errorMessage = `HTTP ${error.status}: ${error.statusText}`;
			}
		}

		return throwError(() => new Error(errorMessage));
	}
}
