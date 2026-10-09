# AGENTS.md

Comprehensive guidelines for AI agents working on this repository. For project setup and API configuration, see [README.md](README.md). [CLAUDE.md](CLAUDE.md) points Claude Code to these shared instructions.

## Project Structure

The current modules are declared in [settings.gradle.kts](settings.gradle.kts):

- `build-logic/convention/` — Gradle convention plugins.
- `composeApp/` — Android and iOS entry points, navigation, and DI assembly.
- `config/` — Build configuration and platform-specific API token loading.
- `core/` — `coroutines`, `designsystem`, `exception`, `extensions`, `logging`, `network`, and `ui`.
- `feature/match/` and `feature/tournament/` — Each contains `data`, `domain`, and `ui` modules.
- `iosApp/` — Xcode host application.

Shared code lives in `src/commonMain/kotlin`; platform code lives in `src/androidMain/kotlin` and `src/iosMain/kotlin`. The configured targets are Android, iOS device ARM64, and iOS simulator ARM64.

## Architecture and Dependencies

The project uses layered architecture with MVI and Flow-based state reduction:

```text
feature UI → feature domain → feature data
```

- **Data:** Repository interfaces and implementations, remote/demo data sources, and DTOs. Repository contracts currently return DTOs; there is no separate data-model mapping layer.
- **Domain:** Use cases with `operator fun invoke`, domain models, and DTO-to-domain mappers.
- **UI:** Compose screens, Navigation 3 routes, ViewModels, immutable state, and sealed partial-state interfaces.
- **DI:** Each feature layer declares its own Koin module. `composeApp` assembles these modules and directly depends on feature data, domain, and UI modules.

Feature UI modules depend on their domain module; domain modules depend on their data module. Data modules must not depend on domain, UI, or `composeApp`. Shared infrastructure belongs in core modules.

ViewModels extend `BaseViewModel` from `core:ui`. Input and data streams emit partial states, which are merged and reduced using `runningFold`. `stateInViewModel` shares the resulting state; UI state and one-time events are derived from it. Preserve this pattern when adding behavior.

## Naming and Code Style

- Feature packages: `io.bifri.interview.cmp.padel.feature.<feature>.<data|domain|ui>`.
- Core packages: `io.bifri.interview.cmp.padel.core.<area>`.
- Module names: `core:<area>` and `feature:<name>:<data|domain|ui>`.
- Use 4-space indentation and follow [.editorconfig](.editorconfig), including the 120-character line limit and trailing commas.
- Follow `.editorconfig`'s import layout: `*,java.**,javax.**,kotlin.**,^`. Order non-aliased imports by group: all other packages first (including `kotlinx`), then `java`, `javax`, and `kotlin`; put all aliased imports (`import ... as ...`) last, regardless of package. Sort lexicographically within each group and do not insert blank lines between imports or groups.
- Use PascalCase constants, such as `BaseUrl`, following the [Jetpack Compose API guidelines for constants](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-api-guidelines.md#singletons_constants_sealed-class-and-enum-class-values).
- Prefer immutable state and reducer updates.

## Verification and Testing

Use checks appropriate to the changed modules and platforms:

```bash
./gradlew :composeApp:compileKotlinMetadata --quiet
./gradlew :composeApp:compileDebugKotlinAndroid --quiet
./gradlew :composeApp:compileKotlinIosSimulatorArm64 --quiet
./gradlew test
./gradlew ktlintCheck
./gradlew detekt
./gradlew lint
```

Metadata compilation alone does not verify Android or iOS compilation. iOS checks require macOS and Xcode. Android build/run instructions are in [README.md](README.md#quick-start).

- Add shared tests under `src/commonTest/kotlin`, using the configured `kotlin.test` dependency and the same package structure as the tested code.
- There are currently no test source files in the repository; a successful test task may have no tests to execute.
- Use deterministic demo data for tests rather than real API calls.
- Test state transitions by providing partial-state events and checking the reduced state.
- Use `./gradlew :<module>:tasks --all` to inspect available platform-specific test tasks before adding new test infrastructure.

## Dependencies and Convention Plugins

[gradle/libs.versions.toml](gradle/libs.versions.toml) is the source of truth for dependency versions and plugin IDs. The app uses Kotlin, Compose Multiplatform with Material 3, Ktor, kotlinx.serialization, Koin, Navigation 3, and Coil. Ktor uses OkHttp on Android and Darwin on iOS.

Apply convention plugins through version-catalog aliases, following existing modules:

```kotlin
plugins {
    alias(libs.plugins.convention.uifeature)
}
```

Key aliases:

- `convention.library.core` — Base KMP library configuration: Android/iOS targets, shared external dependencies, and quality tooling; adds no project-module dependencies.
- `convention.library` — Extends `convention.library.core` with dependencies on `:config`, `:core:coroutines`, `:core:exception`, `:core:extensions`, and `:core:logging`.
- `convention.library.compose` — Adds Compose support to an existing KMP module.
- `convention.uifeature` — Combines library and Compose setup and adds `core:ui`.
- `convention.application` / `convention.application.compose` — Application and Compose configuration.
- `convention.koin`, `convention.bignum`, `convention.filekit` — Shared dependencies.
- `convention.detekt`, `convention.ktlint`, `convention.lint` — Quality tooling.
- `convention.padelapiconfig` — Generates Android API token configuration.

## Resources

Compose resources currently contain English strings in `src/commonMain/composeResources/values/`.

Resource classes are configured in each owning module: `DesignSystemRes`, `MatchUiRes`, and `TournamentUiRes`. Follow existing `stringResource(ResourceClass.string.resource_name)` usage and import the generated resource property. Keep strings in the owning module's resources.
