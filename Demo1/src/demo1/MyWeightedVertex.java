/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package demo1;

import java.lang.Comparable;

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
public class MyWeightedVertex {

    String label;
    Double weight = 0.0;
    
    public MyWeightedVertex (String l, double w)
    {
        this.label = l;
        this.weight = w;
    }

     public MyWeightedVertex (String l)
    {
        this.label = l;
    }

    public void setWeight(double w)
    {
        this.weight = w;
    }

    public void setLabel(String l)
    {
        this.label = l;
    }

    public String toString()
    {
        return this.label.concat(" ").concat(this.weight.toString());
    }

}
