# Always create user

## Description

A Module For Xposed Framework

Let you to bypass some restrictions when creating users, such as:

```
Cannot add more profiles of type android.os.usertype.profile.(MANAGED | CLONE | PRIVATE) for user 0
Cannot add user. Maximum user limit is reached.

Cannot add more managed profiles for user
Error: couldn't create User.
```

### Some details

- `isCreationOverrideEnabled` Android 14+
- `canAddMoreProfilesToUser` Android 11 ~ 13 (Override by `isCreationOverrideEnabled`)
- `isUserLimitReached` Android 7 ~ 13 (Override by `isCreationOverrideEnabled`)
- `canAddMoreManagedProfiles` Android 6 ~ 10 (Move to `canAddMoreProfilesToUser`)
- `isUserLimitReachedLocked` Android 4.2 ~ 6 (Rename to `isUserLimitReached`)

## Screenshot

| ![Island](/docs/img/Island.png) | ![Thanox](/docs/img/Thanox.png) |
|---------------------------------|---------------------------------|