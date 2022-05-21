package space.pixelsg.novelreader.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import io.reactivex.disposables.CompositeDisposable;
import space.pixelsg.novelreader.R;
import space.pixelsg.novelreader.data.models.Novel;
import space.pixelsg.novelreader.databinding.ActivityNovelBinding;
import space.pixelsg.novelreader.vm.NovelActivityViewModel;

public class NovelActivity extends AppCompatActivity {

    private ActivityNovelBinding binding;

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_novel);
        binding.setLifecycleOwner(this);

        NovelActivityViewModel viewModel = new ViewModelProvider(this).get(NovelActivityViewModel.class);

        disposable = new CompositeDisposable();
        disposable.addAll(
            viewModel.showCenterProgress.subscribe(loading -> {
                binding.progressCircular.setVisibility(loading? View.VISIBLE : View.GONE);
                binding.nestedScrollView.setVisibility(loading? View.GONE : View.VISIBLE);
            }),
            viewModel.getNovel(getIntent().getStringExtra("novel_id")).subscribe(this::setNovel, this::onError)
        );
    }

    private void onError(Throwable t) {
        binding.textViewError.setVisibility(View.VISIBLE);
        binding.textViewError.setText(t.getMessage());
    }

    private void setNovel(Novel novel) {
        binding.textViewError.setVisibility(View.GONE);
        binding.novelTopLayout.posterView.loadFromUrl(novel.posters.get(0));
        binding.novelTopLayout.textViewId.setText(novel.id);
        binding.novelTopLayout.textViewName.setText(novel.name);
        binding.textViewDescr.setText(novel.description);
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}