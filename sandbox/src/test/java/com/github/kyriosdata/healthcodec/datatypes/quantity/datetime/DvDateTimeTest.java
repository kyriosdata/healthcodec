package com.github.kyriosdata.healthcodec.datatypes.quantity.datetime;

import com.github.kyriosdata.healthcodec.RMObject.DvDateTime;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DvDateTimeTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvDateTime(DvDateTime d){
        assertTrue(d.isPartial());
        assertFalse(d.isMinuteKnown());
        assertTrue(d.isSecondKnown());
        assertFalse(d.isFractionalSecKnown());
        DvTemporalTest.testValidDvTemporal(d.getDvTemporal());
        DvDateTest.testValidDvDate(d.getDateTime());
    }

    @Test
    void testValidDvDateTime(){
        DvDateTime d = RMObjectTestHelper.dvDateTime();
        s.serializeDvDateTime(d);
        d = s.deserializeDvDateTime();

        testValidDvDateTime(d);
    }
}
