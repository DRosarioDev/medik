package com.rosariodev.medik.persistence;

public class DAOException extends RuntimeException{

    public DAOException(Throwable cause){
        super(cause);
    }
    
    public DAOException(String msg){
        super(msg);
    }

    public DAOException(String msg, Throwable cause){
        super(msg, cause);
    }

}
