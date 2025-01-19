# Speak Easy Translator

"Speak Easy Translator" is your go-to app for instant text and speech translation. With support for
multiple languages and an easy-to-use interface, it’s perfect for travel and learning.

- KMP (Kotlin Multi-Platform) Compose-based Proof-of-concept experimental app for iOS and Android.
- Pure Kotlin App using _ComposeApp_ core and _ComposeApp_ Compose based UI, running in native
  Kotlin.

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
![badge-kmp](https://img.shields.io/badge/KMP-1.7.3-blue)
![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)

## Project Structure

 [<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/structure/project_structure.png">]()

### What is Modularization?

* Modularization is the practice of dividing a monolithic codebase into independent, self-contained modules. This approach brings several advantages, such as parallel development, improved build times, better encapsulation, and enhanced reusability.

### Benefits of Modularization
- Scalability – Separation of concerns allows for independent feature development.
- Parallel Development – Multiple developers can work on different modules simultaneously with minimal conflicts.
- Ownership – Each module can have a dedicated owner responsible for its maintenance.
- Encapsulation – Well-defined module boundaries enhance readability, testing, and maintainability.
- Reduced Build Time – Incremental and parallel builds optimize the development workflow.
- Reusability – Shared logic can be used across different apps or platforms.
  
### Potential Pitfalls
- Too Many Modules – Excessive modularization can lead to increased build complexity.
- Too Few Modules – Large, tightly coupled modules reduce the benefits of modularization.
- Overcomplexity – Modularization should be aligned with project needs rather than applied blindly.
  
### Modularization Strategy

* A well-structured module should have low coupling (minimal dependencies on other modules) and high cohesion (a well-defined responsibility). The project follows a layered modularization approach, separating concerns across core and feature-specific modules.

### Core Modules
These modules provide fundamental functionalities and architecture:

* analytics - Handles event tracking and logging.
* common - Contains shared utilities and extensions.
* data - Manages data flow between repository and domain layers.
* database - Implements local storage mechanisms.
* datastore - Provides key-value storage solutions.
* designsystem - Contains UI components and themes.
* domain - Defines business logic and use cases.
* navigation - Manages in-app navigation.
* network - Handles API communication.
* ui - Contains base UI logic.
* ui-components - Reusable UI elements.
 
### Feature Modules
These modules encapsulate specific features of the application:

* camera-capture - Handles camera-based text recognition.
* history - Manages translation history.
* languages - Provides language selection and handling.
* permission - Manages app permissions.
* settings - Implements user settings, including "Help & Support" and "About".
* speech - Handles speech-to-text and text-to-speech functionalities.
* translator - Core translation engine using various APIs.

## Libraries

### Android Jetpack

* [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) An interface
  that automatically responds to lifecycle events.

* [Navigation](https://developer.android.com/guide/navigation?gclsrc=aw.ds&gclid=Cj0KCQiA09eQBhCxARIsAAYRiymyM6hTEs0cGr5ZCXOWtLhVUwDK1O86vf8V_Uq2DWvVYNFZwPFznzAaAllMEALw_wcB)
  Navigation refers to interactions that allow users to navigate through , enter, and exit various
  parts of the content in your app. Navigation component Android Jetpack helps you navigate, from
  simple button clicks to more complex templates like application panels and navigation bar. The
  navigation component also provides a consistent and predictable user interface, adhering to an
  established set of principles.

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) Data related to
  the user interface that is not destroyed when the application is rotated. Easily schedule
  asynchronous tasks for optimal execution.

### Image loader

* [Coil3](https://skydoves.github.io/landscapist/coil3/) Coil3 is a highly optimized,
  pluggable Compose image loading solution.

### Multithreading

* [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) Asynchronous or non-blocking
  programming is an important part of the development landscape. When creating server-side, desktop,
  or mobile applications, it's important to provide an experience that is not only fluid from the
  user's perspective, but also scalable when needed.

### DI

* [Koin](https://insert-koin.io/docs/reference/koin-mp/kmp/)
  Koin is a pure Kotlin library and can be used in your shared Kotlin project.

### Network

* [Ktor](https://ktor.io/docs/client-create-multiplatform-application.html)
  The Ktor HTTP client can be used in multiplatform projects. In this tutorial, we'll create a
  simple Kotlin Multiplatform Mobile application, which sends a request and receives a response body
  as plain HTML text.

### Database

* [Room (Kotlin Multiplatform)](https://developer.android.com/kotlin/multiplatform/room) The Room
  persistence library provides an abstraction layer over SQLite to allow for more robust database
  access while harnessing the full power of SQLite. This page focuses on using Room in Kotlin
  Multiplatform (KMP) projects. For more information on using Room, see Save data in a local
  database using Room or our official samples.

* [DataStore (Kotlin Multiplatform)](https://developer.android.com/kotlin/multiplatform/datastore)
  The DataStore library stores data asynchronously, consistently, and transactionally, overcoming
  some of the drawbacks of SharedPreferences. This page focuses on creating DataStore in Kotlin
  Multiplatform (KMP) projects. For more information on DataStore, see the primary documentation for
  DataStore and official samples.

## Screen Shots

### Android Screenshots

[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/dark_main_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/dark_history_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/dark_setting_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/dark_camera_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/dark_theme_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/dark_about_app_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/dark_language_dialog.png">]()

### iOS Screenshots

[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/light_main_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/light_history_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/light_setting_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/light_theme_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/light_rate_app_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/light_about_app_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/light_language_dialog.png">]()
