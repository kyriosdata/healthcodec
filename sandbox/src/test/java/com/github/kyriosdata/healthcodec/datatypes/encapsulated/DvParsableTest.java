package com.github.kyriosdata.healthcodec.datatypes.encapsulated;

import com.github.kyriosdata.healthcodec.RMObject.DvParsable;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvParsableTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testDvParsableValid(DvParsable d){
        DvEncapsulatedTest.testValidDvEncapsulated(d.getDvEncapsulated());
        assertEquals("value", d.getValue());
        assertEquals("formalism", d.getFormalism());
    }

    @Test
    void dvParsableValidTest(){
        DvParsable d = RMObjectTestHelper.dvParsable();
        s.serializeDvParsable(d);
        d = s.deserializeDvParsable();

        testDvParsableValid(d);
    }

    @Test
    void dvParsableNullValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvParsable(RMObjectTestHelper.dvEncapsulated(),
                    null, "formalism");
        });
    }

    @Test
    void dvParsableEmptyFormalism(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvParsable(RMObjectTestHelper.dvEncapsulated(),
                    "value", "");
        });
    }
}
