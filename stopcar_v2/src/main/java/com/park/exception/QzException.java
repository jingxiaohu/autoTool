package com.park.exception;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class QzException extends Exception {
	Logger log = Logger.getLogger(QzException.class);
	public QzException(){
		super();
	}
	
	public QzException(String message){
		super(message);
	}
	
    public QzException(String message, Throwable cause) {
        super(message+":"+cause.getMessage(), cause);
        log.error(message+":"+cause.getMessage(), cause); 
    }
	
}
