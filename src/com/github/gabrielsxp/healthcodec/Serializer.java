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
import com.github.gabrielsxp.healthcodec.RMObjects.*;
import java.io.UnsupportedEncodingException;
import java.nio.ReadOnlyBufferException;
/**
 *
 * @author Gabriel
 */
public interface Serializer {
    
    public RMObjectSerializationClient serializeDvBoolean(boolean value);
    
    public RMObjectSerializationClient serializeDvIdentifier(
            String issuer,String assigner,String id,String type
    ) throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeInternetID(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeISOOID(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeUUID(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeTerminologyID(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeGenericID(String value, String scheme)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeTemplateID(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeCodePhrase(
            String terminologyIDValue, String codeString)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeDVURI(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeDVEHRURI(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeVersionTreeID(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeArchetypeID(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeObjectVersionID(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
    public RMObjectSerializationClient serializeHierObjectID(String value)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException;
    
}
