package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.ArchetypeID;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArchetypeIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidArchetypeID(ArchetypeID a){
        ObjectIDTest.testValidObjectID(a.getObjectID());
    }

    @Test
    void archetypeIDValidTest(){
        ArchetypeID a = RMObjectTestHelper.archetypeID();
        s.serializeArchetypeID(a);
        a = s.deserializeArchetypeID();

        testValidArchetypeID(a);
    }

    @Test
    void archetypeIDNullValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newArchetypeID(null);
        });
    }

    @Test
    void archetypeIDEmptyValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newArchetypeID("");
        });
    }
}
