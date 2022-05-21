package space.pixelsg.novelreader.data.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Novel {
    public String id;
    public String name;
    public List<String> posters;
    public String description;
    public String url;

    public Novel() {
        posters = new ArrayList<>();
    }

    @NonNull
    @Override
    public String toString() {
        return "Novel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", posters=" + posters +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
