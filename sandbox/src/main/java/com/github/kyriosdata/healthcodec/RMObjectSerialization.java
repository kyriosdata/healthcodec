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

import java.util.*;

import static com.github.kyriosdata.healthcodec.PrimitiveTypeSize.BOOLEAN;

/**
 *
 * @author Gabriel
 * 
 * Classe que encapsula os métodos necessários para que as classes do MR sejam
 * serializadas/desserializadas.
 * Todos os métodos possuem a referência ao buffer e um inteiro offset 
 * como parâmetro.
 * IMPORTANTE: É de inteira responsabilidade do usuário que utilizar essa 
 * coleção de métodos, o devido controle sob as posições do buffer para evitar
 * qualquer tipo de resultado inesparado durante a utilização.
 * 
 * Funcionamento geral dos métodos
 * 
 * serializador: recebe o buffer, os atributos da classe e o offset.
 * Um cabeçalho é contruído antes do armazenamento dos atributos da classe de
 * modo que dois tipos de dados são armazenados:
 * 1) Posição: a posição específica na qual o serializador do atributo inicia
 * seu processamento. É armazenado um inteiro (4 bytes).
 * 2) Condição de existência: certos atributos precisam estar necessariamente
 * presentes no momentos de instanciação da classe. É armazenada uma flag no
 * cabeçalho de modo que se a classe existe, um byte (1) é armazenado. Caso
 * contrário, um byte (0) é armazenado.
 *
 * Seguindo essas condições, é possível serializar os tipos primitivos presentes
 * em cada classe de forma direta a partir da posição indicada. E fazer o
 * processo inverso apenas lendo as informações do cabeçalho e acessando a
 * posição de cada classe, reconstruindo ao fim o objeto criado no {@link RMObjectFactory}
 * 
 */
public class RMObjectSerialization {

    static class DvBooleanSerializer {

        protected int serialize(Buffer buffer, int offset, boolean value){
            buffer.writeBoolean(offset, value);
            return offset + BOOLEAN.getSize();
        }
        
        protected int serialize(Buffer buffer, int offset, DvBoolean d){
            int position = offset;
            DvBooleanSerializer dbs = new DvBooleanSerializer();
            position = dbs.serialize(buffer, position, d.getValue());
            
            return position;
        }

        protected DvBoolean deserialize(Buffer buffer, int offset){
            boolean value = buffer.readBoolean(offset);
            return RMObjectFactory.newDvBoolean(value);
        }

    }

    static class DvIdentifierSerializer {

