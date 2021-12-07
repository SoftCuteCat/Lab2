package com.company;

import java.util.Stack;


/**
 * A class designed for calculating mathematical expressions. Works with expressions entered from the command line.
 * The functionality includes:
 * Addition;
 * Subtraction;
 * Multiplication;
 * Division;
 * Parentheses.
 * @author Evgeniy Kalishkin
 * @version 1.0
 */
public class Solver {
    /**
     * The introduced mathematical expression.
     */
    public String mathematicalExpression; // математическое выражение
    //---------------------

    /**
     * Stack with numbers of mathematical expression.
     */
    private Stack<Double> quantity; // стек с числами, выуженными из выражения
    /**
     * Stack with mathematical expression operations.
     */
    private Stack<Character> operations; //стек с операциями, выуженными из выражения
    //---------------------

    /**
     * Index of the current element.
     */
    private int currentElement; // индекс текущего элемента
    /**
     * The answer of a mathematical expression.
     */
    public double answer; // ответ
    //---------------------

    /**
     * Checking the possibility of a unary minus.
     * 0 - unary minus is not possible (inside the expression);
     * 1 - at the beginning and after the opening parenthesis.
     */
    private boolean isUnaryMinus; // проверка на возможность унарного минуса. 0 - если минус не может быть, 1 - в начале выражения
    // или после открытой скобки

    /**
     * 0 - the previous element is not a number;
     * 1 - the previous element is a number.
     */
    private boolean isNumber; // 0 - текущий элемент не является числом, 1 - если это число.

    //----------Main Part-----------

    /**
     * Initializer of the class.
     * @param exp A mathematical expression that needs to be calculated.
     */
    public Solver(String exp) {
        quantity = new Stack<>();
        operations = new Stack<>();

        mathematicalExpression = exp;
        isUnaryMinus = true;
        isNumber = false;

        currentElement = 0;
    }

    /**
     * A method that analyzes the current element of a mathematical expression.
     * Calls other methods when certain characters are found.
     * @throws Exception Throws an error if the symbol is not defined.
     */
    public void qualifier() throws Exception {
        while (currentElement != mathematicalExpression.length()) { // пока индекс текущего элемента не выходит за длину строки

            char curItem = mathematicalExpression.charAt(currentElement); // переменная, хранящая текущий элемент

            if (curItem == '-' && isUnaryMinus || curItem >= '0' && curItem <= '9') {
                number();
            }

            else if (curItem == '(') {
                openBracket();// вызов метода
            }

            else if (curItem == '*' || curItem == '/') { // если текущий элемент умножение или деление
                productAndDivision(); // вызов метода
            }

            else if (curItem == '+' || curItem == '-' && !isUnaryMinus) { // если текущий элемент плюс или минус без возможности унарного минуса
                summationAndSubtraction(); // вызов метода
            }

            else if (curItem == ')') {
                closeBracket();
            }

            else if (curItem == ' ') {
                currentElement++;
            }

            else {
                throw new Exception("In the entered mathematical expression, a symbol was found that is not defined in the calculator.\n Please check the entered expression for correctness.");
            }

        }
        while(!operations.isEmpty()) {
            calculation();
        }
        answer = quantity.pop();
    }


    /**
     * If the current element of the mathematical expression is a digit,
     * then this method is called. Checks the possibility of a unary minus and calls a
     * method to extract a number from a mathematical expression.
     * @throws Exception
     */
    private void number() throws Exception {
        int sign = 1; // знак числа

        if (mathematicalExpression.charAt(currentElement) == '-') { // если мы зашли сюда по причине унарного минуса, то
            currentElement++; // идём на следующий элемент
            sign = -1;
        }

        quantity.push(numberExtraction() * sign); // вставляем вычлененное число и умножаем на знак

        isUnaryMinus = false; // унарного минуса уже не может быть
        isNumber = true; // текущее значение - число
    }

    /**
     * Called if the current element of the mathematical expression is equal to plus or minus.
     * Adds the current operation to the stack, and also checks for the priority of the operation.
     * @throws Exception Throws a warning if there are several operations in a row.
     */
    private void summationAndSubtraction() throws Exception {
        if (!isNumber) { //исключение если подряд 2 нака операции
            throw new Exception("Several operations are in a row. Please fix it.");
        }

        boolean isRight = true;

        while (!operations.isEmpty() && isRight) {
            if (operations.peek() != '(' && operations.peek() != ')') {
                calculation();
            }
            else {
                isRight = false;
            }
        }

        operations.push(mathematicalExpression.charAt(currentElement)); // вставка текущей операции в стек с операциями

        currentElement++; // индекс ++
        isNumber = false; // это не число
    }

