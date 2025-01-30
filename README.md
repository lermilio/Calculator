OVERVIEW
*This project implements a stack-based calculator that evaluates arithmetic expressions using operator precedence and parentheses handling. The calculator follows the principles of reverse Polish notation (RPN) processing, internally managing numbers and operators on separate stacks.

PROJECT GOALS
*Implement a calculator that evaluates expressions using stack-based processing.
*Support operator precedence and parentheses handling.
*Use efficient data structures for evaluating expressions.

FEATURES
Expression Parsing
*Tokenizer: Extracts numbers and operators from the input string.
*Operator Precedence Handling: Ensures correct order of operations (* before +, etc.).
*Negative Numbers Handling: Supports unary - for negative numbers.
Stack-Based Evaluation
*Number Stack: Stores numeric values for computations.
*Operator Stack: Stores operators to apply when precedence conditions are met.
*Parentheses Processing: Supports expressions with () by ensuring correct precedence.

DATA STRUCTURES USED
*Stack<Double> → Stores numeric values for calculations.
*Stack<Character> → Stores operators, ensuring correct precedence.
*LinkedStack<E> → Implements a stack using a linked list.
*ArrayStack<E> → Implements a stack using an array-based approach.
*ListStack<E> → Implements a stack using a Java List.

IMPLEMENTATION DETAILS 
Stack Operations
*Push & Pop Mechanism: Uses push(), pop(), and peek() to process operands and operators.
*Stack Processing: Maintains correct precedence while evaluating expressions.
Expression Evaluation
*doNumber(double x) → Pushes a number onto the number stack.
*doOperator(char op) → Processes operators and maintains precedence.
*doEquals() → Evaluates the final expression and returns the result.
Handling Parentheses
*processOperator(char op) → Handles ( and ) operators by deferring execution until ) is encountered.

