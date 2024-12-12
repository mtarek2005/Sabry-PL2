import java.io.Serializable;

public class ProductCategory implements StrSerializable,Serializable {
    private String name;
    public ProductCategory(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
    // @Override
    public StrSerializable fromString(String s) throws IllegalArgumentException {
        String[] parts=s.split("\t");
        if(parts.length>=1){
            return new ProductCategory(parts[0]);
        }
        else throw new IllegalArgumentException("not enough arguments");
    }
}
