package rationalCalculator;

import java.util.ArrayList;

public class Polynomial {

    double[] arguments;
    ArrayList<Double> roots;
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
    
    public String showRoots() {
        String roots = "";
        for (int i = 0; i < this.roots.size(); i++) {
            roots += "and" + this.roots.get(i);
        }
        return roots;
    }
    
    public ArrayList<Double> getRoots() {
        rootFinder(this);
        return this.roots;
    }

    public void rootFinder(Polynomial p) {

        if (p.arguments.length == 2) {
            double b = p.arguments[0];
            double a = p.arguments[1];

            for (int i = 0; i < this.roots.size(); i++) {
                if (this.roots.get(i) == 0.0d) {
                    this.roots.set(i, -b / a);
                    break;
                }
            }
        } else if (p.arguments.length == 3) {
            double c = p.arguments[0];
            double b = p.arguments[1];
            double a = p.arguments[2];

            double dis = Math.pow(b, 2) - 4 * a * c;
            if (dis > 0) {
                this.rootFinderQuad(a, b, c, dis);
            }
        } else if (testInt(p)) {
            this.rootGuesser(p);
        } else if (p.arguments.length % 2 == 1) {
            Polynomial deriv = p.derivative();
            this.newtonMethod(p, deriv, 0);
        } else if (p.arguments.length % 2 == 0) {
            this.laguerre(p);
        }
    }

    public void rootFinderQuad(double a, double b, double c, double dis) {
        double first = (-b - Math.sqrt(dis)) / (2 * a);
        double second = (-b + Math.sqrt(dis)) / (2 * a);
        for (int i = 0; i < this.roots.size(); i++) {
            if (this.roots.get(i) == 0.0d) {
                this.roots.set(i, first);
                this.roots.set(i + 1, second);
                break;
            }
        }
    }

    public boolean testInt(Polynomial p) {
        double first = p.arguments[0];
        double last = p.arguments[p.arguments.length - 1];
        boolean firstboole, lastboole;

        if (first > 0 && (first % 1 > 0.99 || first % 1 < 0.01)) {
            firstboole = true;
        } else if (first < 0 && (first % 1 > -0.01 || first % 1 < -0.99)) {
            firstboole = true;
        } else {
            firstboole = false;
        }
        if (firstboole) {
            if (last > 0 && (last % 1 > 0.99 || last % 1 < 0.01)) {
                lastboole = true;
            } else if (last < 0 && (last % 1 > -0.01 || last % 1 < -0.99)) {
                lastboole = true;
            } else {
                lastboole = false;
            }
        } else {
            lastboole = false;
        }

        if (firstboole && lastboole) {
            return true;
        } else {
            return false;
        }
    }

    public void rootGuesser(Polynomial p) {
        int a = Math.abs((int) Math.rint(p.arguments[0]));
        int b = Math.abs((int) Math.rint(p.arguments[p.arguments.length - 1]));

        ArrayList<Integer> aFact = new ArrayList<Integer>();
        ArrayList<Integer> bFact = new ArrayList<Integer>();

        for (int i = 1; i <= (int) (a / 2); i++) {
            if (a % i == 0) {
                aFact.add(i);
                aFact.add(a / i);
            }
        }
        for (int i = 0; i < (int) (b / 2); i++) {
            if (b % i == 0) {
                bFact.add(i);
                bFact.add(b / i);
            }
        }

        for (int i = 0; i < aFact.size(); i++) {
            for (int j = 0; j < bFact.size(); j++) {
                double testValue = aFact.get(i) / bFact.get(j);
                if (p.evaluate(testValue) == 0) {
                    for (int k = 0; k < this.roots.size(); k++) {
                        if (this.roots.get(i) == 0.0d) {
                            this.roots.set(i, testValue);
                        }
                    }
                    Polynomial root = new Polynomial(new double[]{1, -testValue});
                    Polynomial q = root.divide(p)[0];
                    this.rootFinder(q);
                } else if (p.evaluate(-testValue) == 0) {
                    for (int k = 0; k < this.roots.size(); k++) {
                        if (this.roots.get(i) == 0.0d) {
                            this.roots.set(i, -testValue);
                        }
                    }
                    Polynomial root = new Polynomial(new double[]{1, testValue});
                    Polynomial q = root.divide(p)[0];
                    this.rootFinder(q);
                }
            }
        }
    }

    public void newtonMethod(Polynomial p, Polynomial deriv, double d) {
        double num = p.evaluate(d);
        double den = deriv.evaluate(d);

        if (num == 0) {
            Polynomial root = new Polynomial(new double[]{1, d});
            Polynomial q = root.divide(p)[0];
            this.rootFinder(q);
        } else if (den == 0) {
            newtonMethod(p, deriv, d + 1);
        }
        double e = d - num / den;
        num = p.evaluate(e);
        den = deriv.evaluate(e);

        double f = e - num / den;

        for (int i = 0; i < 100; i++) {
            num = p.evaluate(f);
            den = deriv.evaluate(f);
            e = f - num / den;

            if (Math.abs(f - e) < 0.001) {
                for (int k = 0; k < this.roots.size(); k++) {
                    if (this.roots.get(i) == 0.0d) {
                        this.roots.set(i, e);
                    }
                }
                Polynomial root = new Polynomial(new double[]{1, -e});
                Polynomial q = root.divide(p)[0];
                this.rootFinder(q);
            }
            num = p.evaluate(e);
            den = deriv.evaluate(e);
            f = e - num / den;

            if (Math.abs(f - e) < 0.001) {
                for (int k = 0; k < this.roots.size(); k++) {
                    if (this.roots.get(i) == 0.0d) {
                        this.roots.set(i, f);
                    }
                }
                Polynomial root = new Polynomial(new double[]{1, -f});
                Polynomial q = root.divide(p)[0];
                this.rootFinder(q);
            }
        }

        this.laguerre(p);
    }

    public void laguerre(Polynomial p) {
        int degree = p.arguments.length - 1;
        Polynomial d1 = p.derivative();
        Polynomial d2 = p.derivative();

        double guess = 0;
        for (int i = 0; i < 100; i++) {
            if (Math.abs(p.evaluate(guess)) < .0000000001) {
                for (int k = 0; k < this.roots.size(); k++) {
                    if (this.roots.get(i) == 0.0d) {
                        this.roots.set(i, guess);
                    }
                }
                Polynomial root = new Polynomial(new double[]{1, -guess});
                Polynomial q = root.divide(p)[0];
                this.rootFinder(q);
            }
            double g = d1.evaluate(guess)/p.evaluate(guess);
            double h = g*g - d2.evaluate(guess)/p.evaluate(guess);
            double dem1 = g + Math.sqrt((degree-1)*(degree*h - g*g));
            double dem2 = g - Math.sqrt((degree-1)*(degree*h - g*g));
            double demF;
            if (Math.abs(dem1)>Math.abs(dem2)) {
                demF = dem1;
            }else{
                demF = dem2;
            }
            double a = degree/demF;
            guess = guess - a;
        }
    }
}
