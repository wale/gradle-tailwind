package au.id.wale.tailwind.tasks

import au.id.wale.tailwind.TailwindPlugin
import au.id.wale.tailwind.platform.TailwindPlatform
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.options.Option
import java.nio.file.Path

abstract class BaseTailwindTask : DefaultTask() {
    init {
        group = TailwindPlugin.PLUGIN_GROUP
    }

    @get:Input
    @get:Option(option = "version", description = "The current Tailwind version to download.")
    val version: Property<String> = project.objects.property(String::class.java)

    @get:InputDirectory
    @get:Option(option = "cacheDir", description = "The directory where the Tailwind binaries are cached")
    val cacheDir: Property<Path> = project.objects.property(Path::class.java)

    private val formattedName = TailwindPlatform().format()

    @Input
    protected fun getBinary(): String {
        val file = cacheDir.map { dir ->
            dir.resolve(version.get())
                .resolve(formattedName)
                .toFile()
        }
        return file.get().absolutePath
    }
}