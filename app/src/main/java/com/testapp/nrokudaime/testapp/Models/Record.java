package com.testapp.nrokudaime.testapp.Models;

import android.graphics.Color;
import android.util.Log;

import java.util.Date;

public class Record
{
    private Long id;
    private String title;
    private String content;
    private String image_url;
    private Date start_date_formated;
    private Date end_date_formated;
    private String tag;
    private String organizer;
    private String place;
    private Long innerId;
    private String start_date_not_formated;
    private String end_date_not_formated;
    private String slug;
    private String start_date;
    private String end_date;
    private String site_id;
    private String short_title;
    private String short_content;
    private String expotype_name;

    public Record() {

    }

    public Record(Long id, String title, String content, String image_url, Date start_date_formated, Date end_date_formated, String tag, String organizer, String place) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image_url = image_url;
        this.start_date_formated = start_date_formated;
        this.end_date_formated = end_date_formated;
        this.tag = tag;
        this.organizer = organizer;
        this.place = place;
    }

    public Date getStart_date_formated() {
        return start_date_formated;
    }

    public void setStart_date_formated(Date start_date_formated) {
        this.start_date_formated = start_date_formated;
    }

    public Date getEnd_date_formated() {
        return end_date_formated;
    }

    public void setEnd_date_formated(Date end_date_formated) {
        this.end_date_formated = end_date_formated;
    }

    public Long getInnerId() {
        return innerId;
    }

    public void setInnerId(Long innerId) {
        this.innerId = innerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getStart_date_not_formated() {
        return start_date_not_formated;
    }

    public void setStart_date_not_formated(String start_date_not_formated) {
        this.start_date_not_formated = start_date_not_formated;
    }

    public String getEnd_date_not_formated() {
        return end_date_not_formated;
    }

    public void setEnd_date_not_formated(String end_date_not_formated) {
        this.end_date_not_formated = end_date_not_formated;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public String getShort_content() {
        return short_content;
    }

    public void setShort_content(String short_content) {
        this.short_content = short_content;
    }

    public String getExpotype_name() {
        return expotype_name;
    }

    public void setExpotype_name(String expotype_name) {
        this.expotype_name = expotype_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getColor() {
        if (this.tag != null) {
            switch (this.tag) {
                case "orange":
                    return Color.YELLOW;
                case "blue":
                    return Color.BLUE;
                case "green":
                    return Color.GREEN;
                default:
                    return Color.BLACK;
            }
        }
        return Color.BLACK;
    }

}
