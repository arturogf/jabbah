/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jabbah;

/**
 * Class with some common blocks of HTN-PDDL code
 * @author arturo
 */
public class PDDLBlocks
{
    public static final String requirements =
  "\n(:requirements\n" +
  ":typing \n" +
  ":fluents \n" +
  ":htn-expansion \n" +
  ":durative-actions \n"+
  ":negative-preconditions\n" +
  ":universal-preconditions\n" +
  ":disjuntive-preconditions\n" +
  ":conditional-effects\n"+
  ":derived-predicates\n" +
  ":metatags)\n\n";

  public static final String types =
        "\n(:types\n" +
 	"parameter - object\n" +
	"activity - object\n" +
	"participant - object\n" +
	"lane - object\n";


public static final String predicates =
        "\n(:predicates\n" +
	"(completed ?a - activity)\n"+
        "(ordered ?a - activity ?b - activity)\n"+
	"(belongs_to_lane ?p - participant ?l - lane)\n";
    }