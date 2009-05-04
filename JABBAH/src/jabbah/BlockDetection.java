/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbah;

import org.jgrapht.graph.*;
import org.jgrapht.Graphs;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;
import java.util.Iterator;

/**
 * Computes the ECA Rules block detection algorithm.
 * It is composed of the four steps, implemented as private methods of
 * the class, that are executed respectively in the class constructor.
 *
 * @author Arturo González Ferrer <arturogf@ugr.es>
 */
public class BlockDetection
{

    ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> G;
    Set N; // Set of vertices
    private static int pb_index = 1; // index for parallel blocks nodes
    private static int sb_index = 1;    // index for serial blocks nodes

    /**
     * Execute the ECA Rules Block Detection algorithm over the graph
     * passed as parameters, and change it to a hierarchical tree representation
     *
     * @param g  the graph to analize, given as a ListenableDirectedWeightedGraph.
     */
    public BlockDetection(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g)
    {

        Vector serial, parallel = new Vector();


        this.G = (ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge>) g;
        this.N = G.vertexSet();

        this.branchWater();

        // consecutive serial and parallel block detection, searching for
        // the most inner block until we have only one node (root) in G
        while (G.vertexSet().size() != 1)
        {
            serial = this.SerialBlockDetection();

            if (serial == null)
            {
                parallel = this.ParallelBlockDetection();

                if (parallel == null)
                {
                    System.out.print("WARNING!!!: serial and parallel block " +
                            "detection were unsucessful!");
                } else
                {
                    this.replaceNodesWithPB(parallel);
                }
            } else
            {
                this.replaceNodesWithSB(serial);
            }
        }

        // at this point, G has size = 1, so we can rebuild the tree
        // using the internal "block" Vector found at MyWeightedVertex class
        MyWeightedVertex root_node = G.vertexSet().iterator().next();
        this.rebuildTree(root_node);
    }

    /**
     * Creates a new PB node (type = 2), hooks it with predecessor and succesors and
     * removes the vertices found in Vector v
     *
     * @param v  a Vector containing the nodes to be replaced with a new PB node
     */
    private void replaceNodesWithPB(Vector v)
    {
        // if a parallel block was detected, substitute it with
        // a new PB node
        MyWeightedVertex pb = new MyWeightedVertex("PB" + this.pb_index, 0);
        G.addVertex(pb);
        pb.type = NodeType.PARALLEL;
        pb.setBlock(v);
        MyWeightedVertex first = (MyWeightedVertex) v.firstElement();
        MyWeightedVertex last = (MyWeightedVertex) v.lastElement();

        pb.setWeight(first.weight);

        // hook the new node with previous predecessors and sucessors
        for (MyWeightedVertex pre : Graphs.predecessorListOf(G, first))
        {
            G.addEdge(pre, pb, new MyWeightedEdge(pre, pb, ""));
        }

        for (MyWeightedVertex suc : Graphs.successorListOf(G, last))
        {
            G.addEdge(pb, suc, new MyWeightedEdge(pb, suc, ""));
        }
        // remove all vertices from G that are not in vector "parallel"
        G.removeAllVertices(v);

        this.pb_index = this.pb_index + 1;
    }


    /**
     * Creates a new SB node (type = 1), hooks it with predecessors and succesors and
     * removes the vertices found in Vector v
     *
     * @param v  a Vector containing the nodes to be replaced with a new SB node
     */
    private void replaceNodesWithSB(Vector v)
    {
        // if a serial block was detected, we substitute it by a new SB node
        MyWeightedVertex sb = new MyWeightedVertex("SB" + this.sb_index, 0);
        G.addVertex(sb);
        sb.type = NodeType.SERIAL;
        sb.setBlock(v);
        MyWeightedVertex first = (MyWeightedVertex) v.firstElement();
        MyWeightedVertex last = (MyWeightedVertex) v.lastElement();

        sb.setWeight(first.weight);

        // hook the new node with previous predecessors and sucessors
        for (MyWeightedVertex pre : Graphs.predecessorListOf(G, first))
        {
            G.addEdge(pre, sb, new MyWeightedEdge(pre, sb, ""));
        }

        for (MyWeightedVertex suc : Graphs.successorListOf(G, last))
        {
            G.addEdge(sb, suc, new MyWeightedEdge(sb, suc, ""));
        }
        // remove all vertices from G that are not in vector "serial"
        G.removeAllVertices(v);

        this.sb_index = this.sb_index + 1;

    }


