import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
	selector: 'app-teams',
	imports: [
		CommonModule,
		ButtonModule,
		CardModule
	],
	template: `
    <div class="container mx-auto px-4 py-8">
      <div class="text-center mb-8">
        <h1 class="text-4xl font-bold text-gray-900 mb-4">My Teams</h1>
        <p class="text-lg text-gray-600">
          Manage your League of Legends teams and analyze team compositions.
        </p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <p-card class="h-full">
          <ng-template pTemplate="header">
            <div class="p-4 text-center">
              <i class="pi pi-users text-3xl text-blue-600 mb-2"></i>
              <h3 class="text-lg font-semibold">Team Alpha</h3>
            </div>
          </ng-template>
          <p class="text-gray-600 text-center mb-4">
            Your main competitive team for ranked games.
          </p>
          <ng-template pTemplate="footer">
            <div class="flex justify-center">
              <p-button label="View Details" severity="primary" size="small"></p-button>
            </div>
          </ng-template>
        </p-card>

        <p-card class="h-full">
          <ng-template pTemplate="header">
            <div class="p-4 text-center">
              <i class="pi pi-plus text-3xl text-green-600 mb-2"></i>
              <h3 class="text-lg font-semibold">Create New Team</h3>
            </div>
          </ng-template>
          <p class="text-gray-600 text-center mb-4">
            Start building a new team composition.
          </p>
          <ng-template pTemplate="footer">
            <div class="flex justify-center">
              <p-button label="Create Team" severity="success" size="small"></p-button>
            </div>
          </ng-template>
        </p-card>
      </div>
    </div>
  `,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class TeamsComponent {}
