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
import com.github.gabrielsxp.healthcodec.RMObject.Archetyped;
import com.github.gabrielsxp.healthcodec.RMObject.Cluster;
import com.github.gabrielsxp.healthcodec.RMObject.CodePhrase;
import com.github.gabrielsxp.healthcodec.RMObject.DVEHRURI;
import com.github.gabrielsxp.healthcodec.RMObject.DVURI;
import com.github.gabrielsxp.healthcodec.RMObject.DvCodedText;
import com.github.gabrielsxp.healthcodec.RMObject.DvEncapsulated;
import java.io.UnsupportedEncodingException;
import java.util.List;
import com.github.gabrielsxp.healthcodec.RMObject.DvIdentifier;
import com.github.gabrielsxp.healthcodec.RMObject.DvMultimedia;
import com.github.gabrielsxp.healthcodec.RMObject.DvParagraph;
import com.github.gabrielsxp.healthcodec.RMObject.DvParsable;
import com.github.gabrielsxp.healthcodec.RMObject.DvState;
import com.github.gabrielsxp.healthcodec.RMObject.DvText;
import com.github.gabrielsxp.healthcodec.RMObject.Element;
import com.github.gabrielsxp.healthcodec.RMObject.FeederAudit;
import com.github.gabrielsxp.healthcodec.RMObject.FeederAuditDetails;
import com.github.gabrielsxp.healthcodec.RMObject.Item;
import com.github.gabrielsxp.healthcodec.RMObject.Link;
import com.github.gabrielsxp.healthcodec.RMObject.Locatable;
import com.github.gabrielsxp.healthcodec.RMObject.Match;
import com.github.gabrielsxp.healthcodec.RMObject.ObjectID;
import com.github.gabrielsxp.healthcodec.RMObject.ObjectVersionID;
import com.github.gabrielsxp.healthcodec.RMObject.PartyIdentified;
import com.github.gabrielsxp.healthcodec.RMObject.PartyProxy;
import com.github.gabrielsxp.healthcodec.RMObject.PartyRef;
import com.github.gabrielsxp.healthcodec.RMObject.PartyRelated;
import com.github.gabrielsxp.healthcodec.RMObject.PartySelf;
import com.github.gabrielsxp.healthcodec.RMObject.ResourceDescriptionItem;
import com.github.gabrielsxp.healthcodec.RMObject.TemplateID;
import com.github.gabrielsxp.healthcodec.RMObject.TerminologyID;
import com.github.gabrielsxp.healthcodec.RMObject.TranslationDetails;
import com.github.gabrielsxp.healthcodec.RMObject.UIDBasedID;
import java.util.Map;
import java.util.Set;

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
     * @param terminologyId
     * @param codeString
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeCodePhrase(
            TerminologyID terminologyId,
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
     * @param id
     * @param value
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializePartyRef(ObjectID id, String value)
            throws UnsupportedEncodingException;

    /**
     * Serializador de ObjectRef
     *
     * @param id
     * @param namespace
     * @param type
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeObjectRef(
            ObjectID id,
            String namespace,
            String type) throws UnsupportedEncodingException;

    /**
     * Serializador de LocatableRef
     *
     * @param id
     * @param namespace
     * @param type
     * @param path (opcional)
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeLocatableRef(
            ObjectVersionID id,
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
     * @param id
     * @param value
     * @param name
     * @param identifiers
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializePartyIdentified(
            PartyRef externalRef,
            String name,
            List<DvIdentifier> identifiers)
            throws UnsupportedEncodingException;

    /**
     * Serializador de Archetyped
     *
     * @param archetypedId
     * @param rmVersionLength
     * @param templateId
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
     * @param charset
     * @param language
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

    /**
     * Serializador de Multimedia
     *
     * @param dvMultimediaDvEncapsulated
     * @param alternateText
     * @param mediaType
     * @param compressionAlgorithm
     * @param integrityCheck
     * @param integrityCheckAlgorithm
     * @param thumbnail
     * @param uri
     * @param data
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvMultimedia(
            DvEncapsulated dvMultimediaDvEncapsulated,
            String alternateText,
            CodePhrase mediaType,
            CodePhrase compressionAlgorithm,
            byte[] integrityCheck,
            CodePhrase integrityCheckAlgorithm,
            DvMultimedia thumbnail,
            DVURI uri,
            byte[] data) throws UnsupportedEncodingException;

    /**
     * Serializador de DvText
     *
     * @param value
     * @param mappings
     * @param formatting
     * @param hyperlink
     * @param language
     * @param charset
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvText(String value,
            List<RMObject.TermMapping> mappings,
            String formatting,
            DVURI hyperlink,
            CodePhrase language,
            CodePhrase charset) throws UnsupportedEncodingException;

    /**
     * Serilizador de DvCodedText
     *
     * @param dvText
     * @param definingCode
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvCodedText(DvText dvText,
            CodePhrase definingCode) throws UnsupportedEncodingException;

    /**
     * Serializador de TermMapping
     *
     * @param target
     * @param match
     * @param purpose
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeTermMapping(CodePhrase target,
            Match match,
            DvCodedText purpose) throws UnsupportedEncodingException;

    /**
     * Serializador de Link
     *
     * @param meaning
     * @param type
     * @param target
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeLink(DvText meaning, DvText type,
            DVEHRURI target) throws UnsupportedEncodingException;

    /**
     * Serializador de Link
     *
     * @param link
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeLink(
            Link link) throws UnsupportedEncodingException;

    /**
     * Serializador de DvState
     *
     * @param value
     * @param terminal
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvState(
            DvCodedText value,
            String terminal) throws UnsupportedEncodingException;

    /**
     * Serializador de DvState
     *
     * @param dvState
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvState(
            DvState dvState) throws UnsupportedEncodingException;

    /**
     * Serializador de DvParagraph
     *
     * @param items
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvParagraph(
            List<DvText> items) throws UnsupportedEncodingException;

    /**
     * Serializador de DvParagraph
     *
     * @param dvparagraph
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvParagraph(
            DvParagraph dvparagraph) throws UnsupportedEncodingException;

    /**
     * Serializador de PartyProxy
     *
     * @param externalRef
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializePartyProxy(
            PartyRef externalRef) throws UnsupportedEncodingException;

    /**
     * *
     * Serializador de PartyProxy
     *
     * @param partyProxy
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializePartyProxy(
            PartyProxy partyProxy) throws UnsupportedEncodingException;

    /**
     * Serializador de FeederAuditDetails
     *
     * @param systemID
     * @param provider
     * @param location
     * @param subject
     * @param versionID
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeFeederAuditDetails(String systemID,
            PartyIdentified provider, PartyIdentified location,
            /*DvDateTime time,*/ PartyProxy subject,
            String versionID) throws UnsupportedEncodingException;

    /**
     * Serializador de FeederAuditDetails
     *
     * @param fad
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeFeederAuditDetails(
            FeederAuditDetails fad) throws UnsupportedEncodingException;
    
    /**
     * Serializador de FeederAudit
     * 
     * @param originatingSystemAudit
     * @param originatingSystemItemIDs
     * @param feederSystemAudit
     * @param feederSystemItemIDs
     * @param originalContent
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeFeederAudit(
            FeederAuditDetails originatingSystemAudit,
            List<DvIdentifier> originatingSystemItemIDs,
            FeederAuditDetails feederSystemAudit,
            List<DvIdentifier> feederSystemItemIDs,
            DvEncapsulated originalContent) throws UnsupportedEncodingException;
    
    /**
     * Serializador de FeederAudit
     * @param fa
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeFeederAudit(
            FeederAudit fa) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Locatable
     * @param uid
     * @param archetypeNodeId
     * @param name
     * @param archetypeDetails
     * @param feederAudit
     * @param links
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeLocatable(UIDBasedID uid, 
            String archetypeNodeId, DvText name, Archetyped archetypeDetails,
            FeederAudit feederAudit, 
            Set<Link> links) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Locatable
     * 
     * @param locatable
     * @return
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeLocatable(
            Locatable locatable) throws UnsupportedEncodingException;
    
    /**
     * Serilizador de PartyRelated
     * @param pr
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializePartyRelated(
            PartyRelated pr) throws UnsupportedEncodingException;
    
    /**
     * Serializador de PartyRelated
     * 
     * @param pi
     * @param relationship
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializePartyRelated(
            PartyIdentified pi, 
            DvCodedText relationship) throws UnsupportedEncodingException;
    
    /**
     * Serializador de PartySelf
     * 
     * @param externalRef
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializePartySelf(
            PartyRef externalRef) throws UnsupportedEncodingException;
    
    /**
     * Serializador de PartySelf
     * 
     * @param ps
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializePartySelf(
            PartySelf ps) throws UnsupportedEncodingException;
    
    /**
     * Serializador de ResourceDescriptionItem
     * @param language
     * @param purpose
     * @param keywords
     * @param use
     * @param misuse
     * @param copyright
     * @param originalResourceUri
     * @param otherDetails
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeResourceDescriptionItem(
            CodePhrase language, String purpose, List<String> keywords, 
            String use, String misuse, String copyright, 
            Map<String, String> originalResourceUri, 
            Map<String, String> otherDetails) 
                throws UnsupportedEncodingException;
    
    /**
     * Serializador de ResourceDescriptionItem
     * @param rdi
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeResourceDescriptionItem(
            ResourceDescriptionItem rdi) throws UnsupportedEncodingException;
    
    /**
     * Serializador de TranslationDetails
     * @param td
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeTranslationDetails(
            TranslationDetails td) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Item
     * @param item
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeItem(
            Item item) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Cluster
     * @param cluster
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeCluster(
            Cluster cluster) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Element
     * @param element
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeElement(
            Element element) throws UnsupportedEncodingException;
}
