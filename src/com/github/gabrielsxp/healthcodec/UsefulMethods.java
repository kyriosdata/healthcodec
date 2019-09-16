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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Gabriel
 */
public class UsefulMethods {
    
    public static byte[] getUTF8BytesFromString(String s) throws UnsupportedEncodingException{
        return s.getBytes("UTF-8");
    }
    
    public static byte[] writeIntegerOnByteArray(int position, int value, byte[] array){
        ByteBuffer b = ByteBuffer.wrap(array);
        b.putInt(value, position);
        
        return b.array();
    }
    
    /*
    * Função repsonsável por receber um array de bytes com os bytes na codificação
    * UTF-8 e realizar a conversão da mesma em String
    * 
    * @params utf8ByteArray Array de bytes com os bytes da String em UTF-8
    * @return a String convertida
    */
    public static String getStringFromByteArray(byte[] utf8ByteArray) {
        return new String(utf8ByteArray, StandardCharsets.UTF_8);
    }
    
}
