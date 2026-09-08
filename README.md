# CodeLens

### AI-Powered Developer Productivity Assistant

CodeLens is an AI-powered developer productivity tool that helps developers understand, review, debug, and test source code from a single interface.

## 🚀 Problem

Developers often spend significant time:

- Understanding unfamiliar code
- Finding common code-quality problems
- Identifying potential bugs
- Creating initial test cases

These tasks can slow down development, especially when working with unfamiliar codebases.

## 💡 Solution

CodeLens provides four focused developer workflows:

| Mode | Purpose |
|---|---|
| Understand | Explains unfamiliar code and its structure |
| Code Review | Identifies code-quality, maintainability and performance issues |
| Debug | Finds potential bugs and logical/runtime problems |
| Generate Tests | Creates normal, edge and failure test cases |

The tool provides AI-generated recommendations while clearly reminding developers to verify suggestions before applying them.

## 👨‍💻 Target Users

CodeLens is designed for:

- Software developers
- Students learning programming
- Developers working with unfamiliar code
- Developers who want quick initial code reviews and test ideas

## ✨ Key Features

### 1. Code Understanding

Provides:

- Overall purpose
- Important classes
- Important methods/functions
- Code execution flow

### 2. Code Review

Analyzes code for:

- Code-quality issues
- Bad practices
- Code smells
- Maintainability problems
- Performance concerns
- Readability issues

### 3. Debugging

Identifies potential:

- Logical errors
- Runtime errors
- Invalid input handling
- Null-related problems
- Boundary/index errors

Each potential issue includes an explanation and suggested fix.

### 4. Test Generation

Generates test scenarios covering:

- Normal cases
- Edge cases
- Failure cases
- Invalid inputs

Each test case includes an expected result and what the test verifies.

### 5. Responsible AI

CodeLens:

- Labels results as AI-generated
- Warns users to verify recommendations
- Handles empty input
- Handles API failures
- Keeps API credentials outside the source code
- Does not claim uncertain AI findings as guaranteed facts

## 🏗️ Architecture

```text
+----------------------+
|      Developer       |
+----------+-----------+
           |
           v
+----------------------+
|     CodeLens UI      |
|      HTML/CSS/JS     |
+----------+-----------+
           |
           | POST /api/analyze
           v
+----------------------+
|   Spring Boot API    |
|   CodeLensController |
+----------+-----------+
           |
           v
+----------------------+
|     GroqService      |
|  Request Processing  |
+----------+-----------+
           |
           v
+----------------------+
|      Groq API        |
|   AI Code Analysis   |
+----------+-----------+
           |
           v
+----------------------+
|     AI Response      |
+----------------------+
           |
           v
+----------------------+
|      Developer       |
+----------------------+