```java
//INTEGRATION TEST

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProductTest() throws  Exception{
        mockMvc.perform(
                get("/product/{name}","Biscuit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Biscuit"))
                .andExpect(jsonPath("$.price").value(10));

    }

    @Test
    public void getAllProducts() throws  Exception{
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }
}
```



````java
@SpringBootTest
public class CartServiceTest {

    @Autowired
    CartService cartService;

    private Product productA;
    private Product productB;
    private List<Product> products;

    @BeforeEach
    public void initializeProducts(){
        productA = Product.builder().name("COLA 1 L ").price(7.0).build();
        productB = Product.builder().name("MILK 1 L ").price(15.0).build();
        products = new LinkedList<>();
        products.add(productA);
        products.add(productB);

        System.out.println(productA);
        System.out.println(productB);
    }

    @Test
    @DisplayName("thereWillBeTwoProductInCart - There will be TwoProduct in Cart when 2 product send to cart")
    public void thereWillBeTwoProductInCart(){
        CartDetail cartDetail = null;
        cartDetail = cartService.getCartDetail(products);
        assertEquals(cartDetail.getTotalAmount(), products.size());
    }

    @Test
    @DisplayName("totalPriceEqualsToProductsTotalPrice")
    public void totalPriceEqualsToProductsTotalPrice(){
        CartDetail cartDetail = null;
        cartDetail = cartService.getCartDetail(products);
        assertEquals(cartDetail.getTotalPrice(), productA.getPrice() + productB.getPrice());
    }

    @Test
    @DisplayName("cartDiscount25Percent - When discountCart campaing is %25 will discount price p-p*0.25")
    public void cartDiscount25Percent(){
        CartDetail cart = CartDetail.builder()
                .totalPrice(100.0)
                .totalAmount(4).build();

        Campaing campaing = new Total25PriceCampaing();

        CartDetail result = cartService.discountCart(cart,campaing);
        assertEquals(result.getTotalPrice(), cart.getTotalPrice() * 0.75);
        assertEquals(result.getTotalAmount(), cart.getTotalAmount());
    }

    @Test
    @DisplayName("cartDiscountBasketValue - BasketCampaing When disCart have more than or equal 5 items will have %5 discount")
    public void cartDiscountBasketValue(){
        CartDetail cart = CartDetail.builder().totalPrice(50.0).totalAmount(5).build();
        Campaing campaing = new BasketKingCampaing();
        CartDetail result = cartService.discountCart(cart,campaing);
        assertEquals(result.getTotalPrice(), cart.getTotalPrice() * 0.95);
        assertTrue(result.getTotalAmount() >= 5 );
    }
}
````


`````java

@SpringBootTest
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private  ProductService productService;

    @BeforeAll
    public static void beforeAll(){
        System.out.println("Before All");
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("Before Each");
    }

    @Test
    public void noProductReturnedTest() {
        System.out.println("TEST - noProductReturnedTest");
        given(productRepository.getProductNames()).willReturn(Collections.emptyList());
        List<String> result =  productService.getProductsWithEvenCharactersWithFiredMethod();
        //there will return empty list as result.
        assertTrue(result.isEmpty());
    }

    @Test
    public void productFoundsTest(){
        System.out.println("TEST - productFoundsTest");
        List<String> input = Arrays.asList("aa","bbbb","ccc");
        given(productRepository.getProductNames()).willReturn(input);
        List<String> result = productService.getProductsWithEvenCharactersWithFiredMethod();
        assertEquals(2,result.size());
    }

    //test if is method called
    @Test
    public void testIfAddProductCalled(){
        System.out.println("TEST - testIfAddProductCalled");
        List<String> input = Arrays.asList("aa","bbbb","ccc");
        given(productRepository.getProductNames()).willReturn(input);
        List<String> result = productService.getProductsWithEvenCharactersWithFiredMethod();
        verify(productRepository,times(2)).addProductFired(any());
    }

    @Test
    public void testIfNoEvenProduct(){
        System.out.println("TEST - testIfNoEvenProduct");
        given(productRepository.getProductNames()).willReturn(Collections.emptyList());
        List<String> result = productService.getProductsWithEvenChars();
        assertTrue(result.isEmpty());
    }


}

``````

    @DisplayName("cartDiscountBasketValue - BasketCampaing When disCart have more than or equal 5 items will have %5 discount")
