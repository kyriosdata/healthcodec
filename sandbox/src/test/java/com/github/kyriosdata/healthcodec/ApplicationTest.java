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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * TODO remover ou converter para teste reaplicável e verificável
 * automaticamente
 */
public class ApplicationTest {
    public static void main(String[] args) throws UnsupportedEncodingException{
        String oidValue = "OBJECTID";
        String value = "VALUE";
        String name = "NAME";
        
        RMObject.ObjectID oid = RMObjectFactory.newObjectID(oidValue);
        RMObject.PartyRef externalRef = RMObjectFactory.newPartyRef(oid, value);
        
        String issuer = "ISSUER";
        String assigner = "ASSIGNER";
        String id = "ID";
        String type = "TYPE";

        List<RMObject.DvIdentifier> identifiers = new ArrayList<>();
        identifiers.add(
                RMObjectFactory.newDvIdentifier(issuer, assigner, id, type));
        
        RMObject.PartyIdentified pi = RMObjectFactory.newPartyIdentified(
                externalRef, name, identifiers);
        
        List<RMObject.TermMapping> mappings = null;
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink =  RMObjectFactory.newDVURI(hyperlinkValue);
        
        String languageTIDValue = "Language Terminology ID";
        String languageValue = "Language value";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue, languageValue);
        RMObject.CodePhrase language = RMObjectFactory.newCodePhrase(languageTID,
                languageValue);
        
        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID = 
                RMObjectFactory.newTerminologyID(charsetTIDValue, charsetValue);
        RMObject.CodePhrase charset = 
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);
        
        RMObject.DvText dvText = RMObjectFactory.newDvText(
                value, mappings, formatting, hyperlink, language, charset);
        
        String definingCodeTIDValue = "Defining Code Terminology ID Value";
        RMObject.TerminologyID definingCodeTID = 
                RMObjectFactory.newTerminologyID(definingCodeTIDValue, definingCodeTIDValue);
        String definingCodeValue = "Defining Code Value";
        RMObject.CodePhrase definingCode = 
                RMObjectFactory.newCodePhrase(
                        definingCodeTID, definingCodeTIDValue);
        
        RMObject.DvCodedText relationship = RMObjectFactory.newDvCodedText(
                dvText, definingCode);
        
        RMObject.PartyRelated pr = RMObjectFactory.newPartyRelated(pi, relationship);
        
        
        RMObjectSerializationClient s = RMObjectSerializationClient.create();
        
        s.serializePartyRelated(pr);
    }
}
