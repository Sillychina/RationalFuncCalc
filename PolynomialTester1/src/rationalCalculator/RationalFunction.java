package rationalCalculator;

public class RationalFunction {
    Polynomial numerator, denominator;
    
    public RationalFunction(Polynomial numerator, Polynomial denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    public RationalFunction derivative() {
        Polynomial top = numerator.derivative().multiply(denominator).subtract(numerator.multiply(denominator.derivative()));
        Polynomial bottom = denominator.multiply(denominator);
        return new RationalFunction(top, bottom);
    }
    
    public RationalFunction add(Polynomial p) {
        return new RationalFunction(numerator.add(p.multiply(denominator)), denominator);
    }
    
    public RationalFunction subtract(Polynomial p) {
        return new RationalFunction(numerator.subtract(p.multiply(denominator)), denominator);
    }
    
    public RationalFunction multiply(Polynomial p) {
        return new RationalFunction(numerator.multiply(p), denominator.multiply(p));
    }
    
    public RationalFunction multiply(RationalFunction r) {
        return new RationalFunction(numerator.multiply(r.numerator), denominator.multiply(r.denominator));
    }
    
    @Override
    public String toString() {
        return "(" + numerator.toString() + ")/(" + denominator.toString() + ")";
    }
}
