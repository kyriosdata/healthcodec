package com.github.kyriosdata.healthcodec.datatypes.quantity.datetime;

import com.github.kyriosdata.healthcodec.RMObject.DvDate;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DvDateTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvDate(DvDate d){
        assertTrue(d.isDayKnown());
        assertFalse(d.isMonthKnown());
        assertTrue(d.isPartial());
        DvTemporalTest.testValidDvTemporal(d.getDvTemporal());
    }

    @Test
    void dvDateValidTest(){
        DvDate d = RMObjectTestHelper.dvDate();
        s.serializeDvDate(d);
        d = s.deserializeDvDate();

        testValidDvDate(d);
    }
}
