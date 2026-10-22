# Validation

Version 0.2.1, 2026-10-22. Java 25, Fabric Loom 1.19.2.

- Java compilation and Gradle build passed for 26.1, 26.2 and 26.3. The first jar covers the 26.1 patch family.
- The original five hook signatures were inspected on 26.1, 26.1.1, 26.1.2, 26.2 and 26.3. Mob.tick and SpearUseGoal start/continue signatures were inspected on the four cached 26.x baselines.
- Both optional screen implementations compile against their version-specific libraries. Common initialization has no screen-library references. UI libraries are compile-only and not bundled.
- Every mechanic hook checks its individual setting through the master switch. Geometry refresh observes the same effective setting; spear continuation is gated as well as starting.
- Screens edit a copied config. Save persists a new snapshot before publishing it, and Cancel leaves the active snapshot alone. Missing JSON fields keep their defaults. Invalid files are logged and left unchanged.
- Removed the portal mixin, config field and shared screen option. Existing JSON files tolerate the obsolete field.
- Jar metadata, six mixin class entries, main/Mod Menu entrypoints, version ranges and absence of nested dependency jars were inspected.
- vibe-check completed with zero errors and zero warnings for each checkout. Its scan covers supported text files.
