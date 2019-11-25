package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.TerminologyID;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TerminologyIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidTerminologyID(TerminologyID t){
        assertEquals("name", t.getName());
        assertEquals("version", t.getVersion());
        assertEquals("name(version)", t.getObjectID().getValue());
    }

    @Test
    void testValidTerminologyID(){
        TerminologyID t = RMObjectTestHelper.terminologyID();
        s.serializeTerminologyID(t);
        t = s.deserializeTerminologyID();

        testValidTerminologyID(t);
    }
}
