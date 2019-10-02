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

import com.github.gabrielsxp.healthcodec.RMObject.ArchetypeID;
import com.github.gabrielsxp.healthcodec.RMObject.CodePhrase;
import java.io.UnsupportedEncodingException;
import java.util.List;
import com.github.gabrielsxp.healthcodec.RMObject.DvIdentifier;
import com.github.gabrielsxp.healthcodec.RMObject.DvParsable;
import com.github.gabrielsxp.healthcodec.RMObject.ObjectID;
import com.github.gabrielsxp.healthcodec.RMObject.TemplateID;

/**
 *
 * @author Gabriel
 */
public interface Serializer {

    /**
     * Serializador de DvBoolean
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvBoolean(boolean value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de DvIdentifier
     *
     * @param issuer
     * @param assigner
     * @param id
     * @param type
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvIdentifier(
            String issuer,
            String assigner,
            String id,
            String type) throws UnsupportedEncodingException;

    /**
     * Serializador de InternetID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeInternetID(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de ISO_OID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeISOOID(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de UUID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeUUID(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de TerminologyID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeTerminologyID(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de GenericID
     *
     * @param value
     * @param scheme
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeGenericID(
            String value,
            String scheme) throws UnsupportedEncodingException;

    /**
     * Serializador de TemplateID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeTemplateID(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de CodePhrase
     *
     * @param terminologyIDValue
     * @param codeString
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeCodePhrase(
            String terminologyIDValue,
            String codeString) throws UnsupportedEncodingException;

    /**
     * Serializador de DVURI
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDVURI(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de DVEHRURI
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDVEHRURI(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de VersionTreeID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeVersionTreeID(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de ArchetypeID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeArchetypeID(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de ObjectVersionID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeObjectVersionID(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de ObjectID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeHierObjectID(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de ObjectID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeObjectID(String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de PartyRef
     *
     * @param oidValue String value de ObjectID
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializePartyRef(String oidValue, String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de ObjectRef
     *
     * @param oidValue String value de ObjectID
     * @param namespace
     * @param type
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeObjectRef(
            String oidValue,
            String namespace,
            String type) throws UnsupportedEncodingException;

    /**
     * Serializador de LocatableRef
     *
     * @param oidValue
     * @param namespace
     * @param type
     * @param path (opcional)
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeLocatableRef(
            String oidValue,
            String namespace,
            String type,
            String path) throws UnsupportedEncodingException;

    /**
     * Serializador de LocatableRef
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     */
    RMObjectSerializationClient serializeProportionKind(int value);

    /**
     * Serializador de AccessGroupRef
     *
     * @param id
     * @return Instância de RMObjectSerializationClient para chaining
     */
    RMObjectSerializationClient serializeAccessGroupRef(ObjectID id);

    /**
     * Serializador de PartyIdentified
     *
     * @param oidValue
     * @param value
     * @param name
     * @param identifiers
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializePartyIdentified(String oidValue,
            String value,
            String name,
            List<DvIdentifier> identifiers)
            throws UnsupportedEncodingException;

    /**
     * Serializador de Archetyped
     *
     * @param archetypeIDValue
     * @param rmVersionLength
     * @param templateIDValue
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeArchetyped(ArchetypeID archetypedId,
            TemplateID templateId,
            String rmVersionLength) throws UnsupportedEncodingException;

    /**
     * Serializador de DvEncapsulated
     *
     * @param charset
     * @param language
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvEncapsulated(
            CodePhrase charset,
            CodePhrase language) throws UnsupportedEncodingException;

    /**
     * Serializador de UIDBasedID
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeUIDBasedID(
            String value) throws UnsupportedEncodingException;

    /**
     * Serializador de DvParsable
     *
     * @param codePhraseCharsetTerminologyIDValue
     * @param charsetCodeString
     * @param codePhraseLanguageTerminologyIDValue
     * @param languageCodeString
     * @param value
     * @param formalism
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvParsable(
            CodePhrase charset,
            CodePhrase language,
            String value,
            String formalism) throws UnsupportedEncodingException;
    
    /**
     * Serializador de DvTimeSpecification
     *
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvTimeSpecification(
            DvParsable value) throws UnsupportedEncodingException;
}
