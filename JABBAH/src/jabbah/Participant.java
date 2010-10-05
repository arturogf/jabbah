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

