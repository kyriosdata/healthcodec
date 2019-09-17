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
package main.java.com.github.gabrielsxp.healthcodec;

import java.io.UnsupportedEncodingException;

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

}
