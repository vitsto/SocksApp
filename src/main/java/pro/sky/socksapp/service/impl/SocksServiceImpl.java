package pro.sky.socksapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Service;
import pro.sky.socksapp.exception.SocksAlreadyCreatedException;
import pro.sky.socksapp.exception.SocksNotFoundException;
import pro.sky.socksapp.model.*;
import pro.sky.socksapp.service.FilesService;
import pro.sky.socksapp.service.SocksService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
public class SocksServiceImpl implements SocksService {
    private final FilesService filesService;
    Map<Socks, Integer> socksStore = new HashMap<>();

    public SocksServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Integer addSocks(Socks socks, int quantity) {
        if (socksStore.containsKey(socks)) {
            throw new SocksAlreadyCreatedException("Такие носки уже есть на складе");
        }
        saveToFile();
        saveToLog(new Transaction("приемка", LocalDateTime.now(), new SocksDTO(socks, quantity)));
        return socksStore.put(socks, quantity);
    }

    @Override
    public SocksDTO getSocks(Socks socks, int quantity) {
        return getSocksDTO(socks, quantity, "выдача");
    }

    @Override
    public Integer findSocks(Color color, Size size, int minCotton, int maxCotton) {
        Range<Integer> cottonRange = Range.between(minCotton, maxCotton);
        int count = 0;

        for (Socks socks : socksStore.keySet()) {
            if (socks.getColor().equals(color) && socks.getSize().equals(size) && cottonRange.contains(socks.getCotton())) {
                count += socksStore.get(socks);
            }
        }
        return count;
    }

    @Override
    public SocksDTO deleteSocks(Socks socks, int quantity) {
        return getSocksDTO(socks, quantity, "списание");
    }

    private SocksDTO getSocksDTO(Socks socks, int quantity, String operation) {
        int socksBalance = socksStore.getOrDefault(socks, 0);
        int restOfSocks = socksBalance - quantity;
        if (restOfSocks < 0) {
            throw new SocksNotFoundException("На складе нет такого количества носков");
        }
        socksStore.put(socks, restOfSocks);
        saveToLog(new Transaction(operation, LocalDateTime.now(), new SocksDTO(socks, quantity)));
        saveToFile();
        return new SocksDTO(socks, restOfSocks);
    }

    private void saveToFile() {
        List<SocksDTO> socksDTO = new LinkedList<>();
        for (Socks socks : socksStore.keySet()) {
            socksDTO.add(new SocksDTO(socks, socksStore.get(socks)));
        }
        try {
            String json = new ObjectMapper().writeValueAsString(socksDTO);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile();
        try {
            LinkedList<SocksDTO> socksDTO = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
            for (SocksDTO value : socksDTO) {
                socksStore.put(value.getSocks(), value.getQuantity());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToLog(Transaction transaction) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(transaction);
            filesService.saveToLog(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
//    private void saveToLog(String type, LocalDateTime dateTime, int quantity, Socks socks) {
//        String logStr = "Тип: " + type + ", Дата и время операции: " + dateTime + ", Количество: " + quantity + ", " +
//                "Разер: " + socks.getSize() + ", Содержание хлопка: " + socks.getCotton() + ", Цвет: " + socks.getColor() +
//                "\n";
//        filesService.saveToLog(logStr);
//    }
}
