
package jabbah;

/**
 * Define some constants that represents the type of transition restriction for
 * the node, directly extracted from the XPDL source
 * 
 * SPLIT_EXCLUSIVE represents an XOR
 * JOIN_EXCLUSIVE represents the join from an XOR
 * SPLIT_PARALLEL represents a parallel block
 * JOIN_INCLUSIVE represents a simple merge from a parallel block
 @author Arturo
 */
class TransitionRestriction
{
    public static final int SPLIT_EXCLUSIVE = 0;
    public static final int JOIN_EXCLUSIVE = 1;
    public static final int SPLIT_PARALLEL = 2;
    public static final int JOIN_INCLUSIVE = 3;
}

