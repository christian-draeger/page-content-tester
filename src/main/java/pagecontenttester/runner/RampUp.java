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
            "\n   .----.     .-----.    .----.     .----.",
            "  /      \\   /  .-.  \\  /      \\   /      \\",
            " |  .´`.  ; (___) ;  ; |  .´`.  ; |  ,´`.  ;",
            " |  |  |  |    .'`   | |  |  (__) |  |  |  |",
            " |  |  |  |  /  .'|  | |  |   __  |  |  |  |",
            " |  |  ;  | ;  ¦  ;  | |  |  (  ) |  |  |  |",
            " |  `-´   ; |  `-´   |  \\  `-'  ; |  :  ;  |",
            " |  \\___.'   `.___.'__)  `.___.'   \\  `´   ;",
            " :  ;  ___╔═════════════════════╗___`.___.'",
            " (__)  \\  ╠≡═- version " + version + " -═≡╣  /",
            "       /__╚═════════════════════╝__\\\n");

    static {
        for (String line: ascii) {
            log.info(line);
        }
    }

}
