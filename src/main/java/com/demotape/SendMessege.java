package com.demotape;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.wrapper.spotify.model_objects.specification.Track;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//This service is responsible for obtaining authorization for Twilio api access.
//After getting authentication, it builds a messege with From, To and Messege Body;
//Body: I have filtered top 10 tracks for the body

@Service
public class SendMessege {


    private static final String ACCOUNT_SID = "AC53cab5e7963ae5317827a1a804b5c44a";
    private static final String AUTH_TOKEN = "1dace23c5f739f1093363f7e474693c5";

    public  String send(ArrayList<Track> tracks, String name, String number) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        StringBuffer sb = new StringBuffer();

        for (Track t: tracks){
            sb.append("Track: "+ t.getName()+", Album: "+ t.getAlbum().getName()+"\n");
        }
        try {
            Message message = Message.creator(
                    new PhoneNumber(number),
                    new PhoneNumber("+15853269179"),
                    "Here are Top 10 songs of " + name + ": \n" + sb.toString()
                    ).create();

            System.out.println(message.getSid());
            return "Success";

        }catch (Exception e){
            return null;
        }


    }
}
