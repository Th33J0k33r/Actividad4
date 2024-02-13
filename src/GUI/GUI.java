package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument.Iterator;

import java.io.*;

public abstract class GUI implements ActionListener {
	
	private static JLabel telefonoLabel, nombreLabel, successLabel;
	private static JTextField telefonoText, nombreText;
	//private static JPasswordField passwordText;
	private static JButton listButton, createButton, deleteButton, addButton;
	private static JPanel panel;
	static String partes[] = null;
	private static JFrame frame;
	private static Map<String, String> addressBook = new HashMap<>();
	
	
	
	public static void main(String[] args) {

		frame = new JFrame();
		panel = new JPanel();
		
		frame.setSize(350,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(panel);//, BorderLayout.CENTER);
		frame.setTitle("Agenda Telefonica");
		
		panel.setLayout(null);
		
		listButton = new JButton("List Contacts"); listButton.setBounds(10, 10, 130, 25); listButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					readBook();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
            }

        });		
		panel.add(listButton);
		
		createButton = new JButton("Create Contact"); createButton.setBounds(10, 40, 130, 25); 
		createButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					crearContacto(Load());
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }

        });	
		panel.add(createButton);
		
		deleteButton = new JButton("Delete Contact"); deleteButton.setBounds(10, 70, 130, 25); deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	borrarContacto();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }

        });		
		panel.add(deleteButton);
		frame.setVisible(true);
	}

	
	
	public static Map<String, String> Load() throws FileNotFoundException{
		File file = new File("C:\\Pp\\AddBook.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "", partes1[] = null;
		
		try {
			while((line = br.readLine()) != null) {
				partes1 = line.split(",");
				addressBook.put(partes1[0],partes1[1]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addressBook;
	}
	
	public static void readBook() throws FileNotFoundException{
		String data ="Contactos: ";
		
		File file = new File("C:\\Pp\\AddBook.txt");
		Scanner sc = new Scanner(file);
		
		while(sc.hasNext()) {
			partes = sc.nextLine().split(",");
			addressBook.put(partes[0],partes[1]);
			data = data + "\n" + partes[0] + ", " + partes[1];
		}
		JTextArea textArea = new JTextArea(5, 20);
		//JScrollPane scrollPane = new JScrollPane(textArea); 
		
		frame.setVisible(false);
		textArea.setText(data);
		textArea.setEditable(false);
		textArea.setBounds(10, 105, 180, 100);
		panel.add(textArea);
		frame.setVisible(true);
		sc.close();
	}
	
public static void crearContacto(Map<String, String> addressBook1) throws FileNotFoundException {
		
		telefonoLabel = new JLabel("Telefono: "); telefonoLabel.setBounds(10,105,80,25); panel.add(telefonoLabel);
		telefonoText = new JTextField(); telefonoText.setBounds(100,105,165,25); panel.add(telefonoText);
		
		nombreLabel = new JLabel("Nombre: "); nombreLabel.setBounds(10,135,80,25); panel.add(nombreLabel);
		nombreText = new JTextField(); nombreText.setBounds(100,135,165,25); panel.add(nombreText);
		
		successLabel = new JLabel(""); successLabel.setBounds(10,200,150,25); panel.add(successLabel);
		
		//successLabel
		addButton = new JButton("Add Contact"); addButton.setBounds(120, 170, 130, 25); addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	final String telefono = telefonoText.getText();
            	final String nombre = nombreText.getText();
            	if (telefono.isEmpty() || nombre.isEmpty()) {
            		successLabel.setText("Falta telefono/nombre");
            	} else {
            		successLabel.setText("");
            		addressBook1.put(telefono, nombre);
            		JOptionPane.showMessageDialog(null, "New contact was added");
            	}
            }
        });		
		panel.add(addButton);
		File file = new File("C:\\Pp\\AddBook.txt");
		PrintWriter pw = new PrintWriter(file);
			
        for (HashMap.Entry<String, String> entry : addressBook1.entrySet()) {
        	String phone = entry.getKey();
            String name = entry.getValue();
            //System.out.println(phone + " ," + name);
            
            pw.println(phone + " , " + name);
            pw.close();
		}
	}
	
	public static void borrarContacto() throws FileNotFoundException {
		addressBook = Load();
		telefonoLabel = new JLabel("Telefono: "); telefonoLabel.setBounds(10,105,80,25); panel.add(telefonoLabel);
		telefonoText = new JTextField(); telefonoText.setBounds(100,105,165,25); panel.add(telefonoText);
		successLabel = new JLabel(""); successLabel.setBounds(10,200,190,25); panel.add(successLabel);
		addButton = new JButton("Delete Contact"); addButton.setBounds(120, 170, 130, 25); addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	final String telefono = telefonoText.getText();
            	
            	if (telefono.isEmpty()) {
            		successLabel.setText("Ingresa el telefono a borrar");
            	} else {
            		if(addressBook.containsKey(telefono)) {
            			addressBook.remove(telefono);
            			JOptionPane.showMessageDialog(null, "Contact was Deleted");
            			successLabel.setText("");
            		} else {
            			successLabel.setText("Ingresa un telefono existente");
            		}
            		
            		File file = new File("C:\\Pp\\AddBook.txt");
            		PrintWriter pw = null;
					try {
						pw = new PrintWriter(file);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            			
                    for (HashMap.Entry<String, String> entry : addressBook.entrySet()) {
                    	String phone = entry.getKey();
                        String name = entry.getValue();
                        //System.out.println(phone + " ," + name);
                        
                        pw.println(phone + " , " + name);
                        pw.close();
            		}
              	}
            }
        });	
		panel.add(addButton);
	}

}