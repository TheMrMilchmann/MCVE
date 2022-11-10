plugins {
    java
    alias(libs.plugins.monticore)
}

repositories {
    mavenCentral()
    maven(url = "https://nexus.se.rwth-aachen.de/content/groups/public")
}

dependencies {
    implementation(libs.monticore.grammar)
    implementation(libs.monticore.runtime)

    grammar(libs.monticore.grammar) {
        capabilities {
            requireCapabilities("de.monticore:monticore-grammar-grammars")
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

sourceSets["main"].java.srcDir("$buildDir/generated-sources/monticore/sourcecode")

val grammarsDir = file("src/main/grammars")

tasks {
    val generate = create<de.monticore.MCTask>("generate") {
        val grammarFile = File(grammarsDir, "Foo.mc4")

        grammar.set(grammarFile)
        outputDir.set(file("$buildDir/generated-sources/monticore/sourcecode"))
    }

    compileJava {
        dependsOn(generate)
    }
}