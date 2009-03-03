/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2007, by Barak Naveh and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/* ----------------------
 * demo1.java
 * ----------------------
 * (C) Copyright 2003-2007, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   -
 *
 * $Id: demo1.java 568 2007-09-30 00:12:18Z perfecthash $
 *
 * Changes
 * -------
 * 03-Aug-2003 : Initial revision (BN);
 * 07-Nov-2003 : Adaptation to JGraph 3.0 (BN);
 *
 */
package demo1;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

import org.jgraph.*;
import org.jgraph.graph.*;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

// resolve ambiguity
import org.jgrapht.graph.DefaultEdge;


/**
 * A demo applet that shows how to use JGraph to visualize JGraphT graphs.
 *
 * @author Barak Naveh
 * @since Aug 3, 2003
 */
public class demo1
    extends JApplet
{
    //~ Static fields/initializers ---------------------------------------------

    private static final long serialVersionUID = 3256444702936019250L;
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    //~ Instance fields --------------------------------------------------------

    //
    private JGraphModelAdapter jgAdapter;

    //~ Methods ----------------------------------------------------------------

    /**
     * An alternative starting point for this demo, to also allow running this
     * applet as an application.
     *
     * @param args ignored.
     */
    public static void main(String [] args)
    {
        demo1 applet = new demo1();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    public void init()
    {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g =
            new ListenableDirectedMultigraph<String, DefaultEdge>(
                DefaultEdge.class);

        // create a visualization using JGraph, via an adapter
        jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(g);

        JGraph jgraph = new JGraph(jgAdapter);

        adjustDisplaySettings(jgraph);
        getContentPane().add(jgraph);
        resize(DEFAULT_SIZE);

    

        // add some sample data (graph manipulated via JGraphT)
        /*g<String, DefaultEdge> g =
            new Defaultg<String, DefaultEdge>
            (DefaultEdge.class);*/
        g.addVertex("S");
        g.addVertex("A1");
        g.addVertex("AND_A2_A3");
        g.addVertex("A2");
        g.addVertex("A3");
        g.addVertex("A4");
        g.addVertex("OR_A5_A6");
        g.addVertex("A5");
        g.addVertex("A6");
        g.addVertex("MOR_A5_A6");
        g.addVertex("A7");
        g.addVertex("A8");
        g.addVertex("AND_A9_A10");
        g.addVertex("A9");
        g.addVertex("A10");
        g.addVertex("MAND_A9_A10");
        g.addVertex("A11");
        g.addVertex("MAND_A2_A3");
        g.addVertex("A12");
        g.addVertex("A13");
        g.addVertex("E");

        g.addEdge("S", "A1");
        g.addEdge("A1", "AND_A2_A3");
        g.addEdge("AND_A2_A3","A2");
        g.addEdge("AND_A2_A3","A3");
        g.addEdge("A3", "MAND_A2_A3");
        g.addEdge("A2", "A4");
        g.addEdge("A4", "OR_A5_A6");
        g.addEdge("OR_A5_A6", "A5");
        g.addEdge("OR_A5_A6", "A6");
        g.addEdge("A5", "MOR_A5_A6");
        g.addEdge("A6", "MOR_A5_A6");
        g.addEdge("MOR_A5_A6","A7");
        g.addEdge("A7", "A8");
        g.addEdge("A8", "AND_A9_A10");
        g.addEdge("AND_A9_A10", "A9");
        g.addEdge("AND_A9_A10", "A10");
        g.addEdge("A9","MAND_A9_A10");
        g.addEdge("A10","MAND_A9_A10");
        g.addEdge("MAND_A9_A10", "A11");
        g.addEdge("A11", "MAND_A2_A3");
        g.addEdge("A13", "MAND_A2_A3");
        g.addEdge("MAND_A2_A3", "A12");
        g.addEdge("A12", "A13");
        g.addEdge("A13", "E");
       

        // position vertices nicely within JGraph component
        //positionVertexAt(v1, 130, 40);
        //positionVertexAt(v2, 60, 200);
        //positionVertexAt(v3, 310, 230);
        //positionVertexAt(v4, 380, 70);

        // that's all there is to it!...
    }

    private void adjustDisplaySettings(JGraph jg)
    {
        jg.setPreferredSize(DEFAULT_SIZE);

        Color c = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter("bgcolor");
        } catch (Exception e) {
        }

        if (colorStr != null) {
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

// End demo1.java
