### filter()

`````java
  List<Transaction> transactions = transactionList.stream()
                .filter(transaction -> transaction.getAmount() > 1500)
                .collect(Collectors.toList());
``````

### map()

`````java
  List<Integer> transactionsOfMacau = transactionList.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Macau"))
                .map(transaction -> transaction.getAmount())
                .collect(Collectors.toList());
``````

### sorted()
``````java
        List<Transaction> btrans =  transactionList.stream()
                .filter(transaction -> transaction.getYear() >= 2009)
                .filter(transaction -> transaction.getYear() <= 2016)
                .sorted(Comparator.comparingInt(Transaction::getYear))
                .collect(Collectors.toList());
``````

### reduce()
`````java
Optional<Integer> highest = transactionList.stream()
                .map(Transaction::getAmount)
                .reduce(Integer::max);
`````

### groupingBy()
``````java
Map<Integer, List<Transaction>> yearByTransactions =
                transactionList.stream()
                        .collect(Collectors.groupingBy(Transaction::getYear));
```````

### avaraging()
``````java
 double avarage = transactionList.stream()
                .collect(Collectors.averagingInt(Transaction::getAmount));
``````

### Custom Grouping
``````java
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

``````
``````java
public Map<String, Integer> customersArea(){
        Map<String, Integer> customersArea = new HashMap<>();
        enum CustomersArea { EUROPE, ASIA }

        List<Car> allCars = carsService.getAllCars();

        Map<CustomersArea, List<Car>>  carsByArea = allCars.stream()
                .collect(Collectors.groupingBy(car -> {
                    if(car.getCountry().equals("Turkey") || car.getCountry().equals("Russia")){
                        return  CustomersArea.ASIA;
                    }else{
                        return CustomersArea.EUROPE;
                    }
                }));

        customersArea.put("ASIA",carsByArea.get(CustomersArea.ASIA).size());
        customersArea.put("EUROPE",carsByArea.get(CustomersArea.EUROPE).size());

        return  customersArea;
    }
```````


`````java
  public List<Car> filterByYears(Integer start , Integer end){
        return  carsService.getAllCars().stream()
                .filter(car -> car.getSoldyear() > start)
                .filter((car->car.getSoldyear() < end))
                .collect(Collectors.toList());
    }



    public Map<String, List<Car>> groupTheCarsWithManufactorerFirst3Letters(){
        List<Car> cars = carsService.getAllCars();

        return cars.stream()
                .distinct()
                .collect(Collectors.groupingBy(c->
                 c.getCountry().substring(0,3).toUpperCase(Locale.ROOT)));

    }

``````

### PREDICATE

``````java
Predicate<Car> countryPred = car -> car.getCountry().equals(country.get());
        Predicate<Car> manufactorerPred = car -> car.getManufactorer().equals(manufacturer.get());

        List<Predicate<Car>> carFilter = Arrays.asList(countryPred,manufactorerPred);
        List<Car> cars = service.getAllCars();

        cars = cars.stream().filter(carFilter.stream()
                        .reduce(predicate-> true, Predicate::and))
                .collect(Collectors.toList());

        return cars;
``````





