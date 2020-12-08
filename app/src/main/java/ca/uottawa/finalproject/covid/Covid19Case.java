package ca.uottawa.finalproject.covid;


/**
 * Class to hold one record from Json data fetched
 */
class Covid19Case {

    /**
     * Id of the record
     */
    private long id;
    /**
     * Country of the record
     */
    private String country;
    /**
     * CountryCode of the record
     */
    private String countryCode;
    /**
     * Province of the record
     */
    private String province;
    /**
     * City of the record
     */
    private String city;
    /**
     * CityCode of the record
     */
    private String cityCode;
    /**
     * Lat of the record
     */
    private String lat;
    /**
     * Lon of the record
     */
    private String lon;
    /**
     * Cases of the record
     */
    private long cases;
    /**
     * Status of the record
     */
    private String status;
    /**
     * Date of the record
     */
    private String date;

    public Covid19Case(String country, String countryCode, String province, String city, String cityCode, String lat, String lon, long cases, String status, String date) {
        this.country = country;
        this.countryCode = countryCode;
        this.province = province;
        this.city = city;
        this.cityCode = cityCode;
        this.lat = lat;
        this.lon = lon;
        this.cases = cases;
        this.status = status;
        this.date = date;
    }

    public Covid19Case(long id, String country, String countryCode, String province, String city, String cityCode, String lat, String lon, long cases, String status, String date) {
        this.id = id;
        this.country = country;
        this.countryCode = countryCode;
        this.province = province;
        this.city = city;
        this.cityCode = cityCode;
        this.lat = lat;
        this.lon = lon;
        this.cases = cases;
        this.status = status;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public long getCases() {
        return cases;
    }

    public void setCases(long cases) {
        this.cases = cases;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
