/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.com.github.gabrielsxp.healthcodec;

import com.github.gabrielsxp.healthcodec.RMObject.*;
import com.github.gabrielsxp.healthcodec.RMObjectFactory;

/**
 *
 * @author Gabriel
 */
public class RMObjectInstance {
    public static DvBoolean DvBoolean(){
        return RMObjectFactory.newDvBoolean(true);
    }
    
    public static DvIdentifier DvIdentifier(){
        String issuer = "issuer";
        String assigner = "assigner";
        String id = "id";
        String type = "type";
        return RMObjectFactory.newDvIdentifier(issuer, assigner, id, type);
    }
    
    public static InternetID InternetID(){
        String value = "InternetID";
        
        return RMObjectFactory.newInternetID(value);
    }
    
    public static ISO_OID ISOOID() {
        String value = "ISO_OID";
        
        return RMObjectFactory.newISOOID(value);
    }
    
    public static UUID UUID(){
        String value = "UUID";
        
        return RMObjectFactory.newUUID(value);
    }
}
