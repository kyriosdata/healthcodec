package com.github.kyriosdata.healthcodec;

import java.util.*;

import static com.github.kyriosdata.healthcodec.RMObject.*;

/**
 * Classe responsável por criar instâncias com parâmetros fixos que serão
 * utilizados na classe de teste RMObjectSerializationClient.
 *
 */
public class RMObjectTestHelper {

    /**
     * Cria uma instância de DvBoolean com valores fixos.
     *
     * @param value
     * @return DvBoolean(true) se @param true, DvBoolean(false) se @param false
     */
    public static DvBoolean dvBoolean(boolean value){
        return RMObjectFactory.newDvBoolean(value);
    }

    /**
     * Cria uma instância de DvIdentifier com valores fixos.
     *
     * @return instância de DvIdentifier
     */
    public static DvIdentifier dvIdentifier(){
        return RMObjectFactory.newDvIdentifier("issuer",
                "assigner", "id", "type");
    }

    /**
     * Cria uma instância de UID com valor fixo.
     *
     * @return instância de UID
     */
    public static UID uID(){
        return RMObjectFactory.newUID("value");
    }

    /**
     * Cria uma instância de InternetID com valor fixo.
     *
     * @return instância de InternetID
     */
    public static InternetID internetID(){
        return RMObjectFactory.newInternetID("openehr");
    }

    /**
     * Cria uma instância de ISO_OID com valor fixo.
     *
     * @return instância de ISO_OID
     */
    public static ISO_OID isooid(){
        return RMObjectFactory.newISOOID("value");
    }

    /**
     * Cria uma instância de UUID com valor fixo.
     *
     * @return instância de UUID
     */
    public static RMObject.UUID uUID(){
        return RMObjectFactory.newUUID("value");
    }

    /**
     * Cria uma instância de GenericID com valor fixo.
     *
     * @return instância de GenericID
     */
    public static GenericID genericID(){
        return RMObjectFactory.newGenericID("value","scheme");
    }

    /**
     * Cria uma instância de TemplateID com valor fixo.
     *
     * @return instância de TemplateID
     */
    public static TemplateID templateID(){
        return RMObjectFactory.newTemplateID("value");
    }

    /**
     * Cria uma instância de TerminologyID com valor fixo.
     *
     * @return instância de TerminologyID
     */
    public static TerminologyID terminologyID(){
        return RMObjectFactory.newTerminologyID("name", "version");
    }

    /**
     * Cria uma instância de CodePhrase com valor fixo.
     *
     * @return instância de CodePhrase
     */
    public static CodePhrase codePhrase(){
        TerminologyID terminologyID = RMObjectTestHelper.terminologyID();
        return RMObjectFactory.newCodePhrase(terminologyID,
                "codeString");
    }

    /**
     * Cria uma instância de DVURI com valor fixo.
     *
     * @return instância de DVURI
     */
    public static DVURI dVURI(){
        return RMObjectFactory.newDVURI("value" );
    }

    /**
     * Cria uma instância de DvEHRURI com valor fixo.
     *
     * @return instância de DvEHRURI
     */
    public static DvEHRURI dvEHRURI(){
        return RMObjectFactory.newDvEHRURI("value");
    }

    /**
     * Cria uma instância de VersionTreeID com valor fixo.
     *
     * @return instância de VersionTreeID
     */
    public static VersionTreeID versionTreeID(){
        return RMObjectFactory.newVersionTreeID("value");
    }

    /**
     * Cria uma instância de ArchetypeID com valor fixo.
     *
     * @return instância de ArchetypeID
     */
    public static ArchetypeID archetypeID(){
        return RMObjectFactory.newArchetypeID("value");
    }

    /**
     * Cria uma instância de ObjectVersionID com valor fixo
     *
     * @return instância de ObjectVersionID
     */
    public static ObjectVersionID objectVersionID(){
        return RMObjectFactory.newObjectVersionID("value");
    }

    /**
     * Cria uma instância de HierObjectID com valor fixo.
     *
     * @return instância de HierObjectID
     */
    public static HierObjectID hierObjectID(){
        return RMObjectFactory.newHierObjectID("value");
    }

    /**
     * Cria uma instância de ObjectID com valor fixo.
     *
     * @return instância de ObjectID
     */
    public static ObjectID objectID(){
        return RMObjectFactory.newObjectID("value");
    }

    /**
     * Cria uma instância de PartyRef com valor fixo.
     *
     * @return instância de PartyRef
     */
    public static PartyRef partyRef(){
        return RMObjectFactory.newPartyRef(
                RMObjectTestHelper.objectID(),"type");
    }

    /**
     * Cria uma instância de ObjectRef com valor fixo.
     *
     * @return instância de ObjectRef
     */
    public static ObjectRef objectRef(){
        return RMObjectFactory.newObjectRef(RMObjectTestHelper.objectID(),
                "namespace",
                "type");
    }

    /**
     * Cria uma instância de EHR com valor fixo.
     *
     * @return instância de EHR
     */
    public static EHR eHR(){
        List<ObjectRef> contributions = new ArrayList<>();
        ObjectRef contribution = RMObjectFactory.newObjectRef(objectID(),
                "namespace", "CONTRIBUTION");
        contributions.add(contribution);

        List<ObjectRef> compositions = new ArrayList<>();
        ObjectRef composition = RMObjectFactory.newObjectRef(objectID(),
                "namespace", "VERSIONED_COMPOSITION");
        compositions.add(composition);

        HierObjectID systemID = hierObjectID();
        HierObjectID ehrID = hierObjectID();
        DvDateTime timeCreated = dvDateTime();
        ObjectRef ehrStatus = objectRef();
        ObjectRef directory = RMObjectFactory.newObjectRef(objectID(),
                "namespace", "VERSIONED_FOLDER");
        contributions.add(contribution);

        return RMObjectFactory.newEHR(systemID, ehrID, timeCreated,
                contributions, ehrStatus, directory, compositions);
    }

    /**
     * Cria uma instância de EHRStatus com valor fixo.
     *
     * @return instância de EHRStatus
     */
    public static EHRStatus ehrStatus(){
        Locatable locatable = locatable();
        PartySelf subject = partySelf();
        boolean isQueryable = true;
        boolean isModifiable = false;
        ItemStructure otherDetails = itemStructure();

        return RMObjectFactory.newEHRStatus(locatable, subject, isQueryable,
                isModifiable, otherDetails);
    }

    /**
     * Cria uma instância de LocatableRef com valor fixo.
     *
     * @return instância de LocatableRef
     */
    public static LocatableRef locatableRef() {
        return RMObjectFactory.newLocatableRef(
                RMObjectTestHelper.objectVersionID(),
                "namespace", "type", "path");
    }

    /**
     * Cria uma instância de ProportionKind com valor fixo.
     *
     * @return instância de ProportionKind
     */
    public static ProportionKind proportionKind(){
        return ProportionKind.FRACTION;
    }

    /**
     * Cria uma instância de AccessGroupRef com valor fixo.
     *
     * @return instância de LocatableRef
     */
    public static AccessGroupRef accessGroupRef(){
        return RMObjectFactory.newAccessGroupRef(
                RMObjectFactory.newObjectID("value"));
    }



    /**
     * Cria uma instância de PartyIdentified com valor fixo.
     *
     *
     * @return instância de PartyIdentified
     */
    public static PartyIdentified partyIdentified(){
        return RMObjectFactory.newPartyIdentified(
                RMObjectFactory.newPartyRef(
                        RMObjectFactory.newObjectID("value"),
                        "type"), "name",
                dvIdentifierList(false));
    }

    /**
     * Cria uma instância de Archetyped com valor fixo
     *
     * @return instância de Archetyped
     */
    public static Archetyped archetyped(){
        return RMObjectFactory.newArchetyped(
                RMObjectFactory.newArchetypeID("value"),
                RMObjectTestHelper.templateID(), "rmVersion");
    }

    /**
     * Cria uma instância de UIDBasedID com valor fixo
     *
     * @return instância de UIDBasedID
     */
    public static UIDBasedID uIDBasedID(){
        return RMObjectFactory.newUIDBasedID("value" );
    }

