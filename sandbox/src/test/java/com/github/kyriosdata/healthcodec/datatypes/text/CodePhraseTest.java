package com.github.kyriosdata.healthcodec.datatypes.text;

import com.github.kyriosdata.healthcodec.RMObject.CodePhrase;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.TerminologyIDTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CodePhraseTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidCodePhrase(CodePhrase c){
        TerminologyIDTest.testValidTerminologyID(c.getTerminologyID());
        assertEquals("codeString", c.getCodeString());
    }

    @Test
    void codePhraseValidTest(){
        CodePhrase c = RMObjectTestHelper.codePhrase();
        s.serializeCodePhrase(c);
        c = s.deserializeCodePhrase();

        testValidCodePhrase(c);
    }

    @Test
    void codePhraseNullTerminologyIDTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newCodePhrase(null,
                    "codeString");
        });
    }

    @Test
    void codePhraseEmptyCodeStringTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newCodePhrase(RMObjectTestHelper.terminologyID(),
                    "");
        });
    }
}
