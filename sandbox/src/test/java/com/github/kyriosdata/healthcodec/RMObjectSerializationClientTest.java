/**
 * Copyright 2019 Instituto de Informática - UFG
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kyriosdata.healthcodec;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

/**
 *
 * @author Gabriel
 */
public class RMObjectSerializationClientTest {

    /**
     *
     */
    private RMObjectSerializationClient s;

    @Before
    public void setUp() {
        this.s = RMObjectSerializationClient.create();
    }

    @Test
    public void DvBooleanTestTrue() {
        s.serializeDvBoolean(true);
        RMObject.DvBoolean db = s.deserializeDvBoolean();
        assertEquals(true, db.getValue());
    }

    @Test
    public void DvBooleanTestFalse() {
        s.serializeDvBoolean(false);
        RMObject.DvBoolean db = s.deserializeDvBoolean();
        assertEquals(false, db.getValue());
    }

    @Test
    public void DvIdentifierTest()
            throws UnsupportedEncodingException {
        String issuer = "issuer";
        String assigner = "assigner";
        String id = "id";
        String type = "type";

        s.serializeDvIdentifier(issuer, assigner, id, type);
        RMObject.DvIdentifier di = s.deserializeDvIdentifier();

        assertEquals(issuer, di.getIssuer());
        assertEquals(assigner, di.getAssigner());
        assertEquals(id, di.getId());
        assertEquals(type, di.getType());
    }

    @Test
    public void InternetIDTest()
            throws UnsupportedEncodingException {
        String value = "_INTERNETID_";
        s.serializeInternetID(value);
        RMObject.InternetID ii = s.deserializeInternetID();

        assertEquals(value, ii.getValue());
    }

    @Test
    public void ISOOIDTest()
            throws UnsupportedEncodingException {
        String value = "_ISOOID_";
        s.serializeISOOID(value);
        RMObject.ISO_OID io = s.deserializeISOOID();

        assertEquals(value, io.getValue());
    }

    @Test
    public void UUIDTest()
            throws UnsupportedEncodingException {
        String value = "_UUID_";
        s.serializeUUID(value);
        RMObject.UUID u = s.deserializeUUID();

        assertEquals(value, u.getValue());
    }

    @Test
    public void TerminologyIDTest()
            throws UnsupportedEncodingException {
        String value = "_TERMINOLOGYID_";
        s.serializeTerminologyID(value);
        RMObject.TerminologyID t = s.deserializeTerminologyID();

        assertEquals(value, t.getValue());
    }

    @Test
    public void GenericIDTest()
            throws UnsupportedEncodingException {
        String value = "_GENERICID_";
        String scheme = "_GENERICID_SCHEME_";
        s.serializeGenericID(value, scheme);
        RMObject.GenericID g = s.deserializeGenericID();

        assertEquals(value, g.getValue());
        assertEquals(scheme, g.getScheme());
    }

    @Test
    public void TemplateIDTest()
            throws UnsupportedEncodingException {
        String value = "_TEMPLATEID_";
        s.serializeTemplateID(value);
        RMObject.TemplateID t = s.deserializeTemplateID();

        assertEquals(value, t.getValue());
    }

    @Test
    public void CodePhrase()
            throws UnsupportedEncodingException {
        String terminololyIdValue = "_TEMPLATEIDVALUE_";
        String value = "_CODEPRHASE_";
        RMObject.TerminologyID terminologyId =
                RMObjectFactory.newTerminologyID(terminololyIdValue);
        s.serializeCodePhrase(terminologyId, value);
        RMObject.CodePhrase cp = s.deserializeCodePhrase();

        assertEquals(terminololyIdValue, cp.getTerminologyID().getValue());
        assertEquals(value, cp.getValue());
    }

    @Test
    public void DVURITest()
            throws UnsupportedEncodingException {
        String value = "_DVURI_";
        s.serializeDVURI(value);
        RMObject.DVURI d = s.deserializeDVURI();

        assertEquals(value, d.getValue());
    }

    @Test
    public void DVEHRURITest()
            throws UnsupportedEncodingException {
        String value = "_DVEHRURI_";
        s.serializeDVEHRURI(value);
        RMObject.DVEHRURI d = s.deserializeDVEHRURI();

        assertEquals(value, d.getValue());
    }

    @Test
    public void VersionTreeID()
            throws UnsupportedEncodingException {
        String value = "_VERSIONTREEID_";
        s.serializeVersionTreeID(value);
        RMObject.VersionTreeID v = s.deserializeVersionTreeID();

        assertEquals(value, v.getValue());
    }

    @Test
    public void ArchetypeID()
            throws UnsupportedEncodingException {
        String value = "_ARCHETYPEID_";
        s.serializeArchetypeID(value);
        RMObject.ArchetypeID a = s.deserializeArchetypeID();

        assertEquals(value, a.getValue());
    }

    @Test
    public void ObjectVersionID()
            throws UnsupportedEncodingException {
        String value = "_OBJECTVERSIONID_";
        s.serializeObjectVersionID(value);
        RMObject.ObjectVersionID ovi = s.deserializeObjectVersionID();

        assertEquals(value, ovi.getValue());
    }

    @Test
    public void HierObjectID()
            throws UnsupportedEncodingException {
        String value = "_HIEROBJECTID_";
        s.serializeHierObjectID(value);
        RMObject.HierObjectID hoi = s.deserializeHierObjectID();

        assertEquals(value, hoi.getValue());
    }

    @Test
    public void ObjectID()
            throws UnsupportedEncodingException {
        String value = "_OBJECTID_";
        s.serializeObjectID(value);
        RMObject.ObjectID oid = s.deserializeObjectID();

        assertEquals(value, oid.getValue());
    }

    @Test
    public void PartyRef()
            throws UnsupportedEncodingException {
        String oidValue = "_OBJECTID_";
        RMObject.ObjectID id = RMObjectFactory.newObjectID(oidValue);
        String value = "_PARTYREF_";
        s.serializePartyRef(id, value);
        RMObject.PartyRef pr = s.deserializePartyRef();

        assertEquals(oidValue, pr.getId().getValue());
        assertEquals(value, pr.getValue());
    }

    @Test
    public void ObjectRef()
            throws UnsupportedEncodingException {
        String oidValue = "_OBJECTID_";
        String namespace = "NAMESPACE";
        String type = "TYPE";

        RMObject.ObjectID id = RMObjectFactory.newObjectID(oidValue);

        s.serializeObjectRef(id, namespace, type);
        RMObject.ObjectRef or = s.deserializeObjectRef();

        assertEquals(oidValue, or.getId().getValue());
        assertEquals(namespace, or.getNamespace());
        assertEquals(type, or.getType());
    }

    @Test
    public void LocatableRefWithPath()
            throws UnsupportedEncodingException {
        String ovidValue = "_OBJECTVERSIONID_";
        String namespace = "NAMESPACE";
        String type = "TYPE";
        String path = "PATH";

        RMObject.ObjectVersionID id =
                RMObjectFactory.newObjectVersionID(ovidValue);

        s.serializeLocatableRef(id, namespace, type, path);
        RMObject.LocatableRef lr = s.deserializeLocatableRef();

        assertEquals(ovidValue, lr.getId().getValue());
        assertEquals(namespace, lr.getNamespace());
        assertEquals(type, lr.getType());
        assertEquals(path, lr.getPath());
    }

