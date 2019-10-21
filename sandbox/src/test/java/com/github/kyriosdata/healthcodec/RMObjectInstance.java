/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kyriosdata.healthcodec;

/**
 *
 * @author Gabriel
 */
public class RMObjectInstance {
    public static RMObject.DvBoolean DvBoolean(){
        return RMObjectFactory.newDvBoolean(true);
    }
    
    public static RMObject.DvIdentifier DvIdentifier(){
        String issuer = "issuer";
        String assigner = "assigner";
        String id = "id";
        String type = "type";
        return RMObjectFactory.newDvIdentifier(issuer, assigner, id, type);
    }
    
    public static RMObject.InternetID InternetID(){
        String value = "InternetID";
        
        return RMObjectFactory.newInternetID(value);
    }
    
    public static RMObject.ISO_OID ISOOID() {
        String value = "ISO_OID";
        
        return RMObjectFactory.newISOOID(value);
    }
    
    public static RMObject.UUID UUID(){
        String value = "UUID";
        
        return RMObjectFactory.newUUID(value);
    }
}
