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
* limitations under the License.f
 */
package com.github.gabrielsxp.healthcodec;

import java.util.List;
import com.github.gabrielsxp.healthcodec.RMObject.*;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Gabriel
 */
public class RMObjectFactory {

    public static DvBoolean newDvBoolean(boolean value) {
        return new DvBoolean(value);
    }

    public static DvIdentifier newDvIdentifier(
            String issuer, String assigner, String id, String type
    ) {
        return new DvIdentifier(issuer, assigner, id, type);
    }

    public static InternetID newInternetID(String value) {
        return new InternetID(value);
    }

    public static ISO_OID newISOOID(String value) {
        return new ISO_OID(value);
    }

    public static UUID newUUID(String value) {
        return new UUID(value);
    }

    public static GenericID newGenericID(String value, String scheme) {
        return new GenericID(value, scheme);
    }

    public static TemplateID newTemplateID(String value) {
        return new TemplateID(value);
    }

    public static TerminologyID newTerminologyID(String value) {
        return new TerminologyID(value);
    }

    public static CodePhrase newCodePhrase(
            TerminologyID terminologyID, String value) {
        return new CodePhrase(terminologyID, value);
    }

    public static DVURI newDVURI(String value) {
        return new DVURI(value);
    }

    public static DVEHRURI newDVEHRURI(String value) {
        return new DVEHRURI(value);
    }

    public static VersionTreeID newVersionTreeID(String value) {
        return new VersionTreeID(value);
    }

    public static ArchetypeID newArchetypeID(String value) {
        return new ArchetypeID(value);
    }

    public static ObjectVersionID newObjectVersionID(String value) {
        return new ObjectVersionID(value);
    }

    public static HierObjectID newHierObjectID(String value) {
        return new HierObjectID(value);
    }

    public static ObjectID newObjectID(String value) {
        return new ObjectID(value);
    }

    public static PartyRef newPartyRef(ObjectID id, String value) {
        return new PartyRef(id, value);
    }

    public static ObjectRef newObjectRef(
            ObjectID id,
            String namespace,
            String type) {
        return new ObjectRef(id, namespace, type);
    }

    public static LocatableRef newLocatableRef(
            ObjectVersionID id,
            String namespace,
            String type,
            String path) {
        return new LocatableRef(id, namespace, type, path);
    }

    public static ProportionKind newPropotionKind(int value) {
        return new ProportionKind(value);
    }

    public static AccessGroupRef newAccessGroupRef(ObjectID id) {
        return new AccessGroupRef(id);
    }

    public static PartyIdentified newPartyIdentified(
            PartyRef externalRef,
            String name,
            List<DvIdentifier> identifiers) {
        return new PartyIdentified(externalRef, name, identifiers);
    }

    public static Archetyped newArchetyped(
            ArchetypeID archetypeId,
            TemplateID templateId,
            String rmVersion) {
        return new Archetyped(archetypeId, templateId, rmVersion);
    }

    public static DvEncapsulated newDvEncapsulated(CodePhrase charset,
            CodePhrase language) {
        return new DvEncapsulated(charset, language);
    }

    public static UIDBasedID newUIDBasedID(String value) {
        return new UIDBasedID(value);
    }

    public static DvParsable newDvParsable(CodePhrase charset,
            CodePhrase language, String value, String formalism) {
        return new DvParsable(charset, language, value, formalism);
    }

    public static DvTimeSpecification newDvTimeSpecification(DvParsable value) {
        return new DvTimeSpecification(value);
    }

    public static DvMultimedia newDvMultimedia(
            DvEncapsulated dvMultimediaDvEncapsulated,
            String alternateText,
            CodePhrase mediaType,
            CodePhrase compressionAlgorithm,
            byte[] integrityCheck,
            CodePhrase integrityCheckAlgorithm,
            DvMultimedia thumbnail,
            DVURI uri,
            byte[] data) {
        return new DvMultimedia(
                dvMultimediaDvEncapsulated, alternateText, mediaType,
                compressionAlgorithm, integrityCheck, integrityCheckAlgorithm,
                thumbnail, uri, data);
    }

