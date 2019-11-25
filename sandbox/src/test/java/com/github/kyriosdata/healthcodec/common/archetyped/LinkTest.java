package com.github.kyriosdata.healthcodec.common.archetyped;

import com.github.kyriosdata.healthcodec.RMObject.Link;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.DvTextTest;
import com.github.kyriosdata.healthcodec.datatypes.uri.DvEHRURITest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinkTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidLink(Link l){
        DvTextTest.testValidDvText(l.getMeaning());
        DvTextTest.testValidDvText(l.getType());
        DvEHRURITest.testValidDvEHRURI(l.getTarget());
    }

    @Test
    void linkValidTest(){
        Link l = RMObjectTestHelper.link();
        s.serializeLink(l);
        l = s.deserializeLink();

        testValidLink(l);
    }

    @Test
    void linkNullMeaning(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newLink(null,
                    RMObjectTestHelper.dvText(),
                    RMObjectTestHelper.dvEHRURI());
        });
    }

    @Test
    void linkNullTypeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newLink(RMObjectTestHelper.dvText(),
                    null,
                    RMObjectTestHelper.dvEHRURI());
        });
    }

    @Test
    void linkNullTargetTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newLink(RMObjectTestHelper.dvText(),
                    RMObjectTestHelper.dvText(),
                    null);
        });
    }
}
