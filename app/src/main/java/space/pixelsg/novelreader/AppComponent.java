package space.pixelsg.novelreader;

import javax.inject.Singleton;

import dagger.Component;
import space.pixelsg.novelreader.data.DataModule;
import space.pixelsg.novelreader.data.Repository;
import space.pixelsg.novelreader.data.sources.remote.parsers.ParsersModule;
import space.pixelsg.novelreader.vm.AppViewModel;

@Singleton
@Component(modules = {ParsersModule.class, DataModule.class})
public interface AppComponent {
    Repository getRepository();

    void injectIntoAppViewModel(AppViewModel appViewModel);
}
