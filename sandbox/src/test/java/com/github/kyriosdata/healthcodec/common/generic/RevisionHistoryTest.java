package com.github.kyriosdata.healthcodec.common.generic;

import com.github.kyriosdata.healthcodec.RMObject.RevisionHistory;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RevisionHistoryTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidRevisionHistory(RevisionHistory r){
        RevisionHistoryItemTest.testValidRevisionHistoryItem(r.getItems().get(0));
    }

    @Test
    void revisionHistoryTestValid(){
        RevisionHistory r = RMObjectTestHelper.revisionHistory();
        s.serializeRevisionHistory(r);
        r = s.deserializeRevisionHistory();

        testValidRevisionHistory(r);
    }

    @Test
    void revisionHistoryNullItemsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newRevisionHistory(null);
        });
    }

    @Test
    void revisionHistoryRmptyItemsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newRevisionHistory(
                    RMObjectTestHelper.revisionHistoryItemList(true));
        });
    }
}
