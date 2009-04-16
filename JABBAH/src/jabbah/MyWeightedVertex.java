
package jabbah;

import java.util.Vector;

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
public class MyWeightedVertex
{
    int type = NodeType.DEFAULT;
    String label;
    Double weight = 0.0;
    Double duration = 1.0;
    
    boolean marked;
    Vector block = null;

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
}

/**
 * Define some constants that represents the type of node
 * 
 */
class NodeType
{
    public static final int DEFAULT = 0;
    public static final int SERIAL = 1;
    public static final int PARALLEL = 2;
    public static final int GATEWAY = 3;
}
