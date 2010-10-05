/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbah;

import java.io.IOException;
import org.jgrapht.graph.*;
import org.jgrapht.Graphs;
import org.jgrapht.alg.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
    java.util.List<java.util.Set<MyWeightedVertex>> connectedSubgraphs;

    private static int pb_index = 1; // index for parallel blocks nodes
    private static int sb_index = 1;    // index for serial blocks nodes

    private static Logger logger = Logger.getLogger(BlockDetection.class.getName());
    private static FileHandler handler;

    /**
     * Execute the ECA Rules Block Detection algorithm over the graph
     * passed as parameters, and change it to a hierarchical tree representation
     *
     * @param g  the graph to analize, given as a ListenableDirectedWeightedGraph.
     */
    public BlockDetection(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g)
    {

        try {
            String tmpdir = System.getProperty("java.io.tmpdir");
            handler = new FileHandler(tmpdir + "/jabbah.log", true);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "IOException Creating the log file ", ex);
        } catch (SecurityException ex) {
            logger.log(Level.SEVERE, "SecurityException Creating the log file ", ex);
        }
        // Add to the desired logger
        SimpleFormatter fm = new SimpleFormatter();
        handler.setFormatter(fm);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);

        Vector serial, parallel = new Vector();


        this.G = (ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge>) g;
        this.N = G.vertexSet();

        this.branchWater(1.0);

        // consecutive serial and parallel block detection, searching for
        // the most inner block until we have only one node (root) in G

        // TODO: Esto debería ser en realidad hecho de forma independiente para
        // cada subgrafo en G, de lo contrario, falla

        ConnectivityInspector ci = new ConnectivityInspector(g);
        connectedSubgraphs = ci.connectedSets();
        

        for (int i = 0; i < connectedSubgraphs.size(); i++) {
            while (connectedSubgraphs.get(i).size() != 1) {
                serial = this.SerialBlockDetection(connectedSubgraphs.get(i));

                if (serial == null) {
                    parallel = this.ParallelBlockDetection(connectedSubgraphs.get(i));

                    if (parallel == null) {
                        logger.log(Level.SEVERE,
                                "Serial and Parallel block detection were unsucessful");
                        break;
                    } else {
                        this.replaceNodesWithPB(connectedSubgraphs.get(i),parallel);
                    }
                } else {
                    this.replaceNodesWithSB(connectedSubgraphs.get(i),serial);
                }
            }

            // at this point, G has size = 1, so we can rebuild the tree
            // using the internal "block" Vector found at MyWeightedVertex class
            MyWeightedVertex root_node = (MyWeightedVertex) connectedSubgraphs.get(i).iterator().next();
            this.rebuildTree(root_node);

            handler.close();
        }
    }

    /**
     * Creates a new PB node (type = 2), hooks it with predecessor and succesors and
     * removes the vertices found in Vector v
     *
     * @param v  a Vector containing the nodes to be replaced with a new PB node
     */
    private void replaceNodesWithPB(Set<MyWeightedVertex> subset, Vector v)
    {
        // if a parallel block was detected, substitute it with
        // a new PB node
        MyWeightedVertex pb = new MyWeightedVertex("PB" + BlockDetection.pb_index, 0);
        G.addVertex(pb);
        pb.type = NodeType.PARALLEL;
        pb.setBlock(v);
        MyWeightedVertex first = (MyWeightedVertex) v.firstElement();
        MyWeightedVertex last = (MyWeightedVertex) v.lastElement();

        pb.setWeight(first.weight);
        pb.param = first.param;
        pb.restriction = first.restriction;

        // we try to store the ParameterRelations for this node
        if (pb.restriction == TransitionRestriction.SPLIT_EXCLUSIVE)
        {
            for (MyWeightedVertex suc : Graphs.successorListOf(G, first))
            {
                List path = DijkstraShortestPath.findPathBetween(G, first, suc);
                if (path.size()>1)
                     logger.log(Level.SEVERE, "Path between nodes cannot be greater than one edge!!!");
                else if (path.size()<1)
                    logger.log(Level.SEVERE, "Path between nodes should exists!!!");
                else {
                    MyWeightedEdge e = (MyWeightedEdge) path.get(0);
                    pb.addpair(e.p, e.v, suc);
                }
            }
        }

        // hook the new node with previous predecessors and sucessors
        for (MyWeightedVertex pre : Graphs.predecessorListOf(G, first))
        {
            MyWeightedEdge e = new MyWeightedEdge(pre, pb, "");
            List path = DijkstraShortestPath.findPathBetween(G, pre, first);
            if (path.size() > 1) {
                logger.log(Level.SEVERE, "Path between nodes cannot be greater than one edge!!!");
            } else if (path.size() < 1) {
                logger.log(Level.SEVERE, "Path between nodes should exists!!!");
            } else {
                 MyWeightedEdge aux = (MyWeightedEdge) path.get(0);
                e.p = aux.p;
                e.operator = aux.operator;
                e.v = aux.v;
            }
            G.addEdge(pre, pb, e);
        }

        for (MyWeightedVertex suc : Graphs.successorListOf(G, last))
        {
            G.addEdge(pb, suc, new MyWeightedEdge(pb, suc, ""));
        }
        // remove all vertices from G that are not in vector "parallel"
        G.removeAllVertices(v);

        subset.removeAll(v);
        subset.add(pb);


        BlockDetection.pb_index = BlockDetection.pb_index + 1;
    }


    /**
     * Creates a new SB node (type = 1), hooks it with predecessors and succesors and
     * removes the vertices found in Vector v
     *
     * @param v  a Vector containing the nodes to be replaced with a new SB node
     */
    private void replaceNodesWithSB(Set<MyWeightedVertex> subset,Vector v)
    {
        // if a serial block was detected, we substitute it by a new SB node
        MyWeightedVertex sb = new MyWeightedVertex("SB" + BlockDetection.sb_index, 0);
        G.addVertex(sb);
        sb.type = NodeType.SERIAL;
        sb.setBlock(v);
        MyWeightedVertex first = (MyWeightedVertex) v.firstElement();
        MyWeightedVertex last = (MyWeightedVertex) v.lastElement();

        sb.setWeight(first.weight);

        // hook the new node with previous predecessors and sucessors
        for (MyWeightedVertex pre : Graphs.predecessorListOf(G, first))
        {
            MyWeightedEdge e = new MyWeightedEdge(pre, sb, "");
            List path = DijkstraShortestPath.findPathBetween(G, pre, first);
            if (path.size() > 1) {
                logger.log(Level.SEVERE, "Path between nodes cannot be greater than one edge!!!");
            } else if (path.size() < 1) {
                logger.log(Level.SEVERE, "Path between nodes should exists!!!");
            } else {
                 MyWeightedEdge aux = (MyWeightedEdge) path.get(0);
                e.p = aux.p;
                e.operator = aux.operator;
                e.v = aux.v;
            }
            G.addEdge(pre, sb, e);
            //G.addEdge(pre, sb, new MyWeightedEdge(pre, sb, ""));
        }

        for (MyWeightedVertex suc : Graphs.successorListOf(G, last))
        {
            G.addEdge(sb, suc, new MyWeightedEdge(sb, suc, ""));
        }
        // remove all vertices from G that are not in vector "serial"
        G.removeAllVertices(v);

        subset.removeAll(v);
        subset.add(sb);


        BlockDetection.sb_index = BlockDetection.sb_index + 1;

    }


    /**
     * Rebuilds the graph as a tree of the blocks (serial, parallel) detected
     * previously in BlockDetection.
     *
     * @param j  the root Vertex from which we rebuild the hierarchical tree
     */
    private void rebuildTree(MyWeightedVertex j) {

        // normal nodes are not explored
        if (j.type == NodeType.DEFAULT ||
                j.type == NodeType.START ||
                j.type == NodeType.END) {
            return;
        }

        
        if (j.type == NodeType.SUBPROCESS) {
            
            MyWeightedVertex p = Graphs.predecessorListOf(G, j).iterator().next();
            MyWeightedVertex v = j.subgraph.vertexSet().iterator().next();

            G.addVertex(v);
            G.addEdge(p,v,new MyWeightedEdge(p, v, ""));
            
            this.rebuildTree(v);
            G.removeVertex(j);

        } else {
            // iterate the respective j block nodes
            Iterator i;
            i = j.block.iterator();

            while (i.hasNext()) {
                MyWeightedVertex y = (MyWeightedVertex) i.next();

                // gateways and subprocesses nodes are not shown
                if (y.type != NodeType.GATEWAY) {

                    G.addVertex(y);
                    G.addEdge(j, y, new MyWeightedEdge(j, y, ""));
                    
                    if (y.type != NodeType.DEFAULT) {
                        this.rebuildTree(y);
                    }
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
    private void branchWater(double initial)
    {
        boolean add_to_queue = true;

        Vector queue = new Vector();
        Vector w_list = new Vector();

        Iterator i = this.N.iterator();
        MyWeightedVertex S;

        Vector StartNodes = this.findStartNodes(this.N);

        if (StartNodes.isEmpty()) {
            logger.log(Level.SEVERE,
                         "There are no Start nodes when doing branchWater procedure");
        }
        else
        {
            if (StartNodes.size()>1)
                logger.log(Level.WARNING,
                         "There are no more than 1 Start Node!");

            // we can find a graph with multiple start nodes
            // (i.e. a cooperative system with multiple graphs in lanes)
            while (!StartNodes.isEmpty()) {

                S = (MyWeightedVertex) StartNodes.firstElement();
                S.setWeight(initial);

                queue.addElement(S);
                w_list.addElement(initial);

                while (!queue.isEmpty()) {
                    MyWeightedVertex v = (MyWeightedVertex) queue.firstElement();
                    queue.removeElementAt(0);
                    v.marked = true;

                    for (MyWeightedVertex j : Graphs.successorListOf(G, v)) {
                        j.setWeight(j.weight + (v.weight / Graphs.successorListOf(G, v).size()));

                        add_to_queue = true;
                        // check that all predecesors are marked
                        for (MyWeightedVertex p : Graphs.predecessorListOf(G, j)) {
                            if (!p.marked) {
                                add_to_queue = false;
                            }
                        }

                        if (add_to_queue) {
                            queue.addElement((MyWeightedVertex) j);
                        }
                    }
                }
                // remove the start node for the subgraph just processed
                if (StartNodes.size()>0)
                    StartNodes.removeElementAt(0);
            }
        
        }

    }

    /**
     * Search the min weight in N, this graph Vertex Set. This min value will be
     * used by the SerialBlockDetection and ParallelBlockDetection
     *
     * @return The minimum weight found in the graph Vertex Set
     */
    private Double minWeight(Set<MyWeightedVertex> subset)
    {
        Double min = 1.0;
        Iterator i = subset.iterator();

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
    private Vector SerialBlockDetection(Set<MyWeightedVertex> subset)
    {
        MyWeightedVertex v;

        boolean LOOP = true;
        boolean finish = true;
        boolean continua = false;

        // TODO: esto falla cuando hay varios subgrafos en G
        Double min_weight = this.minWeight(subset);

        List<MyWeightedVertex> successors;

        MyWeightedVertex S;
        
        Vector queue = new Vector();
        Vector SB = new Vector();
        Vector StartNodes = this.findStartNodes(subset);

        if (!StartNodes.isEmpty())
             S = (MyWeightedVertex) StartNodes.firstElement();
        else
            return null;

        queue.addElement(S);

        while ((LOOP) && (!queue.isEmpty()))
        {
            v = (MyWeightedVertex) queue.firstElement();
            queue.removeElementAt(0);

            // once we recognize the node with min weight, and in-out degrees are 1
            // we have to do the same operation and build the SerialBlock detected
            if ((v.weight.doubleValue() == min_weight.doubleValue()) &&
                    (G.inDegreeOf(v) <= 1) &&
                    (G.outDegreeOf(v) == 1))
            {
                //LOOP = false;
                SB.addElement(v);

                finish = true;
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
                // si al salir de aquí sólo tiene 1 elemento, borralo
                if (SB.size() == 1)
                    SB.removeAllElements();
                else
                    return SB;

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
    private Vector ParallelBlockDetection(Set<MyWeightedVertex> subset)
    {
        boolean LOOP = true;

        // TODO: esto falla cuando hay varios subgrafos en G
        Double min_weight = this.minWeight(subset);

        Vector queue = new Vector();
        Vector PB = new Vector();

        MyWeightedVertex S;

        Vector StartNodes = this.findStartNodes(subset);

        if (!StartNodes.isEmpty())
             S = (MyWeightedVertex) StartNodes.firstElement();
        else
            return null;

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

                try {
                    MyWeightedVertex pre = Graphs.predecessorListOf(G, v).get(0);


                    for (MyWeightedVertex j : Graphs.successorListOf(G, pre)) {
                        PB.addElement((MyWeightedVertex) j);
                    }
                } catch (java.lang.IndexOutOfBoundsException ex) {
                    logger.severe("Cannot get predecessors List for node in ParallelBlockDetection()");
                    //System.exit(1);
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

    /**
     * return a Set of Start Nodes, useful for other methods
     *
     * @param N  the Set of Vertex
     */
    private Vector findStartNodes(Set N)
    {
        Vector StartNodes = new Vector();

        Iterator i = N.iterator();
        

        do
        {
            MyWeightedVertex node = (MyWeightedVertex) i.next();

            if (node.type == NodeType.START)
                StartNodes.add(node);
        }
        while (i.hasNext());

        return StartNodes;

    }

    /**
     * return a Set of End Nodes, useful for other methods
     *
     * @param N  the Set of Vertex
     */
    private Vector findEndNodes(Set N)
    {
        Vector EndNodes = new Vector();

        Iterator i = N.iterator();

        

        do
        {
            MyWeightedVertex node = (MyWeightedVertex) i.next();
            
            if (node.type == NodeType.END)
                EndNodes.add(node);
        }
        while (i.hasNext());

        return EndNodes;

    }
}
