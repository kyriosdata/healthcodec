package com.github.kyriosdata.healthcodec.datatypes.basic;

import com.github.kyriosdata.healthcodec.RMObject.DvIdentifier;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DvIdentifierTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDvIdentifier(DvIdentifier d) {
        assertEquals("issuer", d.getIssuer());
        assertEquals("assigner", d.getAssigner());
        assertEquals("id", d.getId());
        assertEquals("type", d.getType());
    }

    @Test
    void dvIdentifierValidInstanceTest() {
        DvIdentifier d = RMObjectTestHelper.dvIdentifier();
        s.serializeDvIdentifier(d);
        d = s.deserializeDvIdentifier();

        testValidDvIdentifier(d);
    }

    @Test
    void dvIdentifierEmptyIssuerTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvIdentifier("", "assigner",
                    "id", "type");
        });
    }

    @Test
    void dvIdentifierEmptyAssignerTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvIdentifier("issuer", "",
                    "id", "type");
        });
    }

    @Test
    void dvIdentifierEmptyIdTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvIdentifier("issuer", "assigner",
                    "", "type");
        });
    }

    @Test
    void dvIdentifierEmptyTypeTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvIdentifier("issuer", "assigner",
                    "id", "");
        });
    }

    @Test
    void dvIdentifierNullIssuerTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvIdentifier(null, "assigner",
                    "id", "type");
        });
    }

    @Test
    void dvIdentifierNullAssignerTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvIdentifier("issuer", null,
                    "id", "type");
        });
    }

    @Test
    void dvIdentifierNullIdTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newDvIdentifier("issuer", "assigner",
                    null, "type");
        });
    }
}
