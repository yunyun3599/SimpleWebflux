package com.example.fluxtest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class MyFilter implements Filter {

    private final EventNotify eventNotify;

    public MyFilter(EventNotify eventNotify) {
        this.eventNotify = eventNotify;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("필터 실행됨");

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/event-stream; charset=utf-8");
        // text/plain 형태이면 반복문 내에서 flush를 해주어도 한 번에 모든 문자열이 찍힘
        // text/event-stream으로 설정하면 flush 될 때마다 웹페이지에 출력됨

        // 웹플럭스
        // TODO: Reactive Streams 라이브러리를 쓰면 표준을 지켜서 응답할 수 있음
        PrintWriter out = servletResponse.getWriter();
        for (int i=0; i<5; i++) {
            out.print("응답: "+ i + "\n");
            out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // 응답 종료 후에도 계속 돌아가도록 무한루프 생성
        // TODO: SSE Emitter 라이브러리를 사용하면 편하게 쓸 수 있음
        while(true) {
            try {
                if(eventNotify.getChange()) {
                    int lastIndex = eventNotify.getEvents().size() - 1;
                    out.print("응답: " + eventNotify.getEvents().get(lastIndex) + "\n");
                    out.flush();
                    eventNotify.setChange(false);
                }
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * WebFlux => Reactive Streams 가 적용된 stream (비동기 단일 스레드 동작)
         * Servlet MVC => Reactive Streams 이 적용된 Steam (멀티 스레드 방식)
         */
    }
}
