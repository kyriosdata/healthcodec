package com.github.kyriosdata.healthcodec.common.generic;

import com.github.kyriosdata.healthcodec.RMObject.Participation;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.DvCodedTextTest;
import com.github.kyriosdata.healthcodec.datatypes.text.DvTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParticipationTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidParticipation(Participation p){
        PartyProxyTest.testValidPartyProxy(p.getPerformer());
        DvTextTest.testValidDvText(p.getFunction());
        DvCodedTextTest.testValidDvCodedText(p.getMode());
    }

    @Test
    void participationValidTest(){
        Participation p = RMObjectTestHelper.participation();
        s.serializeParticipation(p);
        p = s.deserializeParticipation();

        testValidParticipation(p);
    }

    @Test
    void participationNullPerformerTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newParticipation(null,
                    RMObjectTestHelper.dvText(),
                    RMObjectTestHelper.dvCodedText(),
                    RMObjectTestHelper.dvInterval());
        });
    }

    @Test
    void participationNullFunctionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newParticipation(RMObjectTestHelper.partyProxy(),
                    null,
                    RMObjectTestHelper.dvCodedText(),
                    RMObjectTestHelper.dvInterval());
        });
    }

    @Test
    void participationNullModeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newParticipation(RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.dvText(),
                    null,
                    RMObjectTestHelper.dvInterval());
        });
    }
}
