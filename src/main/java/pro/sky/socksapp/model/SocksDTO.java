package pro.sky.socksapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocksDTO {
    private Socks socks;
    private int quantity;
}
