package com.github.kyriosdata.healthcodec.composition.content.entry;

import com.github.kyriosdata.healthcodec.RMObject.InstructionDetails;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.LocatableRefTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InstructionDetailsTest {

    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidInstructionDetails(InstructionDetails i){
        LocatableRefTest.testValidLocatableRef(i.getInstructionId());
        assertEquals("activityId", i.getActivityId());
        ItemStructureTest.testValidItemStructure(i.getWfDetails());
    }

    @Test
    void instructionDetailsValidTest(){
        InstructionDetails i = RMObjectTestHelper.instructionDetails();
        s.serializeinstructionDetails(i);
        i = s.deserializeInstructionDetails();

        testValidInstructionDetails(i);
    }

    @Test
    void instructionDetailsNullInstructionIdTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newInstructionDetails(null,
                    "activityId", RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void instructionDetailsEmptyActivityIdTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newInstructionDetails(
                    RMObjectTestHelper.locatableRef(),"",
                    RMObjectTestHelper.itemStructure());
        });
    }
}
