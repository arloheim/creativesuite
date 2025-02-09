# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.1.0] - 2025-02-09

### Added

- Clear and fill your inventory with a hotbar with the `/clearfill` command.
- Quickly toggle infinite night vision with the `/nightvision` command.
- Drop gravity-affected blocks at locations with the `/drop` and `/anvil` commands.
- Summon a lightning effect at locations with the `/smite` command.
- The sign material listener now supports new pale oak signs released in version 1.21.4.
- Locations in commands now support player and alias names.

### Changed

- **The plugin now requires a Paper v1.21.4 server or higher.**
- Default permissions for commands that change data are changed to op.

### Fixed

- Fixed a bug in the `/hotbar save` command.

## [2.0.0] - 2024-08-28

### Added

- Replace signs with another material by right clicking on them with a sign of a different material. Works with all types of signs.
- Aliases can take parameters now. Define a parameter using `/alias save alias:name say **<message=Hello>**` and set it using `/alias run alias:name **message=Bye**`.
- The messages for all commands are now configurable in the `config.yml` file.

### Changed

- **The plugin now requires a Paper v1.20 server or higher and is not compatible anymore with regular Bukkit or Spigot servers.**
- **Renamed the plugin from GregoCommands to CreativeSuite to reflect its usage better. Make sure to rename your plugin folder before upgrading.**
- Refactored model and plugin structure, moved common plugin classes to a separate package.

### Fixed

- Some permission nodes had a wrong name or wrong child nodes, those have been fixed.

## [1.1.1] - 2024-08-07

### Changed

- Improved output of the `/alias list` and `/hotbar list` commands by grouping namespaced keys by namespace.

### Fixed

- Fixed usage message of the `/alias` command.

## [1.1.0] - 2024-07-10

### Added

- Save and run shared aliased commands with the `/alias` and `/run` commands.

## [1.0.0] - 2024-07-09

### Added

- Save and load shared hotbars with the `/hotbar` command.
- Display shared Unicode characters for easy copying and pasting, and add custom ones, with the `/charmap` command.
