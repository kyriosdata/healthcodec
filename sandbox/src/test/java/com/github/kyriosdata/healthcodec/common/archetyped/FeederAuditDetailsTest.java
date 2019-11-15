package com.github.kyriosdata.healthcodec.common.archetyped;

import com.github.kyriosdata.healthcodec.RMObject.FeederAuditDetails;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.generic.PartyIdentifiedTest;
import com.github.kyriosdata.healthcodec.common.generic.PartyProxyTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FeederAuditDetailsTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidFeederAuditDetails(FeederAuditDetails f){
        assertEquals("systemID", f.getSystemID());
        PartyIdentifiedTest.testValidPartyIndentified(f.getLocation());
        PartyIdentifiedTest.testValidPartyIndentified(f.getProvider());
        PartyProxyTest.testValidPartyProxy(f.getSubject());
        assertEquals("versionID", f.getVersionID());
    }

    @Test
    void feederAuditDetailsValidTest(){
        FeederAuditDetails f = RMObjectTestHelper.feederAuditDetails();
        s.serializeFeederAuditDetails(f);
        f = s.deserializeFeederAuditDetails();

        testValidFeederAuditDetails(f);
    }

    @Test
    void feederAuditDetailsEmptySystemIDTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newFeederAuditDetails("",
                    RMObjectTestHelper.partyIdentified(),
                    RMObjectTestHelper.partyIdentified(),
                    RMObjectTestHelper.partyProxy(),
                    "versionID");
        });
    }
}
