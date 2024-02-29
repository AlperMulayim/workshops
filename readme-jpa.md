## NOTES for JPA.
#### ManyToOne
Product.java 
`````java 
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private ProductCategory productCategory;
`````

#### ManyToMany
Product.java
`````java
    @ManyToMany
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<ProductTags> tags;
`````

#### Inheritance
Product.java and DiscountedProduct.java
`````java
    //Product.java
    @Entity
    @Table(name = "products")
    @Data
    @Inheritance(strategy = InheritanceType.JOINED)
    public class Product { //code  }
    
    //DiscountedProduct.java
    @Entity
    @Data
    @Table(name = "discounted_products")
    public class DiscountedProduct  extends  Product{
        @Column(name = "id")
        private Integer id;
        @Column(name = "discount_percentage")
        private Double percentage;
    }
`````

#### application.yml
`````yml
spring:
  datasource:
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost/jpadb
    username: root
    password: rootpass
  jpa:
    hibernate.ddl-auto: validate
    generate-ddl: true
    show-sql: true
`````
#### database
``````mysql
CREATE TABLE products(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    barcode VARCHAR(100),
    product_name VARCHAR(400),
    category_id INT,
    dtype VARCHAR(50),
    FOREIGN KEY(category_id) REFERENCES categories(id)
);

CREATE TABLE discounted_products(
    id INT,
    discount_percentage DOUBLE
);

``````

Result
````json
[
    {
        "id": 2,
        "barcode": "12345678",
        "name": "MYMILK MILK 1L",
        "dtype": null,
        "productCategory": {
            "name": "BEVERAGES"
        },
        "tags": []
    },
    {
        "id": 3,
        "barcode": "123456782",
        "name": "FUN MILK 1L",
        "dtype": null,
        "productCategory": {
            "name": "BEVERAGES"
        },
        "tags": [
            {
                "name": "weekend products"
            }
        ],
        "percentage": 0.25
    },
    {
        "id": 4,
        "barcode": "123456782",
        "name": "CAKE",
        "dtype": null,
        "productCategory": {
            "name": "FOOD"
        },
        "tags": [
            {
                "name": "weekend products"
            },
            {
                "name": "for students"
            }
        ]
    }
]
````

### Enum Data

`````java
@Entity
@Table(name = "cpdb_rules")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CampaignRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rule_name")
    private String ruleName;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "rule_type", length = 255, columnDefinition = "enum")
    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    private CampaignRuleType campaignRuleType;
}


public enum CampaignRuleType {
    R_CAMPAIGN,R_DEFAULT,R_COMPANY
}
`````
Database Enum
`````sql

CREATE TABLE cpdb_rules(
	id INT NOT NULL AUTO_INCREMENT,
	rule_name VARCHAR(100),
	rule_type ENUM("R_CAMPAIGN","R_DEFAULT","R_COMPANY"),
	create_date DATE,
	update_date DATE,
	PRIMARY KEY(id)
);
`````
