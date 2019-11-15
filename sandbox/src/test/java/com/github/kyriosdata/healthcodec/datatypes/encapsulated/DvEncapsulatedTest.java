package com.github.kyriosdata.healthcodec.datatypes.encapsulated;

import com.github.kyriosdata.healthcodec.RMObject.DvEncapsulated;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.CodePhraseTest;
import org.junit.jupiter.api.Test;

public class DvEncapsulatedTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvEncapsulated(DvEncapsulated d){
        CodePhraseTest.testValidCodePhrase(d.getCharset());
        CodePhraseTest.testValidCodePhrase(d.getLanguage());
    }

    @Test
    void dvEncapsulatedValidTest(){
        DvEncapsulated d = RMObjectTestHelper.dvEncapsulated();
        s.serializeDvEncapsulated(d);
        d = s.deserializeDvEncapsulated();

        testValidDvEncapsulated(d);
    }
}
