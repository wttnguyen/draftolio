# Feature Specification: Drafting Simulation

> **Status**: Draft
> 
> **Author**: Draftolio Team
> 
> **Created Date**: 2025-08-02
> 
> **Last Updated**: 2025-08-02
> 
> **Epic/Initiative**: Core Drafting Experience

## 1. Overview

### 1.1 Summary

The Drafting Simulation feature is the core functionality of Draftolio, providing a realistic and interactive environment for League of Legends draft simulation. It enables users to create and participate in drafts with multiple modes (Tournament, Fearless, Full Fearless), following the standard competitive pick and ban process. The feature supports real-time collaboration between captains and spectators, allowing teams to practice and refine their drafting strategies in a realistic environment.

### 1.2 Business Objectives

- **Improve Drafting Skills**: Provide a platform for teams and players to practice and improve their drafting strategies
- **Enhance Competitive Experience**: Create a realistic simulation of the competitive drafting environment
- **Facilitate Collaboration**: Enable teams to practice drafts together with real-time updates and interactions
- **Support Multiple Formats**: Accommodate various draft formats used in different competitive settings
- **Educational Value**: Help players understand the strategic aspects of the draft phase

### 1.3 User Value Proposition

The Drafting Simulation feature provides significant value to users by offering a dedicated platform to practice and refine drafting strategies, which is a critical aspect of competitive League of Legends. It allows teams to experiment with different champion selections and banning strategies in a risk-free environment, helping them prepare for actual competitions. The real-time collaboration aspect enables team members to participate together, fostering communication and strategic thinking. For content creators and analysts, it provides a tool to demonstrate and explain drafting concepts to their audience.

### 1.4 Scope

- **In Scope**: 
  - Tournament Draft mode implementation
  - Fearless Draft mode implementation
  - Full Fearless Draft mode implementation
  - Draft creation and configuration
  - Real-time draft process with pick and ban phases
  - Captain and spectator roles
  - Champion selection and validation
  - Draft state management
  - Timer functionality
  - Draft completion and history
  - Integration with team management feature

- **Out of Scope**: 
  - Advanced draft analytics
  - AI-assisted drafting recommendations
  - Custom draft mode creation
  - Tournament management
  - Champion statistics and performance metrics

- **Future Considerations**:
  - Draft templates and presets
  - Draft sharing and export capabilities
  - In-draft communication tools
  - Draft replay functionality
  - Integration with professional match data

## 2. User Stories and Requirements

### 2.1 User Personas

| Persona | Description | Goals | Pain Points |
|---------|-------------|-------|-------------|
| Team Captain | Leader of a competitive team who manages drafting strategy | Create effective drafts, practice different strategies, prepare for competitions | Lack of realistic draft practice, difficulty coordinating with team members |
| Team Player | Member of a competitive team who participates in matches | Understand team's drafting strategy, provide input on champion selections | Limited visibility into draft planning, inability to practice drafting outside of competitions |
| Team Coach | Guides team strategy and helps improve performance | Develop effective drafting strategies, teach players about draft importance | Lack of tools to demonstrate draft concepts, difficulty simulating real draft scenarios |
| Content Creator | Creates educational or entertainment content about League of Legends | Analyze drafts, create engaging content about drafting strategies | Lack of visual tools to demonstrate draft concepts, difficulty simulating professional drafts |
| Tournament Organizer | Runs competitions between multiple teams | Efficiently manage draft phase of tournaments, ensure fair competition | Manual tracking of draft process, inconsistent draft procedures |
| Casual Player | Plays League of Legends for fun but is interested in competitive aspects | Learn about drafting, experiment with draft strategies | Intimidating competitive environment, lack of accessible drafting tools |

### 2.2 User Stories

#### US-1: Draft Creation

As a team captain, I want to create a new draft with customizable settings, so that I can practice specific drafting scenarios.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given I am logged in, when I navigate to the Drafts section, then I can see a "Create Draft" button.
2. Given I click "Create Draft", when I fill in the draft details, then I can specify a name, draft mode (Tournament, Fearless, Full Fearless), and optional description.
3. Given I am creating a draft, when I select a draft mode, then I see an explanation of the mode's rules.
4. Given I am creating a draft, when I complete the form, then I can invite another user to be the opposing captain.
5. Given I have created a draft, when the draft is created, then I am assigned as the Blue Side captain by default.
6. Given I have created a draft, when I view the draft details, then I can generate a shareable spectator link.

#### US-2: Tournament Draft Mode

As a team captain, I want to participate in a Tournament Draft, so that I can practice the standard competitive drafting format.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given I am in a Tournament Draft, when the draft begins, then it follows the standard pick and ban order.
2. Given I am in a Tournament Draft, when it's my turn to ban, then I can select a champion to ban.
3. Given I am in a Tournament Draft, when it's my turn to pick, then I can select a champion to pick.
4. Given I am in a Tournament Draft, when a champion is picked or banned, then it becomes unavailable for selection.
5. Given I am in a Tournament Draft, when all picks and bans are completed, then the draft is marked as completed.

#### US-3: Fearless Draft Mode

As a team captain, I want to participate in a Fearless Draft series, so that I can practice drafting across multiple games where previous picks are banned.

**Priority**: Should Have

**Acceptance Criteria**:
1. Given I am creating a Fearless Draft, when I set up the draft, then I can specify the number of games in the series (Bo3 or Bo5).
2. Given I am in a Fearless Draft, when a game in the series is completed, then champions picked in that game are automatically banned for subsequent games.
3. Given I am in a Fearless Draft, when viewing the champion selection screen, then previously picked champions are shown as unavailable.
4. Given I am in a Fearless Draft, when all games in the series are completed, then the draft series is marked as completed.

#### US-4: Full Fearless Draft Mode

As a team captain, I want to participate in a Full Fearless Draft series, so that I can practice drafting with both previous picks and bans unavailable.

**Priority**: Should Have

**Acceptance Criteria**:
1. Given I am creating a Full Fearless Draft, when I set up the draft, then I can specify the number of games in the series (Bo3 or Bo5).
2. Given I am in a Full Fearless Draft, when a game in the series is completed, then both champions picked and banned in that game are automatically banned for subsequent games.
3. Given I am in a Full Fearless Draft, when viewing the champion selection screen, then previously picked and banned champions are shown as unavailable.
4. Given I am in a Full Fearless Draft, when all games in the series are completed, then the draft series is marked as completed.

#### US-5: Real-time Draft Updates

As a draft participant (captain or spectator), I want to see real-time updates during the draft, so that I can follow the draft process as it happens.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given I am in a draft, when a champion is picked or banned, then I see the update immediately without refreshing.
2. Given I am in a draft, when the current phase or turn changes, then I see the update immediately.
3. Given I am in a draft, when the timer is running, then I see it counting down in real-time.
4. Given I am in a draft as a spectator, when I join mid-draft, then I see the current state of the draft with all previous picks and bans.
5. Given I am in a draft, when I temporarily lose connection, then I automatically reconnect and sync with the current draft state.

