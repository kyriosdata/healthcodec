/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.github.gabrielsxp.healthcodec;

import main.java.com.github.gabrielsxp.healthcodec.RMObject.*;

/**
 *
 * @author Gabriel
 */
public class RMObjectFactory {
    public static DvBoolean newDvBoolean(boolean value){
        return new DvBoolean(value);
    }
    
    public static DvIdentifier newDvIdentifier(
        String issuer, String assigner, String id, String type
    ){
        return new DvIdentifier(issuer, assigner, id, type);
    }
    
    public static InternetID newInternetID(String value){
        return new InternetID(value);
    }
    
    public static ISO_OID newISOOID(String value){
        return new ISO_OID(value);
    }
    
    public static UUID newUUID(String value){
        return new UUID(value);
    }
    
    public static GenericID newGenericID(String value, String scheme){
        return new GenericID(value, scheme);
    }
    
    public static TemplateID newTemplateID(String value){
        return new TemplateID(value);
    }
    
    public static TerminologyID newTerminologyID(String value){
        return new TerminologyID(value);
    }
    
    public static CodePhrase newCodePhrase(
            TerminologyID terminologyID, String value) {
        return new CodePhrase(terminologyID, value);
    }
    
    public static DVURI newDVURI(String value){
        return new DVURI(value);
    }
    
    public static DVEHRURI newDVEHRURI(String value){
        return new DVEHRURI(value);
    }
    
    public static VersionTreeID newVersionTreeID(String value){
        return new VersionTreeID(value);
    }
    
    public static ArchetypeID newArchetypeID(String value){
        return new ArchetypeID(value);
    }
    
    public static ObjectVersionID newVersionID(String value){
        return new ObjectVersionID(value);
    }
    
    public static HierObjectID newHierObjectID(String value){
        return new HierObjectID(value);
    }
    
}