    /**
     * Cria uma instância de DvEncapsulated com valor fixo
     *
     * @return instância de DvEncapsulated
     */
    public static DvEncapsulated dvEncapsulated(){
        return RMObjectFactory.newDvEncapsulated(
                RMObjectTestHelper.codePhrase(),
                RMObjectTestHelper.codePhrase());
    }

    /**
     * Cria uma instância de DvParsable com valor fixo
     *
     * @return instância de DvParsable
     */
    public static DvParsable dvParsable(){
        return RMObjectFactory.newDvParsable(
                RMObjectTestHelper.dvEncapsulated(),"value",
                "formalism");
    }

    /**
     * Cria uma instância de DvTimeSpecification com valor fixo
     *
     * @return instância de DvTimeSpecification
     */
    public static DvTimeSpecification dvTimeSpecification(){
        return RMObjectFactory.newDvTimeSpecification(
                RMObjectTestHelper.dvParsable());
    }

    public static DvMultimedia dvMultimedia(){
        DvEncapsulated de = RMObjectTestHelper.dvEncapsulated();
        String alternateText = "alternateText";
        CodePhrase mediaType = RMObjectTestHelper.codePhrase();
        CodePhrase compressionAlgorithm =
                RMObjectTestHelper.codePhrase();
        byte[] integrityCheck = {0,1,0,1};
        CodePhrase integrityCheckAlgorithm =
                RMObjectTestHelper.codePhrase();
        DVURI du = RMObjectTestHelper.dVURI();
        byte[] data = {1,0,1,0};

        return RMObjectFactory.newDvMultimedia(
                de,alternateText,mediaType,compressionAlgorithm,integrityCheck,
                integrityCheckAlgorithm,null, du, data);
    }

    /**
     * Cria uma instância de DvTextTest com valor fixo
     *
     * @return instância de DvTextTest
     */
    public static DvText dvText(){
        String value = "value";
        List<TermMapping> mappings =
                RMObjectTestHelper.termMappingList(false);
        String formatting = "formatting";
        DVURI hyperlink = RMObjectTestHelper.dVURI();
        CodePhrase language = RMObjectTestHelper.codePhrase();
        CodePhrase charset = RMObjectTestHelper.codePhrase();

        return RMObjectFactory.newDvText(value, mappings, formatting,
                hyperlink, language, charset);
    }

    /**
     * Cria uma instância de TermMapping com valor fixo.
     *
     * @return instância de TermMapping
     */
    public static TermMapping termMapping(){
        return RMObjectFactory.newTermMapping(
                RMObjectTestHelper.codePhrase(),
                Match.BROADER,
                RMObjectTestHelper.dvCodedText());
    }

    /**
     * Cria uma instância de DvCodedText com valor fixo.
     *
     * @return instância de DvCodedText
     */
    public static DvCodedText dvCodedText(){
        CodePhrase cp = RMObjectTestHelper.codePhrase();

        return RMObjectFactory.newDvCodedText(
                RMObjectTestHelper.dvText(), cp);

    }

    /**
     * Cria uma instância de Link com valor fixo.
     *
     * @return nova instância de Link
     */
    public static Link link(){
        return RMObjectFactory.newLink(RMObjectTestHelper.dvText(),
                RMObjectTestHelper.dvText(),RMObjectTestHelper.dvEHRURI());
    }

    /**
     * Cria uma instância de DvState com valor fixo.
     *
     * @return instância de DvState
     */
    public static DvState dvState(){
        return RMObjectFactory.newDvState(RMObjectTestHelper.dvCodedText(),
                "terminal");
    }

    /**
     * Cria uma instância de DvParagraphTest com valor fixo.
     *
     * @return nova instância de DvParagraphTest
     */
    public static DvParagraph dvParagraph(){
        return RMObjectFactory.newDvParagraph(
                RMObjectTestHelper.dvTextList(false));
    }

    /**
     * Cria uma instância de PartyProxy com valor fixo.
     *
     * @return nova instância de PartyProxy
     */
    public static PartyProxy partyProxy(){
        return RMObjectFactory.newPartyProxy(RMObjectTestHelper.partyRef());
    }

    /**
     * Cria uma instância de FeederAuditDetails com valor fixo
     *
     * @return nova instância de FeederAuditDetails
     */
    public static FeederAuditDetails feederAuditDetails(){
        String systemID = "systemID";
        PartyIdentified provider = RMObjectTestHelper.partyIdentified();
        PartyIdentified location = RMObjectTestHelper.partyIdentified();
        DvDateTime time = dvDateTime();
        PartyProxy subject = RMObjectTestHelper.partyProxy();
        String versionID = "versionID";

        return RMObjectFactory.newFeederAuditDetails(systemID, provider,
                location,  subject, versionID);
    }

    /**
     * Cria uma instância de FeederAudit com valor fixo
     *
     * @return nova instância de FeederAudit
     */
    public static FeederAudit feederAudit(){
        FeederAuditDetails originatingSystemAudit =
                RMObjectTestHelper.feederAuditDetails();
        List<DvIdentifier> originatingSystemItemIDs =
                RMObjectTestHelper.dvIdentifierList(false);
        FeederAuditDetails feederSystemAudit =
                RMObjectTestHelper.feederAuditDetails();

        List<DvIdentifier> feederSystemItemIDs =
                RMObjectTestHelper.dvIdentifierList(false);

        DvEncapsulated originalContent = RMObjectTestHelper.dvEncapsulated();

        return RMObjectFactory.newFeederAudit(originatingSystemAudit,
                originatingSystemItemIDs, feederSystemAudit,
                feederSystemItemIDs, originalContent);
    }

    /**
     * Cria uma instância de Locatable com valor fixo.
     *
     * @return nova instância de Locatable
     */
    public static Locatable locatable(){
        UIDBasedID uid = RMObjectTestHelper.uIDBasedID();
        String archetypeNodeId = "value";
        DvText name = RMObjectTestHelper.dvText();
        Archetyped archetypeDetails = RMObjectTestHelper.archetyped();
        FeederAudit feederAudit = RMObjectTestHelper.feederAudit();
        Set<Link> links = RMObjectTestHelper.linkSet(false);

        return RMObjectFactory.newLocatable(uid, archetypeNodeId, name,
                archetypeDetails, feederAudit, links);
    }

    /**
     * Cria uma instância de PartyRelated com valor fixo
     *
     * @return nova instância de PartyRelated
     */
    public static PartyRelated partyRelated(){
        PartyIdentified pi = RMObjectTestHelper.partyIdentified();
        DvCodedText relationship = RMObjectTestHelper.dvCodedText();

        return RMObjectFactory.newPartyRelated(pi, relationship);
    }

    /**
     * Cria uma instância de PartySelf com valor fixo
     *
     * @return nova instância de PartySelf
     */
    public static PartySelf partySelf(){
        return RMObjectFactory.newPartySelf(RMObjectTestHelper.partyRef());
    }

    /**
     * Cria uma instância de ResourceDescriptionItem com valor fixo
     *
     * @return nova instância de ResourceDescriptionItem
     */
    public static ResourceDescriptionItem resourceDescriptionItem(){
        CodePhrase language =
                RMObjectTestHelper.codePhrase();
        String purpose = "purpose";
        List<String> keywords = RMObjectTestHelper.stringList(false);
        String use = "use";
        String misuse = "misuse";
        String copyright = "copyright";
        Map<String, String> originalResourceUri =
                RMObjectTestHelper.stringStringMap(false);
        Map<String, String> otherDetails =
                RMObjectTestHelper.stringStringMap(false);

        return RMObjectFactory.newResourceDescriptionItem(language,purpose,
                keywords,use,misuse,copyright,originalResourceUri,
                otherDetails);
    }

    /**
     * Cria uma instância de TranslationDetails com valor fixo
     *
     * @return nova instância de TranslationDetails
     */
    public static TranslationDetails translationDetails(){
        CodePhrase language = RMObjectTestHelper.codePhrase();
        Map<String, String> author = RMObjectTestHelper.stringStringMap(
                false);
        String accreditation = "accreditation";
        Map<String, String> otherDetails =RMObjectTestHelper.stringStringMap(
                false);

        return RMObjectFactory.newTranslationDetails(language,author,
                accreditation, otherDetails);
    }

