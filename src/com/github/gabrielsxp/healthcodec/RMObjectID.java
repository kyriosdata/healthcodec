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

/**
 *
 * @author Gabriel
 */
public enum RMObjectID {
    DVBOOLEAN(0),
    DVIDENTIFIER(1),
    INTERNETID(2),
    ISO_OID(3),
    UUID(4),
    TERMINOLOGYID(5),
    GENERICID(6),
    TEMPLATEID(7),
    CODEPHRASE(8),
    DVURI(9),
    DVEHRURI(10),
    VERSIONTREEID(11),
    ARCHETYPEID(12),
    OBJECTVERSIONID(13),
    HIEROBJECTID(14),
    OBJECTID(15),
    PARTYREF(16),
    OBJECTREF(17),
    LOCATABLEREF(18),
    PROPORTIONKIND(19),
    ACCESSGROUPREF(20),
    PARTYIDENTIFIED(21),
    ARCHETYPED(22),
    DVENCAPSULATED(23),
    UIDBASEDID(24),
    DVPARSABLE(25),
    DVTIMESPECIFICATION(26),
    DVMULTIMEDIA(27);
    

    private final int value;

    private RMObjectID(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
