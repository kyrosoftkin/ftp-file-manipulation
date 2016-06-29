package FTPconnect;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

public class FTPconnect {
	private FTPClient ftpClient = new FTPClient();
	private String host;
	private String user;
	private String pass;
	
	public FTPconnect(String host, String user, String pass){
		this.host = host;
		this.user = user;
		this.pass = pass;
	}
	
	public FTPClient getClient(){
		return ftpClient;
	}
	
	public void openConnection(){
		try{	
			ftpClient.connect(this.host, 21);
			ftpClient.login(this.user, this.pass);
		} catch(IOException ex){
			System.out.println("Oops! Something is wrong!");
	        ex.printStackTrace();
		}
	}
	
	public void closeConnection(){
		try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
	}
}