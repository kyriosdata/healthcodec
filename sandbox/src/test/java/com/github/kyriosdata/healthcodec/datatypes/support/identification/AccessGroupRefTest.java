package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.AccessGroupRef;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccessGroupRefTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidAccessGroupRef(AccessGroupRef a){
        ObjectIDTest.testValidObjectID(a.getObjectRef().getId());
        assertEquals("ACCESS_CONTROL", a.getObjectRef().getNamespace());
        assertEquals("ACCESS_GROUP", a.getObjectRef().getType());
    }

    @Test
    void accessGroupRefValidTest(){
        AccessGroupRef a = RMObjectTestHelper.accessGroupRef();
        s.serializeAccessGroupRef(a);
        a = s.deserializeAccessGroupRef();

        testValidAccessGroupRef(a);
    }

    @Test
    void accessGroupRefNullIdTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newAccessGroupRef(null);
        });
    }

}
