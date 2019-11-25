package com.github.kyriosdata.healthcodec.composition.ehr;

import com.github.kyriosdata.healthcodec.RMObject;
import com.github.kyriosdata.healthcodec.RMObject.EHR;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDateTimeTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.HierObjectIDTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectIDTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectRefTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EHRTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidEHR(EHR e){
        HierObjectIDTest.testValidHierObjectID(e.getSystemID());

        HierObjectIDTest.testValidHierObjectID(e.getEhrID());

        DvDateTimeTest.testValidDvDateTime(e.getTimeCreated());

        ObjectIDTest.testValidObjectID(e.getContributions().get(0).getId());
        assertEquals("namespace", e.getContributions().get(0).getNamespace());
        assertEquals("CONTRIBUTION", e.getContributions().get(0).getType());

        ObjectRefTest.testValidObjectRef(e.getEhrStatus());

        ObjectIDTest.testValidObjectID(e.getDirectory().getId());
        assertEquals("namespace", e.getDirectory().getNamespace());
        assertEquals("VERSIONED_FOLDER", e.getDirectory().getType());

        ObjectIDTest.testValidObjectID(e.getCompositions().get(0).getId());
        assertEquals("namespace", e.getCompositions().get(0).getNamespace());
        assertEquals("VERSIONED_COMPOSITION", e.getCompositions().get(0).getType());
    }

    @Test
    void ehrValidTest(){
        EHR e = RMObjectTestHelper.eHR();
        s.serializeEHR(e);
        e = s.deserializeEHR();

        testValidEHR(e);
    }

    @Test
    void ehrNullSystemIDTest(){
        List<RMObject.ObjectRef> contributions = new ArrayList<>();
        RMObject.ObjectRef contribution = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "CONTRIBUTION");
        contributions.add(contribution);

        List<RMObject.ObjectRef> compositions = new ArrayList<>();
        RMObject.ObjectRef composition = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_COMPOSITION");
        compositions.add(composition);

        RMObject.HierObjectID systemID = null;
        RMObject.HierObjectID ehrID = RMObjectTestHelper.hierObjectID();
        RMObject.DvDateTime timeCreated = RMObjectTestHelper.dvDateTime();
        RMObject.ObjectRef ehrStatus = RMObjectTestHelper.objectRef();
        RMObject.ObjectRef directory = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_FOLDER");
        contributions.add(contribution);

        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHR(systemID, ehrID, timeCreated,
                    contributions, ehrStatus, directory, compositions);
        });
    }

    @Test
    void ehrNullEhrIDTest(){
        List<RMObject.ObjectRef> contributions = new ArrayList<>();
        RMObject.ObjectRef contribution = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "CONTRIBUTION");
        contributions.add(contribution);

        List<RMObject.ObjectRef> compositions = new ArrayList<>();
        RMObject.ObjectRef composition = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_COMPOSITION");
        compositions.add(composition);

        RMObject.HierObjectID systemID = RMObjectTestHelper.hierObjectID();
        RMObject.HierObjectID ehrID = null;
        RMObject.DvDateTime timeCreated = RMObjectTestHelper.dvDateTime();
        RMObject.ObjectRef ehrStatus = RMObjectTestHelper.objectRef();
        RMObject.ObjectRef directory = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_FOLDER");
        contributions.add(contribution);

        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHR(systemID, ehrID, timeCreated,
                    contributions, ehrStatus, directory, compositions);
        });
    }

    @Test
    void ehrNullTimeCreatedTest(){
        List<RMObject.ObjectRef> contributions = new ArrayList<>();
        RMObject.ObjectRef contribution = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "CONTRIBUTION");
        contributions.add(contribution);

        List<RMObject.ObjectRef> compositions = new ArrayList<>();
        RMObject.ObjectRef composition = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_COMPOSITION");
        compositions.add(composition);

        RMObject.HierObjectID systemID = RMObjectTestHelper.hierObjectID();
        RMObject.HierObjectID ehrID = RMObjectTestHelper.hierObjectID();
        RMObject.DvDateTime timeCreated = null;
        RMObject.ObjectRef ehrStatus = RMObjectTestHelper.objectRef();
        RMObject.ObjectRef directory = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_FOLDER");
        contributions.add(contribution);

        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHR(systemID, ehrID, timeCreated,
                    contributions, ehrStatus, directory, compositions);
        });
    }

    @Test
    void ehrNullContributionsTest(){
        List<RMObject.ObjectRef> contributions = null;


        List<RMObject.ObjectRef> compositions = new ArrayList<>();
        RMObject.ObjectRef composition = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_COMPOSITION");
        compositions.add(composition);

        RMObject.HierObjectID systemID = RMObjectTestHelper.hierObjectID();
        RMObject.HierObjectID ehrID = RMObjectTestHelper.hierObjectID();
        RMObject.DvDateTime timeCreated = RMObjectTestHelper.dvDateTime();
        RMObject.ObjectRef ehrStatus = RMObjectTestHelper.objectRef();
        RMObject.ObjectRef directory = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_FOLDER");

        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHR(systemID, ehrID, timeCreated,
                    contributions, ehrStatus, directory, compositions);
        });
    }

    @Test
    void ehrInvalidContributionsTypeTest(){
        List<RMObject.ObjectRef> contributions = RMObjectTestHelper.
                objectRefList(false);

        List<RMObject.ObjectRef> compositions = new ArrayList<>();
        RMObject.ObjectRef composition = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_COMPOSITION");
        compositions.add(composition);

        RMObject.HierObjectID systemID = RMObjectTestHelper.hierObjectID();
        RMObject.HierObjectID ehrID = RMObjectTestHelper.hierObjectID();
        RMObject.DvDateTime timeCreated = RMObjectTestHelper.dvDateTime();
        RMObject.ObjectRef ehrStatus = RMObjectTestHelper.objectRef();
        RMObject.ObjectRef directory = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_FOLDER");

        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHR(systemID, ehrID, timeCreated,
                    contributions, ehrStatus, directory, compositions);
        });
    }

    @Test
    void ehrNullCompositionsTest(){
        List<RMObject.ObjectRef> contributions = new ArrayList<>();
        RMObject.ObjectRef contribution = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "CONTRIBUTION");
        contributions.add(contribution);

        List<RMObject.ObjectRef> compositions = null;

        RMObject.HierObjectID systemID = RMObjectTestHelper.hierObjectID();
        RMObject.HierObjectID ehrID = RMObjectTestHelper.hierObjectID();;
        RMObject.DvDateTime timeCreated = RMObjectTestHelper.dvDateTime();
        RMObject.ObjectRef ehrStatus = RMObjectTestHelper.objectRef();
        RMObject.ObjectRef directory = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_FOLDER");
        contributions.add(contribution);

        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHR(systemID, ehrID, timeCreated,
                    contributions, ehrStatus, directory, compositions);
        });
    }

    @Test
    void ehrInvalidCompositionsTypeTest(){
        List<RMObject.ObjectRef> contributions = new ArrayList<>();
        RMObject.ObjectRef contribution = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "CONTRIBUTION");
        contributions.add(contribution);

        List<RMObject.ObjectRef> compositions = RMObjectTestHelper.
                objectRefList(false);

        RMObject.HierObjectID systemID = RMObjectTestHelper.hierObjectID();
        RMObject.HierObjectID ehrID = RMObjectTestHelper.hierObjectID();;
        RMObject.DvDateTime timeCreated = RMObjectTestHelper.dvDateTime();
        RMObject.ObjectRef ehrStatus = RMObjectTestHelper.objectRef();
        RMObject.ObjectRef directory = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_FOLDER");
        contributions.add(contribution);

        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHR(systemID, ehrID, timeCreated,
                    contributions, ehrStatus, directory, compositions);
        });
    }

    @Test
    void ehrInvalidDirectoryTypeTest(){
        List<RMObject.ObjectRef> contributions = new ArrayList<>();
        RMObject.ObjectRef contribution = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "CONTRIBUTION");
        contributions.add(contribution);

        List<RMObject.ObjectRef> compositions = new ArrayList<>();
        RMObject.ObjectRef composition = RMObjectFactory.newObjectRef(
                RMObjectTestHelper.objectID(),
                "namespace", "VERSIONED_COMPOSITION");
        compositions.add(composition);

        RMObject.HierObjectID systemID = RMObjectTestHelper.hierObjectID();
        RMObject.HierObjectID ehrID = RMObjectTestHelper.hierObjectID();
        RMObject.DvDateTime timeCreated = RMObjectTestHelper.dvDateTime();
        RMObject.ObjectRef ehrStatus = RMObjectTestHelper.objectRef();
        RMObject.ObjectRef directory = RMObjectTestHelper.objectRef();

        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEHR(systemID, ehrID, timeCreated,
                    contributions, ehrStatus, directory, compositions);
        });
    }
}
