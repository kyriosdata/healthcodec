package com.github.kyriosdata.healthcodec.datatypes.uri;

import com.github.kyriosdata.healthcodec.RMObject.DVEHRURI;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvEHRURITest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvEHRURI(DVEHRURI d){
        DvURITest.testValidDvURI(d.getDvuri());
    }

    @Test
    void dvEHRURIValidTest(){
        DVEHRURI d = RMObjectTestHelper.dVEHRURI();
        s.serializeDVEHRURI(d);
        d = s.deserializeDVEHRURI();

        testValidDvEHRURI(d);
    }

    @Test
    void dvEHRURINullValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDVEHRURI(null);
        });
    }

    @Test
    void dvEHRURIEmptyValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDVEHRURI("");
        });
    }
}
