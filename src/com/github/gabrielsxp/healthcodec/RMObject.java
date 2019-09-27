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

        protected DvIdentifier(String issuer, String assigner, String id, String type) {
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

    public static class InternetID {

        private final String value;

        protected InternetID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class ISO_OID {

        private final String value;

        protected ISO_OID(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static class UUID {

        private final String value;

        protected UUID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class GenericID {

        private final String value;
        private final String scheme;

        protected GenericID(String value, String scheme) {
            this.value = value;
            this.scheme = scheme;
        }

        public String getScheme() {
            return scheme;
        }

        public String getValue() {
            return value;
        }
    }

    public static class TemplateID {

        private final String value;

        protected TemplateID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class TerminologyID {

        private final String value;

        protected TerminologyID(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static class CodePhrase {

        private final TerminologyID terminologyID;
        private final String value;

        protected CodePhrase(TerminologyID terminologyID, String value) {
            this.terminologyID = terminologyID;
            this.value = value;
        }

        public TerminologyID getTerminologyID() {
            return terminologyID;
        }

        public String getValue() {
            return value;
        }
    }

    public static class DVURI {

        private final String value;

        protected DVURI(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class DVEHRURI {

        private final String value;

        protected DVEHRURI(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class VersionTreeID {

        private final String value;

        protected VersionTreeID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class ArchetypeID {

        private final String value;

        protected ArchetypeID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class ObjectVersionID {

        private final String value;

        protected ObjectVersionID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class HierObjectID {

        private final String value;

        protected HierObjectID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class ObjectID {

        private final String value;

        protected ObjectID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class PartyRef {

        private final ObjectID id;
        private final String value;

        protected PartyRef(ObjectID id, String value) {
            this.id = id;
            this.value = value;
        }

        public ObjectID getId() {
            return id;
        }

        public String getValue() {
            return value;
        }
    }

    public static class ObjectRef {

        private final ObjectID id;
        private final String namespace;
        private final String type;

        protected ObjectRef(ObjectID id, String namespace, String type) {
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

        private final ObjectVersionID id;
        private final String namespace;
        private final String type;
        private final String path;

        public ObjectVersionID getId() {
            return id;
        }

        public String getNamespace() {
            return namespace;
        }

        public String getType() {
            return type;
        }

        public String getPath() {
            return path;
        }

        protected LocatableRef(ObjectVersionID id, String namespace, String type,
                String path) {
            this.id = id;
            this.namespace = namespace;
            this.type = type;
            this.path = path;
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

        private final ObjectID id;

        protected AccessGroupRef(ObjectID id) {
            this.id = id;
        }

        public ObjectID getId() {
            return id;
        }
    }

    public static class PartyIdentified {

        private final PartyRef externalRef;
        private final String name;
        private final List<DvIdentifier> identifiers;

        protected PartyIdentified(PartyRef externalRef, String name,
                List<DvIdentifier> identifiers) {
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

        protected ArchetypeID getArchetypeId() {
            return archetypeId;
        }

        public TemplateID getTemplateId() {
            return templateId;
        }

        public String getRmVersion() {
            return rmVersion;
        }

        public Archetyped(ArchetypeID archetypeId, TemplateID templateId, String rmVersion) {
            this.archetypeId = archetypeId;
            this.templateId = templateId;
            this.rmVersion = rmVersion;
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
            this.value = value;
        }

        public String getValue() {
            return value;
        } 
    }
}
