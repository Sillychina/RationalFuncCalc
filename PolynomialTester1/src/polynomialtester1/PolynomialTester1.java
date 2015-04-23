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
    public static void main(String[] args) {
        Polynomial a = Input.parse("5x^2+3");
        Polynomial b = a.derivative();
        for (int i = a.arguments.length - 1; i > -1; i--) {
            System.out.println(a.arguments[i] + "x^" + i);
        }
        for (int i = b.arguments.length - 1; i > -1; i--) {
            System.out.println(b.arguments[i] + "x^" + i);
        }
    }
    
}
