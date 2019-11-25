package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

public class OrganisationTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidOrganisation(RMObject.Organisation o){
        ActorTest.testValidActor(o.getActor());
    }

    @Test
    void OrganisationValidTest(){
        RMObject.Organisation a = RMObjectTestHelper.organisation();
        s.serializeOrganisation(a);
        a = s.deserializeOrganisation();

        testValidOrganisation(a);
    }
}
