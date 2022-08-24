import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        StreamTutor streamTutor = new StreamTutor();
        List<Trader> traderList = streamTutor.traders;
        List<Transaction> transactionList = streamTutor.transactions;

        transactionList.forEach(System.out::println);
        traderList.forEach(System.out::println);
        System.out.println();

        streamTutor.filterByAmount();
        System.out.println();

        streamTutor.filterMacauCityTransactions();
        System.out.println();

        streamTutor.getOnlyTransactionsAmountsOfMacauCity();
        System.out.println();

        streamTutor.getOnlyCities();
        System.out.println();

        streamTutor.getTradersOfMilan();
        System.out.println();

        streamTutor.getTransactionsBetween2009and2016();
        System.out.println();

        streamTutor.findHighestValueTransaction();
        System.out.println();

        streamTutor.findTotalTransactions();
        System.out.println();

        streamTutor.groupByYearTransactions();
        System.out.println();

        streamTutor.avarageAmountOfTransactions();
        System.out.println();

        streamTutor.avarageAmountOfTransactionsByYear();
        System.out.println();

        streamTutor.tradersNamesByCity();
        System.out.println();

        streamTutor.customGrouping();
        System.out.println();


    }
}
