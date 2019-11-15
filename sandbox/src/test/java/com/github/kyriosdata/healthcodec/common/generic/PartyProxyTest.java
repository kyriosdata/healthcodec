package com.github.kyriosdata.healthcodec.common.generic;

import com.github.kyriosdata.healthcodec.RMObject.PartyProxy;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.PartyRefTest;
import org.junit.jupiter.api.Test;

public class PartyProxyTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidPartyProxy(PartyProxy p){
        PartyRefTest.testValidPartyRef(p.getExternalRef());
    }

    @Test
    void partyProxyValidTest(){
        PartyProxy p = RMObjectTestHelper.partyProxy();
        s.serializePartyProxy(p);
        p = s.deserializePartyProxy();

        testValidPartyProxy(p);
    }
}
