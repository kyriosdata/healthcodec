package com.github.kyriosdata.healthcodec.composition.ehr;

import com.github.kyriosdata.healthcodec.RMObject.EHRStatus;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import com.github.kyriosdata.healthcodec.common.generic.PartySelfTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EHRStatusTest {

    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidEHRStatus(EHRStatus e){
        LocatableTest.testValidLocatable(e.getLocatable());
        PartySelfTest.testValidPartySelf(e.getSubject());
        assertTrue(e.isQueryable());
        assertFalse(e.isModifiable());
        ItemStructureTest.testValidItemStructure(e.getOtherDetails());
    }

    @Test
    void ehrStatusValidTest(){
        EHRStatus e = RMObjectTestHelper.ehrStatus();
        s.serializeEHRStatus(e);
        e = s.deserializeEHRStatus();

        testValidEHRStatus(e);
    }

    @Test
    void ehrStatusNullLocatableTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHRStatus(null,
                    RMObjectTestHelper.partySelf(), true,
                    false, RMObjectTestHelper.itemStructure());
        });
    }

    @Test
    void ehrStatusNullSubjectTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHRStatus(RMObjectTestHelper.locatable(),
                    null, true,
                    false, RMObjectTestHelper.itemStructure());
        });
    }
}
