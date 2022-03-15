# Alphabet Soup

An answer-key generator for word search puzzles

## Building

The following commands require Maven 3+:

```bash
mvn clean   # cleans the 'target' build directory
mvn test    # runs the unit tests 
mvn package # builds the alphabet-soup jar 
```

## Running

The following commands were run against Java 8:

```bash
java -jar /path/to/alphabet-soup.jar /path/to/input/file.txt

# Example:
java -jar target/alphabet-soup-1.0-SNAPSHOT.jar src/test/resources/SampleInput.txt
```

## Input Format
The program accepts an ASCII text file as input. 

The file is made up of three parts: 
1. The first line, which specifies the number of rows and columns in the grid of characters, separated by an 'x'. 
2. The grid of characters in the word search. 
3. The words to be found.

The file format is as follows:

```
3x3
A B C
D E F
G H I
ABC
AEI
```

## Output
The program prints out an answer key for the word search, where each line is an embedded word, followed by the start and
end positions. The origin (0,0) is at the top-left of the grid.

Output for the above example input:
```
ABC 0:0 0:2
AEI 0:0 2:2
```