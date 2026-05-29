package au.id.wale.tailwind.tasks

import org.gradle.api.GradleException
import org.gradle.api.file.RelativePath
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option


abstract class TailwindCompileTask : BaseTailwindTask() {
    init {
        description = "Compiles Tailwind sources to a CSS file"
        dependsOn("tailwindDownload")
    }

    @get:Input
    @get:Option(option = "input", description = "The path of the Tailwind-compatible CSS file.")
    val input: Property<String> = project.objects.property(String::class.java)

    @get:Input
    @get:Option(option = "output", description = "The desired output path for the built CSS file.")
    val output: Property<String> = project.objects.property(String::class.java)

    @TaskAction
    fun compileTailwind() {
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

            project.providers.exec {
                it.workingDir = project.projectDir
                it.executable = getBinary()
                it.args = args
            }
        }
    }
}