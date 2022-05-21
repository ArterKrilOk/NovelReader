package space.pixelsg.novelreader.data.sources.remote;


import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import space.pixelsg.novelreader.data.models.ListNovel;
import space.pixelsg.novelreader.data.models.Novel;
import space.pixelsg.novelreader.data.models.exceptions.ParserNotFoundException;
import space.pixelsg.novelreader.data.sources.DataSource;

public class RemoteDataSource implements DataSource {
    private final List<NovelsParser> novelsParsers;

    public RemoteDataSource(List<NovelsParser> novelsParsers) {
        this.novelsParsers = novelsParsers;
    }

    @Override
    public Observable<List<ListNovel>> getListNovels(int page) {
        return Observable.merge(
                novelsParsers.stream()
                        .map(novelsParser -> novelsParser.getListNovels(page))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Observable<Novel> getNovel(String id) {
        for(NovelsParser parser : novelsParsers)
            if(parser.name().equals(NovelsParser.nameFromId(id)))
                return parser.getNovel(id);

        return Observable.error(new ParserNotFoundException());
    }
}
