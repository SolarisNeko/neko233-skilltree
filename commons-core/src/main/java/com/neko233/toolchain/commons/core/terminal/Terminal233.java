package com.neko233.toolchain.commons.core.terminal;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 终端
 *
 * @author SolarisNeko
 * Date on 2023-02-01
 */
@Slf4j
public class Terminal233 {

    /**
     * @param command 单个命令
     * @return 响应的每一行
     */
    public static List<String> executeCommandSync(String command) {
        List<String> commandCallbackList;
        try {
            log.info("[Terminal] execute start. command = {}", command);
            Process exec = Runtime.getRuntime().exec(command);
            log.info("[Terminal] execute done. command = {}", command);
            // block
            InputStream inputStream = exec.getInputStream();
            commandCallbackList = Optional.of(IOUtils.readLines(inputStream, StandardCharsets.UTF_8))
                    .orElse(new ArrayList<>());
        } catch (IOException e) {
            log.error("terminal execute command sync error. command = {}", command, e);
            return new ArrayList<>();
        }
        return commandCallbackList;
    }

    public static String executeCommandToOneLineSync(String command) {
        return Optional.ofNullable(executeCommandSync(command))
                .orElse(new ArrayList<>())
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }


    public static void executeCommandAsync(String command) {
        try {
            log.info("[Terminal] execute async start. command = {}", command);
            Process exec = Runtime.getRuntime().exec(command);
            log.info("[Terminal] execute async done. command = {}", command);
        } catch (IOException e) {
            log.error("terminal execute command async error. command = {}", command, e);
        }
    }

}
