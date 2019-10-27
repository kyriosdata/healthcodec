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

    /**
     * Testes para ArchetypeID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ArchetypeID() throws UnsupportedEncodingException {
        ArchetypeID archetypeId = RMObjectTestHelper.ArchetypeID(false);
        s.serializeArchetypeID(archetypeId);
        archetypeId = s.deserializeArchetypeID();

        assertEquals("value", archetypeId.getObjectID().getValue());
    }

    @Test
    public void ArchetypeIDException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.ArchetypeID(true);
        });
    }

    /**
     * Testes para ObjectVersionID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ObjectVersionID() throws UnsupportedEncodingException {
        ObjectVersionID oid = RMObjectTestHelper.ObjectVersionID(false);
        s.serializeObjectVersionID(oid);
        oid = s.deserializeObjectVersionID();

        assertEquals("value", oid.getUIDBasedID().getValue());
    }

    @Test
    public void ObjectVersionIDException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.ObjectVersionID(true);
        });
    }

    /**
     * Testes para HierObjectID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void HierObjectID() throws UnsupportedEncodingException {
        HierObjectID hid = RMObjectTestHelper.HierObjectID(false);
        s.serializeHierObjectID(hid);
        hid = s.deserializeHierObjectID();

        assertEquals("value", hid.getUIDBasedID().getValue());
    }

    @Test
    public void HierObjectIDException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.HierObjectID(true);
        });
    }

    /**
     * Testes para HierObjectID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ObjectID() throws UnsupportedEncodingException {
        ObjectID oid = RMObjectTestHelper.ObjectID(false);
        s.serializeObjectID(oid);
        oid = s.deserializeObjectID();

        assertEquals("value", oid.getValue());
    }

    @Test
    public void ObjectIDException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.ObjectID(true);
        });
    }

    /**
     * Testes para PartyRef
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void PartyRef() throws UnsupportedEncodingException {
        PartyRef pr = RMObjectTestHelper.PartyRef();
        s.serializePartyRef(pr);
        pr = s.deserializePartyRef();

        assertEquals(pr.getObjectRef().getId().getValue(), "value");
        assertEquals(pr.getObjectRef().getNamespace(), "DEMOGRAPHIC");
        assertEquals(pr.getObjectRef().getType(), "type");
    }

    /**
     * Testes para ObjectRef
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ObjectRef() throws UnsupportedEncodingException {
        ObjectRef or = RMObjectTestHelper.ObjectRef(false);
        s.serializeObjectRef(or);
        or = s.deserializeObjectRef();

        assertEquals("value", or.getId().getValue());
        assertEquals("namespace", or.getNamespace());
        assertEquals("type", or.getType());
    }

    @Test
    public void ObjectRefException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.ObjectRef(true);
        });
    }

    /**
     * Testes para LocatableRef
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void LocatableRef() throws UnsupportedEncodingException {
        LocatableRef lr = RMObjectTestHelper.LocatableRef(false);
        s.serializeLocatableRef(lr);
        lr = s.deserializeLocatableRef();

        assertEquals("value", lr.getObjectRef().getId().getValue());
        assertEquals("namespace", lr.getObjectRef().getNamespace());
        assertEquals("type", lr.getObjectRef().getType());
        assertEquals("path", lr.getPath());
    }

    @Test
    public void LocatableRefException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.LocatableRef(true);
        });
    }

    /**
     * Testes para PropotionKind
     */
    @Test
    public void ProportionKind(){
        ProportionKind pk = RMObjectTestHelper.ProportionKind();
        s.serializeProportionKind(pk);
        pk = s.deserializeProportionKind();

        assertEquals(1, pk.getValue());
    }

    /**
     * Testes para AccessGroupRef
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void AccessGroupRef() throws UnsupportedEncodingException {
        AccessGroupRef ag = RMObjectTestHelper.AccessGroupRef(false);
        s.serializeAccessGroupRef(ag);
        ag = s.deserializeAccessGroupRef();

        assertEquals("value", ag.getObjectRef().getId().getValue());
        assertEquals("ACCESS_CONTROL",
                ag.getObjectRef().getNamespace());
        assertEquals("ACCESS_GROUP", ag.getObjectRef().getType());
    }

    @Test
    public void AccessGroupRefException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.AccessGroupRef(true);
        });
    }

    /**
     * Testes para PartyIdentified
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void PartyIdentified() throws UnsupportedEncodingException {
        PartyIdentified pi = RMObjectTestHelper.PartyIdentified(
                false, false,
                false);

        s.serializePartyIdentified(pi);
        pi = s.deserializePartyIdentified();
        //externalRef
        assertEquals("value", pi.getExternalRef().getObjectRef().
                getId().getValue());
        assertEquals("DEMOGRAPHIC", pi.getExternalRef().
                getObjectRef().getNamespace());
        assertEquals("value", pi.getExternalRef().
                getObjectRef().getType());
        //name
        assertEquals("name", pi.getName());
        //identifiers
        assertEquals("issuer", pi.getIdentifiers().get(0).getIssuer());
        assertEquals("issuer", pi.getIdentifiers().get(1).getIssuer());
        assertEquals("issuer", pi.getIdentifiers().get(2).getIssuer());

        assertEquals("assigner", pi.getIdentifiers().
                get(0).getAssigner());
        assertEquals("assigner", pi.getIdentifiers().
                get(1).getAssigner());
        assertEquals("assigner", pi.getIdentifiers().
                get(2).getAssigner());

        assertEquals("id", pi.getIdentifiers().get(0).getId());
        assertEquals("id", pi.getIdentifiers().get(1).getId());
        assertEquals("id", pi.getIdentifiers().get(2).getId());

        assertEquals("type", pi.getIdentifiers().get(0).getType());
        assertEquals("type", pi.getIdentifiers().get(1).getType());
        assertEquals("type", pi.getIdentifiers().get(2).getType());
    }

    @Test
    public void PartyIdentifiedPartyRefException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.PartyIdentified(true,
                    false, false);
        });
    }

    @Test
    public void PartyIdentifiedNameException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.PartyIdentified(false,
                    true, false);
        });
    }

    @Test
    public void PartyIdentifiedListException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.PartyIdentified(false,
                    false, true);
        });
    }

    @Test
    public void Archetyped() throws UnsupportedEncodingException {
        Archetyped a = RMObjectTestHelper.Archetyped(
                false, false);
        s.serializeArchetyped(a);
        a = s.deserializeArchetyped();

        assertEquals("value", a.getArchetypeId().
                getObjectID().getValue());
        assertEquals("value", a.getTemplateId().
                getObjectID().getValue());
        assertEquals("rmVersion", a.getRmVersion());
    }

    @Test
    public void ArchetypedArchetypeIDException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.Archetyped(true,
                    false);
        });
    }

    @Test
    public void ArchetypedRmVersionException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.Archetyped(false,
                    true);
        });
    }
}