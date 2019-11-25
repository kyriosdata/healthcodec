package com.github.kyriosdata.healthcodec.datatypes.uri;

import com.github.kyriosdata.healthcodec.RMObject.DvEHRURI;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvEHRURITest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvEHRURI(DvEHRURI d){
        DvURITest.testValidDvURI(d.getDvuri());
    }

    @Test
    void DvEHRURIValidTest(){
        DvEHRURI d = RMObjectTestHelper.dvEHRURI();
        s.serializeDvEHRURI(d);
        d = s.deserializeDvEHRURI();

        testValidDvEHRURI(d);
    }

    @Test
    void DvEHRURINullValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvEHRURI(null);
        });
    }

    @Test
    void DvEHRURIEmptyValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvEHRURI("");
        });
    }
}
