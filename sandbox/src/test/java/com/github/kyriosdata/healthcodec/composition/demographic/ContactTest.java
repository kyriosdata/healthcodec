package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject.Contact;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContactTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidContact(Contact c){
        LocatableTest.testValidLocatable(c.getLocatable());
        AddressTest.testValidAddress(c.getAddresses().get(0));
    }

    @Test
    void contactValidTest(){
        Contact c = RMObjectTestHelper.contact();
        s.serializeContact(c);
        c = s.deserializeContact();

        testValidContact(c);
    }

    @Test
    void contactEmptyAddressesTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newContact(RMObjectTestHelper.locatable(),
                    null);
        });
    }
}
