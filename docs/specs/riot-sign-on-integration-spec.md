# Feature Specification: Riot Sign-On (RSO) Integration

> **Status**: Draft
>
> **Author**: Draftolio Team
>
> **Created Date**: 2025-08-03
>
> **Last Updated**: 2025-08-03
>
> **Epic/Initiative**: Authentication and Riot Integration

## 1. Overview

### 1.1 Summary

The Riot Sign-On (RSO) Integration provides secure authentication for Draftolio AI users through Riot Games' official authentication service. This integration
enables users to authenticate using their Riot Games credentials, providing access to their League of Legends player data and ensuring secure access to team
management and drafting features.

### 1.2 Business Objectives

- Provide secure and trusted authentication through Riot Games' official system
- Enable access to authenticated user's League of Legends data
- Streamline user onboarding by leveraging existing Riot accounts
- Ensure compliance with Riot Games' authentication standards
- Reduce friction in user authentication process

### 1.3 User Value Proposition

RSO Integration provides users with a seamless authentication experience using their existing Riot Games credentials. Users can securely access Draftolio AI
features without creating additional accounts, while ensuring their League of Legends data is accessed through official channels with proper authorization.

### 1.4 Scope

- **In Scope**:
    - OAuth 2.0 Authorization Code Flow implementation
    - RSO client registration and configuration
    - Access token, ID token, and refresh token management
    - UserInfo endpoint integration for player data
    - Token refresh mechanism
    - Secure token storage and validation
    - HTTPS enforcement for production

- **Out of Scope**:
    - Custom authentication system
    - Social media authentication providers
    - Multi-factor authentication (handled by RSO)
    - Password reset functionality (handled by RSO)

- **Future Considerations**:
    - Advanced token caching strategies
    - Token revocation handling
    - Multiple region support optimization
    - Enhanced error handling and user feedback

## 2. User Stories and Requirements

### 2.1 User Personas

| Persona                  | Description                                                     | Goals                                                                          | Pain Points                                                                    |
|--------------------------|-----------------------------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| League of Legends Player | Active player who wants to access Draftolio AI features         | Access drafting tools using existing Riot account, avoid creating new accounts | Having to remember multiple login credentials, concerns about account security |
| Team Captain             | Leader who manages team drafts and needs secure access          | Securely authenticate to manage team data, ensure team member authentication   | Difficulty verifying team member identities, managing access permissions       |
| Casual User              | Occasional user of drafting tools                               | Quick and easy access to basic features                                        | Complex registration processes, forgotten passwords                            |
| Tournament Organizer     | Manages competitive events requiring verified player identities | Verify participant identities through official Riot accounts                   | Manual verification processes, fake or duplicate accounts                      |

### 2.2 User Stories

#### US-1: User Authentication with Riot Sign-On

As a League of Legends player, I want to sign in using my Riot account, so that I can access Draftolio AI features without creating a separate account and
ensure my identity is verified.

**Priority**: Must Have

**Acceptance Criteria**:

1. Given I am on the login page, when I click "Sign in with Riot", then I am redirected to the official Riot Sign-On page.
2. Given I am on the Riot Sign-On page, when I enter my Riot credentials and authenticate, then I am redirected back to Draftolio AI.
3. Given I have successfully authenticated with Riot, when I am redirected back to the application, then I am logged in and can access all authenticated
   features.
4. Given I am logged in, when I view my profile, then I can see my Riot account information including summoner name and region.
5. Given I am logged in, when my session expires, then I am prompted to re-authenticate through Riot Sign-On.
6. Given I want to log out, when I click the logout button, then my session is terminated and I am redirected to the login page.

#### US-2: Automatic Token Refresh

As a user, I want my authentication session to be automatically renewed when possible, so that I don't get interrupted while using the application.

**Priority**: Must Have

**Acceptance Criteria**:

1. Given I am logged in and my access token is about to expire, when the system detects the impending expiration, then it automatically attempts to refresh the
   token using my refresh token.
2. Given the token refresh is successful, when I continue using the application, then I remain logged in without interruption.
3. Given the token refresh fails, when the system cannot obtain a new access token, then I am prompted to re-authenticate.
4. Given I have been inactive for an extended period, when my refresh token expires, then I am logged out and must sign in again.
5. Given the automatic refresh is in progress, when I make API requests, then the system queues them until the new token is available.

#### US-3: Secure Session Management

As a user, I want my authentication session to be secure and properly managed, so that my account remains protected from unauthorized access.

**Priority**: Must Have

**Acceptance Criteria**:

1. Given I log in successfully, when my session is created, then my tokens are stored securely and not exposed to client-side JavaScript.
2. Given I am using the application, when I navigate between pages, then my authentication state is maintained without requiring re-login.
3. Given I close my browser, when I return to the application within the session timeout period, then I remain logged in.
4. Given I am logged in on multiple devices, when I log out from one device, then my session on that device is terminated but other devices remain logged in.
5. Given there is suspicious activity detected, when the system identifies potential security issues, then my session is terminated and I am required to
   re-authenticate.