    /**
     * Called if the current element of the mathematical expression is equal to multiplication or division.
     * Adds the current operation to the stack, and also checks for the priority of the operation.
     * @throws Exception Throws a warning if there are several operations in a row.
     */
    private void productAndDivision() throws Exception {
        if (!isNumber) { // если подряд идут две операции, например ++, --, */ и так далее.
            throw new Exception("Several operations are in a row. Please fix it.");
        }

        boolean isRight = true;

        while (!operations.isEmpty() && isRight == true) {
            if (operations.peek() == '*' || operations.peek() == '/') {
                calculation(); // метод
            }
            else {
                isRight = false;
            }
        }

        operations.push(mathematicalExpression.charAt(currentElement));

        currentElement++; // прибавляем 1 к текущему индексу
        isNumber = false; // текущий элемент не число
    }

    /**
     * Called if the current element of the mathematical expression is equal to open bracket.
     * @throws Exception Throws a warning if there is no sign before the bracket.
     */
    private void openBracket() throws Exception {
        if (isNumber == true && currentElement != 0) {
            throw new Exception("No operation was placed before the opening bracket.");
        }

        operations.push(mathematicalExpression.charAt(currentElement));
        currentElement++;

        isUnaryMinus = true; // после открытой скобки возможен унарный минус
        isNumber = true;
    }

    /**
     * Called if the current element of the mathematical expression is equal to close bracket.
     * @throws Exception Throws a warning if there is no number in front of the closed parenthesis
     * or there was no opening parenthesis in the mathematical expression.
     */
    private void closeBracket() throws Exception {
        if (!operations.contains('(')) { // если в стеке нет ни одной открывающейся скобки
            throw new Exception("There are not enough open brackets in this mathematical expression!");
        }
        if (!isNumber) { // если перед скобкой стояла операция
            throw new Exception("No number found before the closed parenthesis. Please correct the expression.");
        }

        while(operations.peek() != '(') {
            calculation();
        }

        operations.pop();

        if (!operations.isEmpty()) {
            if (operations.peek() == '-') {
                operations.pop();
                operations.push('+');
                quantity.push(quantity.pop() * (-1));
            }
        }

        currentElement++;

        isUnaryMinus = true;
        isNumber = true;
    }

    /**
     * Extracts a number from a mathematical operation.
     * @return The found number.
     * @throws Exception
     */
    private double numberExtraction() throws Exception {
        String number = ""; // создается пустая строка

        while (currentElement != mathematicalExpression.length() && mathematicalExpression.charAt(currentElement) >= '0' && mathematicalExpression.charAt(currentElement) <= '9') {
            // пока нет выхода за границы и текущий элемент - цифра
            number += mathematicalExpression.charAt(currentElement++); // к строке прибавляем текущий элемент и добавляем 1 к индексу
        }

        return Integer.parseInt(number); // возвращает строку в виде числа
    }

    /**
     * Calculation of operations in the stack.
     * @throws Exception When dividing by 0 or in the absence of paired brackets.
     */
    private void calculation() throws Exception {
        char op = operations.pop();
        switch(op) {
            case '+': {
                quantity.push(quantity.pop()+ quantity.pop());
                break;
            }
            case '-': {
                quantity.push(-quantity.pop()+ quantity.pop());
                break;
            }
            case '*': {
                quantity.push(quantity.pop()* quantity.pop());
                break;
            }
            case '/': {
                if (quantity.peek() == 0) {
                    throw new Exception("As a result of calculations, the denominator turned out to be 0. Division by 0 is impossible!");
                }
                quantity.push(Math.pow(quantity.pop(), -1) * quantity.pop());
                break;
            }
            default: throw new Exception("Not a paired number of brackets. Please check the correctness of the entered data.");
        }
    }

    //----------Override----------------
    /**
     * A method that returns the result as a string.
     * @return Answer in string
     */
    @Override
    public String toString() {
        return String.valueOf(answer);
    }

    /**
     * Comparison of two objects.
     * @param o The object to compare the current one with.
     * @return true if they are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        Solver that = (Solver) o;
        return Double.compare(that.answer, answer) == 0 && mathematicalExpression.equals(that.mathematicalExpression);
    }

    /**
     * Hash code calculation for the current object of this class.
     * @return Hash code
     */
    @Override
    public int hashCode() {
        return (int) (mathematicalExpression.length() * 170 - Math.round(answer));
    }
}
