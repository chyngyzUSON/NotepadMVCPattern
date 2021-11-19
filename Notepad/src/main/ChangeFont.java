import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeFont {

    private JLabel fontLabel;
    private JButton fontColor;
    private JDialog dialog;
    private JButton done;
    private JComboBox<String> fontBox;
    private JComboBox<Integer> fontSize;

    public ChangeFont(JFrame frame, JTextArea textArea) {

        fontLabel = new JLabel("Font: ");

        int maxFont = 40;
        Integer[] fontInt = new Integer[maxFont];
        int minFont = 8;
        for (int i = 0; i < maxFont; i++) {
            fontInt[i] = minFont;
            minFont += 2;
        }

        String[] fonts = {"Arial", "Serif", "Sansserif", "Monospaced"};

        fontSize = new JComboBox<Integer>(fontInt);
        fontSize.setEditable(true);

        fontColor = new JButton("Color");
        fontColor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource().equals(fontColor)) {
                    JColorChooser colorChooser = new JColorChooser();

                    Color color = colorChooser.showDialog(null, "Choose a color", Color.black);

                    textArea.setForeground(color);
                }

            }

        });
        fontBox = new JComboBox<String>(fonts);

        done = new JButton("OK");
        done.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource().equals(done)) {

                    textArea.setFont(
                            new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));

                    textArea.setFont(
                            new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSize.getSelectedItem()));

                    dialog.dispose();
                }

            }

        });


        dialog = new JDialog();
        dialog.setSize(500, 200);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new FlowLayout());
        dialog.setTitle("Font");
        dialog.setFocusable(true);
        dialog.add(fontLabel);
        dialog.add(fontSize);
        dialog.add(fontColor);
        dialog.add(fontBox);
        dialog.add(done);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

}
