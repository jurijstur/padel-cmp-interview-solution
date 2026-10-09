# Interview Assessment Project

This repository is a Compose Multiplatform (CMP) interview assessment project designed to evaluate mobile developer skills across Android and iOS platforms.

## Project Overview

The app is a demonstration of a padel tournament browsing application built with:
- **Multiplatform Architecture** — Shared business logic via Kotlin Multiplatform
- **Layered Architecture** — Data, domain, and UI layers with clean separation
- **MVI State Management** — Flow-based state reduction with ViewModels
- **Compose UI** — Material 3 design system on both platforms

See [README.md](README.md) for project setup, features, and API configuration.

## Tasks

### Task 1: Fix Pull-to-Refresh Bug in Tournament Detail Screen

**Scenarios:**

1. **Tournament Detail Screen:**
   - Navigate to TournamentsScreen
   - Enable Aeroplane mode (to simulate network error)
   - Tap on any tournament
   - TournamentScreen opens and displays a network error
   - Attempt pull-to-refresh gesture — it does not work

2. **Match Detail Screen:**
   - Navigate to Matches screen
   - Enable Aeroplane mode (to simulate network error)
   - Tap on any match
   - MatchScreen opens and displays a network error
   - Attempt pull-to-refresh gesture — it does not work

**Screenshots:** See `docs/screenshots/task1-pull-refresh-error-tournament.png` and `docs/screenshots/task1-pull-refresh-error-match.png`

**Issue:** The pull-to-refresh gesture is unresponsive when a network error is displayed on the tournament or match detail screen.

**Expected Behavior:** Pull-to-refresh should remain functional even when network errors occur, allowing users to retry loading the data.

**Acceptance Criteria:**
- Pull-to-refresh gesture works when network error is shown
- User can retry loading tournament data via pull-to-refresh
- The same fix applies to both TournamentScreen and MatchScreen

---

### Task 2: Improve Set Score Display Design on Matches and Match Screens

**Current Issue:** 
Set scores are displayed inline with team names on the Matches list screen, causing misalignment when scores contain tiebreak notation like `6(5)` or `6(4)`. The scores appear as inline text (e.g., "6 6(5) 7") which breaks visual alignment across multiple match rows.

**Screenshot:** See `docs/screenshots/task2-misaligned-scores-matches.png` and `docs/screenshots/task2-misaligned-scores-match.png`
- Matches list showing scores inline with team names
- Scores with tiebreaks like "6 6(5) 7" and "2 7 6(4)" cause visual misalignment
- Inconsistent horizontal spacing across match rows due to varying score widths

**Requirement:**
Redesign the set score display on both screens
- Edge cases like `6(5)`, `6(4)` do not cause visual misalignment
- The layout works cleanly on both Matches list and Match detail screens

**Screens to Update:**
- Matches list screen
- Match detail screen

**Acceptance Criteria:**
- Scores remain visually aligned regardless of tiebreak notation
- Layout is responsive and readable on various screen sizes
- No visual misalignment across multiple match rows

---

### Task 3: Create Players List Screen

**Description:**
Implement a new Players list screen that fetches and displays player data from the PadelAPI when an API token is provided, or displays demo data if no token is configured.

