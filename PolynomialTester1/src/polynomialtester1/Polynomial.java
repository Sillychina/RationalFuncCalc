/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package givearthur2;

/**
 *
 * @author Sillychina
 */
public class Polynomial {

    double[] arguments, roots;
    Polynomial fdiv, sdiv;

    public Polynomial(double[] a) {
        arguments = a;
        //Function here to get roots
    }

    public Polynomial(double[][] factors) {
        //ie: (2x+5)(3x-9) is represented by the following:
        //factors = {{2.0,5.0}, {3.0,-9.0}}
        this(1.0, factors);
    }

    public Polynomial(double leading, double[][] factors) {
        //ie: 3(2x+5)(x-3) is represented by the following:
        // leading = 3.0, factors = {{2.0,5.0},{1.0,-3.0}}
        int l = factors.length;
        this.roots = new double[l];
        double[] a = {leading};
        Polynomial expanded = new Polynomial(a);
        for (int i = 0; i < l; i++) {
            this.roots[i] = factors[i][1] / factors[i][0];
            double[] b = {factors[i][1], factors[i][0]};
            expanded = expanded.multPolynomial(new Polynomial(b));
        }
        this.arguments = expanded.arguments;

    }

    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < arguments.length; i++) {
            sum += arguments[i] * Math.pow(x, i);
        }
        return sum;
    }

    public Polynomial addPolynomial(Polynomial p) {

        int t = this.arguments.length;
        int f = p.arguments.length;
        int min = Math.min(t, f);
        int max = Math.max(t, f);

        double[] ans = new double[max];
        for (int i = 0; i < min; i++) {
            ans[i] = this.arguments[i] + p.arguments[i];
        }
        if (t > f) {
            for (int i = f; i < t; i++) {
                ans[i] = this.arguments[i];
            }
        } else {
            for (int i = t; i < f; i++) {
                ans[i] = p.arguments[i];
            }
        }
        return (new Polynomial(ans));
    }

    public Polynomial subPolynomial(Polynomial p) {

        int t = this.arguments.length;
        int f = p.arguments.length;
        int min = Math.min(t, f);
        int max = Math.max(t, f);

        double[] ans = new double[max];
        for (int i = 0; i < min; i++) {
            ans[i] = this.arguments[i] - p.arguments[i];
        }
        if (t > f) {
            for (int i = f; i < t; i++) {
                ans[i] = this.arguments[i];
            }
        } else {
            for (int i = t; i < f; i++) {
                ans[i] = -p.arguments[i];
            }
        }
        return (new Polynomial(ans));
    }

    public Polynomial multPolynomial(Polynomial p) {

        int t = this.arguments.length;
        int f = p.arguments.length;

        double[] ans = new double[t + f - 1];

        for (int i = 0; i < (t + f - 1); i++) {
            ans[i] = 0;
        }

        for (int i = 0; i < t; i++) {
            for (int j = 0; j < f; j++) {
                ans[i + j] = ans[i + j] + (this.arguments[i] * p.arguments[j]);
            }
        }
        return (new Polynomial(ans));
    }

    public Polynomial[] divPolynomial(Polynomial p) {
        Polynomial[] ans = new Polynomial[2];
        int t = this.arguments.length;
        int f = p.arguments.length;
        int counter = f - t;
        double[] remainder = p.arguments.clone();
        double[] quotient = new double[counter + 1];
        double[] fRemainder = new double[t];
        double lead = this.arguments[t - 1];
        for (int i = 0; i <= counter; i++) {
            quotient[counter - i] = remainder[(f - 1) - i] / lead;
            for (int j = 0; j < t; j++) {
                double end = this.arguments[(t - 1) - j] * quotient[counter - i];
                remainder[(f - 1) - i - j] = remainder[(f - 1) - i - j] - end;
            }
        }
        for (int i = 0; i < (t - 1); i++) {
            fRemainder[i] = remainder[i];
        }
        ans[0] = new Polynomial(quotient);
        ans[1] = new Polynomial(fRemainder);
        return ans;
    }

    public Polynomial derivPolynomial() {
        double[] ans = new double[this.arguments.length - 1];
        for (int i = 1; i < this.arguments.length; i++) {
            ans[i - 1] = this.arguments[i] * i;
        }
        Polynomial polyAns = new Polynomial(ans);
        return polyAns;
    }

    public String showPolynomial() {
        String poly;
        int last = this.arguments.length - 1;
        poly = "" + this.arguments[last] + "x^" + last;
        
        for (int i = (this.arguments.length - 2); i > 0; i--) {
            String argue;
            if (this.arguments[i] > 0) {
                poly += "+" + this.arguments[i] + "x^" + i;
            }
            else if (this.arguments[i] < 0){
                poly += "" + this.arguments[i] + "x^" + i;
            }  
        }
        if (this.arguments[0] > 0) {
            poly += "+" + this.arguments[0];
        }
        else if (this.arguments[0] < 0){
            poly += "" + this.arguments[0];
        }  
        return poly;
    }
}
