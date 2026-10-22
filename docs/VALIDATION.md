# Build and static inspection

2026-10-22, Java 25 and Fabric Loom 1.19.2.

- `./gradlew compileJava build --no-daemon` succeeded independently for 26.1, 26.2 and 26.3. The 26.1 jar is labelled 26.1.x.
- All five injection method signatures exist unchanged in official 26.1, 26.1.1, 26.1.2, 26.2 and 26.3 jars, inspected with javap. Method-body hooks use HEAD/TAIL rather than instruction ordinals.
- Packaged jar metadata, all four mixin class entries and the Minecraft version ranges were inspected.
- No test sources were created. The Gradle test tasks report NO-SOURCE. No game launch, Mixin runtime application, farm run, rate measurement or world modification was performed, as requested.
- The baby riding position change is confirmed by code inspection. Its role as the sole cause of spillage remains unverified. Other mod compatibility is untested.

| Jar family | SHA-256 |
| --- | --- |
| 26.1.x | `dc21c06aaecb29e75493aaf5389b3e0d0563147d6667a3fa645e132c856ac4e4` |
| 26.2 | `0fd2fe935a35519753b142a938be7f234499db7f4244d156be87b4e01cb870dd` |
| 26.3 | `3654db26a239af2d96a9e9b60956f01574736fa406f0550253ca5d01464b2b6d` |
