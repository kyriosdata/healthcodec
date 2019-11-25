package com.github.kyriosdata.healthcodec.datatypes.basic;

import com.github.kyriosdata.healthcodec.RMObject.DvState;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.DvCodedTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvStateTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    @Test
    void dvStateValidInstance(){
        DvState d = RMObjectFactory.newDvState(
                RMObjectTestHelper.dvCodedText(), "terminal");
        s.serializeDvState(d);
        d = s.deserializaDvState();

        DvCodedTextTest.testValidDvCodedText(d.getValue());
        assertEquals("terminal", d.getTerminal());
    }

    @Test
    void dvStateNullValueTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvState(null, "terminal");
        });
    }
}
