package id.seruput.core.data.product;

import id.seruput.api.data.product.Product;
import id.seruput.api.data.product.ProductId;

public class ProductImpl implements Product {

    private final ProductId productId;
    private final String productName;
    private final long productPrice;
    private final String productDescription;

    public ProductImpl(ProductId productId, String productName, long productPrice, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }

    @Override
    public ProductId productId() {
        return productId;
    }

    @Override
    public String name() {
        return productName;
    }

    @Override
    public long price() {
        return productPrice;
    }

    @Override
    public String description() {
        return productDescription;
    }

}
