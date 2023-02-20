rootProject.name = "sandbox"

includeBuild("../") {
    dependencySubstitution {
        substitute(module("com.example:library")).using(project(":library"))
    }
}