package com.github.kyriosdata.healthcodec.composition.content.entry;

import com.github.kyriosdata.healthcodec.RMObject.AdminEntry;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminEntryTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidAdminEntry(AdminEntry a){
        EntryTest.testValidEntry(a.getEntry());
        ItemStructureTest.testValidItemStructure(a.getData());
    }

    @Test
    void adminEntryTestValid(){
        AdminEntry a = RMObjectTestHelper.adminEntry();
        s.serializeAdminEntry(a);
        a = s.deserializeAdminEntry();

        testValidAdminEntry(a);
    }

    @Test
    void adminEntryNullDataTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newAdminEntry(RMObjectTestHelper.entry(),
                    null);
        });
    }
}
