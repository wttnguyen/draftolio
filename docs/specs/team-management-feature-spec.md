# Feature Specification: Team Management

> **Status**: Draft
> 
> **Author**: Draftolio Team
> 
> **Created Date**: 2025-08-02
> 
> **Last Updated**: 2025-08-02
> 
> **Epic/Initiative**: Team Management and Riot Integration

## 1. Overview

### 1.1 Summary

The Team Management feature allows users to create and manage teams of League of Legends players, assign them to specific positions, and create drafts between teams. Users can sign in using Riot Sign-On (RSO) to access these features. The system automatically calculates each player's top 3 champions based on their ranked match history and performance. During drafting, captains can see each player's top 3 champions that are not removed from availability, providing valuable information for strategic drafting decisions.

### 1.2 Business Objectives

- Enhance the drafting experience by incorporating real player data and preferences
- Increase user engagement by allowing users to create and manage teams
- Streamline the draft setup process for competitive teams
- Provide additional strategic information during drafting
- Integrate with Riot's ecosystem to improve user authentication and data accuracy

### 1.3 User Value Proposition

Team Management provides significant value to users by allowing them to create virtual representations of their actual teams, complete with player positions and champion preferences. This makes the drafting experience more realistic and valuable for practice. Teams can simulate drafts with accurate player information, helping them prepare for competitive matches by considering each player's champion pool and preferences.

### 1.4 Scope

- **In Scope**: 
  - Team creation and management
  - Player assignment to positions (Top, Jungle, Middle, Bottom, Support)
  - Creating drafts between two teams
  - Automatic calculation of players' top 3 champions based on ranked match history and performance
  - Displaying players' top 3 champions during drafting
  - Basic team statistics
  - Integration with Riot Sign-On authentication (detailed in [RSO Integration Specification](./riot-sign-on-integration-spec.md))

- **Out of Scope**: 
  - Advanced team analytics
  - Player performance tracking
  - Integration with tournament platforms
  - Team communication features

- **Future Considerations**:
  - Team practice scheduling
  - Champion recommendation system based on team composition
  - Historical draft analysis by team
  - Integration with professional team data

## 2. User Stories and Requirements

### 2.1 User Personas

| Persona | Description | Goals | Pain Points |
|---------|-------------|-------|-------------|
| Team Captain | Leader of a competitive team who manages drafting strategy | Create effective drafts, manage team roster, practice drafting strategies | Difficulty tracking player preferences, lack of realistic draft practice |
| Team Player | Member of a competitive team who participates in matches | Improve champion pool, practice specific roles, contribute to team strategy | Limited visibility into team's drafting strategy, inability to communicate champion preferences |
| Team Coach | Guides team strategy and helps improve performance | Develop effective drafting strategies, analyze team strengths and weaknesses | Lack of tools to simulate drafts with actual team data, difficulty tracking player champion pools |
| Tournament Organizer | Runs competitions between multiple teams | Efficiently manage draft phase of tournaments, ensure fair competition | Manual tracking of teams and players, inconsistent draft processes |

### 2.2 User Stories

**Note**: For user stories related to Riot Sign-On authentication, see [Riot Sign-On Integration Specification](./riot-sign-on-integration-spec.md).

#### US-1: Team Creation and Management

As a team captain, I want to create and manage teams with League of Legends players, so that I can organize my roster and prepare for drafts.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given I am logged in, when I navigate to the Teams section, then I can see a list of my teams.
2. Given I am in the Teams section, when I click "Create Team", then I can enter team details (name, description).
3. Given I am creating or editing a team, when I add players, then I can specify their summoner names or Riot IDs.
4. Given I am managing a team, when I view the team details, then I can see all players on the team.
5. Given I am managing a team, when I edit the team, then I can update team details and player information.
6. Given I am managing a team, when I remove a player, then the player is no longer associated with the team.

#### US-2: Player Position Assignment

As a team captain, I want to assign players to specific positions, so that I can organize my team according to their roles.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given I am managing a team, when I add or edit a player, then I can assign them to a position (Top, Jungle, Middle, Bottom, Support).
2. Given I am viewing team details, when I look at the player list, then I can see each player's assigned position.
3. Given I am managing a team, when I try to assign multiple players to the same position, then I receive a warning but am allowed to proceed.
4. Given I am managing a team with more than 5 players, when I view the team, then I can see all players grouped by their positions.

#### US-3: Automatic Champion Preference Calculation

As a player, I want the system to automatically calculate my champion preferences based on my ranked match history and performance, so that my team can see my best champions during drafting without manual input.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given a player is added to a team, when the system analyzes their ranked match history, then it automatically calculates their top 3 champions for each position.
2. Given the system is calculating champion preferences, when analyzing match history, then it considers factors such as win rate, KDA ratio, and games played.
3. Given a player has played ranked games recently, when I view their profile, then I can see their automatically calculated top 3 champions for each position.
4. Given a player's match history changes over time, when they play new ranked games, then their champion preferences are updated accordingly.
5. Given I am a team captain, when I view a player's profile, then I can see their automatically calculated champion preferences.

