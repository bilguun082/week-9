# Debugging Analysis and Error Corrections Report

**Subject:** Practice Class Debugging Tasks  
**Topics:** 
1. Iterative Fibonacci Calculator Error Analysis  
2. Horstmann's `WordAnalyzer.java` Error Analysis & Corrections  

---

## 1. Code Fragment 1: Iterative Fibonacci Calculator

### 1.1 Original Code
```java
// expected f(0)=1, f(1)=1, f(N)=f(N-1)+f(N-2) for greater N>1
static int fib(int n) {
    int f = 0;
    int f0 = 1;
    int f1 = 1;
    while (n > 1) {
        n--;
        f = f0 + f1;
        f0 = f1;
        f1 = f;
    }
    return f;
}
```

### 1.2 Error Analysis: What Went Wrong?
- **Root Cause: Incorrect Initialization / Unhandled Base Cases**
  - Variable `f` is initialized to `0` (`int f = 0;`).
  - When `n = 0`, the loop condition `n > 1` evaluates to `false` immediately. The loop body is skipped, and the method returns `f = 0`. However, the specification requires `f(0) = 1`.
  - When `n = 1`, the loop condition `n > 1` also evaluates to `false` immediately. The loop body is skipped, and the method returns `f = 0`. However, the specification requires `f(1) = 1`.
  - For $n \ge 2$, the loop runs $n - 1$ times and computes the sum correctly (e.g., $f(2) = 2, f(3) = 3, f(4) = 5$).
  - Therefore, the method only fails for the base cases $n = 0$ and $n = 1$ due to returning the initial value `f = 0`.

### 1.3 Corrected Code
#### Solution A: Initialize `f = 1`
By simply initializing `int f = 1;`, if `n = 0` or `n = 1`, the loop does not execute and returns `1`. For $n > 1$, `f` is updated during the iterations as expected.

```java
static int fib(int n) {
    int f = 1; // initialized to 1 so base cases n=0 and n=1 return 1
    int f0 = 1;
    int f1 = 1;
    while (n > 1) {
        n--;
        f = f0 + f1;
        f0 = f1;
        f1 = f;
    }
    return f;
}
```

#### Solution B: Explicit Base Case Guard
```java
static int fib(int n) {
    if (n <= 1) {
        return 1;
    }
    int f0 = 1;
    int f1 = 1;
    int f = 0;
    while (n > 1) {
        n--;
        f = f0 + f1;
        f0 = f1;
        f1 = f;
    }
    return f;
}
```

---

## 2. Code Fragment 2: `WordAnalyzer.java`

