package com.neko233.skilltree.all_module;

import com.neko233.skilltree.commons.core.base.KvTemplate233;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AllModuleGenerator {

    public static final String template = "<dependency>\n" +
            "\t\t\t<groupId>com.neko233</groupId>\n" +
            "\t\t\t<artifactId>${moduleName}</artifactId>\n" +
            "\t\t\t<version>${project.parent.version}</version>\n" +
            "\t\t</dependency>";

    public static void main(String[] args) {

        String input = "        <!-- 通用层 -->\n" +
                "        <module>skilltree-commons-core</module>\n" +
                "        <module>skilltree-commons-sql</module>\n" +
                "        <module>skilltree-commons-api</module>\n" +
                "        <module>skilltree-commons-parser</module>\n" +
                "        <module>skilltree-commons-metrics</module>\n" +
                "        <module>skilltree-commons-reactive</module>\n" +
                "        <module>skilltree-commons-event-system</module>\n" +
                "        <module>skilltree-commons-storage</module>\n" +
                "\n" +
                "        <!-- 功能层 -->\n" +
                "        <module>skilltree-aop</module>\n" +
                "        <module>skilltree-ioc</module>\n" +
                "        <module>skilltree-actor</module>\n" +
                "        <module>skilltree-counter</module>\n" +
                "        <module>skilltree-id-generator</module>\n" +
                "        <module>skilltree-i18n</module>\n" +
                "        <module>skilltree-distribute-system</module>\n" +
                "        <module>skilltree-memory-database</module>\n" +
                "        <module>skilltree-validator</module>\n" +
                "        <module>skilltree-scheduler</module>";

        List<String> skillTreeModules = extractSkillTreeModules(input);
        List<String> moduleNameList = skillTreeModules.stream()
                .map(moduleName -> KvTemplate233.builder(template)
                        .put("moduleName", moduleName)
                        .build())
                .collect(Collectors.toList());

        moduleNameList.forEach(System.out::println);
    }

    private static List<String> extractSkillTreeModules(String input) {
        List<String> modules = new ArrayList<>();
        Pattern pattern = Pattern.compile("<module>(skilltree-[a-z\\-]+)</module>");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String module = matcher.group(1);
            modules.add(module);
        }

        return modules;
    }


}

