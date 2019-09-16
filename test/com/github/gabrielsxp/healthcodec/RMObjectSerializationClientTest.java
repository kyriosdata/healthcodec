/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gabrielsxp.healthcodec;

import com.github.gabrielsxp.healthcodec.RMObjects.*;
import java.io.UnsupportedEncodingException;
import java.nio.ReadOnlyBufferException;
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
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
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
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_INTERNETID_";
        s.serializeInternetID(value);
        InternetID ii = s.deserializeInternetID();

        assertEquals(value, ii.getValue());
    }

    @Test
    public void ISOOIDTest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_ISOOID_";
        s.serializeISOOID(value);
        ISO_OID io = s.deserializeISOOID();

        assertEquals(value, io.getValue());
    }

    @Test
    public void UUIDTest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_UUID_";
        s.serializeUUID(value);
        UUID u = s.deserializeUUID();

        assertEquals(value, u.getValue());
    }

    @Test
    public void TerminologyIDTest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_TERMINOLOGYID_";
        s.serializeTerminologyID(value);
        TerminologyID t = s.deserializeTerminologyID();

        assertEquals(value, t.getValue());
    }

    @Test
    public void GenericIDTest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_GENERICID_";
        String scheme = "_GENERICID_SCHEME_";
        s.serializeGenericID(value, scheme);
        GenericID g = s.deserializeGenericID();

        assertEquals(value, g.getValue());
        assertEquals(scheme, g.getScheme());
    }

    @Test
    public void TemplateIDTest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_TEMPLATEID_";
        s.serializeTemplateID(value);
        TemplateID t = s.deserializeTemplateID();

        assertEquals(value, t.getValue());
    }

    @Test
    public void CodePhrase()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String terminololyIdValue = "_TEMPLATEIDVALUE_";
        String value = "_CODEPRHASE_";
        s.serializeCodePhrase(terminololyIdValue, value);
        CodePhrase cp = s.deserializeCodePhrase();

        assertEquals(terminololyIdValue, cp.getTerminologyID().getValue());
        assertEquals(value, cp.getValue());
    }

    @Test
    public void DVURITest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_DVURI_";
        s.serializeDVURI(value);
        DVURI d = s.deserializeDVURI();
        
        assertEquals(value, d.getValue());
    }
    
    @Test
    public void DVEHRURITest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_DVEHRURI_";
        s.serializeDVEHRURI(value);
        DVEHRURI d = s.deserializeDVEHRURI();
        
        assertEquals(value, d.getValue());
    }
    
    @Test
    public void VersionTreeID()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_VERSIONTREEID_";
        s.serializeVersionTreeID(value);
        VersionTreeID v = s.deserializeVersionTreeID();
        
        assertEquals(value, v.getValue());
    }
    
    @Test
    public void ArchetypeID()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_ARCHETYPEID_";
        s.serializeArchetypeID(value);
        ArchetypeID a = s.deserializeArchetypeID();
        
        assertEquals(value, a.getValue());
    }
    
    @Test
    public void ObjectVersionID()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_OBJECTVERSIONID_";
        s.serializeObjectVersionID(value);
        ObjectVersionID ovi = s.deserializeObjectVersionID();
        
        assertEquals(value, ovi.getValue());
    }
    
    @Test
    public void HierObjectID()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_HIEROBJECTID_";
        s.serializeHierObjectID(value);
        HierObjectID hoi = s.deserializeHierObjectID();
        
        assertEquals(value, hoi.getValue());
    }
}
