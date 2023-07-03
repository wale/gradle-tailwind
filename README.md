# `gradle-tailwind`
`gradle-tailwind` is a plugin for the [Gradle](https://gradle.org) build manager for the [TailwindCSS](https://tailwindcss.com) framework.

> **NOTE**
> This Gradle plugin is currently incomplete, so some information listed here does not apply fully yet.
> The plugin is currently *not* on the Gradle plugin marketplace.

## How To Use
### Requirements
- Gradle 6+
- Tailwind 3.0.3+

### Plugin Configuration
The plugin configuration is simple, just see the following section for the relevant DSL for your project:

#### Groovy DSL
First, add your plugin like so:
```groovy
plugins {
    id "au.id.wale.tailwind" version "0.1.0"
}
```
And configure your Tailwind application as desired, like so:
```groovy
tailwind {
    version "3.3.2"
    configPath "src/main/resources"
    input layout.projectDirectory.file("src/main/resources/tailwind/tailwind.css")
    output layout.projectDirectory.file("src/main/resources/css/example.css")
}
```
#### Kotlin DSL
First, add your plugin like so:
```kts
plugins {
    id("au.id.wale.tailwind") version "0.1.0"
}
```
And configure your Tailwind application as desired, like so:
```kts
tailwind {
    version.set("3.3.2")
    configPath.set("src/main/resources")
    input.set(layout.projectDirectory.file("src/main/resources/tailwind/tailwind.css"))
    output.set(layout.projectDirectory.file("src/main/resources/css/example.css"))
}
```

### Available Tasks
- `:tailwindDownload` - Downloads the TailwindCSS binary passed through from the config. Automatically run if the version or the cache folder cannot be found.(and on first run)
- `:tailwindInit` - Initialises the `tailwind.config.js` file with the input and output provided in the config.
- `:tailwindCompile` - Compiles the  given Tailwind PostCSS file to the path provided in `output`.

### Example
There is a working example containing a rudimentary Tailwind project. To compile the CSS and view the HTML properly, run the following task:
```bash
./gradlew :example:tailwindCompile
```

## Development
Because Gradle sucks, the only LTS versions that this build allows for are 8 and 11, due to a known issue [with the tests](https://github.com/gradle/gradle/issues/18647). The plugin itself should build with Java 17 in the meantime.

## License
This project is licensed under the [Apache 2.0](https://github.com/wale/gradle-tailwind/blob/main/LICENSE) license.