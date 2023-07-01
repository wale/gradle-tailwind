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

package au.id.wale.tailwind.tasks

import au.id.wale.tailwind.TailwindPlugin
import au.id.wale.tailwind.platform.TailwindOS
import au.id.wale.tailwind.platform.TailwindPlatform
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption


abstract class TailwindDownloadTask : DefaultTask() {

    init {
        description = "Downloads and caches a given TailwindCSS binary."
        group = TailwindPlugin.PLUGIN_GROUP

        onlyIf { !getBinary().get().asFile.exists() }
    }

    @get:Input
    @get:Option(option = "version", description = "The current Tailwind version to download.")
    val version: Property<String> = project.objects.property(String::class.java)

    @get:InputDirectory
    @get:Option(option = "cacheDir", description = "The directory where the Tailwind binaries are cached")
    val cacheDir: Property<Path> = project.objects.property(Path::class.java)


    private val formattedName = TailwindPlatform().format()

    private val BASE_URL = "https://github.com/tailwindlabs/tailwindcss/releases/download"

    @TaskAction
    fun downloadTailwind() {
        Files.createDirectories(getBinary().get().asFile.toPath().parent)

        URL("${BASE_URL}/v${version.get()}/${formattedName}").openStream().use { `in` ->
            Files.copy(
                `in`,
                getBinary().get().asFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
        }

        // Set the downloaded Tailwind executable to be `true`.
        if ((TailwindPlatform.platformOS == TailwindOS.LINUX) || (TailwindPlatform.platformOS == TailwindOS.MAC)) {
            getBinary().get().asFile.setExecutable(true)
        }
    }


    @OutputFile
    fun getBinary(): Provider<RegularFile> {
        return project.layout.file(cacheDir.map { dir ->
            dir.resolve(version.get())
                .resolve(formattedName)
                .toFile()
        })
    }
}