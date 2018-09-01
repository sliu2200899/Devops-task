package hello.http;

import hello.service.GeoLocation;
import hello.service.Coordinate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @RequestMapping(value= "/nxchallenge/astarisborn",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public String sayHello() {

        GeoLocation gl = new GeoLocation();
        String ip = gl.queryHostIP();
        String hostIp = "\"myHostIP\"" + ":" + "\"" + ip + "\"";

        Coordinate c = gl.queryLocation(ip);
        String latitude = "\"latitude\"" + ":" + "\"" + c.getLat() + "\"";
        String longitude = "\"longitude\"" + ":" + "\"" + c.getLon() + "\"";

        return "\"response\"" + ":"+ "\"Hello World\"" + "\n\n" + hostIp + "\n" + latitude + "\n" + longitude;
    }

    @RequestMapping("/healthz")
    public String health() {
        return "running...";
    }
}

