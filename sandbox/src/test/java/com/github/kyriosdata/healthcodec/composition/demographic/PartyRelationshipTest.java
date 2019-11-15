package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject.PartyRelationship;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectRefTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PartyRelationshipTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidPartyRelationship(PartyRelationship p){
        LocatableTest.testValidLocatable(p.getLocatable());
        ItemStructureTest.testValidItemStructure(p.getDetails());
        ObjectRefTest.testValidObjectRef(p.getSource());
        ObjectRefTest.testValidObjectRef(p.getTarget());
    }

    @Test
    void partyRelationshipValidTest(){
        PartyRelationship p = RMObjectTestHelper.partyRelationship();
        s.serializePartyRelationship(p);
        p = s.deserializePartyRelationship();

        testValidPartyRelationship(p);
    }

    @Test
    void partyRelationshipNullLocatableTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newPartyRelationship(null,
                    RMObjectTestHelper.itemStructure(),
                    RMObjectTestHelper.objectRef(),
                    RMObjectTestHelper.objectRef());
        });
    }

    @Test
    void partyRelationshipNullSourceTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newPartyRelationship(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.itemStructure(),
                    null,
                    RMObjectTestHelper.objectRef());
        });
    }

    @Test
    void partyRelationshipNullTargetTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newPartyRelationship(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.itemStructure(),
                    RMObjectTestHelper.objectRef(),
                    null);
        });
    }
}
