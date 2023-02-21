plugins {
    `java-library`
}

val ecj = configurations.create("ecj") {
    isCanBeConsumed = false
    isCanBeResolved = true
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    withType<JavaCompile> {
        /* Overwrite the javaCompiler to make sure that it is not inferred from the toolchain. */
        javaCompiler.set(provider { null })

        /* ECJ does not support generating JNI headers. Make sure the property is not used. */
        options.headerOutputDirectory.set(provider { null })

        afterEvaluate {
            /* The version for which a toolchain is requested if the project's toolchain is not compatible. */
            val PREFERRED_JAVA_VERSION = 17

            /* The version required to run ECJ. */
            val REQUIRED_JAVA_VERSION = 11

            val javaLauncher = if (java.toolchain.languageVersion.orNull?.canCompileOrRun(REQUIRED_JAVA_VERSION) == true) {
                javaToolchains.launcherFor(java.toolchain).orNull ?: error("Could not get launcher for toolchain: ${java.toolchain}")
            } else {
                javaToolchains.launcherFor {
                    languageVersion.set(JavaLanguageVersion.of(PREFERRED_JAVA_VERSION))
                }.orNull ?: error("Could not provision launcher for Java $PREFERRED_JAVA_VERSION")
            }

            options.isFork = true
            options.forkOptions.executable = javaLauncher.executablePath.asFile.absolutePath

            options.forkOptions.jvmArgumentProviders.add(CommandLineArgumentProvider {
                mutableListOf("-cp", ecj.asPath, "org.eclipse.jdt.internal.compiler.batch.Main")
            })
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    ecj("org.eclipse.jdt:ecj:3.32.0")
}