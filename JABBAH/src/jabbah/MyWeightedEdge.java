package jabbah;

import org.jgrapht.graph.*;

/**
 * Extends the DefaultEdge class, adding weights and custom label
 *
 * @author arturogf
 */
public class MyWeightedEdge<V> extends DefaultEdge
{

    double weight = 0.0;
    private V source;
    private V target;
    private String label;

    public MyWeightedEdge(V v1, V v2, String label)
    {
        this.source = v1;
        this.target = v2;
        this.label = label;
    }

    /**
     * Set the weight for the egde instance
     *
     * @param weight
     */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }
    /**
     * Returns the source of this directed edge
     *
     */
    public Object getSource()
    {
        return this.source;
    }
    /**
     * Returns the target of this directed edge
     *
     */
    public Object getTarget()
    {
        return this.target;
    }

    /**
     * Overrides the original toString method, that shows the label in the
     * graphical representation of the edge.
     *
     */
    @Override
    public String toString()
    {
        String label_string = this.label;

        return label_string;
    }
}