        protected int serialize(Buffer buffer,
                int offset, String issuer, String assigner, String id,
                String type) {
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize();
            
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, issuer);
            
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, assigner);
            
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, id);
            
            writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, type);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                DvIdentifier dvi) {
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

        protected DvIdentifier deserialize(Buffer buffer, int offset){
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
                 {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            DvIdentifierSerializer dis = new DvIdentifierSerializer();

            for (DvIdentifier d : items){
                header = writeHeader(buffer, header, position);
                position = dis.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<DvIdentifier> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<DvIdentifier> list = new ArrayList<>();
            DvIdentifierSerializer dis = new DvIdentifierSerializer();

            for (int i = 0; i < listSize; i++){
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
                String value) {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                UID u) {
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
                 {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                InternetID id) {
            int position = offset;
            InternetIDSerializer s = new InternetIDSerializer();
            
            position = s.serialize(buffer, position, id.getUid().getValue());
            
            return position;
        }

        protected InternetID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);
            
            return RMObjectFactory.newInternetID(value);
        }
    }

    static class ISOOIDSerialilzer {

        protected int serialize(Buffer buffer, int offset, String value)
                 {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                ISO_OID iso) {
            int position = offset;
            ISOOIDSerialilzer isos = new ISOOIDSerialilzer();
            
            position = isos.serialize(buffer, position, 
                    iso.getUid().getValue());
            
            return position;
        }
        
        protected ISO_OID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newISOOID(value);
        }
    }

    static class UUIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                 {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                UUID uuid){
            int position = offset;
            UUIDSerializer us = new UUIDSerializer();
            position = us.serialize(buffer, position, uuid.getUid().getValue());
            
            return position;
        }

        protected UUID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newUUID(value);
        }
    }

    static class GenericIDSerializer {

        protected int serialize(
                Buffer buffer, int offset, String value, String scheme)
                 {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, value);
            
            writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, scheme);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                GenericID gid) {
            int position = offset;
            GenericIDSerializer gids = new GenericIDSerializer();
            position = gids.serialize(buffer, position, 
                    gid.getObjectID().getValue(), gid.getScheme());
            
            return position;
        }

        protected GenericID deserialize(Buffer buffer, int offset){
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
                 {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                TemplateID tid) {
            int position = offset;
            TemplateIDSerializer ts = new TemplateIDSerializer();
            position = ts.serialize(buffer, position, 
                    tid.getObjectID().getValue());
            
            return position;
        }

        protected TemplateID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newTemplateID(value);
        }
    }

    static class TerminologyIDSerializer {

        protected int serialize(Buffer buffer, int offset, String name, 
                String version) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, name);
            
            writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, version);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, TerminologyID tid)
                 {
            int position = offset;
            TerminologyIDSerializer tids = new TerminologyIDSerializer();
            position = tids.serialize(buffer, position, 
                    tid.getName(), tid.getVersion());
            
            return position;
        }

        protected TerminologyID deserialize(Buffer buffer, int offset){
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
                 {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            
            TerminologyIDSerializer ts = new TerminologyIDSerializer();
            
            header = writeHeader(buffer, header, position);
            position = ts.serialize(buffer, position, terminologyId);
            
            writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, codeString);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                CodePhrase cp) {
            int position = offset;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            position = cps.serialize(buffer,
                    position, cp.getTerminologyID(), cp.getCodeString());

            return position;
        }

        protected CodePhrase deserialize(
                Buffer buffer, int offset){
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
                 {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DVURI d){
            int position = offset;
            DVURISerializer ds = new DVURISerializer();
            position = ds.serialize(buffer, position, d.getValue());
            
            return position;
        }

        protected DVURI deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newDVURI(value);
        }
    }

    static class DvEHRURISerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                 {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }

        protected int serialize(Buffer buffer, int offset,
                DvEHRURI DvEHRURI) {
            DvEHRURISerializer des = new DvEHRURISerializer();
            int position = offset;

            position = des.serialize(buffer, position, 
                    DvEHRURI.getDvuri().getValue());

            return position;
        }

        protected DvEHRURI deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newDvEHRURI(value);
        }

        protected int setSerializer(Buffer buffer, int offset,
                                    Set<DvEHRURI> items) {
            int setSize = items.size();
            int position = offset + (setSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            int header = offset;
            DvEHRURISerializer des = new DvEHRURISerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<DvEHRURI> it = items.iterator();

            while (it.hasNext()){
                DvEHRURI d = it.next();
                int dvPosition = position;
                header = writeHeader(buffer, header, dvPosition);
                position = des.serialize(buffer, position, d);
            }

            return position;
        }

        protected Set<DvEHRURI> setDeserializer(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            DvEHRURISerializer des = new DvEHRURISerializer();
            Set<DvEHRURI> dves = new HashSet<>();

            for (int i = 0; i < listSize; i++){
                int dvPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                DvEHRURI dv = des.deserialize(buffer, dvPosition);
                dves.add(dv);
            }

            return dves;
        }
    }

    static class VersionTreeIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                 {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }
        
        protected int serialize(Buffer buffer, int offset, VersionTreeID vti)
                 {
            int position = offset;
            VersionTreeIDSerializer vs = new VersionTreeIDSerializer();
            position = vs.serialize(buffer, position, vti.getValue());
            
            return position;
        }

        protected VersionTreeID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);
            
            return RMObjectFactory.newVersionTreeID(value);
        }
    }

    static class ArchetypeIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                 {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, ArchetypeID a)
                 {
            int position = offset;
            ArchetypeIDSerializer as = new ArchetypeIDSerializer();
            position = as.serialize(buffer, position, 
                    a.getObjectID().getValue());
            
            return position;
        }

        protected ArchetypeID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newArchetypeID(value);
        }
    }

    static class ObjectVersionIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                 {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }
        
        protected int serialize(Buffer buffer, int offset, ObjectVersionID o)
                 {
            int position = offset;
            ObjectVersionIDSerializer os = new ObjectVersionIDSerializer();
            position = os.serialize(buffer, position, 
                    o.getUIDBasedID().getValue());
            
            return position;
        }

        protected ObjectVersionID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newObjectVersionID(value);
        }
    }

    static class HierObjectIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                 {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }
        
        protected int serialize(Buffer buffer, int offset, HierObjectID h)
                 {
            int position = offset;
            HierObjectIDSerializer os = new HierObjectIDSerializer();
            position = os.serialize(buffer, position, 
                    h.getUIDBasedID().getValue());
            
            return position;
        }
        
        protected HierObjectID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newHierObjectID(value);
        }
    }

    static class ObjectIDSerializer {

        protected int serialize(Buffer buffer, int offset, String value)
                 {
            int position = offset;
            return stringSerialization(buffer, position, value);
        }
        
        protected int serialize(Buffer buffer, int offset, ObjectID o)
                 {
            int position = offset;
            ObjectIDSerializer os = new ObjectIDSerializer();
            position = os.serialize(buffer, position, o.getValue());
            
            return position;
        }
        
        protected ObjectID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);

            return RMObjectFactory.newObjectID(value);
        }

        protected int mapSerialization(Buffer buffer, int offset,
                                       Map<ObjectID, Party> map)
        {
            int header = offset;
            int mapSize = map.size();
            int position = offset
                    + mapSize * (2 * PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            ObjectIDSerializer ois = new ObjectIDSerializer();
            PartySerializer ps = new PartySerializer();

            header = writeHeader(buffer, header, mapSize);
            if (mapSize == 0){
                return header;
            }

            for (Map.Entry<ObjectID, Party> entry : map.entrySet()){
                ObjectID key = entry.getKey();
                Party value = entry.getValue();

                header = writeHeader(buffer, header, position);
                position = ois.serialize(buffer, position, key);
                header = writeHeader(buffer, header, position);
                position = ps.serialize(buffer, position, value);
            }

            return position;
        }

        protected Map<ObjectID, Party> mapDeserialization(
                Buffer buffer, int offset){
            int position = offset;
            int mapSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            ObjectIDSerializer ois = new ObjectIDSerializer();
            PartySerializer ps = new PartySerializer();
            Map<ObjectID, Party> map = new HashMap<>();
            if (mapSize == 0){
                return map;
            }

            for (int i = 0; i < mapSize; i++){
                int keyPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                int valuePosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                ObjectID key = ois.deserialize(buffer, keyPosition);
                Party value = ps.deserialize(buffer, valuePosition);

                map.put(key, value);
            }

            return map;
        }
    }

    static class PartyRefSerializer {

        protected int serialize(
                Buffer buffer,
                int offset,
                ObjectID id,
                String value) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            ObjectIDSerializer os = new ObjectIDSerializer();
            
            header = writeHeader(buffer, header, position);
            position = os.serialize(buffer, position, id);
            
            writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, value);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartyRef pr) {
            int position = offset;
            PartyRefSerializer prs = new PartyRefSerializer();

            position
                    = prs.serialize(buffer, position,
                            pr.getObjectRef().getId(), 
                            pr.getObjectRef().getType());

            return position;
        }

        protected PartyRef deserialize(Buffer buffer, int offset){
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
                String type) {
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();
            
            ObjectIDSerializer os = new ObjectIDSerializer();
            
            header = writeHeader(buffer, header, position);
            position = os.serialize(buffer, position, id);
            
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, namespace);
            
            writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, type);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObjectRef or) {
            int position = offset;
            ObjectRefSerializer ors = new ObjectRefSerializer();
            position = ors.serialize(buffer, position, or.getId(),
                    or.getNamespace(), or.getType());

            return position;
        }

        protected ObjectRef deserialize(Buffer buffer, int offset){
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

        protected int setSerializer(Buffer buffer, int offset,
                                    Set<ObjectRef> items) {
            int setSize = items.size();
            int position = offset + (setSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            int header = offset;
            ObjectRefSerializer ors = new ObjectRefSerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<ObjectRef> it = items.iterator();

            while (it.hasNext()){
                ObjectRef or = it.next();
                int objectRefPosition = position;
                header = writeHeader(buffer, header, objectRefPosition);
                position = ors.serialize(buffer, position, or);
            }

            return position;
        }

        protected Set<ObjectRef> setDeserializer(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            ObjectRefSerializer ors = new ObjectRefSerializer();
            Set<ObjectRef> items = new HashSet<>();

            for (int i = 0; i < listSize; i++){
                int orPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                ObjectRef or = ors.deserialize(buffer, orPosition);
                items.add(or);
            }

            return items;
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<ObjectRef> items)
        {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            ObjectRefSerializer dis = new ObjectRefSerializer();

            for (ObjectRef o : items){
                header = writeHeader(buffer, header, position);
                position = dis.serialize(buffer, position, o);
            }

            return position;
        }

        protected List<ObjectRef> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<ObjectRef> list = new ArrayList<>();
            ObjectRefSerializer dis = new ObjectRefSerializer();

            for (int i = 0; i < listSize; i++){
                int objectRefPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                ObjectRef o = dis.deserialize(buffer, objectRefPosition);
                list.add(o);
            }

            return list;
        }


    }

    static class LocatableRefSerializer {

        protected int serialize(
                Buffer buffer,
                int offset,
                ObjectVersionID id,
                String namespace,
                String type,
                String path) {
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize()+
                    BOOLEAN.getSize();
            
            ObjectVersionIDSerializer os = new ObjectVersionIDSerializer();
            
            header = writeHeader(buffer, header, position);
            position = os.serialize(buffer, position, id);
            
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, namespace);
            
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, type);
            
            boolean hasPath = path != null;
            if(hasPath){
                writeHeader(buffer, header, hasPath, position);
                position = stringSerialization(buffer, position, path);
            } else {
                writeHeader(buffer, header, hasPath);
            }
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                LocatableRef lr) {
            int position = offset;
            LocatableRefSerializer lrs = new LocatableRefSerializer();

            position = lrs.serialize(buffer, position,
                    RMObjectFactory.newObjectVersionID(
                            lr.getObjectRef().getId().getValue()), 
                    lr.getObjectRef().getNamespace(), 
                    lr.getObjectRef().getType(), lr.getPath());

            return position;
        }

        protected LocatableRef deserialize(Buffer buffer, int offset){
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
            position += BOOLEAN.getSize();
            String path = null;
            if(hasPath){
                int pathPosition = buffer.readInteger(position);
                path = stringDeserialization(buffer, pathPosition);
            }
            
            return RMObjectFactory.newLocatableRef(id, namespace, type, path);
        }

        protected int setSerializer(Buffer buffer, int offset,
                Set<LocatableRef> lrefs) {
            int setSize = lrefs.size();
            int position = offset + (setSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            int header = offset;
            LocatableRefSerializer lrs = new LocatableRefSerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<LocatableRef> it = lrefs.iterator();

            while (it.hasNext()){
                LocatableRef lr = it.next();
                int linkPosition = position;
                header = writeHeader(buffer, header, linkPosition);
                position = lrs.serialize(buffer, position, lr);
            }

            return position;
        }

        protected Set<LocatableRef> setDeserializer(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            LocatableRefSerializer lrs = new LocatableRefSerializer();
            Set<LocatableRef> lrefs = new HashSet<>();

            for (int i = 0; i < listSize; i++){
                int prPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                LocatableRef lr = lrs.deserialize(buffer, prPosition);
                lrefs.add(lr);
            }

            return lrefs;
        }

    }

    static class ProportionKindSerializer {

        protected int serialize(Buffer buffer, int offset, ProportionKind p){
            int position = offset;
            
            buffer.writeInteger(position, p.getValue());
            position += PrimitiveTypeSize.INT.getSize();
            
            return position;
        }

        protected ProportionKind deserialize(Buffer buffer, int offset){
            int value = buffer.readInteger(offset);

            ProportionKind p = ProportionKind.valueOf(value);

            return p;
        }
    }

    static class AccessGroupRefSerializer {

        protected int serialize(Buffer buffer, int offset, 
                ObjectID id) {
            int position = offset;
            
            ObjectIDSerializer os = new ObjectIDSerializer();
            
            position = os.serialize(buffer, position, id);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                AccessGroupRef agr) {
            int position = offset;
            
            AccessGroupRefSerializer agrs = new AccessGroupRefSerializer();
            
            position = agrs.serialize(
                    buffer, position, agr.getObjectRef().getId());
            
            return position;
        }
        
        protected AccessGroupRef deserialize(Buffer buffer, int offset){
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
                 {
            int header = offset;
            int position = offset + (3 * PrimitiveTypeSize.INT.getSize());
            PartyRefSerializer prs = new PartyRefSerializer();
            DvIdentifierSerializer dis = new DvIdentifierSerializer();

            header = writeHeader(buffer, header, position);
            position = prs.serialize(buffer, position, externalRef);

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, name);

            writeHeader(buffer, header, position);
            position = dis.listSerialize(buffer, position, identifiers);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, PartyIdentified pi)
                 {
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            int position = offset;

            position = pis.serialize(buffer, position,
                    pi.getExternalRef(), pi.getName(), pi.getIdentifiers());

            return position;
        }

        protected PartyIdentified deserialize(Buffer buffer, int offset){
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
                String rmVersion) {
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize() +
                    BOOLEAN.getSize();
            
            ArchetypeIDSerializer ais = new ArchetypeIDSerializer();
            TemplateIDSerializer tis = new TemplateIDSerializer();
            
            header = writeHeader(buffer, header, position);
            position = ais.serialize(buffer, position, archetypeId);
            
            boolean hasTemplateId = templateId != null;
            if(hasTemplateId){
                header = writeHeader(buffer, header, hasTemplateId, position);
                position = tis.serialize(buffer, position, templateId);
            } else {
                header = writeHeader(buffer, header, hasTemplateId);
            }
            
            writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, rmVersion);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Archetyped a) {
            int position = offset;
            ArchetypedSerializer as = new ArchetypedSerializer();

            position = as.serialize(buffer, position,
                    a.getArchetypeId(), a.getTemplateId(), a.getRmVersion());

            return position;
        }

        protected Archetyped deserialize(Buffer buffer, int offset){
            int position = offset;
            ArchetypeIDSerializer ais = new ArchetypeIDSerializer();
            TemplateIDSerializer tis = new TemplateIDSerializer();
            
            int archetypeIdPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ArchetypeID archetypeId = ais.deserialize(
                    buffer, archetypeIdPosition);
            
            boolean hasTemplateId = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
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
                CodePhrase language) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            
            CodePhraseSerializer cps = new CodePhraseSerializer();
            
            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, charset);
            
            writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, language);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                DvEncapsulated de) {
            int position = offset;
            DvEncapsulatedSerializer des = new DvEncapsulatedSerializer();
            position = des.serialize(
                    buffer, position, de.getCharset(), de.getLanguage());

            return position;
        }

        protected DvEncapsulated deserialize(Buffer buffer, int offset){
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
                 {
            int position = offset;
            position = stringSerialization(buffer, position, value);
            
            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                UIDBasedID uid) {
            int position = offset;
            UIDBasedIDSerializer us = new UIDBasedIDSerializer();

            position = us.serialize(buffer, position, uid.getValue());

            return position;
        }

        protected UIDBasedID deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);
            
            return RMObjectFactory.newUIDBasedID(value);
        }
    }

    public static class DvParsableSerializer {

        protected int serialize(Buffer buffer, int offset, 
                DvEncapsulated dvEncapsulated, String value,
                String formalism) {
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize();
            
            DvEncapsulatedSerializer des = new DvEncapsulatedSerializer();
            
            header = writeHeader(buffer, header, position);
            position = des.serialize(buffer, position, dvEncapsulated);
            
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, value);
            
            writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, formalism);

            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DvParsable dp){
            int position = offset;
            DvParsableSerializer dps = new DvParsableSerializer();
            
            position = dps.serialize(buffer, position, 
                    dp.getDvEncapsulated(), dp.getValue(), dp.getFormalism());
            
            return position;
        }

        protected DvParsable deserialize(Buffer buffer, int offset){
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
                DvParsable value) {
            int position = offset;
            DvParsableSerializer s = new DvParsableSerializer();
            position = s.serialize(buffer, position, value.getDvEncapsulated(), 
                    value.getValue(), value.getFormalism());

            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DvTimeSpecification d) {
            int position = offset;
            DvTimeSpecificationSerializer dtss = 
                    new DvTimeSpecificationSerializer();
            position = dtss.serialize(buffer, position, d.getValue());
            
            return position;
        }

        protected DvTimeSpecification deserialize(Buffer buffer, int offset){
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
                byte[] data) {

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
            int header = offset;

            header = writeHeader(buffer, header, position);
            position = dve.serialize(buffer, position,
                    dvEncapsulated.getCharset(),
                    dvEncapsulated.getLanguage());
            header = writeHeader(buffer, header, position);
            position = stringSerialization(
                    buffer, position, alternateText);

            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position,
                    mediaType.getTerminologyID(), mediaType.getCodeString());

            if (hasCompressionAlgorithm){
                header = writeHeader(
                        buffer, header, hasCompressionAlgorithm, position);
                position = cps.serialize(
                        buffer,
                        position,
                        compressionAlgorithm.getTerminologyID(),
                        compressionAlgorithm.getCodeString());
            } else {
                header = writeHeader(buffer, header, false);
            }

            if (hasIntegrityCheck && hasIntegrityCheckAlgorithm){
                header = writeHeader(buffer, header, hasIntegrityCheck, position);
                header = writeHeader(buffer, header, integrityCheck.length);
                buffer.writeByteArray(position, integrityCheck);
                position += integrityCheck.length;

                header = writeHeader(
                        buffer, header, hasIntegrityCheckAlgorithm, position);
                position = cps.serialize(
                        buffer,
                        position,
                        integrityCheckAlgorithm);
            } else {
                throw new IllegalArgumentException("Integrity Check fails!");
            }

            if (hasThumbnail){
                header = writeHeader(buffer, header, hasThumbnail, position);
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
                header = writeHeader(buffer, header, hasThumbnail);
            }

            header = writeHeader(buffer, header, position);
            position = dvu.serialize(buffer, position, uri.getValue());

            header = writeHeader(buffer, header, position);
            writeHeader(buffer, header, data.length);
            buffer.writeByteArray(position, data);
            position += data.length;

            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                DvMultimedia d) {
            int position = offset;
            DvMultimediaSerializer dms = new DvMultimediaSerializer();
            
            position = dms.serialize(buffer, position, 
                    d.getDvEncapsulated(), d.getAlternateText(),
                    d.getMediaType(), d.getCompressionAlgorithm(),
                    d.getIntegrityCheck(), d.getIntegrityCheckAlgorithm(),
                    d.getThumbnail(), d.getUri(), d.getData());
            
            return position;
        }

        protected DvMultimedia deserialize(Buffer buffer, int offset){

            DvEncapsulatedSerializer dve = new DvEncapsulatedSerializer();;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            DVURISerializer dvu = new DVURISerializer();
            DvMultimediaSerializer dvm = new DvMultimediaSerializer();

            int header = offset;
            int dvEncapsulatedPosition = buffer.readInteger(header);
            DvEncapsulated dvMultimediaDvEncapsulated
                    = dve.deserialize(buffer, dvEncapsulatedPosition);
            header += PrimitiveTypeSize.INT.getSize();

            int alternateTextPosition = buffer.readInteger(header);
            String alternateText
                    = stringDeserialization(buffer, alternateTextPosition);
            header += PrimitiveTypeSize.INT.getSize();

            int mediaTypePosition = buffer.readInteger(header);
            CodePhrase mediaType = cps.deserialize(buffer, mediaTypePosition);
            header += PrimitiveTypeSize.INT.getSize();

            boolean hasCompressionAlgorithm = buffer.readBoolean(header);
            int compressionAlgorithmPosition = 0;
            CodePhrase compressionAlgorithm = null;
            if (hasCompressionAlgorithm){
                header += BOOLEAN.getSize();
                compressionAlgorithmPosition = buffer.readInteger(header);
                compressionAlgorithm
                        = cps.deserialize(buffer, compressionAlgorithmPosition);
                header += PrimitiveTypeSize.INT.getSize();
            } else {
                header += BOOLEAN.getSize();
            }
            boolean hasIntegrityCheck = buffer.readBoolean(header);
            int integrityCheckPosition = 0;
            int integrityCheckLength = 0;
            byte[] integrityCheck = null;
            if (hasIntegrityCheck){
                header += BOOLEAN.getSize();
                integrityCheckPosition = buffer.readInteger(header);
                header += PrimitiveTypeSize.INT.getSize();
                integrityCheckLength = buffer.readInteger(header);
                header += PrimitiveTypeSize.INT.getSize();

                integrityCheck = buffer.readByteArray(integrityCheckPosition,
                        integrityCheckLength);
            } else {
                header += BOOLEAN.getSize();
            }

            boolean hasIntegrityCheckAlgorithm = buffer.readBoolean(header);
            int integrityCheckAlgorithmPosition = 0;
            CodePhrase integrityCheckAlgorithm = null;
            if (hasIntegrityCheckAlgorithm){
                header += BOOLEAN.getSize();
                integrityCheckAlgorithmPosition = buffer.readInteger(header);
                header += PrimitiveTypeSize.INT.getSize();
                integrityCheckAlgorithm = cps.deserialize(buffer,
                        integrityCheckAlgorithmPosition);
            } else {
                header += BOOLEAN.getSize();
            }

            boolean hasThumbnail = buffer.readBoolean(header);
            int thumbnailPosition = 0;
            DvMultimedia thumbnail = null;
            if (hasThumbnail){
                header += BOOLEAN.getSize();
                thumbnailPosition = buffer.readInteger(header);
                header += PrimitiveTypeSize.INT.getSize();
                thumbnail = dvm.deserialize(buffer, thumbnailPosition);
            } else {
                header += BOOLEAN.getSize();
            }

            int uriPosition = buffer.readInteger(header);
            DVURI uri = dvu.deserialize(buffer, uriPosition);
            header += PrimitiveTypeSize.INT.getSize();

            int dataPosition = buffer.readInteger(header);
            header += PrimitiveTypeSize.INT.getSize();
            int dataLength = buffer.readInteger(header);
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
                CodePhrase charset) {
            boolean hasMappings = mappings != null;
            boolean hasFormatting = formatting != null;
            boolean hasHyperlink = hyperlink != null;

            CodePhraseSerializer cps = new CodePhraseSerializer();
            TermMappingSerializer tms = new TermMappingSerializer();
            DVURISerializer dvu = new DVURISerializer();

            int header = offset;
            int position = offset + 6 * PrimitiveTypeSize.INT.getSize()+
                    3 * BOOLEAN.getSize();

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, value);
            if (hasMappings){

                header = writeHeader(buffer, header, hasMappings, position);
                position = tms.listSerialize(buffer, position, mappings);
            } else {
                header = writeHeader(buffer, header, hasMappings);
            }

            if (hasFormatting){
                header = writeHeader(buffer, header, hasFormatting, position);
                position
                        = stringSerialization(buffer, position, formatting);
            } else {
                header = writeHeader(buffer, header, hasFormatting);
            }

            if (hasHyperlink){
                header = writeHeader(buffer, header, hasHyperlink, position);
                position
                        = dvu.serialize(buffer, position,
                                hyperlink.getValue());
            } else {
                header = writeHeader(buffer, header, hasHyperlink);
            }

            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, language);
            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, charset);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvText dvText)
                 {
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

        protected DvText deserialize(Buffer buffer, int offset){
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
            position += BOOLEAN.getSize();
            if (hasMappings){
                mappingsPosition = buffer.readInteger(position);
                mappings = tms.deserializeList(buffer, mappingsPosition);
                position += PrimitiveTypeSize.INT.getSize();
            }

            boolean hasFormatting = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            int formattingPosition = 0;
            String formatting = null;
            if (hasFormatting){
                formattingPosition = buffer.readInteger(position);
                formatting
                        = stringDeserialization(buffer, formattingPosition);
                position += PrimitiveTypeSize.INT.getSize();
            }
            boolean hasHyperlink = buffer.readBoolean(position);
            int hyperlinkPosition = 0;
            DVURI hyperlink = null;
            position += BOOLEAN.getSize();
            if (hasHyperlink){
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
                 {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize * PrimitiveTypeSize.INT.getSize())
                    + PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            DvTextSerializer tms = new DvTextSerializer();

            for (DvText d : items){
                header = writeHeader(buffer, header, position);
                position = tms.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<DvText> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<DvText> list = new ArrayList<>();
            DvTextSerializer dts = new DvTextSerializer();

            for (int i = 0; i < listSize; i++){
                int dvTextPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                DvText t = dts.deserialize(buffer, dvTextPosition);
                list.add(t);
            }

            return list;
        }
        
        protected int setSerializer(Buffer buffer, int offset,
                Set<DvText> items) {
            int setSize = items.size();
            int position = offset + (setSize *
                    PrimitiveTypeSize.INT.getSize())+
                    PrimitiveTypeSize.INT.getSize();
            int header = offset;
            DvTextSerializer dts = new DvTextSerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<DvText> it = items.iterator();

            while (it.hasNext()){
                DvText d = it.next();
                int linkPosition = position;
                header = writeHeader(buffer, header, linkPosition);
                position = dts.serialize(buffer, position, d);
            }

            return position;
        }

        protected Set<DvText> setDeserializer(Buffer buffer, int offset){
            int position = offset;
            int setSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            DvTextSerializer dts = new DvTextSerializer();
            Set<DvText> items = new HashSet<>();

            for (int i = 0; i < setSize; i++){
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
                 {
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            int header = offset;
            DvTextSerializer dvt = new DvTextSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();

            header = writeHeader(buffer, header, position);
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

            writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, definingCode);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                DvCodedText dct) {
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            int position = offset;

            position = dcs.serialize(buffer, position,
                    dct.getDvText(), dct.getDefiningCode());

            return position;
        }

        protected DvCodedText deserialize(Buffer buffer, int offset){
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
                 {
            int position = offset;
            String value = match.getValue();
            position = stringSerialization(buffer, position, value);

            return position;
        }

        protected Match deserialize(Buffer buffer, int offset){
            int position = offset;
            String value = stringDeserialization(buffer, position);

            Match match = Match.fromValue(value);

            return match;
        }
    }

    public static class TermMappingSerializer {

        protected int serialize(Buffer buffer, int offset, CodePhrase target,
                Match match, 
                DvCodedText purpose) {
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize() +
                    BOOLEAN.getSize();
            boolean hasPurpose = purpose != null;
            CodePhraseSerializer cps = new CodePhraseSerializer();
            MatchSerializer ms = new MatchSerializer();
            DvCodedTextSerializer dct = new DvCodedTextSerializer();

            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, target);
            header = writeHeader(buffer, header, position);
            position = ms.serialize(buffer, position, match);
            if (hasPurpose){
                writeHeader(buffer, header, hasPurpose, position);
                position = dct.serialize(buffer, position,
                        purpose.getDvText(), purpose.getDefiningCode());
            } else {
                writeHeader(buffer, header, hasPurpose);
            }
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                TermMapping t) {
            int position = offset;
            TermMappingSerializer tms = new TermMappingSerializer();
            
            position = tms.serialize(buffer, position, 
                    t.getTarget(), t.getMatch(), t.getPurpose());
            
            return position;
        }
        
        protected TermMapping deserialize(Buffer buffer, int offset){
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
            match = ms.deserialize(buffer, matchPosition);

            boolean hasPurpose = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            DvCodedText purpose = null;
            if (hasPurpose){
                int purposePosition = buffer.readInteger(position);
                purpose = dct.deserialize(buffer, purposePosition);
                position += PrimitiveTypeSize.INT.getSize();
            }

            return RMObjectFactory.newTermMapping(target, match, purpose);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<TermMapping> mappings)
                 {
            int header = offset;
            int listSize = mappings.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            TermMappingSerializer tms = new TermMappingSerializer();

            for (TermMapping t : mappings){
                header = writeHeader(buffer, header, position);
                position = tms.serialize(buffer, position,
                        t.getTarget(), t.getMatch(), t.getPurpose());
            }

            return position;
        }

        protected List<TermMapping> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<TermMapping> list = new ArrayList<>();
            TermMappingSerializer tms = new TermMappingSerializer();

            for (int i = 0; i < listSize; i++){
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
                DvEHRURI target) {

            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();
            int header = offset;

            DvTextSerializer dts = new DvTextSerializer();
            DvEHRURISerializer des = new DvEHRURISerializer();

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, meaning);
            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, type);
            header = writeHeader(buffer, header, position);
            position = des.serialize(buffer, position, target);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Link link) {
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
            DvEHRURISerializer des = new DvEHRURISerializer();

            int meaningPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvText meaning = dts.deserialize(buffer, meaningPosition);

            int typePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvText type = dts.deserialize(buffer, typePosition);

            int targetPosition = buffer.readInteger(position);
            DvEHRURI target = des.deserialize(buffer, targetPosition);

            return RMObjectFactory.newLink(meaning, type, target);
        }

        protected int setSerializer(Buffer buffer, int offset,
                Set<Link> links) {
            int setSize = links.size();
            int position = offset + (setSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            int header = offset;
            LinkSerializer ls = new LinkSerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<Link> it = links.iterator();

            while (it.hasNext()){
                Link link = it.next();
                int linkPosition = position;
                header = writeHeader(buffer, header, linkPosition);
                position = ls.serialize(buffer, position, link);
            }

            return position;
        }

        protected Set<Link> setDeserializer(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            LinkSerializer ls = new LinkSerializer();
            Set<Link> links = new HashSet<>();

            for (int i = 0; i < listSize; i++){
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
                String terminal) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize() +
                    BOOLEAN.getSize();
            boolean hasTerminal = terminal != null;

            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            header = writeHeader(buffer, header, position);
            position = dcs.serialize(buffer, position, value);

            if (hasTerminal){
                header = writeHeader(buffer, header, hasTerminal, position);
                position = stringSerialization(buffer, position, terminal);
            } else {
                header = writeHeader(buffer, header, hasTerminal);
            }

            return position;
        }

        protected int serialize(Buffer buffer,
                int offset,
                DvState dvState) {
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
            position += PrimitiveTypeSize.INT.getSize();

            DvCodedText value = dcs.deserialize(buffer, valuePosition);

            boolean hasTerminal = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            String terminal = null;
            if (hasTerminal){
                int terminalPosition = buffer.readInteger(position);
                terminal = stringDeserialization(buffer, terminalPosition);
            }

            return RMObjectFactory.newDvState(value, terminal);
        }
    }

    public static class DvParagraphSerializer {

        protected int serialize(Buffer buffer, int offset,
                List<DvText> items) {
            int position = offset;
            DvTextSerializer dts = new DvTextSerializer();
            position = dts.listSerialize(buffer, position, items);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                DvParagraph dvParagraph) {
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
                PartyRef externalRef) {
            int position = offset;
            PartyRefSerializer prs = new PartyRefSerializer();
            position = prs.serialize(buffer, position, externalRef);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartyProxy partyProxy) {
            int position = offset;
            PartyProxySerializer prs = new PartyProxySerializer();

            position
                    = prs.serialize(
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
                                PartyProxy subject, String versionID) {
            int header = offset;
            int position = offset + (6 * PrimitiveTypeSize.INT.getSize()) + 5 * PrimitiveTypeSize.BOOLEAN.getSize();
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            PartyProxySerializer pps = new PartyProxySerializer();

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, systemID);

            boolean hasProvider = provider != null;
            if (hasProvider) {
                header = writeHeader(buffer, header, hasProvider, position);
                position = pis.serialize(buffer, position, provider);
            } else {
                header = writeHeader(buffer, header, hasProvider);
            }

            boolean hasLocation = location != null;
            if (hasLocation) {
                header = writeHeader(buffer, header, hasLocation, position);
                position = pis.serialize(buffer, position, location);
            } else {
                header = writeHeader(buffer, header, hasLocation);
            }

            boolean hasTime = false; //TO DO
            if (hasTime) {
                header = writeHeader(buffer, header, hasTime, position);
                //position = pdt.serialize(buffer, position, time);
            } else {
                header = writeHeader(buffer, header, hasTime);
            }
            boolean hasSubject = subject != null;
            if (hasSubject) {
                header = writeHeader(buffer, header, hasSubject, position);
                position = pps.serialize(buffer, position, subject);
            } else {
                header = writeHeader(buffer, header, hasSubject);
            }

            boolean hasVersionID = versionID != null;
            if (hasVersionID) {
                writeHeader(buffer, header, hasVersionID, position);
                position = stringSerialization(buffer, position, versionID);
            } else {
                writeHeader(buffer, header, hasVersionID);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                FeederAuditDetails fad) {
            FeederAuditDetailsSerializer fas
                    = new FeederAuditDetailsSerializer();
            int position = offset;

            position = fas.serialize(
                    buffer,
                    position,
                    fad.getSystemID(),
                    fad.getProvider(),
                    fad.getLocation(),
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
        ) {
            int header = offset;
            int position = offset + (4 * PrimitiveTypeSize.INT.getSize()) +
                    4 * BOOLEAN.getSize();
            FeederAuditDetailsSerializer fas
                    = new FeederAuditDetailsSerializer();
            header = writeHeader(buffer, header, position);
            position = fas.serialize(buffer, position, originatingSystemAudit);

            boolean hasOriginatingSystemItemIDs
                    = originatingSystemItemIDs != null;
            DvIdentifierSerializer dis = new DvIdentifierSerializer();
            if (hasOriginatingSystemItemIDs){
                header = writeHeader(
                        buffer, header, hasOriginatingSystemItemIDs, position);
                position = dis.listSerialize(
                        buffer, position, originatingSystemItemIDs);
            } else {
                header = writeHeader(buffer, header, hasOriginatingSystemItemIDs);
            }

            boolean hasFeederSystemAudit = feederSystemAudit != null;
            if (hasFeederSystemAudit){
                header = writeHeader(buffer, header, hasFeederSystemAudit, position);
                position = fas.serialize(buffer, position, feederSystemAudit);
            } else {
                header = writeHeader(buffer, header, hasFeederSystemAudit);
            }

            boolean hasFeederSystemItemIDs = feederSystemItemIDs != null;
            if (hasFeederSystemItemIDs){
                header = writeHeader(
                        buffer, header, hasFeederSystemItemIDs, position);
                position = dis.listSerialize(
                        buffer, position, feederSystemItemIDs);
            } else {
                header = writeHeader(buffer, header, hasFeederSystemItemIDs);
            }

            boolean hasOriginalContent = originalContent != null;
            DvEncapsulatedSerializer des = new DvEncapsulatedSerializer();
            if (hasOriginalContent){
                writeHeader(buffer, header, hasOriginalContent, position);
                position = des.serialize(buffer, position, originalContent);
            } else {
                writeHeader(buffer, header, hasOriginalContent, position);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                FeederAudit fa) {
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
            position += PrimitiveTypeSize.INT.getSize();

            boolean hasOriginatingSystemItemIDs = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            List<DvIdentifier> originatingSystemItemIDs = null;
            if (hasOriginatingSystemItemIDs){
                int feederSystemItemIDsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                originatingSystemItemIDs = dis.deserializeList(
                        buffer, feederSystemItemIDsPosition);
            }

            boolean hasFeederSystemAudit = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            FeederAuditDetails feederSystemAudit = null;
            if (hasFeederSystemAudit){
                int hasFeederSystemAuditPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                feederSystemAudit = f.deserialize(
                        buffer, hasFeederSystemAuditPosition);
            }

            boolean hasFeederSystemItemIDs = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            List<DvIdentifier> feederSystemItemIDs = null;
            if (hasFeederSystemItemIDs){
                int feederSystemItemIDsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                feederSystemItemIDs = dis.deserializeList(
                        buffer, feederSystemItemIDsPosition);
            }

            boolean hasOriginalContent = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            DvEncapsulated originalContent = null;
            if (hasOriginalContent){
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
                FeederAudit feederAudit, Set<Link> links) {

            int position = offset + (6 * PrimitiveTypeSize.INT.getSize())+ 5 *
                    BOOLEAN.getSize();
            int header = offset;

            UIDBasedIDSerializer uids = new UIDBasedIDSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            ArchetypedSerializer as = new ArchetypedSerializer();
            FeederAuditSerializer fas = new FeederAuditSerializer();
            LinkSerializer ls = new LinkSerializer();

            boolean hasUid = uid != null;
            if (hasUid){
                header = writeHeader(buffer, header, hasUid, position);
                position = uids.serialize(buffer, position, uid);
            } else {
                header = writeHeader(buffer, header, hasUid);
            }

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, archetypeNodeId);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, name);

            boolean hasArchetypeDetails = archetypeDetails != null;
            if (hasArchetypeDetails){
                header = writeHeader(buffer, header, hasArchetypeDetails, position);
                position = as.serialize(buffer, position, archetypeDetails);
            } else {
                header = writeHeader(buffer, header, hasArchetypeDetails);
            }

            boolean hasFeederAudit = feederAudit != null;
            if (hasFeederAudit){
                header = writeHeader(buffer, header, hasFeederAudit, position);
                position = fas.serialize(buffer, position, feederAudit);
            } else {
                header = writeHeader(buffer, header, hasFeederAudit);
            }

            boolean hasLinks = links != null;
            if (hasLinks){
                writeHeader(buffer, header, hasLinks, position);
                position = ls.setSerializer(buffer, position, links);
            } else {
                writeHeader(buffer, header, hasLinks);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Locatable locatable) {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();
            position = ls.serialize(buffer, position, locatable.getUid(),
                    locatable.getArchetypeNodeId(), locatable.getName(),
                    locatable.getArchetypeDetails(), locatable.getFeederAudit(),
                    locatable.getLinks());

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
            if (hasUid){
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
            position += BOOLEAN.getSize();
            Archetyped archetypeDetails = null;
            if (hasArchetypeDetails){
                int archetypeDetailsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                archetypeDetails = as.deserialize(
                        buffer, archetypeDetailsPosition);
            }

            boolean hasFeederAudit = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            FeederAudit feederAudit = null;
            if (hasFeederAudit){
                int hasFeederAuditPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                feederAudit = fas.deserialize(buffer, hasFeederAuditPosition);
            }

            boolean hasLinks = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            Set<Link> links = null;
            if (hasLinks){
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
                DvCodedText relationship) {
            int header = offset;
            int position = offset + (2 * PrimitiveTypeSize.INT.getSize())
                    + BOOLEAN.getSize();
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            DvCodedTextSerializer dts = new DvCodedTextSerializer();

            if (relationship == null){
                throw new IllegalArgumentException(
                        "relantionship não pode ser null");
            }
            boolean hasPi = pi != null;
            if (hasPi){
                header = writeHeader(buffer, header, hasPi, position);
                position = pis.serialize(buffer, position, pi);
            } else {
                header = writeHeader(buffer, header, hasPi);
            }

            writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, relationship);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartyRelated pr) {
            int position = offset;
            PartyRelatedSerializer prs = new PartyRelatedSerializer();
            position = prs.serialize(
                    buffer, position, pr.getPi(), pr.getRelationship());

            return position;
        }

        protected PartyRelated deserialize(Buffer buffer, int offset){
            int position = offset;
            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            DvCodedTextSerializer dts = new DvCodedTextSerializer();

            boolean hasPi = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            PartyIdentified pi = null;
            if (hasPi){
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
                PartyRef externalRef) {
            int position = offset + PrimitiveTypeSize.INT.getSize()
                    + BOOLEAN.getSize();
            int header = offset;
            PartyRefSerializer prs = new PartyRefSerializer();

            boolean hasExternalRef = externalRef != null;
            if (hasExternalRef){
                writeHeader(buffer, header, hasExternalRef, position);
                position = prs.serialize(buffer, position, externalRef);
            } else {
                writeHeader(buffer, header, hasExternalRef);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartySelf ps) {
            int position = offset;
            PartySelfSerializer pss = new PartySelfSerializer();
            position = pss.serialize(buffer, position, ps.getExternalRef());

            return position;
        }

        protected PartySelf deserialize(Buffer buffer, int offset){
            int position = offset;
            boolean hasExternalRef = buffer.readBoolean(position);
            position += BOOLEAN.getSize();

            PartyRefSerializer prs = new PartyRefSerializer();

            PartyRef externalRef = null;
            if (hasExternalRef){
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
                 {
            int position = offset + 8 * PrimitiveTypeSize.INT.getSize() +
                    3 * BOOLEAN.getSize();

            int header = offset;
            CodePhraseSerializer cps = new CodePhraseSerializer();

            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, language);

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, purpose);

            boolean hasKeywords = keywords != null;
            if (hasKeywords){
                header = writeHeader(buffer, header, hasKeywords, position);
                position = listStringSerialization(buffer, position, keywords);
            } else {
                header = writeHeader(buffer, header, hasKeywords);
            }

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, use);

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, misuse);

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, copyright);

            boolean hasOriginalResourceUri = originalResourceUri != null;
            if (hasOriginalResourceUri){
                header
                        = writeHeader(buffer, header, hasOriginalResourceUri,
                                position);
                position = mapStringSerialization(
                        buffer, position, originalResourceUri);
            } else {
                header = writeHeader(buffer, header, hasOriginalResourceUri);
            }

            boolean hasOtherDetails = otherDetails != null;
            if (hasOtherDetails){
                writeHeader(buffer, header, hasOtherDetails, position);
                position = mapStringSerialization(buffer, position,
                        otherDetails);
            } else {
                writeHeader(buffer, header, hasOtherDetails);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ResourceDescriptionItem rdi)
                 {
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
                int offset){
            int position = offset;

            int languagePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CodePhraseSerializer cps = new CodePhraseSerializer();
            CodePhrase language = cps.deserialize(buffer, languagePosition);

            int purposePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String purpose = stringDeserialization(
                    buffer, purposePosition);

            boolean hasKeywords = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            List<String> keywords = null;
            if (hasKeywords){
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
            position += BOOLEAN.getSize();
            Map<String, String> originalResourceUri = null;
            if (hasOriginalResourceUri){
                int originalResourceUriPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                originalResourceUri = mapStringDeserialization(
                        buffer, originalResourceUriPosition);
            }

            boolean hasOtherDetails = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            Map<String, String> otherDetails = null;
            if (hasOtherDetails){
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
                 {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            ResourceDescriptionItemSerializer rdis
                    = new ResourceDescriptionItemSerializer();

            for (ResourceDescriptionItem d : items){
                header = writeHeader(buffer, header, position);
                position = rdis.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<ResourceDescriptionItem> deserializeList(Buffer buffer,
                int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<ResourceDescriptionItem> list = new ArrayList<>();
            ResourceDescriptionItemSerializer rdis
                    = new ResourceDescriptionItemSerializer();

            for (int i = 0; i < listSize; i++){
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
                 {
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize() +
                    2 * BOOLEAN.getSize();
            CodePhraseSerializer cps = new CodePhraseSerializer();

            if (language == null){
                throw new IllegalArgumentException("null language");
            }
            if (author == null){
                throw new IllegalArgumentException("null author");
            }

            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, language);

            header = writeHeader(buffer, header, position);
            position = mapStringSerialization(buffer, position, author);

            boolean hasAccreditation = accreditation != null;
            if (hasAccreditation){
                header = writeHeader(buffer, header, hasAccreditation, position);
                position = stringSerialization(
                        buffer, position, accreditation);
            } else {
                header = writeHeader(buffer, header, hasAccreditation);
            }

            boolean hasOtherDetails = otherDetails != null;
            if (hasOtherDetails){
                writeHeader(buffer, header, hasOtherDetails, position);
                position = mapStringSerialization(buffer, position,
                        otherDetails);
            } else {
                writeHeader(buffer, header, hasOtherDetails);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                TranslationDetails td) {
            TranslationDetailsSerializer tdss
                    = new TranslationDetailsSerializer();

            int position = offset;
            position = tdss.serialize(
                    buffer, position, td.getLanguage(), td.getAuthor(),
                    td.getAccreditation(), td.getOtherDetails());

            return position;
        }

        protected TranslationDetails deserialize(Buffer buffer, int offset){
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
            position += BOOLEAN.getSize();
            String accreditation = null;
            if (hasAccreditation){
                int accreditationPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                accreditation = stringDeserialization(
                        buffer, accreditationPosition);
            }

            boolean hasOtherDetails = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            Map<String, String> otherDetails = null;
            if (hasOtherDetails){
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
                 {
            int header = offset;
            int mapSize = map.size();
            int position = offset
                    + mapSize * (2 * PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            TranslationDetailsSerializer tdss
                    = new TranslationDetailsSerializer();

            header = writeHeader(buffer, header, mapSize);
            if (mapSize == 0){
                return header;
            }

            for (Map.Entry<String, TranslationDetails> entry : map.entrySet()){
                String key = entry.getKey();
                TranslationDetails value = entry.getValue();

                header = writeHeader(buffer, header, position);
                position = stringSerialization(buffer, position, key);
                header = writeHeader(buffer, header, position);
                position = tdss.serialize(buffer, position, value);
            }

            return position;
        }

        protected Map<String, TranslationDetails> mapDeserialization(
                Buffer buffer, int offset){
            int position = offset;
            int mapSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            TranslationDetailsSerializer tdss
                    = new TranslationDetailsSerializer();
            Map<String, TranslationDetails> map = new HashMap<>();
            if (mapSize == 0){
                return map;
            }

            for (int i = 0; i < mapSize; i++){
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
                Locatable locatable) {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();

            position = ls.serialize(buffer, position, locatable);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Item item) {
            int position = offset;
            ItemSerializer is = new ItemSerializer();

            position = is.serialize(buffer, position, item.getLocatable());

            return position;
        }

        protected Item deserialize(Buffer buffer, int offset){
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();

            Locatable locatable = ls.deserialize(buffer, position);

            return RMObjectFactory.newItem(locatable);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<Item> items)
                 {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            ItemSerializer is = new ItemSerializer();

            for (Item d : items){
                header = writeHeader(buffer, header, position);
                position = is.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<Item> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Item> list = new ArrayList<>();
            ItemSerializer is = new ItemSerializer();

            for (int i = 0; i < listSize; i++){
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
                List<Item> items) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            ItemSerializer is = new ItemSerializer();

            header = writeHeader(buffer, header, position);
            position = is.serialize(buffer, position, item);

            writeHeader(buffer, header, position);
            position = is.listSerialize(buffer, position, items);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Cluster cluster) {
            int position = offset;
            ClusterSerializer cs = new ClusterSerializer();

            position = cs.serialize(buffer, position,
                    cluster.getItem(), cluster.getItems());

            return position;
        }

        protected Cluster deserialize(Buffer buffer, int offset){
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
                 {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            ClusterSerializer cs = new ClusterSerializer();

            for (Cluster d : items){
                header = writeHeader(buffer, header, position);
                position = cs.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<Cluster> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Cluster> list = new ArrayList<>();
            ClusterSerializer es = new ClusterSerializer();

            for (int i = 0; i < listSize; i++){
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
                DvCodedText nullFlavour) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            ItemSerializer is = new ItemSerializer();
            DvCodedTextSerializer dts = new DvCodedTextSerializer();

            header = writeHeader(buffer, header, position);
            position = is.serialize(buffer, position, item);

            writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, nullFlavour);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Element element) {
            int position = offset;
            ElementSerializer es = new ElementSerializer();

            position = es.serialize(buffer, position,
                    element.getItem(), element.getNullFlavour());

            return position;
        }

        protected Element deserialize(Buffer buffer, int offset){
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
                 {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            ElementSerializer is = new ElementSerializer();

            for (Element d : items){
                header = writeHeader(buffer, header, position);
                position = is.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<Element> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Element> list = new ArrayList<>();
            ElementSerializer es = new ElementSerializer();

            for (int i = 0; i < listSize; i++){
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
                Locatable locatable) {
            int position = offset;
            LocatableSerializer ls = new LocatableSerializer();

            position = ls.serialize(buffer, position, locatable);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                DataStructure ds) {
            int position = offset;
            DataStructureSerializer dss = new DataStructureSerializer();

            position = dss.serialize(buffer, position, ds.getLocatable());

            return position;
        }

        protected DataStructure deserialize(Buffer buffer, int offset){
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
                List<Element> items) {
            int header = offset;
            int position = offset + (7 * PrimitiveTypeSize.INT.getSize()) +
                    5 * BOOLEAN.getSize();

            UIDBasedIDSerializer us = new UIDBasedIDSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            FeederAuditSerializer fas = new FeederAuditSerializer();
            ArchetypedSerializer as = new ArchetypedSerializer();
            LinkSerializer ls = new LinkSerializer();
            ElementSerializer es = new ElementSerializer();

            boolean hasUid = uid != null;
            if (hasUid){
                header = writeHeader(buffer, header, hasUid, position);
                position = us.serialize(buffer, position, uid);
            } else {
                header = writeHeader(buffer, header, hasUid);
            }

            header = writeHeader(buffer, header, position);
            position = stringSerialization(
                    buffer, position, archetypeNodeId);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, name);

            boolean hasArchetypeDetails = archetypeDetails != null;
            if (hasArchetypeDetails){
                header = writeHeader(buffer, header, hasArchetypeDetails, position);
                position = as.serialize(buffer, position, archetypeDetails);
            } else {
                header = writeHeader(buffer, header, hasArchetypeDetails);
            }

            boolean hasFeederAudit = feederAudit != null;
            if (hasFeederAudit){
                header = writeHeader(buffer, header, hasFeederAudit, position);
                position = fas.serialize(buffer, position, feederAudit);
            } else {
                header = writeHeader(buffer, header, hasFeederAudit);
            }

            boolean hasLinks = links != null;
            if (hasLinks){
                header = writeHeader(buffer, header, hasLinks, position);
                position = ls.setSerializer(buffer, position, links);
            } else {
                header = writeHeader(buffer, header, hasLinks);
            }

            boolean hasItems = items != null;
            if (hasItems){
                writeHeader(buffer, header, hasItems, position);
                position = es.listSerialize(buffer, position, items);
            } else {
                writeHeader(buffer, header, hasItems);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ItemList il) {
            ItemListSerializer is = new ItemListSerializer();
            int position = offset;

            position = is.serialize(buffer, position, il.getUid(),
                    il.getArchetypeNodeId(), il.getName(),
                    il.getArchetypeDetails(), il.getFeederAudit(),
                    il.getLinks(), il.getItems());

            return position;
        }

        protected ItemList deserialize(Buffer buffer, int offset){
            int position = offset;

            UIDBasedIDSerializer us = new UIDBasedIDSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            FeederAuditSerializer fas = new FeederAuditSerializer();
            ArchetypedSerializer as = new ArchetypedSerializer();
            LinkSerializer ls = new LinkSerializer();
            ElementSerializer es = new ElementSerializer();

            boolean hasUId = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            UIDBasedID uid = null;
            if (hasUId){
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
            position += BOOLEAN.getSize();
            Archetyped archetypeDetails = null;
            if (hasArchetypeDetails){
                int archetypeDetailsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                archetypeDetails = as.deserialize(
                        buffer, archetypeDetailsPosition);
            }

            boolean hasFeederAudit = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            FeederAudit feederAudit = null;
            if (hasFeederAudit){
                int feederAuditPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                feederAudit = fas.deserialize(buffer, feederAuditPosition);
            }

            boolean hasLinks = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            Set<Link> links = null;
            if (hasLinks){
                int linksPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                links = ls.setDeserializer(buffer, linksPosition);
            }

            boolean hasItems = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
            List<Element> items = null;
            if (hasItems){
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
                DataStructure ds) {
            int position = offset;
            DataStructureSerializer dss = new DataStructureSerializer();
            position = dss.serialize(buffer, position, ds);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ItemStructure is) {
            int position = offset;
            ItemStructureSerializer iss = new ItemStructureSerializer();
            position = iss.serialize(buffer, position, is.getDataStructure());

            return position;
        }

        protected ItemStructure deserialize(Buffer buffer, int offset){
            int position = offset;
            DataStructureSerializer dss = new DataStructureSerializer();

            DataStructure ds = dss.deserialize(buffer, position);
            return RMObjectFactory.newItemStructure(ds);
        }
    }

    public static class ItemSingleSerializer {

        protected int serialize(Buffer buffer, int offset, ItemStructure is,
                Element item) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            ItemStructureSerializer iss = new ItemStructureSerializer();
            ElementSerializer es = new ElementSerializer();

            header = writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, is);

            writeHeader(buffer, header, position);
            position = es.serialize(buffer, position, item);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ItemSingle is) {
            int position = offset;
            ItemSingleSerializer iss = new ItemSingleSerializer();

            position = iss.serialize(buffer, position, is.getItemStructure(),
                    is.getItem());

            return position;
        }

        protected ItemSingle deserialize(Buffer buffer, int offset){
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
                List<Cluster> rows) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ClusterSerializer cs = new ClusterSerializer();

            header = writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, is);

            writeHeader(buffer, header, position);
            position = cs.listSerialize(buffer, position, rows);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ItemTable it) {
            int position = offset;
            ItemTableSerializer its = new ItemTableSerializer();

            position = its.serialize(buffer, position,
                    it.getItemStructure(), it.getRows());

            return position;
        }

        protected ItemTable deserialize(Buffer buffer, int offset){
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
                List<Item> items) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize() +
                    BOOLEAN.getSize();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ItemSerializer isr = new ItemSerializer();

            header = writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, is);

            boolean hasItems = items != null;
            if (hasItems){
                writeHeader(buffer, header, hasItems, position);
                position = isr.listSerialize(buffer, position, items);
            } else {
                writeHeader(buffer, header, hasItems);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                ItemTree it) {
            int position = offset;
            ItemTreeSerializer its = new ItemTreeSerializer();

            position = its.serialize(buffer, position, it.getItemStructure(),
                    it.getItems());

            return position;
        }

        protected ItemTree deserialize(Buffer buffer, int offset){
            int position = offset;
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ItemSerializer isr = new ItemSerializer();

            int itemStructurePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemStructure is = iss.deserialize(buffer, itemStructurePosition);

            boolean hasItems = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<Item> items = null;
            if (hasItems){
                int itemsPosition = buffer.readInteger(position);
                items = isr.deserializeList(buffer, itemsPosition);
            }

            return RMObjectFactory.newItemTree(is, items);
        }
    }

    public static class PartyIdentitySerializer {

        protected int serialize(Buffer buffer, int offset, Locatable locatable,
                ItemStructure details) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, details);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartyIdentity pi) {
            int position = offset;
            PartyIdentitySerializer pis = new PartyIdentitySerializer();

            position = pis.serialize(buffer, position,
                    pi.getLocatable(), pi.getDetails());

            return position;
        }

        protected PartyIdentity deserialize(Buffer buffer, int offset){
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
                Set<PartyIdentity> piSet) {
            int setSize = piSet.size();
            int position = offset + (setSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            int header = offset;
            PartyIdentitySerializer pis = new PartyIdentitySerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<PartyIdentity> it = piSet.iterator();

            while (it.hasNext()){
                PartyIdentity pi = it.next();
                int linkPosition = position;
                header = writeHeader(buffer, header, linkPosition);
                position = pis.serialize(buffer, position, pi);
            }

            return position;
        }

        protected Set<PartyIdentity> setDeserializer(Buffer buffer,
                int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            PartyIdentitySerializer pis = new PartyIdentitySerializer();
            Set<PartyIdentity> piSet = new HashSet<>();

            for (int i = 0; i < listSize; i++){
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
                                ObjectRef target) {
            int header = offset;
            int position = offset + 5 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            header = writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, details);

            header = writeHeader(buffer, header, position);
            position = ors.serialize(buffer, position, source);

            writeHeader(buffer, header, position);
            position = ors.serialize(buffer, position, target);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                PartyRelationship pr) {
            PartyRelationshipSerializer prs = new PartyRelationshipSerializer();
            int position = offset;
            position = prs.serialize(buffer, position, pr.getLocatable(),
                    pr.getDetails(), pr.getSource(), pr.getTarget());

            return position;
        }

        protected PartyRelationship deserialize(Buffer buffer, int offset){

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
                 {
            int setSize = prSet.size();
            int position = offset + (setSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            int header = offset;
            PartyRelationshipSerializer pis = new PartyRelationshipSerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<PartyRelationship> it = prSet.iterator();

            while (it.hasNext()){
                PartyRelationship pr = it.next();
                int linkPosition = position;
                header = writeHeader(buffer, header, linkPosition);
                position = pis.serialize(buffer, position, pr);
            }

            return position;
        }

        protected Set<PartyRelationship> setDeserializer(Buffer buffer,
                int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            PartyRelationshipSerializer prs = new PartyRelationshipSerializer();
            Set<PartyRelationship> prSet = new HashSet<>();

            for (int i = 0; i < listSize; i++){
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
                ItemStructure details) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, details);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Address a) {
            int position = offset;
            AddressSerializer as = new AddressSerializer();

            position = as.serialize(buffer, position, a.getLocatable(),
                    a.getDetails());

            return position;
        }

        protected Address deserialize(Buffer buffer, int offset){
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
                 {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            AddressSerializer dis = new AddressSerializer();

            for (Address d : items){
                header = writeHeader(buffer, header, position);
                position = dis.serialize(buffer, position, d);
            }

            return position;
        }

        protected List<Address> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Address> list = new ArrayList<>();
            AddressSerializer as = new AddressSerializer();

            for (int i = 0; i < listSize; i++){
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
                                List<Address> addresses) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            AddressSerializer as = new AddressSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            //DvInterval<DvDate> timeValidity todo
            writeHeader(buffer, header, position);
            position = as.listSerialize(buffer, position, addresses);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Contact c) {
            int position = offset;
            ContactSerializer cs = new ContactSerializer();

            position = cs.serialize(buffer, position, c.getLocatable(), c.getAddresses());

            return position;
        }

        protected Contact deserialize(Buffer buffer, int offset){
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
                Set<Contact> contacts) {
            int setSize = contacts.size();
            int position = offset + (setSize * PrimitiveTypeSize.INT.getSize())
                    + PrimitiveTypeSize.INT.getSize();
            int header = offset;
            ContactSerializer cs = new ContactSerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<Contact> it = contacts.iterator();

            while (it.hasNext()){
                Contact c = it.next();
                int contactPosition = position;
                header = writeHeader(buffer, header, contactPosition);
                position = cs.serialize(buffer, position, c);
            }

            return position;
        }

        protected Set<Contact> setDeserializer(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            ContactSerializer cs = new ContactSerializer();
            Set<Contact> contacts = new HashSet<>();

            for (int i = 0; i < listSize; i++){
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
                ItemStructure details) {
            int header = offset;
            int position = offset + 6 *
                    PrimitiveTypeSize.INT.getSize()+
                    BOOLEAN.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            ContactSerializer cs = new ContactSerializer();
            PartyIdentitySerializer pis = new PartyIdentitySerializer();
            LocatableRefSerializer lrs = new LocatableRefSerializer();
            PartyRelationshipSerializer prs = new PartyRelationshipSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            header = writeHeader(buffer, header, position);
            position = pis.setSerializer(buffer, position, identities);

            header = writeHeader(buffer, header, position);
            position = cs.setSerializer(buffer, position, contacts);

            header = writeHeader(buffer, header, position);
            position = prs.setSerializer(buffer, position, relationships);

            header = writeHeader(buffer, header, position);
            position = lrs.setSerializer(
                    buffer, position, reverseRelationships);

            boolean hasDetails = details != null;
            if (hasDetails){
                writeHeader(buffer, header, hasDetails, position);
                position = iss.serialize(buffer, position, details);
            } else {
                writeHeader(buffer, header, hasDetails);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Party p) {
            int position = offset;
            PartySerializer ps = new PartySerializer();

            position = ps.serialize(buffer, position, p.getLocatable(),
                    p.getIdentities(), p.getContacts(), p.getRelationships(),
                    p.getReverseRelationships(), p.getDetails());

            return position;
        }

        protected Party deserialize(Buffer buffer, int offset){
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
            position += BOOLEAN.getSize();
            ItemStructure details = null;
            if(hasDetails){
                int detailsPosition = buffer.readInteger(position);
                details = iss.deserialize(buffer, detailsPosition);
            }
            
            return RMObjectFactory.newParty(locatable, identities, 
                    contacts, relationships, reverseRelationships, details);
        }

        protected int setSerializer(Buffer buffer, int offset,
                                    Set<Party> items) {
            int setSize = items.size();
            int position = offset + (setSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            int header = offset;
            PartySerializer ps = new PartySerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<Party> it = items.iterator();

            while (it.hasNext()){
                Party p = it.next();
                int partyPosition = position;
                header = writeHeader(buffer, header, partyPosition);
                position = ps.serialize(buffer, position, p);
            }

            return position;
        }

        protected Set<Party> setDeserializer(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            PartySerializer des = new PartySerializer();
            Set<Party> parties = new HashSet<>();

            for (int i = 0; i < listSize; i++){
                int partyPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                Party p = des.deserialize(buffer, partyPosition);
                parties.add(p);
            }

            return parties;
        }
    }
    
    public static class CapabilitySerializer {

        protected int serialize(Buffer buffer, int offset, Locatable credentials,
                ItemStructure details) {
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, credentials);

            writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, details);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                Capability c) {
            int position = offset;
            CapabilitySerializer cs = new CapabilitySerializer();

            position = cs.serialize(buffer, position, c.getLocatable(),
                    c.getCredentials());

            return position;
        }

        protected Capability deserialize(Buffer buffer, int offset){
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
                 {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            CapabilitySerializer cs = new CapabilitySerializer();

            for (Capability c : items){
                header = writeHeader(buffer, header, position);
                position = cs.serialize(buffer, position, c);
            }

            return position;
        }

        protected List<Capability> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Capability> list = new ArrayList<>();
            CapabilitySerializer cs = new CapabilitySerializer();

            for (int i = 0; i < listSize; i++){
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
                                PartyRef performer) {
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize() +
                    2 * PrimitiveTypeSize.BOOLEAN.getSize();

            PartySerializer ps = new PartySerializer();
            CapabilitySerializer cs = new CapabilitySerializer();
            PartyRefSerializer prs = new PartyRefSerializer();

            header = writeHeader(buffer, header, position);
            position = ps.serialize(buffer, position, party);

            boolean hasCapabilities = capabilities != null;
            if(hasCapabilities){
                header = writeHeader(buffer, header, hasCapabilities, position);
                position = cs.listSerialize(buffer, position, capabilities);
            } else {
                header = writeHeader(buffer, header, hasCapabilities);
            }

            //TODO DvInterval<DvDate> timeValidity

            writeHeader(buffer, header, position);
            position = prs.serialize(buffer, position, performer);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Role r) {
            int position = offset;
            RoleSerializer rs = new RoleSerializer();

            position = rs.serialize(buffer, position, r.getParty(),
                    r.getCapabilities(), r.getPerformer());

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

            return RMObjectFactory.newRole(party, capabilities, null,
                    performer);
        }
        
        protected int setSerializer(Buffer buffer, int offset,
                Set<Role> roles) {
            int setSize = roles.size();
            int position = offset + (setSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            int header = offset;
            RoleSerializer rs = new RoleSerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<Role> it = roles.iterator();

            while (it.hasNext()){
                Role role = it.next();
                int linkPosition = position;
                header = writeHeader(buffer, header, linkPosition);
                position = rs.serialize(buffer, position, role);
            }

            return position;
        }

        protected Set<Role> setDeserializer(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            RoleSerializer rs = new RoleSerializer();
            Set<Role> roles = new HashSet<>();

            for (int i = 0; i < listSize; i++){
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
                Set<DvText> languages){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();
            
            PartySerializer ps = new PartySerializer();
            RoleSerializer rs = new RoleSerializer();
            DvTextSerializer dts = new DvTextSerializer();
            
            header = writeHeader(buffer, header, position);
            position = ps.serialize(buffer, position, party);
            
            header = writeHeader(buffer, header, position);
            position = rs.setSerializer(buffer, position, roles);
            
            writeHeader(buffer, header, position);
            position = dts.setSerializer(buffer, position, languages);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Actor a) {
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            position = as.serialize(buffer, position, a.getParty(),
                    a.getRoles(), a.getLanguages());
            
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
                Actor actor) {
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            position = as.serialize(buffer, position, actor);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Agent agent) {
            int position = offset;
            AgentSerializer as = new AgentSerializer();
            
            position = as.serialize(buffer, position, agent.getActor());
            
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
                Actor actor) {
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            position = as.serialize(buffer, position, actor);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Group group) {
            int position = offset;
            GroupSerializer gs = new GroupSerializer();
            
            position = gs.serialize(buffer, position, group.getActor());
            
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
                Actor actor) {
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            position = as.serialize(buffer, position, actor);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Organisation organisation) {
            int position = offset;
            OrganisationSerializer os = new OrganisationSerializer();
            
            position = os.serialize(buffer, position, organisation.getActor());
            
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
                Actor a) {
            int position = offset;
            ActorSerializer as = new ActorSerializer();
            
            position = as.serialize(buffer, position, a);
            
            return position;
        }
        
         protected int serialize(Buffer buffer, int offset, 
                Person p) {
            int position = offset;
            PersonSerializer ps = new PersonSerializer();
            
            position = ps.serialize(buffer, position, p.getActor());
            
            return position;
        }
         
         protected Person deserialize(Buffer buffer, int offset){
             int position = offset;
             ActorSerializer as = new ActorSerializer();

             Actor actor = as.deserialize(buffer, position);

             return RMObjectFactory.newPerson(actor);
         }
    }
    
    public static class InstructionDetailsSerializer {
        protected int serialize(Buffer buffer, int offset, 
                LocatableRef instructionId, String activityId, 
                ItemStructure wfDetails) {
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize() +
                    BOOLEAN.getSize();
            
            LocatableRefSerializer lrs = new LocatableRefSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            
            header = writeHeader(buffer, header, position);
            position = lrs.serialize(buffer, position, instructionId);
            
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, activityId);
            
            boolean hasWfDetails = wfDetails != null;
            if(hasWfDetails){
                writeHeader(buffer, header, hasWfDetails, position);
                position = iss.serialize(buffer, position, wfDetails);
            } else {
                 writeHeader(buffer, header, hasWfDetails);
            }
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                InstructionDetails id) {
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
            position += BOOLEAN.getSize();
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
                DvCodedText careflowStep){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize() +
                    2 * BOOLEAN.getSize();
            
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            
            header = writeHeader(buffer, header, position);
            position = dcs.serialize(buffer, position, currentState);
            
            boolean hasTransition = transition != null;
            if(hasTransition){
                header = writeHeader(buffer, header, hasTransition, position);
                position = dcs.serialize(buffer, position, transition);
            } else {
                header = writeHeader(buffer, header, hasTransition);
            }
            
            boolean hasCareflowStep = careflowStep != null;
            if(hasCareflowStep){
                writeHeader(buffer, header, hasCareflowStep, position);
                position = dcs.serialize(buffer, position, careflowStep);
            } else {
                 writeHeader(buffer, header, hasCareflowStep);
            }
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                ISMTransition ism){
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
            position += BOOLEAN.getSize();
            DvCodedText transition = null;
            if(hasTransition){
                int transitionPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                transition = dcs.deserialize(buffer, transitionPosition);
            }
            
            boolean hasCareflowStep = buffer.readBoolean(position);
            position += BOOLEAN.getSize();
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
                String actionArchetypeId){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize();
            
            LocatableSerializer ls = new LocatableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            DvParsableSerializer dps = new DvParsableSerializer();
            
            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);
            
            header = writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, description);
            
            header = writeHeader(buffer, header, position);
            position = dps.serialize(buffer, position, timing);
            
            writeHeader(buffer, header, position);
            position = stringSerialization(
                    buffer, position, actionArchetypeId);
            
            return position;
        }
        
        protected int serialize(Buffer buffer, int offset, 
                Activity a){
            int position = offset;
            ActivitySerializer as = new ActivitySerializer();
            
            position = as.serialize(buffer, position, a.getLocatable(),
                    a.getDescription(), a.getTiming(), a.getActionArchetypeId());
            
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
            String actionArchetypeId = stringDeserialization(
                    buffer, actionArchetypeIdPosition);
            
            return RMObjectFactory.newActivity(locatable, description, 
                    timing, actionArchetypeId);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<Activity> items)
        {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            ActivitySerializer as = new ActivitySerializer();

            for (Activity a : items){
                header = writeHeader(buffer, header, position);
                position = as.serialize(buffer, position, a);
            }

            return position;
        }

        protected List<Activity> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Activity> list = new ArrayList<>();
            ActivitySerializer dis = new ActivitySerializer();

            for (int i = 0; i < listSize; i++){
                int activityPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                Activity a = dis.deserialize(buffer, activityPosition);
                list.add(a);
            }

            return list;
        }
    }

    public static class IntervalSerializer {
        protected int serialize(Buffer buffer, int offset, DvOrdered lower,
                                DvOrdered upper){
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize() +
                    2 * PrimitiveTypeSize.BOOLEAN.getSize();
            int header = offset;

            boolean hasLower = lower != null;
            boolean hasUpper = upper != null;

            DvOrderedSerializer ds = new DvOrderedSerializer();
            header = writeHeader(buffer, header, hasLower, position);
            position = ds.serialize(buffer, position, lower);

            writeHeader(buffer, header, hasUpper, position);
            position = ds.serialize(buffer, position, upper);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Interval interval){
            int position = offset;

            IntervalSerializer is = new IntervalSerializer();
            position = is.serialize(buffer, position, interval.getLower(),
                    interval.getUpper());

            return position;
        }

        protected Interval deserialize(Buffer buffer, int offset){
            int position = offset;

            DvOrderedSerializer dos = new DvOrderedSerializer();

            boolean hasLower = buffer.readBoolean(offset);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvOrdered lower = null;
            if(hasLower){
                int lowerPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                lower = dos.deserialize(buffer, lowerPosition);
            }

            boolean hasUpper = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvOrdered upper = null;
            if(hasUpper){
                int upperPosition = buffer.readInteger(position);
                upper = dos.deserialize(buffer, upperPosition);
            }

            return RMObjectFactory.newInterval(lower, upper);
        }
    }

    public static class DvIntervalSerializer {
        protected int serialize(Buffer buffer, int offset, DvOrdered lower,
                                DvOrdered upper){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize() +
                    2 * PrimitiveTypeSize.BOOLEAN.getSize();
            boolean hasLower = lower != null;
            boolean hasUpper = upper != null;

            if(lower == null && upper == null){
                return offset;
            }

            DvOrderedSerializer dos = new DvOrderedSerializer();
            header = writeHeader(buffer, header, hasLower, position);
            position = dos.serialize(buffer, position, lower);

            writeHeader(buffer, header, hasUpper, position);
            position = dos.serialize(buffer, position, upper);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvInterval dvInterval){
            int position = offset;
            DvIntervalSerializer dis = new DvIntervalSerializer();
            if(dvInterval == null){
                return offset;
            }
            if(dvInterval.getInterval().getLower() == null ||
                    dvInterval.getInterval().getUpper() == null){
                return position;
            }
            position = dis.serialize(buffer, position, dvInterval.getInterval().
                    getLower(), dvInterval.getInterval().getUpper());

            return position;
        }

        protected DvInterval deserialize(Buffer buffer, int offset){
            int position = offset;

            DvOrderedSerializer dos = new DvOrderedSerializer();
            boolean hasLower = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            DvOrdered lower = null;
            if(hasLower){
                int lowerPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                lower = dos.deserialize(buffer, lowerPosition);
            }
            boolean hasUpper = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            DvOrdered upper = null;
            if(hasUpper){
                int upperPosition = buffer.readInteger(position);
                upper = dos.deserialize(buffer, upperPosition);
            }

            return RMObjectFactory.newDvInterval(lower, upper);
        }
    }

    public static class ReferenceRangeSerializer {
        protected int serialize(Buffer buffer, int offset, DvText meaning,
                                DvInterval range){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            DvIntervalSerializer dis = new DvIntervalSerializer();
            DvTextSerializer dts = new DvTextSerializer();

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, meaning);

            writeHeader(buffer, header, position);
            position = dis.serialize(buffer, position, range);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, ReferenceRange range){
            int position = offset;
            ReferenceRangeSerializer rrs = new ReferenceRangeSerializer();

            if(range == null){
                return position;
            }

            position = rrs.serialize(buffer, position, range.getMeaning(),
                    range.getRange());

            return position;
        }

        protected ReferenceRange deserialize(Buffer buffer, int offset){
            int position = offset;
            DvIntervalSerializer dis = new DvIntervalSerializer();
            DvTextSerializer dts = new DvTextSerializer();

            int meaningPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvText meaning = dts.deserialize(buffer, meaningPosition);

            int rangePosition = buffer.readInteger(position);
            DvInterval range = dis.deserialize(buffer, rangePosition);

            return RMObjectFactory.newReferenceRange(meaning, range);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<ReferenceRange> items) {
            if(items.size() > 0 && (!items.get(0).getRange().getInterval().
                    isUpperIncluded() && !items.get(0).getRange().getInterval().
                    isLowerIncluded())){
                return offset;
            }
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            ReferenceRangeSerializer rrs = new ReferenceRangeSerializer();

            for (ReferenceRange r : items){
                header = writeHeader(buffer, header, position);
                position = rrs.serialize(buffer, position, r);
            }

            return position;
        }

        protected List<ReferenceRange> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<ReferenceRange> list = new ArrayList<>();
            ReferenceRangeSerializer rrs = new ReferenceRangeSerializer();

            for (int i = 0; i < listSize; i++){
                int referenceRangePosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                ReferenceRange r = rrs.deserialize(buffer, referenceRangePosition);
                list.add(r);
            }

            return list;
        }
    }

    public static class DvOrderedSerializer {
        protected int serialize(Buffer buffer, int offset,
                                List<ReferenceRange> otherReferenceRanges,
                                DvInterval normalRange,
                                CodePhrase normalStatus){
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize() +
                    3 * PrimitiveTypeSize.BOOLEAN.getSize();
            int header = offset;

            boolean hasOtherReferenceRanges = otherReferenceRanges != null;
            boolean hasNormalRange = normalRange != null;
            boolean hasNormalStatus = normalStatus != null;

            ReferenceRangeSerializer rrs = new ReferenceRangeSerializer();
            DvIntervalSerializer dis = new DvIntervalSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();

            header = writeHeader(buffer, header, hasOtherReferenceRanges, position);
            if(hasOtherReferenceRanges){
                position = rrs.listSerialize(buffer, position, otherReferenceRanges);
            }

            header = writeHeader(buffer, header, hasNormalRange, position);
            if(hasNormalRange){
                position = dis.serialize(buffer, position, normalRange);
            }

            writeHeader(buffer, header, hasNormalStatus, position);
            if(hasNormalStatus){
                position = cps.serialize(buffer, position, normalStatus);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvOrdered dvOrdered){
            int position = offset;
            DvOrderedSerializer dos = new DvOrderedSerializer();

            position = dos.serialize(buffer, position,
                    dvOrdered.getOtherReferenceRanges(),
                    dvOrdered.getNormalRange(),
                    dvOrdered.getNormalStatus());

            return position;
        }

        protected DvOrdered deserialize(Buffer buffer, int offset){
            int position = offset;

            ReferenceRangeSerializer rrs = new ReferenceRangeSerializer();
            DvIntervalSerializer dis = new DvIntervalSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();

            boolean hasOtherReferenceRanges = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<ReferenceRange> otherReferenceRanges = null;
            if(hasOtherReferenceRanges){
                int otherReferenceRangesPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                otherReferenceRanges = rrs.deserializeList(buffer,
                        otherReferenceRangesPosition);
            }

            boolean hasNormalRange = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvInterval normalRange = null;
            if(hasNormalRange){
                int normalRangePosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                normalRange = dis.deserialize(buffer, normalRangePosition);
            }

            boolean hasNormalStatus = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            CodePhrase normalStatus = null;
            if(hasNormalStatus){
                int normalStatusPosition = buffer.readInteger(position);
                normalStatus = cps.deserialize(buffer, normalStatusPosition);
            }


            return RMObjectFactory.newDvOrdered(otherReferenceRanges,
                    normalRange, normalStatus);
        }
    }

    public static class DvProportionSerializer {
        protected int serialize(Buffer buffer, int offset, DvAmount dvAmount,
                                double numerator, double denominator,
                                ProportionKind type, int precision){
            int header = offset;
            int position = offset + 5 * PrimitiveTypeSize.INT.getSize();

            DvAmountSerializer das = new DvAmountSerializer();
            ProportionKindSerializer pks = new ProportionKindSerializer();

            header = writeHeader(buffer, header ,position);
            position = das.serialize(buffer, position, dvAmount);

            header = writeHeader(buffer, header, position);
            buffer.writeDouble(position, numerator);
            position += PrimitiveTypeSize.DOUBLE.getSize();

            header = writeHeader(buffer, header, position);
            buffer.writeDouble(position, denominator);
            position += PrimitiveTypeSize.DOUBLE.getSize();

            header = writeHeader(buffer, header, position);
            position = pks.serialize(buffer, position, type);

            writeHeader(buffer, header, position);
            buffer.writeInteger(position, precision);
            position += PrimitiveTypeSize.INT.getSize();

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvProportion d){
            int position = offset;
            DvProportionSerializer dps = new DvProportionSerializer();

            position = dps.serialize(buffer, position, d.getDvAmount(),
                    d.getNumerator(), d.getDenominator(), d.getType(),
                    d.getPrecision());

            return position;
        }

        protected DvProportion deserialize(Buffer buffer, int position){
            DvAmountSerializer das = new DvAmountSerializer();
            ProportionKindSerializer pks = new ProportionKindSerializer();

            int dvAmountPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvAmount dvAmount = das.deserialize(buffer, dvAmountPosition);

            int numeratorPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            double numerator = buffer.readDouble(numeratorPosition);

            int denominatorPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            double denominator = buffer.readDouble(denominatorPosition);

            int typePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ProportionKind type = pks.deserialize(buffer, typePosition);

            int precisionPosition = buffer.readInteger(position);
            int precision = buffer.readInteger(precisionPosition);

            return RMObjectFactory.newDvProportion(dvAmount, numerator,
                    denominator, type, precision);
        }
    }

    public static class DvQuantitySerializer {
        protected int serialize(Buffer buffer, int offset, DvAmount dvAmount,
                                String units, double magnitude, int precision){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize();

            DvAmountSerializer das = new DvAmountSerializer();
            header = writeHeader(buffer, header, position);
            position = das.serialize(buffer, position, dvAmount);

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, units);

            header = writeHeader(buffer, header, position);
            buffer.writeDouble(position, magnitude);
            position += PrimitiveTypeSize.DOUBLE.getSize();

            writeHeader(buffer, header, position);
            buffer.writeInteger(position, precision);
            position += PrimitiveTypeSize.INT.getSize();

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvQuantity d){
            int position = offset;
            DvQuantitySerializer dqs = new DvQuantitySerializer();
            position = dqs.serialize(buffer, position, d.getDvAmount(),
                    d.getUnits(), d.getMagnitude(), d.getPrecision());

            return position;
        }

        protected DvQuantity deserialize(Buffer buffer, int offset){
            int position = offset;
            DvAmountSerializer das = new DvAmountSerializer();

            int dvAmountSerialize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvAmount dvAmount = das.deserialize(buffer, dvAmountSerialize);

            int unitsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String units = stringDeserialization(buffer, unitsPosition);

            int magnitudePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            double magnitude = buffer.readDouble(magnitudePosition);

            int precisionPosition = buffer.readInteger(position);
            int precision = buffer.readInteger(precisionPosition);

            return RMObjectFactory.newDvQuantity(dvAmount, units, magnitude,
                    precision);
        }
    }

    public static class DvDurationSerializer {
        protected int serialize(Buffer buffer, int offset, DvAmount dvAmount,
                                String value){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            DvAmountSerializer das = new DvAmountSerializer();

            header = writeHeader(buffer, header, position);
            position = das.serialize(buffer, position, dvAmount);

            writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, value);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvDuration d){
            int position = offset;

            DvDurationSerializer dds = new DvDurationSerializer();

            position = dds.serialize(buffer, position, d.getDvAmount(),
                    d.getValue());

            return position;
        }

        protected DvDuration deserialize(Buffer buffer, int offset){
            int position = offset;

            DvAmountSerializer das = new DvAmountSerializer();

            int dvAmountPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvAmount dvAmount = das.deserialize(buffer, dvAmountPosition);

            int valuePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String value = stringDeserialization(buffer, valuePosition);

            return RMObjectFactory.newDvDuration(dvAmount, value);
        }
    }

    public static class DvAbsoluteQuantitySerializer {
        protected int serialize(Buffer buffer, int offset,
                                DvAbsoluteQuantityWithDvCount d){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            DvQuantifiedSerializer dqs = new DvQuantifiedSerializer();
            DvCountSerializer dcs = new DvCountSerializer();

            header = writeHeader(buffer, header, position);
            position = dqs.serialize(buffer, position, d.getDvQuantified());

            writeHeader(buffer, header, position);
            position = dcs.serialize(buffer, position, d.getDvCount());

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                DvAbsoluteQuantityWithDvDuration d){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            DvQuantifiedSerializer dfs = new DvQuantifiedSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();

            header = writeHeader(buffer, header, position);
            position = dfs.serialize(buffer, position, d.getDvQuantified());

            writeHeader(buffer, header, position);
            position = dds.serialize(buffer, position, d.getDvDuration());

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                DvAbsoluteQuantityWithDvProportion d){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            DvQuantifiedSerializer dfs = new DvQuantifiedSerializer();
            DvProportionSerializer dps = new DvProportionSerializer();

            header = writeHeader(buffer, header, position);
            position = dfs.serialize(buffer, position, d.getDvQuantified());

            writeHeader(buffer, header, position);
            position = dps.serialize(buffer, position, d.getDvProportion());

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                DvAbsoluteQuantityWithDvQuantity d){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            DvQuantifiedSerializer dfs = new DvQuantifiedSerializer();
            DvQuantitySerializer dqs = new DvQuantitySerializer();

            header = writeHeader(buffer, header, position);
            position = dfs.serialize(buffer, position, d.getDvQuantified());

            writeHeader(buffer, header, position);
            position = dqs.serialize(buffer, position, d.getDvQuantity());

            return position;
        }

        protected DvAbsoluteQuantityWithDvCount deserializeDvCount(Buffer buffer,
                                                            int offset){
            int position = offset;

            DvQuantifiedSerializer dfs = new DvQuantifiedSerializer();
            DvCountSerializer dcs = new DvCountSerializer();

            int dvQuantifiedPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvQuantified dvQuantified = dfs.deserialize(buffer,
                    dvQuantifiedPosition);

            int dvCountPosition = buffer.readInteger(position);
            DvCount dvCount = dcs.deserialize(buffer, dvCountPosition);

            return RMObjectFactory.newDvAbsoluteQuantity(dvQuantified, dvCount);
        }

        protected DvAbsoluteQuantityWithDvDuration deserializeDvDuration(
                Buffer buffer, int offset){
            int position = offset;

            DvQuantifiedSerializer dfs = new DvQuantifiedSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();

            int dvQuantifiedPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvQuantified dvQuantified = dfs.deserialize(buffer,
                    dvQuantifiedPosition);

            int dvDurationPosition = buffer.readInteger(position);
            DvDuration dvDuration = dds.deserialize(buffer, dvDurationPosition);

            return RMObjectFactory.newDvAbsoluteQuantity(dvQuantified,
                    dvDuration);
        }

        protected DvAbsoluteQuantityWithDvProportion deserializeDvProportion(
                Buffer buffer, int offset){
            int position = offset;

            DvQuantifiedSerializer dfs = new DvQuantifiedSerializer();
            DvProportionSerializer dps = new DvProportionSerializer();

            int dvQuantifiedPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvQuantified dvQuantified = dfs.deserialize(buffer,
                    dvQuantifiedPosition);

            int dvProportionPosition = buffer.readInteger(position);
            DvProportion dvProportion= dps.deserialize(buffer, dvProportionPosition);

            return RMObjectFactory.newDvAbsoluteQuantity(dvQuantified,
                    dvProportion);
        }

        protected DvAbsoluteQuantityWithDvQuantity deserializeDvQuantity(
                Buffer buffer, int offset){
            int position = offset;

            DvQuantifiedSerializer dfs = new DvQuantifiedSerializer();
            DvQuantitySerializer dqs = new DvQuantitySerializer();

            int dvQuantifiedPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvQuantified dvQuantified = dfs.deserialize(buffer,
                    dvQuantifiedPosition);

            int dvQuantityPosition = buffer.readInteger(position);
            DvQuantity dvQuantity = dqs.deserialize(buffer, dvQuantityPosition);

            return RMObjectFactory.newDvAbsoluteQuantity(dvQuantified,
                    dvQuantity);
        }
    }

    public static class DvDateSerializer {
        protected int serialize(Buffer buffer, int offset, boolean dayKnown,
                                boolean monthKnown, boolean isPartial,
                                DvTemporal dvTemporal){
            int position = offset;
            DvTemporalSerializer dts = new DvTemporalSerializer();

            buffer.writeBoolean(position, dayKnown);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            buffer.writeBoolean(position, monthKnown);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            buffer.writeBoolean(position, isPartial);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            position = dts.serialize(buffer, position, dvTemporal);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvDate dvDate){
            int position = offset;

            DvDateSerializer dds = new DvDateSerializer();

            position = dds.serialize(buffer, position, dvDate.isDayKnown(),
                    dvDate.isMonthKnown(), dvDate.isPartial(),
                    dvDate.getDvTemporal());

            return position;
        }

        protected DvDate deserialize(Buffer buffer, int offset){
            int position = offset;

            DvTemporalSerializer dts = new DvTemporalSerializer();

            boolean dayKnown = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            boolean monthKnown = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            boolean isPartial = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            DvTemporal dvTemporal = dts.deserialize(buffer, position);

            return RMObjectFactory.newDvDate(dayKnown, monthKnown, isPartial,
                    dvTemporal);

        }
    }

    public static class DvTemporalSerializer {
        protected int serialize(Buffer buffer, int offset,
                                DvAbsoluteQuantityWithDvDuration dvAbsoluteQuantity,
                                String value){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            DvAbsoluteQuantitySerializer das = new DvAbsoluteQuantitySerializer();

            header = writeHeader(buffer, header, position);
            position = das.serialize(buffer, position, dvAbsoluteQuantity);

            writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, value);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvTemporal dvTemporal){
           int position = offset;

            DvTemporalSerializer das = new DvTemporalSerializer();

            position = das.serialize(buffer, position,
                    dvTemporal.getDvAbsoluteQuantity(), dvTemporal.getValue());

            return position;
        }

        protected DvTemporal deserialize(Buffer buffer, int offset){
            int position = offset;

            int dvAbsoluteQuantityPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvAbsoluteQuantitySerializer das = new DvAbsoluteQuantitySerializer();

            DvAbsoluteQuantityWithDvDuration d = das.deserializeDvDuration(buffer,
                    dvAbsoluteQuantityPosition);

            int valuePosition = buffer.readInteger(position);
            String value = stringDeserialization(buffer, valuePosition);

            return RMObjectFactory.newDvTemporal(d, value);
        }

    }


    public static class DvTimeSerializer {
        protected int serialize(Buffer buffer, int offset, boolean isPartial,
                                boolean minuteKnown, boolean secondKnown,
                                boolean fractionalSecKnown,
                                DvTemporal dvTemporal){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            DvTemporalSerializer dts = new DvTemporalSerializer();

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, dvTemporal);

            writeHeader(buffer, header, position);

            buffer.writeBoolean(position, isPartial);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            buffer.writeBoolean(position, minuteKnown);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            buffer.writeBoolean(position, secondKnown);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            buffer.writeBoolean(position, fractionalSecKnown);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvTime dvTime){
            int position = offset;
            DvTimeSerializer dds = new DvTimeSerializer();

            position = dds.serialize(buffer, position, dvTime.isPartial(),
                    dvTime.isMinuteKnown(), dvTime.isSecondKnown(),
                    dvTime.isFractionalSecKnown(), dvTime.getDvTemporal());

            return position;
        }

        protected DvTime deserialize(Buffer buffer, int offset){
            int position = offset;

            DvTemporalSerializer dts = new DvTemporalSerializer();
            DvDateSerializer dds = new DvDateSerializer();

            int dvTemporalPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvTemporal dvTemporal = dts.deserialize(buffer, dvTemporalPosition);

            int firstBooleanPosition = buffer.readInteger(position);

            boolean isPartial = buffer.readBoolean(firstBooleanPosition);
            firstBooleanPosition += PrimitiveTypeSize.BOOLEAN.getSize();

            boolean minuteKnown = buffer.readBoolean(firstBooleanPosition);
            firstBooleanPosition += PrimitiveTypeSize.BOOLEAN.getSize();

            boolean secondKnown = buffer.readBoolean(firstBooleanPosition);
            firstBooleanPosition += PrimitiveTypeSize.BOOLEAN.getSize();

            boolean fractionalSecKnown = buffer.readBoolean(firstBooleanPosition);

            return RMObjectFactory.newDvTime(isPartial, minuteKnown,
                    secondKnown, fractionalSecKnown, dvTemporal);

        }
    }

    public static class DvDateTimeSerializer {
        protected int serialize(Buffer buffer, int offset, boolean isPartial,
                                boolean minuteKnown, boolean secondKnown,
                                boolean fractionalSecKnown,
                                DvTemporal dvTemporal, DvDate dateTime){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();
            DvTemporalSerializer dts = new DvTemporalSerializer();
            DvDateSerializer dds = new DvDateSerializer();

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, dvTemporal);

            header = writeHeader(buffer, header, position);
            position = dds.serialize(buffer, position, dateTime);

            writeHeader(buffer, header, position);

            buffer.writeBoolean(position, isPartial);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            buffer.writeBoolean(position, minuteKnown);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            buffer.writeBoolean(position, secondKnown);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            buffer.writeBoolean(position, fractionalSecKnown);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvDateTime dvDateTime){
            int position = offset;
            DvDateTimeSerializer dds = new DvDateTimeSerializer();

            position = dds.serialize(buffer, position, dvDateTime.isPartial(),
                    dvDateTime.isMinuteKnown(), dvDateTime.isSecondKnown(),
                    dvDateTime.isFractionalSecKnown(), dvDateTime.getDvTemporal(),
                    dvDateTime.getDateTime());

            return position;
        }

        protected DvDateTime deserialize(Buffer buffer, int offset){
            int position = offset;

            DvTemporalSerializer dts = new DvTemporalSerializer();
            DvDateSerializer dds = new DvDateSerializer();

            int dvTemporalPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvTemporal dvTemporal = dts.deserialize(buffer, dvTemporalPosition);

            int dateTimePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDate dateTime = dds.deserialize(buffer, dateTimePosition);

            int firstBooleanPosition = buffer.readInteger(position);

            boolean isPartial = buffer.readBoolean(firstBooleanPosition);
            firstBooleanPosition += PrimitiveTypeSize.BOOLEAN.getSize();

            boolean minuteKnown = buffer.readBoolean(firstBooleanPosition);
            firstBooleanPosition += PrimitiveTypeSize.BOOLEAN.getSize();

            boolean secondKnown = buffer.readBoolean(firstBooleanPosition);
            firstBooleanPosition += PrimitiveTypeSize.BOOLEAN.getSize();

            boolean fractionalSecKnown = buffer.readBoolean(firstBooleanPosition);

            return RMObjectFactory.newDvDateTime(isPartial, minuteKnown,
                    secondKnown, fractionalSecKnown, dvTemporal, dateTime);
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
            int offset, String value) {
        int valueLength = value.length();
        buffer.writeInteger(offset, valueLength);
        buffer.writeString(offset + PrimitiveTypeSize.INT.getSize(),
                value);

        return offset + PrimitiveTypeSize.INT.getSize()+ valueLength;
    }

    /**
     * Deserializa uma string dado um determinado offset
     *
     * @param buffer
     * @param offset
     * @return String deserializada
     */
    private static String stringDeserialization(Buffer buffer,
            int offset){
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
     * @
     */
    public static int listStringSerialization(Buffer buffer, int offset,
            List<String> list) {
        int header = offset;
        int listSize = list.size();
        int position = offset + (listSize * PrimitiveTypeSize.INT.getSize()) +
                PrimitiveTypeSize.INT.getSize();

        header = writeHeader(buffer, header, listSize);
        if (listSize == 0){
            return header;
        }

        Iterator<String> it = list.iterator();
        while (it.hasNext()){
            String value = it.next();
            header = writeHeader(buffer, header, position);
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
            int offset){
        int position = offset;
        int listSize = buffer.readInteger(position);
        position += PrimitiveTypeSize.INT.getSize();

        List<String> list = new ArrayList<>();
        if (listSize == 0){
            return list;
        }

        for (int i = 0; i < listSize; i++){
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
     * @
     */
    public static int mapStringSerialization(
            Buffer buffer,
            int offset,
            Map<String, String> map) {
        int mapSize = map.size();
        int header = offset;
        header = writeHeader(buffer, header, mapSize);
        if (mapSize == 0){
            return header;
        }
        int position = offset + mapSize * (2 *
                PrimitiveTypeSize.INT.getSize()) +
                PrimitiveTypeSize.INT.getSize();

        for (Map.Entry<String, String> entry : map.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, key);
            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, value);
        }

        return position;
    }

    public static class DvQuantifiedSerializer {
        protected int serialize(Buffer buffer, int offset, DvOrdered dvOrdered,
                                String magnitudeStatus){
            int header = offset;
            int position = offset +  2 * PrimitiveTypeSize.INT.getSize() +
                    PrimitiveTypeSize.BOOLEAN.getSize();
            DvOrderedSerializer dos = new DvOrderedSerializer();
            boolean hasMagnitudeStatus = magnitudeStatus != null;

            header = writeHeader(buffer, header, position);
            position = dos.serialize(buffer, position, dvOrdered);
            writeHeader(buffer, header, hasMagnitudeStatus, position);
            position = stringSerialization(buffer, position, magnitudeStatus);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvQuantified d){
            int position = offset;
            DvQuantifiedSerializer dqs = new DvQuantifiedSerializer();
            position = dqs.serialize(buffer, position, d.getDvOrdered(),
                    d.getMagnitudeStatus());

            return position;
        }

        protected DvQuantified deserialize(Buffer buffer, int offset){
            int position = offset;
            DvOrderedSerializer dos = new DvOrderedSerializer();

            int dvOrderedPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvOrdered dvOrdered = dos.deserialize(buffer, dvOrderedPosition);

            boolean hasMagnitudeStatus = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            String magnitudeStatus = null;
            if(hasMagnitudeStatus){
                int magnitudeStatusPosition = buffer.readInteger(position);
                magnitudeStatus = stringDeserialization(buffer,
                        magnitudeStatusPosition);
            }

            return RMObjectFactory.newDvQuantified(dvOrdered, magnitudeStatus);
        }
    }

    public static class DvAmountSerializer {
        protected int serialize(Buffer buffer, int offset, DvOrdered dvOrdered,
                                double accuracy, boolean accuracyPercent){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();

            DvOrderedSerializer dos = new DvOrderedSerializer();
            header = writeHeader(buffer, header, position);
            position = dos.serialize(buffer, position, dvOrdered);

            header = writeHeader(buffer, header, position);
            buffer.writeDouble(position, accuracy);
            position += PrimitiveTypeSize.DOUBLE.getSize();

            writeHeader(buffer, header, position);
            buffer.writeBoolean(position, accuracyPercent);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            return position;
        }

        protected  int serialize(Buffer buffer, int offset, DvAmount dvAmount){
            int position = offset;
            DvAmountSerializer das = new DvAmountSerializer();

            position = das.serialize(buffer, position, dvAmount.getDvOrdered(),
                    dvAmount.getAccuracy(), dvAmount.isAccuracyPercent());

            return position;
        }

        protected DvAmount deserialize(Buffer buffer, int offset){
            int position = offset;

            DvOrderedSerializer dos = new DvOrderedSerializer();
            int dvOrderedPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvOrdered dvOrdered = dos.deserialize(buffer, dvOrderedPosition);

            int accuracyPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            double accuracy = buffer.readDouble(accuracyPosition);

            int accuracyPercentPosition = buffer.readInteger(position);
            boolean accuracyPercent = buffer.readBoolean(
                    accuracyPercentPosition);

            return RMObjectFactory.newDvAmount(dvOrdered, accuracy,
                    accuracyPercent);
        }
    }

    public static class DvOrdinalSerializer {
        protected int serialize(Buffer buffer, int offset,
                                List<ReferenceRange> otherReferenceRanges,
                                DvInterval normalRange, int value,
                                DvCodedText symbol){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize();

            ReferenceRangeSerializer rrs = new ReferenceRangeSerializer();
            DvIntervalSerializer dis = new DvIntervalSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            header = writeHeader(buffer, header, position);
            position = rrs.listSerialize(buffer, position, otherReferenceRanges);

            header = writeHeader(buffer, header, position);
            position = dis.serialize(buffer, position, normalRange);

            header = writeHeader(buffer, header, position);
            buffer.writeInteger(position, value);
            position += PrimitiveTypeSize.INT.getSize();

            writeHeader(buffer, header, position);
            position = dcs.serialize(buffer, position, symbol);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvOrdinal dvOrdinal){
            int position = offset;
            DvOrdinalSerializer dos = new DvOrdinalSerializer();
            position = dos.serialize(buffer, position,
                    dvOrdinal.getOtherReferenceRanges(),
                    dvOrdinal.getNormalRange(), dvOrdinal.getValue(),
                    dvOrdinal.getSymbol());

            return position;
        }

        protected DvOrdinal deserialize(Buffer buffer, int offset){
            int position = offset;

            ReferenceRangeSerializer rrs = new ReferenceRangeSerializer();
            DvIntervalSerializer dis = new DvIntervalSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            int otherReferencesPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            List<ReferenceRange> otherReferences = rrs.deserializeList(buffer,
                    otherReferencesPosition);

            int normalRangePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvInterval normalRange = dis.deserialize(buffer, normalRangePosition);

            int valuePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            int value = buffer.readInteger(valuePosition);

            int symbolPosition = buffer.readInteger(position);
            DvCodedText symbol = dcs.deserialize(buffer, symbolPosition);

            return RMObjectFactory.newDvOrdinal(otherReferences, normalRange,
                    value, symbol);
        }
    }

    public static class DvCountSerializer {
        protected int serialize(Buffer buffer, int offset, DvAmount dvAmount,
                                int magnitude){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            DvAmountSerializer das = new DvAmountSerializer();

            header = writeHeader(buffer, header, position);
            position = das.serialize(buffer, position, dvAmount);

            writeHeader(buffer, header, position);
            buffer.writeInteger(position, magnitude);
            position += PrimitiveTypeSize.INT.getSize();

            return position;
        }

        protected int serialize(Buffer buffer, int offset, DvCount d){
            int position = offset;
            DvCountSerializer dcs = new DvCountSerializer();

            position = dcs.serialize(buffer, position, d.getDvAmount(),
                    d.getMagnitude());

            return position;
        }

        protected DvCount deserialize(Buffer buffer, int offset){
            int position = offset;
            DvAmountSerializer das = new DvAmountSerializer();

            int dvAmountPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvAmount dvAmount = das.deserialize(buffer, dvAmountPosition);

            int magnitudePosition = buffer.readInteger(position);
            int magnitude = buffer.readInteger(magnitudePosition);

            return RMObjectFactory.newDvCount(dvAmount, magnitude);
        }
    }

    public static class ParticipationSerializer {
        protected int serialize(Buffer buffer, int offset, PartyProxy performer,
                                DvText function, DvCodedText mode,
                                DvInterval time){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize() +
                    PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasTime = time != null;

            PartyProxySerializer pps = new PartyProxySerializer();
            DvTextSerializer dts = new DvTextSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            DvIntervalSerializer dis = new DvIntervalSerializer();

            header = writeHeader(buffer, header, position);
            position = pps.serialize(buffer, position, performer);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, function);

            header = writeHeader(buffer, header, position);
            position = dcs.serialize(buffer, position, mode);

            writeHeader(buffer, header, hasTime, position);
            position = dis.serialize(buffer, position, time);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Participation p){
            int position = offset;

            ParticipationSerializer ps = new ParticipationSerializer();

            position = ps.serialize(buffer, position, p.getPerformer(),
                    p.getFunction(), p.getMode(), p.getTime());

            return position;
        }

        protected Participation deserialize(Buffer buffer, int offset){
            int position = offset;

            PartyProxySerializer pps = new PartyProxySerializer();
            DvTextSerializer dts = new DvTextSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            DvIntervalSerializer dis = new DvIntervalSerializer();

            int performerPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            PartyProxy performer = pps.deserialize(buffer, performerPosition);

            int functionPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvText function = dts.deserialize(buffer, functionPosition);

            int modePosition= buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvCodedText mode = dcs.deserialize(buffer, modePosition);

            boolean hasTime = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvInterval time = null;
            if(hasTime){
                int timePosition = buffer.readInteger(position);
                time = dis.deserialize(buffer, timePosition);
            }

            return RMObjectFactory.newParticipation(performer, function, mode,
                    time);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<Participation> items)
        {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            ParticipationSerializer dis = new ParticipationSerializer();

            for (Participation p : items){
                header = writeHeader(buffer, header, position);
                position = dis.serialize(buffer, position, p);
            }

            return position;
        }

        protected List<Participation> deserializeList(Buffer buffer,
                                                      int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Participation> list = new ArrayList<>();
            ParticipationSerializer dis = new ParticipationSerializer();

            for (int i = 0; i < listSize; i++){
                int participationPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                Participation p = dis.deserialize(buffer,
                        participationPosition);
                list.add(p);
            }

            return list;
        }

        protected int setSerializer(Buffer buffer, int offset,
                                    Set<Participation> items) {
            int setSize = items.size();
            int position = offset + (setSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            int header = offset;
            ParticipationSerializer des = new ParticipationSerializer();

            header = writeHeader(buffer, header, setSize);
            Iterator<Participation> it = items.iterator();

            while (it.hasNext()){
                Participation p = it.next();
                int participationPosition = position;
                header = writeHeader(buffer, header, participationPosition);
                position = des.serialize(buffer, position, p);
            }

            return position;
        }

        protected Set<Participation> setDeserializer(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            ParticipationSerializer des = new ParticipationSerializer();
            Set<Participation> participations = new HashSet<>();

            for (int i = 0; i < listSize; i++){
                int participationPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();

                Participation dv = des.deserialize(buffer,
                        participationPosition);
                participations.add(dv);
            }

            return participations;
        }
    }

    public static class AuditDetailsSerializer {
        protected int serialize(Buffer buffer, int offset,
                                String timePosition, PartyProxy committer,
                                DvDateTime timeCommitted,
                                DvCodedText changeType, DvText description){
            int header = offset;
            int position = offset + 5 * PrimitiveTypeSize.INT.getSize();

            PartyProxySerializer pps = new PartyProxySerializer();
            DvTextSerializer dts = new DvTextSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            DvDateTimeSerializer dds = new DvDateTimeSerializer();

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, timePosition);

            header = writeHeader(buffer, header, position);
            position = pps.serialize(buffer, position, committer);

            header = writeHeader(buffer, header, position);
            position = dds.serialize(buffer, position,
                    timeCommitted);

            header = writeHeader(buffer, header, position);
            position = dcs.serialize(buffer, position, changeType);

            writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, description);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, AuditDetails a){
            int position = offset;

            AuditDetailsSerializer ads = new AuditDetailsSerializer();

            position = ads.serialize(buffer, position, a.getTimePosition(),
                    a.getCommitter(), a.getTimeCommitted(),
                    a.getChangeType(), a.getDescription());

            return position;
        }

        protected AuditDetails deserialize(Buffer buffer, int offset){
            int position = offset;

            PartyProxySerializer pps = new PartyProxySerializer();
            DvTextSerializer dts = new DvTextSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            DvDateTimeSerializer dds = new DvDateTimeSerializer();

            int timePositionPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String timePosition = stringDeserialization(buffer,
                    timePositionPosition);

            int committerPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            PartyProxy committer = pps.deserialize(buffer,
                    committerPosition);

            int timeCommittedPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime timeCommitted = dds.deserialize(buffer,
                    timeCommittedPosition);

            int changeTypePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvCodedText changeType = dcs.deserialize(buffer,
                    changeTypePosition);

            int descriptionPosition = buffer.readInteger(position);
            DvText description = dts.deserialize(buffer,
                    descriptionPosition);

            return RMObjectFactory.newAuditDetails(timePosition,
                    committer, timeCommitted, changeType, description);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<AuditDetails> items)
        {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            AuditDetailsSerializer dis = new AuditDetailsSerializer();

            for (AuditDetails a : items){
                header = writeHeader(buffer, header, position);
                position = dis.serialize(buffer, position, a);
            }

            return position;
        }

        protected List<AuditDetails> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<AuditDetails> list = new ArrayList<>();
            AuditDetailsSerializer dis = new AuditDetailsSerializer();

            for (int i = 0; i < listSize; i++){
                int auditDetailsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                AuditDetails a = dis.deserialize(buffer, auditDetailsPosition);
                list.add(a);
            }

            return list;
        }
    }

    public static class AttestationSerializer {
        protected int serialize(Buffer buffer, int offset,
                                AuditDetails auditDetails,
                                DvMultimedia attestedView, String proof,
                                Set<DvEHRURI> items, DvText reason,
                                boolean isPending){
            int header = offset;
            int position = offset + 6 * PrimitiveTypeSize.INT.getSize();

            AuditDetailsSerializer ads = new AuditDetailsSerializer();
            DvMultimediaSerializer dms = new DvMultimediaSerializer();
            DvEHRURISerializer des = new DvEHRURISerializer();
            DvTextSerializer dts = new DvTextSerializer();

            header = writeHeader(buffer, header, position);
            position = ads.serialize(buffer, position, auditDetails);

            header = writeHeader(buffer, header, position);
            position = dms.serialize(buffer, position, attestedView);

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, proof);

            header = writeHeader(buffer, header, position);
            position = des.setSerializer(buffer, position, items);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, reason);

            writeHeader(buffer, header, position);
            buffer.writeBoolean(position, isPending);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Attestation a){
            int position = offset;

            AttestationSerializer as = new AttestationSerializer();

            position = as.serialize(buffer, position, a.getAuditDetails(),
                    a.getAttestedView(), a.getProof(), a.getItems(),
                    a.getReason(), a.isPending());

            return position;
        }

        protected Attestation deserialize(Buffer buffer, int offset){
            int position = offset;

            AuditDetailsSerializer ads = new AuditDetailsSerializer();
            DvMultimediaSerializer dms = new DvMultimediaSerializer();
            DvEHRURISerializer des = new DvEHRURISerializer();
            DvTextSerializer dts = new DvTextSerializer();

            int auditDetailsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            AuditDetails auditDetails = ads.deserialize(buffer,
                    auditDetailsPosition);

            int attestedViewPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvMultimedia attestedView = dms.deserialize(buffer,
                    attestedViewPosition);

            int proofPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String proof = stringDeserialization(buffer, proofPosition);

            int itemsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Set<DvEHRURI> items = des.setDeserializer(buffer, itemsPosition);

            int reasonPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvText reason = dts.deserialize(buffer, reasonPosition);

            int isPendingPosition = buffer.readInteger(position);
            boolean isPending = buffer.readBoolean(isPendingPosition);

            return RMObjectFactory.newAttestation(auditDetails, attestedView,
                    proof, items, reason, isPending);
        }
    }

    public static class RevisionHistoryItemSerializer {
        protected int serialize(Buffer buffer, int offset,
                                List<AuditDetails> audits,
                                ObjectVersionID versionID){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            AuditDetailsSerializer ads = new AuditDetailsSerializer();
            ObjectVersionIDSerializer ovs = new ObjectVersionIDSerializer();

            header = writeHeader(buffer, header, position);
            position = ads.listSerialize(buffer, position, audits);

            writeHeader(buffer, header, position);
            position = ovs.serialize(buffer, position, versionID);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, RevisionHistoryItem r){
            int position = offset;
            RevisionHistoryItemSerializer rhs = new RevisionHistoryItemSerializer();

            position = rhs.serialize(buffer, position, r.getAudits(),
                    r.getVersionID());

            return position;
        }

        protected RevisionHistoryItem deserialize(Buffer buffer, int offset){
            int position = offset;

            AuditDetailsSerializer ads = new AuditDetailsSerializer();
            ObjectVersionIDSerializer ovs = new ObjectVersionIDSerializer();

            int auditsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            List<AuditDetails> audits = ads.deserializeList(buffer,
                    auditsPosition);

            int versionIDPosition = buffer.readInteger(position);
            ObjectVersionID versionID = ovs.deserialize(buffer,
                    versionIDPosition);

            return RMObjectFactory.newRevisionHistoryItem(audits, versionID);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<RevisionHistoryItem> items)
        {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            RevisionHistoryItemSerializer rhs =
                    new RevisionHistoryItemSerializer();

            for (RevisionHistoryItem r : items){
                header = writeHeader(buffer, header, position);
                position = rhs.serialize(buffer, position, r);
            }

            return position;
        }

        protected List<RevisionHistoryItem> deserializeList(Buffer buffer,
                                                            int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<RevisionHistoryItem> list = new ArrayList<>();
            RevisionHistoryItemSerializer dis =
                    new RevisionHistoryItemSerializer();

            for (int i = 0; i < listSize; i++){
                int revisionHistoryItemPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                RevisionHistoryItem r = dis.deserialize(buffer,
                        revisionHistoryItemPosition);
                list.add(r);
            }

            return list;
        }
    }

    public static class RevisionHistorySerializer {
        protected int serialize(Buffer buffer, int offset,
                                List<RevisionHistoryItem> items){
            int position = offset;
            RevisionHistoryItemSerializer rhs =
                    new RevisionHistoryItemSerializer();

            position = rhs.listSerialize(buffer, position, items);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, RevisionHistory r){
            int position = offset;

            RevisionHistorySerializer rhs = new RevisionHistorySerializer();

            position = rhs.serialize(buffer, position, r.getItems());

            return position;
        }

        protected RevisionHistory deserialize(Buffer buffer, int offset){
            int position = offset;

            RevisionHistoryItemSerializer rhs =
                    new RevisionHistoryItemSerializer();

            List<RevisionHistoryItem> items = rhs.deserializeList(buffer,
                    position);

            return RMObjectFactory.newRevisionHistory(items);
        }
    }

    public static class ContributionSerializer {
        protected int serialize(Buffer buffer, int offset, ObjectID uid,
                                Set<ObjectRef> versions, AuditDetails audit){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();

            ObjectIDSerializer ois = new ObjectIDSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();
            AuditDetailsSerializer ads = new AuditDetailsSerializer();

            header = writeHeader(buffer, header, position);
            position = ois.serialize(buffer, position, uid);

            header = writeHeader(buffer, header, position);
            position = ors.setSerializer(buffer, position, versions);

            writeHeader(buffer, header, position);
            position = ads.serialize(buffer, position, audit);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Contribution c){
            int position = offset;

            ContributionSerializer cs = new ContributionSerializer();

            position = cs.serialize(buffer, position, c.getUid(),
                    c.getVersions(), c.getAudit());

            return position;
        }

        protected Contribution deserialize(Buffer buffer, int offset){
            int position = offset;

            ObjectIDSerializer ois = new ObjectIDSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();
            AuditDetailsSerializer ads = new AuditDetailsSerializer();

            int uidPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ObjectID uid = ois.deserialize(buffer, uidPosition);

            int versionPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Set<ObjectRef> versions = ors.setDeserializer(buffer,
                    versionPosition);

            int auditPosition = buffer.readInteger(position);
            AuditDetails audit = ads.deserialize(buffer, auditPosition);

            return RMObjectFactory.newContribution(uid, versions, audit);
        }
    }

    public static class FolderSerializer {
        protected int serialize(Buffer buffer, int offset, Locatable locatable,
                                List<Folder> folders, List<ObjectRef> items){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            FolderSerializer fs = new FolderSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            header = writeHeader(buffer, header, position);
            position = fs.listSerialize(buffer, position, folders);

            writeHeader(buffer, header, position);
            position = ors.listSerialize(buffer, position, items);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Folder f){
            int position = offset;

            FolderSerializer fs = new FolderSerializer();

            position = fs.serialize(buffer, position, f.getLocatable(),
                    f.getFolders(), f.getItems());

            return position;
        }

        protected Folder deserialize(Buffer buffer, int offset){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();
            FolderSerializer fs = new FolderSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            int foldersPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            List<Folder> folders = fs.deserializeList(buffer, foldersPosition);

            int itemsPosition = buffer.readInteger(position);
            List<ObjectRef> items = ors.deserializeList(buffer, itemsPosition);

            return RMObjectFactory.newFolder(locatable, folders, items);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<Folder> folders)
        {
            int header = offset;
            if(folders == null){
                int position = offset;
                buffer.writeInteger(position, -1);
                position += PrimitiveTypeSize.INT.getSize();

                return position;
            }
            int listSize = folders.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            FolderSerializer dis = new FolderSerializer();

            for (Folder f : folders){
                header = writeHeader(buffer, header, position);
                position = dis.serialize(buffer, position, f);
            }

            return position;
        }

        protected List<Folder> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<Folder> folders = new ArrayList<>();
            FolderSerializer fs = new FolderSerializer();

            for (int i = 0; i < listSize; i++){
                int folderPosition = buffer.readInteger(position);
                if(folderPosition == -1){
                    break;
                }
                position += PrimitiveTypeSize.INT.getSize();
                Folder f = fs.deserialize(buffer, folderPosition);
                folders.add(f);
            }

            return folders.size() > 0 ? folders : null;
        }
    }

    /**
     * Deserializa um map de Strings
     *
     * @param buffer
     * @param offset
     * @return mapa original que foi serializado
     */
    public static Map<String, String> mapStringDeserialization(
            Buffer buffer, int offset){
        int position = offset;
        int mapSize = buffer.readInteger(position);
        position += PrimitiveTypeSize.INT.getSize();
        if (mapSize == 0){
            Map<String, String> map = new HashMap<>();
            return map;
        }
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < mapSize; i++){
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

    public static class AuthoredResourceSerializer {
        protected int serialize(Buffer buffer, int offset,
                                CodePhrase originalLanguage,
                                Map<String, TranslationDetails> translations,
                                ResourceDescription description,
                                RevisionHistory revisionHistory,
                                boolean isControlled){
            int header = offset;
            int position = offset + 5 * PrimitiveTypeSize.INT.getSize() +
                    2 * PrimitiveTypeSize.BOOLEAN.getSize();

            CodePhraseSerializer cps = new CodePhraseSerializer();
            TranslationDetailsSerializer tds =
                    new TranslationDetailsSerializer();
            ResourceDescriptionSerializer rds =
                    new ResourceDescriptionSerializer();
            RevisionHistorySerializer rhs = new RevisionHistorySerializer();

            boolean hasDescription = description != null;
            boolean hasRevisionHistory = revisionHistory != null;

            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, originalLanguage);

            header = writeHeader(buffer, header, position);
            position = tds.mapSerialization(buffer, position, translations);

            header = writeHeader(buffer, header, hasDescription, position);
            if(hasDescription){
                position = rds.serialize(buffer, position, description);
            }

            header = writeHeader(buffer, header, hasRevisionHistory, position);
            if(hasRevisionHistory){
                position = rhs.serialize(buffer, position, revisionHistory);
            }

            writeHeader(buffer, header, position);
            buffer.writeBoolean(position, isControlled);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            return position;
        }

        protected int serialize(Buffer buffer, int offset, AuthoredResource a){
            int position = offset;
            AuthoredResourceSerializer ars = new AuthoredResourceSerializer();

            position = ars.serialize(buffer, position, a.getOriginalLanguage(),
                    a.getTranslations(), a.getDescription(),
                    a.getRevisionHistory(), a.isControlled());

            return position;
        }

        protected AuthoredResource deserialize(Buffer buffer, int offset){
            int position = offset;

            CodePhraseSerializer cps = new CodePhraseSerializer();
            TranslationDetailsSerializer tds =
                    new TranslationDetailsSerializer();
            ResourceDescriptionSerializer rds =
                    new ResourceDescriptionSerializer();
            RevisionHistorySerializer rhs = new RevisionHistorySerializer();


            int originalLanguagePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CodePhrase originalLanguage = cps.deserialize(buffer,
                    originalLanguagePosition);

            int translationsPositon = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Map<String, TranslationDetails> translations =
                    tds.mapDeserialization(buffer, translationsPositon);

            boolean hasDescription = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ResourceDescription description = null;
            if(hasDescription){
                int descriptionPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                description = rds.deserialize(buffer, descriptionPosition);
            }

            boolean hasRevisionHistory = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            RevisionHistory revisionHistory  = null;
            if(hasRevisionHistory){
                int revisionHistoryPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                revisionHistory = rhs.deserialize(buffer,
                        revisionHistoryPosition);
            }

            int isControllerPosition = buffer.readInteger(position);
            boolean isControlled = buffer.readBoolean(isControllerPosition);

            return RMObjectFactory.newAuthoredResource(originalLanguage,
                    translations, description, revisionHistory, isControlled);
        }
    }

    public static class ResourceDescriptionSerializer {
        protected int serialize(Buffer buffer, int offset,
                                Map<String, String> originalAuthor,
                                List<String> otherContributors,
                                String lifecycleState,
                                List<ResourceDescriptionItem> details,
                                String resourcePackageUri,
                                Map<String, String> otherDetails,
                                AuthoredResource parentResource){
            int header = offset;
            int position = offset + 7 * PrimitiveTypeSize.INT.getSize() +
                    4 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasOtherContributors = otherContributors != null;
            boolean hasResourcePackageUri = resourcePackageUri != null;
            boolean hasOtherDetails = otherDetails != null;
            boolean hasParentResource = parentResource != null;

            ResourceDescriptionItemSerializer rds =
                    new ResourceDescriptionItemSerializer();
            AuthoredResourceSerializer ars = new AuthoredResourceSerializer();

            header = writeHeader(buffer, header, position);
            position = mapStringSerialization(buffer, position, originalAuthor);

            header = writeHeader(buffer, header, hasOtherContributors, position);
            if(hasOtherContributors){
                position = listStringSerialization(buffer, position,
                    otherContributors);
            }

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, lifecycleState);

            header = writeHeader(buffer, header, position);
            position = rds.listSerialize(buffer, position, details);

            header = writeHeader(buffer, header, hasResourcePackageUri, position);
            if(hasResourcePackageUri){
                position = stringSerialization(buffer, position,
                        resourcePackageUri);
            }

            header = writeHeader(buffer, header, hasOtherDetails, position);
            if(hasOtherDetails){
                position = mapStringSerialization(buffer, position, otherDetails);
            }

            writeHeader(buffer, header, hasParentResource, position);
            if(hasParentResource){
                position = ars.serialize(buffer, position, parentResource);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, ResourceDescription r){
            int position = offset;
            ResourceDescriptionSerializer ars =
                    new ResourceDescriptionSerializer();

            position = ars.serialize(buffer, position, r.getOriginalAuthor(),
                    r.getOtherContributors(), r.getLifecycleState(),
                    r.getDetails(), r.getResourcePackageUri(),
                    r.getOtherDetails(), r.getParentResource());

            return position;
        }

        protected ResourceDescription deserialize(Buffer buffer, int offset){
            int position = offset;

            ResourceDescriptionItemSerializer rds =
                    new ResourceDescriptionItemSerializer();
            AuthoredResourceSerializer ars = new AuthoredResourceSerializer();

            int originalAuthorPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Map<String, String> originalAuthor = mapStringDeserialization(
                    buffer, originalAuthorPosition);

            boolean hasOtherContributors = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<String> otherContributors = null;
            if(hasOtherContributors){
                int otherContributorsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                otherContributors = listStringDeserialization(buffer,
                        otherContributorsPosition);
            }

            int lifecicleStatePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String lifecycleState = stringDeserialization(buffer,
                    lifecicleStatePosition);

            int detailsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            List<ResourceDescriptionItem> details = rds.deserializeList(buffer,
                    detailsPosition);

            boolean hasResourcePackageUri = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            String resourcePackageUri = null;
            if(hasResourcePackageUri){
                int resourcePackageUriPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                resourcePackageUri = stringDeserialization(buffer,
                        resourcePackageUriPosition);
            }

            boolean hasOtherDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Map<String, String> otherDetails = null;
            if(hasOtherDetails){
                int otherDetailsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                otherDetails = mapStringDeserialization(buffer,
                        otherDetailsPosition);
            }

            boolean hasParentResource = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            AuthoredResource parentResource = null;
            if(hasParentResource){
                int parentResourcePosition = buffer.readInteger(position);
                parentResource = ars.deserialize(buffer,
                        parentResourcePosition);
            }

            return RMObjectFactory.newResourceDescription(originalAuthor,
                    otherContributors, lifecycleState, details,
                    resourcePackageUri, otherDetails, parentResource);
        }
    }

    public static class EventSerializer {
        protected int serialize(Buffer buffer, int offset,
                                        Locatable locatable, DvDateTime time,
                                        ItemTree data, ItemStructure state){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ItemTreeSerializer its = new ItemTreeSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, time);

            header = writeHeader(buffer, header, position);
            position = its.serialize(buffer, position, data);

            writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, state);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, EventWithItemTree e){
            int position = offset;
            EventSerializer es = new EventSerializer();

            position = es.serialize(buffer, position, e.getLocatable(),
                    e.getTime(), e.getData(), e.getState());

            return position;
        }

        protected EventWithItemTree deserializeItemTree(Buffer buffer,
                                                        int offset){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ItemTreeSerializer its = new ItemTreeSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            int timePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime time = dts.deserialize(buffer, timePosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemTree data = its.deserialize(buffer, dataPosition);

            int statePosition = buffer.readInteger(position);
            ItemStructure state = iss.deserialize(buffer, statePosition);

            return RMObjectFactory.newEventWithItemTree(locatable, time,
                    data, state);
        }

        protected int serialize(Buffer buffer, int offset,
                                Locatable locatable, DvDateTime time,
                                ItemSingle data, ItemStructure state){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ItemSingleSerializer its = new ItemSingleSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, time);

            header = writeHeader(buffer, header, position);
            position = its.serialize(buffer, position, data);

            writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, state);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, EventWithItemSingle e){
            int position = offset;
            EventSerializer es = new EventSerializer();

            position = es.serialize(buffer, position, e.getLocatable(),
                    e.getTime(), e.getData(), e.getState());

            return position;
        }

        protected EventWithItemSingle deserializeItemSingle(Buffer buffer,
                                                            int offset){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ItemSingleSerializer its = new ItemSingleSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            int timePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime time = dts.deserialize(buffer, timePosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemSingle data = its.deserialize(buffer, dataPosition);

            int statePosition = buffer.readInteger(position);
            ItemStructure state = iss.deserialize(buffer, statePosition);

            return RMObjectFactory.newEventWithItemSingle(locatable, time,
                    data, state);
        }

        protected int serialize(Buffer buffer, int offset,
                                Locatable locatable, DvDateTime time,
                                ItemTable data, ItemStructure state){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize();

            LocatableSerializer ls = new LocatableSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ItemTableSerializer its = new ItemTableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, time);

            header = writeHeader(buffer, header, position);
            position = its.serialize(buffer, position, data);

            writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, state);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, EventWithItemTable e){
            int position = offset;
            EventSerializer es = new EventSerializer();

            position = es.serialize(buffer, position, e.getLocatable(),
                    e.getTime(), e.getData(), e.getState());

            return position;
        }

        protected EventWithItemTable deserializeItemTable(Buffer buffer,
                                                          int offset){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ItemTableSerializer its = new ItemTableSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            int timePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime time = dts.deserialize(buffer, timePosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemTable data = its.deserialize(buffer, dataPosition);

            int statePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemStructure state = iss.deserialize(buffer, statePosition);

            return RMObjectFactory.newEventWithItemTable(locatable, time,
                    data, state);
        }

        protected int listSerializeItemTree(
                Buffer buffer, int offset, List<EventWithItemTree> items)
        {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            EventSerializer es = new EventSerializer();

            for (EventWithItemTree e : items){
                header = writeHeader(buffer, header, position);
                position = es.serialize(buffer, position, e);
            }

            return position;
        }

        protected List<EventWithItemTree> deserializeListOfItemTree(
                Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<EventWithItemTree> list = new ArrayList<>();
            EventSerializer es = new EventSerializer();

            for (int i = 0; i < listSize; i++){
                int eventPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                EventWithItemTree e = es.deserializeItemTree(buffer,
                        eventPosition);
                list.add(e);
            }

            return list;
        }

        protected int listSerializeItemSingle(
                Buffer buffer, int offset, List<EventWithItemSingle> items)
        {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            EventSerializer es = new EventSerializer();

            for (EventWithItemSingle e : items){
                header = writeHeader(buffer, header, position);
                position = es.serialize(buffer, position, e);
            }

            return position;
        }

        protected List<EventWithItemSingle> deserializeListOfItemSingle(Buffer buffer,
                                                            int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<EventWithItemSingle> list = new ArrayList<>();
            EventSerializer es = new EventSerializer();

            for (int i = 0; i < listSize; i++){
                int eventPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                EventWithItemSingle e = es.deserializeItemSingle(buffer,
                        eventPosition);
                list.add(e);
            }

            return list;
        }

        protected int listSerializeItemTable(
                Buffer buffer, int offset, List<EventWithItemTable> items)
        {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            EventSerializer es = new EventSerializer();

            for (EventWithItemTable e : items){
                header = writeHeader(buffer, header, position);
                position = es.serialize(buffer, position, e);
            }

            return position;
        }

        protected List<EventWithItemTable> deserializeListOfItemTable(Buffer buffer,
                                                           int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<EventWithItemTable> list = new ArrayList<>();
            EventSerializer es = new EventSerializer();

            for (int i = 0; i < listSize; i++){
                int eventPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                EventWithItemTable e = es.deserializeItemTable(buffer,
                        eventPosition);
                list.add(e);
            }

            return list;
        }
    }

    public static class HistorySerializer {
        protected int serializeItemTree(Buffer buffer, int offset,
                                DataStructure dataStructure, DvDateTime origin,
                                List<EventWithItemTree> events,
                                DvDuration period, DvDuration duration,
                                ItemStructure summary) {
            int header = offset;
            int position = offset + 6 * PrimitiveTypeSize.INT.getSize() +
                    4 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasEvents = events != null;
            boolean hasPeriod = period != null;
            boolean hasDuration = duration != null;
            boolean hasSummary = summary != null;

            DataStructureSerializer dss = new DataStructureSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = dss.serialize(buffer, position, dataStructure);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, origin);

            header = writeHeader(buffer, header, hasEvents, position);
            if(hasEvents){
                position = es.listSerializeItemTree(buffer, position, events);
            }

            header = writeHeader(buffer, header, hasPeriod, position);
            if(hasPeriod){
                position = dds.serialize(buffer, position, period);
            }

            header = writeHeader(buffer, header, hasDuration, position);
            if(hasDuration){
                position = dds.serialize(buffer, position, duration);
            }

            writeHeader(buffer, header, hasSummary, position);
            if(hasSummary){
                position = iss.serialize(buffer, position, summary);
            }

            return position;
        }

        int serialize(Buffer buffer, int offset, HistoryWithItemTree h){
            int position = offset;
            HistorySerializer hs = new HistorySerializer();

            position = hs.serializeItemTree(buffer, position, h.getDataStructure(),
                    h.getOrigin(), h.getEvents(), h.getPeriod(), h.getDuration(),
                    h.getSummary());

            return position;
        }

        HistoryWithItemTree deserializeItemTree(Buffer buffer, int offset){
            int position = offset;

            DataStructureSerializer dss = new DataStructureSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int dataStructurePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DataStructure dataStructure = dss.deserialize(buffer,
                    dataStructurePosition);

            int originPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime origin = dts.deserialize(buffer, originPosition);

            boolean hasEvents = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<EventWithItemTree> events = null;
            if(hasEvents){
                int eventsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                events = es.deserializeListOfItemTree(buffer, eventsPosition);
            }


            boolean hasPeriod = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDuration period = null;
            if(hasPeriod){
                int periodPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                period = dds.deserialize(buffer, periodPosition);
            }

            boolean hasDuration = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDuration duration = null;
            if(hasDuration){
                int durationPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                duration = dds.deserialize(buffer, durationPosition);
            }

            boolean hasSummary = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructure summary = null;
            if(hasSummary){
                int summaryPosition = buffer.readInteger(position);
                summary = iss.deserialize(buffer, summaryPosition);
            }

            return RMObjectFactory.newHistoryWithItemTree(dataStructure,
                    origin, events, period, duration, summary);
        }

        protected int serializeItemSingle(Buffer buffer, int offset,
                                          DataStructure dataStructure, DvDateTime origin,
                                          List<EventWithItemSingle> events,
                                          DvDuration period, DvDuration duration,
                                          ItemStructure summary) {
            int header = offset;
            int position = offset + 6 * PrimitiveTypeSize.INT.getSize() +
                    4 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasEvents = events != null;
            boolean hasPeriod = period != null;
            boolean hasDuration = duration != null;
            boolean hasSummary = summary != null;

            DataStructureSerializer dss = new DataStructureSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = dss.serialize(buffer, position, dataStructure);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, origin);

            header = writeHeader(buffer, header, hasEvents, position);
            if(hasEvents){
                position = es.listSerializeItemSingle(buffer, position, events);
            }

            header = writeHeader(buffer, header, hasPeriod, position);
            if(hasPeriod){
                position = dds.serialize(buffer, position, period);
            }

            header = writeHeader(buffer, header, hasDuration, position);
            if(hasDuration){
                position = dds.serialize(buffer, position, duration);
            }

            writeHeader(buffer, header, hasSummary, position);
            if(hasSummary){
                position = iss.serialize(buffer, position, summary);
            }

            return position;
        }

        int serialize(Buffer buffer, int offset, HistoryWithItemSingle h){
            int position = offset;
            HistorySerializer hs = new HistorySerializer();

            position = hs.serializeItemSingle(buffer, position, h.getDataStructure(),
                    h.getOrigin(), h.getEvents(), h.getPeriod(), h.getDuration(),
                    h.getSummary());

            return position;
        }

        HistoryWithItemSingle deserializeItemSingle(Buffer buffer, int offset){
            int position = offset;

            DataStructureSerializer dss = new DataStructureSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int dataStructurePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DataStructure dataStructure = dss.deserialize(buffer,
                    dataStructurePosition);
            int originPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime origin = dts.deserialize(buffer, originPosition);

            boolean hasEvents = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<EventWithItemSingle> events = null;
            if(hasEvents){
                int eventsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                events = es.deserializeListOfItemSingle(buffer, eventsPosition);
            }

            boolean hasPeriod = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDuration period = null;
            if(hasPeriod){
                int periodPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                period = dds.deserialize(buffer, periodPosition);
            }

            boolean hasDuration = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDuration duration = null;
            if(hasDuration){
                int durationPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                duration = dds.deserialize(buffer, durationPosition);
            }

            boolean hasSummary = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructure summary = null;
            if(hasSummary){
                int summaryPosition = buffer.readInteger(position);
                summary = iss.deserialize(buffer, summaryPosition);
            }

            return RMObjectFactory.newHistoryWithItemSingle(dataStructure,
                    origin, events, period, duration, summary);
        }

        protected int serializeItemTable(Buffer buffer, int offset,
                                         DataStructure dataStructure, DvDateTime origin,
                                         List<EventWithItemTable> events,
                                         DvDuration period, DvDuration duration,
                                         ItemStructure summary) {
            int header = offset;
            int position = offset + 6 * PrimitiveTypeSize.INT.getSize() +
                    4 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasEvents = events != null;
            boolean hasPeriod = period != null;
            boolean hasDuration = duration != null;
            boolean hasSummary = summary != null;

            DataStructureSerializer dss = new DataStructureSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = dss.serialize(buffer, position, dataStructure);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, origin);

            header = writeHeader(buffer, header, hasEvents, position);
            if(hasEvents){
                position = es.listSerializeItemTable(buffer, position, events);
            }

            header = writeHeader(buffer, header, hasPeriod, position);
            if(hasPeriod){
                position = dds.serialize(buffer, position, period);
            }

            header = writeHeader(buffer, header, hasDuration, position);
            if(hasDuration){
                position = dds.serialize(buffer, position, duration);
            }

            writeHeader(buffer, header, hasSummary, position);
            if(hasSummary){
                position = iss.serialize(buffer, position, summary);
            }

            return position;
        }

        int serialize(Buffer buffer, int offset, HistoryWithItemTable h){
            int position = offset;
            HistorySerializer hs = new HistorySerializer();

            position = hs.serializeItemTable(buffer, position, h.getDataStructure(),
                    h.getOrigin(), h.getEvents(), h.getPeriod(), h.getDuration(),
                    h.getSummary());

            return position;
        }

        HistoryWithItemTable deserializeItemTable(Buffer buffer, int offset){
            int position = offset;

            DataStructureSerializer dss = new DataStructureSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int dataStructurePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DataStructure dataStructure = dss.deserialize(buffer,
                    dataStructurePosition);
            int originPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime origin = dts.deserialize(buffer, originPosition);

            boolean hasEvents = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<EventWithItemTable> events = null;
            if(hasEvents){
                int eventsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                events = es.deserializeListOfItemTable(buffer, eventsPosition);
            }

            boolean hasPeriod = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDuration period = null;
            if(hasPeriod){
                int periodPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                period = dds.deserialize(buffer, periodPosition);
            }

            boolean hasDuration = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDuration duration = null;
            if(hasDuration){
                int durationPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                duration = dds.deserialize(buffer, durationPosition);
            }

            boolean hasSummary = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructure summary = null;
            if(hasSummary){
                int summaryPosition = buffer.readInteger(position);
                summary = iss.deserialize(buffer, summaryPosition);
            }

            return RMObjectFactory.newHistoryWithItemTable(dataStructure,
                    origin, events, period, duration, summary);
        }
    }

    public static class IntervalEventSerializer {
        protected int serializeItemTree(Buffer buffer, int offset,
                                EventWithItemTree event, DvDuration width,
                                DvCodedText mathFunction, int sampleCount){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize() +
                    2 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasWidth = width != null;
            boolean hasMathFunction = mathFunction != null;

            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            header = writeHeader(buffer, header, position);
            position = es.serialize(buffer, position, event);

            header = writeHeader(buffer, header, hasWidth, position);
            if(hasWidth){
                position = dds.serialize(buffer, position, width);
            }

            header = writeHeader(buffer, header, hasMathFunction, position);
            if(hasMathFunction){
                position = dcs.serialize(buffer, position, mathFunction);
            }

            writeHeader(buffer, header, position);
            buffer.writeInteger(position, sampleCount);
            position += PrimitiveTypeSize.INT.getSize();

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                IntervalEventWithItemTree i){
            int position = offset;

            IntervalEventSerializer ies = new IntervalEventSerializer();

            position = ies.serializeItemTree(buffer, position, i.getEvent(),
                    i.getWidth(), i.getMathFunction(), i.getSampleCount());

            return position;
        }

        protected IntervalEventWithItemTree deserializeItemTree(Buffer buffer,
                                                        int offset){
            int position = offset;

            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            int eventPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            EventWithItemTree event = es.deserializeItemTree(buffer,
                    eventPosition);

            boolean hasWidth = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDuration width = null;
            if(hasWidth){
                int widthPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                width = dds.deserialize(buffer, widthPosition);
            }

            boolean hasMathFunction = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvCodedText mathFunction = null;
            if(hasMathFunction){
                int mathFunctionPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                mathFunction = dcs.deserialize(buffer, mathFunctionPosition);
            }

            int sampleCountPosition = buffer.readInteger(position);
            int sampleCount = buffer.readInteger(sampleCountPosition);

            return RMObjectFactory.newIntervalEventWithItemTree(event, width,
                    mathFunction, sampleCount);
        }

        protected int serializeItemSingle(Buffer buffer, int offset,
                                          EventWithItemSingle event,
                                          DvDuration width,
                                          DvCodedText mathFunction,
                                          int sampleCount){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize() +
                    2 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasWidth = width != null;
            boolean hasMathFunction = mathFunction != null;

            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            header = writeHeader(buffer, header, position);
            position = es.serialize(buffer, position, event);

            header = writeHeader(buffer, header, hasWidth, position);
            if(hasWidth){
                position = dds.serialize(buffer, position, width);
            }

            header = writeHeader(buffer, header, hasMathFunction, position);
            if(hasMathFunction){
                position = dcs.serialize(buffer, position, mathFunction);
            }

            writeHeader(buffer, header, position);
            buffer.writeInteger(position, sampleCount);
            position += PrimitiveTypeSize.INT.getSize();

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                IntervalEventWithItemSingle i){
            int position = offset;

            IntervalEventSerializer ies = new IntervalEventSerializer();

            position = ies.serializeItemSingle(buffer, position, i.getEvent(),
                    i.getWidth(), i.getMathFunction(), i.getSampleCount());

            return position;
        }

        protected IntervalEventWithItemSingle deserializeItemSingle(
                Buffer buffer, int offset){
            int position = offset;

            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            int eventPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            EventWithItemSingle event = es.deserializeItemSingle(buffer,
                    eventPosition);

            boolean hasWidth = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDuration width = null;
            if(hasWidth){
                int widthPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                width = dds.deserialize(buffer, widthPosition);
            }

            boolean hasMathFunction = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvCodedText mathFunction = null;
            if(hasMathFunction){
                int mathFunctionPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                mathFunction = dcs.deserialize(buffer, mathFunctionPosition);
            }

            int sampleCountPosition = buffer.readInteger(position);
            int sampleCount = buffer.readInteger(sampleCountPosition);

            return RMObjectFactory.newIntervalEventWithItemSingle(event, width,
                    mathFunction, sampleCount);
        }

        protected int serializeItemTable(Buffer buffer, int offset,
                                         EventWithItemTable event,
                                         DvDuration width,
                                         DvCodedText mathFunction,
                                         int sampleCount){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize() +
                    2 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasWidth = width != null;
            boolean hasMathFunction = mathFunction != null;

            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            header = writeHeader(buffer, header, position);
            position = es.serialize(buffer, position, event);

            header = writeHeader(buffer, header, hasWidth, position);
            if(hasWidth){
                position = dds.serialize(buffer, position, width);
            }

            header = writeHeader(buffer, header, hasMathFunction, position);
            if(hasMathFunction){
                position = dcs.serialize(buffer, position, mathFunction);
            }

            writeHeader(buffer, header, position);
            buffer.writeInteger(position, sampleCount);
            position += PrimitiveTypeSize.INT.getSize();

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                IntervalEventWithItemTable i){
            int position = offset;

            IntervalEventSerializer ies = new IntervalEventSerializer();

            position = ies.serializeItemTable(buffer, position, i.getEvent(),
                    i.getWidth(), i.getMathFunction(), i.getSampleCount());

            return position;
        }

        protected IntervalEventWithItemTable deserializeItemTable(Buffer buffer,
                                                                  int offset){
            int position = offset;

            EventSerializer es = new EventSerializer();
            DvDurationSerializer dds = new DvDurationSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            int eventPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            EventWithItemTable event = es.deserializeItemTable(buffer,
                    eventPosition);

            boolean hasWidth = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDuration width = null;
            if(hasWidth){
                int widthPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                width = dds.deserialize(buffer, widthPosition);
            }

            boolean hasMathFunction = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvCodedText mathFunction = null;
            if(hasMathFunction){
                int mathFunctionPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                mathFunction = dcs.deserialize(buffer, mathFunctionPosition);
            }

            int sampleCountPosition = buffer.readInteger(position);
            int sampleCount = buffer.readInteger(sampleCountPosition);

            return RMObjectFactory.newIntervalEventWithItemTable(event, width,
                    mathFunction, sampleCount);
        }
    }

    public static class PointEventSerializer {
        protected int serialize(Buffer buffer, int offset,
                                        EventWithItemTree event){
            int position = offset;

            EventSerializer es = new EventSerializer();

            position = es.serialize(buffer, position, event);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                PointEventWithItemTree p){
            int position = offset;

            PointEventSerializer pes = new PointEventSerializer();

            position = pes.serialize(buffer, position, p.getEvent());

            return position;
        }

        protected PointEventWithItemTree deserializeItemTree(Buffer buffer,
                                                             int offset){
            int position = offset;

            EventSerializer es = new EventSerializer();

            EventWithItemTree event = es.deserializeItemTree(buffer, position);

            return RMObjectFactory.newPointEventWithItemTree(event);
        }

        protected int serialize(Buffer buffer, int offset,
                                EventWithItemSingle event){
            int position = offset;

            EventSerializer es = new EventSerializer();

            position = es.serialize(buffer, position, event);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                PointEventWithItemSingle p){
            int position = offset;

            PointEventSerializer pes = new PointEventSerializer();

            position = pes.serialize(buffer, position, p.getEvent());

            return position;
        }

        protected PointEventWithItemSingle deserializeItemSingle(Buffer buffer,
                                                                 int offset){
            int position = offset;

            EventSerializer es = new EventSerializer();

            EventWithItemSingle event = es.deserializeItemSingle(buffer, position);

            return RMObjectFactory.newPointEventWithItemSingle(event);
        }

        protected int serialize(Buffer buffer, int offset,
                                EventWithItemTable event){
            int position = offset;

            EventSerializer es = new EventSerializer();

            position = es.serialize(buffer, position, event);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                PointEventWithItemTable p){
            int position = offset;

            PointEventSerializer pes = new PointEventSerializer();

            position = pes.serialize(buffer, position, p.getEvent());

            return position;
        }

        protected PointEventWithItemTable deserializeItemTable(Buffer buffer,
                                                               int offset){
            int position = offset;

            EventSerializer es = new EventSerializer();

            EventWithItemTable event = es.deserializeItemTable(buffer, position);

            return RMObjectFactory.newPointEventWithItemTable(event);
        }
    }

    public static class ContentItemSerializer {
        protected int serialize(Buffer buffer, int offset, Locatable locatable){
            int position = offset;

            LocatableSerializer l = new LocatableSerializer();

            position = l.serialize(buffer, position, locatable);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, ContentItem c){
            int position = offset;

            ContentItemSerializer cis = new ContentItemSerializer();

            position = cis.serialize(buffer, position, c.getLocatable());

            return position;
        }

        protected ContentItem deserialize(Buffer buffer, int offset){
            int position = offset;

            LocatableSerializer l = new LocatableSerializer();

            Locatable locatable = l.deserialize(buffer, position);

            return RMObjectFactory.newContentItem(locatable);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<ContentItem> items)
        {
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            ContentItemSerializer dis = new ContentItemSerializer();

            for (ContentItem c : items){
                header = writeHeader(buffer, header, position);
                position = dis.serialize(buffer, position, c);
            }

            return position;
        }

        protected List<ContentItem> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<ContentItem> list = new ArrayList<>();
            ContentItemSerializer dis = new ContentItemSerializer();

            for (int i = 0; i < listSize; i++){
                int contentItemPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                ContentItem c = dis.deserialize(buffer, contentItemPosition);
                list.add(c);
            }

            return list;
        }
    }

    public static class EntrySerializer {
        protected int serialize(Buffer buffer, int offset,
                                ContentItem contentItem, CodePhrase language,
                                CodePhrase encoding, PartyProxy subject,
                                PartyProxy provider, ObjectRef workflowId,
                                List<Participation> otherParticipations){
            int header = offset;
            int position = offset + 7 * PrimitiveTypeSize.INT.getSize() +
                3 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasProvider = provider != null;
            boolean hasWorkflowId = workflowId != null;
            boolean hasOtherParticipations = otherParticipations != null;

            ContentItemSerializer cis = new ContentItemSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();
            PartyProxySerializer pps = new PartyProxySerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();
            ParticipationSerializer ps = new ParticipationSerializer();

            header = writeHeader(buffer, header, position);
            position = cis.serialize(buffer, position, contentItem);

            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, language);

            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, encoding);

            header = writeHeader(buffer, header, position);
            position = pps.serialize(buffer, position, subject);

            header = writeHeader(buffer, header, hasProvider, position);
            if(hasProvider){
                position = pps.serialize(buffer, position, provider);
            }

            header = writeHeader(buffer, header, hasWorkflowId, position);
            if(hasWorkflowId){
                position = ors.serialize(buffer, position, workflowId);
            }

            writeHeader(buffer, header, hasOtherParticipations, position);
            if(hasOtherParticipations){
                position = ps.listSerialize(buffer, position, otherParticipations);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Entry e){
            int position = offset;

            EntrySerializer es = new EntrySerializer();

            position = es.serialize(buffer, position, e.getContentItem(),
                    e.getLanguage(), e.getEncoding(), e.getSubject(),
                    e.getProvider(), e.getWorkflowId(),
                    e.getOtherParticipations());

            return position;
        }

        protected Entry deserialize(Buffer buffer, int offset){
            int position = offset;

            ContentItemSerializer cis = new ContentItemSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();
            PartyProxySerializer pps = new PartyProxySerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();
            ParticipationSerializer ps = new ParticipationSerializer();

            int contentItemPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ContentItem contentItem = cis.deserialize(buffer,
                    contentItemPosition);

            int languagePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CodePhrase language = cps.deserialize(buffer, languagePosition);

            int encodingPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CodePhrase encoding = cps.deserialize(buffer, encodingPosition);

            int subjectPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            PartyProxy subject = pps.deserialize(buffer, subjectPosition);

            boolean hasProvider = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            PartyProxy provider = null;
            if(hasProvider){
                int providerPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                provider = pps.deserialize(buffer, providerPosition);
            }

            boolean hasWorkflowId = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ObjectRef workflowId = null;
            if(hasWorkflowId){
                int workflowIdPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                workflowId = ors.deserialize(buffer, workflowIdPosition);
            }

            boolean hasOtherParticipation = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<Participation> otherParticipations = null;
            if(hasOtherParticipation){
                int otherParticipationsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.BOOLEAN.getSize();
                otherParticipations = ps.deserializeList(buffer,
                        otherParticipationsPosition);
            }

            return RMObjectFactory.newEntry(contentItem, language, encoding,
                    subject, provider, workflowId, otherParticipations);
        }
    }

    public static class CareEntrySerializer {
        protected int serialize(Buffer buffer, int offset, Entry entry,
                                ItemStructure protocol, ObjectRef guidelineId){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + 2 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasProtocol = protocol != null;
            boolean hasGuidelineId = guidelineId != null;

            EntrySerializer es = new EntrySerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();

            header = writeHeader(buffer, header, position);
            position = es.serialize(buffer, position, entry);

            header = writeHeader(buffer, header, hasProtocol, position);
            if(hasProtocol){
                position = iss.serialize(buffer, position, protocol);
            }

            writeHeader(buffer, header, hasGuidelineId, position);
            if(hasGuidelineId){
                position = ors.serialize(buffer, position, guidelineId);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, CareEntry c){
            int position = offset;

            CareEntrySerializer ces = new CareEntrySerializer();

            position = ces.serialize(buffer, position, c.getEntry(),
                    c.getProtocol(), c.getGuidelineId());

            return position;
        }

        protected CareEntry deserialize(Buffer buffer, int offset){
            int position = offset;

            EntrySerializer es = new EntrySerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();

            int entryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Entry entry = es.deserialize(buffer, entryPosition);

            boolean hasProtocol = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructure protocol = null;
            if(hasProtocol){
                int protocolPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                protocol = iss.deserialize(buffer, protocolPosition);
            }

            boolean hasGuidelineId = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ObjectRef guidelineId = null;
            if(hasGuidelineId){
                int guidelineIdPosition = buffer.readInteger(position);
                guidelineId = ors.deserialize(buffer, guidelineIdPosition);
            }

            return RMObjectFactory.newCareEntry(entry, protocol, guidelineId);
        }
    }

    public static class ActionSerializer {
        protected int serialize(Buffer buffer, int offset, DvDateTime time,
                                ItemStructure description,
                                ISMTransition ismTransition,
                                InstructionDetails instructionDetails){
            int header = offset;
            int position = offset + 4 * PrimitiveTypeSize.INT.getSize()
                + PrimitiveTypeSize.BOOLEAN.getSize();
            boolean hasInstructionDetails = instructionDetails != null;

            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ISMTransitionSerializer its = new ISMTransitionSerializer();
            InstructionDetailsSerializer ids = new
                    InstructionDetailsSerializer();

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, time);

            header = writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, description);

            header = writeHeader(buffer, header, position);
            position = its.serialize(buffer, position, ismTransition);

            header = writeHeader(buffer, header, hasInstructionDetails, position);
            if(hasInstructionDetails){
                position = ids.serialize(buffer, position, instructionDetails);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Action a){
            int position = offset;

            ActionSerializer as = new ActionSerializer();

            position = as.serialize(buffer, position, a.getTime(),
                    a.getDescription(), a.getIsmTransition(),
                    a.getInstructionDetails());

            return position;
        }

        protected Action deserialize(Buffer buffer, int offset){
            int position = offset;

            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();
            ISMTransitionSerializer its = new ISMTransitionSerializer();
            InstructionDetailsSerializer ids = new
                    InstructionDetailsSerializer();

            int timePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime time = dts.deserialize(buffer, timePosition);

            int descriptionPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ItemStructure description = iss.deserialize(buffer,
                    descriptionPosition);

            int ismTransitionPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ISMTransition ismTransition = its.deserialize(buffer,
                    ismTransitionPosition);

            boolean hasInstructionDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            InstructionDetails instructionDetails = null;
            if(hasInstructionDetails){
                int instructionDetailsPosition = buffer.readInteger(position);
                instructionDetails = ids.deserialize(buffer,
                        instructionDetailsPosition);
            }

            return RMObjectFactory.newAction(time,description,ismTransition,
                    instructionDetails);
        }
    }

    public static class AdminEntrySerializer {
        protected int serialize(Buffer buffer, int offset, Entry entry,
                                ItemStructure data){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            EntrySerializer es = new EntrySerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = es.serialize(buffer, position, entry);

            writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, data);

            return position;

        }

        protected int serialize(Buffer buffer, int offset, AdminEntry a){
            int position = offset;
            AdminEntrySerializer aes = new AdminEntrySerializer();

            position = aes.serialize(buffer, position, a.getEntry(),
                    a.getData());

            return position;
        }

        protected AdminEntry deserialize(Buffer buffer, int offset){
            int position = offset;
            EntrySerializer es = new EntrySerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int entryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Entry entry = es.deserialize(buffer, entryPosition);

            int dataPosition = buffer.readInteger(position);
            ItemStructure data = iss.deserialize(buffer, dataPosition);

            return RMObjectFactory.newAdminEntry(entry, data);
        }
    }

    public static class EvaluationSerializer {
        protected int serialize(Buffer buffer, int offset, CareEntry careEntry,
                                ItemStructure data){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();
            CareEntrySerializer es = new CareEntrySerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = es.serialize(buffer, position, careEntry);

            writeHeader(buffer, header, position);
            position = iss.serialize(buffer, position, data);

            return position;

        }

        protected int serialize(Buffer buffer, int offset, Evaluation e){
            int position = offset;
            EvaluationSerializer es = new EvaluationSerializer();

            position = es.serialize(buffer, position, e.getCareEntry(),
                    e.getData());

            return position;
        }

        protected Evaluation deserialize(Buffer buffer, int offset){
            int position = offset;
            CareEntrySerializer ces = new CareEntrySerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer,
                    careEntryPosition);

            int dataPosition = buffer.readInteger(position);
            ItemStructure data = iss.deserialize(buffer, dataPosition);

            return RMObjectFactory.newEvaluation(careEntry, data);
        }
    }

    public static class InstructionSerializer {
        protected int serialize(Buffer buffer, int offset, CareEntry careEntry,
                                DvText narrative, List<Activity> activities,
                                DvDateTime expiryTime,DvParsable wfDefinition){
            int header = offset;
            int position = offset + 5 * PrimitiveTypeSize.INT.getSize()
                    + 3 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasActivities = activities != null;
            boolean hasExpiryTime = expiryTime != null;
            boolean hasWfDefinition = wfDefinition != null;

            CareEntrySerializer ces = new CareEntrySerializer();
            DvTextSerializer dts = new DvTextSerializer();
            ActivitySerializer as = new ActivitySerializer();
            DvDateTimeSerializer ddts = new DvDateTimeSerializer();
            DvParsableSerializer dps = new DvParsableSerializer();

            header = writeHeader(buffer, header, position);
            position = ces.serialize(buffer, position, careEntry);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, narrative);

            header = writeHeader(buffer, header, hasActivities, position);
            if(hasActivities){
                position = as.listSerialize(buffer, position, activities);
            }

            header = writeHeader(buffer, header, hasExpiryTime, position);
            if(hasExpiryTime){
                position = ddts.serialize(buffer, position, expiryTime);
            }

            writeHeader(buffer, header, hasWfDefinition, position);
            if(hasWfDefinition){
                position = dps.serialize(buffer, position, wfDefinition);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Instruction i){
            int position = offset;

            InstructionSerializer is = new InstructionSerializer();

            position = is.serialize(buffer, position, i.getCareEntry(),
                    i.getNarrative(), i.getActivities(), i.getExpiryTime(),
                    i.getWfDefinition());

            return position;
        }

        protected Instruction deserialize(Buffer buffer, int offset){
            int position = offset;

            CareEntrySerializer ces = new CareEntrySerializer();
            DvTextSerializer dts = new DvTextSerializer();
            ActivitySerializer as = new ActivitySerializer();
            DvDateTimeSerializer ddts = new DvDateTimeSerializer();
            DvParsableSerializer dps = new DvParsableSerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer, careEntryPosition);

            int narrativePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvText narrative = dts.deserialize(buffer, narrativePosition);

            boolean hasActivities = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<Activity> activities = null;
            if(hasActivities){
                int activitiesPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                activities = as.deserializeList(buffer, activitiesPosition);
            }

            boolean hasExpiryTime = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDateTime expiryTime = null;
            if(hasExpiryTime){
                int expiryTimePosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                expiryTime = ddts.deserialize(buffer, expiryTimePosition);
            }

            boolean hasWfDefinition = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvParsable wfDefinition = null;
            if(hasWfDefinition){
                int wfDefinitionPosition = buffer.readInteger(position);
                wfDefinition = dps.deserialize(buffer, wfDefinitionPosition);
            }

            return RMObjectFactory.newInstruction(careEntry, narrative,
                    activities, expiryTime, wfDefinition);
        }
    }

    public static class ObservationSerializer {
        protected int serializeItemTreeItemTree(Buffer buffer, int offset,
                                CareEntry careEntry,
                                HistoryWithItemTree data,
                                HistoryWithItemTree state){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + PrimitiveTypeSize.BOOLEAN.getSize();
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            boolean hasState = state != null;

            header = writeHeader(buffer, header, position);
            position = ces.serialize(buffer, position, careEntry);

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, data);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObservationWithItemTreeItemTree o){
            int position = offset;

            ObservationSerializer os = new ObservationSerializer();

            position = os.serializeItemTreeItemTree(buffer, position,
                    o.getCareEntry(), o.getData(), o.getState());

            return position;
        }

        protected ObservationWithItemTreeItemTree
            deserializeItemTreeItemTree(Buffer buffer, int offset){
            int position = offset;
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer, careEntryPosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HistoryWithItemTree data = hs.deserializeItemTree(buffer,
                    dataPosition);

            return RMObjectFactory.newObservationWithItemTreeItemTree(careEntry,
                    data, null);
        }

        protected int serializeItemTreeItemSingle(Buffer buffer, int offset,
                                                  CareEntry careEntry,
                                                  HistoryWithItemTree data,
                                                  HistoryWithItemSingle state){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + PrimitiveTypeSize.BOOLEAN.getSize();
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            boolean hasState = state != null;

            header = writeHeader(buffer, header, position);
            position = ces.serialize(buffer, position, careEntry);

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, data);

            writeHeader(buffer, header, hasState, position);
            if(hasState){
                position = hs.serialize(buffer, position, state);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObservationWithItemTreeItemSingle o){
            int position = offset;

            ObservationSerializer os = new ObservationSerializer();

            position = os.serializeItemTreeItemSingle(buffer, position,
                    o.getCareEntry(), o.getData(), o.getState());

            return position;
        }

        protected ObservationWithItemTreeItemSingle
            deserializeItemTreeItemSingle(Buffer buffer, int offset){
            int position = offset;
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer, careEntryPosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HistoryWithItemTree data = hs.deserializeItemTree(buffer,
                    dataPosition);

            boolean hasState = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            HistoryWithItemSingle state = null;
            if(hasState){
                int statePosition = buffer.readInteger(position);
                //state = hs.deserializeItemSingle(buffer, statePosition);
            }

            return RMObjectFactory.newObservationWithItemTreeItemSingle(careEntry,
                    data, state);
        }

        protected int serializeItemTreeItemTable(Buffer buffer, int offset,
                                                  CareEntry careEntry,
                                                  HistoryWithItemTree data,
                                                  HistoryWithItemTable state){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + PrimitiveTypeSize.BOOLEAN.getSize();
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            boolean hasState = state != null;

            header = writeHeader(buffer, header, position);
            position = ces.serialize(buffer, position, careEntry);

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, data);

            writeHeader(buffer, header, hasState, position);
            if(hasState){
                position = hs.serialize(buffer, position, state);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObservationWithItemTreeItemTable o){
            int position = offset;

            ObservationSerializer os = new ObservationSerializer();

            position = os.serializeItemTreeItemTable(buffer, position,
                    o.getCareEntry(), o.getData(), o.getState());

            return position;
        }

        protected ObservationWithItemTreeItemTable
            deserializeItemTreeItemTable(Buffer buffer, int offset){
            int position = offset;
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer, careEntryPosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HistoryWithItemTree data = hs.deserializeItemTree(buffer,
                    dataPosition);

            boolean hasState = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            HistoryWithItemTable state = null;
            if(hasState){
                int statePosition = buffer.readInteger(position);
                //state = hs.deserializeItemTable(buffer, statePosition);
            }

            return RMObjectFactory.newObservationWithItemTreeItemTable(careEntry,
                    data, state);
        }

        protected int serializeItemSingleItemTree(Buffer buffer, int offset,
                                                  CareEntry careEntry,
                                                  HistoryWithItemSingle data,
                                                  HistoryWithItemTree state){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + PrimitiveTypeSize.BOOLEAN.getSize();
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            boolean hasState = state != null;

            header = writeHeader(buffer, header, position);
            position = ces.serialize(buffer, position, careEntry);

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, data);

            writeHeader(buffer, header, hasState, position);
            if(hasState){
                position = hs.serialize(buffer, position, state);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObservationWithItemSingleItemTree o){
            int position = offset;

            ObservationSerializer os = new ObservationSerializer();

            position = os.serializeItemSingleItemTree(buffer, position,
                    o.getCareEntry(), o.getData(), o.getState());

            return position;
        }

        protected ObservationWithItemSingleItemTree
        deserializeItemSingleItemTree(Buffer buffer, int offset){
            int position = offset;
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer, careEntryPosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HistoryWithItemSingle data = hs.deserializeItemSingle(buffer,
                    dataPosition);

            boolean hasState = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            HistoryWithItemTree state = null;
            if(hasState){
                int statePosition = buffer.readInteger(position);
                //state = hs.deserializeItemTree(buffer, statePosition);
            }

            return RMObjectFactory.newObservationWithItemSingleItemTree(careEntry,
                    data, state);
        }

        protected int serializeItemSingleItemSingle(Buffer buffer, int offset,
                                                    CareEntry careEntry,
                                                    HistoryWithItemSingle data,
                                                    HistoryWithItemSingle state){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + PrimitiveTypeSize.BOOLEAN.getSize();
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            boolean hasState = state != null;

            header = writeHeader(buffer, header, position);
            position = ces.serialize(buffer, position, careEntry);

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, data);

            writeHeader(buffer, header, hasState, position);
            if(hasState){
                position = hs.serialize(buffer, position, state);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObservationWithItemSingleItemSingle o){
            int position = offset;

            ObservationSerializer os = new ObservationSerializer();

            position = os.serializeItemSingleItemSingle(buffer, position,
                    o.getCareEntry(), o.getData(), o.getState());

            return position;
        }

        protected ObservationWithItemSingleItemSingle
        deserializeItemSingleItemSingle(Buffer buffer, int offset){
            int position = offset;
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer, careEntryPosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HistoryWithItemSingle data = hs.deserializeItemSingle(buffer,
                    dataPosition);

            boolean hasState = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            HistoryWithItemSingle state = null;
            if(hasState){
                int statePosition = buffer.readInteger(position);
                //state = hs.deserializeItemSingle(buffer, statePosition);
            }

            return RMObjectFactory.newObservationWithItemSingleItemSingle(careEntry,
                    data, state);
        }

        protected int serializeItemSingleItemTable(Buffer buffer, int offset,
                                                   CareEntry careEntry,
                                                   HistoryWithItemSingle data,
                                                   HistoryWithItemTable state){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + PrimitiveTypeSize.BOOLEAN.getSize();
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            boolean hasState = state != null;

            header = writeHeader(buffer, header, position);
            position = ces.serialize(buffer, position, careEntry);

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, data);

            writeHeader(buffer, header, hasState, position);
            if(hasState){
                position = hs.serialize(buffer, position, state);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObservationWithItemSingleItemTable o){
            int position = offset;

            ObservationSerializer os = new ObservationSerializer();

            position = os.serializeItemSingleItemTable(buffer, position,
                    o.getCareEntry(), o.getData(), o.getState());

            return position;
        }

        protected ObservationWithItemSingleItemTable
        deserializeItemSingleItemTable(Buffer buffer, int offset){
            int position = offset;
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer, careEntryPosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HistoryWithItemSingle data = hs.deserializeItemSingle(buffer,
                    dataPosition);

            boolean hasState = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            HistoryWithItemTable state = null;
            if(hasState){
                int statePosition = buffer.readInteger(position);
                //state = hs.deserializeItemTable(buffer, statePosition);
            }

            return RMObjectFactory.newObservationWithItemSingleItemTable(careEntry,
                    data, state);
        }

        protected int serializeItemTableItemTree(Buffer buffer, int offset,
                                                 CareEntry careEntry,
                                                 HistoryWithItemTable data,
                                                 HistoryWithItemTree state){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + PrimitiveTypeSize.BOOLEAN.getSize();
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            boolean hasState = state != null;

            header = writeHeader(buffer, header, position);
            position = ces.serialize(buffer, position, careEntry);

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, data);

            writeHeader(buffer, header, hasState, position);
            if(hasState){
                position = hs.serialize(buffer, position, state);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObservationWithItemTableItemTree o){
            int position = offset;

            ObservationSerializer os = new ObservationSerializer();

            position = os.serializeItemTableItemTree(buffer, position,
                    o.getCareEntry(), o.getData(), o.getState());

            return position;
        }

        protected ObservationWithItemTableItemTree
        deserializeItemTableItemTree(Buffer buffer, int offset){
            int position = offset;
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer, careEntryPosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HistoryWithItemTable data = hs.deserializeItemTable(buffer,
                    dataPosition);

            boolean hasState = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            HistoryWithItemTree state = null;
            if(hasState){
                int statePosition = buffer.readInteger(position);
                //state = hs.deserializeItemTree(buffer, statePosition);
            }

            return RMObjectFactory.newObservationWithItemTableItemTree(careEntry,
                    data, state);
        }

        protected int serializeItemTableItemSingle(Buffer buffer, int offset,
                                                   CareEntry careEntry,
                                                   HistoryWithItemTable data,
                                                   HistoryWithItemSingle state){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + PrimitiveTypeSize.BOOLEAN.getSize();
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            boolean hasState = state != null;

            header = writeHeader(buffer, header, position);
            position = ces.serialize(buffer, position, careEntry);

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, data);

            writeHeader(buffer, header, hasState, position);
            if(hasState){
                position = hs.serialize(buffer, position, state);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObservationWithItemTableItemSingle o){
            int position = offset;

            ObservationSerializer os = new ObservationSerializer();

            position = os.serializeItemTableItemSingle(buffer, position,
                    o.getCareEntry(), o.getData(), o.getState());

            return position;
        }

        protected ObservationWithItemTableItemSingle
        deserializeItemTableItemSingle(Buffer buffer, int offset){
            int position = offset;
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer, careEntryPosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HistoryWithItemTable data = hs.deserializeItemTable(buffer,
                    dataPosition);

            boolean hasState = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            HistoryWithItemSingle state = null;
            if(hasState){
                int statePosition = buffer.readInteger(position);
                //state = hs.deserializeItemSingle(buffer, statePosition);
            }

            return RMObjectFactory.newObservationWithItemTableItemSingle(careEntry,
                    data, state);
        }

        protected int serializeItemTableItemTable(Buffer buffer, int offset,
                                                  CareEntry careEntry,
                                                  HistoryWithItemTable data,
                                                  HistoryWithItemTable state){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + PrimitiveTypeSize.BOOLEAN.getSize();
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            boolean hasState = state != null;

            header = writeHeader(buffer, header, position);
            position = ces.serialize(buffer, position, careEntry);

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, data);

            writeHeader(buffer, header, hasState, position);
            if(hasState){
                position = hs.serialize(buffer, position, state);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                ObservationWithItemTableItemTable o){
            int position = offset;

            ObservationSerializer os = new ObservationSerializer();

            position = os.serializeItemTableItemTable(buffer, position,
                    o.getCareEntry(), o.getData(), o.getState());

            return position;
        }

        protected ObservationWithItemTableItemTable
        deserializeItemTableItemTable(Buffer buffer, int offset){
            int position = offset;
            CareEntrySerializer ces = new CareEntrySerializer();
            HistorySerializer hs = new HistorySerializer();

            int careEntryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CareEntry careEntry = ces.deserialize(buffer, careEntryPosition);

            int dataPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HistoryWithItemTable data = hs.deserializeItemTable(buffer,
                    dataPosition);

            boolean hasState = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            HistoryWithItemTable state = null;
            if(hasState){
                int statePosition = buffer.readInteger(position);
                //state = hs.deserializeItemTable(buffer, statePosition);
            }

            return RMObjectFactory.newObservationWithItemTableItemTable(careEntry,
                    data, state);
        }
    }

    public static class SectionSerializer {
        protected int serialize(Buffer buffer, int offset,
                                ContentItem contentItem, List<ContentItem> items){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize()
                    + PrimitiveTypeSize.BOOLEAN.getSize();
            boolean hasItems = items != null;

            ContentItemSerializer cis = new ContentItemSerializer();

            header = writeHeader(buffer, header, position);
            position = cis.serialize(buffer, position, contentItem);

            writeHeader(buffer, header, hasItems, position);
            if(hasItems){
                position = cis.listSerialize(buffer, position, items);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Section s){
            int position = offset;

            SectionSerializer ss = new SectionSerializer();

            position = ss.serialize(buffer, position, s.getContentItem(),
                    s.getItems());

            return position;
        }

        protected Section deserialize(Buffer buffer, int offset){
            int position = offset;

            ContentItemSerializer cs = new ContentItemSerializer();

            int contentItemPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ContentItem contentItem = cs.deserialize(buffer, contentItemPosition);

            boolean hasItems = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<ContentItem> items = null;
            if(hasItems){
                int itemsPosition = buffer.readInteger(position);
                items = cs.deserializeList(buffer, itemsPosition);
            }

            return RMObjectFactory.newSection(contentItem, items);
        }
    }

    public static class EventContextSerializer {
        protected int serialize(Buffer buffer, int offset,
                                PartyIdentified healthCareFacility,
                                DvDateTime startTime, DvDateTime endTime,
                                List<Participation> participations,
                                String location, DvCodedText setting,
                                ItemStructure otherContext){
            int header = offset;
            int position = offset + 7 * PrimitiveTypeSize.INT.getSize()
                + 5 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasHealthCareFacility = healthCareFacility != null;
            boolean hasEndTime = endTime != null;
            boolean hasParticipations = participations != null;
            boolean hasLocation = location != null;
            boolean hasOtherContext = otherContext != null;

            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ParticipationSerializer ps = new ParticipationSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, hasHealthCareFacility, position);
            if(hasHealthCareFacility){
                position = pis.serialize(buffer, position, healthCareFacility);
            }

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, startTime);

            header = writeHeader(buffer, header, hasEndTime, position);
            if(hasEndTime){
                position = dts.serialize(buffer, position, endTime);
            }

            header = writeHeader(buffer, header, hasParticipations, position);
            if(hasParticipations){
                position = ps.listSerialize(buffer, position, participations);
            }

            header = writeHeader(buffer, header, hasLocation, position);
            if(hasLocation){
                position = stringSerialization(buffer, position, location);
            }

            header = writeHeader(buffer, header, position);
            position = dcs.serialize(buffer, position, setting);

            header = writeHeader(buffer, header, hasOtherContext, position);
            position = iss.serialize(buffer, position, otherContext);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, EventContext e){
            int position = offset;

            EventContextSerializer ecs = new EventContextSerializer();

            position = ecs.serialize(buffer, position, e.getHealthCareFacility(),
                    e.getStartTime(), e.getEndTime(), e.getParticipations(),
                    e.getLocation(), e.getSetting(), e.getOtherContext());

            return position;
        }

        protected EventContext deserialize(Buffer buffer, int offset){
            int position = offset;

            PartyIdentifiedSerializer pis = new PartyIdentifiedSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ParticipationSerializer ps = new ParticipationSerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            boolean hasHealthCareFacility = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            PartyIdentified healthCareFacility = null;
            if(hasHealthCareFacility){
                int healthCareFacilityPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                healthCareFacility = pis.deserialize(buffer,
                        healthCareFacilityPosition);
            }
            int startTimePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime startTime = dts.deserialize(buffer, startTimePosition);

            boolean hasEndTime = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDateTime endTime = null;
            if(hasEndTime){
                int endTimePosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                endTime = dts.deserialize(buffer, endTimePosition);
            }

            boolean hasParticipations = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<Participation> participations = null;
            if(hasParticipations){
                int participationPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                participations = ps.deserializeList(buffer,
                        participationPosition);
            }

            boolean hasLocation = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            String location = null;
            if(hasLocation){
                int locationPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                location = stringDeserialization(buffer,
                        locationPosition);
            }

            int settingPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvCodedText setting = dcs.deserialize(buffer, settingPosition);

            boolean hasOtherContext = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructure otherContext = null;
            if(hasOtherContext){
                int otherContextPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
               otherContext = iss.deserialize(buffer,
                        otherContextPosition);
            }

            return RMObjectFactory.newEventContext(healthCareFacility,
                    startTime, endTime, participations, location, setting,
                    otherContext);
        }
    }

    public static class CompositionSerializer {
        protected int serialize(Buffer buffer, int offset, Locatable locatable,
                                List<ContentItem> content, CodePhrase language,
                                EventContext context, PartyProxy composer,
                                DvCodedText category, CodePhrase territory){
            int header = offset;
            int position = offset + 7 * PrimitiveTypeSize.INT.getSize()
                + 2 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasContent = content != null;
            boolean hasContext = context != null;

            LocatableSerializer ls = new LocatableSerializer();
            ContentItemSerializer cis = new ContentItemSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();
            EventContextSerializer ecs = new EventContextSerializer();
            PartyProxySerializer pps = new PartyProxySerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            header = writeHeader(buffer, header, hasContent, position);
            if(hasContent){
                position = cis.listSerialize(buffer, position, content);
            }

            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position, language);

            header = writeHeader(buffer, header, hasContext, position);
            if(hasContext){
                position = ecs.serialize(buffer, position, context);
            }

            header = writeHeader(buffer, header, position);
            position = pps.serialize(buffer, position, composer);

            header = writeHeader(buffer, header, position);
            position = dcs.serialize(buffer, position, category);

            header = writeHeader(buffer, header, position);
            position = cps.serialize(buffer, position ,territory);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Composition c){
            int position = offset;

            CompositionSerializer cps = new CompositionSerializer();

            position = cps.serialize(buffer, position, c.getLocatable(),
                    c.getContent(), c.getLanguage(), c.getContext(),
                    c.getComposer(), c.getCategory(), c.getTerritory());

            return position;
        }

        protected Composition deserialize(Buffer buffer, int offset){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();
            ContentItemSerializer cis = new ContentItemSerializer();
            CodePhraseSerializer cps = new CodePhraseSerializer();
            EventContextSerializer ecs = new EventContextSerializer();
            PartyProxySerializer pps = new PartyProxySerializer();
            DvCodedTextSerializer dcs = new DvCodedTextSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            boolean hasContent = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<ContentItem> content = null;
            if(hasContent){
                int contentPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                content = cis.deserializeList(buffer, contentPosition);
            }

            int languagePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            CodePhrase language = cps.deserialize(buffer, languagePosition);

            boolean hasContext = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            EventContext context = null;
            if(hasContext){
                int contextPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                context = ecs.deserialize(buffer, contextPosition);
            }

            int composerPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            PartyProxy composer = pps.deserialize(buffer, composerPosition);

            int categoryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvCodedText category = dcs.deserialize(buffer, categoryPosition);

            int territoryPosition = buffer.readInteger(position);
            CodePhrase territory = cps.deserialize(buffer, territoryPosition);

            return RMObjectFactory.newComposition(locatable, content, language,
                    context, composer, category, territory);
        }
    }

    public static class EHRSerializer {
        protected int serialize(Buffer buffer, int offset, HierObjectID systemID,
                                HierObjectID ehrID, DvDateTime timeCreated,
                                List<ObjectRef> contributions,
                                ObjectRef ehrStatus, ObjectRef directory,
                                List<ObjectRef> compositions){
            int header = offset;
            int position = offset + 7 * PrimitiveTypeSize.INT.getSize();

            HierObjectIDSerializer hs = new HierObjectIDSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, systemID);

            header = writeHeader(buffer, header, position);
            position = hs.serialize(buffer, position, ehrID);

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, timeCreated);

            header = writeHeader(buffer, header, position);
            position = ors.listSerialize(buffer, position, contributions);

            header = writeHeader(buffer, header, position);
            position = ors.serialize(buffer, position, ehrStatus);

            header = writeHeader(buffer, header, position);
            position = ors.serialize(buffer, position, directory);

            header = writeHeader(buffer, header, position);
            position = ors.listSerialize(buffer, position, compositions);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, EHR e){
            int position = offset;

            EHRSerializer es = new EHRSerializer();

            position = es.serialize(buffer, position, e.getSystemID(),
                    e.getEhrID(), e.getTimeCreated(), e.getContributions(),
                    e.getEhrStatus(), e.getDirectory(), e.getCompositions());

            return position;
        }

        protected EHR deserialize(Buffer buffer, int offset){
            int position = offset;
            HierObjectIDSerializer hs = new HierObjectIDSerializer();
            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            ObjectRefSerializer ors = new ObjectRefSerializer();

            int systemIDposition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HierObjectID systemID = hs.deserialize(buffer, systemIDposition);

            int ehrIDposition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            HierObjectID ehrID = hs.deserialize(buffer, ehrIDposition);

            int timeCreatedPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime timeCreated = dts.deserialize(buffer,
                    timeCreatedPosition);

            int contributionsPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            List<ObjectRef> contributions = ors.deserializeList(buffer,
                    contributionsPosition);

            int ehrStatusPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ObjectRef ehrStatus = ors.deserialize(buffer, ehrStatusPosition);

            int directoryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ObjectRef directory = ors.deserialize(buffer, directoryPosition);

            int compositionPosition = buffer.readInteger(position);
            List<ObjectRef> compositions = ors.deserializeList(buffer,
                    compositionPosition);

            return RMObjectFactory.newEHR(systemID, ehrID, timeCreated,
                    contributions, ehrStatus, directory, compositions);
        }
    }

    public static class EHRStatusSerializer {
        protected int serialize(Buffer buffer, int offset, Locatable locatable,
                                PartySelf subject, boolean isQueryable,
                                boolean isModifiable,ItemStructure otherDetails){
            int header = offset;
            int position = offset + 5 * PrimitiveTypeSize.INT.getSize()
                    + 2 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasSubject = subject != null;
            boolean hasOtherDetails = otherDetails != null;

            LocatableSerializer ls = new LocatableSerializer();
            PartySelfSerializer pss = new PartySelfSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            header = writeHeader(buffer, header, hasSubject, position);
            if(hasSubject){
                position = pss.serialize(buffer, position, subject);
            }

            header = writeHeader(buffer, header, position);
            buffer.writeBoolean(position, isQueryable);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            header = writeHeader(buffer, header, position);
            buffer.writeBoolean(position, isModifiable);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            writeHeader(buffer, header, hasOtherDetails, position);
            position = iss.serialize(buffer, position, otherDetails);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, EHRStatus e){
            int position = offset;

            EHRStatusSerializer ess = new EHRStatusSerializer();

            position = ess.serialize(buffer, position, e.getLocatable(),
                    e.getSubject(), e.isQueryable(), e.isModifiable(),
                    e.getOtherDetails());

            return position;
        }

        protected EHRStatus deserialize(Buffer buffer, int offset){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();
            PartySelfSerializer pss = new PartySelfSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            boolean hasSubject = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            PartySelf subject = null;
            if(hasSubject){
                int subjectPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                subject = pss.deserialize(buffer, subjectPosition);
            }

            int isQueryablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            boolean isQueryable = buffer.readBoolean(isQueryablePosition);

            int isModifiablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            boolean isModifiable = buffer.readBoolean(isModifiablePosition);

            boolean hasOtherDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructure otherDetails = null;
            if(hasOtherDetails){
                int hasOtherDetailsPosition = buffer.readInteger(position);
                otherDetails = iss.deserialize(buffer, hasOtherDetailsPosition);
            }

            return RMObjectFactory.newEHRStatus(locatable, subject, isQueryable,
                    isModifiable, otherDetails);
        }
    }

    public static class EHRAccessSerializer {
        protected int serialize(Buffer buffer, int offset, Locatable locatable){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();

            position = ls.serialize(buffer, position, locatable);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, EHRAccess e){
            int position = offset;

            EHRAccessSerializer es = new EHRAccessSerializer();

            position = es.serialize(buffer, position, e.getLocatable());

            return position;
        }

        protected EHRAccess deserialize(Buffer buffer, int offset){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();

            Locatable locatable = ls.deserialize(buffer, position);

            return RMObjectFactory.newEHRAccess(locatable);
        }
    }

    public static class XTerminologySerializer {
        protected int serialize(Buffer buffer, int offset,
                                ItemStructure itemStructure){
            int position = offset;

            ItemStructureSerializer iss = new ItemStructureSerializer();

            position = iss.serialize(buffer, position, itemStructure);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, XTerminology t){
            int position = offset;

            XTerminologySerializer xts = new XTerminologySerializer();

            position = xts.serialize(buffer, position, t.getItemStructure());

            return position;
        }

        protected XTerminology deserialize(Buffer buffer, int offset){
            int position = offset;

            ItemStructureSerializer iss = new ItemStructureSerializer();

            ItemStructure itemStructure = iss.deserialize(buffer, position);

            return RMObjectFactory.newXTerminology(itemStructure);
        }
    }

    public static class XCompositionSerializer {
        protected int serialize(Buffer buffer, int offset, boolean primary,
                                DvEHRURI originalPath, Composition composition){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize();

            DvEHRURISerializer ds = new DvEHRURISerializer();
            CompositionSerializer cs = new CompositionSerializer();

            header = writeHeader(buffer, header, position);
            buffer.writeBoolean(position, primary);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            header = writeHeader(buffer, header, position);
            position = ds.serialize(buffer, position, originalPath);

            writeHeader(buffer, header, position);
            position = cs.serialize(buffer, position, composition);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, XComposition c){
            int position = offset;

            XCompositionSerializer xcs = new XCompositionSerializer();

            position = xcs.serialize(buffer, position, c.isPrimary(),
                    c.getOriginalPath(), c.getComposition());

            return position;
        }

        protected XComposition deserialize(Buffer buffer, int offset){
            int position = offset;

            DvEHRURISerializer ds = new DvEHRURISerializer();
            CompositionSerializer cs = new CompositionSerializer();

            int primaryPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            boolean primary = buffer.readBoolean(primaryPosition);

            int originalPathPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvEHRURI originalPath = ds.deserialize(buffer, originalPathPosition);

            int compositionPosition = buffer.readInteger(position);
            Composition composition = cs.deserialize(buffer,
                    compositionPosition);

            return RMObjectFactory.newXComposition(primary, originalPath,
                    composition);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<XComposition> items){
            int header = offset;
            if(items.size() == 0){
                return offset;
            }
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, listSize);
            XCompositionSerializer xcs = new XCompositionSerializer();

            for (XComposition c : items){
                header = writeHeader(buffer, header, position);
                position = xcs.serialize(buffer, position, c);
            }

            return position;
        }

        protected List<XComposition> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            if(listSize == 0){
                List<XComposition> list = new ArrayList<>();
                return list;
            }
            List<XComposition> list = new ArrayList<>();
            XCompositionSerializer dis = new XCompositionSerializer();

            for (int i = 0; i < listSize; i++){
                int xCompositionPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                XComposition c = dis.deserialize(buffer, xCompositionPosition);
                list.add(c);
            }

            return list;
        }
    }

    public static class XDemographicsSerializer {
        protected int serialize(Buffer buffer, int offset,
                                Map<ObjectID, Party> parties,
                                ItemStructure details){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize()
                    + 2 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasParties = parties != null;
            boolean hasDetails = details != null;

            ObjectIDSerializer ois = new ObjectIDSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, hasParties, position);
            if(hasParties){
                position = ois.mapSerialization(buffer, position, parties);
            }

            header = writeHeader(buffer, header, hasDetails, position);
            if(hasDetails){
                position = iss.serialize(buffer, position, details);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, XDemographics d){
            int position = offset;

            XDemographicsSerializer xds = new XDemographicsSerializer();

            position = xds.serialize(buffer, position, d.getParties(),
                    d.getDetails());

            return position;
        }

        protected XDemographics deserialize(Buffer buffer, int offset){
            int position = offset;

            ObjectIDSerializer ois = new ObjectIDSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            boolean hasParties = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Map<ObjectID, Party> parties = null;
            if(hasParties){
                int partiesPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                parties = ois.mapDeserialization(buffer,
                        partiesPosition);
            }

            boolean hasDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructure details = null;
            if(hasDetails){
                int detailsPosition = buffer.readInteger(position);
                details = iss.deserialize(buffer, detailsPosition);
            }

            return RMObjectFactory.newXDemographics(parties, details);
        }
    }

    public static class XFolderSerializer {
        protected int serialize(Buffer buffer, int offset, Locatable locatable,
                                List<XFolder> folders,
                                List<XComposition> compositions){
            int header = offset;
            int position = offset + 3 * PrimitiveTypeSize.INT.getSize()
                    + 2 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasFolders = folders != null;
            boolean hasCompositions = compositions != null;

            LocatableSerializer ls = new LocatableSerializer();
            XFolderSerializer xfs = new XFolderSerializer();
            XCompositionSerializer xcs = new XCompositionSerializer();

            header = writeHeader(buffer, header, position);
            position = ls.serialize(buffer, position, locatable);

            header = writeHeader(buffer, header, hasFolders, position);
            if(hasFolders){
                position = xfs.listSerialize(buffer, position, folders);
            }

            writeHeader(buffer, header, hasCompositions, position);
            if(hasCompositions){
                position = xcs.listSerialize(buffer, position, compositions);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, XFolder f){
            int position = offset;

            XFolderSerializer xfs = new XFolderSerializer();

            position = xfs.serialize(buffer, position, f.getLocatable(),
                    f.getFolders(), f.getCompositions());

            return position;
        }

        protected XFolder deserialize(Buffer buffer, int offset){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();
            XFolderSerializer xfs = new XFolderSerializer();
            XCompositionSerializer xcs = new XCompositionSerializer();

            int locatablePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Locatable locatable = ls.deserialize(buffer, locatablePosition);

            boolean hasFolders = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<XFolder> folders = null;
            if(hasFolders){
                int xFoldersPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                folders = xfs.deserializeList(buffer,
                        xFoldersPosition);
            }

            boolean hasCompositions = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            List<XComposition> compositions = null;
            if(hasCompositions){
                int compositionPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                compositions = xcs.deserializeList(buffer,
                        compositionPosition);
            }

            return RMObjectFactory.newXFolder(locatable, folders, compositions);
        }

        protected int listSerialize(
                Buffer buffer, int offset, List<XFolder> items){
            int header = offset;
            int listSize = items.size();
            int position = offset + (listSize *
                    PrimitiveTypeSize.INT.getSize()) +
                    PrimitiveTypeSize.INT.getSize();
            header = writeHeader(buffer, header, listSize);
            XFolderSerializer xfs = new XFolderSerializer();

            for (XFolder f : items){
                header = writeHeader(buffer, header, position);
                position = xfs.serialize(buffer, position, f);
            }

            return position;
        }

        protected List<XFolder> deserializeList(Buffer buffer, int offset){
            int position = offset;
            int listSize = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();

            List<XFolder> list = new ArrayList<>();
            XFolderSerializer dis = new XFolderSerializer();

            for (int i = 0; i < listSize; i++){
                int xFolderPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                XFolder f = dis.deserialize(buffer, xFolderPosition);
                list.add(f);
            }

            return list;
        }
    }

    public static class XAccessControlSerializer {
        protected int serialize(Buffer buffer, int offset,
                                Map<ObjectID, Party> groups,
                                ItemStructure details){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize()
                    + 2 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasParties = groups != null;
            boolean hasDetails = details != null;

            ObjectIDSerializer ois = new ObjectIDSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            header = writeHeader(buffer, header, hasParties, position);
            if(hasParties){
                position = ois.mapSerialization(buffer, position, groups);
            }

            writeHeader(buffer, header, hasDetails, position);
            if(hasDetails){
                position = iss.serialize(buffer, position, details);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, XAccessControl d){
            int position = offset;

            XAccessControlSerializer xds = new XAccessControlSerializer();

            position = xds.serialize(buffer, position, d.getGroups(),
                    d.getDetails());

            return position;
        }

        protected XAccessControl deserialize(Buffer buffer, int offset){
            int position = offset;

            ObjectIDSerializer ois = new ObjectIDSerializer();
            ItemStructureSerializer iss = new ItemStructureSerializer();

            boolean hasParties = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Map<ObjectID, Party> groups = null;
            if(hasParties){
                int groupsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                groups = ois.mapDeserialization(buffer,
                        groupsPosition);
            }

            boolean hasDetails = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            ItemStructure details = null;
            if(hasDetails){
                int detailsPosition = buffer.readInteger(position);
                details = iss.deserialize(buffer, detailsPosition);
            }

            return RMObjectFactory.newXAccessControl(groups, details);
        }
    }

    public static class EHRExtractSerializer {
        protected int serialize(Buffer buffer, int offset,
                                DvDateTime timeCreated, String ehrId,
                                PartyRef subjectOfCare, PartyRef originator,
                                Set<Participation> otherParticipations,
                                boolean includeMultimedia, int followLinks,
                                XFolder directory, XTerminology terminology,
                                XDemographics demographics,
                                XAccessControl accessControl){
            int header = offset;
            int position = offset + 11 * PrimitiveTypeSize.INT.getSize()
                    + 9 * PrimitiveTypeSize.BOOLEAN.getSize();

            boolean hasTimeCreated = timeCreated != null;
            boolean hasEhrId = ehrId != null;
            boolean hasSubjectOfCare = subjectOfCare != null;
            boolean hasOriginator = originator != null;
            boolean hasOtherParticipations = otherParticipations != null;
            boolean hasDirectory = directory != null;
            boolean hasTerminology = terminology != null;
            boolean hasDemographics = demographics != null;
            boolean hasAccessControl = accessControl != null;

            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            PartyRefSerializer prs = new PartyRefSerializer();
            ParticipationSerializer ps = new ParticipationSerializer();
            XFolderSerializer xfs = new XFolderSerializer();
            XTerminologySerializer xts = new XTerminologySerializer();
            XDemographicsSerializer xds = new XDemographicsSerializer();
            XAccessControlSerializer xas = new XAccessControlSerializer();

            header = writeHeader(buffer, header, hasTimeCreated, position);
            if(hasTimeCreated){
                position = dts.serialize(buffer, position, timeCreated);
            }

            header = writeHeader(buffer, header, hasEhrId, position);
            if(hasEhrId){
                position = stringSerialization(buffer, position, ehrId);
            }

            header = writeHeader(buffer, header, hasSubjectOfCare, position);
            if(hasSubjectOfCare){
                position = prs.serialize(buffer, position, subjectOfCare);
            }

            header = writeHeader(buffer, header, hasOriginator, position);
            if(hasOriginator){
                position = prs.serialize(buffer, position, originator);
            }

            header = writeHeader(buffer, header, hasOtherParticipations, position);
            if(hasOtherParticipations){
                position = ps.setSerializer(buffer, position,
                        otherParticipations);
            }

            header = writeHeader(buffer, header, position);
            buffer.writeBoolean(position, includeMultimedia);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            header = writeHeader(buffer, header, position);
            buffer.writeInteger(position, followLinks);
            position += PrimitiveTypeSize.INT.getSize();

            header = writeHeader(buffer, header, hasDirectory, position);
            if(hasDirectory){
                position = xfs.serialize(buffer, position, directory);
            }

            header = writeHeader(buffer, header, hasTerminology, position);
            if(hasTerminology){
                position = xts.serialize(buffer, position, terminology);
            }

            header = writeHeader(buffer, header, hasDemographics, position);
            if(hasDemographics){
                position = xds.serialize(buffer, position, demographics);
            }

            writeHeader(buffer, header, hasAccessControl, position);
            if(hasAccessControl){
                position = xas.serialize(buffer, position, accessControl);
            }

            return position;
        }

        protected int serialize(Buffer buffer, int offset, EHRExtract e){
            int position = offset;

            EHRExtractSerializer es = new EHRExtractSerializer();

            position = es.serialize(buffer, position, e.getTimeCreated(),
                    e.getEhrId(), e.getSubjectOfCare(), e.getOriginator(),
                    e.getOtherParticipations(), e.isIncludeMultimedia(),
                    e.getFollowLinks(), e.getDirectory(), e.getTerminology(),
                    e.getDemographics(), e.getAccessControl());

            return position;
        }

        protected EHRExtract deserialize(Buffer buffer, int offset){
            int position = offset;

            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            PartyRefSerializer prs = new PartyRefSerializer();
            ParticipationSerializer ps = new ParticipationSerializer();
            XFolderSerializer xfs = new XFolderSerializer();
            XTerminologySerializer xts = new XTerminologySerializer();
            XDemographicsSerializer xds = new XDemographicsSerializer();
            XAccessControlSerializer xas = new XAccessControlSerializer();

            boolean hasTimeCreated = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            DvDateTime timeCreated = null;
            if(hasTimeCreated){
                int timeCreatedPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                timeCreated = dts.deserialize(buffer, timeCreatedPosition);
            }

            boolean hasEhrId = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            String ehrId = null;
            if(hasEhrId){
                int ehrIdPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                ehrId = stringDeserialization(buffer, ehrIdPosition);
            }

            boolean hasSubjectOfCare = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            PartyRef subjectOfCare = null;
            if(hasSubjectOfCare){
                int subjectOfCarePosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                subjectOfCare = prs.deserialize(buffer, subjectOfCarePosition);
            }

            boolean hasOriginator = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            PartyRef originator = null;
            if(hasOriginator){
                int originatorPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                originator = prs.deserialize(buffer, originatorPosition);
            }

            boolean hasOtherParticipations = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            Set<Participation> otherParticipations = null;
            if(hasOtherParticipations){
                int otherParticipationsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                otherParticipations = ps.setDeserializer(buffer,
                        otherParticipationsPosition);
            }

            int includeMultimediaPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            boolean includeMultimedia = buffer.readBoolean(
                    includeMultimediaPosition);

            int followLinksPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            int followLinks = buffer.readInteger(followLinksPosition);

            boolean hasDirectory = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            XFolder directory = null;
            if(hasDirectory){
                int directoryPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                directory = xfs.deserialize(buffer, directoryPosition);
            }

            boolean hasTerminology = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            XTerminology terminology = null;
            if(hasTerminology){
                int terminologyPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                terminology = xts.deserialize(buffer, terminologyPosition);
            }

            boolean hasDemographics = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            XDemographics demographics = null;
            if(hasDemographics){
                int demographicsPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                demographics = xds.deserialize(buffer, demographicsPosition);
            }

            boolean hasAccessControl = buffer.readBoolean(position);
            position += PrimitiveTypeSize.BOOLEAN.getSize();
            XAccessControl accessControl = null;
            if(hasAccessControl){
                int accessControlPosition = buffer.readInteger(position);
                position += PrimitiveTypeSize.INT.getSize();
                accessControl = xas.deserialize(buffer, accessControlPosition);
            }

            return RMObjectFactory.newEHRExtract(timeCreated, ehrId,
                    subjectOfCare, originator, otherParticipations,
                    includeMultimedia, followLinks, directory, terminology,
                    demographics, accessControl);
        }
    }

    public static class GenericEntrySerializer {
        protected int serialize(Buffer buffer, int offset,
                                ContentItem contentItem, ItemTree data){
            int header = offset;
            int position = offset + 2 * PrimitiveTypeSize.INT.getSize();

            ContentItemSerializer cis = new ContentItemSerializer();
            ItemTreeSerializer its = new ItemTreeSerializer();

            header = writeHeader(buffer, header, position);
            position = cis.serialize(buffer, position, contentItem);

            writeHeader(buffer, header, position);
            position = its.serialize(buffer, position, data);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, GenericEntry g){
            int position = offset;

            GenericEntrySerializer ges = new GenericEntrySerializer();

            position = ges.serialize(buffer, position, g.getContentItem(),
                    g.getData());

            return position;
        }

        protected GenericEntry deserialize(Buffer buffer, int offset){
            int position = offset;
            ContentItemSerializer cis = new ContentItemSerializer();
            ItemTreeSerializer its = new ItemTreeSerializer();

            int contentItemPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            ContentItem contentItem = cis.deserialize(buffer,
                    contentItemPosition);

            int dataPosition = buffer.readInteger(position);
            ItemTree data = its.deserialize(buffer, dataPosition);

            return RMObjectFactory.newGenericEntry(contentItem, data);
        }
    }

    public static class MessageContentSerializer {
        protected int serialize(Buffer buffer, int offset, Locatable locatable){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();

            position = ls.serialize(buffer, position, locatable);

            return position;
        }

        protected int serialize(Buffer buffer, int offset,
                                MessageContent messageContent){
            int position = offset;

            MessageContentSerializer mcs = new MessageContentSerializer();

            position = mcs.serialize(buffer, position,
                    messageContent.getLocatable());

            return position;
        }

        protected MessageContent deserialize(Buffer buffer, int offset){
            int position = offset;

            LocatableSerializer ls = new LocatableSerializer();

            Locatable locatable = ls.deserialize(buffer, position);

            return RMObjectFactory.newMessageContent(locatable);
        }
    }

    public static class MessageSerializer {
        protected int serialize(Buffer buffer, int offset,
                                DvDateTime timeSent, PartyRef sender,
                                PartyRef receiver,PartyRef senderNode,
                                PartyRef receiverNode,String sendersReference,
                                boolean initiator, DvOrdinal urgency,
                                Attestation signature,Set<Party> parties,
                                MessageContent content){
            int header = offset;
            int position = offset + 11 * PrimitiveTypeSize.INT.getSize();

            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            PartyRefSerializer prs = new PartyRefSerializer();
            DvOrdinalSerializer dos = new DvOrdinalSerializer();
            AttestationSerializer as = new AttestationSerializer();
            PartySerializer ps = new PartySerializer();
            MessageContentSerializer mcs = new MessageContentSerializer();

            header = writeHeader(buffer, header, position);
            position = dts.serialize(buffer, position, timeSent);

            header = writeHeader(buffer, header, position);
            position = prs.serialize(buffer, position, sender);

            header = writeHeader(buffer, header, position);
            position = prs.serialize(buffer, position, receiver);

            header = writeHeader(buffer, header, position);
            position = prs.serialize(buffer, position, senderNode);

            header = writeHeader(buffer, header, position);
            position = prs.serialize(buffer, position, receiverNode);

            header = writeHeader(buffer, header, position);
            position = stringSerialization(buffer, position, sendersReference);

            header = writeHeader(buffer, header, position);
            buffer.writeBoolean(position, initiator);
            position += PrimitiveTypeSize.BOOLEAN.getSize();

            header = writeHeader(buffer, header, position);
            position = dos.serialize(buffer, position, urgency);

            header = writeHeader(buffer, header, position);
            position = as.serialize(buffer, position, signature);

            header = writeHeader(buffer, header, position);
            position = ps.setSerializer(buffer, position, parties);

            writeHeader(buffer, header, position);
            position = mcs.serialize(buffer, position, content);

            return position;
        }

        protected int serialize(Buffer buffer, int offset, Message m){
            int position = offset;

            MessageSerializer ms = new MessageSerializer();

            position = ms.serialize(buffer, position, m.getTimeSent(),
                    m.getSender(), m.getReceiver(), m.getSenderNode(),
                    m.getReceiverNode(), m.getSendersReference(),
                    m.isInitiator(), m.getUrgency(), m.getSignature(),
                    m.getParties(), m.getContent());

            return position;
        }

        protected Message deserialize(Buffer buffer, int offset){
            int position = offset;

            DvDateTimeSerializer dts = new DvDateTimeSerializer();
            PartyRefSerializer prs = new PartyRefSerializer();
            DvOrdinalSerializer dos = new DvOrdinalSerializer();
            AttestationSerializer as = new AttestationSerializer();
            PartySerializer ps = new PartySerializer();
            MessageContentSerializer mcs = new MessageContentSerializer();

            int timeSentPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvDateTime timeSent = dts.deserialize(buffer, timeSentPosition);

            int senderPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            PartyRef sender = prs.deserialize(buffer, senderPosition);

            int receiverPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            PartyRef receiver = prs.deserialize(buffer, receiverPosition);

            int senderNodePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            PartyRef senderNode = prs.deserialize(buffer, senderNodePosition);

            int receiverNodePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            PartyRef receiverNode = prs.deserialize(buffer, receiverNodePosition);

            int sendersReferencePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            String sendersReference = stringDeserialization(buffer,
                    sendersReferencePosition);

            int initiatorPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            boolean initiator = buffer.readBoolean(initiatorPosition);

            int urgencyPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            DvOrdinal urgency = dos.deserialize(buffer, urgencyPosition);

            int signaturePosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Attestation signature = as.deserialize(buffer, signaturePosition);

            int partiesPosition = buffer.readInteger(position);
            position += PrimitiveTypeSize.INT.getSize();
            Set<Party> parties = ps.setDeserializer(buffer, partiesPosition);

            int contentPosition = buffer.readInteger(position);
            MessageContent content = mcs.deserialize(buffer, contentPosition);

            return RMObjectFactory.newMessage(timeSent, sender, receiver,
                    senderNode, receiverNode, sendersReference, initiator,
                    urgency, signature, parties, content);
        }
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
    private static int writeHeader(Buffer buffer, int offset, int value){
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
    private static int writeHeader(Buffer buffer, int offset, boolean exists){
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
            boolean exists, int value){
        int position = offset;
        if (exists){
            buffer.writeBoolean(position, exists);
            position += BOOLEAN.getSize();
            buffer.writeInteger(position, value);

            position += PrimitiveTypeSize.INT.getSize();
        } else {
            buffer.writeBoolean(position, exists);
            position += BOOLEAN.getSize();
        }
        return position;
    }
}
