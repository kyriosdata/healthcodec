/*
* Copyright 2019 Instituto de Informática - UFG
*
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
package com.github.kyriosdata.healthcodec;

import com.github.kyriosdata.healthcodec.RMObject.*;
import com.github.kyriosdata.healthcodec.RMObject.DvEHRURI;
import com.github.kyriosdata.healthcodec.RMObject.DVURI;
import com.github.kyriosdata.healthcodec.RMObject.ISO_OID;
import com.github.kyriosdata.healthcodec.RMObject.UID;
import com.github.kyriosdata.healthcodec.RMObject.UUID;
import com.github.kyriosdata.healthcodec.RMObjectSerialization.*;

import java.util.List;

import static com.github.kyriosdata.healthcodec.RMObjectID.*;
import static com.github.kyriosdata.healthcodec.RMObjectID.DvEHRURI;
import static com.github.kyriosdata.healthcodec.RMObjectID.DVURI;
import static com.github.kyriosdata.healthcodec.RMObjectID.ISO_OID;
import static com.github.kyriosdata.healthcodec.RMObjectID.UID;
import static com.github.kyriosdata.healthcodec.RMObjectID.UUID;

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
    private final Buffer buffer = Buffer.newInstance();
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
            DvIdentifier d)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeUID(
            UID u)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeInternetID(
            InternetID i)  {

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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeISOOID(ISO_OID i)
             {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeUUID(UUID u)
             {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeTerminologyID(
            TerminologyID t)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeGenericID(
            GenericID g)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeTemplateID(
            TemplateID t)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeCodePhrase(
            CodePhrase c)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeDVURI(
            DVURI d)  {
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
     * Serializador de DvEHRURI
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     * @
     */
    @Override
    public RMObjectSerializationClient serializeDvEHRURI(
            DvEHRURI d)  {
        DvEHRURISerializer s = new DvEHRURISerializer();
        register(DvEHRURI, offset);
        s.serialize(buffer, offset, d);

        return this;
    }

    /**
     * Deserializador de DvEHRURI
     *
     * @return nova instânciade DvEHRURI
     */
    @Override
    public DvEHRURI deserializeDvEHRURI() {
        DvEHRURISerializer d = new DvEHRURISerializer();
        return d.deserialize(buffer, getOffsetFromID(DvEHRURI));
    }

    /**
     * Serializador de VersionTreeID
     *
     * @param v
     * @return instância de RMObjectSerializationClient atual
     * @
     */
    @Override
    public RMObjectSerializationClient serializeVersionTreeID(
            VersionTreeID v)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeArchetypeID(
            ArchetypeID a)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeObjectVersionID(
            ObjectVersionID o)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeHierObjectID(
            HierObjectID h)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeObjectID(ObjectID o)
             {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializePartyRef(
            PartyRef p)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeObjectRef(
            ObjectRef o)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeLocatableRef(
            LocatableRef l)  {
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
            AccessGroupRef a) {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializePartyIdentified(
            PartyIdentified p)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeArchetyped(
            Archetyped a)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeDvEncapsulated(
            DvEncapsulated d)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeUIDBasedID(
            UIDBasedID u)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeDvParsable(
            DvParsable d)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeDvTimeSpecification(
            DvTimeSpecification d)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeDvMultimedia(
            DvMultimedia d)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeDvText(
            DvText d)  {

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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeDvCodedText(
            DvCodedText d)  {
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
            TermMapping t)  {

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
            Link l)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeDvState(
            DvState d)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeDvParagraph(
            DvParagraph d)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializePartyProxy(
            PartyProxy p)  {
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
     * @
     */
    @Override
    public RMObjectSerializationClient serializeFeederAuditDetails(
            FeederAuditDetails f)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeFeederAudit(
            FeederAudit f)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeLocatable(
            Locatable l)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializePartyRelated(
            PartyRelated p)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializePartySelf(
            PartySelf p)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeResourceDescriptionItem(
            ResourceDescriptionItem r)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeTranslationDetails(
            TranslationDetails t)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeItem(
            Item i)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeCluster(
            Cluster c)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeElement(
            Element e)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeDataStructure(
            DataStructure d)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeItemList(
            ItemList il)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeItemStructure(
            ItemStructure i)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeItemSingle(
            ItemSingle i)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeItemTable(
            ItemTable i)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeItemTree(
            ItemTree i)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializePartyIdentity(
            PartyIdentity p)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializePartyRelationship(
            PartyRelationship p)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeAddress(
            Address a)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeContact(
            Contact c)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeParty(
            Party p)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeCapability(
            Capability c)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeRole(
            Role r)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeActor(
            Actor a)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeAgent(
            Agent a)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeGroup(
            Group g)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeOrganisation(
            Organisation o)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializePerson(
            Person p)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeinstructionDetails(
            InstructionDetails id)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeISMTransition(
            ISMTransition ism)  {
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
     * @ 
     */
    @Override
    public RMObjectSerializationClient serializeActivity(
            Activity a)  {
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
     * Serializador de DvOrdered
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvOrdered(DvOrdered d) {
        DvOrderedSerializer s = new DvOrderedSerializer();
        register(DVORDERED, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvOrdered
     *
     * @return nova instância de DvOrdered
     */
    @Override
    public DvOrdered deserializeDvOrdered() {
        DvOrderedSerializer d = new DvOrderedSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVORDERED));
    }

    /**
     * Serializador de DvInterval
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvInterval(DvInterval d) {
        DvIntervalSerializer s = new DvIntervalSerializer();
        register(DVINTERVAL, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvInterval
     *
     * @return nova instância de DvInterval
     */
    @Override
    public DvInterval deserializeDvInterval() {
        DvIntervalSerializer d = new DvIntervalSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVINTERVAL));
    }

    /**
     * Serializador de DvQuantified.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvQuantified(DvQuantified d) {
        DvQuantifiedSerializer s = new DvQuantifiedSerializer();
        register(DVQUANTIFIED, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvQuantified
     *
     * @return nova instância de DvQuantified
     */
    @Override
    public DvQuantified deserializeDvQuantified() {
        DvQuantifiedSerializer d = new DvQuantifiedSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVQUANTIFIED));
    }

    /**
     * Serializador de DvAmount.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvAmount(DvAmount d) {
        DvAmountSerializer s = new DvAmountSerializer();
        register(DVAMOUNT, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvAmount
     *
     * @return nova instância de DvAmount
     */
    @Override
    public DvAmount deserializeDvAmount() {
        DvAmountSerializer d = new DvAmountSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVAMOUNT));
    }

    /**
     * Serializador de DvOrdinal.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvOrdinal(DvOrdinal d) {
        DvOrdinalSerializer s = new DvOrdinalSerializer();
        register(DVORDINAL, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvOrdinal
     *
     * @return nova instância de DvOrdinal
     */
    @Override
    public DvOrdinal deserializeDvOrdinal() {
        DvOrdinalSerializer d = new DvOrdinalSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVORDINAL));
    }

    /**
     * Serializador de DvCount.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvCount(DvCount d) {
        DvCountSerializer s = new DvCountSerializer();
        register(DVCOUNT, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvCount
     *
     * @return nova instância de DvCount
     */
    @Override
    public DvCount deserializeDvCount() {
        DvCountSerializer d = new DvCountSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVCOUNT));
    }

    /**
     * Serializador de DvProportion.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvProportion(DvProportion d) {
        DvProportionSerializer s = new DvProportionSerializer();
        register(DVPROPORTION, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvProportion
     *
     * @return nova instância de DvProportion
     */
    @Override
    public DvProportion deserializeDvProportion() {
        DvProportionSerializer d = new DvProportionSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVPROPORTION));
    }

    /**
     * Serializador de DvQuantity.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvQuantity(DvQuantity d) {
        DvQuantitySerializer s = new DvQuantitySerializer();
        register(DVQUANTITY, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvQuantity
     *
     * @return nova instância de DvQuantity
     */
    @Override
    public DvQuantity deserializeDvQuantity() {
        DvQuantitySerializer d = new DvQuantitySerializer();
        return d.deserialize(buffer, getOffsetFromID(DVQUANTITY));
    }

    /**
     * Serializador de DvDuration.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvDuration(DvDuration d) {
        DvDurationSerializer s = new DvDurationSerializer();
        register(DVDURATION, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvDuration
     *
     * @return nova instância de DvDuration
     */
    @Override
    public DvDuration deserializeDvDuration() {
        DvDurationSerializer d = new DvDurationSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVDURATION));
    }

    /**
     * Serializador de DvAbsoluteQuantity com DvCount.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvCount d) {
        DvAbsoluteQuantitySerializer s = new DvAbsoluteQuantitySerializer();
        register(DVABSOLUTEQUANTITY, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvAbsoluteQuantity com DvCount
     *
     * @return nova instância de DvAbsoluteQuantity
     */
    @Override
    public DvAbsoluteQuantityWithDvCount deserializeDvAbsoluteQuantityDvCount() {
        DvAbsoluteQuantitySerializer d = new DvAbsoluteQuantitySerializer();
        return d.deserializeDvCount(buffer, getOffsetFromID(DVABSOLUTEQUANTITY));
    }

    /**
     * Serializador de DvAbsoluteQuantity com DvDuration.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvDuration d) {
        DvAbsoluteQuantitySerializer s = new DvAbsoluteQuantitySerializer();
        register(DVABSOLUTEQUANTITY, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvAbsoluteQuantity com DvDuration
     *
     * @return nova instância de DvAbsoluteQuantity
     */
    @Override
    public DvAbsoluteQuantityWithDvDuration deserializeDvAbsoluteQuantityDvDuration() {
        DvAbsoluteQuantitySerializer d = new DvAbsoluteQuantitySerializer();
        return d.deserializeDvDuration(buffer, getOffsetFromID(DVABSOLUTEQUANTITY));
    }

    /**
     * Serializador de DvAbsoluteQuantity com DvProportion.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvProportion d) {
        DvAbsoluteQuantitySerializer s = new DvAbsoluteQuantitySerializer();
        register(DVABSOLUTEQUANTITY, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvAbsoluteQuantity com DvProportion
     *
     * @return nova instância de DvAbsoluteQuantity
     */
    @Override
    public DvAbsoluteQuantityWithDvProportion deserializeDvAbsoluteQuantityDvProportion() {
        DvAbsoluteQuantitySerializer d = new DvAbsoluteQuantitySerializer();
        return d.deserializeDvProportion(buffer, getOffsetFromID(DVABSOLUTEQUANTITY));
    }

    /**
     * Serializador de DvAbsoluteQuantity com DvProportion.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvQuantity d) {
        DvAbsoluteQuantitySerializer s = new DvAbsoluteQuantitySerializer();
        register(DVABSOLUTEQUANTITY, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvAbsoluteQuantity com DvProportion
     *
     * @return nova instância de DvAbsoluteQuantity
     */
    @Override
    public DvAbsoluteQuantityWithDvQuantity deserializeDvAbsoluteQuantityDvQuantity() {
        DvAbsoluteQuantitySerializer d = new DvAbsoluteQuantitySerializer();
        return d.deserializeDvQuantity(buffer, getOffsetFromID(DVABSOLUTEQUANTITY));
    }

    /**
     * Serializador de DvDate.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvDate(DvDate d) {
        DvDateSerializer s = new DvDateSerializer();
        register(DVDATE, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvDate
     *
     * @return nova instância de DvDate
     */
    @Override
    public DvDate deserializeDvDate() {
        DvDateSerializer d = new DvDateSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVDATE));
    }

    /**
     * Serializador de DvTime.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvTime(DvTime d) {
        DvTimeSerializer s = new DvTimeSerializer();
        register(DVTIME, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvTime.
     *
     * @return nova instância de DvTime
     */
    @Override
    public DvTime deserializeDvTime() {
        DvTimeSerializer d = new DvTimeSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVTIME));
    }

    /**
     * Serializador de DvTime.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvDateTime(DvDateTime d) {
        DvDateTimeSerializer s = new DvDateTimeSerializer();
        register(DVDATETIME, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvDateTime.
     *
     * @return nova instância de DvDateTime
     */
    @Override
    public DvDateTime deserializeDvDateTime() {
        DvDateTimeSerializer d = new DvDateTimeSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVDATETIME));
    }

    /**
     * Serializador de DvTemporal.
     *
     * @param d
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvTemporal(DvTemporal d) {
        DvTemporalSerializer s = new DvTemporalSerializer();
        register(DVTEMPORAL, offset);
        setOffset(s.serialize(buffer, offset, d));

        return this;
    }

    /**
     * Deserializador de DvTemporal.
     *
     * @return nova instância de DvTemporal
     */
    @Override
    public DvTemporal deserializeDvTemporal() {
        DvTemporalSerializer d = new DvTemporalSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVTEMPORAL));
    }

    /**
     * Serializador de Participation.
     *
     * @param p
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeParticipation(Participation p) {
        ParticipationSerializer s = new ParticipationSerializer();
        register(PARTICIPATION, offset);
        setOffset(s.serialize(buffer, offset, p));

        return this;
    }

    /**
     * Deserializador de Participation.
     *
     * @return nova instância de Participation
     */
    @Override
    public Participation deserializeParticipation() {
        ParticipationSerializer d = new ParticipationSerializer();
        return d.deserialize(buffer, getOffsetFromID(PARTICIPATION));
    }

    /**
     * Serializador de AuditDetails.
     *
     * @param a
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeAuditDetails(AuditDetails a) {
        AuditDetailsSerializer s = new AuditDetailsSerializer();
        register(AUDITDETAILS, offset);
        setOffset(s.serialize(buffer, offset, a));

        return this;
    }

    /**
     * Deserializador de AuditDetails.
     *
     * @return nova instância de AuditDetails
     */
    @Override
    public AuditDetails deserializeAuditDetails() {
        AuditDetailsSerializer d = new AuditDetailsSerializer();
        return d.deserialize(buffer, getOffsetFromID(AUDITDETAILS));
    }

    /**
     * Serializador de AuditDetails.
     *
     * @param a
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeAttestation(Attestation a) {
        AttestationSerializer s = new AttestationSerializer();
        register(ATTESTATION, offset);
        setOffset(s.serialize(buffer, offset, a));

        return this;
    }

    /**
     * Deserializador de Attestation.
     *
     * @return nova instância de Attestation
     */
    @Override
    public Attestation deserializeAttestation() {
        AttestationSerializer d = new AttestationSerializer();
        return d.deserialize(buffer, getOffsetFromID(ATTESTATION));
    }

    /**
     * Serializador de RevisionHistoryItem.
     *
     * @param r
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeRevisionHistoryItem(
            RevisionHistoryItem r) {
        RevisionHistoryItemSerializer s = new RevisionHistoryItemSerializer();
        register(REVISIONHISTORYITEM, offset);
        setOffset(s.serialize(buffer, offset, r));

        return this;
    }

    /**
     * Deserializador de RevisionHistoryItem.
     *
     * @return nova instância de RevisionHistoryItem
     */
    @Override
    public RevisionHistoryItem deserializeRevisionHistoryItem() {
        RevisionHistoryItemSerializer d = new RevisionHistoryItemSerializer();
        return d.deserialize(buffer, getOffsetFromID(REVISIONHISTORYITEM));
    }

    /**
     * Serializador de RevisionHistory.
     *
     * @param r
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeRevisionHistory(
            RevisionHistory r) {
        RevisionHistorySerializer s = new RevisionHistorySerializer();
        register(REVISIONHISTORY, offset);
        setOffset(s.serialize(buffer, offset, r));

        return this;
    }

    /**
     * Deserializador de RevisionHistory.
     *
     * @return nova instância de RevisionHistory
     */
    @Override
    public RevisionHistory deserializeRevisionHistory() {
        RevisionHistorySerializer d = new RevisionHistorySerializer();
        return d.deserialize(buffer, getOffsetFromID(REVISIONHISTORY));
    }

    /**
     * Serializador de Contribution.
     *
     * @param c
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeContribution(Contribution c) {
        ContributionSerializer s = new ContributionSerializer();
        register(CONTRIBUTION, offset);
        setOffset(s.serialize(buffer, offset, c));

        return this;
    }

    /**
     * Deserializador de Contribution.
     *
     * @return nova instância de Contribution
     */
    @Override
    public Contribution deserializeContribution() {
        ContributionSerializer d = new ContributionSerializer();
        return d.deserialize(buffer, getOffsetFromID(CONTRIBUTION));
    }

    /**
     * Serializador de Folder.
     *
     * @param f
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeFolder(Folder f) {
        FolderSerializer s = new FolderSerializer();
        register(FOLDER, offset);
        setOffset(s.serialize(buffer, offset, f));

        return this;
    }

    /**
     * Deserializador de Folder.
     *
     * @return nova instância de Folder
     */
    @Override
    public Folder deserializeFolder() {
        FolderSerializer d = new FolderSerializer();
        return d.deserialize(buffer, getOffsetFromID(FOLDER));
    }

    /**
     * Serializador de AuthoredResource.
     *
     * @param a
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeAuthoredResource(
            AuthoredResource a) {
        AuthoredResourceSerializer s = new AuthoredResourceSerializer();
        register(AUTHOREDRESOURCE, offset);
        setOffset(s.serialize(buffer, offset, a));

        return this;
    }

    /**
     * Deserializador de AuthoredResource.
     *
     * @return nova instância de AuthoredResource
     */
    @Override
    public AuthoredResource deserializeAuthoredResource() {
        AuthoredResourceSerializer d = new AuthoredResourceSerializer();
        return d.deserialize(buffer, getOffsetFromID(AUTHOREDRESOURCE));
    }

    /**
     * Serializador de ResourceDescription.
     *
     * @param r
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeResourceDescription(
            ResourceDescription r) {
        ResourceDescriptionSerializer s = new ResourceDescriptionSerializer();
        register(RESOURCEDESCRIPTION, offset);
        setOffset(s.serialize(buffer, offset, r));

        return this;
    }

    /**
     * Deserializador de ResourceDescription.
     *
     * @return nova instância de ResourceDescription
     */
    @Override
    public ResourceDescription deserializeResourceDescription() {
        ResourceDescriptionSerializer d = new ResourceDescriptionSerializer();
        return d.deserialize(buffer, getOffsetFromID(RESOURCEDESCRIPTION));
    }

    /**
     * Serializador de Event com ItemTree.
     *
     * @param e
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeEvent(EventWithItemTree e) {
        EventSerializer s = new EventSerializer();
        register(EVENT, offset);
        setOffset(s.serialize(buffer, offset, e));

        return this;
    }

    /**
     * Deserializador de Event com ItemTree.
     *
     * @return nova instância de Event com ItemTree
     */
    @Override
    public EventWithItemTree deserializeEventWithItemTree() {
        EventSerializer d = new EventSerializer();
        return d.deserializeItemTree(buffer, getOffsetFromID(EVENT));
    }

    /**
     * Serializador de Event com ItemSingle.
     *
     * @param e
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeEvent(EventWithItemSingle e) {
        EventSerializer s = new EventSerializer();
        register(EVENT, offset);
        setOffset(s.serialize(buffer, offset, e));

        return this;
    }

    /**
     * Deserializador de Event com ItemSingle.
     *
     * @return nova instância de Event com ItemSingle
     */
    @Override
    public EventWithItemSingle deserializeEventWithItemSingle() {
        EventSerializer d = new EventSerializer();
        return d.deserializeItemSingle(buffer, getOffsetFromID(EVENT));
    }

    /**
     * Serializador de Event com ItemTable.
     *
     * @param e
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeEvent(EventWithItemTable e) {
        EventSerializer s = new EventSerializer();
        register(EVENT, offset);
        setOffset(s.serialize(buffer, offset, e));

        return this;
    }

    /**
     * Deserializador de Event com ItemTable.
     *
     * @return nova instância de Event com ItemTable
     */
    @Override
    public EventWithItemTable deserializeEventWithItemTable() {
        EventSerializer d = new EventSerializer();
        return d.deserializeItemTable(buffer, getOffsetFromID(EVENT));
    }

    /**
     * Serializador de IntevalEvent com ItemTree.
     *
     * @param i
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeIntervalEvent(
            IntervalEventWithItemTree i) {
        IntervalEventSerializer s = new IntervalEventSerializer();
        register(INTERVALEVENT, offset);
        setOffset(s.serialize(buffer, offset, i));

        return this;
    }

    /**
     * Deserializador de Event com ItemTable.
     *
     * @return nova instância de Event com ItemTable
     */
    @Override
    public IntervalEventWithItemTree deserializeIntervalEventWithItemTree() {
        IntervalEventSerializer d = new IntervalEventSerializer();
        return d.deserializeItemTree(buffer, getOffsetFromID(INTERVALEVENT));
    }

    /**
     * Serializador de IntevalEvent com ItemSingle.
     *
     * @param i
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeIntervalEvent(
            IntervalEventWithItemSingle i) {
        IntervalEventSerializer s = new IntervalEventSerializer();
        register(INTERVALEVENT, offset);
        setOffset(s.serialize(buffer, offset, i));

        return this;
    }

    /**
     * Deserializador de Event com ItemTable.
     *
     * @return nova instância de Event com ItemTable
     */
    @Override
    public IntervalEventWithItemSingle deserializeIntervalEventWithItemSingle() {
        IntervalEventSerializer d = new IntervalEventSerializer();
        return d.deserializeItemSingle(buffer, getOffsetFromID(INTERVALEVENT));
    }

    /**
     * Serializador de IntevalEvent com ItemTable.
     *
     * @param i
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeIntervalEvent(
            IntervalEventWithItemTable i) {
        IntervalEventSerializer s = new IntervalEventSerializer();
        register(INTERVALEVENT, offset);
        setOffset(s.serialize(buffer, offset, i));

        return this;
    }

    /**
     * Deserializador de Event com ItemTable.
     *
     * @return nova instância de Event com ItemTable
     */
    @Override
    public IntervalEventWithItemTable deserializeIntervalEventWithItemTable() {
        IntervalEventSerializer d = new IntervalEventSerializer();
        return d.deserializeItemTable(buffer, getOffsetFromID(INTERVALEVENT));
    }

    /**
     * Serializador de History com ItemTree.
     *
     * @param h
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeHistory(HistoryWithItemTree h) {
        HistorySerializer s = new HistorySerializer();
        register(HISTORY, offset);
        setOffset(s.serialize(buffer, offset, h));

        return this;
    }

    public void testEventS(List<EventWithItemTree> l){
        EventSerializer es = new EventSerializer();
        register(EVENT, offset);
        setOffset(es.listSerializeItemTree(buffer, offset, l));
    }

    public List<EventWithItemTree> testEventD(){
        EventSerializer es = new EventSerializer();
        return es.deserializeListOfItemTree(buffer, getOffsetFromID(EVENT));
    }

    /**
     * Deserializador de History com ItemTree.
     *
     * @return nova instância de History com ItemTable
     */
    @Override
    public HistoryWithItemTree deserializeHistoryWithItemTree() {
        HistorySerializer d = new HistorySerializer();
        return d.deserializeItemTree(buffer, getOffsetFromID(HISTORY));
    }

    /**
     * Serializador de History com ItemSingle.
     *
     * @param h
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeHistory(HistoryWithItemSingle h) {
        HistorySerializer s = new HistorySerializer();
        register(HISTORY, offset);
        setOffset(s.serialize(buffer, offset, h));

        return this;
    }

    /**
     * Deserializador de History com ItemSingle.
     *
     * @return nova instância de History com ItemTable
     */
    @Override
    public HistoryWithItemSingle deserializeHistoryWithItemSingle() {
        HistorySerializer d = new HistorySerializer();
        return d.deserializeItemSingle(buffer, getOffsetFromID(HISTORY));
    }

    /**
     * Serializador de History com ItemTable.
     *
     * @param h
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeHistory(HistoryWithItemTable h) {
        HistorySerializer s = new HistorySerializer();
        register(HISTORY, offset);
        setOffset(s.serialize(buffer, offset, h));

        return this;
    }

    /**
     * Deserializador de History com ItemTable.
     *
     * @return nova instância de History com ItemTable
     */
    @Override
    public HistoryWithItemTable deserializeHistoryWithItemTable() {
        HistorySerializer d = new HistorySerializer();
        return d.deserializeItemTable(buffer, getOffsetFromID(HISTORY));
    }

    /**
     * Serializador de PointEvent com ItemTree.
     *
     * @param p
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializePointEvent(
            PointEventWithItemTree p) {
        PointEventSerializer s = new PointEventSerializer();
        register(POINTEVENT, offset);
        setOffset(s.serialize(buffer, offset, p));

        return this;
    }

    /**
     * Deserializador de PointEvent com ItemTree.
     *
     * @return nova instância de PointEvent com ItemTree
     */
    @Override
    public PointEventWithItemTree deserializePointEventWithItemTree() {
        PointEventSerializer d = new PointEventSerializer();
        return d.deserializeItemTree(buffer, getOffsetFromID(POINTEVENT));
    }

    /**
     * Serializador de PointEvent com ItemSingle.
     *
     * @param p
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializePointEvent(
            PointEventWithItemSingle p) {
        PointEventSerializer s = new PointEventSerializer();
        register(POINTEVENT, offset);
        setOffset(s.serialize(buffer, offset, p));

        return this;
    }

    /**
     * Deserializador de PointEvent com ItemSingle.
     *
     * @return nova instância de PointEvent com ItemSingle
     */
    @Override
    public PointEventWithItemSingle deserializePointEventWithItemSingle() {
        PointEventSerializer d = new PointEventSerializer();
        return d.deserializeItemSingle(buffer, getOffsetFromID(POINTEVENT));
    }

    /**
     * Serializador de PointEvent com ItemTable.
     *
     * @param p
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializePointEvent(
            PointEventWithItemTable p) {
        PointEventSerializer s = new PointEventSerializer();
        register(POINTEVENT, offset);
        setOffset(s.serialize(buffer, offset, p));

        return this;
    }

    /**
     * Deserializador de PointEvent com ItemTable.
     *
     * @return nova instância de PointEvent com ItemTable
     */
    @Override
    public PointEventWithItemTable deserializePointEventWithItemTable() {
        PointEventSerializer d = new PointEventSerializer();
        return d.deserializeItemTable(buffer, getOffsetFromID(POINTEVENT));
    }

    /**
     * Serializador de ContentItem.
     *
     * @param c
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeContentItem(ContentItem c) {
        ContentItemSerializer s = new ContentItemSerializer();
        register(CONTENTITEM, offset);
        setOffset(s.serialize(buffer, offset, c));

        return this;
    }

    /**
     * Deserializador de PointEvent com ItemTable.
     *
     * @return nova instância de PointEvent com ItemTable
     */
    @Override
    public ContentItem deserializeContentItem() {
        ContentItemSerializer d = new ContentItemSerializer();
        return d.deserialize(buffer, getOffsetFromID(CONTENTITEM));
    }

    /**
     * Serializador de Entry.
     *
     * @param e
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeEntry(Entry e) {
        EntrySerializer s = new EntrySerializer();
        register(ENTRY, offset);
        setOffset(s.serialize(buffer, offset, e));

        return this;
    }

    /**
     * Deserializador de Entry.
     *
     * @return nova instância de Entry.
     */
    @Override
    public Entry deserializeEntry() {
        EntrySerializer d = new EntrySerializer();
        return d.deserialize(buffer, getOffsetFromID(ENTRY));
    }

    /**
     * Serializador de CareEntry.
     *
     * @param e
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeCareEntry(CareEntry e) {
        CareEntrySerializer s = new CareEntrySerializer();
        register(CAREENTRY, offset);
        setOffset(s.serialize(buffer, offset, e));

        return this;
    }

    /**
     * Deserializador de CareEntry.
     *
     * @return nova instância de CareEntry.
     */
    @Override
    public CareEntry deserializeCareEntry() {
        CareEntrySerializer d = new CareEntrySerializer();
        return d.deserialize(buffer, getOffsetFromID(CAREENTRY));
    }

    /**
     * Serializador de Action.
     *
     * @param a
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeAction(Action a) {
        ActionSerializer s = new ActionSerializer();
        register(ACTION, offset);
        setOffset(s.serialize(buffer, offset, a));

        return this;
    }

    /**
     * Deserializador de Action.
     *
     * @return nova instância de Action.
     */
    @Override
    public Action deserializeAction() {
        ActionSerializer d = new ActionSerializer();
        return d.deserialize(buffer, getOffsetFromID(ACTION));
    }

    /**
     * Serializador de AdminEntry.
     *
     * @param a
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeAdminEntry(AdminEntry a) {
        AdminEntrySerializer s = new AdminEntrySerializer();
        register(ADMINENTRY, offset);
        setOffset(s.serialize(buffer, offset, a));

        return this;
    }

    /**
     * Deserializador de AdminEntry.
     *
     * @return nova instância de AdminEntry.
     */
    @Override
    public AdminEntry deserializeAdminEntry() {
        AdminEntrySerializer d = new AdminEntrySerializer();
        return d.deserialize(buffer, getOffsetFromID(ADMINENTRY));
    }

    /**
     * Serializador de Evaluation.
     *
     * @param a
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeEvaluation(Evaluation a) {
        EvaluationSerializer s = new EvaluationSerializer();
        register(EVALUATION, offset);
        setOffset(s.serialize(buffer, offset, a));

        return this;
    }

    /**
     * Deserializador de Evaluation.
     *
     * @return nova instância de Evaluation.
     */
    @Override
    public Evaluation deserializeEvaluation() {
        EvaluationSerializer d = new EvaluationSerializer();
        return d.deserialize(buffer, getOffsetFromID(EVALUATION));
    }

    /**
     * Serializador de Instruction.
     *
     * @param i
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeInstruction(Instruction i) {
        InstructionSerializer s = new InstructionSerializer();
        register(INSTRUCTION, offset);
        setOffset(s.serialize(buffer, offset, i));

        return this;
    }

    /**
     * Deserializador de Instruction.
     *
     * @return nova instância de Instruction.
     */
    @Override
    public Instruction deserializeInstruction() {
        InstructionSerializer d = new InstructionSerializer();
        return d.deserialize(buffer, getOffsetFromID(INSTRUCTION));
    }

    /**
     * Serializador de Observation com data ItemTree e state ItemTree.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    @Override
    public RMObjectSerializationClient serializeObservation(
            ObservationWithItemTreeItemTree o) {
        ObservationSerializer s = new ObservationSerializer();
        register(OBSERVATION, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }

    /**
     * Deserializador de Observation com data ItemTree e state ItemTree.
     * @return nova instância de Observation
     */
    @Override
    public ObservationWithItemTreeItemTree deserializeObservationWithItemTreeItemTree() {
        ObservationSerializer d = new ObservationSerializer();
        return d.deserializeItemTreeItemTree(buffer,
                getOffsetFromID(OBSERVATION));
    }

    /**
     * Serializador de Observation com data ItemTree e state ItemSingle.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    @Override
    public RMObjectSerializationClient serializeObservation(ObservationWithItemTreeItemSingle o) {
        ObservationSerializer s = new ObservationSerializer();
        register(OBSERVATION, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }

    /**
     * Deserializador de Observation com data ItemTree e state ItemSingle.
     * @return nova instância de Observation
     */
    @Override
    public ObservationWithItemTreeItemSingle deserializeObservationWithItemTreeItemSingle() {
        ObservationSerializer d = new ObservationSerializer();
        return d.deserializeItemTreeItemSingle(buffer,
                getOffsetFromID(OBSERVATION));
    }


    /**
     * Serializador de Observation com data ItemTree e state ItemTable.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    @Override
    public RMObjectSerializationClient serializeObservation(ObservationWithItemTreeItemTable o) {
        ObservationSerializer s = new ObservationSerializer();
        register(OBSERVATION, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }

    /**
     * Deserializador de Observation com data ItemTree e state ItemTable.
     * @return nova instância de Observation
     */
    @Override
    public ObservationWithItemTreeItemTable deserializeObservationWithItemTreeItemTable() {
        ObservationSerializer d = new ObservationSerializer();
        return d.deserializeItemTreeItemTable(buffer,
                getOffsetFromID(OBSERVATION));
    }

    /**
     * Serializador de Observation com data ItemTree e state ItemTable.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    @Override
    public RMObjectSerializationClient serializeObservation(ObservationWithItemSingleItemTree o) {
        ObservationSerializer s = new ObservationSerializer();
        register(OBSERVATION, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }
    /**
     * Serializador de Observation com data ItemTree e state ItemTable.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    @Override
    public RMObjectSerializationClient serializeObservation(ObservationWithItemSingleItemSingle o) {
        ObservationSerializer s = new ObservationSerializer();
        register(OBSERVATION, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }
    /**
     * Serializador de Observation com data ItemTree e state ItemTable.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    @Override
    public RMObjectSerializationClient serializeObservation(ObservationWithItemSingleItemTable o) {
        ObservationSerializer s = new ObservationSerializer();
        register(OBSERVATION, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }
    /**
     * Serializador de Observation com data ItemTree e state ItemTable.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    @Override
    public RMObjectSerializationClient serializeObservation(ObservationWithItemTableItemTree o) {
        ObservationSerializer s = new ObservationSerializer();
        register(OBSERVATION, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }
    /**
     * Serializador de Observation com data ItemTree e state ItemTable.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    @Override
    public RMObjectSerializationClient serializeObservation(ObservationWithItemTableItemSingle o) {
        ObservationSerializer s = new ObservationSerializer();
        register(OBSERVATION, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }
    /**
     * Serializador de Observation com data ItemTree e state ItemTable.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    @Override
    public RMObjectSerializationClient serializeObservation(ObservationWithItemTableItemTable o) {
        ObservationSerializer s = new ObservationSerializer();
        register(OBSERVATION, offset);
        setOffset(s.serialize(buffer, offset, o));

        return this;
    }

    /**
     * Deserializador de Observation com data ItemSingle e state ItemTree.
     * @return nova instância de Observation
     */
    @Override
    public ObservationWithItemSingleItemTree deserializeObservationWithItemSingleItemTree() {
        ObservationSerializer d = new ObservationSerializer();
        return d.deserializeItemSingleItemTree(buffer,
                getOffsetFromID(OBSERVATION));
    }

    /**
     * Deserializador de Observation com data ItemSingle e state ItemSingle.
     * @return nova instância de Observation
     */
    @Override
    public ObservationWithItemSingleItemSingle deserializeObservationWithItemSingleItemSingle() {
        ObservationSerializer d = new ObservationSerializer();
        return d.deserializeItemSingleItemSingle(buffer,
                getOffsetFromID(OBSERVATION));
    }

    /**
     * Deserializador de Observation com data ItemSingle e state ItemTable.
     * @return nova instância de Observation
     */
    @Override
    public ObservationWithItemSingleItemTable deserializeObservationWithItemSingleItemTable() {
        ObservationSerializer d = new ObservationSerializer();
        return d.deserializeItemSingleItemTable(buffer,
                getOffsetFromID(OBSERVATION));
    }

    /**
     * Deserializador de Observation com data ItemTable e state ItemTable.
     * @return nova instância de Observation
     */
    @Override
    public ObservationWithItemTableItemTable deserializeObservationWithItemTableItemTable() {
        ObservationSerializer d = new ObservationSerializer();
        return d.deserializeItemTableItemTable(buffer,
                getOffsetFromID(OBSERVATION));
    }

    /**
     * Deserializador de Observation com data ItemTable e state ItemTree.
     * @return nova instância de Observation
     */
    @Override
    public ObservationWithItemTableItemTree deserializeObservationWithItemTableItemTree() {
        ObservationSerializer d = new ObservationSerializer();
        return d.deserializeItemTableItemTree(buffer,
                getOffsetFromID(OBSERVATION));
    }

    /**
     * Deserializador de Observation com data ItemTable e state ItemSingle.
     * @return nova instância de Observation
     */
    @Override
    public ObservationWithItemTableItemSingle deserializeObservationWithItemTableItemSingle() {
        ObservationSerializer d = new ObservationSerializer();
        return d.deserializeItemTableItemSingle(buffer,
                getOffsetFromID(OBSERVATION));
    }

    /**
     * Serializador de Section.
     *
     * @param s
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeSection(Section s) {
        SectionSerializer ss = new SectionSerializer();
        register(SECTION, offset);
        setOffset(ss.serialize(buffer, offset, s));

        return this;
    }

    /**
     * Deserializador de Section.
     *
     * @return nova instância de Section.
     */
    @Override
    public Section deserializeSection() {
        SectionSerializer d = new SectionSerializer();
        return d.deserialize(buffer, getOffsetFromID(SECTION));
    }

    /**
     * Serializador de EventContext.
     *
     * @param e
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeEventContext(EventContext e) {
        EventContextSerializer s = new EventContextSerializer();
        register(EVENTCONTEXT, offset);
        setOffset(s.serialize(buffer, offset, e));

        return this;
    }
    /**
     * Deserializador de EventContext.
     *
     * @return nova instância de EventContext.
     */
    @Override
    public EventContext deserializeEventContext() {
        EventContextSerializer d = new EventContextSerializer();
        return d.deserialize(buffer, getOffsetFromID(EVENTCONTEXT));
    }

    /**
     * Serializador de Composition.
     *
     * @param c
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeComposition(Composition c) {
        CompositionSerializer s = new CompositionSerializer();
        register(COMPOSITION, offset);
        setOffset(s.serialize(buffer, offset, c));

        return this;
    }

    /**
     * Deserializador de Composition.
     *
     * @return nova instância de Composition.
     */
    @Override
    public Composition deserializeComposition() {
        CompositionSerializer d = new CompositionSerializer();
        return d.deserialize(buffer, getOffsetFromID(COMPOSITION));
    }

    /**
     * Serializador de EHR.
     *
     * @param e
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeEHR(RMObject.EHR e) {
        EHRSerializer s = new EHRSerializer();
        register(EHR, offset);
        setOffset(s.serialize(buffer, offset, e));

        return this;
    }

    /**
     * Deserializador de EHR.
     *
     * @return nova instância de EHR.
     */
    @Override
    public EHR deserializeEHR() {
        EHRSerializer d = new EHRSerializer();
        return d.deserialize(buffer, getOffsetFromID(EHR));
    }

    /**
     * Serializador de EHRStatus.
     *
     * @param e
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeEHRStatus(EHRStatus e) {
        EHRStatusSerializer s = new EHRStatusSerializer();
        register(EHRSTATUS, offset);
        setOffset(s.serialize(buffer, offset, e));

        return this;
    }

    /**
     * Deserializador de EHRStatus.
     *
     * @return nova instância de EHRStatus.
     */
    @Override
    public EHRStatus deserializeEHRStatus() {
        EHRStatusSerializer d = new EHRStatusSerializer();
        return d.deserialize(buffer, getOffsetFromID(EHRSTATUS));
    }

    /**
     * Serializador de EHRAccess.
     *
     * @param e
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeEHRAccess(EHRAccess e) {
        EHRAccessSerializer s = new EHRAccessSerializer();
        register(EHRACCESS, offset);
        setOffset(s.serialize(buffer, offset, e));

        return this;
    }

    /**
     * Deserializador de EHRAccess.
     *
     * @return nova instância de EHRAccess.
     */
    @Override
    public EHRAccess deserializeEHRAccess() {
        EHRAccessSerializer d = new EHRAccessSerializer();
        return d.deserialize(buffer, getOffsetFromID(EHRACCESS));
    }

    /**
     * Serializador de XTerminology.
     *
     * @param t
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeXTerminology(XTerminology t) {
        XTerminologySerializer s = new XTerminologySerializer();
        register(XTERMINOLOGY, offset);
        setOffset(s.serialize(buffer, offset, t));

        return this;
    }

    /**
     * Deserializador de XTerminology.
     *
     * @return nova instância de XTerminology.
     */
    @Override
    public XTerminology deserializeXTerminology() {
        XTerminologySerializer d = new XTerminologySerializer();
        return d.deserialize(buffer, getOffsetFromID(XTERMINOLOGY));
    }

    /**
     * Serializador de XComposition.
     *
     * @param c
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeXComposition(XComposition c) {
        XCompositionSerializer s = new XCompositionSerializer();
        register(XCOMPOSITION, offset);
        setOffset(s.serialize(buffer, offset, c));

        return this;
    }

    /**
     * Deserializador de XComposition.
     *
     * @return nova instância de XComposition.
     */
    @Override
    public XComposition deserializeXComposition() {
        XCompositionSerializer d = new XCompositionSerializer();
        return d.deserialize(buffer, getOffsetFromID(XCOMPOSITION));
    }

    /**
     * Serializador de XComposition.
     *
     * @param c
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeXDemographics(XDemographics c) {
        XDemographicsSerializer s = new XDemographicsSerializer();
        register(XDEMOGRAPHICS, offset);
        setOffset(s.serialize(buffer, offset, c));

        return this;
    }

    /**
     * Deserializador de XComposition.
     *
     * @return nova instância de XComposition.
     */
    @Override
    public XDemographics deserializeXDemographics() {
        XDemographicsSerializer d = new XDemographicsSerializer();
        return d.deserialize(buffer, getOffsetFromID(XDEMOGRAPHICS));
    }

    /**
     * Serializador de XFolder.
     *
     * @param f
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeXFolder(XFolder f) {
        XFolderSerializer s = new XFolderSerializer();
        register(XFOLDER, offset);
        setOffset(s.serialize(buffer, offset, f));

        return this;
    }

    /**
     * Deserializador de XFolder.
     *
     * @return nova instância de XFolder.
     */
    @Override
    public XFolder deserializeXFolder() {
        XFolderSerializer d = new XFolderSerializer();
        return d.deserialize(buffer, getOffsetFromID(XFOLDER));
    }

    /**
     * Serializador de XAccessControl.
     *
     * @param a
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeXAccessControl(XAccessControl a) {
        XAccessControlSerializer s = new XAccessControlSerializer();
        register(XACCESSCONTROL, offset);
        setOffset(s.serialize(buffer, offset, a));

        return this;
    }

    /**
     * Deserializador de XAccessControl.
     *
     * @return nova instância de XAccessControl.
     */
    @Override
    public XAccessControl deserializeXAccessControl() {
        XAccessControlSerializer d = new XAccessControlSerializer();
        return d.deserialize(buffer, getOffsetFromID(XACCESSCONTROL));
    }

    /**
     * Serializador de EHRExtract.
     *
     * @param e
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeEHRExtract(EHRExtract e) {
        EHRExtractSerializer s = new EHRExtractSerializer();
        register(EHREXTRACT, offset);
        setOffset(s.serialize(buffer, offset, e));

        return this;
    }

    /**
     * Deserializador de EHRExtract.
     *
     * @return nova instância de EHRExtract.
     */
    @Override
    public EHRExtract deserializeEHRExtract() {
        EHRExtractSerializer d = new EHRExtractSerializer();
        return d.deserialize(buffer, getOffsetFromID(EHREXTRACT));
    }

    /**
     * Serializador de EHRExtract.
     *
     * @param g
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeGenericEntry(GenericEntry g) {
        GenericEntrySerializer s = new GenericEntrySerializer();
        register(GENERICENTRY, offset);
        setOffset(s.serialize(buffer, offset, g));

        return this;
    }

    /**
     * Deserializador de GenericEntry.
     *
     * @return nova instância de GenericEntry.
     */
    @Override
    public GenericEntry deserializeGenericEntry() {
        GenericEntrySerializer d = new GenericEntrySerializer();
        return d.deserialize(buffer, getOffsetFromID(GENERICENTRY));
    }

    /**
     * Serializador de MessageContent.
     *
     * @param m
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeMessageContent(MessageContent m) {
        MessageContentSerializer s = new MessageContentSerializer();
        register(MESSAGECONTENT, offset);
        setOffset(s.serialize(buffer, offset, m));

        return this;
    }

    /**
     * Deserializador de MessageContent.
     *
     * @return nova instância de MessageContent.
     */
    @Override
    public MessageContent deserializeMessageContent() {
        MessageContentSerializer d = new MessageContentSerializer();
        return d.deserialize(buffer, getOffsetFromID(MESSAGECONTENT));
    }

    /**
     * Serializador de Message.
     *
     * @param m
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeMessage(Message m) {
        MessageSerializer s = new MessageSerializer();
        register(MESSAGE, offset);
        setOffset(s.serialize(buffer, offset, m));

        return this;
    }

    /**
     * Deserializador de Message.
     *
     * @return nova instância de Message.
     */
    @Override
    public Message deserializeMessage() {
        MessageSerializer d = new MessageSerializer();
        return d.deserialize(buffer, getOffsetFromID(MESSAGE));
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

    public byte[] getBuffer(){
        return buffer.data();
    }
}
