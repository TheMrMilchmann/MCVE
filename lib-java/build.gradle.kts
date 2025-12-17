plugins {
  java
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

dependencies {
  annotationProcessor(project(":apt"))
}
