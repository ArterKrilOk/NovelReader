package space.pixelsg.novelreader.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.reactivex.disposables.CompositeDisposable;
import space.pixelsg.novelreader.R;
import space.pixelsg.novelreader.data.models.ListNovel;
import space.pixelsg.novelreader.databinding.ActivityMainBinding;
import space.pixelsg.novelreader.ui.adapters.ListNovelViewAdapter;
import space.pixelsg.novelreader.vm.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        disposable = new CompositeDisposable();

        ListNovelViewAdapter adapter = new ListNovelViewAdapter((v, novel) -> openNovel(novel));
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(adapter);
        adapter.assignRecyclerView(binding.recyclerView);

        adapter.setOnLoadMoreListener(() -> disposable.add(
                viewModel.getNovels().subscribe(adapter::addItems, this::onError)
        ));

        disposable.addAll(
                viewModel.getNovels().subscribe(adapter::addItems),
                viewModel.loading.subscribe(adapter::setLoading, this::onError),
                viewModel.showCenterProgress.subscribe(loading -> binding.progressCircular.setVisibility(loading ? View.VISIBLE : View.GONE))
        );
    }

    private void onError(Throwable t) {
        binding.textViewError.setVisibility(View.VISIBLE);
        binding.textViewError.setText(t.getMessage());
    }

    private void openNovel(ListNovel novel) {
        Intent intent = new Intent(MainActivity.this, NovelActivity.class);
        intent.putExtra("novel_id", novel.id);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}