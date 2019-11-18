package com.github.kyriosdata.healthcodec.datatypes.quantity;

import com.github.kyriosdata.healthcodec.RMObject.DvAbsoluteQuantityWithDvCount;
import com.github.kyriosdata.healthcodec.RMObject.DvAbsoluteQuantityWithDvDuration;
import com.github.kyriosdata.healthcodec.RMObject.DvAbsoluteQuantityWithDvProportion;
import com.github.kyriosdata.healthcodec.RMObject.DvAbsoluteQuantityWithDvQuantity;
import com.github.kyriosdata.healthcodec.RMObjectSerializationClient;
import com.github.kyriosdata.healthcodec.RMObjectTestHelper;
import com.github.kyriosdata.healthcodec.datatypes.quantity.datetime.DvDurationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DvAbsoluteQuantityTest {

    RMObjectSerializationClient s = null;

    @BeforeEach
    void setUp(){
        s = RMObjectSerializationClient.create();
    }

    public static void testValidDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvCount d) {
        DvQuantifiedTest.testValidDvQuantified(d.getDvQuantified());
        DvCountTest.testValidDvCount(d.getDvCount());


    }

    public static void testValidDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvProportion d) {
        DvQuantifiedTest.testValidDvQuantified(d.getDvQuantified());
        DvProportionTest.testValidDvProportion(d.getDvProportion());
    }

    public static void testValidDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvDuration d) {
        DvQuantifiedTest.testValidDvQuantified(d.getDvQuantified());
        DvDurationTest.testValidDvDuration(d.getDvDuration());
    }

    public static void testValidDvAbsoluteQuantity(
            DvAbsoluteQuantityWithDvQuantity d) {
        DvQuantifiedTest.testValidDvQuantified(d.getDvQuantified());
        DvQuantityTest.testValidDvQuantity(d.getDvQuantity());
    }

    @Test
    void testDvAbsoluteQuantityWithDvCountTest(){
        DvAbsoluteQuantityWithDvCount d = RMObjectTestHelper.dvAQDvCount();
        s.serializeDvAbsoluteQuantity(d);
        d = s.deserializeDvAbsoluteQuantityDvCount();

        testValidDvAbsoluteQuantity(d);
    }

    @Test
    void testDvAbsoluteQuantityWithDvQuantityTest(){
        DvAbsoluteQuantityWithDvQuantity d = RMObjectTestHelper.dvAQDvQuantity();
        s.serializeDvAbsoluteQuantity(d);
        d = s.deserializeDvAbsoluteQuantityDvQuantity();

        testValidDvAbsoluteQuantity(d);
    }

    @Test
    void testDvAbsoluteQuantityWithDvProportionTest(){
        DvAbsoluteQuantityWithDvProportion d = RMObjectTestHelper.dvAQDvProportion();
        s.serializeDvAbsoluteQuantity(d);
        d = s.deserializeDvAbsoluteQuantityDvProportion();

        testValidDvAbsoluteQuantity(d);
    }

    @Test
    void testDvAbsoluteQuantityWithDvDurationTest(){
        DvAbsoluteQuantityWithDvDuration d = RMObjectTestHelper.dvAQDvDuration();
        s.serializeDvAbsoluteQuantity(d);
        d = s.deserializeDvAbsoluteQuantityDvDuration();

        testValidDvAbsoluteQuantity(d);
    }
}
