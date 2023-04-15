
```````java
@RestController
@RequestMapping("api/v1/boarding")
public class BoardingCardController {

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("")
    public ResponseEntity<String> getBoardingCard() throws IOException {
        String templateFile = "boardingcard.html";

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Map<String,Object> data = new HashMap<>();

        Passenger passenger = new Passenger("Alper","Mulayim","DLM");

        data.put("passenger",passenger);
        Context context = new Context();
        data.keySet().forEach(key->{
            context.setVariable(key,data.get(key));
        });
        
        return ResponseEntity.ok(templateEngine.process(templateFile, context));
    }
}
``````
