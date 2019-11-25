package com.github.kyriosdata.healthcodec.datatypes.quantity;

import com.github.kyriosdata.healthcodec.RMObject.DvCount;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DvCountTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvCount(DvCount d){
        DvAmountTest.testValidDvAmount(d.getDvAmount());
        assertEquals(10, d.getMagnitude());
    }

    @Test
    void dvCountValidTest(){
        DvCount d = RMObjectTestHelper.dvCount();
        s.serializeDvCount(d);
        d = s.deserializeDvCount();

        testValidDvCount(d);
    }
}
