import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class Controller extends WindowAdapter implements ActionListener, KeyListener, MouseListener, DocumentListener {
    private View view;
    private FileManager fileManager;
    private StatusBar statusBar;
    private Edit edit;
    private boolean isFileSaved;
    private int textHashcode;
    private ChangeFont changeFont;

    public Controller(View v) {
        this.view = v;
        fileManager = new FileManager();
        statusBar = new StatusBar(view);
        edit = new Edit(view);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Create":
                if (showSaveConfirmDialogAndSaveIfConfirmed() != JOptionPane.CANCEL_OPTION) {
                    fileManager.setPathToFile(null);
                    view.updateTextArea(null);
                }
                break;
            case "Exit":
                view.exit();
                break;
            case "Save":
                saveOrShowSaveDialog();
                break;
            case "Save as":
                view.showSaveDialog();
                break;
            case "ApproveSelection":
                fileManager.setPathToFile(view.getSelectedFile().getAbsolutePath());
                if (view.getDialogType() == JFileChooser.OPEN_DIALOG) {
                    String text = fileManager.readFromFile();
                    textHashcode = text.hashCode();
                    view.updateTextArea(text);
                } else {
                    saveOrShowSaveDialog();
                }
                break;
            case "Open":
                if (showSaveConfirmDialogAndSaveIfConfirmed() != JOptionPane.CANCEL_OPTION) {
                    view.showOpenDialog();
                }
                break;
            case "Status Bar":
                statusBar.toggleStatusBar();
                break;
            case "Print":
                new PrintWindow(view);
                break;
            case "Select All":
                edit.selectAll();
                break;
            case "Copy":
                edit.copy();
                break;
            case "Paste":
                edit.paste();
                break;
            case "Cut":
                edit.cut();
                break;
            case "Find":
                view.showFindDialog();
                break;
            case "Replace Dialog":
                edit.initReplaceDialog();
                break;
            case "Replace":
                edit.replace();
                break;
            case "line-wrap":
                view.switchLineWrap();
            case "Remove":
                edit.remove();
                break;
            case "Cancel":
                edit.makeUndo();
                break;
            case "Restore":
                edit.makeRedo();
                break;
            case "About Program":
                view.showAboutNotepad();
                break;
            case "Time Data":
                edit.timeDate();
                break;
            case "Font":
                view.showFontDialog();
                break;
        }
    }

    public void keyPressed(KeyEvent key) {
        update();
    }

    private void update() {
        statusBar.update();
    }

    public void keyTyped(KeyEvent e) {
        update();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        update();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        update();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        update();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        update();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        update();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        isFileSaved = false;
        updateViewTitle();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        isFileSaved = false;
        updateViewTitle();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        isFileSaved = false;
        updateViewTitle();
    }

    private void updateViewTitle() {
        if (view.getText().trim().isEmpty() && textHashcode == 0) {
            view.updateTitle("Notepad MVC");
        } else if (isFileSaved || textHashcode == view.getText().hashCode()) {
            view.updateTitle(fileManager.getFileName() + " - Notepad MVC");
        } else if (fileManager.isFilePathNull()) {
            view.updateTitle("*Untitled - Notepad MVC");
        } else {
            view.updateTitle("*" + fileManager.getFileName() + " - Notepad MVC");
        }
    }

    private int saveOrShowSaveDialog() {
        if (fileManager.isFilePathNull()) {
            return view.showSaveDialog();
        } else {
            fileManager.saveToFile(view.getText());
            isFileSaved = true;
            updateViewTitle();
            return JFileChooser.APPROVE_OPTION;
        }
    }

    private int showSaveConfirmDialogAndSaveIfConfirmed() {
        if (isFileSaved) {
            return JOptionPane.YES_OPTION;
        } else if (textHashcode == 0 && view.getText().trim().isEmpty()) {
            return JOptionPane.NO_OPTION;
        } else if (textHashcode == view.getText().hashCode()) {
            return JOptionPane.YES_OPTION;
        }

        int op = view.showSaveConfirmDialog();
        if (op == JOptionPane.YES_OPTION) {
            if (saveOrShowSaveDialog() == JFileChooser.CANCEL_OPTION) {
                return JOptionPane.CANCEL_OPTION;
            }
        }
        return op;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int op = showSaveConfirmDialogAndSaveIfConfirmed();
        if (op != JOptionPane.CANCEL_OPTION) {
            e.getWindow().dispose();
            System.exit(0);
        }

    }
}

