package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.ObjectRef;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectRefTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidObjectRef(ObjectRef o){
        ObjectIDTest.testValidObjectID(o.getId());
        assertEquals("namespace", o.getNamespace());
        assertEquals("type", o.getType());
    }

    @Test
    void objectRefValidTest(){
        ObjectRef o = RMObjectTestHelper.objectRef();
        s.serializeObjectRef(o);
        o = s.deserializeObjectRef();

        testValidObjectRef(o);
    }

    @Test
    void objectRefNullIdTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newObjectRef(null, "namespace",
                    "type");
        });
    }

    @Test
    void objectRefNullNamespaceTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newObjectRef(RMObjectTestHelper.objectID(),
                    null,"type");
        });
    }

    @Test
    void objectRefNullTypeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newObjectRef(RMObjectTestHelper.objectID(),
                    "namespace",null);
        });
    }
}
