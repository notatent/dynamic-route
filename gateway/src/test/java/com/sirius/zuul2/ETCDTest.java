package com.sirius.zuul2;

import com.coreos.jetcd.data.ByteSequence;
import com.sirius.zuul2.etcd.ETCDClientHodler;

import java.util.concurrent.ExecutionException;

public class ETCDTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Client client = Client.builder()
//                .endpoints("http://127.0.0.1:23791").authority("www").user(new ByteSequence("www"))
//                .build();

        ETCDClientHodler.init("www", "www", "http://127.0.0.1:23791");

//        PutResponse response1 = ETCDClientHodler.client().getKVClient()
//                .put(new ByteSequence("/abc/ccccc"), new ByteSequence("v1")).get();
//
//        System.out.println(response1);
//
//        PutResponse response2 = ETCDClientHodler
//                .client()
//                .getKVClient()
//                .put(new ByteSequence("/abc/bcd"), new ByteSequence("v2")).get();
//
//        GetResponse response3 = ETCDClientHodler.client().getKVClient().get(new ByteSequence("/abc"),
//                GetOption.newBuilder().withPrefix(ByteSequence.fromString("true")).withLimit(100).build()).get();
//        Optional.of(response3).ifPresent(response -> {
//            response.getKvs().forEach(kv -> {
//                System.out.printf("%s/%s/%s/%s\n",
//                        kv.getKey().toStringUtf8(),
//                        kv.getValue().toStringUtf8(),
//                        kv.getVersion(),
//                        kv.getModRevision());
//            });
//        });

        ETCDClientHodler.client().getKVClient().put(new ByteSequence("/endpoints/rpc/abc/127.0.0.1#8080"),
                new ByteSequence("v1")).thenAccept(System.out::println);

    }

}
