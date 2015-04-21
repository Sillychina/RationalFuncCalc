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

    double[] arguments, roots;

    public Polynomial(double[] a) {
        arguments = a;
    }

    public Polynomial addPolynomial(Polynomial p) {

        int diff = Math.abs(this.arguments.length - p.arguments.length);
        
        
    }
    
    public Polynomial subPolynomial(Polynomial p) {
        
        double[] ans = new double[Math.max(this.arguments.length, p.arguments.length)];
        int diff = this.arguments.length - p.arguments.length;
        if (diff > 0) {
            for (int i = 0; i < p.arguments.length; i++) {
                ans[i + diff] = this.arguments[i + diff] - p.arguments[i];
            }
        }
        else{
            for (int i = 0; i < diff; i++) {
                ans[i] = -p.arguments[i];
            }
            for (int i = -diff; i < p.arguments.length; i++) {
                ans[i] = this.arguments[i+diff] - p.arguments[i];
            }
        }
        Polynomial ansp = new Polynomial(ans);
        return ansp;
    }
    
    public Polynomial multPolynomial(Polynomial p){
        
        int t = this.arguments.length;
        int f = p.arguments.length;
        
        double[] ans = new double[t+f-1];
        
        for (int i = 0; i < (t+f-1); i++) {
            ans[i] = 0;
        }
        
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < f; j++) {
                ans[i+j] = ans[i+j] + (this.arguments[i] * p.arguments[j]);
            }
        }
        return (new Polynomial(ans));
    }
    
    public Polynomial divPolynomial (Polynomial p){
        
        int counter = p.arguments.length - this.arguments.length;
    }
}