    /**
     * Cria uma instância de Item com valor fixo
     *
     * @return nova instância de Item
     */
    public static Item item(){
        return RMObjectFactory.newItem(RMObjectTestHelper.locatable());
    }

    /**
     * Cria uma instância de Cluster com valor fixo
     *
     * @return nova instância de Cluster
     */
    public static Cluster cluster(){
        return RMObjectFactory.newCluster(
                RMObjectTestHelper.item(),
                RMObjectTestHelper.itemList(false));
    }

    /**
     * Cria uma instância de Element com valor fixo
     *
     * @return nova instância de Element
     */
    public static Element element(){
        return RMObjectFactory.newElement(
                RMObjectTestHelper.item(),
                RMObjectTestHelper.dvCodedText());
    }

    /**
     * Cria uma instância de DataStructure com valor fixo
     *
     * @return nova instância de DataStructure
     */
    public static DataStructure dataStructure(){
        return RMObjectFactory.newDataStructure(
                RMObjectTestHelper.locatable());
    }

    /**
     * Cria uma instância de ItemListTest com valor fixo
     *
     * @return nova instância de ItemListTest
     */
    public static ItemList itemList(){
        return RMObjectFactory.newItemList(
                RMObjectTestHelper.uIDBasedID(),
                "value",
                RMObjectTestHelper.dvText(),
                RMObjectTestHelper.archetyped(),
                RMObjectTestHelper.feederAudit(),
                RMObjectTestHelper.linkSet(false),
                RMObjectTestHelper.elementList(false));
    }

    /**
     * Cria uma instância de ItemStructure com valor fixo
     *
     * @return nova instância de ItemStructure
     */
    public static ItemStructure itemStructure(){
        return RMObjectFactory.newItemStructure(
                RMObjectTestHelper.dataStructure());
    }

    /**
     * Cria uma instância de ItemSingle com valor fixo
     *
     * @return nova instância de ItemSingle
     */
    public static ItemSingle itemSingle(){
        return RMObjectFactory.newItemSingle(RMObjectTestHelper.itemStructure(),
                RMObjectTestHelper.element());
    }

    /**
     * Cria uma instância de ItemTable com valor fixo
     *
     * @return nova instância de ItemTable
     */
    public static ItemTable itemTable(){
        return RMObjectFactory.newItemTable(
                RMObjectTestHelper.itemStructure(),
                RMObjectTestHelper.clusterList(false));
    }

    /**
     * Cria uma instância de ItemTree com valor fixo
     *
     * @return nova instância de ItemTree
     */
    public static ItemTree itemTree(){
        return RMObjectFactory.newItemTree(
                RMObjectTestHelper.itemStructure(),
                RMObjectTestHelper.itemList(false));
    }

    /**
     * Cria uma instância de PartyIdentity com valor fixo
     *
     * @return nova instância de PartyIdentity
     */
    public static PartyIdentity partyIdentity(){
        return RMObjectFactory.newPartyIdentity(
                RMObjectTestHelper.locatable(),
                RMObjectTestHelper.itemStructure());
    }

    /**
     * Cria uma instância de PartyRelationship com valor fixo
     *
     * @return nova instância de PartyRelationship
     */
    public static PartyRelationship partyRelationship(){
        Locatable locatable = RMObjectTestHelper.locatable();
        ItemStructure details = RMObjectTestHelper.itemStructure();
        ObjectRef source = RMObjectTestHelper.objectRef();
        ObjectRef target = RMObjectTestHelper.objectRef();

        return RMObjectFactory.newPartyRelationship(locatable, details,
                 source, target);
    }

    /**
     * Cria uma instância de Address com valor fixo
     *
     * @return nova instância de Address
     */
    public static Address address(){
        return RMObjectFactory.newAddress(RMObjectTestHelper.locatable(),
                RMObjectTestHelper.itemStructure());
    }

    /**
     * Cria uma instância de Contact com valor fixo
     *
     * @return nova instância de Contact
     */
    public static Contact contact(){
        return RMObjectFactory.newContact(
                RMObjectTestHelper.locatable(),
                RMObjectTestHelper.addressList(false));
    }

    /**
     * Cria uma instância de Party com valor fixo
     *
     * @return nova instância de Party
     */
    public static Party party(boolean legalIdentity){
        Locatable locatable = RMObjectTestHelper.locatable();

        Set<PartyIdentity> identities = RMObjectTestHelper.partyIdentitySet(
                false, legalIdentity);

        Set<Contact> contacts = RMObjectTestHelper.contactSet(false);

        Set<PartyRelationship> relationships = RMObjectTestHelper.
                partyRelationshipSet(false);

        Set<LocatableRef> reverseRelationships =
                RMObjectTestHelper.locatableRefSet(false);

        ItemStructure details = RMObjectTestHelper.itemStructure();

        return RMObjectFactory.newParty(locatable, identities, contacts,
                relationships, reverseRelationships, details);
    }

    /**
     * Cria uma instância de Capability com valor fixo
     *
     * @return nova instância de Capability
     */
    public static Capability capability(){
        return RMObjectFactory.newCapability(
                RMObjectTestHelper.locatable(),
                RMObjectTestHelper.itemStructure());
    }

    /**
     * Cria uma instância de Role com valor fixo
     *
     * @return nova instância de Role
     */
    public static Role role(){
        Party party = RMObjectTestHelper.party(false);

        List<Capability> capabilities = RMObjectTestHelper.
                capabilityList(false);

        DvInterval timeValidity = RMObjectTestHelper.dvInterval();

        PartyRef performer = RMObjectTestHelper.partyRef();

        return RMObjectFactory.newRole(party, capabilities, timeValidity,
                performer);
    }

    /**
     * Cria uma instância de Actor com valor fixo
     *
     * @return nova instância de Actor
     */
    public static Actor actor(){
        Party party = RMObjectTestHelper.party(true);

        return RMObjectFactory.newActor(party,
                RMObjectTestHelper.roleSet(false),
                RMObjectTestHelper.dvTextSet(false));
    }

    /**
     * Cria uma instância de Agent com valor fixo
     *
     * @return nova instância de Agent
     */
    public static Agent agent(){
        return RMObjectFactory.newAgent(RMObjectTestHelper.actor());
    }

    /**
     * Cria uma instância de Group com valor fixo
     *
     * @return nova instância de Group
     */
    public static Group group(){
        return RMObjectFactory.newGroup(RMObjectTestHelper.actor());
    }

    /**
     * Cria uma instância de Organisation com valor fixo
     *
     * @return nova instância de Organisation
     */
    public static Organisation organisation(){
        return RMObjectFactory.newOrganisation(RMObjectTestHelper.actor());
    }

    /**
     * Cria uma instância de Person com valor fixo
     *
     * @return nova instância de Person
     */
    public static Person person(){
        return RMObjectFactory.newPerson(RMObjectTestHelper.actor());
    }

    /**
     * Cria uma instância de InstructionDetails com valor fixo
     *
     * @return nova instância de InstructionDetails
     */
    public static InstructionDetails instructionDetails() {
        return RMObjectFactory.newInstructionDetails(
                RMObjectTestHelper.locatableRef(),
                "activityId",
                RMObjectTestHelper.itemStructure());
    }

    /**
     * Cria uma instância de ISMTransition com valor fixo
     *
     * @return nova instância de ISMTransition
     */
    public static ISMTransition iSMTransition(){
        return RMObjectFactory.newISMTransition(
                RMObjectTestHelper.dvCodedText(),
                RMObjectTestHelper.dvCodedText(),
                RMObjectTestHelper.dvCodedText());
    }

    /**
     * Cria uma instância de Activity com valor fixo
     *
     * @return nova instância de Activity
     */
    public static Activity activity(){
        Locatable locatable = RMObjectTestHelper.locatable();
        ItemStructure description = RMObjectTestHelper.itemStructure();
        DvParsable timing = RMObjectTestHelper.dvParsable();
        String actionArchetypeId = "actionArchetypeId";

        return RMObjectFactory.newActivity(locatable, description, timing,
                actionArchetypeId);
    }

