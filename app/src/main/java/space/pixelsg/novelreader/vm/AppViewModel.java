package space.pixelsg.novelreader.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import javax.inject.Inject;

import space.pixelsg.novelreader.App;
import space.pixelsg.novelreader.data.Repository;

public class AppViewModel extends AndroidViewModel {

    @Inject public Repository repository;

    public AppViewModel(@NonNull Application application) {
        super(application);

        App.fromApplication(application).getAppComponent().injectIntoAppViewModel(this);
    }
}
