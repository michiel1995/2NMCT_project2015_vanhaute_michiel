package be.howest.nmct.admin;

/**
 * Created by Michiel on 31/03/2015.
 */
public class BeerPrice {
    private String organisation;
    private Float price;
    private String city;
    private String street;
    private Double longitude;
    private Double latitude;
    private int number;
    private String brand;

    public String getBrand() {
        return brand;
    }

    public BeerPrice(String organisation,String brand, Float price, String city, String street, int number, Double longitude, Double latitude) {
        this.organisation = organisation;
        this.price = price;
        this.city = city;
        this.street = street;
        this.number = number;
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

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public int getNumber() {
        return number;
    }
}
