package com.github.kyriosdata.healthcodec.composition.ehr;

import com.github.kyriosdata.healthcodec.RMObject.EHRAccess;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EHRAccessTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidEHRAccess(EHRAccess e){
        LocatableTest.testValidLocatable(e.getLocatable());
    }

    @Test
    void ehrAccessValidTest(){
        EHRAccess e = RMObjectTestHelper.ehrAccess();
        s.serializeEHRAccess(e);
        e = s.deserializeEHRAccess();

        testValidEHRAccess(e);
    }

    @Test
    void ehrAccessNullLocatableTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHRAccess(null);
        });
    }

}
