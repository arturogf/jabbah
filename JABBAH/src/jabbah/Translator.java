
package jabbah;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

/**
 * 
 * Class responsible of translating the workflow patterns decomposition tree
 * (extracted from the execution of Block Detection algorithm) into the 
 * specified planning language domain and problem files (HTN-PDDL)
 *
 */
public class Translator {

    ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> G;
    String d_filepath;
    String p_filepath;
    
    /**
     * 
     * The corresponding constructor. 
     * @param g the workflow patterns decomposition graph to translate
     * @param dfilepath the domain file path
     * @param pfilepath the problem file path
     */
    public Translator(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g,
                        String dfilepath, String pfilepath)
    {
        this.G = g;
        this.d_filepath = dfilepath;
        this.p_filepath = pfilepath;
    }

    /**
     * 
     * Method to set the domain name
     * @param dname the domain name we want to appear at the domain and problem
     * files
     * @return a string with the header for the domain file
     */
    public String setDomainName(String dname)
    {
        return ("(define (domain " + dname + ")\n");
    }
    
    /**
     * 
     * Build up the constants section in HTN-PDDL, mainly activities, lanes and
     * gateways
     * @return a string containing the constants section
     */
    public String setConstants()
    {
        String result = "(:constants\n ";

        result = result + "true false - boolean\n";

        // add the ACTIVITIES as constants
        for (MyWeightedVertex v : this.G.vertexSet())
        {
            if (v.type == NodeType.DEFAULT) 
            {
                result = result + v.label.toLowerCase() + " ";
            }
        
        }
        
        result = result + "- activity\n";

        // TODO: add ALL PARAMETERS FOUND like 'optimize - parameter'
        result = result + "optimize - parameter\n";

        // TODO: add LANES and maybe gateways
        result = result+")\n";
        
        return result;
    }
    
    /**
     * 
     * @param name the name for the durative action, usually the activity node label
     * @param duration the duration for the activity, extracted from the XPDL source
     * (it should be expressed as a extendedAttribute tag)
     * @param lane the lane label to which the activity belongs
     * @return a string containing the durative action definition for the node
     * specified
     */
    public String buildDurativeAction(String name, Double duration, String lane)
    {
        String result = "\n(:durative-action " + name.toUpperCase() + "\n" +
	":parameters(?w - participant)" + "\n" +
	":duration (= ?duration " + duration.toString() + ")\n" +
	":condition(belongs_to_lane ?w " + lane + ")\n" +
	":effect (completed " + name.toLowerCase() +"))\n";
        
        return result;
    }
            
            
    /**
     * 
     * Explore all the nodes of the graph, and build up durative actions for
     * every activity that is considered of NodeType DEFAULT (usually actions)
     * 
     * @return a string with all the durative action definitions for the domain
     */
     
    public String setDurativeActions()
    {
        String result = "";
    
        for (MyWeightedVertex v : this.G.vertexSet())
        {
            if (v.type == NodeType.DEFAULT) 
            {
                result = result + this.buildDurativeAction(v.label,
                                        v.duration,v.lane);
            }
        
        }  
        
        
        return result;    
    }
    /**
     * 
     * Search for nodes of NodeType SERIAL to define the compound actions that
     * represents a serial block in HTN-PDDL.
     * 
     * @return the string corresponding to all serial blocks definitions
     */
    public String setSerialBlocks()
    {
        String result = "";
        
        for (MyWeightedVertex v : this.G.vertexSet())
        {
            if (v.type == NodeType.SERIAL) 
            {
                result = result + "(:task Block" + v.label+"\n" +
                         ":parameters ()\n" +
                         "(:method bl" + v.label.toLowerCase()+"\n" +
                         ":precondition ()\n" +
                         ":tasks (";
                
                int num_worker = 1;
                
                for (MyWeightedEdge e : this.G.outgoingEdgesOf(v))
                {
                   MyWeightedVertex j = (MyWeightedVertex) (e.getTarget());
                   if (j.type == NodeType.DEFAULT)
                   {
                        result = result + "(" + j.label + " ?w" + num_worker + ") ";
                        num_worker = num_worker + 1;
                   }
                   else
                   {
                        result = result + "(Block" + j.label;
                        //add the parameter
                        if (j.type == NodeType.PARALLEL &&
                                j.restriction == TransitionRestriction.SPLIT_EXCLUSIVE)
                            result = result + " " + "optimize";
                         result = result + ") ";

                   }
                }
                result = result + ")\n))\n\n";    
            }
           
        }  
        
        return result;
    }
    
