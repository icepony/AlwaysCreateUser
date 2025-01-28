# Always Create User

[![Xposed Module](https://img.shields.io/badge/Xposed%20Module-✓-green.svg)](https://repo.xposed.info/)
[![Android Version](https://img.shields.io/badge/Android-4.2%2B-blue.svg)]()

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

| ![Island](https://testingcf.jsdelivr.net/gh/Xposed-Modules-Repo/io.github.icepony.alwayscreateuser@main/docs/img/Island.png) | ![Thanox](https://testingcf.jsdelivr.net/gh/Xposed-Modules-Repo/io.github.icepony.alwayscreateuser@main/docs/img/Thanox.png) |
|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|