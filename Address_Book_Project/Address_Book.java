import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalComboBoxButton;
import javax.swing.table.JTableHeader;

public class Address_Book {

  String class_Name = "org.postgresql.Driver";
  String con_url    = "jdbc:postgresql:<your_database_name>"
  String con_user   = "<your_username>"
  String con_pass   = "<your_passwrod>"

  Connection  con = null;
  Statement   stm = null;
  ResultSet   res = null;
  String      sql = null;

  JFrame              jf1;
  JPanel              jp1, jp2, jp3;
  JLabel              jl1, jl2, jl3, jl4, jl5;
  JButton             jb1, jb2, jb3, jb4, jb5;
  JComboBox<String>   jc1;
  JTextField          jt1, jt2, jt3, jt4, jt5;
  MetalComboBoxButton jm1;
  ImageIcon           ji1;

  String str = "'@*()-+_[]{}|:,<.>", pid = "";
  char   cha;
    
  Address_Book() {  
    try {

      // ! ESTABLISHING DATABASE CONNECTION
      Class.forName(class_Name);
      con = DriverManager.getConnection(con_url, con_user, con_pass);
      stm = con.createStatement();

      // ! CREATING TABLE IF NOT EXISTS
      sql = "CREATE TABLE IF NOT EXISTS address_book(person_id SERIAL, person_name VARCHAR(50), person_phone VARCHAR(50), person_email VARCHAR(50), person_address VARCHAR(50), PRIMARY KEY(person_id))";
      stm.executeUpdate(sql);

      // ! DEFINING COMPONENTS
      ji1 = new ImageIcon("Icon.png");
      jf1 = new JFrame("YOUR ADDRESS BOOK");

      jp1 = new JPanel(new GridLayout(0, 2, -270, 0));        
      jp2 = new JPanel(new GridLayout(4, 2, -270, 10));       
      jp3 = new JPanel();  
      
      jl1 = new JLabel("Search");
      jl2 = new JLabel("Name");
      jl3 = new JLabel("Phone");
      jl4 = new JLabel("Email");
      jl5 = new JLabel("Address");
      
      jb1 = new JButton("ADD");
      jb2 = new JButton("UPDATE");
      jb3 = new JButton("DELETE");
      jb4 = new JButton("CLEAR");
      jb5 = new JButton("LIST");
      
      jc1 = new JComboBox<String>();

      jt1 = (JTextField) jc1.getEditor().getEditorComponent();
      jt2 = new JTextField();
      jt3 = new JTextField();
      jt4 = new JTextField();
      jt5 = new JTextField();

      jm1 = (MetalComboBoxButton) jc1.getComponent(0);

      // ! ADDING PROPERTIES TO COMPONENTS
      jf1.setIconImage(ji1.getImage());
      jf1.setLayout(new BorderLayout()); 
      jf1.setBackground(Color.WHITE);
      jf1.addWindowListener(new WindowAdapter() { public void windowOpened(WindowEvent e) {  jt2.requestFocus();  }});
      
      jp1.setBackground(Color.BLACK);  
      jp1.setBorder(new EmptyBorder(10, 20, 10, 20));
      jp2.setBorder(new EmptyBorder(10, 20, 10, 20));
      jp3.setBackground(Color.BLACK);      

      jl1.setFont(new Font("Rockwell"  , Font.PLAIN, 20));    jl1.setForeground(Color.WHITE);
      jl2.setFont(new Font("Rockwell"  , Font.PLAIN, 20));    jl2.setForeground(Color.BLACK); 
      jl3.setFont(new Font("Rockwell"  , Font.PLAIN, 20));    jl3.setForeground(Color.BLACK);
      jl4.setFont(new Font("Rockwell"  , Font.PLAIN, 20));    jl4.setForeground(Color.BLACK);
      jl5.setFont(new Font("Rockwell"  , Font.PLAIN, 20));    jl5.setForeground(Color.BLACK);

      jb1.setBackground(Color.WHITE);    jb1.setCursor(new Cursor(Cursor.HAND_CURSOR));    jb1.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {  actionManager(2);  } public void mouseEntered(MouseEvent e) {  jb1.setBackground(Color.GRAY);  } public void mouseExited(MouseEvent e) {  jb1.setBackground(Color.WHITE);  }});    
      jb2.setBackground(Color.WHITE);    jb2.setCursor(new Cursor(Cursor.HAND_CURSOR));    jb2.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {  actionManager(3);  } public void mouseEntered(MouseEvent e) {  jb2.setBackground(Color.GRAY);  } public void mouseExited(MouseEvent e) {  jb2.setBackground(Color.WHITE);  }});      
      jb3.setBackground(Color.WHITE);    jb3.setCursor(new Cursor(Cursor.HAND_CURSOR));    jb3.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {  actionManager(4);  } public void mouseEntered(MouseEvent e) {  jb3.setBackground(Color.GRAY);  } public void mouseExited(MouseEvent e) {  jb3.setBackground(Color.WHITE);  }});
      jb4.setBackground(Color.WHITE);    jb4.setCursor(new Cursor(Cursor.HAND_CURSOR));    jb4.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {  actionManager(5);  } public void mouseEntered(MouseEvent e) {  jb4.setBackground(Color.GRAY);  } public void mouseExited(MouseEvent e) {  jb4.setBackground(Color.WHITE);  }});
      jb5.setBackground(Color.WHITE);    jb5.setCursor(new Cursor(Cursor.HAND_CURSOR));    jb5.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {  actionManager(6);  } public void mouseEntered(MouseEvent e) {  jb5.setBackground(Color.GRAY);  } public void mouseExited(MouseEvent e) {  jb5.setBackground(Color.WHITE);  }});
      
      jm1.removeMouseListener(jm1.getMouseListeners()[1]);    jm1.setEnabled(false);

      jc1.setEditable(true);    jc1.setBackground(Color.WHITE);    jc1.setCursor(new Cursor(Cursor.TEXT_CURSOR));    jc1.setBorder(new LineBorder(Color.WHITE, 2));    jc1.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 16));

      jt1.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) {  cha = e.getKeyChar();  if (cha == KeyEvent.VK_ENTER)  actionManager(1);  else if (Character.isLetterOrDigit(cha) || Character.isWhitespace(cha) || str.contains(Character.toString(cha))  || cha == KeyEvent.VK_BACK_SPACE) actionManager(0);  }});
      jt1.addFocusListener(new FocusListener() { public void focusGained(FocusEvent e) {  jt1.setText("");  } public void focusLost(FocusEvent e) {  jt1.setText("Search for a Name & Press Enter");  }});

      jt2.setBackground(Color.WHITE);    jt2.setCursor(new Cursor(Cursor.TEXT_CURSOR));    jt2.setBorder(new LineBorder(Color.BLACK, 2));    jt2.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 16));
      jt3.setBackground(Color.WHITE);    jt3.setCursor(new Cursor(Cursor.TEXT_CURSOR));    jt3.setBorder(new LineBorder(Color.BLACK, 2));    jt3.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 16));
      jt4.setBackground(Color.WHITE);    jt4.setCursor(new Cursor(Cursor.TEXT_CURSOR));    jt4.setBorder(new LineBorder(Color.BLACK, 2));    jt4.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 16));
      jt5.setBackground(Color.WHITE);    jt5.setCursor(new Cursor(Cursor.TEXT_CURSOR));    jt5.setBorder(new LineBorder(Color.BLACK, 2));    jt5.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 16));

      // ! ADDING COMPONENTS TO PANELS
      jp1.add(jl1);    jp1.add(jc1);

      jp2.add(jl2);    jp2.add(jt2);
      jp2.add(jl3);    jp2.add(jt3);
      jp2.add(jl4);    jp2.add(jt4);
      jp2.add(jl5);    jp2.add(jt5);

      jp3.add(jb1);
      jp3.add(jb2);
      jp3.add(jb3);
      jp3.add(jb4);
      jp3.add(jb5);

      // ! ADDING PANELS TO FRAME 
      jf1.add(jp1, BorderLayout.NORTH);
      jf1.add(jp2, BorderLayout.CENTER);
      jf1.add(jp3, BorderLayout.SOUTH);

      // ! FRAME OPERATIONS
      jf1.setResizable(false);
      jf1.setSize(500, 280);
      jf1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jf1.setLocationRelativeTo(null);
      jf1.setVisible(true);
    
    } catch(Exception e) { JOptionPane.showMessageDialog(jf1, "EXCEPTION : " + e, "AN EXCEPTION OCCURED", JOptionPane.ERROR_MESSAGE); }
  }

  protected void actionManager(int actionType) {
    
    String  st1, st2, st3, st4, rows[][], cols[] = {"No.", "Name", "Phone", "Email", "Address"};
    int     num, row_count = 0, row = 0, index = 1;

    JDialog      jdl;
    JTable       jtb;
    JScrollBar   jsb;
    JScrollPane  jsp;
    JTableHeader jth;
    
    try {
      switch (actionType) { 

        // ! SEARCH DROP DOWN
        case 0: st1 = jt1.getText();
                st2 = st1.replaceAll("'", "''");  
                num = 1;
                
                jc1.removeAllItems();
                
                sql = "SELECT person_name FROM address_book WHERE person_name LIKE '%" + st2 + "%' ORDER BY person_name ASC";
                res = stm.executeQuery(sql);

                jc1.addItem(st1);
                while (res.next()) { jc1.addItem(res.getString(1)); num += 1; }

                jc1.setMaximumRowCount((st1.equals("")) ? 7 : (num > 7) ? 7 : num);
                jc1.showPopup();
        break;

        // ! SEARCH ENTER
        case 1: st1 = jt1.getText().replaceAll("'", "''");

                sql = "SELECT * FROM address_book WHERE person_name = '" + st1 + "' LIMIT 1";
                res = stm.executeQuery(sql);

                actionManager(5);
                
                while (res.next()) {
                  pid = res.getString(1);

                  jt2.setText(res.getString(2));
                  jt3.setText(res.getString(3));
                  jt4.setText(res.getString(4));
                  jt5.setText(res.getString(5));
                }

                jt2.requestFocus();
        break;

        // ! ADD
        case 2: st1 = jt2.getText().replace("'", "''");
                st2 = jt3.getText().replace("'", "''");
                st3 = jt4.getText().replace("'", "''");
                st4 = jt5.getText().replace("'", "''");

                if      (st1.equals("") || st1.equals(" "))                           JOptionPane.showMessageDialog(jf1, "Name must have a Visible character.", "WARNING", JOptionPane.WARNING_MESSAGE);
                else if (st2.equals("") && st3.equals("") && st4.equals("")) JOptionPane.showMessageDialog(jf1, "Contact must atleast contain a Phone, Email or Address.", "WARNING", JOptionPane.WARNING_MESSAGE);
                else {
                  sql = "INSERT INTO address_book(person_name, person_phone, person_email, person_address) VALUES('" + st1  + "', '" + st2  + "', '" + st3  + "', '" + st4  + "')";
                
                  if (stm.executeUpdate(sql) == 0) JOptionPane.showMessageDialog(jf1, "Contact was Not Added.\nDatabase Error.", "FAILURE", JOptionPane.ERROR_MESSAGE);
                  else                             JOptionPane.showMessageDialog(jf1, "Contact Added Successfully.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                  
                  sql = "SELECT person_id FROM address_book WHERE person_name = '" + st1 + "' AND person_phone = '" + st2 + "' AND person_email = '" + st3 + "' AND person_address = '" + st4 + "' ORDER BY person_id ASC LIMIT 1";
                  res = stm.executeQuery(sql);

                  while (res.next()) { pid = res.getString(1); }
                }        
        break;
        
        // ! UPDATE
        case 3: if (pid.equals("")) JOptionPane.showMessageDialog(jf1, "Contact must exists in Database in order to Update it.", "WARNING", JOptionPane.WARNING_MESSAGE);
                else {

                  st1 = jt2.getText().replace("'", "''");
                  st2 = jt3.getText().replace("'", "''");
                  st3 = jt4.getText().replace("'", "''");
                  st4 = jt5.getText().replace("'", "''");

                  if      (st1.equals("") || st1.equals(" "))                           JOptionPane.showMessageDialog(jf1, "Name must have a Visible character.", "WARNING", JOptionPane.WARNING_MESSAGE);
                  else if (st2.equals("") && st3.equals("") && st4.equals("")) JOptionPane.showMessageDialog(jf1, "Contact must atleast contain a Phone, Email or Address.", "WARNING", JOptionPane.WARNING_MESSAGE);
                  else {
                    sql = "UPDATE address_book SET person_name = '" + st1  + "', person_phone = '" + st2  + "', person_email = '" + st3  + "', person_address = '" + st4  + "' WHERE person_id = '" + pid + "'";
                
                    if (stm.executeUpdate(sql) == 0) JOptionPane.showMessageDialog(jf1, "Contact was Not Updated.\nDatabase Error.", "FAILURE", JOptionPane.ERROR_MESSAGE);
                    else                             JOptionPane.showMessageDialog(jf1, "Contact Updated Successfully.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                  }
                }
        break;

        // ! DELETE
        case 4: if (pid.equals("")) JOptionPane.showMessageDialog(jf1, "Contact is not Added to the Database yet.", "WARNING", JOptionPane.WARNING_MESSAGE);
                else {
                  sql = "DELETE FROM address_book WHERE person_id = '" + pid  + "'";
                
                  if (stm.executeUpdate(sql) == 0) JOptionPane.showMessageDialog(jf1, "Contact was Not Deleted.\nDatabase Error.", "FAILURE", JOptionPane.ERROR_MESSAGE);
                  else                             JOptionPane.showMessageDialog(jf1, "Contact Deleted Successfully.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);

                  actionManager(5);
                }
        break;

        // ! CLEAR
        case 5: jc1.removeAllItems();
                jc1.addItem("Search for a Name & Press Enter");

                jt2.setText("");
                jt3.setText("");
                jt4.setText("");
                jt5.setText("");

                pid = "";
        break;
        
        // ! LIST
        case 6: sql = "SELECT COUNT(person_name) FROM address_book";
                res = stm.executeQuery(sql);

                while (res.next()) { row_count = res.getInt(1); }
                rows = new String[row_count][5];

                sql = "SELECT * FROM address_book ORDER BY person_name ASC";
                res = stm.executeQuery(sql);

                while (res.next()) {

                  rows[row][0] = Integer.toString(index);
                  rows[row][1] = res.getString(2);
                  rows[row][2] = res.getString(3);
                  rows[row][3] = res.getString(4);
                  rows[row][4] = res.getString(5);

                  index += 1;
                  row += 1;
                }

                jdl = new JDialog();
                jtb = new JTable(rows, cols);
                jsp = new JScrollPane(jtb);

                jth = jtb.getTableHeader();
                jsb = jsp.getVerticalScrollBar();

                jdl.setLayout(new BorderLayout());
                jdl.setModal(true);
                jdl.setResizable(false);

                jtb.setDefaultEditor(Object.class, null);
                jtb.setBackground(Color.WHITE);
                jtb.setCursor(new Cursor(Cursor.HAND_CURSOR));
                jtb.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 16));
                jtb.setRowHeight(25);

                jtb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                jtb.setDragEnabled(false);
                jtb.getColumnModel().getColumn(0).setPreferredWidth(50);
                jtb.getColumnModel().getColumn(1).setPreferredWidth(250);
                jtb.getColumnModel().getColumn(2).setPreferredWidth(250);
                jtb.getColumnModel().getColumn(3).setPreferredWidth(350);
                jtb.getColumnModel().getColumn(4).setPreferredWidth(350);

                jth.setBackground(Color.BLACK);
                jth.setForeground(Color.WHITE);
                jth.setFont(new Font("Rockwell", Font.PLAIN, 20));
                jth.setReorderingAllowed(false);

                jsp.setBorder(new LineBorder(Color.BLACK, 2));
                jsb.setBackground(Color.BLACK);
                jsb.setPreferredSize(new Dimension(14, 0));
            
                jdl.add(jsp, BorderLayout.CENTER);          
                jdl.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                jdl.setSize(1282, 500);
                jdl.setLocationRelativeTo(null);
                jdl.setVisible(true);                
        break;
      }
    } catch(Exception ex) { JOptionPane.showMessageDialog(jf1, "EXCEPTION : " + ex, "AN EXCEPTION OCCURED", JOptionPane.ERROR_MESSAGE); }
    return;
  }

  public static void main(String[] args) { new Address_Book(); }
}