package com.ceyhun.service;

import com.ceyhun.Author;
import com.ceyhun.Book;
import com.ceyhun.BookAuthorServiceGrpc;
import com.ceyhun.DbTemplate;
import com.google.protobuf.Descriptors;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class BookAuthorClientService {

    @GrpcClient("grpc-service")
    BookAuthorServiceGrpc.BookAuthorServiceBlockingStub synchronousClient;

    @GrpcClient("grpc-service")
    BookAuthorServiceGrpc.BookAuthorServiceStub asynchronousClient;

    public Map<Descriptors.FieldDescriptor, Object> getAuthor(int authorId) {
        Author authorRequest = Author.newBuilder().setAuthorId(authorId).build();
        Author authorResponse = synchronousClient.getAuthor(authorRequest);
        return authorResponse.getAllFields();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthor(int authorId) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(1);
        Author authorRequest = Author.newBuilder().setAuthorId(authorId).build();
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        asynchronousClient.getBooksByAuthor(authorRequest, new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                response.add(book.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                count.countDown();
            }

            @Override
            public void onCompleted() {
                count.countDown();
            }
        });
        boolean await = count.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }

    public Map<String, Map<Descriptors.FieldDescriptor, Object>> getExpensiveBook() throws InterruptedException {
        final CountDownLatch count = new CountDownLatch(1);
        final Map<String, Map<Descriptors.FieldDescriptor, Object>> response = new HashMap<>();
        StreamObserver<Book> responseObserver = asynchronousClient.getExpensiveBook(new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                response.put("Expensive Book", book.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                count.countDown();
            }

            @Override
            public void onCompleted() {
                count.countDown();
            }
        });

        DbTemplate.getBooksFromDbTemplate().forEach(responseObserver::onNext);
        responseObserver.onCompleted();
        boolean await = count.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyMap();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthorGender(String gender) throws InterruptedException {
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        final CountDownLatch count = new CountDownLatch(1);
        StreamObserver<Book> responseObserver = asynchronousClient.getBooksByAuthorGender(new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                response.add(book.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                count.countDown();
            }

            @Override
            public void onCompleted() {
                count.countDown();
            }
        });
        DbTemplate.getAuthorFromDbTemplate()
                .stream()
                .filter(author -> author.getGender().equalsIgnoreCase(gender))
                .forEach(author -> responseObserver.onNext(Book.newBuilder().setAuthorId(author.getAuthorId()).build()));
                responseObserver.onCompleted();
        boolean await = count.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }


}
