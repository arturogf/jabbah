package jabbah;

/**
 *
 * Define some constants that represents the type of node  
 * @param DEFAULT is an activity
 * @param GATEWAY is a Route activity
 * @param SERIAL is used in the block detection algorithm
 * @param PARALLEL is used in the block detection algorithm
 *  
 */
public class NodeType
{
    public static final int DEFAULT = 0;
    public static final int SERIAL = 1;
    public static final int PARALLEL = 2;
    public static final int GATEWAY = 3;
    public static final int START = 4;
    public static final int END = 5;
    public static final int SUBPROCESS = 6;
}
