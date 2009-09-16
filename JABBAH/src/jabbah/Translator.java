
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
    XpdlObjectMapping xom;
    String d_filepath;
    String p_filepath;
    int uid = 1;
    
    /**
     * 
     * The corresponding constructor. 
     * @param g the workflow patterns decomposition graph to translate
     * @param dfilepath the domain file path
     * @param pfilepath the problem file path
     */
    public Translator(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g,
                        XpdlObjectMapping mapping,
                        String dfilepath, String pfilepath)
    {
        this.xom = mapping;
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
     * Method to set the domain name
     * @param dname the problem name we want to appear at the problem
     * file
     * @return a string with the header for the problem file
     */
    public String setProblemName(String pname)
    {
        return ("(define (problem " + pname + ")\n");
    }

       /**
     *
     * Method to set the domain name in the problem file
     * @param dname the domain name we want to appear at the problem
     * file
     * @return a string with the header for the problem file
     */
    public String setPDomain(String dname)
    {
        return ("(:domain " + dname + ")\n");
    }


    /**
     *
     * Build up the types section in HTN-PDDL
     * @return a string containing the types section
     */
    public String setTypes()
    {
        String result = PDDLBlocks.types;
        for (int i=0; i<xom.Parameters.length; i++)
            result = result + xom.Parameters[i].type.toLowerCase() + " - object\n";

        result = result+")\n\n";
        return result;
    }

     /**
     *
     * Build up the predicates section in HTN-PDDL
     * @return a string containing the predicates section
     */
    public String setPredicates()
    {
        String result = PDDLBlocks.predicates;
        for (int i=0; i<xom.Parameters.length; i++)
            if (xom.Parameters[i].type.equalsIgnoreCase("boolean"))
                {
                    result = result + "(value ?x - parameter ?v - boolean)\n";
                    break;
                }

        result = result + ")\n\n";
        return result;
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

        //add ALL PARAMETERS FOUND like 'optimize - parameter'
         for (int i=0; i<xom.Parameters.length; i++)
            result = result + xom.Parameters[i].name + " - parameter\n";

        //add ALL LANES FOUND like 'Training - lane'
        for (int i=0; i<this.xom.Lanes.length; i++)
        {
            result = result + this.xom.Lanes[i].name + " ";
        }
        result = result + " - lane\n";


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
        ":meta (\n" +
        "(:tag prettyprint  \"?start; ?end; ?duration; " + this.xom.findActivityName(name) + "\")\n" +
	"(:tag short \"ACTIVIDAD " + this.xom.findActivityName(name) + "\")\n" +
	//	(:tag resource "?f")
	//	(:tag resource "?dosis")
		"(:tag resource \"?w\")\n" +
		"(:tag monitoring \"manual\")\n" +
	//	"(:tag UID \""+ name.substring(1,name.length()) + "\")\n" +
                "(:tag UID \""+ this.uid + "\")\n" +
		"(:tag Type \"0\")\n" +
		"(:tag OutlineLevel \"1\")\n" +
		"(:tag OutlineNumber \"1\")\n" +
		"(:tag WBS \"1\")\n" +
		"(:tag Summary \"0\")\n)\n" +
        //		(:tag scope "?objetivo")
	
	":duration (= ?duration " + duration.toString() + ")\n" +
	":condition(belongs_to_lane ?w " + lane + ")\n" +
	":effect (completed " + name.toLowerCase() +"))\n";

        this.uid = this.uid + 1;
        
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
    
        /*for (MyWeightedVertex v : this.G.vertexSet())
        {
            if (v.type == NodeType.DEFAULT) 
            {
                result = result + this.buildDurativeAction(v.label,
                                        v.duration,v.lane);
            }
        
        }  */

        for (int i=0; i<xom.Activities.length; i++)
        {
            if (xom.Activities[i].type == NodeType.DEFAULT)
            {
                if (this.xom.Activities[i].duration != null)
                    result = result + this.buildDurativeAction(this.xom.Activities[i].node.label,
                            Double.parseDouble(this.xom.Activities[i].duration),
                            xom.findLane(xom.Activities[i].lane_id));
                else
                    // if there is no duration at this stage, we consider it has a duration of 1.0 units of time
                    result = result + this.buildDurativeAction(this.xom.Activities[i].node.label,
                            1.0,
                            xom.findLane(xom.Activities[i].lane_id));


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
                int dont_do_next = 0;
                
                for (MyWeightedEdge e : this.G.outgoingEdgesOf(v))
                {
                   MyWeightedVertex j = (MyWeightedVertex) (e.getTarget());
                   if (j.type == NodeType.DEFAULT)
                   {
                       // the next is a right brother of a parallel block
                       if (dont_do_next==1) {
                           dont_do_next=0;
                           }
                       else {
                            result = result + "(" + j.label + " ?w" + num_worker + ") ";
                            num_worker = num_worker + 1;
                        }
                   }
                   else
                   {
                        result = result + "(Block" + j.label;
                        //add the parameter in case that it is a XOR PB Block
                        if (j.type == NodeType.PARALLEL &&
                                j.restriction == TransitionRestriction.SPLIT_EXCLUSIVE)
                            result = result + " " + j.param.name;
                        else
                            dont_do_next = 1;
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
                   

                    if (v.param != null)
                    {
                         result = result + "(:task Block" + v.label + "\n";
                         result = result + ":parameters (?x - parameter)\n";
                         
                        // we do BY NOW only the ones with boolean parameters (IF/ELSE)
                        String methodname, predicate;
                        for (int i=0; i<v.param.affectedTransitions.length;i++)
                        {
                            if (v.param.affectedTransitions[i].parameterValue.equalsIgnoreCase(""))
                            {

                                MyWeightedVertex act_node = this.xom.findActivityNode(v.param.affectedTransitions[i].to);
                                methodname = "if_" + act_node.label;
                                predicate = "(value ?x false)";
                                result = result + "(:method "+ methodname+"\n";
                                result = result + ":precondition " + predicate +"\n";
                                result = result + ":tasks (" + act_node.label + "?w" + num_worker + "))\n";
                            }
                            else
                            {
                                MyWeightedVertex act_node = this.xom.findActivityNode(v.param.affectedTransitions[i].to);
                                methodname = "if_" + act_node.label;
                                predicate = "(value ?x " + v.param.affectedTransitions[i].parameterValue+")";
                                result = result + "(:method "+ methodname+"\n";
                                result = result + ":precondition " + predicate +"\n";
                                result = result + ":tasks (" + act_node.label + "?w" + num_worker + "))\n";
                            }
                        }                        
                    }
                    result = result + ")\n\n";

                }
        }  
        
        return result;
    
    }

    public String setObjects()
    {

        String result = "(:objects\n";

        for (int i=0; i<this.xom.Participants.length; i++)
             result = result + " " + this.xom.Participants[i].name;

        result = result+ " - participant\n)\n";
    
        return result;
    }

    public String setInitConditions()
    {

        String result = "(:init\n";

        // INITIAL VALUES FOR THE PARAMETERS MUST BE SET BY USER OR EXTERNAL SERVICE
        for (int i=0; i<this.xom.Parameters.length;i++)
        {
            if (this.xom.Parameters[i].type.equalsIgnoreCase("boolean"))
                    result = result + "(value " + this.xom.Parameters[i].name + " SET_BY_USER_OR_SERVICE)\n";
        }

        // PARTICIPANTS BELONGS TO SPECIFIC LANES
        for (int i=0; i<this.xom.Participants.length; i++)
             result = result + "(belongs_to_lane " 
                     + this.xom.Participants[i].name
                     + " "
                     + this.xom.Participants[i].lane
                     + ")\n";

        result = result + ")\n";
        return result;
    
    }

    public String setCustomize()
    {
        String result = "(:customization\n" +
	" (= :time-format \"%d/%m/%Y %H:%M\")\n" +
	"(= :time-horizon-relative 10000)\n" +
	"(= :time-start \"21/09/2009 8:00\")\n"+
	"(= :time-unit :hours)\n)\n\n";

        return result;

    }

    public String setTaskGoal()
    {
        MyWeightedVertex rootnode = this.G.vertexSet().iterator().next();

        String result = "(:tasks-goal\n:tasks(\n";

        result = result + "(Block" + rootnode.label + ")\n";
        result = result + "))\n";
        
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
            dfile.write(this.setTypes());
            dfile.write(this.setConstants());
            dfile.write(this.setPredicates());
            dfile.write(this.setDurativeActions());
            dfile.write(this.setSerialBlocks());
            dfile.write(this.setSimpleMerges());
            dfile.write("\n)");
            dfile.close();

            pfile = new FileWriter(this.p_filepath, false);
            pfile.write(this.setProblemName("midominio"));
            pfile.write(this.setPDomain("midominio"));
            pfile.write(this.setCustomize());
            pfile.write(this.setObjects());
            pfile.write(this.setInitConditions());
            pfile.write(this.setTaskGoal());
            pfile.write("\n)");
            pfile.close();

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

