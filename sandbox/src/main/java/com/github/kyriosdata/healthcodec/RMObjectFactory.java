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
* limitations under the License.f
 */
package com.github.kyriosdata.healthcodec;

import com.github.kyriosdata.healthcodec.RMObject.*;

import java.util.List;
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

    public static DvIdentifier newDvIdentifier(String issuer, String assigner, 
            String id, String type) {
        return new DvIdentifier(issuer, assigner, id, type);
    }
    
    public static UID newUID(String value){
        return new UID(value);
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

    public static TerminologyID newTerminologyID(String name, String value) {
        return new TerminologyID(name, value);
    }

    public static CodePhrase newCodePhrase(
            TerminologyID terminologyID, String codeString) {
        return new CodePhrase(terminologyID, codeString);
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

    public static DvParsable newDvParsable(DvEncapsulated dvEncapsulated, 
            String value, String formalism) {
        return new DvParsable(dvEncapsulated, value, formalism);
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
    
    public static ItemStructure newItemStructure(DataStructure ds){
        return new ItemStructure(ds);
    }
    
    public static ItemSingle newItemSingle(ItemStructure is, Element item){
        return new ItemSingle(is, item);
    }
    
    public static ItemTable newItemTable(ItemStructure is, List<Cluster> rows){
        return new ItemTable(is, rows);
    }
    
    public static ItemTree newItemTree(ItemStructure is, List<Item> items){
        return new ItemTree(is, items);
    }
    
    public static PartyIdentity newPartyIdentity(Locatable locatable, 
            ItemStructure details){
        return new PartyIdentity(locatable, details);
    }
    
    public static PartyRelationship newPartyRelationship(Locatable locatable, 
            ItemStructure details, ObjectRef source, ObjectRef target ){
        return new PartyRelationship(locatable, details, source, target);
    }
    
    public static Address newAddress(Locatable locatable, 
            ItemStructure details){
        return new Address(locatable, details);
    }
    
    public static Contact newContact(Locatable locatable, 
            List<Address> addresses){
        return new Contact(locatable, addresses);
    }
    
    public static Party newParty(Locatable locatable, 
            Set<PartyIdentity> identities, Set<Contact> contacts, 
            Set<PartyRelationship> relationships, 
            Set<LocatableRef> reverseRelationships, ItemStructure details) {
        return new Party(locatable, identities, contacts, relationships, 
                reverseRelationships, details);
    }
    
    public static Capability newCapability(Locatable locatable, 
            ItemStructure credentials){
        return new Capability(locatable, credentials);
    }
    
    public static Role newRole(Party party, List<Capability> capabilities, 
            PartyRef performer){
        return new Role(party, capabilities, performer);
    }
    
    public static Actor newActor(Party party, Set<Role> roles, 
            Set<DvText> languages){
        return new Actor(party, roles, languages);
    }
    
    public static Agent newAgent(Actor actor){
        return new Agent(actor);
    }
    
    public static Group newGroup(Actor actor){
        return new Group(actor);
    }
    
    public static Organisation newOrganisation(Actor actor){
        return new Organisation(actor);
    }
    
    public static Person newPerson(Actor a){
        return new Person(a);
    }
    
    public static InstructionDetails newInstructionDetails(
            LocatableRef instructionId, String activityId, 
            ItemStructure wfDetails){
        return new InstructionDetails(instructionId, activityId, wfDetails);
    }
    
    public static ISMTransition newISMTransition(DvCodedText currentState, 
                    DvCodedText transition, DvCodedText careflowStep){
        return new ISMTransition(currentState, transition, careflowStep);
    }
    
    public static Activity newActivity(Locatable locatable, 
            ItemStructure description, DvParsable timing, 
            String actionArchetypeId){
        return new Activity(locatable, description, timing, actionArchetypeId);
    }

    public static Interval newInterval(DvOrdered lower,DvOrdered upper){
        return new Interval(lower, upper);
    }

    public static DvInterval newDvInterval(DvOrdered lower, DvOrdered upper){
        return new DvInterval(lower, upper);
    }

    public static ReferenceRange newReferenceRange(DvText meaning,
                                                   DvInterval range){
        return new ReferenceRange(meaning, range);
    }

    public static DvOrdered newDvOrdered(
            List<ReferenceRange> otherReferenceRanges,
            DvInterval normalRange,
            CodePhrase normalStatus){
        return new DvOrdered(otherReferenceRanges, normalRange, normalStatus);
    }

    public static DvQuantified newDvQuantified(DvOrdered dvOrdered,
                                               String magnitudeStatus){
        return new DvQuantified(dvOrdered, magnitudeStatus);
    }

    public static DvAmount newDvAmount(DvOrdered dvOrdered,
                                       double accuracy,
                                       boolean accuracyPercent){
        return new DvAmount(dvOrdered, accuracy, accuracyPercent);
    }

    public static DvOrdinal newDvOrdinal(List<ReferenceRange>
                                                 otherReferenceRanges,
                                         DvInterval normalRange, int value,
                                         DvCodedText symbol){
        return new DvOrdinal(otherReferenceRanges, normalRange, value, symbol);
    }

    public static DvCount newDvCount(DvAmount dvAmount, int magnitude){
        return new DvCount(dvAmount, magnitude);
    }

    public static DvProportion newDvProportion(DvAmount dvAmount,
                                                double numerator,
                                                double denominator,
                                                ProportionKind type,
                                                int precision){
        return new DvProportion(dvAmount, numerator, denominator, type,
                precision);
    }

    public static DvQuantity newDvQuantity(DvAmount dvAmount, String units,
                                           double magnitude, int precision){
        return new DvQuantity(dvAmount, units, magnitude, precision);
    }

    public static DvDuration newDvDuration(DvAmount dvAmount, String value){
        return new DvDuration(dvAmount, value);
    }

    public static DvAbsoluteQuantityWithDvCount newDvAbsoluteQuantity(
            DvQuantified dvQuantified, DvCount dvCount){
        return new DvAbsoluteQuantityWithDvCount(dvQuantified, dvCount);
    }

    public static DvAbsoluteQuantityWithDvQuantity newDvAbsoluteQuantity(
            DvQuantified dvQuantified, DvQuantity dvQuantity){
        return new DvAbsoluteQuantityWithDvQuantity(dvQuantified, dvQuantity);
    }

    public static DvAbsoluteQuantityWithDvProportion newDvAbsoluteQuantity(
            DvQuantified dvQuantified, DvProportion dvProportion){
        return new DvAbsoluteQuantityWithDvProportion(dvQuantified, dvProportion);
    }

    public static DvAbsoluteQuantityWithDvDuration newDvAbsoluteQuantity(
            DvQuantified dvQuantified, DvDuration dvDuration){
        return new DvAbsoluteQuantityWithDvDuration(dvQuantified,dvDuration);
    }

    public static DvTemporal newDvTemporal(
            DvAbsoluteQuantityWithDvDuration dvAbsoluteQuantity, String value){
        return new DvTemporal(dvAbsoluteQuantity, value);
    }

    public static DvDate newDvDate(boolean dayKnown, boolean monthKnown,
                                   boolean isPartial, DvTemporal dvTemporal){
        return new DvDate(dayKnown, monthKnown, isPartial, dvTemporal);
    }

    public static DvTime newDvTime(boolean isPartial, boolean minuteKnown,
                                   boolean secondKnown, boolean fractionalSecKnown,
                                   DvTemporal dvTemporal){
        return new DvTime(isPartial, minuteKnown, secondKnown,
                fractionalSecKnown, dvTemporal);
    }

    public static DvDateTime newDvDateTime(boolean isPartial,
                                            boolean minuteKnown,
                                            boolean secondKnown,
                                            boolean fractionalSecKnown,
                                            DvTemporal dvTemporal,
                                            DvDate dateTime){
        return new DvDateTime(isPartial, minuteKnown, secondKnown,
                fractionalSecKnown,dvTemporal, dateTime);
    }

    public static Participation newParticipation(PartyProxy performer,
                                                 DvText function,
                                                 DvCodedText mode,
                                                 DvInterval time){
        return new Participation(performer, function, mode, time);
    }

    public static AuditDetails newAuditDetails(String timePosition,
                                               PartyProxy committer,
                                               DvDateTime timeCommitted,
                                               DvCodedText changeType,
                                               DvText description){
        return new AuditDetails(timePosition, committer, timeCommitted,
                changeType, description);
    }

    public static Attestation newAttestation(AuditDetails auditDetails,
                                             DvMultimedia attestedView,
                                             String proof, Set<DVEHRURI> items,
                                             DvText reason, boolean isPending){
        return new Attestation(auditDetails, attestedView, proof, items, reason,
                isPending);
    }

    public static RevisionHistoryItem newRevisionHistoryItem(
            List<AuditDetails> audits, ObjectVersionID versionID){
        return new RevisionHistoryItem(audits, versionID);
    }

    public static RevisionHistory newRevisionHistory(
            List<RevisionHistoryItem> items){
        return new RevisionHistory(items);
    }

    public static Contribution newContribution(ObjectID uid,
                                               Set<ObjectRef> versions,
                                               AuditDetails audit){
        return new Contribution(uid, versions, audit);
    }

    public static Folder newFolder(Locatable locatable, List<Folder> folders,
                                   List<ObjectRef> items){
        return new Folder(locatable, folders, items);
    }

    public static AuthoredResource newAuthoredResource(
            CodePhrase originalLanguage,
            Map<String, TranslationDetails> translations,
            ResourceDescription description,
            RevisionHistory revisionHistory, boolean isControlled){
        return new AuthoredResource(originalLanguage, translations, description,
                revisionHistory, isControlled);
    }

    public static ResourceDescription newResourceDescription(
            Map<String, String> originalAuthor,List<String> otherContributors,
            String lifecycleState, List<ResourceDescriptionItem> details,
            String resourcePackageUri, Map<String, String> otherDetails,
            AuthoredResource parentResource){
        return new ResourceDescription(originalAuthor, otherContributors,
                lifecycleState, details, resourcePackageUri, otherDetails,
                parentResource);
    }

    public static EventWithItemTree newEventWithItemTree(Locatable locatable,
                                                         DvDateTime time,
                                                         ItemTree data,
                                                         ItemStructure state){
        return new EventWithItemTree(locatable, time, data, state);
    }

    public static EventWithItemSingle newEventWithItemSingle(Locatable locatable,
                                                            DvDateTime time,
                                                            ItemSingle data,
                                                            ItemStructure state){
        return new EventWithItemSingle(locatable, time, data, state);
    }

    public static EventWithItemTable newEventWithItemTable(Locatable locatable,
                                                           DvDateTime time,
                                                           ItemTable data,
                                                           ItemStructure state){
        return new EventWithItemTable(locatable, time, data, state);
    }

    public static HistoryWithItemTree newHistoryWithItemTree(
            DataStructure dataStructure,DvDateTime origin,
            List<EventWithItemTree> events, DvDuration period,
            DvDuration duration, ItemStructure summary){
        return new HistoryWithItemTree(dataStructure, origin, events, period,
                duration, summary);
    }

    public static HistoryWithItemSingle newHistoryWithItemSingle(
            DataStructure dataStructure,DvDateTime origin,
            List<EventWithItemSingle> events, DvDuration period,
            DvDuration duration, ItemStructure summary){
        return new HistoryWithItemSingle(dataStructure, origin, events, period,
                duration, summary);
    }

    public static HistoryWithItemTable newHistoryWithItemTable(
            DataStructure dataStructure,DvDateTime origin,
            List<EventWithItemTable> events, DvDuration period,
            DvDuration duration, ItemStructure summary){
        return new HistoryWithItemTable(dataStructure, origin, events, period,
                duration, summary);
    }

    public static IntervalEventWithItemTree newIntervalEventWithItemTree(
            EventWithItemTree event, DvDuration width, DvCodedText mathFunction,
            int sampleCount){
        return new IntervalEventWithItemTree(event, width, mathFunction,
                sampleCount);
    }

    public static IntervalEventWithItemSingle newIntervalEventWithItemSingle(
            EventWithItemSingle event, DvDuration width, DvCodedText mathFunction,
            int sampleCount){
        return new IntervalEventWithItemSingle(event, width, mathFunction,
                sampleCount);
    }

    public static IntervalEventWithItemTable newIntervalEventWithItemTable(
            EventWithItemTable event, DvDuration width, DvCodedText mathFunction,
            int sampleCount){
        return new IntervalEventWithItemTable(event, width, mathFunction,
                sampleCount);
    }

    public static PointEventWithItemTree newPointEventWithItemTree(
            EventWithItemTree event){
        return new PointEventWithItemTree(event);
    }

    public static PointEventWithItemSingle newPointEventWithItemSingle(
            EventWithItemSingle event){
        return new PointEventWithItemSingle(event);
    }

    public static PointEventWithItemTable newPointEventWithItemTable(
            EventWithItemTable event){
        return new PointEventWithItemTable(event);
    }

    public static ContentItem newContentItem(Locatable locatable){
        return new ContentItem(locatable);
    }

    public static Entry newEntry(ContentItem contentItem, CodePhrase language,
                                 CodePhrase encoding, PartyProxy subject,
                                 PartyProxy provider, ObjectRef workflowId,
                                 List<Participation> otherParticipations){
        return new Entry(contentItem, language, encoding, subject, provider,
                workflowId, otherParticipations);
    }

    public static CareEntry newCareEntry(Entry entry, ItemStructure protocol,
                                         ObjectRef guidelineId){
        return new CareEntry(entry, protocol, guidelineId);
    }

    public static Action newAction(DvDateTime time, ItemStructure description,
                                   ISMTransition ismTransition,
                                   InstructionDetails instructionDetails){
        return new Action(time, description, ismTransition, instructionDetails);
    }

    public static AdminEntry newAdminEntry(Entry entry, ItemStructure data){
        return new AdminEntry(entry, data);
    }

    public static Evaluation newEvaluation(CareEntry careEntry,
                                           ItemStructure data){
        return new Evaluation(careEntry, data);
    }

    public static Instruction newInstruction(CareEntry careEntry,
                                             DvText narrative,
                                             List<Activity> activities,
                                             DvDateTime expiryTime,
                                             DvParsable wfDefinition){
        return new Instruction(careEntry, narrative, activities, expiryTime,
                wfDefinition);
    }

    public static ObservationWithItemTreeItemTree
        newObservationWithItemTreeItemTree(CareEntry careEntry,
                                           HistoryWithItemTree data,
                                           HistoryWithItemTree state){
        return new ObservationWithItemTreeItemTree(careEntry, data, state);
    }

    public static ObservationWithItemTreeItemSingle
    newObservationWithItemTreeItemSingle(CareEntry careEntry,
                                         HistoryWithItemTree data,
                                         HistoryWithItemSingle state){
        return new ObservationWithItemTreeItemSingle(careEntry, data, state);
    }

    public static ObservationWithItemTreeItemTable
    newObservationWithItemTreeItemTable(CareEntry careEntry,
                                        HistoryWithItemTree data,
                                        HistoryWithItemTable state){
        return new ObservationWithItemTreeItemTable(careEntry, data, state);
    }

    public static ObservationWithItemSingleItemTree
    newObservationWithItemSingleItemTree(CareEntry careEntry,
                                         HistoryWithItemSingle data,
                                         HistoryWithItemTree state){
        return new ObservationWithItemSingleItemTree(careEntry, data, state);
    }

    public static ObservationWithItemSingleItemSingle
    newObservationWithItemSingleItemSingle(CareEntry careEntry,
                                           HistoryWithItemSingle data,
                                           HistoryWithItemSingle state){
        return new ObservationWithItemSingleItemSingle(careEntry, data, state);
    }

    public static ObservationWithItemSingleItemTable
    newObservationWithItemSingleItemTable(CareEntry careEntry,
                                          HistoryWithItemSingle data,
                                          HistoryWithItemTable state){
        return new ObservationWithItemSingleItemTable(careEntry, data, state);
    }

    public static ObservationWithItemTableItemTree
    newObservationWithItemTableItemTree(CareEntry careEntry,
                                        HistoryWithItemTable data,
                                        HistoryWithItemTree state){
        return new ObservationWithItemTableItemTree(careEntry, data, state);
    }

    public static ObservationWithItemTableItemSingle
    newObservationWithItemTableItemSingle(CareEntry careEntry,
                                          HistoryWithItemTable data,
                                          HistoryWithItemSingle state){
        return new ObservationWithItemTableItemSingle(careEntry, data, state);
    }

    public static ObservationWithItemTableItemTable
    newObservationWithItemTableItemTable(CareEntry careEntry,
                                         HistoryWithItemTable data,
                                         HistoryWithItemTable state){
        return new ObservationWithItemTableItemTable(careEntry, data, state);
    }

    public static Section newSection(ContentItem contentItem,
                                     List<ContentItem> items){
        return new Section(contentItem, items);
    }
}
