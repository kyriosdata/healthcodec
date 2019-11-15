package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.PartyRef;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartyRefTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidPartyRef(PartyRef p){
        ObjectIDTest.testValidObjectID(p.getObjectRef().getId());
        assertEquals("DEMOGRAPHIC", p.getObjectRef().getNamespace());
        assertEquals("type", p.getObjectRef().getType());
    }

    @Test
    void partyRefValidTest(){
        PartyRef p = RMObjectTestHelper.partyRef();
        s.serializePartyRef(p);
        p = s.deserializePartyRef();

        testValidPartyRef(p);
    }
}
