package au.id.wale.tailwind.tasks

import org.gradle.api.GradleException
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

abstract class TailwindCompileTask : BaseTailwindTask() {
    init {
        description = "Compiles Tailwind sources to a CSS file"
    }

    @get:Input
    @get:Option(option = "input", description = "The path of the Tailwind-compatible CSS file.")
    val input: Property<RegularFile> = project.objects.fileProperty()

    @get:Input
    @get:Option(option = "output", description = "The desired output path for the built CSS file.")
    val output: Property<RegularFile> = project.objects.fileProperty()

    @get:Input
    @get:Option(option = "config", description = "The desired folder path of the `tailwind.config.js` file.")
    val configPath: Property<String> = project.objects.property(String::class.java)

    @TaskAction
    fun compileTailwind() {
        val configFile = File(configPath.get(), "tailwind.config.js")

        if (!input.get().asFile.exists()) {
            throw GradleException("The input file ${input.get().asFile.absolutePath} does not exist.")
        } else {
            val args = arrayListOf<String>()
            args += "-i"
            args += input.get().asFile.absolutePath
            args += "-o"
            args += output.get().asFile.absolutePath
            args += "-c"
            args += File(project.projectDir, configFile.path).absolutePath

            project.exec {
                it.workingDir = File(configPath.get())
                it.executable = getBinary()
                it.args = args
            }
        }
    }
}