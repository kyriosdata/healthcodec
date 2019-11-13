package com.github.kyriosdata.healthcodec;

import org.junit.jupiter.api.BeforeEach;

class RMObjectSerializationClientTest {

    private RMObjectSerializationClient s = null;

    @BeforeEach
    void setUp() {
        s = RMObjectSerializationClient.create();
    }

}