    /**
     * Cria uma instância de DvOrdered com valor fixo
     *
     * @return nova instância de DvOrdered
     */
    public static DvOrdered dvOrdered(){
        List<ReferenceRange> otherReferenceRanges =
                referenceRangeList(false);
        DvInterval normalRange = RMObjectFactory.newDvInterval(null,
                null);
        CodePhrase normalStatus = RMObjectTestHelper.codePhrase();

        return RMObjectFactory.newDvOrdered(otherReferenceRanges,
                null, normalStatus);
    }

    /**
     * Cria uma instância de Interval com valor fixo
     *
     * @return nova instância de Interval
     */
    public static Interval interval(){
        DvOrdered upper = RMObjectTestHelper.dvOrdered();
        DvOrdered lower = RMObjectTestHelper.dvOrdered();

        return RMObjectFactory.newInterval(lower, upper);
    }

    /**
     * Cria uma instância de DvInterval com valor fixo
     *
     * @return nova instância de DvInterval
     */
    public static DvInterval dvInterval(){
        DvOrdered upper = RMObjectTestHelper.dvOrdered();
        DvOrdered lower = RMObjectTestHelper.dvOrdered();

        return RMObjectFactory.newDvInterval(lower, upper);
    }

    /**
     * Cria uma instância de ReferenceRange com valor fixo
     *
     * @return nova instância de ReferenceRange
     */
    public static ReferenceRange referenceRange(){
        DvText meaning = RMObjectTestHelper.dvText();
        DvInterval range = RMObjectTestHelper.dvInterval();

        return RMObjectFactory.newReferenceRange(meaning, range);
    }

    /**
     * Cria uma instância de DvQuantified com valor fixo
     *
     * @return nova instância de DvQuantified
     */
    public static DvQuantified dvQuantified(){
        DvOrdered dvOrdered = dvOrdered();
        String magnitudeStatus = "magnitudeStatus";

        return RMObjectFactory.newDvQuantified(dvOrdered, magnitudeStatus);
    }

    /**
     * Cria uma instância de DvAmount com valor fixo
     *
     * @return nova instância de DvAmount
     */
    public static DvAmount dvAmount(){
        DvOrdered dvOrdered = dvOrdered();
        double accuracy = 5.000000;
        boolean accuracyPercent = true;

        return RMObjectFactory.newDvAmount(dvOrdered, accuracy, accuracyPercent);
    }

    /**
     * Cria uma instância de DvOrdinal com valor fixo
     *
     * @return nova instância de DvOrdinal
     */
    public static DvOrdinal dvOrdinal(){
        List<ReferenceRange> otherReferences = referenceRangeList(
                false);
        DvInterval normalRange = dvInterval();
        int value = 10;
        DvCodedText symbol = dvCodedText();

        return RMObjectFactory.newDvOrdinal(otherReferences,normalRange,
                value,symbol);
    }

    /**
     * Cria uma instância de DvCount com valor fixo
     *
     * @return nova instância de DvCount
     */
    public static DvCount dvCount(){
        DvAmount dvAmount = dvAmount();
        int magnitude = 10;

        return RMObjectFactory.newDvCount(dvAmount, magnitude);
    }

    /**
     * Cria uma instância de DvProportion com valor fixo
     *
     * @return nova instância de DvProportion
     */
    public static DvProportion dvProportion(){
        DvAmount dvAmount = dvAmount();
        double numerator = 5.000000;
        double denominator = 2.000000;
        ProportionKind type = ProportionKind.FRACTION;
        int precision = 2;

        return RMObjectFactory.newDvProportion(dvAmount, numerator, denominator,
                type, precision);
    }

    /**
     * Cria uma instância de DvQuantity com valor fixo
     *
     * @return nova instância de DvQuantity
     */
    public static DvQuantity dvQuantity(){
        String units = "units";
        double magnitude = 5.000000;
        int precision = 2;

        return RMObjectFactory.newDvQuantity(dvAmount(), units, magnitude,
                precision);
    }

    /**
     * Cria uma instância de DvAbsoluteQuantity com valor fixo
     *
     * @return nova instância de DvAbsoluteQuantity
     */
    public static DvAbsoluteQuantityWithDvCount dvAQDvCount(){
        DvQuantified dvQuantified = dvQuantified();
        DvCount dvCount = dvCount();

        return RMObjectFactory.newDvAbsoluteQuantity(dvQuantified, dvCount);
    }

    /**
     * Cria uma instância de DvAbsoluteQuantity com valor fixo
     *
     * @return nova instância de DvAbsoluteQuantity
     */
    public static DvAbsoluteQuantityWithDvDuration dvAQDvDuration(){
        DvQuantified dvQuantified = dvQuantified();
        DvDuration dvDuration = dvDuration();

        return RMObjectFactory.newDvAbsoluteQuantity(dvQuantified, dvDuration);
    }

    /**
     * Cria uma instância de DvAbsoluteQuantity com valor fixo
     *
     * @return nova instância de DvAbsoluteQuantity
     */
    public static DvAbsoluteQuantityWithDvProportion dvAQDvProportion(){
        DvQuantified dvQuantified = dvQuantified();
        DvProportion dvProportion = dvProportion();

        return RMObjectFactory.newDvAbsoluteQuantity(dvQuantified, dvProportion);
    }

    /**
     * Cria uma instância de DvAbsoluteQuantity com valor fixo
     *
     * @return nova instância de DvAbsoluteQuantity
     */
    public static  DvAbsoluteQuantityWithDvQuantity dvAQDvQuantity(){
        DvQuantified dvQuantified = dvQuantified();
        DvQuantity dvQuantity = dvQuantity();

        return RMObjectFactory.newDvAbsoluteQuantity(dvQuantified, dvQuantity);
    }

    /**
     * Cria uma instância de DvDuration com valor fixo
     *
     * @return nova instância de DvDuration
     */
    public static DvDuration dvDuration(){
        DvAmount dvAmount = dvAmount();
        String value = "value";

        return RMObjectFactory.newDvDuration(dvAmount, value);
    }

    /**
     * Cria uma instância de DvTemporal com valor fixo
     *
     * @return nova instância de DvTemporal
     */
    public static DvTemporal dvTemporal(){
        DvAbsoluteQuantityWithDvDuration d = dvAQDvDuration();
        String value = "HH:mm:ss";

        return RMObjectFactory.newDvTemporal(d, value);

    }

    /**
     * Cria uma instância de Participation com valor fixo
     *
     * @return nova instância de Participation
     */
    public static Participation participation(){
        PartyProxy performer = partyProxy();
        DvText function = dvText();
        DvCodedText mode = dvCodedText();
        DvInterval time = dvInterval();

        return RMObjectFactory.newParticipation(performer, function, mode,
                time);
    }

    /**
     * Cria uma instância de AuditDetails com valor fixo
     *
     * @return nova instância de AuditDetails
     */
    public static AuditDetails auditDetails(){
        String timePosition = "timePosition";
        PartyProxy committer = partyProxy();
        DvDateTime timeCommitted = dvDateTime();
        DvCodedText changeType = dvCodedText();
        DvText description = dvText();

        return RMObjectFactory.newAuditDetails(timePosition, committer,
                timeCommitted, changeType, description);
    }

    /**
     * Cria uma instância de Attestation com valor fixo
     *
     * @return nova instância de Attestation
     */
    public static Attestation attestation() {
        AuditDetails auditDetails = auditDetails();
        DvMultimedia attestedView = dvMultimedia();
        String proof = "proof";
        Set<DvEHRURI> items = DvEHRURISet(false);
        DvText reason = dvText();

        return RMObjectFactory.newAttestation(auditDetails, attestedView,
                proof, items, reason, true);
    }

    /**
     * Cria uma instância de Contribution com valor fixo.
     *
     * @return nova instância de Contribution
     */
    public static Contribution contribution(){
        ObjectID uid = objectID();
        Set<ObjectRef> versions = objectRefSet(false);
        AuditDetails audit = auditDetails();

        return RMObjectFactory.newContribution(uid, versions, audit);
    }

    /**
     * Cria uma instância de RevisionHistoryItem com valor fixo
     *
     * @return nova instância de RevisionHistoryItem
     */
    public static RevisionHistoryItem revisionHistoryItem(){
        List<AuditDetails> audits = auditDetailsList(false);
        ObjectVersionID versionID = objectVersionID();

        return RMObjectFactory.newRevisionHistoryItem(audits, versionID);
    }

