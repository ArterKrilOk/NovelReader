package space.pixelsg.novelreader.data.models;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ListNovel {
    public String id;
    public String name;
    public String banner;
    public String url;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListNovel novel = (ListNovel) o;
        return Objects.equals(id, novel.id) && Objects.equals(name, novel.name) && Objects.equals(banner, novel.banner) && Objects.equals(url, novel.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, banner, url);
    }

    @NonNull
    @Override
    public String toString() {
        return "ListNovel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", banner='" + banner + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
