package com.github.kyriosdata.healthcodec.common.resource;

import com.github.kyriosdata.healthcodec.RMObject.AuthoredResource;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.generic.RevisionHistoryTest;
import com.github.kyriosdata.healthcodec.datatypes.text.CodePhraseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthoredResourceTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidAuthoredResource(AuthoredResource a){
        CodePhraseTest.testValidCodePhrase(a.getOriginalLanguage());
        TranslationDetailsTest.testValidTranslationDetails(
                a.getTranslations().get("key1"));
        TranslationDetailsTest.testValidTranslationDetails(
                a.getTranslations().get("key2"));
        TranslationDetailsTest.testValidTranslationDetails(
                a.getTranslations().get("key3"));
        RevisionHistoryTest.testValidRevisionHistory(
                a.getRevisionHistory());
        assertTrue(a.isControlled());
    }

    @Test
    void authoredResourceValidTest(){
        AuthoredResource a = RMObjectTestHelper.authoredResource();
        s.serializeAuthoredResource(a);
        a = s.deserializeAuthoredResource();

        testValidAuthoredResource(a);
    }
}
