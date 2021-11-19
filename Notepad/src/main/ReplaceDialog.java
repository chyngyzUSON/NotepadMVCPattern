import javax.swing.*;

public class ReplaceDialog {

    private JDialog replaceDialog;
    private JTextField searchTextField;
    private JTextField replaceWithTextField;

    public ReplaceDialog(Controller controller, int locationX, int locationY) {
        replaceDialog = new JDialog();
        replaceDialog.setTitle("Word replacement");
        replaceDialog.setLocation(locationX, locationY);

        JLabel wordLabel = new JLabel("Find");
        wordLabel.setSize(wordLabel.getPreferredSize().width, wordLabel.getPreferredSize().height);
        wordLabel.setLocation(20, 20);
        replaceDialog.add(wordLabel);

        searchTextField = new JTextField();
        searchTextField.setSize(100, 20);
        searchTextField.setLocation(10, 50);
        replaceDialog.add(searchTextField);

        JLabel replacementWordLabel = new JLabel("Replace to");
        replacementWordLabel.setSize(replacementWordLabel.getPreferredSize().width,
                replacementWordLabel.getPreferredSize().height);
        replacementWordLabel.setLocation(160, 20);
        replaceDialog.add(replacementWordLabel);


        replaceWithTextField = new JTextField();
        replaceWithTextField.setSize(100, 20);
        replaceWithTextField.setLocation(160, 50);
        replaceDialog.add(replaceWithTextField);

        JButton button = new JButton("Replace");
        button.setSize(80, 20);
        button.setLocation(100, 100);
        button.addActionListener(controller);
        button.setActionCommand("Replace");
        replaceDialog.add(button);

        replaceDialog.setLayout(null);
        replaceDialog.setSize(300, 200);
        replaceDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        replaceDialog.setModal(true);
        replaceDialog.setVisible(true);
        replaceDialog.setResizable(false);

    }

    public String getSearchWord() {
        return searchTextField.getText();
    }

    public String getReplacementWord() {
        return replaceWithTextField.getText();
    }

    public void close() {
        replaceDialog.dispose();
        replaceDialog = null;
        searchTextField = null;
        replaceWithTextField = null;
    }
}
