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
            System.out.println(ansi().fgBrightGreen().bold().a("\u2705 SUCCESS\t\t: all " + result.getRunCount() + " tests pass").reset());
        } else {
            System.out.println(ansi().fgRed().bold().a("\uD83D\uDED1 DAMN IT\t\t: some tests failed").reset());
        }
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        System.out.println("\u26d4 " + ansi().fgRed().bold().a("failing test\t\t: ").reset() + failure.getDescription().getDisplayName());
    }

    public void testIgnored(Description description) throws Exception {
        System.out.println("\u23ed " + ansi().fgBrightBlack().bold().a("skipped test\t\t: ").reset() + description.getDisplayName());
    }

    @Override
    public void testFinished(Description description) {
        System.out.println("\uD83C\uDFC1 " + ansi().fgBrightCyan().bold().a("finished test\t: ").reset() + description.getDisplayName());
    }

    private Ansi.Color paco = CYAN;
    private Ansi.Color banner = MAGENTA;
    private Ansi.Color version = GREEN;

    private List<String> ascii = Arrays.asList(ansi().bold().fg(paco) +
            "\n   .----.     .-----.    .----.     .----.",
            "  /      \\   /  .-.  \\  /      \\   /      \\",
            " |  .´`.  ; (___) ;  ; |  .´`.  ; |  ,´`.  ;",
            " |  |  |  |    .'`   | |  |  (__) |  |  |  |",
            " |  |  |  |  /  .'|  | |  |   __  |  |  |  |",
            " |  |  ;  | ;  ¦  ;  | |  |  (  ) |  |  |  |",
            " |  `-´   ; |  `-´   |  \\  `-'  ; |  :  ;  |",
            " |  \\___.'   `.___.'__)  `.___.'   \\  `´   ;",
            " :  ;" + ansi().boldOff().fg(banner) + "  ___╔═════════════════════╗___" + ansi().bold().fg(paco) + "`.___.'",
            " (__)" + ansi().boldOff().fg(banner) + "  \\  ╠≡═- " + ansi().fg(version).a("version " + pacoVersion) + ansi().fg(banner) + " -═≡╣  /",
            "       /__╚═════════════════════╝__\\\n" + ansi().reset());

    private void printAsciiArt() {
        if (config.isPacoAsciiActive()) {
            for (String line: ascii) {
                System.out.println(line);
            }
        }
    }

}
