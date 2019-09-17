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
import java.nio.ReadOnlyBufferException;
import main.java.com.github.gabrielsxp.healthcodec.RMObject;
import main.java.com.github.gabrielsxp.healthcodec.RMObjectSerializationClient;
import org.junit.Test;
import static org.junit.Assert.*;

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
        assertEquals(false, db.getValue());
    }

    @Test
    public void DvBooleanTestFalse() {
        s.serializeDvBoolean(false);
        RMObject.DvBoolean db = s.deserializeDvBoolean();
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
        RMObject.DvIdentifier di = s.deserializeDvIdentifier();

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
        RMObject.InternetID ii = s.deserializeInternetID();

        assertEquals(value, ii.getValue());
    }

    @Test
    public void ISOOIDTest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_ISOOID_";
        s.serializeISOOID(value);
        RMObject.ISO_OID io = s.deserializeISOOID();

        assertEquals(value, io.getValue());
    }

    @Test
    public void UUIDTest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_UUID_";
        s.serializeUUID(value);
        RMObject.UUID u = s.deserializeUUID();

        assertEquals(value, u.getValue());
    }

    @Test
    public void TerminologyIDTest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_TERMINOLOGYID_";
        s.serializeTerminologyID(value);
        RMObject.TerminologyID t = s.deserializeTerminologyID();

        assertEquals(value, t.getValue());
    }

    @Test
    public void GenericIDTest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_GENERICID_";
        String scheme = "_GENERICID_SCHEME_";
        s.serializeGenericID(value, scheme);
        RMObject.GenericID g = s.deserializeGenericID();

        assertEquals(value, g.getValue());
        assertEquals(scheme, g.getScheme());
    }

    @Test
    public void TemplateIDTest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_TEMPLATEID_";
        s.serializeTemplateID(value);
        RMObject.TemplateID t = s.deserializeTemplateID();

        assertEquals(value, t.getValue());
    }

    @Test
    public void CodePhrase()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String terminololyIdValue = "_TEMPLATEIDVALUE_";
        String value = "_CODEPRHASE_";
        s.serializeCodePhrase(terminololyIdValue, value);
        RMObject.CodePhrase cp = s.deserializeCodePhrase();

        assertEquals(terminololyIdValue, cp.getTerminologyID().getValue());
        assertEquals(value, cp.getValue());
    }

    @Test
    public void DVURITest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_DVURI_";
        s.serializeDVURI(value);
        RMObject.DVURI d = s.deserializeDVURI();
        
        assertEquals(value, d.getValue());
    }
    
    @Test
    public void DVEHRURITest()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_DVEHRURI_";
        s.serializeDVEHRURI(value);
        RMObject.DVEHRURI d = s.deserializeDVEHRURI();
        
        assertEquals(value, d.getValue());
    }
    
    @Test
    public void VersionTreeID()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_VERSIONTREEID_";
        s.serializeVersionTreeID(value);
        RMObject.VersionTreeID v = s.deserializeVersionTreeID();
        
        assertEquals(value, v.getValue());
    }
    
    @Test
    public void ArchetypeID()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_ARCHETYPEID_";
        s.serializeArchetypeID(value);
        RMObject.ArchetypeID a = s.deserializeArchetypeID();
        
        assertEquals(value, a.getValue());
    }
    
    @Test
    public void ObjectVersionID()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_OBJECTVERSIONID_";
        s.serializeObjectVersionID(value);
        RMObject.ObjectVersionID ovi = s.deserializeObjectVersionID();
        
        assertEquals(value, ovi.getValue());
    }
    
    @Test
    public void HierObjectID()
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException, UnsupportedEncodingException {
        String value = "_HIEROBJECTID_";
        s.serializeHierObjectID(value);
        RMObject.HierObjectID hoi = s.deserializeHierObjectID();
        
        assertEquals(value, hoi.getValue());
    }
}