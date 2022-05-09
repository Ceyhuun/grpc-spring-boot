package com.ceyhun.controller;

import com.ceyhun.service.BookAuthorClientService;
import com.google.protobuf.Descriptors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BookAuthorController {

    BookAuthorClientService bookAuthorService;

    //Unary RPC
    @GetMapping("author/{authorId}")
    public Map<Descriptors.FieldDescriptor, Object> getAuthor(@PathVariable String authorId){
        return bookAuthorService.getAuthor(Integer.parseInt(authorId));
    }

    //Server streaming RPC
    @GetMapping("/book/{authorId}")
    public List<Map<Descriptors.FieldDescriptor,Object>> getBooksByAuthor(@PathVariable String authorId) throws InterruptedException {
        return bookAuthorService.getBooksByAuthor(Integer.parseInt(authorId));
    }

    //Client streaming RPC
    @GetMapping("/book")
    public Map<String, Map<Descriptors.FieldDescriptor, Object>> getExpensiveBook() throws InterruptedException {
        return bookAuthorService.getExpensiveBook();
    }

    //Bidirectional streaming RPC
    @GetMapping("/book/author/{gender}")
    public List<Map<Descriptors.FieldDescriptor,Object>> getBooksByAuthorGender(@PathVariable String gender) throws InterruptedException {
        return bookAuthorService.getBooksByAuthorGender(gender);
    }
}
