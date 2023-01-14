package pro.sky.socksapp.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.socksapp.service.FilesService;
import pro.sky.socksapp.service.SocksService;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesController {
    private final FilesService filesService;

    public FilesController(FilesService filesService, SocksService socksService) {
        this.filesService = filesService;
    }

    @GetMapping("/export/socks")
    public ResponseEntity<InputStreamResource> downloadSocksFile() throws FileNotFoundException {
        File file = filesService.getDataFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SocksInfo.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping(value = "/import/socks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadSocksFile(@RequestParam MultipartFile file) {
        filesService.cleanDataFile();
        File dataFile = filesService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
