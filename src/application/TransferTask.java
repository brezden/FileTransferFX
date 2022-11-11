package application;

import javafx.concurrent.Task;

public class TransferTask extends Task{

	private final long limit;
	
	public TransferTask(long limit) {
		this.limit = limit;
	}
	
	@Override
	protected Object call() throws Exception {
		long sum = 0;
		
		for (int i = 0; i <= limit; i++) {
			sum = sum + 1;
			Thread.sleep(100);
			System.out.println(sum);
			updateProgress(i, limit);
			updateValue(sum);
		}
		
		return sum;
	}

}