    /**
     * Cria uma instância de RevisionHistory com valor fixo
     *
     * @return nova instância de RevisionHistory
     */
    public static RevisionHistory revisionHistory(){
        List<RevisionHistoryItem> items = revisionHistoryItemList(
                false);

        return RMObjectFactory.newRevisionHistory(items);
    }

    /**
     * Cria uma instância de DvDate com valor fixo
     *
     * @return nova instância de DvDate
     */
    public static DvDate dvDate(){
        DvTemporal dvTemporal = dvTemporal();
        return RMObjectFactory.newDvDate(true, false,
                true, dvTemporal);
    }

    /**
     * Cria uma instância de DvDateTime com valor fixo
     *
     * @return nova instância de DvDateTime
     */
    public static DvDateTime dvDateTime(){
        DvTemporal dvTemporal = dvTemporal();
        DvDate dateTime = dvDate();

        return RMObjectFactory.newDvDateTime(true, false,
                true, false, dvTemporal,
                dateTime);
    }

    /**
     * Cria uma instância de DvTime com valor fixo
     *
     * @return nova instância de DvTime
     */
    public static DvTime dvTime(){
        DvTemporal dvTemporal = dvTemporal();
        return RMObjectFactory.newDvTime(true, false,
                true, false, dvTemporal);
    }

    /**
     * Cria uma instância de Folder com valor fixo
     *
     * @return nova instância de Folder
     */
    public static Folder folder(){
        Locatable locatable = locatable();
        List<Folder> folders = folderList(false);
        List<ObjectRef> items = objectRefList(false);

        return RMObjectFactory.newFolder(locatable, folders, items);
    }

    /**
     * Cria uma instância de ResourceDescription com valor fixo
     *
     * @return nova instância de ResourceDescription
     */
    public static ResourceDescription resourceDescription(){
        Map<String, String> originalAuthor = stringStringMap(false);
        List<String> otherContributors = stringList(false);
        String lifecycleState = "lifecycleState";
        List<ResourceDescriptionItem> details = resourceDescriptionItemList(
                false);
        String resourcePackageUri = "resourcePackageUri";
        Map<String, String> otherDetails = stringStringMap(false);
        AuthoredResource parentResource = authoredResource();

        return RMObjectFactory.newResourceDescription(originalAuthor,
                otherContributors, lifecycleState, details, resourcePackageUri,
                otherDetails, parentResource);
    }

    /**
     * Cria uma instância de AuthoredResource com valor fixo
     *
     * @return nova instância de AuthoredResource
     */
    public static AuthoredResource authoredResource(){
        CodePhrase originalLanguage = codePhrase();
        Map<String, TranslationDetails> translations =
                stringTranslationDetailsMap(false);
        ResourceDescription description = RMObjectFactory.
                newResourceDescription(stringStringMap(false),
                        stringList(false),"lifecycleState",
                        resourceDescriptionItemList(false),
                        "resourcePackageUri",
                        stringStringMap(false), null);

        RevisionHistory revisionHistory = revisionHistory();

        return RMObjectFactory.newAuthoredResource(originalLanguage,
                translations, description, revisionHistory, true);
    }

    /**
     * Cria uma instância de Event com ItemTree e com valor fixo.
     *
     * @return nova instância de Event.
     */
    public static EventWithItemTree eventWithItemTree(){
        Locatable locatable = locatable();
        DvDateTime time = dvDateTime();
        ItemTree data = itemTree();
        ItemStructure state = itemStructure();

        return RMObjectFactory.newEventWithItemTree(locatable, time,
                data, state);
    }

    /**
     * Cria uma instância de Event com ItemSingle e com valor fixo.
     *
     * @return nova instância de Event.
     */
    public static EventWithItemSingle eventWithItemSingle(){
        Locatable locatable = locatable();
        DvDateTime time = dvDateTime();
        ItemSingle data = itemSingle();
        ItemStructure state = itemStructure();

        return RMObjectFactory.newEventWithItemSingle(locatable, time,
                data, state);
    }

    /**
     * Cria uma instância de Event com ItemTable e com valor fixo.
     *
     * @return nova instância de Event.
     */
    public static EventWithItemTable eventWithItemTable(){
        Locatable locatable = locatable();
        DvDateTime time = dvDateTime();
        ItemTable data = itemTable();
        ItemStructure state = itemStructure();

        return RMObjectFactory.newEventWithItemTable(locatable, time,
                data, state);
    }

    /**
     * Cria uma instância de History com ItemTree e com valor fixo.
     *
     * @return nova instância de History.
     */
    public static HistoryWithItemTree historyWithItemTree(){
        DataStructure dataStructure = dataStructure();
        DvDateTime origin = dvDateTime();
        List<EventWithItemTree> events = eventWithItemTreeList(false);
        DvDuration period = dvDuration();
        DvDuration duration = dvDuration();
        ItemStructure summary = itemStructure();

        return RMObjectFactory.newHistoryWithItemTree(dataStructure,
                origin, events, period, duration, summary);
    }

    /**
     * Cria uma instância de History com ItemSingle e com valor fixo.
     *
     * @return nova instância de History.
     */
    public static HistoryWithItemSingle historyWithItemSingle(){
        DataStructure dataStructure = dataStructure();
        DvDateTime origin = dvDateTime();
        List<EventWithItemSingle> events = eventWithItemSingleList(false);
        DvDuration period = dvDuration();
        DvDuration duration = dvDuration();
        ItemStructure summary = itemStructure();

        return RMObjectFactory.newHistoryWithItemSingle(dataStructure,
                origin, events, period, duration, summary);
    }

    /**
     * Cria uma instância de History com ItemTable e com valor fixo.
     *
     * @return nova instância de History.
     */
    public static HistoryWithItemTable historyWithItemTable(){
        DataStructure dataStructure = dataStructure();
        DvDateTime origin = dvDateTime();
        List<EventWithItemTable> events = eventWithItemTableList(false);
        DvDuration period = dvDuration();
        DvDuration duration = dvDuration();
        ItemStructure summary = itemStructure();

        return RMObjectFactory.newHistoryWithItemTable(dataStructure,
                origin, events, period, duration, summary);
    }

    /**
     * Cria uma instância de PointEvent com ItemTree e com valor fixo.
     *
     * @return nova instância de PointEvent.
     */
    public static PointEventWithItemTree pointEventWithItemTree(){
        EventWithItemTree event = eventWithItemTree();

        return RMObjectFactory.newPointEventWithItemTree(event);
    }

    /**
     * Cria uma instância de PointEvent com ItemSingle e com valor fixo.
     *
     * @return nova instância de PointEvent.
     */
    public static PointEventWithItemSingle pointEventWithItemSingle(){
        EventWithItemSingle event = eventWithItemSingle();

        return RMObjectFactory.newPointEventWithItemSingle(event);
    }

    /**
     * Cria uma instância de PointEvent com ItemTable e com valor fixo.
     *
     * @return nova instância de PointEvent.
     */
    public static PointEventWithItemTable pointEventWithItemTable(){
        EventWithItemTable event = eventWithItemTable();

        return RMObjectFactory.newPointEventWithItemTable(event);
    }

    /**
     * Cria uma instância de IntervalEvent com ItemTree e com valor fixo.
     *
     * @return nova instância de IntervalEvent.
     */
    public static IntervalEventWithItemTree intervalEventWithItemTree(){
        EventWithItemTree event = eventWithItemTree();
        DvDuration width = dvDuration();
        DvCodedText mathFunction = dvCodedText();
        int sampleCount = 10;

        return RMObjectFactory.newIntervalEventWithItemTree(event, width,
                mathFunction, sampleCount);
    }

    /**
     * Cria uma instância de IntervalEvent com ItemSingle e com valor fixo.
     *
     * @return nova instância de IntervalEvent.
     */
    public static IntervalEventWithItemSingle intervalEventWithItemSingle(){
        EventWithItemSingle event = eventWithItemSingle();
        DvDuration width = dvDuration();
        DvCodedText mathFunction = dvCodedText();
        int sampleCount = 10;

        return RMObjectFactory.newIntervalEventWithItemSingle(event, width,
                mathFunction, sampleCount);
    }

