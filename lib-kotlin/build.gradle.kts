plugins {
  kotlin("jvm") version "2.3.0"
  kotlin("kapt") version "2.3.0"
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

dependencies {
  kapt(project(":apt"))
}
