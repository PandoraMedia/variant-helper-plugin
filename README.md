# Introduction

The __Build Variant Quick-Selector__ Android Studio plugin helps make changing your selected `Build Variant` much simpler when your project has multiple `Build Flavor` and/or `Build Type` relationships.

# How to install?

The plugin is published under the *JetBrains Plugins Repository* (see [here](https://plugins.jetbrains.com/plugin/14450-build-variant-quick-selector))
and can be installed following these simple steps:

1. Open __Settings__ menu (`Ctrl Alt S`). 
2. Access __Plugins__ section.
3. Click __Browse repositories...__ button.
4. Search for __Build Variant Quick-Selector__ and click __Install__ button.


# How do I use it?

This plugin adds a new *Switch All Build Variants...* menu action right after *Select Build Variant...* in the *Build* menu.  
- Also accessible via `Command + Shift + V`
- Finds common variant scopes for all modules
- Select a fully-qualified variant name for each type listed
- Wait for any `Gradle` sync to complete

![Menu location of command](screenshots/switch_menu.png?raw=true)

![Variant selection dialog](screenshots/variant_selection.png?raw=true)

The plugin invokes the same updates as a single variant change from the `Build Variants` tool window.
