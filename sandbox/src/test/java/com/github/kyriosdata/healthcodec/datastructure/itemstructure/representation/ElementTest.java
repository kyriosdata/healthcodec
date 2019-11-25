package com.github.kyriosdata.healthcodec.datastructure.itemstructure.representation;

import com.github.kyriosdata.healthcodec.RMObject.Element;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.DvCodedTextTest;
import org.junit.jupiter.api.Test;

public class ElementTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidElement(Element e){
        ItemTest.testValidItem(e.getItem());
        DvCodedTextTest.testValidDvCodedText(e.getNullFlavour());
    }

    @Test
    void elementValidTest(){
        Element e = RMObjectTestHelper.element();
        s.serializeElement(e);
        e = s.deserializeElement();

        testValidElement(e);
    }
}
