package com.github.kyriosdata.healthcodec.composition.message;

import com.github.kyriosdata.healthcodec.RMObject.Message;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.generic.AttestationTest;
import com.github.kyriosdata.healthcodec.composition.demographic.PartyTest;
import com.github.kyriosdata.healthcodec.datatypes.quantity.DvOrdinalTest;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDateTimeTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.PartyRefTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidMessage(Message m){
        DvDateTimeTest.testValidDvDateTime(m.getTimeSent());
        PartyRefTest.testValidPartyRef(m.getSender());
        PartyRefTest.testValidPartyRef(m.getReceiver());
        PartyRefTest.testValidPartyRef(m.getSenderNode());
        PartyRefTest.testValidPartyRef(m.getReceiverNode());
        assertEquals("value", m.getSendersReference());
        assertFalse(m.isInitiator());
        DvOrdinalTest.testValidDvOrdinal(m.getUrgency());
        AttestationTest.testvalidAttestation(m.getSignature());
        PartyTest.testValidParty(m.getParties().iterator().next());
        MessageContentTest.testValidMessageContent(m.getContent());
    }

    @Test
    void messageValidTest(){
        Message m = RMObjectTestHelper.message();
        s.serializeMessage(m);
        m = s.deserializeMessage();

        testValidMessage(m);
    }

    @Test
    void messageTimeSentNullTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessage(null,
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    "value",
                    false,
                    RMObjectTestHelper.dvOrdinal(),
                    RMObjectTestHelper.attestation(),
                    RMObjectTestHelper.partySet(false),
                    RMObjectTestHelper.messageContent());
        });
    }

    @Test
    void messageSenderNullTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessage(RMObjectTestHelper.dvDateTime(),
                    null,
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    "value",
                    false,
                    RMObjectTestHelper.dvOrdinal(),
                    RMObjectTestHelper.attestation(),
                    RMObjectTestHelper.partySet(false),
                    RMObjectTestHelper.messageContent());
        });
    }

    @Test
    void messageReceiverNullTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessage(RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.partyRef(),
                    null,
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    "value",
                    false,
                    RMObjectTestHelper.dvOrdinal(),
                    RMObjectTestHelper.attestation(),
                    RMObjectTestHelper.partySet(false),
                    RMObjectTestHelper.messageContent());
        });
    }

    @Test
    void messageSenderNodeNullTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessage(RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    null,
                    RMObjectTestHelper.partyRef(),
                    "value",
                    false,
                    RMObjectTestHelper.dvOrdinal(),
                    RMObjectTestHelper.attestation(),
                    RMObjectTestHelper.partySet(false),
                    RMObjectTestHelper.messageContent());
        });
    }

    @Test
    void messageReceiverNodeNullTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessage(RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    null,
                    "value",
                    false,
                    RMObjectTestHelper.dvOrdinal(),
                    RMObjectTestHelper.attestation(),
                    RMObjectTestHelper.partySet(false),
                    RMObjectTestHelper.messageContent());
        });
    }

    @Test
    void messageSendersReferenceEmptyTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessage(RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    "",
                    false,
                    RMObjectTestHelper.dvOrdinal(),
                    RMObjectTestHelper.attestation(),
                    RMObjectTestHelper.partySet(false),
                    RMObjectTestHelper.messageContent());
        });
    }

    @Test
    void messageUrgencyNullTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessage(RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    "value",
                    false,
                    null,
                    RMObjectTestHelper.attestation(),
                    RMObjectTestHelper.partySet(false),
                    RMObjectTestHelper.messageContent());
        });
    }

    @Test
    void messageSignatureNullTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessage(RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    "value",
                    false,
                    RMObjectTestHelper.dvOrdinal(),
                    null,
                    RMObjectTestHelper.partySet(false),
                    RMObjectTestHelper.messageContent());
        });
    }

    @Test
    void messagePartiesEmptyTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessage(RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    "value",
                    false,
                    RMObjectTestHelper.dvOrdinal(),
                    RMObjectTestHelper.attestation(),
                    RMObjectTestHelper.partySet(true),
                    RMObjectTestHelper.messageContent());
        });
    }

    @Test
    void messageContentNullTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newMessage(RMObjectTestHelper.dvDateTime(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    RMObjectTestHelper.partyRef(),
                    "value",
                    false,
                    RMObjectTestHelper.dvOrdinal(),
                    RMObjectTestHelper.attestation(),
                    RMObjectTestHelper.partySet(false),
                    null);
        });
    }
}
