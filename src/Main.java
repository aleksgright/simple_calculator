import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.Character.isDigit;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение, чтобы посчитать его значение");
        System.out.println("Введите \"quit\", чтобы закончить выполнение");
        System.out.println("Введите \"tests\", чтобы простестировать программу");
        while (true) {
            String str = scanner.nextLine();
            if (str.equals("tests")) {
                Tests.runTests();
                continue;
            }
            if (str.equals("quit")) {
                System.out.println("Завершение работы");
                System.exit(0);
            }

            System.out.println(compute(expression(lexemeReader(str))));
        }
    }


    static Fraction compute(Node tree) {
        switch (tree.type) {
            case MULTIPLICATION:
                return Fraction.multiplication(compute(tree.left), compute(tree.right));
            case DIVIDING:
                return Fraction.dividing(compute(tree.left), compute(tree.right));
            case ADDING:
                return Fraction.adding(compute(tree.left), compute(tree.right));
            case SUBTRACTION:
                return Fraction.subtraction(compute(tree.left), compute(tree.right));
            case NUMBER:
                return new Fraction(tree.str);
        }
        System.exit(3);
        return new Fraction(1, 1);
    }


    static Node mulDiv(LinkedList<Lexeme> lexemes) {
        LinkedList<Lexeme> left = new LinkedList<>();
        LinkedList<Lexeme> right = new LinkedList<>();
        boolean isFound = false;
        int parenthesis = 0;
        Node cur = null;
        for (int i = lexemes.size() - 1; i >= 0; i--) {
            if (lexemes.get(i).type == Type.LEFT_PARENTHESIS) {
                parenthesis++;
            }
            if (lexemes.get(i).type == Type.RIGHT_PARENTHESIS) {
                parenthesis--;
            }
            if ((lexemes.get(i).type == Type.MULTIPLICATION || lexemes.get(i).type == Type.DIVIDING) && parenthesis == 0 && !isFound) {
                cur = new Node();
                cur.type = lexemes.get(i).type;
                cur.str = lexemes.get(i).str;
                isFound = true;
                continue;
            }
            if (cur != null) {
                left.addFirst(lexemes.get(i));
            } else {
                right.addFirst(lexemes.get(i));
            }
        }
        if (isFound) {
            cur.left = expression(left);
            cur.right = expression(right);
        }
        return cur;
    }

    static Node plusMinus(LinkedList<Lexeme> lexemes) {
        LinkedList<Lexeme> left = new LinkedList<>();
        LinkedList<Lexeme> right = new LinkedList<>();
        boolean isFound = false;
        int parenthesis = 0;
        Node cur = null;
        for (int i = lexemes.size() - 1; i >= 0; i--) {
            if (lexemes.get(i).type == Type.LEFT_PARENTHESIS) {
                parenthesis++;
            }
            if (lexemes.get(i).type == Type.RIGHT_PARENTHESIS) {
                parenthesis--;
            }
            if ((lexemes.get(i).type == Type.ADDING || lexemes.get(i).type == Type.SUBTRACTION) && parenthesis == 0 && !isFound) {
                cur = new Node();
                cur.type = lexemes.get(i).type;
                cur.str = lexemes.get(i).str;
                isFound = true;
                continue;
            }
            if (cur != null) {
                left.addFirst(lexemes.get(i));
            } else {
                right.addFirst(lexemes.get(i));
            }
        }
        if (isFound) {
            cur.left = expression(left);
            cur.right = expression(right);
        }
        return cur;
    }

    public static Node expression(LinkedList<Lexeme> lexemes) {
        Node cur;
        if (lexemes.size() == 1) {
            return new Node(Type.NUMBER, lexemes.get(0).str);
        }
        cur = plusMinus(lexemes);
        if (cur != null) {
            return cur;
        }
        cur = mulDiv(lexemes);
        if (cur != null) {
            return cur;
        }
        lexemes.poll();
        lexemes.removeLast();
        return expression(lexemes);
    }


    public static LinkedList<Lexeme> lexemeReader(String str) {
        str = str.replaceAll("\\s", "");
        LinkedList<Lexeme> lexemes = new LinkedList<>();
        int i = 0;
        while (i < str.length()) {
            String buffer = "";
            if (isDigit(str.charAt(i)) || (str.charAt(i) == '-' && (lexemes.isEmpty()
                    || lexemes.peekLast().isOperation()
                    || lexemes.peekLast().type == Type.LEFT_PARENTHESIS))) {
                for (int j = i; j < str.length(); j++) {
                    if (isDigit(str.charAt(j))
                            || str.charAt(j) == '/'
                            || (str.charAt(j) == '-'
                            && ((buffer.isEmpty() || str.charAt(j - 1) == '/')))) {
                        buffer += str.charAt(j);
                    } else {
                        i = j - 1;
                        break;
                    }
                    if (j == str.length() - 1) {
                        i = j;
                    }
                }
                if (!lexemes.isEmpty() && lexemes.peekLast().type == Type.RIGHT_PARENTHESIS) {
                    throw new RuntimeException("После закрывающей скобки должна идти операция");
                }
                lexemes.add(new Lexeme(Type.NUMBER, buffer));
                i++;
                continue;
            }
            char toCompare = str.charAt(i);
            i++;
            if (toCompare == '-') {
                lexemes.add(new Lexeme(Type.SUBTRACTION, "-"));
                continue;
            }
            if (toCompare == '+') {
                validateOperation(lexemes);
                lexemes.add(new Lexeme(Type.ADDING, "+"));
                continue;
            }
            if (toCompare == '*') {
                validateOperation(lexemes);
                lexemes.add(new Lexeme(Type.MULTIPLICATION, "*"));
                continue;
            }
            if (toCompare == ':') {
                validateOperation(lexemes);
                lexemes.add(new Lexeme(Type.DIVIDING, ":"));
                continue;
            }
            if (toCompare == '(') {
                if (!lexemes.isEmpty() && lexemes.peekLast().type == Type.NUMBER) {
                    throw new RuntimeException("После числа не может быть открывающей скобки");
                }
                lexemes.add(new Lexeme(Type.LEFT_PARENTHESIS, "("));
                continue;
            }
            if (toCompare == ')') {
                if (lexemes.isEmpty()) {
                    throw new RuntimeException("Выражение не может начинаться с закрывающей скобки");
                }
                if (lexemes.peekLast().isOperation()) {
                    throw new RuntimeException("После операции не может быть закрывающей скобки");
                }
                lexemes.add(new Lexeme(Type.RIGHT_PARENTHESIS, ")"));
                continue;
            }
            throw new RuntimeException("Вы ввели некорректный символ: " + toCompare);
        }
        return lexemes;
    }

    static void validateOperation(LinkedList<Lexeme> lexemes) {
        if (lexemes.isEmpty()) {
            throw new RuntimeException("Выражение не может начинаться с оператора");
        }
        if (lexemes.peekLast().isOperation()) {
            throw new RuntimeException("После оператора должно идти число");
        }
        if (lexemes.peekLast().type == Type.LEFT_PARENTHESIS) {
            throw new RuntimeException("После открывающей скобки должно идти число");
        }

    }
}
