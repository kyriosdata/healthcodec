package com.github.kyriosdata.healthcodec.composition.ehrextract;

import com.github.kyriosdata.healthcodec.RMObject.XComposition;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.composition.CompositionTest;
import com.github.kyriosdata.healthcodec.datatypes.uri.DvEHRURITest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XCompositionTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidXComposition(XComposition c){
        assertTrue(c.isPrimary());
        DvEHRURITest.testValidDvEHRURI(c.getOriginalPath());
        CompositionTest.testValidComposition(c.getComposition());
    }

    @Test
    void xCompositionValidTest(){
        XComposition c = RMObjectTestHelper.xComposition();
        s.serializeXComposition(c);
        c = s.deserializeXComposition();

        testValidXComposition(c);
    }

    @Test
    void xCompositionNullOriginalPathTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newXComposition(true,
                    null,
                    RMObjectTestHelper.composition());
        });
    }

    @Test
    void xCompositionNullCompositionTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newXComposition(true,
                    RMObjectTestHelper.dvEHRURI(),
                    null);
        });
    }
}
