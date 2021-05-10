/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_substring;

/**
 *
 * @author hsz0r
 */
public class Test_substring {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        substring test = new substring();
        String text = "Next, use short sentences, because grandma, like anyone of us, if you make very long sentences, she gets to the end and she can't remember what you said in the beginning, anymore.";
        String pattern = "se";
        System.out.println(test.Rabin_Karp(text, pattern).toString());
        System.out.println(test.Boyer_Moore(text, pattern).toString());
        System.out.println(test.Knuth_Morris_Pratt(text, pattern).toString());
        
    }

}
