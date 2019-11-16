package com.github.kyriosdata.healthcodec.datatypes.quantity;

import com.github.kyriosdata.healthcodec.RMObject.DvQuantity;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvQuantityTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvQuantity(DvQuantity d) {
        DvAmountTest.testValidDvAmount(d.getDvAmount());
        assertEquals("units", d.getUnits());
        assertEquals(5.000000, d.getMagnitude());
        assertEquals(2, d.getPrecision());
    }

    @Test
    void dvQuantityValidTest(){
        DvQuantity d = RMObjectTestHelper.dvQuantity();
        s.serializeDvQuantity(d);
        d = s.deserializeDvQuantity();

        testValidDvQuantity(d);
    }

    @Test
    void dvQuantityNegativePrecisionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvQuantity(RMObjectTestHelper.dvAmount(),
                    "units", 5.000000, -2);
        });
    }
}
