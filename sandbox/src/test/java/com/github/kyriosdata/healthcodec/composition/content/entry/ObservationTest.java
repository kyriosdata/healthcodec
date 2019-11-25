package com.github.kyriosdata.healthcodec.composition.content.entry;

import com.github.kyriosdata.healthcodec.RMObject;
import com.github.kyriosdata.healthcodec.RMObject.ObservationWithItemSingleItemTree;
import com.github.kyriosdata.healthcodec.RMObject.ObservationWithItemTreeItemSingle;
import com.github.kyriosdata.healthcodec.RMObject.ObservationWithItemTreeItemTable;
import com.github.kyriosdata.healthcodec.RMObject.ObservationWithItemTreeItemTree;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.history.HistoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObservationTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidObservationWithItemTreeItemTree(
            ObservationWithItemTreeItemTree o){
        CareEntryTest.testValidCareEntry(o.getCareEntry());
        HistoryTest.testValidHistoryWithItemTree(o.getData());
        assertEquals(null, o.getState());
    }

    public static void testValidObservationWithItemTreeItemSingle(
            ObservationWithItemTreeItemSingle o){
        CareEntryTest.testValidCareEntry(o.getCareEntry());
        HistoryTest.testValidHistoryWithItemTree(o.getData());
        assertEquals(null, o.getState());
    }

    public static void testValidObservationWithItemTreeItemTable(
            ObservationWithItemTreeItemTable o){
        CareEntryTest.testValidCareEntry(o.getCareEntry());
        HistoryTest.testValidHistoryWithItemTree(o.getData());
        assertEquals(null, o.getState());
    }

    public static void testValidObservationWithItemSingleItemTree(
            ObservationWithItemSingleItemTree o){
        CareEntryTest.testValidCareEntry(o.getCareEntry());
        HistoryTest.testValidHistoryWithItemSingle(o.getData());
        assertEquals(null, o.getState());
    }

    public static void testValidObservationWithItemSingleItemSingle(
            RMObject.ObservationWithItemSingleItemSingle o){
        CareEntryTest.testValidCareEntry(o.getCareEntry());
        HistoryTest.testValidHistoryWithItemSingle(o.getData());
        assertEquals(null, o.getState());
    }

    public static void testValidObservationWithItemSingleItemTable(
            RMObject.ObservationWithItemSingleItemTable o){
        CareEntryTest.testValidCareEntry(o.getCareEntry());
        HistoryTest.testValidHistoryWithItemSingle(o.getData());
        assertEquals(null, o.getState());
    }

    public static void testValidObservationWithItemTableItemTable(
            RMObject.ObservationWithItemTableItemTable o){
        CareEntryTest.testValidCareEntry(o.getCareEntry());
        HistoryTest.testValidHistoryWithItemTable(o.getData());
        assertEquals(null, o.getState());
    }

    public static void testValidObservationWithItemTableItemSingle(
            RMObject.ObservationWithItemTableItemSingle o){
        CareEntryTest.testValidCareEntry(o.getCareEntry());
        HistoryTest.testValidHistoryWithItemTable(o.getData());
        assertEquals(null, o.getState());
    }

    public static void testValidObservationWithItemTableItemTree(
            RMObject.ObservationWithItemTableItemTree o){
        CareEntryTest.testValidCareEntry(o.getCareEntry());
        HistoryTest.testValidHistoryWithItemTable(o.getData());
        assertEquals(null, o.getState());
    }

    @Test
    void observationWithItemTreeItemTreeValidTest(){
        ObservationWithItemTreeItemTree o = RMObjectTestHelper.observationWithItemTreeItemTree();
        s.serializeObservation(o);
        o = s.deserializeObservationWithItemTreeItemTree();

        testValidObservationWithItemTreeItemTree(o);
    }

    @Test
    void observationWithItemTreeItemSingleValidTest(){
        ObservationWithItemTreeItemSingle o = RMObjectTestHelper.observationWithItemTreeItemSingle();
        s.serializeObservation(o);
        o = s.deserializeObservationWithItemTreeItemSingle();

        testValidObservationWithItemTreeItemSingle(o);
    }

    @Test
    void observationWithItemTreeItemTableValidTest(){
        ObservationWithItemTreeItemTable o = RMObjectTestHelper.observationWithItemTreeItemTable();
        s.serializeObservation(o);
        o = s.deserializeObservationWithItemTreeItemTable();

        testValidObservationWithItemTreeItemTable(o);
    }

    @Test
    void observationWithItemSingleItemTreeValidTest(){
        ObservationWithItemSingleItemTree o = RMObjectTestHelper.observationWithItemSingleItemTree();
        s.serializeObservation(o);
        o = s.deserializeObservationWithItemSingleItemTree();

        testValidObservationWithItemSingleItemTree(o);
    }

    @Test
    void observationWithItemSingleItemSingleValidTest(){
        RMObject.ObservationWithItemSingleItemSingle o = RMObjectTestHelper.observationWithItemSingleItemSingle();
        s.serializeObservation(o);
        o = s.deserializeObservationWithItemSingleItemSingle();

        testValidObservationWithItemSingleItemSingle(o);
    }

    @Test
    void observationWithItemSingleItemTableValidTest(){
        RMObject.ObservationWithItemSingleItemTable o = RMObjectTestHelper.observationWithItemSingleItemTable();
        s.serializeObservation(o);
        o = s.deserializeObservationWithItemSingleItemTable();

        testValidObservationWithItemSingleItemTable(o);
    }

    @Test
    void observationWithItemTableItemTableValidTest(){
        RMObject.ObservationWithItemTableItemTable o = RMObjectTestHelper.observationWithItemTableItemTable();
        s.serializeObservation(o);
        o = s.deserializeObservationWithItemTableItemTable();

        testValidObservationWithItemTableItemTable(o);
    }

    @Test
    void observationWithItemTableItemSingleValidTest(){
        RMObject.ObservationWithItemTableItemSingle o = RMObjectTestHelper.observationWithItemTableItemSingle();
        s.serializeObservation(o);
        o = s.deserializeObservationWithItemTableItemSingle();

        testValidObservationWithItemTableItemSingle(o);
    }

    @Test
    void observationWithItemTreeTableTreeValidTest(){
        RMObject.ObservationWithItemTableItemTree o = RMObjectTestHelper.observationWithItemTableItemTree();
        s.serializeObservation(o);
        o = s.deserializeObservationWithItemTableItemTree();

        testValidObservationWithItemTableItemTree(o);
    }
}
