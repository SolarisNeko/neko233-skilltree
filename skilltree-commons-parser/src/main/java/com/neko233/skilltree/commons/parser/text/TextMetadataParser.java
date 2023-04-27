package com.neko233.skilltree.commons.parser.text;




import com.neko233.skilltree.commons.core.annotation.NotNull;
import com.neko233.skilltree.commons.core.file.FileUtils233;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 文本元数据统计
 *
 * @author SolarisNeko
 * Date on 2023-01-29
 */
public class TextMetadataParser {

    public static TextMetadata parseByAll(String allContent) {
        final TextMetadata metadata = TextMetadata.builder()
                .lineCount(0)
                .charCount(0)
                .byteCount(0)
                .build();


        Arrays.stream(Optional.ofNullable(allContent).orElse("")
                        .split(System.lineSeparator()))
                .forEach(line -> statisticsLine(metadata).accept(line));

        return metadata;
    }


    public static TextMetadata parseByIterator(File file) throws IOException {
        final TextMetadata metadata = TextMetadata.builder()
                .lineCount(0)
                .charCount(0)
                .byteCount(0)
                .build();
        FileUtils233.iterateLines(file, statisticsLine(metadata));
        return metadata;
    }

    @NotNull
    private static Consumer<String> statisticsLine(final TextMetadata metadata) {
        return (line) -> {
            metadata.addLineCount(1);
            metadata.addCharCount(line);
            metadata.addByteCount(line);
        };
    }
}

