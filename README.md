# SurveyTestGenerator

## Introduction

This is a refactored version of a Survey & Test Generator I wrote for my Software Architecture Class.

## Getting Started

### Prerequisites

You must have Java 11 installed. No external libraries were used.

### Functionality

It supports:

* Creation
* Displaying
  * Correct Answers (Test)
  * No Answers (Both)
* Loading
* Saving
* Taking
* Modifying
* Tabulating
* AutoGrading (Test)

Question types:

* True/False
* Multiple Choice
* Short Answer
* Essay
* Date (yyyy-mm-dd)
* Matching (alphanumeric)

For example:

```
Welcome to Survey Generator!

Pick one of the following:
1) Survey
2) Test
3) Exit
2

Pick one of the following:
1) Create a new Test
2) Display an existing Test without correct answers
3) Display an existing Test with correct answers
4) Load an existing Test
5) Save the current Test
6) Take the current Test
7) Modify the current Test
8) Tabulate a Test
9) Grade a Test
10) Return to previous menu
9
Select an existing test to grade:
Please enter number of file to load: 
1) testTest.ser
1
Select an existing response set:
	1) testTest.ser_1
1
You received a(n) 66.66666666666666 on the test.
The test was worth 100%, but only 66.66666666666667 of those points could be auto-graded because there was/were 1 Essay question(s)

Pick one of the following:
1) Create a new Test
2) Display an existing Test without correct answers
3) Display an existing Test with correct answers
4) Load an existing Test
5) Save the current Test
6) Take the current Test
7) Modify the current Test
8) Tabulate a Test
9) Grade a Test
10) Return to previous menu
10
Returning to previous menu!

Pick one of the following:
1) Survey
2) Test
3) Exit
3
Thank you for using Survey and Test Generator!

Process finished with exit code 0
```

### Code Hierarchy

This is the UML generated by IntelliJ Ultimate 2021.1:

![UML](https://i.imgur.com/Cwg8tei.png)

### Running The Encoder

The IDE used was IntelliJ, as seen through `.idea/` and `SurveyTestGenerator.iml`.
Go to `ConsoleMenu.java` and run `main()`.

## Author

Kevin Karnani