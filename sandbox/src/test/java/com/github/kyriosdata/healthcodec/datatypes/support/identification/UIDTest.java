package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.UID;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidUID(UID u){
        assertEquals("value", u.getValue());
    }

    @Test
    void uidValidTest(){
        UID u = RMObjectTestHelper.uID();
        s.serializeUID(u);
        u = s.deserializeUID();

        testValidUID(u);
    }

    @Test
    void uidEmptyValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newUID("");
        });
    }
}
