package com.example.reactivetest;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySub implements Subscriber<Integer> {

    final int BUFFER_SIZE = 3;

    private Subscription s;
    private int bufferSize = BUFFER_SIZE;

    @Override
    public void onSubscribe(Subscription s) {
        System.out.println("구독자: 구독 정보 수신 완료");
        this.s = s;
        System.out.println("구독자: 신문 하루에 1개씩 받기 희망");
        this.s.request(bufferSize);   //신문 n개씩 매일매일 달라는 의미 (백프레셔) 소비자가 한 번에 처리할 수 있는 개수를 요청
    }

    @Override
    public void onNext(Integer t) {
        System.out.println("onNext(): " + t);
        bufferSize--;
        if(bufferSize == 0) {
            System.out.println("하루 지남");
            bufferSize = BUFFER_SIZE;
            this.s.request(bufferSize);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("구독중 에러");
    }

    @Override
    public void onComplete() {
        System.out.println("구독 완료");
    }
}
