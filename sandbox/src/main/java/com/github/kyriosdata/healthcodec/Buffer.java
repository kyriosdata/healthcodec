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
 * Classe responsável por encapsular operações de leitura e escrita de
 * valores de tipos "primitivos", empregados pelo modelo de referência do
 * openEHR, em um vetor de bytes.
 *
 * <p>Cada um dos tipos primitivos conta com uma operação de leitura e outra
 * de escrita. Além dos tipos primitivos "clássicos", a saber, <em>byte</em>,
 * <em>int</em> e <em>boolean</em>, também há as operações de leitura e
 * escrita para sequências de caracteres (<em>String</em>) e vetor de bytes
 * (<em>byte[]</em>.
 *
 * </p>
 */
public class Buffer {

    /**
     * Tamanho máximo do buffer em bytes.
     */
    private static final int MAX_BYTES_SIZE_BUFFER = 10_000_000;

    /**
     * Estrutura empregada para armazenamento.
     */
    private final ByteBuffer buffer;

    /**
     * Construtor empregado exclusivamente por factory methods.
     */
    private Buffer(final ByteBuffer original) {
        buffer = original;
    }

    /**
     * Cria uma instância (buffer) com a capaciadade máxima de bytes.
     *
     * @return Cria um buffer com a capacidade máxima de bytes.
     */
    public static Buffer newInstance() {
        return new Buffer(ByteBuffer.allocate(MAX_BYTES_SIZE_BUFFER));
    }

    /**
     * Cria um novo buffer a partir dos dados armazenados no buffer fornecido.
     *
     * @return Um novo buffer a partir dos dados do buffer fornecido.
     */
    public static Buffer newInstance(Buffer original) {
        return new Buffer(ByteBuffer.wrap(original.data()));
    }

    /**
     * Cria buffer a partir dos dados fornecidos.
     * @param dados Dados a partir do qual o buffer será definido.
     *
     * @return Instância de buffer a partir dos dados fornecidos.
     */
    public static Buffer newInstance(byte[] dados) {
        return new Buffer(ByteBuffer.wrap(dados));
    }

    /**
     * Obtém o _byte_ do buffer na posição indicada.
     *
     * @param position Posição do _byte_ no buffer.
     * @return O byte armazenado no buffer na posição indicada.
     * @throws IndexOutOfBoundsException se a posição indicada for negativa
     *                                   ou superior ao limite do buffer.
     */
    public byte readByte(int position) {
        return buffer.get(position);
    }

    /**
     * Armazena no buffer, na posição indicada, o byte fornecido.
     *
     * @param position Posição em que o byte deve ser depositado no buffer.
     * @param b        O byte a ser depositado.
     * @throws IndexOutOfBoundsException se a posição é negativa ou superior
     *                                   ao limite do buffer.
     */
    public void writeByte(int position, byte b) {
        buffer.put(position, b);
    }

    /**
     * Função responsável por ler 4 bytes sequenciais do buffer, a partir da
     * posição indicada, e retornar o número inteiro correspondente.
     *
     * @param position Posição inicial de leitura no <em>buffer</em>.
     * @return Valor inteiro correspondente aos 4 btyes lidos a partir da
     * posição indicada.
     * @throws BufferUnderflowException no caso de não conseguir ler 4 bytes do
     *                                  buffer
     */
    public int readInteger(int position) throws BufferUnderflowException {
        return buffer.getInt(position);
    }

    /**
     * Função responsável por armazenar o valor do inteiro fornecido a partir
     * da posição indicada.
     *
     * @param position A posição inicial da escrita do valor inteiro no
     *                 <em>buffer</em>.
     * @param valor    Valor a ser armazenado no <em>buffer</em>.
     * @throws IndexOutOfBoundsException no caso de uma posição maior que o
     *                                   tamanho do array
     * @throws ReadOnlyBufferException   no caso do buffer ser apenas para
     *                                   escrita
     */
    public void writeInteger(int position, int valor) {
        buffer.putInt(position, valor);
    }

    /**
     * Função responsável por ler 8 bytes sequenciais do buffer, a partir da
     * posição indicada, e retornar o double correspondente.
     *
     * @param position Posição inicial de leitura no <em>buffer</em>.
     * @return double aos 8 btyes lidos a partir da
     * posição indicada.
     * @throws BufferUnderflowException no caso de não conseguir ler 8 bytes do
     *                                  buffer
     */
    public double readDouble(int position) throws BufferUnderflowException {
        return buffer.getDouble(position);
    }

    /**
     * Função responsável por armazenar o double fornecido a partir
     * da posição indicada.
     *
     * @param position A posição inicial da escrita do double no
     *                 <em>buffer</em>.
     * @param valor    Valor a ser armazenado no <em>buffer</em>.
     * @throws IndexOutOfBoundsException no caso de uma posição maior que o
     *                                   tamanho do array
     * @throws ReadOnlyBufferException   no caso do buffer ser apenas para
     *                                   escrita
     */
    public void writeDouble(int position, double valor) {
        buffer.putDouble(position, valor);
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
        byte[] bytes = new byte[length];
        buffer.position(position);
        buffer.get(bytes, 0, length);

        return bytes;
    }

    /**
     * TODO Não é o caso de usar System.arraycopy?
     * <p>
     * Função responsável por escrever um array de bytes do buffer a partir de
     * uma posição inicial e da quantidade de bytes que serão lidos a partir
     * desta posição
     *
     * @param position Posição do array
     * @param bytes     Vetor de bytes cujo conteúdo será depositado no buffer.
     * @throws BufferUnderflowException  no caso de não conseguir ler a
     *                                   quantidade
     *                                   de bytes do buffer
     * @throws IndexOutOfBoundsException no caso de não conseguir acessar a
     *                                   posição no buffer
     */
    public void writeByteArray(int position, byte[] bytes) {
        buffer.position(position);
        buffer.put(bytes);
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
        byte byteValue = buffer.get(position);
        return byteValue == 1;
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
