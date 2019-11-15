package com.github.kyriosdata.healthcodec.datastructure.itemstructure.representation;

import com.github.kyriosdata.healthcodec.RMObject.Item;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import org.junit.jupiter.api.Test;

public class ItemTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidItem(Item i){
        LocatableTest.testValidLocatable(i.getLocatable());
    }

    @Test
    void itemValidTest(){
        Item i = RMObjectTestHelper.item();
        s.serializeItem(i);
        i = s.deserializeItem();

        testValidItem(i);
    }
}
