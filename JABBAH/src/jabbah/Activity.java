/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jabbah;

/**
 *
 * @author arturogf
 */
public class Activity {

    String id;
    String name;
    String lane_id;
    String duration;
    int type = NodeType.DEFAULT;
    int restriction = TransitionRestriction.NONE;
    MyWeightedVertex node;
    // if it is a gateway
    Parameter param;
    // if it is a subprocess
    String subset_id="";

    public Activity()
    {
    }

    public int numInputTransitions(Transition[] T)
    {
        int n=0;

        for (int i=0; i<T.length; i++)
        {
            if (T[i].to.equalsIgnoreCase(this.id))
                n=n+1;
        }

        return n;
    }


}
