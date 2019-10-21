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

/**
 * Identificador de tipo primitivo.
 *
 * TODO não está faltando vetor de byte e boolean?
 */
public enum PrimitiveTypeID {
        
    INT(0,4),
    DOUBLE(1,8),
    BYTE(2,1),
    CHAR(3,2),
    STRING(4, -1);

    /**
     * Código que identifica unicamente o tipo primitivo.
     */
    private final int id;

    /**
     * Tamanho em bytes necessário para armazenar um valor do tipo em questão.
     * O valor {@code -1} é empregado para tipo de tamanho variável, a saber,
     * sequências de caracteres e vetores de bytes.
     */
    private final int size;

    private PrimitiveTypeID(int codigo, int tamanho) {
        id = codigo;
        size = tamanho;
    }

    public int getValue() {
        return id;
    }
}
