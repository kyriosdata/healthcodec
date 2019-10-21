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
