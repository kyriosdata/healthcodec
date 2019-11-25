package com.github.kyriosdata.healthcodec.datastructure.itemstructure;

import com.github.kyriosdata.healthcodec.RMObject.ItemStructure;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

public class ItemStructureTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidItemStructure(ItemStructure i){
        DataStructureTest.testValidDataStructure(i.getDataStructure());
    }

    @Test
    void itemStructureValidTest(){
        ItemStructure i = RMObjectTestHelper.itemStructure();
        s.serializeItemStructure(i);
        i = s.deserializeItemStructure();

        testValidItemStructure(i);
    }
}
