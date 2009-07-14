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
    int type = NodeType.DEFAULT;
    int restriction = TransitionRestriction.NONE;
    //int numInputs;
    MyWeightedVertex node;

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
