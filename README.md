<h1 align="center">
  <img width=250 height=250 src="https://raw.githubusercontent.com/kiinse/DarkWaterAPI/master/.github/img/logo.png" />
  <br>DarkWaterAPI<br>
</h1>

<p align="center">
  <b>A library for <code>PaperMC 1.18 and above</code> that aims to improve plugin code</b><br><br>

  <a href="https://app.codacy.com/gh/kiinse/DarkWaterAPI/dashboard">
    <img src="https://app.codacy.com/project/badge/Grade/04669f7c982b4ec8ba4783493dfb1ca9" alt="codacy"/>
  </a>

  <a href="https://github.com/kiinse/DarkWaterAPI/releases">
    <img src="https://img.shields.io/github/v/release/kiinse/DarkWaterAPI?include_prereleases&style=flat-square" alt="release">
  </a>
  <a href="https://github.com/kiinse/DarkWaterAPI/actions/workflows/gradle-package.yml">
    <img src="https://img.shields.io/github/workflow/status/kiinse/DarkWaterAPI/Create%20packages%20with%20Gradle?style=flat-square" alt="build"> 
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
  </a><br><br>
  <a href="#support">Support</a> •
  <a href="#features">Features</a> •
  <a href="#download">Download</a> •
  <a href="#usage">Usage</a> •
  <a href="#commands">Commands</a> •
  <a href="#config">Config</a>
</p>
<p align="center">
  <ins>English</ins> • <a href="https://github.com/kiinse/DarkWaterAPI/blob/master/.github/Rus-README.md">Русский</a>
</p>

## Donations

If you want to support the project, then
star this repository and tell your friends about DarkWaterAPI =3

## Support

Join the [Discord](https://discord.gg/ec7y5NY82b) if you have any questions.
Please **don't** open an issue just for the sake of questions.

## Features

- Simple and convenient plugin localization system
- Easily create interactive text in messages
- Improved command work
- Player stats tracking
- The 'isWalking(player)' and 'isJumping(player)' methods. Really useful stuff =)
- Easy management bukkit scheduler's
- Easily manage files and data in them
- Checking the config version
- Enabling, disabling and reloading plugins that use this library
- And much more

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

----

<b>Since all examples are on WIKI, I will give a small example of working with messages here.</b>

In the resources folder, we create a "messages" folder, where we also create several localization files. For example en.json and ru.json. We get the following structure:

```txt
.
└── resources
    └── messages
        ├── en.json
        └── ru.json
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
        MessagesUtils messagesUtils = new MessagesUtilsImpl(this);
        messagesUtils.sendMessageWithPrefix(player, Message.MESSAGE_HELLO); // We send to player the message "message_hello" from the json file.
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

## Commands

| Command                     | Permission          | Description                                   |
|-----------------------------|---------------------|-----------------------------------------------|
| /locale change              | locale.change       | Opens a GUI to change the selected language   |
| /locale help                | locale.help         | Help command                                  |
| /locale set [locale]        | locale.change       | Setting the language without opening the GUI  |
| /locale list                | locale.list         | List of languages available for selection     |
| /locale get [player]        | locale.get          | View player's language                        |
| /darkwater reload [plugin]  | darkwater.reload    | Reloading a plugin using DarkWaterAPI         |
| /darkwater disable [plugin] | darkwater.disable   | Disabling a plugin using DarkWaterAPI         |
| /darkwater enable [plugin]  | darkwater.enable    | Enabling a plugin using DarkWaterAPI          |
| /statistic                  | darkwater.statistic | View statistics on the number of killed mobs. |

## Placeholders

| Placeholder                                             | Description                                            |
|---------------------------------------------------------|--------------------------------------------------------|
| %statistic_PUT-HERE-MOB% (Example: %statistic_CREEPER%) | Display the number of killed mob                       |
| %locale_player%                                         | Language display                                       |
| %locale_list%                                           | Displaying a list of languages available for selection |

## Config

```yaml
locale.default: en # The default language if the player's language is not available on the server, or it has not been defined

first.join.message: true # A message when the player enters, telling about the definition of the player's language and the possibility of changing this language.
actionbar.indicators: true # Indicators above the player's toolbar. This function is needed for some plugins that use DarkWaterAPI. Requires PlaceHolderAPI to work.

rest.enable: false
rest.port: 8080
rest.name: darkwater

rest.auth.enable: true # Enable and disable authentication for Rest requests. If it is disabled, then some requests will be disabled.
rest.auth.type: BEARER # BEARER | BASIC

rest.bearer.expire: 744 # Token expire in hours
rest.bearer.secret: darkwater # Secret word for the HMAC256 algorithm when signing the token.
rest.bearer.users:
  - admin:admin # User:Password
  - darkwater:darkwater # User:Password

# To get a bearer token for a user, you need to send a no auth request to any Rest link.
# The request header must contain the 'user' and 'password' parameters.
# Attention: A token can only be obtained once per user.
# The next time the token can be obtained either after the end of the previous one, or after the plugin is reloaded.

rest.basic.login: darkwater
rest.basic.password: darkwater

rest.service.commands: true # Command Usage Service with REST
rest.service.code: true # Code generator for players when requested via REST

rest.encrypted.data: true # Accept encrypted data via REST or not

# Encrypted data REST: localhost:port/darkwater/code?uuid={ENCRYPTED Player name}&exponent={RSA Exponent}&modulus={RSA Modulus}
# Decrypted data REST: localhost:port/darkwater/code?uuid={Player name}&exponent={RSA Exponent}&modulus={RSA Modulus}
# This parameter applies to command and code services
# To get darkwater public key REST: localhost:port/darkwater/code || localhost:port/darkwater/execute



# ------------------- REST -------------------
# localhost:port/service name/ping?ip={IP} - View ping from the specified ip address to the server
# localhost:port/service name/data - Viewing Server Data
# localhost:port/service name/plugins - View all plugins
# localhost:port/service name/darkwater - View data of all plugins using DarkWaterAPI ***
# localhost:port/service name/execute?cmd={COMMAND} - Command usage ***
# localhost:port/service name/code?player={Player name}&exponent={RSA Exponent}&modulus={RSA Modulus} - Sends the code to the player, and also returns it in encrypted form ***
# localhost:port/service name/code?uuid={Player uuid}&exponent={RSA Exponent}&modulus={RSA Modulus} - Sends the code to the player, and also returns it in encrypted form ***
#---------------------------------------------
# You can also use instead of the player's name - his UUID
# *** - Disabled if REST authorization is disabled
# ------------------- REST -------------------




config.version: 2 # DO NOT TOUCH THIS PLEASE =)
debug: false # This line is not in the config by default, but you can enter it in the DarkWaterAPI config to display config logs in the server console.
```
