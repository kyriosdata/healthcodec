package com.github.kyriosdata.healthcodec.common.archetyped;

import com.github.kyriosdata.healthcodec.RMObject;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArchetypedTest {
    private RMObjectSerializationClient s = null;

    @BeforeEach
    void setUp() {
        s = RMObjectSerializationClient.create();
    }

    @Test
    void archetypedValidTrue(){
        RMObject.Archetyped a = RMObjectTestHelper.archetyped();
        s.serializeArchetyped(a);
        a = s.deserializeArchetyped();

        //testa archetypedId
        assertEquals("value",
                a.getArchetypeId().getObjectID().getValue());

        //testa ObjectID
        assertEquals("value",
                a.getTemplateId().getObjectID().getValue());

        //testa rmVersion
        assertEquals("rmVersion", a.getRmVersion());
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
