package by.vchernukha.demo.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientAccountDTO {

    @NotNull
    private Long clientId;

    @DecimalMin(value = "0.00", message = "Value must be greater than 0.00")
    @Digits(integer = 10, fraction = 2, message = "The fractional part is 2 decimal places")
    private BigDecimal initialBalance;

}
