/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package demo1;

import org.jgraph.*;
import org.jgraph.graph.*;


import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import org.jgrapht.Graphs;

import java.util.List;
import java.util.Set;


/**
 *
 * @author arturogf
 * This class computes the ECA Rules algorithm.
 * It is composed of the five steps, implemented as private methods of
 * the class
 */
public class BlockDetection {

    ListenableDirectedWeightedGraph <MyWeightedVertex, MyWeightedEdge> G;
    Set N; // obtained with vertexSet()
    Set A; // obtained with edgeSet()
    Object S;

    private void branchWater()
    {
        for (MyWeightedEdge edge : G.edgeSet()) {
            if (edge.getSource() == "S")
                System.out.printf("%s is an enemy of %s\n", edge.getSource(), edge.getTarget());
        }
    } 


    public BlockDetection(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g)
    {
        this.G = (ListenableDirectedWeightedGraph <MyWeightedVertex, MyWeightedEdge>) g;
        this.N = G.vertexSet();
        this.A = G.edgeSet();

        this.branchWater();


        Object[] n = this.N.toArray();
        
        System.out.println("Nodos:");

        for (int i=0; i< n.length ; i++)
        {
            System.out.println(n[i]);
            //Set s = g.outgoingEdgesOf(n[i].toString());
        }

         Object[] a = this.A.toArray();

        System.out.println("Aristas:");

        for (int i=0; i< a.length ; i++)
        {
            System.out.println(a[i]);
        }


        
    }

}
