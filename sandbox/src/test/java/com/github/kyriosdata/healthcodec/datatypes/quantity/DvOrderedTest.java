package com.github.kyriosdata.healthcodec.datatypes.quantity;

import com.github.kyriosdata.healthcodec.RMObject.DvOrdered;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.CodePhraseTest;
import org.junit.jupiter.api.Test;

public class DvOrderedTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvOrdered(DvOrdered d){
        ReferenceRangeTest.testValidReferenceRange(d.getOtherReferenceRanges().
                get(0));
        CodePhraseTest.testValidCodePhrase(d.getNormalStatus());
    }

    @Test
    void dvOrderedValidTest(){
        DvOrdered d = RMObjectTestHelper.dvOrdered();
        s.serializeDvOrdered(d);
        d = s.deserializeDvOrdered();

        testValidDvOrdered(d);
    }
}
