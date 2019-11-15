package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject.Actor;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.DvTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ActorTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidActor(Actor a){
        assertEquals("legal identity",
                a.getParty().getIdentities().iterator().next().
                        getLocatable().getName().getValue());
        RoleTest.testValidRoleTest(a.getRoles().iterator().next());
        DvTextTest.testValidDvText(a.getLanguages().iterator().next());
    }

    @Test
    void actorValidTest(){
        Actor a = RMObjectTestHelper.actor();
        s.serializeActor(a);
        a = s.deserializeActor();

        testValidActor(a);
    }

    @Test
    void actorNotLegalIdentityTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newActor(RMObjectTestHelper.party(false),
                    RMObjectTestHelper.roleSet(false),
                    RMObjectTestHelper.dvTextSet(false));
        });
    }

    @Test
    void actorEmptyRolesTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newActor(RMObjectTestHelper.party(true),
                    RMObjectTestHelper.roleSet(true),
                    RMObjectTestHelper.dvTextSet(false));
        });
    }

    @Test
    void actorEmptyLanguagesTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newActor(RMObjectTestHelper.party(true),
                    RMObjectTestHelper.roleSet(false),
                    RMObjectTestHelper.dvTextSet(true));
        });
    }
}
