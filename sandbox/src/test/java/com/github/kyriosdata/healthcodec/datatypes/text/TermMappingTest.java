package com.github.kyriosdata.healthcodec.datatypes.text;

import com.github.kyriosdata.healthcodec.RMObject;
import com.github.kyriosdata.healthcodec.RMObject.TermMapping;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TermMappingTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidTermMapping(TermMapping t){
        CodePhraseTest.testValidCodePhrase(t.getTarget());
        assertEquals(RMObject.Match.BROADER, t.getMatch());
        DvCodedTextTest.testValidDvCodedText(t.getPurpose());
    }

    @Test
    void termMappingValidTest(){
        TermMapping t = RMObjectTestHelper.termMapping();
        s.serializeTermMapping(t);
        t = s.deserializeTermMapping();

        testValidTermMapping(t);
    }

    @Test
    void termMappingNullTargetTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newTermMapping(null, RMObject.Match.BROADER,
                    RMObjectTestHelper.dvCodedText());
        });
    }

    @Test
    void termMappingNullMatchTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newTermMapping(RMObjectTestHelper.codePhrase(),
                    null, RMObjectTestHelper.dvCodedText());
        });
    }
}
