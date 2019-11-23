package com.github.kyriosdata.healthcodec.composition.message;

import com.github.kyriosdata.healthcodec.RMObject.MessageContent;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageContentTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidMessageContent(MessageContent m){
        LocatableTest.testValidLocatable(m.getLocatable());
    }

    @Test
    void messageContentValidTest(){
        MessageContent m = RMObjectTestHelper.messageContent();
        s.serializeMessageContent(m);
        m = s.deserializeMessageContent();
    }

    @Test
    void messageContentNullLocatableTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessageContent(null);
        });
    }
}
