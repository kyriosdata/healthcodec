package com.github.kyriosdata.healthcodec.datatypes.quantity;

import com.github.kyriosdata.healthcodec.RMObject.ReferenceRange;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.datatypes.text.DvTextTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReferenceRangeTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidReferenceRange(ReferenceRange referenceRange){
        DvTextTest.testValidDvText(referenceRange.getMeaning());
        assertEquals(null, referenceRange.getRange().getInterval().getLower());
        assertEquals(null, referenceRange.getRange().getInterval().getUpper());
    }
}
