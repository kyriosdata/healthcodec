package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject.Address;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidAddress(Address a){
        LocatableTest.testValidLocatable(a.getLocatable());
        ItemStructureTest.testValidItemStructure(a.getDetails());
    }

    @Test
    void addressValidTest(){
        Address a = RMObjectTestHelper.address();
        s.serializeAddress(a);
        a = s.deserializeAddress();

        testValidAddress(a);
    }

    @Test
    void addressNullDetailsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newAddress(RMObjectTestHelper.locatable(),
                    null);
        });
    }
}
