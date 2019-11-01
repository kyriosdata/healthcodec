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

import com.github.kyriosdata.healthcodec.RMObject.*;
import com.github.kyriosdata.healthcodec.RMObject.UUID;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *
 * @author Gabriel
 */
public class RMObjectSerialization {

    static class DvBooleanSerializer {

        protected int serialize(Buffer buffer, int offset, boolean value) {
            buffer.writeBoolean(offset, value);
            return offset + PrimitiveTypeSize.BOOLEAN.getSize();
        }
        
        protected int serialize(Buffer buffer, int offset, DvBoolean d) {
            int position = offset;
            DvBooleanSerializer dbs = new DvBooleanSerializer();
            position = dbs.serialize(buffer, position, d.getValue());
            
            return position;
        }

        protected DvBoolean deserialize(Buffer buffer, int offset) {
            boolean value = buffer.readBoolean(offset);
            return RMObjectFactory.newDvBoolean(value);
        }

    }

    static class DvIdentifierSerializer {

        protected int serialize(Buffer buffer,
                int offset, String issuer, String assigner, String id,
                String type) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize();
            
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, issuer);
            
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, assigner);
            
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, id);
            
            writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, type);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                DvIdentifier dvi) throws UnsupportedEncodingException {
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            int position = offset;

            position = dis.serialize(
                    buffer,
                    position,
                    dvi.getIssuer(),
                    dvi.getAssigner(),
                    dvi.getId(),
                    dvi.getType());

            return position;
        }

        protected DvIdentifier deserialize(Buffer buffer, int offset) {
            int position = offset;
            
            int issuerPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String issuer = stringDeserialization(buffer, issuerPosition);
            
            int assignerPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String assigner = stringDeserialization(
                    buffer, assignerPosition);
            
            int idPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String id = stringDeserialization(buffer, idPosition);
            
            int typePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String type = stringDeserialization(buffer, typePosition);
            
            return RMObjectFactory.newDvIdentifier(issuer, assigner, id, type);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<DvIdentifier> items)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = items.size();
            int position = offset + (listSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();

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
            position += PrimitiveTypeSize.INT.getSize();

            List<DvIdentifier> list = new ArrayList<>();
            DvIdentifierSerializer dis = new DvIdentifierSerializer();

            for (int i = 0; i < listSize; i++) {
                int dvIdentifierPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                DvIdentifier t = dis.deserialize(buffer, dvIdentifierPosition);
                list.add(t);
            }

            return list;
        }
    }
    
    static class UIDSerializer {
        protected int serialize(Buffer buffer, int offset, 
                String value) throws UnsupportedEncodingException {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                UID u) throws UnsupportedEncodingException {
            int position = offset;
            UIDSerializer us = new UIDSerializer();
            position = us.serialize(buffer, position, u.getValue());
            
            return position;
        }
        
        protected UID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);
            
            return RMObjectFactory.newUID(value);
        }
    }
    
    static class InternetIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                InternetID id) throws UnsupportedEncodingException {
            int position = offset;
            InternetIDSerializer s = new InternetIDSerializer();
            
            position = s.serialize(buffer, position, id.getUid().getValue());
            
            return position;
        }

        protected InternetID deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);
            
            return RMObjectFactory.newInternetID(value);
        }
    }

    static class ISOOIDSerialilzer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                ISO_OID iso) throws UnsupportedEncodingException {
            int position = offset;
            ISOOIDSerialilzer isos = new ISOOIDSerialilzer();
            
            position = isos.serialize(buffer, position, 
                    iso.getUid().getValue());
            
            return position;
        }
        
        protected ISO_OID deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newISOOID(value);
        }
    }

    static class UUIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                UUID uuid) throws UnsupportedEncodingException{
            int position = offset;
            UUIDSerializer us = new UUIDSerializer();
            position = us.serialize(buffer, position, uuid.getUid().getValue());
            
            return position;
        }

        protected UUID deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newUUID(value);
        }
    }

    static class GenericIDSerializer {

        protected int serialize(
                Buffer buffer, int offset, String value, String scheme)
                throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, value);
            
            writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, scheme);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                GenericID gid) throws UnsupportedEncodingException {
            int position = offset;
            GenericIDSerializer gids = new GenericIDSerializer();
            position = gids.serialize(buffer, position, 
                    gid.getObjectID().getValue(), gid.getScheme());
            
            return position;
        }

        protected GenericID deserialize(Buffer buffer, int offset) {
            int position = offset;
            
            int valuePositon = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String value = stringDeserialization(buffer, valuePositon);
            
            int schemePosition = buffer.readInteger(position);
            String scheme = stringDeserialization(buffer, schemePosition);
            
            return RMObjectFactory.newGenericID(value, scheme);
        }
    }

    static class TemplateIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                TemplateID tid) throws UnsupportedEncodingException {
            int position = offset;
            TemplateIDSerializer ts = new TemplateIDSerializer();
            position = ts.serialize(buffer, position, 
                    tid.getObjectID().getValue());
            
            return position;
        }

        protected TemplateID deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newTemplateID(value);
        }
    }

    static class TerminologyIDSerializer {

        protected int serialize(Buffer buffer, int offset, String name, 
                String version) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, name);
            
            writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, version);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, TerminologyID tid)
                throws UnsupportedEncodingException {
            int position = offset;
            TerminologyIDSerializer tids = new TerminologyIDSerializer();
            position = tids.serialize(buffer, position, 
                    tid.getName(), tid.getVersion());
            
            return position;
        }

        protected TerminologyID deserialize(Buffer buffer, int offset) {
            int position = offset;

            int namePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String name = stringDeserialization(buffer, namePosition);

            int versionPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String version = stringDeserialization(buffer, versionPosition);

            return RMObjectFactory.newTerminologyID(name, version);
        }
    }

    static class CodePhraseSerializer {

        protected int serialize(
                Buffer buffer, int offset,
                TerminologyID terminologyId, String codeString)
                throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            
            TerminologyIDSerializer ts = new TerminologyIDSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = ts.serialize(buffer, position, terminologyId);
            
            writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, codeString);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                CodePhrase cp) throws UnsupportedEncodingException {
            int position = offset;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            position = cps.serialize(buffer,
                    position, cp.getTerminologyID(), cp.getCodeString());

            return position;
        }

        protected CodePhrase deserialize(
                Buffer buffer, int offset) {
            int position = offset;
            TerminologyIDSerializer ts = new TerminologyIDSerializer();
            
            int terminologyIDPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            TerminologyID terminologyID = ts.deserialize(
                    buffer, terminologyIDPosition);
            
            int codeStringPosition = buffer.readInteger(position);
            String codeString = stringDeserialization(buffer,
                    codeStringPosition);
            
            return RMObjectFactory.newCodePhrase(terminologyID, codeString);
        }
    }

    static class DVURISerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DVURI d) throws UnsupportedEncodingException{
            int position = offset;
            DVURISerializer ds = new DVURISerializer();
            position = ds.serialize(buffer, position, d.getValue());
            
            return position;
        }

        protected DVURI deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newDVURI(value);
        }
    }

    static class DVEHRURISerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }

        protected int serialize(Buffer buffer, int offset,
                DVEHRURI dvehruri) throws UnsupportedEncodingException {
            DVEHRURISerializer des = new DVEHRURISerializer();
            int position = offset;

            position = des.serialize(buffer, position, 
                    dvehruri.getDvuri().getValue());

            return position;
        }

        protected DVEHRURI deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newDVEHRURI(value);
        }
    }

    static class VersionTreeIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }
        
        protected int serialize(Buffer buffer, int offset, VersionTreeID vti)
                throws UnsupportedEncodingException {
            int position = offset;
            VersionTreeIDSerializer vs = new VersionTreeIDSerializer();
            position = vs.serialize(buffer, position, vti.getValue());
            
            return position;
        }

        protected VersionTreeID deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);
            
            return RMObjectFactory.newVersionTreeID(value);
        }
    }

    static class ArchetypeIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, ArchetypeID a)
                throws UnsupportedEncodingException {
            int position = offset;
            ArchetypeIDSerializer as = new ArchetypeIDSerializer();
            position = as.serialize(buffer, position, 
                    a.getObjectID().getValue());
            
            return position;
        }

        protected ArchetypeID deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newArchetypeID(value);
        }
    }

    static class ObjectVersionIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }
        
        protected int serialize(Buffer buffer, int offset, ObjectVersionID o)
                throws UnsupportedEncodingException {
            int position = offset;
            ObjectVersionIDSerializer os = new ObjectVersionIDSerializer();
            position = os.serialize(buffer, position, 
                    o.getUIDBasedID().getValue());
            
            return position;
        }

        protected ObjectVersionID deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newObjectVersionID(value);
        }
    }

    static class HierObjectIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }
        
        protected int serialize(Buffer buffer, int offset, HierObjectID h)
                throws UnsupportedEncodingException {
            int position = offset;
            HierObjectIDSerializer os = new HierObjectIDSerializer();
            position = os.serialize(buffer, position, 
                    h.getUIDBasedID().getValue());
            
            return position;
        }
        
        protected HierObjectID deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newHierObjectID(value);
        }
    }

    static class ObjectIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }
        
        protected int serialize(Buffer buffer, int offset, ObjectID o)
                throws UnsupportedEncodingException {
            int position = offset;
            ObjectIDSerializer os = new ObjectIDSerializer();
            position = os.serialize(buffer, position, o.getValue());
            
            return position;
        }
        
        protected ObjectID deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newObjectID(value);
        }
    }

    static class PartyRefSerializer {

        protected int serialize(
                Buffer buffer,
                int offset,
                ObjectID id,
                String value) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            ObjectIDSerializer os = new ObjectIDSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = os.serialize(buffer, position, id);
            
            writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, value);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartyRef pr) throws UnsupportedEncodingException {
            int position = offset;
            PartyRefSerializer prs = new PartyRefSerializer();

            position
                    = prs.serialize(buffer, position,
                            pr.getObjectRef().getId(), 
                            pr.getObjectRef().getType());

            return position;
        }

        protected PartyRef deserialize(Buffer buffer, int offset) {
            int position = offset;
            ObjectIDSerializer os = new ObjectIDSerializer();
            
            int idPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ObjectID id = os.deserialize(buffer, idPosition);
            
            int valuePosition = buffer.readInteger(position);
            String value = stringDeserialization(buffer, valuePosition);

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
            int meta = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();
            
            ObjectIDSerializer os = new ObjectIDSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = os.serialize(buffer, position, id);
            
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, namespace);
            
            writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, type);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObjectRef or) throws UnsupportedEncodingException {
            int position = offset;
            ObjectRefSerializer ors = new ObjectRefSerializer();
            position = ors.serialize(buffer, position, or.getId(),
                    or.getNamespace(), or.getType());

            return position;
        }

        protected ObjectRef deserialize(Buffer buffer, int offset) {
            int position = offset;
            ObjectIDSerializer os = new ObjectIDSerializer();
            
            int idPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ObjectID id = os.deserialize(buffer, idPosition);
            
            int namespacePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String namespace = stringDeserialization(
                    buffer, namespacePosition);
            
            int typePosition = buffer.readInteger(position);
            String type = stringDeserialization(buffer, typePosition);
            
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
            int meta = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize() + PrimitiveTypeSize.BOOLEAN.getSize();
            
            ObjectVersionIDSerializer os = new ObjectVersionIDSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = os.serialize(buffer, position, id);
            
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, namespace);
            
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, type);
            
            boolean hasPath = path != null;
            if(hasPath){
                writeHeader(buffer, meta, hasPath, position);
                position = stringSerialization(buffer, position, path);
            } else {
                writeHeader(buffer, meta, hasPath);
            }
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                LocatableRef lr) throws UnsupportedEncodingException {
            int position = offset;
            LocatableRefSerializer lrs = new LocatableRefSerializer();

            position = lrs.serialize(buffer, position,
                    RMObjectFactory.newObjectVersionID(
                            lr.getObjectRef().getId().getValue()), 
                    lr.getObjectRef().getNamespace(), 
                    lr.getObjectRef().getType(), lr.getPath());

            return position;
        }

        protected LocatableRef deserialize(Buffer buffer, int offset) {
            int position = offset;
            ObjectVersionIDSerializer os = new ObjectVersionIDSerializer();
            
            int idPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ObjectVersionID id = os.deserialize(buffer, idPosition);
            
            int namespacePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String namespace = stringDeserialization(
                    buffer, namespacePosition);
            
            int typePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String type = stringDeserialization(buffer, typePosition);
            
            boolean hasPath = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            String path = null;
            if(hasPath){
                int pathPosition = buffer.readInteger(position);
                path = stringDeserialization(buffer, pathPosition);
            }
            
            return RMObjectFactory.newLocatableRef(id, namespace, type, path);
        }

        protected int setSerializer(Buffer buffer, int offset,
                Set<LocatableRef> lrefs) throws UnsupportedEncodingException {
            int setSize = lrefs.size();
            int position = offset + (setSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();
            int meta = offset;
            LocatableRefSerializer lrs = new LocatableRefSerializer();

            meta = writeHeader(buffer, meta, setSize);
            Iterator<LocatableRef> it = lrefs.iterator();

            while (it.hasNext()) {
                LocatableRef lr = it.next();
                int linkPosition = position;
                meta = writeHeader(buffer, meta, linkPosition);
                position = lrs.serialize(buffer, position, lr);
            }

            return position;
        }

        protected Set<LocatableRef> setDeserializer(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            LocatableRefSerializer lrs = new LocatableRefSerializer();
            Set<LocatableRef> lrefs = new HashSet<>();

            for (int i = 0; i < listSize; i++) {
                int prPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                LocatableRef lr = lrs.deserialize(buffer, prPosition);
                lrefs.add(lr);
            }

            return lrefs;
        }

    }

    static class ProportionKindSerializer {

        protected int serialize(Buffer buffer, int offset, int value) {
            int position = offset;
            
            buffer.writeInteger(position, value);
            position += PrimitiveTypeSize.INT.getSize();
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, ProportionKind p) {
            int position = offset;
            ProportionKindSerializer ps = new ProportionKindSerializer();
            
            position = ps.serialize(buffer, position, p.getValue());
            
            return position;
        }

        protected ProportionKind deserialize(Buffer buffer, int offset) {
            int value = buffer.readInteger(offset);
            return RMObjectFactory.newPropotionKind(value);
        }
    }

    static class AccessGroupRefSerializer {

        protected int serialize(Buffer buffer, int offset, 
                ObjectID id) throws UnsupportedEncodingException {
            int position = offset;
            
            ObjectIDSerializer os = new ObjectIDSerializer();
            
            position = os.serialize(buffer, position, id);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                AccessGroupRef agr) throws UnsupportedEncodingException {
            int position = offset;
            
            AccessGroupRefSerializer agrs = new AccessGroupRefSerializer();
            
            position = agrs.serialize(
                    buffer, position, agr.getObjectRef().getId());
            
            return position;
        }
        
        protected AccessGroupRef deserialize(Buffer buffer, int offset) {
            int position = offset;
            
            ObjectIDSerializer os = new ObjectIDSerializer();
            ObjectID id = os.deserialize(buffer, position);

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
            int position = offset + (3 * PrimitiveTypeSize.INT.getSize());
            PartyRefSerializer prs = new PartyRefSerializer();
            DvIdentifierSerializer dis = new DvIdentifierSerializer();

            meta = writeHeader(buffer, meta, position);
            position = prs.serialize(buffer, position, externalRef);

            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, name);

            writeHeader(buffer, meta, position);
            position = dis.listSerialize(buffer, position, identifiers);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, PartyIdentified pi)
                throws UnsupportedEncodingException {
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            int position = offset;

            position = pis.serialize(buffer, position,
                    pi.getExternalRef(), pi.getName(), pi.getIdentifiers());

            return position;
        }

        protected PartyIdentified deserialize(Buffer buffer, int offset) {
            int position = offset;
            PartyRefSerializer prs = new PartyRefSerializer();
            DvIdentifierSerializer dis = new DvIdentifierSerializer();

            int externalRefPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            PartyRef externalRef = prs.deserialize(buffer, externalRefPosition);

            int namePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String name = stringDeserialization(buffer, namePosition);

            int identifiersPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            List<DvIdentifier> identifiers = dis.deserializeList(
                    buffer, identifiersPosition);

            return RMObjectFactory.newPartyIdentified(
                    externalRef, name, identifiers);
        }
    }

    static class ArchetypedSerializer {

        protected int serialize(Buffer buffer, int offset,
                ArchetypeID archetypeId,TemplateID templateId,
                String rmVersion) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize() + PrimitiveTypeSize.BOOLEAN.getSize();
            
            ArchetypeIDSerializer ais = new ArchetypeIDSerializer();
            TemplateIDSerializer tis = new TemplateIDSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = ais.serialize(buffer, position, archetypeId);
            
            boolean hasTemplateId = templateId != null;
            if(hasTemplateId){
                meta = writeHeader(buffer, meta, hasTemplateId, position);
                position = tis.serialize(buffer, position, templateId);
            } else {
                meta = writeHeader(buffer, meta, hasTemplateId);
            }
            
            writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, rmVersion);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Archetyped a) throws UnsupportedEncodingException {
            int position = offset;
            ArchetypedSerializer as = new ArchetypedSerializer();

            position = as.serialize(buffer, position,
                    a.getArchetypeId(), a.getTemplateId(), a.getRmVersion());

            return position;
        }

        protected Archetyped deserialize(Buffer buffer, int offset) {
            int position = offset;
            ArchetypeIDSerializer ais = new ArchetypeIDSerializer();
            TemplateIDSerializer tis = new TemplateIDSerializer();
            
            int archetypeIdPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ArchetypeID archetypeId = ais.deserialize(
                    buffer, archetypeIdPosition);
            
            boolean hasTemplateId = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            TemplateID templateId = null;
            if(hasTemplateId){
                int templateIdPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                templateId = tis.deserialize(buffer, templateIdPosition);
            }
            
            int rmVersionPosition = buffer.readInteger(position);
            String rmVersion = stringDeserialization(buffer, rmVersionPosition);
            
            return RMObjectFactory.
                    newArchetyped(archetypeId, templateId, rmVersion);
        }
    }

    public static class DvEncapsulatedSerializer {

        protected int serialize(Buffer buffer, int offset, CodePhrase charset, 
                CodePhrase language) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            
            CodePhraseSerializer cps = new CodePhraseSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position, charset);
            
            writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position, language);
            
            return position;
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
            int position = offset;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            
            int charsetPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CodePhrase charset = cps.deserialize(buffer, charsetPosition);
            
            int languagePosition = buffer.readInteger(position);
            CodePhrase language = cps.deserialize(buffer, languagePosition);

            return RMObjectFactory.newDvEncapsulated(charset, language);
        }
    }

    public static class UIDBasedIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                throws UnsupportedEncodingException {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                UIDBasedID uid) throws UnsupportedEncodingException {
            int position = offset;
            UIDBasedIDSerializer us = new UIDBasedIDSerializer();

            position = us.serialize(buffer, position, uid.getValue());

            return position;
        }

        protected UIDBasedID deserialize(Buffer buffer, int offset) {
            int position = offset;
            String value = stringDeserialization(buffer, position);
            
            return RMObjectFactory.newUIDBasedID(value);
        }
    }

    public static class DvParsableSerializer {

        protected int serialize(Buffer buffer, int offset, 
                DvEncapsulated dvEncapsulated, String value,
                String formalism) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize();
            
            DvEncapsulatedSerializer des = new DvEncapsulatedSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = des.serialize(buffer, position, dvEncapsulated);
            
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, value);
            
            writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, formalism);

            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DvParsable dp) throws UnsupportedEncodingException{
            int position = offset;
            DvParsableSerializer dps = new DvParsableSerializer();
            
            position = dps.serialize(buffer, position, 
                    dp.getDvEncapsulated(), dp.getValue(), dp.getFormalism());
            
            return position;
        }

        protected DvParsable deserialize(Buffer buffer, int offset) {
            int position = offset;
            
            DvEncapsulatedSerializer des = new DvEncapsulatedSerializer();
            
            int dvEncapsulatedPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvEncapsulated dvEncapsulated = des.deserialize(buffer, 
                    dvEncapsulatedPosition);
            
            int valuePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String value = stringDeserialization(buffer, valuePosition);
            
            int formalismPosition = buffer.readInteger(position);
            String formalism = stringDeserialization(buffer, formalismPosition);

            return RMObjectFactory.newDvParsable(dvEncapsulated, value, 
                    formalism);
        }
    }

    public static class DvTimeSpecificationSerializer {

        protected int serialize(Buffer buffer, int offset,
                DvParsable value) throws UnsupportedEncodingException {
            int position = offset;
            DvParsableSerializer s = new DvParsableSerializer();
            position = s.serialize(buffer, position, value.getDvEncapsulated(), 
                    value.getValue(), value.getFormalism());

            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DvTimeSpecification d) throws UnsupportedEncodingException {
            int position = offset;
            DvTimeSpecificationSerializer dtss = 
                    new DvTimeSpecificationSerializer();
            position = dtss.serialize(buffer, position, d.getValue());
            
            return position;
        }

        protected DvTimeSpecification deserialize(Buffer buffer, int offset) {
            int position = offset;
            
            DvParsableSerializer dps = new DvParsableSerializer();
            DvParsable value = dps.deserialize(buffer, position);

            return RMObjectFactory.newDvTimeSpecification(value);
        }
    }

    public static class DvMultimediaSerializer {

        protected int serialize(Buffer buffer, int offset,
                DvEncapsulated dvEncapsulated, String alternateText,
                CodePhrase mediaType, CodePhrase compressionAlgorithm,
                byte[] integrityCheck, CodePhrase integrityCheckAlgorithm,
                DvMultimedia thumbnail, DVURI uri, 
                byte[] data) throws UnsupportedEncodingException {

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
            position = dve.serialize(buffer, position,
                    dvEncapsulated.getCharset(),
                    dvEncapsulated.getLanguage());
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(
                    buffer, position, alternateText);

            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position,
                    mediaType.getTerminologyID(), mediaType.getCodeString());

            if (hasCompressionAlgorithm) {
                meta = writeHeader(
                        buffer, meta, hasCompressionAlgorithm, position);
                position = cps.serialize(
                        buffer,
                        position,
                        compressionAlgorithm.getTerminologyID(),
                        compressionAlgorithm.getCodeString());
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
                        integrityCheckAlgorithm);
            } else {
                throw new IllegalArgumentException("Integrity Check fails!");
            }

            if (hasThumbnail) {
                meta = writeHeader(buffer, meta, hasThumbnail, position);
                position = dvm.serialize(
                        buffer,
                        position,
                        thumbnail.getDvEncapsulated(),
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
            position = dvu.serialize(buffer, position, uri.getValue());

            meta = writeHeader(buffer, meta, position);
            writeHeader(buffer, meta, data.length);
            buffer.writeByteArray(position, data);
            position += data.length;

            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DvMultimedia d) throws UnsupportedEncodingException {
            int position = offset;
            DvMultimediaSerializer dms = new DvMultimediaSerializer();
            
            position = dms.serialize(buffer, position, 
                    d.getDvEncapsulated(), d.getAlternateText(),
                    d.getMediaType(), d.getCompressionAlgorithm(),
                    d.getIntegrityCheck(), d.getIntegrityCheckAlgorithm(),
                    d.getThumbnail(), d.getUri(), d.getData());
            
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
            meta += PrimitiveTypeSize.INT.getSize();

            int alternateTextPosition = buffer.readInteger(meta);
            String alternateText
                    = stringDeserialization(buffer, alternateTextPosition);
            meta += PrimitiveTypeSize.INT.getSize();

            int mediaTypePosition = buffer.readInteger(meta);
            CodePhrase mediaType = cps.deserialize(buffer, mediaTypePosition);
            meta += PrimitiveTypeSize.INT.getSize();

            boolean hasCompressionAlgorithm = buffer.readBoolean(meta);
            int compressionAlgorithmPosition = 0;
            CodePhrase compressionAlgorithm = null;
            if (hasCompressionAlgorithm) {
                meta += PrimitiveTypeSize.BOOLEAN.getSize();
                compressionAlgorithmPosition = buffer.readInteger(meta);
                compressionAlgorithm
                        = cps.deserialize(buffer, compressionAlgorithmPosition);
                meta += PrimitiveTypeSize.INT.getSize();
            } else {
                meta += PrimitiveTypeSize.BOOLEAN.getSize();
            }
            boolean hasIntegrityCheck = buffer.readBoolean(meta);
            int integrityCheckPosition = 0;
            int integrityCheckLength = 0;
            byte[] integrityCheck = null;
            if (hasIntegrityCheck) {
                meta += PrimitiveTypeSize.BOOLEAN.getSize();
                integrityCheckPosition = buffer.readInteger(meta);
                meta += PrimitiveTypeSize.INT.getSize();
                integrityCheckLength = buffer.readInteger(meta);
                meta += PrimitiveTypeSize.INT.getSize();

                integrityCheck = buffer.readByteArray(integrityCheckPosition, integrityCheckLength);
            } else {
                meta += PrimitiveTypeSize.BOOLEAN.getSize();
            }

            boolean hasIntegrityCheckAlgorithm = buffer.readBoolean(meta);
            int integrityCheckAlgorithmPosition = 0;
            CodePhrase integrityCheckAlgorithm = null;
            if (hasIntegrityCheckAlgorithm) {
                meta += PrimitiveTypeSize.BOOLEAN.getSize();
                integrityCheckAlgorithmPosition = buffer.readInteger(meta);
                meta += PrimitiveTypeSize.INT.getSize();
                integrityCheckAlgorithm = cps.deserialize(buffer, integrityCheckAlgorithmPosition);
            } else {
                meta += PrimitiveTypeSize.BOOLEAN.getSize();
            }

            boolean hasThumbnail = buffer.readBoolean(meta);
            int thumbnailPosition = 0;
            DvMultimedia thumbnail = null;
            if (hasThumbnail) {
                meta += PrimitiveTypeSize.BOOLEAN.getSize();
                thumbnailPosition = buffer.readInteger(meta);
                meta += PrimitiveTypeSize.INT.getSize();
                thumbnail = dvm.deserialize(buffer, thumbnailPosition);
            } else {
                meta += PrimitiveTypeSize.BOOLEAN.getSize();
            }

            int uriPosition = buffer.readInteger(meta);
            DVURI uri = dvu.deserialize(buffer, uriPosition);
            meta += PrimitiveTypeSize.INT.getSize();

            int dataPosition = buffer.readInteger(meta);
            meta += PrimitiveTypeSize.INT.getSize();
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
            int position = offset + 6 * PrimitiveTypeSize.INT.getSize() + 3 * PrimitiveTypeSize.BOOLEAN.getSize();

            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, value);
            if (hasMappings) {

                meta = writeHeader(buffer, meta, hasMappings, position);
                position = tms.listSerialize(buffer, position, mappings);
            } else {
                meta = writeHeader(buffer, meta, hasMappings);
            }

            if (hasFormatting) {
                meta = writeHeader(buffer, meta, hasFormatting, position);
                position
                        = stringSerialization(buffer, position, formatting);
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
            position = cps.serialize(buffer, position, language);
            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position, charset);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvText dvText)
                throws UnsupportedEncodingException {
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
            String value = stringDeserialization(buffer, valuePosition);
            position += PrimitiveTypeSize.INT.getSize();

            boolean hasMappings = buffer.readBoolean(position);
            int mappingsPosition = 0;
            List<TermMapping> mappings = null;
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            if (hasMappings) {
                mappingsPosition = buffer.readInteger(position);
                mappings = tms.deserializeList(buffer, mappingsPosition);
                position += PrimitiveTypeSize.INT.getSize();
            }

            boolean hasFormatting = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            int formattingPosition = 0;
            String formatting = null;
            if (hasFormatting) {
                formattingPosition = buffer.readInteger(position);
                formatting
                        = stringDeserialization(buffer, formattingPosition);
                position += PrimitiveTypeSize.INT.getSize();
            }
            boolean hasHyperlink = buffer.readBoolean(position);
            int hyperlinkPosition = 0;
            DVURI hyperlink = null;
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            if (hasHyperlink) {
                hyperlinkPosition = buffer.readInteger(position);
                hyperlink = dvu.deserialize(buffer, hyperlinkPosition);
                position += PrimitiveTypeSize.INT.getSize();
            }

            int languagePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
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
            int position = offset + (listSize * PrimitiveTypeSize.INT.getSize())
                    + PrimitiveTypeSize.INT.getSize();

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
            position += PrimitiveTypeSize.INT.getSize();

            List<DvText> list = new ArrayList<>();
            DvTextSerializer dts = new DvTextSerializer();

            for (int i = 0; i < listSize; i++) {
                int dvTextPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                DvText t = dts.deserialize(buffer, dvTextPosition);
                list.add(t);
            }

            return list;
        }
        
        protected int setSerializer(Buffer buffer, int offset,
                Set<DvText> items) throws UnsupportedEncodingException {
            int setSize = items.size();
            int position = offset + (setSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();
            int meta = offset;
            DvTextSerializer dts = new DvTextSerializer();

            meta = writeHeader(buffer, meta, setSize);
            Iterator<DvText> it = items.iterator();

            while (it.hasNext()) {
                DvText d = it.next();
                int linkPosition = position;
                meta = writeHeader(buffer, meta, linkPosition);
                position = dts.serialize(buffer, position, d);
            }

            return position;
        }

        protected Set<DvText> setDeserializer(Buffer buffer, int offset) {
            int position = offset;
            int setSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            DvTextSerializer dts = new DvTextSerializer();
            Set<DvText> items = new HashSet<>();

            for (int i = 0; i < setSize; i++) {
                int dPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                DvText d = dts.deserialize(buffer, dPosition);
                items.add(d);
            }

            return items;
        }
    }

    public static class DvCodedTextSerializer {

        protected int serialize(Buffer buffer, int offset,
                DvText dvText, CodePhrase definingCode)
                throws UnsupportedEncodingException {
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            int meta = offset;
            DvTextSerializer dvt = new DvTextSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();

            meta = writeHeader(buffer, meta, position);
            if(dvText != null){
                position = dvt.serialize(
                        buffer,
                        position,
                        dvText.getValue(),
                        dvText.getMappings(),
                        dvText.getFormatting(),
                        dvText.getHyperlink(),
                        dvText.getLanguage(),
                        dvText.getCharset());
            }

            writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position, definingCode);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                DvCodedText dct) throws UnsupportedEncodingException {
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            int position = offset;

            position = dcs.serialize(buffer, position,
                    dct.getDvText(), dct.getDefiningCode());

            return position;
        }

        protected DvCodedText deserialize(Buffer buffer, int offset) {
            int position = offset;
            int dvTextPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
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
            position = stringSerialization(buffer, position, value);

            return position;
        }

        protected Match deserialize(Buffer buffer, int offset)
                throws IllegalAccessException {
            int position = offset;
            String value = stringDeserialization(buffer, position);

            Match match = Match.fromValue(value);

            return match;
        }
    }

    public static class TermMappingSerializer {

        protected int serialize(Buffer buffer, int offset, CodePhrase target,
                Match match, 
                DvCodedText purpose) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize() + PrimitiveTypeSize.BOOLEAN.getSize();
            boolean hasPurpose = purpose != null;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            MatchSerializer ms = new MatchSerializer();
            DvCodedTextSerializer dct = new DvCodedTextSerializer();

            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position, target);
            meta = writeHeader(buffer, meta, position);
            position = ms.serialize(buffer, position, match);
            if (hasPurpose) {
                writeHeader(buffer, meta, hasPurpose, position);
                position = dct.serialize(buffer, position,
                        purpose.getDvText(), purpose.getDefiningCode());
            } else {
                writeHeader(buffer, meta, hasPurpose);
            }
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                TermMapping t) throws UnsupportedEncodingException {
            int position = offset;
            TermMappingSerializer tms = new TermMappingSerializer();
            
            position = tms.serialize(buffer, position, 
                    t.getTarget(), t.getMatch(), t.getPurpose());
            
            return position;
        }
        
        protected TermMapping deserialize(Buffer buffer, int offset) {
            int position = offset;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            MatchSerializer ms = new MatchSerializer();
            DvCodedTextSerializer dct = new DvCodedTextSerializer();

            int targetPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CodePhrase target = cps.deserialize(buffer, targetPosition);

            int matchPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Match match = null;
            try {
                match = ms.deserialize(buffer, matchPosition);
            } catch (IllegalAccessException ex) {
                //
            }

            boolean hasPurpose = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvCodedText purpose = null;
            if (hasPurpose) {
                int purposePosition = buffer.readInteger(position);
                purpose = dct.deserialize(buffer, purposePosition);
                position += PrimitiveTypeSize.INT.getSize();
            }

            return RMObjectFactory.newTermMapping(target, match, purpose);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<TermMapping> mappings)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = mappings.size();
            int position = offset + (listSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();

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
            position += PrimitiveTypeSize.INT.getSize();

            List<TermMapping> list = new ArrayList<>();
            TermMappingSerializer tms = new TermMappingSerializer();

            for (int i = 0; i < listSize; i++) {
                int termMappingPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
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
                DVEHRURI target) throws UnsupportedEncodingException {

            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();
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
                Link link) throws UnsupportedEncodingException {
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

        protected Link deserialize(Buffer buffer, int offset) {
            int position = offset;
            DvTextSerializer dts = new DvTextSerializer();
            DVEHRURISerializer des = new DVEHRURISerializer();

            int meaningPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvText meaning = dts.deserialize(buffer, meaningPosition);

            int typePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvText type = dts.deserialize(buffer, typePosition);

            int targetPosition = buffer.readInteger(position);
            DVEHRURI target = des.deserialize(buffer, targetPosition);

            return RMObjectFactory.newLink(meaning, type, target);
        }

        protected int setSerializer(Buffer buffer, int offset,
                Set<Link> links) throws UnsupportedEncodingException {
            int setSize = links.size();
            int position = offset + (setSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();
            int meta = offset;
            LinkSerializer ls = new LinkSerializer();

            meta = writeHeader(buffer, meta, setSize);
            Iterator<Link> it = links.iterator();

            while (it.hasNext()) {
                Link link = it.next();
                int linkPosition = position;
                meta = writeHeader(buffer, meta, linkPosition);
                position = ls.serialize(buffer, position, link);
            }

            return position;
        }

        protected Set<Link> setDeserializer(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            LinkSerializer ls = new LinkSerializer();
            Set<Link> links = new HashSet<>();

            for (int i = 0; i < listSize; i++) {
                int linkPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

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
                String terminal) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize() + PrimitiveTypeSize.BOOLEAN.getSize();
            boolean hasTerminal = terminal != null;

            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            meta = writeHeader(buffer, meta, position);
            position = dcs.serialize(buffer, position, value);

            if (hasTerminal) {
                meta = writeHeader(buffer, meta, hasTerminal, position);
                position = stringSerialization(buffer, position, terminal);
            } else {
                meta = writeHeader(buffer, meta, hasTerminal);
            }

            return position;
        }

        protected int serialize(Buffer buffer,
                int offset,
                DvState dvState) throws UnsupportedEncodingException {
            int position = offset;
            DvStateSerializer dss = new DvStateSerializer();
            position = dss.serialize(
                    buffer, position, dvState.getValue(), dvState.getTerminal());

            return position;
        }

        protected DvState deserialize(Buffer buffer, int offset) {
            int position = offset;
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            int valuePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            DvCodedText value = dcs.deserialize(buffer, valuePosition);

            boolean hasTerminal = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            String terminal = null;
            if (hasTerminal) {
                int terminalPosition = buffer.readInteger(position);
                terminal = stringDeserialization(buffer, terminalPosition);
            }

            return RMObjectFactory.newDvState(value, terminal);
        }
    }

    public static class DvParagraphSerializer {

        protected int serialize(Buffer buffer, int offset,
                List<DvText> items) throws UnsupportedEncodingException {
            int position = offset;
            DvTextSerializer dts = new DvTextSerializer();
            position = dts.listSerialize(buffer, position, items);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                DvParagraph dvParagraph) throws UnsupportedEncodingException {
            int position = offset;
            DvParagraphSerializer dps = new DvParagraphSerializer();
            position = dps.serialize(buffer, position, dvParagraph.getItems());

            return position;
        }

        protected DvParagraph deserialize(Buffer buffer, int offset) {
            int position = offset;
            DvTextSerializer dts = new DvTextSerializer();
            List<DvText> items = dts.deserializeList(buffer, position);

            DvParagraph dvParagraph = RMObjectFactory.newDvParagraph(items);

            return dvParagraph;
        }
    }

    public static class PartyProxySerializer {

        protected int serialize(Buffer buffer, int offset,
                PartyRef externalRef) throws UnsupportedEncodingException {
            int position = offset;
            PartyRefSerializer prs = new PartyRefSerializer();
            position = prs.serialize(buffer, position, externalRef);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartyProxy partyProxy) throws UnsupportedEncodingException {
            int position = offset;
            PartyProxySerializer prs = new PartyProxySerializer();

            position
                    = prs.serialize(
                            buffer, position, partyProxy.getExternalRef());

            return position;
        }

        protected PartyProxy deserialize(Buffer buffer, int offset) {
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
            int position = offset + (6 * PrimitiveTypeSize.INT.getSize()) + 5 * PrimitiveTypeSize.BOOLEAN.getSize();
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            PartyProxySerializer pps = new PartyProxySerializer();

            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, systemID);

            boolean hasProvider = provider != null;
            if (hasProvider) {
                meta = writeHeader(buffer, meta, hasProvider, position);
                position = pis.serialize(buffer, position, provider);
            } else {
                meta = writeHeader(buffer, meta, hasProvider);
            }

            boolean hasLocation = location != null;
            if (hasLocation) {
                meta = writeHeader(buffer, meta, hasLocation, position);
                position = pis.serialize(buffer, position, location);
            } else {
                meta = writeHeader(buffer, meta, hasLocation);
            }

            boolean hasTime = false; //TO DO
            if (hasTime) {
                meta = writeHeader(buffer, meta, hasTime, position);
                //position = pdt.serialize(buffer, position, time);
            } else {
                meta = writeHeader(buffer, meta, hasTime);
            }
            boolean hasSubject = subject != null;
            if (hasSubject) {
                meta = writeHeader(buffer, meta, hasSubject, position);
                position = pps.serialize(buffer, position, subject);
            } else {
                meta = writeHeader(buffer, meta, hasSubject);
            }

            boolean hasVersionID = versionID != null;
            if (hasVersionID) {
                writeHeader(buffer, meta, hasVersionID, position);
                position = stringSerialization(buffer, position, versionID);
            } else {
                writeHeader(buffer, meta, hasVersionID);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                FeederAuditDetails fad) throws UnsupportedEncodingException {
            FeederAuditDetailsSerializer fas
                    = new FeederAuditDetailsSerializer();
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

        protected FeederAuditDetails deserialize(Buffer buffer, int offset) {
            int position = offset;
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            PartyProxySerializer pps = new PartyProxySerializer();
            //DvDateTimeSerializer dds = new DvDateTimeSerializer();
            int systemIDPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String systemID
                    = stringDeserialization(buffer, systemIDPosition);

            boolean hasProvider = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            PartyIdentified provider = null;
            if (hasProvider) {
                int providerPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                provider = pis.deserialize(buffer, providerPosition);
            }

            boolean hasLocation = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            PartyIdentified location = null;
            if (hasLocation) {
                int locationPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                location = pis.deserialize(buffer, locationPosition);
            }

            boolean hasTime = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            if (hasTime) {
                //TODO
            }

            boolean hasSubject = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            PartyProxy subject = null;
            if (hasSubject) {
                int subjectPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                subject = pps.deserialize(buffer, subjectPosition);
            }

            boolean hasVersionID = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            String versionID = null;
            if (hasVersionID) {
                int versionIDPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                versionID
                        = stringDeserialization(buffer, versionIDPosition);
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
        ) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + (4 * PrimitiveTypeSize.INT.getSize()) + 4 * PrimitiveTypeSize.BOOLEAN.getSize();
            FeederAuditDetailsSerializer fas
                    = new FeederAuditDetailsSerializer();
            meta = writeHeader(buffer, meta, position);
            position = fas.serialize(buffer, position, originatingSystemAudit);

            boolean hasOriginatingSystemItemIDs
                    = originatingSystemItemIDs != null;
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            if (hasOriginatingSystemItemIDs) {
                meta = writeHeader(
                        buffer, meta, hasOriginatingSystemItemIDs, position);
                position = dis.listSerialize(
                        buffer, position, originatingSystemItemIDs);
            } else {
                meta = writeHeader(buffer, meta, hasOriginatingSystemItemIDs);
            }

            boolean hasFeederSystemAudit = feederSystemAudit != null;
            if (hasFeederSystemAudit) {
                meta = writeHeader(buffer, meta, hasFeederSystemAudit, position);
                position = fas.serialize(buffer, position, feederSystemAudit);
            } else {
                meta = writeHeader(buffer, meta, hasFeederSystemAudit);
            }

            boolean hasFeederSystemItemIDs = feederSystemItemIDs != null;
            if (hasFeederSystemItemIDs) {
                meta = writeHeader(
                        buffer, meta, hasFeederSystemItemIDs, position);
                position = dis.listSerialize(
                        buffer, position, feederSystemItemIDs);
            } else {
                meta = writeHeader(buffer, meta, hasFeederSystemItemIDs);
            }

            boolean hasOriginalContent = originalContent != null;
            DvEncapsulatedSerializer des = new DvEncapsulatedSerializer();
            if (hasOriginalContent) {
                writeHeader(buffer, meta, hasOriginalContent, position);
                position = des.serialize(buffer, position, originalContent);
            } else {
                writeHeader(buffer, meta, hasOriginalContent, position);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                FeederAudit fa) throws UnsupportedEncodingException {
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

        protected FeederAudit deserialize(Buffer buffer, int offset) {
            int position = offset;
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            FeederAuditDetailsSerializer f = new FeederAuditDetailsSerializer();
            DvEncapsulatedSerializer des = new DvEncapsulatedSerializer();
            int OriginatingSystemAuditPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            boolean hasOriginatingSystemItemIDs = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<DvIdentifier> originatingSystemItemIDs = null;
            if (hasOriginatingSystemItemIDs) {
                int feederSystemItemIDsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                originatingSystemItemIDs = dis.deserializeList(
                        buffer, feederSystemItemIDsPosition);
            }

            boolean hasFeederSystemAudit = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            FeederAuditDetails feederSystemAudit = null;
            if (hasFeederSystemAudit) {
                int hasFeederSystemAuditPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                feederSystemAudit = f.deserialize(
                        buffer, hasFeederSystemAuditPosition);
            }

            boolean hasFeederSystemItemIDs = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<DvIdentifier> feederSystemItemIDs = null;
            if (hasFeederSystemItemIDs) {
                int feederSystemItemIDsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                feederSystemItemIDs = dis.deserializeList(
                        buffer, feederSystemItemIDsPosition);
            }

            boolean hasOriginalContent = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvEncapsulated originalContent = null;
            if (hasOriginalContent) {
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
                String archetypeNodeId, DvText name, Archetyped archetypeDetails,
                FeederAudit feederAudit,
                Set<Link> links) throws UnsupportedEncodingException {

            int position = offset + (6 * PrimitiveTypeSize.INT.getSize()) + 5 *
                    PrimitiveTypeSize.BOOLEAN.getSize();
            int meta = offset;

            UIDBasedIDSerializer uids = new UIDBasedIDSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            ArchetypedSerializer as = new ArchetypedSerializer();
            FeederAuditSerializer fas = new FeederAuditSerializer();
            LinkSerializer ls = new LinkSerializer();

            boolean hasUid = uid != null;
            if (hasUid) {
                meta = writeHeader(buffer, meta, hasUid, position);
                position = uids.serialize(buffer, position, archetypeNodeId);
            } else {
                meta = writeHeader(buffer, meta, hasUid);
            }

            meta = writeHeader(buffer, meta, position);
            position
                    = stringSerialization(buffer, position, archetypeNodeId);

            meta = writeHeader(buffer, meta, position);
            position = dts.serialize(buffer, position, name);

            boolean hasArchetypeDetails = archetypeDetails != null;
            if (hasArchetypeDetails) {
                meta = writeHeader(buffer, meta, hasArchetypeDetails, position);
                position = as.serialize(buffer, position, archetypeDetails);
            } else {
                meta = writeHeader(buffer, meta, hasArchetypeDetails);
            }

            boolean hasFeederAudit = feederAudit != null;
            if (hasFeederAudit) {
                meta = writeHeader(buffer, meta, hasFeederAudit, position);
                position = fas.serialize(buffer, position, feederAudit);
            } else {
                meta = writeHeader(buffer, meta, hasFeederAudit);
            }

            boolean hasLinks = links != null;
            if (hasLinks) {
                writeHeader(buffer, meta, hasLinks, position);
                position = ls.setSerializer(buffer, position, links);
            } else {
                writeHeader(buffer, meta, hasLinks);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Locatable locatable) throws UnsupportedEncodingException {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();
            position = ls.serialize(buffer, position, locatable.getUid(),
                    locatable.getArchetypeNodeId(), locatable.getName(),
                    locatable.getArchetypeDetails(), locatable.getFeederAudit(),
                    locatable.getLinks());

            return position;
        }

        protected Locatable deserialize(Buffer buffer, int offset) {
            int position = offset;

            UIDBasedIDSerializer uids = new UIDBasedIDSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            ArchetypedSerializer as = new ArchetypedSerializer();
            FeederAuditSerializer fas = new FeederAuditSerializer();
            LinkSerializer ls = new LinkSerializer();

            boolean hasUid = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            UIDBasedID uid = null;
            if (hasUid) {
                int uidPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                uid = uids.deserialize(buffer, uidPosition);
            }

            int archetypeNodeIdPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String archetypeNodeId = stringDeserialization(
                    buffer, archetypeNodeIdPosition);

            int namePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvText name = dts.deserialize(buffer, namePosition);

            boolean hasArchetypeDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Archetyped archetypeDetails = null;
            if (hasArchetypeDetails) {
                int archetypeDetailsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                archetypeDetails = as.deserialize(
                        buffer, archetypeDetailsPosition);
            }

            boolean hasFeederAudit = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            FeederAudit feederAudit = null;
            if (hasFeederAudit) {
                int hasFeederAuditPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                feederAudit = fas.deserialize(buffer, hasFeederAuditPosition);
            }

            boolean hasLinks = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Set<Link> links = null;
            if (hasLinks) {
                int linksPosition = buffer.readInteger(position);
                links = ls.setDeserializer(buffer, linksPosition);
            }

            return RMObjectFactory.newLocatable(
                    uid, archetypeNodeId, name, archetypeDetails,
                    feederAudit, links);
        }
    }

    public static class PartyRelatedSerializer {

        protected int serialize(Buffer buffer, int offset, PartyIdentified pi,
                DvCodedText relationship) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + (2 * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.BOOLEAN.getSize();
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            DvCodedTextSerializer dts = new DvCodedTextSerializer();

            if (relationship == null) {
                throw new IllegalArgumentException(
                        "relantionship não pode ser null");
            }
            boolean hasPi = pi != null;
            if (hasPi) {
                meta = writeHeader(buffer, meta, hasPi, position);
                position = pis.serialize(buffer, position, pi);
            } else {
                meta = writeHeader(buffer, meta, hasPi);
            }

            writeHeader(buffer, meta, position);
            position = dts.serialize(buffer, position, relationship);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartyRelated pr) throws UnsupportedEncodingException {
            int position = offset;
            PartyRelatedSerializer prs = new PartyRelatedSerializer();
            position = prs.serialize(
                    buffer, position, pr.getPi(), pr.getRelationship());

            return position;
        }

        protected PartyRelated deserialize(Buffer buffer, int offset) {
            int position = offset;
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            DvCodedTextSerializer dts = new DvCodedTextSerializer();

            boolean hasPi = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            PartyIdentified pi = null;
            if (hasPi) {
                int piPosition = buffer.readInteger(position);
                pi = pis.deserialize(buffer, piPosition);
                position += PrimitiveTypeSize.INT.getSize();
            }

            DvCodedText relationship = null;
            int relationshipPosition = buffer.readInteger(position);
            relationship = dts.deserialize(buffer, relationshipPosition);

            return RMObjectFactory.newPartyRelated(pi, relationship);
        }
    }

    public static class PartySelfSerializer {

        protected int serialize(Buffer buffer, int offset,
                PartyRef externalRef) throws UnsupportedEncodingException {
            int position = offset + PrimitiveTypeSize.INT.getSize() + PrimitiveTypeSize.BOOLEAN.getSize();
            int meta = offset;
            PartyRefSerializer prs = new PartyRefSerializer();

            boolean hasExternalRef = externalRef != null;
            if (hasExternalRef) {
                writeHeader(buffer, meta, hasExternalRef, position);
                position = prs.serialize(buffer, position, externalRef);
            } else {
                writeHeader(buffer, meta, hasExternalRef);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartySelf ps) throws UnsupportedEncodingException {
            int position = offset;
            PartySelfSerializer pss = new PartySelfSerializer();
            position = pss.serialize(buffer, position, ps.getExternalRef());

            return position;
        }

        protected PartySelf deserialize(Buffer buffer, int offset) {
            int position = offset;
            boolean hasExternalRef = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            PartyRefSerializer prs = new PartyRefSerializer();

            PartyRef externalRef = null;
            if (hasExternalRef) {
                int externalRefPosition = buffer.readInteger(position);
                externalRef = prs.deserialize(buffer, externalRefPosition);
            }

            return RMObjectFactory.newPartySelf(externalRef);
        }
    }

    public static class ResourceDescriptionItemSerializer {

        protected int serialize(Buffer buffer, int offset,
                CodePhrase language, String purpose, List<String> keywords,
                String use, String misuse, String copyright,
                Map<String, String> originalResourceUri,
                Map<String, String> otherDetails)
                throws UnsupportedEncodingException {
            int position = offset + 8 * PrimitiveTypeSize.INT.getSize() + 3 * PrimitiveTypeSize.BOOLEAN.getSize();

            int meta = offset;
            CodePhraseSerializer cps = new CodePhraseSerializer();

            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position, language);

            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, purpose);

            boolean hasKeywords = keywords != null;
            if (hasKeywords) {
                meta = writeHeader(buffer, meta, hasKeywords, position);
                position = listStringSerialization(buffer, position, keywords);
            } else {
                meta = writeHeader(buffer, meta, hasKeywords);
            }

            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, use);

            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, misuse);

            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, copyright);

            boolean hasOriginalResourceUri = originalResourceUri != null;
            if (hasOriginalResourceUri) {
                meta
                        = writeHeader(buffer, meta, hasOriginalResourceUri,
                                position);
                position = mapStringSerialization(
                        buffer, position, originalResourceUri);
            } else {
                meta = writeHeader(buffer, meta, hasOriginalResourceUri);
            }

            boolean hasOtherDetails = otherDetails != null;
            if (hasOtherDetails) {
                writeHeader(buffer, meta, hasOtherDetails, position);
                position = mapStringSerialization(buffer, position,
                        otherDetails);
            } else {
                writeHeader(buffer, meta, hasOtherDetails);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ResourceDescriptionItem rdi)
                throws UnsupportedEncodingException {
            ResourceDescriptionItemSerializer rdis
                    = new ResourceDescriptionItemSerializer();
            int position = offset;

            position = rdis.serialize(buffer, position, rdi.getLanguage(),
                    rdi.getPurpose(), rdi.getKeywords(), rdi.getUse(),
                    rdi.getMisuse(), rdi.getCopyright(),
                    rdi.getOriginalResourceUri(), rdi.getOtherDetails());

            return position;
        }

        protected ResourceDescriptionItem deserialize(Buffer buffer,
                int offset) {
            int position = offset;

            int languagePosition = buffer.readInteger(position);
            System.out.println(languagePosition);
            position += PrimitiveTypeSize.INT.getSize();
            CodePhraseSerializer cps = new CodePhraseSerializer();
            CodePhrase language = cps.deserialize(buffer, languagePosition);

            int purposePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String purpose = stringDeserialization(
                    buffer, purposePosition);

            boolean hasKeywords = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<String> keywords = null;
            if (hasKeywords) {
                int keywordsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                keywords = listStringDeserialization(buffer, keywordsPosition);
            }

            int usePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String use = stringDeserialization(buffer, usePosition);

            int misusePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String misuse = stringDeserialization(buffer, misusePosition);

            int copyrightPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String copyright = stringDeserialization(
                    buffer, copyrightPosition);

            boolean hasOriginalResourceUri = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Map<String, String> originalResourceUri = null;
            if (hasOriginalResourceUri) {
                int originalResourceUriPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                originalResourceUri = mapStringDeserialization(
                        buffer, originalResourceUriPosition);
            }

            boolean hasOtherDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Map<String, String> otherDetails = null;
            if (hasOtherDetails) {
                int otherDetailsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                otherDetails = mapStringDeserialization(
                        buffer, otherDetailsPosition);
            }

            return RMObjectFactory.newResourceDescriptionItem(language, purpose,
                    keywords, use, misuse, copyright, originalResourceUri,
                    otherDetails);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<ResourceDescriptionItem> items)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = items.size();
            int position = offset + (listSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();

            meta = writeHeader(buffer, meta, listSize);
            ResourceDescriptionItemSerializer rdis
                    = new ResourceDescriptionItemSerializer();

            for (ResourceDescriptionItem d : items) {
                meta = writeHeader(buffer, meta, position);
                position = rdis.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<ResourceDescriptionItem> deserializeList(Buffer buffer,
                int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<ResourceDescriptionItem> list = new ArrayList<>();
            ResourceDescriptionItemSerializer rdis
                    = new ResourceDescriptionItemSerializer();

            for (int i = 0; i < listSize; i++) {
                int resourceDescriptionItemPosition
                        = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                ResourceDescriptionItem t = rdis.deserialize(
                        buffer, resourceDescriptionItemPosition);
                list.add(t);
            }

            return list;
        }
    }

    public static class TranslationDetailsSerializer {

        protected int serialize(Buffer buffer, int offset, CodePhrase language,
                Map<String, String> author, String accreditation,
                Map<String, String> otherDetails)
                throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize() + 2 * PrimitiveTypeSize.BOOLEAN.getSize();
            CodePhraseSerializer cps = new CodePhraseSerializer();

            if (language == null) {
                throw new IllegalArgumentException("null language");
            }
            if (author == null) {
                throw new IllegalArgumentException("null author");
            }

            meta = writeHeader(buffer, meta, position);
            position = cps.serialize(buffer, position, language);

            meta = writeHeader(buffer, meta, position);
            position = mapStringSerialization(buffer, position, author);

            boolean hasAccreditation = accreditation != null;
            if (hasAccreditation) {
                meta = writeHeader(buffer, meta, hasAccreditation, position);
                position = stringSerialization(
                        buffer, position, accreditation);
            } else {
                meta = writeHeader(buffer, meta, hasAccreditation);
            }

            boolean hasOtherDetails = otherDetails != null;
            if (hasOtherDetails) {
                writeHeader(buffer, meta, hasOtherDetails, position);
                position = mapStringSerialization(buffer, position,
                        otherDetails);
            } else {
                writeHeader(buffer, meta, hasOtherDetails);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                TranslationDetails td) throws UnsupportedEncodingException {
            TranslationDetailsSerializer tdss
                    = new TranslationDetailsSerializer();

            int position = offset;
            position = tdss.serialize(
                    buffer, position, td.getLanguage(), td.getAuthor(),
                    td.getAccreditation(), td.getOtherDetails());

            return position;
        }

        protected TranslationDetails deserialize(Buffer buffer, int offset) {
            int position = offset;
            CodePhraseSerializer cps = new CodePhraseSerializer();

            int languagePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CodePhrase language = cps.deserialize(buffer, languagePosition);

            int authorPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Map<String, String> author = mapStringDeserialization(
                    buffer, authorPosition);

            boolean hasAccreditation = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            String accreditation = null;
            if (hasAccreditation) {
                int accreditationPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                accreditation = stringDeserialization(
                        buffer, accreditationPosition);
            }

            boolean hasOtherDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Map<String, String> otherDetails = null;
            if (hasOtherDetails) {
                int otherDetailsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                otherDetails = mapStringDeserialization(
                        buffer, otherDetailsPosition);
            }

            return RMObjectFactory.newTranslationDetails(language, author,
                    accreditation, otherDetails);
        }

        protected int mapSerialization(Buffer buffer, int offset,
                Map<String, TranslationDetails> map)
                throws UnsupportedEncodingException {
            int meta = offset;
            int mapSize = map.size();
            int position = offset
                    + mapSize * (2 * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();
            TranslationDetailsSerializer tdss
                    = new TranslationDetailsSerializer();

            meta = writeHeader(buffer, meta, mapSize);
            if (mapSize == 0) {
                return meta;
            }

            for (Map.Entry<String, TranslationDetails> entry : map.entrySet()) {
                String key = entry.getKey();
                TranslationDetails value = entry.getValue();

                meta = writeHeader(buffer, meta, position);
                position = stringSerialization(buffer, position, key);
                meta = writeHeader(buffer, meta, position);
                position = tdss.serialize(buffer, position, value);
            }

            return position;
        }

        protected Map<String, TranslationDetails> mapDeserialization(
                Buffer buffer, int offset) {
            int position = offset;
            int mapSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            TranslationDetailsSerializer tdss
                    = new TranslationDetailsSerializer();
            Map<String, TranslationDetails> map = new HashMap<>();
            if (mapSize == 0) {
                return map;
            }

            for (int i = 0; i < mapSize; i++) {
                int keyPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                int valuePosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                String key = stringDeserialization(buffer, keyPosition);
                TranslationDetails value = tdss.deserialize(
                        buffer, valuePosition);

                map.put(key, value);
            }

            return map;
        }
    }

    public static class ItemSerializer {

        protected int serialize(Buffer buffer, int offset,
                Locatable locatable) throws UnsupportedEncodingException {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();

            position = ls.serialize(buffer, position, locatable);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Item item) throws UnsupportedEncodingException {
            int position = offset;
            ItemSerializer is = new ItemSerializer();

            position = is.serialize(buffer, position, item.getLocatable());

            return position;
        }

        protected Item deserialize(Buffer buffer, int offset) {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();

            Locatable locatable = ls.deserialize(buffer, position);

            return RMObjectFactory.newItem(locatable);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<Item> items)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = items.size();
            int position = offset + (listSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();

            meta = writeHeader(buffer, meta, listSize);
            ItemSerializer is = new ItemSerializer();

            for (Item d : items) {
                meta = writeHeader(buffer, meta, position);
                position = is.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<Item> deserializeList(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Item> list = new ArrayList<>();
            ItemSerializer is = new ItemSerializer();

            for (int i = 0; i < listSize; i++) {
                int dvItemPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                Item t = is.deserialize(buffer, dvItemPosition);
                list.add(t);
            }

            return list;
        }
    }

    public static class ClusterSerializer {

        protected int serialize(Buffer buffer, int offset, Item item,
                List<Item> items) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            ItemSerializer is = new ItemSerializer();

            meta = writeHeader(buffer, meta, position);
            position = is.serialize(buffer, position, item);

            writeHeader(buffer, meta, position);
            position = is.listSerialize(buffer, position, items);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Cluster cluster) throws UnsupportedEncodingException {
            int position = offset;
            ClusterSerializer cs = new ClusterSerializer();

            position = cs.serialize(buffer, position,
                    cluster.getItem(), cluster.getItems());

            return position;
        }

        protected Cluster deserialize(Buffer buffer, int offset) {
            int position = offset;
            ItemSerializer is = new ItemSerializer();

            int itemPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            Item item = is.deserialize(buffer, itemPosition);

            int itemsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Item> items = is.deserializeList(buffer, itemsPosition);

            return RMObjectFactory.newCluster(item, items);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<Cluster> items)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = items.size();
            int position = offset + (listSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();

            meta = writeHeader(buffer, meta, listSize);
            ClusterSerializer cs = new ClusterSerializer();

            for (Cluster d : items) {
                meta = writeHeader(buffer, meta, position);
                position = cs.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<Cluster> deserializeList(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Cluster> list = new ArrayList<>();
            ClusterSerializer es = new ClusterSerializer();

            for (int i = 0; i < listSize; i++) {
                int clusterPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                Cluster t = es.deserialize(buffer, clusterPosition);
                list.add(t);
            }

            return list;
        }
    }

    public static class ElementSerializer {

        protected int serialize(Buffer buffer, int offset, Item item,
                DvCodedText nullFlavour) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            ItemSerializer is = new ItemSerializer();
            DvCodedTextSerializer dts = new DvCodedTextSerializer();

            meta = writeHeader(buffer, meta, position);
            position = is.serialize(buffer, position, item);

            writeHeader(buffer, meta, position);
            position = dts.serialize(buffer, position, nullFlavour);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Element element) throws UnsupportedEncodingException {
            int position = offset;
            ElementSerializer es = new ElementSerializer();

            position = es.serialize(buffer, position,
                    element.getItem(), element.getNullFlavour());

            return position;
        }

        protected Element deserialize(Buffer buffer, int offset) {
            int position = offset;
            ItemSerializer is = new ItemSerializer();
            DvCodedTextSerializer dts = new DvCodedTextSerializer();

            int itemPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Item item = is.deserialize(buffer, itemPosition);

            int nullFlavourPosition = buffer.readInteger(position);
            DvCodedText nullFlavour = dts.deserialize(
                    buffer, nullFlavourPosition);

            return RMObjectFactory.newElement(item, nullFlavour);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<Element> items)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = items.size();
            int position = offset + (listSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();

            meta = writeHeader(buffer, meta, listSize);
            ElementSerializer is = new ElementSerializer();

            for (Element d : items) {
                meta = writeHeader(buffer, meta, position);
                position = is.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<Element> deserializeList(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Element> list = new ArrayList<>();
            ElementSerializer es = new ElementSerializer();

            for (int i = 0; i < listSize; i++) {
                int elementPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                Element t = es.deserialize(buffer, elementPosition);
                list.add(t);
            }

            return list;
        }
    }

    public static class DataStructureSerializer {

        protected int serialize(Buffer buffer, int offset,
                Locatable locatable) throws UnsupportedEncodingException {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();

            position = ls.serialize(buffer, position, locatable);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                DataStructure ds) throws UnsupportedEncodingException {
            int position = offset;
            DataStructureSerializer dss = new DataStructureSerializer();

            position = dss.serialize(buffer, position, ds.getLocatable());

            return position;
        }

        protected DataStructure deserialize(Buffer buffer, int offset) {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();

            Locatable locatable = ls.deserialize(buffer, position);

            return RMObjectFactory.newDataStructure(locatable);
        }
    }

    public static class ItemListSerializer {

        protected int serialize(Buffer buffer, int offset, UIDBasedID uid,
                String archetypeNodeId, DvText name,
                Archetyped archetypeDetails, FeederAudit feederAudit,
                Set<Link> links,
                List<Element> items) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + (7 * PrimitiveTypeSize.INT.getSize()) + 5 * PrimitiveTypeSize.BOOLEAN.getSize();

            UIDBasedIDSerializer us = new UIDBasedIDSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            FeederAuditSerializer fas = new FeederAuditSerializer();
            ArchetypedSerializer as = new ArchetypedSerializer();
            LinkSerializer ls = new LinkSerializer();
            ElementSerializer es = new ElementSerializer();

            boolean hasUid = uid != null;
            if (hasUid) {
                meta = writeHeader(buffer, meta, hasUid, position);
                position = us.serialize(buffer, position, uid);
            } else {
                meta = writeHeader(buffer, meta, hasUid);
            }

            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(
                    buffer, position, archetypeNodeId);

            meta = writeHeader(buffer, meta, position);
            position = dts.serialize(buffer, position, name);

            boolean hasArchetypeDetails = archetypeDetails != null;
            if (hasArchetypeDetails) {
                meta = writeHeader(buffer, meta, hasArchetypeDetails, position);
                position = as.serialize(buffer, position, archetypeDetails);
            } else {
                meta = writeHeader(buffer, meta, hasArchetypeDetails);
            }

            boolean hasFeederAudit = feederAudit != null;
            if (hasFeederAudit) {
                meta = writeHeader(buffer, meta, hasFeederAudit, position);
                position = fas.serialize(buffer, position, feederAudit);
            } else {
                meta = writeHeader(buffer, meta, hasFeederAudit);
            }

            boolean hasLinks = links != null;
            if (hasLinks) {
                meta = writeHeader(buffer, meta, hasLinks, position);
                position = ls.setSerializer(buffer, position, links);
            } else {
                meta = writeHeader(buffer, meta, hasLinks);
            }

            boolean hasItems = items != null;
            if (hasItems) {
                writeHeader(buffer, meta, hasItems, position);
                position = es.listSerialize(buffer, position, items);
            } else {
                writeHeader(buffer, meta, hasItems);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ItemList il) throws UnsupportedEncodingException {
            ItemListSerializer is = new ItemListSerializer();
            int position = offset;

            position = is.serialize(buffer, position, il.getUid(),
                    il.getArchetypeNodeId(), il.getName(),
                    il.getArchetypeDetails(), il.getFeederAudit(),
                    il.getLinks(), il.getItems());

            return position;
        }

        protected ItemList deserialize(Buffer buffer, int offset) {
            int position = offset;

            UIDBasedIDSerializer us = new UIDBasedIDSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            FeederAuditSerializer fas = new FeederAuditSerializer();
            ArchetypedSerializer as = new ArchetypedSerializer();
            LinkSerializer ls = new LinkSerializer();
            ElementSerializer es = new ElementSerializer();

            boolean hasUId = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            UIDBasedID uid = null;
            if (hasUId) {
                int uidPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                uid = us.deserialize(buffer, uidPosition);
            }

            int archetypeNodeIdPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String archetypeNodeId = stringDeserialization(
                    buffer, archetypeNodeIdPosition);

            int namePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvText name = dts.deserialize(buffer, namePosition);

            boolean hasArchetypeDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Archetyped archetypeDetails = null;
            if (hasArchetypeDetails) {
                int archetypeDetailsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                archetypeDetails = as.deserialize(
                        buffer, archetypeDetailsPosition);
            }

            boolean hasFeederAudit = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            FeederAudit feederAudit = null;
            if (hasFeederAudit) {
                int feederAuditPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                feederAudit = fas.deserialize(buffer, feederAuditPosition);
            }

            boolean hasLinks = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Set<Link> links = null;
            if (hasLinks) {
                int linksPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                links = ls.setDeserializer(buffer, linksPosition);
            }

            boolean hasItems = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<Element> items = null;
            if (hasItems) {
                int itemsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                items = es.deserializeList(buffer, itemsPosition);
            }

            return RMObjectFactory.newItemList(uid, archetypeNodeId, name,
                    archetypeDetails, feederAudit, links, items);
        }
    }

    public static class ItemStructureSerializer {

        protected int serialize(Buffer buffer, int offset,
                DataStructure ds) throws UnsupportedEncodingException {
            int position = offset;
            DataStructureSerializer dss = new DataStructureSerializer();
            position = dss.serialize(buffer, position, ds);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ItemStructure is) throws UnsupportedEncodingException {
            int position = offset;
            ItemStructureSerializer iss = new ItemStructureSerializer();
            position = iss.serialize(buffer, position, is.getDataStructure());

            return position;
        }

        protected ItemStructure deserialize(Buffer buffer, int offset) {
            int position = offset;
            DataStructureSerializer dss = new DataStructureSerializer();

            DataStructure ds = dss.deserialize(buffer, position);
            return RMObjectFactory.newItemStructure(ds);
        }
    }

    public static class ItemSingleSerializer {

        protected int serialize(Buffer buffer, int offset, ItemStructure is,
                Element item) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            ItemStructureSerializer iss = new ItemStructureSerializer();
            ElementSerializer es = new ElementSerializer();

            meta = writeHeader(buffer, meta, position);
            position = iss.serialize(buffer, position, is);

            writeHeader(buffer, meta, position);
            position = es.serialize(buffer, position, item);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ItemSingle is) throws UnsupportedEncodingException {
            int position = offset;
            ItemSingleSerializer iss = new ItemSingleSerializer();

            position = iss.serialize(buffer, position, is.getItemStructure(),
                    is.getItem());

            return position;
        }

        protected ItemSingle deserialize(Buffer buffer, int offset) {
            int position = offset;

            ItemStructureSerializer iss = new ItemStructureSerializer();
            ElementSerializer es = new ElementSerializer();

            int itemStructurePosition = buffer.readInteger(position);
            ItemStructure is = iss.deserialize(buffer, itemStructurePosition);
            position += PrimitiveTypeSize.INT.getSize();

            int itemPosition = buffer.readInteger(position);
            Element item = es.deserialize(buffer, itemPosition);
            position += PrimitiveTypeSize.INT.getSize();

            return RMObjectFactory.newItemSingle(is, item);
        }
    }

    public static class ItemTableSerializer {

        protected int serialize(Buffer buffer, int offset, ItemStructure is,
                List<Cluster> rows) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ClusterSerializer cs = new ClusterSerializer();

            meta = writeHeader(buffer, meta, position);
            position = iss.serialize(buffer, position, is);

            writeHeader(buffer, meta, position);
            position = cs.listSerialize(buffer, position, rows);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ItemTable it) throws UnsupportedEncodingException {
            int position = offset;
            ItemTableSerializer its = new ItemTableSerializer();

            position = its.serialize(buffer, position,
                    it.getItemStructure(), it.getRows());

            return position;
        }

        protected ItemTable deserialize(Buffer buffer, int offset) {
            int position = offset;
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ClusterSerializer cs = new ClusterSerializer();

            int itemStructurePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemStructure is = iss.deserialize(buffer, itemStructurePosition);

            int rowsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            List<Cluster> rows = cs.deserializeList(buffer, rowsPosition);

            return RMObjectFactory.newItemTable(is, rows);
        }
    }

    public static class ItemTreeSerializer {

        protected int serialize(Buffer buffer, int offset, ItemStructure is,
                List<Item> items) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize() + PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ItemSerializer isr = new ItemSerializer();

            meta = writeHeader(buffer, meta, position);
            position = iss.serialize(buffer, position, is);

            boolean hasItems = items != null;
            if (hasItems) {
                writeHeader(buffer, meta, hasItems, position);
                position = isr.listSerialize(buffer, position, items);
            } else {
                writeHeader(buffer, meta, hasItems);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ItemTree it) throws UnsupportedEncodingException {
            int position = offset;
            ItemTreeSerializer its = new ItemTreeSerializer();

            position = its.serialize(buffer, position, it);

            return position;
        }

        protected ItemTree deserialize(Buffer buffer, int offset) {
            int position = offset;
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ItemSerializer isr = new ItemSerializer();

            int itemStructurePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemStructure is = iss.deserialize(buffer, itemStructurePosition);

            boolean hasItems = buffer.readBoolean(position);
            List<Item> items = null;
            if (hasItems) {
                int itemsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                items = isr.deserializeList(buffer, itemsPosition);
            }

            return RMObjectFactory.newItemTree(is, items);
        }
    }

    public static class PartyIdentitySerializer {

        protected int serialize(Buffer buffer, int offset, Locatable locatable,
                ItemStructure details) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            meta = writeHeader(buffer, meta, position);
            position = ls.serialize(buffer, position, locatable);

            writeHeader(buffer, meta, position);
            position = iss.serialize(buffer, position, details);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartyIdentity pi) throws UnsupportedEncodingException {
            int position = offset;
            PartyIdentitySerializer pis = new PartyIdentitySerializer();

            position = pis.serialize(buffer, position,
                    pi.getLocatable(), pi.getDetails());

            return position;
        }

        protected PartyIdentity deserialize(Buffer buffer, int offset) {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            int detailsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemStructure details = iss.deserialize(buffer, detailsPosition);

            return RMObjectFactory.newPartyIdentity(locatable, details);
        }

        protected int setSerializer(Buffer buffer, int offset,
                Set<PartyIdentity> piSet) throws UnsupportedEncodingException {
            int setSize = piSet.size();
            int position = offset + (setSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();
            int meta = offset;
            PartyIdentitySerializer pis = new PartyIdentitySerializer();

            meta = writeHeader(buffer, meta, setSize);
            Iterator<PartyIdentity> it = piSet.iterator();

            while (it.hasNext()) {
                PartyIdentity pi = it.next();
                int linkPosition = position;
                meta = writeHeader(buffer, meta, linkPosition);
                position = pis.serialize(buffer, position, pi);
            }

            return position;
        }

        protected Set<PartyIdentity> setDeserializer(Buffer buffer,
                int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            PartyIdentitySerializer pis = new PartyIdentitySerializer();
            Set<PartyIdentity> piSet = new HashSet<>();

            for (int i = 0; i < listSize; i++) {
                int piPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                PartyIdentity pi = pis.deserialize(buffer, piPosition);
                piSet.add(pi);
            }

            return piSet;
        }
    }

    public static class PartyRelationshipSerializer {

        protected int serialize(Buffer buffer, int offset, Locatable locatable,
                ItemStructure details, ObjectRef source,
                ObjectRef target) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();

            meta = writeHeader(buffer, meta, position);
            position = ls.serialize(buffer, position, locatable);

            meta = writeHeader(buffer, meta, position);
            position = iss.serialize(buffer, position, details);

            meta = writeHeader(buffer, meta, position);
            position = ors.serialize(buffer, position, source);

            writeHeader(buffer, meta, position);
            position = ors.serialize(buffer, position, target);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartyRelationship pr) throws UnsupportedEncodingException {
            PartyRelationshipSerializer prs = new PartyRelationshipSerializer();
            int position = offset;
            position = prs.serialize(buffer, position, pr);

            return position;
        }

        protected PartyRelationship deserialize(Buffer buffer, int offset) {

            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            int detailsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemStructure details = iss.deserialize(buffer, detailsPosition);

            //DvInterval - TODO
            int sourcePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ObjectRef source = ors.deserialize(buffer, sourcePosition);

            int targetPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ObjectRef target = ors.deserialize(buffer, targetPosition);

            return RMObjectFactory.newPartyRelationship(locatable, details,
                    source, target);
        }

        protected int setSerializer(Buffer buffer, int offset,
                Set<PartyRelationship> prSet)
                throws UnsupportedEncodingException {
            int setSize = prSet.size();
            int position = offset + (setSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();
            int meta = offset;
            PartyRelationshipSerializer pis = new PartyRelationshipSerializer();

            meta = writeHeader(buffer, meta, setSize);
            Iterator<PartyRelationship> it = prSet.iterator();

            while (it.hasNext()) {
                PartyRelationship pr = it.next();
                int linkPosition = position;
                meta = writeHeader(buffer, meta, linkPosition);
                position = pis.serialize(buffer, position, pr);
            }

            return position;
        }

        protected Set<PartyRelationship> setDeserializer(Buffer buffer,
                int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            PartyRelationshipSerializer prs = new PartyRelationshipSerializer();
            Set<PartyRelationship> prSet = new HashSet<>();

            for (int i = 0; i < listSize; i++) {
                int prPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                PartyRelationship pr = prs.deserialize(buffer, prPosition);
                prSet.add(pr);
            }

            return prSet;
        }
    }

    public static class AddressSerializer {

        protected int serialize(Buffer buffer, int offset, Locatable locatable,
                ItemStructure details) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            meta = writeHeader(buffer, meta, position);
            position = ls.serialize(buffer, position, locatable);

            writeHeader(buffer, meta, position);
            position = iss.serialize(buffer, position, details);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Address a) throws UnsupportedEncodingException {
            int position = offset;
            AddressSerializer as = new AddressSerializer();

            position = as.serialize(buffer, position, a);

            return position;
        }

        protected Address deserialize(Buffer buffer, int offset) {
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            int detailsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemStructure details = iss.deserialize(buffer, detailsPosition);

            return RMObjectFactory.newAddress(locatable, details);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<Address> items)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = items.size();
            int position = offset + (listSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();

            meta = writeHeader(buffer, meta, listSize);
            AddressSerializer dis = new AddressSerializer();

            for (Address d : items) {
                meta = writeHeader(buffer, meta, position);
                position = dis.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<Address> deserializeList(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Address> list = new ArrayList<>();
            AddressSerializer as = new AddressSerializer();

            for (int i = 0; i < listSize; i++) {
                int addressPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                Address a = as.deserialize(buffer, addressPosition);
                list.add(a);
            }

            return list;
        }
    }

    public static class ContactSerializer {

        protected int serialize(Buffer buffer, int offset, Locatable locatable,
                List<Address> addresses) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            AddressSerializer as = new AddressSerializer();

            meta = writeHeader(buffer, meta, position);
            position = ls.serialize(buffer, position, locatable);

            //DvInterval<DvDate> timeValidity todo
            writeHeader(buffer, meta, position);
            position = as.listSerialize(buffer, position, addresses);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Contact c) throws UnsupportedEncodingException {
            int position = offset;
            ContactSerializer cs = new ContactSerializer();

            position = cs.serialize(buffer, position, c);

            return position;
        }

        protected Contact deserialize(Buffer buffer, int offset) {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();
            AddressSerializer as = new AddressSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            //DvInterval<DvDate> timeValidity,
            int addressesPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            List<Address> addresses = as.deserializeList(
                    buffer, addressesPosition);

            return RMObjectFactory.newContact(locatable, addresses);
        }

        protected int setSerializer(Buffer buffer, int offset,
                Set<Contact> contacts) throws UnsupportedEncodingException {
            int setSize = contacts.size();
            int position = offset + (setSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();
            int meta = offset;
            ContactSerializer cs = new ContactSerializer();

            meta = writeHeader(buffer, meta, setSize);
            Iterator<Contact> it = contacts.iterator();

            while (it.hasNext()) {
                Contact c = it.next();
                int linkPosition = position;
                meta = writeHeader(buffer, meta, linkPosition);
                position = cs.serialize(buffer, position, c);
            }

            return position;
        }

        protected Set<Contact> setDeserializer(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            ContactSerializer cs = new ContactSerializer();
            Set<Contact> contacts = new HashSet<>();

            for (int i = 0; i < listSize; i++) {
                int prPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                Contact c = cs.deserialize(buffer, prPosition);
                contacts.add(c);
            }

            return contacts;
        }
    }

    public static class PartySerializer {

        protected int serialize(Buffer buffer, int offset, Locatable locatable,
                Set<PartyIdentity> identities, Set<Contact> contacts,
                Set<PartyRelationship> relationships,
                Set<LocatableRef> reverseRelationships,
                ItemStructure details) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 6 * PrimitiveTypeSize.INT.getSize() + PrimitiveTypeSize.BOOLEAN.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            ContactSerializer cs = new ContactSerializer();
            PartyIdentitySerializer pis = new PartyIdentitySerializer();
            LocatableRefSerializer lrs = new LocatableRefSerializer();
            PartyRelationshipSerializer prs = new PartyRelationshipSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            meta = writeHeader(buffer, meta, position);
            position = ls.serialize(buffer, position, locatable);
            
            meta = writeHeader(buffer, meta, position);
            position = pis.setSerializer(buffer, position, identities);

            meta = writeHeader(buffer, meta, position);
            position = cs.setSerializer(buffer, position, contacts);

            meta = writeHeader(buffer, meta, position);
            position = prs.setSerializer(buffer, position, relationships);

            meta = writeHeader(buffer, meta, position);
            position = lrs.setSerializer(
                    buffer, position, reverseRelationships);

            boolean hasDetails = details != null;
            if (hasDetails) {
                writeHeader(buffer, meta, hasDetails, position);
                position = iss.serialize(buffer, position, details);
            } else {
                writeHeader(buffer, meta, hasDetails);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Party p) throws UnsupportedEncodingException {
            int position = offset;
            PartySerializer ps = new PartySerializer();

            position = ps.serialize(buffer, position, p);
            return position;
        }

        protected Party deserialize(Buffer buffer, int offset) {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();
            ContactSerializer cs = new ContactSerializer();
            PartyIdentitySerializer pis = new PartyIdentitySerializer();
            LocatableRefSerializer lrs = new LocatableRefSerializer();
            PartyRelationshipSerializer prs = new PartyRelationshipSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            
            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);
            
            int identitiesPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Set<PartyIdentity> identities = pis.setDeserializer(
                    buffer, identitiesPosition);
            
            int contactsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Set<Contact > contacts = cs.setDeserializer(
                    buffer, contactsPosition);
            
            int relationshipsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Set<PartyRelationship> relationships = prs.setDeserializer(
                    buffer, relationshipsPosition);
            
            int reverseRelationshipsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Set<LocatableRef> reverseRelationships = lrs.setDeserializer(
                    buffer, reverseRelationshipsPosition);
            
            boolean hasDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructure details = null;
            if(hasDetails){
                int detailsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                details = iss.deserialize(buffer, detailsPosition);
            }
            
            return RMObjectFactory.newParty(locatable, identities, 
                    contacts, relationships, reverseRelationships, details);
        }
    }
    
    public static class CapabilitySerializer {

        protected int serialize(Buffer buffer, int offset, Locatable credentials,
                ItemStructure details) throws UnsupportedEncodingException {
            int meta = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            meta = writeHeader(buffer, meta, position);
            position = ls.serialize(buffer, position, credentials);

            writeHeader(buffer, meta, position);
            position = iss.serialize(buffer, position, details);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Capability c) throws UnsupportedEncodingException {
            int position = offset;
            CapabilitySerializer cs = new CapabilitySerializer();

            position = cs.serialize(buffer, position, c);

            return position;
        }

        protected Capability deserialize(Buffer buffer, int offset) {
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            int credentialsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemStructure credentials = iss.deserialize(
                    buffer, credentialsPosition);

            return RMObjectFactory.newCapability(locatable, credentials);
        }
        
        protected int listSerialize(
                Buffer buffer, int offset, List<Capability> items)
                throws UnsupportedEncodingException {
            int meta = offset;
            int listSize = items.size();
            int position = offset + (listSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();

            meta = writeHeader(buffer, meta, listSize);
            CapabilitySerializer cs = new CapabilitySerializer();

            for (Capability c : items) {
                meta = writeHeader(buffer, meta, position);
                position = cs.serialize(buffer, position, c);
            }

            return position;
        }

        protected List<Capability> deserializeList(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Capability> list = new ArrayList<>();
            CapabilitySerializer cs = new CapabilitySerializer();

            for (int i = 0; i < listSize; i++) {
                int capabilityPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                Capability c = cs.deserialize(buffer, capabilityPosition);
                list.add(c);
            }

            return list;
        }
    }
    
    public static class RoleSerializer {
        protected int serialize(Buffer buffer, int offset, Party party, 
                List<Capability> capabilities, 
                PartyRef performer) throws UnsupportedEncodingException{
            int meta = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize() + 2 * PrimitiveTypeSize.BOOLEAN.getSize();
            
            PartySerializer ps = new PartySerializer();
            CapabilitySerializer cs = new CapabilitySerializer();
            PartyRefSerializer prs = new PartyRefSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = ps.serialize(buffer, position, party);
            
            boolean hasCapabilities = capabilities != null;
            if(hasCapabilities){
                meta = writeHeader(buffer, meta, hasCapabilities, position);
                position = cs.listSerialize(buffer, position, capabilities);
            } else {
                meta = writeHeader(buffer, meta, hasCapabilities);
            }
            
            //TODO DvInterval<DvDate> timeValidity
            
            writeHeader(buffer, meta, position);
            position = prs.serialize(buffer, position, performer);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Role r) throws UnsupportedEncodingException {
            int position = offset;
            RoleSerializer rs = new RoleSerializer();
            
            position = rs.serialize(buffer, position, r);
            
            return position;
        }
        
        protected Role deserialize(Buffer buffer, int offset){
            int position = offset;
            PartySerializer ps = new PartySerializer();
            CapabilitySerializer cs = new CapabilitySerializer();
            PartyRefSerializer prs = new PartyRefSerializer();
            
            int partyPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Party party = ps.deserialize(buffer, partyPosition);
            
            boolean hasCapabilities = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<Capability> capabilities = null;
            if(hasCapabilities){
                int capabilitiesPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                capabilities = cs.deserializeList(buffer, capabilitiesPosition);
            }
            
            //TODO DvInterval<DvDate> timeValidity
            
            int performerPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            PartyRef performer = prs.deserialize(buffer, performerPosition);
            
            return RMObjectFactory.newRole(party, capabilities, performer);
        }
        
        protected int setSerializer(Buffer buffer, int offset,
                Set<Role> roles) throws UnsupportedEncodingException {
            int setSize = roles.size();
            int position = offset + (setSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();
            int meta = offset;
            RoleSerializer rs = new RoleSerializer();

            meta = writeHeader(buffer, meta, setSize);
            Iterator<Role> it = roles.iterator();

            while (it.hasNext()) {
                Role role = it.next();
                int linkPosition = position;
                meta = writeHeader(buffer, meta, linkPosition);
                position = rs.serialize(buffer, position, role);
            }

            return position;
        }

        protected Set<Role> setDeserializer(Buffer buffer, int offset) {
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            RoleSerializer rs = new RoleSerializer();
            Set<Role> roles = new HashSet<>();

            for (int i = 0; i < listSize; i++) {
                int rPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                Role role = rs.deserialize(buffer, rPosition);
                roles.add(role);
            }

            return roles;
        }
    }
    
    public static class ActorSerializer {
        protected int serialize(Buffer buffer, int offset, Party party, 
                Set<Role> roles, 
                Set<DvText> languages) throws UnsupportedEncodingException{
            int meta = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();
            
            PartySerializer ps = new PartySerializer();
            RoleSerializer rs = new RoleSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = ps.serialize(buffer, position, party);
            
            meta = writeHeader(buffer, meta, position);
            position = rs.setSerializer(buffer, position, roles);
            
            writeHeader(buffer, meta, position);
            position = dts.setSerializer(buffer, position, languages);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Actor a) throws UnsupportedEncodingException {
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            position = as.serialize(buffer, position, a);
            
            return position;
        }
        
        protected Actor deserialize(Buffer buffer, int offset){
            int position = offset;
            
            PartySerializer ps = new PartySerializer();
            RoleSerializer rs = new RoleSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            
            int partyPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Party party = ps.deserialize(buffer, partyPosition);
            
            int rolesPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Set<Role> roles = rs.setDeserializer(buffer, rolesPosition);
            
            int languagesPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Set<DvText> languages = dts.setDeserializer(
                    buffer, languagesPosition);
            
            return RMObjectFactory.newActor(party, roles, languages);
        }
    }
    
    public static class AgentSerializer {
        protected int serialize(Buffer buffer, int offset, 
                Actor actor) throws UnsupportedEncodingException {
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            position = as.serialize(buffer, position, actor);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Agent agent) throws UnsupportedEncodingException {
            int position = offset;
            AgentSerializer as = new AgentSerializer();
            
            position = as.serialize(buffer, position, agent);
            
            return position;
        }
        
        protected Agent deserialize(Buffer buffer, int offset){
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            Actor actor = as.deserialize(buffer, position);
            
            return RMObjectFactory.newAgent(actor);
        }
    }
    
    public static class GroupSerializer {
        protected int serialize(Buffer buffer, int offset, 
                Actor actor) throws UnsupportedEncodingException {
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            position = as.serialize(buffer, position, actor);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Group group) throws UnsupportedEncodingException {
            int position = offset;
            GroupSerializer gs = new GroupSerializer();
            
            position = gs.serialize(buffer, position, group);
            
            return position;
        }
        
        protected Group deserialize(Buffer buffer, int offset){
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            Actor a = as.deserialize(buffer, position);
            
            return RMObjectFactory.newGroup(a);
        } 
    }
    
    public static class OrganisationSerializer {
        protected int serialize(Buffer buffer, int offset, 
                Actor actor) throws UnsupportedEncodingException {
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            position = as.serialize(buffer, position, actor);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Organisation organisation) throws UnsupportedEncodingException {
            int position = offset;
            OrganisationSerializer os = new OrganisationSerializer();
            
            position = os.serialize(buffer, position, organisation);
            
            return position;
        }
        
        protected Organisation deserialize(Buffer buffer, int offset){
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            Actor actor = as.deserialize(buffer, position);
            
            return RMObjectFactory.newOrganisation(actor);
        } 
    }
    
    public static class PersonSerializer {
        protected int serialize(Buffer buffer, int offset, 
                Actor a) throws UnsupportedEncodingException {
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            position = as.serialize(buffer, position, a);
            
            return position;
        }
        
         protected int serialize(Buffer buffer, int offset, 
                Person p) throws UnsupportedEncodingException {
            int position = offset;
            PersonSerializer ps = new PersonSerializer();
            
            position = ps.serialize(buffer, position, p);
            
            return position;
        }
         
         protected Person deserialize(Buffer buffer, int offset){
             int position = offset;
             ActorSerializer as = new ActorSerializer();
             
             int actorPosition = buffer.readInteger(position);
             
             Actor a = as.deserialize(buffer, actorPosition);
             
             return RMObjectFactory.newPerson(a);
         }
    }
    
    public static class InstructionDetailsSerializer {
        protected int serialize(Buffer buffer, int offset, 
                LocatableRef instructionId, String activityId, 
                ItemStructure wfDetails)  throws UnsupportedEncodingException{
            int meta = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize() + PrimitiveTypeSize.BOOLEAN.getSize();
            
            LocatableRefSerializer lrs = new LocatableRefSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = lrs.serialize(buffer, position, instructionId);
            
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, activityId);
            
            boolean hasWfDetails = wfDetails != null;
            if(hasWfDetails){
                writeHeader(buffer, meta, hasWfDetails, position);
                position = iss.serialize(buffer, position, wfDetails);
            } else {
                 writeHeader(buffer, meta, hasWfDetails);
            }
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                InstructionDetails id)  throws UnsupportedEncodingException{
            int position = offset;
            InstructionDetailsSerializer ids = 
                    new InstructionDetailsSerializer();
            
            position = ids.serialize(buffer, position, 
                    id.getInstructionId(), id.getActivityId(), 
                    id.getWfDetails());
            
            return position;
        }
        
        protected InstructionDetails deserialize(Buffer buffer, int offset){
            int position = offset;
            LocatableRefSerializer lrs = new LocatableRefSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            
            int instructionIdPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            LocatableRef instructionId = lrs.deserialize(
                    buffer, instructionIdPosition);
            
            int activityIdPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String activityId = stringDeserialization(
                    buffer, activityIdPosition);
            
            boolean hasWfDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructure wfDetails = null;
            if(hasWfDetails){
                int wfDetailsPosition = buffer.readInteger(position);
                wfDetails = iss.deserialize(buffer, wfDetailsPosition);
            }
            
            return RMObjectFactory.newInstructionDetails(instructionId, 
                    activityId, wfDetails);
        }
    }
    
    public static class ISMTransitionSerializer {
        protected int serialize(Buffer buffer, int offset, 
                DvCodedText currentState, DvCodedText transition, 
                DvCodedText careflowStep) throws UnsupportedEncodingException{
            int meta = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize() + 2 * PrimitiveTypeSize.BOOLEAN.getSize();
            
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = dcs.serialize(buffer, position, currentState);
            
            boolean hasTransition = transition != null;
            if(hasTransition){
                meta = writeHeader(buffer, meta, hasTransition, position);
                position = dcs.serialize(buffer, position, transition);
            } else {
                meta = writeHeader(buffer, meta, hasTransition);
            }
            
            boolean hasCareflowStep = careflowStep != null;
            if(hasCareflowStep){
                writeHeader(buffer, meta, hasCareflowStep, position);
                position = dcs.serialize(buffer, position, careflowStep);
            } else {
                 writeHeader(buffer, meta, hasCareflowStep);
            }
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                ISMTransition ism) throws UnsupportedEncodingException{
            int position = offset;
            ISMTransitionSerializer is = new ISMTransitionSerializer();
            
            position = is.serialize(buffer, position, ism.getCurrentState(), 
                    ism.getTransition(), ism.getCareflowStep());
            
            return position;
        }
        
        protected ISMTransition deserialize(Buffer buffer, int offset){
            int position = offset;
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            
            int currentStatePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvCodedText currentState = dcs.deserialize(
                    buffer, currentStatePosition);
            
            boolean hasTransition = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvCodedText transition = null;
            if(hasTransition){
                int transitionPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                transition = dcs.deserialize(buffer, transitionPosition);
            }
            
            boolean hasCareflowStep = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvCodedText careflowStep = null;
            if(hasCareflowStep){
                int careflowStepPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                careflowStep = dcs.deserialize(buffer, careflowStepPosition);
            }
            
            return RMObjectFactory.newISMTransition(currentState, transition, 
                    careflowStep);
        }
    }
    
    public static class ActivitySerializer {
        protected int serialize(Buffer buffer, int offset, Locatable locatable, 
                ItemStructure description, DvParsable timing, 
                String actionArchetypeId) throws UnsupportedEncodingException{
            int meta = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();
            
            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            DvParsableSerializer dps = new DvParsableSerializer();
            
            meta = writeHeader(buffer, meta, position);
            position = ls.serialize(buffer, position, locatable);
            
            meta = writeHeader(buffer, meta, position);
            position = iss.serialize(buffer, position, description);
            
            meta = writeHeader(buffer, meta, position);
            position = dps.serialize(buffer, position, timing);
            
            writeHeader(buffer, meta, position);
            position = stringSerialization(
                    buffer, position, actionArchetypeId);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Activity a) throws UnsupportedEncodingException{
            int position = offset;
            ActivitySerializer as = new ActivitySerializer();
            
            position = as.serialize(buffer, position, a);
            
            return position;
        }
        
        protected Activity deserialize(Buffer buffer, int offset){
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            DvParsableSerializer dps = new DvParsableSerializer();
            
            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);
            
            int descriptionPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemStructure description = iss.deserialize(
                    buffer, descriptionPosition);
            
            int timingPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvParsable timing = dps.deserialize(buffer, timingPosition);
            
            int actionArchetypeIdPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String actionArchetypeId = stringDeserialization(
                    buffer, actionArchetypeIdPosition);
            
            return RMObjectFactory.newActivity(locatable, description, 
                    timing, actionArchetypeId);
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
    private static int stringSerialization(
            Buffer buffer,
            int offset, String value) throws UnsupportedEncodingException {
        int valueLength = value.length();
        buffer.writeInteger(offset, valueLength);
        buffer.writeString(offset + PrimitiveTypeSize.INT.getSize(), value);

        return offset + PrimitiveTypeSize.INT.getSize() + valueLength;
    }

    /**
     * Deserializa uma string dado um determinado offset
     *
     * @param buffer
     * @param offset
     * @return String deserializada
     */
    private static String stringDeserialization(Buffer buffer,
            int offset) {
        int position = offset;
        int length = buffer.readInteger(position);
        position += PrimitiveTypeSize.INT.getSize();

        return buffer.readString(position, length);
    }

    /**
     * Serializa uma lista de Strings
     *
     * @param buffer
     * @param offset
     * @param list
     * @return posição final após a serialização
     * @throws UnsupportedEncodingException
     */
    public static int listStringSerialization(Buffer buffer, int offset,
            List<String> list) throws UnsupportedEncodingException {
        int meta = offset;
        int listSize = list.size();
        int position = offset + (listSize * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();

        meta = writeHeader(buffer, meta, listSize);
        if (listSize == 0) {
            return meta;
        }

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String value = it.next();
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, value);
        }

        return position;
    }

    /**
     * Deserializa uma lista de strings
     *
     * @param buffer
     * @param offset
     * @return lista original que foi serializada
     */
    public static List<String> listStringDeserialization(Buffer buffer,
            int offset) {
        int position = offset;
        int listSize = buffer.readInteger(position);
        position += PrimitiveTypeSize.INT.getSize();

        List<String> list = new ArrayList<>();
        if (listSize == 0) {
            return list;
        }

        for (int i = 0; i < listSize; i++) {
            int valuePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            String value = stringDeserialization(buffer, valuePosition);
            list.add(value);
        }

        return list;
    }

    /**
     * Serializa um map de Strings
     *
     * @param buffer
     * @param offset
     * @param map
     * @return posição final após a serialização de todos os items do map
     * @throws UnsupportedEncodingException
     */
    public static int mapStringSerialization(
            Buffer buffer,
            int offset,
            Map<String, String> map) throws UnsupportedEncodingException {
        int mapSize = map.size();
        int meta = offset;
        meta = writeHeader(buffer, meta, mapSize);
        if (mapSize == 0) {
            return meta;
        }
        int position = offset + mapSize * (2 * PrimitiveTypeSize.INT.getSize()) + PrimitiveTypeSize.INT.getSize();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, key);
            meta = writeHeader(buffer, meta, position);
            position = stringSerialization(buffer, position, value);
        }

        return position;
    }

    /**
     * Deserializa um map de Strings
     *
     * @param buffer
     * @param offset
     * @return mapa original que foi serializado
     */
    public static Map<String, String> mapStringDeserialization(
            Buffer buffer, int offset) {
        int position = offset;
        int mapSize = buffer.readInteger(position);
        position += PrimitiveTypeSize.INT.getSize();
        if (mapSize == 0) {
            Map<String, String> map = new HashMap<>();
            return map;
        }
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < mapSize; i++) {
            int keyPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            int valuePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String key = stringDeserialization(buffer, keyPosition);
            String value = stringDeserialization(buffer, valuePosition);

            map.put(key, value);
        }

        return map;
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
        return position + PrimitiveTypeSize.INT.getSize();
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
        return position + PrimitiveTypeSize.BOOLEAN.getSize();
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
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            buffer.writeInteger(position, value);

            position += PrimitiveTypeSize.INT.getSize();
        } else {
            buffer.writeBoolean(position, exists);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
        }
        return position;
    }
}
