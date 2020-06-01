package by.vchernukha.demo.dto.account;

import by.vchernukha.demo.dto.client.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientAccountDTO {

    private Long id;
    private ClientDTO client;
    private BigDecimal balance;

}
