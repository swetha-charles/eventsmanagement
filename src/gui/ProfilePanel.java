package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import client.Client;
import model.Model;
import model.ModelState;

public class ProfilePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Client controller = null;
	private Model model;
	
	JLabel hello;
	JPanel detailsPanel = new JPanel();
	JLabel details = new JLabel("Details");
	JLabel name = new JLabel("Name");
	JLabel email = new JLabel("Email");

	JLabel nameA;
	JLabel emailA;
	
	JButton editDetails = new JButton("Edit Details");
	JButton editPassword = new JButton("Change Pasword");
	
	
	public ProfilePanel(Client controller, Model model){
		
		this.controller = controller;
		this.model = model;
		
		//here we need it to get the information from the database
		nameA = new JLabel(model.getFirstName() + " " + model.getLastname());
		emailA = new JLabel(model.getEmail());
		hello = new JLabel("Hello "+ model.getUsername() +"!");
		
		Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setPreferredSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-70));
	
		hello.setForeground(Color.DARK_GRAY);
		details.setForeground(Color.GRAY);
		name.setForeground(Color.DARK_GRAY);
		nameA.setForeground(Color.DARK_GRAY);
		email.setForeground(Color.DARK_GRAY);
		emailA.setForeground(Color.DARK_GRAY);
		
		hello.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
		details.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		name.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		nameA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		email.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		emailA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		editDetails.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		editPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		
		detailsPanel.setLayout(new GridLayout(3,2));
		detailsPanel.setPreferredSize(new Dimension(700,100));
		detailsPanel.add(name);
		detailsPanel.add(nameA);
		detailsPanel.add(email);
		detailsPanel.add(emailA);
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		add(hello);
		add(details);
		add(detailsPanel);
		add(editDetails);
		add(editPassword);
		
		layout.putConstraint(SpringLayout.WEST, hello, 70, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, hello, 50, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, detailsPanel, 80, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, detailsPanel, 20, SpringLayout.SOUTH, details);
		
		layout.putConstraint(SpringLayout.WEST, details, 70, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, details, 20, SpringLayout.SOUTH, hello);
		
		layout.putConstraint(SpringLayout.WEST, editDetails, 70, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, editDetails, 20, SpringLayout.SOUTH, detailsPanel);
		
		layout.putConstraint(SpringLayout.WEST, editPassword, 10, SpringLayout.EAST, editDetails);
		layout.putConstraint(SpringLayout.NORTH, editPassword, 20, SpringLayout.SOUTH, detailsPanel);
		
		//--------------------Listeners-----------------------//
		
		editDetails.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.changeCurrentState(ModelState.EDIT);
			}
		});
		
		editPassword.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.changeCurrentState(ModelState.PASSWORD);
			}
		});
	}
	
//	public static void main(String[] args) {
//		
//		JFrame frame = new JFrame();
//		Client controller = new Client();
//		
//		ProfilePanel menu = new ProfilePanel(controller);
//		
//		JFrame.setDefaultLookAndFeelDecorated(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setContentPane(menu);
//		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
//		frame.setResizable(true);
//		frame.setVisible(true);
//	}

}

