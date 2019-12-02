package com.wonde.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "feed")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Feed {

    @Id
    private int id;
    private String title;
    private String link;
    private String description;
    private String author;
    private String published_date;
    private String category;

    public Feed() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublished_date() {
        return published_date;
    }
    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