#### US-4: Authentication Error Handling

As a user, I want to receive clear feedback when authentication issues occur, so that I understand what went wrong and how to resolve it.

**Priority**: Must Have

**Acceptance Criteria**:

1. Given I attempt to sign in and the Riot Sign-On service is unavailable, when the authentication fails, then I see a clear error message explaining the
   service is temporarily unavailable.
2. Given I deny authorization on the Riot Sign-On page, when I am redirected back to the application, then I see a message explaining that authorization was
   denied and I can try again.
3. Given my authentication session expires while using the application, when I try to access a protected feature, then I am redirected to login with a message
   explaining that my session has expired.
4. Given there is a network error during authentication, when the process fails, then I see an appropriate error message and can retry the authentication.
5. Given I encounter any authentication error, when I view the error message, then I have clear options to retry or get help.

#### US-5: User Profile Information Access

As a user, I want to view my Riot account information within the application, so that I can verify my identity and see my account details.

**Priority**: Should Have

**Acceptance Criteria**:

1. Given I am logged in, when I navigate to my profile page, then I can see my Riot account information including summoner name and region.
2. Given I have multiple accounts across different regions, when I view my profile, then I can see my current region (CPID) information.
3. Given my Riot account information changes, when I refresh my profile or log in again, then the updated information is displayed.
4. Given I want to verify my identity, when I view my profile, then I can see confirmation that I am authenticated through Riot Sign-On.
5. Given I am concerned about privacy, when I view what information is shared, then I can see exactly what data is accessed from my Riot account.

### 2.3 Non-Functional Requirements

| Requirement Type | Description                                          | Acceptance Criteria                                                                            |
|------------------|------------------------------------------------------|------------------------------------------------------------------------------------------------|
| Security         | All authentication must use secure protocols         | All communication uses HTTPS, tokens are stored securely, CSRF protection is implemented       |
| Performance      | Authentication flow must be fast and responsive      | Login redirect completes in under 2 seconds, token refresh completes in under 1 second         |
| Reliability      | Authentication service must be highly available      | 99.9% uptime for authentication endpoints, graceful handling of RSO service outages            |
| Usability        | Authentication process must be intuitive             | Users can complete login without documentation, clear error messages for all failure scenarios |
| Compliance       | Must comply with Riot Games authentication standards | Follows OAuth 2.0 best practices, implements all required RSO security measures                |
| Scalability      | Must support concurrent user authentication          | System can handle 1000+ concurrent login attempts without degradation                          |

## 3. Technical Requirements

### 3.1 RSO Endpoints

The following Riot Sign-On endpoints will be integrated:

| Endpoint      | URL                                    | Purpose                              |
|---------------|----------------------------------------|--------------------------------------|
| Authorization | `https://auth.riotgames.com/authorize` | Obtain authorization code            |
| Token         | `https://auth.riotgames.com/token`     | Exchange codes for tokens            |
| JWKS          | `https://auth.riotgames.com/jwks.json` | JSON Web Keys for token verification |
| UserInfo      | `https://auth.riotgames.com/userinfo`  | Retrieve user information            |

### 3.2 Client Registration Requirements

The following details are required for RSO client registration:

#### Mandatory Fields

- **Client Name**: Draftolio AI
- **Logo URI**: 60px by 60px logo image
- **Privacy Policy URI**: Link to privacy policy
- **Terms of Service URI**: Link to terms of service
- **Grant Types**: `["authorization_code", "refresh_token"]`
- **Redirect URIs**: Application callback URLs
- **Post Logout Redirect URIs**: URLs for post-logout redirection
- **Token Endpoint Auth Method**: `client_secret_basic` or `client_secret_jwt`

#### Localization Support

Riot supports the following locales for client registration fields:
`cs_CZ`, `de_DE`, `el_GR`, `en_AU`, `en_GB`, `en_PL`, `en_US`, `es_AR`, `es_ES`, `es_MX`, `fr_FR`, `hu_HU`, `it_IT`, `ja_JP`, `pl_PL`, `pt_BR`, `ro_RO`,
`ru_RU`, `tr_TR`

### 3.3 Authorization Code Flow

#### 3.3.1 Authorization Request Parameters

**Mandatory Parameters:**

- `redirect_uri`: OAuth 2 callback route (must be registered)
- `client_id`: Client ID assigned during registration
- `response_type`: Must be `code` for authorization code flow
- `scope`: Must include `openid` for authentication

**Additional Scopes:**

- `cpid`: Returns game region for League of Legends
- `offline_access`: Allows refresh tokens for `/userinfo` endpoint access

