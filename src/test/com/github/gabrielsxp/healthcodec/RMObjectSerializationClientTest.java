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
package test.com.github.gabrielsxp.healthcodec;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import com.github.gabrielsxp.healthcodec.RMObject.*;
import com.github.gabrielsxp.healthcodec.RMObjectFactory;
import com.github.gabrielsxp.healthcodec.RMObjectSerializationClient;
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
        DvBoolean db = s.deserializeDvBoolean();
        assertEquals(true, db.getValue());
    }

    @Test
    public void DvBooleanTestFalse() {
        s.serializeDvBoolean(false);
        DvBoolean db = s.deserializeDvBoolean();
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
        DvIdentifier di = s.deserializeDvIdentifier();

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
        InternetID ii = s.deserializeInternetID();

        assertEquals(value, ii.getValue());
    }

    @Test
    public void ISOOIDTest()
            throws UnsupportedEncodingException {
        String value = "_ISOOID_";
        s.serializeISOOID(value);
        ISO_OID io = s.deserializeISOOID();

        assertEquals(value, io.getValue());
    }

    @Test
    public void UUIDTest()
            throws UnsupportedEncodingException {
        String value = "_UUID_";
        s.serializeUUID(value);
        UUID u = s.deserializeUUID();

        assertEquals(value, u.getValue());
    }

    @Test
    public void TerminologyIDTest()
            throws UnsupportedEncodingException {
        String value = "_TERMINOLOGYID_";
        s.serializeTerminologyID(value);
        TerminologyID t = s.deserializeTerminologyID();

        assertEquals(value, t.getValue());
    }

    @Test
    public void GenericIDTest()
            throws UnsupportedEncodingException {
        String value = "_GENERICID_";
        String scheme = "_GENERICID_SCHEME_";
        s.serializeGenericID(value, scheme);
        GenericID g = s.deserializeGenericID();

        assertEquals(value, g.getValue());
        assertEquals(scheme, g.getScheme());
    }

    @Test
    public void TemplateIDTest()
            throws UnsupportedEncodingException {
        String value = "_TEMPLATEID_";
        s.serializeTemplateID(value);
        TemplateID t = s.deserializeTemplateID();

        assertEquals(value, t.getValue());
    }

    @Test
    public void CodePhrase()
            throws UnsupportedEncodingException {
        String terminololyIdValue = "_TEMPLATEIDVALUE_";
        String value = "_CODEPRHASE_";
        TerminologyID terminologyId = 
                RMObjectFactory.newTerminologyID(terminololyIdValue);
        s.serializeCodePhrase(terminologyId, value);
        CodePhrase cp = s.deserializeCodePhrase();

        assertEquals(terminololyIdValue, cp.getTerminologyID().getValue());
        assertEquals(value, cp.getValue());
    }

    @Test
    public void DVURITest()
            throws UnsupportedEncodingException {
        String value = "_DVURI_";
        s.serializeDVURI(value);
        DVURI d = s.deserializeDVURI();

        assertEquals(value, d.getValue());
    }

    @Test
    public void DVEHRURITest()
            throws UnsupportedEncodingException {
        String value = "_DVEHRURI_";
        s.serializeDVEHRURI(value);
        DVEHRURI d = s.deserializeDVEHRURI();

        assertEquals(value, d.getValue());
    }

    @Test
    public void VersionTreeID()
            throws UnsupportedEncodingException {
        String value = "_VERSIONTREEID_";
        s.serializeVersionTreeID(value);
        VersionTreeID v = s.deserializeVersionTreeID();

        assertEquals(value, v.getValue());
    }

    @Test
    public void ArchetypeID()
            throws UnsupportedEncodingException {
        String value = "_ARCHETYPEID_";
        s.serializeArchetypeID(value);
        ArchetypeID a = s.deserializeArchetypeID();

        assertEquals(value, a.getValue());
    }

    @Test
    public void ObjectVersionID()
            throws UnsupportedEncodingException {
        String value = "_OBJECTVERSIONID_";
        s.serializeObjectVersionID(value);
        ObjectVersionID ovi = s.deserializeObjectVersionID();

        assertEquals(value, ovi.getValue());
    }

    @Test
    public void HierObjectID()
            throws UnsupportedEncodingException {
        String value = "_HIEROBJECTID_";
        s.serializeHierObjectID(value);
        HierObjectID hoi = s.deserializeHierObjectID();

        assertEquals(value, hoi.getValue());
    }

    @Test
    public void ObjectID()
            throws UnsupportedEncodingException {
        String value = "_OBJECTID_";
        s.serializeObjectID(value);
        ObjectID oid = s.deserializeObjectID();

        assertEquals(value, oid.getValue());
    }

    @Test
    public void PartyRef()
            throws UnsupportedEncodingException {
        String oidValue = "_OBJECTID_";
        ObjectID id = RMObjectFactory.newObjectID(oidValue);
        String value = "_PARTYREF_";
        s.serializePartyRef(id, value);
        PartyRef pr = s.deserializePartyRef();

        assertEquals(oidValue, pr.getId().getValue());
        assertEquals(value, pr.getValue());
    }

    @Test
    public void ObjectRef()
            throws UnsupportedEncodingException {
        String oidValue = "_OBJECTID_";
        String namespace = "NAMESPACE";
        String type = "TYPE";
        
        ObjectID id = RMObjectFactory.newObjectID(oidValue);
        
        s.serializeObjectRef(id, namespace, type);
        ObjectRef or = s.deserializeObjectRef();

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
        
        ObjectVersionID id = RMObjectFactory.newObjectVersionID(ovidValue);
        
        s.serializeLocatableRef(id, namespace, type, path);
        LocatableRef lr = s.deserializeLocatableRef();

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
        
        ObjectVersionID id = RMObjectFactory.newObjectVersionID(ovidValue);
        
        s.serializeLocatableRef(id, namespace, type, null);
        LocatableRef lr = s.deserializeLocatableRef();

        assertEquals(ovidValue, lr.getId().getValue());
        assertEquals(namespace, lr.getNamespace());
        assertEquals(type, lr.getType());
        assertEquals(null, lr.getPath());
    }

    @Test
    public void ProportionKind() {
        int value = 10;
        s.serializeProportionKind(value);
        ProportionKind p = s.deserializeProportionKind();
        assertEquals(value, p.getValue());
    }

    @Test
    public void AccessGroupRef() {
        String oidValue = "_OBJECTID_";
        ObjectID id = RMObjectFactory.newObjectID(oidValue);
        s.serializeAccessGroupRef(id);
        AccessGroupRef a = s.deserializeAccessGroupRef();
        assertEquals(oidValue, a.getId().getValue());
    }

    @Test
    public void PartyIdentified() throws UnsupportedEncodingException {
        String oidValue = "OBJECTID";
        String value = "VALUE";
        String name = "NAME";

        String issuer = "ISSUER";
        String assigner = "ASSIGNER";
        String id = "ID";
        String type = "TYPE";
        
        ObjectID oid = RMObjectFactory.newObjectID(oidValue);

        List<DvIdentifier> identifiers = new ArrayList<>();
        identifiers.add(
                RMObjectFactory.newDvIdentifier(issuer, assigner, id, type));

        s.serializePartyIdentified(oid, value, name, identifiers);
        PartyIdentified p = s.deserializePartyIdentified();
        
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
        
        ArchetypeID archetypeId = 
                RMObjectFactory.newArchetypeID(archetypeIDValue);
        TemplateID templateId = 
                RMObjectFactory.newTemplateID(templateIDValue);
        
        s.serializeArchetyped(archetypeId, templateId, rmVersion);
        Archetyped a = s.deserializeArchetyped();
        
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
        
        TerminologyID charsetTID = 
                RMObjectFactory.newTerminologyID(
                        codePhraseCharsetTerminologyIDValue);
        CodePhrase charset 
                = RMObjectFactory.newCodePhrase(
                        charsetTID, 
                        charsetCodeString);
        
        TerminologyID languageID = 
                RMObjectFactory.newTerminologyID(
                        codePhraseLanguageTerminologyIDValue);
        
        CodePhrase language 
                = RMObjectFactory.newCodePhrase(
                        languageID, 
                        languageCodeString);
        
        s.serializeDvEncapsulated(charset, language);
        
        DvEncapsulated d = s.deserializeDvEncapsulated();
        
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
        
        UIDBasedID uid = s.deserializeUIDBasedID();
        assertEquals(value, uid.getValue());
    }
    
    @Test
    public void DvParsable() throws UnsupportedEncodingException  {
        String codePhraseCharsetTerminologyIDValue = "_TERMINOLOGYCHARSET_";
        String charsetCodeString = "UTF-8";
        String codePhraseLanguageTerminologyIDValue = "_LANGUAGETERMINOLOGY_";
        String languageCodeString = "UTF-8";
        String value = "_DVPARSABLE_";
        String formalism = "FORMALISM";
        
        TerminologyID charsetTID = 
                RMObjectFactory.newTerminologyID(
                        codePhraseCharsetTerminologyIDValue);
        CodePhrase charset 
                = RMObjectFactory.newCodePhrase(
                        charsetTID, 
                        charsetCodeString);
        
        TerminologyID languageID = 
                RMObjectFactory.newTerminologyID(
                        codePhraseLanguageTerminologyIDValue);
        
        CodePhrase language 
                = RMObjectFactory.newCodePhrase(
                        languageID, 
                        languageCodeString);
        
        
        s.serializeDvParsable(charset, language,value, formalism);
        
        DvParsable d = s.deserializeDvParsable();
        
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
        TerminologyID terminologyID = 
                RMObjectFactory.newTerminologyID(terminologyIDValue);
        CodePhrase charset = RMObjectFactory.newCodePhrase(
                terminologyID, codePhraseValue);
        CodePhrase language = RMObjectFactory.newCodePhrase(
                terminologyID, codePhraseValue);
        DvParsable value = RMObjectFactory.newDvParsable(
                charset, language, dvParsablevalue, formalism);
        
        s.serializeDvTimeSpecification(value);
        DvTimeSpecification d = s.deserializeDvTimeSpecification();
        
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
        TerminologyID charsetTID = RMObjectFactory.newTerminologyID("charset");
        TerminologyID languageTID = 
                RMObjectFactory.newTerminologyID("language");
        CodePhrase charset = 
                RMObjectFactory.newCodePhrase(charsetTID, "charset");
        CodePhrase language = 
                RMObjectFactory.newCodePhrase(languageTID, "language");
        DvEncapsulated dvMultimediaDvEncapsulated = 
                RMObjectFactory.newDvEncapsulated(charset, language);
        String alternateText = "alternateText";
        TerminologyID mediaTypeTID = RMObjectFactory.newTerminologyID("media");
        CodePhrase mediaType = 
                RMObjectFactory.newCodePhrase(mediaTypeTID, "mediaType");
        TerminologyID compressionAlgorithmTID = 
                RMObjectFactory.newTerminologyID("compressionAlgorithm");
        CodePhrase compressionAlgorithm = 
                RMObjectFactory.newCodePhrase(
                        compressionAlgorithmTID, "compressionAlgorithm");
        byte[] integrityCheck = {0, 1, 0, 1 ,0, 1};
        TerminologyID integrityCheckTID = 
                RMObjectFactory.newTerminologyID("integrityCheck");
        CodePhrase integrityCheckAlgorithm = 
                RMObjectFactory.newCodePhrase(integrityCheckTID, "integrity");
        DVURI uri = RMObjectFactory.newDVURI("DVURI");
        byte[] data = {1,0,1,1,0};
        DvMultimedia thumbnail = RMObjectFactory.newDvMultimedia(
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
        
        DvMultimedia dvMultimedia = s.deserializeDvMultimedia();
        
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
        
        assertEquals(uri.getValue(), dvMultimedia.getThumbnail().getUri().getValue());
        
        assertArrayEquals(data, dvMultimedia.getThumbnail().getData());
    }
}
