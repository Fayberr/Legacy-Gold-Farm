# Mechanics

The reference is potato_noir's [1.21 gold farm](https://www.youtube.com/watch?v=wLLTKyptV0k). Source inspection used official Mojang jars and mappings, with javap and CFR. Decompiled Mojang sources are not distributed here.

## Baby geometry

In 1.21.11, `ZombifiedPiglin.BABY_DIMENSIONS` is the adult type's dimensions scaled by 0.5, with eye height 0.97. This produces a 0.3 x 0.975 baby and VEHICLE attachment Y -0.35.

26.1 changes this to width 0.49, height 0.99 and eye height 0.78, with VEHICLE attachment Y +0.1875. In 26.3 the height is 0.98, with the same attachment. `Entity.positionRider` subtracts that attachment from the boat seat position, lowering the baby's feet by 0.5375 blocks. Mob drops originate at those feet.

The unchanged item escape code can push embedded items sideways. Lowering the drop origin into the boat is therefore a plausible explanation for occasional spillage.

The baby setting restores the old dimensions and attachments. It affects babies outside the farm too, including collision, reach and riding position. It does not restore the old visual model.

Sources: [26.1 snapshot 7](https://www.minecraft.net/en-us/article/minecraft-26-1-snapshot-7), [snapshot 8](https://www.minecraft.net/en-us/article/minecraft-26-1-snapshot-8), official 1.21.11/26.x `ZombifiedPiglin`, `Entity`, `ItemEntity` bytecode.

## Target handoff and timers

1.21.10 resolves saved anger UUIDs with `ServerLevel.getEntity`, in the current dimension. In 1.21.11+, `NeutralMob.readPersistentAngerSaveData` uses `EntityReference.getLivingEntity`; its Level resolver explicitly calls `getEntityInAnyDimension`. The cached reference also lacks a dimension check. A piglin can consequently recover the Nether llama as its live target after entering the Overworld and pursue its coordinates in that dimension.

Old anger starts a timer when the target changes and pauses countdown for player targets. The newer update refreshes anger while a mob target remains present too. That allows the foreign-target problem to persist.

Local anger targets changes target resolution/validation only for zombified piglins. Legacy anger timers separately restores target-change and player-only pause rules, including remaining duration on dimension transfer. Modern absolute-time save serialization remains. Each toggle works independently. Restoring local targets also changes contraptions that intentionally rely on cross-dimension pursuit; countdown changes can require repeated provocation in other aggro setups.

The original elevator relies on a local snow-golem target. These changes provide a concrete way its target handoff can fail. No separate ladder or trapdoor change was established by the inspected WalkNodeEvaluator comparison.

Source: [MC-312355](https://bugs.mojang.com/browse/MC/issues/MC-312355), [readable report](https://mojira.dev/MC-312355), official 1.21.10/1.21.11 `NeutralMob` and `EntityReference` bytecode.

## Portal contact

The author's pinned comment describes boat stacks no longer teleporting immediately in 1.21.5. Mojang subsequently reverted that thin-contact change: the [1.21.6 release notes](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-6) list MC-295395, MC-295895 and MC-296099. In the inspected 26.1, 26.2 and 26.3 code, NetherPortalBlock does not override `getEntityInsideCollisionShape`; BlockBehaviour returns `Shapes.block()`.

There is no remaining thin portal shape to revert in these versions. The unnecessary optional boat polling added in 0.2.0 was removed in 0.2.1. Portal contact, cooldowns and linking remain entirely vanilla.

## Spear movement

[1.21.11](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-11) added spears and their attack AI. Zombified piglins register SpearUseGoal at priority 1 and ZombieAttackGoal at priority 2. The spear goal owns MOVE/LOOK and switches between approach, engagement and reposition states. This differs from ordinary melee pursuit and could interfere with aggro-based transport. It is not established as the cause of the old elevator failure.

Piglin melee pursuit prevents that spear goal from starting or continuing for zombified piglins. The scheduler stops an active goal through vanilla cleanup, then the existing melee goal can run. It does not delete the goal, replace held items, suppress spear spawns or modify loot. Turning it off lets the spear goal start again normally.

Side effects are intentional: all spear-equipped zombified piglins in the installation lose their charging/reposition combat behavior while enabled. Farms using spear charges can stop working. Their held-item attributes and enchantments remain current, so this is a movement/attack-goal revert, not a full pre-spear combat backport. Other spear-using mobs and players are unaffected. The option defaults off.

[MC-305570](https://mojira.dev/MC-305570) also reports friendly-fire infighting caused by piglin spear charges (closed as a duplicate of intended behavior). Removing the spear goal prevents that attack source, without globally changing retaliation rules.
