package com.github.kyriosdata.healthcodec.composition.content.entry;

import com.github.kyriosdata.healthcodec.RMObject.Entry;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.generic.ParticipationTest;
import com.github.kyriosdata.healthcodec.common.generic.PartyProxyTest;
import com.github.kyriosdata.healthcodec.composition.content.ContentItemTest;
import com.github.kyriosdata.healthcodec.datatypes.support.identification.ObjectRefTest;
import com.github.kyriosdata.healthcodec.datatypes.text.CodePhraseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntryTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidEntry(Entry e){
        ContentItemTest.testValidContentItem(e.getContentItem());
        CodePhraseTest.testValidCodePhrase(e.getLanguage());
        CodePhraseTest.testValidCodePhrase(e.getEncoding());
        PartyProxyTest.testValidPartyProxy(e.getSubject());
        PartyProxyTest.testValidPartyProxy(e.getProvider());
        ObjectRefTest.testValidObjectRef(e.getWorkflowId());
        ParticipationTest.testValidParticipation(e.
                getOtherParticipations().get(0));
    }

    @Test
    void entryValidTest(){
        Entry e = RMObjectTestHelper.entry();
        s.serializeEntry(e);
        e = s.deserializeEntry();

        testValidEntry(e);
    }

    @Test
    void entryNullLanguageTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEntry(RMObjectTestHelper.contentItem(),
                    null,
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.objectRef(),
                    RMObjectTestHelper.participationList(false));
        });
    }

    @Test
    void entryNullEncodingTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEntry(RMObjectTestHelper.contentItem(),
                    RMObjectTestHelper.codePhrase(),
                    null,
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.objectRef(),
                    RMObjectTestHelper.participationList(false));
        });
    }

    @Test
    void entryNullSubjectTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEntry(RMObjectTestHelper.contentItem(),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.codePhrase(),
                    null,
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.objectRef(),
                    RMObjectTestHelper.participationList(false));
        });
    }

    @Test
    void entryEmptyOtherParticipationsTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newEntry(RMObjectTestHelper.contentItem(),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.objectRef(),
                    RMObjectTestHelper.participationList(true));
        });
    }
}
