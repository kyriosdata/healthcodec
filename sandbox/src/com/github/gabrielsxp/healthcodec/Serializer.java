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
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Gabriel
 */
public interface Serializer {

    /**
     * Serializador de DvBoolean
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     */
    RMObjectSerializationClient serializeDvBoolean(DvBoolean d);

    /**
     * Serializador de DvIdentifier
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvIdentifier(
            DvIdentifier d) throws UnsupportedEncodingException;

    /**
     * Serializador de InternetID
     *
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeInternetID(
            InternetID i) throws UnsupportedEncodingException;
    
    /**
     * Serializador de UID
     *
     * @param u
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeUID(
            UID u) throws UnsupportedEncodingException;
    
    /**
     * Serializador de ISO_OID
     *
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeISOOID(
            ISO_OID i) throws UnsupportedEncodingException;

    /**
     * Serializador de UUID
     *
     * @param u
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeUUID(
            UUID u) throws UnsupportedEncodingException;

    /**
     * Serializador de TerminologyID
     *
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeTerminologyID(
            TerminologyID t) throws UnsupportedEncodingException;

    /**
     * Serializador de GenericID
     *
     * @param g
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeGenericID(
            GenericID g) throws UnsupportedEncodingException;

    /**
     * Serializador de TemplateID
     *
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeTemplateID(
            TemplateID t) throws UnsupportedEncodingException;

    /**
     * Serializador de CodePhrase
     *
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeCodePhrase(
            CodePhrase c) throws UnsupportedEncodingException;

    /**
     * Serializador de DVURI
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDVURI(
            DVURI d) throws UnsupportedEncodingException;

    /**
     * Serializador de DVEHRURI
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDVEHRURI(
            DVEHRURI d) throws UnsupportedEncodingException;

    /**
     * Serializador de VersionTreeID
     *
     * @param v
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeVersionTreeID(
            VersionTreeID v) throws UnsupportedEncodingException;

    /**
     * Serializador de ArchetypeID
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeArchetypeID(
            ArchetypeID a) throws UnsupportedEncodingException;

    /**
     * Serializador de ObjectVersionID
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeObjectVersionID(
            ObjectVersionID o) throws UnsupportedEncodingException;

    /**
     * Serializador de HierObjectID
     *
     * @param h
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeHierObjectID(
            HierObjectID h) throws UnsupportedEncodingException;

    /**
     * Serializador de ObjectID
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeObjectID(
            ObjectID o) throws UnsupportedEncodingException;

    /**
     * Serializador de PartyRef
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializePartyRef(
            PartyRef p) throws UnsupportedEncodingException;

    /**
     * Serializador de ObjectRef
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeObjectRef(
            ObjectRef o) throws UnsupportedEncodingException;

    /**
     * Serializador de LocatableRef
     *
     * @param l
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeLocatableRef(
            LocatableRef l) throws UnsupportedEncodingException;

    /**
     * Serializador de ProportionKind
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     */
    RMObjectSerializationClient serializeProportionKind(ProportionKind p);

    /**
     * Serializador de AccessGroupRef
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     */
    RMObjectSerializationClient serializeAccessGroupRef(
            AccessGroupRef a) throws UnsupportedEncodingException;

