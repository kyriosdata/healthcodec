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
     * @return instância de DvIdentifier
     */
    public static DvIdentifier DvIdentifier(){
        return RMObjectFactory.newDvIdentifier("issuer",
                "assigner", "id", "type");
    }
}
