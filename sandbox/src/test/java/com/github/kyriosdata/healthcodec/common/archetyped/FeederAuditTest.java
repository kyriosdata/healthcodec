package com.github.kyriosdata.healthcodec.common.archetyped;

import com.github.kyriosdata.healthcodec.RMObject.FeederAudit;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.basic.DvIdentifierTest;
import com.github.kyriosdata.healthcodec.datatypes.encapsulated.DvEncapsulatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FeederAuditTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidFeederAudit(FeederAudit f){
        FeederAuditDetailsTest.testValidFeederAuditDetails(
                f.getFeederSystemAudit());
        DvIdentifierTest.testValidDvIdentifier(f.getFeederSystemItemIDs().get(0));
        FeederAuditDetailsTest.testValidFeederAuditDetails(
                f.getOriginatingSystemAudit());
        DvIdentifierTest.testValidDvIdentifier(
                f.getOriginatingSystemItemIDs().get(0));
        DvEncapsulatedTest.testValidDvEncapsulated(f.getOriginalContent());
    }

    @Test
    void feederAuditValidTest(){
        FeederAudit f = RMObjectTestHelper.feederAudit();
        s.serializeFeederAudit(f);
        f = s.deserializeFeederAudit();

        testValidFeederAudit(f);
    }

    @Test
    void feederAuditNullOriginatingSystemAuditTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newFeederAudit(
                    null,
                    RMObjectTestHelper.dvIdentifierList(false),
                    RMObjectTestHelper.feederAuditDetails(),
                    RMObjectTestHelper.dvIdentifierList(false),
                    RMObjectTestHelper.dvEncapsulated());
        });
    }

    @Test
    void feederAuditEmptyoriginatingSystemItemIDsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newFeederAudit(
                    RMObjectTestHelper.feederAuditDetails(),
                    RMObjectTestHelper.dvIdentifierList(true),
                    RMObjectTestHelper.feederAuditDetails(),
                    RMObjectTestHelper.dvIdentifierList(false),
                    RMObjectTestHelper.dvEncapsulated());
        });
    }

    @Test
    void feederAuditNullFeederSystemItemIDsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newFeederAudit(
                    RMObjectTestHelper.feederAuditDetails(),
                    RMObjectTestHelper.dvIdentifierList(false),
                    RMObjectTestHelper.feederAuditDetails(),
                    RMObjectTestHelper.dvIdentifierList(true),
                    RMObjectTestHelper.dvEncapsulated());
        });
    }
}
