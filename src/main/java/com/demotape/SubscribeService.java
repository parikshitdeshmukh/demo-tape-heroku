package com.demotape;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.ArrayList;
import java.util.List;


@Service
public class SubscribeService {


    @Autowired
    TextBody textBody;

    public ArrayList<Track> getTracks(String name){

        return textBody.getTopTracks(name);

    }
}
