package com.github.kyriosdata.healthcodec.common.archetyped;

import com.github.kyriosdata.healthcodec.RMObject.Link;
import com.github.kyriosdata.healthcodec.RMObject.Locatable;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.text.DvTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocatableTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidLocatable(Locatable l){
        assertEquals("value", l.getUid().getValue());
        assertEquals("value", l.getArchetypeNodeId());
        DvTextTest.testValidDvText(l.getName());
        ArchetypedTest.testValidArchetyped(l.getArchetypeDetails());
        FeederAuditTest.testValidFeederAudit(l.getFeederAudit());

        Link li = l.getLinks().iterator().next();
        LinkTest.testValidLink(li);
    }

    @Test
    void locatableValidTest(){
        Locatable l = RMObjectTestHelper.locatable();
        s.serializeLocatable(l);
        l = s.deserializeLocatable();

        testValidLocatable(l);
    }

    @Test
    void locatableNullArchetypeNodeIdTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newLocatable(RMObjectTestHelper.uIDBasedID(),
                    null,
                    RMObjectTestHelper.dvText(),
                    RMObjectTestHelper.archetyped(),
                    RMObjectTestHelper.feederAudit(),
                    RMObjectTestHelper.linkSet(false));
        });
    }

    @Test
    void locatableNullNameTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newLocatable(RMObjectTestHelper.uIDBasedID(),
                    "value",
                    null,
                    RMObjectTestHelper.archetyped(),
                    RMObjectTestHelper.feederAudit(),
                    RMObjectTestHelper.linkSet(false));
        });
    }

    @Test
    void locatableEmptyLinksTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newLocatable(RMObjectTestHelper.uIDBasedID(),
                    "value",
                    RMObjectTestHelper.dvText(),
                    RMObjectTestHelper.archetyped(),
                    RMObjectTestHelper.feederAudit(),
                    RMObjectTestHelper.linkSet(true));
        });
    }
}
