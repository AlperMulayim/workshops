import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class Transaction {
    private Integer year;
    private Integer amount;
    private  Trader trader;
}
