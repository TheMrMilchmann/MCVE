rootProject.name = "b"

includeBuild("../a") {
    dependencySubstitution {
        substitute(module("com.example:a")).using(project(":"))
    }
}