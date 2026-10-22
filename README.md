# Legacy Gold Farm

Configurable Fabric tweaks for potato_noir's [gold farm](https://www.youtube.com/watch?v=wLLTKyptV0k), for Minecraft 26.1.x, 26.2 and 26.3.

## Download

Download the jar matching your Minecraft version:

- [Minecraft 26.1.x](https://github.com/Fayberr/Legacy-Gold-Farm/releases/tag/v0.2.1-mc26.1.x)
- [Minecraft 26.2](https://github.com/Fayberr/Legacy-Gold-Farm/releases/tag/v0.2.1-mc26.2)
- [Minecraft 26.3](https://github.com/Fayberr/Legacy-Gold-Farm/releases/tag/v0.2.1-mc26.3)

## Settings

Open **Mods > Legacy Gold Farm > Configure** with [Mod Menu](https://modrinth.com/mod/modmenu) and [Cloth Config](https://modrinth.com/mod/cloth-config) installed. [Modern Config](https://github.com/Fayberr/modern-config) is also supported and takes priority when installed. It requires [Modern GUI](https://github.com/Fayberr/modern-gui). The mod works without a settings screen; you can edit its JSON config instead.

| Setting | Default | Effect |
| --- | --- | --- |
| Enable reverts | On | Master switch. Off bypasses every mechanic patch without forgetting individual choices. |
| Baby piglin geometry | On | Restore old baby zombified piglin dimensions and boat riding position. |
| Local anger targets | On | Prevent zombified piglins from pursuing targets in another dimension. |
| Legacy anger timers | On | Count down anger toward mobs instead of constantly refreshing it. |
| Piglin melee pursuit | Off | Let the existing melee goal run instead of spear charging and repositioning. |

Save applies changes in the running instance. Cancel leaves active settings unchanged. Existing piglins refresh their geometry on their next tick. The master switch stops future interventions; it does not undo teleports, damage or targeting decisions that already happened.

### Side effects

All settings affect matching entities throughout the installation, not just this farm. The baby model stays current and can extend beyond the old hitbox. Local targeting disables cross-dimension pursuit in other contraptions. Legacy timers change other llama/golem aggro setups too.

Melee pursuit changes combat for spear-carrying zombified piglins and can break contraptions that use their charging attacks. Zombies, husks, ordinary piglins, player spears, equipment and loot tables are unchanged.

## Installation

Place the matching jar in your instance's `mods` folder, then restart Minecraft. Requires Fabric Loader 0.20.1 or newer and Java 25. Fabric API is not required by this mod; the optional screen libraries may have their own dependencies.

Install on the server or in the singleplayer instance for mechanics. Install on clients too, with matching geometry settings, for consistent baby hitboxes. A client-only installation cannot change server mechanics.

Settings are stored in `config/legacy-gold-farm.json`. On a dedicated server, edit that server's file and restart it. Mod Menu edits the local installation only, not a remote server. No config packets or remote administration commands are added.

## Building from source

Check out `fabric/26.1.x`, `fabric/26.2` or `fabric/26.3` for the version you want to build. Use JDK 25 and the included Gradle wrapper.

The [Modern Config](https://github.com/Fayberr/modern-config) adapter currently needs `net.fayber:modern-config` installed in your local Maven repository. The required version is listed in `gradle.properties`; it is a compile-only dependency and is not bundled. This prerequisite applies to source builds only. Downloaded jars work with Cloth Config without [Modern Config](https://github.com/Fayberr/modern-config).

Run `./gradlew compileJava`, then `./gradlew build` (`gradlew.bat` on Windows). Jars are written to `build/libs`.

## Feedback

Report problems through [GitHub Issues](https://github.com/Fayberr/Legacy-Gold-Farm/issues). Include your Minecraft and mod versions, enabled settings, and a screenshot or short reproduction. For crashes, attach the crash report.

[Mechanics and sources](docs/RESEARCH.md) explain the changes. [Validation](docs/VALIDATION.md) records the checks performed. The updated Overworld elevator is recommended.

GPL-3.0-or-later. Copyright 2026 Fayber.
