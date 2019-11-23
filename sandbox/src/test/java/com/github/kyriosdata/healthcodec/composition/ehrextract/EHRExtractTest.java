package com.github.kyriosdata.healthcodec.composition.ehrextract;

import com.github.kyriosdata.healthcodec.RMObject.EHRExtract;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.generic.ParticipationTest;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDateTimeTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.PartyRefTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EHRExtractTest {

    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidEHRExtract(EHRExtract e){
        DvDateTimeTest.testValidDvDateTime(e.getTimeCreated());
        assertEquals("value", e.getEhrId());
        PartyRefTest.testValidPartyRef(e.getSubjectOfCare());
        PartyRefTest.testValidPartyRef(e.getOriginator());
        ParticipationTest.testValidParticipation(e.getOtherParticipations().
                iterator().next());
        assertFalse(e.isIncludeMultimedia());
        assertEquals(10, e.getFollowLinks());
        assertEquals(null, e.getDirectory());
        XTerminologyTest.testValidXTerminology(e.getTerminology());
        XDemographicsTest.testValidXDemographics(e.getDemographics());
        XAccessControlTest.testValidXAccessControl(e.getAccessControl());
    }

    @Test
    void ehrExtractValidTest(){
        EHRExtract e = RMObjectTestHelper.ehrExtract();
        s.serializeEHRExtract(e);
        e = s.deserializeEHRExtract();

        testValidEHRExtract(e);
    }
}
