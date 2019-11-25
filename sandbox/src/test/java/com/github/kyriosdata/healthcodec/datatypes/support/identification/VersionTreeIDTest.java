package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.VersionTreeID;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VersionTreeIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidVersionTreeID(VersionTreeID v){
        assertEquals("value", v.getValue());
    }

    @Test
    void versionTreeIDValidTest(){
        VersionTreeID v = RMObjectTestHelper.versionTreeID();
        s.serializeVersionTreeID(v);
        v = s.deserializeVersionTreeID();

        testValidVersionTreeID(v);
    }

    @Test
    void versionTreeIDNullValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newVersionTreeID(null);
        });
    }

    @Test
    void versionTreeIDEmptyValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newVersionTreeID("");
        });
    }
}
