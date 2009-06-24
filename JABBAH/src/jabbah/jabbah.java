package jabbah;

import java.awt.*;
import java.awt.geom.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.jgraph.*;
import org.jgraph.graph.*;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

// resolve ambiguity
import com.jgraph.layout.*;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Map;
import org.xml.sax.SAXException;

public class jabbah
        extends JApplet
{
    //~ Static fields/initializers ---------------------------------------------

    //private static final long serialVersionUID = 3256444702936019250L;
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(1200, 1200);

    //~ Instance fields --------------------------------------------------------

    //
    private JGraphModelAdapter jgAdapter;
    private JGraphModelAdapter jgAdapter2;
    private ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g_left;
    private ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g_right;

    //~ Methods ----------------------------------------------------------------
    /**
     * An alternative starting point for this demo, to also allow running this
     * applet as an application.
     *
     * @param args ignored.
     */
    public static void main(String[] args) 
    {

        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        jabbah applet = new jabbah();

        System.setProperty("sun.java2d.d3d", "false");

        menuBar = new JMenuBar();
        //Build the first menu.
        menu = new JMenu("Archive");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
        "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Import XPDL file ", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Open the specified file");
        menu.add(menuItem);

        menuItem = new JMenuItem("Exit ", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Exit the JABBAH application");
        menu.add(menuItem);

        JFrame frame = new JFrame(JGraphLayout.VERSION);

        frame.setJMenuBar(menuBar);
        
        frame.getContentPane().add(applet);
        frame.setTitle("JABBAH Framework");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(800, 600);
        applet.init();
        frame.setVisible(true);


    }

    // a method to build our example graph
    private void buildMyGraph(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g)
    {

        XpdlObjectMapping xom = new XpdlObjectMapping();
        try
        {
            xom.parse("/Users/arturogf/ecarules/JABBAH/input/GA.xpdl.xml");
        } catch (SAXException ex)
        {
            Logger.getLogger(jabbah.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(jabbah.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(jabbah.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex)
        {
            Logger.getLogger(jabbah.class.getName()).log(Level.SEVERE, null, ex);
        }

        MyWeightedVertex S = new MyWeightedVertex("S");
        MyWeightedVertex A1 = new MyWeightedVertex("A1");
        MyWeightedVertex A2 = new MyWeightedVertex("A2");
        MyWeightedVertex A3 = new MyWeightedVertex("A3");
        MyWeightedVertex A4 = new MyWeightedVertex("A4");
        MyWeightedVertex A5 = new MyWeightedVertex("A5");
        MyWeightedVertex A6 = new MyWeightedVertex("A6");
        MyWeightedVertex A7 = new MyWeightedVertex("A7");
        MyWeightedVertex A8 = new MyWeightedVertex("A8");
        MyWeightedVertex A9 = new MyWeightedVertex("A9");
        MyWeightedVertex A10 = new MyWeightedVertex("A10");
        MyWeightedVertex A11 = new MyWeightedVertex("A11");
        MyWeightedVertex A12 = new MyWeightedVertex("A12");
        MyWeightedVertex A13 = new MyWeightedVertex("A13");
        MyWeightedVertex E = new MyWeightedVertex("E");

        MyWeightedVertex G1 = new MyWeightedVertex("G1"); // AND A2-A3
        G1.type = NodeType.GATEWAY;
        G1.restriction = TransitionRestriction.SPLIT_PARALLEL;
        MyWeightedVertex FG1 = new MyWeightedVertex("FIN G1"); // FIN AND A2-A3
        FG1.type = NodeType.GATEWAY;
        FG1.restriction = TransitionRestriction.JOIN_INCLUSIVE;
        MyWeightedVertex G2 = new MyWeightedVertex("G2"); // OR A5-A6
        G2.type = NodeType.GATEWAY;
        G2.restriction = TransitionRestriction.SPLIT_EXCLUSIVE;
        // add the parameter optimize

        G2.param = new Parameter();

        G2.param.name = "optimize";
        G2.param.type = "boolean";

        MyWeightedVertex FG2 = new MyWeightedVertex("FIN G2"); // FIN OR A5-A6
        FG2.type = NodeType.GATEWAY;
        FG2.restriction = TransitionRestriction.JOIN_EXCLUSIVE;

        MyWeightedVertex G3 = new MyWeightedVertex("G3"); // AND A9-A10
        G3.type = NodeType.GATEWAY;
        G3.restriction = TransitionRestriction.SPLIT_PARALLEL;
        MyWeightedVertex FG3 = new MyWeightedVertex("FIN G3"); // FIN OR A9-A10
        FG3.type = NodeType.GATEWAY;
        FG3.restriction = TransitionRestriction.JOIN_INCLUSIVE;

        // add start, end and activities
        g.addVertex(S);
        g.addVertex(A1);
        g.addVertex(A2);
        g.addVertex(A3);
        g.addVertex(A4);
        g.addVertex(A5);
        g.addVertex(A6);
        g.addVertex(A7);
        g.addVertex(A8);
        g.addVertex(A9);
        g.addVertex(A10);
        g.addVertex(A11);
        g.addVertex(A12);
        g.addVertex(A13);
        g.addVertex(E);

        // add gateways vertices
        g.addVertex(G1);
        g.addVertex(FG1);
        g.addVertex(G2);
        g.addVertex(FG2);
        g.addVertex(G3);
        g.addVertex(FG3);

        // add edges 
        g.addEdge(S, A1, new MyWeightedEdge(S, A1, "E1"));
        g.addEdge(A1, G1, new MyWeightedEdge(A1, G1, "E2"));
        g.addEdge(G1, A2, new MyWeightedEdge(G1, A2, "E3"));
        g.addEdge(G1, A3, new MyWeightedEdge(G1, A3, "E4"));
        g.addEdge(A3, FG1, new MyWeightedEdge(A3, FG1, "E5"));
        g.addEdge(A2, A4, new MyWeightedEdge(A2, A4, "E6"));
        g.addEdge(A4, G2, new MyWeightedEdge(A4, G2, "E7"));
        g.addEdge(G2, A5, new MyWeightedEdge(G2, A5, "E8"));
        g.addEdge(G2, A6, new MyWeightedEdge(G2, A6, "E9"));
        g.addEdge(A5, FG2, new MyWeightedEdge(A5, FG2, "E10"));
        g.addEdge(A6, FG2, new MyWeightedEdge(A6, FG2, "E11"));
        g.addEdge(FG2, A7, new MyWeightedEdge(FG2, A7, "E12"));
        g.addEdge(A7, A8, new MyWeightedEdge(A7, A8, "E13"));
        g.addEdge(A8, G3, new MyWeightedEdge(A8, G3, "E14"));
        g.addEdge(G3, A9, new MyWeightedEdge(G3, A9, "E15"));
        g.addEdge(G3, A10, new MyWeightedEdge(G3, A10, "E16"));
        g.addEdge(A9, FG3, new MyWeightedEdge(A9, FG3, "E17"));
        g.addEdge(A10, FG3, new MyWeightedEdge(A10, FG3, "E18"));
        g.addEdge(FG3, A11, new MyWeightedEdge(FG3, A11, "E19"));
        g.addEdge(A11, FG1, new MyWeightedEdge(A11, FG1, "E20"));
        g.addEdge(FG1, A12, new MyWeightedEdge(FG1, A12, "E21"));
        g.addEdge(A12, A13, new MyWeightedEdge(A12, A13, "E22"));
        g.addEdge(A13, E, new MyWeightedEdge(A13, E, "E23"));
    }

    /**
     * {@inheritDoc}
     */
    public void init()
    {

        // create a JGraphT directed weighted graph, using a custom class MyWeightedEdge
        g_left = new ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge>(
                MyWeightedEdge.class);

        // build or proof of concept graph
        this.buildMyGraph(g_left);

        /* Create the left side jgraph and respective layout and JGraphModelAdapter */
        jgAdapter = new JGraphModelAdapter<MyWeightedVertex, MyWeightedEdge>(g_left);
        
        this.putColor(jgAdapter, g_left);


        // esto crea para un nodo determinado una serie de atributos
        //DefaultGraphCell cell = jgAdapter.getVertexCell(g_left.vertexSet().iterator().next());
        //AttributeMap attr = cell.getAttributes();
        //GraphConstants.setBackground(attr, Color.GRAY);

        JGraph jgraph = new JGraph(jgAdapter);
        JGraphFacade facade = new JGraphFacade(jgraph);
        JGraphTreeLayout layout = new JGraphTreeLayout();
        //layout.setOrientation(SwingConstants.WEST);
        layout.run(facade);
        Map nested = facade.createNestedMap(true, true);
        jgraph.getGraphLayoutCache().edit(nested);

        /* Create the left side jgraph and respective layout and JGraphModelAdapter,
        using a clone of g */

        // this must be changed in the future, just to show...
        g_right = new ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge>(
                MyWeightedEdge.class);
        //g_right = (ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge>) g_left.clone();

        this.buildMyGraph(g_right);

        // call the ECA Rules Block Detection Algorithm
        BlockDetection block = new BlockDetection(g_right);

        // set the layout for the result
        jgAdapter2 = new JGraphModelAdapter<MyWeightedVertex, MyWeightedEdge>(g_right);
        
        this.putColor(jgAdapter2, g_right);

        JGraph jgraph2 = new JGraph(jgAdapter2);
        JGraphFacade facade2 = new JGraphFacade(jgraph2);
        JGraphHierarchicalLayout layout2 = new JGraphHierarchicalLayout();
        layout2.setOrientation(SwingConstants.NORTH);
        layout2.run(facade2);
        Map nested2 = facade2.createNestedMap(true, true);
        jgraph2.getGraphLayoutCache().edit(nested2);

        // adjust the settings for the graph
        adjustDisplaySettings(jgraph);
        adjustDisplaySettings(jgraph2);

        // set a 2 columns layout
        getContentPane().setLayout(new GridLayout(1, 2));
     
        //getContentPane().add(new Button("Do a new step"));
        JScrollPane left = new JScrollPane(jgraph);
        JScrollPane right = new JScrollPane(jgraph2);

        getContentPane().add(left);
        getContentPane().add(right);

         // create a translator instance and call the corresponding PDDL translation
        Translator T = new Translator(g_right,
                                      "/Users/arturogf/ecarules/JABBAH/output/domain.pddl",
                                      "/Users/arturogf/ecarules/JABBAH/output/problem.pddl");
        T.PDDLTranslator();

    }

    
    private void putColor(JGraphModelAdapter<MyWeightedVertex, MyWeightedEdge> adapter,
                                   ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> graph)
    {
         DefaultGraphCell cell;
         AttributeMap attr;
    // esto crea para un nodo determinado una serie de atributos
        Iterator iter = graph.vertexSet().iterator();

        while (iter.hasNext())
        {
             MyWeightedVertex foo = ( MyWeightedVertex )  iter.next (  ) ;
            cell = adapter.getVertexCell(foo);
            attr = cell.getAttributes();                                  
            if (foo.type == NodeType.GATEWAY)
            {
                GraphConstants.setBackground(attr, Color.ORANGE);

                GraphConstants.setBorder(attr, BorderFactory.createLineBorder(Color.BLACK));
            }           
             if (foo.type == NodeType.PARALLEL)
            {
                GraphConstants.setBackground(attr, Color.RED);

                GraphConstants.setBorder(attr, BorderFactory.createLineBorder(Color.BLACK));
            }           
             if (foo.type == NodeType.SERIAL)
            {
                GraphConstants.setBackground(attr, Color.BLUE);

                GraphConstants.setBorder(attr, BorderFactory.createLineBorder(Color.BLACK));
              
            }           
             if (foo.type == NodeType.DEFAULT)
            {
                GraphConstants.setBackground(attr, Color.GRAY);
            }           
        }
    }
   

    
    private void adjustDisplaySettings(JGraph jg)
    {
        jg.setPreferredSize(DEFAULT_SIZE);

        Color c = DEFAULT_BG_COLOR;

        String colorStr = null;

        try
        {
            colorStr = getParameter("bgcolor");
        } catch (Exception e)
        {
        }

        if (colorStr != null)
        {
            c = Color.decode(colorStr);
        }

        jg.setBackground(c);
    }

    @SuppressWarnings("unchecked") // FIXME hb 28-nov-05: See FIXME below
    private void positionVertexAt(Object vertex, int x, int y)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
                new Rectangle2D.Double(
                x,
                y,
                bounds.getWidth(),
                bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);

        // TODO: Clean up generics once JGraph goes generic
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
    }

    @SuppressWarnings("unchecked")
    private void setGatewayAttr(Object vertex)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();

        Color c = GraphConstants.getForeground(attr);

        GraphConstants.setForeground(attr, Color.GRAY);

        // TODO: Clean up generics once JGraph goes generic
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
    }


    //~ Inner Classes ----------------------------------------------------------
    /**
     * a listenable directed multigraph that allows loops and parallel edges.
     */
    private static class ListenableDirectedMultigraph<V, E>
            extends DefaultListenableGraph<V, E>
            implements DirectedGraph<V, E>
    {

        private static final long serialVersionUID = 1L;

        ListenableDirectedMultigraph(Class<E> edgeClass)
        {
            super(new DirectedMultigraph<V, E>(edgeClass));
        }
    }
}

// End jabbah.java
