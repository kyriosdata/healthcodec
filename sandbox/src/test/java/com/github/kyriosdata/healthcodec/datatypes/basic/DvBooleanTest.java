package com.github.kyriosdata.healthcodec.datatypes.basic;

import com.github.kyriosdata.healthcodec.RMObject.DvBoolean;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DvBooleanTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    @Test
    void dvBooleanTrueTest(){
        DvBoolean d = RMObjectFactory.newDvBoolean(true);
        s.serializeDvBoolean(d);
        d = s.deserializeDvBoolean();

        assertTrue(d.getValue());
    }

    @Test
    void dvBooleanFalseTest(){
        DvBoolean d = RMObjectFactory.newDvBoolean(false);
        s.serializeDvBoolean(d);
        d = s.deserializeDvBoolean();

        assertFalse(d.getValue());
    }
}
