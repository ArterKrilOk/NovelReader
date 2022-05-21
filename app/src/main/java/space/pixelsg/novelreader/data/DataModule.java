package space.pixelsg.novelreader.data;


import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import space.pixelsg.novelreader.data.sources.local.RamCacheDataSource;
import space.pixelsg.novelreader.data.sources.remote.NovelsParser;
import space.pixelsg.novelreader.data.sources.remote.RemoteDataSource;

@Module
public class DataModule {
    @Singleton
    @Provides
    RemoteDataSource provideRemoteDataSource(List<NovelsParser> novelsParsers) {
        return new RemoteDataSource(novelsParsers);
    }

    @Singleton
    @Provides
    RamCacheDataSource provideRamCacheDataSource() {
        return new RamCacheDataSource();
    }

    @Singleton
    @Provides
    Repository provideRepository(RemoteDataSource remoteDataSource, RamCacheDataSource ramCacheDataSource) {
        return new Repository(remoteDataSource, ramCacheDataSource);
    }
}
