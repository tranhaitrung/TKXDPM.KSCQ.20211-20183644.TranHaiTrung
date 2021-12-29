package entity.shipping;

public class Location {
    private String province;
    private String address;

    public Location() {

    }

    public boolean checkAddressSupport(String province) {
        return province.equals("Hà Nội");
    }
}
