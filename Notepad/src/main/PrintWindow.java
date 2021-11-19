import java.awt.*;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.List;

public class PrintWindow implements Printable {
    private List<String> splittedText;
    private int[] pageBreaks;
    private View view;
    private boolean flag;

    public PrintWindow(View view) {
        splittedText = new ArrayList<String>();
        this.view = view;
        flag = false;
        checkJob();
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        g.setColor(Color.black);
        Font myFont = new Font(view.getFont().toString(), Font.PLAIN, view.getFont().getSize());
        g.setFont(myFont);

        int x = 40;
        int y = 40;
        int fontHeight = g.getFontMetrics().getHeight();

        int pageWidth = (int) pf.getImageableWidth() - x;
        int pageHeight = (int) pf.getImageableHeight() - y;
        int lineHeight = fontHeight;

        Graphics2D g2d = (Graphics2D) g;
        //g2d.drawRect(40, 40, pageWidth, pageHeight);
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        if (!flag) {
            editTextLines(g, pageWidth);
        }

        if (pageBreaks == null) {
            int linesPerPage = pageHeight / lineHeight - 1;
            int numBreaks = (splittedText.size() - 1) / linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b = 0; b < numBreaks; b++) {
                pageBreaks[b] = (b + 1) * linesPerPage;
            }
        }

        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         * Since we are drawing text we
         */


        /* Draw each line that is on this page.
         * Increment 'y' position by lineHeight for each line.
         */

        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];
        int end = (pageIndex == pageBreaks.length) ? splittedText.size() : pageBreaks[pageIndex];
        for (int line = start; line < end; line++) {
            g.drawString(splittedText.get(line), x, y);
            y = y + lineHeight;
        }

        return Printable.PAGE_EXISTS;
    }

    private void checkJob() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                System.out.println(ex);
            }
        }
    }

    private void editTextLines(Graphics g, int pageWidth) {
        pageWidth = pageWidth - 40;
        String text = view.getText();
        for (String line : text.split("\n")) {
            splittedText.add(line);
        }
        for (int i = 0; i < splittedText.size(); i++) {
            if (g.getFontMetrics().stringWidth(splittedText.get(i)) >= pageWidth) {
                List<String> list = new ArrayList<>();
                int startIndex = 0;
                int endIndex = 0;
                boolean subString = false;
                while (true) {
                    String partLine = "";
                    while (true) {
                        if (startIndex == endIndex) {
                            endIndex++;
                        }
                        partLine = splittedText.get(i).substring(startIndex, endIndex);
                        if (endIndex == splittedText.get(i).length()) {
                            break;
                        }
                        if (g.getFontMetrics().stringWidth(partLine) < pageWidth) {
                            endIndex++;
                        } else {
                            if (!checkSpaces(partLine)) {
                                break;
                            }
                            int spaceIndex = partLine.lastIndexOf(" ");
                            partLine = partLine.substring(0, spaceIndex);
                            endIndex = startIndex + spaceIndex + 1;
                            break;
                        }
                    }
                    if (subString) {
                        partLine = partLine.trim();
                    }
                    subString = true;
                    startIndex = endIndex;
                    list.add(partLine);
                    if (endIndex == splittedText.get(i).length()) {
                        break;
                    }
                }
                int index = i + 1;
                splittedText.set(i, list.get(0));
                for (int j = 1; j < list.size(); j++) {
                    splittedText.add(index, list.get(j));
                    ++index;
                }
                i = --index;
            }
        }
        flag = true;
    }

    private boolean checkSpaces(String partLine) {
        return partLine.trim().contains(" ");
    }
}