From [Cay Horstmann's Debugger Tutorial](http://www.horstmann.com/bigj/help/debugger/tutorial.html), `WordAnalyzer.java` contains three distinct methods intended for word analysis, each having a specific bug.

---

### 2.1 Method 1: `firstRepeatedCharacter()`

#### Original Code
```java
public char firstRepeatedCharacter()
{
    for (int i = 0; i < word.length(); i++)
    {
        char ch = word.charAt(i);
        if (ch == word.charAt(i + 1))
            return ch;
    }
    return 0;
}
```

#### Error Description
- **Error:** `StringIndexOutOfBoundsException` (Off-by-one error).
- **Cause:** 
  The loop condition is `i < word.length()`. In the final iteration where `i = word.length() - 1`, the expression `word.charAt(i + 1)` evaluates to `word.charAt(word.length())`. In Java, string indexing is $0$-based up to `length() - 1`. Accessing `word.length()` throws a `StringIndexOutOfBoundsException`.

#### Correction
Change the loop boundary to `i < word.length() - 1`:
```java
public char firstRepeatedCharacter()
{
    for (int i = 0; i < word.length() - 1; i++)
    {
        char ch = word.charAt(i);
        if (ch == word.charAt(i + 1))
            return ch;
    }
    return 0;
}
```

---

### 2.2 Method 2: `firstMultipleCharacter()` and helper `find(char c, int pos)`

#### Original Code
```java
public char firstMultipleCharacter()
{
    for (int i = 0; i < word.length(); i++)
    {
        char ch = word.charAt(i);
        if (find(ch, i) >= 0)
            return ch;
    }
    return 0;
}

private int find(char c, int pos)
{
    for (int i = pos; i < word.length(); i++)
    {
        if (word.charAt(i) == c)
        {
            return i;
        }
    }
    return -1;
}
```

#### Error Description
- **Error:** Logic error / Self-match.
- **Cause:**
  In `firstMultipleCharacter()`, the call is `find(ch, i)`. The `find` helper begins searching at index `pos = i`.
  Because `word.charAt(i)` is already equal to `ch`, `find(ch, i)` immediately matches the character at index `i` itself and returns `i`.
  Since `i >= 0` is always true on the first iteration ($i = 0$), `firstMultipleCharacter()` will **always return the very first character of the string** (`word.charAt(0)`), regardless of whether it repeats later or is completely unique.

#### Correction
`find` should start searching for occurrences **after** position `i`, i.e., `pos = i + 1`:
```java
public char firstMultipleCharacter()
{
    for (int i = 0; i < word.length(); i++)
    {
        char ch = word.charAt(i);
        if (find(ch, i + 1) >= 0) // start search after current position
            return ch;
    }
    return 0;
}
```

---

### 2.3 Method 3: `countRepeatedCharacters()`

#### Original Code
```java
public int countRepeatedCharacters()
{
    int c = 0;
    for (int i = 1; i < word.length() - 1; i++)
    {
        if (word.charAt(i) == word.charAt(i + 1)) // found a repetition
        {
            if (word.charAt(i - 1) != word.charAt(i)) // it't the start
                c++;
        }
    }
    return c;
}
```

#### Error Description
- **Error 1: Ignores repetitions starting at index 0 (Off-by-one / Boundary error).**
  - The loop starts at index `i = 1`. If the repetition occurs at the very start of the string (e.g., index 0 and 1, such as in `"aardvark"`, `"aabb"`, or `"roommate"` where words begin with repeated characters), it is completely missed.
  - For example, with `"aardvark"`, `i = 0` ('a' and 'a') is never checked, so `c` ends up being `0` instead of `1`.
  - With a 2-character repeating string like `"aa"`, `word.length() - 1 = 1`, and because `i = 1`, the loop `i < 1` does not execute at all and returns `0`.
- **Reason why the author introduced this bug:**
  The author checked `word.charAt(i - 1) != word.charAt(i)` to ensure only the start of a repeated block is counted. To avoid `StringIndexOutOfBoundsException` when evaluating `i - 1`, they mistakenly shifted the entire loop to begin at `i = 1` rather than handling `i == 0` separately.

#### Correction
Start the loop from `i = 0` and check if `i == 0 || word.charAt(i - 1) != word.charAt(i)`:
```java
public int countRepeatedCharacters()
{
    int c = 0;
    for (int i = 0; i < word.length() - 1; i++)
    {
        if (word.charAt(i) == word.charAt(i + 1)) // found a repetition
        {
            if (i == 0 || word.charAt(i - 1) != word.charAt(i)) // it's the start
                c++;
        }
    }
    return c;
}
```

---

## 3. Summary of Mistakes & Fixes Matrix

| Component | Error Location | Issue Type | Description | Correction |
| :--- | :--- | :--- | :--- | :--- |
| **Fibonacci** | `int f = 0;` / Base case | Boundary / Init error | `f` returns `0` for $n=0$ and $n=1$ instead of expected `1`. | Initialize `int f = 1;` or add `if (n <= 1) return 1;`. |
| **WordAnalyzer** | `firstRepeatedCharacter` | Runtime Exception | Loop checks up to `i < word.length()`, causing `word.charAt(i + 1)` to throw `StringIndexOutOfBoundsException`. | Change loop condition to `i < word.length() - 1`. |
| **WordAnalyzer** | `firstMultipleCharacter` | Logic Error | `find(ch, i)` starts searching at index `i`, matching itself and always returning the 1st character. | Call `find(ch, i + 1)`. |
| **WordAnalyzer** | `countRepeatedCharacters`| Boundary / Logic Error | Loop starts at `i = 1`, missing any repeated character groups at index 0 (e.g., `"aa"` in `"aardvark"`). | Loop from `i = 0` and use `(i == 0 \|\| word.charAt(i - 1) != word.charAt(i))`. |
