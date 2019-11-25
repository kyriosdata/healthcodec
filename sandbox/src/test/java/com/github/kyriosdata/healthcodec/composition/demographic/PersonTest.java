package com.github.kyriosdata.healthcodec.composition.demographic;

import com.github.kyriosdata.healthcodec.RMObject.Person;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.Test;

public class PersonTest {
    RMObjectSerializationClient s =  RMObjectSerializationClient.create();

    public static void testValidPerson(Person p){
        ActorTest.testValidActor(p.getActor());
    }

    @Test
    void personValidTest(){
        Person p = RMObjectTestHelper.person();
        s.serializePerson(p);
        p = s.deserializePerson();

        testValidPerson(p);
    }
}
