package com.github.kyriosdata.healthcodec.datatypes.quantity;

import com.github.kyriosdata.healthcodec.RMObject.DvOrdinal;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.DvCodedTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DvOrdinalTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvOrdinal(DvOrdinal d){
        DvOrderedTest.testValidDvOrdered(d.getOtherReferenceRanges().get(0).
                getRange().getInterval().getLower());
        DvOrderedTest.testValidDvOrdered(d.getOtherReferenceRanges().get(0).
                getRange().getInterval().getUpper());
        DvIntervalTest.testValidDvInterval(d.getNormalRange());
        assertEquals(10, d.getValue());
        DvCodedTextTest.testValidDvCodedText(d.getSymbol());
    }

    @Test
    void dvOrdinalValidTest(){
        DvOrdinal d = RMObjectTestHelper.dvOrdinal();
        s.serializeDvOrdinal(d);
        d = s.deserializeDvOrdinal();

        testValidDvOrdinal(d);
    }
}

