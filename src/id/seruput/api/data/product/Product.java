package id.seruput.api.data.product;

import id.seruput.api.database.Entity;

public interface Product extends Entity<ProductId> {

    ProductId productId();

    String name();

    Product name(String name);

    long price();

    Product price(long price);

    String description();

    Product description(String description);

}
