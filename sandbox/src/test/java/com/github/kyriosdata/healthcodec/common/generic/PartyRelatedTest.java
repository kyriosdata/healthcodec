package com.github.kyriosdata.healthcodec.common.generic;

import com.github.kyriosdata.healthcodec.RMObject.PartyRelated;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.DvCodedTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PartyRelatedTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidPartyRelated(PartyRelated p){
        PartyIdentifiedTest.testValidPartyIndentified(p.getPi());
        DvCodedTextTest.testValidDvCodedText((p.getRelationship()));
    }

    @Test
    void partyRelatedValidTest(){
        PartyRelated p = RMObjectTestHelper.partyRelated();
        s.serializePartyRelated(p);
        p = s.deserializePartyRelated();

        testValidPartyRelated(p);
    }

    @Test
    void partyRelatedNullRelationship(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newPartyRelated(RMObjectTestHelper.partyIdentified(),
                    null);
        });
    }
}
