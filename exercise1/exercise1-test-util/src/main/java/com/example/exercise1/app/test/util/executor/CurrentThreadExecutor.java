package com.example.exercise1.app.test.util.executor;

import java.util.concurrent.Executor;

public class CurrentThreadExecutor implements Executor{

	@Override
	public void execute(Runnable command) {
		command.run();
	}

}
