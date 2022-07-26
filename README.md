# DarkWaterAPI

## Wiki: [Gitlab WIKI](https://gitlab.com/nubilum-development/project-watermelon/DarkWaterAPI/-/wikis/home)

## Adding to your plugin:

<<<<<<< 02df60564d28780afbc258bf87f352deb3ea2278
To connect DarkWaterAPI to the plugin via Maven, you need to write the following in pom.xml:
=======
<<<<<<< bacf75b531469ebad15087f857b474fde0b3137c
=======
<<<<<<< Updated upstream
To connect DarkWaterAPI to the plugin via Maven, you need to write the following in pom.xml:
=======
<<<<<<< Updated upstream
=======
<<<<<<< Updated upstream
To connect DarkWaterAPI to the plugin via Maven, you need to write the following in pom.xml:
=======
>>>>>>> Stashed changes
>>>>>>> Auto stash before merge of "master" and "origin/master"
  <a href="https://github.com/kiinse/DarkWaterAPI/releases">
    <img src="https://img.shields.io/github/v/release/kiinse/DarkWaterAPI?include_prereleases&style=flat-square" alt="release">
  </a>
  <a href="https://github.com/kiinse/DarkWaterAPI/actions/workflows/gradle-package.yml">
<<<<<<< bacf75b531469ebad15087f857b474fde0b3137c
    <img src="https://img.shields.io/github/workflow/status/kiinse/DarkWaterAPI/Create%20packages%20with%20Gradle?style=flat-square" alt="workflow"> 
=======
<<<<<<< Updated upstream
    <img src="https://img.shields.io/github/workflow/status/kiinse/DarkWaterAPI/Create%20packages%20with%20Gradle?style=flat-square" alt="workflow"> 
=======
    <img src="https://img.shields.io/github/workflow/status/kiinse/DarkWaterAPI/Create%20packages%20with%20Gradle?style=flat-square" alt="build"> 
>>>>>>> Stashed changes
>>>>>>> Auto stash before merge of "master" and "origin/master"
  </a>
  <a href="https://github.com/kiinse/DarkWaterAPI">
    <img src="https://img.shields.io/github/repo-size/kiinse/DarkWaterAPI?style=flat-square" alt="size"> 
  </a>
  <a href="https://github.com/kiinse/DarkWaterAPI/releases">
    <img src="https://img.shields.io/github/downloads/kiinse/DarkWaterAPI/total?style=flat-square" alt="downloads"> 
  </a>
  <a href="https://github.com/kiinse/DarkWaterAPI/issues">
    <img src="https://img.shields.io/github/issues/kiinse/DarkWaterAPI?style=flat-square" alt="issues"> 
  </a>
  <a href="https://github.com/kiinse/DarkWaterAPI/blob/master/LICENSE">
    <img src="https://img.shields.io/github/license/kiinse/DarkWaterAPI?style=flat-square" alt="licence"> 
  </a>
<<<<<<< bacf75b531469ebad15087f857b474fde0b3137c
  <img src="https://img.shields.io/github/workflow/status/ByteZ1337/ParticleLib/Java%20CI%20with%20Maven" alt="build"/><br><br>
=======
<<<<<<< Updated upstream
  <img src="https://img.shields.io/github/workflow/status/ByteZ1337/ParticleLib/Java%20CI%20with%20Maven" alt="build"/><br><br>
=======
>>>>>>> Stashed changes
>>>>>>> Auto stash before merge of "master" and "origin/master"
  <a href="#support">Support</a> •
  <a href="#features">Features</a> •
  <a href="#download">Download</a> •
  <a href="#usage">Usage</a>
</p>
<<<<<<< bacf75b531469ebad15087f857b474fde0b3137c
=======
<<<<<<< Updated upstream
=======
>>>>>>> Stashed changes
>>>>>>> Stashed changes
>>>>>>> Stashed changes
>>>>>>> Auto stash before merge of "master" and "origin/master"
>>>>>>> Auto stash before merge of "master" and "origin/master"

Maven: 

```xml
<repositories>
  <repository>
    <id>gitlab-maven</id>
    <url>https://gitlab.com/api/v4/projects/35422828/packages/maven</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>kiinse.plugins.api</groupId>
    <artifactId>DarkWaterAPI</artifactId>
    <version>VERSION</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

Gradle:

```groovy
repositories {
    maven {
        url "https://gitlab.com/api/v4/projects/35422828/packages/maven"
        name "gitlab-maven"
    }
}

dependencies {
    compileOnly 'kiinse.plugins.api:DarkWaterAPI:VERSION'
}
```


## [VERSIONS & DOWNLOAD](https://gitlab.com/nubilum-development/project-watermelon/DarkWaterAPI/-/packages)
