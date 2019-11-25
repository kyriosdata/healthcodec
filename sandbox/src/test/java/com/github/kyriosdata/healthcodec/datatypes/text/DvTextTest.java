package com.github.kyriosdata.healthcodec.datatypes.text;

import com.github.kyriosdata.healthcodec.RMObject.DvText;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.uri.DvURITest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvTextTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvText(DvText d){
        assertEquals("value", d.getValue());
        assertEquals("formatting", d.getFormatting());
        DvURITest.testValidDvURI(d.getHyperlink());
        CodePhraseTest.testValidCodePhrase(d.getCharset());
        CodePhraseTest.testValidCodePhrase(d.getLanguage());
    }

    @Test
    void dvTextValidTest(){
        DvText d = RMObjectTestHelper.dvText();
        s.serializeDvText(d);
        d = s.deserializeDvText();

        testValidDvText(d);
    }

    @Test
    void dvTextEmptyMappingsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvText("value",RMObjectTestHelper.
                    termMappingList(true),"formatting",
                    RMObjectTestHelper.dVURI(),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.codePhrase());
        });
    }

    @Test
    void dvTextEmptyFormattingTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvText("value",RMObjectTestHelper.
                            termMappingList(false),"",
                    RMObjectTestHelper.dVURI(),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.codePhrase());
        });
    }
}
