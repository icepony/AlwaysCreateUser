# Always create user

[![Xposed Module](https://img.shields.io/badge/Xposed%20Module-✓-green.svg)]()
[![Android Version](https://img.shields.io/badge/Android-4.2%2B-blue.svg)]()
[![GitHub issues](https://img.shields.io/github/issues/icepony/AlwaysCreateUser)](https://github.com/icepony/AlwaysCreateUser/issues)

An Xposed Framework module that bypasses Android's user/profile creation limits

## Features

- Bypass common profile creation errors
  - `Cannot add more profiles of type android.os.usertype.profile.(MANAGED | CLONE | PRIVATE) for user
    0`
  - `Maximum user limit is reached`
  - `Cannot add more managed profiles for user`
  - `Error: couldn't create User`
- Support Android 4.2 through Android 14+
- Compatible with popular profile manager apps (Island, Shelter, etc.)

## Compatibility Overview

The module hooks methods within [
`com.android.server.power.batterysaver.BatterySaverStateMachine`](https://github.com/aosp-mirror/platform_frameworks_base/blob/54642d141f80d495a475b304052eedd2832fcdb1/services/core/java/com/android/server/pm/UserManagerService.java#L5733)

| Android Version | Hook Methods List           |
|-----------------|-----------------------------|
| 14+             | `isCreationOverrideEnabled` |
| 11-13           | `canAddMoreProfilesToUser`  |
| 7-13            | `isUserLimitReached`        |
| 6-10            | `canAddMoreManagedProfiles` |
| 4.2-6           | `isUserLimitReachedLocked`  |

## Screenshot

| [Island](https://github.com/oasisfeng/island) | [Thanox](https://github.com/Tornaco/Thanox) |
|-----------------------------------------------|---------------------------------------------|
| ![Island](/docs/img/Island.png)               | ![Thanox](/docs/img/Thanox.png)             |

### [How to Setup for Island](https://island.oasisfeng.com/setup.html#manual-setup-for-island)

## Check Out My Other Project!

* **[AlwaysBatterySaver](https://github.com/icepony/AlwaysBatterySaver)**: An Xposed module that
  prevents Android from automatically disabling Battery Saver mode when the device is charging.

## Thanks

* Xposed Framework Developers
* [CorePatch](https://github.com/LSPosed/CorePatch) (Inspiration for hook structure)
* LLMs (Gemini, DeepSeek, ChatGPT) for assistance.