**Optional Parameters:**

- `login_hint`: Pre-populate login data
    - `{regioncode}`: e.g., `na1`
    - `{regioncode}|{username}`: e.g., `na1|summoner`
    - `{regioncode}#{userid}`: e.g., `na1#12345`
- `ui_locales`: Space-separated BCP47 language tags
- `state`: CSRF protection token

#### 3.3.2 Authorization URL Format

```
https://auth.riotgames.com/authorize?redirect_uri={CALLBACK_URL}&client_id={CLIENT_ID}&response_type=code&scope=openid cpid offline_access&state={STATE_TOKEN}
```

### 2.4 Token Exchange

#### 2.4.1 Token Request

**Headers:**

```
Authorization: Basic {BASE64_ENCODE(client_id:client_secret)}
Content-Type: application/x-www-form-urlencoded
```

**Form Data:**

- `grant_type`: `authorization_code`
- `code`: Authorization code from callback
- `redirect_uri`: Same redirect URI used in authorization request

#### 2.4.2 Token Response

```json
{
    "scope": "openid cpid offline_access",
    "expires_in": 600,
    "token_type": "Bearer",
    "refresh_token": "dXJuOnJpb3Q6cOk1qdNal...8zN3NzbQ.xw96rZeGEMtrFlDCGLyA",
    "id_token": "eyJhbGciJSUzI1mtpZCInMxIn0...YiI6InVybjpyaW90OpZDp2MTpNalV",
    "sub_sid": "vldfsXGdDPoafSKfjS932cslKu8JDUKZ-woZvXDoq8",
    "access_token": "eyJhbGciOi1NsImZCI6InM...NTkzMTA3LCJjaWQiJnmE-BVnZbYqY"
}
```

**Response Fields:**

- `scope`: Level of access provided by the access token
- `expires_in`: Token lifespan in seconds
- `token_type`: Authorization method (Bearer)
- `refresh_token`: Token for obtaining new access tokens
- `id_token`: JWT containing user identity information
- `sub_sid`: Session identifier for the subject
- `access_token`: Encrypted JWT for API access

### 2.5 Token Types and Usage

#### 2.5.1 Access Token

- **Format**: Encrypted JWT (cannot be decoded)
- **Usage**: API authentication with Bearer authorization
- **Lifespan**: 600 seconds (10 minutes)
- **Refreshable**: Yes, using refresh token

#### 2.5.2 ID Token

- **Format**: Signed JWT (can be decoded and verified)
- **Usage**: User identity verification
- **Contains**: User identity claims
- **Verification**: Required using JWKS endpoint

#### 2.5.3 Refresh Token

- **Format**: Self-contained signed JWT
- **Usage**: Obtain new access tokens
- **Lifespan**: Extended (weeks to months)
- **Validation**: Can be inspected and validated locally

### 2.6 Refresh Token Usage

#### 2.6.1 Refresh Request

**Headers:**

```
Authorization: Basic {BASE64_ENCODE(client_id:client_secret)}
Content-Type: application/x-www-form-urlencoded
```

**Form Data:**

- `grant_type`: `refresh_token`
- `refresh_token`: Current refresh token
- `scope`: Same or narrower scope (optional)

#### 2.6.2 Refresh Response

```json
{
    "scope": "openid cpid offline_access",
    "expires_in": 600,
    "token_type": "Bearer",
    "refresh_token": "dXJuOnJpb3Q6cGlkOn...amNvaG8zN3NzbQeGEmeMtrFlDCGLyA",
    "access_token": "eyJhbGciOiJSUzI1NiI...sFwkadLmWmwtvJouhX22Tc6vPnfXTk"
}
```

**Note**: If a new refresh token is provided, the previous one becomes invalid and must be replaced.

### 2.7 UserInfo Endpoint

#### 2.7.1 UserInfo Request

**Headers:**

```
Authorization: Bearer {ACCESS_TOKEN}
```

**URL:**

```
https://auth.riotgames.com/userinfo
```

#### 2.7.2 UserInfo Response

```json
{
    "sub": "2SqiN9ZWecDMZdo-y3Xaaoos32kTazZQDgzOyxzJe66SzGYqIljuuxjmctK0-XbBIhzZn929LZnH5S90L4vNVjx7En27",
    "cpid": "NA1"
}
```

**Response Fields:**

- `sub`: Subject identifier (unique user ID)
- `cpid`: Game region for League of Legends

## 3. Implementation Requirements

### 3.1 Security Requirements

#### 3.1.1 HTTPS Enforcement

- All production services must use HTTPS
- HTTP connections must be redirected to HTTPS
- SSL/TLS certificates must be valid and up-to-date

#### 3.1.2 Token Security

