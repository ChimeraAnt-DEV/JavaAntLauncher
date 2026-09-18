# JavaAntLauncher — repository notes

Android launcher for Minecraft: Java Edition. Fork of Zalith Launcher 2, which itself
descends from PojavLauncher. Repo root is the Gradle root; the Android app module is
`ZalithLauncher/`.

## Build

* `./gradlew :ZalithLauncher:assembleDebug` (needs a JDK and an Android SDK).
* A dev container may have neither installed — check before promising a compile or a run.
* `ZalithLauncher/gradle.properties` holds `oauth_client_id`; a git-ignored
  `ZalithLauncher/.oauth_client_id.txt` overrides it locally. CI passes `OAUTH_CLIENT_ID`.
* Microsoft sign-in needs a real Azure app registration (public client flows enabled).
  Without one, only offline/local accounts work.

## Architecture worth knowing

* Launch pipeline: `LaunchGameOperation.TryLaunch` (UI gate) →
  `LaunchGameViewModel.start()` → `GameLaunchFlow.buildLaunchPhases()` (account check +
  file verification) → `runGame()` → `GameLauncher.launch()`.
* `LaunchGameOperation` is a sealed interface in
  `ui/screens/content/elements/LauncherElements.kt`. Adding a case means handling it in the
  `when` in that same file — it is exhaustive, so the compiler enforces it.
* `Version` is `Parcelize`d and passed through Intents. `getVersionInfo()` returns
  `VersionInfo?`; it is **null** whenever the version's `<name>.json` is missing or fails to
  parse, while the version can still exist on disk. `version.isValid()` is exactly
  "versionInfo parsed successfully".
* `version.offlineAccountLogin` decides whether `GameLauncher` swaps in a LOCAL copy of the
  account (`usingAccount`). Microsoft accounts with no entitlement are constructed as offline
  identities (locally derived UUID, no official profile), so they *must* keep this flag or
  the client fails session validation after launch.
* `OfflineYggdrasilServer` is only started for LOCAL accounts that have a local skin file;
  it is what lets offline accounts resolve skins.

## Conventions

* UI strings live in `values/`, `values-zh-rCN/`, `values-zh-rTW/`. Add all three.
* Comments in this codebase are written in Chinese; match the surrounding file.
* `!!` on `getVersionInfo()` is a latent crash. Use `?.` and surface a launch error instead.

## Gotchas

* No JDK/Android SDK in the default dev container: verify Kotlin changes by reading, not by
  compiling, unless you install a toolchain.
* `MCOptions` watches `options.txt` with a `FileObserver`; it stops watching around its own
  atomic write to avoid re-entrant reloads.
