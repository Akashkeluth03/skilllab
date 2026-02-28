import java.util.*;

class AutocompleteSystem {

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isWord = true;
        int frequency = 0;

        // Store top 5 suggestions sorted by frequency (descending)
        List<String> topSuggestions = new ArrayList<>();
    }

    private TrieNode root = new TrieNode();

    // Global frequency map
    private Map<String, Integer> wordFrequency = new HashMap<>();

    private static final int TOP_K = 5;

    // Insert word with frequency update
    public void insert(String word) {
        wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);

        TrieNode node = root;

        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);

            updateTopSuggestions(node, word);
        }

        node.isWord = true;
        node.frequency = wordFrequency.get(word);
    }

    // Update top 5 list at each node
    private void updateTopSuggestions(TrieNode node, String word) {

        if (!node.topSuggestions.contains(word)) {
            node.topSuggestions.add(word);
        }

        // Sort by frequency descending
        node.topSuggestions.sort((a, b) ->
                wordFrequency.get(b) - wordFrequency.get(a));

        // Keep only top 5
        if (node.topSuggestions.size() > TOP_K) {
            node.topSuggestions.remove(node.topSuggestions.size() - 1);
        }
    }

    // O(L) prefix search
    public List<String> getTopSuggestions(String prefix) {
        TrieNode node = root;

        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return new ArrayList<>();
            }
            node = node.children.get(c);
        }

        return node.topSuggestions;
    }

    // Simple test
    public static void main(String[] args) {
        AutocompleteSystem system = new AutocompleteSystem();

        system.insert("apple");
        system.insert("app");
        system.insert("application");
        system.insert("app");
        system.insert("apex");
        system.insert("appetite");
        system.insert("apple");
        system.insert("apple");
        system.insert("apply");

        System.out.println(system.getTopSuggestions("app"));
    }
}