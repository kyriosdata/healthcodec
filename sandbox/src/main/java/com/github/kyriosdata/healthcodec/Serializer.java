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

/**
 *
 * @author Gabriel
 * Interface com uma coleção de métodos para a serialização das classes do
 * MR de forma que apenas a última referência é armazenada.
 * CUIDADO: Caso seja realizada a serialização de mais de uma instância de
 * um mesmo objeto, a referência do objeto anterior será perdida.
 * IMPORTANTE: Caso seja de interesse do usuário, é interessante acessar
 * {@link RMObjectSerialization} para acessar a coleção de métodos que permitem
 * uma flexibilidade alta quanto a realização de mútliplas serializações de uma
 * mesma classe
 */
public interface Serializer {

    /**
     * para a última instância Serializador de DvBoolean
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient evia{@@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente {
     * @@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining..
     */
    RMObjectSerializationClient serializeDvBoolean(DvBoolean d);

    /**
     * para a última instância Serializador de DvIdentifier
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient evia{@@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente {
     * @@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvIdentifier(DvIdentifier d);

    /**
     * para a última instância Serializador de InternetID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient evia{@@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente {
     * @@link RMObjectSerialization}.
     *
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeInternetID(InternetID i);
    
    /**
     * para a última instância Serializador de UID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient evia{@@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente {
     * @@link RMObjectSerialization}.
     *
     * @param u
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeUID(UID u);
    
    /**
     * para a última instância Serializador de ISO_OID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient evia{@@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente {
     * @@link RMObjectSerialization}.
     *
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeISOOID(ISO_OID i);

    /**
     * para a última instância Serializador de UUID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient evia{@@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente {
     * @@link RMObjectSerialization}.
     *
     * @param u
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeUUID(UUID u);

    /**
     * para a última instância Serializador de TerminologyID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient evia{@@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente {
     * @@link RMObjectSerialization}.
     *
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeTerminologyID(TerminologyID t);

    /**
     * para a última instância Serializador de GenericID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@{@link RMObjectSerializationClient evia{@@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente {
     * @@link RMObjectSerialization}.
     *
     * @param g
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeGenericID(
            GenericID g);

    /**
     * para a última instância Serializador de TemplateID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de { {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeTemplateID(
            TemplateID t);

    /**
     * para a última instância Serializador de CodePhrase
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeCodePhrase(
            CodePhrase c);

    /**
     * para a última instância Serializador de DVURI
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDVURI(
            DVURI d);

    /**
     * para a última instância Serializador de DvEHRURI
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvEHRURI(
            DvEHRURI d);

    /**
     * para a última instância Serializador de VersionTreeID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param v
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeVersionTreeID(
            VersionTreeID v);

    /**
     * para a última instância Serializador de ArchetypeID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeArchetypeID(
            ArchetypeID a);

    /**
     * para a última instância Serializador de ObjectVersionID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObjectVersionID(
            ObjectVersionID o);

    /**
     * para a última instância Serializador de HierObjectID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param h
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeHierObjectID(
            HierObjectID h);

    /**
     * para a última instância Serializador de ObjectID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObjectID(
            ObjectID o);

    /**
     * para a última instância Serializador de PartyRef
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializePartyRef(
            PartyRef p);

    /**
     * para a última instância Serializador de ObjectRef
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObjectRef(
            ObjectRef o);

    /**
     * para a última instância Serializador de LocatableRef
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param l
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeLocatableRef(
            LocatableRef l);

    /**
     * para a última instância Serializador de ProportionKind
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     */
    RMObjectSerializationClient serializeProportionKind(ProportionKind p);

    /**
     * para a última instância Serializador de AccessGroupRef
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     */
    RMObjectSerializationClient serializeAccessGroupRef(
            AccessGroupRef a);

