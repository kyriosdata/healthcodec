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

import com.github.gabrielsxp.healthcodec.RMObject.*;

/**
 *
 * @author Gabriel
 */
public interface Deserializer {

    /**
     * Deserializador de DvBoolean
     *
     * @return Instância de DvBoolean
     */
    DvBoolean deserializeDvBoolean();

    /**
     * Deserializador de DvIdentifier
     *
     * @return Instância de DvIdentifier
     */
    DvIdentifier deserializeDvIdentifier();

    /**
     * Deserializador de InternetID
     *
     * @return Instância de InternetID
     */
    InternetID deserializeInternetID();

    /**
     * Deserializador de ISO_OID
     *
     * @return Instância de ISO_OID
     */
    ISO_OID deserializeISOOID();

    /**
     * Deserializador de UUID
     *
     * @return Instância de UUID
     */
    UUID deserializeUUID();

    /**
     * Deserializador de TerminologyID
     *
     * @return Instância de TerminologyID
     */
    TerminologyID deserializeTerminologyID();

    /**
     * Deserializador de GenericID
     *
     * @return Instância de GenericID
     */
    GenericID deserializeGenericID();

    /**
     * Deserializador de TemplateID
     *
     * @return Instância de TemplateID
     */
    TemplateID deserializeTemplateID();

    /**
     * Deserializador de CodePhrase
     *
     * @return Instância de CodePhrase
     */
    CodePhrase deserializeCodePhrase();

    /**
     * Deserializador de DVURI
     *
     * @return Instância de DVURI
     */
    DVURI deserializeDVURI();

    /**
     * Deserializador de DVEHRURI
     *
     * @return Instância de DVEHRURI
     */
    DVEHRURI deserializeDVEHRURI();

    /**
     * Deserializador de VersionTreeID
     *
     * @return Instância de VersionTreeID
     */
    VersionTreeID deserializeVersionTreeID();

    /**
     * Deserializador de ArchetypeID
     *
     * @return Instância de ArchetypeID
     */
    ArchetypeID deserializeArchetypeID();

    /**
     * Deserializador de ObjectVersionID
     *
     * @return Instância de ObjectVersionID
     */
    ObjectVersionID deserializeObjectVersionID();

    /**
     * Deserializador de HierObjectID
     *
     * @return Instância de HierObjectID
     */
    HierObjectID deserializeHierObjectID();

    /**
     * Deserializador de ObjectID
     *
     * @return Instância de ObjectID
     */
    ObjectID deserializeObjectID();

    /**
     * Deserializador de PartyRef
     *
     * @return Instância de PartyRef
     */
    PartyRef deserializePartyRef();

    /**
     * Deserializador de ObjectRef
     *
     * @return Instância de ObjectRef
     */
    ObjectRef deserializeObjectRef();

    /**
     * Deserializador de LocatableRef
     *
     * @return Instância de LocatableRef
     */
    LocatableRef deserializeLocatableRef();

    /**
     * Deserializador de ProportionKind
     *
     * @return Instância de ProportionKind
     */
    ProportionKind deserializeProportionKind();

    /**
     * Deserializador de AccessGroupRef
     *
     * @return instância de AccessGroupRef
     */
    AccessGroupRef deserializeAccessGroupRef();

    /**
     * Deserializador de PartyIdentified
     *
     * @return instância de PartyIdentified
     */
    PartyIdentified deserializePartyIdentified();

    /**
     * Deserializador de Archetyped
     *
     * @return instância de Archetyped
     */
    Archetyped deserializeArchetyped();

    /**
     * Deserializador de DvEncapsulated
     *
     * @return instância de DvEncapsulated
     */
    DvEncapsulated deserializeDvEncapsulated();

    /**
     * Deserializador de UIDBasedID
     *
     * @return instância de DvEncapsulated
     */
    UIDBasedID deserializeUIDBasedID();

    /**
     * Deserializador de DvParsable
     *
     * @return instância de DvParsable
     */
    DvParsable deserializeDvParsable();

    /**
     * Deserializador de DvTimeSpecification
     *
     * @return instância de DvParsable
     */
    DvTimeSpecification deserializeDvTimeSpecification();

    /**
     * Deserializador de DvMultimedia
     *
     * @return instância de DvMultimedia
     */
    DvMultimedia deserializeDvMultimedia();
    
    /**
     * Deserializador de DvText
     * 
     * @return instância de DvText
     */
    DvText deserializeDvText();
    
    /**
     * Deserializador de DvCodedText
     * 
     * @return instância de DvText
     */
    DvCodedText deserializeDvCodedText();
    
    /**
     * Deserializador de TermMapping
     * 
     * @return instância de TermMapping;
     */
    TermMapping deserializeTermMapping();
    
    /**
     * Deserializador de Link
     * @return nova instância de Link
     */
    Link deserializeLink();
    
    /**
     * Deserializador de DvState
     * @return nova instância de DvState
     */
    DvState deserializaDvState();
    
    /**
     * Deserializador de DvParagraph
     * @return nova instância de DvParagraph
     */
    DvParagraph deserializeDvParagraph();
    
    /**
     * Deserializador de PartyProxy
     * @return nova instância de PartyProxy
     */
    PartyProxy deserializePartyProxy();
    
    /**
     * Deserializador de FeederAuditDetails
     * @return nova instância de FeederAuditDetails
     */
    FeederAuditDetails deserializeFeederAuditDetails();
    
    /**
     * Deserializador de FeederAudit
     * @return nova instância de FeederAudit
     */
    FeederAudit deserializeFeederAudit();
    
    /**
     * Deserializador de Locatable
     * @return nova instância de Locatable
     */
    Locatable deserializeLocatable();
    
    /**
     * Deserilizador de PartyRelated
     * @return nova instância de PartyRelated
     */
    PartyRelated deserializePartyRelated();
    
    /**
     * Deserializador de PartySelf
     * @return nova instância de PartySelf
     */
    PartySelf deserializePartySelf();
    
    /**
     * Deserializador de ResourceDescriptionItem
     * @return nova instância de ResourceDescriptionItem
     */
    ResourceDescriptionItem deserializeResourceDescriptionItem();
    
    /**
     * Deserializador de TranslationDetails
     * @return nova instância de TranslationDetails
     */
    TranslationDetails deserializeTranslationDetails();
    
    /**
     * Deserializador de Item
     * @return nova instância de Item
     */
    Item deserializeItem();
    
    /**
     * Deserializador de Cluster
     * @return nova instância de Cluster
     */
    Cluster deserializeCluster();
    
    /**
     * Deserializador de Element
     * @return nova instância de Element
     */
    Element deserializeElement();
}