#### US-4: Team-Based Draft Creation

As a team captain, I want to create drafts between two teams, so that we can practice drafting with our actual rosters.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given I am logged in, when I create a new draft, then I can select "Team Draft" as the draft type.
2. Given I am creating a team draft, when I select teams, then I can choose from my teams or search for other teams.
3. Given I am creating a team draft, when I select a team, then I must assign a player to each position (Top, Jungle, Middle, Bottom, Support).
4. Given I have selected two teams and assigned positions, when I start the draft, then the draft begins with the selected teams and players.
5. Given a team has fewer than 5 players, when I try to create a draft, then I am prompted to assign players to all positions before proceeding.

#### US-5: Viewing Player Champion Preferences During Draft

As a captain, I want to see each player's top 3 champions during drafting, so that I can make informed picking and banning decisions.

**Priority**: Must Have

**Acceptance Criteria**:
1. Given I am in a team draft, when I view the draft board, then I can see each player's position and name.
2. Given I am in a team draft, when I hover over a player, then I can see their top 3 champions that are not banned or picked.
3. Given a player's preferred champion is banned or picked, when I hover over the player, then that champion is not shown in their top 3 list.
4. Given I am the captain of the blue side, when I view player information, then I can only see detailed champion preferences for my team.
5. Given I am a spectator, when I view the draft, then I can see basic player information but not detailed champion preferences.

### 2.3 Non-Functional Requirements

| Requirement Type | Description | Acceptance Criteria |
|------------------|-------------|---------------------|
| Performance | Team and player data must load quickly | Team list loads in under 1 second, player details in under 500ms |
| Security | User authentication must be secure | All authentication uses HTTPS, tokens are properly validated and stored securely |
| Usability | Team management interface must be intuitive | Users can create a team and add players without requiring documentation |
| Accessibility | All features must be accessible | Interface meets WCAG 2.1 AA standards |
| Scalability | System must support many teams and players | System can handle at least 10,000 teams with 50 players each |
| Real-time | Champion preference updates during draft must be immediate | When a champion is banned or picked, player preferences update within 100ms |

## 3. Design

### 3.1 User Experience

#### 3.1.1 User Flow

1. User logs in using Riot Sign-On
2. User navigates to Teams section
3. User creates a new team or selects an existing team
4. User adds players to the team and assigns positions
5. User navigates to Drafts section
6. User creates a new team draft, selecting teams and assigning players to positions
7. User starts the draft
8. During the draft, user can view player information and champion preferences
9. After the draft, user can view results and statistics

#### 3.1.2 Wireframes/Mockups

```
[Team Management Page]
+-----------------------------------------------+
| Teams                           [Create Team] |
+-----------------------------------------------+
| Team Name    | Players | Last Draft | Actions |
|--------------|---------|------------|---------|
| Team Alpha   | 5       | 2025-07-30 | Edit    |
| Team Beta    | 7       | 2025-08-01 | Edit    |
| Team Gamma   | 4       | -          | Edit    |
+-----------------------------------------------+

[Team Detail Page]
+-----------------------------------------------+
| Team Alpha                     [Edit] [Delete]|
+-----------------------------------------------+
| Description: Competitive team for tournaments |
| Created: 2025-07-15                          |
+-----------------------------------------------+
| Players                         [Add Player]  |
+-----------------------------------------------+
| Player      | Position | Top Champions        |
|-------------|----------|----------------------|
| SummonerOne | Top      | Darius, Garen, Sett  |
| SummonerTwo | Jungle   | Lee Sin, Vi, Elise   |
| SummonerThr | Middle   | Ahri, Syndra, Orianna|
| SummonerFour| Bottom   | Jinx, Caitlyn, Ezreal|
| SummonerFive| Support  | Thresh, Leona, Lulu  |
+-----------------------------------------------+

[Create Team Draft]
+-----------------------------------------------+
| Create Team Draft                             |
+-----------------------------------------------+
| Blue Side Team: [Team Alpha  v]               |
|                                               |
| Top:      [SummonerOne  v]                    |
| Jungle:   [SummonerTwo  v]                    |
| Middle:   [SummonerThr  v]                    |
| Bottom:   [SummonerFour v]                    |
| Support:  [SummonerFive v]                    |
+-----------------------------------------------+
| Red Side Team: [Team Beta   v]                |
|                                               |
| Top:      [SummonerSix   v]                   |
| Jungle:   [SummonerSeven v]                   |
| Middle:   [SummonerEight v]                   |
| Bottom:   [SummonerNine  v]                   |
| Support:  [SummonerTen   v]                   |
+-----------------------------------------------+
|                [Start Draft]                  |
+-----------------------------------------------+

[Draft Board with Player Info]
+-----------------------------------------------+
| Blue Side                     Red Side        |
| Team Alpha                    Team Beta       |
+-----------------------------------------------+
| Bans:                         Bans:           |
| [ ] [ ] [ ] [ ] [ ]           [ ] [ ] [ ] [ ] [ ] |
+-----------------------------------------------+
| Picks:                        Picks:          |
| Top:    [ ]                   Top:    [ ]     |
| Jungle: [ ]                   Jungle: [ ]     |
| Mid:    [ ]                   Mid:    [ ]     |
| Bot:    [ ]                   Bot:    [ ]     |
| Sup:    [ ]                   Sup:    [ ]     |
+-----------------------------------------------+
| Player Info:                                  |
| SummonerOne (Top)                             |
| Top Champions: Darius, Garen, Sett            |
+-----------------------------------------------+
```

