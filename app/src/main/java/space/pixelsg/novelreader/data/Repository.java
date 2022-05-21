package space.pixelsg.novelreader.data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import space.pixelsg.novelreader.data.models.ListNovel;
import space.pixelsg.novelreader.data.models.Novel;
import space.pixelsg.novelreader.data.sources.local.RamCacheDataSource;
import space.pixelsg.novelreader.data.sources.remote.RemoteDataSource;

public class Repository {
    private final RemoteDataSource remoteDataSource;
    private final RamCacheDataSource cacheDataSource;

    public Repository(RemoteDataSource remoteDataSource, RamCacheDataSource cacheDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.cacheDataSource = cacheDataSource;
    }

    public Observable<List<ListNovel>> getListNovels(int page) {
        return remoteDataSource
                .getListNovels(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Novel> getNovel(String id) {
        return Observable
                .concat(cacheDataSource.getNovel(id), getNovelFromRemote(id))
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable();
    }

    private Observable<Novel> getNovelFromRemote(String id) {
        return remoteDataSource.getNovel(id).doOnNext(cacheDataSource::saveToCache);
    }
}