    @Test
    public void LocatableRefWithoutPath()
            throws UnsupportedEncodingException {
        String ovidValue = "_OBJECTVERSIONID_";
        String namespace = "NAMESPACE";
        String type = "TYPE";

        RMObject.ObjectVersionID id =
                RMObjectFactory.newObjectVersionID(ovidValue);

        s.serializeLocatableRef(id, namespace, type, null);
        RMObject.LocatableRef lr = s.deserializeLocatableRef();

        assertEquals(ovidValue, lr.getId().getValue());
        assertEquals(namespace, lr.getNamespace());
        assertEquals(type, lr.getType());
        assertEquals(null, lr.getPath());
    }

    @Test
    public void ProportionKind() {
        int value = 10;
        s.serializeProportionKind(value);
        RMObject.ProportionKind p = s.deserializeProportionKind();
        assertEquals(value, p.getValue());
    }

    @Test
    public void AccessGroupRef() {
        String oidValue = "_OBJECTID_";
        RMObject.ObjectID id = RMObjectFactory.newObjectID(oidValue);
        s.serializeAccessGroupRef(id);
        RMObject.AccessGroupRef a = s.deserializeAccessGroupRef();
        assertEquals(oidValue, a.getId().getValue());
    }

    @Test
    public void PartyIdentified() throws UnsupportedEncodingException {
        String oidValue = "OBJECTID";
        String value = "VALUE";
        String name = "NAME";

        RMObject.ObjectID oid = RMObjectFactory.newObjectID(oidValue);
        RMObject.PartyRef externalRef = RMObjectFactory.newPartyRef(oid, value);

        String issuer = "ISSUER";
        String assigner = "ASSIGNER";
        String id = "ID";
        String type = "TYPE";

        List<RMObject.DvIdentifier> identifiers = new ArrayList<>();
        identifiers.add(
                RMObjectFactory.newDvIdentifier(issuer, assigner, id, type));

        s.serializePartyIdentified(externalRef, name, identifiers);
        RMObject.PartyIdentified p = s.deserializePartyIdentified();

        assertEquals(oidValue, p.getExternalRef().getId().getValue());
        assertEquals(value, p.getExternalRef().getValue());
        assertEquals(issuer, p.getIdentifiers().get(0).getIssuer());
        assertEquals(assigner, p.getIdentifiers().get(0).getAssigner());
        assertEquals(id, p.getIdentifiers().get(0).getId());
        assertEquals(type, p.getIdentifiers().get(0).getType());
    }

    @Test
    public void Archetyped() throws UnsupportedEncodingException {
        String archetypeIDValue = "_ARCHETYPEID_";
        String templateIDValue = "_TEMPLATEID_";
        String rmVersion = "_RMVERSION_";

        RMObject.ArchetypeID archetypeId =
                RMObjectFactory.newArchetypeID(archetypeIDValue);
        RMObject.TemplateID templateId =
                RMObjectFactory.newTemplateID(templateIDValue);

        s.serializeArchetyped(archetypeId, templateId, rmVersion);
        RMObject.Archetyped a = s.deserializeArchetyped();

        assertEquals(archetypeIDValue, a.getArchetypeId().getValue());
        assertEquals(templateIDValue, a.getTemplateId().getValue());
        assertEquals(rmVersion, a.getRmVersion());
    }

    @Test
    public void DvEncapsulated() throws UnsupportedEncodingException {
        String codePhraseCharsetTerminologyIDValue = "_TERMINOLOGYCHARSET_";
        String charsetCodeString = "UTF-8";
        String codePhraseLanguageTerminologyIDValue = "_LANGUAGETERMINOLOGY_";
        String languageCodeString = "UTF-8";

        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(
                        codePhraseCharsetTerminologyIDValue);
        RMObject.CodePhrase charset
                = RMObjectFactory.newCodePhrase(
                charsetTID,
                charsetCodeString);

        RMObject.TerminologyID languageID =
                RMObjectFactory.newTerminologyID(
                        codePhraseLanguageTerminologyIDValue);

        RMObject.CodePhrase language
                = RMObjectFactory.newCodePhrase(
                languageID,
                languageCodeString);

        s.serializeDvEncapsulated(charset, language);

        RMObject.DvEncapsulated d = s.deserializeDvEncapsulated();

        assertEquals(
                codePhraseCharsetTerminologyIDValue,
                d.getCharset().getTerminologyID().getValue());
        assertEquals(charsetCodeString, d.getCharset().getValue());
        assertEquals(
                codePhraseLanguageTerminologyIDValue,
                d.getLanguage().getTerminologyID().getValue());
        assertEquals(
                languageCodeString, d.getLanguage().getValue());
    }

    @Test
    public void UIDBasedID() throws UnsupportedEncodingException {
        String value = "_UIDBASEDID_";
        s.serializeUIDBasedID(value);

        RMObject.UIDBasedID uid = s.deserializeUIDBasedID();
        assertEquals(value, uid.getValue());
    }

    @Test
    public void DvParsable() throws UnsupportedEncodingException {
        String codePhraseCharsetTerminologyIDValue = "_TERMINOLOGYCHARSET_";
        String charsetCodeString = "UTF-8";
        String codePhraseLanguageTerminologyIDValue = "_LANGUAGETERMINOLOGY_";
        String languageCodeString = "UTF-8";
        String value = "_DVPARSABLE_";
        String formalism = "FORMALISM";

        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(
                        codePhraseCharsetTerminologyIDValue);
        RMObject.CodePhrase charset
                = RMObjectFactory.newCodePhrase(
                charsetTID,
                charsetCodeString);

        RMObject.TerminologyID languageID =
                RMObjectFactory.newTerminologyID(
                        codePhraseLanguageTerminologyIDValue);

        RMObject.CodePhrase language
                = RMObjectFactory.newCodePhrase(
                languageID,
                languageCodeString);


        s.serializeDvParsable(charset, language, value, formalism);

        RMObject.DvParsable d = s.deserializeDvParsable();

        assertEquals(
                codePhraseCharsetTerminologyIDValue,
                d.getCharset().getTerminologyID().getValue());
        assertEquals(charsetCodeString, d.getCharset().getValue());
        assertEquals(
                codePhraseLanguageTerminologyIDValue,
                d.getLanguage().getTerminologyID().getValue());
        assertEquals(
                languageCodeString, d.getLanguage().getValue());
        assertEquals(value, d.getValue());
        assertEquals(formalism, d.getFormalism());
    }

    @Test
    public void DvTimeSpecification() throws UnsupportedEncodingException {
        String terminologyIDValue = "_TERMINOLOGYID_";
        String codePhraseValue = "_CODEPHRASE_";
        String dvParsablevalue = "_DVVPARSABLEVALUE_";
        String formalism = "_DVPARSABLEFORMALISM_";
        RMObject.TerminologyID terminologyID =
                RMObjectFactory.newTerminologyID(terminologyIDValue);
        RMObject.CodePhrase charset = RMObjectFactory.newCodePhrase(
                terminologyID, codePhraseValue);
        RMObject.CodePhrase language = RMObjectFactory.newCodePhrase(
                terminologyID, codePhraseValue);
        RMObject.DvParsable value = RMObjectFactory.newDvParsable(
                charset, language, dvParsablevalue, formalism);

        s.serializeDvTimeSpecification(value);
        RMObject.DvTimeSpecification d = s.deserializeDvTimeSpecification();

        assertEquals(terminologyIDValue,
                d.getValue().getCharset().getTerminologyID().getValue());
        assertEquals(codePhraseValue, d.getValue().getCharset().getValue());
        assertEquals(terminologyIDValue,
                d.getValue().getLanguage().getTerminologyID().getValue());
        assertEquals(codePhraseValue, d.getValue().getLanguage().getValue());
        assertEquals(dvParsablevalue, d.getValue().getValue());
        assertEquals(formalism, d.getValue().getFormalism());
    }

