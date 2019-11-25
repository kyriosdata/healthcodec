package com.github.kyriosdata.healthcodec.datastructure.itemstructure;

import com.github.kyriosdata.healthcodec.RMObject.ItemTable;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.representation.ClusterTest;
import org.junit.jupiter.api.Test;

public class ItemTableTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidItemTable(ItemTable i){
        ItemStructureTest.testValidItemStructure(i.getItemStructure());
        ClusterTest.testValidCluster(i.getRows().get(0));
    }

    @Test
    void itemTableValidTest(){
        ItemTable i = RMObjectTestHelper.itemTable();
        s.serializeItemTable(i);
        i = s.deserializeItemTable();

        testValidItemTable(i);
    }
}
