/* 
This file is part of the Jabbah Framework, a software that is able to
carry out a transformation between Business Process Models into 
Hierarchical Task Networks Planning domains.

    Copyright (C) 2010
    	      Arturo Gonzalez-Ferrer, arturogf@decsai.ugr.es

    Este programa es software libre: usted puede redistribuirlo y/o modificarlo 
    bajo los t�rminos de la Academic Free License ("AFL") v. 3.0).

    Este programa se distribuye con la esperanza de que sea �til, pero 
    SIN GARANT�A ALGUNA; ni siquiera la garant�a impl�cita 
    MERCANTIL o de APTITUD PARA UN PROP�SITO DETERMINADO. 
    Consulte los detalles de la Academic Free License ("AFL) v 3.0 para obtener 
    una informaci�n m�s detallada. 

    Deber�a haber recibido una copia de la Academic Free License ("AFL") v. 3.0
    junto a este programa. 


*/
package jabbah;

/**
 * Define some constants that represents the type of transition restriction for
 * the node, directly extracted from the XPDL source
 * 
 * @param SPLIT_EXCLUSIVE represents an XOR
 * @param JOIN_EXCLUSIVE represents the join from an XOR
 * @param SPLIT_PARALLEL represents a parallel block
 * @param JOIN_INCLUSIVE represents a simple merge from a parallel block
 * @author Arturo
 */
public class TransitionRestriction
{   
    public static final int NONE = 0;
    public static final int SPLIT_EXCLUSIVE = 1;
    public static final int JOIN_EXCLUSIVE = 2;
    public static final int SPLIT_PARALLEL = 3;
    public static final int JOIN_INCLUSIVE = 4;
}


