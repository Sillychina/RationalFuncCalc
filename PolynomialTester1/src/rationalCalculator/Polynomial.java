package rationalCalculator;

import java.util.ArrayList;
import java.util.Arrays;

public class Polynomial {

    double[] arguments; // these are the coefficients of the polynomial
    ArrayList<Double> roots; //roots of the polynomial
    Polynomial fdiv, sdiv; //first and second derivative of the polynomial
    
    public Polynomial(String s) {
        this(Input.parse(s).arguments);
    }

    public Polynomial(double[] a) {
        arguments = a;
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
        double[] a = {leading};
        Polynomial expanded = new Polynomial(a);
        for (int i = 0; i < l; i++) {
            this.roots.add(factors[i][1] / factors[i][0]);
            double[] b = {factors[i][1], factors[i][0]};
            expanded = expanded.multiply(new Polynomial(b));
        }
        this.arguments = expanded.arguments;

    }

    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < arguments.length; i++) {
            sum += arguments[i] * Math.pow(x, i); //takes all the values the arguments and adds them assuming you've given a value for x
        }
        return sum; //returns all values added together
    }

    public Polynomial add(Polynomial p) { // adds two the polynomials together

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

    public Polynomial subtract(Polynomial p) { //subtracts two polynomials where the number being subtracted is the first one

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

    public Polynomial multiply(Polynomial p) { //multiplies two polynomials together

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

    @SuppressWarnings("empty-statement")
    public Polynomial[] divide(Polynomial p, boolean round) { // polynomial division, given two polynomials, it divides them
        Polynomial[] ans = new Polynomial[2];
        int t = this.arguments.length;
        int f = p.arguments.length;
        int counter = f - t;
        double[] remainder = p.arguments.clone();
        double[] quotient = new double[counter + 1];
        double lead = this.arguments[t - 1];
        for (int i = 0; i <= counter; i++) {
            quotient[counter - i] = remainder[(f - 1) - i] / lead;
            for (int j = 0; j < t; j++) {
                double end = this.arguments[(t - 1) - j] * quotient[counter - i];
                remainder[(f - 1) - i - j] = remainder[(f - 1) - i - j] - end;
            }
        }
        int zeroCount = 0;
        for (int i = remainder.length - 1; i >= 0; i--) {
            if (remainder[i] == 0) {
                zeroCount++;
            }
            else{
                break;
            }
        }
        
        double[] fRemainder;
        if (remainder.length - zeroCount > 0) {
           fRemainder = new double[remainder.length - zeroCount];
        }
        else{
            fRemainder = new double[1];
        }
        for (int i = 0; i < fRemainder.length - zeroCount; i++) {
            fRemainder[i] = remainder[i];
        }
        ans[0] = new Polynomial(quotient);
        ans[1] = new Polynomial(fRemainder);
        return ans; //returns the answer in a two-element 1-d array of polynomials that houses the remainder and the quotient
    }

    public Polynomial[] divide(Polynomial p) { //helpful sub that makes rounding simpler, since you dont have to round in normal division, but need to to check for roots
        return divide(p, true);
    }

    public Polynomial derivative() { //finds derivative of polynomial given said polynomial
        if (this.arguments.length > 1) {
            double[] ans = new double[this.arguments.length - 1];
            for (int i = 1; i < this.arguments.length; i++) {
                ans[i - 1] = this.arguments[i] * i;
            }
            Polynomial polyAns = new Polynomial(ans);
            return polyAns;
        } else {
            Polynomial polyAns = new Polynomial(new double[]{0});
            return polyAns; //returns the derivative
        }

    }


    public void rootFinder(Polynomial p) { //helps sort to which function to use to find the roots. This includes basic root-finding strategy given the degree of polynomial. 
        if (p.arguments.length == 2) {
            double b = p.arguments[0];
            double a = p.arguments[1];
            this.roots.add(-b / a); //if binomial, root is easy

        } else if (p.arguments.length == 3) { //if quadratic, use quadratic formula

            double c = p.arguments[0];
            double b = p.arguments[1];
            double a = p.arguments[2];

            double dis = (b * b) - (4 * a * c);

            if (dis >= 0) {
                this.rootFinderQuad(a, b, dis);
            }
        } else if (testInt(p)) { // if beginning and end of polynomial are integers, then find the root by guessing. (rational root theorem)

            this.rootGuesser(p);
        } else if (p.arguments.length % 2 == 1) {
            Polynomial deriv = p.derivative();
            this.newtonMethod(p, deriv, 0); // if degree is even, Newton's method is better
        } else if (p.arguments.length % 2 == 0 && p.arguments.length > 3) { // only does the Laguerre's method if it's high degree than quadratic and degree is even
            this.laguerre(p);
        }
    }

    private void rootFinderQuad(double a, double b, double dis) { //quadratic formula
        double first = Math.rint((-b - Math.sqrt(dis)) * 256 / (2 * a)) / 256;
        double second = Math.rint((-b + Math.sqrt(dis)) * 256 / (2 * a)) / 256;
        this.roots.add(first);
        this.roots.add(second);

    }

    public boolean testInt(Polynomial p) { //sees if the first argument and the last argument are both integers. Then finds if any testable values work
        double first = p.arguments[0];
        double last = p.arguments[p.arguments.length - 1];

        double round1 = Math.rint(first * 256) / 256;
        double round2 = Math.rint(last * 256) / 256;

        if (round1 % 1 == 0 && round2 % 1 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public double round(double d) { //rounds for easier checking. Used due to double computer rounding error
        double ans = Math.rint(d * 256) / 256;
        return ans;
    }

    public void rootGuesser(Polynomial p) { //guesses roots if both leading and constant coefficients are integers
        int a = (int) Math.abs(p.arguments[0]);
        int b = (int) Math.abs(p.arguments[p.arguments.length - 1]);

        ArrayList<Integer> aFact = new ArrayList<Integer>();
        ArrayList<Integer> bFact = new ArrayList<Integer>();

        for (int i = 1; i <= (int) (Math.sqrt(a)); i++) {
            if (a % i == 0) {
                aFact.add(i);
                aFact.add(a / i); //makes arraylist of all the possible factors for constant
            }
        }
        for (int i = 1; i <= (int) Math.sqrt(b); i++) {
            if (b % i == 0) {
                bFact.add(i);
                bFact.add(b / i); //makes arraylist of all the possible factors for leading
            }
        }
        boolean rootFound = false;
        outerLoop:
        for (int i = 0; i < aFact.size(); i++) {
            for (int j = 0; j < bFact.size(); j++) {
                double testValue = aFact.get(i) / bFact.get(j);
                if (round(p.evaluate(testValue)) == 0) {
                    this.roots.add(testValue);
                    Polynomial root = new Polynomial(new double[]{-testValue, 1}); //if either test value works, it does the process again with the new polynomial (freshly divided by root)
                    Polynomial q = root.divide(p)[0];
                    rootFound = true;
                    this.rootFinder(q);
                    break outerLoop;
                } else if (round(p.evaluate(-testValue)) == 0) {
                    this.roots.add(-testValue);
                    Polynomial root = new Polynomial(new double[]{testValue, 1}); 
                    Polynomial q = root.divide(p)[0];
                    rootFound = true;
                    this.rootFinder(q);
                    break outerLoop;
                }
            }
        }
        if (rootFound == false) {
            fdiv = p.derivative();
            this.newtonMethod(p, fdiv, 0); //does newtons method if no values found
        }
    }

    public void newtonMethod(Polynomial p, Polynomial deriv, double d) { //method to aproximate roots
        double num = p.evaluate(d);
        double den = deriv.evaluate(d);
        if (num == 0) {
            Polynomial root = new Polynomial(new double[]{-d, 1});
            Polynomial q = root.divide(p)[0];
            this.rootFinder(q);
        } else if (den == 0) {
            if (deriv.arguments.length > 2) this.newtonMethod(p, deriv, d + 1);
        } else {
            double e = d - num / den;
            num = p.evaluate(e);
            den = deriv.evaluate(e);

            double f = e - num / den;

            boolean works = false;

            for (int i = 0; i < 100; i++){
                num = p.evaluate(f);
                den = deriv.evaluate(f);
                e = f - num / den;
                if (round(f - e) == 0) {
                    this.roots.add(e);
                    works = true;
                    Polynomial root = new Polynomial(new double[]{-e, 1}); //if found root within 100 iterations, divides and repeats process with new root
                    Polynomial q = root.divide(p)[0];
                    this.rootFinder(q);
                    break;
                }
                num = p.evaluate(e);
                den = deriv.evaluate(e);
                f = e - num / den;
                if (round(f - e) == 0) {
                    this.roots.add(f);
                    works = true;
                    Polynomial root = new Polynomial(new double[]{-f, 1});
                    Polynomial q = root.divide(p)[0];
                    this.rootFinder(q);
                    break;
                }
            }
            if (!works) {
                this.laguerre(p); //if no roots found, then uses laguerre's method
            }
        }

    }

    public void laguerre(Polynomial p) { //laguerre's method is almost foolproof, but can find complex roots instead of real ones sometimes. for more info, google "leguerre's method"
        int degree = p.arguments.length - 1;
        Polynomial d1 = p.derivative();
        Polynomial d2 = p.derivative();

        double guess = 0;
        for (int i = 0; i < 100; i++) {
            if (round(p.evaluate(guess)) == 0) {
                this.roots.add(guess);
                Polynomial root = new Polynomial(new double[]{-guess, 1});
                Polynomial q = root.divide(p)[0];
                this.rootFinder(q);
                break;
            }
            double g = d1.evaluate(guess) / p.evaluate(guess);
            double h = g * g - d2.evaluate(guess) / p.evaluate(guess);
            double dis = (degree - 1) * (degree * h - g * g);
            if (dis < 0 && p.arguments.length % 2 == 0) { // if found complexcomplex and is an odd leading coefficient, try newton's method at a new inteval
                Polynomial div = p.derivative();
                this.newtonMethod(p, div, 100);
                break;
            }
            double dem1 = g + Math.sqrt(dis);
            double dem2 = g - Math.sqrt(dis);
            double demF;
            if (Math.abs(dem1) > Math.abs(dem2)) {
                demF = dem1;
            } else {
                demF = dem2;
            }
            double a = degree / demF;
            guess -= a;
        }
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
            if (!(this.arguments[last] == 0)) poly = prettyInt(this.arguments[last]) + "x";
            else poly = "";
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
        
        if (poly.charAt(0) == '+') poly = poly.substring(1);
        poly = poly.replaceAll("-", " - ");
        poly = poly.replaceAll("\\+", " + ");
        return poly;
    }
    
    public String showRoots() {
        String result = "";
        ArrayList<Double> roots = getRoots();
        for (Double root : roots) {
            result += "and" + root;
        }
        return result;
    }
    
    public ArrayList<Double> getRoots() {
        if (this.roots == null) { // only calculate roots if they haven't been calculated already
            this.roots = new ArrayList<>();
            rootFinder(this);
        }
        return this.roots;
    }
}
