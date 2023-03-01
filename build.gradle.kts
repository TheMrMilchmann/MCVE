plugins {
    `java-library`
}

val ecj = configurations.create("ecj") {
    isCanBeConsumed = false
    isCanBeResolved = true
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        if (GradleVersion.current() < GradleVersion.version("8.0")) {
            /* Overwrite the javaCompiler to make sure that it is not inferred from the toolchain. */
            javaCompiler.set(provider { null })
        }

        /* ECJ does not support generating JNI headers. Make sure the property is not used. */
        options.headerOutputDirectory.set(provider { null })

        options.isFork = true
        options.forkOptions.jvmArgumentProviders.add(CommandLineArgumentProvider {
            mutableListOf("-cp", ecj.asPath, "org.eclipse.jdt.internal.compiler.batch.Main")
        })

        /* The version for which a toolchain is requested if the project's toolchain is not compatible. */
        val PREFERRED_JAVA_VERSION = 17

        /* The version required to run ECJ. */
        val REQUIRED_JAVA_VERSION = 11

        /*
         * forkOptions.executable is, unfortunately, not a property. Setting it eagerly here (at configuration time)
         * would be a bad decision and could lead to ordering issues. Thus, we create a provider here instead.
         * However, we still have to register this provider as task input for proper incremental builds.
         * Unfortunately, it is not possible to replicate the functionality of @Nested for programmatically defined
         * task inputs. Hence, we can only use the languageVersion for now. This could lead to some undesired cache
         * hits but the chance should be low and there is very little risk of this being an issue.
         */
        val javaLauncher = provider {
            if (java.toolchain.languageVersion.orNull?.canCompileOrRun(REQUIRED_JAVA_VERSION) == true) {
                project.javaToolchains.launcherFor(java.toolchain).orNull ?: error("Could not get launcher for toolchain: ${java.toolchain}")
            } else {
                project.javaToolchains.launcherFor {
                    languageVersion.set(JavaLanguageVersion.of(PREFERRED_JAVA_VERSION))
                }.orNull ?: error("Could not provision launcher for Java $PREFERRED_JAVA_VERSION")
            }
        }

        inputs.property("javaLauncher", javaLauncher.map { it.metadata.languageVersion.asInt() })

        @Suppress("ObjectLiteralToLambda")
        doFirst(object : Action<Task> {
            override fun execute(t: Task) {
                options.forkOptions.executable = javaLauncher.get().executablePath.asFile.absolutePath
            }
        })
    }
}

repositories {
    mavenCentral()
}

dependencies {
    ecj("org.eclipse.jdt:ecj:3.32.0")
}