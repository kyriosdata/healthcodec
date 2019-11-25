package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.ISO_OID;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

public class ISO_OIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidISOOID(ISO_OID i){
        UIDTest.testValidUID(i.getUid());
    }

    @Test
    void iSOOIDValidTest(){
        ISO_OID i = RMObjectTestHelper.isooid();
        s.serializeISOOID(i);
        i = s.deserializeISOOID();

        testValidISOOID(i);
    }
}
