# Changelog

## 0.2.1

- Remove the unnecessary boat portal polling option and mixin. Portal behavior is vanilla on all supported versions.
- Existing config files remain compatible; the removed field is ignored and disappears on the next save.

## 0.2.0

- Add a master switch and separate toggles for each mechanic.
- Add Mod Menu settings through Cloth Config or optional Modern Config.
- Add optional zombified piglin melee pursuit and legacy boat portal polling, both off by default.
- Refresh existing piglin dimensions after changing geometry settings.
- Correct the portal diagnosis: Mojang already restored full-block contact in 1.21.6.

## 0.1.0

- Restore old baby zombified piglin dimensions and riding attachment.
- Restrict piglin anger targets to their current dimension.
- Restore non-player anger countdown and carry remaining anger across dimension transfers.