#### 3.1.3 UI Components

| Component | Description | Status |
|-----------|-------------|--------|
| Team List | Displays all teams with basic information | New |
| Team Detail | Shows detailed team information and player roster | New |
| Player Card | Displays player information and champion preferences | New |
| Team Form | Form for creating and editing teams | New |
| Player Form | Form for adding and editing players | New |
| Team Draft Setup | Interface for setting up a draft between teams | New |
| Player Champion Preferences | Component showing player's top champions during draft | New |
| Position Assignment | Interface for assigning players to positions | New |

### 3.2 Technical Design

#### 3.2.1 Architecture

The Team Management feature will be integrated into the existing Draftolio architecture, with new components for team and player management. The feature will use the Riot API for authentication, champion data, and player match history.

```
[Client] <--> [API Gateway]
                  |
    +-------------+-------------+-------------+
    |             |             |             |
[Auth Service] [Draft Service] [Team Service] [Match Analysis Service]
    |             |             |      |      |
    +-------------+-------------+      |      |
                  |                    |      |
              [Database] <-------------+      |
                                              |
                                        [Riot API]
```

The Match Analysis Service is responsible for:
1. Fetching player ranked match history from the Riot API
2. Analyzing match performance (win rate, KDA, CS/min, etc.)
3. Calculating champion preferences based on performance metrics
4. Periodically updating preferences as players play new ranked games
5. Caching match data to minimize API calls and respect rate limits

#### 3.2.2 Data Model

| Entity | Attributes | Relationships | Changes |
|--------|------------|---------------|---------|
| User | id, username, email, password, role, createdAt, updatedAt, riotId | createdDrafts, participatedDrafts, spectatedDrafts, ownedTeams, memberOfTeams | Modified (added riotId, ownedTeams, memberOfTeams) |
| Team | id, name, description, createdAt, updatedAt, ownerId | owner, players, blueSideDrafts, redSideDrafts | New |
| Player | id, summonerName, riotId, teamId, position, lastAnalyzedAt | team, topChampions, matchHistory | Modified (added lastAnalyzedAt, matchHistory) |
| PlayerChampion | id, playerId, championId, position, preference (1-3), winRate, kda, gamesPlayed, lastUpdatedAt | player, champion | Modified (added position, winRate, kda, gamesPlayed, lastUpdatedAt) |
| PlayerMatchHistory | id, playerId, matchId, championId, position, win, kills, deaths, assists, cs, gameDuration, gameDate | player, champion | New |
| ChampionPerformance | id, playerId, championId, position, winRate, kda, csPerMin, gamesPlayed, averageDamage | player, champion | New |
| Draft | id, name, status, draftMode, createdAt, updatedAt, blueSideCaptain, redSideCaptain, currentPhase, currentTurn, blueSideTeam, redSideTeam | blueSidePicks, redSidePicks, blueSideBans, redSideBans, spectators, draftHistory, blueSideTeam, redSideTeam | Modified (added blueSideTeam, redSideTeam) |
| Pick | id, champion, pickOrder, team, draftId, position, playerId | player, position | Modified (added position, playerId) |

#### 3.2.3 API Design

##### Endpoint 1: POST /api/v1/auth/riot

**Request**:
```json
{
  "code": "authorization_code_from_riot"
}
```

**Response**:
```json
{
  "token": "jwt_token",
  "user": {
    "id": "user_id",
    "username": "username",
    "riotId": "riot_id"
  }
}
```

**Status Codes**:
- 200: Success
- 400: Bad Request
- 401: Unauthorized
- 500: Internal Server Error

##### Endpoint 2: GET /api/v1/teams

**Response**:
```json
{
  "teams": [
    {
      "id": "team_id",
      "name": "Team Alpha",
      "description": "Competitive team for tournaments",
      "createdAt": "2025-07-15T10:00:00Z",
      "updatedAt": "2025-07-30T15:30:00Z",
      "playerCount": 5,
      "lastDraftDate": "2025-07-30T20:00:00Z"
    }
  ]
}
```

**Status Codes**:
- 200: Success
- 401: Unauthorized
- 500: Internal Server Error

##### Endpoint 3: POST /api/v1/teams

**Request**:
```json
{
  "name": "Team Alpha",
  "description": "Competitive team for tournaments"
}
```

