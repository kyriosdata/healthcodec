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
     *
     */
    RMObjectSerializationClient serializeDvIdentifier(DvIdentifier d);

    /**
     * Serializador de InternetID
     *
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeInternetID(InternetID i);
    
    /**
     * Serializador de UID
     *
     * @param u
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeUID(UID u);
    
    /**
     * Serializador de ISO_OID
     *
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeISOOID(ISO_OID i);

    /**
     * Serializador de UUID
     *
     * @param u
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeUUID(UUID u);

    /**
     * Serializador de TerminologyID
     *
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeTerminologyID(TerminologyID t);

    /**
     * Serializador de GenericID
     *
     * @param g
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeGenericID(
            GenericID g);

    /**
     * Serializador de TemplateID
     *
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeTemplateID(
            TemplateID t);

    /**
     * Serializador de CodePhrase
     *
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeCodePhrase(
            CodePhrase c);

    /**
     * Serializador de DVURI
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDVURI(
            DVURI d);

    /**
     * Serializador de DVEHRURI
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDVEHRURI(
            DVEHRURI d);

    /**
     * Serializador de VersionTreeID
     *
     * @param v
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeVersionTreeID(
            VersionTreeID v);

    /**
     * Serializador de ArchetypeID
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeArchetypeID(
            ArchetypeID a);

    /**
     * Serializador de ObjectVersionID
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeObjectVersionID(
            ObjectVersionID o);

    /**
     * Serializador de HierObjectID
     *
     * @param h
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeHierObjectID(
            HierObjectID h);

    /**
     * Serializador de ObjectID
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeObjectID(
            ObjectID o);

    /**
     * Serializador de PartyRef
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializePartyRef(
            PartyRef p);

    /**
     * Serializador de ObjectRef
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeObjectRef(
            ObjectRef o);

    /**
     * Serializador de LocatableRef
     *
     * @param l
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeLocatableRef(
            LocatableRef l);

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
            AccessGroupRef a);

    /**
     * Serializador de PartyIdentified
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializePartyIdentified(
            PartyIdentified p);

    /**
     * Serializador de Archetyped
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeArchetyped(
            Archetyped a);

    /**
     * Serializador de DvEncapsulated
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvEncapsulated(
            DvEncapsulated d);

    /**
     * Serializador de UIDBasedID
     *
     * @param u
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeUIDBasedID(
            UIDBasedID u);

    /**
     * Serializador de DvParsable
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvParsable(
            DvParsable d);

    /**
     * Serializador de DvTimeSpecification
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvTimeSpecification(
            DvTimeSpecification d);

    /**
     * Serializador de DvMultimedia
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvMultimedia(
            DvMultimedia d);

    /**
     * Serializador de DvText
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvText(
            DvText d);

    /**
     * Serilizador de DvCodedText
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvCodedText(
            DvCodedText d);

    /**
     * Serializador de TermMapping
     *
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeTermMapping(
            TermMapping t);

    /**
     * Serializador de Link
     *
     * @param l
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeLink(
            Link l);

    /**
     * Serializador de DvState
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvState(
            DvState d);

    /**
     * Serializador de DvParagraph
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvParagraph(
            DvParagraph d);

    /**
     * *
     * Serializador de PartyProxy
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializePartyProxy(
            PartyProxy p);


    /**
     * Serializador de FeederAuditDetails
     *
     * @param f
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeFeederAuditDetails(
            FeederAuditDetails f);
        
    /**
     * Serializador de FeederAudit
     * 
     * @param f
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeFeederAudit(
            FeederAudit f);
    
    /**
     * Serializador de Locatable
     * 
     * @param l
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeLocatable(
            Locatable l);
    
    /**
     * Serilizador de PartyRelated
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializePartyRelated(
            PartyRelated p);
    
    /**
     * Serializador de PartySelf
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializePartySelf(
            PartySelf p);
    
    
    /**
     * Serializador de ResourceDescriptionItem
     * 
     * @param r
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeResourceDescriptionItem(
            ResourceDescriptionItem r);
    
    /**
     * Serializador de TranslationDetails
     * 
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeTranslationDetails(
            TranslationDetails t);
    
    /**
     * Serializador de Item
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeItem(
            Item i);
    
    /**
     * Serializador de Cluster
     * 
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeCluster(
            Cluster c);
    
    /**
     * Serializador de Element
     * 
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeElement(
            Element e);
    
    /**
     * Serializador de DataStructure
     * 
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeDataStructure(
            DataStructure d);
    
    /**
     * Serializador de ItemList
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeItemList(
            ItemList i);
    
    /**
     * Serializador de ItemStructure
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeItemStructure(
            ItemStructure i);
    
    /**
     * Serializador de ItemSingle
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeItemSingle(
            ItemSingle i);
    
    /**
     * Serializador de ItemTable
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeItemTable(
            ItemTable i); 
    
    /**
     * Serializador de ItemTree
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeItemTree(
            ItemTree i); 
    
    /**
     * Serializador de PartyIdentity
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializePartyIdentity(PartyIdentity p)
               ; 
    
    /**
     * Serializador de PartyRelationship
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializePartyRelationship(
            PartyRelationship p); 
    
    /**
     * Serializador de Address
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeAddress(
            Address a); 
    
    /**
     * Serializador de Contact
     * 
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeContact(
            Contact c); 
    
    /**
     * Serializador de Party
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeParty(
            Party p); 
    
    /**
     * Serializador de Capability
     * 
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeCapability(
            Capability c); 
    
    /**
     * Serializador de Role
     * 
     * @param r
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeRole(
            Role r); 
    
    /**
     * Serializador de Actor
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeActor(
            Actor a);
    
    /**
     * Serializador de Agent
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeAgent(
            Agent a);
    
    /**
     * Serializador de Group
     * 
     * @param g
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeGroup(
            Group g);
    
    /**
     * Serializador de Organisation
     * 
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeOrganisation(
            Organisation o);
    
    /**
     * Serializador de Person
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializePerson(Person p)
               ;
    
    /**
     * Serializador de InstructionDetails
     * 
     * @param id
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeinstructionDetails(
            InstructionDetails id);
    
    /**
     * Serializador de ISMTransition
     * 
     * @param ism
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeISMTransition(
            ISMTransition ism);
    
    /**
     * Serializador de Activity
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining
     * 
     */
    RMObjectSerializationClient serializeActivity(
            Activity a);

    /**
     * Serializador de DvOrdered.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvOrdered(DvOrdered d);

    /**
     * Serializador de DvInterval.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvInterval(DvInterval d);

    /**
     * Serializador de DvQuantified.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvQuantified(DvQuantified d);

    /**
     * Serializador de DvAmount.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvAmount(DvAmount d);

    /**
     * Serializador de DvOrdinal.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvOrdinal(DvOrdinal d);

    /**
     * Serializador de DvCount.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvCount(DvCount d);

    /**
     * Serializador de DvProportion.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvProportion(DvProportion d);

    /**
     * Serializador de DvQuantity.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvQuantity(DvQuantity d);

    /**
     * Serializador de DvDuration.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvDuration(DvDuration d);

    /**
     * Serializador de DvAbsoluteQuantity.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvCount d);

    /**
     * Serializador de DvAbsoluteQuantity.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvDuration d);

    /**
     * Serializador de DvAbsoluteQuantity.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvProportion d);

    /**
     * Serializador de DvAbsoluteQuantity.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvQuantity d);

    /**
     * Serializador de DvDate.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvDate(DvDate d);

    /**
     * Serializador de DvTime.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvTime(DvTime d);

    /**
     * Serializador de DvDateTime.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvDateTime(DvDateTime d);

    /**
     * Serializador de DvTemporal.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining
     *
     */
    RMObjectSerializationClient serializeDvTemporal(DvTemporal d);
}
