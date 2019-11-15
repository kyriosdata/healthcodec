package com.github.kyriosdata.healthcodec.common.resource;

import com.github.kyriosdata.healthcodec.RMObject.ResourceDescriptionItem;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.CodePhraseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResourceDescriptionItemTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidResourceDescriptionItem(ResourceDescriptionItem r){
        CodePhraseTest.testValidCodePhrase(r.getLanguage());
        assertEquals("purpose", r.getPurpose());
        assertEquals("value", r.getKeywords().get(0));
        assertEquals("value", r.getKeywords().get(1));
        assertEquals("value", r.getKeywords().get(2));
        assertEquals("use", r.getUse());
        assertEquals("misuse", r.getMisuse());
        assertEquals("copyright", r.getCopyright());
        assertEquals("value", r.getOriginalResourceUri().get("key1"));
        assertEquals("value", r.getOriginalResourceUri().get("key2"));
        assertEquals("value", r.getOriginalResourceUri().get("key3"));
        assertEquals("value", r.getOtherDetails().get("key1"));
        assertEquals("value", r.getOtherDetails().get("key2"));
        assertEquals("value", r.getOtherDetails().get("key3"));
    }

    @Test
    void resourceDescriptionItemValidTest(){
        ResourceDescriptionItem r = RMObjectTestHelper.resourceDescriptionItem();
        s.serializeResourceDescriptionItem(r);
        r = s.deserializeResourceDescriptionItem();

        testValidResourceDescriptionItem(r);
    }

    @Test
    void resourceDescriptionItemEmptyPurposeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newResourceDescriptionItem(
                    RMObjectTestHelper.codePhrase(),
                    "",
                    RMObjectTestHelper.stringList(false),
                    "use",
                    "misuse",
                    "copyright",
                    RMObjectTestHelper.stringStringMap(false),
                    RMObjectTestHelper.stringStringMap(false));
        });
    }

    @Test
    void resourceDescriptionItemEmptyUseTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newResourceDescriptionItem(
                    RMObjectTestHelper.codePhrase(),
                    "purpose",
                    RMObjectTestHelper.stringList(false),
                    "",
                    "misuse",
                    "copyright",
                    RMObjectTestHelper.stringStringMap(false),
                    RMObjectTestHelper.stringStringMap(false));
        });
    }

    @Test
    void resourceDescriptionItemEmptyMisuseTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newResourceDescriptionItem(
                    RMObjectTestHelper.codePhrase(),
                    "purpose",
                    RMObjectTestHelper.stringList(false),
                    "use",
                    "",
                    "copyright",
                    RMObjectTestHelper.stringStringMap(false),
                    RMObjectTestHelper.stringStringMap(false));
        });
    }

    @Test
    void resourceDescriptionItemEmptyCopyrightTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newResourceDescriptionItem(
                    RMObjectTestHelper.codePhrase(),
                    "purpose",
                    RMObjectTestHelper.stringList(false),
                    "use",
                    "misuse",
                    "",
                    RMObjectTestHelper.stringStringMap(false),
                    RMObjectTestHelper.stringStringMap(false));
        });
    }
}