**Response**:
```json
{
  "id": "team_id",
  "name": "Team Alpha",
  "description": "Competitive team for tournaments",
  "createdAt": "2025-08-02T10:00:00Z",
  "updatedAt": "2025-08-02T10:00:00Z",
  "ownerId": "user_id"
}
```

**Status Codes**:
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 500: Internal Server Error

##### Endpoint 4: GET /api/v1/teams/{teamId}

**Response**:
```json
{
  "id": "team_id",
  "name": "Team Alpha",
  "description": "Competitive team for tournaments",
  "createdAt": "2025-07-15T10:00:00Z",
  "updatedAt": "2025-07-30T15:30:00Z",
  "ownerId": "user_id",
  "players": [
    {
      "id": "player_id",
      "summonerName": "SummonerOne",
      "riotId": "riot_id",
      "position": "TOP",
      "topChampions": [
        {
          "championId": "champion_id",
          "name": "Darius",
          "preference": 1
        },
        {
          "championId": "champion_id",
          "name": "Garen",
          "preference": 2
        },
        {
          "championId": "champion_id",
          "name": "Sett",
          "preference": 3
        }
      ]
    }
  ]
}
```

**Status Codes**:
- 200: Success
- 401: Unauthorized
- 404: Not Found
- 500: Internal Server Error

##### Endpoint 5: POST /api/v1/teams/{teamId}/players

**Request**:
```json
{
  "summonerName": "SummonerOne",
  "position": "TOP"
}
```

**Response**:
```json
{
  "id": "player_id",
  "summonerName": "SummonerOne",
  "riotId": "riot_id",
  "position": "TOP",
  "teamId": "team_id"
}
```

**Status Codes**:
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 404: Not Found
- 500: Internal Server Error

##### Endpoint 6: GET /api/v1/players/{playerId}/champion-preferences

**Response**:
```json
{
  "playerId": "player_id",
  "summonerName": "SummonerOne",
  "position": "TOP",
  "lastAnalyzedAt": "2025-08-02T10:00:00Z",
  "championPreferences": {
    "TOP": [
      {
        "championId": "champion_id",
        "name": "Darius",
        "imageUrl": "url_to_image",
        "preference": 1,
        "winRate": 0.65,
        "kda": 3.2,
        "gamesPlayed": 25
      },
      {
        "championId": "champion_id",
        "name": "Garen",
        "imageUrl": "url_to_image",
        "preference": 2,
        "winRate": 0.58,
        "kda": 2.8,
        "gamesPlayed": 18
      },
      {
        "championId": "champion_id",
        "name": "Sett",
        "imageUrl": "url_to_image",
        "preference": 3,
        "winRate": 0.52,
        "kda": 2.5,
        "gamesPlayed": 15
      }
    ]
  }
}
```

**Status Codes**:
- 200: Success
- 401: Unauthorized
- 404: Not Found
- 500: Internal Server Error

##### Endpoint 7: POST /api/v1/players/{playerId}/analyze

**Description**: Triggers an immediate analysis of the player's ranked match history to update champion preferences.

**Response**:
```json
{
  "status": "ANALYSIS_STARTED",
  "message": "Match history analysis has been initiated",
  "estimatedCompletionTime": "2025-08-02T10:05:00Z"
}
```

**Status Codes**:
- 202: Accepted
- 400: Bad Request
- 401: Unauthorized
- 404: Not Found
- 429: Too Many Requests (if Riot API rate limits are reached)
- 500: Internal Server Error

##### Endpoint 8: POST /api/v1/drafts/team

**Request**:
```json
{
  "name": "Team Alpha vs Team Beta",
  "draftMode": "TOURNAMENT",
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
  }
}
```

**Response**:
```json
{
  "id": "draft_id",
  "name": "Team Alpha vs Team Beta",
  "status": "CREATED",
  "draftMode": "TOURNAMENT",
  "createdAt": "2025-08-02T15:00:00Z",
  "blueSideTeam": {
    "id": "team_id",
    "name": "Team Alpha"
  },
  "redSideTeam": {
    "id": "team_id",
    "name": "Team Beta"
  },
  "blueSideCaptain": "user_id",
  "redSideCaptain": "user_id",
  "spectateUrl": "https://draftolio.com/draft/spectate/abc123"
}
```

**Status Codes**:
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 404: Not Found
- 500: Internal Server Error

#### 3.2.4 WebSocket Events

##### Event 1: player_info_request

**Payload**:
```json
{
  "draftId": "draft_id",
  "playerId": "player_id"
}
```

**Direction**: Client to Server

**Description**: Sent when a captain requests information about a player during the draft.

##### Event 2: player_info_response

