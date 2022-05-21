package space.pixelsg.novelreader;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

import space.pixelsg.novelreader.data.DataModule;
import space.pixelsg.novelreader.data.sources.remote.parsers.ParsersModule;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        DynamicColors.applyToActivitiesIfAvailable(this);

        appComponent = DaggerAppComponent
                .builder()
                .parsersModule(new ParsersModule())
                .dataModule(new DataModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static App fromApplication(Application application) {
        return (App) application;
    }
}
