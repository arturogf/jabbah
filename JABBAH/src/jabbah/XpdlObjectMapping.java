/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbah;

import java.io.IOException;
import java.util.HashMap;
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

import org.w3c.dom.NamedNodeMap;
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
    HashMap ASets;
    Lane[] Lanes;

    public void XpdlObjectMapping() {
    }

    public String findLane(Lane[] lanes, String id) {
        for (int i = 0; i < lanes.length; i++) {
            if (lanes[i].id.equals(id)) {
                return lanes[i].name;
            }
        }
        return "";
    }

    public Transition findTransition(Transition[] transitions,String id) {
        for (int i = 0; i < transitions.length; i++) {
            if (transitions[i].id.equals(id)) {
                return transitions[i];
            }
        }

        return null;

    }

    public String findActivityName(Activity[] activities, String label) {
        for (int i = 0; i < activities.length; i++) {

            if (activities[i].node.label.equalsIgnoreCase(label)) {
                return activities[i].name;
            }
        }

        return null;
    }

    public MyWeightedVertex findActivityNode(Activity[] activities,String id) {
        for (int i = 0; i < activities.length; i++) {
            if (activities[i].id.equalsIgnoreCase(id)) {
                return activities[i].node;
            }
        }

        return null;
    }

    public Activity findActivity(Activity[] activities, String id) {
        for (int i = 0; i < activities.length; i++) {
            if (activities[i].id.equalsIgnoreCase(id)) {
                return activities[i];
            }
        }

        return null;
    }

    public void parse(String source_file)
            throws SAXException, XPathExpressionException,
            IOException, ParserConfigurationException {
        // TODO code application logic here

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(source_file);

        XPathFactory xfactory = XPathFactory.newInstance();

        XPath xpath = xfactory.newXPath();
        xpath.setNamespaceContext(new XpdlNamespaceContext());

        this.parseParameters(doc, xpath);
        this.parseLanes(doc, xpath);
        this.parseParticipants(doc, xpath);

        this.Transitions = this.parseTransitions(doc, null, xpath, this.Transitions);
        this.Activities = this.parseActivities(doc, null, xpath, this.Activities, this.Transitions);
        this.parseActivitySet(doc, xpath);
    }

    private void parseParameters(org.w3c.dom.Document doc, XPath xpath) {
        XPathExpression exp_param = null;
        try {
            exp_param = xpath.compile("//xpdl2:FormalParameter");
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                    "Error compiling xpath expression for FormalParater", ex);
        }

        Object res_param = null;
        try {
            res_param = exp_param.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                    "Error evaluating xpath expression for FormalParater", ex);
        }

        NodeList nodes = (NodeList) res_param;

        Parameters = new Parameter[nodes.getLength()];

        // in this loop, the "Lanes" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++) {
            Parameters[i] = new Parameter();

            Node p_name, p_id, p_type;
            try {
                p_id = (Node) xpath.evaluate("@Id", nodes.item(i), XPathConstants.NODE);
                if (p_id != null) {
                    Parameters[i].id = p_id.getNodeValue();
                }
                else
                    Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                        "The Parameter @Id is empty or null");

                p_name = (Node) xpath.evaluate("@Name", nodes.item(i), XPathConstants.NODE);
                if (p_name != null) {
                    Parameters[i].name = p_name.getNodeValue();
                }
                else
                    Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                        "The Parameter @Name is empty or null");

                p_type = (Node) xpath.evaluate("xpdl2:DataType/xpdl2:BasicType", nodes.item(i),
                        XPathConstants.NODE);
                if (p_type != null) {
                    Parameters[i].type = p_type.getAttributes().item(0).getNodeValue();
                }
                else
                    Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                        "The Parameter type is empty or null");


            } catch (XPathExpressionException ex) {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                        "Error evaluating id,names or types for FormalParater", ex);
            }

        }

    }

    private void parseLanes(org.w3c.dom.Document doc, XPath xpath) {
        XPathExpression exp_lane = null;
        try {
            exp_lane = xpath.compile("//xpdl2:Lane");
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                    "Error compiling xpath expression for Lane", ex);
        }

        Object res_lane = null;
        try {
            res_lane = exp_lane.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                    "Error evaluating xpath expression for Lane", ex);
        }

        NodeList nodes = (NodeList) res_lane;

        Lanes = new Lane[nodes.getLength()];

        // in this loop, the "Lanes" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++) {
            Lanes[i] = new Lane();

            Node l_name, l_id;
            try {
                l_name = (Node) xpath.evaluate("@Name", nodes.item(i), XPathConstants.NODE);
                if (l_name != null)
                    Lanes[i].name = l_name.getNodeValue();
                else
                    Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                        "The Lane @Name is empty or null");

                l_id = (Node) xpath.evaluate("@Id", nodes.item(i), XPathConstants.NODE);
                if (l_id != null)
                    Lanes[i].id = l_id.getNodeValue();
                else
                    Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                        "The Lane @Id is empty or null");

            } catch (XPathExpressionException ex) {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                        "Error evaluating Name or Id for Lane", ex);
            }

        }

    }

    private void parseParticipants(org.w3c.dom.Document doc, XPath xpath) {
        XPathExpression exp_participant = null;
        try {
            exp_participant = xpath.compile("//xpdl2:Participant");
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                    "Error compiling xpath expression for Participant", ex);
        }

        Object res_act = null;
        try {
            res_act = exp_participant.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                    "Error evaluating xpath expression for Participant", ex);
        }

        NodeList nodes = (NodeList) res_act;

        Participants = new Participant[nodes.getLength()];

        // in this loop, the "Activities" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++) {
            Participants[i] = new Participant();

            Node a_name, a_id, a_extended, a_lane;
            try {

                a_id = (Node) xpath.evaluate("@Id", nodes.item(i), XPathConstants.NODE);
                if (a_id != null) {
                    Participants[i].id = a_id.getNodeValue();
                }
                 else
                     Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                                "There is no @Id for a Participant!");

                a_name = (Node) xpath.evaluate("@Name", nodes.item(i), XPathConstants.NODE);
                if (a_name != null) {
                    // replace all spaces for nothing
                    Participants[i].name = a_name.getNodeValue().replace(" ", "");
                } else
                     Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                                "There is no @Name for a Participant with id "+Participants[i].id);


                

                // check if the participant is assigned to a specific Lane, by using
                // an extended Attribute called "Lane"
                a_extended = (Node) xpath.evaluate("xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute/@Name", nodes.item(i),
                        XPathConstants.NODE);
                if (a_extended != null) {
                    if (a_extended.getNodeValue().equalsIgnoreCase("Lane")) {
                        a_lane = (Node) xpath.evaluate("xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute/@Value", nodes.item(i),
                                XPathConstants.NODE);
                        if (a_lane != null) {
                            Participants[i].lane = a_lane.getNodeValue();
                        }
                        else
                            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                                "There is no ExtendedAttribute Lane @Value for Participant "+ a_name);

                    } else
                        Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                                "There is ExtendedAttribute for Participant "+ a_name+ " but it is not called 'Lane'");
                } else
                 Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                    "There is no ExtendedAttribute @Name for Participant "+ a_name);


            } catch (XPathExpressionException ex) {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                        "Error evaluating Name, Id, or Lane for Participant", ex);
            }

        }

    }

    private Activity[] parseActivities(org.w3c.dom.Document doc, NodeList set,
            XPath xpath, Activity[] activities,Transition[] transitions) {

        XPathExpression exp_activity = null;

        try {
            if (set == null) {
                exp_activity = xpath.compile("//xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess/xpdl2:Activities/xpdl2:Activity");
            }

        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                    "Error compiling xpath expression for Main Activities", ex);
        }

        Object res_act = null;

        try {
            // if the method receive a null NodeList, we use the document
            if (set == null) {
                res_act = exp_activity.evaluate(doc, XPathConstants.NODESET);
            } else {
                res_act = set;
            }

        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                    "Error evaluating xpath expression for Main Activities", ex);
        }

        NodeList nodes = (NodeList) res_act;

        activities = new Activity[nodes.getLength()];

        // in this loop, the "Activities" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++) {
            activities[i] = new Activity();

            NodeList a_extended, a_trefs;
            NamedNodeMap attr;
            Node a_name, a_id, a_lane, a_gateway, g_type, a_start, a_end, a_subprocess;
            try {
                a_name = (Node) xpath.evaluate("@Name", nodes.item(i), XPathConstants.NODE);
                if (a_name != null) {
                    activities[i].name = a_name.getNodeValue();
                }
                else
                 Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                    "There is no @Name for Activity");

                a_id = (Node) xpath.evaluate("@Id", nodes.item(i), XPathConstants.NODE);
                if (a_id != null) {
                    activities[i].id = a_id.getNodeValue();
                }
                else
                 Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                    "There is no ExtendedAttribute @Id for Activity "+activities[i].name);

                a_lane = (Node) xpath.evaluate("xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId", nodes.item(i),
                        XPathConstants.NODE);
                // there is a lane_id for this activity
                if (a_lane != null) {
                    //TODO: cuando son actividades internas a un subproceso, no pillan el lane!
                    activities[i].lane_id = a_lane.getNodeValue();
                }
                else
                 Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                    "There is no @LaneId for Activity "+ a_name);


                a_start = (Node) xpath.evaluate("xpdl2:Event/xpdl2:StartEvent", nodes.item(i),
                        XPathConstants.NODE);
                // the activity is a start event
                if (a_start != null) {
                    activities[i].type = NodeType.START;
                } else {
                    a_end = (Node) xpath.evaluate("xpdl2:Event/xpdl2:EndEvent", nodes.item(i),
                            XPathConstants.NODE);
                    // the activity is an end event
                    if (a_end != null) {
                        activities[i].type = NodeType.END;
                    }
                }

                a_subprocess = (Node) xpath.evaluate("xpdl2:BlockActivity/@ActivitySetId", nodes.item(i),
                        XPathConstants.NODE);
                // the activity is a subprocess
                if (a_subprocess != null) {
                    activities[i].type = NodeType.SUBPROCESS;
                    activities[i].subset_id = a_subprocess.getNodeValue();
                    //this.parseActivitySets(doc, xpath, Activities[i].sub_id, Activities[i].subactivities);
                }

                // check if the activity has associated a specific duration, by using
                // an extended Attribute called "Duration"

                a_extended = (NodeList) xpath.evaluate("xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name='Duration']",
                        nodes.item(i),XPathConstants.NODESET);

                 if (a_extended != null) {
                    // in this loop, the "Activities" array is populated, using xpath parsing on the context node
                    for (int j = 0; j < a_extended.getLength(); j++) {

                        attr = a_extended.item(j).getAttributes();
                        
                        if (!attr.getNamedItem("Value").getNodeValue().equalsIgnoreCase(""))
                        {
                                activities[i].duration = attr.getNamedItem("Value").getNodeValue();
                        }
                        else
                            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                                        "an extended attribute Duration was found with empty value in activity " +
                                        activities[i].name);
                    }
                 }


                /*a_extended = (NodeList) xpath.evaluate("xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute", nodes.item(i),
                       XPathConstants.NODESET);

                if (a_extended != null) {
                    // in this loop, the "Activities" array is populated, using xpath parsing on the context node
                    for (int j = 0; j < a_extended.getLength(); j++) {
                        attr = a_extended.item(j).getAttributes();
                        if (attr.item(0).getNodeValue().equalsIgnoreCase("Duration")) {
                            if (!(attr.item(1).getNodeValue().equalsIgnoreCase(""))) {
                                activities[i].duration = attr.item(1).getNodeValue();
                            } else {
                                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                                        "an extended attribute Duration was found with no value in activity " +
                                        activities[i].name);
                            }
                        }
                        else
                            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                                        "an extended attribute was found for activity " +
                                        activities[i].name+" but it is not Duration");
                    }
                }
                */


                a_gateway = (Node) xpath.evaluate("xpdl2:Route/@GatewayType", nodes.item(i),
                        XPathConstants.NODE);
                // the node is a GATEWAY node
                if (a_gateway != null) {
                    activities[i].type = NodeType.GATEWAY;

                    g_type = (Node) xpath.evaluate(
                            "xpdl2:TransitionRestrictions/xpdl2:TransitionRestriction/xpdl2:Split/@Type",
                            nodes.item(i),
                            XPathConstants.NODE);
                    // the node is surely a JOIN
                    if (g_type == null) {
                        g_type = (Node) xpath.evaluate(
                                "xpdl2:TransitionRestrictions/xpdl2:TransitionRestriction/xpdl2:Join/@Type",
                                nodes.item(i),
                                XPathConstants.NODE);
                        if (g_type != null) {
                            if (g_type.getNodeValue().equalsIgnoreCase("Exclusive")) {
                                activities[i].restriction = TransitionRestriction.JOIN_EXCLUSIVE;
                            } else if (g_type.getNodeValue().equalsIgnoreCase("Inclusive")) {
                                activities[i].restriction = TransitionRestriction.JOIN_INCLUSIVE;
                            }
                        }
                    } // the node is a SPLIT
                    else {
                        if (g_type.getNodeValue().equalsIgnoreCase("Parallel")) {
                            activities[i].restriction = TransitionRestriction.SPLIT_PARALLEL;
                        } else if (g_type.getNodeValue().equalsIgnoreCase("Exclusive")) {
                            activities[i].restriction = TransitionRestriction.SPLIT_EXCLUSIVE;

                            a_trefs = (NodeList) xpath.evaluate("xpdl2:TransitionRestrictions/xpdl2:TransitionRestriction/xpdl2:Split/xpdl2:TransitionRefs/xpdl2:TransitionRef", nodes.item(i),
                                    XPathConstants.NODESET);

                            if (a_trefs != null) {

                                activities[i].param = new Parameter();
                                activities[i].param.affectedTransitions = new Transition[a_trefs.getLength()];
                                // now we need to parse the transitions that are involved in the XOR SPLIT
                                for (int j = 0; j < a_trefs.getLength(); j++) {
                                    attr = a_trefs.item(j).getAttributes();
                                    activities[i].param.affectedTransitions[j] = this.findTransition(transitions,attr.item(0).getNodeValue());
                                    activities[i].param.name = activities[i].param.affectedTransitions[j].parameterId;
                                }

                            }
                        }

                    }
                }

            } catch (XPathExpressionException ex) {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                        "Error evaluating XPDL information about Activities", ex);
            }

        }

        return activities;

    }

    private void parseActivitySet(org.w3c.dom.Document doc, XPath xpath) {

        XPathExpression exp_set = null;
        NodeList res_set, subact, subtrans = null;
        Node a_subsetid;

        try {
            exp_set = xpath.compile("//xpdl2:ActivitySets/xpdl2:ActivitySet");
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                    "Error compiling xpath expression for ActivitySet", ex);
        }

        try {
            res_set = (NodeList) exp_set.evaluate(doc, XPathConstants.NODESET);

            //this.ASets = new ActivitySet[res_set.getLength()];
            this.ASets = new HashMap();
            
            // para cada ActivitySet
            for (int i = 0; i < res_set.getLength(); i++) {

                ActivitySet as = new ActivitySet();

                a_subsetid = (Node) xpath.evaluate("@Id", res_set.item(i), XPathConstants.NODE);

                if (a_subsetid != null) {
                    as.id = a_subsetid.getNodeValue();

                    //we need to parse activities internal to this ActivitySet
                    try {
                        exp_set = xpath.compile("//xpdl2:ActivitySets/xpdl2:ActivitySet[@Id='" + as.id + "']/xpdl2:Activities/xpdl2:Activity");

                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                                "Error evaluating activities xpath expression for ActivitySet "+as.id, ex);
                    }
                    try {
                        subact = (NodeList) exp_set.evaluate(doc, XPathConstants.NODESET);
                        if (subact.getLength() > 0) {
                            as.activities = this.parseActivities(null, subact, xpath, as.activities, as.transitions);
                        }
                        else
                            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                                        "There is no subactivities for ActivitySet "+as.id);

                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                                "Error evaluating activities internal to ActivitySet "+as.id, ex);

                    }

                    //we need to parse Transitions internal to this ActivitySet
                    try {
                        exp_set = xpath.compile("//xpdl2:ActivitySets/xpdl2:ActivitySet[@Id='"
                                + as.id + "']/xpdl2:Transitions/xpdl2:Transition");

                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                                "Error compiling transitions xpath expression for ActivitySet "+as.id, ex);
                    }
                    try {
                        subtrans = (NodeList) exp_set.evaluate(doc, XPathConstants.NODESET);
                        if (subtrans.getLength() > 0) {
                            as.transitions = this.parseTransitions(null, subtrans, xpath, as.transitions);
                        }
                        else
                            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                                        "There is no transitions within ActivitySet "+as.id);


                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                                "Error evaluating transitions internal to ActivitySet "+as.id, ex);

                    }
                    // colocamos el ActivitySet en el HashMap ASets con la clave "id"
                    // del propio ActivitySet, asi nos sera facil encontrarlo luego
                    this.ASets.put(as.id,as);
                }

            }

        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                     "Error evaluating xpath expression for ActivitySet", ex);
        }

    }

    private Transition[] parseTransitions(org.w3c.dom.Document doc, NodeList set,
            XPath xpath, Transition[] transitions) {
        XPathExpression exp_trans = null;
        try {
            if (set==null)
                exp_trans = xpath.compile("//xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess/xpdl2:Transitions/xpdl2:Transition");
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                     "Error compiling xpath expression for Main Transitions", ex);
        }

        Object res_trans = null;
        try {
            if (set==null)
                res_trans = exp_trans.evaluate(doc, XPathConstants.NODESET);
            else
                res_trans = set;
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                     "Error evaluating xpath expression for Main Transitions", ex);
        }

        NodeList nodes = (NodeList) res_trans;

        transitions = new Transition[nodes.getLength()];

        // in this loop, the "Activities" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++) {
            transitions[i] = new Transition();

            Node a_name, a_id, a_from, a_to, a_param_id, a_operator, a_param_value;
            try {
                a_id = (Node) xpath.evaluate("@Id", nodes.item(i), XPathConstants.NODE);
                if (a_id != null)
                    transitions[i].id = a_id.getNodeValue();
                else
                    Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                        "There is no @Id for Transition");

                a_name = (Node) xpath.evaluate("@Name", nodes.item(i), XPathConstants.NODE);
                if (a_name != null)
                    transitions[i].name = a_name.getNodeValue();
                else
                    Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                        "There is no @Name for Transition");

                a_from = (Node) xpath.evaluate("@From", nodes.item(i), XPathConstants.NODE);
                if (a_from != null)
                    transitions[i].from = a_from.getNodeValue();
                else
                    Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                        "There is no @From for Transition");

                a_to = (Node) xpath.evaluate("@To", nodes.item(i), XPathConstants.NODE);
                if (a_to != null)
                    transitions[i].to = a_to.getNodeValue();
                else
                    Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.WARNING,
                        "There is no @To for Transition");

                a_param_id = (Node) xpath.evaluate("xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute/simulation:TransitionSimulationData/simulation:StructuredCondition/simulation:ParameterId",
                        nodes.item(i), XPathConstants.NODE);
                if (a_param_id != null) {
                    if (a_param_id.getFirstChild() != null) {
                        transitions[i].parameterId = a_param_id.getFirstChild().getTextContent();
                    }
                }

                a_operator = (Node) xpath.evaluate("xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute/simulation:TransitionSimulationData/simulation:StructuredCondition/simulation:Operator",
                        nodes.item(i), XPathConstants.NODE);
                if (a_operator != null) {
                    if (a_operator.getFirstChild() != null) {
                        transitions[i].operator = a_operator.getFirstChild().getTextContent();
                    }
                }

                a_param_value = (Node) xpath.evaluate("xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute/simulation:TransitionSimulationData/simulation:StructuredCondition/simulation:ParameterValue",
                        nodes.item(i), XPathConstants.NODE);
                if (a_param_value != null) {
                    if (a_param_value.getFirstChild() != null) {
                        transitions[i].parameterValue = a_param_value.getFirstChild().getTextContent();
                    }
                }
            } catch (XPathExpressionException ex) {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE,
                         "Error evaluating xpdl info for Transitions", ex);
            }

        }

        return transitions;

    }
}
