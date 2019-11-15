package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject.Party;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.LocatableRefTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PartyTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidParty(Party p){
        LocatableTest.testValidLocatable(p.getLocatable());
        PartyIdentityTest.testValidPartyIdentity(
                p.getIdentities().iterator().next());
        ContactTest.testValidContact(p.getContacts().iterator().next());
        PartyRelationshipTest.testValidPartyRelationship(
                p.getRelationships().iterator().next());
        LocatableRefTest.testValidLocatableRef(
                p.getReverseRelationships().iterator().next());
        ItemStructureTest.testValidItemStructure(p.getDetails());
    }

    @Test
    void partyValidTest(){
        Party p = RMObjectTestHelper.party(false);
        s.serializeParty(p);
        p = s.deserializeParty();

        testValidParty(p);
    }

    @Test
    void partyNullLocatableTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newParty(null,
                    RMObjectTestHelper.partyIdentitySet(
                            false, false),
                    RMObjectTestHelper.contactSet(false),
                    RMObjectTestHelper.partyRelationshipSet(false),
                    RMObjectTestHelper.locatableRefSet(false),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void partyEmptyIdentitiesTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newParty(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.partyIdentitySet(
                            true, true),
                    RMObjectTestHelper.contactSet(false),
                    RMObjectTestHelper.partyRelationshipSet(false),
                    RMObjectTestHelper.locatableRefSet(false),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void partyEmptyContactsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newParty(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.partyIdentitySet(
                            false, false),
                    RMObjectTestHelper.contactSet(true),
                    RMObjectTestHelper.partyRelationshipSet(false),
                    RMObjectTestHelper.locatableRefSet(false),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void partyEmptyRelationshipsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newParty(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.partyIdentitySet(
                            false, false),
                    RMObjectTestHelper.contactSet(false),
                    RMObjectTestHelper.partyRelationshipSet(true),
                    RMObjectTestHelper.locatableRefSet(false),
                    RMObjectTestHelper.itemStructure());
        });
    }
}