    /**
     * Rebuilds the graph as a tree of the blocks (serial, parallel) detected
     * previously in BlockDetection.
     *
     * @param j  the root Vertex from which we rebuild the hierarchical tree
     */
    private void rebuildTree(MyWeightedVertex j)
    {
        // normal nodes are not explored
        if (j.type == NodeType.DEFAULT)
        {
            return;
        }

        // iterate the respective j block nodes
        Iterator i = j.block.iterator();
        while (i.hasNext())
        {
            MyWeightedVertex y = (MyWeightedVertex) i.next();

            // gateways nodes are not shown
            if (y.type != NodeType.GATEWAY)
            {
                G.addVertex(y);
                G.addEdge(j, y, new MyWeightedEdge(j, y, ""));
                if (y.type != NodeType.DEFAULT)
                {
                    this.rebuildTree(y);
                }
            }

        }

        return;
    }

    /**
     * Mark the nodes with weights (0.0-1.0), as if there was a pipe with 1.0 
     * of water that goes from start_node S to end_node E.
     *
     */
    private void branchWater()
    {
        boolean add_to_queue = true;

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
                j.setWeight(j.weight + (v.weight / Graphs.successorListOf(G, v).size()));

                add_to_queue = true;
                // check that all predecesors are marked
                for (MyWeightedVertex p : Graphs.predecessorListOf(G, j))
                {
                    if (!p.marked)
                    {
                        add_to_queue = false;
                    }
                }

                if (add_to_queue)
                {
                    queue.addElement((MyWeightedVertex) j);
                }
            }
        }
    }

    /**
     * Search the min weight in N, this graph Vertex Set. This min value will be
     * used by the SerialBlockDetection and ParallelBlockDetection
     *
     * @return The minimum weight found in the graph Vertex Set
     */
    private Double minWeight()
    {
        Double min = 1.0;
        Iterator i = this.N.iterator();

        MyWeightedVertex Node;

        while (i.hasNext())
        {
            Node = (MyWeightedVertex) i.next();
            if (Node.weight < min)
            {
                min = Node.weight;
            }

        }

        return min;
    }

    /**
     * Detects a serial block in G, supposing that the min-w is found and
     * this block size is greater than 1 node.
     *
     * @return the vector containg the Vertex nodes that forms the Serial Block
     *
     */
    private Vector SerialBlockDetection()
    {
        MyWeightedVertex v;

        boolean LOOP = true;
        boolean finish = true;
        boolean continua = false;

        Double min_weight = this.minWeight();

        List<MyWeightedVertex> successors;

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

            v = (MyWeightedVertex) queue.firstElement();
            queue.removeElementAt(0);

            // once we recognize the node with min weight, and in-out degrees are 1
            // we have to do the same operation and build the SerialBlock detected
            if ((v.weight.doubleValue() == min_weight.doubleValue()) &&
                    (G.inDegreeOf(v) <= 1) &&
                    (G.outDegreeOf(v) == 1))
            {
                LOOP = false;
                SB.addElement(v);

                while (finish)
                {
                    continua = false;
                    successors = new ArrayList<MyWeightedVertex>();
                    successors = Graphs.successorListOf(G, v);

                    for (MyWeightedVertex j : successors)
                    {
                        if (j.weight.equals(v.weight))
                        {
                            SB.addElement(j);
                            continua = true;
                        }
                    }

                    if (continua == false)
                    {
                        finish = false;
                    } else
                    {
                        v = (MyWeightedVertex) SB.lastElement();
                    }
                }
            } else // Add the successors of v to queue
            {
                for (MyWeightedVertex j : Graphs.successorListOf(G, v))
                {
                    queue.addElement((MyWeightedVertex) j);
                }
            }
        }

        if (SB.size() > 1)
        {
            return SB;
        } else
        {
            return null;
        }
    }

    /**
     * Detects a Parallel Block in G, supposing that the min-w is found
     * and this block size is greater than 1 node.
     *
     * @return the vector containg the Vertex nodes that forms the Parallel Block
     * 
     */
    private Vector ParallelBlockDetection()
    {
        boolean LOOP = true;
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
            if (v.weight.doubleValue() == min_weight.doubleValue())
            {
                LOOP = false;

                MyWeightedVertex pre = Graphs.predecessorListOf(G, v).get(0);
                for (MyWeightedVertex j : Graphs.successorListOf(G, pre))
                {
                    PB.addElement((MyWeightedVertex) j);
                }
            } else // Add the successors of v to queue
            {
                for (MyWeightedVertex j : Graphs.successorListOf(G, v))
                {
                    queue.addElement((MyWeightedVertex) j);
                }
            }
        }

        if (PB.size() > 1)
        {
            MyWeightedVertex j = (MyWeightedVertex) PB.get(0);
            PB.add(0, Graphs.predecessorListOf(G, j).get(0));
            PB.addElement(Graphs.successorListOf(G, j).get(0));
            return PB;
        } else
        {
            return null;
        }
    }
}