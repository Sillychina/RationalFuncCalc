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
public class PolynomialTester1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[] b = {1,1};
        double[] c = {1,1};
        Polynomial a = new Polynomial(b);
        Polynomial d = new Polynomial(c);
        
        Polynomial added = d.multPolynomial(a);
        for (int i = 0; i < added.arguements.length; i++) {
            System.out.println(added.arguements[i]);
        }
    }
    
}
