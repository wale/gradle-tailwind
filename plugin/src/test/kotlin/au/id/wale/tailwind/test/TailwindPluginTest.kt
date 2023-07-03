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
}