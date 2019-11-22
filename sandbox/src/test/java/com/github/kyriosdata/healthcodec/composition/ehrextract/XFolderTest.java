package com.github.kyriosdata.healthcodec.composition.ehrextract;

import com.github.kyriosdata.healthcodec.RMObject.XFolder;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XFolderTest {

    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidXFolder(XFolder f){
        LocatableTest.testValidLocatable(f.getLocatable());
        LocatableTest.testValidLocatable(f.getFolders().get(0).getLocatable());
        assertEquals(null, f.getFolders().get(0).getFolders());
        XCompositionTest.testValidXComposition(f.getFolders().get(0).
                getCompositions().get(0));
        XCompositionTest.testValidXComposition(f.getCompositions().get(0));
    }

    @Test
    void xFolderValidTest(){
        XFolder f = RMObjectTestHelper.xFolder();
        s.serializeXFolder(f);
        f = s.deserializeXFolder();

        testValidXFolder(f);
    }

    @Test
    void xFolderEmptyFoldersTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newXFolder(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.xFolderList(true),
                            RMObjectTestHelper.xCompositionList(false));
        });
    }

    @Test
    void xFolderEmptyCompositionsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newXFolder(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.xFolderList(false),
                    RMObjectTestHelper.xCompositionList(true));
        });
    }
}
