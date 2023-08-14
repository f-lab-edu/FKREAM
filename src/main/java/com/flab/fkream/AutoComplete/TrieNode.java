package com.flab.fkream.AutoComplete;

import com.flab.fkream.item.Item;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrieNode {

    private boolean end;
    private Item item;
    private Map<Character, TrieNode> children = new TreeMap<>();


    TrieNode() {
    }
}