- Access tokens must be stored securely (HTTP-only cookies or secure storage)
- Refresh tokens must be stored with additional security measures
- Tokens must not be exposed in client-side JavaScript
- Implement proper token expiration handling

#### 3.1.3 CSRF Protection

- Use `state` parameter for CSRF protection
- Validate state parameter on callback
- Generate cryptographically secure random state values

#### 3.1.4 Token Validation

- Verify ID token signatures using JWKS endpoint
- Validate token expiration times
- Verify token issuer and audience claims

### 3.2 Configuration Requirements

#### 3.2.1 Environment Variables

```
RSO_CLIENT_ID=your_client_id
RSO_CLIENT_SECRET=your_client_secret
RSO_REDIRECT_URI=https://your-domain.com/oauth2-callback
RSO_BASE_URL=https://auth.riotgames.com
```

#### 3.2.2 Application Configuration

- Configure allowed redirect URIs
- Set appropriate token storage mechanisms
- Configure session management
- Set up error handling and logging

### 3.3 Error Handling

#### 3.3.1 Authorization Errors

- Invalid client credentials
- Unauthorized redirect URI
- Invalid scope requests
- User denial of authorization

#### 3.3.2 Token Errors

- Expired authorization codes
- Invalid refresh tokens
- Network connectivity issues
- RSO service unavailability

#### 3.3.3 User Experience

- Provide clear error messages
- Implement retry mechanisms where appropriate
- Graceful degradation for service outages
- Proper logging for debugging

## 4. API Specifications

### 4.1 Authentication Endpoints

#### 4.1.1 Login Initiation

```
GET /auth/login
```

**Response**: Redirect to RSO authorization URL

#### 4.1.2 OAuth Callback

```
GET /oauth2-callback?code={code}&state={state}
```

**Parameters:**

- `code`: Authorization code from RSO
- `state`: CSRF protection token

**Response**: Process tokens and redirect to application

#### 4.1.3 Logout

```
POST /auth/logout
```

**Response**: Clear session and redirect to logout page

#### 4.1.4 Token Refresh

```
POST /auth/refresh
```

**Response**: New access token or error

### 4.2 User Information

#### 4.2.1 Current User

```
GET /api/user/me
```

**Headers**: `Authorization: Bearer {access_token}`
**Response**: User profile information

## 5. Testing Requirements

### 5.1 Integration Tests

#### 5.1.1 Authentication Flow Tests

- Test complete OAuth 2.0 flow
- Verify token exchange process
- Test callback handling
- Validate state parameter usage

#### 5.1.2 Token Management Tests

- Test token refresh mechanism
- Verify token expiration handling
- Test token validation
- Test secure token storage

#### 5.1.3 Error Handling Tests

- Test invalid authorization codes
- Test expired tokens
- Test network failures
- Test malformed responses

### 5.2 Security Tests

#### 5.2.1 CSRF Protection Tests

- Test state parameter validation
- Test missing state parameter
- Test invalid state parameter

#### 5.2.2 Token Security Tests

- Test token exposure prevention
- Test secure token transmission
- Test token storage security

## 6. Deployment Requirements

### 6.1 Environment Setup

#### 6.1.1 Development Environment

- Use `http://localhost:3000` for local development
- Configure `/etc/hosts` entry: `127.0.0.1 local.example.com`
- Register development redirect URI

#### 6.1.2 Production Environment

- Use HTTPS-only configuration
- Configure production redirect URIs
- Set up proper SSL certificates
- Configure secure session storage

### 6.2 Monitoring and Logging

#### 6.2.1 Authentication Metrics

- Track successful authentications
- Monitor failed authentication attempts
- Track token refresh rates
- Monitor RSO service availability

#### 6.2.2 Security Monitoring

- Log authentication failures
- Monitor suspicious activity
- Track token usage patterns
- Alert on security anomalies

## 7. Dependencies

### 7.1 External Dependencies

- Riot Sign-On service availability
- HTTPS certificate management
- Secure session storage solution

### 7.2 Internal Dependencies

- User management system
- Session management
- Error handling framework
- Logging infrastructure

## 8. Glossary

| Term         | Definition                                                                   |
|--------------|------------------------------------------------------------------------------|
| RSO          | Riot Sign-On, the authentication system used by Riot Games                   |
| JWT          | JSON Web Token, a compact token format for securely transmitting information |
| JWKS         | JSON Web Key Set, a set of keys containing public keys used to verify JWTs   |
| OAuth 2.0    | Authorization framework for secure API access                                |
| CSRF         | Cross-Site Request Forgery, a type of security vulnerability                 |
| Bearer Token | A security token with the property that any party in possession can use it   |
| CPID         | Client Platform ID, represents the game region for League of Legends         |
| Sub          | Subject, the unique identifier for the authenticated user                    |
| State        | A random value used to prevent CSRF attacks in OAuth flows                   |
