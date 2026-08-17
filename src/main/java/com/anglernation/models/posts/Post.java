package com.anglernation.models.posts;

import java.util.ArrayList;
import java.util.List;

public class Post {

    private String title;
    private String description;
    private List<String> comments = new ArrayList<>();

    public Post(String title, String description, List<String> comments) {
        this.title = title;
        this.description = description;
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", comments=" + comments +
                '}';
    }
}
