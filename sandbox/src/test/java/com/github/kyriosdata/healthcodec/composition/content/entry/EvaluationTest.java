package com.github.kyriosdata.healthcodec.composition.content.entry;

import com.github.kyriosdata.healthcodec.RMObject.Evaluation;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EvaluationTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidEvaluation(Evaluation e){
        CareEntryTest.testValidCareEntry(e.getCareEntry());
        ItemStructureTest.testValidItemStructure(e.getData());
    }

    @Test
    void adminEntryTestValid(){
        Evaluation e = RMObjectTestHelper.evaluation();
        s.serializeEvaluation(e);
        e = s.deserializeEvaluation();

        testValidEvaluation(e);
    }

    @Test
    void evaluationEntryNullDataTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEvaluation(RMObjectTestHelper.careEntry(),
                    null);
        });
    }
}