    /**
     * para a última instância Serializador de PartyIdentified
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializePartyIdentified(
            PartyIdentified p);

    /**
     * para a última instância Serializador de Archetyped
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeArchetyped(
            Archetyped a);

    /**
     * para a última instância Serializador de DvEncapsulated
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvEncapsulated(
            DvEncapsulated d);

    /**
     * para a última instância Serializador de UIDBasedID
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param u
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeUIDBasedID(
            UIDBasedID u);

    /**
     * para a última instância Serializador de DvParsable
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvParsable(
            DvParsable d);

    /**
     * para a última instância Serializador de DvTimeSpecification
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvTimeSpecification(
            DvTimeSpecification d);

    /**
     * para a última instância Serializador de DvMultimedia
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvMultimedia(
            DvMultimedia d);

    /**
     * para a última instância Serializador de DvText
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvText(
            DvText d);

    /**
     * para a última instância Serilizador de DvCodedText
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvCodedText(
            DvCodedText d);

    /**
     * para a última instância Serializador de TermMapping
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeTermMapping(
            TermMapping t);

    /**
     * para a última instância Serializador de Link
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param l
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeLink(
            Link l);

    /**
     * para a última instância Serializador de DvState
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvState(
            DvState d);

    /**
     * para a última instância Serializador de DvParagraph
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvParagraph(
            DvParagraph d);

    /**
     * *
     * para a última instância Serializador de PartyProxy
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializePartyProxy(
            PartyProxy p);


    /**
     * para a última instância Serializador de FeederAuditDetails
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param f
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeFeederAuditDetails(
            FeederAuditDetails f);
        
    /**
     * para a última instância Serializador de FeederAudit
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param f
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeFeederAudit(
            FeederAudit f);
    
    /**
     * para a última instância Serializador de Locatable
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param l
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeLocatable(
            Locatable l);
    
    /**
     * para a última instância Serilizador de PartyRelated
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializePartyRelated(
            PartyRelated p);
    
    /**
     * para a última instância Serializador de PartySelf
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializePartySelf(
            PartySelf p);
    
    
    /**
     * para a última instância Serializador de ResourceDescriptionItem
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param r
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeResourceDescriptionItem(
            ResourceDescriptionItem r);
    
    /**
     * para a última instância Serializador de TranslationDetails
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeTranslationDetails(
            TranslationDetails t);
    
    /**
     * para a última instância Serializador de Item
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeItem(
            Item i);
    
    /**
     * para a última instância Serializador de Cluster
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeCluster(
            Cluster c);
    
    /**
     * para a última instância Serializador de Element
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeElement(
            Element e);
    
    /**
     * para a última instância Serializador de DataStructure
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeDataStructure(
            DataStructure d);
    
    /**
     * para a última instância Serializador de ItemList
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeItemList(
            ItemList i);
    
    /**
     * para a última instância Serializador de ItemStructure
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {{@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * @param i
     *@return Instância de RMObjectS.erializationClient para chaining
     *. 
     */
    RMObjectSerializationClient serializeItemStructure(
            ItemStructure i);
    
    /**
     * para a última instância Serializador de ItemSingle
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle d {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeItemSingle(
            ItemSingle i);
    
    /**
     * para a última instância Serializador de ItemTable
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeItemTable(
            ItemTable i); 
    
    /**
     * para a última instância Serializador de ItemTree
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeItemTree(
            ItemTree i); 
    
    /**
     * para a última instância Serializador de PartyIdentity
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializePartyIdentity(PartyIdentity p)
               ; 
    
    /**
     * para a última instância Serializador de PartyRelationship
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializePartyRelationship(
            PartyRelationship p); 
    
    /**
     * para a última instância Serializador de Address
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeAddress(
            Address a); 
    
    /**
     * para a última instância Serializador de Contact
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeContact(
            Contact c); 
    
    /**
     * para a última instância Serializador de Party
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeParty(
            Party p); 
    
    /**
     * para a última instância Serializador de Capability
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeCapability(
            Capability c); 
    
    /**
     * para a última instância Serializador de Role
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param r
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeRole(
            Role r);

    /**
     * para a última instância Serializador de Actor
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeActor(
            Actor a);
    
    /**
     * para a última instância Serializador de Agent
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeAgent(
            Agent a);
    
    /**
     * para a última instância Serializador de Group
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param g
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeGroup(
            Group g);
    
    /**
     * para a última instância Serializador de Organisation
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeOrganisation(
            Organisation o);
    
    /**
     * para a última instância Serializador de Person
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializePerson(Person p)
               ;
    
    /**
     * para a última instância Serializador de InstructionDetails
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param id
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeinstructionDetails(
            InstructionDetails id);
    
    /**
     * para a última instância Serializador de ISMTransition
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param ism
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeISMTransition(
            ISMTransition ism);
    
    /**
     * para a última instância Serializador de Activity
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     * 
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     * 
     */
    RMObjectSerializationClient serializeActivity(
            Activity a);

