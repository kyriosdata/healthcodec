package com.github.kyriosdata.healthcodec.composition.content.entry;

import com.github.kyriosdata.healthcodec.RMObject.Activity;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datatypes.encapsulated.DvParsableTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ActivityTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidActivity(Activity a){
        System.out.println(a.getLocatable().getUid().getValue());
        LocatableTest.testValidLocatable(a.getLocatable());
        ItemStructureTest.testValidItemStructure(a.getDescription());
        DvParsableTest.testDvParsableValid(a.getTiming());
        assertEquals("actionArchetypeId", a.getActionArchetypeId());
    }

    @Test
    void activityValidTest(){
        Activity a = RMObjectTestHelper.activity();
        s.serializeActivity(a);
        a = s.deserializeActivity();

        testValidActivity(a);
    }

    @Test
    void activityNullDescriptionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newActivity(RMObjectTestHelper.locatable(),
                    null,
                    RMObjectTestHelper.dvParsable(),
                    "actionArchetypeId");
        });
    }

    @Test
    void activityEmptyActionArchetypeIdTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newActivity(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.itemStructure(),
                    RMObjectTestHelper.dvParsable(),
                    "");
        });
    }
}