    public static DvText newDvText(String value,
            List<TermMapping> mappings,
            String formatting,
            DVURI hyperlink,
            CodePhrase language,
            CodePhrase charset) {
        return new DvText(value, mappings, formatting, hyperlink, language, charset);
    }

    public static DvCodedText newDvCodedText(DvText dvText,
            CodePhrase definingCode) {
        return new DvCodedText(dvText, definingCode);
    }

    public static TermMapping newTermMapping(CodePhrase target,
            Match match,
            DvCodedText purpose) {
        return new TermMapping(target, match, purpose);
    }

    public static Link newLink(DvText meaning, DvText type, DVEHRURI target) {
        return new Link(meaning, type, target);
    }

    public static DvState newDvState(DvCodedText value, String terminal) {
        return new DvState(value, terminal);
    }

    public static DvParagraph newDvParagraph(List<DvText> items) {
        return new DvParagraph(items);
    }

    public static PartyProxy newPartyProxy(PartyRef externalRef) {
        return new PartyProxy(externalRef);
    }

    public static FeederAuditDetails newFeederAuditDetails(
            String systemID,
            PartyIdentified provider,
            PartyIdentified location,
            //DateTime time, TODO
            PartyProxy subject,
            String versionID) {
        return new FeederAuditDetails(
                systemID,
                provider,
                location,
                /*time,*/
                subject, versionID);
    }

    public static FeederAudit newFeederAudit(
            FeederAuditDetails originatingSystemAudit,
            List<DvIdentifier> originatingSystemItemIDs,
            FeederAuditDetails feederSystemAudit,
            List<DvIdentifier> feederSystemItemIDs,
            DvEncapsulated originalContent) {
        return new FeederAudit(
                originatingSystemAudit,
                originatingSystemItemIDs,
                feederSystemAudit,
                feederSystemItemIDs,
                originalContent);
    }

    public static Locatable newLocatable(UIDBasedID uid,
            String archetypeNodeId, DvText name, Archetyped archetypeDetails,
            FeederAudit feederAudit, Set<Link> links) {
        return new Locatable(uid, archetypeNodeId, name, archetypeDetails,
                feederAudit, links);
    }

    public static PartyRelated newPartyRelated(PartyIdentified pi,
            DvCodedText relationship) {
        return new PartyRelated(pi, relationship);
    }

    public static PartySelf newPartySelf(PartyRef externalRef) {
        return new PartySelf(externalRef);
    }

    public static ResourceDescriptionItem newResourceDescriptionItem(
            CodePhrase language, String purpose,List<String> keywords, 
            String use, String misuse, String copyright,
            Map<String, String> originalResourceUri, 
            Map<String, String> otherDetails){
        return new ResourceDescriptionItem(
                language, purpose, keywords, use, misuse, copyright, 
                originalResourceUri, otherDetails);
    }
    
    public static TranslationDetails newTranslationDetails(CodePhrase language, 
            Map<String, String> author, String accreditation, 
            Map<String, String> otherDetails){
        return new TranslationDetails(language, author, 
                accreditation, otherDetails);
    }
    
    public static Item newItem(Locatable locatable){
        return new Item(locatable);
    }
    
    public static Cluster newCluster(Item item, List<Item> items){
        return new Cluster(item, items);
    }
    
    public static Element newElement(Item item, DvCodedText nullFlavour){
        return new Element(item, nullFlavour);
    }
    
    public static DataStructure newDataStructure(Locatable locatable){
        return new DataStructure(locatable);
    }
    
    public static ItemList newItemList(UIDBasedID uid, String archetypeNodeId, 
            DvText name, Archetyped archetypeDetails, FeederAudit feederAudit, 
            Set<Link> links, List<Element> items){
        return new ItemList(uid, archetypeNodeId, name, archetypeDetails,
                feederAudit, links, items);
    }
}