    /**
     * Serializador para a última instância de DvOrdered.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvOrdered(DvOrdered d);

    /**
     * Serializador para a última instância de DvInterval.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvInterval(DvInterval d);

    /**
     * Serializador para a última instância de DvQuantified.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvQuantified(DvQuantified d);

    /**
     * Serializador para a última instância de DvAmount.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvAmount(DvAmount d);

    /**
     * Serializador para a última instância de DvOrdinal.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvOrdinal(DvOrdinal d);

    /**
     * Serializador para a última instância de DvCount.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvCount(DvCount d);

    /**
     * Serializador para a última instância de DvProportion.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvProportion(DvProportion d);

    /**
     * Serializador para a última instância de DvQuantity.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvQuantity(DvQuantity d);

    /**
     * Serializador para a última instância de DvDuration.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvDuration(DvDuration d);

    /**
     * Serializador para a última instância de DvAbsoluteQuantity.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvCount d);

    /**
     * Serializador para a última instância de DvAbsoluteQuantity.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvDuration d);

    /**
     * Serializador para a última instância de DvAbsoluteQuantity.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvProportion d);

    /**
     * Serializador para a última instância de DvAbsoluteQuantity.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvQuantity d);

    /**
     * Serializador para a última instância de DvDate.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvDate(DvDate d);

    /**
     * Serializador para a última instância de DvTime.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvTime(DvTime d);

    /**
     * Serializador para a última instância de DvDateTime.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvDateTime(DvDateTime d);

    /**
     * Serializador para a última instância de DvTemporal.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param d
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeDvTemporal(DvTemporal d);

    /**
     * Serializador para a última instância de Participation.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeParticipation(Participation p);

    /**
     * Serializador para a última instância de AuditDetails.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeAuditDetails(AuditDetails a);

    /**
     * Serializador para a última instância de Attestation.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeAttestation(Attestation a);

    /**
     * Serializador para a última instância de RevisionHistoryItem.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param r
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeRevisionHistoryItem(
            RevisionHistoryItem r);

    /**
     * Serializador para a última instância de RevisionHistory.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param r
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeRevisionHistory(
            RevisionHistory r);

    /**
     * Serializador para a última instância de Contribution.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeContribution(
            Contribution c);

    /**
     * Serializador para a última instância de Folder.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param f
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeFolder(
            Folder f);

    /**
     * Serializador para a última instância de AuthoredResource.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeAuthoredResource(AuthoredResource a);

    /**
     * Serializador para a última instância de ResourceDescription.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param r
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeResourceDescription(
            ResourceDescription r);

    /**
     * Serializador de Event para a última instância com ItemTree.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeEvent(EventWithItemTree e);

    /**
     * Serializador de Event para a última instância com ItemSingle.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeEvent(EventWithItemSingle e);

    /**
     * Serializador de Event para a última instância com ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeEvent(EventWithItemTable e);

    /**
     * Serializador de IntervalEvent para a última instância com ItemTree.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeIntervalEvent(
            IntervalEventWithItemTree i);

    /**
     * Serializador de IntervalEvent para a última instância com ItemSingle.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeIntervalEvent(
            IntervalEventWithItemSingle i);

    /**
     * Serializador de IntervalEvent para a última instância com ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeIntervalEvent(
            IntervalEventWithItemTable i);

    /**
     * Serializador de History para a última instância com ItemTree.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param h
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeHistory(HistoryWithItemTree h);

    /**
     * Serializador de History para a última instância com ItemSingle.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param h
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeHistory(HistoryWithItemSingle h);

    /**
     * Serializador de History para a última instância com ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param h
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeHistory(HistoryWithItemTable h);

    /**
     * Serializador de PointEvent para a última instância com ItemTree.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializePointEvent(PointEventWithItemTree p);

    /**
     * Serializador de PointEvent para a última instância com ItemSingle.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializePointEvent(PointEventWithItemSingle p);

    /**
     * Serializador de PointEvent para a última instância com ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param p
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializePointEvent(PointEventWithItemTable p);

    /**
     * Serializador para a última instância de ContentItem.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeContentItem(ContentItem c);

    /**
     * Serializador para a última instância de Entry.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeEntry(Entry e);

    /**
     * Serializador para a última instância de CareEntry.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeCareEntry(CareEntry e);

    /**
     * Serializador para a última instância de Action.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeAction(Action a);

    /**
     * Serializador para a última instância de AdminEntry.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeAdminEntry(AdminEntry a);

    /**
     * Serializador para a última instância de Evaluation.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeEvaluation(Evaluation a);

    /**
     * Serializador para a última instância de Instruction.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param i
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeInstruction(Instruction i);

    /**
     * Serializador de Observation com data ItemTree e para a última instância state ItemTree.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObservation(
            ObservationWithItemTreeItemTree o);

    /**
     * Serializador de Observation com data ItemTree e para a última instância state ItemSingle.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObservation(
            ObservationWithItemTreeItemSingle o);

    /**
     * Serializador de Observation com data ItemTree e para a última instância state ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObservation(ObservationWithItemTreeItemTable o);

    /**
     * Serializador de Observation com data ItemTree e para a última instância state ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObservation(ObservationWithItemSingleItemTree o);

    /**
     * Serializador de Observation com data ItemTree e para a última instância state ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObservation(ObservationWithItemSingleItemSingle o);

    /**
     * Serializador de Observation com data ItemTree e para a última instância state ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObservation(ObservationWithItemSingleItemTable o);

    /**
     * Serializador de Observation com data ItemTree e para a última instância state ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObservation(ObservationWithItemTableItemTree o);

    /**
     * Serializador de Observation com data ItemTree e para a última instância state ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeObservation(ObservationWithItemTableItemSingle o);

    /**
     * Serializador de Observation com data ItemTree e para a última instância state ItemTable.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param o
     * @return Instância de para a última instância RMObjectSerializationClient para chaining.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     */
    RMObjectSerializationClient serializeObservation(ObservationWithItemTableItemTable o);

