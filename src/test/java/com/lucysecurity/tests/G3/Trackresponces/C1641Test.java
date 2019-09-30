package com.lucysecurity.tests.G3.Trackresponces;

import com.lucysecurity.DriverBase;
import org.junit.Test;

public class C1641Test extends DriverBase {

    @Test
    public void firstTest() {
        gagarinMail = "gagarin3@phishing.services";
        campName = "TrackMailAsPhish";
        domain = "lucysecurity.host";
        recipGroup = gagarinMail;
        scenarioTemplate = "access online";


        driver.get("https://update.lucysecurity.host");




    }


}
