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

The list requests fetch one page; incremental pagination is not implemented.

## Development Guidance

See [AGENTS.md](AGENTS.md) for module structure, architecture, coding conventions, verification commands, and testing guidance. [CLAUDE.md](CLAUDE.md) is the entry point for Claude Code and references those shared instructions.
