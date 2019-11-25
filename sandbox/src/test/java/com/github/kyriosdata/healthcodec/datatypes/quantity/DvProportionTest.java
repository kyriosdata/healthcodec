package com.github.kyriosdata.healthcodec.datatypes.quantity;

import com.github.kyriosdata.healthcodec.RMObject.ProportionKind;
import com.github.kyriosdata.healthcodec.RMObject.DvProportion;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvProportionTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvProportion(DvProportion d){
        DvAmountTest.testValidDvAmount(d.getDvAmount());
        assertEquals(5.000000, d.getNumerator());
        assertEquals(2.000000, d.getDenominator());
        assertEquals(ProportionKind.FRACTION, d.getType());
        assertEquals(2, d.getPrecision());
    }

    @Test
    void dvProportionValidTest(){
        DvProportion d = RMObjectTestHelper.dvProportion();
        s.serializeDvProportion(d);
        d = s.deserializeDvProportion();

        testValidDvProportion(d);
    }

    @Test
    void dvProportionNullTypeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvProportion(RMObjectTestHelper.dvAmount(),
                    5.000000, 2.000000, null, 2);
        });
    }

    @Test
    void dvProportionUnitaryDenominatorExceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvProportion(RMObjectTestHelper.dvAmount(),
                    5.000000, 2.000000,
                    ProportionKind.UNITARY, 2);
        });
    }

    @Test
    void dvProportionPercentDenominatorExceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvProportion(RMObjectTestHelper.dvAmount(),
                    5.000000, 2.000000,
                    ProportionKind.PERCENT, 2);
        });
    }

    @Test
    void dvProportionFractionDenominatorExceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvProportion(RMObjectTestHelper.dvAmount(),
                    5.000000, 2.700000,
                    ProportionKind.FRACTION, 2);
        });
    }

    @Test
    void dvProportionIntegerlFractionDenominatorExceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvProportion(RMObjectTestHelper.dvAmount(),
                    5.000000, 2.700000,
                    ProportionKind.INTEGER_FRACTION, 2);
        });
    }
}
