/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polynomialtester1;

/**
 *
 * @author Ian
 */
public class RationalFunction {
    Polynomial numerator, denominator;
    
    public RationalFunction(Polynomial numerator, Polynomial denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    public RationalFunction derivative() {
        Polynomial top = numerator.derivPolynomial().multPolynomial(denominator).subPolynomial(numerator.multPolynomial(denominator.derivPolynomial()));
        Polynomial bottom = denominator.multPolynomial(denominator);
        return new RationalFunction(top, bottom);
    }
}
