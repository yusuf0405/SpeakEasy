# Speak Easy Translator

"Speak Easy Translator" is your go-to app for instant text and speech translation. With support for
multiple languages and an easy-to-use interface, it’s perfect for travel and learning.

- KMP (Kotlin Multi-Platform) Compose-based Proof-of-concept experimental app for iOS and Android.
- Pure Kotlin App using _ComposeApp_ core and _ComposeApp_ Compose based UI, running in native
  Kotlin.

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
![badge-kmp](https://img.shields.io/badge/KMP-1.6.1.1-blue)
![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)

## Build

* In project, implemented a composite build structure to enhance modularity and improve
build times. This approach allows us to develop and test individual components independently while
maintaining a cohesive project architecture. It simplifies dependency management and facilitates
collaboration among team members.

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

[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/light_main_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/light_language_dialog.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/light_language_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/dark_main_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/dark_language_dialog.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/android/dark_language_screen.png">]()

### iOS Screenshots

[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/light_main_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/light_language_dialog.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/light_language_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/dark_main_screen.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/dark_language_dialog.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/SpeakEasy/blob/master/assets/screenshots/ios/dark_language_screen.png">]()