**Payload**:
```json
{
  "draftId": "draft_id",
  "playerId": "player_id",
  "summonerName": "SummonerOne",
  "position": "TOP",
  "topChampions": [
    {
      "championId": "champion_id",
      "name": "Darius",
      "imageUrl": "url_to_image"
    },
    {
      "championId": "champion_id",
      "name": "Garen",
      "imageUrl": "url_to_image"
    },
    {
      "championId": "champion_id",
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
Team Draft Creation:

User -> Frontend: Create Team Draft
Frontend -> API: POST /api/v1/drafts/team
API -> TeamService: Validate Teams and Players
TeamService -> API: Teams and Players Valid
API -> DraftService: Create Draft
DraftService -> Database: Save Draft
Database -> DraftService: Draft Saved
DraftService -> API: Draft Created
API -> Frontend: Draft Created Response
Frontend -> WebSocketService: Connect to Draft Room
WebSocketService -> Frontend: Draft State
Frontend -> User: Show Draft Board
```

### 3.3 Security Considerations

- **Authentication**: All team management features require authentication via Riot Sign-On (RSO). For detailed authentication requirements and implementation,
  see [Riot Sign-On Integration Specification](./riot-sign-on-integration-spec.md).
- **Authorization**: Only team owners can edit team details and add/remove players.
- **Data Protection**: Player information is protected and only shared with team members and during drafts.
- **API Rate Limiting**: Implement rate limiting for Riot API calls to prevent abuse.
- **Input Validation**: All user inputs are validated to prevent injection attacks.

### 3.4 Performance Considerations

- **Caching**: Cache team and player data to reduce database load.
- **Lazy Loading**: Load player champion preferences only when needed during drafts.
- **Pagination**: Implement pagination for team and player lists to handle large datasets.
- **Optimized Queries**: Use optimized database queries for team and player data.
- **WebSocket Optimization**: Minimize payload size for real-time updates.
- **Riot API Caching**: Cache Riot API responses to reduce external API calls.

## 4. Implementation Plan

### 4.1 Dependencies

| Dependency | Description | Status |
|------------|-------------|--------|
| Riot API | External API for player data and authentication | Available |
| WebSocket Infrastructure | Real-time communication for draft updates | Available |
| User Authentication Service | Existing service for user authentication | Available |
| Draft Service | Existing service for draft management | Available |

### 4.2 Tasks

#### Backend Tasks

| Task ID | Description                                                                                            | Estimated Effort | Dependencies      |
|---------|--------------------------------------------------------------------------------------------------------|------------------|-------------------|
| BE-1    | Implement Riot Sign-On authentication (see [RSO Integration Spec](./riot-sign-on-integration-spec.md)) | 3 days           | None              |
| BE-2    | Create Team and Player data models                                                                     | 2 days           | None              |
| BE-3    | Implement Team CRUD operations                                                                         | 3 days           | BE-2              |
| BE-4    | Implement Player CRUD operations                                                                       | 2 days           | BE-2              |
| BE-5    | Create Match Analysis Service architecture                                                             | 3 days           | BE-2, BE-4        |
| BE-6    | Implement Riot API integration for match history                                                       | 4 days           | BE-1, BE-5        |
| BE-7    | Develop champion performance calculation algorithms                                                    | 3 days           | BE-5, BE-6        |
| BE-8    | Implement automatic champion preference ranking                                                        | 2 days           | BE-7              |
| BE-9    | Create scheduled job for periodic preference updates                                                   | 2 days           | BE-8              |
| BE-10   | Implement caching strategy for Riot API data                                                           | 2 days           | BE-6              |
| BE-11   | Modify Draft service for team-based drafts                                                             | 3 days           | BE-2, BE-3        |
| BE-12   | Implement player information WebSocket events                                                          | 2 days           | BE-4, BE-8, BE-11 |
| BE-13   | Create API endpoints for champion preferences                                                          | 2 days           | BE-8              |
| BE-14   | Implement manual trigger for match history analysis                                                    | 1 day            | BE-7, BE-8        |

#### Frontend Tasks

| Task ID | Description                                                                                | Estimated Effort | Dependencies           |
|---------|--------------------------------------------------------------------------------------------|------------------|------------------------|
| FE-1    | Implement Riot Sign-On UI (see [RSO Integration Spec](./riot-sign-on-integration-spec.md)) | 2 days           | BE-1                   |
| FE-2    | Create Team management UI                                                                  | 3 days           | BE-3                   |
| FE-3    | Create Player management UI                                                                | 3 days           | BE-4                   |
| FE-4    | Implement Champion performance display UI                                                  | 3 days           | BE-8, BE-13            |
| FE-5    | Create visualization components for win rates and performance metrics                      | 2 days           | FE-4                   |
| FE-6    | Implement manual analysis trigger button                                                   | 1 day            | BE-14                  |
| FE-7    | Create Team-based draft setup UI                                                           | 3 days           | BE-11                  |
| FE-8    | Implement Player information display in draft UI                                           | 2 days           | BE-12                  |
| FE-9    | Create loading states for champion preference calculation                                  | 1 day            | FE-4, FE-6             |
| FE-10   | Implement error handling for Riot API limitations                                          | 1 day            | FE-4, FE-6             |
| FE-11   | Create responsive layouts for all new components                                           | 2 days           | FE-2, FE-3, FE-7, FE-8 |

#### Data Tasks

