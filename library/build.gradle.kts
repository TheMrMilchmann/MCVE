plugins {
    `java-library`
    `maven-publish`
}

configurations.configureEach {
    resolutionStrategy.dependencySubstitution {
        substitute(module("com.example:library")).using(project(":library"))
    }
}

dependencies {
    testImplementation("com.example:sandbox:0.1.0")
}