public class Lexeme {
    Type type;
    String str;

    public boolean isOperation(){
        return type==Type.MULTIPLICATION||type==Type.DIVIDING||type==Type.SUBTRACTION||type==Type.ADDING;
    }

    public boolean isParenthesis(){
        return type==Type.LEFT_PARENTHESIS||type==Type.RIGHT_PARENTHESIS;
    }

    public boolean isNumber(){
        return type==Type.NUMBER;
    }

    Lexeme(Type type, String str) {
        this.type = type;
        this.str = str;
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "str='" + str + '\'' +
                '}';
    }
}