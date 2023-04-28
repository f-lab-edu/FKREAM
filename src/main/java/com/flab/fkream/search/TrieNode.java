package com.flab.fkream.search;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrieNode {

    private boolean end;
    private Map<Character, TrieNode> children = new TreeMap<>();

    TrieNode() {
    }
}
