package rationalCalculator;


public class Polynomial {

    double[] arguments, roots;
    Polynomial fdiv, sdiv;

    public Polynomial(double[] a) {
        arguments = a;
    }
    
    public Polynomial(String s) {
        this(Input.parse(s).arguments);
    }

    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < arguments.length; i++) {
            sum += arguments[i] * Math.pow(x, i);
        }
        return sum;
    }

    public Polynomial add(Polynomial p) {

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

    public Polynomial subtract(Polynomial p) {

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
        return new Polynomial(ans);
    }

    public Polynomial multiply(Polynomial p) {

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
        return new Polynomial(ans);
    }

    public Polynomial[] divide(Polynomial p) {
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

    public Polynomial derivative() {
        double[] ans = new double[this.arguments.length - 1];
        for (int i = 1; i < this.arguments.length; i++) {
            ans[i - 1] = this.arguments[i] * i;
        }
        Polynomial polyAns = new Polynomial(ans);
        return polyAns;
    }
    
    private String prettyInt(double d) { // Strip decimals from integers stored as doubles for prettier toString
        return d == Math.round(d) ? "" + Math.round(d) : "" + d;
    }

    @Override
    public String toString() {
        String poly;
        int last = this.arguments.length - 1;
        if (last == 0) {
            poly = prettyInt(this.arguments[last]);
        } else if (last == 1) {
            poly = prettyInt(this.arguments[last]) + "x";
        } else {
            poly = prettyInt(this.arguments[last]) + "x^" + last;
        }
        
        for (int i = (this.arguments.length - 2); i > 1; i--) {
            if (this.arguments[i] > 0) {
                poly += "+" + prettyInt(this.arguments[i]) + "x^" + i;
            }
            else if (this.arguments[i] < 0){
                poly += prettyInt(this.arguments[i]) + "x^" + i;
            }  
        }
        if (this.arguments.length > 2) {
            if (this.arguments[1] > 0) {
                poly += "+" + prettyInt(this.arguments[1]) + "x";
            } else if (this.arguments[1] < 0) {
                poly += prettyInt(this.arguments[1]) + "x";
            }
        }
        if (this.arguments[0] > 0 && this.arguments.length > 1) {
            poly += "+" + prettyInt(this.arguments[0]);
        } else if (this.arguments[0] < 0 && this.arguments.length > 1){
            poly += prettyInt(this.arguments[0]);
        }  
        
        poly = poly.replaceAll("-", " - ");
        poly = poly.replaceAll("\\+", " + ");
        return poly;
    }
}
