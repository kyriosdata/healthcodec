package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject.Agent;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

public class AgentTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidAgent(Agent a){
        ActorTest.testValidActor(a.getActor());
    }

    @Test
    void agentValidTest(){
        Agent a = RMObjectTestHelper.agent();
        s.serializeAgent(a);
        a = s.deserializeAgent();

        testValidAgent(a);
    }
}
