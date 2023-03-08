import net.minecraftforge.gradle.userdev.UserDevExtension
import org.spongepowered.asm.gradle.plugins.MixinExtension

buildscript {
    repositories {
        mavenCentral()

        maven("https://maven.minecraftforge.net/")
        maven("https://files.minecraftforge.net/maven/")
    }

    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:4.+")
        classpath("org.spongepowered:mixingradle:0.7.+")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    }
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://files.minecraftforge.net/maven/")
}

plugins {
    java
    kotlin("jvm") version "1.7.20"
}

apply {
    plugin("net.minecraftforge.gradle")
    plugin("org.spongepowered.mixin")
    plugin("kotlin")
    plugin("idea")
}

group = "dev.cart"
version = "1.0"

configurations.create("libraries")

dependencies {
    "minecraft"("net.minecraftforge:forge:1.12.2-14.23.5.2860")

    "libraries"("org.spongepowered:mixin:0.8") {
        exclude("module", "guava")
        exclude("module", "gson")
        exclude("module", "commons-io")
    }

    annotationProcessor("org.spongepowered:mixin:0.8:processor") {
        exclude("module", "gson")
    }

    testAnnotationProcessor("org.spongepowered:mixin:0.8:processor") {
        exclude("module", "gson")
    }

    "libraries"("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.20") {
        exclude("module", "kotlin-stdlib-common")
        exclude("module", "annotations")
    }

    "libraries"("org.jetbrains.kotlin:kotlin-reflect:1.7.20") {
        exclude("module", "kotlin-stdlib")
    }

    "libraries"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4") {
        exclude("module", "kotlin-stdlib-jdk8")
        exclude("module", "kotlin-stdlib-common")
    }

    "libraries"("com.github.therealbush:eventbus-kotlin:v1.0.1") {
        exclude("module", "log4j-api")
        exclude("module", "log4j-core")
    }

    implementation(configurations.getByName("libraries"))
}

configure<UserDevExtension> {
    mappings("snapshot", "20180304-1.12")

    runs {
        create("client") {
            workingDirectory = project.file("run").path

            properties(
                mapOf(
                    "forge.logging.markers" to "SCAN,REGISTRIES,REGISTRYDUMP",
                    "forge.logging.console.level" to "debug",
                    "fml.coreMods.load" to "dev.cart.mixin.MixinLoader"
                )
            )
        }
    }
}

configure<MixinExtension> {
    defaultObfuscationEnv = "searge"
    add(sourceSets["main"], "mixins.smartproxy.refmap.json")
    config("mixins.smartproxy.json")
}

tasks.processResources {
    from(sourceSets["main"].resources.srcDirs) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        include("mcmod.info")
        expand(mapOf("version" to version, "mcversion" to "1.12.2"))
    }
}

tasks.jar {
    manifest.attributes(
        "Manifest-Version" to "1.0",
        "MixinConfigs" to "mixins.smartproxy.json",
        "TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
        "FMLCorePluginContainsFMLMod" to "true",
        "FMLCorePlugin" to "dev.cart.mixin.MixinLoader",
        "ForceLoadAsMod" to "true"
    )

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(
        configurations.getByName("libraries").map {
            if (it.isDirectory) it else zipTree(it)
        }
    )
}


sourceSets["main"].java.srcDir("src/main/kotlin")