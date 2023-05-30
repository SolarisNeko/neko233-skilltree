package com.neko233.skilltree.commons.compress.huffman;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author SolarisNeko on 2023-02-13
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HuffmanResult {

    private byte[] zip;

    private Map<Byte, String> huffmanPathTree;

}
