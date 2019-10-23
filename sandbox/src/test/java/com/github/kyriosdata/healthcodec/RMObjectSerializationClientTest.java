package com.github.kyriosdata.healthcodec;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static com.github.kyriosdata.healthcodec.RMObject.*;
import static org.junit.jupiter.api.Assertions.*;

class RMObjectSerializationClientTest {

    private RMObjectSerializationClient s = null;

    @BeforeEach
    void setUp() {
        s = RMObjectSerializationClient.create();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void DvBooleanTestTrue() {
        DvBoolean dvBoolean = RMObjectTestHelper.DvBoolean(true);
        s.serializeDvBoolean(dvBoolean);
        dvBoolean = s.deserializeDvBoolean();

        assertTrue(dvBoolean.getValue());
    }

    @Test
    public void DvBooleanTestFalse() {
        DvBoolean dvBoolean = RMObjectTestHelper.DvBoolean(false);
        s.serializeDvBoolean(dvBoolean);
        dvBoolean = s.deserializeDvBoolean();

        assertFalse(dvBoolean.getValue());
    }

    @Test
    public void DvIdentifier() throws UnsupportedEncodingException {
        DvIdentifier dvIdentifier =
                RMObjectTestHelper.DvIdentifier(false);
        s.serializeDvIdentifier(dvIdentifier);
        dvIdentifier = s.deserializeDvIdentifier();

        assertEquals("issuer", dvIdentifier.getIssuer());
        assertEquals("assigner", dvIdentifier.getAssigner());
        assertEquals("id", dvIdentifier.getId());
        assertEquals("type", dvIdentifier.getType());
    }

    @Test
    public void DvIdentifierException()  {
        assertThrows(IllegalArgumentException.class, () -> {
            DvIdentifier dvIdentifier =
                    RMObjectTestHelper.DvIdentifier(true);
        });
    }

    @Test
    public void UID() throws UnsupportedEncodingException {
        UID uid = RMObjectTestHelper.UID(false);
        s.serializeUID(uid);
        uid = s.deserializeUID();

        assertEquals("value", uid.getValue());
    }

    @Test
    public void UIDException() {
        assertThrows(IllegalArgumentException.class, () -> {
            UID uid = RMObjectTestHelper.UID(true);
        });
    }

}