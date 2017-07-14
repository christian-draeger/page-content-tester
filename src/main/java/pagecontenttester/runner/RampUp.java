package pagecontenttester.runner;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import pagecontenttester.configurations.Config;

@Slf4j
class RampUp {

    protected static Config config = new Config();
    static String version = config.getPacoVersion();

    static List<String> ascii = Arrays.asList(
            "   .-..    .---.   .--.    .--.   ",
            "  /    \\  / .-, \\ /    \\  /    \\  ",
            " ' .-,  ;(__) ; ||  .-. ;|  .-. ; ",
            " | |  . |  .'`  ||  |(___) |  | | ",
            " | |  | | / .'| ||  |    | |  | | ",
            " | |  | || /  | ||  | ___| |  | | ",
            " | |  ' |; |  ; ||  '(   ) '  | | ",
            " | `-'  '' `-'  |'  `-' |'  `-' / ",
            " | \\__.' `.__.'_. `.__,'  `.__.'  ",
            " | | -=The-Page-Content-Tester=-   ",
            "(___)-=≡≡≡≡≡≡≡[v" + version + "]≡≡≡≡≡≡≡≡=- \n");

    static {
        for (String line: ascii) {
            log.info(line);
        }
    }

}
