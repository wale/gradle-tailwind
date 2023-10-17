package au.id.wale.tailwind.tasks

import org.gradle.api.GradleException
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RelativePath
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.Path

abstract class TailwindCompileTask : BaseTailwindTask() {
    init {
        description = "Compiles Tailwind sources to a CSS file"
    }

    @get:Input
    @get:Option(option = "input", description = "The path of the Tailwind-compatible CSS file.")
    val input: Property<String> = project.objects.property(String::class.java)

    @get:Input
    @get:Option(option = "output", description = "The desired output path for the built CSS file.")
    val output: Property<String> = project.objects.property(String::class.java)

    @get:Input
    @get:Option(option = "config", description = "The desired folder path of the `tailwind.config.js` file.")
    val configPath: Property<String> = project.objects.property(String::class.java)

    @TaskAction
    fun compileTailwind() {
        //val configFile = File(configPath.get(), "tailwind.config.js")
        val configPath = RelativePath.parse(true, configPath.get()).getFile(project.projectDir)
        val configFile = Path(configPath.path, "tailwind.config.js").toFile()
        val input = RelativePath.parse(true, input.get()).getFile(project.projectDir)
        val output = RelativePath.parse(true, output.get()).getFile(project.projectDir)
        if (!input.exists()) {
            throw GradleException("The input file ${input.path} does not exist.")
        } else {
            val args = arrayListOf<String>()

            args += "-i"
            args += input.absolutePath
            args += "-o"
            args += output.absolutePath
            args += "-c"
            args += configFile.absolutePath

            project.exec {
                //it.workingDir = File(configPath.get())
                it.workingDir = project.projectDir
                it.executable = getBinary()
                it.args = args
            }
        }
    }
}