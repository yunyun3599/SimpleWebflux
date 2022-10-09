package com.example.reactivetest;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.Arrays;

public class MyPub implements Publisher<Integer> {

    Iterable<Integer> its = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        System.out.println("1. 신문사 구독하기");
        System.out.println("2. 구독 정보 생성 후 돌려주기 (대기 필요)");
        MySubscription subscription = new MySubscription(subscriber, its);
        System.out.println("3. 신문사: 구독 정보 생성 완료");
        subscriber.onSubscribe(subscription);
    }
}
