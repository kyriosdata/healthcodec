/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gabrielsxp.healthcodec;

import com.github.gabrielsxp.healthcodec.BufferOperations;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Gabriel
 */
public class BufferOperationsTest {

    public static void main(String[] args) throws UnsupportedEncodingException {

        byte[] data = new byte[50];

        int valor = 35;
        String string1 = "Primeira String de Teste";
        String string2 = "Segunda String de Teste";
        String str1 = "teste 1";
        String str2 = "teste 2";

        BufferOperations serializable = BufferOperations.serialize(data);
        serializable.setDvParsable(valor, string1, string2);
        serializable.setTerminologyID(str1, str2);

        byte[] b = serializable.data();
        BufferOperations operations = BufferOperations.deserialize(b);
        
        
        byte[] buffer = operations.data();
        for (int i = 0; i < buffer.length; i++) {
            System.out.println(i + " - " + buffer[i]);
        }
        

        DvParsable parsable = operations.getDvParsable();
        TerminologyID terminology = operations.getTerminologyID();
        
        System.out.println("Parsable: " + parsable.getInteger() + ", " + parsable.getFirstString() + ", " + parsable.getSecondString());
        System.out.println("TerminologyID: " + terminology.getFirstString() + ", " + terminology.getSecondString());
        
    }

}
