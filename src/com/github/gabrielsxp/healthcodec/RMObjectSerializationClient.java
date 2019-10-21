/*
* Copyright 2019 Instituto de Informática - UFG
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*    http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
 */
package com.github.gabrielsxp.healthcodec;

import com.github.gabrielsxp.healthcodec.RMObjectSerialization.*;
import static com.github.gabrielsxp.healthcodec.RMObjectID.*;
import com.github.gabrielsxp.healthcodec.RMObject.*;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Gabriel 
 * Classe responsável por fornecer os métodos necessários para o
 * cliente serializar e deserializar os atributos das classes do modelo de
 * referência
 */
public class RMObjectSerializationClient implements Serializer, Deserializer {

    //Índice utilizado para armazenar a posição de cada objeto serializado
    private final Index index = new Index();
    //Array auxiliar que auxilia na identificação da ordem de cada objeto
    private final int[] order;
    //Instância do buffer para a utilização das operações de W/R
    private final Buffer buffer = Buffer.serialize();
    //Posição atual para leitura e escrita no buffer
    private int offset;

    /*
    * Construtor privado para ser utilizado na função #link{create}
     */
    RMObjectSerializationClient() {
        this.order = new int[RMObjectID.values().length];
        offset = 0;
    }

    /**
     * Cria uma instância de RMObjectSerializationClient para a utilização de
     * chaining methods. Exemplo: RMObjectSerializationClient.create()
     * .serializeDvBoolean(true) .serializeObjectVersionID("1.01")
     *
     * @return nova instância de RMObjectSerializationClient
     */
    public static RMObjectSerializationClient create() {
        return new RMObjectSerializationClient();
    }

