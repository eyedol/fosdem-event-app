<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/assets/images/cover-dark.png">
  <source media="(prefers-color-scheme: light)" srcset="docs/assets/images/cover-light.png">
  <img alt="app-cover-readme-file" src="docs/assets/images/cover-dark.png">
</picture>


<div style="display: inline-block"  align="center">
	<h1>FOSDEM Event App - unofficial</h1>
</div>

![Latest App Verion](https://img.shields.io/github/v/release/eyedol/fosdem-event-app?style=flat)
[![F-Droid Version](https://img.shields.io/f-droid/v/com.addhen.fosdem.android.app)](https://f-droid.org/en/packages/com.addhen.fosdem.android.app)
[![Kotlin Version](https://img.shields.io/badge/dynamic/toml?url=https://raw.githubusercontent.com/eyedol/fosdem-event-app/main/gradle/libs.versions.toml&query=versions.kotlin&style=flat&logo=kotlin&label=Kotlin)](https://kotlinlang.org)
![Android Platform](https://img.shields.io/badge/platform-android-6EDB8D?style=flat)
![Desktop](https://img.shields.io/badge/platform-desktop-DB413D?style=flat)


[FOSDEM](https://fosdem.org/) is a free annual event held in **Brussels, Belgium** for software developers to meet, share ideas and collaborate.

This is yet another app for the FOSDEM conference, but this one provides a multi-platform client app.

> Disclaimer: The name FOSDEM and the logo are registered trademarks of FOSDEM.


## 💪 Try it out

### Development version

#### Android

You can test the development app through DeployGate.

[<img src="https://dply.me/qgph1r/button/large" alt="Try it on your device via DeployGate">](https://dply.me/qgph1r#install)


### Sample Demo
#### Android

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/assets/images/android-demo-dark.gif" />
  <source media="(prefers-color-scheme: light)" srcset="docs/assets/images/android-demo-light.gif" />
  <img alt="app-cover-readme-file" src="docs/assets/images/android-demo-light.gif" />
</picture>

#### Desktop

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/assets/images/desktop-demo-dark.gif" />
  <source media="(prefers-color-scheme: light)" srcset="docs/assets/images/desktop-demo-light.gif" />
  <img alt="app-cover-readme-file" src="docs/assets/images/desktop-demo-light.gif" />
</picture>

#### iOS

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/assets/images/ios-demo-dark.gif" />
  <source media="(prefers-color-scheme: light)" srcset="docs/assets/images/ios-demo-light.gif" />
  <img alt="app-cover-readme-file" src="docs/assets/images/ios-demo-light.gif" />
</picture>

## 🛠️ Teck stack
1. [Kotlin](https://kotlinlang.org/) – Programming language
2. [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) – Async calls
3. [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) KMP – Multiplatfrom support
4. [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) – Multiplatform UI support
5. [Circuit](https://slackhq.github.io/circuit/) – Multiplatform UI architecture
6. [Ktor](https://ktor.io/) – Network calls
7. [SqlDelight](https://cashapp.github.io/sqldelight/2.0.1/) – Local storage
8. [Kotlin Inject](https://github.com/evant/kotlin-inject) – Dependency injection
9. [Kotlinx Serialization](https://kotlinlang.org/docs/serialization.html) – Serializations
10. [xmlutil](https://github.com/pdvrieze/xmlutil) - XML parsing
11. [KSoup](https://github.com/MohamedRejeb/Ksoup) – HTML parser
12. [Lyricist](https://github.com/adrielcafe/lyricist) – Localization & i18 support
13. [DeployGate](https://deploygate.com) – Development build distributions
14. [Github Actions](https://docs.github.com/en/actions) – CI/CD suppport

## 👩‍💻 Development
To locally build this project requires no additional setup other than having a [kotlin mulitplatform development environment](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-setup.html) setup.
The project requires whatever JDK version is currently defined libs.versions.toml.
If you use [sdkman](https://sdkman.io) there is a `.sdkmanrc` file that will automatically set the JDK version to the one defined in the project.

See the [CONTRIBUTING.md](CONTRIBUTING.md) file for more details on how to build or contribute to this project.

## 🫡 Credits
This project had a lot of inspiration, borrowed a lot of code and ideas from a lot of other people's work. Especially the following projects helped a lot to make this project come to live:

- [DroidKaigi conference-app-2023](https://github.com/DroidKaigi/conference-app-2023) – Borrowed a lot of their code and UIs
- [Tivi App](https://github.com/chrisbanes/tivi) – Borrowed a lot of their code to architect the app
- [FOSDEM companion android](https://github.com/cbeyls/fosdem-companion-android?tab=readme-ov-file) – Lots of inspiration in figuring out how to work with FOSDEM XML-based data
- [NYTimes-KMP](https://github.com/xxfast/NYTimes-KMP) – Used it to figure out how to add WearOS support


## ✍️ Author

👤 **Henry Addo**

* Twitter: <a href="https://twitter.com/eyedol" target="_blank">@eyedol</a>

## 📝 License

```
Copyright © 2024 - Addhen Limited and the FOSDEM Event app project contributors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissio
```
