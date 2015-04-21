package polynomialtester1;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Input {
    
    Pattern factoredForm = Pattern.compile("(?:\\(-?\\d+(?\\.\\d+)x\\+|-\\d+(?:\\.\\d+)?\\))+"); // Matches a product of binomials
    Pattern generalForm = Pattern.compile("\\+|\\-(?:\\d+(?:\\.\\d+)?(?:x(?://^//d+(?://.//d+)?)))+"); // Matches a sum or difference of exponents
    
    public double[] parse(String s) {
        // Check for valid input
        if (s.matches(factoredForm)) {
            // Factored form
            Scanner s;
            
        } else if (s.matches(generalForm)) {
            // General form
        } else {
            System.out.println("Invalid input")
            return {};
        }
    }
}
