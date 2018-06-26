package com.sirius.zuul2;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.kv.PutResponse;

import java.util.concurrent.ExecutionException;

public class ETCDTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Client client = Client.builder()
                .endpoints("http://127.0.0.1:23791").authority("www").user(new ByteSequence("www"))
                .build();

        PutResponse response = client.getKVClient()
                .put(new ByteSequence("/abc/ccccc"), new ByteSequence("v1")).get();

        System.out.println(response);

//        PutResponse response2 = ETCDClientHodler
//                .get()
//                .getKVClient()
//                .put(new ByteSequence("/abc/bcd"), new ByteSequence("v2")).get();
//
//        ETCDClientHodler.get().getKVClient().get(new ByteSequence("/abc/")).thenAccept(System.out::println);
    }

}
