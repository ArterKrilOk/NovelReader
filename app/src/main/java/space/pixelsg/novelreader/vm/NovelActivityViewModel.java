package space.pixelsg.novelreader.vm;


import android.app.Application;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import space.pixelsg.novelreader.data.models.Novel;

public class NovelActivityViewModel extends AppViewModel {

    public NovelActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public final Subject<Boolean> showCenterProgress = BehaviorSubject.createDefault(false);

    public Observable<Novel> getNovel(String id) {
        return repository
                .getNovel(id)
                .doOnSubscribe(disposable -> showCenterProgress.onNext(true))
                .doOnNext(listNovels -> showCenterProgress.onNext(false))
                .doOnComplete(() -> showCenterProgress.onNext(false))
                .doOnError(throwable -> showCenterProgress.onNext(false));
    }


}
