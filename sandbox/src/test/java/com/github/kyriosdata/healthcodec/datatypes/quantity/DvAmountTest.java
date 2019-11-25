package com.github.kyriosdata.healthcodec.datatypes.quantity;

import com.github.kyriosdata.healthcodec.RMObject.DvAmount;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DvAmountTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvAmount(DvAmount d){
        DvOrderedTest.testValidDvOrdered(d.getDvOrdered());
        assertEquals(5.000000, d.getAccuracy());
        assertTrue(d.isAccuracyPercent());
    }

    @Test
    void dvAmountValidTest(){
        DvAmount d = RMObjectTestHelper.dvAmount();
        s.serializeDvAmount(d);
        d = s.deserializeDvAmount();

        testValidDvAmount(d);
    }
}
