package space.pixelsg.novelreader.data.sources;

import java.util.List;

import io.reactivex.Observable;
import space.pixelsg.novelreader.data.models.ListNovel;
import space.pixelsg.novelreader.data.models.Novel;

public interface DataSource {
    Observable<List<ListNovel>> getListNovels(int page);
    Observable<Novel> getNovel(String id);
}
