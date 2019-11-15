package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.ObjectVersionID;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectVersionIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidObjectVersionID(ObjectVersionID o){
        UIDBasedIDTest.testValidUIDBasedID(o.getUIDBasedID());
    }

    @Test
    void objectVersionIDValidTest(){
        ObjectVersionID o = RMObjectTestHelper.objectVersionID();
        s.serializeObjectVersionID(o);
        o = s.deserializeObjectVersionID();

        testValidObjectVersionID(o);
    }

    @Test
    void objectVersionIDNullValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newObjectVersionID(null);
        });
    }

    @Test
    void objectVersionIDEmptyValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newObjectVersionID("");
        });
    }
}
