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

import java.io.UnsupportedEncodingException;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.github.gabrielsxp.healthcodec.RMObject.*;
import static com.github.gabrielsxp.healthcodec.PrimitiveTypeSize.*;
import com.github.gabrielsxp.healthcodec.RMObject.DvIdentifier;

/**
 *
 * @author Gabriel
 */
public class RMObjectSerialization {

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
        ) throws UnsupportedEncodingException {
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

        protected int serializeList(
                Buffer buffer, int offset, List<DvIdentifier> list)
                throws UnsupportedEncodingException {
            int position = offset;
            int size = list.size();
            buffer.writeInteger(position, size);
            position += INT.getSize();
            for (Iterator<DvIdentifier> it = list.iterator(); it.hasNext();) {
                DvIdentifier d = it.next();
                String issuer = d.getIssuer();
                String assigner = d.getAssigner();
                String id = d.getId();
                String type = d.getType();
                int issuerLength = issuer.length();
                int assignerLength = assigner.length();
                int idLength = id.length();
                int typeLength = type.length();
                int dvIdentifierSize
                        = 4 * INT.getSize()
                        + issuerLength
                        + assignerLength
                        + idLength
                        + typeLength;
                buffer.writeInteger(position, dvIdentifierSize);
                position += INT.getSize();
            }
            for (Iterator<DvIdentifier> it = list.iterator(); it.hasNext();) {
                DvIdentifier d = it.next();
                DvIdentifierSerializer s = new DvIdentifierSerializer();
                String issuer = d.getIssuer();
                String assigner = d.getAssigner();
                String id = d.getId();
                String type = d.getType();
                position = s.serialize(buffer,
                        position, issuer, assigner, id, type);
            }
            return position;
        }

        protected List<DvIdentifier> deserializeList(Buffer buffer, int offset) {
            int position = offset;
            DvIdentifierSerializer d = new DvIdentifierSerializer();
            int listSize = buffer.readInteger(position);
            List<DvIdentifier> identifiers = new ArrayList<>();
            position += INT.getSize();
            int dvPosition = position + listSize * INT.getSize();
            for (int i = 0; i < listSize; i++) {

                DvIdentifier a = d.deserialize(buffer, dvPosition);
                identifiers.add(a);

                int pos = buffer.readInteger(position);
                position += INT.getSize();
                dvPosition += pos;
            }
            return identifiers;
        }
    }

    static class InternetIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
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
                throws UnsupportedEncodingException {
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
                throws UnsupportedEncodingException {
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
                throws UnsupportedEncodingException {
            int valueLength = value.length();
            int schemeLength = scheme.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeInteger(offset + INT.getSize(), schemeLength);
            buffer.writeString(offset + 2 * INT.getSize(), value);
            buffer.writeString(
                    offset + 2 * INT.getSize() + valueLength, scheme);

            return offset + 2 * INT.getSize() + valueLength + schemeLength;
        }

        protected GenericID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            int schemeLength = buffer.readInteger(offset + INT.getSize());
            String value = buffer.readString(
                    offset + 2 * INT.getSize(), valueLength);
            String scheme = buffer.readString(
                    offset + 2
                    * INT.getSize()
                    + valueLength, schemeLength);

            return RMObjectFactory.newGenericID(value, scheme);
        }
    }

    static class TemplateIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
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
                throws UnsupportedEncodingException {
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
                throws UnsupportedEncodingException {
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
                throws UnsupportedEncodingException {
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
                throws UnsupportedEncodingException {
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
                throws UnsupportedEncodingException {
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
                throws UnsupportedEncodingException {
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
                throws UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected ObjectVersionID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newObjectVersionID(value);
        }
    }

    static class HierObjectIDSerialilzer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
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

    static class ObjectIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected ObjectID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newObjectID(value);
        }
    }

    static class PartyRefSerializer {

        protected int serialize(
                Buffer buffer,
                int offset,
                String oidValue,
                String value) throws UnsupportedEncodingException {
            int oidValueLength = oidValue.length();
            int valueLength = value.length();
            buffer.writeInteger(offset, oidValueLength);
            buffer.writeInteger(offset + INT.getSize(), valueLength);
            buffer.writeString(offset + 2 * INT.getSize(), oidValue);
            buffer.writeString(offset
                    + 2 * INT.getSize()
                    + oidValueLength, value);

            return offset + 2 * INT.getSize() + valueLength + oidValueLength;
        }

        protected PartyRef deserialize(Buffer buffer, int offset) {
            int oidValueLength = buffer.readInteger(offset);
            int valueLength = buffer.readInteger(offset + INT.getSize());
            String oidValue = buffer.readString(
                    offset + 2 * INT.getSize(),
                    oidValueLength);
            String value = buffer.readString(
                    offset + 2 * INT.getSize() + oidValueLength,
                    valueLength);
            ObjectID id = RMObjectFactory.newObjectID(oidValue);

            return RMObjectFactory.newPartyRef(id, value);
        }
    }

    static class ObjectRefSerializer {

        protected int serialize(
                Buffer buffer,
                int offset,
                String oidValue,
                String namespace,
                String type) throws UnsupportedEncodingException {
            int oidValueLength = oidValue.length();
            int namespaceLength = namespace.length();
            int typeLength = type.length();
            buffer.writeInteger(offset, oidValueLength);
            buffer.writeInteger(offset + INT.getSize(), namespaceLength);
            buffer.writeInteger(offset + 2 * INT.getSize(), typeLength);

            buffer.writeString(offset + 3 * INT.getSize(), oidValue);
            buffer.writeString(
                    offset + 3 * INT.getSize() + oidValueLength, namespace);
            buffer.writeString(
                    offset + 3 * INT.getSize()
                    + oidValueLength
                    + namespaceLength,
                    type);

            return offset
                    + 3 * INT.getSize()
                    + oidValueLength
                    + namespaceLength
                    + typeLength;
        }

        protected ObjectRef deserialize(Buffer buffer, int offset) {
            int oidValueLength = buffer.readInteger(offset);
            int namespaceLength = buffer.readInteger(offset + INT.getSize());
            int typeLength = buffer.readInteger(offset + 2 * INT.getSize());

            String oidValue = buffer.readString(
                    offset + 3 * INT.getSize(), oidValueLength);
            String namespace = buffer.readString(
                    offset + 3 * INT.getSize()
                    + oidValueLength,
                    namespaceLength);
            String type = buffer.readString(
                    offset + 3 * INT.getSize()
                    + oidValueLength
                    + namespaceLength,
                    typeLength);

            ObjectID id = RMObjectFactory.newObjectID(oidValue);
            return RMObjectFactory.newObjectRef(id, namespace, type);
        }
    }

    static class LocatableRefSerializer {

        protected int serialize(
                Buffer buffer,
                int offset,
                String ovidValue,
                String namespace,
                String type,
                String path) throws UnsupportedEncodingException {
            boolean hasPath = path != null;
            int dataPositionStart = 0;

            int ovidValueLength = ovidValue.length();
            int namespaceLength = namespace.length();
            int typeLength = type.length();
            int pathLength = hasPath ? path.length() : 0;

            buffer.writeByte(offset, hasPath ? (byte) 1 : (byte) 0);
            buffer.writeInteger(offset + BYTE.getSize(), ovidValueLength);
            buffer.writeInteger(offset + INT.getSize()
                    + BYTE.getSize(), namespaceLength);
            buffer.writeInteger(offset + 2 * INT.getSize()
                    + BYTE.getSize(), typeLength);
            dataPositionStart = offset + 3 * INT.getSize() + BYTE.getSize();
            if (hasPath) {
                buffer.writeInteger(offset
                        + 3 * INT.getSize() + BYTE.getSize(), pathLength);
                dataPositionStart = offset + 4 * INT.getSize() + BYTE.getSize();
            }
            buffer.writeString(dataPositionStart, ovidValue);
            dataPositionStart += ovidValueLength;
            buffer.writeString(dataPositionStart, namespace);
            dataPositionStart += namespaceLength;
            buffer.writeString(dataPositionStart, type);
            dataPositionStart += typeLength;
            if (hasPath) {
                buffer.writeString(dataPositionStart, path);
                return dataPositionStart + pathLength;
            }
            return dataPositionStart;
        }

        protected LocatableRef deserialize(Buffer buffer, int offset) {
            boolean hasPath = buffer.readByte(offset) == 1;
            int ovidValueLength = buffer.readInteger(offset + BYTE.getSize());
            int namespaceLength = buffer.readInteger(offset
                    + INT.getSize() + BYTE.getSize());
            int typeLength = buffer.readInteger(offset
                    + 2 * INT.getSize() + BYTE.getSize());
            int pathLength = hasPath ? buffer.readInteger(offset
                    + 3 * INT.getSize() + BYTE.getSize()) : 0;
            int dataPositionStart = hasPath
                    ? offset + 4 * INT.getSize() + BYTE.getSize()
                    : offset + 3 * INT.getSize() + BYTE.getSize();
            String ovidValue = buffer.readString(
                    dataPositionStart, ovidValueLength);
            dataPositionStart += ovidValueLength;
            String namespace = buffer.readString(
                    dataPositionStart, namespaceLength);
            dataPositionStart += namespaceLength;
            String type = buffer.readString(
                    dataPositionStart, typeLength);
            dataPositionStart += typeLength;

            String path = "";
            if (hasPath) {
                path = buffer.readString(dataPositionStart, pathLength);
            }
            ObjectVersionID id = RMObjectFactory.newObjectVersionID(ovidValue);
            return RMObjectFactory.newLocatableRef(
                    id,
                    namespace,
                    type,
                    hasPath ? path : null
            );
        }
    }

    static class ProportionKindSerializer {

        protected int serialize(Buffer buffer, int offset, int value) {
            buffer.writeInteger(offset, value);
            return offset + INT.getSize();
        }

        protected ProportionKind deserialize(Buffer buffer, int offset) {
            int value = buffer.readInteger(offset);
            return RMObjectFactory.newPropotionKind(value);
        }
    }

    static class AccessGroupRefSerializer {

        protected int serializer(Buffer buffer, int offset, String oidValue) {
            int oidValueLength = oidValue.length();
            buffer.writeInteger(offset, oidValueLength);
            buffer.writeString(offset + INT.getSize(), oidValue);

            return offset + INT.getSize() + oidValueLength;
        }

        protected AccessGroupRef deserialize(Buffer buffer, int offset) {
            int oidValueLength = buffer.readInteger(offset);
            String oidValue = buffer.readString(
                    offset + INT.getSize(),
                    oidValueLength);
            ObjectID id = new ObjectID(oidValue);

            return RMObjectFactory.newAccessGroupRef(id);
        }
    }

    static class PartyIdentifiedSerializer {

        protected int serializer(
                Buffer buffer,
                int offset,
                String oidValue,
                String value,
                String name,
                List<DvIdentifier> identifiers)
                throws UnsupportedEncodingException {
            int oidValueLength = oidValue.length();
            int valueLength = value.length();
            int nameLength = name.length();
            buffer.writeInteger(offset, oidValueLength);
            buffer.writeInteger(offset + INT.getSize(), valueLength);
            buffer.writeInteger(offset + 2 * INT.getSize(), nameLength);
            int position = offset + 3 * INT.getSize();
            buffer.writeString(position, oidValue);
            position += oidValueLength;
            buffer.writeString(position, value);
            position += valueLength;
            buffer.writeString(position, name);
            position += nameLength;

            DvIdentifierSerializer s = new DvIdentifierSerializer();
            position += s.serializeList(buffer, position, identifiers);
            return position;
        }

        protected PartyIdentified deserializer(Buffer buffer, int offset) {
            int oidValueLength = buffer.readInteger(offset);
            int valueLength = buffer.readInteger(offset + INT.getSize());
            int nameLength = buffer.readInteger(offset + 2 * INT.getSize());

            int position = offset + 3 * INT.getSize();
            String oidValue = buffer.readString(position, oidValueLength);
            position += oidValueLength;
            String value = buffer.readString(position, valueLength);
            position += valueLength;
            String name = buffer.readString(position, nameLength);
            position += nameLength;

            ObjectID id = RMObjectFactory.newObjectID(oidValue);
            PartyRef externalRef = RMObjectFactory.newPartyRef(id, value);

            DvIdentifierSerializer s = new DvIdentifierSerializer();

            List<DvIdentifier> identifiers = s.deserializeList(buffer, position);

            return RMObjectFactory.newPartyIdentified(
                    externalRef, name, identifiers);
        }
    }

    static class ArchetypedSerializer {

        protected int serializer(
                Buffer buffer,
                int offset,
                String archetypeIDValue,
                String templateIDValue,
                String rmVersion) throws UnsupportedEncodingException {
            int archetypeIDValueLength = archetypeIDValue.length();
            int templateIDValueLength = templateIDValue.length();
            int rmVersionLength = rmVersion.length();

            buffer.writeInteger(offset, archetypeIDValueLength);
            buffer.writeInteger(offset + INT.getSize(), templateIDValueLength);
            buffer.writeInteger(offset + 2 * INT.getSize(), rmVersionLength);

            int dataPosition = offset + 3 * INT.getSize();
            buffer.writeString(dataPosition, archetypeIDValue);
            dataPosition += archetypeIDValueLength;
            buffer.writeString(dataPosition, templateIDValue);
            dataPosition += templateIDValueLength;
            buffer.writeString(dataPosition, rmVersion);
            dataPosition += rmVersionLength;

            return dataPosition;
        }

        protected Archetyped deserialize(Buffer buffer, int offset) {
            int archetypeIDValueLength = buffer.readInteger(offset);
            int templateIDValueLength = buffer.readInteger(
                    offset + INT.getSize());
            int rmVersionLength = buffer.readInteger(offset + 2 * INT.getSize());

            int dataPosition = offset + 3 * INT.getSize();

            String archetypeIDValue = buffer.readString(
                    dataPosition, archetypeIDValueLength);

            dataPosition += archetypeIDValueLength;

            String templateIDValue = buffer.readString(
                    dataPosition, templateIDValueLength);

            dataPosition += templateIDValueLength;

            String rmVersion = buffer.readString(dataPosition, rmVersionLength);

            ArchetypeID archetypeId = RMObjectFactory.
                    newArchetypeID(archetypeIDValue);
            TemplateID templateId = RMObjectFactory.
                    newTemplateID(templateIDValue);

            return RMObjectFactory.
                    newArchetyped(archetypeId, templateId, rmVersion);
        }
    }

    public static class DvEncapsulatedSerializer {

        protected int serialize(Buffer buffer,
                int offset,
                String codePhraseCharsetTerminologyIDValue,
                String charsetCodeString,
                String codePhraseLanguageTerminologyIDValue,
                String languageCodeString) {
            int cpCharsetTerminologyIDValueLength
                    = codePhraseCharsetTerminologyIDValue.length();
            int charsetCodeStringLength = charsetCodeString.length();
            int cpLanguageTerminologyIDValueLength
                    = codePhraseLanguageTerminologyIDValue.length();
            int languageCodeStringLength = languageCodeString.length();

            buffer.writeInteger(offset, cpCharsetTerminologyIDValueLength);
            buffer.writeInteger(
                    offset + INT.getSize(), charsetCodeStringLength);
            buffer.writeInteger(
                    offset + 2 * INT.getSize(),
                    cpLanguageTerminologyIDValueLength);
            buffer.writeInteger(
                    offset + 3 * INT.getSize(), languageCodeStringLength);

            int dataPosition = offset + INT.getSize() * 4;
            buffer.writeString(
                    dataPosition, codePhraseCharsetTerminologyIDValue);
            dataPosition += cpCharsetTerminologyIDValueLength;
            buffer.writeString(dataPosition, charsetCodeString);
            dataPosition += charsetCodeStringLength;
            buffer.writeString(
                    dataPosition, codePhraseLanguageTerminologyIDValue);
            dataPosition += cpLanguageTerminologyIDValueLength;
            buffer.writeString(dataPosition, languageCodeString);
            dataPosition += languageCodeStringLength;

            return dataPosition;
        }

        protected DvEncapsulated deserialize(Buffer buffer, int offset) {
            int cpCharsetTerminologyIDValueLength = buffer.readInteger(offset);
            int charsetCodeStringLength
                    = buffer.readInteger(offset + INT.getSize());
            int cpLanguageTerminologyIDValueLength
                    = buffer.readInteger(offset + 2 * INT.getSize());
            int languageCodeStringLength
                    = buffer.readInteger(offset + 3 * INT.getSize());

            int dataPosition = offset + 4 * INT.getSize();

            String codePhraseCharsetTerminologyIDValue
                    = buffer.readString(
                            dataPosition,
                            cpCharsetTerminologyIDValueLength);
            dataPosition += cpCharsetTerminologyIDValueLength;
            String charsetCodeString
                    = buffer.readString(dataPosition, charsetCodeStringLength);
            dataPosition += charsetCodeStringLength;
            String cpLanguageTerminologyIDValue
                    = buffer.readString(
                            dataPosition,
                            cpLanguageTerminologyIDValueLength);
            dataPosition += cpLanguageTerminologyIDValueLength;
            String languageCodeString
                    = buffer.readString(dataPosition, languageCodeStringLength);
            TerminologyID terminologyIDCharset
                    = RMObjectFactory.newTerminologyID(
                            codePhraseCharsetTerminologyIDValue);

            CodePhrase charset
                    = RMObjectFactory.newCodePhrase(
                            terminologyIDCharset,
                            charsetCodeString);

            TerminologyID terminologyIDLanguage
                    = RMObjectFactory.newTerminologyID(
                            cpLanguageTerminologyIDValue);

            CodePhrase language
                    = RMObjectFactory.newCodePhrase(
                            terminologyIDLanguage, languageCodeString);

            return RMObjectFactory.newDvEncapsulated(charset, language);
        }
    }

    public static class UIDBasedIDSerializer {
        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int valueLength = value.length();
            buffer.writeInteger(offset, valueLength);
            buffer.writeString(offset + INT.getSize(), value);

            return offset + INT.getSize() + valueLength;
        }

        protected UIDBasedID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newUIDBasedID(value);
        }
    }
    
    public static class DvParsableSerializer {
        protected int serialize(Buffer buffer, int offset,
                String codePhraseCharsetTerminologyIDValue,
                String charsetCodeString,
                String codePhraseLanguageTerminologyIDValue,
                String languageCodeString,
                String value,
                String formalism) throws UnsupportedEncodingException {
            int cpCharsetTerminologyIDValueLength
                    = codePhraseCharsetTerminologyIDValue.length();
            int charsetCodeStringLength = charsetCodeString.length();
            int cpLanguageTerminologyIDValueLength
                    = codePhraseLanguageTerminologyIDValue.length();
            int languageCodeStringLength = languageCodeString.length();
            int valueLength = value.length();
            int formalismLength = formalism.length();

            buffer.writeInteger(offset, cpCharsetTerminologyIDValueLength);
            buffer.writeInteger(
                    offset + INT.getSize(), charsetCodeStringLength);
            buffer.writeInteger(
                    offset + 2 * INT.getSize(),
                    cpLanguageTerminologyIDValueLength);
            buffer.writeInteger(
                    offset + 3 * INT.getSize(), languageCodeStringLength);
            buffer.writeInteger(offset + 4 * INT.getSize(), valueLength);
            buffer.writeInteger(offset + 5 * INT.getSize(), formalismLength);
            
            int dataPosition = offset + INT.getSize() * 6;
            buffer.writeString(
                    dataPosition, codePhraseCharsetTerminologyIDValue);
            dataPosition += cpCharsetTerminologyIDValueLength;
            buffer.writeString(dataPosition, charsetCodeString);
            dataPosition += charsetCodeStringLength;
            buffer.writeString(
                    dataPosition, codePhraseLanguageTerminologyIDValue);
            dataPosition += cpLanguageTerminologyIDValueLength;
            buffer.writeString(dataPosition, languageCodeString);
            dataPosition += languageCodeStringLength;
            
            buffer.writeString(dataPosition, value);
            dataPosition += valueLength;
            buffer.writeString(dataPosition, formalism);
            dataPosition += formalismLength;
            
            return dataPosition;
        }
        
        protected DvParsable deserialize(Buffer buffer, int offset){
            int cpCharsetTerminologyIDValueLength = buffer.readInteger(offset);
            int charsetCodeStringLength
                    = buffer.readInteger(offset + INT.getSize());
            int cpLanguageTerminologyIDValueLength
                    = buffer.readInteger(offset + 2 * INT.getSize());
            int languageCodeStringLength
                    = buffer.readInteger(offset + 3 * INT.getSize());
            int valueLength = buffer.readInteger(offset + 4 * INT.getSize());
            int formalismLength = 
                    buffer.readInteger(offset + 5 * INT.getSize());

            int dataPosition = offset + 6 * INT.getSize();

            String codePhraseCharsetTerminologyIDValue
                    = buffer.readString(
                            dataPosition,
                            cpCharsetTerminologyIDValueLength);
            dataPosition += cpCharsetTerminologyIDValueLength;
            
            String charsetCodeString
                    = buffer.readString(dataPosition, charsetCodeStringLength);
            dataPosition += charsetCodeStringLength;
            
            String cpLanguageTerminologyIDValue
                    = buffer.readString(
                            dataPosition,
                            cpLanguageTerminologyIDValueLength);
            dataPosition += cpLanguageTerminologyIDValueLength;
            
            String languageCodeString
                    = buffer.readString(dataPosition, languageCodeStringLength);
            dataPosition += languageCodeStringLength;
            
            String value = buffer.readString(dataPosition, valueLength);
            dataPosition += valueLength;
            
            String formalism = buffer.readString(dataPosition, formalismLength);
            
            TerminologyID terminologyIDCharset
                    = RMObjectFactory.newTerminologyID(
                            codePhraseCharsetTerminologyIDValue);

            CodePhrase charset
                    = RMObjectFactory.newCodePhrase(
                            terminologyIDCharset,
                            charsetCodeString);

            TerminologyID terminologyIDLanguage
                    = RMObjectFactory.newTerminologyID(
                            cpLanguageTerminologyIDValue);

            CodePhrase language
                    = RMObjectFactory.newCodePhrase(
                            terminologyIDLanguage, languageCodeString);
            
            return RMObjectFactory.newDvParsable(
                    charset, 
                    language, 
                    value, formalism);
        }
    }

}
