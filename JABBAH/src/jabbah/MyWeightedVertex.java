package jabbah;

import java.util.List;
import java.util.Locale;
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
    String label="";
    String lane ="LANE_NAME";
    Double weight = 0.0;
    Double duration = 1.0;

    // here, we are going to store the causal preconditions and effects
    // based on predicates (ordered x y), (completed x)
    PDDLExpression preconditions;
    PDDLExpression effects;


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
        if (this.weight!=null)
            return this.label.concat(" ").concat(this.weight.toString());
        else
            return this.label;
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
     * @return the vector, or null if there are not predecessors
     */
    public PDDLExpression setCausalPreconditions(
            ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> G) {
        PDDLExpression e1 = new PDDLExpression();
        PDDLExpression e2 = new PDDLExpression();
        PDDLExpression e = new PDDLExpression();

        Vector c_exp = new Vector(); // (completed x)
        Vector o_exp = new Vector(); // (ordered x y)


        for (MyWeightedVertex p : Graphs.predecessorListOf(G, this)) {
            // if inmediate predecessor is an activity
            if (p.type == NodeType.DEFAULT) {
                PDDLExpression t1 = new PDDLExpression();
                PDDLExpression t2 = new PDDLExpression();

                t1.setPredicate("completed", p.label.toLowerCase());
                t2.setPredicate("ordered", p.label.toLowerCase(), this.label.toLowerCase());

                c_exp.add(t1);
                o_exp.add(t2);
                e.AND(t1, t2);
            } 
           // if inmediate predecessor is a starting gateway
            //(we suppose that a starting gateway can only have one predecessor j)

            else if ((p.type == NodeType.GATEWAY && p.restriction == TransitionRestriction.SPLIT_EXCLUSIVE) ||
                    (p.type == NodeType.GATEWAY && p.restriction == TransitionRestriction.SPLIT_PARALLEL)) {

                for (MyWeightedVertex j : Graphs.predecessorListOf(G, p)) {
                    PDDLExpression t1 = new PDDLExpression();
                    PDDLExpression t2 = new PDDLExpression();

                    t1.setPredicate("completed", j.label.toLowerCase());
                    t2.setPredicate("ordered", j.label.toLowerCase(), this.label.toLowerCase());

                    e.AND(t1,t2);
                }


            } // if inmediate predecessor is a closing XOR Gateway
            else if (p.type == NodeType.GATEWAY &&
                    p.restriction == TransitionRestriction.JOIN_EXCLUSIVE)
            {
                for (MyWeightedVertex j : Graphs.predecessorListOf(G, p))
                {
                    PDDLExpression t1 = new PDDLExpression();
                    PDDLExpression t2 = new PDDLExpression();

                    t1.setPredicate("completed", j.label.toLowerCase());
                    t2.setPredicate("ordered", j.label.toLowerCase(), this.label.toLowerCase());

                    c_exp.add(t1);
                    o_exp.add(t2);
                }

                e1.OR(c_exp);
                e2.OR(o_exp);
                e.AND(e1, e2);

            } // if inmediate predecessor is a XOR Gateway
            else if (p.type == NodeType.GATEWAY &&
                    p.restriction == TransitionRestriction.JOIN_INCLUSIVE)
            {
                for (MyWeightedVertex j : Graphs.predecessorListOf(G, p))
                {
                    PDDLExpression t1 = new PDDLExpression();
                    PDDLExpression t2 = new PDDLExpression();

                    t1.setPredicate("completed", j.label.toLowerCase());
                    t2.setPredicate("ordered", j.label.toLowerCase(), this.label.toLowerCase());

                    c_exp.add(t1);
                    o_exp.add(t2);
                }

                e1.AND(c_exp);
                e2.AND(o_exp);
                e.AND(e1, e2);
            }
        }

        return e;
    }

        /**
     *
     * @param pred the vector to be filled up with predeccesor nodes
     * @param G the Graph to be examined
     * @return the vector, or null if there are not predecessors
     */
    public PDDLExpression setCausalEffects(
            ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> G) {
        PDDLExpression e1 = new PDDLExpression();
        PDDLExpression e2 = new PDDLExpression();
        PDDLExpression e = new PDDLExpression();

        Vector c_exp = new Vector(); // (completed x)
        Vector o_exp = new Vector(); // (ordered x y)


        for (MyWeightedVertex p : Graphs.successorListOf(G, this)) {
            
            if (p.type==NodeType.END)
            {
                PDDLExpression t1 = new PDDLExpression();
                t1.setPredicate("completed", this.label.toLowerCase());
                return t1;
            }
            // if inmediate successor is an activity
            else if (p.type == NodeType.DEFAULT) {
                PDDLExpression t1 = new PDDLExpression();
                PDDLExpression t2 = new PDDLExpression();

                t1.setPredicate("completed", this.label.toLowerCase());
                t2.setPredicate("ordered", this.label.toLowerCase(), p.label.toLowerCase());

                e.AND(t1, t2);
            }
           // if inmediate successor is a starting gateway
           //(we suppose that a starting gateway can only have one predecessor j)

            else if (p.type == NodeType.GATEWAY) {
                PDDLExpression t1 = new PDDLExpression();
                t1.setPredicate("completed", this.label.toLowerCase());

                o_exp.add(t1);

                for (MyWeightedVertex j : Graphs.successorListOf(G, p)) {
                    PDDLExpression t2 = new PDDLExpression();

                    t2.setPredicate("ordered", this.label.toLowerCase(), j.label.toLowerCase());
                    o_exp.add(t2);
                }

                e.AND(o_exp);
            }
        }

        return e;
    }

}
