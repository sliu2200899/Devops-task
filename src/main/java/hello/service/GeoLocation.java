package hello.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class GeoLocation {
    private String myHostIp;

    private static final String GEO_IP_URL = "http://ip-api.com/json/";
    private static final String HOST_IP = "http://ipv4bot.whatismyipaddress.com";

    private Client client = ClientBuilder.newClient();

    public GeoLocation() {
        myHostIp = getHostIP();
    }

    public LocationResponse getJsonLocation(String myHostIp) {
        return client
                .target(GEO_IP_URL + myHostIp)
                .request(MediaType.APPLICATION_JSON)
                .get(LocationResponse.class);
    }

    public String getHostIP() {
        return client
                .target(HOST_IP)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class);
    }

    public Coordinate queryLocation() {
        if (myHostIp == null || myHostIp.length() == 0) return new Coordinate();

        LocationResponse lr = getJsonLocation(myHostIp);
        return new Coordinate(lr.getLat(), lr.getLon());
    }

    public Coordinate queryLocation(String ip) {
        if (ip == null || ip.length() == 0) return new Coordinate();

        LocationResponse lr = getJsonLocation(ip);
        return new Coordinate(lr.getLat(), lr.getLon());
    }

    public String queryHostIP() {
         return myHostIp;
    }
}
