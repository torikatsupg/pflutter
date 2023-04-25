import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "me.torikatsu"
version = "1.0-SNAPSHOT"

val skArtifact = "skiko-awt-runtime-macos-arm64"
val skVersion = "0.7.18"
val lwjglVersion = "3.3.1"
val lwjglNatives = "natives-macos-arm64"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    testImplementation(kotlin("test"))
    api("org.jetbrains.skiko:${skArtifact}:${skVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    implementation("org.lwjgl:lwjgl:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-glfw:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-opengl:$lwjglVersion")

    runtimeOnly("org.lwjgl:lwjgl:$lwjglVersion:$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-glfw:$lwjglVersion:$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-opengl:$lwjglVersion:$lwjglNatives")
}

tasks {
    val run by existing(JavaExec::class) {
        jvmArgs("-XstartOnFirstThread")
    }
}

tasks.test {
    useJUnit()
}

application {
    mainClass.set("MainKt") 
}


tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
