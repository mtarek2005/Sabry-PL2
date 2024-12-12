import java.io.Serializable;
import java.time.LocalDate;

public class Product implements StrSerializable,Serializable {
    private ProductCategory[] categories;
    private String name;
    private LocalDate productionDate;
    private LocalDate expirationDate;
    private int quantity;
    public ProductCategory[] getCategories() {
        return categories;
    }
    public void setCategories(ProductCategory[] categories) {
        this.categories = categories;
    }
    public String getCategoryNames() {
        String names = "";
        for (int i = 0; i < categories.length; i++) {
            names+=categories[i].getName()+(i<categories.length-1?", ":"");
        }
        return names;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getProductionDate() {
        return productionDate;
    }
    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Product(ProductCategory[] categories, String name, LocalDate productionDate, LocalDate expirationDate,
            int quantity) {
        this.categories = categories;
        this.name = name;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }
}
