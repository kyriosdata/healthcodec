package com.github.kyriosdata.healthcodec.common.generic;

import com.github.kyriosdata.healthcodec.RMObject.PartyIdentified;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.basic.DvIdentifierTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.PartyRefTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PartyIdentifiedTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidPartyIndentified(PartyIdentified p){
        PartyRefTest.testValidPartyRef(p.getExternalRef());
        assertEquals("name", p.getName());
        DvIdentifierTest.testValidDvIdentifier(p.getIdentifiers().get(0));
    }

    @Test
    void partyIdentifiedValidTest(){
        PartyIdentified p = RMObjectTestHelper.partyIdentified();
        s.serializePartyIdentified(p);
        p = s.deserializePartyIdentified();

        testValidPartyIndentified(p);
    }

    @Test
    void partyIdentifiedNullExternalRefNullNameNullIdentifiers(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newPartyIdentified(null,null,
                    null);
        });
    }

    @Test
    void partyIdentifiedEmptyName(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newPartyIdentified(RMObjectTestHelper.partyRef(),
                    "",
                    RMObjectTestHelper.dvIdentifierList(false));
        });
    }

    @Test
    void partyIdentifiedEmptyIdentifiers(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newPartyIdentified(RMObjectTestHelper.partyRef(),
                    "name",
                    RMObjectTestHelper.dvIdentifierList(true));
        });
    }
}
