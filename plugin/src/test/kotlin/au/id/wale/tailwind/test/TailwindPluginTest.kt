package au.id.wale.tailwind.test

import au.id.wale.tailwind.TailwindExtension
import au.id.wale.tailwind.tasks.TailwindDownloadTask
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class TailwindPluginTest {
    @Test
    fun `plugin is applied correctly to the project`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("au.id.wale.tailwind")

        assert(project.tasks.getByName("tailwindDownload") is TailwindDownloadTask)
    }

    @Test
    fun `properly download plugin`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("au.id.wale.tailwind")

        (project.extensions.getByName("tailwind") as TailwindExtension).apply {
            this.version.set("3.4.3")
            this.config.set("src/main/resources/tailwind.config.js")
            this.input.set("src/app/tailwind.css")
            this.output.set("${project.buildDir}/assets/app.css")
        }.run {  }

        assertTrue { project.state.executed }
    }
}