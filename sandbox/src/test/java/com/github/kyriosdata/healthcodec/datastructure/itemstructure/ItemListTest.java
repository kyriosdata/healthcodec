package com.github.kyriosdata.healthcodec.datastructure.itemstructure;

import com.github.kyriosdata.healthcodec.RMObject.ItemList;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.ArchetypedTest;
import com.github.kyriosdata.healthcodec.common.archetyped.FeederAuditTest;
import com.github.kyriosdata.healthcodec.common.archetyped.LinkTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.representation.ElementTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.UIDBasedIDTest;
import com.github.kyriosdata.healthcodec.datatypes.text.DvTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemListTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidItemList(ItemList i){
        UIDBasedIDTest.testValidUIDBasedID(i.getUid());
        assertEquals("value", i.getArchetypeNodeId());
        DvTextTest.testValidDvText(i.getName());
        ArchetypedTest.testValidArchetyped(i.getArchetypeDetails());
        FeederAuditTest.testValidFeederAudit(i.getFeederAudit());
        LinkTest.testValidLink(i.getLinks().iterator().next());
        ElementTest.testValidElement(i.getItems().get(0));
    }

    @Test
    void itemListValidTrue(){
        ItemList i = RMObjectTestHelper.itemList();
        s.serializeItemList(i);
        i = s.deserializeItemList();

        testValidItemList(i);
    }
}
