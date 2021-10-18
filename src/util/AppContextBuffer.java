package util;

import context.AppContext;

public final class AppContextBuffer {

    private static volatile AppContext appContext;

    public synchronized static AppContext getAppContext() {
        return appContext;
    }

    public synchronized static void setAppContext(AppContext appContext) {
        AppContextBuffer.appContext = appContext;
    }
}
