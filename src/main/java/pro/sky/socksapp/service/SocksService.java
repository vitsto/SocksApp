package pro.sky.socksapp.service;

import pro.sky.socksapp.model.Color;
import pro.sky.socksapp.model.Size;
import pro.sky.socksapp.model.Socks;
import pro.sky.socksapp.model.SocksDTO;

public interface SocksService {
    Integer addSocks(Socks socks, int quantity);

    SocksDTO getSocks(Socks socks, int quantity);

    Integer findSocks(Color color, Size size, int minCotton, int maxCotton);

    SocksDTO deleteSocks(Socks socks, int quantity);
}
