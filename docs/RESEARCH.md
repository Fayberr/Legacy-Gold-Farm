# Farm compatibility research

Scope: potato_noir's gold farm, https://www.youtube.com/watch?v=wLLTKyptV0k, using the updated elevator. Owner requested no gameplay or farm tests. This is a source-based compatibility candidate, not a verified working farm release.

## Baby riding geometry, changed in 26.1

The 1.21.11 `ZombifiedPiglin.BABY_DIMENSIONS` definition is `EntityType.ZOMBIFIED_PIGLIN.getDimensions().scale(0.5F).withEyeHeight(0.97F)`. The adult dimensions are 0.6 x 1.95, with a VEHICLE attachment Y of -0.7. Scaling produces a 0.3 x 0.975 baby and attachment Y -0.35.

In 26.1 the definition instead uses 0.49 x 0.99 and eye height 0.78, with an explicit VEHICLE attachment Y +0.1875. 26.3 uses height 0.98 and the same riding attachment. Mojang announced the baby visual changes in snapshot 7 and adjusted their hitboxes in snapshot 8:
- https://www.minecraft.net/en-us/article/minecraft-26-1-snapshot-7
- https://www.minecraft.net/en-us/article/minecraft-26-1-snapshot-8

`Entity.positionRider` subtracts the passenger's VEHICLE attachment from the boat's passenger riding position. Changing -0.35 to +0.1875 lowers the baby by 0.5375 blocks in the same boat. `Entity.spawnAtLocation` spawns the mob's drops at its feet. Ordinary Boat rideHeight is one third of the boat height: the new attachment places baby feet at the boat base instead of above its top.

`ItemEntity.tick` checks all collision shapes through Level.noCollision. If embedded, it sets noPhysics and invokes Entity.moveTowardsClosestSpace, which can choose a horizontal escape direction. That behavior predates 26.1; comparisons against 1.21.10 and 1.21.11 found the same collision/escape path. Therefore a blanket rollback of item motion has no evidence behind it.

Conclusion: the lower baby riding position is a concrete mechanism for drops starting inside boats and being ejected. It is a strong source-based explanation for occasional spillage from a farm that also spawns babies. We have not established that it is the only cause, or observed the exact farm with/without the mod. The patch restores the complete old baby dimensions and attachments, rather than altering all items or magnetizing drops. Adults and loot tables are unchanged.

## Anger and cross-dimension targets, changed in 1.21.11

1.21.10 NeutralMob.readPersistentAngerSaveData resolves the stored UUID in the current ServerLevel. In 1.21.11+ it uses EntityReference.getLivingEntity. The cached EntityReference can return an entity object without verifying its level. Mob.asValidTarget also does not check dimensions. This permits a piglin to retain a foreign-dimension target. Report MC-312355 documents this and includes 1.21.10 versus 1.21.11 recordings:
- https://bugs.mojang.com/browse/MC/issues/MC-312355
- Readable mirror: https://mojira.dev/MC-312355

Also, old updatePersistentAnger starts a new timer only when the target UUID changes and pauses countdown only for player targets. The current implementation restarts the timer whenever stayAngryIfTargetPresent is true, including non-player targets such as llamas. ZombifiedPiglin passes true. This changes non-player anger from a countdown to a continuously refreshed timer.

The mod resolves saved targets in the current level, rejects foreign-level live targets for zombified piglins, and uses the old target-change and player-only pause rules. It also carries remaining anger duration across a dimension transfer when dimension clocks differ. It retains modern anger serialization (anger_end_time, angry_at). This is a targeted semantic restoration, not a complete backport of every old anger persistence behavior: modern absolute-time storage remains.

The old elevator's reported snow-golem problem cannot be assigned exclusively to this bug without checking the original world. The user selected the updated elevator, so no trapdoor/pathfinder rollback is included. The llama alert radius, alert cadence, line-of-sight check and group spreading code examined in 1.21.10 and 26.1 have the same behavior. No evidence supports replacing those globally.

## Other tutorial caveats

The author's 1.21.5 boat portal setup workaround (push boats farther into the portal) still applies. This mod does not restore pre-1.21.5 portal intersections. Portal linking, temporary setup height, multiplayer despawn distances and attack timing remain build/operation requirements, not demonstrated 26.x regressions. No new item collection guarantee, spawn rate boost, loot multiplier, chunkloader or auto-attacker is added.

## Evidence provenance

Compared official Mojang game artifacts locally with javap and CFR. Decompiled Mojang code is not distributed in this repository. Old artifact downloads and mappings come from Mojang's version manifest. Hashes below identify the cached merged comparison jars (Loom outputs, not upstream downloadable hashes).

| Version | SHA-256 of compared merged jar |
| --- | --- |
| 26.1 | `eef9b53294667ee099b26483dcc73b923c826ea0c700b16f14fd28eed687a5b9` |
| 26.1.2 | `62686f33938a431088a99e91e6c03fe173f0ec53d65fd9b6bff3ec0e9fd06647` |
| 26.2 | `42a58e3b84a83b2269505e623298003b94138393e8b63081304fff2a233ebc4c` |
| 26.3 | `946098fd0301ee78816da29f754ebd3004ed2745950a8b0b043775e7eb5e7197` |

## Original elevator follow-up (2026-10-22)

Further inspection identifies the global UUID lookup explicitly: EntityReference.getEntity(Level, Class) binds Level.getEntityInAnyDimension for LivingEntity targets, not merely a dimension-blind cached reference. This is present in the official 1.21.11 jar (EntityReference cgr, Level dwo.a(UUID), mapped to getEntityInAnyDimension) and remains in 26.1. NeutralMob.readPersistentAngerSaveData uses this resolver and assigns the result as the live target. In 1.21.10 the same load path uses ServerLevel.getEntity(UUID), restricted to the current dimension.

A piglin angered by the Nether llama can consequently reload its llama target after entering the Overworld. Navigation uses that target's unscaled coordinates, even though the llama is still in the Nether. The changed anger timer also continuously refreshes while the foreign target persists. This is a concrete failure mechanism for the old elevator's target handoff: Nether llama -> local snow golem -> climb toward the golem. Its contribution to the author's exact reported elevator failure is a source-based inference, not a reproduced observation. The current local-target/load and timer patches already address this mechanism.

A separate 1.21.11 change remains outside the patch: spear-equipped zombified piglins run priority-1 SpearUseGoal instead of exclusively relying on the priority-2 ZombieAttackGoal. SpearUseGoal owns MOVE/LOOK and has approach/engagement/reposition states. Thus even a correctly acquired snow-golem target need not produce the old continuous melee pursuit. This is an additional candidate for interference with an aggro-driven elevator, not established as its cause. No spear-AI rollback is shipped. No blanket ladder or trapdoor rollback is justified by the WalkNodeEvaluator comparison performed here; the inspected node-evaluation code does not establish such a change.

This follow-up changes the diagnosis, not the built jars. Restoring dimension-local aggro should help the old elevator, but the mod still does not establish complete restoration of old elevator behavior. A new-elevator build avoids needing the snow-golem handoff but does not itself remove spear AI elsewhere in the farm.
