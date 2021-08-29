rootProject.name = "c"

includeBuild("../b") {
    dependencySubstitution {
        substitute(module("com.example:b")).using(project(":"))
    }
}