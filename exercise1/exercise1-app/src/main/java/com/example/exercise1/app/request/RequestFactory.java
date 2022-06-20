package com.example.exercise1.app.request;

public class RequestFactory {
	
	public static RequestRepository buildRepository() {
		return new RequestRepositoryImp();
	}
}
