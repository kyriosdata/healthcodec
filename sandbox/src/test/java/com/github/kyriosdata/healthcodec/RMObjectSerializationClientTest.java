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
     * Testes para ObjectID
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

    /**
     * Testes para Archetyped
     *
     * @throws UnsupportedEncodingException
     */
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

    /**
     * Testes para DvEncapsulated
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DvEncapsulated() throws UnsupportedEncodingException {
        DvEncapsulated de = RMObjectTestHelper.DvEncapsulated();
        s.serializeDvEncapsulated(de);
        de = s.deserializeDvEncapsulated();

        assertEquals("name(version)",
                de.getCharset().getTerminologyID().getObjectID().getValue());
        assertEquals("version",
                de.getCharset().getTerminologyID().getVersion());
        assertEquals("codeString", de.getCharset().getCodeString());
        assertEquals("name(version)",
                de.getLanguage().getTerminologyID().getObjectID().getValue());
        assertEquals("version",
                de.getLanguage().getTerminologyID().getVersion());
        assertEquals("codeString", de.getLanguage().getCodeString());
    }

    /**
     * Testes para UIDBasedID
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void UIDBasedID() throws UnsupportedEncodingException {
        UIDBasedID uid = RMObjectTestHelper.UIDBasedID(false);
        s.serializeUIDBasedID(uid);
        uid = s.deserializeUIDBasedID();

        assertEquals("value", uid.getValue());
    }

    @Test
    public void UIDBasedIDException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.UIDBasedID(true);
        });
    }

    /**
     * Testes para DvParsable
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DvParsable() throws UnsupportedEncodingException {
        DvParsable dp = RMObjectTestHelper.DvParsable(false,
                false);
        s.serializeDvParsable(dp);
        dp = s.deserializeDvParsable();

        //charset
        assertEquals("name(version)",
                dp.getDvEncapsulated().getCharset().
                        getTerminologyID().getObjectID().getValue());
        assertEquals("version",
                dp.getDvEncapsulated().getCharset().
                        getTerminologyID().getVersion());
        assertEquals("codeString",
                dp.getDvEncapsulated().getCharset().getCodeString());
        //language
        assertEquals("name(version)",
                dp.getDvEncapsulated().getLanguage()
                        .getTerminologyID().getObjectID().getValue());
        assertEquals("version",
                dp.getDvEncapsulated().
                        getLanguage().getTerminologyID().getVersion());
        assertEquals("codeString", dp.getDvEncapsulated().
                getLanguage().getCodeString());
        //value
        assertEquals("value", dp.getValue());

        //formalism
        assertEquals("formalism", dp.getFormalism());
    }

    @Test
    public void DvParsableValueException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvParsable(true, false);
        });
    }

    @Test
    public void DvParsableFormalismException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvParsable(false, true);
        });
    }

    /**
     * Testes para DvTimeSpecification
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DvTimeSpecification() throws UnsupportedEncodingException {
        DvTimeSpecification dt = RMObjectTestHelper.DvTimeSpecification(false);
        s.serializeDvTimeSpecification(dt);
        dt = s.deserializeDvTimeSpecification();

        //charset
        assertEquals("name(version)",
                dt.getValue().getDvEncapsulated().getCharset().
                        getTerminologyID().getObjectID().getValue());
        assertEquals("version",
                dt.getValue().getDvEncapsulated().getCharset().
                        getTerminologyID().getVersion());
        assertEquals("codeString",
                dt.getValue().getDvEncapsulated().getCharset().getCodeString());
        //language
        assertEquals("name(version)",
                dt.getValue().getDvEncapsulated().getLanguage()
                        .getTerminologyID().getObjectID().getValue());
        assertEquals("version",
                dt.getValue().getDvEncapsulated().
                        getLanguage().getTerminologyID().getVersion());
        assertEquals("codeString", dt.getValue().getDvEncapsulated().
                getLanguage().getCodeString());
        //value
        assertEquals("value", dt.getValue().getValue());

        //formalism
        assertEquals("formalism", dt.getValue().getFormalism());
    }

    @Test
    public void DvTimeSpecificationException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvTimeSpecification(true);
        });
    }

    /**
     * Testes para DvMultimedia
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DvMultimedia() throws UnsupportedEncodingException {
        DvMultimedia dm = RMObjectTestHelper.DvMultimedia(
                false, false,
                false, false);
        s.serializeDvMultimedia(dm);
        dm = s.deserializeDvMultimedia();

        //dvEncapsulated
        assertEquals("name(version)",
                dm.getDvEncapsulated().getCharset().
                        getTerminologyID().getObjectID().getValue());
        assertEquals("version",
                dm.getDvEncapsulated().getCharset().
                        getTerminologyID().getVersion());
        assertEquals("codeString", dm.getDvEncapsulated().
                getCharset().getCodeString());
        assertEquals("name(version)",
                dm.getDvEncapsulated().getLanguage().getTerminologyID().
                        getObjectID().getValue());
        assertEquals("version",
                dm.getDvEncapsulated().getLanguage().getTerminologyID().getVersion());
        assertEquals("codeString", dm.getDvEncapsulated().
                getLanguage().getCodeString());

        //alternateText
        assertEquals("alternateText", dm.getAlternateText());

        //mediaType
        assertEquals("name", dm.getMediaType().
                getTerminologyID().getName());
        assertEquals("version",
                dm.getMediaType().getTerminologyID().getVersion());
        assertEquals("name(version)",
                dm.getMediaType().getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", dm.getMediaType().getCodeString());

        //compressionAlgorithm
        assertEquals("name", dm.getCompressionAlgorithm().
                getTerminologyID().getName());
        assertEquals("version",
                dm.getCompressionAlgorithm().getTerminologyID().getVersion());
        assertEquals("name(version)",
                dm.getCompressionAlgorithm().getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", dm.getCompressionAlgorithm().
                getCodeString());

        //integrityCheck
        byte[] integrityCheck = {0,1,0,1};
        assertArrayEquals(integrityCheck, dm.getIntegrityCheck());

        //thumbnail
        assertEquals(null, dm.getThumbnail());

        //uri
        assertEquals("value", dm.getUri().getValue());

        //data
        byte[] data = {1,0,1,0};
        assertArrayEquals(data, dm.getData());
    }

    @Test
    public void DvMultimediaMediaTypeException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvMultimedia(true,
                    false,
                    false, false);
        });
    }

    @Test
    public void DvMultimediaCompressionAlgorithmException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvMultimedia(false,
                    true,
                    false, false);
        });
    }

    @Test
    public void DvMultimediaIntegrityCheckException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvMultimedia(false,
                    false,
                    true, false);
        });
    }

    @Test
    public void DvMultimediaDataException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvMultimedia(false,
                    false,
                    false, true);
        });
    }

    /**
     * Testes para DvText
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DvText() throws UnsupportedEncodingException {
        DvText dt = RMObjectTestHelper.DvText(false,
                false);
        s.serializeDvText(dt);
        dt = s.deserializeDvText();

        //value
        assertEquals("value", dt.getValue());

        //mappings

        //0
        assertEquals("name", dt.getMappings().get(0).getTarget()
                .getTerminologyID().getName());
        assertEquals("version",
                dt.getMappings().get(0).getTarget().
                        getTerminologyID().getVersion());
        assertEquals("name(version)",
                dt.getMappings().get(0).getTarget().
                        getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", dt.getMappings().get(0).
                getTarget().getCodeString());
        assertEquals(">", dt.getMappings().get(0).
                getMatch().getValue());
        assertEquals("value", dt.getMappings().get(0).
                getPurpose().getDvText().getValue());
        assertEquals(null, dt.getMappings().get(0).getPurpose().
                getDvText().getMappings());
        assertEquals("formatting", dt.getMappings().get(0).
                getPurpose().getDvText().getFormatting());
        assertEquals("value", dt.getMappings().get(0).getPurpose().
                getDvText().getHyperlink().getValue());
        assertEquals("name", dt.getMappings().get(0).getPurpose().
                getDvText().getCharset().getTerminologyID().getName());
        assertEquals("version",
                dt.getMappings().get(0).getPurpose().
                        getDvText().getCharset().getTerminologyID().getVersion());
        assertEquals("name(version)",
                dt.getMappings().get(0).getPurpose().
                        getDvText().getCharset().
                        getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", dt.getMappings().get(0).getPurpose().
                getDvText().getCharset().getCodeString());

        //1
        assertEquals("name", dt.getMappings().get(1).getTarget()
                .getTerminologyID().getName());
        assertEquals("version",
                dt.getMappings().get(1).getTarget().
                        getTerminologyID().getVersion());
        assertEquals("name(version)",
                dt.getMappings().get(1).getTarget().
                        getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", dt.getMappings().get(1).
                getTarget().getCodeString());
        assertEquals(">", dt.getMappings().get(1).
                getMatch().getValue());
        assertEquals("value", dt.getMappings().get(1).
                getPurpose().getDvText().getValue());
        assertEquals(null, dt.getMappings().get(1).getPurpose().
                getDvText().getMappings());
        assertEquals("formatting", dt.getMappings().get(1).
                getPurpose().getDvText().getFormatting());
        assertEquals("value", dt.getMappings().get(1).getPurpose().
                getDvText().getHyperlink().getValue());
        assertEquals("name", dt.getMappings().get(1).getPurpose().
                getDvText().getCharset().getTerminologyID().getName());
        assertEquals("version",
                dt.getMappings().get(1).getPurpose().
                        getDvText().getCharset().getTerminologyID().getVersion());
        assertEquals("name(version)",
                dt.getMappings().get(1).getPurpose().
                        getDvText().getCharset().
                        getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", dt.getMappings().get(1).getPurpose().
                getDvText().getCharset().getCodeString());

        //2
        assertEquals("name", dt.getMappings().get(2).getTarget()
                .getTerminologyID().getName());
        assertEquals("version",
                dt.getMappings().get(2).getTarget().
                        getTerminologyID().getVersion());
        assertEquals("name(version)",
                dt.getMappings().get(2).getTarget().
                        getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", dt.getMappings().get(2).
                getTarget().getCodeString());
        assertEquals(">", dt.getMappings().get(2).
                getMatch().getValue());
        assertEquals("value", dt.getMappings().get(2).
                getPurpose().getDvText().getValue());
        assertEquals(null, dt.getMappings().get(2).getPurpose().
                getDvText().getMappings());
        assertEquals("formatting", dt.getMappings().get(2).
                getPurpose().getDvText().getFormatting());
        assertEquals("value", dt.getMappings().get(2).getPurpose().
                getDvText().getHyperlink().getValue());
        assertEquals("name", dt.getMappings().get(2).getPurpose().
                getDvText().getCharset().getTerminologyID().getName());
        assertEquals("version",
                dt.getMappings().get(2).getPurpose().
                        getDvText().getCharset().getTerminologyID().getVersion());
        assertEquals("name(version)",
                dt.getMappings().get(2).getPurpose().
                        getDvText().getCharset().
                        getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", dt.getMappings().get(2).getPurpose().
                getDvText().getCharset().getCodeString());

        //formatting
        assertEquals("formatting", dt.getFormatting());

        //hyperlink
        assertEquals("value", dt.getHyperlink().getValue());

        //language
        assertEquals("name", dt.getLanguage()
                .getTerminologyID().getName());
        assertEquals("version", dt.getLanguage().
                        getTerminologyID().getVersion());
        assertEquals("name(version)", dt.getLanguage().
                        getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", dt.getLanguage().getCodeString());

        //charset
        assertEquals("name", dt.getCharset()
                .getTerminologyID().getName());
        assertEquals("version", dt.getCharset().
                getTerminologyID().getVersion());
        assertEquals("name(version)", dt.getCharset().
                getTerminologyID().getObjectID().getValue());
        assertEquals("codeString", dt.getCharset().getCodeString());
    }

    @Test
    public void DvTextMappingException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvText(true,
                    false);
        });
    }

    @Test
    public void DvTextFormattingException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvText(false,
                    true);
        });
    }

    /**
     * Testes para DvCodedText
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DvCodedText() throws UnsupportedEncodingException {
        DvCodedText dt = RMObjectTestHelper.DvCodedText(false);
        s.serializeDvCodedText(dt);
        dt = s.deserializeDvCodedText();

        assertEquals("value",dt.getDvText().getValue());
        assertEquals("name", dt.getDefiningCode().
                getTerminologyID().getName());
        assertEquals("version",
                dt.getDefiningCode().getTerminologyID().getVersion());
        assertEquals("name(version)",
                dt.getDefiningCode().getTerminologyID().
                        getObjectID().getValue());
        assertEquals("codeString", dt.getDefiningCode().getCodeString());

    }

    @Test
    public void DvCodedTextDefiningCodeException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvCodedText(true);
        });
    }

    /**
     * Testes para Link
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void Link() throws UnsupportedEncodingException {
        Link l = RMObjectTestHelper.Link(
                false,
                false, false);
        s.serializeLink(l);
        l = s.deserializeLink();

        //meaning
        assertEquals("value", l.getMeaning().getValue());

        //type
        assertEquals("value", l.getType().getValue());

        //target
        assertEquals("value", l.getTarget().getDvuri().getValue());
    }

    @Test
    public void LinkMeaningException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.Link(true,
                    false, false);
        });
    }

    @Test
    public void LinkTypeException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.Link(false,
                    true, false);
        });
    }

    @Test
    public void LinkTargetException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.Link(false,
                    false, true);
        });
    }

    /**
     * Testes para DvState
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DvState() throws UnsupportedEncodingException {
        DvState ds = RMObjectTestHelper.DvState(false);
        s.serializeDvState(ds);
        ds = s.deserializaDvState();

        //dvcodedtext
        assertEquals("value", ds.getValue().getDvText().getValue());

        //terminal
        assertEquals("terminal", ds.getTerminal());
    }

    @Test
    public void DvStateException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvState(true);
        });
    }

    /**
     * Testes para DvParagraph
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DvParagraph() throws UnsupportedEncodingException {
        DvParagraph dp = RMObjectTestHelper.DvParagraph(false);
        s.serializeDvParagraph(dp);
        dp = s.deserializeDvParagraph();

        assertEquals("value", dp.getItems().get(0).getValue());
        assertEquals("value", dp.getItems().get(1).getValue());
        assertEquals("value", dp.getItems().get(2).getValue());
    }

    @Test
    public void DvParagraphException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.DvParagraph(true);
        });
    }

    /**
     * Testes para FeederAuditDetails
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void FeederAuditDetails() throws UnsupportedEncodingException {
        FeederAuditDetails fa = RMObjectTestHelper.FeederAuditDetails(
                false);
        s.serializeFeederAuditDetails(fa);
        fa = s.deserializeFeederAuditDetails();

        //systemID
        assertEquals("systemID", fa.getSystemID());

        //provider
        assertEquals("name", fa.getProvider().getName());

        //location
        assertEquals("name", fa.getLocation().getName());

        //subject
        assertEquals("value", fa.getSubject().
                getExternalRef().getObjectRef().getId().getValue());

        assertEquals("versionID", fa.getVersionID());
    }

    @Test
    public void FeederAuditDetailsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.FeederAuditDetails(true);
        });
    }

    /**
     * Testes para FeederAudit
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void FeederAudit() throws UnsupportedEncodingException {
        FeederAudit fa = RMObjectTestHelper.FeederAudit(false,
                false, false);
        s.serializeFeederAudit(fa);
        fa = s.deserializeFeederAudit();

        //originatingSystemAudit
        assertEquals("systemID",
                fa.getOriginatingSystemAudit().getSystemID());
        //originatingSystemItemIDs
        assertEquals("issuer",
                fa.getOriginatingSystemItemIDs().get(0).getIssuer());
        //feederSystemAudit
        assertEquals("systemID",
                fa.getFeederSystemAudit().getSystemID());
        //feederSystemItemIDs
        assertEquals("issuer",
                fa.getFeederSystemItemIDs().get(0).getIssuer());
        //originalContent
        assertEquals("codeString",
                fa.getOriginalContent().getLanguage().getCodeString());
    }

    @Test
    public void FeederAuditOSAEException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.FeederAudit(true,
                    false, false);
        });
    }

    @Test
    public void FeederAuditOSIIException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.FeederAudit(false,
                    true, false);
        });
    }

    @Test
    public void FeederAuditFSIIException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.FeederAudit(false,
                    false, true);
        });
    }

    /**
     * Testes para Locatable
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void Locatable() throws UnsupportedEncodingException {
        Locatable l = RMObjectTestHelper.Locatable(
                false,
                false, false);
        s.serializeLocatable(l);
        l = s.deserializeLocatable();

        //uid
        assertEquals("archetypeNodeId", l.getUid().getValue());

        //archetypeNodeId
        assertEquals("archetypeNodeId", l.getArchetypeNodeId());

        //name
        assertEquals("value", l.getName().getValue());

        //archetypeDetails
        assertEquals("rmVersion", l.getArchetypeDetails().getRmVersion());

        //feederAudit
        assertEquals("systemID", l.getFeederAudit().
                getOriginatingSystemAudit().getSystemID());
        //links
        Link link = l.getLinks().iterator().next();
        assertEquals("value", link.getTarget().getDvuri().getValue());
    }

    @Test
    public void LocatableArchetypeNodeIdException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.Locatable(true,
                    false, false);
        });
    }

    @Test
    public void LocatableNameException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.Locatable(false,
                    true, false);
        });
    }

    @Test
    public void LocatableLinksException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.Locatable(false,
                    false, true);
        });
    }

    /**
     * Testes para PartyRelated
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void PartyRelated() throws UnsupportedEncodingException {
        PartyRelated pr = RMObjectTestHelper.PartyRelated(false);
        s.serializePartyRelated(pr);
        pr = s.deserializePartyRelated();

        //pi
        assertEquals("name", pr.getPi().getName());
        //relationship
        assertEquals("value", pr.
                getRelationship().getDvText().getValue());
    }

    @Test
    public void PartyRelatedException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.PartyRelated(true);
        });
    }

    /**
     * Testes para PartySelf
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void PartySelf() throws UnsupportedEncodingException {
        PartySelf ps = RMObjectTestHelper.PartySelf();
        s.serializePartySelf(ps);
        ps = s.deserializePartySelf();

        assertEquals(ps.getExternalRef().getObjectRef().getId().getValue(),
                "value");
        assertEquals(ps.getExternalRef().getObjectRef().getNamespace(),
                "DEMOGRAPHIC");
        assertEquals(ps.getExternalRef().getObjectRef().getType(),
                "type");
    }

    /**
     * Testes para ResourceDescriptionItem
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ResourceDescriptionItem() throws UnsupportedEncodingException {
        ResourceDescriptionItem rd = RMObjectTestHelper.ResourceDescriptionItem(
                false, false,
                false, false);
        s.serializeResourceDescriptionItem(rd);
        rd = s.deserializeResourceDescriptionItem();

        //language
        assertEquals("name", rd.getLanguage().getTerminologyID().getName());

        //purpose
        assertEquals("purpose", rd.getPurpose());

        //keywords
        assertEquals("value", rd.getKeywords().get(0));
        assertEquals("value", rd.getKeywords().get(1));
        assertEquals("value", rd.getKeywords().get(2));

        //use
        assertEquals("use", rd.getUse());

        //misuse
        assertEquals("misuse", rd.getMisuse());

        //copyright
        assertEquals("copyright", rd.getCopyright());

        //originalResourceUri
        assertEquals("value", rd.getOriginalResourceUri().get("key1"));
        assertEquals("value", rd.getOriginalResourceUri().get("key2"));
        assertEquals("value", rd.getOriginalResourceUri().get("key3"));

        //otherDetails
        assertEquals("value", rd.getOtherDetails().get("key1"));
        assertEquals("value", rd.getOtherDetails().get("key2"));
        assertEquals("value", rd.getOtherDetails().get("key3"));
    }

    @Test
    public void ResourceDescriptionItemPurposeException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.ResourceDescriptionItem(true,
                     false, false,
                    false);
        });
    }

    @Test
    public void ResourceDescriptionItemUseException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.ResourceDescriptionItem(false,
                    true, false,
                    false);
        });
    }

    @Test
    public void ResourceDescriptionItemMisuseException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.ResourceDescriptionItem(false,
                    false, true,
                    false);
        });
    }

    @Test
    public void ResourceDescriptionItemCopyrightException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.ResourceDescriptionItem(false,
                    false, false,
                    true);
        });
    }

    /**
     * Testes para TranslationDetails
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void TranslationDetails() throws UnsupportedEncodingException {
        TranslationDetails td = RMObjectTestHelper.TranslationDetails(
                false, false);
        s.serializeTranslationDetails(td);
        td = s.deserializeTranslationDetails();

        //language
        assertEquals("codeString", td.getLanguage().getCodeString());

        //author
        assertEquals("value", td.getAuthor().get("key1"));
        assertEquals("value", td.getAuthor().get("key2"));
        assertEquals("value", td.getAuthor().get("key3"));

        //accreditation
        assertEquals("accreditation", td.getAccreditation());

        //otherDetails
        assertEquals("value", td.getOtherDetails().get("key1"));
        assertEquals("value", td.getOtherDetails().get("key2"));
        assertEquals("value", td.getOtherDetails().get("key3"));
    }

    @Test
    public void TranslationDetailsLanguageException() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.TranslationDetails(true, false);
        });
    }

    @Test
    public void TranslationDetailsAuthorException() {
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.TranslationDetails(
                    false,true);
        });
    }

    /**
     * Testes para Item
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void Item() throws UnsupportedEncodingException {
        Item i = RMObjectTestHelper.Item();
        s.serializeItem(i);
        i = s.deserializeItem();

        assertEquals("value", i.getLocatable().getName().getValue());
    }

    /**
     * Testes para Cluster
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void Cluster() throws UnsupportedEncodingException {
        Cluster c = RMObjectTestHelper.Cluster();
        s.serializeCluster(c);
        c = s.deserializeCluster();

        //item
        assertEquals("value", c.getItem().getLocatable().getName().getValue());

        //items
        assertEquals("value", c.getItems().get(0).
                getLocatable().getName().getValue());
    }

    /**
     * Testes para Element
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void Element() throws UnsupportedEncodingException {
        Element e = RMObjectTestHelper.Element();
        s.serializeElement(e);
        e = s.deserializeElement();

        //item
        assertEquals("value", e.getItem().getLocatable().
                getName().getValue());

        //nullFlavour
        assertEquals("formatting", e.getNullFlavour().
                getDvText().getFormatting());
    }

    /**
     * Testes para DataStructure
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void DataStrucuture()
            throws UnsupportedEncodingException {
        DataStructure ds = RMObjectTestHelper.DataStructure();
        s.serializeDataStructure(ds);
        ds = s.deserializeDataStructure();

        //locatable
        assertEquals("value", ds.getLocatable().getName().getValue());
    }

    /**
     * Testes para ItemList
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ItemList() throws UnsupportedEncodingException {
        ItemList il = RMObjectTestHelper.ItemList();
        s.serializeItemList(il);
        il = s.deserializeItemList();

        //uid
        assertEquals("value", il.getUid().getValue());

        //archetypeNodeId
        assertEquals("archetypeNodeId", il.getArchetypeNodeId());

        //name
        assertEquals("value", il.getName().getValue());

        //archetypeDetails
        assertEquals("rmVersion",
                il.getArchetypeDetails().getRmVersion());

        //feederAudit
        assertEquals("systemID",
                il.getFeederAudit().getOriginatingSystemAudit().getSystemID());

        //links
        Link l = il.getLinks().iterator().next();
        assertEquals("value", l.getType().getValue());

        //items
        Element e = il.getItems().get(0);
        assertEquals("value", e.getItem().
                getLocatable().getName().getValue());
    }

    /**
     * Testes para ItemStructure
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ItemStructure() throws UnsupportedEncodingException {
        ItemStructure is = RMObjectTestHelper.ItemStructure();
        s.serializeItemStructure(is);
        is = s.deserializeItemStructure();

        //uid
        assertEquals("archetypeNodeId", is.getDataStructure().
                getLocatable().getUid().getValue());
        //...

        //links
        Link l = is.getDataStructure().getLocatable().
                getLinks().iterator().next();
        assertEquals("value", l.getType().getValue());
    }

    /**
     * Testes para ItemSingle
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ItemSingle() throws UnsupportedEncodingException {
        ItemSingle is = RMObjectTestHelper.ItemSingle(false);
        s.serializeItemSingle(is);
        is = s.deserializeItemSingle();

        //item
        assertEquals("value", is.getItem().getItem().
                getLocatable().getName().getValue());

        //itemStructure
        assertEquals("value", is.getItemStructure().
                getDataStructure().getLocatable().getName().getValue());
    }

    @Test
    public void ItemSingleException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.ItemSingle(true);
        });
    }

    /**
     * Testes para ItemTable
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ItemTable() throws UnsupportedEncodingException {
        ItemTable it = RMObjectTestHelper.ItemTable();
        s.serializeItemTable(it);
        it = s.deserializeItemTable();

        //itemStructure
        assertEquals("value", it.getItemStructure().
                getDataStructure().getLocatable().getName().getValue());

        //rows
        assertEquals("value", it.getRows().get(0).getItem().
                getLocatable().getName().getValue());
        assertEquals("value", it.getRows().get(0).getItems().get(0).
                getLocatable().getName().getValue());
    }

    /**
     * Testes para ItemTree
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void ItemTree() throws UnsupportedEncodingException {
        ItemTree it = RMObjectTestHelper.ItemTree();
        s.serializeItemTree(it);
        it = s.deserializeItemTree();

        //itemStructure
        assertEquals("value", it.getItemStructure().getDataStructure().
                getLocatable().getName().getValue());
        //items
        assertEquals("value", it.getItems().get(0).getLocatable().
                getName().getValue());
    }

    /**
     * Testes para PartyIdentity
     * 
     * @throws UnsupportedEncodingException
     */
    @Test
    public void PartyIdentity() throws UnsupportedEncodingException {
        PartyIdentity pi = RMObjectTestHelper.PartyIdentity(false);
        s.serializePartyIdentity(pi);
        pi = s.deserializePartyIdentity();

        //locatable
        assertEquals("value", pi.getLocatable().getName().getValue());

        //details
        assertEquals("value", pi.getDetails().getDataStructure().
                getLocatable().getName().getValue());

    }

    @Test
    public void PartyIdentityException(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectTestHelper.PartyIdentity(true);
        });
    }
}