/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jabbah;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 *  Object filled with the corresponding values from the source XPDL
 * file, so that we maintain an OO structure that is going to be used everywhere
 */
public class XpdlObjectMapping {

    Participant[] Participants;
    Parameter[] Parameters;
    Transition[] Transitions;
    Activity[] Activities;
    Lane[] Lanes;

    public void XpdlObjectMapping()
    {
    }

    public void parse(String source_file)
            throws SAXException, XPathExpressionException,
            IOException, ParserConfigurationException  {
        // TODO code application logic here

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(source_file);

        XPathFactory xfactory = XPathFactory.newInstance();

        XPath xpath = xfactory.newXPath();
        xpath.setNamespaceContext(new XpdlNamespaceContext());
        this.parseLanes(doc, xpath);
        this.parseActivities(doc, xpath);


    }

    private void parseLanes(org.w3c.dom.Document doc, XPath xpath)
    {
        XPathExpression exp_lane = null;
        try
        {
            exp_lane = xpath.compile("//xpdl2:Lane");
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object res_lane = null;
        try
        {
            res_lane = exp_lane.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        NodeList nodes = (NodeList) res_lane;

        Lanes = new Lane[nodes.getLength()];

        // in this loop, the "Lanes" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++) {
            Lanes[i] = new Lane();
           
            String exp_lane_name = "@Name";
            String exp_lane_id = "@Id";

            Node l_name, l_id;
            try
            {
                l_name = (Node) xpath.evaluate(exp_lane_name, nodes.item(i), XPathConstants.NODE);
                Lanes[i].name = l_name.getNodeValue();

                l_id = (Node) xpath.evaluate(exp_lane_id, nodes.item(i), XPathConstants.NODE);
                Lanes[i].id = l_id.getNodeValue();

            } catch (XPathExpressionException ex)
            {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

  private void parseActivities(org.w3c.dom.Document doc, XPath xpath)
    {
        XPathExpression exp_activity = null;
        try
        {
            exp_activity = xpath.compile("//xpdl2:Activity");
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object res_act = null;
        try
        {
            res_act = exp_activity.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        NodeList nodes = (NodeList) res_act;

        Activities = new Activity[nodes.getLength()];

        // in this loop, the "Activities" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++) {
            Activities[i] = new Activity();

            String exp_act_name = "@Name";
            String exp_act_id = "@Id";

            Node a_name, a_id;
            try
            {
                a_name = (Node) xpath.evaluate(exp_act_name, nodes.item(i), XPathConstants.NODE);
                Activities[i].name = a_name.getNodeValue();

                a_id = (Node) xpath.evaluate(exp_act_id, nodes.item(i), XPathConstants.NODE);
                Activities[i].id = a_id.getNodeValue();

            } catch (XPathExpressionException ex)
            {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}


