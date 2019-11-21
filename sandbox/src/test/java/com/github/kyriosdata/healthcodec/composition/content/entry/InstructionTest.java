package com.github.kyriosdata.healthcodec.composition.content.entry;

import com.github.kyriosdata.healthcodec.RMObject.Instruction;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.encapsulated.DvParsableTest;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDateTimeTest;
import com.github.kyriosdata.healthcodec.datatypes.text.DvTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InstructionTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidInstruction(Instruction i){
        CareEntryTest.testValidCareEntry(i.getCareEntry());
        DvTextTest.testValidDvText(i.getNarrative());
        ActivityTest.testValidActivity(i.getActivities().get(0));
        DvDateTimeTest.testValidDvDateTime(i.getExpiryTime());
        DvParsableTest.testDvParsableValid(i.getWfDefinition());
    }

    @Test
    void instructionValidTest(){
        Instruction i = RMObjectTestHelper.instruction();
        s.serializeInstruction(i);
        i = s.deserializeInstruction();

        testValidInstruction(i);
    }

    @Test
    void instructionNullNarrativeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newInstruction(RMObjectTestHelper.careEntry(),
                    null,
                    RMObjectTestHelper.activityList(false),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.dvParsable());
        });
    }

    @Test
    void instructionEmptyActivitiesTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newInstruction(RMObjectTestHelper.careEntry(),
                    RMObjectTestHelper.dvText(),
                    RMObjectTestHelper.activityList(true),
                    RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.dvParsable());
        });
    }
}
