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
    ListenableDirectedWeightedGraph <MyWeightedVertex, MyWeightedEdge> out;

    Set N; // obtained with vertexSet()
    Set A; // obtained with edgeSet()

    // constructor 
    public BlockDetection(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g)
    {

        Vector serial, parallel = new Vector();

        this.G = (ListenableDirectedWeightedGraph <MyWeightedVertex, MyWeightedEdge>) g;
        this.N = g.vertexSet();
        this.A = g.edgeSet();

        this.branchWater();


        parallel = this.ParallelBlockDetection();

        if (parallel != null)
        {
            Iterator i = parallel.iterator();

            while (i.hasNext())
            {
                System.out.println(i.next().toString());
            }
        }
        else
            System.out.println("PB nulllllll");



        serial = this.SerialBlockDetection();

        if (serial != null)
        {
            Iterator i = serial.iterator();

            while (i.hasNext())
            {
                System.out.println(i.next().toString());
            }
        }
        else
            System.out.println("nulllllll");
    }

    // mark the nodes with weights, as if there was a pipe with 1.0 of water
    // that goes from start node to end node
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
                j.setWeight(j.weight + (v.weight/ Graphs.successorListOf(G, v).size()));

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
    }
    // this function search for the min weight in N, the Vertex Set. It is
    // used by the SerialBlockDetection and ParallelBlockDetection

    private Double minWeight()
    {
        Double min = 1.0;
        Iterator i = this.N.iterator();

        MyWeightedVertex Node;
        
        while (i.hasNext())
        {
             Node = (MyWeightedVertex) i.next();
             if (Node.weight < min)
                min = Node.weight;

        }
        
        return min;
    }

    // this function detects a serialblock in G, supposing that the min-w
    // is found and this block size is > 1 node.
    private Vector SerialBlockDetection()
    {
        boolean LOOP = true;
        boolean finish = false;
        Double min_weight = this.minWeight();

        Vector queue = new Vector();
        Vector SB = new Vector();

        Iterator i = this.N.iterator();

        MyWeightedVertex S = (MyWeightedVertex) i.next();

        queue.addElement(S);

        while (LOOP)
        {
            if (queue.isEmpty())
            {
                return null;
            }
            
            MyWeightedVertex v = (MyWeightedVertex) queue.firstElement();
            queue.removeElementAt(0);

            // once we recognize the node with min weight, and in-out degrees of 1
            // we have to do the same operation and build the SerialBlock Detected
            if ( (v.weight == min_weight) &&
                 (G.inDegreeOf(v) == 1) &&
                 (G.outDegreeOf(v)== 1))
            {
                LOOP = false;
                SB.addElement(v);

                while (!finish)
                {
                    for (MyWeightedVertex j : Graphs.successorListOf(G, v))
                    {
                        if (j.weight == v.weight)
                            SB.addElement((MyWeightedVertex) j);
                        else
                            finish = true;
                    }
                }


                //SB = this.SerialFrom(v);
            }
            else    // Add the successors of v to queue
                for (MyWeightedVertex j : Graphs.successorListOf(G, v))
                    queue.addElement((MyWeightedVertex) j);
        }

        if (SB.size() > 1)
            return SB;
        else
            return null;
    }

    // this function detects a serialblock in G, supposing that the min-w
    // is found and this block size is > 1 node.
    private Vector ParallelBlockDetection()
    {
        boolean LOOP = true;
        boolean finish = false;
        Double min_weight = this.minWeight();

        Vector queue = new Vector();
        Vector PB = new Vector();

        Iterator i = this.N.iterator();

        MyWeightedVertex S = (MyWeightedVertex) i.next();

        queue.addElement(S);

        while (LOOP)
        {
            if (queue.isEmpty())
            {
                return null;
            }

            MyWeightedVertex v = (MyWeightedVertex) queue.firstElement();
            queue.removeElementAt(0);

            // once we recognize the node with min weight, and in-out degrees of 1
            // we have to do the same operation and build the SerialBlock Detected
            if (v.weight == min_weight)
            {
                LOOP = false;
             
                    MyWeightedVertex pre = Graphs.predecessorListOf(G, v).get(0);
                    for (MyWeightedVertex j : Graphs.successorListOf(G, pre))
                            PB.addElement((MyWeightedVertex) j);    
            }
            else    // Add the successors of v to queue
                for (MyWeightedVertex j : Graphs.successorListOf(G, v))
                    queue.addElement((MyWeightedVertex) j);
        }

        if (PB.size() > 1)
        {
            MyWeightedVertex j = (MyWeightedVertex) PB.get(0);
            PB.add(0,Graphs.predecessorListOf(G, j).get(0));
            PB.addElement(Graphs.successorListOf(G, j).get(0));
            return PB;
        }
        else
            return null;
    }


    
}
