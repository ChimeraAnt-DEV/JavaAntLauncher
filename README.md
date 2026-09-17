# Java Ant Launcher

![Downloads](https://img.shields.io/github/downloads/ChimeraAnt-DEV/JavaAntLauncher/total)
[![Sponsor](https://img.shields.io/badge/sponsor-30363D?logo=GitHub-Sponsors)](https://afdian.com/a/MovTery)

[简体中文](README_ZH_CN.md) | [繁體中文](README_ZH_TW.md)


> [!IMPORTANT]
> This project is **completely separate** from [ZalithLauncher](https://github.com/ZalithLauncher/ZalithLauncher).  

> [!WARNING]
> **This is an unofficial fork.** This repository is a community fork of
> [ZalithLauncher/ZalithLauncher2](https://github.com/ZalithLauncher/ZalithLauncher2) and is **not** affiliated with,
> endorsed by, or maintained by the upstream project. It is distributed under a different name, as required by the
> additional terms of the GPLv3 license. Please report issues about this fork here, not upstream.

<a id="about"></a>
## About

**Java Ant Launcher** is a community fork of **Zalith Launcher 2** — a newly designed launcher for **Android devices** tailored for [Minecraft: Java Edition](https://www.minecraft.net/). It uses [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher/tree/v3_openjdk/app_pojavlauncher/src/main/jni) as its core launching engine and features a modern UI built with **Jetpack Compose** and **Material Design 3**.

This fork focuses on two things:

* a reactive, premium "VIP" dark theme (**Obsidian VIP**) with animated gold and aurora accents, and
* a fully English documentation set.

> [!NOTE]
> The core launcher targets [Minecraft: Java Edition](https://www.minecraft.net/). Purchasing Minecraft: Java Edition is
> what grants you a legitimate Microsoft/Mojang account for online play. This fork does not, and will not, bypass that
> requirement — see [Accounts](#accounts) below for what is actually supported.

We are currently building the upstream project's official website [zalithlauncher.cn](https://zalithlauncher.cn)
Additionally, we are aware that a third-party website has been set up using the name “Zalith Launcher”, appearing to be official. Please note: **this site was not created by us**. It exploits the name to display ads for profit. We **do not participate in, endorse, or trust** such content.
Please stay vigilant and **protect your personal privacy**!

<a id="accounts"></a>
## 🎮 Accounts

The launcher supports three account types:

| Account type | What it is | Online play |
|--------------|------------|-------------|
| **Microsoft** | Your paid Minecraft: Java Edition account, signed in with Microsoft OAuth | ✅ Yes |
| **Offline / Local** | A local username that never touches Mojang's servers — no purchase, no login | ❌ No (single-player and LAN only) |
| **Auth server** | A third-party Yggdrasil / authlib server (e.g. a community server) | ✅ Yes, on that server |

**You do not need to own Minecraft or have a Microsoft account to play.** The **offline (local) account** option is
fully supported and available to everyone on every build: open **Settings → Accounts → Add account → Local account**,
pick a username, and you can play single-player worlds, mods and resource packs immediately. The launcher never forces
you through Microsoft sign-in.

Local accounts are also the route to join **offline/cracked servers** (servers that do not verify ownership with
Mojang). Whether a server accepts you is that server's own check, not something a launcher can change — servers that
verify ownership with Mojang will reject an unowned account no matter what the launcher sends, so use a local account
with servers that allow it.

### Signing in with a Microsoft account

If you have a Microsoft account, you can sign in with it even if it has never bought Minecraft. Microsoft's identity
service authenticates you; the launcher then checks ownership against Mojang's `entitlements` service as part of the
normal login flow. When that check reports no entitlement, the launcher:

1. **Signs you in anyway**, using your Xbox gamertag as a local offline identity.
2. **Lets you launch the instance you selected**, immediately, with no further interruption.
3. Shows a small "ownership unverified" hint so you know to expect the offline experience.

What you get in that state is the offline experience: single-player worlds, mods, resource packs and LAN. What you do
not get is online play against official Mojang servers, because **those servers verify ownership themselves** on every
connection. No client-side change can alter that — it is their check, not ours. Offline/cracked servers will accept
the account normally.

> [!IMPORTANT]
> This fork does not remove, disable, or bypass the ownership check. It still runs exactly as upstream intends, and
> still reports honestly. The only change is that a failed check is now treated as a *limitation notice* rather than a
> hard stop, so a user who signs in with an account that has no entitlement is not left staring at an error with no way
> forward.
>
> For full online play, buying Minecraft: Java Edition is the only legitimate route, and this project will not help you
> avoid that purchase.

[Discord Server Shutdown Announcement](/.github/notice/DiscordStatus.md)  

## 🌐 Language and Translation Support

We are using the Weblate platform to translate Zalith Launcher 2. You're welcome to join our [Weblate project](https://hosted.weblate.org/projects/zalithlauncher2) and contribute to the translations!  
Thank you to every language contributor for helping make Zalith Launcher 2 more multilingual and global!

## 📦 Build Instructions (For Developers)

> The following section is for developers who wish to contribute or build the project locally.

### Requirements

* Android Studio **Bumblebee** or newer
* Android SDK:
  * **Minimum API level**: 26
  * **Target API level**: 35
* JDK 11

### Build Steps

```bash
git clone git@github.com:ChimeraAnt-DEV/JavaAntLauncher.git
# Open the project in Android Studio and build
```

> [!NOTE]
> This repository is a modified fork of Zalith Launcher 2. The GPLv3 additional terms require that modified versions be
> distributed under a distinct name; the user-facing application name can be changed in
> [gradle.properties](./ZalithLauncher/gradle.properties).

### Enabling Microsoft sign-in (required for Microsoft accounts)

Microsoft sign-in **is entirely optional**. The build this text ships with has no OAuth client ID, so Microsoft sign-in
is unavailable — the launcher tells you so and points you to the local account flow instead. You can ignore Microsoft
sign-in completely and use a local account to play single-player, mods and offline/cracked servers.

If you maintain your own fork and want to enable Microsoft sign-in, you need to supply a client ID: Microsoft's
device-code endpoint rejects a request without one with `400 Bad Request` (`AADSTS900144: The request body must contain
the following parameter: 'client_id'`). The launcher detects this case up front and tells you, but you still have to
supply an ID to use Microsoft accounts.

An OAuth client ID is an Azure app registration that belongs to whoever ships the build. Upstream's ID is not yours to
reuse, so forks must register their own:

1. Open the [Azure Portal](https://portal.azure.com/) → **Microsoft Entra ID** → **App registrations** → **New
   registration**.
2. Set **Supported account types** to *Personal Microsoft accounts only* (or *Accounts in any organizational directory
   and personal Microsoft accounts*).
3. Under **Authentication**, enable **Allow public client flows** — required for the device-code flow.
4. Copy the **Application (client) ID**.
5. Provide it to the build in one of two ways:
   * **Local builds** — create `ZalithLauncher/.oauth_client_id.txt` containing the ID. This file is git-ignored, so it
     will not end up in a commit:
     ```bash
     echo "00000000-0000-0000-0000-000000000000" > ZalithLauncher/.oauth_client_id.txt
     ```
   * **CI builds** — add a repository secret named `OAUTH_CLIENT_ID` (see
     [build.yml](./.github/workflows/build.yml) for how it is passed in).

   Alternatively you can set `oauth_client_id` in `ZalithLauncher/gradle.properties`, but note that file **is**
   tracked by git, so it is a poor place for a real value.

Without one of these, offline/local accounts still work fully; only Microsoft sign-in is unavailable.

## 🎨 Themes

Zalith Launcher 2 ships with a set of built-in color themes plus Material You dynamic color on Android 12+.

This fork adds **Obsidian VIP**, a premium dark theme: near-black obsidian surfaces, champagne-gold primary, violet and
aurora-cyan accents, a gold `VIP` badge in the top bar, and a reactive aurora glow that slowly drifts behind the UI.
Other premium touches (gold gradient card borders, gradient VIP badge) apply automatically while the theme is active.

Select it in **Settings → Launcher → Color theme → Obsidian VIP (Premium)**. It stays dark regardless of the light/dark
setting so the look stays consistent. Everything is drawn with Compose animation primitives, so it is reactive to
theme changes and costs nothing when another theme is selected.

## 📜 License

This project is licensed under the **[GPL-3.0 license](LICENSE)**.

### Additional Terms (Pursuant to Section 7 of the GPLv3 License)

1. When distributing a modified version of this program, you must reasonably modify the program's name or version number to distinguish it from the original version. (According to [GPLv3, 7(c)](https://github.com/ZalithLauncher/ZalithLauncher2/blob/969827b/LICENSE#L372-L374))
    - Modified versions **must not include the original program name "ZalithLauncher" or its abbreviation "ZL" in their name, nor use any name that is similar enough to cause confusion with the official name**.
    - All modified versions **must clearly indicate that they are “Unofficial Modified Versions” on the program’s startup screen or main interface**.
    - The application name of the program can be modified in [gradle.properties](./ZalithLauncher/gradle.properties).

2. You must not remove the copyright notices displayed by the program. (According to [GPLv3, 7(b)](https://github.com/ZalithLauncher/ZalithLauncher2/blob/969827b/LICENSE#L368-L370))

## Open Source Libraries and Licenses

This software uses the following open source libraries:

| Library                               | Copyright                                                                                                     | License              | Official Link                                                                      |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------|----------------------|------------------------------------------------------------------------------------|
| androidx-appcompat                    | Copyright © The Android Open Source Project                                                                   | Apache 2.0           | [Link↗](https://developer.android.com/jetpack/androidx/releases/appcompat)         |
| androidx-constraintlayout-compose     | Copyright © The Android Open Source Project                                                                   | Apache 2.0           | [Link↗](https://developer.android.com/develop/ui/compose/layouts/constraintlayout) |
| androidx-webkit                       | Copyright © The Android Open Source Project                                                                   | Apache 2.0           | [Link↗](https://developer.android.com/jetpack/androidx/releases/webkit)            |
| ANGLE                                 | Copyright 2018 The ANGLE Project Authors                                                                      | BSD 3-Clause License | [Link↗](http://angleproject.org/)                                                  |
| Apache Commons Codec                  | -                                                                                                             | Apache 2.0           | [Link↗](https://commons.apache.org/proper/commons-codec)                           |
| Apache Commons Compress               | -                                                                                                             | Apache 2.0           | [Link↗](https://commons.apache.org/proper/commons-compress)                        |
| Apache Commons IO                     | -                                                                                                             | Apache 2.0           | [Link↗](https://commons.apache.org/proper/commons-io)                              |
| ByteHook                              | Copyright © 2020-2024 ByteDance, Inc.                                                                         | MIT License          | [Link↗](https://github.com/bytedance/bhook)                                        |
| BuildKeys                             | Copyright © 2026 MovTery                                                                                      | Aoache 2.0           | [Link↗](https://github.com/MovTery/BuildKeys)                                      |
| Coil Compose                          | Copyright © 2025 Coil Contributors                                                                            | Apache 2.0           | [Link↗](https://github.com/coil-kt/coil)                                           |
| Coil Gifs                             | Copyright © 2025 Coil Contributors                                                                            | Apache 2.0           | [Link↗](https://github.com/coil-kt/coil)                                           |
| Coil SVG                              | Copyright © 2025 Coil Contributors                                                                            | Apache 2.0           | [Link↗](https://github.com/coil-kt/coil)                                           |
| Fishnet                               | Copyright © 2025 Kyant                                                                                        | Apache 2.0           | [Link↗](https://github.com/Kyant0/Fishnet)                                         |
| gl4es_extra_extra                     | Copyright © 2016-2018 Sebastien Chevalier; Copyright (c) 2013-2016 Ryan Hileman                               | MIT License          | [Link↗](https://github.com/PojavLauncherTeam/gl4es_extra_extra)                    |
| Gson                                  | Copyright © 2008 Google Inc.                                                                                  | Apache 2.0           | [Link↗](https://github.com/google/gson)                                            |
| kotlinx.coroutines                    | Copyright © 2000-2020 JetBrains s.r.o.                                                                        | Apache 2.0           | [Link↗](https://github.com/Kotlin/kotlinx.coroutines)                              |
| ktor-client-content-negotiation       | Copyright © 2000-2023 JetBrains s.r.o.                                                                        | Apache 2.0           | [Link↗](https://ktor.io)                                                           |
| ktor-client-core                      | Copyright © 2000-2023 JetBrains s.r.o.                                                                        | Apache 2.0           | [Link↗](https://ktor.io)                                                           |
| ktor-client-okhttp                    | Copyright © 2000-2023 JetBrains s.r.o.                                                                        | Apache 2.0           | [Link↗](https://ktor.io)                                                           |
| ktor-http                             | Copyright © 2000-2023 JetBrains s.r.o.                                                                        | Apache 2.0           | [Link↗](https://ktor.io)                                                           |
| ktor-serialization-kotlinx-json       | Copyright © 2000-2023 JetBrains s.r.o.                                                                        | Apache 2.0           | [Link↗](https://ktor.io)                                                           |
| LWJGL - Lightweight Java Game Library | Copyright © 2012-present Lightweight Java Game Library All rights reserved.                                   | BSD 3-Clause License | [Link↗](https://github.com/LWJGL/lwjgl3)                                           |
| material-color-utilities              | Copyright 2021 Google LLC                                                                                     | Apache 2.0           | [Link↗](https://github.com/material-foundation/material-color-utilities)           |
| Maven Artifact                        | Copyright © The Apache Software Foundation                                                                    | Apache 2.0           | [Link↗](https://github.com/apache/maven/tree/maven-3.9.9/maven-artifact)           |
| Media3                                | Copyright © The Android Open Source Project                                                                   | Apache 2.0           | [Link↗](https://developer.android.com/jetpack/androidx/releases/media3)            |
| Mesa                                  | Copyright © The Mesa Authors                                                                                  | MIT License          | [Link↗](https://mesa3d.org/)                                                       |
| MMKV                                  | Copyright © 2018 THL A29 Limited, a Tencent company.                                                          | BSD 3-Clause License | [Link↗](https://github.com/Tencent/MMKV)                                           |
| Navigation 3                          | Copyright © The Android Open Source Project                                                                   | Apache 2.0           | [Link↗](https://developer.android.com/jetpack/androidx/releases/navigation3)       |
| NG-GL4ES                              | Copyright © 2016-2018 Sebastien Chevalier; Copyright © 2013-2016 Ryan Hileman; Copyright (c) 2025-2026 BZLZHH | MIT License          | [Link↗](https://github.com/BZLZHH/NG-GL4ES)                                        |
| OkHttp                                | Copyright © 2019 Square, Inc.                                                                                 | Apache 2.0           | [Link↗](https://github.com/square/okhttp)                                          |
| Okio                                  | Copyright © 2013 Square, Inc.                                                                                 | Apache 2.0           | [Link↗](https://square.github.io/okio/)                                            |
| OpenNBT                               | Copyright © 2013-2021 Steveice10.                                                                             | MIT License          | [Link↗](https://github.com/GeyserMC/OpenNBT)                                       |
| Process Phoenix                       | Copyright © 2015 Jake Wharton                                                                                 | Apache 2.0           | [Link↗](https://github.com/JakeWharton/ProcessPhoenix)                             |
| proxy-client-android                  | -                                                                                                             | LGPL-3.0 License     | [Link↗](https://github.com/TouchController/TouchController)                        |
| Reorderable                           | Copyright © 2023 Calvin Liang                                                                                 | Apache 2.0           | [Link↗](https://github.com/Calvin-LL/Reorderable)                                  |
| sdl2-compat                           | Copyright (C) 2026 Sam Lantinga <slouken@libsdl.org>                                                          | Zlib License         | [Link↗](https://github.com/libsdl-org/sdl2-compat)                                 |
| SDL3                                  | Copyright (C) 1997-2026 Sam Lantinga <slouken@libsdl.org>                                                     | Zlib License         | [Link↗](https://github.com/libsdl-org/SDL)                                         |
| skinview3d                            | Copyright © 2014-2018 Kent Rasmussen; Copyright © 2017-2022 Haowei Wen, Sean Boult and contributors           | MIT License          | [Link↗](https://github.com/bs-community/skinview3d)                                |
| sora-editor                           | Copyright (C) 2020-2026  Rosemoe                                                                              | LGPL-2.1 License     | [Link↗](https://github.com/Rosemoe/sora-editor)                                    |
| StringFog                             | Copyright © 2016-2023, Megatron King                                                                          | Apache 2.0           | [Link↗](https://github.com/MegatronKing/StringFog)                                 |
| tm4e (TextMate for Eclipse)           | Copyright © Eclipse Foundation                                                                                | EPL-2.0 License      | [Link↗](https://github.com/eclipse-tm4e/tm4e)                                      |
| XZ for Java                           | Copyright © The XZ for Java authors and contributors                                                          | 0BSD License         | [Link↗](https://tukaani.org/xz/java.html)                                          |
