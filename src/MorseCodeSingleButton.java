import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

class MorseCodeSingleButton extends JFrame implements KeyListener {
    private StringBuilder morseCode = new StringBuilder();
    private final JTextArea outputArea;
    private boolean isCyrillic = false; // Флаг для отслеживания текущей раскладки

    // Карты для декодирования Морзе
    private final Map<String, String> morseCodeMapLatin = new HashMap<>();
    private final Map<String, String> morseCodeMapCyrillic = new HashMap<>();

    public MorseCodeSingleButton() {
        setTitle("Morse Code Input");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);

        initializeMorseCodeMaps(); // Инициализация карт
    }

    private void initializeMorseCodeMaps() {
        // Заполнение карты Морзе для латиницы
        morseCodeMapLatin.put(".-", "A");
        morseCodeMapLatin.put("-...", "B");
        morseCodeMapLatin.put("-.-.", "C");
        morseCodeMapLatin.put("-..", "D");
        morseCodeMapLatin.put(".", "E");
        morseCodeMapLatin.put("..-.", "F");
        morseCodeMapLatin.put("--.", "G");
        morseCodeMapLatin.put("....", "H");
        morseCodeMapLatin.put("..", "I");
        morseCodeMapLatin.put(".---", "J");
        morseCodeMapLatin.put("-.-", "K");
        morseCodeMapLatin.put(".-..", "L");
        morseCodeMapLatin.put("--", "M");
        morseCodeMapLatin.put("-.", "N");
        morseCodeMapLatin.put("---", "O");
        morseCodeMapLatin.put(".--.", "P");
        morseCodeMapLatin.put("--.-", "Q");
        morseCodeMapLatin.put(".-.", "R");
        morseCodeMapLatin.put("...", "S");
        morseCodeMapLatin.put("-", "T");
        morseCodeMapLatin.put("..-", "U");
        morseCodeMapLatin.put("...-", "V");
        morseCodeMapLatin.put(".--", "W");
        morseCodeMapLatin.put("-..-", "X");
        morseCodeMapLatin.put("-.--", "Y");
        morseCodeMapLatin.put("--..", "Z");

        // Заполнение карты Морзе для кириллицы
        morseCodeMapCyrillic.put(".-", "А");
        morseCodeMapCyrillic.put("-...", "Б");
        morseCodeMapCyrillic.put(".--", "В");
        morseCodeMapCyrillic.put("--.", "Г");
        morseCodeMapCyrillic.put("-..", "Д");
        morseCodeMapCyrillic.put(".", "Е");
        morseCodeMapCyrillic.put("...-", "Ж");
        morseCodeMapCyrillic.put("--..", "З");
        morseCodeMapCyrillic.put("..", "И");
        morseCodeMapCyrillic.put(".---.", "Й");
        morseCodeMapCyrillic.put("-.-", "К");
        morseCodeMapCyrillic.put(".-..", "Л");
        morseCodeMapCyrillic.put("--", "М");
        morseCodeMapCyrillic.put("-.", "Н");
        morseCodeMapCyrillic.put("---", "О");
        morseCodeMapCyrillic.put(".--.", "П");
        morseCodeMapCyrillic.put(".-.", "Р");
        morseCodeMapCyrillic.put("...", "С");
        morseCodeMapCyrillic.put("-", "Т");
        morseCodeMapCyrillic.put("..-", "У");
        morseCodeMapCyrillic.put("..-.", "Ф");
        morseCodeMapCyrillic.put("....", "Х");
        morseCodeMapCyrillic.put("-.-.", "Ц");
        morseCodeMapCyrillic.put("---.", "Ч");
        morseCodeMapCyrillic.put("----", "Ш");
        morseCodeMapCyrillic.put("--.-", "Щ");
        morseCodeMapCyrillic.put(".--.-.", "Ъ");
        morseCodeMapCyrillic.put("-.--", "Ы");
        morseCodeMapCyrillic.put("-..-", "Ь");
        morseCodeMapCyrillic.put("..-..", "Э");
        morseCodeMapCyrillic.put("..--", "Ю");
        morseCodeMapCyrillic.put(".-.-", "Я");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || (isCyrillic && e.getKeyCode() == KeyEvent.VK_A)) {
            addMorseSymbol("."); // Краткое нажатие (точка)
        } else if (e.getKeyCode() == KeyEvent.VK_S || (isCyrillic && e.getKeyCode() == KeyEvent.VK_S)) {
            addMorseSymbol("-"); // Длинное нажатие (тире)
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            handleSpacePress(); // Обработка нажатия пробела
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            isCyrillic = !isCyrillic; // Переключение раскладки
            updateTitle();
            System.out.println("Текущая раскладка: " + (isCyrillic ? "Кириллица" : "Латиница")); // Вывод текущей раскладки в консоль
        }
    }

    private void updateTitle() {
        setTitle(isCyrillic ? "Морзянка - Русская раскладка" : "Морзянка - English Layout");
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private void addMorseSymbol(String symbol) {
        morseCode.append(symbol);
        outputArea.setText(morseCode.toString());
    }

    private void handleSpacePress() {
        if (morseCode.length() > 0 && morseCode.charAt(morseCode.length() - 1) != ' ') {
            morseCode.append(" "); // Добавляем пробел (разделитель между символами)
            outputArea.setText(morseCode.toString());
        } else {
            // Если пробел уже есть, значит был введён двойной пробел
            if (morseCode.length() > 1 && morseCode.charAt(morseCode.length() - 1) == ' ') {
                morseCode.setLength(morseCode.length() - 1); // Убираем последний пробел
                decodeMorseCode(); // Декодируем Морзе
            }
        }
    }

    private void decodeMorseCode() {
        String morseString = morseCode.toString().trim();
        String[] morseCharacters = morseString.split(" ");
        StringBuilder decodedMessage = new StringBuilder();

        for (String morseChar : morseCharacters) {
            String letter;
            if (isCyrillic) {
                letter = morseCodeMapCyrillic.get(morseChar);
            } else {
                letter = morseCodeMapLatin.get(morseChar);
            }
            if (letter != null) {
                decodedMessage.append(letter);
            }
        }

        outputArea.setText(decodedMessage.toString());
        morseCode.setLength(0); // Очищаем для следующего слова
    }
}