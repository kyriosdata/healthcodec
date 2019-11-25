package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.TemplateID;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

public class TemplateIDTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidTemplateID(TemplateID t){
        ObjectIDTest.testValidObjectID(t.getObjectID());
    }

    @Test
    void templateIDValidTest(){
        TemplateID t = RMObjectTestHelper.templateID();
        s.serializeTemplateID(t);
        t = s.deserializeTemplateID();

        testValidTemplateID(t);
    }
}
