package com.github.kyriosdata.healthcodec.datatypes.text;

import com.github.kyriosdata.healthcodec.RMObject.DvCodedText;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvCodedTextTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvCodedText(DvCodedText d){
        DvTextTest.testValidDvText(d.getDvText());
        CodePhraseTest.testValidCodePhrase(d.getDefiningCode());
    }

    @Test
    void dvCodedTextValidTest(){
        DvCodedText d = RMObjectTestHelper.dvCodedText();
        s.serializeDvCodedText(d);
        d = s.deserializeDvCodedText();

        testValidDvCodedText(d);
    }

    @Test
    void dvCodeTextNullDefiningCode(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvCodedText(RMObjectTestHelper.dvText(),
                    null);
        });
    }
}
