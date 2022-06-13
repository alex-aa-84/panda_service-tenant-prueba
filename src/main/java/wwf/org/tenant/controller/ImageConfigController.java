package wwf.org.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wwf.org.tenant.entity.ImageConfig;
import wwf.org.tenant.service.ImageConfigService;
import wwf.org.tenant.serviceApi.FormatMessage;

import java.io.IOException;

import static wwf.org.tenant.serviceApi.ImageByteCompress.compressBytes;
import static wwf.org.tenant.serviceApi.ImageByteCompress.decompressBytes;

@CrossOrigin(origins = {"${settings.cors_origin}"}, maxAge = 3600,
        allowedHeaders={"Origin", "X-Requested-With", "Content-Type", "Accept", "x-client-key", "x-client-token", "x-client-secret", "Authorization"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping(value="/wwf/image")
public class ImageConfigController {

    @Autowired
    private ImageConfigService imageConfigService;

    @PostMapping()
    public ResponseEntity<ImageConfig> updateImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

        ImageConfig img = new ImageConfig();
        //img.setImage(compressBytes(file.getBytes()));
        img.setImage(file.getBytes());
        img.setType(file.getContentType());
        img.setName(file.getOriginalFilename());

        ImageConfig imgR = imageConfigService.createImage(img);

        return ResponseEntity.status(HttpStatus.CREATED).body(imgR);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ImageConfig> uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable("id") Long id) throws IOException {

        ImageConfig img = new ImageConfig();
        img.setId(id);
        img.setImage(file.getBytes());
        img.setType(file.getContentType());
        img.setName(file.getOriginalFilename());

        ImageConfig imgR = imageConfigService.updateImage(img);

        return ResponseEntity.status(HttpStatus.CREATED).body(imgR);
    }

}
