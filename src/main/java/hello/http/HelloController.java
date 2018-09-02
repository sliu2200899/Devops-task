package hello.http;

import hello.service.GeoLocation;
import hello.service.Coordinate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/")
public class HelloController {


    private final String secret = "newtonx-challenge";

    @RequestMapping(value= "/nxchallenge/astarisborn",
                    method = RequestMethod.GET,
                    produces = "application/json")
    @ResponseBody
    public String query() {

        return "\"response\"" + ":"+ "\"Hello World\"";
    }

    @RequestMapping(value= "/nxchallenge/astarisborn/{token}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public String queryWithToken(@PathVariable("token") String token) {

            if (!token.equals(secret)) {
                return "the token is incorrect!!";
            }

            GeoLocation gl = new GeoLocation();
            String ip = gl.queryHostIP();
            String hostIp = "\"myHostIP\"" + ":" + "\"" + ip + "\"";

            Coordinate c = gl.queryLocation(ip);
            String latitude = "\"latitude\"" + ":" + "\"" + c.getLat() + "\"";
            String longitude = "\"longitude\"" + ":" + "\"" + c.getLon() + "\"";

            return "\"response\"" + ":"+ "\"Hello World\"" + "\n\n" + hostIp + "\n" + latitude + "\n" + longitude;
    }

    @RequestMapping("*")
    @ResponseBody
    public String fallbackMethod(){
        return "you're lost... please query ./nxchallenge/astarisborn";
    }

    @RequestMapping()
    @ResponseBody
    public String defaultMethod(){
        return "please query ./nxchallenge/astarisborn";
    }

    @RequestMapping("/healthz")
    public String health() {
        return "running...";
    }
}

