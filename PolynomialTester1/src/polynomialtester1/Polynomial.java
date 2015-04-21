/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polynomialtester1;

/**
 *
 * @author Sillychina
 */
public class Polynomial {

    double[] arguements, roots;

    public Polynomial(double[] a) {
        arguements = a;
    }

    public Polynomial addPolynomial(Polynomial p) {

        int diff = Math.abs(this.arguements.length - p.arguements.length);
        
        Polynomial shorter;
        Polynomial longer;

        if (this.arguements.length > p.arguements.length) {
            shorter = p;
            longer = this;
        } else {
            shorter = this;
            longer = p;
        }
        for (int i = 0; i < shorter.arguements.length; i++) {
            longer.arguements[i + diff] = longer.arguements[i + diff] + shorter.arguements[i];
        }
        return longer;
    }
    
    public Polynomial subPolynomial(Polynomial p) {
        
        double[] ans = new double[Math.max(this.arguements.length, p.arguements.length)];
        int diff = this.arguements.length - p.arguements.length;
        if (diff > 0) {
            for (int i = 0; i < p.arguements.length; i++) {
                ans[i + diff] = this.arguements[i + diff] - p.arguements[i];
            }
        }
        else{
            for (int i = 0; i < diff; i++) {
                ans[i] = -p.arguements[i];
            }
            for (int i = -diff; i < p.arguements.length; i++) {
                ans[i] = this.arguements[i+diff] - p.arguements[i];
            }
        }
        Polynomial ansp = new Polynomial(ans);
        return ansp;
    }
    
    public Polynomial multPolynomial(Polynomial p){
        
        int t = this.arguements.length;
        int f = p.arguements.length;
        
        double[] ans = new double[t+f-1];
        
        for (int i = 0; i < (t+f-1); i++) {
            ans[i] = 0;
        }
        
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < f; j++) {
                ans[i+j] = ans[i+j] + (this.arguements[i] * p.arguements[j]);
            }
        }
        return (new Polynomial(ans));
    }
    
    public Polynomial divPolynomial (Polynomial p){
        
        int counter = 
    }
}
