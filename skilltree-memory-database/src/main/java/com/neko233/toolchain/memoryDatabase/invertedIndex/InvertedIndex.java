package com.neko233.toolchain.memoryDatabase.invertedIndex;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 倒排索引
 *
 * @author SolarisNeko
 */
public class InvertedIndex {
    // <分词, <documentFrequency, documentId Set>>
    private final Map<String, Map<Long, Set<Integer>>> invertedIndex = new ConcurrentHashMap<>();

    /**
     * @param documentId    改文章的文档 id
     * @param documentWords 出现过的分词
     */
    public void addDocument(int documentId, List<String> documentWords) {
        for (int i = 0; i < documentWords.size(); i++) {
            String word = documentWords.get(i);
            Map<Long, Set<Integer>> documentFrequencyMap = invertedIndex.computeIfAbsent(word, k -> new ConcurrentHashMap<>());
            long documentFrequency = i + 1;

            Set<Integer> documentIds = documentFrequencyMap.computeIfAbsent(documentFrequency, k -> ConcurrentHashMap.newKeySet());
            documentIds.add(documentId);
        }
    }

    public Map<Long, Set<Integer>> getDocumentFrequencyMap(String word) {
        return invertedIndex.get(word);
    }
}

