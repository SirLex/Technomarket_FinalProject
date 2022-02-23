package com.technomarket.model.pojos;

import com.technomarket.model.dtos.attribute.AttributeFilterDTO;
import com.technomarket.model.relationentity.OrderProduct;
import com.technomarket.model.relationentity.ProductAttribute;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id=?")
@FilterDef(name = "deletedProductFilter", parameters = @ParamDef(name = "deleted", type = "boolean"))
@Filter(name = "deletedProductFilter", condition = "deleted = :deleted")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String brand;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private Subcategory subcategory;

    @Column
    private double price;
    @Column
    private String info;
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToMany(mappedBy = "product")
    Set<ProductAttribute> productAttribute;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteProducts")
    private List<User> userList;

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> images;

    public Product() {
        this.orderProducts = new HashSet<>();
    }

    public boolean isOnDiscount() {
        return discount != null;
    }

    public boolean hasThisParticularAttribute(AttributeFilterDTO dto) {
        for (ProductAttribute attribute : productAttribute) {
            if (sameAttribute(attribute, dto)) return true;
        }
        return false;
    }

    private boolean sameAttribute(ProductAttribute attribute, AttributeFilterDTO dto) {
        return attribute.getAttribute().getName().equals(dto.getName()) && attribute.getValue().equals(dto.getValue());
    }

    public double calculatePriceDiscount() {
        if(discount==null){
            return price;
        }
        return price-(price*(discount.getDiscountPercentage()/100.0));
    }
}