**API Endpoint:**
`GET /api/players` — Returns a list of players from the PadelAPI. Reference: [PadelAPI Players Documentation](https://padelapi.org/docs/api-reference/player/list-players)

**Requirements:**

1. **Screen Implementation**
   - Create a new players list screen accessible from the tournaments list screen via a "Players" chip (similar to the existing "Matches" chip on tournament detail)
   - Display players in a scrollable list with relevant information (name, ranking, country, etc.)
   - Support pull-to-refresh to reload the player list
   - Implement back navigation to return to the tournaments list screen
   - Add filtering functionality to allow users to search and filter players by name
   - Display search input field with clear functionality
   - Ensure the screen design is meaningful and consistent with other screens in the app

2. **Data Handling**
   - Fetch player data from `/api/players` endpoint when API token is present
   - Display bundled demo player data when no API token is configured
   - Follow the same token detection pattern as existing Tournaments and Matches screens
   - Implement appropriate error handling for network failures
   - Pagination support is not required

3. **Architecture**
   - Follow the established layered architecture (data, domain, UI modules)
   - Create necessary repository, use case, and ViewModel components
   - Use the MVI state management pattern with Flow-based state reduction
   - Integrate with existing DI (Koin) configuration

4. **Code Quality**
   - Follow project naming conventions and code style
   - Implement proper error states and loading indicators
   - Ensure the screen works on both Android and iOS

**Acceptance Criteria:**
- "Players" chip appears on tournaments list screen
- Tapping the "Players" chip navigates to the players list screen
- Players list screen loads and displays players from the API (or demo data)
- Pull-to-refresh works correctly
- Back navigation returns to the tournaments list screen
- Search/filter by player name works correctly
- Player list updates in real-time as user types
- Clear button removes the search filter
- Network errors are handled properly
- The implementation follows the existing layered architecture and patterns
- Screen design is meaningful and consistent with other screens in the app
- Code passes all verification checks (linting, formatting, compilation)
- Screen is responsive and works on both Android and iOS simulators

---

### Task 4: Create Player Detail Screen

**Description:**
Implement a player detail screen that displays comprehensive information about a specific player, accessible from both the Players list screen and the Match detail screen.

**API Endpoint:**
`GET /api/players/{id}` — Returns detailed information about a specific player. Reference: [PadelAPI Player Details Documentation](https://padelapi.org/docs/api-reference/player/show-player)

**Requirements:**

1. **Screen Implementation**
   - Create a player detail screen displaying player information (name, ranking, country, statistics, etc.)
   - Make the screen accessible from:
     - Players list screen by tapping a player
     - Match detail screen by tapping on a player/pair name
   - Implement back navigation to return to the originating screen
   - Display player photo if available
   - Ensure the screen design is meaningful and consistent with other screens in the app

2. **Navigation & Integration**
   - Handle navigation from two different source screens
   - Preserve navigation stack so back button returns to the correct previous screen
   - Ensure smooth navigation flow on both platforms

3. **Data Handling**
   - Fetch player details from `/api/players/{id}` endpoint when API token is present
   - Display bundled demo player data when no API token is configured
   - Handle player not found and other API errors gracefully

4. **Architecture**
   - Follow the established layered architecture (data, domain, UI modules)
   - Create necessary repository, use case, and ViewModel components
   - Use the MVI state management pattern with Flow-based state reduction
   - Integrate with existing DI (Koin) configuration

5. **Code Quality**
   - Follow project naming conventions and code style
   - Implement proper loading and error states
   - Ensure the screen works on both Android and iOS

**Acceptance Criteria:**
- Player detail screen displays player information from API (or demo data)
- Screen is accessible from Players list screen
- Screen is accessible from Match detail screen
- Back navigation returns to the correct source screen
- Player photos/avatars load correctly
- Network errors are handled gracefully
- The implementation follows the existing layered architecture and patterns
- Navigation stack is preserved correctly
- Screen design is meaningful and consistent with other screens in the app
- Code passes all verification checks (linting, formatting, compilation)
- Screen is responsive and works on both Android and iOS simulators

---

### Task 5: Add Location-Based Tournament Sorting

**Description:**
Enhance the Tournaments list screen to automatically sort tournaments by distance from the user's current device location. The list should re-sort reactively as the device location changes — no manual user interaction required.

**API Endpoint:**
`GET /api/tournaments` — Returns a list of tournaments. Reference: [PadelAPI Tournaments Documentation](https://padelapi.org/docs/api-reference/tournament/list-tournaments)

**Note on Coordinates:**
The API returns only `location` (city name) and `country` (ISO-2 code) — no latitude/longitude fields. You have full freedom on how to obtain tournament coordinates:
- Hardcode coordinates for known cities/countries
- Use a country centroid lookup table
- Use a free geocoding resource without API limits (e.g. Nominatim/OpenStreetMap)
- Use platform built-in geocoders (`Geocoder` on Android, `CLGeocoder` on iOS) via `expect`/`actual`
- Mock coordinates for demo data

**Focus of This Task:**
The primary objective of this task is not coordinate retrieval, but rather to assess proficiency in working with **reactive streams**. The evaluation emphasizes:
- Combining multiple `Flow`s (`combine`, `flatMapLatest` etc.)
- Platform-specific code via `expect`/`actual` pattern
- MVI state management with reactive data
- Responsive UI that updates automatically without user interaction

Choose any reasonable coordinate sourcing strategy and focus on clean, reactive implementation.

**Requirements:**

1. **Location Access**
   - Request user permission to access device location
   - Observe location using platform APIs (`LocationManager` on Android, `CLLocationManager` on iOS) via `expect`/`actual` with a 60-second minimum interval
   - Emit location updates only when the location has actually changed
   - Handle permission denial gracefully

2. **Reactive Sorting**
   - Combine the device location `Flow` with the tournament data `Flow`
   - Re-sort tournaments automatically when a new distinct location is detected (observed every 60 seconds) — no user interaction needed
   - Sort by distance from current location (nearest first)

3. **UI**
   - Tournaments list re-sorts dynamically as location updates arrive
   - Display distance for each tournament (e.g., "12 km away")
   - Handle cases where location or coordinates are unavailable gracefully

4. **Architecture**
   - Implement `LocationService` following `expect`/`actual` pattern for Android and iOS
   - Integrate with existing MVI state management using `combine`/`flatMapLatest`
   - Handle location permission

5. **Error Handling**
   - Handle location permission denied
   - Handle missing or incomplete tournament coordinate data

**Acceptance Criteria:**
- Tournaments list re-sorts automatically when device location changes (no user tap required)
- Distance is displayed for each tournament (e.g., "15 km away")
- Location observation uses a reactive `Flow` combined with tournament data
- Permission denial is handled gracefully
- The feature works on both Android and iOS
- Code follows the established architecture and patterns
- Code passes all verification checks (linting, formatting, compilation)

---

## Expectations

### Tools & AI Agents

You are **encouraged to use AI agents and tools** to assist with development and documentation, including **Claude Code**, **Codex** etc.

AI agents can help you work faster and validate your approach, but you should:
- Understand the code you write or accept from AI assistance
- Verify that AI-generated solutions work and **align with project architecture**
- Use AI as a productivity tool, not a shortcut for skipping code review or testing 

### What Constitutes Completion

A task is considered complete when it meets all acceptance criteria AND the following quality standards:

- **Clear UI/UX Design** — UI should be intuitive, consistent with the existing design system (Material 3), and provide a clear user experience with appropriate feedback for user actions
- **Adherence to Existing Architecture** — Solutions must follow the established layered architecture (data, domain, UI), MVI state management patterns, and dependency rules outlined in [AGENTS.md](AGENTS.md)
- **Code Quality and Cleanliness** — Code should be readable, maintainable, and follow the project's conventions (naming, formatting, import ordering, line limits)
- **Scalability of Solutions** — Features and bug fixes should be designed with extensibility in mind, avoiding technical debt and future complications
- **Testing and Verification** — Changes must be testable and verified to work across both Android and iOS platforms as outlined in [AGENTS.md](AGENTS.md)

All code should pass the verification commands listed in [AGENTS.md](AGENTS.md) (metadata compilation, platform-specific compilation, linting, and analysis).

### Focus Areas

- Architecture adherence — The primary evaluation axis. Solutions must follow the layered data → domain → UI structure and the MVI reducer pattern from BaseViewModel.
- Reactive patterns — Especially for Task 5: correct use of combine, flatMapLatest, and Flow-based state management.
- Multiplatform correctness — Code must compile and run on both Android and iOS; use expect/actual when platform diverges.
- Code quality — Passes all verification commands: ktlintCheck, detekt, lint, metadata and platform compilations.

## Development Guidance

For architectural patterns, coding conventions, module structure, and verification commands, refer to:
- [AGENTS.md](AGENTS.md) — Comprehensive guidelines for architecture, dependencies, and testing
- [CLAUDE.md](CLAUDE.md) — Entry point referencing shared instructions
- [README.md](README.md) — Project setup and API configuration

## Submission

**Create a New Public Repository with Your Solution**

Create a **new public repository** (not a fork) containing your completed tasks and submit the repository link.

**What to Include in Your Solution Repository:**

1. **Code Changes** — All modifications to fix the bugs or implement features, organized in separate logical commits with one task per commit
2. **Markdown Files** — Any markdown documentation files you changed/created during development
3. **Verification Screenshots** — Evidence that tasks pass acceptance criteria, saved in `docs/screenshots/`

**Submission Steps:**

1. Clone the original repository
2. Create a **new public repository** with a descriptive name (e.g., `padel-cmp-interview-solution`)
3. Push your completed work with clear commit messages (one task per commit)
4. Organize all verification screenshots and development notes
5. Share the public repository URL with reviewers

**Repository Requirements:**

- Must be **public** repository
- Should be **independent** (not a fork)
- Clean commit history: one commit per task
- All code passes verification checks before submission
- Include README with summary of completed tasks

This approach demonstrates your ability to organize, document, and present professional work while making it easy for reviewers to evaluate each task independently.
