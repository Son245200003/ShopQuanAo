package com.example.api.RestController;

import com.example.api.Entity.New;
import com.example.api.Entity.User;
import com.example.api.Service.NewService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api-new")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class NewController {
    private final NewService newService;
    @GetMapping("")
    public List<New> NewView(){
        List<New> newEntities = newService.findAllNew();
        return newEntities;
    }
    @GetMapping("/{id}")
    public ResponseEntity<New> getNewById(@PathVariable int id){
        New New=newService.getById(id);
        if(New!=null){
            return ResponseEntity.ok(New);
        }
        return ResponseEntity.notFound().build();
    }
    //serch
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("keyword") String key){
        ArrayList<New> news = newService.search(key);
        return ResponseEntity.ok(news);
    }
//    @GetMapping("/image/{id}")
//    public ResponseEntity<byte[]> getImageById(@PathVariable int id) {
//        Optional<New> newEntity = newService.findById(id);
//
//        if (newEntity.isPresent() && newEntity.get().getImg() != null) {
//            try {
//                Blob imageBlob = newEntity.get().getImg();
//                int blobLength = (int) imageBlob.length();
//                byte[] imageBytes = imageBlob.getBytes(1, blobLength);
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate media type
//
//                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//            } catch (SQLException e) {
//                // Handle SQLException if needed
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//            }
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @PostMapping("/add_new")
    public ResponseEntity<New> addNew(@RequestParam("img") MultipartFile file, @RequestParam("title") String title,
                                      @RequestParam("discribe") String discribe,
                                      @RequestParam("content") String content)
                                      throws IOException {
        New n=new New();
        n.setDiscribe(discribe);
        n.setTitle(title);
        n.setContent(content);
            byte[] fileByte= file.getBytes();
          n.setImg(fileByte);
        return new ResponseEntity<>(newService.addNew(n), HttpStatus.OK);
    }
    @PutMapping("/update_new/{id}")
    public ResponseEntity<New> updateNew(@PathVariable("id") int id,@RequestParam("img") MultipartFile file,
                                         @RequestParam("title") String title,
                         @RequestParam("discribe") String discribe,
                         @RequestParam("content") String content) throws IOException {
        New exitNew=newService.getById(id);
        byte[] fileByte=file.getBytes();
        if(exitNew!=null){
            exitNew.setImg(fileByte);
            exitNew.setContent(content);
            exitNew.setDiscribe(discribe);
            exitNew.setTitle(title);
            newService.updateNew(exitNew);
            return ResponseEntity.ok(exitNew);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteNew(@PathVariable int id){
        Optional<New> n=newService.findById(id);
        if(n.isPresent()){
            newService.deleteNew(id);
            return ResponseEntity.ok().build();
        }else return ResponseEntity.notFound().build();
    }


}
