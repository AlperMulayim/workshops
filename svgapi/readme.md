<img width="1080" alt="banneralper" src="https://user-images.githubusercontent.com/12942688/232245843-322a49b2-0c63-4220-893b-16f1d7e10dd1.png">

<iframe width="560" height="315" src="https://www.youtube.com/embed/DsFegZg0CmQ" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>

`````java
@Component
public class HtmlTemplateGenerator {

    public String createBindedHtmlTemplate(String filename, Map<String,Object> data){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine  templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();

        data.keySet().forEach(key->{
            context.setVariable(key,data.get(key));
        });

        return  templateEngine.process(filename,context);
    }
}

@Service
public class BoardingCardService {

    @Autowired
    private HtmlTemplateGenerator templateGenerator;
  
    public String getBoardingCard(BoardingCard card){
        Map<String,Object> cardDataMap = new HashMap<>();
        cardDataMap.put("card",card);
        return templateGenerator.createBindedHtmlTemplate("boardingcard.html",cardDataMap);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardingCard {
    private String passengername;
    private String airline;
    private String departure;
    private String arrival;
    private String flightno;
    private String flightdate;
    private String flighttime;
    private String seat;
    private String boardingtime;
    private String gate;
    private String passengerclass;
}

`````
``````
POST /api/v1/boardingcards
{
    "passengername":"Oliver Olive",
    "airline":"Tomato Air",
    "departure":"BJV",
    "arrival":"SAW",
    "flightno": "TM-134",
    "flightdate":"16/04/23",
    "flighttime":"17:35",
    "seat":"29B",
    "boardingtime":"16:25",
    "gate":"103-B",
    "passengerclass":"BUSINESS"
}
POST /api/v1/boardingcards
{
    "passengername":"Oliver Olive",
    "airline":"Tomato Air",
    "departure":"BJV",
    "arrival":"SAW",
    "flightno": "TM-134",
    "flightdate":"16/04/23",
    "flighttime":"17:35",
    "seat":"29B",
    "boardingtime":"16:25",
    "gate":"103-B",
    "passengerclass":"BUSINESS"
}
`````
