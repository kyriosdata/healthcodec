package com.github.kyriosdata.healthcodec.datastructure.history;

import com.github.kyriosdata.healthcodec.RMObject.EventWithItemSingle;
import com.github.kyriosdata.healthcodec.RMObject.EventWithItemTable;
import com.github.kyriosdata.healthcodec.RMObject.EventWithItemTree;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemSingleTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemTableTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemTreeTest;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDateTimeTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventTest {
    RMObjectSerializationClient s =  null;

    @BeforeEach
    void setUp(){
        s = RMObjectSerializationClient.create();
    }

    public static void testValidEventWithItemTree(EventWithItemTree e){
        LocatableTest.testValidLocatable(e.getLocatable());
        DvDateTimeTest.testValidDvDateTime(e.getTime());
        ItemTreeTest.testValidItemTree(e.getData());
        ItemStructureTest.testValidItemStructure(e.getState());
    }

    public static void testValidEventWithItemSingle(EventWithItemSingle e){
        LocatableTest.testValidLocatable(e.getLocatable());
        DvDateTimeTest.testValidDvDateTime(e.getTime());
        ItemSingleTest.testValidItemSingle(e.getData());
        ItemStructureTest.testValidItemStructure(e.getState());
    }

    public static void testValidEventWithItemTable(EventWithItemTable e){
        LocatableTest.testValidLocatable(e.getLocatable());
        DvDateTimeTest.testValidDvDateTime(e.getTime());
        ItemTableTest.testValidItemTable(e.getData());
        ItemStructureTest.testValidItemStructure(e.getState());
    }

    @Test
    void eventWithItemTreeValidTest(){
        EventWithItemTree e = RMObjectTestHelper.eventWithItemTree();
        s.serializeEvent(e);
        e = s.deserializeEventWithItemTree();

        testValidEventWithItemTree(e);
    }

    @Test
    void eventWithItemSingleValidTest(){
        EventWithItemSingle e = RMObjectTestHelper.eventWithItemSingle();
        s.serializeEvent(e);
        e = s.deserializeEventWithItemSingle();

        testValidEventWithItemSingle(e);
    }

    @Test
    void eventWithItemTableValidTest(){
        EventWithItemTable e = RMObjectTestHelper.eventWithItemTable();
        s.serializeEvent(e);
        e = s.deserializeEventWithItemTable();

        testValidEventWithItemTable(e);
    }

    @Test
    void eventWithItemTreeNullTimeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEventWithItemTree(RMObjectTestHelper.locatable(),
                    null, RMObjectTestHelper.itemTree(),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void eventWithItemTreeNullDataTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEventWithItemTree(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.dvDateTime(), null,
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void eventWithItemSingleNullTimeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEventWithItemSingle(RMObjectTestHelper.locatable(),
                    null, RMObjectTestHelper.itemSingle(),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void eventWithItemSingleNullDataTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEventWithItemSingle(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.dvDateTime(), null,
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void eventWithItemTableNullTimeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEventWithItemTable(RMObjectTestHelper.locatable(),
                    null, RMObjectTestHelper.itemTable(),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void eventWithItemTableNullDataTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEventWithItemTable(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.dvDateTime(), null,
                    RMObjectTestHelper.itemStructure());
        });
    }

}
