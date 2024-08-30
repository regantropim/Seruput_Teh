package id.seruput.api.data.product;

import id.seruput.api.data.Identity;

public class ProductId extends Identity {

    public ProductId(int identity, String prefix) {
        super(identity, prefix);
    }

    public static ProductId of(String productId) {
        return of(Integer.parseInt(productId.substring(2)));
    }

    public static ProductId of(int productId) {
        return new ProductId(productId, "TE");
    }

}
