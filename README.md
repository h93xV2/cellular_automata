# cellular_automata

Simulates cellular automata systems.

![example workflow](https://github.com/h93xv2/cellular_automata/actions/workflows/codeql-analysis.yml/badge.svg) ![Coverage](.github/badges/jacoco.svg) ![Branches](.github/badges/branches.svg)

## Description

A simulator for cellular automata systems. Created as a way to experiment with newer Java features and development techniques. Currently supports [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) and other [Life-like cellular automata](https://www.conwaylife.com/wiki/Cellular_automaton#Life-like_cellular_automata). Cells outside of the viewable field are considered dead.

## Features

* Create patterns.
* Run patterns.
* Save patterns.
* Load patterns.

## Local Setup

The Java version is listed in the `.java-version` file. The file tells SDK managers which version of Java to install.

Once Java is installed, run `./gradlew run`.

## Running Release Versions

Prebuilt versions are available for Mac only.

1. Head to the releases page of the repo.
2. Download the DMG file.

If Mac Gatekeeper blocks the file from being opened:

1. Go to Settings.
2. Privacy and Security.
3. Scroll to the bottom.
4. Click "Open Anyway" next to the app name.
