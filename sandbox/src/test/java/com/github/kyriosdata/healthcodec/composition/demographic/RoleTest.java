package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject.Role;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.PartyRefTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoleTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidRoleTest(Role r){
        PartyTest.testValidParty(r.getParty());
        CapabilityTest.testValidCapability(r.getCapabilities().get(0));
        PartyRefTest.testValidPartyRef(r.getPerformer());
    }

    @Test
    void roleValidTest(){
        Role r = RMObjectTestHelper.role();
        s.serializeRole(r);
        r = s.deserializeRole();

        testValidRoleTest(r);
    }

    @Test
    void roleEmptyCapabilitiesTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newRole(RMObjectTestHelper.party(false),
                    RMObjectTestHelper.capabilityList(true),
                    RMObjectTestHelper.partyRef());
        });
    }

    @Test
    void roleNullPerformerTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newRole(RMObjectTestHelper.party(false),
                    RMObjectTestHelper.capabilityList(false),
                    null);
        });
    }
}
