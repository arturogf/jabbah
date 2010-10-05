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
public class Util {


        /**
     * @param name
     * @param uppercase
     *
     * @return the name formatted to be correctly interpreted by the planner
     */
    public static String formatName(String name, boolean uppercase)
    {
        String result = name;

        result = result.replace(" ", "");
        result = result.replace("&", "");
        result = result.replace(" ","");
        result = result.replace(".","");
        result = result.replace("?","");
        result = result.replace("Â¿","");
        result = result.replace("/","");
        result = result.replace("'","");
        result = result.replace(",","");
        result = result.replace(";","");
        result = result.replace("(","");
        result = result.replace(")","");

        if (uppercase)
            result = result.toUpperCase();
        else
            result = result.toLowerCase();

        return result;
    }

    /**
     * @param name
     * @param uppercase
     *
     * @return the name formatted to be correctly interpreted by the planner
     */
    public static String formatName(String name)
    {
        String result = name;

        result = result.replace(" ", "");
        result = result.replace("&", "");
        result = result.replace(" ","");
        result = result.replace(".","");
        result = result.replace("?","");
        result = result.replace("Â¿","");
        result = result.replace("/","");
        result = result.replace("'","");
        result = result.replace(",","");
        result = result.replace(";","");
        result = result.replace("(","");
        result = result.replace(")","");

        return result;
    }


}


