import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class StreamTutor {
    List<Trader> traders = new LinkedList<>();
    List<Transaction> transactions = new LinkedList<>();

    public StreamTutor(){
        getTransactions();
    }

    public void filterByAmount(){
        List<Transaction> transactionList =  transactions;
        List<Transaction> transactions = transactionList.stream()
                .filter(transaction -> transaction.getAmount() > 1500)
                .collect(Collectors.toList());
        System.out.println("TRANSACTION AMOUNTS > 1500 - > " + transactions.stream().count() + " items. ");
        transactions.forEach(System.out::println);
    }

    public void filterMacauCityTransactions(){
        List<Transaction> transactionList =  transactions;

        List<Transaction> transactionsOfMacau = transactionList.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Macau"))
                .collect(Collectors.toList());

        System.out.println("TRANSACTIONS OF MACAU CITY");
        transactionsOfMacau.forEach(System.out::println);

    }

    public void getOnlyTransactionsAmountsOfMacauCity(){
        List<Transaction> transactionList =  transactions;

        List<Integer> transactionsOfMacau = transactionList.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Macau"))
                .map(transaction -> transaction.getAmount())
                .collect(Collectors.toList());

        System.out.println("TRANSACTIONS OF MACAU CITY ONLY AMOUNTS");
        transactionsOfMacau.forEach(System.out::println);
    }

    public void getTradersOfMilan(){
        List<Transaction> transactionList =  transactions;

        List<String> traders = transactionList.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Dowa"))
                        .map(transaction -> transaction.getTrader().getName())
                                .collect(Collectors.toList());


        System.out.println("TRADERS IN DOWA");
        traders.forEach(System.out::println);
    }

    public void getTransactionsBetween2009and2016(){
        List<Transaction> transactionList =  transactions;

        List<Transaction> btrans =  transactionList.stream()
                .filter(transaction -> transaction.getYear() >= 2009)
                .filter(transaction -> transaction.getYear() <= 2016)
                .sorted(Comparator.comparingInt(Transaction::getYear))
                .collect(Collectors.toList());

        System.out.println("TRANSACTIONS BETWEEN 2009 AND 2016");
        btrans.forEach(System.out::println);
    }

    public void findHighestValueTransaction(){
        List<Transaction> transactionList = transactions;

        Optional<Integer> highest = transactionList.stream()
                .map(Transaction::getAmount)
                .reduce(Integer::max);
        System.out.println("MAX TRANSACTION");
        highest.ifPresent(System.out::println);
    }

    public void findTotalTransactions(){
        List<Transaction> transactionList = transactions;

        Integer total = transactionList.stream()
                .map(Transaction::getAmount)
                .reduce(0,Integer::sum);

        System.out.println("TOTAL TRANSACTIONS AMOUNT "+ total);

    }

    public void groupByYearTransactions(){
        List<Transaction> transactionList = transactions;

        Map<Integer, List<Transaction>> yearByTransactions =
                transactionList.stream()
                        .collect(Collectors.groupingBy(Transaction::getYear));

        System.out.println(yearByTransactions);
    }

    public void avarageAmountOfTransactions(){
        List<Transaction> transactionList = transactions;

        double avarage = transactionList.stream()
                .collect(Collectors.averagingInt(Transaction::getAmount));

        System.out.println("AVARAGE AMOUNT OF TRANSACTIONS -> " + avarage);

    }

    public void avarageAmountOfTransactionsByYear(){
        List<Transaction> transactionList = transactions;

        Map<Integer, List<Transaction>> yearByTransactions =
                transactionList.stream()
                        .collect(Collectors.groupingBy(Transaction::getYear));

        Set<Integer > keys = yearByTransactions.keySet();

        Map<Integer, Double> avarageByYears = new HashMap<>();

        keys.forEach(k-> {
            List<Transaction> keyTr = yearByTransactions.get(k);

            double avarage = keyTr.stream().collect(Collectors.averagingDouble(Transaction::getAmount));
            avarageByYears.put(k,avarage);
        });
        System.out.println("AVARAGE AMOUNTS BY YEAR");
        System.out.println(avarageByYears);
    }

    public void tradersNamesByCity(){
        List<Transaction> transactionList = transactions;

        Map<String,List<Trader>> traderMap = transactionList.stream()
                .map(transaction -> transaction.getTrader())
                .collect(Collectors.groupingBy(Trader::getCity));

        Map<String, String >  tradersByCity = new HashMap<>();
        Set<String> traderCities =  traderMap.keySet();

        traderCities.forEach( city->{
                    String traderNames =  traderMap.get(city).stream()
                            .map(Trader::getName)
                            .collect(Collectors.joining(", "));
                    tradersByCity.put(city,traderNames);
                }
        );

        System.out.println(tradersByCity);

    }

    public void customGrouping(){
        //GROUP TRANSACTİONS WITH THEIR LEVEL OF LOW HIGH AND MED.
        List<Transaction> transactionList = transactions;
        enum TransactionLevels {HIGH, MEDIUM, LOW}

        Map<TransactionLevels, List<Transaction>> transactionByLevels =
                transactionList.stream()
                        .collect(groupingBy(tr->
                        {
                            if(tr.getAmount() < 500) return TransactionLevels.LOW;
                            else if (tr.getAmount() <= 1500) {
                                return TransactionLevels.MEDIUM;
                            }
                            else return TransactionLevels.HIGH;
                        }));


        System.out.println(transactionByLevels);
        System.out.println("HIGH TRANSACTIONS -> " + transactionByLevels.get(TransactionLevels.HIGH).size() );
        System.out.println(transactionByLevels.get(TransactionLevels.HIGH));
        System.out.println("MEDIUM TRANSACTIONS -> " + transactionByLevels.get(TransactionLevels.MEDIUM).size());
        System.out.println(transactionByLevels.get(TransactionLevels.MEDIUM));
        System.out.println("LOW TRANSACTIONS -> " + transactionByLevels.get(TransactionLevels.LOW).size());
        System.out.println(transactionByLevels.get(TransactionLevels.LOW));

    }
    public void getOnlyCities(){
        List<Transaction> transactionList =  transactions;

        List<String> cities = transactionList.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());


        System.out.println("ONLY CITIES IN SYSTEM");
        cities.forEach(System.out::println);
        System.out.println("Total " + cities.stream().count()  + " city in system");
    }

    private   List<Transaction> getTransactions(){
        JSONParser jsonParser = new JSONParser();
        List<Transaction> transactions = new LinkedList<>();

        try (FileReader reader = new FileReader("traders.json")){
            Object obj = jsonParser.parse(reader);

            JSONArray transactionList = (JSONArray) obj;
            transactionList.forEach(tr-> {
                JSONObject trJsonObj = (JSONObject) tr;

                JSONObject traderOb = (JSONObject) trJsonObj.get("trader");

                Trader trader = Trader.builder()
                        .name((String) traderOb.get("name"))
                        .city((String) traderOb.get("city"))
                        .build();

                Transaction nTransaction = Transaction.builder()
                        .amount(((Long) trJsonObj.get("amount")).intValue())
                        .year(((Long) trJsonObj.get("year")).intValue())
                        .trader(trader)
                        .build();


                this.transactions.add(nTransaction);
                this.traders.add(trader);
            });

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

}
