/*
 * Copyright 2019 Instituto de Informática - UFG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.kyriosdata.healthcodec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void verificaLimites() {
        Buffer buffer = Buffer.newInstance(new byte[1]);
        assertEquals(0, buffer.readByte(0));
        assertThrows(IndexOutOfBoundsException.class,
                () -> buffer.readByte(-1));
        assertThrows(IndexOutOfBoundsException.class,
                () -> buffer.readByte(1));
    }
}
