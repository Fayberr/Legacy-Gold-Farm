# Legacy Gold Farm

Fabric compatibility candidate for potato_noir's gold farm with the updated, simpler Overworld elevator.

Restores pre-26 baby zombified piglin dimensions and riding position, current-dimension anger targeting, and the old non-player anger countdown. The baby position change is the suspected cause of item spillage. Gameplay and farm tests have deliberately not been performed. This does not guarantee a working farm or unchanged rates.

See [the mechanics investigation](docs/RESEARCH.md) for the evidence, exact changes and remaining uncertainty.

## Installation

Use the jar matching your Minecraft version with Fabric Loader 0.20.1 or newer and Java 25. Fabric API is not required. Install in the singleplayer instance, or on the dedicated server. Installing on clients too keeps the restored baby hitbox and riding geometry consistent with the server; the new baby model is retained and can visually mismatch the old hitbox. A client-only installation cannot fix server mechanics. There are no commands or configuration screens.

The patch affects all zombified piglins in that installation, including ones outside this farm. Other neutral mobs retain vanilla behavior. Existing baby dimensions are restored when loaded/refreshed, so restart the game/server after installation. Remove the jar and restart to return to vanilla behavior. No custom entity NBT is written.

## Building

Run `./gradlew compileJava` followed by `./gradlew build`. Build outputs are under `build/libs`. Compilation and static hook inspection are the only verification performed. No game was launched and no world was modified.

## License

GPL-3.0-or-later. Copyright 2026 Fayber.
