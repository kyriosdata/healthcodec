package com.github.kyriosdata.healthcodec.composition.content.entry;

import com.github.kyriosdata.healthcodec.RMObject.Action;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDateTimeTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ActionTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidAction(Action a){
        DvDateTimeTest.testValidDvDateTime(a.getTime());
        ItemStructureTest.testValidItemStructure(a.getDescription());
        ISMTransitionTest.testValidISMTransition(a.getIsmTransition());
        InstructionDetailsTest.testValidInstructionDetails(
                a.getInstructionDetails());
    }

    @Test
    void actionValidTest(){
        Action a = RMObjectTestHelper.action();
        s.serializeAction(a);
        a = s.deserializeAction();

        testValidAction(a);
    }

    @Test
    void actionNullTimeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newAction(null,
                    RMObjectTestHelper.itemStructure(),
                    RMObjectTestHelper.iSMTransition(),
                    RMObjectTestHelper.instructionDetails());
        });
    }

    @Test
    void actionNullDescriptionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newAction(RMObjectTestHelper.dvDateTime(),
                    null,
                    RMObjectTestHelper.iSMTransition(),
                    RMObjectTestHelper.instructionDetails());
        });
    }

    @Test
    void actionNullISMTransitionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newAction(RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.itemStructure(),
                    null,
                    RMObjectTestHelper.instructionDetails());
        });
    }
}
