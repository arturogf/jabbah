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
import java.util.Vector;

import java.util.Iterator;


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
        boolean a2q = true;
        
        Vector queue = new Vector();
        Vector w_list = new Vector();

        Iterator i = this.N.iterator();

        MyWeightedVertex S = (MyWeightedVertex) i.next();
        S.setWeight(1.0);

        queue.addElement(S);
        w_list.addElement(1.0);

        while (!queue.isEmpty())
        {
            MyWeightedVertex v = (MyWeightedVertex) queue.firstElement();
            queue.removeElementAt(0);
            v.marked = true;

            for (MyWeightedVertex j : Graphs.successorListOf(G, v))
            {
                j.setWeight(j.weight+(v.weight/ Graphs.successorListOf(G, v).size()));

                a2q = true;
                // check that all predecesors are marked
                for (MyWeightedVertex p : Graphs.predecessorListOf(G, j))
                {
                    if (!p.marked)
                        a2q = false;
                }

                if (a2q)
                    queue.addElement((MyWeightedVertex) j);
            }
        }


        /*for (MyWeightedEdge edge : G.vertexSet()) {
            if (vertex.getSource() == "S")
                System.out.printf("%s is an enemy of %s\n", edge.getSource(), edge.getTarget());
        }*/
    } 


    public BlockDetection(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g)
    {
        this.G = (ListenableDirectedWeightedGraph <MyWeightedVertex, MyWeightedEdge>) g;
        this.N = g.vertexSet();
        this.A = g.edgeSet();

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
