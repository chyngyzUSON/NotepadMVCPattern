public class StatusBar {
    private View view;

    public StatusBar(View view) {
        this.view = view;
    }

    public void toggleStatusBar() {
        view.setStatusBar();
        update();
    }

    private String getCaretPosition() {
        int row = 0;
        int column = 0;
        try {
            int caretpos = view.getCaretPosition();
            row = view.getLineOfOffset(caretpos);
            column = caretpos - view.getLineStartOffset(row);
            row += 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "" + row + ",  " + column;
    }

    public void update() {
        if (!view.getStatusBarVisible()) {
            return;
        }
        String text = getCaretPosition();
        view.setStatusBarData(text + "              ");
    }
}
