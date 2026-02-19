import static spark.Spark.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmartBinAutomation {

    // 1. Enter your Twilio Details
    public static final String ACCOUNT_SID = "ACxxxxxxxxxxxxxxxxxxxxxxxx";
    public static final String AUTH_TOKEN = "your_auth_token";
    public static final String TO_PHONE = "whatsapp:+917720891406"; 
    public static final String FROM_PHONE = "whatsapp:+917720891406"; 

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        port(8080); // Server runs on http://localhost:8080

        System.out.println("IoT Java Service Started...");

        // ESP8266 will call this URL when Level is high
        get("/trigger-alert", (req, res) -> {
            String level = req.queryParams("level");
            
            // Send the Automatic Message
            sendAlert(level);
            
            return "Java processed WhatsApp alert for level: " + level;
        });
    }

    public static void sendAlert(String level) {
        Message.creator(
            new PhoneNumber(TO_PHONE),
            new PhoneNumber(FROM_PHONE),
            "🚨 *JSPM JSCOE ALERT* 🚨\nSmart Bin Water Level is HIGH: " + level + "%"
        ).create();
        System.out.println("WhatsApp Alert Sent Successfully!");
    }
}