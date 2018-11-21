package com.demotape;

import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

//Router or Controller to direct the api requests to proper functions

@Controller
public class SubscribeController {

    @Autowired
    SubscribeService subscribeService;
    @Autowired
    SendMessege sendMessege;
    @Autowired
    TextBody textBody;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(){
        return "index";
    }


    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public ResponseEntity<Object> subscribe(@RequestBody ArtistModel artistModel){

        //Getting Artist Name and number from front end model
        String name = artistModel.getName();
        String number = artistModel.getNumber();

        Response response= new Response();

        //Getting Top tracks using Spotify API
        ArrayList<Track> list = subscribeService.getTracks(name);
        //Getting Artist Info
        Artist artistInfo = textBody.getArtistInfo();

        //Handling errors and null response
        if (list==null) {

            response.setError("Error! Artist Does not exist!");

        }else
            //Sending the messege using Twilio api
            if(sendMessege.send(list, name, number)==null){

            response.setArtistInfo(artistInfo);
            response.setError("Error in Sending Messege! Recheck the number");

        }else {
            response.setArtistInfo(artistInfo);
            response.setError("Successfully Subscribed to "+name);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
