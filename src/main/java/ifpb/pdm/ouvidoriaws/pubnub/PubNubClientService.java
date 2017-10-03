/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pdm.ouvidoriaws.pubnub;

//import com.google.gson.JsonObject;
//import com.pubnub.api.PNConfiguration;
//import com.pubnub.api.PubNub;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import ifpb.pdm.ouvidoriaws.entities.Ticket;
import javax.ejb.Stateless;

/**
 *
 * @author natarajan
 */

@Stateless
public class PubNubClientService {
    
    private static final String PUBNUB_SUBTOKEN = "sub-c-a5968858-a4be-11e7-b003-222aeb593f38";
    private static final String PUBNUB_PUBTOKEN = "pub-c-b7e68860-0cd3-4f5f-8fa2-8d049b8b5979";

    private static PubNub pubnub = null;
    private static PubNubClientService instance = null;
    
    private static void init() {
        //
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(PUBNUB_SUBTOKEN);
        pnConfiguration.setPublishKey(PUBNUB_PUBTOKEN);
        pnConfiguration.setSecure(false);
        //
        pubnub = new PubNub(pnConfiguration);
        instance = new PubNubClientService();
    }
    
    public static PubNubClientService instance(){
        return instance;
    }
    
    
    public void sendMessageCreatedTicket() {
        //
//        JsonObject json = new JsonObject();
//        json.addProperty("type", "ticket");
//        json.addProperty("msg", ticket.getId());
//        //
//        pubnub.publish().channel("auditor").message(json);
    }
    
}
