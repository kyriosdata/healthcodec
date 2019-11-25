package com.github.kyriosdata.healthcodec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BufferTest {

    private Buffer buffer;

    @BeforeEach
    void criaBuffer() {
        buffer = Buffer.newInstance();
    }

    @Test
    void obtemInstanciaTamanhoMaximo() {
        assertNotNull(Buffer.newInstance());
    }

    @Test
    void depositaCarregaByte() {
        final byte umByte = (byte) 0xF2;
        buffer.writeByte(0, umByte);
        byte carregado = buffer.readByte(0);
        assertEquals(umByte, carregado);
    }


    @Test
    void verificaLimitesBufferSemElementos() {
        Buffer buffer = Buffer.newInstance(new byte[0]);
        assertThrows(IndexOutOfBoundsException.class,
                () -> buffer.readByte(0));
    }

    _re@Test
    void verificaLimites() {
        Buffer buffer = Buffer.newInstance(new byte[1]);
        assertEquals(0, buffer.readByte(0));
        assertThrows(IndexOutOfBoundsException.class,
                () -> buffer.readByte(-1));
        assertThrows(IndexOutOfBoundsException.class,
                () -> buffer.readByte(1));
    }
}
