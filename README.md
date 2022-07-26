# DarkWaterAPI

## Wiki: [Gitlab WIKI](https://gitlab.com/nubilum-development/project-watermelon/DarkWaterAPI/-/wikis/home)

## Adding to your plugin:

To connect DarkWaterAPI to the plugin via Maven, you need to write the following in pom.xml:

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
