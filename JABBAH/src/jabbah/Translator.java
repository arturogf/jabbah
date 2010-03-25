
package jabbah;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
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
    public Translator (ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g,
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

        for (int i=0; i<xom.Parameters.length; i++) {
            result = result + xom.Parameters[i].type.toLowerCase() + " - object\n";
        }

        result = result + ")\n\n";

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
                result = result + formatName(v.label,false) + " ";
            }
        }
        
        result = result + "- activity\n";

        //add ALL PARAMETERS FOUND like 'optimize - parameter'
         for (int i=0; i<xom.Parameters.length; i++)
            result = result + xom.Parameters[i].name + " - parameter\n";

        //add ALL LANES FOUND like 'Training - lane'
        if (xom.Lanes.length > 0) {
            for (int i=0; i<this.xom.Lanes.length; i++)
            {
                result = result + this.xom.Lanes[i].name + " ";
            }
        }
        else {
            // use a DEFAULT lane, as there are not different lanes
            result = result + "DEFAULT";
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
    public String buildDurativeAction(MyWeightedVertex v)
    {
        //PDDLExpression e = new PDDLExpression();

        String result = "\n(:durative-action " + formatName(v.label,true) + "\n" +
	":parameters(?w - participant)" + "\n" +
        setMetaTags(v.label) +
	setActionDuration(v.duration);

        if (v.lane.equalsIgnoreCase(""))
        {
            result = result + ":condition(belongs_to_lane ?w DEFAULT)\n";
        }
        else
            result = result + ":condition(belongs_to_lane ?w " + v.lane + ")\n";

	result = result + ":effect (completed " + formatName(v.label,false) +"))\n";
    
        return result;
    }

    /**
     * @param name
     * @param uppercase
     *
     * @return the name formatted to be correctly interpreted by the planner
     */
    public String formatName(String name, boolean uppercase)
    {
        String result = name;

        result = result.replace(" ", "");
        result = result.replace("&", "");
        result = result.replace(" ","");
        result = result.replace(".","");
        result = result.replace("?","");
        result = result.replace("¿","");
        result = result.replace("/","");
        result = result.replace("'","");
        result = result.replace(",","");
        result = result.replace(";","");
        result = result.replace("(","");
        result = result.replace(")","");

        if (uppercase)
            result = result.toUpperCase();
        else
            result = result.toLowerCase();

        return result;
    }
     /**
     *
     * @param duration the duration for the durative action
     * @return a string containing the duration expression
     */
    public String setActionDuration(Double duration)
    {
    String result;
    if (duration!=null)
        result = ":duration (= ?duration " + duration.toString() + ")\n";
    else
        result = ":duration (= ?duration 1.0)\n";


    return result;
    }

     /**
     *
     * @param name the name for the durative action, usually the activity node label
     * @return a string containing the metatags needed for the durative action
     */
    public String setMetaTags(String name)
    {
    String result =
                ":meta (\n" +
                "(:tag prettyprint  \"START: ?start; | END: ?end; | DURATION: ?duration; |   '" +
                name + "' ALLOCATED TO '?w' \")\n" +
                "(:tag short \"ACTIVITY " + name + "\")\n" +
		"(:tag resource \"?w\")\n" +
		"(:tag monitoring \"manual\")\n" +
                "(:tag UID \""+ this.uid + "\")\n" +
		"(:tag Type \"0\")\n" +
		"(:tag OutlineLevel \"1\")\n" +
		"(:tag OutlineNumber \"1\")\n" +
		"(:tag WBS \"1\")\n" +
		"(:tag Summary \"0\")\n)\n";

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
    
        for (MyWeightedVertex v : this.G.vertexSet())
        {
            if (v.type == NodeType.DEFAULT) 
            {
                    result = result + this.buildDurativeAction(v);
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
        String result = "\n";
        
        for (MyWeightedVertex v : this.G.vertexSet())
        {
            if (v.type == NodeType.SERIAL) 
            {
                result = result + "(:task Block" + formatName(v.label,true)+"\n" +
                         ":parameters ()\n" +
                         "(:method bl" + formatName(v.label,false)+"\n" +
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
                   else if (j.type!=NodeType.START && j.type!=NodeType.END)
                   {
                        result = result + "(Block" + formatName(j.label,true);
                        //add the parameter in case that it is a XOR PB Block
                        if (j.type == NodeType.PARALLEL &&
                                j.restriction == TransitionRestriction.SPLIT_EXCLUSIVE)
                            result = result + " " + j.param.name;
                        // if the node is a parallel block, we don't include the next
                        // which is a right brother node
                        else if (j.type == NodeType.PARALLEL &&
                                j.restriction == TransitionRestriction.SPLIT_PARALLEL)
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
          
        // in this kind of trees there is only one parent node for every node
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
     * @param v the root_node for the PB parallel block (AND) node in the tree
     * @return a simple/merge block compound task, with tasks as ([T1..Tn] Tj)
     */
    public String buildSplitParallel(MyWeightedVertex v)
    {
        String result = "";

        int num_worker = 1;

        result = result + "(:task Block" + formatName(v.label, true) + "\n" +
                ":parameters ()\n" +
                "(:method bl" + formatName(v.label, false) + "\n" +
                ":precondition ()\n" +
                ":tasks (";

        result = result + "[";

        for (MyWeightedEdge e : this.G.outgoingEdgesOf(v)) {
            MyWeightedVertex j = (MyWeightedVertex) (e.getTarget());
            if (j.type == NodeType.DEFAULT) {
                result = result + "(" + formatName(j.label,true) + " ?w" + num_worker + ") ";
                num_worker = num_worker + 1;
            } else {
                result = result + "(Block" + formatName(j.label, true);
                // if the node have a parameter associated
                if (j.param != null) {
                    result = result + " " + j.param.name;
                }

                result = result + ") ";

            }
        }

        result = result + "]";

        MyWeightedVertex right = this.getRightNode(v);

        if (right != null && right.type != NodeType.START && right.type!=NodeType.END) {
            result = result + "(" + formatName(right.label,true) + " ?w" + num_worker + ")";
        }
        result = result + ")\n))\n\n";

        return result;
    }

    /**
     *
     * @param v the root_node for the PB parallel block (XOR) node in the tree
     * @return a Block with corresponding tasks and method representing the XOR decision
     */
    public String buildSplitExclusive(MyWeightedVertex v)
    {
        String result="";
        PDDLExpression e = new PDDLExpression();

        String methodname = null;

        int num_worker = 1;


        if (v.param != null) {
            result = result + "(:task Block" + formatName(v.label, true) + "\n";
            result = result + ":parameters (?x - parameter)\n";

            // we do BY NOW only the ones with boolean parameters (IF/ELSE)

            for (int i = 0; i < v.param.affectedTransitions.length; i++)
            {
                if (v.param.affectedTransitions[i].parameterValue.equalsIgnoreCase(""))
                {
                    // TODO: aqui posiblemente va a fallar el xom.Activities con los subprocesos
                    MyWeightedVertex act_node =
                            this.xom.findActivityNode(this.xom.Activities, v.param.affectedTransitions[i].to);
                    methodname = "if_" + formatName(act_node.label, false);
                    result = result + "(:method " + methodname + "\n";
                    result = result + ":precondition " +  e.predicate("value", "?x","false") + "\n";
                    result = result + ":tasks (" + formatName(act_node.label,true) + "?w" + num_worker + "))\n";
                }
                else {
                    MyWeightedVertex act_node =
                            this.xom.findActivityNode(this.xom.Activities, v.param.affectedTransitions[i].to);
                    methodname = "if_" + formatName(act_node.label, false);
                    result = result + "(:method " + methodname + "\n";
                    result = result + ":precondition "
                            + e.predicate("value","?x",v.param.affectedTransitions[i].parameterValue) + "\n";
                    result = result + ":tasks (" + formatName(act_node.label,true) + " ?w" + num_worker + "))\n";
                }
            }
        }

        result = result + ")\n\n";

        return result;
    }
    
    /**
     * 
     * @return a the corresponding HPDL code containing all the compound tasks
     * for Parallel Block nodes detected in the Nested Process Model tree
     */
    public String setSimpleMerges()
    {
        String result = "";
        
        for (MyWeightedVertex v : this.G.vertexSet())
        {            
            if (v.type == NodeType.PARALLEL
                    && v.restriction == TransitionRestriction.SPLIT_PARALLEL)
            {
                result = result + buildSplitParallel(v);
            }
            else
                if (v.type == NodeType.PARALLEL &&
                    v.restriction == TransitionRestriction.SPLIT_EXCLUSIVE)
                {
                    result = result + buildSplitExclusive(v);
                }
        }  
        
        return result;
    
    }

    public String setObjects()
    {
        String result = "(:objects\n";

        if (this.xom.Participants.length==0)
        {
                    Logger.getLogger(Translator.class.getName()).log(Level.WARNING,
                            "There are no any participants defined in the process model!");
                    result = result + "DEFAULT";

        }

        for (int i=0; i<this.xom.Participants.length; i++)
             result = result + " " + this.xom.Participants[i].name;

        result = result + " - participant\n)\n";
    
        return result;
    }

    public String setInitConditions()
    {

        String result = "(:init\n";

        // INITIAL VALUES FOR THE PARAMETERS MUST BE SET BY USER OR EXTERNAL SERVICE
        for (int i=0; i<this.xom.Parameters.length;i++)
        {
            if (this.xom.Parameters[i].type.equalsIgnoreCase("boolean"))
                    result = result + "(value " + this.xom.Parameters[i].name + " true)\n";
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

    /**
     *
     * @return a vector containing all the rootnodes for the different HTN's
     * that has been built in the BlockDetection, for all the connected subgraphs
     */
    public Vector getRootNodes()
    {
        Vector result = new Vector();
        MyWeightedVertex v;
        
        Iterator i = this.G.vertexSet().iterator();

        while(i.hasNext())
        {
           v = (MyWeightedVertex) i.next();
           //if it is a root node
           if (this.G.inDegreeOf(v)==0)
               result.add(v);

        }
        return result;

    }
    /**
     *
     * @return the task goal for the problem definition, taking into account
     * different connected subgraphs that may have the process model
     */
    public String setTaskGoal()
    {
        Vector rootnodes = getRootNodes();
        Iterator i = rootnodes.iterator();
        MyWeightedVertex rootnode;

        String result = "(:tasks-goal\n:tasks(\n";

        // If only a rootnode, the task-goal is unique
        if (rootnodes.size() == 1) {

            rootnode = (MyWeightedVertex) i.next();
            result = result + "(Block" + formatName(rootnode.label, true) + ")\n";
            result = result + "))\n";
        // if more than a rootnode, it means that is a coordination process,
        // and that HTN's must be executed in parallel
        }
        else {
            result = result + "[";

            while (i.hasNext()) {
                rootnode = (MyWeightedVertex) i.next();
                result = result + "(Block" + formatName(rootnode.label, true) + ")";
            }

            result = result + "]))\n";

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

