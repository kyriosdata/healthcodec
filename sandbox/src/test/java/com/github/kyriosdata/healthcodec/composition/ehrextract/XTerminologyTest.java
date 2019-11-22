package com.github.kyriosdata.healthcodec.composition.ehrextract;

import com.github.kyriosdata.healthcodec.RMObject.XTerminology;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class XTerminologyTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidXTerminology(XTerminology t){
        ItemStructureTest.testValidItemStructure(t.getItemStructure());
    }

    @Test
    void xTerminologyValidTest(){
        XTerminology t = RMObjectTestHelper.xTerminology();
        s.serializeXTerminology(t);
        t = s.deserializeXTerminology();

        testValidXTerminology(t);
    }

    @Test
    void xTerminologyNullItemStructureTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newXTerminology(null);
        });
    }
}
