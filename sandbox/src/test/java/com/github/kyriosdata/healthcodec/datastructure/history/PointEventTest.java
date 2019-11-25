package com.github.kyriosdata.healthcodec.datastructure.history;

import com.github.kyriosdata.healthcodec.RMObject;
import com.github.kyriosdata.healthcodec.RMObject.PointEventWithItemTree;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PointEventTest {
    RMObjectSerializationClient s = null;

    @BeforeEach
    void setUp(){
        s = RMObjectSerializationClient.create();
    }

    public static void testValidPointEventWithItemTree(PointEventWithItemTree p){
        EventTest.testValidEventWithItemTree(p.getEvent());
    }

    public static void testValidPointEventWithItemSingle(
            RMObject.PointEventWithItemSingle p){
        EventTest.testValidEventWithItemSingle(p.getEvent());
    }

    public static void testValidPointEventWithItemTable(
            RMObject.PointEventWithItemTable p){
        EventTest.testValidEventWithItemTable(p.getEvent());
    }

    @Test
    void testValidPointEventWithItemTree(){
        PointEventWithItemTree p = RMObjectTestHelper.pointEventWithItemTree();
        s.serializePointEvent(p);
        p = s.deserializePointEventWithItemTree();

        testValidPointEventWithItemTree(p);
    }

    @Test
    void testValidPointEventWithItemSingle(){
        RMObject.PointEventWithItemSingle p = RMObjectTestHelper.pointEventWithItemSingle();
        s.serializePointEvent(p);
        p = s.deserializePointEventWithItemSingle();

        testValidPointEventWithItemSingle(p);
    }

    @Test
    void testValidPointEventWithItemTable(){
        RMObject.PointEventWithItemTable p = RMObjectTestHelper.pointEventWithItemTable();
        s.serializePointEvent(p);
        p = s.deserializePointEventWithItemTable();

        testValidPointEventWithItemTable(p);
    }
}
