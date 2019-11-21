package com.github.kyriosdata.healthcodec.composition.content;

import com.github.kyriosdata.healthcodec.RMObject.ContentItem;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContentItemTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidContentItem(ContentItem c){
        LocatableTest.testValidLocatable(c.getLocatable());
    }

    @Test
    void contentItemValidTest(){
        ContentItem c = RMObjectTestHelper.contentItem();
        s.serializeContentItem(c);
        c = s.deserializeContentItem();

        testValidContentItem(c);
    }

    @Test
    void contentItemNullLocatableTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newContentItem(null);
        });
    }
}
