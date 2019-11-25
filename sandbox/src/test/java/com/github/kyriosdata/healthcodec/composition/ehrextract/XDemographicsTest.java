package com.github.kyriosdata.healthcodec.composition.ehrextract;

import com.github.kyriosdata.healthcodec.RMObject;
import com.github.kyriosdata.healthcodec.RMObject.XDemographics;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.composition.demographic.PartyTest;
import com.github.kyriosdata.healthcodec.datastructure.itemstructure.ItemStructureTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectIDTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class XDemographicsTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidXDemographics(XDemographics d){
        RMObject.ObjectID objectID = d.getParties().keySet().iterator().next();
        RMObject.Party party = d.getParties().values().iterator().next();

        ObjectIDTest.testValidObjectID(objectID);
        PartyTest.testValidParty(party);
        ItemStructureTest.testValidItemStructure(d.getDetails());
    }

    @Test
    void xDemographicsValidTest(){
        XDemographics d = RMObjectTestHelper.xDemographics();
        s.serializeXDemographics(d);
        d = s.deserializeXDemographics();

        testValidXDemographics(d);
    }

    @Test
    void xDemographicsEmptyPartiesTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newXDemographics(
                    RMObjectTestHelper.objectIDPartyMap(true),
                    RMObjectTestHelper.itemStructure());
        });
    }

}
