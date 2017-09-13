package pagecontenttester.runner;

import static org.fusesource.jansi.Ansi.ansi;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TestListener extends RunListener {

    @Override
    public void testRunStarted(Description description) throws Exception {
        RampUp.printAsciiArt();
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        if (result.wasSuccessful()) {
            System.out.println(ansi().fgGreen().bold().a("\u2705 SUCCESS\t: all " + result.getRunCount() + " executed tests pass").reset());
        } else {
            System.out.println(ansi().fgRed().bold().a("\uD83D\uDED1 DAMN IT\t: " + result.getFailureCount() + " of " + result.getRunCount() + " executed tests failed").reset());
        }
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        System.out.println("\u26d4 " + ansi().fgRed().bold().a("failing test: ").reset() + failure.getDescription().getDisplayName());
    }

    public void testIgnored(Description description) throws Exception {
        System.out.println("\u23ed " + ansi().fgBrightBlack().bold().a("skipped test  : ").reset() + description.getDisplayName());
    }

    @Override
    public void testFinished(Description description) {
        System.out.println("\uD83C\uDFC1 " + ansi().fgBrightCyan().bold().a("finished test: ").reset() + description.getDisplayName());
    }

}
