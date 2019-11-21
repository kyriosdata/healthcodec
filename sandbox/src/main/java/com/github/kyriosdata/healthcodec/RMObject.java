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

import java.util.ArrayList;
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
            if (issuer == null || issuer.isEmpty()) {
                throw new IllegalArgumentException("issuer vazio ou null");
            }
            if (assigner == null || assigner.isEmpty()) {
                throw new IllegalArgumentException("assigner vazio ou null");
            }
            if (id == null || id.isEmpty()) {
                throw new IllegalArgumentException("id vazio ou null");
            }
            if (type == null || type.isEmpty()) {
                throw new IllegalArgumentException("type vazio ou null");
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
            if (value.isEmpty()) {
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
            if (scheme.isEmpty()) {
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
            String nameVersion = name + (version == null ?
                    "" : "(" + version + ")");

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
            if (terminologyID == null) {
                throw new IllegalArgumentException("null terminologyId");
            }
            if (codeString.isEmpty()) {
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
            if (value == null || value.isEmpty()) {
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
            if (value == null || value.isEmpty()) {
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
            if (value == null || value.isEmpty()) {
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
            if (value == null || value.isEmpty()) {
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
            if (value == null || value.isEmpty()) {
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
            if (value == null || value.isEmpty()) {
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
            if (value == null || value.isEmpty()) {
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

    public enum ProportionKind {
        RATIO(0),
        UNITARY(1),
        PERCENT(2),
        FRACTION(3),
        INTEGER_FRACTION(4);

        ProportionKind(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String toString() {
            return Integer.toString(value);
        }

        public static ProportionKind fromValue(int value) {
            switch (value) {
                case 0:
                    return RATIO;
                case 1:
                    return UNITARY;
                case 2:
                    return PERCENT;
                case 3:
                    return FRACTION;
                case 4:
                    return INTEGER_FRACTION;
                default:
                    throw new IllegalArgumentException("unknown value");
            }
        }

        public static ProportionKind valueOf(int value) {
            return fromValue(value);
        }

        private int value;
    }

    public static class AccessGroupRef {

        private final ObjectRef objectRef;

        protected AccessGroupRef(ObjectID id) {
            if (id == null) {
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
            if (externalRef == null && name == null && identifiers == null) {
                throw new IllegalArgumentException(
                        "externalRef, name, identifiers todos vazios");
            }
            if (name != null && name.isEmpty()) {
                throw new IllegalArgumentException("name vazio");
            }
            if (identifiers != null && identifiers.size() == 0) {
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
            if (value.isEmpty()) {
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
                throw new IllegalArgumentException("formalism vazio");
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
            if (value == null) {
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

    public enum Match {
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
            if (meaning == null) {
                throw new IllegalArgumentException("null meaning");
            }
            if (type == null) {
                throw new IllegalArgumentException("null type");
            }
            if (target == null) {
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
            if (items == null || items.size() == 0) {
                throw new IllegalArgumentException(
                        "null items ou items vazios");
            }
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
            if (systemID.isEmpty()) {
                throw new IllegalArgumentException("empty null ou vazio");
            }
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
            if (originatingSystemAudit == null) {
                throw new IllegalArgumentException(
                        "null originatingSystemAudit");
            }
            if (originatingSystemItemIDs != null &&
                    originatingSystemItemIDs.size() == 0) {
                throw new IllegalArgumentException(
                        "originatingSystemItemIds vazio");
            }
            if (feederSystemItemIDs != null &&
                    feederSystemItemIDs.size() == 0) {
                throw new IllegalArgumentException("feederSystemItemIds vazio");
            }
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
            if (archetypeNodeId == null) {
                throw new IllegalArgumentException("null archetypeNodeId");
            }
            if (name == null) {
                throw new IllegalArgumentException("null name");
            }
            if (links != null && links.isEmpty()) {
                throw new IllegalArgumentException("links vazio");
            }
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
            if (relationship == null) {
                throw new IllegalArgumentException("null relationship");
            }
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
            if (language == null) {
                language = RMObjectFactory.newCodePhrase(
                        RMObjectFactory.newTerminologyID(
                                "language", "ISO_639-1"),
                        "en");
            }
            if (purpose.isEmpty()) {
                throw new IllegalArgumentException("purpose vazio");
            }
            if (use != null && use.isEmpty()) {
                throw new IllegalArgumentException("use vazio");
            }
            if (misuse != null && misuse.isEmpty()) {
                throw new IllegalArgumentException("misuse vazio");
            }
            if (copyright != null && copyright.isEmpty()) {
                throw new IllegalArgumentException("copyright vazio");
            }
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

        protected TranslationDetails(CodePhrase language, Map<String,
                String> author, String accreditation, Map<String,
                String> otherDetails) {
            if (language == null) {
                throw new IllegalArgumentException("null language");
            }
            if (author == null) {
                throw new IllegalArgumentException("null author");
            }
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
            if (item == null) {
                throw new IllegalArgumentException("item null");
            }
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
            if (details == null) {
                throw new IllegalArgumentException("details null");
            }
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
            if (locatable == null) {
                throw new IllegalArgumentException("null locatable");
            }

            if (source == null) {
                throw new IllegalArgumentException("null source");
            }
            if (target == null) {
                throw new IllegalArgumentException("null target");
            }
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
            if (details == null) {
                throw new IllegalArgumentException("null details");
            }
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
            if (addresses == null || addresses.size() == 0) {
                throw new IllegalArgumentException("addresses null ou vazio");
            }
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
            if (locatable == null) {
                throw new IllegalArgumentException("null locatable");
            }
            if (identities == null || identities.isEmpty()) {
                throw new IllegalArgumentException("identities null ou vazio");
            }
            if (contacts != null && contacts.isEmpty()) {
                throw new IllegalArgumentException("contacts vazio");
            }
            if (relationships != null) {
                if (relationships.isEmpty()) {
                    throw new IllegalArgumentException("relationships vazio");
                }
            }
            this.locatable = locatable;
            this.identities = identities;
            this.contacts = contacts;
            this.relationships = relationships;
            this.reverseRelationships = reverseRelationships;
            this.details = details;
        }

        public Locatable getLocatable() {
            return locatable;
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
            if (credentials == null) {
                throw new IllegalArgumentException("null credentials");
            }
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
            if (capabilities != null && capabilities.size() == 0) {
                throw new IllegalArgumentException("capabilities vazio");
            }
            if (performer == null) {
                throw new IllegalArgumentException("null performer");
            }
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
            String LEGAL_IDENTITY = "legal identity";

            boolean hasLegalIdentity = false;
            for (PartyIdentity identity : party.getIdentities()) {
                if (LEGAL_IDENTITY.equals(identity.locatable.name.getValue())) {
                    hasLegalIdentity = true;
                    break;
                }
            }
            if (!hasLegalIdentity) {
                throw new IllegalArgumentException("no legal identity");
            }
            if (roles != null && roles.isEmpty()) {
                throw new IllegalArgumentException("roles vazio");
            }
            if (languages != null && languages.isEmpty()) {
                throw new IllegalArgumentException("languages vazio");
            }
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

        protected Person(Actor actor) {
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

        protected InstructionDetails(LocatableRef instructionId,
                                     String activityId, ItemStructure wfDetails) {
            if (instructionId == null) {
                throw new IllegalArgumentException("null instructionId");
            }
            if (activityId.isEmpty()) {
                throw new IllegalArgumentException("activityId null ou vazio");
            }
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

        protected ISMTransition(DvCodedText currentState,
                                DvCodedText transition, DvCodedText careflowStep) {
            if (currentState == null) {
                throw new IllegalArgumentException("null currentState");
            }
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

        protected Activity(Locatable locatable, ItemStructure description,
                           DvParsable timing, String actionArchetypeId) {
            if (description == null) {
                throw new IllegalArgumentException("null description");
            }
            if (timing == null) {
                throw new IllegalArgumentException("null timing");
            }
            if (actionArchetypeId.isEmpty()) {
                throw new IllegalArgumentException("actionArchetypeId vazio");
            }
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

    public static class DvOrdered {
        private List<ReferenceRange> otherReferenceRanges;
        private final DvInterval normalRange;
        private final CodePhrase normalStatus;

        protected DvOrdered(List<ReferenceRange> otherReferenceRanges,
                            DvInterval normalRange, CodePhrase normalStatus) {
            if (otherReferenceRanges != null) {
                if (otherReferenceRanges.isEmpty()) {
                    throw new IllegalArgumentException(
                            "otherReferenceRanges vazio");
                }
                this.otherReferenceRanges =
                        new ArrayList<ReferenceRange>(otherReferenceRanges);
            } else {
                this.otherReferenceRanges = null;
            }

            this.otherReferenceRanges = otherReferenceRanges;
            this.normalRange = normalRange;
            this.normalStatus = normalStatus;
        }

        public List<ReferenceRange> getOtherReferenceRanges() {
            return otherReferenceRanges;
        }

        public DvInterval getNormalRange() {
            return normalRange;
        }

        public CodePhrase getNormalStatus() {
            return normalStatus;
        }
    }

    public static class Interval {
        private final DvOrdered lower;
        private final DvOrdered upper;
        private boolean lowerIncluded;
        private boolean upperIncluded;

        protected Interval(DvOrdered lower, DvOrdered upper) {
            this.lower = lower;
            this.upper = upper;
            this.lowerIncluded = true;
            this.upperIncluded = true;
        }

        public DvOrdered getLower() {
            return lower;
        }

        public DvOrdered getUpper() {
            return upper;
        }

        public boolean isLowerIncluded() {
            return lowerIncluded;
        }

        public boolean isUpperIncluded() {
            return upperIncluded;
        }
    }

    public static class DvInterval {
        private final Interval interval;

        protected DvInterval(DvOrdered lower, DvOrdered upper) {
            this.interval = new Interval(lower, upper);
        }

        public Interval getInterval() {
            return interval;
        }
    }

    public static class ReferenceRange {
        private final DvText meaning;
        private final DvInterval range;

        protected ReferenceRange(DvText meaning, DvInterval range) {
            if (meaning == null) {
                throw new IllegalArgumentException("null meaning");
            }
            if (range == null) {
                throw new IllegalArgumentException("null range");
            }
            this.meaning = meaning;
            this.range = range;
        }

        public DvText getMeaning() {
            return meaning;
        }

        public DvInterval getRange() {
            return range;
        }
    }

    public static class DvQuantified {
        private final DvOrdered dvOrdered;
        private final String magnitudeStatus;

        protected DvQuantified(DvOrdered dvOrdered, String magnitudeStatus) {
            this.dvOrdered = dvOrdered;
            this.magnitudeStatus = magnitudeStatus;
        }

        public DvOrdered getDvOrdered() {
            return dvOrdered;
        }

        public String getMagnitudeStatus() {
            return magnitudeStatus;
        }
    }

    public static class DvAmount {
        private final DvOrdered dvOrdered;
        private final double accuracy;
        private final boolean accuracyPercent;

        protected DvAmount(DvOrdered dvOrdered, double accuracy,
                           boolean accuracyPercent) {
            this.dvOrdered = dvOrdered;
            this.accuracy = accuracy;
            this.accuracyPercent = accuracyPercent;
        }

        public DvOrdered getDvOrdered() {
            return dvOrdered;
        }

        public double getAccuracy() {
            return accuracy;
        }

        public boolean isAccuracyPercent() {
            return accuracyPercent;
        }
    }

    public static class DvOrdinal {
        private final List<ReferenceRange> otherReferenceRanges;
        private final DvInterval normalRange;
        private final int value;
        private final DvCodedText symbol;

        public List<ReferenceRange> getOtherReferenceRanges() {
            return otherReferenceRanges;
        }

        public DvInterval getNormalRange() {
            return normalRange;
        }

        public int getValue() {
            return value;
        }

        public DvCodedText getSymbol() {
            return symbol;
        }

        protected DvOrdinal(List<ReferenceRange> otherReferenceRanges,
                            DvInterval normalRange, int value, DvCodedText symbol) {
            this.otherReferenceRanges = otherReferenceRanges;
            this.normalRange = normalRange;
            this.value = value;
            this.symbol = symbol;
        }
    }

    public static class DvCount {
        private final DvAmount dvAmount;
        private final int magnitude;

        protected DvCount(DvAmount dvAmount, int magnitude) {
            this.dvAmount = dvAmount;
            this.magnitude = magnitude;
        }

        public DvAmount getDvAmount() {
            return dvAmount;
        }

        public int getMagnitude() {
            return magnitude;
        }
    }

    public static class DvProportion {
        private final DvAmount dvAmount;
        private final double numerator;
        private final double denominator;
        private final ProportionKind type;
        private final int precision;

        public DvAmount getDvAmount() {
            return dvAmount;
        }

        public double getNumerator() {
            return numerator;
        }

        public double getDenominator() {
            return denominator;
        }

        public ProportionKind getType() {
            return type;
        }

        public int getPrecision() {
            return precision;
        }

        protected DvProportion(DvAmount dvAmount, double numerator,
                               double denominator, ProportionKind type,
                               int precision) {
            if (type == null) {
                throw new IllegalArgumentException("null type");
            } else if (type == ProportionKind.UNITARY) {
                if (denominator != 1) {
                    throw new IllegalArgumentException(
                            "denominator for unitary proportion must be 1");
                }
            } else if (type == ProportionKind.PERCENT) {
                if (denominator != 100) {
                    throw new IllegalArgumentException(
                            "denominator for unitary proportion must be 100");
                }
            } else if (type == ProportionKind.FRACTION ||
                    type == ProportionKind.INTEGER_FRACTION) {

                if (!bothIntegral(numerator, denominator)) {
                    throw new IllegalArgumentException(
                            "both numberator and denominator must be integral for " +
                                    "fraction or integer fraction proportion");
                }
            }


            this.dvAmount = dvAmount;
            this.numerator = numerator;
            this.denominator = denominator;
            this.type = type;
            this.precision = precision;
        }

        private boolean bothIntegral(double num1, double num2) {
            return (Math.floor(num1) == num1) && (Math.floor(num2) == num2);
        }
    }

    public static class DvQuantity {
        private final DvAmount dvAmount;
        private final String units;
        private final double magnitude;
        private final int precision;

        protected DvQuantity(DvAmount dvAmount, String units, double magnitude,
                             int precision) {

            if (precision < -1) {
                throw new IllegalArgumentException("negative precision");
            }

            this.dvAmount = dvAmount;
            this.units = units;
            this.magnitude = magnitude;
            this.precision = precision;
        }

        public DvAmount getDvAmount() {
            return dvAmount;
        }

        public String getUnits() {
            return units;
        }

        public double getMagnitude() {
            return magnitude;
        }

        public int getPrecision() {
            return precision;
        }
    }

    public static class DvDuration {
        private final DvAmount dvAmount;
        private final String value;

        protected DvDuration(DvAmount dvAmount, String value) {
            this.dvAmount = dvAmount;
            this.value = value;
        }

        public DvAmount getDvAmount() {
            return dvAmount;
        }

        public String getValue() {
            return value;
        }
    }

    public static class DvAbsoluteQuantityWithDvQuantity {
        private final DvQuantified dvQuantified;
        private final DvQuantity dvQuantity;
        private RMObjectID id;

        protected DvAbsoluteQuantityWithDvQuantity(DvQuantified dvQuantified,
                                                   DvQuantity dvQuantity) {
            this.dvQuantified = dvQuantified;
            this.dvQuantity = dvQuantity;
            this.id = RMObjectID.DVQUANTITY;
        }

        public DvQuantified getDvQuantified() {
            return dvQuantified;
        }

        public DvQuantity getDvQuantity() {
            return dvQuantity;
        }

        public RMObjectID getId() {
            return id;
        }
    }

    public static class DvAbsoluteQuantityWithDvCount {
        private final DvQuantified dvQuantified;
        private final DvCount dvCount;
        private RMObjectID id;

        protected DvAbsoluteQuantityWithDvCount(DvQuantified dvQuantified,
                                                DvCount dvCount) {
            this.dvQuantified = dvQuantified;
            this.dvCount = dvCount;
            this.id = RMObjectID.DVCOUNT;
        }

        public DvQuantified getDvQuantified() {
            return dvQuantified;
        }

        public DvCount getDvCount() {
            return dvCount;
        }

        public RMObjectID getId() {
            return id;
        }
    }

    public static class DvAbsoluteQuantityWithDvProportion {
        private final DvQuantified dvQuantified;
        private final DvProportion dvProportion;
        private RMObjectID id;

        protected DvAbsoluteQuantityWithDvProportion(DvQuantified dvQuantified,
                                                     DvProportion dvProportion) {
            this.dvQuantified = dvQuantified;
            this.dvProportion = dvProportion;
            this.id = RMObjectID.DVPROPORTION;
        }

        public DvQuantified getDvQuantified() {
            return dvQuantified;
        }

        public DvProportion getDvProportion() {
            return dvProportion;
        }

        public RMObjectID getId() {
            return id;
        }
    }

    public static class DvAbsoluteQuantityWithDvDuration {
        private final DvQuantified dvQuantified;
        private final DvDuration dvDuration;
        private RMObjectID id;

        protected DvAbsoluteQuantityWithDvDuration(DvQuantified dvQuantified,
                                                   DvDuration dvDuration) {
            this.dvQuantified = dvQuantified;
            this.dvDuration = dvDuration;
            this.id = RMObjectID.DVPROPORTION;
        }

        public DvQuantified getDvQuantified() {
            return dvQuantified;
        }

        public DvDuration getDvDuration() {
            return dvDuration;
        }

        public RMObjectID getId() {
            return id;
        }
    }

    public static class DvTemporal {
        private final DvAbsoluteQuantityWithDvDuration dvAbsoluteQuantity;
        private final String value;

        protected DvTemporal(DvAbsoluteQuantityWithDvDuration dvAbsoluteQuantity,
                             String value) {
            this.dvAbsoluteQuantity = dvAbsoluteQuantity;
            this.value = value;
        }

        public DvAbsoluteQuantityWithDvDuration getDvAbsoluteQuantity() {
            return dvAbsoluteQuantity;
        }

        public String getValue() {
            return value;
        }
    }

    public static class DvDateTime {
        private final boolean isPartial;
        private final boolean minuteKnown;
        private final boolean secondKnown;
        private final boolean fractionalSecKnown;
        private final DvTemporal dvTemporal;
        private final DvDate dateTime;

        protected DvDateTime(boolean isPartial, boolean minuteKnown,
                             boolean secondKnown, boolean fractionalSecKnown,
                             DvTemporal dvTemporal, DvDate dateTime) {
            this.isPartial = isPartial;
            this.minuteKnown = minuteKnown;
            this.secondKnown = secondKnown;
            this.fractionalSecKnown = fractionalSecKnown;
            this.dvTemporal = dvTemporal;
            this.dateTime = dateTime;
        }

        public boolean isPartial() {
            return isPartial;
        }

        public boolean isMinuteKnown() {
            return minuteKnown;
        }

        public boolean isSecondKnown() {
            return secondKnown;
        }

        public boolean isFractionalSecKnown() {
            return fractionalSecKnown;
        }

        public DvTemporal getDvTemporal() {
            return dvTemporal;
        }

        public DvDate getDateTime() {
            return dateTime;
        }
    }

    public static class DvTime {
        private final boolean isPartial;
        private final boolean minuteKnown;
        private final boolean secondKnown;
        private final boolean fractionalSecKnown;
        private final DvTemporal dvTemporal;

        protected DvTime(boolean isPartial, boolean minuteKnown,
                         boolean secondKnown, boolean fractionalSecKnown,
                         DvTemporal dvTemporal) {
            this.isPartial = isPartial;
            this.minuteKnown = minuteKnown;
            this.secondKnown = secondKnown;
            this.fractionalSecKnown = fractionalSecKnown;
            this.dvTemporal = dvTemporal;
        }

        public boolean isPartial() {
            return isPartial;
        }

        public boolean isMinuteKnown() {
            return minuteKnown;
        }

        public boolean isSecondKnown() {
            return secondKnown;
        }

        public boolean isFractionalSecKnown() {
            return fractionalSecKnown;
        }

        public DvTemporal getDvTemporal() {
            return dvTemporal;
        }
    }

    public static class DvDate {
        private final boolean dayKnown;
        private final boolean monthKnown;
        private final boolean isPartial;
        private final DvTemporal dvTemporal;

        protected DvDate(boolean dayKnown, boolean monthKnown, boolean isPartial,
                         DvTemporal dvTemporal) {
            this.dayKnown = dayKnown;
            this.monthKnown = monthKnown;
            this.isPartial = isPartial;
            this.dvTemporal = dvTemporal;
        }

        public boolean isDayKnown() {
            return dayKnown;
        }

        public boolean isMonthKnown() {
            return monthKnown;
        }

        public boolean isPartial() {
            return isPartial;
        }

        public DvTemporal getDvTemporal() {
            return dvTemporal;
        }
    }

    public static class Participation {
        private final PartyProxy performer;
        private final DvText function;
        private final DvCodedText mode;
        private final DvInterval time;

        public PartyProxy getPerformer() {
            return performer;
        }

        public DvText getFunction() {
            return function;
        }

        public DvCodedText getMode() {
            return mode;
        }

        public DvInterval getTime() {
            return time;
        }

        protected Participation(PartyProxy performer, DvText function,
                                DvCodedText mode, DvInterval time) {
            if (performer == null) {
                throw new IllegalArgumentException("null performer");
            }
            if (function == null) {
                throw new IllegalArgumentException("null function");
            }
            if (mode == null) {
                throw new IllegalArgumentException("null mode");
            }
            this.performer = performer;
            this.function = function;
            this.mode = mode;
            this.time = time;
        }
    }

    public static class AuditDetails {
        private final String timePosition;
        private final PartyProxy committer;
        private final DvDateTime timeCommitted;
        private final DvCodedText changeType;
        private final DvText description;

        protected AuditDetails(String timePosition, PartyProxy committer,
                               DvDateTime timeCommitted, DvCodedText changeType,
                               DvText description) {
            this.timePosition = timePosition;
            this.committer = committer;
            this.timeCommitted = timeCommitted;
            this.changeType = changeType;
            this.description = description;
        }

        public String getTimePosition() {
            return timePosition;
        }

        public PartyProxy getCommitter() {
            return committer;
        }

        public DvDateTime getTimeCommitted() {
            return timeCommitted;
        }

        public DvCodedText getChangeType() {
            return changeType;
        }

        public DvText getDescription() {
            return description;
        }
    }

    public static class Attestation {
        private final AuditDetails auditDetails;
        private final DvMultimedia attestedView;
        private final String proof;
        private final Set<DVEHRURI> items;
        private final DvText reason;
        private final boolean isPending;

        protected Attestation(AuditDetails auditDetails, DvMultimedia attestedView,
                              String proof, Set<DVEHRURI> items, DvText reason,
                              boolean isPending) {
            if (items != null && items.isEmpty()) {
                throw new IllegalArgumentException("empty items");
            }
            if (reason == null) {
                throw new IllegalArgumentException("null reason");
            }

            this.auditDetails = auditDetails;
            this.attestedView = attestedView;
            this.proof = proof;
            this.items = items;
            this.reason = reason;
            this.isPending = isPending;
        }

        public AuditDetails getAuditDetails() {
            return auditDetails;
        }

        public DvMultimedia getAttestedView() {
            return attestedView;
        }

        public String getProof() {
            return proof;
        }

        public Set<DVEHRURI> getItems() {
            return items;
        }

        public DvText getReason() {
            return reason;
        }

        public boolean isPending() {
            return isPending;
        }
    }

    public static class RevisionHistoryItem {
        private final List<AuditDetails> audits;
        private final ObjectVersionID versionID;

        protected RevisionHistoryItem(List<AuditDetails> audits,
                                      ObjectVersionID versionID) {
            if (audits == null || audits.size() == 0) {
                throw new IllegalArgumentException("empty audits");
            }
            if (versionID == null) {
                throw new IllegalArgumentException("null versionId");
            }

            this.audits = audits;
            this.versionID = versionID;
        }

        public List<AuditDetails> getAudits() {
            return audits;
        }

        public ObjectVersionID getVersionID() {
            return versionID;
        }
    }

    public static class RevisionHistory {
        private final List<RevisionHistoryItem> items;

        protected RevisionHistory(List<RevisionHistoryItem> items) {
            if (items == null || items.size() == 0) {
                throw new IllegalArgumentException("empty items");
            }
            this.items = items;
        }

        public List<RevisionHistoryItem> getItems() {
            return items;
        }
    }

    public static class Contribution {
        private final ObjectID uid;
        private final Set<ObjectRef> versions;
        private final AuditDetails audit;

        protected Contribution(ObjectID uid, Set<ObjectRef> versions,
                               AuditDetails audit) {
            if (uid == null) {
                throw new IllegalArgumentException("null uid");
            }
            if (audit == null) {
                throw new IllegalArgumentException("null audit");
            }
            if (audit.getDescription() == null) {
                throw new IllegalArgumentException("null audit description");
            }
            if (versions == null || versions.isEmpty()) {
                throw new IllegalArgumentException("invalid versions");
            }
            this.uid = uid;
            this.versions = versions;
            this.audit = audit;
        }

        public ObjectID getUid() {
            return uid;
        }

        public Set<ObjectRef> getVersions() {
            return versions;
        }

        public AuditDetails getAudit() {
            return audit;
        }
    }

    public static class Folder {
        private final Locatable locatable;
        private final List<Folder> folders;
        private final List<ObjectRef> items;

        protected Folder(Locatable locatable, List<Folder> folders,
                         List<ObjectRef> items) {
            if (folders != null && folders.size() == 0) {
                throw new IllegalArgumentException("sub-folders vazios");
            }
            this.locatable = locatable;
            this.folders = folders;
            this.items = items;
        }

        public Locatable getLocatable() {
            return locatable;
        }

        public List<Folder> getFolders() {
            return folders;
        }

        public List<ObjectRef> getItems() {
            return items;
        }
    }

    public static class ResourceDescription {
        private final Map<String, String> originalAuthor;
        private final List<String> otherContributors;
        private final String lifecycleState;
        private final List<ResourceDescriptionItem> details;
        private final String resourcePackageUri;
        private final Map<String, String> otherDetails;
        private final AuthoredResource parentResource;

        public ResourceDescription(Map<String, String> originalAuthor,
                                   List<String> otherContributors,
                                   String lifecycleState,
                                   List<ResourceDescriptionItem> details,
                                   String resourcePackageUri,
                                   Map<String, String> otherDetails,
                                   AuthoredResource parentResource) {
            this.originalAuthor = originalAuthor;
            this.otherContributors = otherContributors;
            this.lifecycleState = lifecycleState;
            this.details = details;
            this.resourcePackageUri = resourcePackageUri;
            this.otherDetails = otherDetails;
            this.parentResource = parentResource;
        }

        public Map<String, String> getOriginalAuthor() {
            return originalAuthor;
        }

        public List<String> getOtherContributors() {
            return otherContributors;
        }

        public String getLifecycleState() {
            return lifecycleState;
        }

        public List<ResourceDescriptionItem> getDetails() {
            return details;
        }

        public String getResourcePackageUri() {
            return resourcePackageUri;
        }

        public Map<String, String> getOtherDetails() {
            return otherDetails;
        }

        public AuthoredResource getParentResource() {
            return parentResource;
        }
    }

    public static class AuthoredResource {
        private final CodePhrase originalLanguage;
        private final Map<String, TranslationDetails> translations;
        private final ResourceDescription description;
        private final RevisionHistory revisionHistory;
        private final boolean isControlled;

        public AuthoredResource(CodePhrase originalLanguage,
                                Map<String, TranslationDetails> translations,
                                ResourceDescription description,
                                RevisionHistory revisionHistory,
                                boolean isControlled) {
            if (originalLanguage == null) {
                throw new IllegalArgumentException("null originalLanguage");
            }
            if (translations != null) {
                if (translations.isEmpty()) {
                    throw new IllegalArgumentException("empty translations");
                }
                if (translations.containsKey(originalLanguage.getCodeString())) {
                    throw new IllegalArgumentException(
                            "original language in translations");
                }
            }
            if (isControlled == (revisionHistory == null)) {
                throw new IllegalArgumentException(
                        "breach of revision history validity");
            }
            this.originalLanguage = originalLanguage;
            this.translations = translations;
            this.description = description;
            this.revisionHistory = revisionHistory;
            this.isControlled = isControlled;
        }

        public CodePhrase getOriginalLanguage() {
            return originalLanguage;
        }

        public Map<String, TranslationDetails> getTranslations() {
            return translations;
        }

        public ResourceDescription getDescription() {
            return description;
        }

        public RevisionHistory getRevisionHistory() {
            return revisionHistory;
        }

        public boolean isControlled() {
            return isControlled;
        }
    }

    public static class EventWithItemTree {
        private final Locatable locatable;
        private final DvDateTime time;
        private final ItemTree data;
        private final ItemStructure state;

        public EventWithItemTree(Locatable locatable, DvDateTime time,
                                 ItemTree data, ItemStructure state) {
            if (time == null) {
                throw new IllegalArgumentException("null time");
            }
            if (data == null) {
                throw new IllegalArgumentException("null data");
            }
            this.locatable = locatable;
            this.time = time;
            this.data = data;
            this.state = state;
        }

        public Locatable getLocatable() {
            return locatable;
        }

        public DvDateTime getTime() {
            return time;
        }

        public ItemTree getData() {
            return data;
        }

        public ItemStructure getState() {
            return state;
        }
    }

    public static class EventWithItemSingle {
        private final Locatable locatable;
        private final DvDateTime time;
        private final ItemSingle data;
        private final ItemStructure state;

        public EventWithItemSingle(Locatable locatable, DvDateTime time,
                                   ItemSingle data, ItemStructure state) {
            if (time == null) {
                throw new IllegalArgumentException("null time");
            }
            if (data == null) {
                throw new IllegalArgumentException("null data");
            }
            this.locatable = locatable;
            this.time = time;
            this.data = data;
            this.state = state;
        }

        public Locatable getLocatable() {
            return locatable;
        }

        public DvDateTime getTime() {
            return time;
        }

        public ItemSingle getData() {
            return data;
        }

        public ItemStructure getState() {
            return state;
        }
    }

    public static class EventWithItemTable {
        private final Locatable locatable;
        private final DvDateTime time;
        private final ItemTable data;
        private final ItemStructure state;

        public EventWithItemTable(Locatable locatable, DvDateTime time,
                                  ItemTable data, ItemStructure state) {
            if (time == null) {
                throw new IllegalArgumentException("null time");
            }
            if (data == null) {
                throw new IllegalArgumentException("null data");
            }
            this.locatable = locatable;
            this.time = time;
            this.data = data;
            this.state = state;
        }

        public Locatable getLocatable() {
            return locatable;
        }

        public DvDateTime getTime() {
            return time;
        }

        public ItemTable getData() {
            return data;
        }

        public ItemStructure getState() {
            return state;
        }
    }


    public static class HistoryWithItemTree {
        private final DataStructure dataStructure;
        private final DvDateTime origin;
        private final List<EventWithItemTree> events;
        private final DvDuration period;
        private final DvDuration duration;
        private final ItemStructure summary;

        public HistoryWithItemTree(DataStructure dataStructure,
                                   DvDateTime origin,
                                   List<EventWithItemTree> events,
                                   DvDuration period, DvDuration duration,
                                   ItemStructure summary) {
            if (origin == null) {
                throw new IllegalArgumentException("null origin");
            }
            if (events != null && events.size() == 0) {
                throw new IllegalArgumentException("empty events");
            }
            if (events == null && summary == null) {
                throw new IllegalArgumentException("null events and summary");
            }
            this.dataStructure = dataStructure;
            this.origin = origin;
            this.events = events;
            this.period = period;
            this.duration = duration;
            this.summary = summary;
        }

        public DataStructure getDataStructure() {
            return dataStructure;
        }

        public DvDateTime getOrigin() {
            return origin;
        }

        public List<EventWithItemTree> getEvents() {
            return events;
        }

        public DvDuration getPeriod() {
            return period;
        }

        public DvDuration getDuration() {
            return duration;
        }

        public ItemStructure getSummary() {
            return summary;
        }
    }

    public static class HistoryWithItemSingle {
        private final DataStructure dataStructure;
        private final DvDateTime origin;
        private final List<EventWithItemSingle> events;
        private final DvDuration period;
        private final DvDuration duration;
        private final ItemStructure summary;

        public HistoryWithItemSingle(DataStructure dataStructure,
                                     DvDateTime origin,
                                     List<EventWithItemSingle> events,
                                     DvDuration period, DvDuration duration,
                                     ItemStructure summary) {
            if (origin == null) {
                throw new IllegalArgumentException("null origin");
            }
            if (events != null && events.size() == 0) {
                throw new IllegalArgumentException("empty events");
            }
            if (events == null && summary == null) {
                throw new IllegalArgumentException("null events and summary");
            }
            this.dataStructure = dataStructure;
            this.origin = origin;
            this.events = events;
            this.period = period;
            this.duration = duration;
            this.summary = summary;
        }

        public DataStructure getDataStructure() {
            return dataStructure;
        }

        public DvDateTime getOrigin() {
            return origin;
        }

        public List<EventWithItemSingle> getEvents() {
            return events;
        }

        public DvDuration getPeriod() {
            return period;
        }

        public DvDuration getDuration() {
            return duration;
        }

        public ItemStructure getSummary() {
            return summary;
        }
    }

    public static class HistoryWithItemTable {
        private final DataStructure dataStructure;
        private final DvDateTime origin;
        private final List<EventWithItemTable> events;
        private final DvDuration period;
        private final DvDuration duration;
        private final ItemStructure summary;

        public HistoryWithItemTable(DataStructure dataStructure,
                                    DvDateTime origin,
                                    List<EventWithItemTable> events,
                                    DvDuration period, DvDuration duration,
                                    ItemStructure summary) {
            if (origin == null) {
                throw new IllegalArgumentException("null origin");
            }
            if (events != null && events.size() == 0) {
                throw new IllegalArgumentException("empty events");
            }
            if (events == null && summary == null) {
                throw new IllegalArgumentException("null events and summary");
            }
            this.dataStructure = dataStructure;
            this.origin = origin;
            this.events = events;
            this.period = period;
            this.duration = duration;
            this.summary = summary;
        }

        public DataStructure getDataStructure() {
            return dataStructure;
        }

        public DvDateTime getOrigin() {
            return origin;
        }

        public List<EventWithItemTable> getEvents() {
            return events;
        }

        public DvDuration getPeriod() {
            return period;
        }

        public DvDuration getDuration() {
            return duration;
        }

        public ItemStructure getSummary() {
            return summary;
        }
    }

    public static class IntervalEventWithItemTree {
        private final EventWithItemTree event;
        private final DvDuration width;
        private final DvCodedText mathFunction;
        private final int sampleCount;

        public IntervalEventWithItemTree(EventWithItemTree event,
                                         DvDuration width,
                                         DvCodedText mathFunction,
                                         int sampleCount) {
            if (width == null) {
                throw new IllegalArgumentException("null width");
            }
            if (mathFunction == null) {
                throw new IllegalArgumentException("null mathFunction");
            }
            this.event = event;
            this.width = width;
            this.mathFunction = mathFunction;
            this.sampleCount = sampleCount;
        }

        public EventWithItemTree getEvent() {
            return event;
        }

        public DvDuration getWidth() {
            return width;
        }

        public DvCodedText getMathFunction() {
            return mathFunction;
        }

        public int getSampleCount() {
            return sampleCount;
        }
    }

    public static class IntervalEventWithItemSingle {
        private final EventWithItemSingle event;
        private final DvDuration width;
        private final DvCodedText mathFunction;
        private final int sampleCount;

        public IntervalEventWithItemSingle(EventWithItemSingle event,
                                           DvDuration width,
                                           DvCodedText mathFunction,
                                           int sampleCount) {
            if (width == null) {
                throw new IllegalArgumentException("null width");
            }
            if (mathFunction == null) {
                throw new IllegalArgumentException("null mathFunction");
            }
            this.event = event;
            this.width = width;
            this.mathFunction = mathFunction;
            this.sampleCount = sampleCount;
        }

        public EventWithItemSingle getEvent() {
            return event;
        }

        public DvDuration getWidth() {
            return width;
        }

        public DvCodedText getMathFunction() {
            return mathFunction;
        }

        public int getSampleCount() {
            return sampleCount;
        }
    }

    public static class IntervalEventWithItemTable {
        private final EventWithItemTable event;
        private final DvDuration width;
        private final DvCodedText mathFunction;
        private final int sampleCount;

        public IntervalEventWithItemTable(EventWithItemTable event,
                                          DvDuration width,
                                          DvCodedText mathFunction,
                                          int sampleCount) {
            if (width == null) {
                throw new IllegalArgumentException("null width");
            }
            if (mathFunction == null) {
                throw new IllegalArgumentException("null mathFunction");
            }
            this.event = event;
            this.width = width;
            this.mathFunction = mathFunction;
            this.sampleCount = sampleCount;
        }

        public EventWithItemTable getEvent() {
            return event;
        }

        public DvDuration getWidth() {
            return width;
        }

        public DvCodedText getMathFunction() {
            return mathFunction;
        }

        public int getSampleCount() {
            return sampleCount;
        }
    }

    public static class PointEventWithItemTree {
        private final EventWithItemTree event;

        public PointEventWithItemTree(EventWithItemTree event) {
            this.event = event;
        }

        public EventWithItemTree getEvent() {
            return event;
        }
    }

    public static class PointEventWithItemSingle {
        private final EventWithItemSingle event;

        public PointEventWithItemSingle(EventWithItemSingle event) {
            this.event = event;
        }

        public EventWithItemSingle getEvent() {
            return event;
        }
    }

    public static class PointEventWithItemTable {
        private final EventWithItemTable event;

        public PointEventWithItemTable(EventWithItemTable event) {
            this.event = event;
        }

        public EventWithItemTable getEvent() {
            return event;
        }
    }

    public static class ContentItem {
        private final Locatable locatable;

        public ContentItem(Locatable locatable) {
            if (locatable == null) {
                throw new IllegalArgumentException("null locatable");
            }
            this.locatable = locatable;
        }

        public Locatable getLocatable() {
            return locatable;
        }
    }

    public static class Entry {
        private final ContentItem contentItem;
        private final CodePhrase language;
        private final CodePhrase encoding;
        private final PartyProxy subject;
        private final PartyProxy provider;
        private final ObjectRef workflowId;
        private final List<Participation> otherParticipations;

        public ContentItem getContentItem() {
            return contentItem;
        }

        public CodePhrase getLanguage() {
            return language;
        }

        public CodePhrase getEncoding() {
            return encoding;
        }

        public PartyProxy getSubject() {
            return subject;
        }

        public PartyProxy getProvider() {
            return provider;
        }

        public ObjectRef getWorkflowId() {
            return workflowId;
        }

        public List<Participation> getOtherParticipations() {
            return otherParticipations;
        }

        public Entry(ContentItem contentItem, CodePhrase language,
                     CodePhrase encoding, PartyProxy subject,
                     PartyProxy provider, ObjectRef workflowId,
                     List<Participation> otherParticipations) {
            if (language == null) {
                throw new IllegalArgumentException("null language");
            }
            if (encoding == null) {
                throw new IllegalArgumentException("null encoding");
            }
            if (subject == null) {
                throw new IllegalArgumentException("null subject");
            }
            if (otherParticipations != null && otherParticipations.isEmpty()) {
                throw new IllegalArgumentException("otherParticipations vazio");
            }

            this.contentItem = contentItem;
            this.language = language;
            this.encoding = encoding;
            this.subject = subject;
            this.provider = provider;
            this.workflowId = workflowId;
            this.otherParticipations = (otherParticipations == null ? null :
                    new ArrayList<Participation>(otherParticipations));
        }
    }

    public static class CareEntry {
        private final Entry entry;
        private final ItemStructure protocol;
        private final ObjectRef guidelineId;

        public CareEntry(Entry entry, ItemStructure protocol,
                         ObjectRef guidelineId) {
            this.entry = entry;
            this.protocol = protocol;
            this.guidelineId = guidelineId;
        }

        public Entry getEntry() {
            return entry;
        }

        public ItemStructure getProtocol() {
            return protocol;
        }

        public ObjectRef getGuidelineId() {
            return guidelineId;
        }
    }

    public static class Action {
        private final DvDateTime time;
        private final ItemStructure description;
        private final ISMTransition ismTransition;
        private final InstructionDetails instructionDetails;

        public Action(DvDateTime time, ItemStructure description,
                      ISMTransition ismTransition,
                      InstructionDetails instructionDetails) {

            if (time == null) {
                throw new IllegalArgumentException("null time");
            }
            if (description == null) {
                throw new IllegalArgumentException("null description");
            }
            if (ismTransition == null) {
                throw new IllegalArgumentException("null ismTransition");
            }

            this.time = time;
            this.description = description;
            this.ismTransition = ismTransition;
            this.instructionDetails = instructionDetails;
        }

        public DvDateTime getTime() {
            return time;
        }

        public ItemStructure getDescription() {
            return description;
        }

        public ISMTransition getIsmTransition() {
            return ismTransition;
        }

        public InstructionDetails getInstructionDetails() {
            return instructionDetails;
        }
    }

    public static class AdminEntry {
        private final Entry entry;
        private final ItemStructure data;

        public AdminEntry(Entry entry, ItemStructure data) {
            if (data == null) {
                throw new IllegalArgumentException("null data");
            }
            this.entry = entry;
            this.data = data;
        }

        public Entry getEntry() {
            return entry;
        }

        public ItemStructure getData() {
            return data;
        }
    }

    public static class Evaluation {
        private final CareEntry careEntry;
        private final ItemStructure data;

        public Evaluation(CareEntry careEntry, ItemStructure data) {
            if (data == null) {
                throw new IllegalArgumentException("null data");
            }
            this.careEntry = careEntry;
            this.data = data;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public ItemStructure getData() {
            return data;
        }
    }

    public static class Instruction {
        private final CareEntry careEntry;
        private final DvText narrative;
        private final List<Activity> activities;
        private final DvDateTime expiryTime;
        private final DvParsable wfDefinition;

        public Instruction(CareEntry careEntry, DvText narrative,
                           List<Activity> activities, DvDateTime expiryTime,
                           DvParsable wfDefinition) {

            if (narrative == null) {
                throw new IllegalArgumentException("null narrative");
            }
            if (activities != null && activities.size() == 0) {
                throw new IllegalArgumentException("empty activities");
            }

            this.careEntry = careEntry;
            this.narrative = narrative;
            this.activities = activities;
            this.expiryTime = expiryTime;
            this.wfDefinition = wfDefinition;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public DvText getNarrative() {
            return narrative;
        }

        public List<Activity> getActivities() {
            return activities;
        }

        public DvDateTime getExpiryTime() {
            return expiryTime;
        }

        public DvParsable getWfDefinition() {
            return wfDefinition;
        }
    }

    public static class ObservationWithItemTreeItemTree {
        private final CareEntry careEntry;
        private final HistoryWithItemTree data;
        private final HistoryWithItemTree state;

        public ObservationWithItemTreeItemTree(CareEntry careEntry,
                                               HistoryWithItemTree data,
                                               HistoryWithItemTree state) {
            if(careEntry == null){
                throw new IllegalArgumentException("null careEntry");
            }
            if(data == null){
                throw new IllegalArgumentException("null data");
            }
            this.careEntry = careEntry;
            this.data = data;
            this.state = state;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public HistoryWithItemTree getData() {
            return data;
        }

        public HistoryWithItemTree getState() {
            return state;
        }
    }

    public static class ObservationWithItemTreeItemSingle {
        private final CareEntry careEntry;
        private final HistoryWithItemTree data;
        private final HistoryWithItemSingle state;

        public ObservationWithItemTreeItemSingle(CareEntry careEntry,
                                                 HistoryWithItemTree data,
                                                 HistoryWithItemSingle state) {
            if(careEntry == null){
                throw new IllegalArgumentException("null careEntry");
            }
            if(data == null){
                throw new IllegalArgumentException("null data");
            }
            this.careEntry = careEntry;
            this.data = data;
            this.state = state;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public HistoryWithItemTree getData() {
            return data;
        }

        public HistoryWithItemSingle getState() {
            return state;
        }
    }

    public static class ObservationWithItemTreeItemTable {
        private final CareEntry careEntry;
        private final HistoryWithItemTree data;
        private final HistoryWithItemTable state;

        public ObservationWithItemTreeItemTable(CareEntry careEntry,
                                                HistoryWithItemTree data,
                                                HistoryWithItemTable state) {
            if(careEntry == null){
                throw new IllegalArgumentException("null careEntry");
            }
            if(data == null){
                throw new IllegalArgumentException("null data");
            }
            this.careEntry = careEntry;
            this.data = data;
            this.state = state;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public HistoryWithItemTree getData() {
            return data;
        }

        public HistoryWithItemTable getState() {
            return state;
        }
    }

    public static class ObservationWithItemSingleItemTree {
        private final CareEntry careEntry;
        private final HistoryWithItemSingle data;
        private final HistoryWithItemTree state;

        public ObservationWithItemSingleItemTree(CareEntry careEntry,
                                                 HistoryWithItemSingle data,
                                                 HistoryWithItemTree state) {
            if(careEntry == null){
                throw new IllegalArgumentException("null careEntry");
            }
            if(data == null){
                throw new IllegalArgumentException("null data");
            }
            this.careEntry = careEntry;
            this.data = data;
            this.state = state;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public HistoryWithItemSingle getData() {
            return data;
        }

        public HistoryWithItemTree getState() {
            return state;
        }
    }

    public static class ObservationWithItemSingleItemSingle {
        private final CareEntry careEntry;
        private final HistoryWithItemSingle data;
        private final HistoryWithItemSingle state;

        public ObservationWithItemSingleItemSingle(CareEntry careEntry,
                                                   HistoryWithItemSingle data,
                                                   HistoryWithItemSingle state) {
            if(careEntry == null){
                throw new IllegalArgumentException("null careEntry");
            }
            if(data == null){
                throw new IllegalArgumentException("null data");
            }
            this.careEntry = careEntry;
            this.data = data;
            this.state = state;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public HistoryWithItemSingle getData() {
            return data;
        }

        public HistoryWithItemSingle getState() {
            return state;
        }
    }

    public static class ObservationWithItemSingleItemTable {
        private final CareEntry careEntry;
        private final HistoryWithItemSingle data;
        private final HistoryWithItemTable state;

        public ObservationWithItemSingleItemTable(CareEntry careEntry,
                                                  HistoryWithItemSingle data,
                                                HistoryWithItemTable state) {
            if(careEntry == null){
                throw new IllegalArgumentException("null careEntry");
            }
            if(data == null){
                throw new IllegalArgumentException("null data");
            }
            this.careEntry = careEntry;
            this.data = data;
            this.state = state;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public HistoryWithItemSingle getData() {
            return data;
        }

        public HistoryWithItemTable getState() {
            return state;
        }
    }

    public static class ObservationWithItemTableItemTree {
        private final CareEntry careEntry;
        private final HistoryWithItemTable data;
        private final HistoryWithItemTree state;

        public ObservationWithItemTableItemTree(CareEntry careEntry,
                                                HistoryWithItemTable data,
                                                HistoryWithItemTree state) {
            if(careEntry == null){
                throw new IllegalArgumentException("null careEntry");
            }
            if(data == null){
                throw new IllegalArgumentException("null data");
            }
            this.careEntry = careEntry;
            this.data = data;
            this.state = state;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public HistoryWithItemTable getData() {
            return data;
        }

        public HistoryWithItemTree getState() {
            return state;
        }
    }

    public static class ObservationWithItemTableItemSingle {
        private final CareEntry careEntry;
        private final HistoryWithItemTable data;
        private final HistoryWithItemSingle state;

        public ObservationWithItemTableItemSingle(CareEntry careEntry,
                                                  HistoryWithItemTable data,
                                                  HistoryWithItemSingle state) {
            if(careEntry == null){
                throw new IllegalArgumentException("null careEntry");
            }
            if(data == null){
                throw new IllegalArgumentException("null data");
            }
            this.careEntry = careEntry;
            this.data = data;
            this.state = state;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public HistoryWithItemTable getData() {
            return data;
        }

        public HistoryWithItemSingle getState() {
            return state;
        }
    }

    public static class ObservationWithItemTableItemTable {
        private final CareEntry careEntry;
        private final HistoryWithItemTable data;
        private final HistoryWithItemTable state;

        public ObservationWithItemTableItemTable(CareEntry careEntry,
                                                 HistoryWithItemTable data,
                                                 HistoryWithItemTable state) {
            if(careEntry == null){
                throw new IllegalArgumentException("null careEntry");
            }
            if(data == null){
                throw new IllegalArgumentException("null data");
            }
            this.careEntry = careEntry;
            this.data = data;
            this.state = state;
        }

        public CareEntry getCareEntry() {
            return careEntry;
        }

        public HistoryWithItemTable getData() {
            return data;
        }

        public HistoryWithItemTable getState() {
            return state;
        }
    }
}
