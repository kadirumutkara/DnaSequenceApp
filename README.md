# DNA Sequence Analyzer

The DNA Sequence Analyzer is a Spring Boot application that analyzes DNA sequences and calculates the visibility count of DNA bases under red and green laser lights for each cycle of sequencing.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You need to have the Java Development Kit (JDK) and Maven installed on your machine to run the application.

### Installing

1. Clone or download the project to your local machine.
2. Open a command line and navigate to the project directory.
3. Run the following Maven command to install dependencies:

    ```
    mvn clean install
    ```

### Running the Application

Start the application from the command line using the following command:
```
mvn spring-boot:run -Dspring-boot.run.arguments="/path/to/your/inputfile.txt"
  ```


Replace `/path/to/your/inputfile.txt` with the path to the file containing your DNA sequences.

### Input File Format

The input file should be formatted with one DNA sequence per line, with each base (G, T, C, A) separated by spaces. An example input file would look like this:

G T C A G T C A

A G T A G T A C

C T C T G A C A

### Output

The application will write the processing results to the path specified in `application.properties`. For example:

```
app.output-file-path=src/main/resources/output.txt
```


You can modify this path in the `application.properties` file to determine where the results will be written. Once the processing is complete, the results will be written to the specified file, and the full path of the file will be printed to the console.

