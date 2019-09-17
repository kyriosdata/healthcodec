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
package main.java.com.github.gabrielsxp.healthcodec;

import main.java.com.github.gabrielsxp.healthcodec.RMObject.*;

/**
 *
 * @author Gabriel
 */
 public interface Deserializer {

    /**
     * Deserializador de DvBoolean
     * @return Instância de DvBoolean
     */
     DvBoolean deserializeDvBoolean();
    
    /**
     * Deserializador de DvIdentifier
     * @return Instância de DvIdentifier
     */
     DvIdentifier deserializeDvIdentifier();
    
    /**
     * Deserializador de InternetID
     * @return Instância de InternetID
     */
     InternetID deserializeInternetID();
    
    /**
     * Deserializador de ISO_OID
     * @return Instância de ISO_OID
     */
     ISO_OID deserializeISOOID();
    
    /**
     * Deserializador de UUID
     * @return Instância de UUID
     */
     UUID deserializeUUID();
    
    /**
     * Deserializador de TerminologyID
     * @return Instância de TerminologyID
     */
     TerminologyID deserializeTerminologyID();
    
    /**
     * Deserializador de GenericID
     * @return Instância de GenericID
     */
     GenericID deserializeGenericID();
    
    /**
     * Deserializador de TemplateID
     * @return Instância de TemplateID
     */
     TemplateID deserializeTemplateID();
    
    /**
     * Deserializador de CodePhrase
     * @return Instância de CodePhrase
     */
     CodePhrase deserializeCodePhrase();
    
    /**
     * Deserializador de DVURI
     * @return Instância de DVURI
     */
     DVURI deserializeDVURI();
    
    /**
     * Deserializador de DVEHRURI
     * @return Instância de DVEHRURI
     */
     DVEHRURI deserializeDVEHRURI();
    
    /**
    * Deserializador de VersionTreeID
     * @return Instância de VersionTreeID
     */
     VersionTreeID deserializeVersionTreeID();
    
    /**
     * Deserializador de ArchetypeID
     * @return Instância de ArchetypeID
     */
     ArchetypeID deserializeArchetypeID();
    
    /**
     * Deserializador de ObjectVersionID
     * @return Instância de ObjectVersionID
     */
     ObjectVersionID deserializeObjectVersionID();
    
    /**
     * Deserializador de HierObjectID
     * @return Instância de HierObjectID
     */
     HierObjectID deserializeHierObjectID();
}
