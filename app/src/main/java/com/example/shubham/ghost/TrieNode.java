/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.shubham.ghost;

import java.util.HashMap;


public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        HashMap<Character,TrieNode> temp_child=children;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            TrieNode trieNode;
            if (temp_child.containsKey(c)){
                trieNode=temp_child.get(c);

            }else {
                trieNode=new TrieNode();
                temp_child.put(c,trieNode);
            }
            temp_child=trieNode.children;
            //set leaf node
            if (i==s.length()-1)
                trieNode.isWord=true;
        }

    }


    public boolean isWord(String s) {

        TrieNode trieNode=searchNode(s);
        if (trieNode!=null&&trieNode.isWord)
            return true;
        else
            return false;
    }
    public TrieNode searchNode(String s){
        HashMap<Character,TrieNode> temp_child=children;
        TrieNode trieNode=null;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(temp_child.containsKey(c)){
                trieNode=temp_child.get(c);
                temp_child=trieNode.children;

            }else {
                return null;
            }
        }
        return trieNode;
    }

    public String getAnyWordStartingWith(String s) {
        TrieNode trieNode=searchNode(s);
        String result=s;
        HashMap<Character,TrieNode>temp_child;
        if (trieNode==null) {
            return null;
        }
        else {
            while (!trieNode.isWord){
                temp_child=trieNode.children;
                Character next=(Character) temp_child.keySet().toArray()[0];
                result+=next;
                trieNode=temp_child.get(next);
            }
        }
        return result;
    }

    public String getGoodWordStartingWith(String s) {
        TrieNode trieNode=null;
        String result=s;
        for (int i=0;i<s.length();i++){
            if (trieNode.children.containsKey(s.substring(i,i+1))){
                trieNode=trieNode.children.get(s.substring(i,i+1));

            }else {
                return null;
            }
        }
        int j=result.length();
        while (true){
            Character c=trieNode.children.entrySet().iterator().next().getKey();
            if (c==null&& j!=result.length()+1 && trieNode.children.size()>1){
                return result;

            }else {
                j++;
                result +=c;
                trieNode=trieNode.children.get(c);

            }
        }
    }
}
