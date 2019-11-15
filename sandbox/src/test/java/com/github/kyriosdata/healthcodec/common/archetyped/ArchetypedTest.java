package com.github.kyriosdata.healthcodec.common.archetyped;

import com.github.kyriosdata.healthcodec.RMObject.Archetyped;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ArchetypeIDTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.TemplateIDTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArchetypedTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidArchetyped(Archetyped a){
        ArchetypeIDTest.testValidArchetypeID(a.getArchetypeId());
        TemplateIDTest.testValidTemplateID(a.getTemplateId());
        assertEquals("rmVersion", a.getRmVersion());
    }

    @Test
    void archetypedValidTrue(){
        Archetyped a = RMObjectTestHelper.archetyped();
        s.serializeArchetyped(a);
        a = s.deserializeArchetyped();

        testValidArchetyped(a);
    }

    @Test
    void archetypedArchetypeIDNullTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newArchetyped(null,
                    RMObjectTestHelper.templateID(), "rmVersion");
        });
    }

    @Test
    void archetypedRmVersionEmptyTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newArchetyped(RMObjectTestHelper.archetypeID(),
                    RMObjectTestHelper.templateID(),"");
        });
    }
}
