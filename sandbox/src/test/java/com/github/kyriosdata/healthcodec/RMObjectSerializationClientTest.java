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
     *2
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

    /**
     * Teste sobre TemplateID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void TemplateID() throws UnsupportedEncodingException {
        TemplateID templateId = RMObjectTestHelper.TemplateID();
        s.serializeTemplateID(templateId);
        templateId = s.deserializeTemplateID();

        assertEquals("value", templateId.getObjectID().getValue());
    }

    /**
     * Teste sobre TerminologyID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void TerminologyID() throws UnsupportedEncodingException {
        TerminologyID terminologyID = RMObjectTestHelper.TerminologyID();
        s.serializeTerminologyID(terminologyID);
        terminologyID = s.deserializeTerminologyID();

        assertEquals("name", terminologyID.getName());
        assertEquals("version", terminologyID.getVersion());
        assertEquals("name(version)",
                terminologyID.getObjectID().getValue());
    }

    /**
     * Testes de CodePhrase
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void CodePhrase() throws UnsupportedEncodingException {
        CodePhrase codePhrase = RMObjectTestHelper.CodePhrase(false);
        s.serializeCodePhrase(codePhrase);
        codePhrase = s.deserializeCodePhrase();

        assertEquals("name", codePhrase.getTerminologyID().getName());
        assertEquals("version",
                codePhrase.getTerminologyID().getVersion());
        assertEquals("name(version)",
                codePhrase.getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", codePhrase.getCodeString());
    }

    @Test
    public void CodePhraseException() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.CodePhrase(true);
        });
    }

    /**
     * Testes para DVURI
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DVURI() throws UnsupportedEncodingException {
        DVURI dvuri = RMObjectTestHelper.DVURI(false);
        s.serializeDVURI(dvuri);
        dvuri = s.deserializeDVURI();

        assertEquals("value", dvuri.getValue());
    }

    @Test
    public void DVURIException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DVURI(true);
        });
    }

    /**
     * Testes para DVEHRURI
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DVEHRURI() throws UnsupportedEncodingException {
        DVEHRURI dvehruri = RMObjectTestHelper.DVEHRURI(false);
        s.serializeDVEHRURI(dvehruri);
        dvehruri = s.deserializeDVEHRURI();

        assertEquals("value", dvehruri.getDvuri().getValue());
    }

    @Test
    public void DVEHRURIException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DVEHRURI(true);
        });
    }

    /**
     * Testes para VersionTreeID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void VersionTreeID() throws UnsupportedEncodingException {
        VersionTreeID vid = RMObjectTestHelper.VersionTreeID(false);
        s.serializeVersionTreeID(vid);
        vid = s.deserializeVersionTreeID();

        assertEquals("value", vid.getValue());
    }

    @Test
    public void VersionTreeIDException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.VersionTreeID(true);
        });
    }
}