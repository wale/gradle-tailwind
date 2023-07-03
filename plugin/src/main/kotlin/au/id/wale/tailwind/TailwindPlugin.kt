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

package au.id.wale.tailwind

import au.id.wale.tailwind.tasks.TailwindDownloadTask
import au.id.wale.tailwind.tasks.TailwindInitTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.createDirectories
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

class TailwindPlugin : Plugin<Project> {

    companion object {
        const val PLUGIN_GROUP = "Tailwind"
    }

    override fun apply(project: Project) {
        val extension = project.extensions.create("tailwind", TailwindExtension::class.java, project)

        val cacheDirectory: Path = Optional.ofNullable(project.findProperty("au.id.wale.tailwind.cache.dir") as String?)
            .map(Paths::get)
            .orElseGet {
                project.gradle
                    .gradleUserHomeDir
                    .toPath()
                    .resolve("caches")
                    .resolve("au.id.wale.tailwind")
            }

        if (!cacheDirectory.exists()) cacheDirectory.createDirectories()

        project.tasks.register("tailwindDownload", TailwindDownloadTask::class.java) {
            it.cacheDir.set(cacheDirectory)
            it.version.set(extension.version)
        }

        project.tasks.register("tailwindInit", TailwindInitTask::class.java) {
            it.configPath.set(extension.configPath)
            it.version.set(extension.version)
            it.cacheDir.set(cacheDirectory)
        }
    }
}