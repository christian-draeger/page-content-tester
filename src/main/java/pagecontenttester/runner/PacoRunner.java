package pagecontenttester.runner;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import lombok.SneakyThrows;

public class PacoRunner extends BlockJUnit4ClassRunner {

    public PacoRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    @SneakyThrows
    public void run(RunNotifier notifier) {
        notifier.addListener(new TestListener());
        notifier.fireTestRunStarted(getDescription());
    }
}
