/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gabrielsxp.healthcodec;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Gabriel
 */
public class UsefulMethods {
    
    public static byte[] getUTF8BytesFromString(String s) throws UnsupportedEncodingException{
        return s.getBytes("UTF-8");
    }
    
    public static byte[] writeIntegerOnByteArray(int position, int value, byte[] array){
        ByteBuffer b = ByteBuffer.wrap(array);
        b.putInt(value, position);
        
        return b.array();
    }
    
    /*
    * Função repsonsável por receber um array de bytes com os bytes na codificação
    * UTF-8 e realizar a conversão da mesma em String
    * 
    * @params utf8ByteArray Array de bytes com os bytes da String em UTF-8
    * @return a String convertida
    */
    public static String getStringFromByteArray(byte[] utf8ByteArray) {
        return new String(utf8ByteArray, StandardCharsets.UTF_8);
    }
    
}
