package jabbah;

/**
 * Define some constants that represents the type of transition restriction for
 * the node, directly extracted from the XPDL source
 * 
 * @param SPLIT_EXCLUSIVE represents an XOR
 * @param JOIN_EXCLUSIVE represents the join from an XOR
 * @param SPLIT_PARALLEL represents a parallel block
 * @param JOIN_INCLUSIVE represents a simple merge from a parallel block
 * @author Arturo
 */
public class TransitionRestriction
{
    public static final int SPLIT_EXCLUSIVE = 0;
    public static final int JOIN_EXCLUSIVE = 1;
    public static final int SPLIT_PARALLEL = 2;
    public static final int JOIN_INCLUSIVE = 3;
}

