/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package demo1;
import org.jgrapht.graph.*;


/**
 *
 * @author arturogf
 */
public class MyWeightedEdge<V> extends DefaultEdge {

    double weight = 0.0;

    private V source;
    private V target;
    private String label;

    public MyWeightedEdge (V v1, V v2, String label)
    {
        this.source = v1;
        this.target = v2;
        this.label = label;
    }


    /* override method setWeight */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public Object getSource()
    {
        return this.source;
    }

    public Object getTarget()
    {
        return this.target;
    }

    @Override
    public String toString()
    {
        String label_string = this.label;
    
        return label_string;
    }

}
