package com.github.kyriosdata.healthcodec.datastructure.history;

import com.github.kyriosdata.healthcodec.RMObject;
import com.github.kyriosdata.healthcodec.RMObject.HistoryWithItemTree;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.DataStructureTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDateTimeTest;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDurationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class HistoryTest {
    RMObjectSerializationClient s = null;

    @BeforeEach
    void setUp(){
        s = RMObjectSerializationClient.create();
    }

    public static void testValidHistoryWithItemTree(HistoryWithItemTree h){
        DataStructureTest.testValidDataStructure(h.getDataStructure());
        DvDateTimeTest.testValidDvDateTime(h.getOrigin());
        EventTest.testValidEventWithItemTree(h.getEvents().get(0));
        DvDurationTest.testValidDvDuration(h.getPeriod());
        DvDurationTest.testValidDvDuration(h.getDuration());
        ItemStructureTest.testValidItemStructure(h.getSummary());
    }

    public static void testValidHistoryWithItemSingle(RMObject.HistoryWithItemSingle h){
        DataStructureTest.testValidDataStructure(h.getDataStructure());
        DvDateTimeTest.testValidDvDateTime(h.getOrigin());
        EventTest.testValidEventWithItemSingle(h.getEvents().get(0));
        DvDurationTest.testValidDvDuration(h.getPeriod());
        DvDurationTest.testValidDvDuration(h.getDuration());
        ItemStructureTest.testValidItemStructure(h.getSummary());
    }

    public static void testValidHistoryWithItemTable(RMObject.HistoryWithItemTable h){
        DataStructureTest.testValidDataStructure(h.getDataStructure());
        DvDateTimeTest.testValidDvDateTime(h.getOrigin());
        EventTest.testValidEventWithItemTable(h.getEvents().get(0));
        DvDurationTest.testValidDvDuration(h.getPeriod());
        DvDurationTest.testValidDvDuration(h.getDuration());
        ItemStructureTest.testValidItemStructure(h.getSummary());
    }

    @Test
    void historyWithItemTreeValidTest(){
        HistoryWithItemTree h = RMObjectTestHelper.historyWithItemTree();
        s.serializeHistory(h);
        h = s.deserializeHistoryWithItemTree();

        testValidHistoryWithItemTree(h);
    }

    @Test
    void historyWithItemSingleValidTest(){
        RMObject.HistoryWithItemSingle h = RMObjectTestHelper.historyWithItemSingle();
        s.serializeHistory(h);
        h = s.deserializeHistoryWithItemSingle();

        testValidHistoryWithItemSingle(h);
    }

    @Test
    void historyWithItemTableValidTest(){
        RMObject.HistoryWithItemTable h = RMObjectTestHelper.historyWithItemTable();
        s.serializeHistory(h);
        h = s.deserializeHistoryWithItemTable();

        testValidHistoryWithItemTable(h);
    }

    @Test
    void historyWithItemTreeNullOriginTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHistoryWithItemTree(
                    RMObjectTestHelper.dataStructure(),
                    null,
                    RMObjectTestHelper.eventWithItemTreeList(false),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void historyWithItemTreeEmptyEventsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHistoryWithItemTree(
                    RMObjectTestHelper.dataStructure(),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.eventWithItemTreeList(true),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.itemStructure());
        });
    }
    @Test
    void historyWithItemTreeEmptyEventsNullSummaryTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHistoryWithItemTree(
                    RMObjectTestHelper.dataStructure(),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.eventWithItemTreeList(true),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.dvDuration(),
                    null);
        });
    }

    @Test
    void historyWithItemSingleNullOriginTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHistoryWithItemSingle(
                    RMObjectTestHelper.dataStructure(),
                    null,
                    RMObjectTestHelper.eventWithItemSingleList(false),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void historyWithItemSingleEmptyEventsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHistoryWithItemSingle(
                    RMObjectTestHelper.dataStructure(),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.eventWithItemSingleList(true),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.itemStructure());
        });
    }
    @Test
    void historyWithItemSingleEmptyEventsNullSummaryTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHistoryWithItemSingle(
                    RMObjectTestHelper.dataStructure(),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.eventWithItemSingleList(true),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.dvDuration(),
                    null);
        });
    }

    @Test
    void historyWithItemTableNullOriginTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHistoryWithItemTable(
                    RMObjectTestHelper.dataStructure(),
                    null,
                    RMObjectTestHelper.eventWithItemTableList(false),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void historyWithItemTableEmptyEventsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHistoryWithItemTable(
                    RMObjectTestHelper.dataStructure(),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.eventWithItemTableList(true),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.itemStructure());
        });
    }
    @Test
    void historyWithItemTableEmptyEventsNullSummaryTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newHistoryWithItemTable(
                    RMObjectTestHelper.dataStructure(),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.eventWithItemTableList(true),
                    RMObjectTestHelper.dvDuration(),
                    RMObjectTestHelper.dvDuration(),
                    null);
        });
    }
}
