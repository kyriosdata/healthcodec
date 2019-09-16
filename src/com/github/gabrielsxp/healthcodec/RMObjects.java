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

/**
 *
 * @author Gabriel
 */
public class RMObjects {
    static class DvBoolean {
        private final boolean value;
        
        protected DvBoolean(boolean value){
            this.value = value;
        }
        
        public boolean getValue() {
            return value;
        }
    }
    
    static class DvIdentifier {
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
    
    static class InternetID {
        private final String value;

        protected InternetID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    static class ISO_OID {
        private final String value;
        
        protected ISO_OID(String value) {
            this.value = value;
        }
        
        public String getValue(){
            return this.value;
        }   
    }
    
    static class UUID {
        private final String value;

        protected UUID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    static class GenericID {
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
    
    static class TemplateID {
        private final String value;

        protected TemplateID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        } 
    }
    
    static class TerminologyID {
        private final String value;
        
        protected TerminologyID(String value){
            this.value = value;
        }
        
        public String getValue(){
            return this.value;
        }
    }
    
    static class CodePhrase {
        private final TerminologyID terminologyID;
        private final String value;
        
        protected CodePhrase(TerminologyID terminologyID, String value){
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
    
    static class DVURI {
        private final String value;

        protected DVURI(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    static class DVEHRURI {
        private final String value;

        protected DVEHRURI(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    static class VersionTreeID {
        private final String value;

        protected VersionTreeID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    static class ArchetypeID {
        private final String value;

        protected ArchetypeID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    static class ObjectVersionID {
        private final String value;

        protected ObjectVersionID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    static class HierObjectID {
        private final String value;

        protected HierObjectID(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
