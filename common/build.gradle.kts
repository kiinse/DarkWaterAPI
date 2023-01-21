plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("signing")
    id("maven-publish")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("com.github.johnrengelman.shadow")
}

group = "kiinse.me.plugins.darkwaterapi"
version = "4.0.0-beta.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://repo.maven.apache.org/maven2/") }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":core"))

    implementation("com.vdurmont:semver4j:3.1.0")
    implementation("org.json:json:20220924")

    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.10.9")
}

kapt {
    useBuildCache = false
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "KiinseRepository"
            url = uri(if(isSnapshot()) "https://repo.kiinse.me/snapshots" else "https://repo.kiinse.me/releases")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

fun isSnapshot(): Boolean {
    val version = version.toString()
    return version.contains("beta") || version.contains("alpha") || version.contains("rc") || version.contains("SNAPSHOT")
}