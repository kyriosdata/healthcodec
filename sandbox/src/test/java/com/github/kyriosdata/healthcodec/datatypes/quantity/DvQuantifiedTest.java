package com.github.kyriosdata.healthcodec.datatypes.quantity;

import com.github.kyriosdata.healthcodec.RMObject.DvQuantified;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DvQuantifiedTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvQuantified(DvQuantified dvQuantified){
        DvOrderedTest.testValidDvOrdered(dvQuantified.getDvOrdered());
        assertEquals("magnitudeStatus", dvQuantified.getMagnitudeStatus());
    }

    @Test
    void dvQuantifiedValidTest(){
        DvQuantified d = RMObjectTestHelper.dvQuantified();
        s.serializeDvQuantified(d);
        d = s.deserializeDvQuantified();

        testValidDvQuantified(d);
    }

}
