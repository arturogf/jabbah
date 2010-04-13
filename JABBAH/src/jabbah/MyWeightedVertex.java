package jabbah;

import java.util.Vector;
import org.jgrapht.Graphs;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

/**
 * Represents a weighted vertex for a graph. 
 *
 * @param type
 * 0 - a normal node
 * 1 - a serial block node
 * 2 - a parallel block node
 * 3 - a gateway node
 *
 * @param block store a vector with the nodes that form a block, for type 1
 * and type 2
 *
 * @author arturogf
 */

class ParameterRelation
{
    String P;
    String Value;
    MyWeightedVertex target;
}


public class MyWeightedVertex
{
    int type = NodeType.DEFAULT;
    int restriction = TransitionRestriction.NONE;
    String label;
    String lane ="LANE_NAME";
    Double weight = 0.0;
    Double duration = 1.0;

    // in case it is a Gateway
    Parameter param;

    // needed for the BlockDetection class
    boolean marked;
    Vector block = null;
    // we are going to need these pairs for XOR params decisions
    Vector pairs = new Vector();

    // needed in case it is a subprocess node
    ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> subgraph;
    String aset_id="";



    /**
     * The constructor for a weighted vertex
     *
     * @param l the label for the vertex
     * @param w the weight for the vertex
     */
    public MyWeightedVertex(String l, double w)
    {
        this.label = l;
        this.weight = w;
    }

    public void addpair(String p, String value, MyWeightedVertex t)
    {
        ParameterRelation pr = new ParameterRelation();

        pr.P = p;
        pr.Value = value;
        pr.target = t;

        this.pairs.add(pr);
    }

    /*public MyWeightedVertex getPairTarget (Parameter p, String v)
    {
        ParameterRelation pr;

        for (int i=0; i<this.pairs.size();i++)
        {
            pr =(ParameterRelation) this.pairs.get(i);

            if ((pr.P.id.equals(p.id)) && pr.Value.equals(v))
                return pr.target;
        }

        return null;
        
    }
     * */
    /**
     * The constructor for a weighted vertex, using a default weight
     *
     * @param l the label for the vertex
     */
    public MyWeightedVertex(String l)
    {
        this.label = l;
    }

     /**
     * Set the weight for a vertex
     *
     * @param w the weight to be set for a specific node instance
     */
    public void setWeight(double w)
    {
        this.weight = w;
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
     * Set the duration for a vertex
     *
     * @param l the duration to be set as a String
     */
    public void setDuration(String l)
    {
        this.duration = Double.parseDouble(l);
    }

    /**
     * Overrides the original method, so that the corresponding node label shown
     * is the result of concatenate the node label with its weight
     *
     */
    @Override
    public String toString()
    {
        return this.label.concat(" ").concat(this.weight.toString());
    }

     /**
     * Sets the block to the specified vector
     *
     * @param v the vector that represents the block for parallel/serial blocks
     */
    public void setBlock(Vector v)
    {
        if (this.type == NodeType.SERIAL || this.type == NodeType.PARALLEL)
            this.block = v;
    }

    /**
     *
     * @param pred the vector to be filled up with predeccesor nodes
     * @param G the Graph to be examined
     * @return an error code <> 0 or 0 if it was correct
     */
    public int getPredecessorActivities(Vector pred,
            ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> G)
    {
        if (pred == null)
            pred = new Vector();
        
        for (MyWeightedVertex p: Graphs.predecessorListOf(G,this))
        {
            if (p.type == NodeType.DEFAULT)
                pred.add(p);
            else
                if (p.type == NodeType.GATEWAY)
                    pred.addAll(Graphs.predecessorListOf(G, p));

        }

        return 0;
    }

}
