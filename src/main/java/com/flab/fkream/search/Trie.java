package com.flab.fkream.search;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.springframework.stereotype.Component;

@Component
public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.getChildren().containsKey(c)) {
                TrieNode trieNode = new TrieNode();
                node.getChildren().put(c, trieNode);
            }
            node = node.getChildren().get(c);
        }
        node.setEnd(true);
    }

    public List<String> search(String word) {
        List<String> results = new ArrayList<>();
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.getChildren().containsKey(c)) {
                return results;
            }
            node = node.getChildren().get(c);
        }
        findAllWords(node, word, results);
        return results;
    }

    private void findAllWords(TrieNode node, String prefix, List<String> result) {
        if (node.isEnd()) {
            result.add(prefix);
        }
        if (result.size()==10){
            return;
        }
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            char ch = entry.getKey();
            TrieNode child = entry.getValue();
            findAllWords(child, prefix + ch, result);
        }
    }
}