    /**
     * Serializador para a última instância de Section.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param s
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeSection(Section s);

    /**
     * Serializador para a última instância de EventContext.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeEventContext(EventContext e);

    /**
     * Serializador para a última instância de Composition.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeComposition(Composition c);

    /**
     * Serializador para a última instância de EHR.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeEHR(EHR e);

    /**
     * Serializador para a última instância de EHRStatus.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeEHRStatus(EHRStatus e);

    /**
     * Serializador para a última instância de EHRAccess.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeEHRAccess(EHRAccess e);

    /**
     * Serializador para a última instância de XTerminology.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param t
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeXTerminology(XTerminology t);

    /**
     * Serializador para a última instância de XComposition.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeXComposition(XComposition c);

    /**
     * Serializador para a última instância de XDemographics.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param c
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeXDemographics(XDemographics c);

    /**
     * Serializador para a última instância de XFolder.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param f
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeXFolder(XFolder f);

    /**
     * Serializador para a última instância de XAccessControl.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param a
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeXAccessControl(XAccessControl a);

    /**
     * Serializador para a última instância de EHRExtract.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param e
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeEHRExtract(EHRExtract e);

    /**
     * Serializador para a última instância de GenericEntry.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param g
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeGenericEntry(GenericEntry g);

    /**
     * Serializador para a última instância de MessageContent.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param m
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeMessageContent(MessageContent m);

    /**
     * Serializador para a última instância de Message.
     * IMPORTANTE: O controle das posições desse método estão sobre o
     * controle de {@link RMObjectSerializationClient} via {@link Index}.
     * Para um controle maior sobre as posições de início do processo
     * de serialização, considere utilizar diretamente 
     * {@link RMObjectSerialization}.
     *
     * @param m
     * @return Instância de RMObjectSerializationClient para chaining.
     *
     */
    RMObjectSerializationClient serializeMessage(Message m);
}
