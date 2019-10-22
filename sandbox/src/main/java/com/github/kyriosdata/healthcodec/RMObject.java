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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Gabriel
 */
public class RMObject {

    public static class DvBoolean {

        private final boolean value;

        protected DvBoolean(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }
    }

    public static class DvIdentifier {

        private final String issuer;
        private final String assigner;
        private final String id;
        private final String type;

        protected DvIdentifier(String issuer, String assigner, String id,
                String type) {
            if(issuer.isEmpty()){
                throw new IllegalArgumentException("empty or null issuer");
            }
            if(assigner.isEmpty()){
                throw new IllegalArgumentException("empty or null assigner");
            }
            if(id.isEmpty()){
                throw new IllegalArgumentException("empty or null id");
            }
            if(type.isEmpty()){
                throw new IllegalArgumentException("empty or null type");
            }
            this.issuer = issuer;
            this.assigner = assigner;
            this.id = id;
            this.type = type;
        }

        public String getIssuer() {
            return issuer;
        }

        public String getAssigner() {
            return assigner;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }
    }
    
    public static class UID {
        private final String value;

        protected UID(String value) {
            if(value.isEmpty()){
                throw new IllegalArgumentException("value vazio");
            }
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class InternetID {

        private final UID uid;
        private static String PATTERN = "[a-zA-Z]([a-zA-Z0-9-]*[a-zA-Z0-9])?(\\.[a-zA-Z]([a-zA-Z0-9-]*[a-zA-Z0-9])?)*";

        protected InternetID(String value) {
            if (!value.matches(PATTERN)) {
                throw new IllegalArgumentException("formato incorreto");
            }
            this.uid = RMObjectFactory.newUID(value);
        }

        public UID getUid() {
            return uid;
        }
    }

    public static class ISO_OID {

        private final UID uid;

        protected ISO_OID(String value) {
            this.uid = RMObjectFactory.newUID(value);
        }

        public UID getUid() {
            return uid;
        }
    }

    public static class UUID {

        private final UID uid;

        protected UUID(String value) {
            this.uid = RMObjectFactory.newUID(value);
        }

        public UID getUid() {
            return uid;
        }
    }

    public static class GenericID {

        private final ObjectID objectID;
        private final String scheme;

        protected GenericID(String value, String scheme) {
            if(scheme.isEmpty()) {
                throw new IllegalArgumentException("scheme vazio");
            }
            this.objectID = RMObjectFactory.newObjectID(value);
            this.scheme = scheme;
        }

        public String getScheme() {
            return scheme;
        }

        public ObjectID getObjectID() {
            return objectID;
        }
    }

    public static class TemplateID {

        private final ObjectID objectID;

        protected TemplateID(String value) {
            this.objectID = RMObjectFactory.newObjectID(value);
        }

        public ObjectID getObjectID() {
            return objectID;
        }
    }

    public static class TerminologyID {

        private final String name;
        private final String version;
        private final ObjectID objectID;

        protected TerminologyID(String name, String version) {
            String nameVersion = name + ( version == null ?
                "" : "(" + version + ")" );
            
            this.name = name;
            this.version = version;
            this.objectID = RMObjectFactory.newObjectID(nameVersion);
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public ObjectID getObjectID() {
            return objectID;
        }
    }

    public static class CodePhrase {

        private final TerminologyID terminologyID;
        private final String codeString;

        protected CodePhrase(TerminologyID terminologyID, String codeString) {
            if(terminologyID == null) {
                throw new IllegalArgumentException("null terminologyId");
            }
            if(codeString.isEmpty()) {
                throw new IllegalArgumentException("codeString vazio");
            }
            this.terminologyID = terminologyID;
            this.codeString = codeString;
        }

        public TerminologyID getTerminologyID() {
            return terminologyID;
        }

        public String getCodeString() {
            return codeString;
        }
    }

    public static class DVURI {

        private final String value;

        protected DVURI(String value) {
            if(value.isEmpty() || value == null){
                throw new IllegalArgumentException("codeString null ou vazio");
            }
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class DVEHRURI {

        private final DVURI dvuri;

        protected DVEHRURI(String value) {
            if (value.isEmpty()) {
                throw new IllegalArgumentException("value vazio");
            }
            this.dvuri = RMObjectFactory.newDVURI(value);
        }

        public DVURI getDvuri() {
            return dvuri;
        }
    }

    public static class VersionTreeID {

        private final String value;

        protected VersionTreeID(String value) {
            if (value.isEmpty()) {
                throw new IllegalArgumentException("value vazio");
            }
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class ArchetypeID {

        private final ObjectID objectID;

        protected ArchetypeID(String value) {
            if (value.isEmpty()) {
                throw new IllegalArgumentException("value vazio");
            }
            this.objectID = RMObjectFactory.newObjectID(value);
        }

        public ObjectID getObjectID() {
            return objectID;
        }
    }

    public static class ObjectVersionID {

        private final UIDBasedID uidBasedID;

        protected ObjectVersionID(String value) {
            if (value.isEmpty()) {
                throw new IllegalArgumentException("value vazio");
            }
            this.uidBasedID = RMObjectFactory.newUIDBasedID(value);
        }

        public UIDBasedID getUIDBasedID() {
            return uidBasedID;
        }
    }

    public static class HierObjectID {

        private final UIDBasedID uidBasedID;

        protected HierObjectID(String value) {
            if (value.isEmpty()) {
                throw new IllegalArgumentException("value vazio");
            }
            this.uidBasedID = RMObjectFactory.newUIDBasedID(value);
        }

        public UIDBasedID getUIDBasedID() {
            return uidBasedID;
        }
    }

    public static class ObjectID {

        private final String value;

        protected ObjectID(String value) {
            if (value.isEmpty()) {
                throw new IllegalArgumentException("value vazio");
            }
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class PartyRef {

        private final ObjectRef objectRef;

        protected PartyRef(ObjectID id, String value) {
            this.objectRef = RMObjectFactory.newObjectRef(
                    id, "DEMOGRAPHIC", value);
        }

        public ObjectRef getObjectRef() {
            return objectRef;
        }
    }

    public static class ObjectRef {

        private final ObjectID id;
        private final String namespace;
        private final String type;

        protected ObjectRef(ObjectID id, String namespace, String type) {
            if (id == null) {
                throw new IllegalArgumentException("null id");
            }
            if (namespace == null) {
                throw new IllegalArgumentException("null namespace");
            }
            if (type == null) {
                throw new IllegalArgumentException("null type");
            }
            this.id = id;
            this.namespace = namespace;
            this.type = type;
        }

        public ObjectID getId() {
            return id;
        }

        public String getNamespace() {
            return namespace;
        }

        public String getType() {
            return type;
        }
    }

    public static class LocatableRef {

        private final ObjectRef objectRef;
        private final String path;
        
        protected LocatableRef(ObjectVersionID id, String namespace, String type,
                String path) {
            if (id == null) {
                throw new IllegalArgumentException("null id");
            }
            if (namespace == null) {
                throw new IllegalArgumentException("null namespace");
            }
            if (type == null) {
                throw new IllegalArgumentException("null type");
            }
            if (path != null && path.isEmpty()) {
                throw new IllegalArgumentException("path vazio");
            }
            this.objectRef = RMObjectFactory.newObjectRef(
                    RMObjectFactory.newObjectID(id.getUIDBasedID().getValue()), 
                    namespace, type);
            this.path = path;
        }
        
        public ObjectRef getObjectRef() {
            return objectRef;
        }
        
        public String getPath() {
            return path;
        }
    }

    public static class ProportionKind {

        private final int value;

        protected ProportionKind(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static class AccessGroupRef {
        
        private final ObjectRef objectRef;

        protected AccessGroupRef(ObjectID id) {
            if(id == null){
                throw new IllegalArgumentException("null id");
            }
            this.objectRef = RMObjectFactory.newObjectRef(id, 
                    "ACCESS_CONTROL", "ACCESS_GROUP");
        }

        public ObjectRef getObjectRef() {
            return objectRef;
        }
    }

    public static class PartyIdentified {

        private final PartyRef externalRef;
        private final String name;
        private final List<DvIdentifier> identifiers;

        protected PartyIdentified(PartyRef externalRef, String name,
                List<DvIdentifier> identifiers) {
            if(externalRef == null && name == null && identifiers == null) {
                throw new IllegalArgumentException(
                        "externalRef, name, identifiers todos vazios");
            }
            if(name != null && name.isEmpty()) {
                throw new IllegalArgumentException("name vazio");
            }
            if(identifiers != null && identifiers.size() == 0) {
                throw new IllegalArgumentException("identifiers vazios");
            }
            this.externalRef = externalRef;
            this.name = name;
            this.identifiers = identifiers;
        }

        public PartyRef getExternalRef() {
            return externalRef;
        }

        public String getName() {
            return name;
        }

        public List<DvIdentifier> getIdentifiers() {
            return identifiers;
        }
    }

    public static class Archetyped {

        private final ArchetypeID archetypeId;
        private final TemplateID templateId;
        private final String rmVersion;

        public Archetyped(ArchetypeID archetypeId, TemplateID templateId,
                          String rmVersion) {
            if (archetypeId == null) {
                throw new IllegalArgumentException("null archetypeId");
            }
            if (rmVersion.isEmpty()) {
                throw new IllegalArgumentException("rmVersion vazio");
            }
            this.archetypeId = archetypeId;
            this.templateId = templateId;
            this.rmVersion = rmVersion;
        }

        public ArchetypeID getArchetypeId() {
            return archetypeId;
        }

        public TemplateID getTemplateId() {
            return templateId;
        }

        public String getRmVersion() {
            return rmVersion;
        }
    }

    public static class DvEncapsulated {

        private final CodePhrase charset;
        private final CodePhrase language;

        protected DvEncapsulated(CodePhrase charset, CodePhrase language) {
            this.charset = charset;
            this.language = language;
        }

        public CodePhrase getCharset() {
            return charset;
        }

        public CodePhrase getLanguage() {
            return language;
        }
    }

    public static class UIDBasedID {

        private final String value;

        protected UIDBasedID(String value) {
            if(value.isEmpty()){
                throw new IllegalArgumentException("value vazio");
            }
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class DvParsable {

        private final DvEncapsulated dvEncapsulated;
        private final String value;
        private final String formalism;

        protected DvParsable(DvEncapsulated dvEncapsulated, String value, 
                String formalism) {
            if (value == null) {
                throw new IllegalArgumentException("null value");
            }
            if (formalism.isEmpty()) {
                throw new IllegalArgumentException("formalism null ou vazio");
            }
            this.dvEncapsulated = dvEncapsulated;
            this.value = value;
            this.formalism = formalism;
        }

        public DvEncapsulated getDvEncapsulated() {
            return dvEncapsulated;
        }
        
        public String getValue() {
            return value;
        }

        public String getFormalism() {
            return formalism;
        }
    }

    public static class DvTimeSpecification {

        private final DvParsable value;

        protected DvTimeSpecification(DvParsable value) {
            if(value == null) {
                throw new IllegalArgumentException("null value");
            }
            this.value = value;
        }

        public DvParsable getValue() {
            return value;
        }
    }

    public static class DvMultimedia {

        private final DvEncapsulated dvEncapsulated;
        private final String alternateText;
        private final CodePhrase mediaType;
        private final CodePhrase compressionAlgorithm;
        private final byte[] integrityCheck;
        private final CodePhrase integrityCheckAlgorithm;
        private final DvMultimedia thumbnail;
        private final DVURI uri;
        private final byte[] data;

        protected DvMultimedia(
                DvEncapsulated dvEncapsulated,
                String alternateText,
                CodePhrase mediaType,
                CodePhrase compressionAlgorithm,
                byte[] integrityCheck,
                CodePhrase integrityCheckAlgorithm,
                DvMultimedia thumbnail,
                DVURI uri,
                byte[] data) {
            if (mediaType == null) {
                throw new IllegalArgumentException("null mediaType");
            }
            if (compressionAlgorithm == null) {
                throw new IllegalArgumentException("null compressionAlgorithm");
            }
            if (integrityCheck != null &&
                    integrityCheckAlgorithm == null) {
                throw new IllegalArgumentException(
                        "null integrity check algorithm");
            }
            if (uri == null && data == null) {
                throw new IllegalArgumentException("null uri e null data");
            }
            this.dvEncapsulated = dvEncapsulated;
            this.alternateText = alternateText;
            this.mediaType = mediaType;
            this.compressionAlgorithm = compressionAlgorithm;
            this.integrityCheck = integrityCheck;
            this.integrityCheckAlgorithm = integrityCheckAlgorithm;
            this.thumbnail = thumbnail;
            this.uri = uri;
            this.data = data;
        }

        public DvEncapsulated getDvEncapsulated() {
            return dvEncapsulated;
        }

        public String getAlternateText() {
            return alternateText;
        }

        public CodePhrase getMediaType() {
            return mediaType;
        }

        public CodePhrase getCompressionAlgorithm() {
            return compressionAlgorithm;
        }

        public byte[] getIntegrityCheck() {
            return integrityCheck;
        }

        public CodePhrase getIntegrityCheckAlgorithm() {
            return integrityCheckAlgorithm;
        }

        public DvMultimedia getThumbnail() {
            return thumbnail;
        }

        public DVURI getUri() {
            return uri;
        }

        public byte[] getData() {
            return data;
        }
    }

    public static enum Match {
        NARROWER("<"),
        EQUIVALENT("="),
        BROADER(">"),
        UNKNOWN("?");

        private Match(String value) {
            this.value = value;
        }

        public static Match fromValue(String value) {
            if (value != null) {
                for (Match match : values()) {
                    if (match.value.equals(value)) {
                        return match;
                    }
                }
            }

            return getDefault();
        }

        public static Match getDefault() {
            return UNKNOWN;
        }

        public String getValue() {
            return value;
        }

        private final String value;
    }

    public static class TermMapping {

        public final CodePhrase target;
        public final Match match;
        public final DvCodedText purpose;

        protected TermMapping(CodePhrase target,
                Match match, DvCodedText purpose) {
            if (target == null) {
                throw new IllegalArgumentException("null target");
            }
            if (match == null) {
                throw new IllegalArgumentException("null match");
            }
            this.target = target;
            this.match = match;
            this.purpose = purpose;
        }

        public CodePhrase getTarget() {
            return target;
        }

        public Match getMatch() {
            return match;
        }

        public DvCodedText getPurpose() {
            return purpose;
        }
    }

    public static class DvText {

        private final String value;
        private final List<TermMapping> mappings;
        private final String formatting;
        private final DVURI hyperlink;
        private final CodePhrase language;
        private final CodePhrase charset;

        protected DvText(String value,
                List<TermMapping> mappings,
                String formatting,
                DVURI hyperlink, CodePhrase language, CodePhrase charset) {
            if (!value.isEmpty() && !value.contains("\n\r")) {
                throw new IllegalArgumentException("value inválido: " + value);
            }
            if (mappings != null && mappings.isEmpty()) {
                throw new IllegalArgumentException("mapping vazio");
            }
            if (formatting != null && formatting.isEmpty()) {
                throw new IllegalArgumentException("formatting vazio");
            }
            this.value = value;
            this.mappings = mappings;
            this.formatting = formatting;
            this.hyperlink = hyperlink;
            this.language = language;
            this.charset = charset;
        }

        public String getValue() {
            return value;
        }

        public List<TermMapping> getMappings() {
            return mappings;
        }

        public String getFormatting() {
            return formatting;
        }

        public DVURI getHyperlink() {
            return hyperlink;
        }

        public CodePhrase getLanguage() {
            return language;
        }

        public CodePhrase getCharset() {
            return charset;
        }
    }

    public static class DvCodedText {

        private final DvText dvText;
        private final CodePhrase definingCode;

        protected DvCodedText(DvText dvText, CodePhrase definingCode) {
            if (definingCode == null) {
                throw new IllegalArgumentException("null defining code");
            }
            this.dvText = dvText;
            this.definingCode = definingCode;
        }

        public DvText getDvText() {
            return dvText;
        }

        public CodePhrase getDefiningCode() {
            return definingCode;
        }
    }

    public static class Link {

        private final DvText meaning;
        private final DvText type;
        private final DVEHRURI target;

        protected Link(DvText meaning, DvText type, DVEHRURI target) {
            if(meaning == null) {
                throw new IllegalArgumentException("null meaning");
            }
            if(type == null) {
                throw new IllegalArgumentException("null type");
            }
            if(target == null) {
                throw new IllegalArgumentException("null target");
            }
            this.meaning = meaning;
            this.type = type;
            this.target = target;
        }

        public DvText getMeaning() {
            return meaning;
        }

        public DvText getType() {
            return type;
        }

        public DVEHRURI getTarget() {
            return target;
        }
    }

    public static class DvState {

        private final DvCodedText value;
        private final String terminal;

        protected DvState(DvCodedText value, String terminal) {
            if (value == null) {
                throw new IllegalArgumentException("null value");
            }
            this.value = value;
            this.terminal = terminal;
        }

        public DvCodedText getValue() {
            return value;
        }

        public String getTerminal() {
            return terminal;
        }
    }

    public static class DvParagraph {

        private final List<DvText> items;

        protected DvParagraph(List<DvText> items) {
            this.items = items;
        }

        public List<DvText> getItems() {
            return items;
        }
    }

    public static class PartyProxy {

        private final PartyRef externalRef;

        protected PartyProxy(PartyRef externalRef) {
            this.externalRef = externalRef;
        }

        public PartyRef getExternalRef() {
            return externalRef;
        }
    }

    public static class FeederAuditDetails {

        private final String systemID;
        private final PartyIdentified provider;
        private final PartyIdentified location;
        //private final DvDateTime time; TODO
        private final PartyProxy subject;
        private final String versionID;

        protected FeederAuditDetails(
                String systemID,
                PartyIdentified provider,
                PartyIdentified location,
                //DateTime time, TODO
                PartyProxy subject,
                String versionID) {
            this.systemID = systemID;
            this.provider = provider;
            this.location = location;
            this.subject = subject;
            this.versionID = versionID;
        }

        public String getSystemID() {
            return systemID;
        }

        public PartyIdentified getProvider() {
            return provider;
        }

        public PartyIdentified getLocation() {
            return location;
        }

        public PartyProxy getSubject() {
            return subject;
        }

        public String getVersionID() {
            return versionID;
        }
    }

    public static class FeederAudit {

        private final FeederAuditDetails originatingSystemAudit;
        private final List<DvIdentifier> originatingSystemItemIDs;
        private final FeederAuditDetails feederSystemAudit;
        private final List<DvIdentifier> feederSystemItemIDs;
        private final DvEncapsulated originalContent;

        protected FeederAudit(
                FeederAuditDetails originatingSystemAudit,
                List<DvIdentifier> originatingSystemItemIDs,
                FeederAuditDetails feederSystemAudit,
                List<DvIdentifier> feederSystemItemIDs,
                DvEncapsulated originalContent) {
            this.originatingSystemAudit = originatingSystemAudit;
            this.originatingSystemItemIDs = originatingSystemItemIDs;
            this.feederSystemAudit = feederSystemAudit;
            this.feederSystemItemIDs = feederSystemItemIDs;
            this.originalContent = originalContent;
        }

        public FeederAuditDetails getOriginatingSystemAudit() {
            return originatingSystemAudit;
        }

        public List<DvIdentifier> getOriginatingSystemItemIDs() {
            return originatingSystemItemIDs;
        }

        public FeederAuditDetails getFeederSystemAudit() {
            return feederSystemAudit;
        }

        public List<DvIdentifier> getFeederSystemItemIDs() {
            return feederSystemItemIDs;
        }

        public DvEncapsulated getOriginalContent() {
            return originalContent;
        }
    }

    public static class Locatable {

        private final UIDBasedID uid;
        private final String archetypeNodeId;
        private final DvText name;
        private final Archetyped archetypeDetails;
        private final FeederAudit feederAudit;
        private final Set<Link> links;

        protected Locatable(
                UIDBasedID uid,
                String archetypeNodeId,
                DvText name,
                Archetyped archetypeDetails,
                FeederAudit feederAudit,
                Set<Link> links) {
            this.uid = uid;
            this.archetypeNodeId = archetypeNodeId;
            this.name = name;
            this.archetypeDetails = archetypeDetails;
            this.feederAudit = feederAudit;
            this.links = links;
        }

        public UIDBasedID getUid() {
            return uid;
        }

        public String getArchetypeNodeId() {
            return archetypeNodeId;
        }

        public DvText getName() {
            return name;
        }

        public Archetyped getArchetypeDetails() {
            return archetypeDetails;
        }

        public FeederAudit getFeederAudit() {
            return feederAudit;
        }

        public Set<Link> getLinks() {
            return links;
        }
    }

    public static class PartyRelated {

        private final PartyIdentified pi;
        private final DvCodedText relationship;

        protected PartyRelated(PartyIdentified pi, DvCodedText relationship) {
            this.pi = pi;
            this.relationship = relationship;
        }

        public PartyIdentified getPi() {
            return pi;
        }

        public DvCodedText getRelationship() {
            return relationship;
        }
    }

    public static class PartySelf {

        private final PartyRef externalRef;

        protected PartySelf(PartyRef externalRef) {
            this.externalRef = externalRef;
        }

        public PartyRef getExternalRef() {
            return externalRef;
        }
    }

    public static class ResourceDescriptionItem {

        private final CodePhrase language;
        private final String purpose;
        private final List<String> keywords;
        private final String use;
        private final String misuse;
        private final String copyright;
        private final Map<String, String> originalResourceUri;
        private final Map<String, String> otherDetails;

        protected ResourceDescriptionItem(
                CodePhrase language, String purpose, List<String> keywords,
                String use, String misuse, String copyright,
                Map<String, String> originalResourceUri,
                Map<String, String> otherDetails) {
            this.language = language;
            this.purpose = purpose;
            this.keywords = keywords;
            this.use = use;
            this.misuse = misuse;
            this.copyright = copyright;
            this.originalResourceUri = originalResourceUri;
            this.otherDetails = otherDetails;
        }

        public CodePhrase getLanguage() {
            return language;
        }

        public String getPurpose() {
            return purpose;
        }

        public List<String> getKeywords() {
            return keywords;
        }

        public String getUse() {
            return use;
        }

        public String getMisuse() {
            return misuse;
        }

        public String getCopyright() {
            return copyright;
        }

        public Map<String, String> getOriginalResourceUri() {
            return originalResourceUri;
        }

        public Map<String, String> getOtherDetails() {
            return otherDetails;
        }
    }

    public static class TranslationDetails {

        private final CodePhrase language;
        private final Map<String, String> author;
        private final String accreditation;
        private final Map<String, String> otherDetails;

        protected TranslationDetails(CodePhrase language, Map<String, String> author, String accreditation,
                Map<String, String> otherDetails) {
            this.language = language;
            this.author = author;
            this.accreditation = accreditation;
            this.otherDetails = otherDetails;
        }

        public CodePhrase getLanguage() {
            return language;
        }

        public Map<String, String> getAuthor() {
            return author;
        }

        public String getAccreditation() {
            return accreditation;
        }

        public Map<String, String> getOtherDetails() {
            return otherDetails;
        }
    }

    public static class Item {

        private final Locatable locatable;

        protected Item(Locatable locatable) {
            this.locatable = locatable;
        }

        public Locatable getLocatable() {
            return locatable;
        }
    }

    public static class Cluster {

        private final Item item;
        private final List<Item> items;

        protected Cluster(Item item, List<Item> items) {
            this.item = item;
            this.items = items;
        }

        public Item getItem() {
            return item;
        }

        public List<Item> getItems() {
            return items;
        }
    }

    public static class Element {

        private final Item item;
        private final DvCodedText nullFlavour;

        protected Element(Item item, DvCodedText nullFlavour) {
            this.item = item;
            this.nullFlavour = nullFlavour;
        }

        public Item getItem() {
            return item;
        }

        public DvCodedText getNullFlavour() {
            return nullFlavour;
        }
    }

    public static class DataStructure {

        private final Locatable locatable;

        protected DataStructure(Locatable locatable) {
            this.locatable = locatable;
        }

        public Locatable getLocatable() {
            return locatable;
        }
    }

    public static class ItemList {

        private final UIDBasedID uid;
        private final String archetypeNodeId;
        private final DvText name;
        private final Archetyped archetypeDetails;
        private final FeederAudit feederAudit;
        private final Set<Link> links;
        private final List<Element> items;

        protected ItemList(UIDBasedID uid, String archetypeNodeId, DvText name,
                Archetyped archetypeDetails, FeederAudit feederAudit,
                Set<Link> links, List<Element> items) {
            this.uid = uid;
            this.archetypeNodeId = archetypeNodeId;
            this.name = name;
            this.archetypeDetails = archetypeDetails;
            this.feederAudit = feederAudit;
            this.links = links;
            this.items = items;
        }

        public UIDBasedID getUid() {
            return uid;
        }

        public String getArchetypeNodeId() {
            return archetypeNodeId;
        }

        public DvText getName() {
            return name;
        }

        public Archetyped getArchetypeDetails() {
            return archetypeDetails;
        }

        public FeederAudit getFeederAudit() {
            return feederAudit;
        }

        public Set<Link> getLinks() {
            return links;
        }

        public List<Element> getItems() {
            return items;
        }
    }

    public static class ItemStructure {

        private final DataStructure dataStructure;

        protected ItemStructure(DataStructure dataStructure) {
            this.dataStructure = dataStructure;
        }

        public DataStructure getDataStructure() {
            return dataStructure;
        }
    }

    public static class ItemSingle {

        private final ItemStructure itemStructure;
        private final Element item;

        protected ItemSingle(ItemStructure itemStructure, Element item) {
            this.itemStructure = itemStructure;
            this.item = item;
        }

        public ItemStructure getItemStructure() {
            return itemStructure;
        }

        public Element getItem() {
            return item;
        }
    }

    public static class ItemTable {

        private final ItemStructure itemStructure;
        private final List<Cluster> rows;

        protected ItemTable(ItemStructure itemStructure, List<Cluster> rows) {
            this.itemStructure = itemStructure;
            this.rows = rows;
        }

        public ItemStructure getItemStructure() {
            return itemStructure;
        }

        public List<Cluster> getRows() {
            return rows;
        }
    }

    public static class ItemTree {

        private final ItemStructure itemStructure;
        private final List<Item> items;

        protected ItemTree(ItemStructure itemStructure, List<Item> items) {
            this.itemStructure = itemStructure;
            this.items = items;
        }

        public ItemStructure getItemStructure() {
            return itemStructure;
        }

        public List<Item> getItems() {
            return items;
        }
    }

    public static class PartyIdentity {

        private final Locatable locatable;
        private final ItemStructure details;

        protected PartyIdentity(Locatable locatable, ItemStructure details) {
            this.locatable = locatable;
            this.details = details;
        }

        public Locatable getLocatable() {
            return locatable;
        }

        public ItemStructure getDetails() {
            return details;
        }
    }

    public static class PartyRelationship {

        private final Locatable locatable;
        private final ItemStructure details;
        //private final DvInterval<DvDate> timeValidity todo;
        private final ObjectRef source;
        private final ObjectRef target;

        protected PartyRelationship(Locatable locatable,
                ItemStructure details, ObjectRef source, ObjectRef target) {
            this.locatable = locatable;
            this.details = details;
            this.source = source;
            this.target = target;
        }

        public Locatable getLocatable() {
            return locatable;
        }

        public ItemStructure getDetails() {
            return details;
        }

        public ObjectRef getSource() {
            return source;
        }

        public ObjectRef getTarget() {
            return target;
        }
    }

    public static class Address {

        private final Locatable locatable;
        private final ItemStructure details;

        protected Address(Locatable locatable, ItemStructure details) {
            this.locatable = locatable;
            this.details = details;
        }

        public Locatable getLocatable() {
            return locatable;
        }

        public ItemStructure getDetails() {
            return details;
        }
    }

    public static class Contact {

        private final Locatable locatable;
        //private final DvInterval<DvDate> timeValidity todo;
        private final List<Address> addresses;

        protected Contact(Locatable locatable, List<Address> addresses) {
            this.locatable = locatable;
            this.addresses = addresses;
        }

        public Locatable getLocatable() {
            return locatable;
        }

        public List<Address> getAddresses() {
            return addresses;
        }
    }

    public static class Party {

        private final Locatable locatable;
        private final Set<PartyIdentity> identities;
        private final Set<Contact> contacts;
        private final Set<PartyRelationship> relationships;
        private final Set<LocatableRef> reverseRelationships;
        private final ItemStructure details;

        protected Party(Locatable locatable, Set<PartyIdentity> identities,
                Set<Contact> contacts, Set<PartyRelationship> relationships,
                Set<LocatableRef> reverseRelationships, ItemStructure details) {
            this.locatable = locatable;
            this.identities = identities;
            this.contacts = contacts;
            this.relationships = relationships;
            this.reverseRelationships = reverseRelationships;
            this.details = details;
        }

        public Set<PartyIdentity> getIdentities() {
            return identities;
        }

        public Set<Contact> getContacts() {
            return contacts;
        }

        public Set<PartyRelationship> getRelationships() {
            return relationships;
        }

        public Set<LocatableRef> getReverseRelationships() {
            return reverseRelationships;
        }

        public ItemStructure getDetails() {
            return details;
        }
    }

    public static class Capability {

        private final Locatable locatable;
        private final ItemStructure credentials;

        protected Capability(Locatable locatable, ItemStructure credentials) {
            this.locatable = locatable;
            this.credentials = credentials;
        }

        public Locatable getLocatable() {
            return locatable;
        }

        public ItemStructure getCredentials() {
            return credentials;
        }
    }

    public static class Role {

        private final Party party;
        private final List<Capability> capabilities;
        //private final DvInterval<DvDate> timeValidity todo
        private final PartyRef performer;

        protected Role(Party party, List<Capability> capabilities,
                PartyRef performer) {
            this.party = party;
            this.capabilities = capabilities;
            this.performer = performer;
        }

        public Party getParty() {
            return party;
        }

        public List<Capability> getCapabilities() {
            return capabilities;
        }

        public PartyRef getPerformer() {
            return performer;
        }
    }

    public static class Actor {

        private final Party party;
        private final Set<Role> roles;
        private final Set<DvText> languages;

        protected Actor(Party party, Set<Role> roles, Set<DvText> languages) {
            this.party = party;
            this.roles = roles;
            this.languages = languages;
        }

        public Party getParty() {
            return party;
        }

        public Set<Role> getRoles() {
            return roles;
        }

        public Set<DvText> getLanguages() {
            return languages;
        }
    }

    public static class Agent {

        private final Actor actor;

        protected Agent(Actor actor) {
            this.actor = actor;
        }

        public Actor getActor() {
            return actor;
        }
    }

    public static class Group {

        private final Actor actor;

        protected Group(Actor actor) {
            this.actor = actor;
        }

        public Actor getActor() {
            return actor;
        }
    }

    public static class Organisation {

        private final Actor actor;

        protected Organisation(Actor actor) {
            this.actor = actor;
        }

        public Actor getActor() {
            return actor;
        }
    }

    public static class Person {

        private final Actor actor;

        public Person(Actor actor) {
            this.actor = actor;
        }

        public Actor getActor() {
            return actor;
        }
    }

    public static class InstructionDetails {

        private final LocatableRef instructionId;
        private final String activityId;
        private final ItemStructure wfDetails;

        public InstructionDetails(LocatableRef instructionId,
                String activityId, ItemStructure wfDetails) {
            this.instructionId = instructionId;
            this.activityId = activityId;
            this.wfDetails = wfDetails;
        }

        public LocatableRef getInstructionId() {
            return instructionId;
        }

        public String getActivityId() {
            return activityId;
        }

        public ItemStructure getWfDetails() {
            return wfDetails;
        }
    }

    public static class ISMTransition {

        private final DvCodedText currentState;
        private final DvCodedText transition;
        private final DvCodedText careflowStep;

        public ISMTransition(DvCodedText currentState,
                DvCodedText transition, DvCodedText careflowStep) {
            this.currentState = currentState;
            this.transition = transition;
            this.careflowStep = careflowStep;
        }

        public DvCodedText getCurrentState() {
            return currentState;
        }

        public DvCodedText getTransition() {
            return transition;
        }

        public DvCodedText getCareflowStep() {
            return careflowStep;
        }
    }
    
    public static class Activity {
        private final Locatable locatable;
        private final ItemStructure description;
        private final DvParsable timing;
        private final String actionArchetypeId;

        public Activity(Locatable locatable, ItemStructure description, 
                DvParsable timing, String actionArchetypeId) {
            this.locatable = locatable;
            this.description = description;
            this.timing = timing;
            this.actionArchetypeId = actionArchetypeId;
        }

        public Locatable getLocatable() {
            return locatable;
        }

        public ItemStructure getDescription() {
            return description;
        }

        public DvParsable getTiming() {
            return timing;
        }

        public String getActionArchetypeId() {
            return actionArchetypeId;
        }
    }
}
