# 🔥 FlameArrowIgnite

A small just-for-fun Minecraft plugin made for Paper/Spigot servers.
Its goal is to add a bit of realism and roleplay flavor 🎭 to the game.

When a burning arrow hits a block, it may ignite it based on a configurable chance.

## ✨ Features
- Paper / Spigot 1.21+
- Works only with burning arrows
- Correct fire placement (top, sides, bottom — no floating fire)
- Configurable ignition chance

## 🛠 Build
Requirements:
- Java 21
- Maven

Build command:
mvn package

Output:
target/flamearrowignite-1.0.0.jar

## 📦 Installation
1. Drop the jar into the plugins folder
2. Restart the server

## ⚙ Configuration
File:
plugins/FlameArrowIgnite/config.yml

Example:
chance: 0.25

Chance is a value from 0.0 to 1.0.

## 🔥 Notes
Fire spreading and decay are handled by vanilla Minecraft mechanics.
Server gamerules or protection plugins may affect fire behavior.

Made for fun, immersion, and a bit of chaos 😉
