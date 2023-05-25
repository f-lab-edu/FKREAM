package com.flab.fkream.search;

import com.flab.fkream.item.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrieNode {

    private boolean end;
    private List<Item> items = new ArrayList<>();
    private Map<Character, TrieNode> children = new TreeMap<>();

    TrieNode() {
    }
}
