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
import java.nio.ReadOnlyBufferException;
import java.util.List;

/**
 *
 * @author Gabriel Classe responsável por fornecer os métodos necessários para o
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
     * Serializa o atributo boolean value de DvBoolean
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvBoolean(boolean value) {
        DvBooleanSerializer s = new DvBooleanSerializer();
        register(DVBOOLEAN, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     * Deserializa DvBoolean
     *
     * @return db instância de DvBoolean
     */
    @Override
    public DvBoolean deserializeDvBoolean() {
        DvBooleanSerializer d = new DvBooleanSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVBOOLEAN));
    }

    /**
     * Serializa todas as Strings obrigatórias de DvIdentifier
     *
     * @param issuer
     * @param assigner
     * @param id
     * @param type
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeDvIdentifier(
            String issuer,
            String assigner,
            String id,
            String type
    ) throws UnsupportedEncodingException {
        DvIdentifierSerializer s = new DvIdentifierSerializer();
        register(DVIDENTIFIER, offset);
        setOffset(s.serialize(buffer, offset, issuer, assigner, id, type));

        return this;
    }

    /**
     * Deserializa DvIdentifier
     *
     * @return di instância de DvIdentifier
     */
    @Override
    public DvIdentifier deserializeDvIdentifier() {
        DvIdentifierSerializer d = new DvIdentifierSerializer();
        return d.deserialize(
                buffer, getOffsetFromID(DVIDENTIFIER)
        );
    }

    /**
     * Serializa a String value de InternetID
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeInternetID(String value)
            throws UnsupportedEncodingException {

        InternetIDSerializer s = new InternetIDSerializer();
        register(INTERNETID, offset);
        setOffset(s.serialize(buffer, offset, value));
        return this;
    }

    /**
     * Deserializa InternetID
     *
     * @return iid instância de InternetID
     */
    @Override
    public InternetID deserializeInternetID() {
        InternetIDSerializer d = new InternetIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(INTERNETID));
    }

    /**
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeISOOID(String value)
            throws UnsupportedEncodingException {
        ISOOIDSerialilzer s = new ISOOIDSerialilzer();
        register(ISO_OID, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     * Deserializa ISO_OID
     *
     * @return is nova instância de ISO_OID
     */
    @Override
    public ISO_OID deserializeISOOID() {
        ISOOIDSerialilzer d = new ISOOIDSerialilzer();
        return d.deserialize(buffer, getOffsetFromID(ISO_OID));
    }

    /**
     * Serializa a String value de UUID
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeUUID(String value)
            throws UnsupportedEncodingException {
        UUIDSerializer s = new UUIDSerializer();
        register(UUID, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public UUID deserializeUUID() {
        UUIDSerializer d = new UUIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(UUID));
    }

    /**
     * Serializa os atributos de TerminologyID
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeTerminologyID(String value)
            throws UnsupportedEncodingException {
        TerminologyIDSerializer s = new TerminologyIDSerializer();
        register(TERMINOLOGYID, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     * Deserializa TerminologyID
     *
     * @return ti nova instância de TerminologyID
     */
    @Override
    public TerminologyID deserializeTerminologyID() {
        TerminologyIDSerializer d = new TerminologyIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(TERMINOLOGYID));
    }

    /**
     * Serializa os atributos de GenericID
     *
     * @param value
     * @param scheme
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeGenericID(String value, String scheme)
            throws UnsupportedEncodingException {
        GenericIDSerializer s = new GenericIDSerializer();
        register(GENERICID, offset);
        setOffset(s.serialize(buffer, offset, value, scheme));

        return this;
    }

    /**
     * Deserializa GenericID
     *
     * @return gi nova instância de GenericID
     */
    @Override
    public GenericID deserializeGenericID() {
        GenericIDSerializer d = new GenericIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(GENERICID));
    }

    /**
     * Serializa os atributos de TemplateID
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeTemplateID(String value)
            throws UnsupportedEncodingException {
        TemplateIDSerializer s = new TemplateIDSerializer();
        register(TEMPLATEID, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     * Deserializa TemplateID
     *
     * @return nova instância de TemplateID
     */
    @Override
    public TemplateID deserializeTemplateID() {
        TemplateIDSerializer d = new TemplateIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(TEMPLATEID));
    }

    /**
     * Serializa os atributos de TerminologyID
     *
     * @param terminologyIDValue
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeCodePhrase(
            String terminologyIDValue,
            String value)
            throws UnsupportedEncodingException {
        CodePhraseSerializer s = new CodePhraseSerializer();
        register(CODEPHRASE, offset);
        setOffset(s.serialize(buffer, offset, terminologyIDValue, value));

        return this;
    }

    /**
     * Deserializa CodePhrase
     *
     * @return cp nova instância de CodePhrase
     */
    @Override
    public CodePhrase deserializeCodePhrase() {
        CodePhraseSerializer d = new CodePhraseSerializer();
        return d.deserialize(buffer, getOffsetFromID(CODEPHRASE));
    }

    /**
     * Serializa os atributos de DVURI
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDVURI(String value)
            throws UnsupportedEncodingException {
        DVURISerializer s = new DVURISerializer();
        register(DVURI, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     * Deserializa DVURI
     *
     * @return du nova instância de DVURI
     */
    @Override
    public DVURI deserializeDVURI() {
        DVURISerializer d = new DVURISerializer();
        return d.deserialize(buffer, getOffsetFromID(DVURI));
    }

    /**
     * Serializa os atributos de DVEHRURI
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeDVEHRURI(String value)
            throws UnsupportedEncodingException {
        DVEHRURISerializer s = new DVEHRURISerializer();
        register(DVEHRURI, offset);
        s.serialize(buffer, offset, value);

        return this;
    }

    /**
     * Deserializa DVEHRURI
     *
     * @return nova instânciade DVEHRURI
     */
    @Override
    public DVEHRURI deserializeDVEHRURI() {
        DVEHRURISerializer d = new DVEHRURISerializer();
        return d.deserialize(buffer, getOffsetFromID(DVEHRURI));
    }

    /**
     * Serializa os atributos de VersionTreeID
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeVersionTreeID(String value)
            throws UnsupportedEncodingException {
        VersionTreeIDSerializer s = new VersionTreeIDSerializer();
        register(VERSIONTREEID, offset);
        s.serialize(buffer, offset, value);

        return this;
    }

    /**
     * Deserializa VersionTreeID
     *
     * @return vi nova instânciad e VersionTreeID
     */
    @Override
    public VersionTreeID deserializeVersionTreeID() {
        VersionTreeIDSerializer d = new VersionTreeIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(VERSIONTREEID));
    }

    /**
     * Serializa os atributos de ArchetypeID
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeArchetypeID(String value)
            throws UnsupportedEncodingException {
        ArchetypeIDSerializer s = new ArchetypeIDSerializer();
        register(ARCHETYPEID, offset);
        s.serialize(buffer, offset, value);

        return this;
    }

    /**
     * Deserializa ArchetypeID
     *
     * @return ai nova instância de ArchetypeID
     */
    @Override
    public ArchetypeID deserializeArchetypeID() {
        ArchetypeIDSerializer d = new ArchetypeIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(ARCHETYPEID));
    }

    /**
     * Serializa os atributos de ObjectVersionID
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeObjectVersionID(String value)
            throws UnsupportedEncodingException {
        ObjectVersionIDSerializer s = new ObjectVersionIDSerializer();
        register(OBJECTVERSIONID, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     * Deserializa ObjectVersionID
     *
     * @return
     */
    @Override
    public ObjectVersionID deserializeObjectVersionID() {
        ObjectVersionIDSerializer d = new ObjectVersionIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(OBJECTVERSIONID));
    }

    /**
     * Serializa os atributos de HierObjectID
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeHierObjectID(String value)
            throws UnsupportedEncodingException {
        HierObjectIDSerialilzer s = new HierObjectIDSerialilzer();
        register(HIEROBJECTID, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     * Deserializa HierObjectID
     *
     * @return hi nova instância de HierObjectID
     */
    @Override
    public HierObjectID deserializeHierObjectID() {
        HierObjectIDSerialilzer d = new HierObjectIDSerialilzer();
        return d.deserialize(buffer, getOffsetFromID(HIEROBJECTID));
    }

    /**
     * Serializa ObjectID
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeObjectID(String value)
            throws UnsupportedEncodingException {
        ObjectIDSerializer s = new ObjectIDSerializer();
        register(OBJECTID, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     * Deserializa ObjectID
     *
     * @return oid Nova instância de ObjectID
     */
    @Override
    public ObjectID deserializeObjectID() {
        ObjectIDSerializer d = new ObjectIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(OBJECTID));
    }

    /**
     * Serializa PartyRef
     *
     * @param oidValue
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializePartyRef(
            String oidValue,
            String value) throws UnsupportedEncodingException {
        PartyRefSerializer s = new PartyRefSerializer();
        register(PARTYREF, offset);
        setOffset(s.serialize(buffer, offset, oidValue, value));

        return this;
    }

    /**
     * Deserializa PartyRef
     *
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public PartyRef deserializePartyRef() {
        PartyRefSerializer d = new PartyRefSerializer();
        return d.deserialize(buffer, getOffsetFromID(PARTYREF));
    }

    /**
     * Serializa ObjectRef
     *
     * @param oidValue
     * @param namespace
     * @param type
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeObjectRef(
            String oidValue,
            String namespace, String type) throws UnsupportedEncodingException {
        ObjectRefSerializer s = new ObjectRefSerializer();
        register(OBJECTREF, offset);
        setOffset(s.serialize(buffer, offset, oidValue, namespace, type));

        return this;
    }

    /**
     * Deserializa ObjectRef
     *
     * @return or instância de ObjectRef
     */
    @Override
    public ObjectRef deserializeObjectRef() {
        ObjectRefSerializer d = new ObjectRefSerializer();
        return d.deserialize(buffer, getOffsetFromID(OBJECTREF));
    }

    /**
     * Serializa LocatableRef
     *
     * @param oidValue
     * @param namespace
     * @param type
     * @param path (opcional)
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeLocatableRef(
            String oidValue,
            String namespace,
            String type,
            String path) throws UnsupportedEncodingException {
        LocatableRefSerializer s = new LocatableRefSerializer();
        register(LOCATABLEREF, offset);
        setOffset(s.serialize(buffer, offset, oidValue, namespace, type, path));

        return this;
    }

    /**
     * Deserializa PartyRef
     *
     * @return pr nova instância de PartyRef
     */
    @Override
    public LocatableRef deserializeLocatableRef() {
        LocatableRefSerializer d = new LocatableRefSerializer();
        return d.deserialize(buffer, getOffsetFromID(LOCATABLEREF));
    }

    /**
     * Serializa ProportionKind
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeProportionKind(int value) {
        ProportionKindSerializer s = new ProportionKindSerializer();
        register(PROPORTIONKIND, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     * Deserializa ProportionKind
     *
     * @return nova instância de ProportionKind
     */
    @Override
    public ProportionKind deserializeProportionKind() {
        ProportionKindSerializer d = new ProportionKindSerializer();
        return d.deserialize(buffer, getOffsetFromID(PROPORTIONKIND));
    }

    /**
     * Serializa AccessGroupRef
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     */
    @Override
    public RMObjectSerializationClient serializeAccessGroupRef(String oidValue) {
        AccessGroupRefSerializer s = new AccessGroupRefSerializer();
        register(ACCESSGROUPREF, offset);
        setOffset(s.serializer(buffer, offset, oidValue));

        return this;
    }

    /**
     * Deserializa AccessGroupRef
     *
     * @return nova instância de PartyIdentified
     */
    @Override
    public AccessGroupRef deserializeAccessGroupRef() {
        AccessGroupRefSerializer d = new AccessGroupRefSerializer();
        return d.deserialize(buffer, getOffsetFromID(ACCESSGROUPREF));
    }

    /**
     * Serializa PartyIdentified
     *
     * @param oidValue
     * @param value
     * @param name
     * @param identifiers
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializePartyIdentified(String oidValue,
            String value,
            String name,
            List<DvIdentifier> identifiers)
            throws UnsupportedEncodingException {
        PartyIdentifiedSerializer s = new PartyIdentifiedSerializer();
        register(PARTYIDENTIFIED, offset);
        setOffset(
            s.serializer(buffer, offset,oidValue, value, name, identifiers));

        return this;
    }

    /**
     * Deserializa PartyIdentified
     *
     * @return nova instância de PartyIdentified
     */
    @Override
    public PartyIdentified deserializePartyIdentified() {
        PartyIdentifiedSerializer d = new PartyIdentifiedSerializer();
        return d.deserializer(buffer, getOffsetFromID(PARTYIDENTIFIED));
    }
    
    /**
     * Serializa Archetyped
     * 
     * @param archetypeIDValue
     * @param templateIDValue
     * @param rmVersionLength
     * @return
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeArchetyped(
            String archetypeIDValue, 
            String templateIDValue, 
            String rmVersionLength) throws UnsupportedEncodingException {
        ArchetypedSerializer s = new ArchetypedSerializer();
        register(ARCHETYPED, offset);
        setOffset(s.serializer(buffer, offset, 
                archetypeIDValue, templateIDValue, rmVersionLength));
        
        return this;
    }
    
    /**
     * Deserializa Archetyped
     * @return nova instância de Archetyped
     */
    @Override
    public Archetyped deserializeArchetyped() {
        ArchetypedSerializer d = new ArchetypedSerializer();
        return d.deserialize(buffer, getOffsetFromID(ARCHETYPED));
    }
    
    /**
     * 
     * @param codePhraseCharsetTerminologyIDValue
     * @param charsetCodeString
     * @param codePhraseLanguageTerminologyIDValue
     * @param languageCodeString
     * @return
     * @throws UnsupportedEncodingException 
     */
    @Override
    public RMObjectSerializationClient serializeDvEncapsulated(
            String codePhraseCharsetTerminologyIDValue, 
            String charsetCodeString, 
            String codePhraseLanguageTerminologyIDValue, 
            String languageCodeString) throws UnsupportedEncodingException {
        DvEncapsulatedSerializer s = new DvEncapsulatedSerializer();
        register(DVENCAPSULATED, offset);
        setOffset(s.serialize(
                buffer, 
                offset, 
                codePhraseCharsetTerminologyIDValue, 
                charsetCodeString, 
                codePhraseLanguageTerminologyIDValue, 
                languageCodeString)
        );
        
        return this;
    }
    
    /**
     * Deserializa DvEncapsulated
     * @return 
     */
    @Override
    public DvEncapsulated deserializeDvEncapsulated() {
        DvEncapsulatedSerializer d = new DvEncapsulatedSerializer();
        return d.deserialize(buffer, getOffsetFromID(DVENCAPSULATED));
    }

    /**
     * Serializa UIDBasedID
     *
     * @param value
     * @return instância de RMObjectSerializationClient atual
     * @throws UnsupportedEncodingException
     */
    @Override
    public RMObjectSerializationClient serializeUIDBasedID(String value)
            throws UnsupportedEncodingException {
        UIDBasedIDSerializer s = new UIDBasedIDSerializer();
        register(UIDBASEDID, offset);
        setOffset(s.serialize(buffer, offset, value));

        return this;
    }

    /**
     * Deserializa UIDBasedID
     *
     * @return nova instância de UIDBasedID
     */
    @Override
    public UIDBasedID deserializeUIDBasedID() {
        UIDBasedIDSerializer d = new UIDBasedIDSerializer();
        return d.deserialize(buffer, getOffsetFromID(UIDBASEDID));
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
     * @return
     */
    private int getOffsetFromID(RMObjectID id) {
        return index.getItemPosition(
                Index.createKey(
                        id.name(),
                        order[id.getValue()]
                )
        );
    }

    private void setOffset(int pos) {
        this.offset = pos;
    }

    public byte[] getBuffer() {
        return this.buffer.data();
    }
}
