package com.demotape;

import com.wrapper.spotify.model_objects.specification.Artist;

//Response model tobe sent to the front end
public class Response {
    private Artist artistInfo;
     private String error;

    public Artist getArtistInfo() {
        return artistInfo;
    }

    public void setArtistInfo(Artist artistInfo) {
        this.artistInfo = artistInfo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
