package space.pixelsg.novelreader.vm;


import android.app.Application;

import androidx.annotation.NonNull;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import space.pixelsg.novelreader.data.models.ListNovel;

public class MainActivityViewModel extends AppViewModel {

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public int page = 1;
    public final Subject<Boolean> showCenterProgress = BehaviorSubject.createDefault(false);
    public final Subject<Boolean> loading = BehaviorSubject.createDefault(false);

    public Observable<List<ListNovel>> getNovels() {
        System.out.println("Loading: " + page);

        return repository
                .getListNovels(page)
                .doOnSubscribe(disposable -> {
                    showCenterProgress.onNext(page == 1);
                    loading.onNext(true);
                })
                .doOnNext(listNovels -> showCenterProgress.onNext(false))
                .doOnComplete(() -> {
                    showCenterProgress.onNext(false);
                    loading.onNext(false);
                    page++;
                })
                .doOnError(throwable -> {
                    showCenterProgress.onNext(false);
                    loading.onNext(false);
                    page = 1;
                });
    }


}
