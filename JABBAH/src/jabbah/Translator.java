/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jabbah;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

/**
 * Class responsible of translating the workflow patterns decomposition tree
 * (extracted from the execution of Block Detection algorithm) into the 
 * specified planning language domain and problem files (HTN-PDDL)
 *
 * @author arturogf
 */
public class Translator {

    ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> G;
    String d_filepath;
    String p_filepath;

    public Translator(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g,
                        String dfilepath, String pfilepath)
    {
        this.G = g;
        this.d_filepath = dfilepath;
        this.p_filepath = pfilepath;
    }

    String setDomainName(String dname)
    {
        return ("(define (domain " + dname + ")\n");
    }
    
    String setConstants()
    {
        String result = "(:constants\n " +
	"true false - boolean\n";
        
        for (MyWeightedVertex v : this.G.vertexSet())
        {
            if (v.type == NodeType.DEFAULT) 
            {
                result = result + v.label.toLowerCase() + " ";
            }
        
        }
        
        result = result + "- activity\n";
        
        // TODO: add LANES and maybe gateways
        
        result = result+")\n";
        
        return result;
    }
    
    String buildDurativeAction(String name, Double duration, String lane)
    {
        String result = "\n(:durative-action " + name.toUpperCase() + "\n" +
	":parameters(?w - participant)" + "\n" +
	":duration (= ?duration " + duration.toString() + ")\n" +
	":condition(belongs_to_lane ?w " + lane + ")\n" +
	":effect (completed " + name.toLowerCase() +"))\n";
        
        return result;
    }
            
            
    
    String setDurativeActions()
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
    
    String setSerialBlocks()
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
                        result = result + "(Block" + j.label +") ";
                   }
                }
                result = result + ")\n))\n\n";    
            }
           
        }  
        
        return result;
    }
    
    
    

    public void PDDLTranslator()
    {
        FileWriter dfile, pfile;

        try
        {
            dfile = new FileWriter(this.d_filepath, true);
            dfile.write(this.setDomainName("midominio"));
            dfile.write(PDDLBlocks.requirements);
            dfile.write(PDDLBlocks.types);
            dfile.write(this.setConstants());
            dfile.write(PDDLBlocks.predicates);
            dfile.write(this.setDurativeActions());
            dfile.write(this.setSerialBlocks());
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

class PDDLBlocks
{
    public static final String requirements = "\n(:requirements\n" +
  ":typing \n" +
  ":fluents \n" +
  ":htn-expansion \n" +
  ":durative-actions \n"+
  ":negative-preconditions\n" +
  ":universal-preconditions\n" +
  ":disjuntive-preconditions\n" +
  ":derived-predicates\n" +
  ":metatags)\n\n";

  public static final String types = "\n(:types\n" +
 	"boolean - object\n" +
	"activity - object\n" +
    "gateway - object\n" +
	"participant - object\n" +
	"role - object\n" +
	"lane - object)\n\n";


public static final String predicates = "\n(:predicates\n" +
	"(completed ?a - activity)\n"+
	"(belongs_to_lane ?p - participant ?a - lane)" +	
        ")\n\n";
}