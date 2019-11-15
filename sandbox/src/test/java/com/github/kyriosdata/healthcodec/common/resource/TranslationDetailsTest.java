package com.github.kyriosdata.healthcodec.common.resource;

import com.github.kyriosdata.healthcodec.RMObject.TranslationDetails;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.CodePhraseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TranslationDetailsTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidTranslationDetails(TranslationDetails t){
        CodePhraseTest.testValidCodePhrase(t.getLanguage());
        assertEquals("value", t.getOtherDetails().get("key1"));
        assertEquals("value", t.getAuthor().get("key1"));
        assertEquals("value", t.getOtherDetails().get("key2"));
        assertEquals("value", t.getAuthor().get("key2"));
        assertEquals("value", t.getOtherDetails().get("key3"));
        assertEquals("value", t.getAuthor().get("key3"));
        assertEquals("accreditation", t.getAccreditation());
    }

    @Test
    void translationDetailsValidTest(){
        TranslationDetails t = RMObjectTestHelper.translationDetails();
        s.serializeTranslationDetails(t);
        t = s.deserializeTranslationDetails();

        testValidTranslationDetails(t);
    }

    @Test
    void translationDetailsNullLanguageTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newTranslationDetails(
                    null,
                    RMObjectTestHelper.stringStringMap(false),
                    "accreditation",
                    RMObjectTestHelper.stringStringMap(false));
        });
    }

    @Test
    void translationDetailsNullAuthorTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newTranslationDetails(
                    RMObjectTestHelper.codePhrase(),
                    null,
                    "accreditation",
                    RMObjectTestHelper.stringStringMap(false));
        });
    }
}
