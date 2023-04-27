package com.neko233.skilltree.commons.parser.text;


import com.neko233.skilltree.commons.core.base.StringUtils233;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko
 * Date on 2023-01-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextMetadata {

    private int lineCount = 0;
    private int charCount = 0;
    private int byteCount = 0;

    public void addCharCount(String line) {
        if (StringUtils233.isBlank(line)) {
            return;
        }
        charCount += line.length();
    }

    public void addLineCount(int count) {
        this.lineCount += count;
    }

    public void addByteCount(String line) {
        this.byteCount += line.getBytes().length;
    }
}
