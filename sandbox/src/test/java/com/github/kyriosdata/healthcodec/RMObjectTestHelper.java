package com.github.kyriosdata.healthcodec;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Cria uma instância de InternetID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em InternetID
     * @return instância de InternetID
     */
    public static InternetID InternetID(boolean forceException){
        return RMObjectFactory.newInternetID(forceException ? "000" : "openehr");
    }

    /**
     * Cria uma instância de ISO_OID com valor fixo
     *
     * @return instância de ISO_OID
     */
    public static ISO_OID ISOOID(){
        return RMObjectFactory.newISOOID("value");
    }

    /**
     * Cria uma instância de UUID com valor fixo
     *
     * @return instância de UUID
     */
    public static UUID UUID(){
        return RMObjectFactory.newUUID("value");
    }

    /**
     * Cria uma instância de GenericID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em GenericID
     * @return instância de GenericID
     */
    public static GenericID GenericID(boolean forceException){
        return RMObjectFactory.newGenericID(
                "value", forceException ? "" : "scheme");
    }

    /**
     * Cria uma instância de TemplateID com valor fixo
     *
     * @return instância de TemplateID
     */
    public static TemplateID TemplateID(){
        return RMObjectFactory.newTemplateID("value");
    }

    /**
     * Cria uma instância de TerminologyID com valor fixo
     *
     * @return instância de TerminologyID
     */
    public static TerminologyID TerminologyID(){
        return RMObjectFactory.newTerminologyID("name", "version");
    }

    /**
     * Cria uma instância de CodePhrase com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em CodePhrase
     * @return instância de CodePhrase
     */
    public static CodePhrase CodePhrase(boolean forceException){
        TerminologyID terminologyID = RMObjectTestHelper.TerminologyID();
        return RMObjectFactory.newCodePhrase(
                forceException ? null : terminologyID, "codeString");
    }

    /**
     * Cria uma instância de DVURI com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em DVURI
     * @return instância de DVURI
     */
    public static DVURI DVURI(boolean forceException){
        return RMObjectFactory.newDVURI(forceException ? null : "value" );
    }

    /**
     * Cria uma instância de DVEHRURI com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em DVEHRURI
     * @return instância de DVEHRURI
     */
    public static DVEHRURI DVEHRURI(boolean forceException){
        return RMObjectFactory.newDVEHRURI(forceException ? "" : "value");
    }

    /**
     * Cria uma instância de VersionTreeID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em VersionTreeID
     * @return instância de VersionTreeID
     */
    public static VersionTreeID VersionTreeID(boolean forceException){
        return RMObjectFactory.newVersionTreeID(forceException ? "" : "value");
    }

    /**
     * Cria uma instância de ArchetypeID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em ArchetypeID
     * @return instância de ArchetypeID
     */
    public static ArchetypeID ArchetypeID(boolean forceException){
        return RMObjectFactory.newArchetypeID(forceException ? "" : "value");
    }

    /**
     * Cria uma instância de ObjectVersionID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em ObjectVersionID
     * @return instância de ObjectVersionID
     */
    public static ObjectVersionID ObjectVersionID(boolean forceException){
        return RMObjectFactory.newObjectVersionID(
                forceException ? "" : "value");
    }

    /**
     * Cria uma instância de HierObjectID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em HierObjectID
     * @return instância de HierObjectID
     */
    public static HierObjectID HierObjectID(boolean forceException){
        return RMObjectFactory.newHierObjectID(forceException ? "" : "value");
    }

    /**
     * Cria uma instância de ObjectID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em ObjectID
     * @return instância de ObjectID
     */
    public static ObjectID ObjectID(boolean forceException){
        return RMObjectFactory.newObjectID(forceException ? "" : "value");
    }

    /**
     * Cria uma instância de PartyRef com valor fixo
     *
     * @return instância de PartyRef
     */
    public static PartyRef PartyRef(){
        return RMObjectFactory.newPartyRef(
                RMObjectTestHelper.ObjectID(false),
                "type");
    }

    /**
     * Cria uma instância de ObjectRef com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em ObjectRef
     * @return instância de ObjectRef
     */
    public static ObjectRef ObjectRef(boolean forceException){
        return RMObjectFactory.newObjectRef(forceException ? null :
                RMObjectTestHelper.ObjectID(false),
                "namespace",
                "type");
    }

    /**
     * Cria uma instância de LocatableRef com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em LocatableRef
     * @return instância de LocatableRef
     */
    public static LocatableRef LocatableRef(boolean forceException) {
        return RMObjectFactory.newLocatableRef(forceException ? null :
                        RMObjectTestHelper.ObjectVersionID(false),
                "namespace", "type", "path");
    }

    /**
     * Cria uma instância de ProportionKind com valor fixo
     *
     * @return instância de ProportionKind
     */
    public static ProportionKind ProportionKind(){
        return RMObjectFactory.newPropotionKind(1);
    }

    /**
     * Cria uma instância de AccessGroupRef com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em AccessGroupRef
     * @return instância de LocatableRef
     */
    public static AccessGroupRef AccessGroupRef(boolean forceException){
        return RMObjectFactory.newAccessGroupRef(forceException ? null :
                RMObjectFactory.newObjectID("value"));
    }



    /**
     * Cria uma instância de PartyIdentified com valor fixo
     *
     * @param forcePartyRefException flag que aciona uma
     *                               IllegalArgumentException de variável null
     *                               ou vazia em PartyIdentified
     * @param forceNameException flag que aciona uma
     *                           {@link IllegalArgumentException} de
     *                           variável null ou vazia em PartyIdentified
     * @param forceListException flag que aciona uma lista vazia
     *                           em PartyIdentified
     *
     * @return instância de PartyIdentified
     */
    public static PartyIdentified PartyIdentified(
            boolean forcePartyRefException, boolean forceNameException,
            boolean forceListException){
        return RMObjectFactory.newPartyIdentified(
                RMObjectFactory.newPartyRef(
                        forcePartyRefException ? null :
                                RMObjectFactory.newObjectID("value"),
                        "value"),
                forceNameException ? "" : "name",
                forceListException ? DvIdentifierList(true) :
                        DvIdentifierList(false));
    }

    /**
     * Cria uma instância de Archetyped com valor fixo
     *
     * @param forceArchetypeIdException flag que aciona uma variável nula em
     *                                  Archetyped
     * @param forceRmVersionException   flag que aciona uma variável vazia em
     *                                  Archetyped
     * @return instância de Archetyped
     */
    public static Archetyped Archetyped(boolean forceArchetypeIdException,
                                        boolean forceRmVersionException){
        return RMObjectFactory.newArchetyped(forceArchetypeIdException ? null :
                        RMObjectFactory.newArchetypeID("value"),
                RMObjectTestHelper.TemplateID(),
                forceRmVersionException ? "" : "rmVersion");
    }

    /**
     * Cria uma instância de UIDBasedID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em UIDBasedID
     * @return instância de UIDBasedID
     */
    public static UIDBasedID UIDBasedID(boolean forceException){
        return RMObjectFactory.newUIDBasedID(forceException ? "" : "value" );
    }

    /**
     * Cria uma instância de DvEncapsulated com valor fixo
     *
     * @return instância de DvEncapsulated
     */
    public static DvEncapsulated DvEncapsulated(){
        return RMObjectFactory.newDvEncapsulated(
                RMObjectTestHelper.CodePhrase(false),
                RMObjectTestHelper.CodePhrase(false));
    }

    /**
     * Cria uma instância de DvParsable com valor fixo
     *
     * @param forceValueException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em DvParsable
     * @param forceFormalismException flag que aciona uma I
     *                       llegalArgumentException de variável null
     *                       ou vazia em DvParsable
     * @return instância de DvParsable
     */
    public static DvParsable DvParsable(boolean forceValueException,
                                        boolean forceFormalismException){
        return RMObjectFactory.newDvParsable(
                RMObjectTestHelper.DvEncapsulated(),
                forceValueException ? null : "value",
                forceFormalismException ? "" : "formalism");
    }

    /**
     * Cria uma instância de DvTimeSpecification com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em DvTimeSpecification
     * @return instância de DvTimeSpecification
     */
    public static DvTimeSpecification DvTimeSpecification(boolean forceException){
        return RMObjectFactory.newDvTimeSpecification(
                forceException ? null :
                RMObjectTestHelper.DvParsable(false,
                        false));
    }

    public static DvMultimedia DvMultimedia(boolean forceMediaTypeException,
                  boolean forceCompressionAlgorithmException,
                  boolean forceIntegrityCheckException,
                  boolean forceDataException){
        DvEncapsulated de = RMObjectTestHelper.DvEncapsulated();
        String alternateText = "alternateText";
        CodePhrase mediaType = RMObjectTestHelper.CodePhrase(false);
        CodePhrase compressionAlgorithm =
                RMObjectTestHelper.CodePhrase(false);
        byte[] integrityCheck = {0,1,0,1};
        CodePhrase integrityCheckAlgorithm =
                RMObjectTestHelper.CodePhrase(false);
        DVURI du = RMObjectTestHelper.DVURI(false);
        byte[] data = {1,0,1,0};

        return RMObjectFactory.newDvMultimedia(
                de,
                alternateText,
                forceMediaTypeException ? null : mediaType,
                forceCompressionAlgorithmException ? null : compressionAlgorithm,
                integrityCheck,
                forceIntegrityCheckException ? null : integrityCheckAlgorithm,
                null,
                forceDataException ? null : du,
                forceDataException ? null : data);
    }

    /**
     * Cria uma instância de DvText com valor fixo
     *
     * @param foceMappingsException erro causado por uma lista vazia
     * @param forceFormattingException string não pode ser vazia
     *
     * @return instância de DvText
     */
    @Test
    public static DvText DvText( boolean foceMappingsException,
                         boolean forceFormattingException){
        String value = "value";
        List<TermMapping> mappings =
                foceMappingsException ?
                RMObjectTestHelper.TermMappingList(true) :
                RMObjectTestHelper.TermMappingList(false);
        String formatting = forceFormattingException ? "" : "formatting";
        DVURI hyperlink = RMObjectTestHelper.DVURI(false);
        CodePhrase language = RMObjectTestHelper.CodePhrase(false);
        CodePhrase charset = RMObjectTestHelper.CodePhrase(false);

        return RMObjectFactory.newDvText(value, mappings, formatting,
                hyperlink, language, charset);
    }

    /**
     * Método que gera uma lista de DvIdentifier
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    private static List<DvIdentifier> DvIdentifierList(boolean emptyList){
        List<DvIdentifier> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        DvIdentifier id = RMObjectFactory.newDvIdentifier("issuer",
                "assigner", "id", "type");
        list.add(id);
        list.add(id);
        list.add(id);

        return list;
    }

    /**
     * Método que gera uma lista de TermMapping
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    private static List<TermMapping> TermMappingList(boolean emptyList){
        List<TermMapping> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        DvText dt = RMObjectFactory.newDvText("value", null,
                "formatting",
                RMObjectTestHelper.DVURI(false),
                RMObjectTestHelper.CodePhrase(false),
                RMObjectTestHelper.CodePhrase(false));

        TermMapping tm = RMObjectFactory.newTermMapping(
                RMObjectTestHelper.CodePhrase(false),
                Match.BROADER,
                RMObjectFactory.newDvCodedText(dt,
                        RMObjectTestHelper.CodePhrase(false)));
        list.add(tm);
        list.add(tm);
        list.add(tm);

        return list;
    }

}
