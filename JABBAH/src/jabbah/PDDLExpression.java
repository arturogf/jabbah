/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jabbah;

/**
 *
 * @author arturogf
 */
public class PDDLExpression {

    String expression="";

    @Override
    public String toString()
    {
        return expression;
    }

    /**
     *
     * @param arguments the predicates to add as arguments of 'and' condition
     */
    public void AND(String...arguments)
    {
        if (arguments.length > 1)
            expression = expression + "(and ";

        for (int i=0; i<arguments.length;i++)
        {
            expression = expression + arguments[i];
        }
        if (arguments.length > 1)
            expression = expression + ")";

    }

    public String OR(String...arguments)
    {
        String expr="";

        if (arguments.length > 1)
            expr = expr + "(or ";

        for (int i=0; i<arguments.length;i++)
        {
            expr = expr + arguments[i];
        }
        if (arguments.length > 1)
            expr = expr + ")";

        return expr;
    }

    public String predicate(String name, String...arguments)
    {
        String expr = "(" + name + " ";

         for (int i=0; i<arguments.length;i++)
        {
            expr = expr + arguments[i] + " ";
        }

        expr = expr + ")";

        return expr;
    }

}


