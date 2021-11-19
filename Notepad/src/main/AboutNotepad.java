import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AboutNotepad implements ActionListener {

    private JDialog frame;

    AboutNotepad(JFrame parent) {
        JButton okButton;
        ImageIcon intern;
        ImageIcon notepad;
        Image int_img;
        Image notepad_img;
        JLabel l1;
        JLabel l2;
        JLabel l3;

        frame = new JDialog();
        frame.setTitle("About Notepad");
        frame.setSize(400, 500);
        frame.setLayout(null);

        intern = new ImageIcon(getClass().getResource("icons/o_internal_labs.png"));
        int_img = intern.getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT);
        intern = new ImageIcon(int_img);
        l1 = new JLabel(intern);
        l1.setBounds(140, 40, 120, 100);
        frame.add(l1);

        notepad = new ImageIcon(getClass().getResource("icons/notepad.png"));
        frame.setIconImage(notepad.getImage());
        notepad_img = notepad.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        notepad = new ImageIcon(notepad_img);
        l2 = new JLabel(notepad);
        l2.setBounds(25, 180, 50, 50);
        frame.add(l2);


        l3 = new JLabel("<html>Intern labs java 3.0<br>Notepad by " +
                "Group_2<br>2021<br><br>Notepad is a word processing program, " +
                "<br>which allows changing of text in a computer<br>" +
                " file. Notepad is a simple text editor for basic text-editing program" +
                "<br> which enables computer users to create documents. </html>");
        l3.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        l3.setBounds(85, 160, 300, 230);
        frame.add(l3);

        okButton = new JButton("OK");
        okButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 10));
        okButton.setBounds(320, 400, 50, 20);
        okButton.addActionListener(this);

        frame.add(okButton);
        frame.setLocationRelativeTo(parent);
        frame.setModal(true);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        frame.dispose();
    }

}
