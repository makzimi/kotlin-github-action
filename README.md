# Kotlin GitHub Action Template

This repository is a **template project** for those who want to write **GitHub Actions** using **Kotlin/JS**. It provides a pre-configured setup with two different build and bundling options: **ncc** (recommended by GitHub) and **webpack** (for cases when ncc may fail due to module format issues). 

## Getting Started


1. **Update `action.yml`:** Define your inputs, outputs, and metadata in the `./action.yml` file based on your action's requirements.

2. **Customize `App.kt`:** Modify `App.kt` to parse inputs and outputs according to the structure defined in `action.yml`.

3. **Write Your Logic:** Implement your action's custom logic in ```App.kt``` using the parsed inputs.

4. **Test Your Action:** Run the test workflow in ```.github/workflows/main.yml``` to validate your changes.

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

## NCC and Webpack: Executing as JS API

**NCC** (Node.js Compiler) and **Webpack** are popular tools for bundling JavaScript code and dependencies into a single file. This is especially useful for Node.js applications, as it reduces the complexity of managing multiple modules and external libraries.

Usually, **ncc** and **webpack** are used as command-line tools. You would typically install them and run commands like `npx ncc build` or `npx webpack`. However, in this project, I’ve reduced the setup work for you.

Instead of manually installing **ncc** or **webpack** as CLI tools, we use their **JavaScript API** programmatically in Kotlin/JS. This allows you to bundle your action without installing and running the clients directly. All you need to do is run the provided tasks, and the tools will execute automatically:

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
After running the build process with either ncc or webpack, the bundled JavaScript file will be located in the ```./dist/index.js``` directory. This is the file that will be executed by the GitHub Actions runner.

## License

This project is licensed under the [Apache License 2.0](./LICENSE).
