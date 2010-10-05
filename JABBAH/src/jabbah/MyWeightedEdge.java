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
package jabbah;

import org.jgrapht.graph.*;

/**
 * Extends the DefaultEdge class, adding weights and custom label
 *
 * @author arturogf
 */
public class MyWeightedEdge<V> extends DefaultEdge
{

    double weight = 0.0;
    private V source;
    private V target;
    private String label;

    String p = "";
    String operator = "";
    String v = "";

    public MyWeightedEdge(V v1, V v2, String label)
    {
        this.source = v1;
        this.target = v2;
        this.label = label;
    }

    /**
     * Set the weight for the egde instance
     *
     * @param weight
     */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

      /**
     * Set the label for a vertex
     *
     * @param l the label to be set for a specific node instance
     */
    public void setLabel(String l)
    {
        this.label = l;
    }

    /**
     * Returns the source of this directed edge
     *
     */
    public Object getSource()
    {
        return this.source;
    }
    /**
     * Returns the target of this directed edge
     *
     */
    public Object getTarget()
    {
        return this.target;
    }

    /**
     * Overrides the original toString method, that shows the label in the
     * graphical representation of the edge.
     *
     */
    @Override
    public String toString()
    {
        String label_string = this.label;

        return label_string;
    }
}

