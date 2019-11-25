package com.github.kyriosdata.healthcodec.datatypes.text;

import com.github.kyriosdata.healthcodec.RMObject.DvParagraph;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvParagraphTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvParagraph(DvParagraph d){
        DvTextTest.testValidDvText(d.getItems().get(0));
    }

    @Test
    void dvParagraphValidTest(){
        DvParagraph d = RMObjectTestHelper.dvParagraph();
        s.serializeDvParagraph(d);
        d = s.deserializeDvParagraph();

        testValidDvParagraph(d);
    }

    @Test
    void dvParagraphNullItemsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvParagraph(null);
        });
    }

    @Test
    void dvParagraphEmptyItemsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvParagraph(RMObjectTestHelper.
                    dvTextList(true));
        });
    }
}