    /**
     * Cria uma instância de IntervalEvent com ItemTable e com valor fixo.
     *
     * @return nova instância de IntervalEvent.
     */
    public static IntervalEventWithItemTable intervalEventWithItemTable(){
        EventWithItemTable event = eventWithItemTable();
        DvDuration width = dvDuration();
        DvCodedText mathFunction = dvCodedText();
        int sampleCount = 10;

        return RMObjectFactory.newIntervalEventWithItemTable(event, width,
                mathFunction, sampleCount);
    }

    /**
     * Cria uma instância de ContentItem com valor fixo.
     *
     * @return nova instância de IntervalEvent.
     */
    public static  ContentItem contentItem(){
        Locatable locatable = locatable();

        return RMObjectFactory.newContentItem(locatable);
    }

    /**
     * Cria uma instância de Entry com valor fixo.
     *
     * @return nova instância de Entry.
     */
    public static Entry entry(){
        ContentItem contentItem = contentItem();
        CodePhrase language = codePhrase();
        CodePhrase encoding = codePhrase();
        PartyProxy subject = partyProxy();
        PartyProxy provider = partyProxy();
        ObjectRef workflowId = objectRef();
        List<Participation> otherParticipations = participationList(
                false);

        return RMObjectFactory.newEntry(contentItem, language, encoding,
                subject, provider, workflowId, otherParticipations);
    }

    /**
     * Cria uma instância de CareEntry com valor fixo.
     *
     * @return nova instância de CareEntry.
     */
    public static CareEntry careEntry(){
        Entry entry = entry();
        ItemStructure protocol = itemStructure();
        ObjectRef guidelineId = objectRef();

        return RMObjectFactory.newCareEntry(entry, protocol, guidelineId);
    }

    /**
     * Cria uma instância de Action com valor fixo.
     *
     * @return nova instância de Action.
     */
    public static Action action(){
        DvDateTime time = dvDateTime();
        ItemStructure description = itemStructure();
        ISMTransition ismTransition = iSMTransition();
        InstructionDetails instructionDetails = instructionDetails();

        return RMObjectFactory.newAction(time, description, ismTransition,
                instructionDetails);
    }

    /**
     * Cria uma instância de AdminEntry com valor fixo.
     *
     * @return nova instância de AdminEntry.
     */
    public static AdminEntry adminEntry(){
        Entry entry = entry();
        ItemStructure data = itemStructure();

        return RMObjectFactory.newAdminEntry(entry, data);
    }

    public static Instruction instruction(){
        CareEntry careEntry = careEntry();
        DvText narrative = dvText();
        List<Activity> activities = activityList(false);
        DvDateTime expiryTime = dvDateTime();
        DvParsable wfDefinition = dvParsable();

        return RMObjectFactory.newInstruction(careEntry, narrative, activities,
                expiryTime, wfDefinition);
    }

    /**
     * Cria uma instância de Evaluation com valor fixo.
     *
     * @return nova instância de Evaluation.
     */
    public static Evaluation evaluation(){
        CareEntry careEntry = careEntry();
        ItemStructure data = itemStructure();

        return RMObjectFactory.newEvaluation(careEntry, data);
    }

    /**
     * Cria uma instância de Observation com data ItemTree e state
     * ItemTree com valor fixo.
     *
     * @return nova instância de Evaluation.
     */
    public static ObservationWithItemTreeItemTree observationWithItemTreeItemTree(){
        CareEntry careEntry = careEntry();
        HistoryWithItemTree data = historyWithItemTree();
        HistoryWithItemTree state = historyWithItemTree();

        return RMObjectFactory.newObservationWithItemTreeItemTree(careEntry,
                data, state);
    }

    /**
     * Cria uma instância de Observation com data ItemTree e state
     * ItemSingle
     *
     * @return nova instância de Evaluation.
     */
    public static ObservationWithItemTreeItemSingle observationWithItemTreeItemSingle(){
        CareEntry careEntry = careEntry();
        HistoryWithItemTree data = historyWithItemTree();
        HistoryWithItemSingle state = historyWithItemSingle();

        return RMObjectFactory.newObservationWithItemTreeItemSingle(careEntry,
                data, state);
    }

    /**
     * Cria uma instância de Observation com data ItemTree e state
     * ItemTable
     *
     * @return nova instância de Evaluation.
     */
    public static ObservationWithItemTreeItemTable observationWithItemTreeItemTable(){
        CareEntry careEntry = careEntry();
        HistoryWithItemTree data = historyWithItemTree();
        HistoryWithItemTable state = historyWithItemTable();

        return RMObjectFactory.newObservationWithItemTreeItemTable(careEntry,
                data, state);
    }

    /**
     * Cria uma instância de Observation com data ItemSingle e state
     * ItemTree
     *
     * @return nova instância de Evaluation.
     */
    public static ObservationWithItemSingleItemTree observationWithItemSingleItemTree(){
        CareEntry careEntry = careEntry();
        HistoryWithItemSingle data = historyWithItemSingle();
        HistoryWithItemTree state = historyWithItemTree();

        return RMObjectFactory.newObservationWithItemSingleItemTree(careEntry,
                data, state);
    }

    /**
     * Cria uma instância de Observation com data ItemSingle e state
     * ItemSingle
     *
     * @return nova instância de Evaluation.
     */
    public static ObservationWithItemSingleItemSingle observationWithItemSingleItemSingle(){
        CareEntry careEntry = careEntry();
        HistoryWithItemSingle data = historyWithItemSingle();
        HistoryWithItemSingle state = historyWithItemSingle();

        return RMObjectFactory.newObservationWithItemSingleItemSingle(careEntry,
                data, state);
    }

    /**
     * Cria uma instância de Observation com data ItemTable e state
     * ItemTable
     *
     * @return nova instância de Evaluation.
     */
    public static ObservationWithItemTableItemTable observationWithItemTableItemTable(){
        CareEntry careEntry = careEntry();
        HistoryWithItemTable data = historyWithItemTable();
        HistoryWithItemTable state = historyWithItemTable();

        return RMObjectFactory.newObservationWithItemTableItemTable(careEntry,
                data, state);
    }

    /**
     * Cria uma instância de Observation com data ItemTable e state
     * ItemTree
     *
     * @return nova instância de Evaluation.
     */
    public static ObservationWithItemTableItemTree observationWithItemTableItemTree(){
        CareEntry careEntry = careEntry();
        HistoryWithItemTable data = historyWithItemTable();
        HistoryWithItemTree state = historyWithItemTree();

        return RMObjectFactory.newObservationWithItemTableItemTree(careEntry,
                data, state);
    }

    /**
     * Cria uma instância de Observation com data ItemSingle e state
     * ItemTable
     *
     * @return nova instância de Evaluation.
     */
    public static ObservationWithItemSingleItemTable observationWithItemSingleItemTable(){
        CareEntry careEntry = careEntry();
        HistoryWithItemSingle data = historyWithItemSingle();
        HistoryWithItemTable state = historyWithItemTable();

        return RMObjectFactory.newObservationWithItemSingleItemTable(careEntry,
                data, state);
    }

    /**
     * Cria uma instância de Observation com data ItemTable e state
     * ItemSingle
     *
     * @return nova instância de Evaluation.
     */
    public static ObservationWithItemTableItemSingle observationWithItemTableItemSingle(){
        CareEntry careEntry = careEntry();
        HistoryWithItemTable data = historyWithItemTable();
        HistoryWithItemSingle state = historyWithItemSingle();

        return RMObjectFactory.newObservationWithItemTableItemSingle(careEntry,
                data, state);
    }

    /**
     * Cria uma instância de Section com valor fixo
     *
     * @return nova instância de Section.
     */
    public static Section section(){
        ContentItem contentItem = contentItem();
        List<ContentItem> items = contentItemList(false);

        return RMObjectFactory.newSection(contentItem, items);
    }