#### US-6: Draft Timer

As a draft captain, I want a timer for each pick and ban phase, so that the draft progresses at a reasonable pace.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given I am in a draft, when it's my turn to pick or ban, then a timer starts counting down.
2. Given I am in a draft, when the timer expires during my turn, then a champion is automatically selected or the turn is skipped.
3. Given I am creating a draft, when configuring settings, then I can customize the timer duration for picks and bans.
4. Given I am in a draft, when the timer is running, then I can see the remaining time prominently displayed.
5. Given I am in a draft, when there are 10 seconds remaining on the timer, then I receive a visual and audio notification.

#### US-7: Champion Selection and Filtering

As a draft captain, I want to search and filter champions during selection, so that I can quickly find the champions I'm looking for.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given I am selecting a champion, when I type in the search box, then the champion list filters in real-time.
2. Given I am selecting a champion, when I use role filters (Top, Jungle, Mid, ADC, Support), then only champions commonly played in those roles are shown.
3. Given I am selecting a champion, when I hover over a champion, then I see additional information about that champion.
4. Given I am selecting a champion, when a champion is unavailable (picked or banned), then it is visually indicated as unavailable.
5. Given I am selecting a champion, when I click on an available champion, then it is selected for my current pick or ban.

#### US-8: Spectator Mode

As a spectator, I want to view ongoing drafts in real-time, so that I can observe the drafting process without participating.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given I have a spectator link, when I open it, then I can view the draft in real-time without needing to log in.
2. Given I am spectating a draft, when a pick or ban occurs, then I see the update in real-time.
3. Given I am spectating a draft, when I view the draft board, then I can see all picks and bans but cannot make selections.
4. Given I am spectating a draft, when the draft is completed, then I can see the final draft result.
5. Given I am a captain, when I want to share my draft, then I can generate a unique spectator link.

#### US-9: Draft Completion and History

As a user, I want to view completed drafts, so that I can analyze and learn from previous drafting sessions.

**Priority**: Should Have

**Acceptance Criteria**:
1. Given a draft is completed, when I view my draft history, then I can see all my completed drafts.
2. Given I am viewing a completed draft, when I open the draft details, then I can see the full pick and ban sequence.
3. Given I am viewing a completed draft, when I view the draft summary, then I can see the final team compositions.
4. Given I am viewing my draft history, when I filter by draft mode, then I see only drafts of the selected mode.
5. Given I am viewing a completed draft, when I want to share it, then I can generate a shareable link.

#### US-10: Team-Based Drafting

As a team captain, I want to create drafts between two teams with assigned positions, so that we can practice with our actual team compositions.

**Priority**: Should Have

**Acceptance Criteria**:
1. Given I am creating a draft, when I select "Team Draft" as the type, then I can select two teams from my teams or search for other teams.
2. Given I am creating a team draft, when I select a team, then I must assign a player to each position (Top, Jungle, Middle, Bottom, Support).
3. Given I am in a team draft, when viewing the draft board, then I can see each player's position and name.
4. Given I am in a team draft, when a champion is picked, then it is assigned to the corresponding player and position.
5. Given I am in a team draft, when I hover over a player, then I can see their top 3 champions that are not banned or picked.

### 2.3 Non-Functional Requirements

| Requirement Type | Description | Acceptance Criteria |
|------------------|-------------|---------------------|
| Performance | Draft updates must be delivered in real-time with minimal latency | Updates are delivered within 100ms to all participants |
| Scalability | System must support many concurrent drafts | System can handle at least 1000 concurrent drafts |
| Reliability | Draft state must be preserved even in case of temporary disconnections | Users can reconnect and continue the draft from where they left off |
| Security | Only authorized users can participate as captains | Authentication and authorization checks prevent unauthorized access |
| Usability | Interface must be intuitive and easy to use | New users can complete a draft without requiring documentation |
| Accessibility | Draft interface must be accessible to users with disabilities | Interface meets WCAG 2.1 AA standards |
| Compatibility | Draft interface must work across different browsers and devices | Interface functions correctly on Chrome, Firefox, Safari, and Edge, as well as on desktop and tablet devices |

## 3. Design

### 3.1 User Experience

#### 3.1.1 User Flow

The drafting simulation feature follows these main user flows:

1. **Draft Creation Flow**:
   - User logs in to the application
   - User navigates to the Drafts section
   - User clicks "Create Draft" button
   - User fills in draft details (name, mode, description)
   - User invites another user as the opposing captain
   - System creates the draft and assigns the creator as Blue Side captain
   - User can generate and share a spectator link

2. **Draft Participation Flow (Captain)**:
   - Captain receives draft invitation or creates a draft
   - Captain joins the draft room
   - System displays the draft board with champion selection interface
   - When it's the captain's turn, they select a champion to pick or ban
   - Captain continues until all picks and bans are completed
   - System displays the final draft result

3. **Draft Spectating Flow**:
   - User receives a spectator link
   - User opens the link in a browser
   - System displays the draft board in read-only mode
   - User sees real-time updates as picks and bans occur
   - User can view the final draft result when completed

4. **Team-Based Draft Flow**:
   - Captain creates a draft and selects "Team Draft" type
   - Captain selects two teams and assigns players to positions
   - System creates the draft with player information
   - During the draft, picks are associated with specific players and positions
   - System displays player information and champion preferences during the draft

5. **Draft History Flow**:
   - User navigates to the Draft History section
   - User views a list of completed drafts
   - User selects a draft to view details
   - System displays the full pick and ban sequence and final team compositions
   - User can filter drafts by mode or date

#### 3.1.2 Wireframes/Mockups

