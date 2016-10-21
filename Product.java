/**
 * Created by joel on 10/6/16.
 */
public class Product {
    private String name, id;

    Product(String id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() { return id; }

    @Override
    public String toString() {
        return getName() + ' ' + getId();
    }
}
