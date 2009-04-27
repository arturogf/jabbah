/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jabbah;

/**
 *
 * @author arturo
 */
class PDDLBlocks
{
    public static final String requirements = "\n(:requirements\n" +
  ":typing \n" +
  ":fluents \n" +
  ":htn-expansion \n" +
  ":durative-actions \n"+
  ":negative-preconditions\n" +
  ":universal-preconditions\n" +
  ":disjuntive-preconditions\n" +
  ":derived-predicates\n" +
  ":metatags)\n\n";

  public static final String types = "\n(:types\n" +
 	"boolean - object\n" +
	"activity - object\n" +
    "gateway - object\n" +
	"participant - object\n" +
	"role - object\n" +
	"lane - object)\n\n";


public static final String predicates = "\n(:predicates\n" +
	"(completed ?a - activity)\n"+
	"(belongs_to_lane ?p - participant ?a - lane)" +	
        ")\n\n";
}