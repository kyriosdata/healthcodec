package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.ObjectID;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidObjectID(ObjectID o){
        assertEquals("value", o.getValue());
    }

    @Test
    void objectIDValidInstanceTest(){
        ObjectID o = RMObjectTestHelper.objectID();
        s.serializeObjectID(o);
        o = s.deserializeObjectID();

        testValidObjectID(o);
    }

    @Test
    void objectIDEmptyValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newObjectID("");
        });
    }

    @Test
    void objectIDNullValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newObjectID(null);
        });
    }
}
