import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
/**
 *Word occurrences application that tells how many times a word was mentioned
 * @author Isabella Correa Castano
 *
 */
@SuppressWarnings("serial")

/**
 * Class for the GUI
 *
 */
class MyPoem extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    public MyPoem() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 500, 500, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.PINK);
        JLabel lblNewLabel = new JLabel("This is MyPoem♡");
        lblNewLabel.setBounds(6, 6, 206, 16);
        contentPane.add(lblNewLabel);
        JLabel lblNewLabel_1 = new JLabel("You can Display your Name And Age♡");
        lblNewLabel_1.setBounds(6, 34, 343, 16);
        contentPane.add(lblNewLabel_1);
        JLabel lblNewLabel_2 = new JLabel("Enter Name♡");
        lblNewLabel_2.setBounds(6, 66, 116, 16);
        contentPane.add(lblNewLabel_2);
        textField = new JTextField();
        textField.setBounds(134, 62, 130, 26);
        contentPane.add(textField);
        textField.setColumns(10);
        JLabel lblNewLabel_3 = new JLabel("Enter Age♡");
        lblNewLabel_3.setBounds(6, 99, 97, 16);
        contentPane.add(lblNewLabel_3);
        textField_1 = new JTextField();
        textField_1.setBounds(134, 94, 130, 26);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(16, 127, 241, 339);
        contentPane.add(scrollPane);
        JTextPane textPane_1 = new JTextPane();
        scrollPane.setViewportView(textPane_1);
        String words = "";
        words = Scrape.getPeomWords();
        textPane_1.setText(words);
        JButton btnNewButton = new JButton("Generate Message♡");
        btnNewButton.setBounds(292, 29, 166, 29);
        contentPane.add(btnNewButton);
        JTextArea textArea = new JTextArea();
        textArea.setBounds(302, 62, 160, 74);
        contentPane.add(textArea);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                String text2 = textField_1.getText();
                textArea.setText("Hello " + text + " age " + text2 + "!" + "\n");
                textField.selectAll();
            }
        });
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyPoem frame = new MyPoem();
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
/**
 * Class for word occurrence
 *
 */
class Scrape {
    /**
     * Using Jsoup to connect to link online
     *
     */
    public static String getPeomWords() {
        Map<String, Word> countMap = new HashMap<String, Word>();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();
        } catch (IOException e) {

            e.printStackTrace();
        }
        for (Element element : doc.select("div.chapter")) {
            String text = element.text();
            for (String word : text.replaceAll("[;—:!‘’?,“.”]", "").split(" ")) {
                Word wordObj = countMap.get(word);
                if (wordObj == null) {
                    wordObj = new Word();
                    wordObj.word = word;
                    wordObj.count = 0;
                    countMap.put(word, wordObj);
                }
                wordObj.count++;
            }
        }
        /**
         * Create an Arraylist for new wordList
         * and sort words
         *
         */
        ArrayList<Word> sortedWords = new ArrayList<Word>(countMap.values());
        Collections.sort(sortedWords, Collections.reverseOrder((a, b) -> {
            return a.count - b.count;
        }));
        String poem = "";
        for (Word word : sortedWords) {
            poem += word.count + " " + word.word + "\n";
        }
        return poem;
    }
}
class Word implements Comparable<Word> {
    String word;
    int count;
    @Override
    public boolean equals(Object obj) {
        return word.equals(((Word) obj).word);
    }
    @Override
    public int compareTo(Word thisword) {
        return thisword.count;
        /**
         * End of program
         */
    }

}

