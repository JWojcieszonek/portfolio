package com.example.portfolio.controllers;

import com.example.portfolio.entities.Photo;
import com.example.portfolio.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PhotoController {

    @Autowired
    PhotoRepository photoRepository;
    private final Path fileStorage = Paths.get("D:\\Szkoła\\Nauka\\Portfolio\\front\\public\\images");

    @CrossOrigin(origins = "*")
    @GetMapping("/photos/{id}")
    public ResponseEntity getPhotoById(@PathVariable Integer id){

        Photo photo = photoRepository.findById(id).orElse(null);
        if(photo!=null) {
            //HttpHeaders headers = new HttpHeaders();
           // headers.add("Access-Control-Allow-Origin","*");
            return new ResponseEntity<>(photo,HttpStatus.OK);
        }
        else return ResponseEntity.badRequest().body("Photo with given id not found");
    }

    @GetMapping("/photos")
    public ResponseEntity getAllPhotos(){

        List<Photo> photos = (List<Photo>) photoRepository.findAll();
        if(!photos.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Total-Count",String.valueOf(photos.size()));
            headers.add("Access-Control-Expose-Headers","X-Total-Count");
            return new ResponseEntity(photos,headers,HttpStatus.OK);
        }
        else return ResponseEntity.badRequest().body("Photo with given id not found");
    }

    @PostMapping("/post")
    public ResponseEntity uploadPhoto(@RequestParam("photoId") Integer photoId, @RequestParam("title") String title,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("photo")MultipartFile file) throws IOException{
        Photo photo = new Photo(photoId, title, description, saveImage(file),"http://127.0.0.1:8887/"+file.getOriginalFilename(),file.getOriginalFilename() );
        photoRepository.save(photo);

        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    //TODO PUT, DELETE and probably modify POST
    /*
    @PutMapping("photos/{id}")
    public ResponseEntity updatePhoto() {

    }
    */

    public String saveImage(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path targetLocation = fileStorage.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = targetLocation.toString();
        path = path.replace("\\", "/");
        return path;
    }

}