    /**
     * Serializador de PartyIdentified
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializePartyIdentified(
            PartyIdentified p) throws UnsupportedEncodingException;

    /**
     * Serializador de Archetyped
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeArchetyped(
            Archetyped a) throws UnsupportedEncodingException;

    /**
     * Serializador de DvEncapsulated
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvEncapsulated(
            DvEncapsulated d) throws UnsupportedEncodingException;

    /**
     * Serializador de UIDBasedID
     *
     * @param u
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeUIDBasedID(
            UIDBasedID u) throws UnsupportedEncodingException;

    /**
     * Serializador de DvParsable
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvParsable(
            DvParsable d) throws UnsupportedEncodingException;

    /**
     * Serializador de DvTimeSpecification
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvTimeSpecification(
            DvTimeSpecification d) throws UnsupportedEncodingException;

    /**
     * Serializador de DvMultimedia
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvMultimedia(
            DvMultimedia d) throws UnsupportedEncodingException;

    /**
     * Serializador de DvText
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvText(
            DvText d) throws UnsupportedEncodingException;

    /**
     * Serilizador de DvCodedText
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvCodedText(
            DvCodedText d) throws UnsupportedEncodingException;

    /**
     * Serializador de TermMapping
     *
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeTermMapping(
            TermMapping t) throws UnsupportedEncodingException;

    /**
     * Serializador de Link
     *
     * @param l
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeLink(
            Link l) throws UnsupportedEncodingException;

    /**
     * Serializador de DvState
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvState(
            DvState d) throws UnsupportedEncodingException;

    /**
     * Serializador de DvParagraph
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeDvParagraph(
            DvParagraph d) throws UnsupportedEncodingException;

    /**
     * *
     * Serializador de PartyProxy
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializePartyProxy(
            PartyProxy p) throws UnsupportedEncodingException;


    /**
     * Serializador de FeederAuditDetails
     *
     * @param f
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeFeederAuditDetails(
            FeederAuditDetails f) throws UnsupportedEncodingException;
        
    /**
     * Serializador de FeederAudit
     * 
     * @param f
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeFeederAudit(
            FeederAudit f) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Locatable
     * 
     * @param l
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeLocatable(
            Locatable l) throws UnsupportedEncodingException;
    
    /**
     * Serilizador de PartyRelated
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializePartyRelated(
            PartyRelated p) throws UnsupportedEncodingException;
    
    /**
     * Serializador de PartySelf
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializePartySelf(
            PartySelf p) throws UnsupportedEncodingException;
    
    
    /**
     * Serializador de ResourceDescriptionItem
     * 
     * @param r
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeResourceDescriptionItem(
            ResourceDescriptionItem r) throws UnsupportedEncodingException;
    
    /**
     * Serializador de TranslationDetails
     * 
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeTranslationDetails(
            TranslationDetails t) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Item
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeItem(
            Item i) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Cluster
     * 
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeCluster(
            Cluster c) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Element
     * 
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeElement(
            Element e) throws UnsupportedEncodingException;
    
    /**
     * Serializador de DataStructure
     * 
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeDataStructure(
            DataStructure d) throws UnsupportedEncodingException;
    
    /**
     * Serializador de ItemList
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeItemList(
            ItemList i) throws UnsupportedEncodingException;
    
    /**
     * Serializador de ItemStructure
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeItemStructure(
            ItemStructure i) throws UnsupportedEncodingException;
    
    /**
     * Serializador de ItemSingle
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeItemSingle(
            ItemSingle i) throws UnsupportedEncodingException;
    
    /**
     * Serializador de ItemTable
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeItemTable(
            ItemTable i) throws UnsupportedEncodingException; 
    
    /**
     * Serializador de ItemTree
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeItemTree(
            ItemTree i) throws UnsupportedEncodingException; 
    
    /**
     * Serializador de PartyIdentity
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializePartyIdentity(PartyIdentity p)
                throws UnsupportedEncodingException; 
    
    /**
     * Serializador de PartyRelationship
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializePartyRelationship(
            PartyRelationship p) throws UnsupportedEncodingException; 
    
    /**
     * Serializador de Address
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeAddress(
            Address a) throws UnsupportedEncodingException; 
    
    /**
     * Serializador de Contact
     * 
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeContact(
            Contact c) throws UnsupportedEncodingException; 
    
    /**
     * Serializador de Party
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeParty(
            Party p) throws UnsupportedEncodingException; 
    
    /**
     * Serializador de Capability
     * 
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serialilzeCapability(
            Capability c) throws UnsupportedEncodingException; 
    
    /**
     * Serializador de Role
     * 
     * @param r
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeRole(
            Role r) throws UnsupportedEncodingException; 
    
    /**
     * Serializador de Actor
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws java.io.UnsupportedEncodingException
     */
    RMObjectSerializationClient serializeActor(
            Actor a) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Agent
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeAgent(
            Agent a) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Group
     * 
     * @param g
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeGroup(
            Group g) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Organisation
     * 
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeOrganisation(
            Organisation o) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Person
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializePerson(Person p)
                throws UnsupportedEncodingException;
    
    /**
     * Serializador de InstructionDetails
     * 
     * @param id
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeinstructionDetails(
            InstructionDetails id) throws UnsupportedEncodingException;
    
    /**
     * Serializador de ISMTransition
     * 
     * @param ism
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeISMTransition(
            ISMTransition ism) throws UnsupportedEncodingException;
    
    /**
     * Serializador de Activity
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     * @throws UnsupportedEncodingException 
     */
    RMObjectSerializationClient serializeActivity(
            Activity a) throws UnsupportedEncodingException;
}
