import static java.lang.Math.abs;

public class Fraction {
    int numerator;
    int denominator;

    public Fraction(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Fraction(int numerator, int denominator) {
        int nod = gcd(abs(numerator), abs(denominator));
        if (nod > 1) {
            numerator /= nod;
            denominator /= nod;
        }
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction(String str) {
        if (str.chars().filter(ch->ch=='/').count()>1){
            throw new RuntimeException("Некорректная дробь");
        }
        int dividingSignIndex = str.indexOf("/");
        if (dividingSignIndex == -1) {
            this.numerator = Integer.parseInt(str);
            this.denominator = 1;
        } else {
            this.numerator = Integer.parseInt(str.substring(0, dividingSignIndex));
            this.denominator = Integer.parseInt(str.substring(dividingSignIndex + 1));
            if (this.denominator==0) {
                throw new RuntimeException("Некорректная дробь");
            }
            if (this.denominator < 0) {
                this.numerator *= -1;
                this.denominator *= -1;
            }
        }
    }

    public static Fraction adding(Fraction first, Fraction second) {
        if (first.denominator == second.denominator) {
            return new Fraction(first.numerator + second.numerator, first.denominator);
        } else {
            return new Fraction(
                    first.numerator * second.denominator + second.numerator * first.denominator,
                    first.denominator * second.denominator
            );
        }
    }

    public static Fraction subtraction(Fraction first, Fraction second) {
        return adding(first, new Fraction(-1 * second.numerator, second.denominator));
    }

    public static Fraction multiplication(Fraction first, Fraction second) {
        return new Fraction(first.numerator * second.numerator, first.denominator * second.denominator);
    }

    public static Fraction dividing(Fraction first, Fraction second) {
        if (second.numerator==0) {
            throw new RuntimeException("Деление на 0 запрещено");
        }
        return multiplication(first, new Fraction(second.denominator, second.numerator));
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    @Override
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        } else {
            return numerator + "/" + denominator;
        }
    }
}
