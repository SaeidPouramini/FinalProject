package ca.uottawa.finalproject.ticketmaster.models;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Event Model to store the data from the api request
 * @author Sundus
 */
public class Event {

    private Long id;
    private String name;
    private String startingDate;
    private String priceRange;
    private String url;
    private String imageUrl;

    public Event(){};
    public Event(Long id, String name, String priceRange, String startingDate, String url, String imageUrl) {
        this.id = id;
        this.name = name;
        this.startingDate = startingDate;
        this.priceRange = priceRange;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Event parseEventFromBundle(Bundle bundle) {
        Event event = new Event();
        event.setName(bundle.getString("name"));
        event.setPriceRange(bundle.getString("priceRange"));
        event.setStartingDate(bundle.getString("startDate"));
        event.setUrl(bundle.getString("url"));
        event.setImageUrl(bundle.getString("imageUrl"));
        event.setId(bundle.getLong("id"));
        return event;
    }

    public static Bundle exportEventToBundle(Event event) {
        Bundle bundle = new Bundle();
        bundle.putString("name", event.getName());
        bundle.putString("priceRange", event.getPriceRange());
        bundle.putString("startDate", event.getStartingDate());
        bundle.putString("url", event.getUrl());
        bundle.putString("imageUrl", event.getImageUrl());
        if(event.getId() != null) {
            bundle.putLong("id", event.getId());
        }
        return bundle;
    }
    public static Event parseEvent(JSONObject jsonObject) {
        try {
            Event event = new Event();
            event.setName(jsonObject.optString("name", "N/A"));
            event.setUrl(jsonObject.optString("url", "N/A"));

            JSONObject datesJsonObject = jsonObject.getJSONObject("dates");
            String dateTime = datesJsonObject.getJSONObject("start").getString("dateTime");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = simpleDateFormat.parse(dateTime);
            event.setStartingDate(simpleDateFormat.format(startDate));
            JSONArray priceRanges = jsonObject.getJSONArray("priceRanges");
            JSONObject price = priceRanges.getJSONObject(0);
            String min = price.getString("min");
            String max = price.getString("max");
            String currency = price.getString("currency");

            event.setPriceRange(min + " - " + max + " " + currency);

            JSONArray jsonArray = jsonObject.getJSONArray("images");
            JSONObject imageJsonObject = jsonArray.getJSONObject(0);
            event.setImageUrl(imageJsonObject.getString("url"));

            return event;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
