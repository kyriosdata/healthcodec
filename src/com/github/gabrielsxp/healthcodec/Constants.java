/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gabrielsxp.healthcodec;

/**
 *
 * @author Gabriel
 */
public class Constants {
    
    public static final int HEADER_POSITION = 0;
    public static final int DATA_POSITION = 1;
    public static final int META_INFO = 2;
    
    public static final byte TYPE_INT_ID = 0;
    public static final byte TYPE_BOOLEAN_ID = 1;
    public static final byte TYPE_DOUBLE_ID = 2;
    public static final byte TYPE_STRING_ID = 3;
    
    public static final byte TYPE_BYTE_SIZE = 1;
    public static final byte TYPE_INT_SIZE = 4;
    public static final byte TYPE_BOOLEAN_SIZE = 1;
    public static final byte TYPE_DOUBLE_SIZE = 8;
    
    public static final int CLASS_DVBOOLEAN = 0;
    public static final int CLASS_DVIDENTIFIER = 1;
    
}
