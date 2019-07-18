package com.hlife.form_designer.test.test;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;

@Slf4j
public class Test {

    public static void main(String[] args) {
         /*ScriptEngine js = new ScriptEngineManager().getEngineByName("js");
        System.out.println(js.eval("x='>=80';x=='>=80'"));*/
        String[] arr = {"M", "K", "A", "B", "Z", "X"};
        Arrays.sort(arr);
        log.info("arr0 => {}", Arrays.toString(arr));
        Arrays.sort(arr, Comparator.reverseOrder());
        log.info("arr => {}", Arrays.toString(arr));
        Arrays.sort(arr, Comparator.naturalOrder());
        log.info("arr2 => {}", Arrays.toString(arr));
    }
}
