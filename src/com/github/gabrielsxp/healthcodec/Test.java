/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gabrielsxp.healthcodec;

import com.github.gabrielsxp.healthcodec.RMObject.DvText;
import com.github.gabrielsxp.healthcodec.RMObject.TermMapping;
import com.github.gabrielsxp.healthcodec.RMObjectSerialization.TermMappingSerializer;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException{
        String value = "DvTextValue";
        List<TermMapping> mappings = new ArrayList<>();
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
                value, null, formatting, hyperlink, language, charset);
        
        String definingCodeTIDValue = "Defining Code Terminology ID Value";
        RMObject.TerminologyID definingCodeTID = 
                RMObjectFactory.newTerminologyID(definingCodeTIDValue);
        String definingCodeValue = "Defining Code Value";
        RMObject.CodePhrase definingCode = 
                RMObjectFactory.newCodePhrase(
                        definingCodeTID, definingCodeTIDValue);
        
        RMObject.DvCodedText purpose = 
                RMObjectFactory.newDvCodedText(dvText, definingCode);
        
        TermMapping t = 
                RMObjectFactory.newTermMapping(charset, RMObject.Match.BROADER, purpose);
        mappings.add(t);
        mappings.add(t);
        
        
        RMObjectSerializationClient s = RMObjectSerializationClient.create();
        
        s.serializeDvText(
                value, mappings, formatting, hyperlink, language, charset);
        DvText d = s.deserializeDvText();
        
        System.out.println(d.getMappings().get(0).getTarget().getValue());
        
        for(int i = 0; i < s.getBuffer().length; i++){
            System.out.println("[" + i + "] = " + s.getBuffer()[i]);
        }
    }
}
