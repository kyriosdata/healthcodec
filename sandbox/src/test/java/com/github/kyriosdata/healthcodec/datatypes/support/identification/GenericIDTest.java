package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.GenericID;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenericIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidGenericID(GenericID g){
        ObjectIDTest.testValidObjectID(g.getObjectID());
        assertEquals("scheme", g.getScheme());
    }

    @Test
    void genericIDValidTest(){
        GenericID g = RMObjectTestHelper.genericID();
        s.serializeGenericID(g);
        g = s.deserializeGenericID();

        testValidGenericID(g);
    }

    @Test
    void genericIDEmptyScheme(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newGenericID("value", "");
        });
    }
}
