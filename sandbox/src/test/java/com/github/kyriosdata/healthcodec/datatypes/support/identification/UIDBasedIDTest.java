package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.UIDBasedID;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UIDBasedIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidUIDBasedID(UIDBasedID u){
        assertEquals("value", u.getValue());
    }

    @Test
    void uIDBasedIDValidoTest(){
        UIDBasedID u = RMObjectTestHelper.uIDBasedID();
        s.serializeUIDBasedID(u);
        u = s.deserializeUIDBasedID();

        testValidUIDBasedID(u);
    }

    @Test
    void uIDBasedIDEmptyValue(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newUIDBasedID("");
        });
    }
}