    /**
     * Cria uma instância de EventContext com valor fixo
     *
     * @return nova instância de EventContext.
     */
    public static EventContext eventContext(){
        PartyIdentified healthCareFacility = partyIdentified();
        DvDateTime startTime = dvDateTime();
        DvDateTime endTime = dvDateTime();
        List<Participation> participations = participationList(false);
        String location = "value";
        DvCodedText setting = dvCodedText();
        ItemStructure otherContext = itemStructure();

        return RMObjectFactory.newEventContext(healthCareFacility, startTime,
                endTime, participations, location, setting, otherContext);
    }

    /**
     * Cria uma instância de Composition com valor fixo
     *
     * @return nova instância de Composition.
     */
    public static Composition composition(){
        Locatable locatable = locatable();
        List<ContentItem> content = contentItemList(false);
        CodePhrase language = codePhrase();
        EventContext context = eventContext();
        PartyProxy composer = partyProxy();
        DvCodedText category = dvCodedText();
        CodePhrase territory = codePhrase();

        return RMObjectFactory.newComposition(locatable, content, language,
                context, composer, category, territory);
    }

    /**
     * Cria uma instância de EHRAccess com valor fixo
     *
     * @return nova instância de EHRAccess.
     */
    public static EHRAccess ehrAccess(){
        Locatable locatable = locatable();
        return RMObjectFactory.newEHRAccess(locatable);
    }

    /**
     * Cria uma instância de XTerminology com valor fixo
     *
     * @return nova instância de XTerminology.
     */
    public static XTerminology xTerminology(){
        ItemStructure itemStructure = itemStructure();

        return RMObjectFactory.newXTerminology(itemStructure);
    }

    /**
     * Cria uma instância de XComposition com valor fixo
     *
     * @return nova instância de XComposition.
     */
    public static XComposition xComposition(){
        boolean primary = true;
        DvEHRURI originalPath = dvEHRURI();
        Composition composition = composition();

        return RMObjectFactory.newXComposition(primary, originalPath,
                composition);
    }

    /**
     * Cria uma instância de xDemographics com valor fixo
     *
     * @return nova instância de xDemographics.
     */
    public static XDemographics xDemographics(){
        Map<ObjectID, Party> parties = objectIDPartyMap(false);
        ItemStructure details = itemStructure();

        return RMObjectFactory.newXDemographics(parties, details);
    }

    /**
     * Cria uma instância de XFolder com valor fixo
     *
     * @return nova instância de XFolder.
     */
    public static XFolder xFolder(){
        Locatable locatable = locatable();
        List<XFolder> folders = xFolderList(false);
        List<XComposition> compositions = xCompositionList(false);

        return RMObjectFactory.newXFolder(locatable, folders, compositions);
    }

    /**
     * Cria uma instância de XFolder com valor fixo
     *
     * @return nova instância de XFolder.
     */
    public static XAccessControl xAccessControl(){
        Map<ObjectID, Party> groups = objectIDPartyMap(false);
        ItemStructure details = itemStructure();

        return RMObjectFactory.newXAccessControl(groups, details);
    }

    /**
     * Cria uma instância de EHRExtract com valor fixo
     *
     * @return nova instância de EHRExtract.
     */
    public static EHRExtract ehrExtract(){
        DvDateTime timeCreated = dvDateTime();
        String ehrId = "value";
        PartyRef subjectOfCare = partyRef();
        PartyRef originator = partyRef();
        Set<Participation> otherParticipations = participationSet(false);
        boolean includeMultimedia = false;
        int followLinks = 10;
        XFolder directory = null;
        XTerminology terminology = xTerminology();
        XDemographics demographics = xDemographics();
        XAccessControl accessControl = xAccessControl();

        return RMObjectFactory.newEHRExtract(timeCreated, ehrId, subjectOfCare,
                originator, otherParticipations, includeMultimedia,
                followLinks, directory, terminology, demographics, accessControl);
    }

    /**
     * Cria uma instância de GenericEntry com valor fixo
     *
     * @return nova instância de GenericEntry.
     */
    public static GenericEntry genericEntry(){
        ContentItem contentItem = contentItem();
        ItemTree data = itemTree();

        return RMObjectFactory.newGenericEntry(contentItem, data);
    }

    /**
     * Cria uma instância de MessageContent com valor fixo
     *
     * @return nova instância de MessageContent.
     */
    public static MessageContent messageContent(){
        Locatable locatable = locatable();

        return RMObjectFactory.newMessageContent(locatable);
    }

    /**
     * Cria uma instância de Message com valor fixo
     *
     * @return nova instância de Message.
     */
    public static Message message(){
        DvDateTime timeSent = dvDateTime();
        PartyRef sender = partyRef();
        PartyRef receiver = partyRef();
        PartyRef senderNode = partyRef();
        PartyRef receiverNode = partyRef();
        String sendersReference = "value";
        boolean initiator = false;
        DvOrdinal urgency = dvOrdinal();
        Attestation signature = attestation();
        Set<Party> parties = partySet(false);
        MessageContent content = messageContent();

        return RMObjectFactory.newMessage(timeSent, sender, receiver,
                senderNode, receiverNode, sendersReference, initiator,
                urgency, signature, parties, content);
    }

    /**
     * Método que gera uma lista de DvIdentifier
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<DvIdentifier> dvIdentifierList(boolean emptyList){
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
     * Método que gera uma lista de XFolder
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<XFolder> xFolderList(boolean emptyList){
        List<XFolder> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        XFolder f = RMObjectFactory.newXFolder(locatable(),
                null, xCompositionList(false));
        list.add(f);

        return list;
    }

    /**
     * Método que gera uma lista de XFolder
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<XComposition> xCompositionList(boolean emptyList){
        List<XComposition> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        XComposition c = xComposition();
        list.add(c);

        return list;
    }

    /**
     * Método que gera uma lista de Activity
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<Activity> activityList(boolean emptyList){
        List<Activity> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Activity a = activity();
        list.add(a);

        return list;
    }

    /**
     * Método que gera uma lista de Participation.
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<Participation> participationList(boolean emptyList){
        List<Participation> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Participation p = participation();
        list.add(p);

        return list;
    }

    /**
     * Método que gera uma lista de Participation.
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<ContentItem> contentItemList(boolean emptyList){
        List<ContentItem> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        ContentItem c = contentItem();
        list.add(c);

        return list;
    }

    /**
     * Método que gera uma lista de EventWithItemTree
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<EventWithItemTree> eventWithItemTreeList(
            boolean emptyList){
        List<EventWithItemTree> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        EventWithItemTree e = eventWithItemTree();
        list.add(e);

        return list;
    }

    /**
     * Método que gera uma lista de EventWithItemSingle
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<EventWithItemSingle> eventWithItemSingleList(
            boolean emptyList){
        List<EventWithItemSingle> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        EventWithItemSingle e = eventWithItemSingle();
        list.add(e);

        return list;
    }

    /**
     * Método que gera uma lista de EventWithItemTable
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<EventWithItemTable> eventWithItemTableList(
            boolean emptyList){
        List<EventWithItemTable> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        EventWithItemTable e = eventWithItemTable();
        list.add(e);

        return list;
    }

    /**
     * Método que gera uma lista de ObjectRef
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<ObjectRef> objectRefList(boolean emptyList){
        List<ObjectRef> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        ObjectRef or = objectRef();
        list.add(or);

        return list;
    }

    /**
     * Método que gera uma lista de ResourceDescriptionItem
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<ResourceDescriptionItem> resourceDescriptionItemList(
            boolean emptyList){
        List<ResourceDescriptionItem> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        ResourceDescriptionItem rdi = resourceDescriptionItem();
        list.add(rdi);

        return list;
    }

    /**
     * Método que gera uma lista de Folder
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<Folder> folderList(boolean emptyList){
        List<Folder> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Folder f = RMObjectFactory.newFolder(locatable(), null,
                objectRefList(false));
        list.add(f);

        return list;
    }

    /**
     * Método que gera uma lista de RevisionHistoryItem
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<RevisionHistoryItem> revisionHistoryItemList(
            boolean emptyList){
        List<RevisionHistoryItem> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        RevisionHistoryItem r = revisionHistoryItem();

        list.add(r);

        return list;
    }

    /**
     * Método que gera uma lista de ReferenceRange
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<ReferenceRange> referenceRangeList(boolean emptyList){
        List<ReferenceRange> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        ReferenceRange r = RMObjectFactory.newReferenceRange(
                RMObjectTestHelper.dvText(), RMObjectFactory.newDvInterval(
                        null, null));
        list.add(r);

        return list;
    }

    /**
     * Método que gera uma lista de TermMapping
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<TermMapping> termMappingList(boolean emptyList){
        List<TermMapping> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        DvText dt = RMObjectFactory.newDvText("value", null,
                "formatting",
                RMObjectTestHelper.dVURI(),
                RMObjectTestHelper.codePhrase(),
                RMObjectTestHelper.codePhrase());

        TermMapping tm = RMObjectFactory.newTermMapping(
                RMObjectTestHelper.codePhrase(),
                Match.BROADER,
                RMObjectFactory.newDvCodedText(dt,
                        RMObjectTestHelper.codePhrase()));
        list.add(tm);
        list.add(tm);
        list.add(tm);

        return list;
    }



    /**
     * Método que gera uma lista de AuditDetails
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<AuditDetails> auditDetailsList(boolean emptyList){
        List<AuditDetails> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        AuditDetails a = auditDetails();

        list.add(a);

        return list;
    }


    /**
     * Método que gera uma lista de DvTextTest
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<DvText> dvTextList(boolean emptyList){
        List<DvText> list = new ArrayList<>();
        if(emptyList){
            return list;
        }

        DvText dt = RMObjectFactory.newDvText("value", null,
                "formatting",
                RMObjectTestHelper.dVURI(),
                RMObjectTestHelper.codePhrase(),
                RMObjectTestHelper.codePhrase());
        list.add(dt);
        list.add(dt);
        list.add(dt);

        return list;
    }

    /**
     * Método que gera uma lista de Capability
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<Capability> capabilityList(boolean emptyList){
        List<Capability> list = new ArrayList<>();
        if(emptyList){
            return list;
        }

        Capability c = RMObjectTestHelper.capability();
        list.add(c);

        return list;
    }


    /**
     * Método que gera um set de Link
     *
     * @param emptySet cria um set vazio
     * @return set
     */
    public static Set<Link> linkSet(boolean emptySet){
        Set<Link> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        Link l = RMObjectTestHelper.link();
        set.add(l);
        set.add(l);
        set.add(l);

        return set;
    }

