package com.github.kyriosdata.healthcodec.common.generic;

import com.github.kyriosdata.healthcodec.RMObject.RevisionHistoryItem;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectVersionIDTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RevisionHistoryItemTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidRevisionHistoryItem(RevisionHistoryItem r){
        AuditDetailsTest.testValidAuditDetails(r.getAudits().get(0));
        ObjectVersionIDTest.testValidObjectVersionID(r.getVersionID());
    }

    @Test
    void revisionHistoryItemsValidTest(){
        RevisionHistoryItem r = RMObjectTestHelper.revisionHistoryItem();
        s.serializeRevisionHistoryItem(r);
        r = s.deserializeRevisionHistoryItem();

        testValidRevisionHistoryItem(r);
    }

    @Test
    void revisionHistoryItemNullAuditsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newRevisionHistoryItem(null,
                    RMObjectTestHelper.objectVersionID());
        });
    }

    void revisionHistoryItemEmptyAuditsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newRevisionHistoryItem(
                    RMObjectTestHelper.auditDetailsList(true),
                    RMObjectTestHelper.objectVersionID());
        });
    }

    void revisionHistoryItemNullVersionIdTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newRevisionHistoryItem(
                    RMObjectTestHelper.auditDetailsList(false),
                    null);
        });
    }
}
