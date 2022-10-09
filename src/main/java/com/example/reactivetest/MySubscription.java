package com.example.reactivetest;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Iterator;

// 구독 정보 (구독자, 어떤 데이터 구독할 지 알고 있어야 함)
public class MySubscription implements Subscription {

    private Subscriber s;
    private Iterator<Integer> it;

    public MySubscription(Subscriber s, Iterable<Integer> its) {
        this.s = s;
        this.it = its.iterator();
    }

    @Override
    public void request(long n) {   // A. 리퀘스트 1이면 / B. 리퀘스트 20이면
        while(n > 0) {
            if(it.hasNext()) {
                s.onNext(it.next());    // A. 구독자에게 1을 보냄 / B. 구독자에게 1,2,3,4,5,6,7,8,9,10 보낸 후 반복문 종료
            } else {
                s.onComplete();
                break;
            }
            n--;
        }
    }

    @Override
    public void cancel() {

    }
}
