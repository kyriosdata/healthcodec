package com.github.kyriosdata.healthcodec.composition;

import com.github.kyriosdata.healthcodec.RMObject.Composition;
import com.github.kyriosdata.healthcodec.RMObjectFactory;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.common.archetyped.LocatableTest;
import com.github.kyriosdata.healthcodec.common.generic.PartyProxyTest;
import com.github.kyriosdata.healthcodec.composition.content.ContentItemTest;
import com.github.kyriosdata.healthcodec.datatypes.text.CodePhraseTest;
import com.github.kyriosdata.healthcodec.datatypes.text.DvCodedTextTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompositionTest {
    RMObjectSerializationClient s = RMObjectSerializationClient.create();

    public static void testValidComposition(Composition c){
        LocatableTest.testValidLocatable(c.getLocatable());
        ContentItemTest.testValidContentItem(c.getContent().get(0));
        CodePhraseTest.testValidCodePhrase(c.getLanguage());
        EventContextTest.testValidEventContext(c.getContext());
        PartyProxyTest.testValidPartyProxy(c.getComposer());
        DvCodedTextTest.testValidDvCodedText(c.getCategory());
        CodePhraseTest.testValidCodePhrase(c.getTerritory());
    }

    @Test
    void compositionTestValid(){
        Composition c = RMObjectTestHelper.composition();
        s.serializeComposition(c);
        c = s.deserializeComposition();

        testValidComposition(c);
    }

    @Test
    void compositionNullLocatableTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newComposition(null,
                    RMObjectTestHelper.contentItemList(false),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.eventContext(),
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.dvCodedText(),
                    RMObjectTestHelper.codePhrase());
        });
    }

    @Test
    void compositionEmptyContentTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newComposition(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.contentItemList(true),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.eventContext(),
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.dvCodedText(),
                    RMObjectTestHelper.codePhrase());
        });
    }

    @Test
    void compositionPersistenteCodeStringTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newComposition(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.contentItemList(false),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.eventContext(),
                    RMObjectTestHelper.partyProxy(),
                    RMObjectFactory.newDvCodedText(RMObjectTestHelper.dvText(),
                            RMObjectFactory.newCodePhrase(
                                    RMObjectTestHelper.terminologyID(),
                                    "persistent")),
                    RMObjectTestHelper.codePhrase());
        });
    }

    @Test
    void compositionNullComposerTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newComposition(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.contentItemList(false),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.eventContext(),
                    null,
                    RMObjectTestHelper.dvCodedText(),
                    RMObjectTestHelper.codePhrase());
        });
    }

    @Test
    void compositionNullLanguageTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newComposition(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.contentItemList(false),
                    null,
                    RMObjectTestHelper.eventContext(),
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.dvCodedText(),
                    RMObjectTestHelper.codePhrase());
        });
    }

    @Test
    void compositionNullCategoryTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newComposition(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.contentItemList(false),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.eventContext(),
                    RMObjectTestHelper.partyProxy(),
                    null,
                    RMObjectTestHelper.codePhrase());
        });
    }

    @Test
    void compositionNullTerritoryTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            RMObjectFactory.newComposition(RMObjectTestHelper.locatable(),
                    RMObjectTestHelper.contentItemList(false),
                    RMObjectTestHelper.codePhrase(),
                    RMObjectTestHelper.eventContext(),
                    RMObjectTestHelper.partyProxy(),
                    RMObjectTestHelper.dvCodedText(),
                    null);
        });
    }
}
