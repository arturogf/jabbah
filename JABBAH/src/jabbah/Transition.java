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
    String id;
    String name;
    String from;
    String to;
    // next are filled in case there are any condition for the transition
    String parameterId = "";
    String operator = "";
    String parameterValue = "";

    public Transition()
    {
    }
}
