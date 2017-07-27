package pagecontenttester.runner;

import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.MAGENTA;
import static org.fusesource.jansi.Ansi.ansi;

import java.util.Arrays;
import java.util.List;

import org.fusesource.jansi.Ansi;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import lombok.extern.slf4j.Slf4j;
import pagecontenttester.configurations.Config;

@Slf4j
public class TestListener extends RunListener {

    protected static Config config = new Config();
    static String pacoVersion = config.getPacoVersion();

    @Override
    public void testRunStarted(Description description) throws Exception {
        printAsciiArt();
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        if (result.wasSuccessful()) {
            System.out.println(ansi().fgGreen().bold().a("\u2705 SUCCESS\t: all " + result.getRunCount() + " tests pass").reset());
        } else {
            System.out.println(ansi().fgRed().bold().a("\uD83D\uDED1 DAMN IT\t: some tests failed").reset());
        }
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        System.out.println("\u26d4 " + ansi().fgRed().bold().a("failing test\t: ").reset() + failure.getDescription().getDisplayName());
    }

    public void testIgnored(Description description) throws Exception {
        System.out.println("\u23ed " + ansi().fgBrightBlack().bold().a("skipped test\t: ").reset() + description.getDisplayName());
    }

    @Override
    public void testFinished(Description description) {
        System.out.println("\uD83C\uDFC1 " + ansi().fgBrightCyan().bold().a("finished test\t: ").reset() + description.getDisplayName());
    }

    private Ansi.Color paco = CYAN;
    private Ansi.Color banner = MAGENTA;
    private Ansi.Color version = GREEN;

    private List<Object> ascii = Arrays.asList("\n",
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
                    + ansi().fg(version).a("version " + pacoVersion).reset()
                    + ansi().fg(banner).a(" -═≡╣  /").reset(),
            ansi().fg(banner).a("            /__╚═════════════════════╝__\\\n").reset());

    private void printAsciiArt() {
        if (config.isPacoAsciiActive()) {
            for (Object line: ascii) {
                System.out.println(line);
            }
        }
    }

}
