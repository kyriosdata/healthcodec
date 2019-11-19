package com.github.kyriosdata.healthcodec.common.directory;

import com.github.kyriosdata.healthcodec.RMObject.Folder;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectRefTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FolderTest {

    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidFolder(Folder f){
        LocatableTest.testValidLocatable(f.getLocatable());
        if(f.getFolders() != null){
            testValidFolder(f.getFolders().get(0));
        }
        ObjectRefTest.testValidObjectRef(f.getItems().get(0));
    }

    @Test
    void folderValidTest(){
        Folder f = RMObjectTestHelper.folder();
        s.serializeFolder(f);
        f = s.deserializeFolder();

        testValidFolder(f);
    }

    @Test
    void folderEmptyFoldersTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newFolder(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.folderList(true),
                    RMObjectTestHelper.objectRefList(false));
        });
    }

}