| Task ID | Description | Estimated Effort | Dependencies |
|---------|-------------|------------------|--------------|
| DATA-1 | Create database migrations for new entities | 1 day | None |
| DATA-2 | Implement data access layer for Team and Player | 2 days | DATA-1 |
| DATA-3 | Create database schema for match history and performance data | 2 days | DATA-1 |
| DATA-4 | Implement repositories for PlayerMatchHistory and ChampionPerformance | 2 days | DATA-3 |
| DATA-5 | Create indexes for performance optimization | 1 day | DATA-3, DATA-4 |
| DATA-6 | Implement caching for team and player data | 2 days | DATA-2 |
| DATA-7 | Develop data retention policy for match history | 1 day | DATA-3 |
| DATA-8 | Implement batch processing for match history analysis | 2 days | DATA-4 |
| DATA-9 | Create data aggregation queries for performance metrics | 2 days | DATA-4 |
| DATA-10 | Implement data synchronization with Riot API | 3 days | DATA-4 |

### 4.3 Timeline

| Phase | Start Date | End Date | Deliverables |
|-------|------------|----------|--------------|
| Design | 2025-08-05 | 2025-08-09 | Detailed technical design, UI mockups |
| Development | 2025-08-12 | 2025-08-30 | Implemented backend and frontend features |
| Testing | 2025-09-02 | 2025-09-09 | Tested features, fixed bugs |
| Deployment | 2025-09-12 | 2025-09-13 | Deployed features to production |

### 4.4 Risks and Mitigations

| Risk | Impact | Likelihood | Mitigation |
|------|--------|------------|------------|
| Riot API changes | High | Medium | Monitor Riot API announcements, implement adapter pattern for API integration |
| Performance issues with large teams | Medium | Medium | Implement pagination, caching, and optimized queries |
| User adoption challenges | Medium | Low | Provide clear documentation and tutorials, gather user feedback early |
| Security vulnerabilities in authentication | High | Low | Conduct security review, follow OAuth best practices, implement proper token handling |

## 5. Testing Strategy

### 5.1 Test Scenarios

| Scenario ID | Description                                                                                              | Test Type        | Priority |
|-------------|----------------------------------------------------------------------------------------------------------|------------------|----------|
| TS-1        | Verify Riot Sign-On authentication flow (see [RSO Integration Spec](./riot-sign-on-integration-spec.md)) | Integration      | High     |
| TS-2        | Test team creation and management                                                                        | Unit/Integration | High     |
| TS-3        | Test player addition and position assignment                                                             | Unit/Integration | High     |
| TS-4        | Verify Riot API integration for match history                                                            | Integration      | High     |
| TS-5        | Test champion performance calculation algorithms                                                         | Unit             | High     |
| TS-6        | Verify automatic champion preference ranking                                                             | Unit/Integration | High     |
| TS-7        | Test scheduled updates of champion preferences                                                           | Integration      | Medium   |
| TS-8        | Verify caching of Riot API data                                                                          | Unit/Integration | Medium   |
| TS-9        | Test manual trigger for match history analysis                                                           | Integration      | Medium   |
| TS-10       | Verify performance metrics visualization                                                                 | Unit             | Medium   |
| TS-11       | Test handling of players with limited match history                                                      | Unit/Integration | Medium   |
| TS-12       | Verify handling of unavailable champions in preferences                                                  | Unit             | Medium   |
| TS-13       | Test team-based draft creation                                                                           | Integration      | High     |
| TS-14       | Verify player information display during draft                                                           | E2E              | High     |
| TS-15       | Test proper authorization for team management                                                            | Unit/Integration | High     |
| TS-16       | Verify error handling for Riot API rate limits                                                           | Integration      | High     |
| TS-17       | Test data retention and cleanup for match history                                                        | Integration      | Medium   |

### 5.2 Test Data Requirements

| Data Type | Description | Source |
|-----------|-------------|--------|
| User Accounts | Test user accounts with Riot IDs | Test environment |
| Teams | Sample teams with various configurations | Generated test data |
| Players | Sample players with positions | Generated test data |
| Champions | Complete list of League of Legends champions | Riot API |
| Match History | Sample ranked match history for players | Mock Riot API / Generated test data |
| Performance Metrics | Sample performance data (win rates, KDA, etc.) | Generated test data |
| Ranked Tiers | Sample ranked tier data for players | Mock Riot API |
| Champion Mastery | Sample champion mastery data for players | Mock Riot API |
| API Rate Limits | Simulated Riot API rate limit scenarios | Test environment |
| Edge Cases | Players with limited/no match history, players with diverse champion pools, etc. | Generated test data |

### 5.3 Performance Testing

