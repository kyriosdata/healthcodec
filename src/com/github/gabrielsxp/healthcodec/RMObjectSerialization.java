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
import com.github.gabrielsxp.healthcodec.RMObject.TermMapping;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        protected int serialize(Buffer buffer, int offset, 
                DvIdentifier dvi) throws UnsupportedEncodingException{
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            int position = offset;
            
            position = dis.serialize(
                    buffer, 
                    position , 
                    dvi.getIssuer(), 
                    dvi.getAssigner(),
                    dvi.getId(), 
                    dvi.getType());
            
            return position;
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
        
        protected int listSerialize(
                Buffer buffer, int offset, List<DvIdentifier> items)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = items.size();
            int position = offset + (listSize * INT.getSize()) + INT.getSize();
            
            meta = writeHeader(buffer, meta, listSize);
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            
            for (DvIdentifier d : items) {
                meta = writeHeader(buffer, meta, position);
                position = dis.serialize(buffer, position, d);
            }
            
            
            return position;
        }

        protected List<DvIdentifier> deserializeList(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += INT.getSize();
            
            List<DvIdentifier> list = new ArrayList<>();
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            
            for(int i = 0; i < listSize; i++){
                int dvIdentifierPosition = buffer.readInteger(position);
                position += INT.getSize();
                DvIdentifier t = dis.deserialize(buffer, dvIdentifierPosition);
                list.add(t);
            }
            
            
            return list;
        }
    }

    static class InternetIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            return valueStringSerialization(buffer, offset, value);
        }

        protected InternetID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(
                    offset + INT.getSize(), valueLength);

            return RMObjectFactory.newInternetID(value);
        }
    }

    static class ISOOIDSerialilzer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            return valueStringSerialization(buffer, offset, value);
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
            return valueStringSerialization(buffer, offset, value);
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
            return valueStringSerialization(buffer, offset, value);
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
            return valueStringSerialization(buffer, offset, value);
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
                TerminologyID terminologyId, String value)
                throws UnsupportedEncodingException {
            int terminologyIDValueLength = terminologyId.getValue().length();
            int valueLength = value.length();
            buffer.writeInteger(offset, terminologyIDValueLength);
            buffer.writeInteger(offset + INT.getSize(), valueLength);
            buffer.writeString(
                    offset + 2 * INT.getSize(), terminologyId.getValue());
            buffer.writeString(
                    offset + 2 * INT.getSize() + terminologyIDValueLength,
                    value);

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
            return valueStringSerialization(buffer, offset, value);
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
            return valueStringSerialization(buffer, offset, value);
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DVEHRURI dvehruri) throws UnsupportedEncodingException{
            DVEHRURISerializer des = new DVEHRURISerializer();
            int position = offset;
            
            position = des.serialize(buffer, position, dvehruri.getValue());
            
            return position;
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
            return valueStringSerialization(buffer, offset, value);
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
            return valueStringSerialization(buffer, offset, value);
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
            return valueStringSerialization(buffer, offset, value);
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
            return valueStringSerialization(buffer, offset, value);
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
            return valueStringSerialization(buffer, offset, value);
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
                ObjectID id,
                String value) throws UnsupportedEncodingException {
            int oidValueLength = id.getValue().length();
            int valueLength = value.length();
            buffer.writeInteger(offset, oidValueLength);
            buffer.writeInteger(offset + INT.getSize(), valueLength);
            buffer.writeString(offset + 2 * INT.getSize(), id.getValue());
            buffer.writeString(offset
                    + 2 * INT.getSize()
                    + oidValueLength, value);

            return offset + 2 * INT.getSize() + valueLength + oidValueLength;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                PartyRef pr) throws UnsupportedEncodingException{
            int position = offset;
            PartyRefSerializer prs = new PartyRefSerializer();
            
            position = 
                    prs.serialize(buffer, position, pr.getId(), pr.getValue());
            
            return position;
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
                ObjectID id,
                String namespace,
                String type) throws UnsupportedEncodingException {
            int oidValueLength = id.getValue().length();
            int namespaceLength = namespace.length();
            int typeLength = type.length();
            buffer.writeInteger(offset, oidValueLength);
            buffer.writeInteger(offset + INT.getSize(), namespaceLength);
            buffer.writeInteger(offset + 2 * INT.getSize(), typeLength);

            buffer.writeString(offset + 3 * INT.getSize(), id.getValue());
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
                ObjectVersionID id,
                String namespace,
                String type,
                String path) throws UnsupportedEncodingException {
            boolean hasPath = path != null;
            int dataPositionStart = 0;

            int ovidValueLength = id.getValue().length();
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
            buffer.writeString(dataPositionStart, id.getValue());
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

        protected int serializer(Buffer buffer, int offset, ObjectID id) {
            int oidValueLength = id.getValue().length();
            buffer.writeInteger(offset, oidValueLength);
            buffer.writeString(offset + INT.getSize(), id.getValue());

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

        protected int serialize(Buffer buffer, 
                int offset, 
                PartyRef externalRef, 
                String name, 
                List<DvIdentifier> identifiers)
                    throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + (3 * INT.getSize());
            PartyRefSerializer prs = new PartyRefSerializer();
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = prs.serialize(buffer, position, externalRef);
            
            meta = writeHeader(buffer, meta, position);
            position = valueStringSerialization(buffer, position, name);
            
            meta = writeHeader(buffer, meta, position);
            position = dis.listSerialize(buffer, position, identifiers);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, PartyIdentified pi) 
                throws UnsupportedEncodingException{
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            int position = offset;
            
            position = 
                    pis.serialize(buffer, position, 
                            pi.getExternalRef(), pi.getName(), 
                            pi.getIdentifiers());
            
            return position;
        }

        protected PartyIdentified deserialize(Buffer buffer, int offset) {
            int position = offset;
            PartyRefSerializer prs = new PartyRefSerializer();
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            
            int externalRefPosition = buffer.readInteger(position);
            position += INT.getSize();
            PartyRef externalRef = prs.deserialize(buffer, externalRefPosition);
            
            int namePosition = buffer.readInteger(position);
            position += INT.getSize();
            String name = valueStringDeserialization(buffer, namePosition);
            
            int identifiersPosition = buffer.readInteger(position);
            position += INT.getSize();
            List<DvIdentifier> identifiers = dis.deserializeList(
                    buffer, identifiersPosition);
            
            return RMObjectFactory.newPartyIdentified(
                    externalRef, name, identifiers);
        }
    }

    static class ArchetypedSerializer {

        protected int serialize(
                Buffer buffer,
                int offset,
                ArchetypeID archetypeId,
                TemplateID templateId,
                String rmVersion) throws UnsupportedEncodingException {
            int archetypeIDValueLength = archetypeId.getValue().length();
            int templateIDValueLength = templateId.getValue().length();
            int rmVersionLength = rmVersion.length();

            buffer.writeInteger(offset, archetypeIDValueLength);
            buffer.writeInteger(offset + INT.getSize(), templateIDValueLength);
            buffer.writeInteger(offset + 2 * INT.getSize(), rmVersionLength);

            int dataPosition = offset + 3 * INT.getSize();
            buffer.writeString(dataPosition, archetypeId.getValue());
            dataPosition += archetypeIDValueLength;
            buffer.writeString(dataPosition, templateId.getValue());
            dataPosition += templateIDValueLength;
            buffer.writeString(dataPosition, rmVersion);
            dataPosition += rmVersionLength;

            return dataPosition;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Archetyped a) throws UnsupportedEncodingException {
            int position = offset;
            ArchetypedSerializer as = new ArchetypedSerializer();
            
            position = as.serialize(buffer, offset, 
                    a.getArchetypeId(), a.getTemplateId(), a.getRmVersion());
            
            return position;
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
                CodePhrase charset, 
                CodePhrase language) throws UnsupportedEncodingException {
            int cpCharsetTerminologyIDValueLength
                    = charset.getTerminologyID().getValue().length();
            int charsetCodeStringLength
                    = charset.getValue().length();
            int cpLanguageTerminologyIDValueLength
                    = language.getTerminologyID().getValue().length();
            int languageCodeStringLength
                    = language.getValue().length();

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
                    dataPosition, charset.getTerminologyID().getValue());
            dataPosition += cpCharsetTerminologyIDValueLength;
            buffer.writeString(dataPosition, charset.getValue());
            dataPosition += charsetCodeStringLength;
            buffer.writeString(
                    dataPosition, language.getTerminologyID().getValue());
            dataPosition += cpLanguageTerminologyIDValueLength;
            buffer.writeString(dataPosition, language.getValue());
            dataPosition += languageCodeStringLength;

            return dataPosition;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DvEncapsulated de) throws UnsupportedEncodingException {
            int position = offset;
            DvEncapsulatedSerializer des = new DvEncapsulatedSerializer();
            position = des.serialize(
                    buffer, position, de.getCharset(), de.getLanguage());
            
            return position;
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
            return valueStringSerialization(buffer, offset, value);
        }

        protected UIDBasedID deserialize(Buffer buffer, int offset) {
            int valueLength = buffer.readInteger(offset);
            String value = buffer.readString(offset + INT.getSize(), valueLength);

            return RMObjectFactory.newUIDBasedID(value);
        }
    }

    public static class DvParsableSerializer {

        protected int serialize(Buffer buffer, int offset,
                CodePhrase charset,
                CodePhrase language,
                String value,
                String formalism) throws UnsupportedEncodingException {
            int cpCharsetTerminologyIDValueLength
                    = charset.getTerminologyID().getValue().length();
            int charsetCodeStringLength = charset.getValue().length();
            int cpLanguageTerminologyIDValueLength
                    = language.getTerminologyID().getValue().length();
            int languageCodeStringLength = language.getValue().length();
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
                    dataPosition, charset.getTerminologyID().getValue());
            dataPosition += cpCharsetTerminologyIDValueLength;
            buffer.writeString(dataPosition, charset.getValue());
            dataPosition += charsetCodeStringLength;
            buffer.writeString(
                    dataPosition, language.getTerminologyID().getValue());
            dataPosition += cpLanguageTerminologyIDValueLength;
            buffer.writeString(dataPosition, language.getValue());
            dataPosition += languageCodeStringLength;

            buffer.writeString(dataPosition, value);
            dataPosition += valueLength;
            buffer.writeString(dataPosition, formalism);
            dataPosition += formalismLength;

            return dataPosition;
        }

        protected DvParsable deserialize(Buffer buffer, int offset) {
            int cpCharsetTerminologyIDValueLength = buffer.readInteger(offset);
            int charsetCodeStringLength
                    = buffer.readInteger(offset + INT.getSize());
            int cpLanguageTerminologyIDValueLength
                    = buffer.readInteger(offset + 2 * INT.getSize());
            int languageCodeStringLength
                    = buffer.readInteger(offset + 3 * INT.getSize());
            int valueLength = buffer.readInteger(offset + 4 * INT.getSize());
            int formalismLength
                    = buffer.readInteger(offset + 5 * INT.getSize());

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

    public static class DvTimeSpecificationSerializer {

        protected int serialize(
                Buffer buffer,
                int offset,
                DvParsable value) throws UnsupportedEncodingException {
            int endPosition = offset;
            DvParsableSerializer s = new DvParsableSerializer();
            endPosition += s.serialize(buffer,
                    offset,
                    value.getCharset(),
                    value.getLanguage(),
                    value.getValue(),
                    value.getFormalism());

            return endPosition;
        }

        protected DvTimeSpecification deserialize(Buffer buffer, int offset) {
            DvParsableSerializer d = new DvParsableSerializer();
            DvParsable value = d.deserialize(buffer, offset);

            return RMObjectFactory.newDvTimeSpecification(value);
        }
    }

    public static class DvMultimediaSerializer {

        protected int serialize(
                Buffer buffer,
                int offset,
                DvEncapsulated dvMultimediaDvEncapsulated,
                String alternateText,
                CodePhrase mediaType,
                CodePhrase compressionAlgorithm,
                byte[] integrityCheck,
                CodePhrase integrityCheckAlgorithm,
                DvMultimedia thumbnail,
                DVURI uri,
                byte[] data) throws
                UnsupportedEncodingException, IllegalArgumentException {

            boolean hasCompressionAlgorithm = compressionAlgorithm != null;
            boolean hasIntegrityCheck = integrityCheck != null;
            boolean hasIntegrityCheckAlgorithm
                    = integrityCheckAlgorithm != null;
            boolean hasThumbnail = thumbnail != null;

            DvEncapsulatedSerializer dve = new DvEncapsulatedSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();
            DVURISerializer dvu = new DVURISerializer();
            DvMultimediaSerializer dvm = new DvMultimediaSerializer();

            int position = offset + 56;
            int meta = offset;

            meta = writeHeader(buffer, meta, position);
            position = dve.serialize(
                    buffer,
                    position,
                    dvMultimediaDvEncapsulated.getCharset(),
                    dvMultimediaDvEncapsulated.getLanguage());
            meta = writeHeader(buffer, meta, position);
            position = valueStringSerialization(
                    buffer, position, alternateText);

            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(
                    buffer,
                    position,
                    mediaType.getTerminologyID(),
                    mediaType.getValue());

            if (hasCompressionAlgorithm) {
                meta = writeHeader(
                        buffer, meta, hasCompressionAlgorithm, position);
                position = cps.serialize(
                        buffer,
                        position,
                        compressionAlgorithm.getTerminologyID(),
                        compressionAlgorithm.getValue());
            } else {
                meta = writeHeader(buffer, meta, false);
            }

            if (hasIntegrityCheck && hasIntegrityCheckAlgorithm) {
                meta = writeHeader(buffer, meta, hasIntegrityCheck, position);
                meta = writeHeader(buffer, meta, integrityCheck.length);
                buffer.writeByteArray(position, integrityCheck);
                position += integrityCheck.length;

                meta = writeHeader(
                        buffer, meta, hasIntegrityCheckAlgorithm, position);
                position = cps.serialize(
                        buffer,
                        position,
                        integrityCheckAlgorithm.getTerminologyID(),
                        integrityCheckAlgorithm.getValue());
            } else {
                throw new IllegalArgumentException("Integrity Check fails!");
            }

            if (hasThumbnail) {
                meta = writeHeader(buffer, meta, hasThumbnail, position);
                position = dvm.serialize(
                        buffer,
                        position,
                        thumbnail.getDvMultimediaDvEncapsulated(),
                        thumbnail.getAlternateText(),
                        thumbnail.getMediaType(),
                        thumbnail.getCompressionAlgorithm(),
                        thumbnail.getIntegrityCheck(),
                        thumbnail.getIntegrityCheckAlgorithm(),
                        thumbnail.getThumbnail(),
                        thumbnail.getUri(),
                        thumbnail.getData());
            } else {
                meta = writeHeader(buffer, meta, hasThumbnail);
            }

            meta = writeHeader(buffer, meta, position);
            position = dvu.serialize(
                    buffer,
                    position,
                    uri.getValue());

            meta = writeHeader(buffer, meta, position);
            meta = writeHeader(buffer, meta, data.length);
            buffer.writeByteArray(position, data);
            position += data.length;

            return position;
        }

        protected DvMultimedia deserialize(Buffer buffer, int offset) {

            DvEncapsulatedSerializer dve = new DvEncapsulatedSerializer();;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            DVURISerializer dvu = new DVURISerializer();
            DvMultimediaSerializer dvm = new DvMultimediaSerializer();

            int meta = offset;
            int dvEncapsulatedPosition = buffer.readInteger(meta);
            DvEncapsulated dvMultimediaDvEncapsulated
                    = dve.deserialize(buffer, dvEncapsulatedPosition);
            meta += INT.getSize();

            int alternateTextPosition = buffer.readInteger(meta);
            String alternateText
                    = valueStringDeserialization(buffer, alternateTextPosition);
            meta += INT.getSize();

            int mediaTypePosition = buffer.readInteger(meta);
            CodePhrase mediaType = cps.deserialize(buffer, mediaTypePosition);
            meta += INT.getSize();

            boolean hasCompressionAlgorithm = buffer.readBoolean(meta);
            int compressionAlgorithmPosition = 0;
            CodePhrase compressionAlgorithm = null;
            if (hasCompressionAlgorithm) {
                meta += BOOLEAN.getSize();
                compressionAlgorithmPosition = buffer.readInteger(meta);
                compressionAlgorithm
                        = cps.deserialize(buffer, compressionAlgorithmPosition);
                meta += INT.getSize();
            } else {
                meta += BOOLEAN.getSize();
            }
            boolean hasIntegrityCheck = buffer.readBoolean(meta);
            int integrityCheckPosition = 0;
            int integrityCheckLength = 0;
            byte[] integrityCheck = null;
            if (hasIntegrityCheck) {
                meta += BOOLEAN.getSize();
                integrityCheckPosition = buffer.readInteger(meta);
                meta += INT.getSize();
                integrityCheckLength = buffer.readInteger(meta);
                meta += INT.getSize();

                integrityCheck = buffer.readByteArray(integrityCheckPosition, integrityCheckLength);
            } else {
                meta += BOOLEAN.getSize();
            }

            boolean hasIntegrityCheckAlgorithm = buffer.readBoolean(meta);
            int integrityCheckAlgorithmPosition = 0;
            CodePhrase integrityCheckAlgorithm = null;
            if (hasIntegrityCheckAlgorithm) {
                meta += BOOLEAN.getSize();
                integrityCheckAlgorithmPosition = buffer.readInteger(meta);
                meta += INT.getSize();
                integrityCheckAlgorithm = cps.deserialize(buffer, integrityCheckAlgorithmPosition);
            } else {
                meta += BOOLEAN.getSize();
            }

            boolean hasThumbnail = buffer.readBoolean(meta);
            int thumbnailPosition = 0;
            DvMultimedia thumbnail = null;
            if (hasThumbnail) {
                meta += BOOLEAN.getSize();
                thumbnailPosition = buffer.readInteger(meta);
                meta += INT.getSize();
                thumbnail = dvm.deserialize(buffer, thumbnailPosition);
            } else {
                meta += BOOLEAN.getSize();
            }

            int uriPosition = buffer.readInteger(meta);
            DVURI uri = dvu.deserialize(buffer, uriPosition);
            meta += INT.getSize();

            int dataPosition = buffer.readInteger(meta);
            meta += INT.getSize();
            int dataLength = buffer.readInteger(meta);
            byte[] data = buffer.readByteArray(dataPosition, dataLength);

            return RMObjectFactory.newDvMultimedia(dvMultimediaDvEncapsulated,
                    alternateText, mediaType, compressionAlgorithm,
                    integrityCheck, integrityCheckAlgorithm, thumbnail,
                    uri, data);
        }
    }

    public static class DvTextSerializer {

        protected int serialize(Buffer buffer,
                int offset,
                String value,
                List<TermMapping> mappings,
                String formatting,
                DVURI hyperlink,
                CodePhrase language,
                CodePhrase charset) throws UnsupportedEncodingException {
            boolean hasMappings = mappings != null;
            boolean hasFormatting = formatting != null;
            boolean hasHyperlink = hyperlink != null;

            CodePhraseSerializer cps = new CodePhraseSerializer();
            TermMappingSerializer tms = new TermMappingSerializer();
            DVURISerializer dvu = new DVURISerializer();

            int meta = offset;
            int position = offset + 6 * INT.getSize() + 3 * BOOLEAN.getSize();

            meta = writeHeader(buffer, meta, position);
            position = valueStringSerialization(buffer, position, value);
            if (hasMappings) {
                
                meta = writeHeader(buffer, meta, hasMappings, position);
                position = tms.listSerialize(buffer, position, mappings);
            } else {
                meta = writeHeader(buffer, meta, hasMappings);
            }

            if (hasFormatting) {
                meta = writeHeader(buffer, meta, hasFormatting, position);
                position
                        = valueStringSerialization(buffer, position, formatting);
            } else {
                meta = writeHeader(buffer, meta, hasFormatting);
            }

            if (hasHyperlink) {
                meta = writeHeader(buffer, meta, hasHyperlink, position);
                position
                        = dvu.serialize(buffer, position,
                                hyperlink.getValue());
            } else {
                meta = writeHeader(buffer, meta, hasHyperlink);
            }

            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position,
                    language.getTerminologyID(), language.getValue());
            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position,
                    charset.getTerminologyID(), charset.getValue());

            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, DvText dvText) 
                throws UnsupportedEncodingException{
            DvTextSerializer dts = new DvTextSerializer();
            int position = offset;
            position = dts.serialize(
                    buffer, 
                    position, 
                    dvText.getValue(), 
                    dvText.getMappings(), 
                    dvText.getFormatting(), 
                    dvText.getHyperlink(), 
                    dvText.getLanguage(), 
                    dvText.getCharset());
            
            return position;
        }

        protected DvText deserialize(Buffer buffer, int offset) {
            int position = offset;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            TermMappingSerializer tms = new TermMappingSerializer();
            DVURISerializer dvu = new DVURISerializer();

            int valuePosition = buffer.readInteger(position);
            String value = valueStringDeserialization(buffer, valuePosition);
            position += INT.getSize();

            boolean hasMappings = buffer.readBoolean(position);
            int mappingsPosition = 0;
            List<TermMapping> mappings = null;
            position += BOOLEAN.getSize();
            if (hasMappings) {
                mappingsPosition = buffer.readInteger(position);
                mappings = tms.deserializeList(buffer, mappingsPosition);
                position += INT.getSize();
            }

            boolean hasFormatting = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            int formattingPosition = 0;
            String formatting = null;
            if (hasFormatting) {
                formattingPosition = buffer.readInteger(position);
                formatting
                        = valueStringDeserialization(buffer, formattingPosition);
                position += INT.getSize();
            }
            boolean hasHyperlink = buffer.readBoolean(position);
            int hyperlinkPosition = 0;
            DVURI hyperlink = null;
            position += BOOLEAN.getSize();
            if (hasHyperlink) {
                hyperlinkPosition = buffer.readInteger(position);
                hyperlink = dvu.deserialize(buffer, hyperlinkPosition);
                position += INT.getSize();
            }

            int languagePosition = buffer.readInteger(position);
            position += INT.getSize();
            CodePhrase language = cps.deserialize(buffer, languagePosition);

            int charsetPosition = buffer.readInteger(position);
            CodePhrase charset = cps.deserialize(buffer, charsetPosition);

            return RMObjectFactory.newDvText(
                    value, mappings, formatting, hyperlink, language, charset);
        }
        
        protected int listSerialize(
                Buffer buffer, int offset, List<DvText> items)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = items.size();
            int position = offset + (listSize * INT.getSize()) + INT.getSize();
            
            meta = writeHeader(buffer, meta, listSize);
            DvTextSerializer tms = new DvTextSerializer();
            
            for (DvText d : items) {
                meta = writeHeader(buffer, meta, position);
                position = tms.serialize(buffer, position, d);
            }
            
            
            return position;
        }

        protected List<DvText> deserializeList(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += INT.getSize();
            
            List<DvText> list = new ArrayList<>();
            DvTextSerializer dts = new DvTextSerializer();
            
            for(int i = 0; i < listSize; i++){
                int dvTextPosition = buffer.readInteger(position);
                position += INT.getSize();
                DvText t = dts.deserialize(buffer, dvTextPosition);
                list.add(t);
            }
            
            
            return list;
        }
    }

    public static class DvCodedTextSerializer {

        protected int serialize(Buffer buffer, int offset,
                DvText dvText, CodePhrase definingCode)
                throws UnsupportedEncodingException {
            int position = offset + 2 * INT.getSize();
            int meta = offset;
            DvTextSerializer dvt = new DvTextSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();

            meta = writeHeader(buffer, meta, position);
            position = dvt.serialize(
                    buffer,
                    position,
                    dvText.getValue(),
                    dvText.getMappings(),
                    dvText.getFormatting(),
                    dvText.getHyperlink(),
                    dvText.getLanguage(),
                    dvText.getCharset());

            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position,
                    definingCode.getTerminologyID(), definingCode.getValue());

            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DvCodedText dct) throws UnsupportedEncodingException{
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            int position = offset;
            
            position = dcs.serialize(buffer, offset, 
                    dct.getDvText(), dct.getDefiningCode());
            
            return position;
        }
                

        protected DvCodedText deserialize(Buffer buffer, int offset) {
            int position = offset;
            int dvTextPosition = buffer.readInteger(position);
            position += INT.getSize();
            int definingCodePosition = buffer.readInteger(position);
            DvTextSerializer dvt = new DvTextSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();

            DvText dvText = dvt.deserialize(buffer, dvTextPosition);
            CodePhrase definingCode
                    = cps.deserialize(buffer, definingCodePosition);

            return RMObjectFactory.newDvCodedText(dvText, definingCode);
        }
    }

    public static class MatchSerializer {

        protected int serialize(Buffer buffer, int offset, Match match)
                throws UnsupportedEncodingException {
            int position = offset;
            String value = match.getValue();
            position = valueStringSerialization(buffer, position, value);

            return position;
        }

        protected Match deserialize(Buffer buffer, int offset)
                throws IllegalAccessException {
            int position = offset;
            String value = valueStringDeserialization(buffer, position);

            Match match = Match.fromValue(value);

            return match;
        }
    }

    public static class TermMappingSerializer {

        protected int serialize(
                Buffer buffer,
                int offset,
                CodePhrase target,
                Match match,
                DvCodedText purpose) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 3 * INT.getSize() + BOOLEAN.getSize();
            boolean hasPurpose = purpose != null;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            MatchSerializer ms = new MatchSerializer();
            DvCodedTextSerializer dct = new DvCodedTextSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position,
                    target.getTerminologyID(), target.getValue());
            meta = writeHeader(buffer, meta, position);
            position = ms.serialize(buffer, position, match);
            if (hasPurpose) {
                meta = writeHeader(buffer, meta, hasPurpose, position);
                position = dct.serialize(buffer, position,
                        purpose.getDvText(), purpose.getDefiningCode());
            } else {
                meta = writeHeader(buffer, meta, hasPurpose);
            }
            return position;
        }

        protected TermMapping deserialize(Buffer buffer, int offset) {
            int position = offset;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            MatchSerializer ms = new MatchSerializer();
            DvCodedTextSerializer dct = new DvCodedTextSerializer();

            int targetPosition = buffer.readInteger(position);
            CodePhrase target = cps.deserialize(buffer, targetPosition);
            position += INT.getSize();

            int matchPosition = buffer.readInteger(position);
            Match match = null;
            try {
                match = ms.deserialize(buffer, matchPosition);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(RMObjectSerialization.class.getName()).log(Level.SEVERE, null, ex);
            }
            position += INT.getSize();

            boolean hasPurpose = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            int purposePosition = 0;
            DvCodedText purpose = null;
            if (hasPurpose) {
                purposePosition = buffer.readInteger(position);
                purpose = dct.deserialize(buffer, purposePosition);
                position += INT.getSize();
            }

            return RMObjectFactory.newTermMapping(target, match, purpose);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<TermMapping> mappings)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = mappings.size();
            int position = offset + (listSize * INT.getSize()) + INT.getSize();
            
            meta = writeHeader(buffer, meta, listSize);
            TermMappingSerializer tms = new TermMappingSerializer();
            
            for (TermMapping t : mappings) {
                meta = writeHeader(buffer, meta, position);
                position = tms.serialize(buffer, position,
                        t.getTarget(), t.getMatch(), t.getPurpose());
            }
            
            
            return position;
        }

        protected List<TermMapping> deserializeList(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += INT.getSize();
            
            List<TermMapping> list = new ArrayList<>();
            TermMappingSerializer tms = new TermMappingSerializer();
            
            for(int i = 0; i < listSize; i++){
                int termMappingPosition = buffer.readInteger(position);
                position += INT.getSize();
                TermMapping t = tms.deserialize(buffer, termMappingPosition);
                list.add(t);
            }
            
            
            return list;
        }
    }
    
    public static class LinkSerializer {
        protected int serialize(Buffer buffer, int offset,
                DvText meaning, 
                DvText type, 
                DVEHRURI target) throws UnsupportedEncodingException{
            
            int position = offset + 3 * INT.getSize();
            int meta = offset;
            
            DvTextSerializer dts = new DvTextSerializer();
            DVEHRURISerializer des = new DVEHRURISerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = dts.serialize(buffer, position, meaning);
            meta = writeHeader(buffer, meta, position);
            position = dts.serialize(buffer, position, type);
            meta = writeHeader(buffer, meta, position);
            position = des.serialize(buffer, position, target);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Link link) throws UnsupportedEncodingException{
            LinkSerializer ls = new LinkSerializer();
            int position = offset;
            
            position = ls.serialize(
                    buffer, 
                    position, 
                    link.getMeaning(), 
                    link.getType(), 
                    link.getTarget());
            
            return position;
        }
        
        protected Link deserialize(Buffer buffer, int offset){
            int position = offset;
            DvTextSerializer dts = new DvTextSerializer();
            DVEHRURISerializer des = new DVEHRURISerializer();
            
            int meaningPosition = buffer.readInteger(position);
            position += INT.getSize();
            DvText meaning = dts.deserialize(buffer, meaningPosition);
            
            int typePosition = buffer.readInteger(position);
            position += INT.getSize();
            DvText type = dts.deserialize(buffer, typePosition);
            
            int targetPosition = buffer.readInteger(position);
            DVEHRURI target = des.deserialize(buffer, targetPosition);
            
            return RMObjectFactory.newLink(meaning, type, target);
        }
        
        protected int setSerializer(Buffer buffer, int offset, 
                Set<Link> links) throws UnsupportedEncodingException{
            int setSize = links.size();
            int position = offset + (setSize * INT.getSize()) + INT.getSize();
            int meta = offset;
            LinkSerializer ls = new LinkSerializer();
            
            meta = writeHeader(buffer, meta, setSize);
            Iterator<Link> it = links.iterator();
            
            while(it.hasNext()){
                Link link = it.next();
                int linkPosition = position;
                meta = writeHeader(buffer, meta, linkPosition);
                position = ls.serialize(buffer, position, link);
            }
            
            return position;
        }
        
        protected Set<Link> setDeserializer(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += INT.getSize();
            
            LinkSerializer ls = new LinkSerializer();
            Set<Link> links = new HashSet<>();
            
            for(int i = 0; i < listSize; i++){
                int linkPosition = buffer.readInteger(position);
                position += INT.getSize();
                
                Link link = ls.deserialize(buffer, linkPosition);
                links.add(link);
            }
            
            return links;
        }
    }
    
    public static class DvStateSerializer {
        protected int serialize(
                Buffer buffer, 
                int offset,
                DvCodedText value, 
                String terminal) throws UnsupportedEncodingException{
            int meta = offset;
            int position = offset + 2 * INT.getSize() + BOOLEAN.getSize();
            boolean hasTerminal = terminal != null;
            
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            meta = writeHeader(buffer, meta, position);
            position = dcs.serialize(buffer, position, value);
            
            if(hasTerminal){
                meta = writeHeader(buffer, meta, hasTerminal, position);
                position = valueStringSerialization(buffer, position, terminal);
            } else {
                meta = writeHeader(buffer, meta, hasTerminal);
            }
            
            return position;
        }
        
        protected int serialize(Buffer buffer, 
                int offset, 
                DvState dvState) throws UnsupportedEncodingException{
            int position = offset;
            DvStateSerializer dss = new DvStateSerializer();
            position = dss.serialize(
                    buffer, position, dvState.getValue(), dvState.getTerminal());
            
            return position;
        }
        
        protected DvState deserialize(Buffer buffer, int offset){
            int position = offset;
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            
            int valuePosition = buffer.readInteger(position);
            position += INT.getSize();
            
            DvCodedText value = dcs.deserialize(buffer, valuePosition);
            
            boolean hasTerminal = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            String terminal = null;
            if(hasTerminal){
                int terminalPosition = buffer.readInteger(position);
                terminal = valueStringDeserialization(buffer, terminalPosition);
            }
            
            return RMObjectFactory.newDvState(value, terminal);
        }
    }
    
    public static class DvParagraphSerializer {
        protected int serialize(Buffer buffer, int offset, 
                List<DvText> items) throws UnsupportedEncodingException{
            int position = offset;
            DvTextSerializer dts = new DvTextSerializer();
            position = dts.listSerialize(buffer, position, items);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DvParagraph dvParagraph) throws UnsupportedEncodingException{
            int position = offset;
            DvParagraphSerializer dps = new DvParagraphSerializer();
            position = dps.serialize(buffer, position, dvParagraph.getItems());
            
            return position;
        }
        
        protected DvParagraph deserialize(Buffer buffer, int offset){
            int position = offset;
            DvTextSerializer dts = new DvTextSerializer();
            List<DvText> items = dts.deserializeList(buffer, position);
            
            DvParagraph dvParagraph = RMObjectFactory.newDvParagraph(items);
            
            return dvParagraph;
        }
    }
    
    public static class PartyProxySerializer {
        protected int serialize(Buffer buffer, int offset, 
            PartyRef externalRef) throws UnsupportedEncodingException{
            int position = offset;
            PartyRefSerializer prs = new PartyRefSerializer();
            position = prs.serialize(buffer, position, externalRef);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                PartyProxy partyProxy) throws UnsupportedEncodingException{
            int position = offset;
            PartyProxySerializer prs = new PartyProxySerializer();
            
            position = 
                    prs.serialize(
                            buffer, position, partyProxy.getExternalRef());
            
            return position;
        }
        
        protected PartyProxy deserialize(Buffer buffer, int offset){
            int position = offset;
            PartyRefSerializer prs = new PartyRefSerializer();
            PartyRef externalRef = prs.deserialize(buffer, position);
            
            return RMObjectFactory.newPartyProxy(externalRef);
        }
    }
    
    public static class FeederAuditDetailsSerializer {
        protected int serialize(Buffer buffer, int offset, String systemID, 
                PartyIdentified provider, PartyIdentified location, 
                /*DvDateTime time,*/ PartyProxy subject, 
                String versionID) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + (6 * INT.getSize()) + 5 * BOOLEAN.getSize();
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            PartyProxySerializer pps = new PartyProxySerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = valueStringSerialization(buffer, position, systemID);
            
            boolean hasProvider = provider != null;
            if(hasProvider){
                meta = writeHeader(buffer, meta, hasProvider, position);
                position = pis.serialize(buffer, position, provider);
            } else {
                meta = writeHeader(buffer, meta, hasProvider);
            }
            
            boolean hasLocation = location != null;
            if(hasLocation){
                meta = writeHeader(buffer, meta, hasLocation, position);
                position = pis.serialize(buffer, position, location);
            } else {
                meta = writeHeader(buffer, meta, hasLocation);
            }
            
            boolean hasTime = false; //TO DO
            if(hasTime){
                meta = writeHeader(buffer, meta, hasTime, position);
                //position = pdt.serialize(buffer, position, time);
            } else {
                meta = writeHeader(buffer, meta, hasTime);
            }
            boolean hasSubject = subject != null;
            if(hasSubject){
                meta = writeHeader(buffer, meta, hasSubject, position);
                position = pps.serialize(buffer, position, subject);
            } else {
                meta = writeHeader(buffer, meta, hasSubject);
            }
            
            boolean hasVersionID = versionID != null;
            if(hasVersionID){
                writeHeader(buffer, meta, hasVersionID, position);
                position = valueStringSerialization(buffer, position, versionID);
            } else {
                writeHeader(buffer, meta, hasVersionID);
            }
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                FeederAuditDetails fad) throws UnsupportedEncodingException {
            FeederAuditDetailsSerializer fas = 
                    new FeederAuditDetailsSerializer();
            int position = offset;
            
            position = fas.serialize(
                    buffer, 
                    position, 
                    fad.getSystemID(),
                    fad.getProvider(),
                    fad.getLocation(),
                    /*fad.getTime(),*/
                    fad.getSubject(),
                    fad.getVersionID());
            
            return position;
        }
        
        protected FeederAuditDetails deserialize(Buffer buffer, int offset){
            int position = offset;
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            PartyProxySerializer pps = new PartyProxySerializer();
            //DvDateTimeSerializer dds = new DvDateTimeSerializer();
            int systemIDPosition = buffer.readInteger(position);
            position += INT.getSize();
            String systemID = 
                    valueStringDeserialization(buffer, systemIDPosition);
            
            boolean hasProvider = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            PartyIdentified provider = null;
            if(hasProvider){
                int providerPosition = buffer.readInteger(position);
                position += INT.getSize();
                provider = pis.deserialize(buffer, providerPosition);
            }
            
            boolean hasLocation = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            PartyIdentified location = null;
            if(hasLocation){
                int locationPosition = buffer.readInteger(position);
                position += INT.getSize();
                location = pis.deserialize(buffer, locationPosition);
            }
            
            boolean hasTime = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            if(hasTime){
                //TODO
            }
            
            boolean hasSubject = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            PartyProxy subject = null;
            if(hasSubject){
                int subjectPosition = buffer.readInteger(position);
                position += INT.getSize();
                subject = pps.deserialize(buffer, subjectPosition);
            }
            
            boolean hasVersionID = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            String versionID = null;
            if(hasVersionID){
                int versionIDPosition = buffer.readInteger(position);
                position += INT.getSize();
                versionID = 
                        valueStringDeserialization(buffer, versionIDPosition);
            }
            
            return RMObjectFactory.newFeederAuditDetails(
                    systemID, provider, location, subject, versionID);
        }
    }
    
    public static class FeederAuditSerializer {
        protected int serialize(Buffer buffer, int offset, 
                FeederAuditDetails originatingSystemAudit, 
                List<DvIdentifier> originatingSystemItemIDs, 
                FeederAuditDetails feederSystemAudit, 
                List<DvIdentifier> feederSystemItemIDs, 
                DvEncapsulated originalContent
        ) throws UnsupportedEncodingException{
            int meta = offset;
            int position = offset + (4 * INT.getSize()) + 4 * BOOLEAN.getSize();
            FeederAuditDetailsSerializer fas = 
                    new FeederAuditDetailsSerializer();
            meta = writeHeader(buffer, meta, position);
            position = fas.serialize(buffer, position, originatingSystemAudit);
            
            boolean hasOriginatingSystemItemIDs = 
                    originatingSystemItemIDs != null;
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            if(hasOriginatingSystemItemIDs){
                meta = writeHeader(
                        buffer, meta, hasOriginatingSystemItemIDs, position);
                position = dis.listSerialize(
                        buffer, position, originatingSystemItemIDs);
            } else {
                meta = writeHeader(buffer, meta, hasOriginatingSystemItemIDs);
            }
            
            boolean hasFeederSystemAudit = feederSystemAudit != null;
            if(hasFeederSystemAudit){
                meta = writeHeader(buffer, meta, hasFeederSystemAudit, position);
                position = fas.serialize(buffer, position, feederSystemAudit);
            } else {
                meta = writeHeader(buffer, meta, hasFeederSystemAudit);
            }
            
            boolean hasFeederSystemItemIDs = feederSystemItemIDs != null;
            if(hasFeederSystemItemIDs){
                meta = writeHeader(
                        buffer, meta, hasFeederSystemItemIDs, position);
                position = dis.listSerialize(
                        buffer, position, feederSystemItemIDs);
            } else {
                meta = writeHeader(buffer, meta, hasFeederSystemItemIDs);
            }
            
            boolean hasOriginalContent = originalContent != null;
            DvEncapsulatedSerializer des = new DvEncapsulatedSerializer();
            if(hasOriginalContent){
                writeHeader(buffer, meta, hasOriginalContent, position);
                position = des.serialize(buffer, position, originalContent);
            } else {
                writeHeader(buffer, meta, hasOriginalContent, position);
            }
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                FeederAudit fa) throws UnsupportedEncodingException{
            int position = offset;
            FeederAuditSerializer fas = new FeederAuditSerializer();
            
            position = fas.serialize(
                    buffer, 
                    position, 
                    fa.getOriginatingSystemAudit(),
                    fa.getFeederSystemItemIDs(),
                    fa.getFeederSystemAudit(),
                    fa.getFeederSystemItemIDs(),
                    fa.getOriginalContent());
            
            return position;
        }
        
        protected FeederAudit deserialize(Buffer buffer, int offset){
            int position = offset;
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            FeederAuditDetailsSerializer f = new FeederAuditDetailsSerializer();
            DvEncapsulatedSerializer des = new DvEncapsulatedSerializer();
            int OriginatingSystemAuditPosition = buffer.readInteger(position);
            position += INT.getSize();
            
            boolean hasOriginatingSystemItemIDs = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            List<DvIdentifier> originatingSystemItemIDs = null;
            if(hasOriginatingSystemItemIDs){
                int feederSystemItemIDsPosition = buffer.readInteger(position);
                position += INT.getSize();
                originatingSystemItemIDs = dis.deserializeList(
                        buffer, feederSystemItemIDsPosition);
            }
            
            boolean hasFeederSystemAudit = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            FeederAuditDetails feederSystemAudit = null;
            if(hasFeederSystemAudit){
                int hasFeederSystemAuditPosition = buffer.readInteger(position);
                position += INT.getSize();
                feederSystemAudit = f.deserialize(
                        buffer, hasFeederSystemAuditPosition);
            }
            
            boolean hasFeederSystemItemIDs = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            List<DvIdentifier> feederSystemItemIDs = null;
            if(hasFeederSystemItemIDs){
                int feederSystemItemIDsPosition = buffer.readInteger(position);
                position += INT.getSize();
                feederSystemItemIDs = dis.deserializeList(
                        buffer, feederSystemItemIDsPosition);
            }
            
            boolean hasOriginalContent = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            DvEncapsulated originalContent = null;
            if(hasOriginalContent){
                int originalContentPosition = buffer.readInteger(position);
                originalContent = des.deserialize(
                        buffer, originalContentPosition);
            }
            
            return RMObjectFactory.newFeederAudit(
                    feederSystemAudit, originatingSystemItemIDs, 
                    feederSystemAudit, feederSystemItemIDs, originalContent);
        }
    }
    
    public static class LocatableSerializer {
        protected int serialize(Buffer buffer, int offset, UIDBasedID uid, 
            String archetypeNodeId, DvText name,Archetyped archetypeDetails,
            FeederAudit feederAudit, 
            Set<Link> links) throws UnsupportedEncodingException{
            
            if(archetypeNodeId == null || name == null){
                throw new IllegalArgumentException(
                        "archetypeNodeId e name não podem ser null");
            }
            
            int position = offset + (7 * INT.getSize()) + 5 * BOOLEAN.getSize();
            int meta = offset;
            
            UIDBasedIDSerializer uids = new UIDBasedIDSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            ArchetypedSerializer as = new ArchetypedSerializer();
            FeederAuditSerializer fas = new FeederAuditSerializer();
            LinkSerializer ls = new LinkSerializer();
            
            boolean hasUid = uid != null;
            if(hasUid){
                meta = writeHeader(buffer, meta, hasUid, position);
                position = uids.serialize(buffer, position, archetypeNodeId);
            } else {
                meta = writeHeader(buffer, meta, hasUid);
            }
            
            meta = writeHeader(buffer, meta, position);
            position = 
                    valueStringSerialization(buffer, position, archetypeNodeId);
            
            meta = writeHeader(buffer, meta, position);
            position = dts.serialize(buffer, position, name);
            
            boolean hasArchetypeDetails = archetypeDetails != null;
            if(hasArchetypeDetails){
                meta = writeHeader(buffer, meta, hasArchetypeDetails, position);
                position = as.serialize(buffer, position, archetypeDetails);
            } else {
                meta = writeHeader(buffer, meta, hasArchetypeDetails);
            }
            
            boolean hasFeederAudit = feederAudit != null;
            if(hasFeederAudit){
                meta = writeHeader(buffer, meta, hasFeederAudit, position);
                position = fas.serialize(buffer, position, feederAudit);
            } else {
                meta = writeHeader(buffer, meta, hasFeederAudit);
            }
            
            boolean hasLinks = links != null;
            if(hasLinks){
                writeHeader(buffer, meta, hasLinks, position);
                position = ls.setSerializer(buffer, position, links);
            } else {
                writeHeader(buffer, meta, hasLinks);
            }
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Locatable locatable) throws UnsupportedEncodingException{
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();
            position = ls.serialize(buffer, position, locatable);
            
            return position;
        }
        
        protected Locatable deserialize(Buffer buffer, int offset){
            int position = offset;
            
            UIDBasedIDSerializer uids = new UIDBasedIDSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            ArchetypedSerializer as = new ArchetypedSerializer();
            FeederAuditSerializer fas = new FeederAuditSerializer();
            LinkSerializer ls = new LinkSerializer();
            
            boolean hasUid = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            UIDBasedID uid = null;
            if(hasUid){
                int uidPosition = buffer.readInteger(position);
                position += INT.getSize();
                uid = uids.deserialize(buffer, uidPosition);
            }
            
            int archetypeNodeIdPosition = buffer.readInteger(position);
            position += INT.getSize();
            String archetypeNodeId = valueStringDeserialization(
                    buffer, archetypeNodeIdPosition);
            
            int namePosition = buffer.readInteger(position);
            position += INT.getSize();
            DvText name = dts.deserialize(buffer, namePosition);
            
            boolean hasArchetypeDetails = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            Archetyped archetypeDetails = null;
            if(hasArchetypeDetails){
                int archetypeDetailsPosition = buffer.readInteger(position);
                position += INT.getSize();
                archetypeDetails = as.deserialize(
                        buffer, archetypeDetailsPosition);
            }
            
            boolean hasFeederAudit = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            FeederAudit feederAudit = null;
            if(hasFeederAudit){
                int hasFeederAuditPosition = buffer.readInteger(position);
                position += INT.getSize();
                feederAudit = fas.deserialize(buffer, hasFeederAuditPosition);
            }
            
            boolean hasLinks = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            Set<Link> links = null;
            if(hasLinks){
                int linksPosition = buffer.readInteger(position);
                links = ls.setDeserializer(buffer, linksPosition);
            }
            
            return RMObjectFactory.newLocatable(
                    uid, archetypeNodeId, name, archetypeDetails, 
                    feederAudit, links);
        }
    }

    /**
     * Serializa uma única String value
     *
     * @param buffer
     * @param offset
     * @param value
     * @return posição final após a serialização da String no buffer
     */
    private static int valueStringSerialization(
            Buffer buffer,
            int offset, String value) throws UnsupportedEncodingException {
        int valueLength = value.length();
        buffer.writeInteger(offset, valueLength);
        buffer.writeString(offset + INT.getSize(), value);

        return offset + INT.getSize() + valueLength;
    }

    /**
     * Deserializa uma string dado um determinado offset
     *
     * @param buffer
     * @param offset
     * @return String deserializada
     */
    private static String valueStringDeserialization(Buffer buffer,
            int offset) {
        int position = offset;
        int length = buffer.readInteger(position);
        position += INT.getSize();

        return buffer.readString(position, length);
    }

    /**
     * Escreve o valor inteiro do header de um determinado parâmetro que será
     * serializado em uma posição do buffer
     *
     * @param buffer
     * @param offset
     * @param value
     * @return posição final após a escrita do valor do header
     */
    private static int writeHeader(Buffer buffer, int offset, int value) {
        int position = offset;
        buffer.writeInteger(offset, value);
        return position + INT.getSize();
    }

    /**
     * Escreve o valor boolean do header de um determinado parâmetro que será
     * serializado em uma posição do buffer
     *
     * @param buffer
     * @param offset
     * @param exists
     * @return posição final após a escrita do valor do header
     */
    private static int writeHeader(Buffer buffer, int offset, boolean exists) {
        int position = offset;
        buffer.writeBoolean(offset, exists);
        return position + BOOLEAN.getSize();
    }

    /**
     * Escreve o valor inteiro do header de um determinado parâmetro que será
     * serializado em uma posição do buffer
     *
     * @param buffer
     * @param offset
     * @param exists
     * @param value
     * @return posição final após a escrita do valor do header
     */
    private static int writeHeader(Buffer buffer, int offset,
            boolean exists, int value) {
        int position = offset;
        if (exists) {
            buffer.writeBoolean(position, exists);
            position += BOOLEAN.getSize();
            buffer.writeInteger(position, value);

            position += INT.getSize();
        } else {
            buffer.writeBoolean(position, exists);
            position += BOOLEAN.getSize();
        }
        return position;
    }
}
