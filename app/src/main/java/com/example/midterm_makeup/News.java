package com.example.midterm_makeup;

import java.io.Serializable;

public class News implements Serializable {
    String title, author, description, publishedAt, pictureUrl, url;

    public News(String title, String author, String description, String publishedAt, String pictureUrl, String url) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.publishedAt = publishedAt;
        this.pictureUrl = pictureUrl;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
