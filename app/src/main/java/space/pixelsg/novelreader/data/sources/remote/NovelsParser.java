package space.pixelsg.novelreader.data.sources.remote;

import space.pixelsg.novelreader.data.sources.DataSource;

public abstract class NovelsParser implements DataSource {
    public abstract String name();

    protected String idFromTag(String tag) {
        return name() + "_" + tag;
    }

    protected String tagFromId(String id) {
        return id.split("_")[1];
    }

    public static String nameFromId(String id) {
        return id.split("_")[0];
    }
}
