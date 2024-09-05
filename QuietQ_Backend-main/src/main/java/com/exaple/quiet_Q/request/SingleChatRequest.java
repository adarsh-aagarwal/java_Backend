package com.exaple.quiet_Q.request;

public class SingleChatRequest {
    private Long id;

    @Override
    public String toString() {
        return "SingleChatRequest{" +
                "userId=" + id +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SingleChatRequest() {
    }

    public SingleChatRequest(Long id) {
        this.id = id;
    }
}
