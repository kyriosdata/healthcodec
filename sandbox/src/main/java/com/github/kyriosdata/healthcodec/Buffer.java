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

import java.io.UnsupportedEncodingException;
import java.nio.BufferUnderflowException;
import java.nio.ReadOnlyBufferException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Classe Responsável por primeiramente encapsular o
 * buffer de dados e fornecer as operações de E/S sobre o mesmo.
 */
public class Buffer {

    /**
     * Tamanho máximo do buffer.
     */
    private static final int MAX_SIZE_BUFFER = 1024;

    /**
     * Buffer que será utilizado para armazenar os headers e os dados passados
     * como argumento na função serialize. Veja {@link #serialize(byte[])}
     */
    private ByteBuffer buffer;

    /**
     * Evita criação desnecessária de instância.
     */
    private Buffer() {
    }

    /**
     * Função responsável por encapsular um array de bytes
     * recebido com entrada em um ByteBuffer que será armazenado
     * no atributo da classe
     *
     * @return Retorna uma instância com o buffer encapsulado e pronto para
     * as operações de E/S
     */
    public static Buffer serialize() {

        Buffer bf = new Buffer();

        bf.buffer = ByteBuffer.allocate(MAX_SIZE_BUFFER);

        return bf;
    }

    /**
     * Função responsável por encapsular um array de bytes
     * recebido com entrada em um ByteBuffer que será armazenado
     * no atributo da classe
     *
     * @param data array de bytes que é recebido como entrada
     *             contendo os headers (metadados e informações importantes
     *             como o tamanho de uma string, por exemplo) e os conteúdos
     *             associado a cada header presente no array
     * @return Retorna uma instância com o buffer encapsulado e pronto para
     * as operações de E/S
     */
    public static Buffer deserialize(Buffer b) {
        Buffer bf = new Buffer();
        bf.buffer = ByteBuffer.wrap(b.data());

        return bf;
    }

    /**
     * Função responsável por ler 4 bytes do buffer e retornar o número
     * inteiro que corresponde a sequência de bytes lida.
     *
     * @param position Posição do array
     * @return intValue Valor inteiro correspondente aos 4 btyes lidos na
     * operação
     * @throws BufferUnderflowException no caso de não conseguir ler 4 bytes do
     *                                  buffer
     */
    public int readInteger(int position) throws BufferUnderflowException {
        int intValue;
        intValue = buffer.getInt(position);
        return intValue;
    }

    /**
     * Função responsável por escrever um número inteiro (4 bytes) no buffer
     *
     * @param position Posição do array
     * @param i        Número inteiro que será escrito
     * @throws IndexOutOfBoundsException no caso de uma posição maior que o
     *                                   tamanho do array
     * @throws ReadOnlyBufferException   no caso do buffer ser apenas para
     *                                   escrita
     */
    public void writeInteger(int position, int i) {
        buffer.position(position);
        buffer.putInt(position, i);
    }

    /**
     * Função responsável por ler 1 byte do buffer e retorná-lo
     *
     * @param position Posição do array
     * @return byteValue correspondente
     * @throws BufferUnderflowException no caso de não conseguir ler 4 bytes
     *                                  do buffer
     */
    public byte readByte(int position) throws BufferUnderflowException {
        byte byteValue;
        byteValue = buffer.get(position);
        return byteValue;
    }

    /*
     * Função responsável por escrever um único byte no buffer
     *
     * @param position Posição do array
     * @param b byte que será escrito
     * @throws IndexOutOfBoundsException no caso de uma posição maior que o
     * tamanho do array
     * @throws ReadOnlyBufferException no caso do buffer ser apenas para escrita
     */
    public void writeByte(int position, byte b) {
        buffer.position(position);
        buffer.put(position, b);
    }

    /**
     * Função responsável por ler um array de bytes do buffer a partir de
     * uma posição inicial e da quantidade de bytes que serão lidos a partir
     * desta posição
     *
     * @param position Posição do array
     * @param length   Quantidade de bytes que serão lidos a partir da posição
     *                 incial
     * @return byteArray Retorna o array de bytes que armazena os bytes
     * solicitados.
     * @throws BufferUnderflowException  no caso de não conseguir ler a
     *                                   quantidade
     *                                   de bytes do buffer
     * @throws IndexOutOfBoundsException no caso de não conseguir acessar a
     *                                   posição no buffer
     */
    public byte[] readByteArray(int position, int length) {
        byte[] byteArray = new byte[length];
        int j = 0;
        for (int i = position; i < (position + length); i++) {
            byteArray[j] = buffer.get(i);
            j++;
        }
        return byteArray;
    }

