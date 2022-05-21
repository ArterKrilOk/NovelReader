package space.pixelsg.novelreader.data.sources.remote.parsers;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import space.pixelsg.novelreader.data.sources.remote.NovelsParser;

@Module
public class ParsersModule {

    @Singleton
    @Provides
    AlexZNovelDataSource provideAlexZNovelDataSource() {
        return new AlexZNovelDataSource();
    }

    @Singleton
    @Provides
    JapitNovelDataSource provideJapitNovelDataSource() {
        return new JapitNovelDataSource();
    }

    @Singleton
    @Provides
    KgNovelDataSource provideKgNovelDataSource() {
        return new KgNovelDataSource();
    }

    @Singleton
    @Provides
    NovelTlDataSource provideNovelTlDataSource() {
        return new NovelTlDataSource();
    }

    @Singleton
    @Provides
    List<NovelsParser> provideNovelParsers(
            AlexZNovelDataSource alexZNovelDataSource,
            JapitNovelDataSource japitNovelDataSource,
            KgNovelDataSource kgNovelDataSource,
            NovelTlDataSource novelTlDataSource) {
        return Arrays.asList(alexZNovelDataSource, japitNovelDataSource, kgNovelDataSource, novelTlDataSource);
    }
}
