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

