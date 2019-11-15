package com.github.kyriosdata.healthcodec.datatypes.uri;

import com.github.kyriosdata.healthcodec.RMObject.DVURI;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvURITest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvURI(DVURI d){
         assertEquals("value", d.getValue());
    }

    @Test
    void dvURIValidTest(){
        DVURI d = RMObjectTestHelper.dVURI();
        s.serializeDVURI(d);
        d = s.deserializeDVURI();

        testValidDvURI(d);
    }

    @Test
    void dvURIEmptyValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDVURI("");
        });
    }

    @Test
    void dvURINullValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDVURI(null);
        });
    }
}
