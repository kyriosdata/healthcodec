package com.github.kyriosdata.healthcodec.datatypes.encapsulated;

import com.github.kyriosdata.healthcodec.RMObject.DvMultimedia;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.CodePhraseTest;
import com.github.kyriosdata.healthcodec.datatypes.uri.DvURITest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DvMultimediaTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvMultimedia(DvMultimedia d){
        byte[] integrityCheck = {0,1,0,1};
        byte[] data = {1,0,1,0};

        DvEncapsulatedTest.testValidDvEncapsulated(d.getDvEncapsulated());
        assertEquals("alternateText", d.getAlternateText());
        CodePhraseTest.testValidCodePhrase(d.getMediaType());
        CodePhraseTest.testValidCodePhrase(d.getCompressionAlgorithm());
        assertArrayEquals(integrityCheck, d.getIntegrityCheck());
        CodePhraseTest.testValidCodePhrase(d.getIntegrityCheckAlgorithm());
        assertEquals(null, d.getThumbnail());
        DvURITest.testValidDvURI(d.getUri());
        assertArrayEquals(data, d.getData());
    }

    @Test
    void dvMultimediaValidTest(){
        DvMultimedia d = RMObjectTestHelper.dvMultimedia();
        s.serializeDvMultimedia(d);
        d = s.deserializeDvMultimedia();

        testValidDvMultimedia(d);
    }

    @Test
    void dvMultimediaNullMediaTypeTest(){
        byte[] integrityCheck = {0,1,0,1};
        byte[] data = {1,0,1,0};
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvMultimedia(
                    RMObjectTestHelper.dvEncapsulated(),
                    "alternateText",
                    null,
                    RMObjectTestHelper.codePhrase(),
                    integrityCheck,
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.dvMultimedia(),
                    RMObjectTestHelper.dVURI(),
                    data);
        });
    }

    @Test
    void dvMultimediaNullCompressionAlgorithmTest(){
        byte[] integrityCheck = {0,1,0,1};
        byte[] data = {1,0,1,0};
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvMultimedia(
                    RMObjectTestHelper.dvEncapsulated(),
                    "alternateText",
                    RMObjectTestHelper.codePhrase(),
                    null,
                    integrityCheck,
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.dvMultimedia(),
                    RMObjectTestHelper.dVURI(),
                    data);
        });
    }

    @Test
    void dvMultimediaNullIntegrityCheckAlgorithmTest(){
        byte[] integrityCheck = {0,1,0,1};
        byte[] data = {1,0,1,0};
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvMultimedia(
                    RMObjectTestHelper.dvEncapsulated(),
                    "alternateText",
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.codePhrase(),
                    integrityCheck,
                    null,
                    RMObjectTestHelper.dvMultimedia(),
                    RMObjectTestHelper.dVURI(),
                    data);
        });
    }

    @Test
    void dvMultimediaNullDataNullUriTest(){
        byte[] integrityCheck = {0,1,0,1};
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvMultimedia(
                    RMObjectTestHelper.dvEncapsulated(),
                    "alternateText",
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.codePhrase(),
                    integrityCheck,
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.dvMultimedia(),
                    null,
                    null);
        });
    }
}
