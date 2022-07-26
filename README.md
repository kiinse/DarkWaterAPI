<h1 align="center">
  <img width=250 height=250 src="https://raw.githubusercontent.com/kiinse/DarkWaterAPI/master/.github/img/logo.png" />
  <br>DarkWaterAPI<br>
</h1>

<p align="center">
  <b>A library for <code>PaperMC</code> that aims to improve plugin code</b><br><br>

  <a href="https://app.codacy.com/gh/kiinse/DarkWaterAPI/dashboard">
    <img src="https://app.codacy.com/project/badge/Grade/04669f7c982b4ec8ba4783493dfb1ca9" alt="codacy"/>
  </a>

  <a href="https://github.com/kiinse/DarkWaterAPI/releases">
    <img src="https://img.shields.io/github/v/release/kiinse/DarkWaterAPI?include_prereleases&style=flat-square" alt="release">
  </a>
  <a href="https://github.com/kiinse/DarkWaterAPI/actions/workflows/gradle-package.yml">
    <img src="https://img.shields.io/github/workflow/status/kiinse/DarkWaterAPI/Create%20packages%20with%20Gradle?style=flat-square" alt="workflow"> 
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
  <img src="https://img.shields.io/github/workflow/status/ByteZ1337/ParticleLib/Java%20CI%20with%20Maven" alt="build"/><br><br>
  <a href="#support">Support</a> •
  <a href="#features">Features</a> •
  <a href="#download">Download</a> •
  <a href="#usage">Usage</a>
</p>

## Support

Join the [Discord](https://discord.gg/ec7y5NY82b) if you have any questions.
Please **don't** open an issue just for the sake of questions.

## Features

* Simple and convenient plugin localization system
* Easily create interactive text in messages
* Improved command work
* Player stats tracking
* The 'isWalking(player)' and 'isJumping(player)' methods. Really useful stuff =)
* Easy management bukkit scheduler's
* Easily manage files and data in them
* Checking the config version
* And much more

## Download

The latest version can be downloaded on the <a href="https://github.com/kiinse/DarkWaterAPI/releases">releases page.</a><br>

### Maven

```xml
<repositories>
  <repository>
    <id>darkwaterapi</id>
    <url>https://repo.kiinse.me/releases</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>kiinse.plugins.api</groupId>
    <artifactId>DarkWaterAPI</artifactId>
    <version>ENTER_VERSION_HERE</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

### Gradle

```groovy
repositories {
    maven {
        url "https://repo.kiinse.me/releases"
    }
}

dependencies {
    compileOnly 'kiinse.plugins.api:DarkWaterAPI:ENTER_VERSION_HERE'
}
```

## Usage

<b>For all examples and more detailed explanation check out the [Wiki](https://github.com/kiinse/DarkWaterAPI/wiki).</b>

#### Since all examples are on WIKI, I will give a small example of working with messages here.

In the resources folder, we create a "messages" folder, where we also create several localization files. For example en.json and ru.json. We get the following structure:

```
  |--resources
     |--messages
        |--en.json
        |--ru.json
```

After running the plugin containing the main class, which was inherited from "DarkWaterJavaPlugin" - These files will appear in the plugin folder on the server.

Sending text from these files takes just two lines:

```java
public final class TestPlugin extends DarkWaterJavaPlugin { // Main class

    @Override
    public void onStart() throws Exception {
        // Code at startup
    }

    @Override
    public void onStop() throws Exception {
        // Shutdown code
    }

    private void sendMessageToPlayer(Player player) {
        SendMessages sendMessages = new SendMessagesImpl(this);
        sendMessages.sendMessageWithPrefix(player, Message.MESSAGE_HELLO); // We send to player the message "message_hello" from the json file.
        // This text will correspond to the selected localization of the player.
    }
}

```

Contents of message files:

File "en.json":

```json
{
  "prefix": "message prefix",
  "message_hello": "Hello player!"
}
```

File "ru.json":

```json
{
  "prefix": "message prefix",
  "message_hello": "Привет, игрок!"
}
```
