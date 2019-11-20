package com.github.kyriosdata.healthcodec.common.resource;

import com.github.kyriosdata.healthcodec.RMObject.ResourceDescription;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceDescriptionTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidResourceDescription(ResourceDescription r){
        assertEquals("value", r.getOriginalAuthor().get("key1"));
        assertEquals("value", r.getOriginalAuthor().get("key2"));
        assertEquals("value", r.getOriginalAuthor().get("key3"));
        assertEquals("value", r.getOtherContributors().get(0));
        ResourceDescriptionItemTest.testValidResourceDescriptionItem(
                r.getDetails().get(0));
        assertEquals("resourcePackageUri", r.getResourcePackageUri());
        assertEquals("value", r.getOtherDetails().get("key1"));
        assertEquals("value", r.getOtherDetails().get("key2"));
        assertEquals("value", r.getOtherDetails().get("key3"));
        if(r.getParentResource() != null){
            AuthoredResourceTest.testValidAuthoredResource(
                    r.getParentResource());
        }
    }

    @Test
    void resourceDescriptionValidTest(){
        ResourceDescription r = RMObjectTestHelper.resourceDescription();
        s.serializeResourceDescription(r);
        r = s.deserializeResourceDescription();

        testValidResourceDescription(r);
    }
}
