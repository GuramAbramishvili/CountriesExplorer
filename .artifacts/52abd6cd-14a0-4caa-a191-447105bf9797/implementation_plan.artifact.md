# Fix Unresolved Reference in AppNavigation.kt

The project is failing to build because of an unresolved reference `rememberSaveableStateHolderNavEntryDecorator` in `AppNavigation.kt`. This symbol appears to have been renamed in the version of Navigation 3 being used (`1.0.0-alpha08`).

## Proposed Changes

### [Component: Navigation]

#### [MODIFY] [AppNavigation.kt](file:///C:/Users/User/Pictures/CountriesExplorer/app/src/main/java/ge/example/countriesexplorer/navigation/AppNavigation.kt)

- Update the import for the state-saving decorator.
- Update the call site in the `NavDisplay` `entryDecorators` list.

Specifically:
- Change `import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator` to `import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator`.
- Change `rememberSaveableStateHolderNavEntryDecorator()` to `rememberSavedStateNavEntryDecorator()`.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:compileDebugKotlin` to verify that the unresolved reference error is resolved.

### Manual Verification
- None required as this is a compilation fix.
