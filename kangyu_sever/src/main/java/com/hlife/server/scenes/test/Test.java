package com.hlife.server.scenes.test;

import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Slf4j
public class Test {
    public static void main(String[] args) throws ScriptException {
        ScriptEngine js = new ScriptEngineManager().getEngineByName("js");
        log.debug("boolean==>{}",js.eval("X=0.0;Y=0.0;X≧Y".replaceAll("[≧≥]", ">=")));
        log.debug("boolean2==>{}",js.eval("X=0.0;Y=0.0;X＜Y".replaceAll("[＜]", "<")));
        /*String[] arr = {"M", "K", "A", "B", "Z", "X"};
        Arrays.sort(arr, (s1, s2) -> s2.compareTo(s1));
        log.debug("arr => {}", arr);
        Arrays.sort(arr);
        log.debug("arr2 => {}", arr);*/
        /*log.debug("ab==>{}", Arrays.toString("ab".split(REG_VERTICAL_LINE)));
        log.debug("ab2==>{}", Arrays.toString("a|b".split(REG_VERTICAL_LINE)));
        log.debug("ab3==>{}", Arrays.toString("a_b".split(UNDER_lINE)));*/
    }
}
