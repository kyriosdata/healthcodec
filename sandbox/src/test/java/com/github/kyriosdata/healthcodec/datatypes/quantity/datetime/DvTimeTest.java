package com.github.kyriosdata.healthcodec.datatypes.quantity.datetime;

import com.github.kyriosdata.healthcodec.RMObject.DvTime;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DvTimeTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvTime(DvTime d){
        assertTrue(d.isPartial());
        assertFalse(d.isMinuteKnown());
        assertTrue(d.isSecondKnown());
        assertFalse(d.isFractionalSecKnown());
        DvTemporalTest.testValidDvTemporal(d.getDvTemporal());
    }

    @Test
    void dvTimeValidTest(){
        DvTime d = RMObjectTestHelper.dvTime();
        s.serializeDvTime(d);
        d = s.deserializeDvTime();

        testValidDvTime(d);
    }
}
