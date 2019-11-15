package com.github.kyriosdata.healthcodec.common.generic;

import com.github.kyriosdata.healthcodec.RMObject.PartySelf;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.PartyRefTest;
import org.junit.jupiter.api.Test;

public class PartySelfTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidPartySelf(PartySelf p){
        PartyRefTest.testValidPartyRef(p.getExternalRef());
    }

    @Test
    void partySelfValidTest(){
        PartySelf p = RMObjectTestHelper.partySelf();
        s.serializePartySelf(p);
        p = s.deserializePartySelf();

        testValidPartySelf(p);
    }
}
