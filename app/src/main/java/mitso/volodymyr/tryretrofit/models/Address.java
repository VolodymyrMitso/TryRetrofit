package mitso.volodymyr.tryretrofit.models;

// @Generated("org.jsonschema2pojo")

public class Address {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;

    /**
     *
     * @return
     * The street
     */
    public String getStreet() {
        return street;
    }

    /**
     *
     * @param street
     * The street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     *
     * @return
     * The suite
     */
    public String getSuite() {
        return suite;
    }

    /**
     *
     * @param suite
     * The suite
     */
    public void setSuite(String suite) {
        this.suite = suite;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     *
     * @param zipcode
     * The zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     *
     * @return
     * The geo
     */
    public Geo getGeo() {
        return geo;
    }

    /**
     *
     * @param geo
     * The geo
     */
    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", suite='" + suite + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", geo=" + geo +
                '}';
    }
}