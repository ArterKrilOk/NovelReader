package space.pixelsg.novelreader.data.sources.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import space.pixelsg.novelreader.data.models.ListNovel;
import space.pixelsg.novelreader.data.models.Novel;
import space.pixelsg.novelreader.data.sources.DataSource;

public class RamCacheDataSource implements DataSource {

    private final Map<String, Novel> novelsCache;

    public RamCacheDataSource() {
        novelsCache = new HashMap<>();
    }

    public void saveToCache(Novel novel) {
        novelsCache.put(novel.id, novel);
    }

    @Override
    public Observable<Novel> getNovel(String id) {
        return Observable.create(e -> {
            if(e.isDisposed())
                return;

            if(novelsCache.containsKey(id))
                e.onNext(novelsCache.get(id));
            e.onComplete();
        });
    }

    @Override
    public Observable<List<ListNovel>> getListNovels(int page) {
        return null;
    }
}
