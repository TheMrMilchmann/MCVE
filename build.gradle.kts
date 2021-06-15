plugins {
    kotlin("multiplatform") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
}

kotlin {
    js(LEGACY)

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
            }
        }

        commonMain {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
            }
        }
    }

}

repositories {
    mavenCentral()
}