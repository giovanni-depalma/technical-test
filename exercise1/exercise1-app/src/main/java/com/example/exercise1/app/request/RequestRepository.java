package com.example.exercise1.app.request;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface RequestRepository {
	
	Stream<Request> getAll(Path path);
}
