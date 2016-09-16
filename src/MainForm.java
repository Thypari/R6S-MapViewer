
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * Created by Bohnen mit Steak on 16.09.2016.
 */
public class MainForm extends JFrame {
    private JPanel rootPanel;
    private JButton buttonNext;
    private JButton buttonPrevious;
    private BackgroundPanel picturePanel;
    private ArrayList<BackgroundPanel> allPanels = new ArrayList<BackgroundPanel>();

    private int currentPic = 0;

    public MainForm() {
        setPreferredSize(new Dimension(1366, 768));
        loadAllPics();

        setTitle("Rainbow Six: Siege - Map Viewer");

        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());
        setContentPane(rootPanel);

        initButtons();
        initializeKeyboardHook();

        rootPanel.add(buttonPrevious, BorderLayout.LINE_START);
        rootPanel.add(buttonNext, BorderLayout.LINE_END);

        picturePanel = allPanels.get(currentPic);

        rootPanel.add(picturePanel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

    }

    private void initButtons() {
        buttonPrevious = new JButton("<");
        buttonPrevious.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                previousPic();
            }
        });

        buttonNext = new JButton(">");
        buttonNext.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                nextPic();
            }
        });
    }

    private void initializeKeyboardHook() {
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override public void keyReleased(GlobalKeyEvent event) {
                System.out.println(event);
                if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_LEFT) previousPic();
                if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_RIGHT) nextPic();
            }

        });
    }

    private void nextPic() {
        rootPanel.remove(picturePanel);
        rootPanel.revalidate();
        rootPanel.repaint();

        if (currentPic == allPanels.size()-1) {
            currentPic = 0;
        } else {
            currentPic++;
        }
        picturePanel = allPanels.get(currentPic);

        rootPanel.add(picturePanel);
        rootPanel.revalidate();
        rootPanel.repaint();
        System.out.println(currentPic);
    }

    private void previousPic() {
        rootPanel.remove(picturePanel);
        rootPanel.revalidate();
        rootPanel.repaint();

        if (currentPic == 0) {
            currentPic = allPanels.size()-1;
        } else {
            currentPic--;
        }
        picturePanel = allPanels.get(currentPic);
        rootPanel.add(picturePanel);
        rootPanel.revalidate();
        rootPanel.repaint();

        System.out.println(currentPic);
    }

    private void loadAllPics() {
        try {
            Files.walk(Paths.get("res/")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    BackgroundPanel newPanel = new BackgroundPanel(filePath.toString());
                    allPanels.add(newPanel);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
