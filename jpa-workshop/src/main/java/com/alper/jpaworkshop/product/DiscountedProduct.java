package com.alper.jpaworkshop.product;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "discounted_products")
public class DiscountedProduct  extends  Product{
    @Column(name = "id")
    private Integer id;
    @Column(name = "discount_percentage")
    private Double percentage;
}
