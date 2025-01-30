package prog04;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import prog02.UserInterface;
import prog02.GUI;
import prog02.ConsoleUI;

public class Calculator {
    boolean pushedOP;
    static final String OPERATORS = "()+-*/u^";
    static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 3, 4 };
    Stack<Character> operatorStack = new Stack<Character>();
    Stack<Double> numberStack = new Stack<Double>();
    UserInterface ui = new GUI("Calculator");

    Calculator (UserInterface ui) { this.ui = ui; }

    int precedence (char op){
        switch (op){
            case '(':
                return -1;
            case ')':
                return -1;
            case '+':
                return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            case 'u':
                return 3;
            case '^':
                return 4;

        }
        return 9;
    }
    void emptyStacks () {
        while (!numberStack.empty())
            numberStack.pop();
        while (!operatorStack.empty())
            operatorStack.pop();
    }

    String numberStackToString () {
        String s = "numberStack: ";
        Stack<Double> helperStack = new Stack<Double>();
        // EXERCISE
        // Put every element of numberStack into helperStack
        // You will need to use a loop.  What kind?
        // What condition? When can you stop moving elements out of numberStack?
        // What method do you use to take an element out of numberStack?
        // What method do you use to put that element into helperStack?
        while(!numberStack.isEmpty()){
            helperStack.push(numberStack.pop());
        }
        while(!helperStack.isEmpty()){
            Double temp = helperStack.pop();
            s = s + " " + temp;
            numberStack.push(temp);
        }
        // Now put everything back, but also add each one to s:
        return s;
    }

    String operatorStackToString () {
        String s = "operatorStack: ";
        Stack<Character> helperStack = new Stack<Character>();
        while(!operatorStack.isEmpty()){
            helperStack.push(operatorStack.pop());
        }
        while(!helperStack.isEmpty()){
            Character temp = helperStack.pop();
            s = s + " " + temp;
            operatorStack.push(temp);
        }
        return s;
    }

    void displayStacks () {
        ui.sendMessage(numberStackToString() + "\n" +
                operatorStackToString());
    }

    void doNumber (double x) {
        numberStack.push(x);
        pushedOP=false;
        displayStacks();
    }

    void doOperator (char op) {
        if(op == '-'){
            if(numberStack.isEmpty() && operatorStack.isEmpty()){
               op = 'u';
            }
            else if(pushedOP){
                op = 'u';
            }
        }
        processOperator(op);
        displayStacks();
    }


    double doEquals () {
        while (!operatorStack.empty())
            evaluateTopOperator();

        return numberStack.pop();
    }

    double evaluateOperator (double a, char op, double b) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '^':
                return Math.pow(a, b);
        }
        System.out.println("Unknown operator " + op);
        return 0;
    }

    void evaluateTopOperator () {
        if(operatorStack.peek() == 'u'){
            numberStack.push(numberStack.pop()*-1);
            pushedOP=false;
            operatorStack.pop();
        }
        else{
            char op = operatorStack.pop();
            Double a = numberStack.pop();
            Double b = numberStack.pop();
            numberStack.push(evaluateOperator(b, op, a));
            pushedOP = false;
        }
        displayStacks();
    }

    void processOperator (char op) {
        if(op == '('){
            operatorStack.push(op);
            pushedOP=true; }
        else if(op == ')'){
            while(operatorStack.peek() != '('){
                evaluateTopOperator();
            }
            operatorStack.pop();
        }
        else if(op == 'u'){
            operatorStack.push(op);
            pushedOP = true;
        }
        else {
            while (!(operatorStack.isEmpty()) && precedence(operatorStack.peek()) >= precedence(op)) {
                evaluateTopOperator();
            }
            operatorStack.push(op);
            pushedOP=true;
        }
    }

    static boolean checkTokens (UserInterface ui, Object[] tokens) {
        for (Object token : tokens)
            if (token instanceof Character &&
                    OPERATORS.indexOf((Character) token) == -1) {
                ui.sendMessage(token + " is not a valid operator.");
                return false;
            }
        return true;
    }

    static void processExpressions (UserInterface ui, Calculator calculator) {
        while (true) {
            String line = ui.getInfo("Enter arithmetic expression or cancel.");
            if (line == null)
                return;
            Object[] tokens = Tokenizer.tokenize(line);
            if (!checkTokens(ui, tokens))
                continue;
            try {
                for (Object token : tokens)
                    if (token instanceof Double)
                        calculator.doNumber((Double) token);
                    else
                        calculator.doOperator((Character) token);
                double result = calculator.doEquals();
                ui.sendMessage(line + " = " + result);
            } catch (Exception e) {
                ui.sendMessage("Bad expression.");
                calculator.emptyStacks();
            }
        }
    }

    public static void main (String[] args) {
        UserInterface ui = new ConsoleUI();
        Calculator calculator = new Calculator(ui);
        processExpressions(ui, calculator);
    }
}

//66 84