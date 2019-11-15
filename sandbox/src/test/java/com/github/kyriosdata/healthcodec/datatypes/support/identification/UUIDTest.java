package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.UUID;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

public class UUIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidUUID(UUID u){
        UIDTest.testValidUID(u.getUid());
    }

    @Test
    void uUIDValidTest(){
        UUID u = RMObjectTestHelper.uUID();
        s.serializeUUID(u);
        u = s.deserializeUUID();

        testValidUUID(u);
    }
}
