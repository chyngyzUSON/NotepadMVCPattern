import java.text.SimpleDateFormat;
import java.util.Date;

public class Edit {
    private View view;
    private String buffer;

    public Edit(View view) {
        this.view = view;
        buffer = "";
    }

    public void selectAll() {
        view.selectAllTextArea();
    }

    public void copy() {
        buffer = view.getSelectedText();
    }

    public void paste() {
        view.insert(buffer, view.getCaretPosition());
    }

    public void cut() {
        buffer = view.getSelectedText();
        view.cutTextArea();
    }

    public void remove() {
        view.cutTextArea();
    }

    public void initReplaceDialog() {
        view.showReplaceDialog();
    }

    public void replace() {
        String search = view.getSearchWord();
        String replace = view.getReplacementWord();
        String txt = view.getText();
        if (checkSpaces(search) || checkSpaces(replace)) {
            return;
        }
        if (txt.contains(search)) {
            view.updateTextArea(txt.replaceAll("\\b" + search + "\\b", replace));
            view.closeReplaceDialog();
        }
    }

    private boolean checkSpaces(String word) {
        int count = word.length() - word.replace(" ", "").length();
        return count == word.length();
    }

    public void makeUndo() {
        if (view.canUndo()) {
            view.undo();
        }
        if (view.getText().isEmpty()) {
            if (view.canUndo()) {
                view.undo();
            }
        }
    }

    public void makeRedo() {
        if (view.canRedo()) {
            view.redo();
        }
        if (view.getText().isEmpty()) {
            if (view.canRedo()) {
                view.redo();
            }
        }
    }

    public void timeDate() {
        view.insert(new SimpleDateFormat("HH:mm:ss  dd.MM.yyyy")
                .format(new Date()), view.getCaretPosition());
    }
}