package com.github.gabrielsxp.healthcodec;

/**
 *
 * @author Gabriel
 */
public class DvBoolean {
    public static byte META = Constants.TYPE_BOOLEAN_ID;
    
    public static int getValue(int handle) {
        return handle + Constants.TYPE_BOOLEAN_SIZE;
    }
}
