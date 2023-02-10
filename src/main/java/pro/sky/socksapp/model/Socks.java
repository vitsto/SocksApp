package pro.sky.socksapp.model;

import lombok.*;
import pro.sky.socksapp.exception.SocksInitializeException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Socks {
    private Color color;
    private Size size;
    private int cotton;

    public void setCotton(int cotton) {
        if (cotton > 100 || cotton <= 0) {
            throw new SocksInitializeException("Некорректное значение хлопка. (Должно быть в диапазоне от 0 до 100)");
        }
        this.cotton = cotton;
    }

}
