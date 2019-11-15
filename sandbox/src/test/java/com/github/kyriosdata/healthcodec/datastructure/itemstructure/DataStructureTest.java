package com.github.kyriosdata.healthcodec.datastructure.itemstructure;

import com.github.kyriosdata.healthcodec.RMObject.DataStructure;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import org.junit.jupiter.api.Test;

public class DataStructureTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidDataStructure(DataStructure d){
        LocatableTest.testValidLocatable(d.getLocatable());
    }

    @Test
    void dataStructureValidTest(){
        DataStructure d = RMObjectTestHelper.dataStructure();
        s.serializeDataStructure(d);
        d = s.deserializeDataStructure();

        testValidDataStructure(d);
    }
}
