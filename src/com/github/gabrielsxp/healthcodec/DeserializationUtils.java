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

import static com.github.gabrielsxp.healthcodec.Constants.BYTE_SIZE;
import static com.github.gabrielsxp.healthcodec.Constants.INT_SIZE;
import com.github.gabrielsxp.healthcodec.RMObjects.*;

/**
 *
 * @author Gabriel
 */
public class DeserializationUtils {
    static class DvBooleanDeserializer {
        protected DvBoolean deserialize(Buffer buffer, int offset) {
            boolean value = buffer.readBoolean(offset);
            return new DvBoolean(value);
        } 
    }
    
    static class DvIdentifierDeserializer {
        protected DvIdentifier deserialize(Buffer buffer, int offset){
            int issuerLength = buffer.readInteger(offset);
            int assignerLength = buffer.readInteger(offset + INT_SIZE);
            int idLength = buffer.readInteger(offset + 2 * INT_SIZE);
            int typeLength = buffer.readInteger(offset + 3 * INT_SIZE);
            
            int stringsPosition = offset + 4 * INT_SIZE;
            String issuer = buffer.readString(stringsPosition, issuerLength);
            stringsPosition += issuerLength;
            String assigner = buffer.readString(stringsPosition, assignerLength);
            stringsPosition += assignerLength;
            String id = buffer.readString(stringsPosition, idLength);
            stringsPosition += idLength;
            String type = buffer.readString(stringsPosition, typeLength);
            
            return new DvIdentifier(issuer, assigner, id, type);
        }
    }
}
