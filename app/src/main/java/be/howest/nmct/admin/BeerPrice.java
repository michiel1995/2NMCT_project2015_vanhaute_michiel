package be.howest.nmct.admin;

/**
 * Created by Michiel on 31/03/2015.
 */
public final class BeerPrice implements Comparable<BeerPrice> {
    @Override
    public int compareTo(BeerPrice another) {
        Double compareDistance = another.getDistance();
        if((this.getDistance() - compareDistance) < 0)
            return -1;
        else
            return 1;
    }

    public enum BEERBRANDS{
        STELLA("Stella"),
        JUPILER("Jupiler");
        private String naam;

        BEERBRANDS(String naam){
            this.naam = naam;
        }

        public String getNaam() {
            return naam;
        }
    }

    private String organisation;
    private Float price;
    private String city;
    private String address;
    private Double longitude;
    private Double latitude;
    private Double distance;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    private String country;
    private BEERBRANDS brand;

    public BEERBRANDS getBrand() {
        return brand;
    }

    public BeerPrice(String organisation,BEERBRANDS brand, Float price, String city, String address, String country, Double longitude, Double latitude) {
        this.organisation = organisation;
        this.price = price;
        this.city = city;
        this.country = country;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.brand = brand;
    }

    public String getOrganisation() {
        return organisation;
    }

    public Float getPrice() {
        return price;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getCountry() {
        return country;
    }
}
