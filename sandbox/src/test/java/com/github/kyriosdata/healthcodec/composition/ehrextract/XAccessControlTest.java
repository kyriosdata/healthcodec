package com.github.kyriosdata.healthcodec.composition.ehrextract;

import com.github.kyriosdata.healthcodec.RMObject;
import com.github.kyriosdata.healthcodec.RMObject.XAccessControl;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.composition.demographic.PartyTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectIDTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class XAccessControlTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidXAccessControl(XAccessControl d){
        RMObject.ObjectID objectID = d.getGroups().keySet().iterator().next();
        RMObject.Party party = d.getGroups().values().iterator().next();

        ObjectIDTest.testValidObjectID(objectID);
        PartyTest.testValidParty(party);
        ItemStructureTest.testValidItemStructure(d.getDetails());
    }

    @Test
    void xAccessControlValidTest(){
        XAccessControl a = RMObjectTestHelper.xAccessControl();
        s.serializeXAccessControl(a);
        a = s.deserializeXAccessControl();

        testValidXAccessControl(a);
    }

    @Test
    void xAccessControlEmptyGroupsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newXAccessControl(RMObjectTestHelper.
                            objectIDPartyMap(true),
                    RMObjectTestHelper.itemStructure());
        });
    }

}