    /**
     * TODO Não é o caso de usar System.arraycopy?
     *
     * Função responsável por escrever um array de bytes do buffer a partir de
     * uma posição inicial e da quantidade de bytes que serão lidos a partir
     * desta posição
     *
     * @param position Posição do array
     * @param b[]      Array de bytes fonte que será armazenado no buffer
     * @throws BufferUnderflowException  no caso de não conseguir ler a
     *                                   quantidade
     *                                   de bytes do buffer
     * @throws IndexOutOfBoundsException no caso de não conseguir acessar a
     *                                   posição no buffer
     * @throws BufferOverflowException   se não existe espaço suficiente para
     *                                   armazenar o array de bytes
     */
    public void writeByteArray(int position, byte[] b) {
        int j = 0;
        for (int i = position; i < (position + b.length); i++) {
            writeByte(i, b[j]);
            j++;
        }
    }

    /**
     * Função responsável por ler um array de bytes do buffer a partir de
     * uma posição inicial e da quantidade de bytes que serão lidos a partir
     * desta posição. Esse array de bytes é então convertido em uma String
     * após a conversão de cada caractere para UTF-8
     *
     * @param position Posição do array
     * @param length   Quantidade de bytes que serão lidos a partir da posição
     *                 incial
     * @return string Retorna a String resultante da leitura dos bytes
     * @throws BufferUnderflowException  no caso de não conseguir ler a
     *                                   quantidade de bytes do buffer
     * @throws IndexOutOfBoundsException no caso de não conseguir acessar a
     *                                   posição no buffer
     */
    public String readString(int position, int length) {
        byte[] byteArray = new byte[length];
        int j = 0;
        for (int i = position; i < (position + length); i++) {
            byteArray[j] = buffer.get(i);
            j++;
        }
        return getStringFromByteArray(byteArray);
    }

    /**
     * Função responsável por escrever uma String no buffer a partir de
     * uma posição inicial e da quantidade de bytes que serão lidos a partir
     * desta posição. Cada caracter dessa String será primeiramente convertido
     * em UTF-8.
     *
     * @param position Posição do array
     * @param text     String que será codificada e armazenada
     * @throws IndexOutOfBoundsException no caso de não conseguir acessar a
     *                                   posição no buffer
     * @throws BufferOverflowException   se não existe espaço suficiente para
     *                                   armazenar o array de bytes
     */
    public void writeString(int position, String text) {
        byte[] b = getUTF8BytesFromString(text);
        int j = 0;
        for (int i = position; i < (position + b.length); i++) {
            writeByte(i, b[j]);
            j++;
        }
    }

    /**
     * Função responsável por ler 1 byte do buffer e retornar o byte
     * correspondente ao valor lógico armazenado
     *
     * @param position Posição do array
     * @return booleanValue correspondente
     * @throws BufferUnderflowException no caso de não conseguir ler 4 bytes do
     *                                  buffer
     */
    public boolean readBoolean(int position) throws BufferUnderflowException {
        boolean booleanValue;
        byte byteValue;
        byteValue = buffer.get(position);
        booleanValue = byteValue == 1;
        return booleanValue;
    }

    /**
     * Função responsável por escrever um único valor booleano no buffer
     *
     * @param position Posição do array
     * @param b        boolean que será escrito
     * @throws IndexOutOfBoundsException no caso de uma posição maior que o
     *                                   tamanho do array
     * @throws ReadOnlyBufferException   no caso do buffer ser apenas para
     *                                   escrita
     */
    public void writeBoolean(int position, boolean b)
            throws IndexOutOfBoundsException,
            ReadOnlyBufferException {
        buffer.position(position);
        buffer.put(position, (byte) (b ? 1 : 0));
    }

    /**
     * Função responsável por obter os btyes UTF-8 de uma String
     *
     * @param s String de entrada
     * @return array de bytes contendo a codificação
     */
    public byte[] getUTF8BytesFromString(String s) {
        byte[] bytes = new byte[s.length()];
        try {
            bytes = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            //TODO
        }
        return bytes;
    }

    /**
     * Função repsonsável por receber um array de bytes com os bytes na
     * codificação
     * UTF-8 e realizar a conversão da mesma em String
     *
     * @return a String convertida
     * @params utf8ByteArray Array de bytes com os bytes da String em UTF-8
     */
    public String getStringFromByteArray(byte[] utf8ByteArray) {
        return new String(utf8ByteArray, StandardCharsets.UTF_8);
    }

    /**
     * Função responsável por retornar a representação em array e bytes
     * do buffer (ByteBuffer)
     *
     * @return byte[] data Array de bytes correspondente ao buffer
     */
    public byte[] data() {
        return buffer.array();
    }
}
