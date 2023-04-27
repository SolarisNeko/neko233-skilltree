package com.neko233.skilltree.commons.reactive;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 * @author SolarisNeko
 * Date on 2023-04-27
 */
public class ReactiveDataTest {

    MockDataDto original = MockDataDto.createTest1();
    MockDataDto modified = MockDataDto.createTest2();


    @Test
    public void set_and_listener_callback() {
        AtomicReference<String> testCallback = new AtomicReference<>("");

        ReactiveData<MockDataDto> reactiveData = new ReactiveData<>(original);
        reactiveData.addListener(t -> testCallback.set("modified"));

        reactiveData.set(modified);
        Assert.assertEquals("modified", testCallback.get());
    }

    @Test
    public void get() {
        ReactiveData<MockDataDto> reactiveData = new ReactiveData<>(original);

        reactiveData.set(modified);
        Assert.assertEquals(modified.getName(), reactiveData.get().getName());
    }


    @Test
    public void removeListener() {
        AtomicReference<String> testCallback = new AtomicReference<>("");

        ReactiveData<MockDataDto> reactiveData = new ReactiveData<>(original);
        Consumer<MockDataDto> consumer = t -> testCallback.set("modified");
        reactiveData.addListener(consumer);
        reactiveData.removeListener(consumer);

        reactiveData.set(modified);

        Assert.assertEquals("", testCallback.get());
    }

    @Test
    public void clearListeners() {
        AtomicReference<String> testCallback = new AtomicReference<>("");

        ReactiveData<MockDataDto> reactiveData = new ReactiveData<>(original);
        Consumer<MockDataDto> consumer = t -> testCallback.set("modified");
        reactiveData.addListener(consumer);
        reactiveData.clearAllListeners();

        reactiveData.set(modified);

        Assert.assertEquals("", testCallback.get());
    }
}