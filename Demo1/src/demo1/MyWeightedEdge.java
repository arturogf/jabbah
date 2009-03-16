/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package demo1;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

import org.jgraph.*;
import org.jgraph.graph.*;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

// resolve ambiguity
import org.jgrapht.graph.DefaultEdge;

import com.jgraph.layout.*;

import com.jgraph.layout.tree.JGraphAbstractTreeLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;
import com.jgraph.layout.tree.OrganizationalChart;

import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.jgraph.layout.organic.JGraphOrganicLayout;
import com.jgraph.layout.organic.JGraphSelfOrganizingOrganicLayout;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.jgraph.layout.tree.JGraphRadialTreeLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;
import com.jgraph.layout.simple.SimpleGridLayout;

import org.jgrapht.*;
import org.jgrapht.graph.*;


/**
 *
 * @author arturogf
 */
public class MyWeightedEdge<V> extends DefaultEdge {

    double weight = 0.0;

    private V source;
    private V target;
    private String label;

    public MyWeightedEdge (V v1, V v2, String label)
    {
        this.source = v1;
        this.target = v2;
        this.label = label;
    }


    /* override method setWeight */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public Object getSource()
    {
        return this.source;
    }

    public Object getTarget()
    {
        return this.target;
    }

    public String toString()
    {
        String w = this.label.concat(" ").concat(Double.toString(this.weight));
    
        return w;
    }

}
