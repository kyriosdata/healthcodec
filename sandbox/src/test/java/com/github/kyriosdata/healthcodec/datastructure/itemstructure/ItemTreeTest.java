package com.github.kyriosdata.healthcodec.datastructure.itemstructure;

import com.github.kyriosdata.healthcodec.RMObject.ItemTree;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.representation.ItemTest;
import org.junit.jupiter.api.Test;

public class ItemTreeTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidItemTree(ItemTree i){
        ItemStructureTest.testValidItemStructure(i.getItemStructure());
        ItemTest.testValidItem(i.getItems().get(0));
    }

    @Test
    void itemTreeValidTest(){
        ItemTree i = RMObjectTestHelper.itemTree();
        s.serializeItemTree(i);
        i = s.deserializeItemTree();

        testValidItemTree(i);
    }
}
