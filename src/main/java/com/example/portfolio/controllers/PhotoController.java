package com.example.portfolio.controllers;

import com.example.portfolio.entities.Photo;
import com.example.portfolio.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    PhotoRepository photoRepository;

    @GetMapping("/id/{photoId}")
    public ResponseEntity getPhotoById(@PathVariable Integer photoId){

        Photo photo = photoRepository.findById(photoId).orElse(null);
        photo.setImage(decompressBytes(photo.getImage()));
        if(photo!=null)
            return new ResponseEntity<>(photo, HttpStatus.OK);
        else return ResponseEntity.badRequest().body("Photo with given id not found");
    }

    @PostMapping("/post")
    public ResponseEntity uploadPhoto(@RequestParam("photoId") Integer photoId, @RequestParam("title") String title,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("photo")MultipartFile file) throws IOException{
        Photo photo = new Photo(photoId, title, description, compressBytes(file.getBytes()));
        photoRepository.save(photo);
        return new ResponseEntity<>(photo, HttpStatus.OK);
    }


    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
