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

import java.util.Vector;

/**
 *
 * @author arturogf
 */
public class PDDLExpression {

    String expression = "";

    @Override
    public String toString()
    {
        return expression;
    }

    /**
     *
     * @param arguments the predicates to add as arguments of 'and' condition
     */
    public void AND(PDDLExpression...arguments)
    {
        if (arguments.length > 1)
            expression = expression + "(and ";
        else
            expression = expression + "(";


        for (int i=0; i<arguments.length;i++)
        {
            expression = expression + arguments[i].toString();
        }

        expression = expression + ")";

    }

    public void AND(Vector arguments)
    {
        if (arguments.size() > 1)
            expression = expression + "(and ";
        else
            expression = expression + "(";


        for (int i=0; i<arguments.size(); i++)
        {
            expression = expression + arguments.get(i).toString();
        }

        expression = expression + ")";

    }


    public void OR(PDDLExpression...arguments)
    {
        if (arguments.length > 1)
            expression = expression + "(or ";
        else
            expression = expression + "(";


        for (int i=0; i<arguments.length;i++)
        {
            expression = expression + arguments[i].toString();
        }

            expression = expression + ")";

    }
     public void OR(Vector arguments)
    {
        if (arguments.size() > 1)
            expression = expression + "(or ";
        else
            expression = expression + "(";


        for (int i=0; i<arguments.size(); i++)
        {
            expression = expression + arguments.get(i).toString();
        }

        expression = expression + ")";

    }


    public String predicate(String name, String...arguments)
    {
        String expr = "(" + name + " ";

         for (int i=0; i<arguments.length;i++)
        {
            expr = expr + arguments[i].toString() + " ";
        }

        expr = expr + ")";
        
        return expr;
    }

    public void setPredicate(String name, String...arguments)
    {
        String expr = "(" + name + " ";

         for (int i=0; i<arguments.length;i++)
        {
            expr = expr + arguments[i].toString() + " ";
        }

        expr = expr + ")";

        this.expression = expr;
    }

}



