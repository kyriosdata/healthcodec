package com.github.kyriosdata.healthcodec.datatypes.quantity;

import com.github.kyriosdata.healthcodec.RMObject.DvInterval;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

public class DvIntervalTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvInterval(DvInterval dvInterval){
        DvOrderedTest.testValidDvOrdered(dvInterval.getInterval().getLower());
        DvOrderedTest.testValidDvOrdered(dvInterval.getInterval().getUpper());
    }

    @Test
    void dvIntervalValidTest(){
        DvInterval d = RMObjectTestHelper.dvInterval();
        s.serializeDvInterval(d);
        d = s.deserializeDvInterval();

        testValidDvInterval(d);
    }
}
