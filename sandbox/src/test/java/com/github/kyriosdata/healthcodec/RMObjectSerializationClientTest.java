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

    /**
     * Testes sobre DvBoolean
     */
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

    /**
     * Testes sobre DvIdentifier
     * @throws UnsupportedEncodingException
     */
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
            RMObjectTestHelper.DvIdentifier(true);
        });
    }

    /**
     * Testes sobre UID
     * @throws UnsupportedEncodingException
     */
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
            RMObjectTestHelper.UID(true);
        });
    }

    /**
     * Testes sobre InternetID
     * @throws UnsupportedEncodingException
     */
    @Test
    public void InternetID() throws UnsupportedEncodingException {
        InternetID internetId = RMObjectTestHelper.InternetID(false);
        s.serializeInternetID(internetId);
        internetId = s.deserializeInternetID();

        assertEquals("openehr", internetId.getUid().getValue());
    }

    @Test
    public void InternetIDException() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.InternetID(true);
        });
    }

    /**
     * Teste sobre ISO_OID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ISOOID() throws UnsupportedEncodingException {
        ISO_OID isooid = RMObjectTestHelper.ISOOID();
        s.serializeISOOID(isooid);
        isooid = s.deserializeISOOID();

        assertEquals("value", isooid.getUid().getValue());
    }

    /**
     * Teste sobre UUID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void UUID() throws UnsupportedEncodingException {
        UUID uuid = RMObjectTestHelper.UUID();
        s.serializeUUID(uuid);
        uuid = s.deserializeUUID();

        assertEquals("value", uuid.getUid().getValue());
    }

    /**
     * Testes sobre GenericID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void GenericID() throws UnsupportedEncodingException {
        GenericID genericID = RMObjectTestHelper.GenericID(false);
        s.serializeGenericID(genericID);
        genericID = s.deserializeGenericID();

        assertEquals("value", genericID.getObjectID().getValue());
        assertEquals("scheme", genericID.getScheme());
    }

    @Test
    public void GenericIDException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.GenericID(true);
        });
    }
}