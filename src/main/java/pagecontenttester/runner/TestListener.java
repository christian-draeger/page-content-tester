package pagecontenttester.runner;

import static org.fusesource.jansi.Ansi.ansi;

import java.util.Date;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TestListener extends RunListener {

    private long startTime;
    private long endTime;

    @Override
    public void testRunStarted(Description description) throws Exception {
        startTime = new Date().getTime();
        RampUp.printAsciiArt();
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        if (result.wasSuccessful()) {
            System.out.println(ansi().fgGreen().bold().a("\u2705 SUCCESS\t: all " + result.getRunCount() + " executed tests passed").reset());
        } else {
            System.out.println(ansi().fgRed().bold().a("\uD83D\uDED1 DAMN IT\t: " + result.getFailureCount() + " of " + result.getRunCount() + " executed tests failed").reset());
        }

        endTime = new Date().getTime();
        System.out.println(ansi().fgBrightBlack().bold().a("\uD83D\uDD57 TIME\t\t: test run took " + getElapsedTime(startTime, endTime) + " (without maven ramp up)").reset());
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        System.out.println("\u26d4 " + ansi().fgRed().bold().a("failing test: ").reset() + failure.getDescription().getDisplayName());
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        System.out.println("\u23ed " + ansi().fgBrightBlack().bold().a("skipped test  : ").reset() + description.getDisplayName());
    }

    @Override
    public void testFinished(Description description) {
        System.out.println("\uD83C\uDFC1 " + ansi().fgBrightCyan().bold().a("finished test: ").reset() + description.getDisplayName());
    }

    private String getElapsedTime(long startTime, long endTime) {
        long elapsed = (endTime-startTime);
        if (elapsed < 1000) {
            return elapsed + " milliseconds";
        }
        return elapsed / 1000 + " seconds";
    }

}
