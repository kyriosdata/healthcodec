package com.github.kyriosdata.healthcodec.composition.integration;

import com.github.kyriosdata.healthcodec.RMObject.GenericEntry;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.composition.content.ContentItemTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemTreeTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenericEntryTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidGenericEntry(GenericEntry g){
        ContentItemTest.testValidContentItem(g.getContentItem());
        ItemTreeTest.testValidItemTree(g.getData());
    }

    @Test
    void genericEntryValidTest(){
        GenericEntry g = RMObjectTestHelper.genericEntry();
        s.serializeGenericEntry(g);
        g = s.deserializeGenericEntry();

        testValidGenericEntry(g);
    }

    @Test
    void geneticEntryNullContentItemTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newGenericEntry(null,
                    RMObjectTestHelper.itemTree());
        });
    }
}
