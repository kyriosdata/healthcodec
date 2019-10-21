/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gabrielsxp.healthcodec;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class Test {
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
        RMObject.TerminologyID languageTID = RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language = RMObjectFactory.newCodePhrase(languageTID, 
                languageValue);
        
        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID = 
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset = 
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);
        
        RMObject.DvText dvText = RMObjectFactory.newDvText(
                value, mappings, formatting, hyperlink, language, charset);
        
        String definingCodeTIDValue = "Defining Code Terminology ID Value";
        RMObject.TerminologyID definingCodeTID = 
                RMObjectFactory.newTerminologyID(definingCodeTIDValue);
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
