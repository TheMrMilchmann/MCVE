plugins {
    `java-library`
	application
}

application {
	mainClass.set("com.example.Main")
	mainModule.set("com.example")
}

repositories {
	mavenCentral()
}

dependencies {
	api("org.lwjgl:lwjgl:3.2.0")
	api("org.lwjgl:lwjgl:3.2.0:natives-windows")
}