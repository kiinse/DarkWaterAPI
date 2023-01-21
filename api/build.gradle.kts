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
    implementation("org.json:json:20220924")
    implementation("org.springframework:spring-core:6.0.3")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.jooq:jooq:3.17.6")
    implementation("org.jooq:jooq-meta:3.17.6")
    implementation("org.jooq:jooq-codegen:3.17.6")
    implementation("org.postgresql:postgresql:42.5.1")

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