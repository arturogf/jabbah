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

    ListenableDirectedWeightedGraph <String, MyWeightedEdge> G;
    Set N; // obtained with vertexSet()
    Set A; // obtained with edgeSet()
    Object S;

    private void branchWater()
    {

    } 


    public BlockDetection(ListenableDirectedWeightedGraph<String, MyWeightedEdge> g)
    {
        this.G = (ListenableDirectedWeightedGraph <String, MyWeightedEdge>) g;
        this.N = g.vertexSet();
        this.A = g.edgeSet();

        this.branchWater();


        Object[] n = this.N.toArray();
        
        System.out.println("Nodos:");

        for (int i=0; i< n.length ; i++)
        {
            System.out.println(n[i]);
            Set s = g.outgoingEdgesOf(n[i].toString());
        }

         Object[] a = this.A.toArray();

        System.out.println("Aristas:");

        for (int i=0; i< a.length ; i++)
        {
            System.out.println(a[i]);
        }


        
    }

}
