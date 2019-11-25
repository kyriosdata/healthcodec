package com.github.kyriosdata.healthcodec.common.changecontrol;

import com.github.kyriosdata.healthcodec.RMObject.Contribution;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.generic.AuditDetailsTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectIDTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectRefTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContributionTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidContribution(Contribution c){
        ObjectIDTest.testValidObjectID(c.getUid());
        ObjectRefTest.testValidObjectRef(c.getVersions().iterator().next());
        AuditDetailsTest.testValidAuditDetails(c.getAudit());
    }

    @Test
    void contributionTestValid(){
        Contribution c = RMObjectTestHelper.contribution();
        s.serializeContribution(c);
        c = s.deserializeContribution();

        testValidContribution(c);
    }

    @Test
    void contributionNullUidTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newContribution(null,
                    RMObjectTestHelper.objectRefSet(false),
                    RMObjectTestHelper.auditDetails());
        });
    }

    @Test
    void contributionNullAuditTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newContribution(RMObjectTestHelper.objectID(),
                    RMObjectTestHelper.objectRefSet(false),
                    null);
        });
    }

    @Test
    void contributionEmptyDescriptionOfAuditTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newContribution(RMObjectTestHelper.objectID(),
                    RMObjectTestHelper.objectRefSet(false),
                    RMObjectFactory.newAuditDetails("timePosition",
                            RMObjectTestHelper.partyProxy(),
                            RMObjectTestHelper.dvDateTime(),
                            RMObjectTestHelper.dvCodedText(),
                            null));
        });
    }

    @Test
    void contributionEmptyVersionsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newContribution(RMObjectTestHelper.objectID(),
                    RMObjectTestHelper.objectRefSet(true),
                    RMObjectTestHelper.auditDetails());
        });
    }

    @Test
    void contributionNullVersionsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newContribution(RMObjectTestHelper.objectID(),
                    null,
                    RMObjectTestHelper.auditDetails());
        });
    }
}
