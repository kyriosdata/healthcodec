package com.github.kyriosdata.healthcodec.datastructure.itemstructure;

import com.github.kyriosdata.healthcodec.RMObject.ItemSingle;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.representation.ElementTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemSingleTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidItemSingle(ItemSingle i){
        ItemStructureTest.testValidItemStructure(i.getItemStructure());
        ElementTest.testValidElement(i.getItem());
    }

    @Test
    void itemSingleValidTest(){
        ItemSingle i = RMObjectTestHelper.itemSingle();
        s.serializeItemSingle(i);
        i = s.deserializeItemSingle();

        testValidItemSingle(i);
    }

    @Test
    void itemSingleNullItemTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newItemSingle(RMObjectTestHelper.itemStructure(),
                    null);
        });
    }
}