```
[Draft Creation Screen]
+-----------------------------------------------+
| Create Draft                                  |
+-----------------------------------------------+
| Draft Name: [________________]                |
|                                               |
| Draft Mode:                                   |
| (•) Tournament Draft                          |
| ( ) Fearless Draft                            |
| ( ) Full Fearless Draft                       |
|                                               |
| Description: (optional)                       |
| [                                           ] |
| [                                           ] |
|                                               |
| Invite Opposing Captain: [_____________] [+]  |
|                                               |
| Timer Settings:                               |
| Ban Phase Timer: [30] seconds                 |
| Pick Phase Timer: [60] seconds                |
|                                               |
|                [Cancel] [Create Draft]        |
+-----------------------------------------------+

[Draft Board - Tournament Mode]
+-----------------------------------------------+
| Tournament Draft: Team Alpha vs Team Beta     |
| Current Phase: Ban Phase 1 - Blue Side Ban 1  |
| Timer: 0:28                                   |
+-----------------------------------------------+
| Blue Side (You)           Red Side            |
+-----------------------------------------------+
| Bans:                     Bans:               |
| [ ] [ ] [ ]               [ ] [ ] [ ]         |
| [ ] [ ]                   [ ] [ ]             |
+-----------------------------------------------+
| Picks:                    Picks:              |
| Top:    [ ]               Top:    [ ]         |
| Jungle: [ ]               Jungle: [ ]         |
| Mid:    [ ]               Mid:    [ ]         |
| Bot:    [ ]               Bot:    [ ]         |
| Sup:    [ ]               Sup:    [ ]         |
+-----------------------------------------------+
| Champion Selection:                           |
| [Search Champions...]     [Role Filter ▼]     |
|                                               |
| [Champion Grid with Icons]                    |
|                                               |
| Champion Info:                                |
| Name: -                                       |
| Roles: -                                      |
| Description: -                                |
+-----------------------------------------------+
| [Share Spectator Link] [Draft History]        |
+-----------------------------------------------+

[Team-Based Draft Board]
+-----------------------------------------------+
| Team Draft: Team Alpha vs Team Beta           |
| Current Phase: Pick Phase 1 - Red Side Pick 1 |
| Timer: 0:45                                   |
+-----------------------------------------------+
| Blue Side: Team Alpha      Red Side: Team Beta|
+-----------------------------------------------+
| Bans:                      Bans:              |
| [Aatrox] [Ahri] [Akali]    [Caitlyn] [Darius] [Ezreal] |
+-----------------------------------------------+
| Picks:                     Picks:             |
| Top:    [Garen] (Player1)  Top:    [ ] (Player6) |
| Jungle: [ ] (Player2)      Jungle: [ ] (Player7) |
| Mid:    [ ] (Player3)      Mid:    [ ] (Player8) |
| Bot:    [ ] (Player4)      Bot:    [ ] (Player9) |
| Sup:    [ ] (Player5)      Sup:    [ ] (Player10)|
+-----------------------------------------------+
| Player Info:                                  |
| Player6 (Top)                                 |
| Top Champions: Camille, Fiora, Jax           |
+-----------------------------------------------+
| Champion Selection:                           |
| [Search Champions...]      [Role Filter ▼]    |
|                                               |
| [Champion Grid with Icons]                    |
+-----------------------------------------------+

[Draft History Screen]
+-----------------------------------------------+
| Draft History                                 |
+-----------------------------------------------+
| Filter: [All Modes ▼] [Date Range ▼]          |
+-----------------------------------------------+
| Draft Name | Mode | Date | Teams | Actions |
|------------|------|------|-------|---------|
| Scrim #1   | Tournament | 2025-07-30 | Alpha vs Beta | View |
| Scrim #2   | Fearless   | 2025-08-01 | Alpha vs Gamma | View |
| Practice   | Full Fearless | 2025-08-02 | Beta vs Delta | View |
+-----------------------------------------------+
```

#### 3.1.3 UI Components

| Component | Description | Status |
|-----------|-------------|--------|
| Draft Creation Form | Form for creating a new draft with customizable settings | New |
| Draft Board | Main interface showing the current state of the draft with picks and bans | New |
| Champion Selection Grid | Grid of champion icons for selection during picks and bans | New |
| Champion Search | Search box for filtering champions by name | New |
| Role Filter | Dropdown for filtering champions by role | New |
| Champion Info Panel | Panel showing detailed information about a selected champion | New |
| Draft Timer | Countdown timer for the current pick or ban phase | New |
| Team Panel | Panel showing team information and player positions | New |
| Player Info Panel | Panel showing player information and champion preferences | New |
| Draft History List | List of completed drafts with filtering options | New |
| Draft Summary | Summary view of a completed draft showing final team compositions | New |
| Spectator Link Generator | Button and modal for generating and sharing spectator links | New |

### 3.2 Technical Design

#### 3.2.1 Architecture

The drafting simulation feature will be implemented using a combination of REST APIs for draft management and WebSockets for real-time updates. The architecture follows the hexagonal (ports and adapters) pattern with clear separation of concerns.

```
[Client Application]
        ↑↓
[API Gateway]
        ↑↓
+-------+-------+
|               |
[Draft Service] ←→ [Champion Service]
|       ↑       |
|       |       |
|       ↓       |
[Database] ←→ [Redis Cache]
        ↑
        |
        ↓
[WebSocket Server] ←→ [Redis Pub/Sub]
        ↑
        |
        ↓
[Client WebSocket]
```

Key components:
1. **Draft Service**: Core service responsible for draft creation, management, and state transitions
2. **Champion Service**: Service providing champion data and validation
3. **WebSocket Server**: Handles real-time communication for draft updates
4. **Redis Cache**: Stores draft state for quick access and recovery
5. **Redis Pub/Sub**: Distributes real-time events across service instances
6. **Database**: Persistent storage for draft data and history

#### 3.2.2 Data Model

| Entity | Attributes | Relationships | Changes |
|--------|------------|---------------|---------|
| Draft | id, name, status, draftMode, createdAt, updatedAt, blueSideCaptain, redSideCaptain, currentPhase, currentTurn, blueSideTeam, redSideTeam, timerDuration, gameNumber | blueSidePicks, redSidePicks, blueSideBans, redSideBans, spectators, draftHistory, blueSideTeam, redSideTeam | Modified (added timerDuration, gameNumber) |
| DraftMode | id, name, description | drafts | New |
| DraftPhase | id, name, order | drafts | New |
| DraftTurn | id, phase, team, order, type | drafts | New |
| Pick | id, champion, pickOrder, team, draftId, position, playerId | draft, champion, player, position | No change |
| Ban | id, champion, banOrder, team, draftId | draft, champion | No change |
| DraftHistory | id, draftId, action, champion, team, timestamp, user, player, position | draft, champion, user, player | No change |
| DraftTimer | id, draftId, duration, startTime, endTime, turnId | draft, turn | New |
| DraftSpectator | id, draftId, userId, accessToken, joinedAt | draft, user | New |
| DraftSeries | id, name, mode, numberOfGames, currentGame | drafts | New (for Fearless and Full Fearless modes) |

#### 3.2.3 API Design

##### Endpoint 1: POST /api/v1/drafts

**Request**:
```json
{
  "name": "Scrim #1",
  "mode": "TOURNAMENT",
  "description": "Practice draft for upcoming tournament",
  "redSideCaptainId": "user_id",
  "timerSettings": {
    "banPhaseDuration": 30,
    "pickPhaseDuration": 60
  },
  "teamDraft": false
}
```

**Response**:
```json
{
  "id": "draft_id",
  "name": "Scrim #1",
  "status": "CREATED",
  "mode": "TOURNAMENT",
  "description": "Practice draft for upcoming tournament",
  "blueSideCaptain": {
    "id": "user_id",
    "username": "username"
  },
  "redSideCaptain": {
    "id": "user_id",
    "username": "username"
  },
  "currentPhase": null,
  "currentTurn": null,
  "createdAt": "2025-08-02T10:00:00Z",
  "updatedAt": "2025-08-02T10:00:00Z",
  "spectateUrl": "https://draftolio.com/draft/spectate/abc123"
}
```

