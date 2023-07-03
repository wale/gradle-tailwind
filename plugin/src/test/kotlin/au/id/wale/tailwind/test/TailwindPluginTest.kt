package au.id.wale.tailwind.test

import au.id.wale.tailwind.TailwindExtension
import au.id.wale.tailwind.tasks.TailwindDownloadTask
import au.id.wale.tailwind.tasks.TailwindInitTask
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TailwindPluginTest {
    @Test
    fun `tailwindDownload task is applied correctly to the project`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("au.id.wale.tailwind")

        assert(project.tasks.getByName("tailwindDownload") is TailwindDownloadTask)
    }

    @Test
    fun `tailwindInit task is applied correctly to the project`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("au.id.wale.tailwind")

        assert(project.tasks.getByName("tailwindInit") is TailwindInitTask)
    }

    @Test
    fun `check if parameters are passed correctly to plugin`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("au.id.wale.tailwind")

        (project.extensions.getByName("tailwind") as TailwindExtension).apply {
            this.version.set("3.4.3")
            this.configPath.set("src/main/resources")
            this.input.set("src/app/tailwind.css")
            this.output.set("${project.buildDir}/assets/app.css")
        }

        val ext = project.extensions.getByName("tailwind") as TailwindExtension

        assertEquals("src/main/resources", ext.configPath.get())
        assertEquals("src/app/tailwind.css", ext.input.get())
        assertEquals("${project.buildDir}/assets/app.css", ext.output.get())
    }
}