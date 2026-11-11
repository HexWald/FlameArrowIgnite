# FlameArrowIgnite

Burning arrows have a chance to start a fire next to the block they hit. The fire needs a valid place to burn. Plugins that cancel Bukkit's block ignition event can stop it.

## Install

Put the JAR from [Releases](https://github.com/HexWald/FlameArrowIgnite/releases) in the `plugins` folder and restart the server. Requires Minecraft Java 1.21 or newer on Paper or Spigot.

Edit `plugins/FlameArrowIgnite/config.yml` to change the chance per hit:

```yaml
chance: 0.25
```

Use `0` to turn it off or `1` to light a fire on every valid hit. Minecraft handles fire spread and decay.

## Build

Run `mvn clean package` with JDK 21. The JAR is in `target/`.
