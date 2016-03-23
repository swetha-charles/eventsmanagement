package server;
/**
 * an interface for the runnable tasks that will be put into the threadpool
 * @author Mark
 *
 */
public interface ExecutableTask extends Runnable{

	public void run();

}
