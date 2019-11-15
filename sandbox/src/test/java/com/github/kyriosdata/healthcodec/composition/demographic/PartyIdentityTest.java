package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject.PartyIdentity;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PartyIdentityTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidPartyIdentity(PartyIdentity p ){
        LocatableTest.testValidLocatable(p.getLocatable());
        ItemStructureTest.testValidItemStructure(p.getDetails());
    }

    @Test
    void partyIdentityValidTest(){
        PartyIdentity p = RMObjectTestHelper.partyIdentity();
        s.serializePartyIdentity(p);
        p = s.deserializePartyIdentity();

        testValidPartyIdentity(p);
    }

    @Test
    void partyIdentityNullDetailsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newPartyIdentity(RMObjectTestHelper.locatable(),
                    null);
        });
    }
}
