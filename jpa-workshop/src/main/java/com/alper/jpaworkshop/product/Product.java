package com.alper.jpaworkshop.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Product")
@Table(name = "products")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends   IProduct{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "product_name")
    private String name;

    @Column(insertable = false, updatable = false)
    private String dtype;

    @JsonIgnore
    @Column(name = "category_id", insertable = false , updatable = false)
    private Integer category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private ProductCategory productCategory;

    @ManyToMany
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<ProductTags> tags;

    @Override
    public String getDetail() {
        String detail = null;

        detail = getCategory() +
                " " +
                tags.stream().map(tag-> tag.getName()).collect(Collectors.joining(", "));
        return detail;
    }
}
