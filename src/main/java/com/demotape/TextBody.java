package com.demotape;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

//This service is responsible for getting top 10 tracks for the artist
@Service
public class TextBody {

    //Retrieving the singleton instance of the Spotify Authentication
    private static SpotifyApi spotifyApi= SpotifyAuth.getSpotifyApi();
    private Artist artistInfo;

    public Artist getArtistInfo() {
        return artistInfo;
    }

    public void setArtistInfo(Artist artistInfo) {
        this.artistInfo = artistInfo;
    }

    public ArrayList<Track> getTopTracks(String artistName) {

        ArrayList<Track> top10Tracks = new ArrayList<>();

        try {

        final GetArtistsTopTracksRequest getArtistsTopTracksRequest = spotifyApi
                .getArtistsTopTracks(
                        //Getting Artist ID from user provided name
                        getArtistId(artistName), CountryCode.US)
                .build();

        //Getting top tracks for the Artist ID and COuntry as US
            final Track[] tracks = getArtistsTopTracksRequest.execute();

            //Selecting top 10 tracks
            int i=0;
            for (Track t: tracks){
                System.out.println(t.getName()+" --" + t.getArtists());
                top10Tracks.add(t);

                if(i==10) break;
            }

            System.out.println("Length: " + tracks.length);

            return top10Tracks;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

//        return null;

    }

    //private method to get Artist ID
    private  String getArtistId(String name) {
        final SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(name)
                .market(CountryCode.US)
                .limit(1)
                .offset(0)
                .build();

        try {
             Paging<Artist> artistPaging = searchArtistsRequest.execute();

            System.out.println("Total: " + artistPaging.getTotal());

            for (Artist a: artistPaging.getItems()){
                System.out.println(a.getName());

            }

            if (artistPaging.getItems().length==0) return "0";
            else {
                this.setArtistInfo(artistPaging.getItems()[0]);
                return artistPaging.getItems()[0].getId();
            }


        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

//        return null;


    }


}
