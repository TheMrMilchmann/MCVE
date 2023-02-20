rootProject.name = "Example"

includeBuild("sandbox") {
    dependencySubstitution {
        substitute(module("com.example:sandbox")).using(project(":"))
    }
}

include(":library")