package com.github.kyriosdata.healthcodec;

import static com.github.kyriosdata.healthcodec.RMObject.*;

/**
 * Classe responsável por criar instâncias com parâmetros fixos que serão
 * utilizados na classe de teste RMObjectSerializationClient
 */
public class RMObjectTestHelper {

    /**
     * Cria uma instância de DvBoolean
     *
     * @param value
     * @return DvBoolean(true) se @param true, DvBoolean(false) se @param false
     */
    public static DvBoolean DvBoolean(boolean value){
       return RMObjectFactory.newDvBoolean(value);
    }

    /**
     * Cria uma instância de DvIdentifier com valores fixos
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em DvIdentifier
     * @return instância de DvIdentifier
     */
    public static DvIdentifier DvIdentifier(boolean forceException){
        return RMObjectFactory.newDvIdentifier(forceException ? "" : "issuer",
                "assigner", "id", "type");
    }

    /**
     * Cria uma instância de UID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em UID
     * @return instância de UID
     */
    public static UID UID(boolean forceException){
        return RMObjectFactory.newUID(forceException ? "" : "value");
    }
}
