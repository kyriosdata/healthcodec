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

import java.io.UnsupportedEncodingException;
import java.nio.ReadOnlyBufferException;
import main.java.com.github.gabrielsxp.healthcodec.RMObject.*;
import static main.java.com.github.gabrielsxp.healthcodec.PrimitiveTypeSize.*;

/**
 *
 * @author Gabriel
 */
public class SerializationUtils {

    static class DvBooleanSerializer {

        protected int serialize(Buffer buffer, int offset, boolean value) {
            buffer.writeBoolean(offset, value);
            return offset + BOOLEAN.getSize();
        }

        protected DvBoolean deserialize(Buffer buffer, int offset) {
            boolean value = buffer.readBoolean(offset);
            return RMObjectFactory.newDvBoolean(value);
        }

    }

    static class DvIdentifierSerializer {

        protected int serialize(Buffer buffer,
                int offset, String issuer, String assigner, String id,
                String type
        ) throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int issuerLength = issuer.length();
            int assignerLength = assigner.length();
            int idLength = id.length();
            int typeLength = type.length();
            buffer.writeInteger(offset, issuerLength);
            buffer.writeInteger(offset + INT.getSize(), assignerLength);
            buffer.writeInteger(offset + 2 * INT.getSize(), idLength);
            buffer.writeInteger(offset + 3 * INT.getSize(), typeLength);
            buffer.writeString(offset + 4 * INT.getSize(), issuer);
            buffer.writeString(offset + 4 * INT.getSize()
                    + issuerLength, assigner
            );
            buffer.writeString(offset + 4 * INT.getSize()
                    + +issuerLength + assignerLength, id
            );
            buffer.writeString(offset + 4 * INT.getSize()
                    + issuerLength + assignerLength + idLength, type
            );

            return offset
                    + 4 * INT.getSize()
                    + issuerLength
                    + assignerLength
                    + idLength
                    + typeLength;
        }

        protected DvIdentifier deserialize(Buffer buffer, int offset) {
            int issuerLength = buffer.readInteger(offset);
            int assignerLength = buffer.readInteger(offset + INT.getSize());
            int idLength = buffer.readInteger(offset + 2 * INT.getSize());
            int typeLength = buffer.readInteger(offset + 3 * INT.getSize());

            int stringsPosition = offset + 4 * INT.getSize();
            String issuer = buffer.readString(stringsPosition, issuerLength);
            stringsPosition += issuerLength;
            String assigner = buffer.readString(stringsPosition, assignerLength);
            stringsPosition += assignerLength;
            String id = buffer.readString(stringsPosition, idLength);
            stringsPosition += idLength;
            String type = buffer.readString(stringsPosition, typeLength);

            return RMObjectFactory.newDvIdentifier(issuer, assigner, id, type);
        }
    }

    static class InternetIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected InternetID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newInternetID(value);
        }
    }

    static class ISOOIDSerialilzer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected ISO_OID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newISOOID(value);
        }
    }

    static class UUIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected UUID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newUUID(value);
        }
    }

    static class GenericIDSerializer {

        protected int serialize(
                Buffer buffer, int offset, String value, String scheme)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            int schemeLength = scheme.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeInteger(offset + INT.getSize(), schemeLength);
            buffer.writeString(offset + 2 * INT.getSize(), value);
            buffer.writeString(offset + 2 * INT.getSize() + valueLength, scheme);

            return offset + 2 * INT.getSize() + valueLength + schemeLength;
        }

        protected GenericID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            int schemeLength = buffer.readInteger(offset + INT.getSize());
            String value =buffer.readString(offset+2*INT.getSize(), valueLength);
            String scheme = buffer.readString(
                    offset+2*INT.getSize() + valueLength, schemeLength
            );

            return RMObjectFactory.newGenericID(value, scheme);
        }
    }

    static class TemplateIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected TemplateID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newTemplateID(value);
        }
    }

    static class TerminologyIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected TerminologyID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newTerminologyID(value);
        }
    }

    static class CodePhraseSerializer {

        protected int serialize(
                Buffer buffer, int offset,
                String terminologyIDValue, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int terminologyIDValueLength = terminologyIDValue.length();
            int valueLength = value.length();
            buffer.writeInteger(offset, terminologyIDValueLength);
            buffer.writeInteger(offset + INT.getSize(), valueLength);
            buffer.writeString(offset + 2 * INT.getSize(), terminologyIDValue);
            buffer.writeString(
                    offset + 2 * INT.getSize() + terminologyIDValueLength, value
            );

            return offset
                    + 2 * INT.getSize()
                    + valueLength
                    + terminologyIDValueLength;
        }

        protected CodePhrase deserialize(
                Buffer buffer, int offset) {
            int terminologyIDValueLength = buffer.readInteger(offset);
            int valueLength = buffer.readInteger(offset + INT.getSize());
            String terminologyIDValue = buffer.readString(
                    offset + 2 * INT.getSize(), terminologyIDValueLength
            );

            String value = buffer.readString(
                    offset + 2 * INT.getSize() + terminologyIDValueLength, 
                    valueLength
            );

            TerminologyID t = RMObjectFactory.newTerminologyID(
                    terminologyIDValue
            );

            return RMObjectFactory.newCodePhrase(t, value);
        }
    }

    static class DVURISerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected DVURI deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newDVURI(value);
        }
    }

    static class DVEHRURISerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected DVEHRURI deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newDVEHRURI(value);
        }
    }

    static class VersionTreeIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected VersionTreeID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newVersionTreeID(value);
        }
    }

    static class ArchetypeIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected ArchetypeID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newArchetypeID(value);
        }
    }
    
    static class ObjectVersionIDSerializer {
        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected ObjectVersionID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newVersionID(value);
        }
    }
    
    static class HierObjectIDSerialilzer {
        protected int serialize(Buffer buffer, int offset, String value)
                throws IndexOutOfBoundsException,
                ReadOnlyBufferException,
                UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected HierObjectID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newHierObjectID(value);
        }
    }
}