    /**
     * Método que gera um set de Participation
     *
     * @param emptySet cria um set vazio
     * @return set
     */
    public static Set<Participation> participationSet(boolean emptySet){
        Set<Participation> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        Participation p = participation();
        set.add(p);

        return set;
    }

    /**
     * Método que gera um set de Participation
     *
     * @param emptySet cria um set vazio
     * @return set
     */
    public static Set<Party> partySet(boolean emptySet){
        Set<Party> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        Party p = party(false);
        set.add(p);

        return set;
    }

    /**
     * Método que gera um set de DvEHRURI
     *
     * @param emptySet cria um set vazio
     * @return set
     */
    public static Set<DvEHRURI> DvEHRURISet(boolean emptySet){
        Set<DvEHRURI> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        DvEHRURI d = RMObjectTestHelper.dvEHRURI();
        set.add(d);

        return set;
    }

    /**
     * Método que gera um set de ObjectRef
     *
     * @param emptySet cria um set vazio
     * @return set
     */
    public static Set<ObjectRef> objectRefSet(boolean emptySet){
        Set<ObjectRef> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        ObjectRef o = RMObjectTestHelper.objectRef();
        set.add(o);

        return set;
    }

    /**
     * Método que gera um set de PartyIdentity
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<PartyIdentity> partyIdentitySet(boolean emptySet, boolean
            legalIdentity){
        Set<PartyIdentity> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        PartyIdentity p = RMObjectFactory.newPartyIdentity(
                RMObjectFactory.newLocatable(
                        RMObjectTestHelper.uIDBasedID(),
                        "value",
                        RMObjectFactory.newDvText(legalIdentity ?
                                        "legal identity" : "value",
                                RMObjectTestHelper.
                                        termMappingList(false),
                                "formatting",
                                RMObjectTestHelper.
                                        dVURI(),
                                RMObjectTestHelper.
                                        codePhrase(),
                                RMObjectTestHelper.
                                        codePhrase()),
                        RMObjectTestHelper.archetyped(),
                        RMObjectTestHelper.feederAudit(),
                        RMObjectTestHelper.linkSet(false)),
                RMObjectTestHelper.itemStructure());
        set.add(p);

        return set;
    }

    /**
     * Método que gera um set de Contact
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<Contact> contactSet(boolean emptySet){
        Set<Contact> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        Contact c = RMObjectTestHelper.contact();
        set.add(c);

        return set;
    }

    /**
     * Método que gera um set de PartyRelationship
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<PartyRelationship> partyRelationshipSet(boolean emptySet){
        Set<PartyRelationship> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        PartyRelationship pr = RMObjectTestHelper.partyRelationship();
        set.add(pr);

        return set;
    }

    /**
     * Método que gera um set de LocatableRef
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<LocatableRef> locatableRefSet(boolean emptySet){
        Set<LocatableRef> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        LocatableRef lr = RMObjectTestHelper.locatableRef();
        set.add(lr);

        return set;
    }

    /**
     * Método que gera um set de Role
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<Role> roleSet(boolean emptySet){
        Set<Role> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        Role r = RMObjectTestHelper.role();
        set.add(r);

        return set;
    }

    /**
     * Método que gera um set de DvTextTest
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<DvText> dvTextSet(boolean emptySet){
        Set<DvText> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        DvText d = RMObjectTestHelper.dvText();
        set.add(d);

        return set;
    }

    /**
     * Método que cria uma lista de Strings
     *
     * @param emptyList lista vazia
     *
     * @return list
     */
    public static List<String> stringList(boolean emptyList){
        List<String> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        list.add("value");
        list.add("value");
        list.add("value");

        return list;
    }

    /**
     * Método que cria uma lista de Items
     *
     * @param emptyList lista vazia
     *
     * @return list
     */
    public static List<Item> itemList(boolean emptyList){
        List<Item> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Item i = RMObjectTestHelper.item();

        list.add(i);

        return list;
    }

    /**
     * Método que cria uma lista de Element
     *
     * @param emptyList lista vazia
     *
     * @return list
     */
    public static List<Element> elementList(boolean emptyList){
        List<Element> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Element e = RMObjectTestHelper.element();

        list.add(e);

        return list;
    }

    /**
     * Método que cria uma lista de Cluster
     *
     * @param emptyList lista vazia
     *
     * @return list
     */
    public static List<Cluster> clusterList(boolean emptyList){
        List<Cluster> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Cluster c = RMObjectTestHelper.cluster();

        list.add(c);

        return list;
    }

    /**
     * Método que cria uma lista de Address
     *
     * @param emptyList lista vazia
     *
     * @return list
     */
    public static List<Address> addressList(boolean emptyList){
        List<Address> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Address a = RMObjectTestHelper.address();

        list.add(a);

        return list;
    }

    /**
     * Método que gera um map de String, String
     *
     * @param emptyMap map vazio
     *
     * @return map
     */
    public static Map<String, String> stringStringMap(boolean emptyMap){
        Map<String, String> map = new HashMap<>();
        if(emptyMap){
            return map;
        }
        map.put("key1", "value");
        map.put("key2", "value");
        map.put("key3", "value");

        return map;
    }

    /**
     * Método que gera um map de String, String
     *
     * @param emptyMap map vazio
     *
     * @return map
     */
    public static Map<ObjectID, Party> objectIDPartyMap(boolean emptyMap){
        Map<ObjectID, Party> map = new HashMap<>();
        if(emptyMap){
            return map;
        }
        ObjectID key = objectID();
        Party value = party(false);
        map.put(key, value);

        return map;
    }

    /**
     * Método que gera um map de String, TranslationDetails
     *
     * @param emptyMap map vazio
     *
     * @return map
     */
    public static Map<String, TranslationDetails> stringTranslationDetailsMap(
            boolean emptyMap){
        Map<String, TranslationDetails> map = new HashMap<>();
        if(emptyMap){
            return map;
        }
        map.put("key1", translationDetails());
        map.put("key2", translationDetails());
        map.put("key3", translationDetails());

        return map;
    }
}
