/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Main.java
 *
 * Created on 25-ene-2010, 17:50:32
 */

package jabbah;

import java.awt.*;

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

import java.util.Iterator;
import java.util.Map;
import org.xml.sax.SAXException;

/**
 *
 * @author arturogf
 */
public class Main extends javax.swing.JFrame {

     //~ Static fields/initializers ---------------------------------------------

    //private static final long serialVersionUID = 3256444702936019250L;
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(1200, 1200);

    private static Logger logger = Logger.getLogger(Main.class.getName());

    //~ Instance fields --------------------------------------------------------
    //
    private JGraphModelAdapter jgAdapter;
    private JGraphModelAdapter jgAdapter2;
    private ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g_left;
    private ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g_right;

    XpdlObjectMapping xom;


    /** Creates new form Main */
    public Main() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem4 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem5 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem6 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem7 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem8 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem9 = new javax.swing.JCheckBoxMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 582, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );

        fileMenu.setText("File");

        jMenuItem1.setText("Open XPDL File");
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem1);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setText("Debug");
        jMenu1.setName("jMenu1"); // NOI18N

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Turn Debug OFF");
        jCheckBoxMenuItem1.setName("jCheckBoxMenuItem1"); // NOI18N
        jCheckBoxMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jCheckBoxMenuItem1MouseReleased(evt);
            }
        });
        jMenu1.add(jCheckBoxMenuItem1);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jMenu1.add(jSeparator1);

        jCheckBoxMenuItem3.setSelected(true);
        jCheckBoxMenuItem3.setText("Log SEVERE");
        jCheckBoxMenuItem3.setName("jCheckBoxMenuItem3"); // NOI18N
        jMenu1.add(jCheckBoxMenuItem3);

        jCheckBoxMenuItem4.setSelected(true);
        jCheckBoxMenuItem4.setText("Log WARNING");
        jCheckBoxMenuItem4.setName("jCheckBoxMenuItem4"); // NOI18N
        jMenu1.add(jCheckBoxMenuItem4);

        jCheckBoxMenuItem5.setSelected(true);
        jCheckBoxMenuItem5.setText("Log INFO");
        jCheckBoxMenuItem5.setName("jCheckBoxMenuItem5"); // NOI18N
        jMenu1.add(jCheckBoxMenuItem5);

        jCheckBoxMenuItem6.setText("Log CONFIG");
        jCheckBoxMenuItem6.setName("jCheckBoxMenuItem6"); // NOI18N
        jMenu1.add(jCheckBoxMenuItem6);

        jCheckBoxMenuItem7.setText("Log FINE");
        jCheckBoxMenuItem7.setName("jCheckBoxMenuItem7"); // NOI18N
        jMenu1.add(jCheckBoxMenuItem7);

        jCheckBoxMenuItem8.setText("Log FINER");
        jCheckBoxMenuItem8.setName("jCheckBoxMenuItem8"); // NOI18N
        jMenu1.add(jCheckBoxMenuItem8);

        jCheckBoxMenuItem9.setText("Log FINEST");
        jCheckBoxMenuItem9.setName("jCheckBoxMenuItem9"); // NOI18N
        jMenu1.add(jCheckBoxMenuItem9);

        menuBar.add(jMenu1);

        helpMenu.setText("Help");

        contentsMenuItem.setText("Contents");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFileChooser chooser = new JFileChooser();

        int returnVal = chooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            logger.fine("You chose to open this file: " +
                    chooser.getSelectedFile().getAbsolutePath());

            this.init(chooser.getSelectedFile().getAbsolutePath());
            //repaint
            this.validate();
            //this.frame.setVisible(true);
            }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jCheckBoxMenuItem1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1MouseReleased
        // TODO add your handling code here:
          if (this.jCheckBoxMenuItem1.isSelected()) {
            this.jCheckBoxMenuItem1.setText("Turn Debug ON");
            this.jCheckBoxMenuItem3.setEnabled(false);
            this.jCheckBoxMenuItem4.setEnabled(false);
            this.jCheckBoxMenuItem5.setEnabled(false);
            this.jCheckBoxMenuItem6.setEnabled(false);
            this.jCheckBoxMenuItem7.setEnabled(false);
            this.jCheckBoxMenuItem8.setEnabled(false);
            this.jCheckBoxMenuItem9.setEnabled(false);
         }
        else {
            this.jCheckBoxMenuItem1.setText("Turn Debug OFF");
            this.jCheckBoxMenuItem3.setEnabled(true);
            this.jCheckBoxMenuItem4.setEnabled(true);
            this.jCheckBoxMenuItem5.setEnabled(true);
            this.jCheckBoxMenuItem6.setEnabled(true);
            this.jCheckBoxMenuItem7.setEnabled(true);
            this.jCheckBoxMenuItem8.setEnabled(true);
            this.jCheckBoxMenuItem9.setEnabled(true);
        }
    }//GEN-LAST:event_jCheckBoxMenuItem1MouseReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem5;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem6;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem7;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem8;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables



 // a method to build our example graph g from the Absolute Path to XPDL file
    private void buildGraphFromXPDL(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g,
                                    String AbsolutePath)
    {
        xom = new XpdlObjectMapping();
        try
        {
            xom.parse(AbsolutePath);
        } catch (SAXException ex)
        {
            logger.log(Level.SEVERE, "Ocurrió una excepción al parsear el XPDL", ex);
        } catch (XPathExpressionException ex)
        {
            logger.log(Level.SEVERE, "Ocurrió un error con XPATH al parsear el XPDL", ex);
        } catch (IOException ex)
        {
            logger.log(Level.SEVERE, "Ocurrió una excepción de I/O al parsear el XPDL", ex);
        } catch (ParserConfigurationException ex)
        {
            logger.log(Level.SEVERE, "Ocurrió una excepción de configuración de " +
                    "parser al leer el XPDL", ex);
        }

        if (xom.Activities.length==0)
            logger.severe ("El número de actividades es CERO en el método buildGraphFromXPDL()");

        int ig = 0;
        
        for (int i=0; i<xom.Activities.length; i++)
        {
            if (xom.Activities[i].type == NodeType.GATEWAY)
            {
                if (xom.Activities[i].restriction == TransitionRestriction.JOIN_EXCLUSIVE ||
                    xom.Activities[i].restriction == TransitionRestriction.JOIN_INCLUSIVE)
                {
                    xom.Activities[i].node = new MyWeightedVertex("END_G"+ig);
                    xom.Activities[i].node.param = xom.Activities[i].param;
                }

                else
                {
                    xom.Activities[i].node = new MyWeightedVertex("G"+ig);
                    xom.Activities[i].node.param = xom.Activities[i].param;
                }

                ig = ig+1;
            }
            else {
                xom.Activities[i].node = new MyWeightedVertex("A"+i);
                xom.Activities[i].node.lane = xom.findLane(xom.Activities[i].lane_id);
            }

            xom.Activities[i].node.type = xom.Activities[i].type;
            xom.Activities[i].node.restriction = xom.Activities[i].restriction;
            g.addVertex(xom.Activities[i].node);
        }

        if (xom.Transitions.length==0)
            logger.severe ("El número de transiciones es CERO en "+
                    Main.class.getName() + "buildGraphFromXPDL()");

        for (int t=0; t< xom.Transitions.length; t++)
        {
            MyWeightedVertex from = xom.findActivityNode(xom.Transitions[t].from);
            MyWeightedVertex to = xom.findActivityNode(xom.Transitions[t].to);

            if ((from!=null) && (to!=null))
            {
                g.addEdge(from, to,
                    new MyWeightedEdge(from, to, "E"+t));
            }
            else
                logger.severe("se encontró una transición con origen o destino " +
                        "NULL en " + Main.class.getName() + " buildGraphFromXPDL");
        }
    }
    // a method to build our example graph
    private void buildMyGraph(ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge> g)
    {
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
    public void init(String AbsolutePath)
    {

        // create a JGraphT directed weighted graph, using a custom class MyWeightedEdge
        g_left = new ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge>(
                MyWeightedEdge.class);

        // build or proof of concept graph
        this.buildGraphFromXPDL(g_left,AbsolutePath);

        /* Create the left side jgraph and respective layout and JGraphModelAdapter */
        jgAdapter = new JGraphModelAdapter<MyWeightedVertex, MyWeightedEdge>(g_left);

        this.putColor(jgAdapter, g_left);

        JGraph jgraph = new JGraph(jgAdapter);
        JGraphFacade facade = new JGraphFacade(jgraph);
        JGraphTreeLayout layout = new JGraphTreeLayout();
        //layout.setOrientation(SwingConstants.EAST);

        layout.run(facade);
        Map nested = facade.createNestedMap(true, true);
        jgraph.getGraphLayoutCache().edit(nested);

        /* Create the right side jgraph and respective layout and JGraphModelAdapter,
        using a clone of g_left */
        g_right = new ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge>(
                MyWeightedEdge.class);
        g_right = (ListenableDirectedWeightedGraph<MyWeightedVertex, MyWeightedEdge>) g_left.clone();

        //this.buildMyGraph(g_right);

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

        // adjust the settings for the graphs
        adjustDisplaySettings(jgraph);
        adjustDisplaySettings(jgraph2);

        // panel vertical
        JPanel vpanel = new JPanel();
        vpanel.setLayout(new BoxLayout(vpanel,BoxLayout.Y_AXIS));

        // primer panel horizontal para los grafos
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.X_AXIS));

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        JScrollPane left = new JScrollPane(jgraph);
        JScrollPane right = new JScrollPane(jgraph2);

        panel1.add(left);
        panel1.add(right);

        // segundo panel horizontal para boton y progress bar
        /*JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.X_AXIS));

        JButton button = new JButton("Run me!");
        JProgressBar pbar = new JProgressBar();
        pbar.setPreferredSize(new Dimension(400,200));

        panel2.add(button);
        button.setPreferredSize(new Dimension(200,200));

        panel2.add(pbar);*/

        vpanel.add(panel1);
        //vpanel.add(panel2);

        getContentPane().add(vpanel);

         // create a translator instance and call the corresponding PDDL translation
        Translator T = new Translator(g_right,
                                        xom,
                                      "/home/arturogf/jabbah/JABBAH/output/domain.pddl",
                                      "/home/arturogf/jabbah/JABBAH/output/problem.pddl");
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

       // try {
       //     colorStr = getParameter("bgcolor");
       // } catch (Exception e) {
       // }

        if (colorStr != null) {
            c = Color.decode(colorStr);
        }

        jg.setBackground(c);
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



 