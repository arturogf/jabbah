/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jabbah;

/**
 *
 * @author arturogf
 */
public class Transition {
    String Id;
    String From;
    String To;
    // next are filled in case there are any condition for the transition
    String ParameterId = "";
    String Operator = "";
    String ParameterValue = "";
}
