package com.github.kyriosdata.healthcodec.composition.content.entry;

import com.github.kyriosdata.healthcodec.RMObject.ISMTransition;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.DvCodedTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ISMTransitionTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidISMTransition(ISMTransition i){
        DvCodedTextTest.testValidDvCodedText(i.getCareflowStep());
        DvCodedTextTest.testValidDvCodedText(i.getCurrentState());
        DvCodedTextTest.testValidDvCodedText(i.getTransition());
    }

    @Test
    void ismTransitionValidTest(){
        ISMTransition i = RMObjectTestHelper.iSMTransition();
        s.serializeISMTransition(i);
        i = s.deserializeISMTransition();

        testValidISMTransition(i);
    }

    @Test
    void ismTransitionNullCurrentStateTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newISMTransition(null,
                    RMObjectTestHelper.dvCodedText(),
                    RMObjectTestHelper.dvCodedText());
        });
    }
}
