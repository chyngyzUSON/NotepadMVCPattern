import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;

public class View {
    private Controller controller;
    private JFrame frame;
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JScrollPane scroll;
    private JLabel statusLabel;
    private JPanel statusBar;
    private ReplaceDialog replaceDialog;
    private UndoManager undoManager;
    private ChangeFont changeFont;

    public View() {
        controller = new Controller(this);
        createGUI();
        frame.setVisible(true);
    }

    public void createGUI() {
        frame = initJFrame();

        textArea = new JTextArea();
        textArea.addKeyListener(controller);
        textArea.addMouseListener(controller);
        textArea.setFont(new Font("SANS_SERIF", Font.PLAIN, 24));
        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);
        textArea.getDocument().addDocumentListener(controller);
        textArea.setWrapStyleWord(true);

        statusBar = initStatusBar();

        fileChooser = new JFileChooser();
        fileChooser.addActionListener(controller);

        //addition horizontal and vertical scroll for textArea
        scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JMenu fileMenu = initFileMenu();

        JMenu editMenu = initEditMenu();

        JMenu formatMenu = initFormatMenu();

        JMenu viewMenu = initViewMenu();

        JMenu referenceMenu = initReferenceMenu();

        JMenuBar menuBar = new JMenuBar();

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(referenceMenu);
        frame.setJMenuBar(menuBar);
        frame.add(statusBar, BorderLayout.SOUTH);
        frame.add(scroll);
        frame.addWindowListener(controller);
    }

    private JMenu initFileMenu() {
        JMenu fileMenu = new JMenu("File");

        JMenuItem fileCreate = new JMenuItem("Create New");
        fileCreate.addActionListener(controller);
        fileCreate.setActionCommand("Create");
        fileCreate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        fileCreate.setIcon(getScaledImageIcon("icons/new.png"));

        JMenuItem fileOpen = new JMenuItem("Open");
        fileOpen.addActionListener(controller);
        fileOpen.setActionCommand("Open");
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        fileOpen.setIcon(getScaledImageIcon("icons/open.png"));

        JMenuItem fileSave = new JMenuItem("Save");
        fileSave.addActionListener(controller);
        fileSave.setActionCommand("Save");
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        fileSave.setIcon(getScaledImageIcon("icons/save.png"));

        JMenuItem fileSaveAs = new JMenuItem("Save as");
        fileSaveAs.addActionListener(controller);
        fileSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        fileSaveAs.setActionCommand("Save as");
        fileSaveAs.setIcon(getScaledImageIcon("icons/save_as.png"));

        JMenuItem filePrint = new JMenuItem("Print");
        filePrint.setActionCommand("Print");
        filePrint.addActionListener(controller);
        filePrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        filePrint.setIcon(getScaledImageIcon("icons/print.png"));

        JMenuItem fileExit = new JMenuItem("Exit");
        fileExit.addActionListener(controller);
        fileExit.setActionCommand("Exit");
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        fileExit.setIcon(getScaledImageIcon("icons/exit.png"));

        fileMenu.add(fileCreate);
        fileMenu.add(fileOpen);
        fileMenu.add(fileSave);
        fileMenu.add(fileSaveAs);
        fileMenu.add(filePrint);
        fileMenu.add(fileExit);
        return fileMenu;
    }

    private JMenu initEditMenu() {
        JMenu editMenu = new JMenu("Edit");

        JMenuItem editCancel = new JMenuItem("Undo");
        editCancel.setActionCommand("Cancel");
        editCancel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        editCancel.addActionListener(controller);
        editCancel.setIcon(getScaledImageIcon("icons/undo.png"));

        JMenuItem editForward = new JMenuItem("Redo");
        editForward.setActionCommand("Restore");
        editForward.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        editForward.addActionListener(controller);
        editForward.setIcon(getScaledImageIcon("icons/redo.png"));

        JMenuItem editCut = new JMenuItem("Cut");
        editCut.setActionCommand("Cut");
        editCut.addActionListener(controller);
        editCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        editCut.setIcon(getScaledImageIcon("icons/cut.png"));

        JMenuItem editCopy = new JMenuItem("Copy");
        editCopy.setActionCommand("Copy");
        editCopy.addActionListener(controller);
        editCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        editCopy.setIcon(getScaledImageIcon("icons/copy.png"));

        JMenuItem editPaste = new JMenuItem("Paste");
        editPaste.setActionCommand("Paste");
        editPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        editPaste.addActionListener(controller);
        editPaste.setIcon(getScaledImageIcon("icons/paste.png"));

        JMenuItem editRemove = new JMenuItem("Remove");
        editRemove.setActionCommand("Remove");
        editRemove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        editRemove.addActionListener(controller);
        editRemove.setIcon(getScaledImageIcon("icons/delete.png"));

        JMenuItem editFind = new JMenuItem("Find");
        editFind.setActionCommand("Find");
        editFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        editFind.addActionListener(controller);
        editFind.setIcon(getScaledImageIcon("icons/search.png"));

        JMenuItem editReplace = new JMenuItem("Replace");
        editReplace.setActionCommand("Replace Dialog");
        editReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        editReplace.addActionListener(controller);
        editReplace.setIcon(getScaledImageIcon("icons/replace.png"));

        JMenuItem editSelectAll = new JMenuItem("Select All");
        editSelectAll.setActionCommand("Select All");
        editSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        editSelectAll.addActionListener(controller);
        editSelectAll.setIcon(getScaledImageIcon("icons/select_all.png"));

        JMenuItem editTimeData = new JMenuItem("Time Data");
        editTimeData.setActionCommand("Time Data");
        editTimeData.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        editTimeData.addActionListener(controller);
        editTimeData.setIcon(getScaledImageIcon("icons/clock.png"));

        editMenu.add(editCancel);
        editMenu.add(editForward);
        editMenu.add(editCut);
        editMenu.add(editCopy);
        editMenu.add(editPaste);
        editMenu.add(editRemove);
        editMenu.add(editFind);
        editMenu.add(editReplace);
        editMenu.add(editSelectAll);
        editMenu.add(editTimeData);
        return editMenu;
    }

    private JMenu initFormatMenu() {
        JMenu formatMenu = new JMenu("Format");

        JMenuItem formatLineWrap = new JMenuItem("Line Wrap");
        formatLineWrap.setActionCommand("line-wrap");
        formatLineWrap.addActionListener(controller);
        formatLineWrap.setIcon(getScaledImageIcon("icons/line_wrap.png"));

        JMenuItem formatFont = new JMenuItem("Font");
        formatFont.setActionCommand("Font");
        formatFont.setIcon(getScaledImageIcon("icons/fonts.png"));
        formatFont.addActionListener(controller);

        formatMenu.add(formatLineWrap);
        formatMenu.add(formatFont);
        return formatMenu;
    }

    private JMenu initViewMenu() {
        JMenu viewMenu = new JMenu("View");
        JMenuItem viewStatusBar = new JMenuItem("Status Bar");
        viewStatusBar.addActionListener(controller);
        viewStatusBar.setActionCommand("Status Bar");
        viewStatusBar.setIcon(getScaledImageIcon("icons/status_bar.png"));
        viewMenu.add(viewStatusBar);
        return viewMenu;
    }

    private JMenu initReferenceMenu() {
        JMenu referenceMenu = new JMenu("About");
        JMenuItem referenceAbout = new JMenuItem("About Program");
        referenceAbout.setIcon(getScaledImageIcon("icons/about.png"));
        referenceAbout.setActionCommand("About Program");
        referenceAbout.addActionListener(controller);
        referenceMenu.add(referenceAbout);
        return referenceMenu;
    }

    private JFrame initJFrame() {
        JFrame frame = new JFrame("TextEditor");
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dimension.width / 2 - frame.getSize().width / 2, dimension.height / 2 - frame.getSize().height / 2);
        frame.setTitle("Notepad MVC");
        frame.setIconImage(getScaledImageIcon("icons/notepad.png").getImage());
        return frame;
    }

    private JPanel initStatusBar() {
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 16));
        statusPanel.setLayout(new BorderLayout());
        statusLabel = new JLabel();
        statusLabel.setHorizontalAlignment(JTextField.RIGHT);
        statusPanel.add(statusLabel, BorderLayout.EAST);
        statusPanel.setVisible(false);
        return statusPanel;
    }

    public void showReplaceDialog() {
        replaceDialog = new ReplaceDialog(
                controller,
                frame.getLocation().x + frame.getWidth() / 3,
                frame.getLocation().y + frame.getHeight() / 3);
    }

    public void showAboutNotepad() {
        new AboutNotepad(frame);
    }

    private ImageIcon getScaledImageIcon(String path) {
        return new ImageIcon(
                new ImageIcon(getClass().getResource(path))
                        .getImage()
                        .getScaledInstance(15, 15, Image.SCALE_SMOOTH)
        );
    }

    public void closeReplaceDialog() {
        replaceDialog.close();
        replaceDialog = null;
    }

    public String getSearchWord() {
        return replaceDialog.getSearchWord();
    }

    public String getReplacementWord() {
        return replaceDialog.getReplacementWord();
    }

    public String getText() {
        return textArea.getText();
    }

    public void exit() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public int showSaveDialog() {
        return fileChooser.showSaveDialog(frame);
    }

    public void showOpenDialog() {
        fileChooser.showOpenDialog(frame);
    }

    public File getSelectedFile() {
        return fileChooser.getSelectedFile();
    }

    public int getDialogType() {
        return fileChooser.getDialogType();
    }

    public void updateTextArea(String text) {
        textArea.setText(text);
    }

    public void cutTextArea() {
        textArea.getSelectedText();
        textArea.cut();
    }

    public void selectAllTextArea() {
        textArea.selectAll();
    }

    public int getCaretPosition() {
        return textArea.getCaretPosition();
    }

    public int getLineOfOffset(int offset) throws BadLocationException {
        return textArea.getLineOfOffset(offset);
    }

    public int getLineStartOffset(int line) throws BadLocationException {
        return textArea.getLineStartOffset(line);
    }

    public void setStatusBar() {
        statusBar.setVisible(!statusBar.isVisible());
    }

    public boolean getStatusBarVisible() {
        return statusBar.isVisible();
    }

    public void setStatusBarData(String text) {
        statusLabel.setText(text);
    }

    public String getSelectedText() {
        return textArea.getSelectedText();
    }

    public void insert(String str, int pos) {
        textArea.insert(str, pos);
    }

    public Font getFont() {
        return textArea.getFont();
    }

    public void updateTitle(String title) {
        frame.setTitle(title);
    }

    public int showSaveConfirmDialog() {
        return JOptionPane.showConfirmDialog(frame, "Do you want to save?");
    }

    public void switchLineWrap() {
        textArea.setLineWrap(!textArea.getLineWrap());
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public boolean canUndo() {
        return undoManager.canUndo();
    }

    public void undo() {
        undoManager.undo();
    }

    public boolean canRedo() {
        return undoManager.canRedo();
    }

    public void redo() {
        undoManager.redo();
    }

    public void showFindDialog() {
        new FindDialog(frame, textArea);
    }

    public void showFontDialog() {
        new ChangeFont(frame, textArea);
    }
}
