import java.util.LinkedList;

public class Tests {
    public static void runTests() {
        testValidAdding();
        testValidSubtraction();
        testValidDividing();
        testValidMultiplication();
        testValidOperationsWithEqualPriority();
        testValidOperationsWithDifferentPriority1();
        testValidOperationsWithDifferentPriority2();
        testValidExpressionWithParenthesis();
        testExpressionWithInvalidSymbols();
        testExpressionWithInvalidFraction();
        testExpressionWithIncorrectSymbolsSequence();
        testDividingByZero();
    }

    private static void testValidAdding() {
        System.out.println("----------Test valid adding------------");
        System.out.println("Входные данные: 1/2+1/3");
        System.out.print("Ожидаемый вывод: 5/6\nФактический вывод: ");
        System.out.println(Main.compute(Main.expression(Main.lexemeReader("1/2+1/3"))));
    }

    private static void testValidSubtraction() {
        System.out.println("----------Test valid subtraction------------");
        System.out.println("Входные данные: 5/6-1/3");
        System.out.print("Ожидаемый вывод: 1/2\nФактический вывод: ");
        System.out.println(Main.compute(Main.expression(Main.lexemeReader("5/6-1/3"))));
    }

    private static void testValidMultiplication() {
        System.out.println("----------Test valid multiplication------------");
        System.out.println("Входные данные: 3/4*2/8");
        System.out.print("Ожидаемый вывод: 3/16\nФактический вывод: ");
        System.out.println(Main.compute(Main.expression(Main.lexemeReader("3/4*2/8"))));
    }

    private static void testValidDividing() {
        System.out.println("----------Test valid dividing------------");
        System.out.println("Входные данные: 3/4:2/8");
        System.out.print("Ожидаемый вывод: 3\nФактический вывод: ");
        System.out.println(Main.compute(Main.expression(Main.lexemeReader("3/4:2/8"))));
    }

    private static void testValidOperationsWithEqualPriority() {
        System.out.println("----------Test valid operations with equal priority------------");
        System.out.println("Входные данные: 1/3+1/5-1/2");
        System.out.print("Ожидаемый вывод: 1/30\nФактический вывод: ");
        System.out.println(Main.compute(Main.expression(Main.lexemeReader("1/3+1/5-1/2"))));
    }

    private static void testValidOperationsWithDifferentPriority1() {
        System.out.println("----------Test valid operations with different priority 1------------");
        System.out.println("Входные данные: 1/2+1/3*3/4");
        System.out.print("Ожидаемый вывод: 3/4\nФактический вывод: ");
        System.out.println(Main.compute(Main.expression(Main.lexemeReader("1/2+1/3*3/4"))));
    }

    private static void testValidOperationsWithDifferentPriority2() {
        System.out.println("----------Test valid operations with different priority 2------------");
        System.out.println("Входные данные: -1/-3*3/4-1/2");
        System.out.print("Ожидаемый вывод: -1/4\nФактический вывод: ");
        System.out.println(Main.compute(Main.expression(Main.lexemeReader("-1/-3*3/4-1/2"))));
    }

    private static void testValidExpressionWithParenthesis() {
        System.out.println("----------Test valid expression with parenthesis------------");
        System.out.println("Входные данные: -1/-3*(3/4-1/2)");
        System.out.print("Ожидаемый вывод: 1/12\nФактический вывод: ");
        System.out.println(Main.compute(Main.expression(Main.lexemeReader("-1/-3*(3/4-1/2)"))));
    }

    private static void testExpressionWithInvalidSymbols() {
        System.out.println("----------Test expression with invalid symbols------------");
        System.out.println("Входные данные: -1/3*1/2#1/5");
        System.out.print("Ожидаемый вывод: \"Вы ввели некорректный символ: #\"\nФактический вывод: ");
        try {
            System.out.println(Main.compute(Main.expression(Main.lexemeReader("-1/3*1/2#1/5"))));
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testExpressionWithInvalidFraction() {
        System.out.println("----------Test expression with invalid fraction------------");
        System.out.println("Входные данные: -1/3*1/2+1/5/4");
        System.out.print("Ожидаемый вывод: \"Некорректная дробь\"\nФактический вывод: ");
        try {
            System.out.println(Main.compute(Main.expression(Main.lexemeReader("-1/3*1/2+1/5/4"))));
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testExpressionWithIncorrectSymbolsSequence() {
        System.out.println("----------Test expression with invalid symbols sequence------------");
        System.out.println("Входные данные: -1/3*3/4-1/2*:2/1");
        System.out.print("Ожидаемый вывод: \"После оператора должно идти число\"\nФактический вывод: ");
        try {
            System.out.println(Main.compute(Main.expression(Main.lexemeReader("-1/3*3/4-1/2*:2/1"))));
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testDividingByZero() {
        System.out.println("----------Test expression with dividing by zero------------");
        System.out.println("Входные данные: -1/3:(1/2-1/2)");
        System.out.print("Ожидаемый вывод: \"Деление на 0 запрещено\"\nФактический вывод: ");
        try {
            System.out.println(Main.compute(Main.expression(Main.lexemeReader("-1/3:(1/2-1/2)"))));
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
