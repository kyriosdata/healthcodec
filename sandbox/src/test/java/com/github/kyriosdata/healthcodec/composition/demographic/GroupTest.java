package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

public class GroupTest {

    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidGroup(RMObject.Group g){
        ActorTest.testValidActor(g.getActor());
    }

    @Test
    void GroupValidTest(){
        RMObject.Group a = RMObjectTestHelper.group();
        s.serializeGroup(a);
        a = s.deserializeGroup();

        testValidGroup(a);
    }
}
