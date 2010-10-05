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
public class Activity {

    String id;
    String name;
    String lane_id;
    String duration;
    int type = NodeType.DEFAULT;
    int restriction = TransitionRestriction.NONE;
    MyWeightedVertex node;
    // if it is a gateway
    Parameter param;
    // if it is a subprocess
    String subset_id="";

    public Activity()
    {
    }

    public int numInputTransitions(Transition[] T)
    {
        int n=0;

        for (int i=0; i<T.length; i++)
        {
            if (T[i].to.equalsIgnoreCase(this.id))
                n=n+1;
        }

        return n;
    }


}