| Test Type | Description | Success Criteria |
|-----------|-------------|------------------|
| Load Testing | Test system with many teams and players | System handles 1000 concurrent users with response times under 500ms |
| Stress Testing | Test system under heavy load during drafts | System maintains stability with 100 concurrent drafts |
| Endurance Testing | Test system over extended period | System maintains performance over 24-hour period |
| Riot API Integration | Test performance of Riot API integration | Match history data retrieval completes within 3 seconds per player |
| Batch Processing | Test performance of batch processing for match analysis | System can analyze 1000 matches in under 5 minutes |
| Caching Efficiency | Test effectiveness of caching strategy | 90% cache hit rate for frequently accessed data |
| Champion Preference Calculation | Test performance of preference calculation algorithms | Calculation completes within 2 seconds per player |
| Concurrent Analysis | Test system handling multiple analysis requests | System can handle 50 concurrent analysis requests |
| Database Query Performance | Test performance of data aggregation queries | Queries complete in under 200ms |
| Rate Limit Handling | Test system behavior under API rate limit constraints | System gracefully handles rate limits without errors |

### 5.4 Real-time Testing

| Test Type | Description | Success Criteria |
|-----------|-------------|------------------|
| WebSocket Performance | Test real-time updates during drafts | Updates delivered within 100ms |
| Connection Resilience | Test reconnection handling | Clients reconnect automatically after disconnection |
| Concurrent Updates | Test multiple simultaneous updates | All clients receive updates in correct order |
| Champion Preference Updates | Test real-time updates of champion preferences when champions are banned/picked | Preferences update within 100ms after a champion is banned/picked |
| Player Information Requests | Test performance of player information requests during draft | Player information with champion preferences delivered within 200ms |
| Draft State Synchronization | Test synchronization of draft state with champion preferences | All clients see the same champion preferences for each player |
| Multiple Team Drafts | Test system handling multiple team drafts simultaneously | System maintains real-time performance across 20 concurrent team drafts |
| WebSocket Payload Size | Test size of WebSocket payloads containing champion preference data | Payload size remains under 10KB for optimal performance |
| Preference Calculation Timing | Test timing of preference calculations relative to draft events | Calculations do not block or delay draft progress |

## 6. Rollout Plan

### 6.1 Deployment Strategy

- **Phased Rollout**: 
  1. Deploy to staging environment for internal testing
  2. Release to beta users for feedback
  3. Gradual rollout to all users

- **Feature Flags**: Implement feature flags to enable/disable team management features

- **Canary Deployment**: Deploy to a subset of servers first to monitor performance and issues

- **Rollback Plan**: Maintain ability to disable team management features if critical issues are discovered

### 6.2 Monitoring and Alerting

| Metric | Description | Threshold | Alert |
|--------|-------------|-----------|-------|
| API Response Time | Time to respond to team management API requests | > 500ms | Warning alert to development team |
| Error Rate | Percentage of failed requests | > 1% | Critical alert to on-call engineer |
| Riot API Usage | Number of Riot API calls | > 80% of rate limit | Warning alert to development team |
| Riot API Response Time | Time for Riot API to respond to requests | > 2s | Warning alert to development team |
| Riot API Error Rate | Percentage of failed Riot API requests | > 5% | Critical alert to on-call engineer |
| Match Analysis Processing Time | Time to analyze match history for a player | > 10s | Warning alert to development team |
| Champion Preference Calculation Time | Time to calculate champion preferences | > 5s | Warning alert to development team |
| Match Analysis Queue Size | Number of pending match analysis jobs | > 100 | Scaling alert to operations team |
| Match Analysis Success Rate | Percentage of successful match analyses | < 95% | Warning alert to development team |
| Cache Hit Rate | Percentage of cache hits for champion preferences | < 80% | Warning alert to development team |
| WebSocket Connection Count | Number of active WebSocket connections | > 5000 | Scaling alert to operations team |
| Preference Update Latency | Time to update preferences after ban/pick | > 200ms | Warning alert to development team |
| Database Query Time | Time for champion preference queries | > 100ms | Warning alert to development team |

### 6.3 Documentation and Training

| Item | Audience | Description | Owner |
|------|----------|-------------|-------|
| User Guide | End Users | Documentation on how to use team management features, including explanation of automatic champion preference calculation | Documentation Team |
| Champion Preference Guide | Team Captains | Guide explaining how champion preferences are calculated and how to interpret performance metrics | Documentation Team |
| API Documentation | Developers | Technical documentation for team management APIs, including champion preference endpoints | Development Team |
| Riot API Integration Guide | Developers | Technical documentation on how the system integrates with Riot API for match history and performance data | Development Team |
| Admin Guide | Administrators | Guide for managing and troubleshooting team features, including Riot API rate limiting and match analysis service | Operations Team |
| Performance Metrics Guide | Team Captains | Guide explaining the performance metrics used in champion preference calculation | Documentation Team |
| Match Analysis FAQ | End Users | Frequently asked questions about match history analysis and champion preference calculation | Support Team |
| Feature Announcement | All Users | Blog post and email announcing team management features with automatic champion preference calculation | Marketing Team |
| Troubleshooting Guide | Support Team | Guide for diagnosing and resolving issues with match history analysis and champion preferences | Operations Team |

## 7. Success Metrics

### 7.1 Key Performance Indicators

