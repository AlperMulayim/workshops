````````
public YahooClasses.FinancialData getStock(String symbol) throws JsonProcessingException {

        String url = "https://query1.finance.yahoo.com/v11/finance/quoteSummary/"+symbol+".IS?modules=financialData";
        RestTemplate restTemplate = new RestTemplate();

        String data =  restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        YahooClasses.Root rootData = mapper.readValue(data, YahooClasses.Root.class);

        return rootData.quoteSummary.result.get(0).financialData;
    }

````````

