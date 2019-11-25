package com.github.kyriosdata.healthcodec.datastructure.history;

import com.github.kyriosdata.healthcodec.RMObject.IntervalEventWithItemTree;
import com.github.kyriosdata.healthcodec.RMObject.IntervalEventWithItemSingle;
import com.github.kyriosdata.healthcodec.RMObject.IntervalEventWithItemTable;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDurationTest;
import com.github.kyriosdata.healthcodec.datatypes.text.DvCodedTextTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntervalEventTest {
    RMObjectSerializationClient s = null;

    @BeforeEach
    void setUp(){
        s = RMObjectSerializationClient.create();
    }

    public static void testValidIntervalEventWithItemTree(
            IntervalEventWithItemTree i){
        EventTest.testValidEventWithItemTree(i.getEvent());
        DvDurationTest.testValidDvDuration(i.getWidth());
        DvCodedTextTest.testValidDvCodedText(i.getMathFunction());
        assertEquals(10, i.getSampleCount());
    }

    public static void testValidIntervalEventWithItemSingle(
            IntervalEventWithItemSingle i){
        EventTest.testValidEventWithItemSingle(i.getEvent());
        DvDurationTest.testValidDvDuration(i.getWidth());
        DvCodedTextTest.testValidDvCodedText(i.getMathFunction());
        assertEquals(10, i.getSampleCount());
    }

    public static void testValidIntervalEventWithItemTable(
            IntervalEventWithItemTable i){
        EventTest.testValidEventWithItemTable(i.getEvent());
        DvDurationTest.testValidDvDuration(i.getWidth());
        DvCodedTextTest.testValidDvCodedText(i.getMathFunction());
        assertEquals(10, i.getSampleCount());
    }

    @Test
    void intervalEventWithItemTreeValidTest(){
        IntervalEventWithItemTree i = RMObjectTestHelper.
                intervalEventWithItemTree();
        s.serializeIntervalEvent(i);
        i = s.deserializeIntervalEventWithItemTree();

        testValidIntervalEventWithItemTree(i);
    }

    @Test
    void intervalEventWithItemTreeNullWidthTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newIntervalEventWithItemTree(
                    RMObjectTestHelper.eventWithItemTree(),
                    null,
                    RMObjectTestHelper.dvCodedText(),
                    10);
        });
    }

    @Test
    void intervalEventWithItemTreeNullMathFunctionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newIntervalEventWithItemTree(
                    RMObjectTestHelper.eventWithItemTree(),
                    RMObjectTestHelper.dvDuration(),
                    null,
                    10);
        });
    }

    @Test
    void intervalEventWithItemSingleValidTest(){
        IntervalEventWithItemSingle i = RMObjectTestHelper.
                intervalEventWithItemSingle();
        s.serializeIntervalEvent(i);
        i = s.deserializeIntervalEventWithItemSingle();

        testValidIntervalEventWithItemSingle(i);
    }

    @Test
    void intervalEventWithItemSingleNullWidthTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newIntervalEventWithItemSingle(
                    RMObjectTestHelper.eventWithItemSingle(),
                    null,
                    RMObjectTestHelper.dvCodedText(),
                    10);
        });
    }

    @Test
    void intervalEventWithItemSingleNullMathFunctionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newIntervalEventWithItemSingle(
                    RMObjectTestHelper.eventWithItemSingle(),
                    RMObjectTestHelper.dvDuration(),
                    null,
                    10);
        });
    }

    @Test
    void intervalEventWithItemTableValidTest(){
        IntervalEventWithItemTable i = RMObjectTestHelper.
                intervalEventWithItemTable();
        s.serializeIntervalEvent(i);
        i = s.deserializeIntervalEventWithItemTable();

        testValidIntervalEventWithItemTable(i);
    }

    @Test
    void intervalEventWithItemTableNullWidthTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newIntervalEventWithItemTable(
                    RMObjectTestHelper.eventWithItemTable(),
                    null,
                    RMObjectTestHelper.dvCodedText(),
                    10);
        });
    }

    @Test
    void intervalEventWithItemTableNullMathFunctionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newIntervalEventWithItemTable(
                    RMObjectTestHelper.eventWithItemTable(),
                    RMObjectTestHelper.dvDuration(),
                    null,
                    10);
        });
    }
}