    public MyWeightedVertex getRightNode(MyWeightedVertex node)
    {
    
        int next = 0;
        MyWeightedVertex right = null;
          
        // in this tree there is only one parent node for every node
        for (MyWeightedEdge e : this.G.incomingEdgesOf(node))
        {
            MyWeightedVertex parent = (MyWeightedVertex) e.getSource();
            
            for (MyWeightedEdge c: this.G.outgoingEdgesOf(parent))
            {
                right = (MyWeightedVertex) c.getTarget();
                
                // if we found it at last iteration, return next
                if (next == 1)
                    return right;
                
                // if the target node is the same as passed as parameter, then
                // the next one is the right brother in tree
                if (right == node)
                    next = 1;
            }
                
        }
        
        return null;
    
    }
    
    /**
     * 
     * @return a string containing all the compound actions for split/merge
     * blocks detected in the source XPDL, as HTN-PDDL code
     */
    public String setSimpleMerges()
    {
        String result = "";
        int simple_merge = 0;
        int num_worker = 1;

        
        for (MyWeightedVertex v : this.G.vertexSet())
        {
            
            num_worker = 1;
            
            if (v.type == NodeType.PARALLEL
                    && v.restriction == TransitionRestriction.SPLIT_PARALLEL)
            {
                result = result + "(:task Block" + v.label+"\n" +
                         ":parameters ()\n" +
                         "(:method bl" + v.label.toLowerCase()+"\n" +
                         ":precondition ()\n" +
                         ":tasks (";
                
                result = result + "[";
                                
                for (MyWeightedEdge e : this.G.outgoingEdgesOf(v))
                {
                   MyWeightedVertex j = (MyWeightedVertex) (e.getTarget());
                   if (j.type == NodeType.DEFAULT)
                   {
                        result = result + "(" + j.label + " ?w" + num_worker + ") ";
                        num_worker = num_worker + 1;
                   }
                   else
                   {
                        result = result + "(Block" + j.label;
                        // if the node have a parameter associated
                        if (j.param != null)
                            result = result + " " + j.param.name;

                        result = result + ") ";

                   }
                }
                
                result = result + "]";

                MyWeightedVertex right = this.getRightNode(v);
                
                if (right != null)
                {
                    result = result + "(" + right.label + " ?w" + num_worker + ")";
                }
                result = result + ")\n))\n\n";
                
            }
            else
                if (v.type == NodeType.PARALLEL &&
                    v.restriction == TransitionRestriction.SPLIT_EXCLUSIVE)
                {
                    result = result + "(:task Block" + v.label + "\n";
                    result = result + ":parameters (?x - parameter)\n";

                    // we do BY NOW only the ones with boolean parameters (IF/ELSE)

                    /*if (v.param.type.equals("boolean"))
                    {
                        result = result + "(:method A\n";
                        result = result + ":precondition " + "()";
                    }*/ 
                }
        }  
        
        return result;
    
    }
    
    /**
     * 
     * the main method that build up the HTN-PDDL domain and problem files
     *
     */
    public void PDDLTranslator()
    {
        FileWriter dfile, pfile;

        try
        {
            dfile = new FileWriter(this.d_filepath, false);
            dfile.write(this.setDomainName("midominio"));
            dfile.write(PDDLBlocks.requirements);
            dfile.write(PDDLBlocks.types);
            dfile.write(this.setConstants());
            dfile.write(PDDLBlocks.predicates);
            dfile.write(this.setDurativeActions());
            dfile.write(this.setSerialBlocks());
            dfile.write(this.setSimpleMerges());
            dfile.close();    
        } catch (IOException ex)
        {
            Logger.getLogger(Translator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            pfile = new FileWriter(this.p_filepath, true);
        } catch (IOException ex)
        {
            Logger.getLogger(Translator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

