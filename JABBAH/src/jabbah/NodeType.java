
package jabbah;

/**
 * @author arturo
 * Define some constants that represents the type of node 
 * 
 * - DEFAULT is an activity
 * - GATEWAY is a Route activity
 * - SERIAL and PARALLEL are used in the block detection algorithm
 * 
 */
class NodeType
{
    public static final int DEFAULT = 0;
    public static final int SERIAL = 1;
    public static final int PARALLEL = 2;
    public static final int GATEWAY = 3;
}
