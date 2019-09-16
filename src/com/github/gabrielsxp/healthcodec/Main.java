/*
* Copyright 2019 Instituto de Informática - UFG
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
package com.github.gabrielsxp.healthcodec;

import com.github.gabrielsxp.healthcodec.RMObjects.*;
import com.github.gabrielsxp.healthcodec.SerializationUtils.*;
import java.io.UnsupportedEncodingException;
import java.nio.ReadOnlyBufferException;

/**
 *
 * @author Gabriel
 */
public class Main {

    public static void main(String[] args)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException,
            UnsupportedEncodingException {
        RMObjectSerializationClient s = RMObjectSerializationClient.create();
        String value = "ISOOID";
        s.serializeISOOID(value);
        ISO_OID io = s.deserializeISOOID();
        System.out.println(io.getValue());

        byte[] buffer = s.getBuffer();

        for (int i = 0; i < buffer.length; i++) {
            System.out.println(buffer[i] + " ");
        }
    }
}
