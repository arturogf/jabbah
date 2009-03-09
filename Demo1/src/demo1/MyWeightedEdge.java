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


/**
 *
 * @author arturogf
 */
public class MyWeightedEdge extends DefaultEdge {

    double weight = WeightedGraph.DEFAULT_EDGE_WEIGHT;


    /* override method setWeight */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public String toString()
    {
        return Double.toString(this.weight);
    }

}
