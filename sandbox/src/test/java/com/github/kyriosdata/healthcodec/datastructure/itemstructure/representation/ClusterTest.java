package com.github.kyriosdata.healthcodec.datastructure.itemstructure.representation;

import com.github.kyriosdata.healthcodec.RMObject.Cluster;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

public class ClusterTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidCluster(Cluster c){
        ItemTest.testValidItem(c.getItem());
        ItemTest.testValidItem(c.getItems().get(0));
    }

    @Test
    void clusterValidTest(){
        Cluster c = RMObjectTestHelper.cluster();
        s.serializeCluster(c);
        c = s.deserializeCluster();

        testValidCluster(c);
    }
}
