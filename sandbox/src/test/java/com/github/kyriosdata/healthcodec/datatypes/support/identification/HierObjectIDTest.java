package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.HierObjectID;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class HierObjectIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidHierObjectID(HierObjectID h){
        UIDBasedIDTest.testValidUIDBasedID(h.getUIDBasedID());
    }

    @Test
    void hierObjectIDValidTest(){
        HierObjectID h = RMObjectTestHelper.hierObjectID();
        s.serializeHierObjectID(h);
        h = s.deserializeHierObjectID();

        testValidHierObjectID(h);
    }

    @Test
    void hierObjectIDNullValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHierObjectID(null);
        });
    }

    @Test
    void hierObjectIDEmptyValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHierObjectID("");
        });
    }
}