    @Test
    public void DvMultimedia() throws UnsupportedEncodingException {
        RMObject.TerminologyID charsetTID = RMObjectFactory.newTerminologyID(
                "charset");
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID("language");
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, "charset");
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID, "language");
        RMObject.DvEncapsulated dvMultimediaDvEncapsulated =
                RMObjectFactory.newDvEncapsulated(charset, language);
        String alternateText = "alternateText";
        RMObject.TerminologyID mediaTypeTID =
                RMObjectFactory.newTerminologyID("media");
        RMObject.CodePhrase mediaType =
                RMObjectFactory.newCodePhrase(mediaTypeTID, "mediaType");
        RMObject.TerminologyID compressionAlgorithmTID =
                RMObjectFactory.newTerminologyID("compressionAlgorithm");
        RMObject.CodePhrase compressionAlgorithm =
                RMObjectFactory.newCodePhrase(
                        compressionAlgorithmTID, "compressionAlgorithm");
        byte[] integrityCheck = {0, 1, 0, 1, 0, 1};
        RMObject.TerminologyID integrityCheckTID =
                RMObjectFactory.newTerminologyID("integrityCheck");
        RMObject.CodePhrase integrityCheckAlgorithm =
                RMObjectFactory.newCodePhrase(integrityCheckTID, "integrity");
        RMObject.DVURI uri = RMObjectFactory.newDVURI("DVURI");
        byte[] data = {1, 0, 1, 1, 0};
        RMObject.DvMultimedia thumbnail = RMObjectFactory.newDvMultimedia(
                dvMultimediaDvEncapsulated,
                alternateText,
                mediaType,
                compressionAlgorithm,
                integrityCheck,
                integrityCheckAlgorithm,
                null,
                uri,
                data);

        s.serializeDvMultimedia(
                dvMultimediaDvEncapsulated,
                alternateText,
                mediaType,
                compressionAlgorithm, integrityCheck, integrityCheckAlgorithm,
                thumbnail, uri, data);

        RMObject.DvMultimedia dvMultimedia = s.deserializeDvMultimedia();

        assertEquals(
                dvMultimediaDvEncapsulated.
                        getCharset().getTerminologyID().getValue(),
                dvMultimedia.getDvMultimediaDvEncapsulated().
                        getCharset().getTerminologyID().getValue());

        assertEquals(alternateText, dvMultimedia.getAlternateText());

        assertEquals(mediaType.getTerminologyID().getValue(),
                dvMultimedia.getMediaType().getTerminologyID().getValue());

        assertEquals(mediaType.getValue(),
                dvMultimedia.getMediaType().getValue());

        assertEquals(compressionAlgorithm.getTerminologyID().getValue(),
                dvMultimedia.
                        getCompressionAlgorithm().
                        getTerminologyID().getValue());

        assertArrayEquals(integrityCheck, dvMultimedia.getIntegrityCheck());

        assertEquals(integrityCheckAlgorithm.getTerminologyID().getValue(),
                dvMultimedia.
                        getIntegrityCheckAlgorithm().
                        getTerminologyID().getValue());

        assertEquals(dvMultimediaDvEncapsulated.
                        getCharset().getTerminologyID().getValue(),
                dvMultimedia.
                        getThumbnail().
                        getDvMultimediaDvEncapsulated().
                        getCharset().getTerminologyID().getValue());

        assertEquals(dvMultimediaDvEncapsulated.
                        getLanguage().getTerminologyID().getValue(),
                dvMultimedia.
                        getThumbnail().
                        getDvMultimediaDvEncapsulated().
                        getLanguage().getTerminologyID().getValue());

        assertEquals(dvMultimediaDvEncapsulated.getCharset().getValue(),
                dvMultimedia.
                        getThumbnail().
                        getDvMultimediaDvEncapsulated().
                        getCharset().getValue());

        assertEquals(dvMultimediaDvEncapsulated.getLanguage().getValue(),
                dvMultimedia.
                        getDvMultimediaDvEncapsulated().
                        getLanguage().getValue());

        assertEquals(alternateText,
                dvMultimedia.getThumbnail().getAlternateText());

        assertEquals(mediaType.getTerminologyID().getValue(),
                dvMultimedia.getMediaType().getTerminologyID().getValue());

        assertEquals(mediaType.getValue(),
                dvMultimedia.getThumbnail().getMediaType().getValue());

        assertEquals(mediaType.getTerminologyID().getValue(),
                dvMultimedia.
                        getThumbnail().
                        getMediaType().getTerminologyID().getValue());

        assertEquals(compressionAlgorithm.getTerminologyID().getValue(),
                dvMultimedia.
                        getThumbnail().
                        getCompressionAlgorithm().
                        getTerminologyID().getValue());

        assertEquals(compressionAlgorithm.getValue(),
                dvMultimedia.
                        getThumbnail().getCompressionAlgorithm().getValue());

        assertArrayEquals(integrityCheck,
                dvMultimedia.getThumbnail().getIntegrityCheck());

        assertEquals(integrityCheckAlgorithm.getTerminologyID().getValue(),
                dvMultimedia.
                        getThumbnail().
                        getIntegrityCheckAlgorithm().
                        getTerminologyID().getValue());

        assertEquals(integrityCheckAlgorithm.getValue(),
                dvMultimedia.
                        getThumbnail().getIntegrityCheckAlgorithm().getValue());

        assertEquals(null, dvMultimedia.getThumbnail().getThumbnail());

        assertEquals(uri.getValue(),
                dvMultimedia.getThumbnail().getUri().getValue());

        assertArrayEquals(data, dvMultimedia.getThumbnail().getData());
    }

    @Test
    public void DvText() throws UnsupportedEncodingException {
        String value = "DvTextValue";
        List<RMObject.TermMapping> mappings = null;
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink = RMObjectFactory.newDVURI(hyperlinkValue);

        String languageTIDValue = "Language Terminology ID";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID,
                languageValue);

        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);

        s.serializeDvText(
                value, mappings, formatting, hyperlink, language, charset);
        RMObject.DvText dvText = s.deserializeDvText();

        assertEquals(value, dvText.getValue());

        assertEquals(null, dvText.getMappings());

        assertEquals(formatting, dvText.getFormatting());

        assertEquals(hyperlink.getValue(), dvText.getHyperlink().getValue());

        assertEquals(language.getTerminologyID().getValue(),
                dvText.getLanguage().getTerminologyID().getValue());

        assertEquals(language.getValue(), dvText.getLanguage().getValue());

        assertEquals(charset.getTerminologyID().getValue(),
                dvText.getCharset().getTerminologyID().getValue());

        assertEquals(charset.getValue(), dvText.getCharset().getValue());
    }

    @Test
    public void DvCodedText() throws UnsupportedEncodingException {
        String value = "DvTextValue";
        List<RMObject.TermMapping> mappings = null;
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink = RMObjectFactory.newDVURI(hyperlinkValue);

        String languageTIDValue = "Language Terminology ID";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID,
                languageValue);

        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);

        RMObject.DvText dvText = RMObjectFactory.newDvText(
                value, mappings, formatting, hyperlink, language, charset);

        String definingCodeTIDValue = "Defining Code Terminology ID Value";
        RMObject.TerminologyID definingCodeTID =
                RMObjectFactory.newTerminologyID(definingCodeTIDValue);
        String definingCodeValue = "Defining Code Value";
        RMObject.CodePhrase definingCode =
                RMObjectFactory.newCodePhrase(
                        definingCodeTID, definingCodeTIDValue);

        s.serializeDvCodedText(dvText, definingCode);
        RMObject.DvCodedText dvCodedText = s.deserializeDvCodedText();

        assertEquals(value, dvCodedText.getDvText().getValue());

        assertEquals(null, dvCodedText.getDvText().getMappings());

        assertEquals(formatting, dvCodedText.getDvText().getFormatting());

        assertEquals(hyperlinkValue,
                dvCodedText.getDvText().getHyperlink().getValue());

        assertEquals(language.getTerminologyID().getValue(),
                dvCodedText.getDvText().
                        getLanguage().getTerminologyID().getValue());

        assertEquals(language.getValue(), dvCodedText.getDvText().
                getLanguage().getValue());

        assertEquals(charset.getTerminologyID().getValue(),
                dvCodedText.getDvText().
                        getCharset().getTerminologyID().getValue());

        assertEquals(charset.getValue(), dvCodedText.getDvText().
                getCharset().getValue());

        assertEquals(definingCode.getTerminologyID().getValue(),
                dvCodedText.getDefiningCode().getTerminologyID().getValue());

        assertEquals(definingCode.getValue(),
                dvCodedText.getDefiningCode().getValue());
    }

    @Test
    public void TermMapping() throws UnsupportedEncodingException {
        RMObject.CodePhrase target = null;
        RMObject.Match match = RMObject.Match.BROADER;
        RMObject.DvCodedText purpose = null;

        String targetTIDValue = "Target TerminologyID";
        String targetValue = "Target Value";
        RMObject.TerminologyID targetTID =
                RMObjectFactory.newTerminologyID(targetTIDValue);
        target = RMObjectFactory.newCodePhrase(targetTID, targetValue);

        String value = "DvTextValue";
        List<RMObject.TermMapping> mappings = null;
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink = RMObjectFactory.newDVURI(hyperlinkValue);

        String languageTIDValue = "Language Terminology ID";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID,
                languageValue);

        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);

        RMObject.DvText dvText = RMObjectFactory.newDvText(
                value, mappings, formatting, hyperlink, language, charset);

        String definingCodeTIDValue = "Defining Code Terminology ID Value";
        RMObject.TerminologyID definingCodeTID =
                RMObjectFactory.newTerminologyID(definingCodeTIDValue);
        String definingCodeValue = "Defining Code Value";
        RMObject.CodePhrase definingCode =
                RMObjectFactory.newCodePhrase(
                        definingCodeTID, definingCodeTIDValue);

        purpose = RMObjectFactory.newDvCodedText(dvText, definingCode);

        s.serializeTermMapping(target, match, purpose);

        RMObject.TermMapping termMapping = s.deserializeTermMapping();

        assertEquals(target.getTerminologyID().getValue(),
                termMapping.getTarget().getTerminologyID().getValue());

        assertEquals(target.getValue(), termMapping.getTarget().getValue());

        assertEquals(match.EQUIVALENT, termMapping.getMatch().EQUIVALENT);

        assertEquals(dvText.getValue(),
                termMapping.getPurpose().getDvText().getValue());

        assertEquals(dvText.getMappings(),
                termMapping.getPurpose().getDvText().getMappings());

        assertEquals(dvText.getFormatting(),
                termMapping.getPurpose().getDvText().getFormatting());

        assertEquals(dvText.getHyperlink().getValue(),
                termMapping.getPurpose().getDvText().getHyperlink().getValue());

        assertEquals(dvText.getLanguage().getTerminologyID().getValue(),
                termMapping.
                        getPurpose().
                        getDvText().
                        getLanguage().getTerminologyID().getValue());

        assertEquals(dvText.getLanguage().getValue(),
                termMapping.getPurpose().getDvText().getLanguage().getValue());

        assertEquals(dvText.getCharset().getTerminologyID().getValue(),
                termMapping.
                        getPurpose().
                        getDvText().
                        getCharset().getTerminologyID().getValue());

        assertEquals(dvText.getCharset().getValue(),
                termMapping.getPurpose().getDvText().getCharset().getValue());

        assertEquals(definingCode.getTerminologyID().getValue(),
                termMapping.
                        getPurpose().
                        getDefiningCode().
                        getTerminologyID().getValue());
        assertEquals(definingCode.getValue(),
                termMapping.getPurpose().getDefiningCode().getValue());
    }

    @Test
    public void TermMappingList() throws UnsupportedEncodingException {

        String value = "DvTextValue";
        List<RMObject.TermMapping> mappings = new ArrayList<>();
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink = RMObjectFactory.newDVURI(hyperlinkValue);

        String languageTIDValue = "Language Terminology ID";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID,
                languageValue);

        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);

        RMObject.DvText dvText = RMObjectFactory.newDvText(
                value, null, formatting, hyperlink, language, charset);

        String definingCodeTIDValue = "Defining Code Terminology ID Value";
        RMObject.TerminologyID definingCodeTID =
                RMObjectFactory.newTerminologyID(definingCodeTIDValue);
        String definingCodeValue = "Defining Code Value";
        RMObject.CodePhrase definingCode =
                RMObjectFactory.newCodePhrase(
                        definingCodeTID, definingCodeTIDValue);

        RMObject.DvCodedText purpose =
                RMObjectFactory.newDvCodedText(dvText, definingCode);

        RMObject.TermMapping t =
                RMObjectFactory.newTermMapping(charset,
                        RMObject.Match.BROADER, purpose);

        mappings.add(t);

        s.serializeDvText(
                value, mappings, formatting, hyperlink, language, charset);
        dvText = s.deserializeDvText();

        assertEquals(value, dvText.getValue());

        assertEquals(mappings.get(0).getTarget().getTerminologyID().getValue(),
                dvText.getMappings().get(0).
                        getTarget().getTerminologyID().getValue());

        assertEquals(mappings.get(0).getTarget().getValue(),
                dvText.getMappings().get(0).getTarget().getValue());

        assertEquals(mappings.get(0).getMatch().BROADER,
                dvText.getMappings().get(0).getMatch().BROADER);

        assertEquals(mappings.get(0).getPurpose().
                        getDefiningCode().getTerminologyID().getValue(),
                dvText.getMappings().
                        get(0).
                        getPurpose().
                        getDefiningCode().getTerminologyID().getValue());

        assertEquals(mappings.get(0).getPurpose().
                        getDefiningCode().getValue(),
                dvText.getMappings().
                        get(0).
                        getPurpose().
                        getDefiningCode().getValue());

        assertEquals(mappings.get(0).getPurpose().
                        getDvText().getValue(),
                dvText.getMappings().
                        get(0).
                        getPurpose().
                        getDvText().getValue());

        assertEquals(mappings.get(0).getPurpose().
                        getDvText().getMappings(),
                dvText.getMappings().
                        get(0).
                        getPurpose().
                        getDvText().getMappings());

        assertEquals(mappings.get(0).getPurpose().
                        getDvText().getFormatting(),
                dvText.getMappings().
                        get(0).
                        getPurpose().
                        getDvText().getFormatting());

        assertEquals(mappings.get(0).getPurpose().
                        getDvText().getHyperlink().getValue(),
                dvText.getMappings().
                        get(0).
                        getPurpose().
                        getDvText().getHyperlink().getValue());

        assertEquals(mappings.get(0).getPurpose().
                        getDvText().getLanguage().getTerminologyID().getValue(),
                dvText.getMappings().
                        get(0).
                        getPurpose().
                        getDvText().getLanguage().
                        getTerminologyID().getValue());

        assertEquals(mappings.get(0).getPurpose().
                        getDvText().getLanguage().getValue(),
                dvText.getMappings().
                        get(0).
                        getPurpose().
                        getDvText().getLanguage().getValue());

        assertEquals(mappings.get(0).getPurpose().
                        getDvText().getCharset().getTerminologyID().getValue(),
                dvText.getMappings().
                        get(0).
                        getPurpose().
                        getDvText().getCharset().
                        getTerminologyID().getValue());

        assertEquals(mappings.get(0).getPurpose().
                        getDvText().getCharset().getValue(),
                dvText.getMappings().
                        get(0).
                        getPurpose().
                        getDvText().getCharset().getValue());

        assertEquals(formatting, dvText.getFormatting());

        assertEquals(hyperlink.getValue(), dvText.getHyperlink().getValue());

        assertEquals(language.getTerminologyID().getValue(),
                dvText.getLanguage().getTerminologyID().getValue());

        assertEquals(language.getValue(), dvText.getLanguage().getValue());

        assertEquals(charset.getTerminologyID().getValue(),
                dvText.getCharset().getTerminologyID().getValue());

        assertEquals(charset.getValue(), dvText.getCharset().getValue());
    }

    @Test
    public void Link() throws UnsupportedEncodingException {
        String value = "DvTextValue";
        List<RMObject.TermMapping> mappings = new ArrayList<>();
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink = RMObjectFactory.newDVURI(hyperlinkValue);

        String languageTIDValue = "Language Terminology ID";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID,
                languageValue);

        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);

        RMObject.DvText dvText = RMObjectFactory.newDvText(
                value, null, formatting, hyperlink, language, charset);

        String dvehruriValue = "DVEHRURI Value";
        RMObject.DVEHRURI dvehruri = RMObjectFactory.newDVEHRURI(dvehruriValue);

        RMObject.Link link = RMObjectFactory.newLink(dvText, dvText, dvehruri);
        RMObjectSerialization.LinkSerializer ls =
                new RMObjectSerialization.LinkSerializer();

        s.serializeLink(link);
        link = s.deserializeLink();

        assertEquals(dvText.getValue(), link.getMeaning().getValue());

        assertEquals(dvText.getMappings(), link.getMeaning().getMappings());

        assertEquals(dvText.getFormatting(), link.getMeaning().getFormatting());

        assertEquals(dvText.getHyperlink().getValue(),
                link.getMeaning().getHyperlink().getValue());

        assertEquals(dvText.getLanguage().getTerminologyID().getValue(),
                link.getMeaning().getLanguage().getTerminologyID().getValue());
        assertEquals(dvText.getLanguage().getValue(),
                link.getMeaning().getLanguage().getValue());
        assertEquals(dvText.getCharset().getTerminologyID().getValue(),
                link.getMeaning().getCharset().getTerminologyID().getValue());
        assertEquals(dvText.getCharset().getValue(),
                link.getMeaning().getCharset().getValue());

        assertEquals(dvText.getValue(), link.getType().getValue());

        assertEquals(dvText.getMappings(), link.getType().getMappings());

        assertEquals(dvText.getFormatting(), link.getType().getFormatting());

        assertEquals(dvText.getHyperlink().getValue(),
                link.getType().getHyperlink().getValue());

        assertEquals(dvText.getLanguage().getTerminologyID().getValue(),
                link.getType().getLanguage().getTerminologyID().getValue());
        assertEquals(dvText.getLanguage().getValue(),
                link.getType().getLanguage().getValue());
        assertEquals(dvText.getCharset().getTerminologyID().getValue(),
                link.getType().getCharset().getTerminologyID().getValue());
        assertEquals(dvText.getCharset().getValue(),
                link.getType().getCharset().getValue());

        assertEquals(dvehruri.getValue(), link.getTarget().getValue());
    }

    @Test
    public void DvStateWithTerminal() throws UnsupportedEncodingException {
        String value = "DvTextValue";
        List<RMObject.TermMapping> mappings = null;
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink = RMObjectFactory.newDVURI(hyperlinkValue);

        String languageTIDValue = "Language Terminology ID";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID,
                languageValue);

        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);

        RMObject.DvText dvText = RMObjectFactory.newDvText(
                value, mappings, formatting, hyperlink, language, charset);

        String definingCodeTIDValue = "Defining Code Terminology ID Value";
        RMObject.TerminologyID definingCodeTID =
                RMObjectFactory.newTerminologyID(definingCodeTIDValue);
        String definingCodeValue = "Defining Code Value";
        RMObject.CodePhrase definingCode =
                RMObjectFactory.newCodePhrase(
                        definingCodeTID, definingCodeTIDValue);

        RMObject.DvCodedText dvStateValue =
                RMObjectFactory.newDvCodedText(dvText, definingCode);
        String terminal = "DvState terminal";

        RMObject.DvState dvState = RMObjectFactory.newDvState(dvStateValue,
                terminal);

        s.serializeDvState(dvState);
        dvState = s.deserializaDvState();

        assertEquals(dvStateValue.getDvText().getValue(),
                dvState.getValue().getDvText().getValue());

        assertEquals(dvStateValue.getDvText().getMappings(),
                dvState.getValue().getDvText().getMappings());

        assertEquals(dvStateValue.getDvText().getFormatting(),
                dvState.getValue().getDvText().getFormatting());

        assertEquals(dvStateValue.getDvText().getHyperlink().getValue(),
                dvState.getValue().getDvText().getHyperlink().getValue());

        assertEquals(dvStateValue.
                        getDvText().getLanguage().getTerminologyID().getValue(),
                dvState.getValue().getDvText().
                        getLanguage().getTerminologyID().getValue());

        assertEquals(dvStateValue.
                        getDvText().getLanguage().getValue(),
                dvState.getValue().getDvText().getLanguage().getValue());

        assertEquals(dvStateValue.
                        getDvText().getCharset().getTerminologyID().getValue(),
                dvState.getValue().getDvText().
                        getCharset().getTerminologyID().getValue());

        assertEquals(dvStateValue.
                        getDvText().getCharset().getValue(),
                dvState.getValue().getDvText().getCharset().getValue());

        assertEquals(terminal, dvState.getTerminal());
    }

    @Test
    public void DvStateWithoutTerminal() throws UnsupportedEncodingException {
        String value = "DvTextValue";
        List<RMObject.TermMapping> mappings = null;
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink = RMObjectFactory.newDVURI(hyperlinkValue);

        String languageTIDValue = "Language Terminology ID";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID,
                languageValue);

        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);

        RMObject.DvText dvText = RMObjectFactory.newDvText(
                value, mappings, formatting, hyperlink, language, charset);

        String definingCodeTIDValue = "Defining Code Terminology ID Value";
        RMObject.TerminologyID definingCodeTID =
                RMObjectFactory.newTerminologyID(definingCodeTIDValue);
        String definingCodeValue = "Defining Code Value";
        RMObject.CodePhrase definingCode =
                RMObjectFactory.newCodePhrase(
                        definingCodeTID, definingCodeTIDValue);

        RMObject.DvCodedText dvStateValue =
                RMObjectFactory.newDvCodedText(dvText, definingCode);
        String terminal = null;

        RMObject.DvState dvState = RMObjectFactory.newDvState(dvStateValue,
                terminal);

        s.serializeDvState(dvState);
        dvState = s.deserializaDvState();

        assertEquals(dvStateValue.getDvText().getValue(),
                dvState.getValue().getDvText().getValue());

        assertEquals(dvStateValue.getDvText().getMappings(),
                dvState.getValue().getDvText().getMappings());

        assertEquals(dvStateValue.getDvText().getFormatting(),
                dvState.getValue().getDvText().getFormatting());

        assertEquals(dvStateValue.getDvText().getHyperlink().getValue(),
                dvState.getValue().getDvText().getHyperlink().getValue());

        assertEquals(dvStateValue.
                        getDvText().getLanguage().getTerminologyID().getValue(),
                dvState.getValue().getDvText().
                        getLanguage().getTerminologyID().getValue());

        assertEquals(dvStateValue.
                        getDvText().getLanguage().getValue(),
                dvState.getValue().getDvText().getLanguage().getValue());

        assertEquals(dvStateValue.
                        getDvText().getCharset().getTerminologyID().getValue(),
                dvState.getValue().getDvText().
                        getCharset().getTerminologyID().getValue());

        assertEquals(dvStateValue.
                        getDvText().getCharset().getValue(),
                dvState.getValue().getDvText().getCharset().getValue());

        assertEquals(terminal, dvState.getTerminal());
    }

    @Test
    public void DvParagraph() throws UnsupportedEncodingException {
        String value = "DvTextValue";
        List<RMObject.TermMapping> mappings = null;
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink = RMObjectFactory.newDVURI(hyperlinkValue);

        String languageTIDValue = "Language Terminology ID";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID,
                languageValue);

        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);

        RMObject.DvText dvText = RMObjectFactory.newDvText(
                value, mappings, formatting, hyperlink, language, charset);

        List<RMObject.DvText> items = new ArrayList<>();
        items.add(dvText);

        s.serializeDvParagraph(items);
        RMObject.DvParagraph dvp = s.deserializeDvParagraph();

        assertEquals(dvText.getValue(), dvp.getItems().get(0).getValue());

        assertEquals(dvText.getMappings(), dvp.getItems().get(0).getMappings());

        assertEquals(dvText.getFormatting(),
                dvp.getItems().get(0).getFormatting());

        assertEquals(dvText.getHyperlink().getValue(),
                dvp.getItems().get(0).getHyperlink().getValue());

        assertEquals(dvText.getLanguage().getTerminologyID().getValue(),
                dvp.getItems().get(0).
                        getLanguage().getTerminologyID().getValue());

        assertEquals(dvText.getLanguage().getValue(),
                dvp.getItems().get(0).getLanguage().getValue());

        assertEquals(dvText.getCharset().getTerminologyID().getValue(),
                dvp.getItems().get(0).
                        getCharset().getTerminologyID().getValue());

        assertEquals(dvText.getCharset().getValue(),
                dvp.getItems().get(0).getCharset().getValue());
    }

    @Test
    public void PartyProxy() throws UnsupportedEncodingException {
        String partyRefOIDValue = "ObjectID value";
        String partyRefValue = "PartyRefValue";

        RMObject.ObjectID oid = RMObjectFactory.newObjectID(partyRefOIDValue);
        RMObject.PartyRef externalValue =
                RMObjectFactory.newPartyRef(oid, partyRefValue);

        RMObject.PartyProxy p = RMObjectFactory.newPartyProxy(externalValue);

        s.serializePartyProxy(p);
        p = s.deserializePartyProxy();

        assertEquals(oid.getValue(), p.getExternalRef().getId().getValue());

        assertEquals(partyRefValue, p.getExternalRef().getValue());
    }

    @Test
    public void FeederAuditDetails() throws UnsupportedEncodingException {
        String systemID = "System ID";

        String oidValue = "_OBJECTID_";
        RMObject.ObjectID id = RMObjectFactory.newObjectID(oidValue);
        String value = "_PARTYREF_";
        RMObject.PartyRef providerRef = RMObjectFactory.newPartyRef(id, value);

        String providerName = "Provider Name";

        String idIssuer = "issuer";
        String idAssigner = "assigner";
        String idId = "id";
        String idType = "type";
        RMObject.DvIdentifier idf =
                RMObjectFactory.newDvIdentifier(
                        idIssuer, idAssigner, idId, idType);
        List<RMObject.DvIdentifier> identifiers = new ArrayList<>();
        identifiers.add(idf);
        identifiers.add(idf);

        RMObject.PartyIdentified provider =
                RMObjectFactory.newPartyIdentified(
                        providerRef, providerName, identifiers);

        RMObject.PartyIdentified location =
                RMObjectFactory.newPartyIdentified(
                        providerRef, providerName, identifiers);

        RMObject.PartyProxy subject =
                RMObjectFactory.newPartyProxy(providerRef);

        String versionID = "Version ID";

        RMObject.FeederAuditDetails f = RMObjectFactory.newFeederAuditDetails(
                systemID, provider, location, subject, versionID);

        s.serializeFeederAuditDetails(f);
        f = s.deserializeFeederAuditDetails();

        assertEquals(systemID, f.getSystemID());

        assertEquals(providerRef.getId().getValue(),
                f.getProvider().getExternalRef().getId().getValue());

        assertEquals(providerRef.getValue(),
                f.getProvider().getExternalRef().getValue());

        assertEquals(providerName, f.getProvider().getName());

        assertEquals(idIssuer,
                f.getProvider().getIdentifiers().get(0).getIssuer());

        assertEquals(idAssigner,
                f.getProvider().getIdentifiers().get(0).getAssigner());

        assertEquals(idId,
                f.getProvider().getIdentifiers().get(0).getId());

        assertEquals(idType,
                f.getProvider().getIdentifiers().get(1).getType());

        assertEquals(idIssuer,
                f.getProvider().getIdentifiers().get(1).getIssuer());

        assertEquals(idAssigner,
                f.getProvider().getIdentifiers().get(1).getAssigner());

        assertEquals(idId,
                f.getProvider().getIdentifiers().get(1).getId());

        assertEquals(idType,
                f.getProvider().getIdentifiers().get(1).getType());

        assertEquals(providerRef.getId().getValue(),
                f.getLocation().getExternalRef().getId().getValue());

        assertEquals(providerRef.getValue(),
                f.getLocation().getExternalRef().getValue());

        assertEquals(providerName, f.getLocation().getName());

        assertEquals(idIssuer,
                f.getLocation().getIdentifiers().get(0).getIssuer());

        assertEquals(idAssigner,
                f.getLocation().getIdentifiers().get(0).getAssigner());

        assertEquals(idId,
                f.getLocation().getIdentifiers().get(0).getId());

        assertEquals(idType,
                f.getLocation().getIdentifiers().get(1).getType());

        assertEquals(idIssuer,
                f.getLocation().getIdentifiers().get(1).getIssuer());

        assertEquals(idAssigner,
                f.getLocation().getIdentifiers().get(1).getAssigner());

        assertEquals(idId,
                f.getLocation().getIdentifiers().get(1).getId());

        assertEquals(idType,
                f.getLocation().getIdentifiers().get(1).getType());


        assertEquals(subject.getExternalRef().getId().getValue(),
                f.getSubject().getExternalRef().getId().getValue());

        assertEquals(subject.getExternalRef().getValue(),
                f.getSubject().getExternalRef().getValue());

        assertEquals(versionID, f.getVersionID());
    }

    @Test
    public void FeederAudit() throws UnsupportedEncodingException {
        String systemID = "System ID";

        String oidValue = "_OBJECTID_";
        RMObject.ObjectID id = RMObjectFactory.newObjectID(oidValue);
        String value = "_PARTYREF_";
        RMObject.PartyRef providerRef = RMObjectFactory.newPartyRef(id, value);

        String providerName = "Provider Name";

        String idIssuer = "issuer";
        String idAssigner = "assigner";
        String idId = "id";
        String idType = "type";
        RMObject.DvIdentifier idf =
                RMObjectFactory.newDvIdentifier(
                        idIssuer, idAssigner, idId, idType);
        List<RMObject.DvIdentifier> identifiers = new ArrayList<>();
        identifiers.add(idf);

        RMObject.PartyIdentified provider =
                RMObjectFactory.newPartyIdentified(
                        providerRef, providerName, identifiers);

        RMObject.PartyIdentified location =
                RMObjectFactory.newPartyIdentified(
                        providerRef, providerName, identifiers);

        RMObject.PartyProxy subject =
                RMObjectFactory.newPartyProxy(providerRef);

        String versionID = "Version ID";

        RMObject.FeederAuditDetails f = RMObjectFactory.newFeederAuditDetails(
                systemID, provider, location, subject, versionID);
        String codePhraseCharsetTerminologyIDValue = "_TERMINOLOGYCHARSET_";
        String charsetCodeString = "UTF-8";
        String codePhraseLanguageTerminologyIDValue = "_LANGUAGETERMINOLOGY_";
        String languageCodeString = "UTF-8";

        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(
                        codePhraseCharsetTerminologyIDValue);
        RMObject.CodePhrase charset
                = RMObjectFactory.newCodePhrase(
                charsetTID,
                charsetCodeString);

        RMObject.TerminologyID languageID =
                RMObjectFactory.newTerminologyID(
                        codePhraseLanguageTerminologyIDValue);

        RMObject.CodePhrase language
                = RMObjectFactory.newCodePhrase(
                languageID,
                languageCodeString);
        RMObject.DvEncapsulated originalContent =
                RMObjectFactory.newDvEncapsulated(charset, language);

        RMObject.FeederAudit fa = RMObjectFactory.newFeederAudit(
                f, identifiers, f, identifiers, originalContent);

        s.serializeFeederAudit(fa);
        fa = s.deserializeFeederAudit();

        //originatingSystemAudit
        assertEquals(systemID, fa.getOriginatingSystemAudit().getSystemID());

        assertEquals(providerRef.getId().getValue(),
                fa.getOriginatingSystemAudit().
                        getProvider().getExternalRef().getId().getValue());

        assertEquals(providerRef.getValue(),
                fa.getOriginatingSystemAudit().
                        getProvider().getExternalRef().getValue());

        assertEquals(providerName, fa.getOriginatingSystemAudit().
                getProvider().getName());

        assertEquals(idIssuer,
                fa.getOriginatingSystemAudit().
                        getProvider().getIdentifiers().get(0).getIssuer());

        assertEquals(idAssigner,
                fa.getOriginatingSystemAudit().
                        getProvider().getIdentifiers().get(0).getAssigner());

        assertEquals(idId,
                fa.getOriginatingSystemAudit().
                        getProvider().getIdentifiers().get(0).getId());

        assertEquals(providerRef.getId().getValue(),
                fa.getOriginatingSystemAudit().
                        getLocation().getExternalRef().getId().getValue());

        assertEquals(providerRef.getValue(),
                fa.getOriginatingSystemAudit().
                        getLocation().getExternalRef().getValue());

        assertEquals(providerName, fa.getOriginatingSystemAudit().
                getLocation().getName());

        assertEquals(idIssuer,
                fa.getOriginatingSystemAudit().
                        getLocation().getIdentifiers().get(0).getIssuer());

        assertEquals(idAssigner,
                fa.getOriginatingSystemAudit().
                        getLocation().getIdentifiers().get(0).getAssigner());

        assertEquals(idId,
                fa.getOriginatingSystemAudit().
                        getLocation().getIdentifiers().get(0).getId());


        assertEquals(subject.getExternalRef().getId().getValue(),
                fa.getOriginatingSystemAudit().
                        getSubject().getExternalRef().getId().getValue());

        assertEquals(subject.getExternalRef().getValue(),
                fa.getOriginatingSystemAudit().
                        getSubject().getExternalRef().getValue());

        assertEquals(versionID, fa.getOriginatingSystemAudit().getVersionID());

        //originatingSystemItemIDs
        assertEquals(idIssuer,
                fa.getOriginatingSystemItemIDs().get(0).getIssuer());

        assertEquals(idAssigner,
                fa.getOriginatingSystemItemIDs().get(0).getAssigner());

        assertEquals(idId,
                fa.getOriginatingSystemItemIDs().get(0).getId());

        assertEquals(idType,
                fa.getOriginatingSystemItemIDs().get(0).getType());

        //feederSystemAudit
        assertEquals(systemID, fa.getFeederSystemAudit().getSystemID());

        assertEquals(providerRef.getId().getValue(),
                fa.getFeederSystemAudit().
                        getProvider().getExternalRef().getId().getValue());

        assertEquals(providerRef.getValue(),
                fa.getFeederSystemAudit().
                        getProvider().getExternalRef().getValue());

        assertEquals(providerName, fa.getFeederSystemAudit().
                getProvider().getName());

        assertEquals(idIssuer,
                fa.getFeederSystemAudit().
                        getProvider().getIdentifiers().get(0).getIssuer());

        assertEquals(idAssigner,
                fa.getFeederSystemAudit().
                        getProvider().getIdentifiers().get(0).getAssigner());

        assertEquals(idId,
                fa.getFeederSystemAudit().
                        getProvider().getIdentifiers().get(0).getId());

        assertEquals(providerRef.getId().getValue(),
                fa.getFeederSystemAudit().
                        getLocation().getExternalRef().getId().getValue());

        assertEquals(providerRef.getValue(),
                fa.getFeederSystemAudit().
                        getLocation().getExternalRef().getValue());

        assertEquals(providerName, fa.getFeederSystemAudit().
                getLocation().getName());

        assertEquals(idIssuer,
                fa.getFeederSystemAudit().
                        getLocation().getIdentifiers().get(0).getIssuer());

        assertEquals(idAssigner,
                fa.getFeederSystemAudit().
                        getLocation().getIdentifiers().get(0).getAssigner());

        assertEquals(idId,
                fa.getFeederSystemAudit().
                        getLocation().getIdentifiers().get(0).getId());


        assertEquals(subject.getExternalRef().getId().getValue(),
                fa.getFeederSystemAudit().
                        getSubject().getExternalRef().getId().getValue());

        assertEquals(subject.getExternalRef().getValue(),
                fa.getFeederSystemAudit().
                        getSubject().getExternalRef().getValue());

        assertEquals(versionID, fa.getFeederSystemAudit().getVersionID());

        //feederSystemItemIDs
        assertEquals(idIssuer,
                fa.getFeederSystemItemIDs().get(0).getIssuer());

        assertEquals(idAssigner,
                fa.getFeederSystemItemIDs().get(0).getAssigner());

        assertEquals(idId,
                fa.getFeederSystemItemIDs().get(0).getId());

        assertEquals(idType,
                fa.getFeederSystemItemIDs().get(0).getType());

        //originalContent
        assertEquals(charset.getTerminologyID().getValue(),
                fa.getOriginalContent().
                        getCharset().getTerminologyID().getValue());

        assertEquals(charset.getValue(), fa.getOriginalContent().
                getCharset().getValue());

        assertEquals(language.getTerminologyID().getValue(),
                fa.getOriginalContent().
                        getLanguage().getTerminologyID().getValue());

        assertEquals(language.getValue(), fa.getOriginalContent().
                getLanguage().getValue());
    }

    @Test
    public void Locatable() {
        String uidValue = "UID Value";

        String value = "DvTextValue";
        List<RMObject.TermMapping> mappings = null;
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink = RMObjectFactory.newDVURI(hyperlinkValue);

        String languageTIDValue = "Language Terminology ID";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID,
                languageValue);

        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);

        String archetypeIDValue = "ArchetypeID Value";
        RMObject.ArchetypeID archetypeId = RMObjectFactory.newArchetypeID(
                archetypeIDValue);
        String templateIDValue = "TemplateID Value";
        RMObject.TemplateID templateId = RMObjectFactory.newTemplateID(value);

        String dvehruriValue = "DVEHRURI Value";
        RMObject.DVEHRURI dvehruri = RMObjectFactory.newDVEHRURI(dvehruriValue);

        String systemID = "System ID";

        String oidValue = "_OBJECTID_";
        RMObject.ObjectID id = RMObjectFactory.newObjectID(oidValue);
        String PrefValue = "_PARTYREF_";
        RMObject.PartyRef providerRef = RMObjectFactory.newPartyRef(id,
                PrefValue);

        String providerName = "Provider Name";

        String idIssuer = "issuer";
        String idAssigner = "assigner";
        String idId = "id";
        String idType = "type";
        RMObject.DvIdentifier idf =
                RMObjectFactory.newDvIdentifier(
                        idIssuer, idAssigner, idId, idType);
        List<RMObject.DvIdentifier> identifiers = new ArrayList<>();
        identifiers.add(idf);

        RMObject.DvEncapsulated originalContent =
                RMObjectFactory.newDvEncapsulated(charset, language);

        RMObject.PartyIdentified provider =
                RMObjectFactory.newPartyIdentified(
                        providerRef, providerName, identifiers);

        RMObject.PartyIdentified location =
                RMObjectFactory.newPartyIdentified(
                        providerRef, providerName, identifiers);

        RMObject.PartyProxy subject =
                RMObjectFactory.newPartyProxy(providerRef);

        String versionID = "Version ID";

        RMObject.FeederAuditDetails f = RMObjectFactory.newFeederAuditDetails(
                systemID, provider, location, subject, versionID);

        //Construindo todos os componentes de Locatable
        RMObject.UIDBasedID uid = RMObjectFactory.newUIDBasedID(uidValue);

        String archetypeNodeId = "Archetyped Node ID";

        RMObject.DvText name = RMObjectFactory.newDvText(
                value, mappings, formatting, hyperlink, language, charset);

        RMObject.Archetyped archetypeDetails = RMObjectFactory.newArchetyped(
                archetypeId, templateId, formatting);

        RMObject.FeederAudit feederAudit = RMObjectFactory.newFeederAudit(
                f, identifiers, f, identifiers, originalContent);

        RMObject.Link link = RMObjectFactory.newLink(name, name, dvehruri);
        Set<RMObject.Link> links = new HashSet<>();
        links.add(link);

        RMObject.Locatable l = RMObjectFactory.newLocatable(
                uid, archetypeNodeId, name, archetypeDetails,
                feederAudit, links);

        //Inicio dos testes

        //UID
        assertEquals(uidValue, l.getUid().getValue());

        //archetypeNodeId
        assertEquals(archetypeNodeId, l.getArchetypeNodeId());

        //name
        assertEquals(value, l.getName().getValue());
        assertEquals(mappings, l.getName().getMappings());
        assertEquals(formatting, l.getName().getFormatting());
        assertEquals(hyperlink.getValue(),
                l.getName().getHyperlink().getValue());
        assertEquals(language.getTerminologyID().getValue(),
                l.getName().getLanguage().getTerminologyID().getValue());
        assertEquals(language.getValue(),
                l.getName().getLanguage().getValue());
        assertEquals(charset.getTerminologyID().getValue(),
                l.getName().getCharset().getTerminologyID().getValue());
        assertEquals(charset.getValue(),
                l.getName().getCharset().getValue());

        //archetypeDetails
        assertEquals(archetypeDetails.getArchetypeId().getValue(),
                l.getArchetypeDetails().getArchetypeId().getValue());
        assertEquals(archetypeDetails.getTemplateId().getValue(),
                l.getArchetypeDetails().getTemplateId().getValue());
        assertEquals(archetypeDetails.getRmVersion(),
                l.getArchetypeDetails().getRmVersion());

        //feederAudit
        assertEquals(feederAudit.getOriginatingSystemAudit().getLocation().getName(),
                l.getFeederAudit().getOriginatingSystemAudit().getLocation().getName());
    }

    @Test
    public void PartyRelated() throws UnsupportedEncodingException {
        String oidValue = "OBJECTID";
        String value = "VALUE";
        String name = "NAME";

        RMObject.ObjectID oid = RMObjectFactory.newObjectID(oidValue);
        RMObject.PartyRef externalRef = RMObjectFactory.newPartyRef(oid, value);

        String issuer = "ISSUER";
        String assigner = "ASSIGNER";
        String id = "ID";
        String type = "TYPE";

        List<RMObject.DvIdentifier> identifiers = new ArrayList<>();
        identifiers.add(
                RMObjectFactory.newDvIdentifier(issuer, assigner, id, type));

        RMObject.PartyIdentified pi = RMObjectFactory.newPartyIdentified(
                externalRef, name, identifiers);

        List<RMObject.TermMapping> mappings = null;
        String formatting = "DvText Formatting";
        String hyperlinkValue = "Hyperlink value";
        RMObject.DVURI hyperlink = RMObjectFactory.newDVURI(hyperlinkValue);

        String languageTIDValue = "Language Terminology ID";
        RMObject.TerminologyID languageTID =
                RMObjectFactory.newTerminologyID(languageTIDValue);
        String languageValue = "Language value";
        RMObject.CodePhrase language =
                RMObjectFactory.newCodePhrase(languageTID,
                languageValue);

        String charsetTIDValue = "Charset Terminology ID";
        String charsetValue = "Charset Value";
        RMObject.TerminologyID charsetTID =
                RMObjectFactory.newTerminologyID(charsetTIDValue);
        RMObject.CodePhrase charset =
                RMObjectFactory.newCodePhrase(charsetTID, charsetValue);

        RMObject.DvText dvText = RMObjectFactory.newDvText(
                value, mappings, formatting, hyperlink, language, charset);

        String definingCodeTIDValue = "Defining Code Terminology ID Value";
        RMObject.TerminologyID definingCodeTID =
                RMObjectFactory.newTerminologyID(definingCodeTIDValue);
        String definingCodeValue = "Defining Code Value";
        RMObject.CodePhrase definingCode =
                RMObjectFactory.newCodePhrase(
                        definingCodeTID, definingCodeTIDValue);

        RMObject.DvCodedText relationship = RMObjectFactory.newDvCodedText(
                dvText, definingCode);

        RMObject.PartyRelated pr = RMObjectFactory.newPartyRelated(pi,
                relationship);

        s.serializePartyRelated(pr);
        //pr = s.deserializePartyRelated();

        //assertEquals(relationship.getDvText().getValue(), pr
        // .getRelationship().getDvText().getValue());

    }
}}}