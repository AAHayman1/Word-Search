import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WordSearcher {
	
	 List<String> words = new ArrayList();

	
	public WordSearcher(String x) {
		
		
		try {
			
			words = Files.readAllLines(Paths.get(x), StandardCharsets.UTF_8);
			
			words.replaceAll(String::toUpperCase);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public  List<Integer> search (String y){
		
		List<Integer> linesFound = new ArrayList();
		
		y = y.toUpperCase();
		
		for(int i = 0; i < words.size(); i++) {
			
			String a = words.get(i);
			
			if(a.indexOf(y) >= 0) {
				
				linesFound.add(i);
				
			}
			
		}
		
		return linesFound;
		
	}

	public static void main(String[] args) {
		
		WordSearcher test = new WordSearcher("hamlet.txt");
		
		/*List<Integer> lineList = new ArrayList(); 
		
		
		lineList = test.search("Denmark");									//Testing Word Searcher
		
		for(int x : lineList) {
			
			System.out.println(x);
		}*/
		
		
		ServerSocket server = null;
		boolean shutdown = false;
		
		try {
			
			server = new ServerSocket(1236);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			System.exit(-1);
		}
		
		while(!shutdown) {
			
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;
			
			try {
				
				client = server.accept();
				input = client.getInputStream();
				output = client.getOutputStream();
				
				int n = input.read();
				byte[] data = new byte[n];
				input.read(data);
				
				String clientInput = new String(data, StandardCharsets.UTF_8);
				
				System.out.println(clientInput);									//Testing if client input is sent
				
				WordSearcher lookUp = new WordSearcher("hamlet.txt");
				
				List<Integer> lines = new ArrayList();
				
				lines = lookUp.search(clientInput);

				
				for(int x : lines) {
					
					System.out.println(x);											//Testing if client input was correctly searched
				}
				
				for(int x : lines) {
				
					String response = Integer.valueOf(x).toString() + "\n";
					
					output.write(response.getBytes());
				}
				
				client.close();
				
				if(clientInput.equalsIgnoreCase("shutdown")) {
					
					System.out.println("Shutting down....");
					
					shutdown = true;
				}
				 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		
		

	}

}
