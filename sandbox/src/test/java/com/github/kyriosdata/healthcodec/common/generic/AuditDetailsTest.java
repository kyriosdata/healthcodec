package com.github.kyriosdata.healthcodec.common.generic;

import com.github.kyriosdata.healthcodec.RMObject.AuditDetails;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDateTimeTest;
import com.github.kyriosdata.healthcodec.datatypes.text.DvCodedTextTest;
import com.github.kyriosdata.healthcodec.datatypes.text.DvTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuditDetailsTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidAuditDetails(AuditDetails a){
        assertEquals("timePosition", a.getTimePosition());
        PartyProxyTest.testValidPartyProxy(a.getCommitter());
        DvDateTimeTest.testValidDvDateTime(a.getTimeCommitted());
        DvCodedTextTest.testValidDvCodedText(a.getChangeType());
        DvTextTest.testValidDvText(a.getDescription());
    }

    @Test
    void auditDetailsValidTest(){
        AuditDetails a = RMObjectTestHelper.auditDetails();
        s.serializeAuditDetails(a);
        a = s.deserializeAuditDetails();

        testValidAuditDetails(a);
    }

}