**Status Codes**:
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 500: Internal Server Error

##### Endpoint 2: POST /api/v1/drafts/team

**Request**:
```json
{
  "name": "Team Alpha vs Team Beta",
  "mode": "TOURNAMENT",
  "description": "Team practice draft",
  "blueSideTeamId": "team_id",
  "redSideTeamId": "team_id",
  "blueSidePositions": {
    "TOP": "player_id",
    "JUNGLE": "player_id",
    "MIDDLE": "player_id",
    "BOTTOM": "player_id",
    "SUPPORT": "player_id"
  },
  "redSidePositions": {
    "TOP": "player_id",
    "JUNGLE": "player_id",
    "MIDDLE": "player_id",
    "BOTTOM": "player_id",
    "SUPPORT": "player_id"
  },
  "timerSettings": {
    "banPhaseDuration": 30,
    "pickPhaseDuration": 60
  }
}
```

**Response**:
```json
{
  "id": "draft_id",
  "name": "Team Alpha vs Team Beta",
  "status": "CREATED",
  "mode": "TOURNAMENT",
  "description": "Team practice draft",
  "blueSideCaptain": {
    "id": "user_id",
    "username": "username"
  },
  "redSideCaptain": {
    "id": "user_id",
    "username": "username"
  },
  "blueSideTeam": {
    "id": "team_id",
    "name": "Team Alpha"
  },
  "redSideTeam": {
    "id": "team_id",
    "name": "Team Beta"
  },
  "currentPhase": null,
  "currentTurn": null,
  "createdAt": "2025-08-02T10:00:00Z",
  "updatedAt": "2025-08-02T10:00:00Z",
  "spectateUrl": "https://draftolio.com/draft/spectate/abc123"
}
```

**Status Codes**:
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 404: Not Found
- 500: Internal Server Error

##### Endpoint 3: POST /api/v1/drafts/{draftId}/start

**Response**:
```json
{
  "id": "draft_id",
  "status": "IN_PROGRESS",
  "currentPhase": "BAN_PHASE_1",
  "currentTurn": {
    "team": "BLUE",
    "type": "BAN",
    "order": 1
  },
  "timerEndTime": "2025-08-02T10:01:30Z"
}
```

**Status Codes**:
- 200: Success
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

##### Endpoint 4: POST /api/v1/drafts/{draftId}/pick

**Request**:
```json
{
  "championId": "champion_id"
}
```

**Response**:
```json
{
  "id": "pick_id",
  "champion": {
    "id": "champion_id",
    "name": "Ahri",
    "imageUrl": "url_to_image"
  },
  "team": "BLUE",
  "position": "MIDDLE",
  "pickOrder": 3
}
```

**Status Codes**:
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 409: Conflict (champion already picked/banned)
- 500: Internal Server Error

##### Endpoint 5: POST /api/v1/drafts/{draftId}/ban

**Request**:
```json
{
  "championId": "champion_id"
}
```

**Response**:
```json
{
  "id": "ban_id",
  "champion": {
    "id": "champion_id",
    "name": "Aatrox",
    "imageUrl": "url_to_image"
  },
  "team": "RED",
  "banOrder": 2
}
```

**Status Codes**:
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 409: Conflict (champion already picked/banned)
- 500: Internal Server Error

##### Endpoint 6: GET /api/v1/drafts/{draftId}

**Response**:
```json
{
  "id": "draft_id",
  "name": "Scrim #1",
  "status": "IN_PROGRESS",
  "mode": "TOURNAMENT",
  "description": "Practice draft for upcoming tournament",
  "blueSideCaptain": {
    "id": "user_id",
    "username": "username"
  },
  "redSideCaptain": {
    "id": "user_id",
    "username": "username"
  },
  "currentPhase": "PICK_PHASE_1",
  "currentTurn": {
    "team": "RED",
    "type": "PICK",
    "order": 2
  },
  "blueSidePicks": [
    {
      "id": "pick_id",
      "champion": {
        "id": "champion_id",
        "name": "Ahri",
        "imageUrl": "url_to_image"
      },
      "position": "MIDDLE",
      "pickOrder": 1
    }
  ],
  "redSidePicks": [
    {
      "id": "pick_id",
      "champion": {
        "id": "champion_id",
        "name": "Yasuo",
        "imageUrl": "url_to_image"
      },
      "position": "MIDDLE",
      "pickOrder": 1
    }
  ],
  "blueSideBans": [
    {
      "id": "ban_id",
      "champion": {
        "id": "champion_id",
        "name": "Aatrox",
        "imageUrl": "url_to_image"
      },
      "banOrder": 1
    },
    {
      "id": "ban_id",
      "champion": {
        "id": "champion_id",
        "name": "Akali",
        "imageUrl": "url_to_image"
      },
      "banOrder": 2
    },
    {
      "id": "ban_id",
      "champion": {
        "id": "champion_id",
        "name": "Alistar",
        "imageUrl": "url_to_image"
      },
      "banOrder": 3
    }
  ],
  "redSideBans": [
    {
      "id": "ban_id",
      "champion": {
        "id": "champion_id",
        "name": "Caitlyn",
        "imageUrl": "url_to_image"
      },
      "banOrder": 1
    },
    {
      "id": "ban_id",
      "champion": {
        "id": "champion_id",
        "name": "Darius",
        "imageUrl": "url_to_image"
      },
      "banOrder": 2
    },
    {
      "id": "ban_id",
      "champion": {
        "id": "champion_id",
        "name": "Ezreal",
        "imageUrl": "url_to_image"
      },
      "banOrder": 3
    }
  ],
  "timerEndTime": "2025-08-02T10:05:30Z",
  "createdAt": "2025-08-02T10:00:00Z",
  "updatedAt": "2025-08-02T10:03:45Z",
  "spectateUrl": "https://draftolio.com/draft/spectate/abc123"
}
```

**Status Codes**:
- 200: Success
- 401: Unauthorized
- 404: Not Found
- 500: Internal Server Error

##### Endpoint 7: GET /api/v1/drafts/history

**Query Parameters**:
- mode: Filter by draft mode (optional)
- startDate: Filter by start date (optional)
- endDate: Filter by end date (optional)
- page: Page number (default: 0)
- size: Page size (default: 20)

