/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo1;

import java.util.Vector;

/**
 *
 * @author arturogf
 */
public class MyWeightedVertex
{

    String label;
    Double weight = 0.0;
    boolean marked;
    int type = 0; // 0 - normal, 1 - SerialBlock, 2 - ParallelBlock, 3 - Gateway
    Vector block = null;

    public MyWeightedVertex(String l, double w)
    {
        this.label = l;
        this.weight = w;
    }

    public MyWeightedVertex(String l)
    {
        this.label = l;
    }

    public void setWeight(double w)
    {
        this.weight = w;
    }

    public void setLabel(String l)
    {
        this.label = l;
    }

    @Override
    public String toString()
    {
        return this.label.concat(" ").concat(this.weight.toString());
    }

    public void setBlock(Vector v)
    {
        this.block = v;
    }
}
