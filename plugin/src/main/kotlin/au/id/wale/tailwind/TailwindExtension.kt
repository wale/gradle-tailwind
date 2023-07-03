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

import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 * Container for the Tailwind extension Gradle DSL.
 *
 * @author Duale Siad
 */

abstract class TailwindExtension @Inject constructor(project: Project) {
    var version: Property<String> = project.objects.property(String::class.java)
    var configPath: Property<String> = project.objects.property(String::class.java)
    var input: Property<RegularFile> = project.objects.fileProperty()
    var output: Property<RegularFile> = project.objects.fileProperty()
}