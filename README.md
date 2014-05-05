# frequency-analyzer

Simple application written in Java and Scala to visualize the number of occurrences
of a word in a text.

Uses Apache Lucene to tokenize the text and JavaFX to draw a histogram.

The project is in public domain. See [LICENSE](./LICENSE) for more information.

# Requirements

You can download a packaged jar from [the release page](https://github.com/tuvistavie/word-frequency-analyzer/releases),
you will need JRE version 8 to run it.

For development, you will need JDK 8 and sbt.

# Usage

```
java -jar PATH_TO_APP [-n WORD_COUNT] -f FILENAME
```
