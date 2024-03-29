package util;

import play.test.FakeApplication;

import static play.test.Helpers.*;

/**
 * Created by fke on 12.05.2016.
 */
public class FakeApplicationUtil {

    public static FakeApplication startFakeApplication() {
        FakeApplication fakeApplication = fakeApplication();
        start(fakeApplication);
        return fakeApplication;
    }

    public static void stopFakeApplication(FakeApplication fakeApplication) {
        stop(fakeApplication);
    }
}
