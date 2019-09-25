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

import java.util.HashMap;

/**
 *
 * @author Gabriel
 */
public class Index {
    private final HashMap<String, Integer> positions = new HashMap<>();
    Index() {
    }
    
    public void setItemPosition(String key, int position){
        positions.put(key, position);
    }
    
    public int getItemPosition(String key){
        return positions.get(key);
    }
    
    public static String createKey(String key, int order){
        return key + order;
    }
}
