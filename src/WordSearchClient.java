import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javax.swing.*;

public class WordSearchClient{

	public JLabel word;
	public JTextField text;
	public JLabel response;
	public JScrollPane scrollableList;
	public JList list;
	public JLabel no;
	public JButton transmit;
	public DefaultListModel<Integer> listModel = new DefaultListModel();
	
	public void search(){
		
		word = new JLabel("Word to Search For:");
		text = new JTextField();
		response = new JLabel("Word / Phrase Was Found on Lines:");
		list = new JList<Integer>(listModel);
		scrollableList = new JScrollPane(list);
		//no = new JLabel("");
		transmit = new JButton("Transmit");
		
		transmit.addActionListener(new MyButtonListener(this));

		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Word Searcher");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		//GridLayout grid = new GridLayout(3,2);
		
		//scrollableList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
	     scrollableList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
		 
	     JPanel top = new JPanel(new GridLayout(1,2));
		
		 top.add(word);
		 top.add(text);
		 
		 frame.add(top, BorderLayout.NORTH);
		 
	     JPanel content = new JPanel(new GridLayout(1,2));

		 content.add(response);
		 content.add(scrollableList);
		 
		 frame.add(content,BorderLayout.CENTER);
		 
		 //frame.add(no, BorderLayout.SOUTH);
		 frame.add(transmit, BorderLayout.SOUTH);
		 
		// frame.add(bottom);
		 frame.pack();
		 frame.setVisible(true);
	}
	
	

	
	public static void main(String[] args) {

		
		WordSearchClient test = new WordSearchClient();
		
		test.search();
		 
	}
}
	
		
	



class MyButtonListener implements ActionListener {
	
	WordSearchClient fr;
	
	public MyButtonListener(WordSearchClient frame)
	
	{
		fr = frame;
	}

	public void actionPerformed(ActionEvent e) 
	
	{
		JButton btn = (JButton) e.getSource();
		
		fr.listModel.clear();
		String text = fr.text.getText();
		
		
		try {
			
			String userString = text; 
			Socket connection = new Socket("127.0.0.1", 1236);
			
			//Testing if connection was made
			if(!connection.isClosed()) {
				System.out.println("Connected");
			}
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			OutputStream output = connection.getOutputStream();
			
			//Writes user input to the server
			output.write(userString.length());
			output.write(userString.getBytes());
			
			System.out.println(userString);
			
			String response;
			
				while((response = reader.readLine()) != null) {
					
					System.out.println(response);			//Testing output from server if reaches client

					if(response != null) {
						
					fr.listModel.addElement(Integer.parseInt(response));
					
					}
					}
					
			if(!connection.isClosed()) {
					connection.close();
					}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		
		
		
		
	}
}





