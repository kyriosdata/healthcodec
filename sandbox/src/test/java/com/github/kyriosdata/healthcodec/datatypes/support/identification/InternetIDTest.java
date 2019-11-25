package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.InternetID;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InternetIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidInternetID(InternetID i){
        assertEquals("openehr", i.getUid().getValue());
    }

    @Test
    void internetIDValidTest(){
        InternetID i = RMObjectTestHelper.internetID();
        s.serializeInternetID(i);
        i = s.deserializeInternetID();

        testValidInternetID(i);
    }

    @Test
    void internetIDInvalidValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newInternetID("000");
        });
    }
}
