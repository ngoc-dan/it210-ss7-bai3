package ss7.bai3.model;

public class Food {
    private String name;
    private String category;
    private double price;
    private String imageUrl;

    public Food() {}

    public Food(String name, String category, double price, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}