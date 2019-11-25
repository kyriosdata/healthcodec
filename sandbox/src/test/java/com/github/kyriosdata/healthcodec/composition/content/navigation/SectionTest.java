package com.github.kyriosdata.healthcodec.composition.content.navigation;

import com.github.kyriosdata.healthcodec.RMObject.Section;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.composition.content.ContentItemTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SectionTest {

    RMObjectSerializationClient s = RMObjectSerializationClient.create();


    public static void testValidSection(Section s){
        ContentItemTest.testValidContentItem(s.getContentItem());
        ContentItemTest.testValidContentItem(s.getItems().get(0));
    }

    @Test
    void sectionValidTest(){
        Section section = RMObjectTestHelper.section();
        s.serializeSection(section);
        section = s.deserializeSection();

        testValidSection(section);
    }

    @Test
    void sectionNullContentItemTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newSection(null,
                    RMObjectTestHelper.contentItemList(false));
        });
    }

    @Test
    void sectionEmptyItemsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newSection(RMObjectTestHelper.contentItem(),
                    RMObjectTestHelper.contentItemList(true));
        });
    }
}
