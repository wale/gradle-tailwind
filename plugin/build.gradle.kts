/**
 *    Copyright 2023-present Duale Siad
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm") version "2.3.21"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "2.1.1"
}

group = "au.id.wale"
version = "0.4.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    this.jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")
}

tasks.named<KotlinJvmCompile>("compileKotlin"){
    compilerOptions {
        jvmTarget = JvmTarget.JVM_1_8
        languageVersion = KotlinVersion.fromVersion("2.3")
        apiVersion = KotlinVersion.fromVersion("2.3")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

gradlePlugin {
    website.set("https://codeberg.org/wale/gradle-tailwind")
    vcsUrl.set("https://codeberg.org/wale/gradle-tailwind")
    plugins {
        create("tailwind") {
            id = "au.id.wale.tailwind"
            displayName = "TailwindCSS Gradle Plugin"
            description = "A Gradle plugin to manage TailwindCSS files."
            tags.set(listOf("tailwind", "css", "web", "frontend"))
            implementationClass = "au.id.wale.tailwind.TailwindPlugin"
        }
    }
}