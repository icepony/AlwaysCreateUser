# Always create user

A Module For Xposed Framework

Let `pm create-user` bypass some checks.

## Description

Xposed Api 82

`isCreationOverrideEnabled` Android 14+
`canAddMoreProfilesToUser` Android 11 - 13 (Override by `isCreationOverrideEnabled`)
`isUserLimitReached` Android 7 - 13 (Override by `isCreationOverrideEnabled`)
`canAddMoreManagedProfiles` Android 6 - 10 (Move to `canAddMoreProfilesToUser`)
`isUserLimitReachedLocked` Android 4.2 - 6 (Rename to `isUserLimitReached`)

## Screenshot

| ![Island](/docs/img/Island.png) | ![Thanox](/docs/img/Thanox.png) |
|---------------------------------|---------------------------------|