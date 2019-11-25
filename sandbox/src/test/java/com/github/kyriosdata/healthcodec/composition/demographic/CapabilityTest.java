package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject.Capability;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CapabilityTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidCapability(Capability c){
        LocatableTest.testValidLocatable(c.getLocatable());
        ItemStructureTest.testValidItemStructure(c.getCredentials());
    }

    @Test
    void capabilityValidTest(){
        Capability c = RMObjectTestHelper.capability();
        s.serializeCapability(c);
        c = s.deserializeCapability();

        testValidCapability(c);
    }

    @Test
    void capabilityNullCredentialsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newCapability(RMObjectTestHelper.locatable(),
                    null);
        });
    }
}
