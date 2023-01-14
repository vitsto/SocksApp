package pro.sky.socksapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    String type;
    LocalDateTime dateTime;
    SocksDTO socksDTO;
}
