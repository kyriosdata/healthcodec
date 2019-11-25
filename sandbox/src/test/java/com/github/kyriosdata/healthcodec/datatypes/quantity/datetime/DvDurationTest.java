package com.github.kyriosdata.healthcodec.datatypes.quantity.datetime;

import com.github.kyriosdata.healthcodec.RMObject.DvDuration;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.quantity.DvAmountTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DvDurationTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvDuration(DvDuration d){
        DvAmountTest.testValidDvAmount(d.getDvAmount());
        assertEquals("value", d.getValue());
    }

    @Test
    void dvDurationTestValid(){
        DvDuration d = RMObjectTestHelper.dvDuration();
        s.serializeDvDuration(d);
        d = s.deserializeDvDuration();

        testValidDvDuration(d);
    }
}