**Response**:
```json
{
  "content": [
    {
      "id": "draft_id",
      "name": "Scrim #1",
      "mode": "TOURNAMENT",
      "status": "COMPLETED",
      "blueSideTeam": "Team Alpha",
      "redSideTeam": "Team Beta",
      "createdAt": "2025-08-02T10:00:00Z",
      "completedAt": "2025-08-02T10:15:30Z"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

**Status Codes**:
- 200: Success
- 401: Unauthorized
- 500: Internal Server Error

##### Endpoint 8: POST /api/v1/drafts/{draftId}/spectate

**Response**:
```json
{
  "spectateUrl": "https://draftolio.com/draft/spectate/abc123",
  "accessToken": "spectate_token",
  "expiresAt": "2025-08-03T10:00:00Z"
}
```

**Status Codes**:
- 200: Success
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

#### 3.2.4 WebSocket Events

##### Event 1: draft_state_update

**Payload**:
```json
{
  "draftId": "draft_id",
  "status": "IN_PROGRESS",
  "currentPhase": "PICK_PHASE_1",
  "currentTurn": {
    "team": "RED",
    "type": "PICK",
    "order": 2
  },
  "timerEndTime": "2025-08-02T10:05:30Z",
  "lastAction": {
    "type": "PICK",
    "team": "BLUE",
    "champion": {
      "id": "champion_id",
      "name": "Ahri",
      "imageUrl": "url_to_image"
    },
    "position": "MIDDLE",
    "player": {
      "id": "player_id",
      "name": "Player1"
    },
    "timestamp": "2025-08-02T10:03:45Z"
  }
}
```

**Direction**: Server to Client

**Description**: Sent when the draft state changes, such as when a champion is picked or banned, or when the current turn changes.

##### Event 2: draft_timer_update

**Payload**:
```json
{
  "draftId": "draft_id",
  "timerEndTime": "2025-08-02T10:05:30Z",
  "remainingSeconds": 28
}
```

**Direction**: Server to Client

**Description**: Sent periodically to update the timer for the current turn.

##### Event 3: draft_pick

**Payload**:
```json
{
  "draftId": "draft_id",
  "championId": "champion_id"
}
```

**Direction**: Client to Server

**Description**: Sent when a captain selects a champion to pick.

##### Event 4: draft_ban

**Payload**:
```json
{
  "draftId": "draft_id",
  "championId": "champion_id"
}
```

**Direction**: Client to Server

**Description**: Sent when a captain selects a champion to ban.

##### Event 5: draft_completed

**Payload**:
```json
{
  "draftId": "draft_id",
  "blueSideTeam": {
    "name": "Team Alpha",
    "picks": [
      {
        "champion": {
          "id": "champion_id",
          "name": "Garen",
          "imageUrl": "url_to_image"
        },
        "position": "TOP",
        "player": {
          "id": "player_id",
          "name": "Player1"
        }
      }
    ]
  },
  "redSideTeam": {
    "name": "Team Beta",
    "picks": [
      {
        "champion": {
          "id": "champion_id",
          "name": "Darius",
          "imageUrl": "url_to_image"
        },
        "position": "TOP",
        "player": {
          "id": "player_id",
          "name": "Player6"
        }
      }
    ]
  },
  "completedAt": "2025-08-02T10:15:30Z"
}
```

**Direction**: Server to Client

**Description**: Sent when a draft is completed, containing the final team compositions.

##### Event 6: player_info_request

**Payload**:
```json
{
  "draftId": "draft_id",
  "playerId": "player_id"
}
```

**Direction**: Client to Server

**Description**: Sent when a captain requests information about a player during the draft.

##### Event 7: player_info_response

**Payload**:
```json
{
  "draftId": "draft_id",
  "playerId": "player_id",
  "name": "Player1",
  "position": "TOP",
  "topChampions": [
    {
      "id": "champion_id",
      "name": "Garen",
      "imageUrl": "url_to_image"
    },
    {
      "id": "champion_id",
      "name": "Darius",
      "imageUrl": "url_to_image"
    },
    {
      "id": "champion_id",
      "name": "Sett",
      "imageUrl": "url_to_image"
    }
  ]
}
```

**Direction**: Server to Client

**Description**: Sent in response to a player_info_request, containing the player's information and top champions that are still available.

#### 3.2.5 Sequence Diagrams

```
Draft Creation:

User -> Frontend: Create Draft Form Submission
Frontend -> API: POST /api/v1/drafts
API -> DraftService: Create Draft
DraftService -> Database: Save Draft
Database -> DraftService: Draft Saved
DraftService -> API: Draft Created
API -> Frontend: Draft Created Response
Frontend -> User: Show Draft Details

Draft Start:

Captain -> Frontend: Start Draft
Frontend -> API: POST /api/v1/drafts/{id}/start
API -> DraftService: Start Draft
DraftService -> Database: Update Draft Status
DraftService -> WebSocketService: Broadcast Draft Started
WebSocketService -> All Clients: draft_state_update
Frontend -> Captain: Show Draft Board

Champion Pick:

Captain -> Frontend: Select Champion
Frontend -> WebSocketService: draft_pick
WebSocketService -> DraftService: Process Pick
DraftService -> Database: Save Pick
DraftService -> WebSocketService: Broadcast State Update
WebSocketService -> All Clients: draft_state_update
Frontend -> All Users: Update Draft Board

Draft Completion:

