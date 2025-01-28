# Always Create User

[![Xposed Module](https://img.shields.io/badge/Xposed%20Module-✓-green.svg)]()
[![Android Version](https://img.shields.io/badge/Android-4.2%2B-blue.svg)]()
[![GitHub issues](https://img.shields.io/github/issues/icepony/AlwaysCreateUser)](https://github.com/icepony/AlwaysCreateUser/issues)

An Xposed Framework module that bypasses Android's user/profile creation limits

## Features

- Bypass common profile creation errors
- Support Android 4.2 through Android 14+
- Compatible with popular profile apps (Island, Shelter, etc.)

## Common Errors Resolved

- "Cannot add more profiles of type android.os.usertype.profile.(MANAGED | CLONE | PRIVATE) for user
  0"
- "Maximum user limit is reached"
- "Cannot add more managed profiles for user"
- "Error: couldn't create User"

## Compatibility Overview

| Android Version | Supported Methods           |
|-----------------|-----------------------------|
| 14+             | `isCreationOverrideEnabled` |
| 11-13           | `canAddMoreProfilesToUser`  |
| 7-13            | `isUserLimitReached`        |
| 6-10            | `canAddMoreManagedProfiles` |
| 4.2-6           | `isUserLimitReachedLocked`  |

## Screenshot

| ![Island](/docs/img/Island.png) | ![Thanox](/docs/img/Thanox.png) |
|---------------------------------|---------------------------------|

### How to Setup for Island

Open Terminal and Follow the Procedure

1. `pm create-user --profileOf 0 --managed Island`

If succeed, you will be prompted with the ID of newly created user (usually 10 or above). Remember
it and replace the <user id> in following commands with this ID.

If you got “Error: couldn’t create User”, execute setprop fw.max_users 10 first, then retry the
command above.

2. `pm install-existing --user <user id> com.oasisfeng.island`

3. `dpm set-profile-owner --user <user id> com.oasisfeng.island/.IslandDeviceAdminReceiver`

If you get error message java.lang.SecurityException: Neither user 2000 nor current process has
android.permission.MANAGE_DEVICE_ADMIN, please review
the [MIUI-specific Preparation](https://island.oasisfeng.com/setup.html#manual-setup-for-island)”.

4. `am start-user <user id>`

If all goes well, Island will show the app list.

## Thanks

- [Gemini](https://gemini.google.com/app)
- [DeepSeek](https://www.deepseek.com/)
- [CorePath](https://github.com/LSPosed/CorePatch)