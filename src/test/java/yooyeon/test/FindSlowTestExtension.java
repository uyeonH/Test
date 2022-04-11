package yooyeon.test;

import jdk.jfr.Threshold;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;


public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private long THRESHOLD;

    public FindSlowTestExtension(long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {

        Method requiredTestMethod = context.getRequiredTestMethod();
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);


        String testMethodName = requiredTestMethod.getName();
        ExtensionContext.Store store = getStore(context);

        Long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;
        if (duration > THRESHOLD && null == annotation) {
            System.out.printf("Please consider mark method [%s] with @SlowTest. \n", testMethodName);
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        String testMethodName = context.getRequiredTestMethod().getName();
        String testClassName = context.getRequiredTestClass().getName();
        return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
    }
}