DraftService -> Database: Update Draft Status to Completed
DraftService -> WebSocketService: Broadcast Draft Completed
WebSocketService -> All Clients: draft_completed
Frontend -> All Users: Show Draft Summary
```

### 3.3 Security Considerations

- **Authentication**: All draft creation and participation endpoints require authentication. Only authenticated users can create drafts and participate as captains.
- **Authorization**: Only the assigned captains can make picks and bans for their respective sides. Spectators have read-only access.
- **Spectator Links**: Spectator links contain a secure token that expires after a set period. The token is validated for each WebSocket connection.
- **Input Validation**: All user inputs (champion selections, draft settings) are validated on the server side to prevent invalid or malicious data.
- **Rate Limiting**: API endpoints and WebSocket connections are rate-limited to prevent abuse.
- **Data Protection**: Draft data is protected and only shared with authorized users (captains and spectators).

### 3.4 Performance Considerations

- **WebSocket Optimization**: WebSocket payloads are kept minimal to reduce bandwidth usage and improve real-time performance.
- **Caching**: Draft state is cached in Redis for quick access and recovery in case of service restarts.
- **Connection Management**: WebSocket connections are managed efficiently to handle many concurrent drafts.
- **Database Optimization**: Indexes are created for frequently queried fields to improve database performance.
- **Scalability**: The system is designed to scale horizontally to handle increased load during peak times.
- **Real-time Updates**: Updates are delivered to all clients within 100ms to ensure a responsive user experience.
- **Reconnection Handling**: Clients can reconnect and sync with the current draft state in case of temporary disconnections.

## 4. Implementation Plan

### 4.1 Dependencies

| Dependency | Description | Status |
|------------|-------------|--------|
| Champion Data | Complete list of League of Legends champions with attributes | Available |
| WebSocket Infrastructure | Infrastructure for real-time communication | Available |
| Redis | In-memory data store for caching and pub/sub | Available |
| User Authentication Service | Service for user authentication and authorization | Available |
| Team Management Feature | Feature for managing teams and players | Available |

### 4.2 Tasks

#### Backend Tasks

| Task ID | Description | Estimated Effort | Dependencies |
|---------|-------------|------------------|--------------|
| BE-1 | Create Draft domain model (entities, repositories) | 3 days | None |
| BE-2 | Implement Draft service with core business logic | 5 days | BE-1 |
| BE-3 | Develop REST API endpoints for draft management | 3 days | BE-2 |
| BE-4 | Implement draft state machine for managing draft flow | 4 days | BE-2 |
| BE-5 | Create WebSocket handlers for real-time updates | 3 days | BE-2, BE-4 |
| BE-6 | Implement draft timer functionality | 2 days | BE-4, BE-5 |
| BE-7 | Develop champion selection and validation logic | 3 days | BE-2, BE-4 |
| BE-8 | Implement Fearless Draft mode logic | 3 days | BE-2, BE-4, BE-7 |
| BE-9 | Implement Full Fearless Draft mode logic | 2 days | BE-8 |
| BE-10 | Create spectator functionality with secure tokens | 2 days | BE-3, BE-5 |
| BE-11 | Implement draft history and completion logic | 3 days | BE-2, BE-4 |
| BE-12 | Integrate with Team Management feature | 3 days | BE-2, BE-3 |
| BE-13 | Implement caching for draft state in Redis | 2 days | BE-2, BE-4 |
| BE-14 | Create automated tests for draft business logic | 4 days | BE-1 through BE-13 |

#### Frontend Tasks

| Task ID | Description | Estimated Effort | Dependencies |
|---------|-------------|------------------|--------------|
| FE-1 | Create Draft creation form component | 2 days | None |
| FE-2 | Develop Draft board UI component | 4 days | None |
| FE-3 | Implement Champion selection grid component | 3 days | None |
| FE-4 | Create Champion search and filtering functionality | 2 days | FE-3 |
| FE-5 | Develop Draft timer UI component | 1 day | None |
| FE-6 | Implement WebSocket client for real-time updates | 3 days | FE-2 |
| FE-7 | Create Draft state management using signals | 3 days | FE-2, FE-6 |
| FE-8 | Implement Team-based draft UI components | 3 days | FE-2 |
| FE-9 | Develop Player info panel component | 2 days | FE-8 |
| FE-10 | Create Draft history and summary components | 3 days | None |
| FE-11 | Implement Spectator link generation and sharing | 1 day | None |
| FE-12 | Develop responsive layouts for all draft components | 2 days | FE-1 through FE-11 |
| FE-13 | Create animations and transitions for draft updates | 2 days | FE-2, FE-7 |
| FE-14 | Implement error handling and recovery for WebSocket disconnections | 2 days | FE-6, FE-7 |

#### Real-time Communication Tasks

| Task ID | Description | Estimated Effort | Dependencies |
|---------|-------------|------------------|--------------|
| RT-1 | Design WebSocket event protocol for draft updates | 2 days | None |
| RT-2 | Implement server-side WebSocket message handlers | 3 days | RT-1, BE-5 |
| RT-3 | Create client-side WebSocket message processors | 3 days | RT-1, FE-6 |
| RT-4 | Develop reconnection and state synchronization logic | 2 days | RT-2, RT-3 |
| RT-5 | Implement Redis pub/sub for distributing events across instances | 2 days | RT-2 |
| RT-6 | Create WebSocket authentication and authorization | 2 days | RT-2 |
| RT-7 | Optimize WebSocket payload size and frequency | 2 days | RT-2, RT-3 |
| RT-8 | Implement WebSocket connection monitoring and metrics | 1 day | RT-2 |

#### Data Tasks

| Task ID | Description | Estimated Effort | Dependencies |
|---------|-------------|------------------|--------------|
| DATA-1 | Create database schema for draft entities | 2 days | BE-1 |
| DATA-2 | Implement database migrations | 1 day | DATA-1 |
| DATA-3 | Create indexes for performance optimization | 1 day | DATA-1 |
| DATA-4 | Implement Redis caching for draft state | 2 days | BE-13 |
| DATA-5 | Develop data access layer for draft entities | 3 days | DATA-1, DATA-2 |
| DATA-6 | Create data retention policy for completed drafts | 1 day | None |
| DATA-7 | Implement draft history data aggregation | 2 days | DATA-5 |

#### DevOps Tasks

| Task ID | Description | Estimated Effort | Dependencies |
|---------|-------------|------------------|--------------|
| OPS-1 | Configure WebSocket server infrastructure | 2 days | None |
| OPS-2 | Set up Redis for caching and pub/sub | 1 day | None |
| OPS-3 | Configure monitoring for WebSocket connections | 1 day | OPS-1 |
| OPS-4 | Implement logging for draft events | 1 day | None |
| OPS-5 | Create deployment pipeline for draft service | 1 day | None |
| OPS-6 | Set up performance testing environment | 2 days | OPS-1, OPS-2 |

### 4.3 Timeline

| Phase | Start Date | End Date | Deliverables |
|-------|------------|----------|--------------|
| Design | 2025-08-05 | 2025-08-09 | Detailed technical design, API specifications, WebSocket protocol |
| Backend Development | 2025-08-12 | 2025-08-30 | Draft service, REST APIs, WebSocket handlers |
| Frontend Development | 2025-08-12 | 2025-08-30 | Draft UI components, WebSocket client |
| Integration | 2025-09-02 | 2025-09-09 | Integrated draft simulation feature |
| Testing | 2025-09-10 | 2025-09-16 | Tested feature with fixed bugs |
| Deployment | 2025-09-17 | 2025-09-18 | Deployed feature to production |

### 4.4 Risks and Mitigations

| Risk | Impact | Likelihood | Mitigation |
|------|--------|------------|------------|
| WebSocket performance issues with many concurrent drafts | High | Medium | Implement connection pooling, optimize payload size, use Redis pub/sub for scaling |
| Inconsistent draft state across clients | High | Medium | Implement robust state synchronization, use server as source of truth, add recovery mechanisms |
| Browser compatibility issues with WebSocket | Medium | Low | Use a WebSocket library with fallback mechanisms, test across all major browsers |
| Redis failure affecting draft state | High | Low | Implement persistence to database, add Redis clustering, create recovery procedures |
| Complex draft rules leading to bugs | Medium | Medium | Implement comprehensive test suite, create state machine for draft flow, add validation |
| Team Management feature integration challenges | Medium | Medium | Define clear interfaces, implement feature toggles, create fallback mechanisms |
| Timer synchronization issues across clients | Medium | High | Use server-side timer with periodic updates, implement drift correction |

## 5. Testing Strategy

### 5.1 Test Scenarios

| Scenario ID | Description | Test Type | Priority |
|-------------|-------------|-----------|----------|
| TS-1 | Verify draft creation with different modes | Integration | High |
| TS-2 | Test complete Tournament Draft flow | E2E | High |
| TS-3 | Test complete Fearless Draft flow | E2E | High |
| TS-4 | Test complete Full Fearless Draft flow | E2E | High |
| TS-5 | Verify champion selection and validation | Unit/Integration | High |
| TS-6 | Test draft timer functionality | Integration | High |
| TS-7 | Verify real-time updates for all participants | Integration | High |
| TS-8 | Test spectator functionality | E2E | Medium |
| TS-9 | Verify draft completion and history | Integration | Medium |
| TS-10 | Test team-based drafting with player positions | E2E | High |
| TS-11 | Verify WebSocket reconnection handling | Integration | High |
| TS-12 | Test concurrent draft sessions | Load | Medium |
| TS-13 | Verify draft state consistency across clients | Integration | High |
| TS-14 | Test champion search and filtering | Unit | Medium |
| TS-15 | Verify draft board UI rendering | Unit | Medium |
| TS-16 | Test responsive layout on different devices | E2E | Medium |
| TS-17 | Verify accessibility compliance | Accessibility | Medium |
| TS-18 | Test error handling for invalid actions | Unit/Integration | High |
| TS-19 | Verify authorization for captain actions | Security | High |
| TS-20 | Test integration with Team Management feature | Integration | High |

### 5.2 Test Data Requirements

| Data Type | Description | Source |
|-----------|-------------|--------|
| Champion Data | Complete list of League of Legends champions with attributes | Static data file |
| User Accounts | Test user accounts with different roles | Test database |
| Draft Templates | Predefined draft configurations for different modes | Test fixtures |
| Team Data | Sample teams with players and positions | Test database |
| Player Data | Sample players with champion preferences | Test database |
| Draft History | Sample completed drafts for testing history views | Test fixtures |
| WebSocket Messages | Sample WebSocket messages for different events | Test fixtures |
| Timer Events | Simulated timer events for testing timeout behavior | Test fixtures |
| Error Scenarios | Data for triggering various error conditions | Test fixtures |
| Browser Configurations | Different browser and device configurations | Test environment |

### 5.3 Performance Testing

| Test Type | Description | Success Criteria |
|-----------|-------------|------------------|
| Load Testing | Test system with many concurrent drafts | System handles 1000 concurrent drafts with response times under 200ms |
| Stress Testing | Test system under peak load conditions | System maintains stability with 2000 concurrent users |
| Endurance Testing | Test system over extended period | System maintains performance over 24-hour period with continuous drafting |
| WebSocket Performance | Test WebSocket message throughput | System handles 100 messages per second per draft |
| API Response Time | Test REST API response times | 95% of API requests complete in under 200ms |
| Database Performance | Test database query performance | Database queries complete in under 50ms |
| Redis Performance | Test Redis caching performance | Cache operations complete in under 10ms |
| Client Rendering | Test client-side rendering performance | UI updates render in under 100ms |
| Network Latency | Test performance under various network conditions | System functions with up to 300ms network latency |
| Resource Utilization | Test CPU, memory, and network usage | System uses resources efficiently under load |

### 5.4 Real-time Testing

| Test Type | Description | Success Criteria |
|-----------|-------------|------------------|
| WebSocket Connection | Test WebSocket connection establishment | Connections established in under 1 second |
| Real-time Updates | Test delivery of real-time updates | Updates delivered to all clients within 100ms |
| Concurrent Actions | Test multiple users performing actions simultaneously | System correctly handles concurrent actions |
| State Synchronization | Test state synchronization across clients | All clients show consistent state within 200ms |
| Reconnection Handling | Test client reconnection after disconnection | Clients reconnect and sync state within 2 seconds |
| Message Ordering | Test correct ordering of WebSocket messages | Messages processed in correct order |
| Broadcast Scaling | Test broadcasting to many spectators | System handles broadcasts to 1000+ spectators |
| Draft Timer Accuracy | Test timer accuracy across clients | Timer synchronization within 500ms across all clients |
| Draft State Transitions | Test state transitions during draft flow | State transitions occur correctly and in real-time |
| Error Recovery | Test recovery from error conditions | System recovers from errors without losing draft state |

## 6. Rollout Plan

### 6.1 Deployment Strategy

The drafting simulation feature will be deployed using a phased rollout approach to minimize risk and gather feedback early:

1. **Phase 1: Internal Testing**
   - Deploy to development environment
   - Internal team testing with simulated drafts
   - Fix critical issues identified during testing

2. **Phase 2: Beta Release**
   - Deploy to staging environment
   - Invite selected users for beta testing
   - Collect feedback and make improvements
   - Monitor performance and stability

3. **Phase 3: Limited Production Release**
   - Deploy to production with feature flag
   - Enable for 10% of users initially
   - Gradually increase to 50% over one week
   - Monitor metrics and user feedback

4. **Phase 4: Full Production Release**
   - Enable for all users
   - Announce feature through blog post and social media
   - Continue monitoring and optimization

**Feature Flags**:
- `enable_drafting_simulation`: Master toggle for the entire feature
- `enable_tournament_draft`: Toggle for Tournament Draft mode
- `enable_fearless_draft`: Toggle for Fearless Draft mode
- `enable_full_fearless_draft`: Toggle for Full Fearless Draft mode
- `enable_team_based_drafting`: Toggle for team-based drafting functionality

**Rollback Plan**:
In case of critical issues, the following rollback procedures will be implemented:
1. Disable the affected feature flag immediately
2. Revert to the previous stable version if necessary
3. Communicate the issue to affected users
4. Investigate and fix the issue in development
5. Re-deploy with fixes and gradually re-enable

### 6.2 Monitoring and Alerting

| Metric | Description | Threshold | Alert |
|--------|-------------|-----------|-------|
| Draft Creation Rate | Number of drafts created per minute | > 100 | Warning alert to operations team |
| Draft Creation Errors | Percentage of draft creation attempts that fail | > 5% | Critical alert to on-call engineer |
| WebSocket Connection Count | Number of active WebSocket connections | > 10,000 | Scaling alert to operations team |
| WebSocket Error Rate | Percentage of WebSocket operations that fail | > 2% | Critical alert to on-call engineer |
| Draft Completion Rate | Percentage of started drafts that complete successfully | < 95% | Warning alert to development team |
| API Response Time | Time to respond to draft API requests | > 500ms | Warning alert to development team |
| Redis CPU Usage | CPU usage of Redis instances | > 70% | Scaling alert to operations team |
| Redis Memory Usage | Memory usage of Redis instances | > 80% | Scaling alert to operations team |
| Database Query Time | Time for draft-related database queries | > 100ms | Warning alert to development team |
| Client Error Rate | Percentage of client-side errors reported | > 1% | Warning alert to development team |
| Draft Timer Sync Deviation | Average timer deviation across clients | > 1s | Warning alert to development team |
| Concurrent Drafts | Number of concurrent active drafts | > 500 | Scaling alert to operations team |

**Dashboards**:
- Draft Activity Dashboard: Shows draft creation, completion, and error rates
- WebSocket Performance Dashboard: Shows connection counts, message rates, and errors
- User Experience Dashboard: Shows client-side performance metrics and errors
- Resource Utilization Dashboard: Shows server, database, and Redis resource usage

**Health Checks**:
- Draft Service API health check
- WebSocket Server health check
- Redis connection health check
- Database connection health check

### 6.3 Documentation and Training

| Item | Audience | Description | Owner |
|------|----------|-------------|-------|
| User Guide | End Users | Documentation on how to use the drafting simulation feature, including all draft modes | Documentation Team |
| API Documentation | Developers | Technical documentation for draft-related APIs | Development Team |
| WebSocket Protocol Guide | Developers | Documentation of WebSocket events and protocols | Development Team |
| Admin Guide | Administrators | Guide for managing and troubleshooting the drafting feature | Operations Team |
| Feature Announcement | All Users | Blog post and email announcing the drafting simulation feature | Marketing Team |
| Video Tutorial | End Users | Video demonstrating how to create and participate in drafts | Content Team |
| Internal Knowledge Base | Support Team | Detailed information for handling user questions and issues | Support Team |
| Release Notes | All Users | Detailed description of the feature and its capabilities | Product Team |
| FAQ | End Users | Frequently asked questions about the drafting simulation feature | Documentation Team |
| Troubleshooting Guide | Support Team | Guide for diagnosing and resolving common issues | Support Team |

## 7. Success Metrics

### 7.1 Key Performance Indicators

| KPI | Description | Target | Measurement Method |
|-----|-------------|--------|-------------------|
| Draft Creation Rate | Number of drafts created per day | 1000+ | Database metrics |
| User Adoption | Percentage of users creating or participating in drafts | 50%+ | User analytics |
| Draft Completion Rate | Percentage of started drafts that complete successfully | 95%+ | Application metrics |
| Average Draft Duration | Average time to complete a draft | 10-15 minutes | Application metrics |
| Concurrent Drafts | Maximum number of concurrent drafts | 500+ | Application metrics |
| Spectator Count | Average number of spectators per draft | 3+ | Application metrics |
| Feature Usage Distribution | Usage distribution across different draft modes | Even distribution | User analytics |
| Team-Based Draft Usage | Percentage of drafts that are team-based | 30%+ | Application metrics |
| User Retention | Increase in user retention after feature launch | 15%+ | User analytics |
| Session Duration | Average time users spend in the application | 20+ minutes | User analytics |
| Cross-Platform Usage | Usage distribution across different devices | Desktop: 70%, Mobile: 30% | User analytics |
| Error Rate | Percentage of drafts encountering errors | < 1% | Application metrics |
| Performance Metrics | Average response time for draft operations | < 200ms | Application metrics |
| WebSocket Reliability | Percentage of WebSocket connections maintained without interruption | 99%+ | Application metrics |

### 7.2 User Feedback

| Feedback Type | Collection Method | Success Criteria |
|---------------|-------------------|------------------|
| Feature Satisfaction | In-app survey after draft completion | 80%+ satisfaction rating |
| Usability | User testing sessions with different personas | 90%+ task completion rate |
| Feature Requests | Feedback form in the application | Collect actionable feedback for future improvements |
| Bug Reports | In-app reporting tool | < 5 critical bugs reported in first month |
| Draft Experience | Post-draft survey | 85%+ of users report realistic draft experience |
| Real-time Performance | Targeted survey about real-time aspects | 90%+ of users satisfied with real-time updates |
| Team-Based Drafting | Survey for team captains | 80%+ of captains find team-based drafting valuable |
| Spectator Experience | Survey for spectators | 75%+ of spectators satisfied with viewing experience |
| Comparative Feedback | Survey comparing to other draft tools | 80%+ prefer Draftolio over alternatives |
| Social Media Sentiment | Analysis of social media mentions | 80%+ positive sentiment |
| Net Promoter Score | In-app NPS survey | NPS score of 40+ |
| Feature Adoption Barriers | Interviews with non-adopters | Identify and address top 3 barriers to adoption |

## 8. Appendix

### 8.1 References

- [League of Legends Champion Data API](https://developer.riotgames.com/docs/lol)
- [WebSocket Protocol RFC 6455](https://tools.ietf.org/html/rfc6455)
- [Redis Pub/Sub Documentation](https://redis.io/topics/pubsub)
- [Angular Signals Documentation](https://angular.dev/guide/signals)
- [Spring WebFlux Documentation](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [League of Legends Competitive Draft Rules](https://lol.fandom.com/wiki/Draft_Mode)
- [Real-time Web Application Architecture](https://www.oreilly.com/library/view/building-real-time-web/9781784393915/)
- [Feature Flag Best Practices](https://martinfowler.com/articles/feature-toggles.html)
- [WebSocket Security Best Practices](https://devcenter.heroku.com/articles/websocket-security)
- [Redis Caching Strategies](https://redis.com/redis-best-practices/caching-patterns/)

### 8.2 Glossary

| Term | Definition |
|------|------------|
| Draft | A structured process of selecting champions for a League of Legends match |
| Tournament Draft | Standard draft mode used in competitive League of Legends |
| Fearless Draft | A series of games where champions picked in previous games are banned for subsequent games |
| Full Fearless Draft | Similar to Fearless Draft, but both previous picks AND bans are banned for subsequent games |
| Captain | User who controls the draft process for one side (Blue or Red) |
| Spectator | User who can view the draft in real-time but cannot make selections |
| Ban | Removing a champion from availability for both teams |
| Pick | Selecting a champion for a specific position on a team |
| Blue Side | The team that gets first pick in the draft |
| Red Side | The team that gets counter-pick advantage in the draft |
| Ban Phase | Period during which captains ban champions |
| Pick Phase | Period during which captains pick champions |
| Draft Board | UI component showing the current state of the draft |
| Champion Grid | UI component showing available champions for selection |
| WebSocket | Communication protocol providing full-duplex communication over a single TCP connection |
| Redis Pub/Sub | Messaging pattern where senders publish messages to channels without knowledge of subscribers |
| Draft State | Current status of the draft, including picks, bans, current phase, and turn |
| Draft Timer | Countdown timer for the current pick or ban phase |
| Feature Flag | Configuration option that allows features to be enabled or disabled at runtime |
| Team-Based Draft | Draft where picks are associated with specific players and positions |

### 8.3 Change Log

| Date | Author | Description |
|------|--------|-------------|
| 2025-08-02 | Draftolio Team | Initial draft |
| 2025-08-02 | Draftolio Team | Added detailed WebSocket events and sequence diagrams |
| 2025-08-02 | Draftolio Team | Added implementation plan and testing strategy |
| 2025-08-02 | Draftolio Team | Added rollout plan and success metrics |
| 2025-08-02 | Draftolio Team | Finalized specification |
