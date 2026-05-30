package tests;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class LogTest {

    @Test
    public void test() {
//        уровни логирования
        log.error("error");
        log.warn("warn");
        log.info("info"); // в автотестировании он в основном используется
        log.debug("debug");
        log.trace("trace");
        log.fatal("fatal");
    }
}
