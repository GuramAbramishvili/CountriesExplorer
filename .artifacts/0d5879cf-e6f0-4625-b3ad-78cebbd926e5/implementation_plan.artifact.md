# Fix missing ic_launcher resource

The build is failing because `AndroidManifest.xml` references `@mipmap/ic_launcher`, but the `mipmap` resource directories and the `ic_launcher` file are missing from the project.

## Proposed Changes

### [app module](file:///C:/Users/User/Pictures/CountriesExplorer/app)

I will create a basic adaptive launcher icon to resolve the build error.

#### [NEW] [ic_launcher_background.xml](file:///C:/Users/User/Pictures/CountriesExplorer/app/src/main/res/drawable/ic_launcher_background.xml)
- Create a simple green background vector.

#### [NEW] [ic_launcher_foreground.xml](file:///C:/Users/User/Pictures/CountriesExplorer/app/src/main/res/drawable/ic_launcher_foreground.xml)
- Create a simple white placeholder foreground vector.

#### [NEW] [ic_launcher.xml](file:///C:/Users/User/Pictures/CountriesExplorer/app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml)
- Define the adaptive icon using the background and foreground drawables.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:processDebugResources` to verify that the resource linking error is resolved.
