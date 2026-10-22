# Legacy Gold Farm

Configurable Fabric tweaks for potato_noir's [gold farm](https://www.youtube.com/watch?v=wLLTKyptV0k), for Minecraft 26.1.x, 26.2 and 26.3.

## Settings

Configurable trough Mod Menu.

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

Use the matching jar with Fabric Loader 0.20.1 or newer and Java 25. Fabric API is not required by this mod; the optional screen libraries may have their own dependencies.

Install on the server or in the singleplayer instance for mechanics. Install on clients too, with matching geometry settings, for consistent baby hitboxes. A client-only installation cannot change server mechanics.

Settings are stored in `config/legacy-gold-farm.json`. On a dedicated server, edit that server's file and restart it. Mod Menu edits the local installation only, not a remote server. No config packets or remote administration commands are added.

## Development

Run `./gradlew compileJava`, then `./gradlew build`. Jars are written to `build/libs`.

[Mechanics and sources](docs/RESEARCH.md) explain the changes. [Validation](docs/VALIDATION.md) records the checks performed. The lower baby drop position is a strong explanation for spillage, rather than a demonstrated complete diagnosis. Use the updated elevator if you want to avoid the original snow-golem targeting setup.

GPL-3.0-or-later. Copyright 2026 Fayber.
