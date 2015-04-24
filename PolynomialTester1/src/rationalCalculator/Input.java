package rationalCalculator;

import java.util.ArrayList;
import java.util.Scanner;

public class Input {
    

    public static Polynomial parse(String arg) {
        // strip any spaces
        arg = arg.replaceAll(" ", "");
        
        // replace uppercase x with lowercase
        arg = arg.replaceAll("X", "x");
            
        ArrayList<Polynomial> factors = new ArrayList<>(); // treat each term as a polynomial

        // Factored form
        Scanner terms = new Scanner(arg);
        terms.useDelimiter("\\)\\(");
        while (terms.hasNext()) {
            String term = terms.next();
            // Remove any extra brackets
            term = term.replace("(", "");
            term = term.replace(")", "");
            factors.add(parseGeneral(term)); // Add the current term to the list of terms
        }
        terms.close();

        Polynomial result = factors.get(0);
        int size = factors.size();
        for (int i = 1; i < size; i++) {
            result = result.multiply(factors.get(i)); // multiply all the terms together
        }
        return result;
            
            
    }
    
    private static Polynomial parseGeneral(String arg) {
    ArrayList<Double> coefficients = new ArrayList<>(); // Arraylist of coefficients
    ArrayList<Integer> exponents = new ArrayList<>(); // Arraylist of exponents
    String signed = (arg.charAt(0) != '-' && arg.charAt(0) != '+') ? arg : "+" + arg;

    int expMax = 0; // Highest exponent in the function
    Scanner terms = new Scanner(arg);
    terms.useDelimiter("\\+|-");
    while (terms.hasNext()) {
        String term = terms.next();
        int index = term.indexOf('x');
        if (index == -1) {
            coefficients.add(Double.parseDouble(term));
            exponents.add(0);
        } else {
            String coe = index == 0 ? "1" : term.substring(0, index); // Coefficient or 1 if omitted
            String exp; // Exponent, or 1 if no exponent, or 0 if no x
            if (term.indexOf('x') == -1) {
                exp = "0";
            } else if (term.indexOf('^') == -1) {
                exp = "1";
            } else {
                exp = term.substring(index + 2, term.length());
            }
            int exponent = Integer.parseInt(exp);
            if (exponent > expMax) expMax = exponent;
            coefficients.add(Double.parseDouble(coe));
            exponents.add(Integer.parseInt(exp));
        }   
    }
    terms.close();

    // Create array
    double[] result = new double[expMax + 1];
    int size = coefficients.size();
    for (int i = 0; i < size; i++) {
        result[exponents.get(i)] += coefficients.get(i);
    }
    return new Polynomial(result);
    }
}

