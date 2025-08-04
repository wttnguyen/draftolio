import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TableModule } from 'primeng/table';

@Component({
	selector: 'app-drafts',
	imports: [
		CommonModule,
		ButtonModule,
		CardModule,
		TableModule
	],
	template: `
    <div class="container mx-auto px-4 py-8">
      <div class="text-center mb-8">
        <h1 class="text-4xl font-bold text-gray-900 mb-4">My Drafts</h1>
        <p class="text-lg text-gray-600">
          View and analyze your League of Legends draft sessions and champion selections.
        </p>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
        <!-- Stats Cards -->
        <p-card>
          <ng-template pTemplate="header">
            <div class="p-4 text-center">
              <i class="pi pi-chart-line text-3xl text-blue-600 mb-2"></i>
              <h3 class="text-lg font-semibold">Total Drafts</h3>
            </div>
          </ng-template>
          <div class="text-center">
            <span class="text-2xl font-bold text-gray-900">24</span>
            <p class="text-sm text-gray-600">Draft sessions analyzed</p>
          </div>
        </p-card>

        <p-card>
          <ng-template pTemplate="header">
            <div class="p-4 text-center">
              <i class="pi pi-trophy text-3xl text-green-600 mb-2"></i>
              <h3 class="text-lg font-semibold">Win Rate</h3>
            </div>
          </ng-template>
          <div class="text-center">
            <span class="text-2xl font-bold text-green-600">68%</span>
            <p class="text-sm text-gray-600">Success rate with AI recommendations</p>
          </div>
        </p-card>

        <p-card>
          <ng-template pTemplate="header">
            <div class="p-4 text-center">
              <i class="pi pi-star text-3xl text-yellow-600 mb-2"></i>
              <h3 class="text-lg font-semibold">Favorite Role</h3>
            </div>
          </ng-template>
          <div class="text-center">
            <span class="text-2xl font-bold text-gray-900">ADC</span>
            <p class="text-sm text-gray-600">Most played position</p>
          </div>
        </p-card>
      </div>

      <!-- Recent Drafts Table -->
      <p-card>
        <ng-template pTemplate="header">
          <div class="flex justify-between items-center p-4">
            <h2 class="text-xl font-semibold text-gray-900">Recent Draft Sessions</h2>
            <p-button 
              label="New Draft Analysis" 
              icon="pi pi-plus"
              severity="primary"
              size="small"
            ></p-button>
          </div>
        </ng-template>
        
        <p-table [value]="mockDrafts" [tableStyle]="{'min-width': '50rem'}">
          <ng-template pTemplate="header">
            <tr>
              <th>Date</th>
              <th>Game Mode</th>
              <th>Your Role</th>
              <th>Champion</th>
              <th>Result</th>
              <th>AI Score</th>
              <th>Actions</th>
            </tr>
          </ng-template>
          <ng-template pTemplate="body" let-draft>
            <tr>
              <td>{{ draft.date }}</td>
              <td>{{ draft.gameMode }}</td>
              <td>{{ draft.role }}</td>
              <td>{{ draft.champion }}</td>
              <td>
                <span [class]="draft.result === 'Win' ? 'text-green-600 font-semibold' : 'text-red-600 font-semibold'">
                  {{ draft.result }}
                </span>
              </td>
              <td>
                <span class="font-semibold text-blue-600">{{ draft.aiScore }}/10</span>
              </td>
              <td>
                <p-button 
                  icon="pi pi-eye" 
                  [text]="true"
                  severity="info"
                  size="small"
                  pTooltip="View Details"
                ></p-button>
              </td>
            </tr>
          </ng-template>
        </p-table>
      </p-card>
    </div>
  `,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class DraftsComponent
{
	readonly mockDrafts = [
		{
			date: '2024-01-15',
			gameMode: 'Ranked Solo',
			role: 'ADC',
			champion: 'Jinx',
			result: 'Win',
			aiScore: 8.5
		},
		{
			date: '2024-01-14',
			gameMode: 'Ranked Solo',
			role: 'Support',
			champion: 'Thresh',
			result: 'Loss',
			aiScore: 7.2
		},
		{
			date: '2024-01-13',
			gameMode: 'Normal Draft',
			role: 'Mid',
			champion: 'Yasuo',
			result: 'Win',
			aiScore: 9.1
		}
	];
}
