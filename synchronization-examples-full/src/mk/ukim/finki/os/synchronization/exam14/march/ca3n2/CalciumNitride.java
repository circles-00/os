package mk.ukim.finki.os.synchronization.exam14.march.ca3n2;

import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import mk.ukim.finki.os.synchronization.ProblemExecution;
import mk.ukim.finki.os.synchronization.TemplateThread;

public class CalciumNitride {

	static Semaphore caPool;
	static Semaphore nPool;
	static Semaphore caHere;
	static Semaphore nHere;
	static Semaphore ready;
	static Semaphore finish;
	static Semaphore leave;

	static Semaphore lock;
	static int caCounter;


	public static void init() {
		caPool = new Semaphore(3);
		nPool = new Semaphore(2);
		caHere = new Semaphore(0);
		nHere = new Semaphore(0);
		ready = new Semaphore(0);
		finish = new Semaphore(0);
		leave = new Semaphore(0);
		lock = new Semaphore(1);
		caCounter = 0;
	}

	public static class Calcium extends TemplateThread {

		public Calcium(int numRuns) {
			super(numRuns);
		}

		@Override
		public void execute() throws InterruptedException {
			caPool.acquire();

			lock.acquire();
			caCounter++;
			if(caCounter == 3){
				caCounter = 0;
				lock.release();
				caHere.acquire(2);
				nHere.acquire(2);
				ready.release(4);
				state.bond();
				finish.acquire(4);
				state.validate();
				leave.release(4);
			} else {
				lock.release();
				caHere.release();
				ready.acquire();
				state.bond();
				finish.release();
				leave.acquire();
			}
			caPool.release();
		}


	}

	public static class Nitrogen extends TemplateThread {

		public Nitrogen(int numRuns) {
			super(numRuns);
		}

		@Override
		public void execute() throws InterruptedException {
			nPool.acquire();
			nHere.release();
			ready.acquire();
			state.bond();
			finish.release();
			leave.acquire();
			nPool.release();
		}
	}

	static CalciumNitrideState state = new CalciumNitrideState();

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			run();
		}
	}

	public static void run() {
		try {
			Scanner s = new Scanner(System.in);
			int numRuns = 1;
			int numIterations = 100;
			s.close();

			HashSet<Thread> threads = new HashSet<Thread>();

			for (int i = 0; i < numIterations; i++) {
				Nitrogen n = new Nitrogen(numRuns);
				threads.add(n);
				Calcium ca = new Calcium(numRuns);
				threads.add(ca);
				ca = new Calcium(numRuns);
				threads.add(ca);
				n = new Nitrogen(numRuns);
				threads.add(n);
				ca = new Calcium(numRuns);
				threads.add(ca);
			}

			init();

			ProblemExecution.start(threads, state);
			System.out.println(new Date().getTime());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
