package com.github.kyriosdata.healthcodec.datatypes.support.identification;

import com.github.kyriosdata.healthcodec.RMObject.LocatableRef;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocatableRefTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidLocatableRef(LocatableRef l){
        ObjectRefTest.testValidObjectRef(l.getObjectRef());
        assertEquals("path", l.getPath());
    }

    @Test
    void locatableRefValidTest(){
        LocatableRef l = RMObjectTestHelper.locatableRef();
        s.serializeLocatableRef(l);
        l = s.deserializeLocatableRef();

        testValidLocatableRef(l);
    }

    @Test
    void locatableRefNullIdTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newLocatableRef(null, "namespace",
                    "type", "path");
        });
    }

    @Test
    void locatableRefNullNamespaceTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newLocatableRef(RMObjectTestHelper.objectVersionID(),
                    null,"type", "path");
        });
    }

    @Test
    void locatableRefNullTypeTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newLocatableRef(RMObjectTestHelper.objectVersionID(),
                    "namespace",null, "path");
        });
    }

    @Test
    void locatableRefEmptyPathTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newLocatableRef(RMObjectTestHelper.objectVersionID(),
                    "namespace","type", "");
        });
    }
}