| KPI | Description | Target | Measurement Method |
|-----|-------------|--------|-------------------|
| Team Creation Rate | Number of teams created per week | 500+ | Database metrics |
| User Adoption | Percentage of users creating or joining teams | 30%+ | User analytics |
| Draft Completion Rate | Percentage of team drafts that complete successfully | 95%+ | Application metrics |
| User Retention | Increase in user retention after feature launch | 15%+ | User analytics |
| Champion Preference Accuracy | Correlation between calculated preferences and actual player performance | 80%+ | Statistical analysis |
| Match Analysis Success Rate | Percentage of players with successfully analyzed match history | 95%+ | Application metrics |
| Champion Preference Usage | Percentage of drafts where captains view player champion preferences | 70%+ | User analytics |
| Analysis Completion Time | Average time to complete match history analysis for a player | < 5s | Application metrics |
| Preference Update Rate | Frequency of champion preference updates per player | Weekly+ | Database metrics |
| API Rate Limit Utilization | Percentage of available Riot API rate limit used | < 70% | Application metrics |
| Cache Efficiency | Percentage of champion preference requests served from cache | 90%+ | Application metrics |

### 7.2 User Feedback

| Feedback Type | Collection Method | Success Criteria |
|---------------|-------------------|------------------|
| Feature Satisfaction | In-app survey | 80%+ satisfaction rating |
| Usability | User testing sessions | 90%+ task completion rate |
| Feature Requests | Feedback form | Collect actionable feedback for future improvements |
| Bug Reports | In-app reporting tool | < 5 critical bugs reported in first month |
| Champion Preference Accuracy | Post-draft survey | 75%+ of captains report preferences matched expectations |
| Performance Metrics Usefulness | Targeted survey | 70%+ of users find performance metrics helpful |
| Match History Analysis | User interviews | 80%+ of users understand how preferences are calculated |
| Preference Update Frequency | In-app feedback | 70%+ of users satisfied with update frequency |
| Preference Visualization | Usability testing | 85%+ of users can correctly interpret preference visualizations |
| Comparison to Manual Selection | Comparative survey | 80%+ of users prefer automatic calculation over manual selection |
| Edge Case Handling | Support ticket analysis | < 10 tickets per month about edge cases (limited match history, etc.) |

## 8. Appendix

### 8.1 References

- [Riot Games API Documentation](https://developer.riotgames.com/docs/portal)
- [Riot Games Match-V5 API](https://developer.riotgames.com/apis#match-v5)
- [Riot Games Summoner-V4 API](https://developer.riotgames.com/apis#summoner-v4)
- [Riot Games Champion-Mastery-V4 API](https://developer.riotgames.com/apis#champion-mastery-v4)
- [Riot Games League-V4 API](https://developer.riotgames.com/apis#league-v4)
- [OAuth 2.0 Best Practices](https://oauth.net/2/best-practices/)
- [League of Legends Positions and Roles](https://leagueoflegends.fandom.com/wiki/Champion_positions)
- [Performance Metrics in Competitive Gaming](https://www.researchgate.net/publication/344124588_Performance_Metrics_in_Competitive_Gaming)
- [Caching Strategies for API Rate Limiting](https://www.moesif.com/blog/technical/api-design/Caching-Strategies-For-API-Rate-Limiting/)
- [Batch Processing Best Practices](https://docs.spring.io/spring-batch/docs/current/reference/html/index.html)
- [Real-time Data Processing Patterns](https://www.oreilly.com/library/view/streaming-systems/9781491983867/)

### 8.2 Glossary

| Term | Definition |
|------|------------|
| RSO | Riot Sign-On, the authentication system used by Riot Games |
| Summoner | A player account in League of Legends |
| Riot ID | Unique identifier for a Riot Games account |
| Position | Specific role in League of Legends (Top, Jungle, Middle, Bottom, Support) |
| Champion | Playable character in League of Legends |
| Match History | Record of a player's past games in League of Legends |
| Ranked Match | Competitive game mode in League of Legends that affects a player's ranking |
| KDA | Kills, Deaths, and Assists ratio, a performance metric in League of Legends |
| Win Rate | Percentage of games won with a specific champion |
| Champion Mastery | Riot Games' system for tracking player experience with specific champions |
| CS | Creep Score, the number of minions and monsters killed by a player |
| CS/min | Creep Score per minute, a performance metric for farming efficiency |
| Performance Metrics | Quantitative measurements of player performance (win rate, KDA, CS/min, etc.) |
| Champion Pool | Set of champions that a player is proficient with |
| Champion Preference | Ranking of champions based on player performance and frequency of play |
| Match Analysis | Process of analyzing match data to extract performance metrics |
| API Rate Limit | Restriction on the number of API requests that can be made in a given time period |
| Batch Processing | Method of processing data in groups rather than individually |
| Cache | Temporary storage of frequently accessed data to improve performance |

### 8.3 Change Log

| Date | Author | Description |
|------|--------|-------------|
| 2025-08-02 | Draftolio Team | Initial draft |
| 2025-08-02 | Draftolio Team | Updated to include automatic calculation of champion preferences based on ranked match history and performance |
