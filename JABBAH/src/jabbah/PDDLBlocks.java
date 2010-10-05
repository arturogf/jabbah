/* 
This file is part of the Jabbah Framework, a software that is able to
carry out a transformation between Business Process Models into 
Hierarchical Task Networks Planning domains.

    Copyright (C) 2010
    	      Arturo Gonzalez-Ferrer, arturogf@decsai.ugr.es

    Este programa es software libre: usted puede redistribuirlo y/o modificarlo 
    bajo los términos de la Academic Free License ("AFL") v. 3.0).

    Este programa se distribuye con la esperanza de que sea útil, pero 
    SIN GARANTÍA ALGUNA; ni siquiera la garantía implícita 
    MERCANTIL o de APTITUD PARA UN PROPÓSITO DETERMINADO. 
    Consulte los detalles de la Academic Free License ("AFL) v 3.0 para obtener 
    una información más detallada. 

    Debería haber recibido una copia de la Academic Free License ("AFL") v. 3.0
    junto a este programa. 


*/
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
