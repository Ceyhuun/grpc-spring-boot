package com.ceyhun;

import java.util.ArrayList;
import java.util.List;

public class DbTemplate {

    public static List<Author> getAuthorFromDbTemplate(){
        return new ArrayList<Author>(){
            {
                add(Author.newBuilder().setAuthorId(1).setBookId(1).setFirstName("Ceyhun").setLastName("Mustafayev").setGender("male").build());
                add(Author.newBuilder().setAuthorId(2).setBookId(2).setFirstName("Ferhad").setLastName("Mehdizade").setGender("oglan").build());
                add(Author.newBuilder().setAuthorId(3).setBookId(3).setFirstName("Fikret").setLastName("Memmedov").setGender("male").build());
                add(Author.newBuilder().setAuthorId(4).setBookId(4).setFirstName("Togrul").setLastName("Mesimli").setGender("male").build());
            }
        };
    }
    public static List<Book> getBooksFromDbTemplate(){
        return new ArrayList<Book>(){
            {
                add(Book.newBuilder().setBookId(1).setAuthorId(1).setPrice(22f).setTittle("BookCeyhun1").setPages(145).build());
                add(Book.newBuilder().setBookId(2).setAuthorId(1).setPrice(23f).setTittle("BookCeyhun2").setPages(132).build());
                add(Book.newBuilder().setBookId(3).setAuthorId(2).setPrice(24f).setTittle("BookFerhad").setPages(155).build());
                add(Book.newBuilder().setBookId(4).setAuthorId(3).setPrice(26f).setTittle("BookFikret").setPages(165).build());
                add(Book.newBuilder().setBookId(5).setAuthorId(4).setPrice(20f).setTittle("BookTogrul1").setPages(143).build());
                add(Book.newBuilder().setBookId(6).setAuthorId(4).setPrice(25f).setTittle("BookTogrul2").setPages(151).build());
            }
        };
    }
}