    /**
     * Serializador de DvBoolean
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvBoolean(DvBoolean d) {
        DvBooleanSerializer s = new DvBooleanSerializer();
        register(DVBOOLEAN, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvBoolean
     *
     * @return db instância de DvBoolean
     */
    @Override
    public DvBoolean deserializeDvBoolean() {
        DvBooleanSerializer d = new DvBooleanSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVBOOLEAN));
    }

    /**
     * Serializador de DvIdentifier
     * 
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvIdentifier(
            DvIdentifier d) throws UnsupportedEncodingException {
        DvIdentifierSerializer s = new DvIdentifierSerializer();
        register(DVIDENTIFIER, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvIdentifier
     *
     * @return di instância de DvIdentifier
     */
    @Override
    public DvIdentifier deserializeDvIdentifier() {
        DvIdentifierSerializer d = new DvIdentifierSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVIDENTIFIER));
    }
    
    /**
     * Serializador de UID
     * 
     * @param u
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeUID(
            UID u) throws UnsupportedEncodingException {
        UIDSerializer s = new UIDSerializer();
        register(UID, offset);
        setOffset(s.serialize(buffer, offset, u));
        
        return this;
    }
    
    /**
     * Deserializador de UID
     * 
     * @return nova instância de UID
     */
    @Override
    public UID deserializeUID() {
        UIDSerializer d = new UIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(UID));
    }
    
    /**
     * Serializador de InternetID
     *
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeInternetID(
            InternetID i) throws UnsupportedEncodingException {

        InternetIDSerializer s = new InternetIDSerializer();
        register(INTERNETID, offset);
        setOffset(s.serialize(buffer, offset, i));
        return this;
    }

    /**
     * Deserializador de InternetID
     *
     * @return nova instância de InternetID
     */
    @Override
    public InternetID deserializeInternetID() {
        InternetIDSerializer d = new InternetIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(INTERNETID));
    }

    /**
     * Serializador de ISO_OID
     * 
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeISOOID(ISO_OID i)
            throws UnsupportedEncodingException {
        ISOOIDSerialilzer s = new ISOOIDSerialilzer();
        register(ISO_OID, offset);
        setOffset(s.serialize(buffer, offset, i));

        return this;
    }

    /**
     * Deserializa ISO_OID
     *
     * @return nova instância de ISO_OID
     */
    @Override
    public ISO_OID deserializeISOOID() {
        ISOOIDSerialilzer d = new ISOOIDSerialilzer();
        return d.deserialize(buffer, getOffsetFromID(ISO_OID));
    }

    /**
     * Serializador de UUID
     *
     * @param u
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeUUID(UUID u)
            throws UnsupportedEncodingException {
        UUIDSerializer s = new UUIDSerializer();
        register(UUID, offset);
        setOffset(s.serialize(buffer, offset, u));

        return this;
    }

    /**
     * Deserializador de UUID
     * 
     * @return nova instância de UUID
     */
    @Override
    public UUID deserializeUUID() {
        UUIDSerializer d = new UUIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(UUID));
    }

    /**
     * Serializador de TerminologyID
     *
     * @param t
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeTerminologyID(
            TerminologyID t) throws UnsupportedEncodingException {
        TerminologyIDSerializer s = new TerminologyIDSerializer();
        register(TERMINOLOGYID, offset);
        setOffset(s.serialize(buffer, offset, t));

        return this;
    }

    /**
     * Deserializador de TerminologyID
     *
     * @return nova instância de TerminologyID
     */
    @Override
    public TerminologyID deserializeTerminologyID() {
        TerminologyIDSerializer d = new TerminologyIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(TERMINOLOGYID));
    }

    /**
     * Serializador de GenericID
     *
     * @param g
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeGenericID(
            GenericID g) throws UnsupportedEncodingException {
        GenericIDSerializer s = new GenericIDSerializer();
        register(GENERICID, offset);
        setOffset(s.serialize(buffer, offset, g));

        return this;
    }

    /**
     * Deserializador de GenericID
     *
     * @return gi nova instância de GenericID
     */
    @Override
    public GenericID deserializeGenericID() {
        GenericIDSerializer d = new GenericIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(GENERICID));
    }

    /**
     * Serializador de TemplateID
     *
     * @param t
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeTemplateID(
            TemplateID t) throws UnsupportedEncodingException {
        TemplateIDSerializer s = new TemplateIDSerializer();
        register(TEMPLATEID, offset);
        setOffset(s.serialize(buffer, offset, t));

        return this;
    }

    /**
     * Deserializador de TemplateID
     *
     * @return nova instância de TemplateID
     */
    @Override
    public TemplateID deserializeTemplateID() {
        TemplateIDSerializer d = new TemplateIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(TEMPLATEID));
    }

    /**
     * Serializador de CodePhrase
     *
     * @param c
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeCodePhrase(
            CodePhrase c) throws UnsupportedEncodingException {
        CodePhraseSerializer s = new CodePhraseSerializer();
        register(CODEPHRASE, offset);
        setOffset(s.serialize(buffer, offset, c));

        return this;
    }

    /**
     * Deserializador de CodePhrase
     *
     * @return nova instância de CodePhrase
     */
    @Override
    public CodePhrase deserializeCodePhrase() {
        CodePhraseSerializer d = new CodePhraseSerializer();
        return d.deserialize(buffer, getOffsetFromID(CODEPHRASE));
    }

    /**
     * Serializador de DVURI
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDVURI(
            DVURI d) throws UnsupportedEncodingException {
        DVURISerializer s = new DVURISerializer();
        register(DVURI, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DVURI
     *
     * @return du nova instância de DVURI
     */
    @Override
    public DVURI deserializeDVURI() {
        DVURISerializer d = new DVURISerializer();
        return d.deserialize(buffer, getOffsetFromID(DVURI));
    }

    /**
     * Serializador de DVEHRURI
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDVEHRURI(
            DVEHRURI d) throws UnsupportedEncodingException {
        DVEHRURISerializer s = new DVEHRURISerializer();
        register(DVEHRURI, offset);
        s.serialize(buffer, offset, d);

        return this;
    }

    /**
     * Deserializador de DVEHRURI
     *
     * @return nova instânciade DVEHRURI
     */
    @Override
    public DVEHRURI deserializeDVEHRURI() {
        DVEHRURISerializer d = new DVEHRURISerializer();
        return d.deserialize(buffer, getOffsetFromID(DVEHRURI));
    }

    /**
     * Serializador de VersionTreeID
     *
     * @param v
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeVersionTreeID(
            VersionTreeID v) throws UnsupportedEncodingException {
        VersionTreeIDSerializer s = new VersionTreeIDSerializer();
        register(VERSIONTREEID, offset);
        s.serialize(buffer, offset, v);

        return this;
    }

    /**
     * Deserializador de VersionTreeID
     *
     * @return nova instânciad e VersionTreeID
     */
    @Override
    public VersionTreeID deserializeVersionTreeID() {
        VersionTreeIDSerializer d = new VersionTreeIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(VERSIONTREEID));
    }

    /**
     * Serializador de ArchetypeID
     *
     * @param a
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeArchetypeID(
            ArchetypeID a) throws UnsupportedEncodingException {
        ArchetypeIDSerializer s = new ArchetypeIDSerializer();
        register(ARCHETYPEID, offset);
        s.serialize(buffer, offset, a);

        return this;
    }

    /**
     * Deserializador de ArchetypeID
     *
     * @return ai nova instância de ArchetypeID
     */
    @Override
    public ArchetypeID deserializeArchetypeID() {
        ArchetypeIDSerializer d = new ArchetypeIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(ARCHETYPEID));
    }

    /**
     * Serializador de ObjectVersionID
     *
     * @param o
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeObjectVersionID(
            ObjectVersionID o) throws UnsupportedEncodingException {
        ObjectVersionIDSerializer s = new ObjectVersionIDSerializer();
        register(OBJECTVERSIONID, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }

    /**
     * Deserializador de ObjectVersionID
     *
     * @return nova instância de ObjectVersionID
     */
    @Override
    public ObjectVersionID deserializeObjectVersionID() {
        ObjectVersionIDSerializer d = new ObjectVersionIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(OBJECTVERSIONID));
    }

    /**
     * Serializador de HierObjectID
     *
     * @param h
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeHierObjectID(
            HierObjectID h) throws UnsupportedEncodingException {
        HierObjectIDSerializer s = new HierObjectIDSerializer();
        register(HIEROBJECTID, offset);
        setOffset(s.serialize(buffer, offset, h));

        return this;
    }

    /**
     * Deserializador de HierObjectID
     *
     * @return nova instância de HierObjectID
     */
    @Override
    public HierObjectID deserializeHierObjectID() {
        HierObjectIDSerializer d = new HierObjectIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(HIEROBJECTID));
    }

    /**
     * Serializador de ObjectID
     *
     * @param o
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeObjectID(ObjectID o)
            throws UnsupportedEncodingException {
        ObjectIDSerializer s = new ObjectIDSerializer();
        register(OBJECTID, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }

    /**
     * Deserializador de ObjectID
     *
     * @return Nova instância de ObjectID
     */
    @Override
    public ObjectID deserializeObjectID() {
        ObjectIDSerializer d = new ObjectIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(OBJECTID));
    }

    /**
     * Serializador de PartyRef
     *
     * @param p
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializePartyRef(
            PartyRef p) throws UnsupportedEncodingException {
        PartyRefSerializer s = new PartyRefSerializer();
        register(PARTYREF, offset);
        setOffset(s.serialize(buffer, offset, p));

        return this;
    }

    /**
     * Deserializador de PartyRef
     *
     * @return nova instância de PartyRef
     */
    @Override
    public PartyRef deserializePartyRef() {
        PartyRefSerializer d = new PartyRefSerializer();
        return d.deserialize(buffer, getOffsetFromID(PARTYREF));
    }

    /**
     * Serializador de ObjectRef
     *
     * @param o
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeObjectRef(
            ObjectRef o) throws UnsupportedEncodingException {
        ObjectRefSerializer s = new ObjectRefSerializer();
        register(OBJECTREF, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }

    /**
     * Deserializador de ObjectRef
     *
     * @return nova instância de ObjectRef
     */
    @Override
    public ObjectRef deserializeObjectRef() {
        ObjectRefSerializer d = new ObjectRefSerializer();
        return d.deserialize(buffer, getOffsetFromID(OBJECTREF));
    }

    /**
     * Serializador de LocatableRef
     *
     * @param l
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeLocatableRef(
            LocatableRef l) throws UnsupportedEncodingException {
        LocatableRefSerializer s = new LocatableRefSerializer();
        register(LOCATABLEREF, offset);
        setOffset(s.serialize(buffer, offset, l));

        return this;
    }

    /**
     * Deserializador de LocatableRef
     *
     * @return pr nova instância de LocatableRef
     */
    @Override
    public LocatableRef deserializeLocatableRef() {
        LocatableRefSerializer d = new LocatableRefSerializer();
        return d.deserialize(buffer, getOffsetFromID(LOCATABLEREF));
    }

    /**
     * Serializador de ProportionKind
     *
     * @param p
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeProportionKind(
            ProportionKind p) {
        ProportionKindSerializer s = new ProportionKindSerializer();
        register(PROPORTIONKIND, offset);
        setOffset(s.serialize(buffer, offset, p));

        return this;
    }

    /**
     * Deserializador de ProportionKind
     *
     * @return nova instância de ProportionKind
     */
    @Override
    public ProportionKind deserializeProportionKind() {
        ProportionKindSerializer d = new ProportionKindSerializer();
        return d.deserialize(buffer, getOffsetFromID(PROPORTIONKIND));
    }

    /**
     * Serializador de AccessGroupRef
     *
     * @param a
     * @return instância de RMObjectSerializationClient atual
     * @throws java.io.UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeAccessGroupRef(
            AccessGroupRef a) throws UnsupportedEncodingException{
        AccessGroupRefSerializer s = new AccessGroupRefSerializer();
        register(ACCESSGROUPREF, offset);
        setOffset(s.serialize(buffer, offset, a));

        return this;
    }

    /**
     * Deserializador de AccessGroupRef
     *
     * @return nova instância de PartyIdentified
     */
    @Override
    public AccessGroupRef deserializeAccessGroupRef() {
        AccessGroupRefSerializer d = new AccessGroupRefSerializer();
        return d.deserialize(buffer, getOffsetFromID(ACCESSGROUPREF));
    }

    /**
     * Serializador de PartyIdentified
     *
     * @param p
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializePartyIdentified(
            PartyIdentified p) throws UnsupportedEncodingException {
        PartyIdentifiedSerializer s = new PartyIdentifiedSerializer();
        register(PARTYIDENTIFIED, offset);
        setOffset(s.serialize(buffer, offset, p));

        return this;
    }

    /**
     * Deserializador de PartyIdentified
     *
     * @return nova instância de PartyIdentified
     */
    @Override
    public PartyIdentified deserializePartyIdentified() {
        PartyIdentifiedSerializer d = new PartyIdentifiedSerializer();
        return d.deserialize(buffer, getOffsetFromID(PARTYIDENTIFIED));
    }

    /**
     * Serializador de Archetyped
     *
     * @param a
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeArchetyped(
            Archetyped a) throws UnsupportedEncodingException {
        ArchetypedSerializer s = new ArchetypedSerializer();
        register(ARCHETYPED, offset);
        setOffset(s.serialize(buffer, offset, a));

        return this;
    }

    /**
     * Deserializador de Archetyped
     *
     * @return nova instância de Archetyped
     */
    @Override
    public Archetyped deserializeArchetyped() {
        ArchetypedSerializer d = new ArchetypedSerializer();
        return d.deserialize(buffer, getOffsetFromID(ARCHETYPED));
    }

    /**
     * Serializador de DvEncapsulated
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDvEncapsulated(
            DvEncapsulated d) throws UnsupportedEncodingException {
        DvEncapsulatedSerializer s = new DvEncapsulatedSerializer();
        register(DVENCAPSULATED, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvEncapsulated
     *
     * @return
     */
    @Override
    public DvEncapsulated deserializeDvEncapsulated() {
        DvEncapsulatedSerializer d = new DvEncapsulatedSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVENCAPSULATED));
    }

    /**
     * Serializador de UIDBasedID
     *
     * @param u
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeUIDBasedID(
            UIDBasedID u) throws UnsupportedEncodingException {
        UIDBasedIDSerializer s = new UIDBasedIDSerializer();
        register(UIDBASEDID, offset);
        setOffset(s.serialize(buffer, offset, u));

        return this;
    }

    /**
     * Deserializador de UIDBasedID
     *
     * @return nova instância de UIDBasedID
     */
    @Override
    public UIDBasedID deserializeUIDBasedID() {
        UIDBasedIDSerializer d = new UIDBasedIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(UIDBASEDID));
    }

    /**
     * Serializador de DvParsable
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDvParsable(
            DvParsable d) throws UnsupportedEncodingException {
        DvParsableSerializer s = new DvParsableSerializer();
        register(DVPARSABLE, offset);
        setOffset(s.serialize(buffer,offset, d));

        return this;
    }

    /**
     * Deserializador de DvParsable
     *
     * @return nova instância de DvParsable
     */
    @Override
    public DvParsable deserializeDvParsable() {
        DvParsableSerializer d = new DvParsableSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVPARSABLE));
    }

    /**
     * Serializador de DvTimeSpecification
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDvTimeSpecification(
            DvTimeSpecification d) throws UnsupportedEncodingException {
        DvTimeSpecificationSerializer s = new DvTimeSpecificationSerializer();
        register(DVTIMESPECIFICATION, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvTimeSpecification
     *
     * @return nova instância de DvTimeSpecification
     */
    @Override
    public DvTimeSpecification deserializeDvTimeSpecification() {
        DvTimeSpecificationSerializer d = new DvTimeSpecificationSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVTIMESPECIFICATION));
    }

    /**
     * Serializador de DvMultimedia
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDvMultimedia(
            DvMultimedia d) throws UnsupportedEncodingException {
        DvMultimediaSerializer s = new DvMultimediaSerializer();
        register(DVMULTIMEDIA, offset);
        setOffset(s.serialize(buffer,offset, d));

        return this;
    }

    /**
     * Deserializador de DvMultimedia
     *
     * @return nova instância de DvMultimedia
     */
    @Override
    public DvMultimedia deserializeDvMultimedia() {
        DvMultimediaSerializer d = new DvMultimediaSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVMULTIMEDIA));
    }

    /**
     * Serializador de DvText
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDvText(
            DvText d) throws UnsupportedEncodingException {

        DvTextSerializer s = new DvTextSerializer();
        register(DVTEXT, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvText
     *
     * @return nova instância de DvText
     */
    @Override
    public DvText deserializeDvText() {
        DvTextSerializer d = new DvTextSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVTEXT));
    }

    /**
     * Serializador de DvCodedText
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDvCodedText(
            DvCodedText d) throws UnsupportedEncodingException {
        DvCodedTextSerializer s = new DvCodedTextSerializer();
        register(DVCODEDTEXT, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvCodedText
     *
     * @return nova instância de DvCodedText
     */
    @Override
    public DvCodedText deserializeDvCodedText() {
        DvCodedTextSerializer d = new DvCodedTextSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVCODEDTEXT));
    }

    /**
     * Serializador de TermMapping
     *
     * @param t
     * @return instância de RMObjectSerializationClient atual
     * @throws java.io.UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeTermMapping(
            TermMapping t) throws UnsupportedEncodingException {

        TermMappingSerializer s = new TermMappingSerializer();
        register(TERMMAPPING, offset);
        setOffset(s.serialize(buffer, offset, t));

        return this;
    }

    /**
     * Deserializador de TermMapping
     *
     * @return nova instância de TermMappping
     */
    @Override
    public TermMapping deserializeTermMapping() {
        TermMappingSerializer d = new TermMappingSerializer();
        return d.deserialize(buffer, getOffsetFromID(TERMMAPPING));
    }

    /**
     * Serializador de Link
     *
     * @param l
     * @return instância de RMObjectSerializationClient atual
     * @throws java.io.UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeLink(
            Link l) throws UnsupportedEncodingException {
        LinkSerializer s = new LinkSerializer();
        register(LINK, offset);
        setOffset(s.serialize(buffer, offset, l));

        return this;
    }

    /**
     * Deserializador de Link
     *
     * @return nova instância de Link
     */
    @Override
    public Link deserializeLink() {
        LinkSerializer d = new LinkSerializer();
        return d.deserialize(buffer, getOffsetFromID(LINK));
    }

    /**
     * Serializador de DvState
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDvState(
            DvState d) throws UnsupportedEncodingException {
        DvStateSerializer s = new DvStateSerializer();
        register(DVSTATE, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvState 
     * 
     * @return nova instância de DvState
     */
    @Override
    public DvState deserializaDvState() {
        DvStateSerializer d = new DvStateSerializer();

        return d.deserialize(buffer, getOffsetFromID(DVSTATE));
    }

    /**
     * Serializador de DvParagraph
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDvParagraph(
            DvParagraph d) throws UnsupportedEncodingException {
        DvParagraphSerializer s = new DvParagraphSerializer();
        register(DVPARAGRAPH, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvParagraph
     *
     * @return nova instância de DvParagraph
     */
    @Override
    public DvParagraph deserializeDvParagraph() {
        DvParagraphSerializer d = new DvParagraphSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVPARAGRAPH));
    }

    /**
     * Serializador de PartyProxy
     *
     * @param p
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializePartyProxy(
            PartyProxy p) throws UnsupportedEncodingException {
        PartyProxySerializer s = new PartyProxySerializer();
        register(PARTYPROXY, offset);
        setOffset(s.serialize(buffer, offset, p));

        return this;
    }

    /**
     * Deserializador de PartyProxy
     *
     * @return nova instância de PartyProxy
     */
    @Override
    public PartyProxy deserializePartyProxy() {
        PartyProxySerializer d = new PartyProxySerializer();
        return d.deserialize(buffer, getOffsetFromID(PARTYPROXY));
    }

    /**
     * Serializador de FeederAuditDetails
     *
     * @param f
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeFeederAuditDetails(
            FeederAuditDetails f) throws UnsupportedEncodingException {
        FeederAuditDetailsSerializer s = new FeederAuditDetailsSerializer();
        register(FEEDERAUDITDETAILS, offset);
        setOffset(s.serialize(buffer, offset, f));

        return this;
    }

    /**
     * Deserializador de FeederAuditDetails
     *
     * @return nova instância de FeederAuditDetails
     */
    @Override
    public FeederAuditDetails deserializeFeederAuditDetails() {
        FeederAuditDetailsSerializer d = new FeederAuditDetailsSerializer();
        return d.deserialize(buffer, getOffsetFromID(FEEDERAUDITDETAILS));
    }
        
    /**
     * Serializador de FeederAudit
     * 
     * @param f
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeFeederAudit(
            FeederAudit f) throws UnsupportedEncodingException {
        FeederAuditSerializer s = new FeederAuditSerializer();
        register(FEEDERAUDIT, offset);
        setOffset(s.serialize(buffer, offset, f));
        
        return this;
    }
    
    /**
     * Deserializador de FeederAudit
     * 
     * @return instância de FeederAudit
     */
    @Override
    public FeederAudit deserializeFeederAudit() {
        FeederAuditSerializer d = new FeederAuditSerializer();
        return d.deserialize(buffer, getOffsetFromID(FEEDERAUDIT));
    }
    
    /**
     * Serializador de Locatable
     * 
     * @param l
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeLocatable(
            Locatable l) throws UnsupportedEncodingException {
       LocatableSerializer s = new LocatableSerializer();
       register(LOCATABLE, offset);
       setOffset(s.serialize(buffer, offset, l));
       
       return this;
    }
    
    /**
     * Deserializador de Locatable
     * 
     * @return nova instância de Locatable
     */
    @Override
    public Locatable deserializeLocatable() {
       LocatableSerializer d = new LocatableSerializer();
       return d.deserialize(buffer, getOffsetFromID(LOCATABLE));
    }
    
    /**
     * Serializador de PartyRelated
     * 
     * @param p
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializePartyRelated(
            PartyRelated p) throws UnsupportedEncodingException {
        PartyRelatedSerializer s = new PartyRelatedSerializer();
        register(PARTYRELATED, offset);
        setOffset(s.serialize(buffer, offset, p));
        
        return this;
    }
   
    /**
     * Deserializador de PartyRelated
     * 
     * @return nova instância de PartyRelated
     */
    @Override
    public PartyRelated deserializePartyRelated() {
        PartyRelatedSerializer d = new PartyRelatedSerializer();
        return d.deserialize(buffer, getOffsetFromID(PARTYRELATED));
    }
    
    /**
     * Serializador de PartySelf
     * 
     * @param p
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializePartySelf(
            PartySelf p) throws UnsupportedEncodingException {
        PartySelfSerializer s = new PartySelfSerializer();
        register(PARTYSELF, offset);
        setOffset(s.serialize(buffer, offset, p));
        
        return this;
    }
    
    /**
     * Deserializador de PartySelf
     * 
     * @return nova instância de PartySelf
     */
    @Override
    public PartySelf deserializePartySelf() {
        PartySelfSerializer d = new PartySelfSerializer();
        return d.deserialize(buffer, getOffsetFromID(PARTYSELF));
    }
    
    
    /**
     * Serializador de ResourceDescriptionItem
     * 
     * @param r
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeResourceDescriptionItem(
            ResourceDescriptionItem r) throws UnsupportedEncodingException {
        ResourceDescriptionItemSerializer s = 
                new ResourceDescriptionItemSerializer();
        register(RESOURCEDESCRIPTIONITEM, offset);
        setOffset(s.serialize(buffer, offset, r));
        
        return this;
    }
    
    /**
     * Deserializador de ResourceDescriptionItem
     * 
     * @return nova instância de ResourceDescriptionItem
     */
    @Override
    public ResourceDescriptionItem deserializeResourceDescriptionItem() {
        ResourceDescriptionItemSerializer d = 
                new ResourceDescriptionItemSerializer();
        
        return d.deserialize(buffer, getOffsetFromID(RESOURCEDESCRIPTIONITEM));
    }
    
    /**
     * Serializador de TranslationDetails
     * 
     * @param t
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeTranslationDetails(
            TranslationDetails t) throws UnsupportedEncodingException {
        TranslationDetailsSerializer s = 
                new TranslationDetailsSerializer();
        register(TRANSLATIONDETAILS, offset);
        setOffset(s.serialize(buffer, offset, t));
        
        return this;
    }

    /**
     * Deserializador de TranslationDetails
     * 
     * @return nova instância de TranslationDetails
     */
    @Override
    public TranslationDetails deserializeTranslationDetails() {
        TranslationDetailsSerializer d = 
                new TranslationDetailsSerializer();
        
        return d.deserialize(buffer, getOffsetFromID(TRANSLATIONDETAILS));
    }
    
    /**
     * Serializador de Item
     * 
     * @param i
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeItem(
            Item i) throws UnsupportedEncodingException {
        ItemSerializer s = new ItemSerializer();
        register(ITEM, offset);
        setOffset(s.serialize(buffer, offset, i));
        
        return this;
    }
    
    /**
     * Deserializador de Item
     * 
     * @return nova instância de Item
     */
    @Override
    public Item deserializeItem() {
        ItemSerializer d = new ItemSerializer();
        return d.deserialize(buffer, getOffsetFromID(ITEM));
    }
    
    /**
     * Serializador de Cluster
     * 
     * @param c
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeCluster(
            Cluster c) throws UnsupportedEncodingException {
        ClusterSerializer s = new ClusterSerializer();
        register(CLUSTER, offset);
        setOffset(s.serialize(buffer, offset, c));
        
        return this;
    }
    
    /**
     * Deserializador de Cluster
     * 
     * @return nova instância de Cluster
     */
    @Override
    public Cluster deserializeCluster() {
        ClusterSerializer d = new ClusterSerializer();
        return d.deserialize(buffer, getOffsetFromID(CLUSTER));
    }
    
    /**
     * Serializador de Element
     * 
     * @param e
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeElement(
            Element e) throws UnsupportedEncodingException {
        ElementSerializer s = new ElementSerializer();
        register(ELEMENT, offset);
        setOffset(s.serialize(buffer, offset, e));
        
        return this;
    }

    /**
     * Deserializador de Element
     * 
     * @return nova instância de Element
     */
    @Override
    public Element deserializeElement() {
        ElementSerializer d = new ElementSerializer();
        return d.deserialize(buffer, getOffsetFromID(ELEMENT));
    }
    
    /**
     * Serializador de DataStructure
     * 
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeDataStructure(
            DataStructure d) throws UnsupportedEncodingException {
        DataStructureSerializer s = new DataStructureSerializer();
        register(DATASTRUCTURE, offset);
        setOffset(s.serialize(buffer, offset, d));
        
        return this;
    }
    
    /**
     * Deserializador de DataStructure
     * 
     * @return nova instância de DataStructure
     */
    @Override
    public DataStructure deserializeDataStructure() {
        DataStructureSerializer d = new DataStructureSerializer();
        return d.deserialize(buffer, getOffsetFromID(DATASTRUCTURE));
    }
    
    /**
     * Serializador de ItemList
     * 
     * @param il
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeItemList(
            ItemList il) throws UnsupportedEncodingException {
        ItemListSerializer s = new ItemListSerializer();
        register(ITEMLIST, offset);
        setOffset(s.serialize(buffer, offset, il));
        
        return this;
    }
    
    /**
     * Deserializador de ItemList
     * 
     * @return nova instância de ItemList
     */
    @Override
    public ItemList deserializeItemList() {
        ItemListSerializer d = new ItemListSerializer();
        return d.deserialize(buffer, getOffsetFromID(ITEMLIST));
    }
    
    /**
     * Serializador de ItemStructure
     * 
     * @param i
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeItemStructure(
            ItemStructure i) throws UnsupportedEncodingException {
        ItemStructureSerializer s = new ItemStructureSerializer();
        register(ITEMSTRUCTURE, offset);
        setOffset(s.serialize(buffer, offset, i));
        
        return this;
    }
    
    /**
     * Deserializador de ItemStructure
     * 
     * @return nova instância de ItemStructure
     */
    @Override
    public ItemStructure deserializeItemStructure() {
        ItemStructureSerializer d = new ItemStructureSerializer();
        return d.deserialize(buffer, getOffsetFromID(ITEMSTRUCTURE));
    }
    
    
    /**
     * Serializador de ItemSingle
     * 
     * @param i
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeItemSingle(
            ItemSingle i) throws UnsupportedEncodingException {
        ItemSingleSerializer s = new ItemSingleSerializer();
        register(ITEMSINGLE, offset);
        setOffset(s.serialize(buffer, offset, i));
        
        return this;
    }
    
    /**
     * Deserializador de ItemSingle
     * 
     * @return nova instância de ItemSingle
     */
    @Override
    public ItemSingle deserializeItemSingle() {
        ItemSingleSerializer d = new ItemSingleSerializer();
        return d.deserialize(buffer, getOffsetFromID(ITEMSINGLE));
    }
    
    /**
     * Serializador de ItemTable
     * 
     * @param i
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeItemTable(
            ItemTable i) throws UnsupportedEncodingException {
        ItemTableSerializer s = new ItemTableSerializer();
        register(ITEMTABLE, offset);
        setOffset(s.serialize(buffer, offset, i));
        
        return this;
    }
    
    /**
     * Deserializador de ItemTable
     * 
     * @return nova instância de ItemTable
     */
    @Override
    public ItemTable deserializeItemTable() {
        ItemTableSerializer d = new ItemTableSerializer();
        return d.deserialize(buffer, getOffsetFromID(ITEMTABLE));
    }
    
    /**
     * Serializador de ItemTree
     * 
     * @param i
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeItemTree(
            ItemTree i) throws UnsupportedEncodingException {
        ItemTreeSerializer s = new ItemTreeSerializer();
        register(ITEMTREE, offset);
        s.serialize(buffer, offset, i);
        
        return this;
    }
    
    /**
     * Deserilizador de ItemTree
     * 
     * @return nova instância de ItemTree
     */
    @Override
    public ItemTree deserializeItemTree() {
        ItemTreeSerializer d = new ItemTreeSerializer();
        return d.deserialize(buffer, getOffsetFromID(ITEMTREE));
    }
    
    /**
     * Serializador de Partyidentitty
     * 
     * @param p
     * @return instância de RMObjectSerializationClient atual 
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializePartyIdentity(
            PartyIdentity p) throws UnsupportedEncodingException {
        PartyIdentitySerializer s = new PartyIdentitySerializer();
        register(PARTYIDENTITY, offset);
        setOffset(s.serialize(buffer, offset, p));
        
        return this;
    }
    
    /**
     * Deserializador de PartyIdentity
     * 
     * @return nova instância de PartyIdentity
     */
    @Override
    public PartyIdentity deserializePartyIdentity() {
        PartyIdentitySerializer d = new PartyIdentitySerializer();
        return d.deserialize(buffer, getOffsetFromID(PARTYIDENTITY));
    }
    
    /**
     * Serializador de PartyRelationship
     * 
     * @param p
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializePartyRelationship(
            PartyRelationship p) throws UnsupportedEncodingException {
        PartyRelationshipSerializer s = new PartyRelationshipSerializer();
        register(PARTYRELATIONSHIP, offset);
        setOffset(s.serialize(buffer, offset, p));
        
        return this;
    }
    
    /**
     * Deserializador de PartyRelationship
     * 
     * @return nova instância de PartyRelationship
     */
    @Override
    public PartyRelationship deserializePartyRelationship() {
        PartyRelationshipSerializer d = new PartyRelationshipSerializer();
        return d.deserialize(buffer, getOffsetFromID(PARTYRELATIONSHIP));
    }
    
    /**
     * Serializador de Address
     * 
     * @param a
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeAddress(
            Address a) throws UnsupportedEncodingException {
        AddressSerializer s = new AddressSerializer();
        register(ADDRESS, offset);
        setOffset(s.serialize(buffer, offset, a));
        
        return this;
    }
    
    /**
     * Deserializador de Address
     * 
     * @return nova instância de Address
     */
    @Override
    public Address deserializeAddress() {
        AddressSerializer d = new AddressSerializer();
        return d.deserialize(buffer, getOffsetFromID(ADDRESS));
    }
    
    /**
     * Serializador de Contact
     * 
     * @param c
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeContact(
            Contact c) throws UnsupportedEncodingException {
        ContactSerializer s = new ContactSerializer();
        register(CONTACT, offset);
        setOffset(s.serialize(buffer, offset, c));
        
        return this;
    }
    
    /**
     * Deserializador de Contact
     * 
     * @return nova instância de Contact
     */
    @Override
    public Contact deserializeContact() {
        ContactSerializer d = new ContactSerializer();
        return d.deserialize(buffer, getOffsetFromID(CONTACT));
    }
    
    /**
     * Serializador de Party
     * 
     * @param p
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeParty(
            Party p) throws UnsupportedEncodingException {
        PartySerializer s = new PartySerializer();
        register(PARTY, offset);
        setOffset(s.serialize(buffer, offset, p));
        
        return this;
    }
    
    /**
     * Deserializador de Party
     * 
     * @return nova instância de Party
     */
    @Override
    public Party deserializeParty() {
        PartySerializer d = new PartySerializer();
        return d.deserialize(buffer, getOffsetFromID(PARTY));
    }
    
    /**
     * Serializador de Capability
     * 
     * @param c
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serialilzeCapability(
            Capability c) throws UnsupportedEncodingException {
        CapabilitySerializer s = new CapabilitySerializer();
        register(CAPABILITY, offset);
        setOffset(s.serialize(buffer, offset, c));
        
        return this;
    }
    
    /**
     * Deserializador de Capability
     * 
     * @return nova instância de Capability
     */
    @Override
    public Capability deserializeCapability() {
        CapabilitySerializer d = new CapabilitySerializer();
        return d.deserialize(buffer, getOffsetFromID(CAPABILITY));
    }
    
    /**
     * Serializador de Role
     * 
     * @param r
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeRole(
            Role r) throws UnsupportedEncodingException {
        RoleSerializer s = new RoleSerializer();
        register(ROLE, offset);
        setOffset(s.serialize(buffer, offset, r));
        
        return this;
    }
    
    /**
     * Deserializador de Role
     * 
     * @return nova instância de Role
     */
    @Override
    public Role deserializeRole() {
        RoleSerializer d = new RoleSerializer();
        return d.deserialize(buffer, getOffsetFromID(ROLE));
    }
    
    /**
     * Serializador de Actor
     * 
     * @param a
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeActor(
            Actor a) throws UnsupportedEncodingException {
        ActorSerializer s = new ActorSerializer();
        register(ACTOR, offset);
        setOffset(s.serialize(buffer, offset, a));
        
        return this;
    }

    /**
     * Deserializador de Actor
     * 
     * @return nova instância de Actor
     */
    @Override
    public Actor deserializeActor() {
        ActorSerializer d = new ActorSerializer();
        return d.deserialize(buffer, getOffsetFromID(ACTOR));
    }
    
    /**
     * Serializador de Agent
     * 
     * @param a
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeAgent(
            Agent a) throws UnsupportedEncodingException {
        AgentSerializer s = new AgentSerializer();
        register(AGENT, offset);
        setOffset(s.serialize(buffer, offset, a));
        
        return this;
    }
    
    /**
     * Deserializador de Agent
     * 
     * @return nova instância de Agentt
     */
    @Override
    public Agent deserializeAgent() {
        AgentSerializer d = new AgentSerializer();
        return d.deserialize(buffer, getOffsetFromID(AGENT));
    }
    
    /**
     * Serializador de Group
     * 
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeGroup(
            Group g) throws UnsupportedEncodingException {
        GroupSerializer s = new GroupSerializer();
        register(GROUP, offset);
        setOffset(s.serialize(buffer, offset, g));
        
        return this;
    }
    
    /**
     * Deserializador de Group
     * 
     * @return nova instância de Group
     */
    @Override
    public Group deserializeGroup() {
        GroupSerializer d = new GroupSerializer();
        return d.deserialize(buffer, getOffsetFromID(GROUP));
    }
    
    /**
     * Serializador de Organisation
     * 
     * @param o
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeOrganisation(
            Organisation o) throws UnsupportedEncodingException {
        OrganisationSerializer s = new OrganisationSerializer();
        register(ORGANISATION, offset);
        setOffset(s.serialize(buffer, offset, o));
        
        return this;
    }
    
    /**
     * Deserializador de Organisation
     * 
     * @return nova instância de Organisation
     */
    @Override
    public Organisation deserializeOrganisation() {
        OrganisationSerializer d = new OrganisationSerializer();
        return d.deserialize(buffer, getOffsetFromID(ORGANISATION));
    }
    
    /**
     * Serializador de Person
     * 
     * @param p
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializePerson(
            Person p) throws UnsupportedEncodingException {
        PersonSerializer s = new PersonSerializer();
        register(PERSON, offset);
        setOffset(s.serialize(buffer, offset, p));
        
        return this;
    }

    /**
     * Deserializador de Person
     * 
     * @return nova instância de Person
     */
    @Override
    public Person deserializePerson() {
        PersonSerializer d = new PersonSerializer();
        return d.deserialize(buffer, getOffsetFromID(PERSON));
    }
    
    /**
     * Serializador de InstructionDetails
     * 
     * @param id
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeinstructionDetails(
            InstructionDetails id) throws UnsupportedEncodingException {
        InstructionDetailsSerializer s = new InstructionDetailsSerializer();
        register(INSTRUCTIONDETAILS, offset);
        setOffset(s.serialize(buffer, offset, id));
        
        return this;
    }
    
    /**
     * Deserializador de InstructionDetails
     * 
     * @return nova instância de InstructionDetails
     */
    @Override
    public InstructionDetails deserializeInstructionDetails() {
        InstructionDetailsSerializer d = new InstructionDetailsSerializer();
        return d.deserialize(buffer, getOffsetFromID(INSTRUCTIONDETAILS));
    }
    
    /**
     * Serializador de ISMTransition
     * 
     * @param ism
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeISMTransition(
            ISMTransition ism) throws UnsupportedEncodingException {
        ISMTransitionSerializer s = new ISMTransitionSerializer();
        register(ISMTRANSITION, offset);
        setOffset(s.serialize(buffer, offset, ism));
        
        return this;
    }
    
    /**
     * Deserilizador de ISMTransition
     * 
     * @return nova instância de ISMTransition
     */
    @Override
    public ISMTransition deserializeISMTransition() {
        ISMTransitionSerializer d = new ISMTransitionSerializer();
        return d.deserialize(buffer, getOffsetFromID(ISMTRANSITION));
    }
    
    /**
     * Serializador de Activity
     * 
     * @param a
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeActivity(
            Activity a) throws UnsupportedEncodingException {
        ActivitySerializer s = new ActivitySerializer();
        register(ACTIVITY, offset);
        setOffset(s.serialize(buffer, offset, a));
        
        return this;
    }
    
    /**
     * Deserializador de Activity
     * 
     * @return nova instância de Activity
     */
    @Override
    public Activity deserializeActivity() {
        ActivitySerializer d = new ActivitySerializer();
        return d.deserialize(buffer, getOffsetFromID(ACTIVITY));
    }
    
    /**
     * Método para registrar um determinado objeto no índice
     * 
     * @param id
     * @param offset
     */
    private void register(RMObjectID id, int offset) {
        String key = Index.createKey(
                id.name(), order[id.getValue()]
        );
        //order[id.getValue()]++;
        index.setItemPosition(key, offset);
    }

    /**
     * Método para obter a posição de um determinado objeto no índice
     *
     * @param id
     * @return offset do item
     */
    private int getOffsetFromID(RMObjectID id) {
        return index.getItemPosition(
                Index.createKey(
                        id.name(),
                        order[id.getValue()]
                )
        );
    }
    
    /**
     * Seta o offset atual
     * @param pos 
     */
    private void setOffset(int pos) {
        this.offset = pos;
    }
}
