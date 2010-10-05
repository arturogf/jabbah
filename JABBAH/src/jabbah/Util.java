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
        result = result.replace("¿","");
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
        result = result.replace("¿","");
        result = result.replace("/","");
        result = result.replace("'","");
        result = result.replace(",","");
        result = result.replace(";","");
        result = result.replace("(","");
        result = result.replace(")","");

        return result;
    }


}
