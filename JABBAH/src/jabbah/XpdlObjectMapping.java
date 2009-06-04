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

    }

    private void parseLanes(org.w3c.dom.Document doc, XPath xpath)
    {
        XPathExpression exp_lane_name = null;
        try
        {
            exp_lane_name = xpath.compile("//xpdl2:Lane/@Name");
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }
        XPathExpression exp_lane_id = null;
        try
        {
            exp_lane_id = xpath.compile("//xpdl2:Lane/@Id");
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object res_lane_id = null;
        try
        {
            res_lane_id = exp_lane_id.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object res_lane_name = null;
        try
        {
            res_lane_name = exp_lane_name.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        NodeList nodes_id = (NodeList) res_lane_id;
        NodeList nodes_name = (NodeList) res_lane_name;

        Lanes = new Lane[nodes_id.getLength()];

        for (int i = 0; i < nodes_id.getLength(); i++) {
            Lanes[i] = new Lane();
            Lanes[i].id = nodes_id.item(i).getNodeValue();
            Lanes[i].name = nodes_name.item(i).getNodeValue();
        }




    }

}


