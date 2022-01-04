<p align="center">
 <img src="https://user-images.githubusercontent.com/45015114/139809463-417377ca-2eef-4cec-9689-bd841b0ce5db.png" align="center" alt="JCompressor" />
 <h2 align="center">JCompressor</h2>
 <p align="center">Is a library for managing files in Java which easily and with the least line of code gives you
  the ability to manage files like moving through folders and directories, reading files and folders information,
  creating new files or folders, making changes to files and folders such as renaming or hiding them, deleting files
  and folders, searching for files or folders by regex and compressing files and folders or extracting them from zip files.</p>
</p>
  <p align="center">
    <a href="https://github.com/shuoros/JCompressor/actions">
      <img src="https://img.shields.io/github/workflow/status/shuoros/JCompressor/Test?label=Test&style=for-the-badge" />
    </a>
    <a href="https://mvnrepository.com/artifact/io.github.shuoros/JCompressor">
      <img src="https://img.shields.io/maven-central/v/io.github.shuoros/JCompressor?style=for-the-badge" />
    </a>
    <a href="https://www.codefactor.io/repository/github/shuoros/jterminal">
      <img alt="code factor" src="https://img.shields.io/codefactor/grade/github/shuoros/jcompressor/main?style=for-the-badge" />
    </a>
    <a href="#">
      <img alt="Contributors" src="https://img.shields.io/github/contributors/shuoros/jcompressor?style=for-the-badge&color=blueviolet" />
    </a>
    <a href="https://github.com/shuoros/JCompressor/blob/main/LICENSE">
      <img alt="License" src="https://img.shields.io/github/license/shuoros/jcompressor?style=for-the-badge" />
    </a>
    <br />
    <br />
    <a href="https://github.com/shuoros/JCompressor/issues">
      <img src="https://img.shields.io/github/issues-raw/shuoros/jcompressor?style=for-the-badge&color=red"/>
    </a>
    <a href="https://github.com/shuoros/JCompressor/issues">
      <img src="https://img.shields.io/github/issues-closed-raw/shuoros/jcompressor?style=for-the-badge"/>
    </a>
  </p>
  <p align="center">
	If you like this project, help me by giving me a star =))<3
  </p>

## What is in V0.1.0

- Compress a list of files in zip.
- Extract zip files.

## Hello JCompressor

To use JCompressor you just need to make a simple call to your desired API and JCompressor will do the rest.

For example:

```java
import io.github.shuoros.jcompressor.JCompressor;
import io.github.shuoros.jcompressor.compress.ZipCompressor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create A ZipCompressor
        JCompressor jCompressor = new ZipCompressor();
        File file = new File("/home/soroush/Desktop/file.txt");
        File zipFile = new File("/home/soroush/Desktop/file.zip");
        // Compress file to a zip file
        jCompressor.compress(List.of(file), zipFile);
        File extractDestination = new File("/home/soroush/Desktop/extractHere/");
        // Extract a zip file
        jCompressor.extract(zipFile, extractDestination);
    }
}
```

## Installation

You can use **JCompressor** with any project management tool:

### Maven

```xml
<!-- https://mvnrepository.com/artifact/io.github.shuoros/JCompressor -->
<dependency>
    <groupId>io.github.shuoros</groupId>
    <artifactId>JCompressor</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle

```gradle
// https://mvnrepository.com/artifact/io.github.shuoros/JCompressor
implementation group: 'io.github.shuoros', name: 'JCompressor', version: '0.1.0'
```

Or

```gradle
// https://mvnrepository.com/artifact/io.github.shuoros/JCompressor
implementation 'io.github.shuoros:JCompressor:0.1.0'
```

And in **Kotlin**

```gradle
// https://mvnrepository.com/artifact/io.github.shuoros/JCompressor
implementation("io.github.shuoros:JCompressor:0.1.0")
```

### SBT

```sbt
// https://mvnrepository.com/artifact/io.github.shuoros/JCompressor
libraryDependencies += "io.github.shuoros" % "JCompressor" % "0.1.0"
```

### Ivy

```xml
<!-- https://mvnrepository.com/artifact/io.github.shuoros/JCompressor -->
<dependency org="io.github.shuoros" name="JCompressor" rev="0.1.0"/>
```

### Grape

```java
// https://mvnrepository.com/artifact/io.github.shuoros/JCompressor
@Grapes(
        @Grab(group = 'io.github.shuoros', module = 'JCompressor', version = '0.1.0')
)
```

### Leiningen

```clj
;; https://mvnrepository.com/artifact/io.github.shuoros/JCompressor
[io.github.shuoros/JCompressor "0.1.0"]
```

## Authors

JCompressor is developed by [Soroush Shemshadi](https://github.com/shuoros)
and [contributors](https://github.com/shuoros/JCompressor/blob/main/CONTRIBUTORS.md).

## Contribution

If you want to contribute on this project, Please read
the [contribution guide](https://github.com/shuoros/JCompressor/blob/main/CONTRIBUTE.md).

## Releases

To see the changes in different versions of JCompressor, you can read
the [release notes](https://github.com/shuoros/JCompressor/blob/main/RELEASENOTES.md).

## Issues

If you encounter a bug or vulnerability, please read
the [issue policy](https://github.com/shuoros/JCompressor/blob/main/ISSUES.md).

## Documentation

To learn how to work with JCompressor, please take a look at the [/doc](https://github.com/shuoros/JCompressor/tree/main/doc)
folder.

## Acknowledgement

A great thanks to [@sarahrajabi](https://github.com/sarahrajabi) for designing the logo.