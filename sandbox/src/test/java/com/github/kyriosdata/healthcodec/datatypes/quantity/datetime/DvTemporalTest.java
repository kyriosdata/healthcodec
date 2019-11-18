package com.github.kyriosdata.healthcodec.datatypes.quantity.datetime;

import com.github.kyriosdata.healthcodec.RMObject.DvTemporal;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.quantity.DvAbsoluteQuantityTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DvTemporalTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvTemporal(DvTemporal dvTemporal){
        DvAbsoluteQuantityTest.testValidDvAbsoluteQuantity(
                dvTemporal.getDvAbsoluteQuantity());
        assertEquals("HH:mm:ss", dvTemporal.getValue());
    }

    @Test
    void dvTemporalTestValid(){
        DvTemporal d = RMObjectTestHelper.dvTemporal();
        s.serializeDvTemporal(d);
        d = s.deserializeDvTemporal();

        testValidDvTemporal(d);
    }

}
