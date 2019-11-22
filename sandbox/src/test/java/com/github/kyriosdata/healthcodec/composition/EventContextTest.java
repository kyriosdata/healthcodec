package com.github.kyriosdata.healthcodec.composition;

import com.github.kyriosdata.healthcodec.RMObject.EventContext;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.generic.ParticipationTest;
import com.github.kyriosdata.healthcodec.common.generic.PartyIdentifiedTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDateTimeTest;
import com.github.kyriosdata.healthcodec.datatypes.text.DvCodedTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventContextTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidEventContext(EventContext e){
        PartyIdentifiedTest.testValidPartyIndentified(e.getHealthCareFacility());
        DvDateTimeTest.testValidDvDateTime(e.getStartTime());
        DvDateTimeTest.testValidDvDateTime(e.getEndTime());
        ParticipationTest.testValidParticipation(e.getParticipations().get(0));
        assertEquals("value", e.getLocation());
        DvCodedTextTest.testValidDvCodedText(e.getSetting());
        ItemStructureTest.testValidItemStructure(e.getOtherContext());
    }

    @Test
    void eventContextValidTest(){
        EventContext e = RMObjectTestHelper.eventContext();
        s.serializeEventContext(e);
        e = s.deserializeEventContext();

        testValidEventContext(e);
    }

    @Test
    void eventContextNullStartTimeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEventContext(RMObjectTestHelper.partyIdentified(),
                    null, RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.participationList(false),
                    "value", RMObjectTestHelper.dvCodedText(),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void eventContextEmptyParticipationsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEventContext(RMObjectTestHelper.partyIdentified(),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.participationList(true),
                    "value", RMObjectTestHelper.dvCodedText(),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void eventContextNullSettingTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEventContext(RMObjectTestHelper.partyIdentified(),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.participationList(true),
                    "value", null,
                    RMObjectTestHelper.itemStructure());
        });
    }
}
