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

package au.id.wale.tailwind.platform

import org.gradle.internal.os.OperatingSystem

open class TailwindPlatform {
    companion object {
        val platformOS: TailwindOS
            get() {
                return when (OperatingSystem.current()) {
                    OperatingSystem.MAC_OS -> TailwindOS.MAC
                    OperatingSystem.LINUX -> TailwindOS.LINUX
                    OperatingSystem.WINDOWS -> TailwindOS.WINDOWS
                    else -> TailwindOS.UNKNOWN
                }
            }

        val platformArch: TailwindArch
            get() {
                return when (System.getProperty("os.arch")) {
                    "x86_64", "amd64" -> TailwindArch.X86_64
                    "aarch32", "arm" -> TailwindArch.AARCH32
                    "aarch64", "arm64" -> TailwindArch.AARCH64
                    else -> TailwindArch.UNKNOWN
                }
            }
    }

    /**
     * Formats a given TailwindCSS version to match the name with the Tailwind binary.
     */
    fun format(): String {
        val archName = platformArch.binaryArch
        val osName = platformOS.binaryOS
        val format = "tailwindcss-$osName-$archName"
        return if (platformOS == TailwindOS.WINDOWS) "$format.exe" else format
    }

    fun formatFolder(version: String): String {
        return "tailwindcss-$version-${platformOS.binaryOS}"
    }
}