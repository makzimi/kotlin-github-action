# Kotlin GitHub Action Template

This repository is a **template project** for those who want to write **GitHub Actions** using **Kotlin/JS**. It provides a pre-configured setup with two different build and bundling options: **ncc** (recommended by GitHub) and **webpack** (for cases when ncc may fail due to module format issues). 

## Getting Started

To use this template, clone the repository and customize it for your action. The most important files to modify are:

- **`./action.yml`**: This is your action definition. Update this file to define your custom GitHub Action's metadata, inputs, and outputs.
- **`./.github/workflows/main.yml`**: This is the test workflow file. Use it to test your action with GitHub Actions.

### Example Usage 

Here's an example:

```yaml
jobs:
  my-job:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v2
      - name: Run Kotlin GitHub Action
        uses: ./
        with:
          owner: "owner of repository" # optional
          repo: "repository name" # optional
          someInput: "value" 
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

## How to Build and Bundle the Action
There are two options for building and bundling the Kotlin/JS GitHub Action:

### Option 1: Using ncc (Recommended by GitHub docs)
The ncc tool is recommended by GitHub for bundling actions because it compiles JavaScript files into a single executable file, ensuring all dependencies are included.

To build and bundle using ncc:

```bash
./gradlew build :ncc:jsNodeProductionRun
```

### Option 2: Using webpack
In some cases, ncc might fail due to differences in JavaScript module formats. If you encounter such issues, you can use webpack as an alternative bundling solution.

To build and bundle using webpack:

```bash
./gradlew build :webpack:jsNodeProductionRun
```

### Output
After running the build process with either ncc or webpack, the bundled JavaScript file will be located in the ./dist/index.js directory. This is the file that will be executed by the GitHub Actions runner.


