# Always Create User

## Description

This Xposed module enables you to bypass user creation restrictions on Android, allowing you to
create more user profiles, managed profiles, and clones even when the system normally prevents it.

It overcomes limitations like:

- "Cannot add more profiles of type android.os.usertype.profile.(MANAGED | CLONE | PRIVATE) for user
  0"
- "Cannot add user. Maximum user limit is reached."
- "Cannot add more managed profiles for user"
- "Error: couldn't create User."

## How it Works

This module achieves its functionality by overriding specific methods within the Android framework
responsible for user creation:

- **Android 14+:** Hooks the `isCreationOverrideEnabled` method to always allow user creation.
- **Android 11-13:** Hooks `canAddMoreProfilesToUser` and `isUserLimitReached` for similar
  functionality, superseded by `isCreationOverrideEnabled` on Android 14+.
- **Android 6-10:** Hooks `canAddMoreManagedProfiles`, which was later replaced by
  `canAddMoreProfilesToUser` in newer Android versions.
- **Android 4.2-6:** Hooks `isUserLimitReachedLocked`, renamed to `isUserLimitReached` in later
  versions.

By manipulating these methods, the module effectively removes restrictions on the number and types
of user profiles you can create.

## Screenshot

| ![Island](/docs/img/Island.png) | ![Thanox](/docs/img/Thanox.png) |
|---------------------------------|---------------------------------|

## Disclaimer

AlwaysCreateUser is provided "as is" without warranty of any kind. Modifying system settings can
potentially lead to instability or unexpected behavior. Use this module at your own risk. The
developer is not responsible for any damage or problems that may arise from using this software.