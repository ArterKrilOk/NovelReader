package space.pixelsg.novelreader.data.sources.remote.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import space.pixelsg.novelreader.data.models.ListNovel;
import space.pixelsg.novelreader.data.models.Novel;
import space.pixelsg.novelreader.data.sources.remote.NovelsParser;

public class KgNovelDataSource extends NovelsParser {
    private static final String URL = "https://kg.novel.tl";

    private String getNovelsListUrl(int page) {
        return URL + "/projects;page=" + page;
    }

    @Override
    public String name() {
        return "kg-novel";
    }

    @Override
    public Observable<List<ListNovel>> getListNovels(int page) {
        return Observable.create(e -> {
            if(e.isDisposed())
                return;

            Element element = Jsoup
                    .connect(getNovelsListUrl(page))
                    .get()
                    .body();

            List<ListNovel> listNovels = new ArrayList<>();

            for(Element nCont : element.select("div.projects-list__item")) {
                ListNovel novel = new ListNovel();
                novel.banner = URL + nCont.selectFirst("img.cover__image").attr("src");
                novel.name = nCont.selectFirst("div.cover__title").text();
                novel.url = URL + nCont.selectFirst("a.cover__link").attr("href");
                novel.id = idFromTag(nCont.selectFirst("a.cover__link").attr("href").split("/")[2]);

                listNovels.add(novel);
            }

            e.onNext(listNovels);
            e.onComplete();
        });
    }

    @Override
    public Observable<Novel> getNovel(String id) {
        return Observable.create(e -> {
            if(e.isDisposed())
                return;

            String novelTag = tagFromId(id);

            Element element = Jsoup
                    .connect(URL + "/r/" + novelTag)
                    .get()
                    .body();

            Novel novel = new Novel();
            novel.id = id;
            novel.url = URL + "/r/" + novelTag;
            novel.name = element.selectFirst("span.headline__text").text();
            novel.description = element.selectFirst("div.info__annotation").text();
            for(Element img : element.select("div.ngucarousel-items img.detail__image"))
                novel.posters.add(URL + img.attr("src"));

            e.onNext(novel);
            e.onComplete();
        });
    }
}
