package paco.runner;

import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.MAGENTA;
import static org.fusesource.jansi.Ansi.ansi;

import java.util.Arrays;
import java.util.List;

import org.fusesource.jansi.Ansi;

import paco.configurations.GlobalConfig;


public class RampUp {

    protected static GlobalConfig globalConfig = new GlobalConfig();

    private static Ansi.Color paco = CYAN;
    private static Ansi.Color banner = MAGENTA;
    private static Ansi.Color version = GREEN;

    private static List<Object> ascii = Arrays.asList(
            ansi().bold().fg(paco).a("        .----.     .-----.    .----.     .----.").reset(),
            ansi().bold().fg(paco).a("       /      \\   /  .-.  \\  /      \\   /      \\").reset(),
            ansi().bold().fg(paco).a("      |  .´`.  ; (___) ;  ; |  .´`.  ; |  ,´`.  ;").reset(),
            ansi().bold().fg(paco).a("      |  |  |  |    .'`   | |  |  (__) |  |  |  |").reset(),
            ansi().bold().fg(paco).a("      |  |  |  |  /  .'|  | |  |   __  |  |  |  |").reset(),
            ansi().bold().fg(paco).a("      |  |  ;  | ;  ¦  ;  | |  |  (  ) |  |  |  |").reset(),
            ansi().bold().fg(paco).a("      |  `-´   ; |  `-´   |  \\  `-'  ; |  :  ;  |").reset(),
            ansi().bold().fg(paco).a("      |  \\___.'   `.___.'__)  `.___.'   \\  `´   ;").reset(),
            ansi().bold().fg(paco).a("      :  ;").reset()
                    +""+ ansi().fg(banner).a("  ___╔═════════════════════╗___").reset()
                    + ansi().bold().fg(paco).a("`.___.'").reset(),
            ansi().bold().fg(paco).a("      (__)").reset()
                    +""+ ansi().fg(banner).a("  \\  ╠≡═- ").reset()
                    + ansi().fg(version).a("version 1.7.4").reset()
                    + ansi().fg(banner).a(" -═≡╣  /").reset(),
            ansi().fg(banner).a("            /__╚═════════════════════╝__\\\n").reset());

    private RampUp() {
    }

    static void printAsciiArt() {
        if (globalConfig.isPacoAsciiActive()) {
            for (Object line: ascii) {
                System.out.println(line);
            }
        }
    }

}
