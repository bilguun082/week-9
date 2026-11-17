# Programming Languages II - Week 9

## Overview
This repository contains the homework and practice coursework for **Week 9** of Programming Languages II.

> [!NOTE]
> **Base Version Notice:** The Car Dealership Management System in this repository is built directly upon the **Week 5 homework solution**, extending it with industry-standard logging (`java.util.logging`) and comprehensive JavaDoc documentation as requested.

---

## 1. Car Dealership Management System (Extended from Week 5)

The project extends the Week 5 object-oriented design (`Vehicle`, `ElectricVehicle`, `Dealership`, `VehicleInterface`, `ElectricInterface`, `FuelType`):

### Key Enhancements
1. **Logging to `cardealerApp.log` & Console:**
   - Configured via a central `setupLogging()` method attaching a `FileHandler` for `cardealerApp.log` with `SimpleFormatter` and `Level.INFO`.
   - **`INFO` Level:** Emitted on every method call across all classes and constructors (including parameters, calculated prices, and comparisons) with descriptive, reasonable text.
   - **`SEVERE` Level:** Emitted for all caught exceptions (file I/O reading errors, record parsing errors, and report writing errors).
2. **JavaDoc Documentation:**
   - Full JavaDoc comments on all classes, interfaces, enum constants, constructors, getters, setters, and business logic methods.
   - Generated HTML documentation is located in the `doc/` directory.

### Running the Dealership System
Compile and execute:
```bash
javac *.java
java Dealership
```
* **Input File:** `vehicles.txt`
* **Output Report:** `outputDealership.txt`
* **Log Output:** `cardealerApp.log` (and console)

---

## 2. Practice Class Tasks

### A. Iterative Fibonacci Bug Analysis & Fix
- File: `Fibonacci.java`
- **Issue:** Base cases $n = 0$ and $n = 1$ returned `f = 0` because `int f` was initialized to `0` and the while loop (`n > 1`) did not execute.
- **Fix:** Initialized `int f = 1;` so that $f(0) = 1$ and $f(1) = 1$ are returned correctly.

### B. Horstmann's WordAnalyzer Debugging
- Directory: `debugging/` (`WordAnalyzer.java`, `WordAnalyzerTester.java`)
- Detailed documentation: `Debug_Report_Fibonacci_and_WordAnalyzer.md`
- **Bug 1 (`firstRepeatedCharacter`):** `StringIndexOutOfBoundsException` fixed by changing upper loop bound to `word.length() - 1`.
- **Bug 2 (`firstMultipleCharacter`):** Self-matching logic error fixed by passing `i + 1` to `find()`.
- **Bug 3 (`countRepeatedCharacters`):** Missing repetitions at index 0 fixed by looping from `i = 0` and checking `(i == 0 || word.charAt(i - 1) != word.charAt(i))`.
