# Padel CMP Interview

> **Candidate?** Start here: [INTERVIEW.md](INTERVIEW.md)

Compose Multiplatform demo app for browsing padel tournaments, tournament matches, and match details on Android and iOS. It uses layered architecture with MVI state management.

## Quick Start

Use JDK 21 and an Android SDK matching the versions in [gradle/libs.versions.toml](gradle/libs.versions.toml). Configure the local SDK path through Android Studio or `local.properties`. iOS development requires macOS and Xcode.

**Android:** Open the repository in Android Studio, sync Gradle, and run the `composeApp` configuration on an emulator or device. To build a debug APK:

```bash
./gradlew :composeApp:assembleDebug
```

**iOS:** In Android Studio, select the `iosApp` run configuration, choose an ARM64 iOS simulator, and run. This workflow requires macOS and Xcode's toolchain. You can also build and run `iosApp/iosApp.xcodeproj` directly in Xcode. Physical device builds require signing configuration.

## Features

- Browse tournaments and view tournament details.
- Open a tournament's matches and inspect scores, teams, and player photos.
- Refresh screens using pull to refresh.
- Use bundled demo data without an API token, or fetch data from PadelAPI with a token.

Data loads on screen entry and refresh.

## Screens

**1. Tournaments List**
Browse all available padel tournaments. Each tournament card displays the name, tier/category, location, and date range. Pull-to-refresh updates the tournament list.

See `docs/screenshots/screen-1-tournaments-list.png`

**2. Tournament Detail**
View detailed information about a selected tournament, including the event poster, location, court type, timezone, prize money, and key metadata. Displays a "Matches" tab to navigate to the tournament's matches.

See `docs/screenshots/screen-2-tournament-detail.png`

**3. Matches List**
View all matches within a tournament. Each match card shows the participating teams, court, date, status, and set scores. Pull-to-refresh updates the matches list.

See `docs/screenshots/screen-3-matches-list.png`

**4. Match Detail**
View comprehensive match information including player photos, names, set-by-set scores, and match status.

See `docs/screenshots/screen-4-match-detail.png`

## API Configuration

An absent or empty token selects `DemoMatchRepository` and `DemoTournamentRepository`. Demo records are bundled in the data modules; images use remote URLs and may still require network access.

A nonempty token selects the PadelAPI repositories. Requests use `https://padelapi.org` with bearer authentication.

**Android:** Create `config/padelapi.properties`:

```properties
apiToken=YOUR_PADEL_API_TOKEN
```

Rebuild the app after changing the token; Gradle embeds it in the generated Android configuration.

**iOS:** Create `iosApp/iosApp/PadelApi.plist` with a string entry named `ApiToken` and ensure it is included in the app bundle:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    <key>ApiToken</key>
    <string>YOUR_PADEL_API_TOKEN</string>
</dict>
</plist>
```

Both token files are ignored by Git. Remove the token file or leave its token empty to use demo data.

For full API documentation, see the [PadelAPI API reference](https://padelapi.org/docs/api-reference/tournament/list-tournaments).

The current remote data sources request:

| Request | Purpose |
| --- | --- |
| `GET /api/tournaments` | Tournament list; `per_page=50`, `sort_by=start_date`, `order_by=desc`, and a fixed `before_date=2026-08-16` filter |
| `GET /api/tournaments/{id}` | Tournament details |
| `GET /api/tournaments/{id}/matches` | Tournament matches; `per_page=50`, `sort_by=updated_at`, `order_by=desc` |
| `GET /api/matches/{id}` | Match details |
| `GET {pairUrl}` from the match response | Pair details containing player photo URLs |
| `GET /api/players` | Player list; `per_page=50`, `sort_by=name`, `order_by=asc` |

The list requests fetch one page; incremental pagination is not implemented.

## Completed tasks

### Task 1: Pull-to-refresh on detail errors

Tournament and match detail screens kept the pull-to-refresh gesture inside a scrollable child. After a failed load that child was not composed, so the gesture did nothing. A failed load now keeps a scrollable error message, and pulling it retries the request. An already loaded screen stays visible if a later refresh fails.

Checked with `./gradlew test`, plus `ktlintCheck` and `detekt` on the tournament and match UI modules. Reducer tests cover the error, refresh, and successful-load transitions.

See `docs/screenshots/task1-pull-refresh-fixed-tournament.png` and `docs/screenshots/task1-pull-refresh-fixed-match.png`.

### Task 2: Aligned set scores

Set scores on the matches list were one string, so a tie-break such as `6(5)` changed the width of the line. Match detail used a minimum column width that a tie-break exceeded, so the set columns drifted between the header and the two teams. Both screens now share fixed-width set columns, aligned to the end, wide enough for a tie-break. Every row reserves three set columns, so set 1 stays in the same place when one match has two sets and another has three.

Checked with `compileDebugKotlinAndroid`, `ktlintCheck`, and `detekt` on the match UI module.

See `docs/screenshots/task2-aligned-scores-matches.png` and `docs/screenshots/task2-aligned-scores-match.png`.

### Task 3: Players list

A Players chip on the tournaments list opens the players screen on the app back stack, so back returns to the tournaments list. The tournaments module does not depend on the players module. With an API token the screen loads `GET /api/players`. Without a token it shows bundled demo players. The list shows name, ranking, and country, and a photo when the API provides one. Pull-to-refresh reloads the list, including after a failed load. The search field filters the loaded names in the screen state as the user types, and the clear button removes that filter without a new request.

Checked with `compileDebugKotlinAndroid`, `test`, `ktlintCheck`, and `detekt` on the player modules.

See `docs/screenshots/task3-players-list.png` and `docs/screenshots/task3-players-search.png`.

## Development Guidance

See [AGENTS.md](AGENTS.md) for module structure, architecture, coding conventions, verification commands, and testing guidance. [CLAUDE.md](CLAUDE.md) is the entry point for Claude Code and references those shared instructions.
