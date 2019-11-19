package com.github.kyriosdata.healthcodec.common.generic;

import com.github.kyriosdata.healthcodec.RMObject.Attestation;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.encapsulated.DvMultimediaTest;
import com.github.kyriosdata.healthcodec.datatypes.text.DvTextTest;
import com.github.kyriosdata.healthcodec.datatypes.uri.DvEHRURITest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AttestationTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testvalidAttestation(Attestation a){
        AuditDetailsTest.testValidAuditDetails(a.getAuditDetails());
        DvMultimediaTest.testValidDvMultimedia(a.getAttestedView());
        assertEquals("proof", a.getProof());
        DvEHRURITest.testValidDvEHRURI(a.getItems().iterator().next());
        DvTextTest.testValidDvText(a.getReason());
        assertTrue(a.isPending());
    }

    @Test
    void attestationTestValid(){
        Attestation a = RMObjectTestHelper.attestation();
        s.serializeAttestation(a);
        a = s.deserializeAttestation();

        testvalidAttestation(a);
    }

    @Test
    void attestationEmptyItemsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newAttestation(RMObjectTestHelper.auditDetails(),
                    RMObjectTestHelper.dvMultimedia(), "proof",
                    RMObjectTestHelper.dvehruriSet(true),
                    RMObjectTestHelper.dvText(), true);
        });
    }

    @Test
    void attestationNullReasonTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newAttestation(RMObjectTestHelper.auditDetails(),
                    RMObjectTestHelper.dvMultimedia(), "proof",
                    RMObjectTestHelper.dvehruriSet(false),
                    null, true);
        });
    }
}
