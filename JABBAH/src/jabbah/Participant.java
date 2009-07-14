/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jabbah;

/**
 * Maps the participants found in the XPDL definition to the corresponding class.
 * We need to include an extendedAttribute tag for every participant at modelling 
 * time, so that we can consider which lane they belong to.
 *
 * @author arturogf
 */
public class Participant {
    String id;
    String name;
    String lane;
    //Vector abilities;
    // Date[] unavailable;
}
