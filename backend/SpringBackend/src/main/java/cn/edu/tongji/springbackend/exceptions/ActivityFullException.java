package cn.edu.tongji.springbackend.exceptions;

public class ActivityFullException extends RuntimeException {
    public ActivityFullException(String message) {
        super(message);
    }
}
