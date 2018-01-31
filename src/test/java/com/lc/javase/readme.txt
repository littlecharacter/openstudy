在多线程环境下，程序退出的条件是，所有的非Daemon线程都正常结束或者某个线程条用了system.exit方法，导致进程强行退出。
在idea下运行Junit的类是org.eclipse.jdt.internal.junit.runner.RemoteTestRunner。
通过查看这个类的main方法。如下：

public static void main(String  [] args) {
	try {
		RemoteTestRunner testRunServer= new RemoteTestRunner();
		testRunServer.init(args);
		testRunServer.run();
	} catch (Throwable   e) {
		e.printStackTrace(); // don't allow System.exit(0) to swallow exceptions
	} finally {
		// fix for 14434
		System.exit(0);
	}
}

显然，只要主线程结束，整个程序将会退出，这就是采用junit的时候奇怪退出程序的原因。