package au.id.wale.tailwind.tasks

import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

abstract class TailwindInitTask : BaseTailwindTask() {
    init {
        description = "Initialises a Tailwind configuration file."
    }

    @get:Input
    @get:Option(option = "config", description = "The desired folder path of the `tailwind.config.js` file.")
    val configPath: Property<String> = project.objects.property(String::class.java)

    @TaskAction
    fun initTailwind() {
        val file = File(configPath.get())
        if (!file.isDirectory) {
            throw GradleException("The path in `tailwind.configPath` is a file, not a directory.")
        } else if(Files.notExists(Path(configPath.get()))) {
            throw GradleException("The path in `tailwind.configPath` does not exist. Create it before running this task.")
        } else {
            val args = arrayListOf<String>()
            args += "init"
            project.exec {
                it.workingDir = file
                it.executable = getBinary()
                it.args = args // TODO: add user-configurable options to the TailwindCSS `init` task.
            }
        }
    }
}