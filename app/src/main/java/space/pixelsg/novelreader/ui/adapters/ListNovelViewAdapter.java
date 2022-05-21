package space.pixelsg.novelreader.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import space.pixelsg.novelreader.R;
import space.pixelsg.novelreader.data.models.ListNovel;
import space.pixelsg.novelreader.databinding.ListNovelItemViewBinding;

public class ListNovelViewAdapter extends RecyclerView.Adapter<ListNovelViewAdapter.ListModelViewHolder> {

    public static class ListModelViewHolder extends RecyclerView.ViewHolder {

        private final ListNovelItemViewBinding binding;

        public ListModelViewHolder(ListNovelItemViewBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bindNovel(ListNovel novel, OnNovelClickListener onNovelClickListener) {
            binding.textViewName.setText(novel.name);
            binding.imageView.loadFromUrl(novel.banner);
            binding.textViewId.setText(novel.id);
            binding.getRoot().setOnClickListener(v -> onNovelClickListener.onClick(v, novel));
        }
    }

    @NonNull
    private final OnNovelClickListener onNovelClickListener;
    private OnLoadMoreListener onLoadMoreListener;
    private final List<ListNovel> listNovels;
    private boolean loading;


    public ListNovelViewAdapter(@NonNull OnNovelClickListener onNovelClickListener) {
        listNovels = new ArrayList<>();
        this.onNovelClickListener = onNovelClickListener;
    }

    @NonNull
    @Override
    public ListModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListNovelItemViewBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_novel_item_view,
                parent,
                false
        );
        return new ListModelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListModelViewHolder holder, int position) {
        holder.bindNovel(listNovels.get(position), onNovelClickListener);
    }

    @Override
    public int getItemCount() {
        return listNovels.size();
    }

    public void assignRecyclerView(RecyclerView recyclerView){
        final GridLayoutManager glm = (GridLayoutManager)recyclerView.getLayoutManager();
        if(glm != null) recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = glm.findLastVisibleItemPosition();
                if(!loading && listNovels.size() <= (lastVisibleItem + 8)){
                    if(onLoadMoreListener != null){
                        onLoadMoreListener.onLoadMore();
                    }
                    loading = true;
                }
            }
        });
    }

    public void clear() {
        int size = listNovels.size();
        listNovels.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addItems(List<ListNovel> newListNovels) {
        newListNovels.addAll(0, listNovels);

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return listNovels.size();
            }

            @Override
            public int getNewListSize() {
                return newListNovels.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return listNovels.get(oldItemPosition).id.equals(newListNovels.get(newItemPosition).id);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return listNovels.get(oldItemPosition).equals(newListNovels.get(newItemPosition));
            }
        });

        listNovels.clear();
        listNovels.addAll(newListNovels);

        result.dispatchUpdatesTo(this);
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnNovelClickListener {
        void onClick(View v, ListNovel novel);
    }
}
