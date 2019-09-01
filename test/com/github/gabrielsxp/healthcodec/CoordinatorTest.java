/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gabrielsxp.healthcodec;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gabriel
 */
public class CoordinatorTest {
    
    /*
    * Teste para verificar definiçãoe recuperação dos valores
    * lógicos definidos para DvBoolean
    */
    @Test
    public void testDvBooleanTrue() throws Exception {
        byte[] data = new byte[2];
        Coordinator serialize = new Coordinator(data);
        serialize.insertDvBoolean(true);
        
        Coordinator deserialize = new Coordinator(serialize.getBuffer(), serialize.getControlMatrix());
        
        int DvBooleanPosition = deserialize.getDvBoolean();
        boolean booleanValue = deserialize.getBoolean(DvBoolean.getValue(DvBooleanPosition));
        assertEquals(true, booleanValue);
        
        
    }
    
    /*
    * Teste para verificar se as 4 Strings definidas para DvIdentifier
    * correspodem exatamente aos valores recuperados após a deserialização
    * dos dados
    */
    @Test
    public void testDvIdentifier() throws Exception {
        
        byte[] data = new byte[50];
        Coordinator c = new Coordinator(data);
        Coordinator deserialize = new Coordinator(c.getBuffer(), c.getControlMatrix());
        String expectedIssuer = "issuer";
        String expectedAssigner = "assigner";
        String expectedId = "id";
        String expectedType = "type";
        deserialize.insertDvIdentifier(expectedIssuer,expectedAssigner,expectedId,expectedType);
        int handle = deserialize.getDvIdentifier();
        
        int issuerPosition = DvIdentifier.getIssuer(handle);
        int issuerLength = deserialize.getInt(DvIdentifier.getIssuerLength(handle));
        String issuer = deserialize.getString(issuerPosition, issuerLength);
        assertEquals(expectedIssuer, issuer);
        
        int assignerPosition = issuerPosition + issuerLength;
        int assignerLength = deserialize.getInt(DvIdentifier.getAssignerLength(handle));
        String assigner = deserialize.getString(assignerPosition, assignerLength);
        assertEquals(expectedAssigner, assigner);
        
        int idPosition = assignerPosition + assignerLength;
        int idLength = deserialize.getInt(DvIdentifier.getIdLength(handle));
        String id = deserialize.getString(idPosition, idLength);
        assertEquals(expectedId, id);
        
        int typePosition = idPosition + idLength;
        int typeLength = deserialize.getInt(DvIdentifier.getTypeLength(handle));
        String type = deserialize.getString(typePosition, typeLength);
        assertEquals(expectedType, type);
        
    }

}
