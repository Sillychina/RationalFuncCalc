package rationalCalculator;

public class RationalFunction {
    Polynomial numerator, denominator;
    
    public RationalFunction(Polynomial numerator, Polynomial denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    public RationalFunction derivative() {
        Polynomial top = numerator.derivative().muliply(denominator).subtract(numerator.muliply(denominator.derivative()));
        Polynomial bottom = denominator.muliply(denominator);
        return new RationalFunction(top, bottom);
    }
    
    @Override
    public String toString() {
        return "(" + numerator.toString() + ")/(" + denominator.toString() + ")";
    }
}
