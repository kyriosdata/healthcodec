package com.github.kyriosdata.healthcodec.composition.content.entry;

import com.github.kyriosdata.healthcodec.RMObject.CareEntry;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectRefTest;
import org.junit.jupiter.api.Test;

public class CareEntryTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidCareEntry(CareEntry c){
        EntryTest.testValidEntry(c.getEntry());
        ItemStructureTest.testValidItemStructure(c.getProtocol());
        ObjectRefTest.testValidObjectRef(c.getGuidelineId());
    }

    @Test
    void careEntryValidTest(){
        CareEntry c = RMObjectTestHelper.careEntry();
        s.serializeCareEntry(c);
        c = s.deserializeCareEntry();

        testValidCareEntry(c);
    }
}
