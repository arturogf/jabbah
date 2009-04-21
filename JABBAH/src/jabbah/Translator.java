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

    public void PDDLTranslator()
    {
        FileWriter dfile, pfile;

        try
        {
            dfile = new FileWriter(this.d_filepath, true);
            dfile.write(PDDLBlocks.requirements);
            dfile.write(PDDLBlocks.types);
            dfile.write(this.setConstants());
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
}
