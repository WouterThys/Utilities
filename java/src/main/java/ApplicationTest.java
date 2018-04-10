import com.waldo.utils.GuiUtils;
import com.waldo.utils.icomponents.ITextField;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class ApplicationTest {


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            setLookAndFeel();
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(JTextFieldPanel());
        panel.add(ITextFieldPanel());
        panel.add(ITextField2Panel());

        frame.add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel JTextFieldPanel() {
        JPanel panel = new JPanel();

        JTextField defaultTf = new JTextField();
        JTextField hintTf = new JTextField("Hint");
        JTextField disabledTf = new JTextField("Hint");
        JTextField errorTf = new JTextField("Error");
        JTextField warningTf = new JTextField("Warning");

        disabledTf.setEnabled(false);

        GuiUtils.GridBagHelper gbc = new GuiUtils.GridBagHelper(panel);
        gbc.addLine("Default: ", defaultTf);
        gbc.addLine("Hint: ", hintTf);
        gbc.addLine("Disabled: ", disabledTf);
        gbc.addLine("Error: ", errorTf);
        gbc.addLine("Waring: ", warningTf);

        panel.setBorder(GuiUtils.createTitleBorder("JTextField"));

        return panel;
    }

    private static JPanel ITextFieldPanel() {
        JPanel panel = new JPanel();

        ITextField defaultTf = new ITextField();
        ITextField hintTf = new ITextField("Hint");
        ITextField disabledTf = new ITextField(false);
        ITextField errorTf = new ITextField();
        ITextField warningTf = new ITextField();

        disabledTf.setText("Disabled");
        errorTf.setError("With error");
        warningTf.setWarning("With warning");

        GuiUtils.GridBagHelper gbc = new GuiUtils.GridBagHelper(panel);
        gbc.addLine("Default: ", defaultTf);
        gbc.addLine("Hint: ", hintTf);
        gbc.addLine("Disabled: ", disabledTf);
        gbc.addLine("Error: ", errorTf);
        gbc.addLine("Waring: ", warningTf);

        panel.setBorder(GuiUtils.createTitleBorder("ITextField"));
        return panel;
    }

    private static JPanel ITextField2Panel() {
        JPanel panel = new JPanel();

        ITextField defaultTf = new ITextField();
        ITextField hintTf = new ITextField("Hint");
        ITextField disabledTf = new ITextField(false);
        ITextField errorTf = new ITextField("Error");
        ITextField warningTf = new ITextField("Warning");

        disabledTf.setText("Disabled");
        errorTf.setError("With error");
        warningTf.setWarning("With warning");

        GuiUtils.GridBagHelper gbc = new GuiUtils.GridBagHelper(panel);
        gbc.addLine("Default: ", defaultTf);
        gbc.addLine("Hint: ", hintTf);
        gbc.addLine("Disabled: ", disabledTf);
        gbc.addLine("Error: ", errorTf);
        gbc.addLine("Waring: ", warningTf);

        panel.setBorder(GuiUtils.createTitleBorder("ITextField"));

        return panel;
    }

    private static void setLookAndFeel() {

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel() {
                @Override
                public UIDefaults getDefaults() {
                    UIDefaults defaults = super.getDefaults();

                    defaults.put("defaultFont", new Font(Font.SANS_SERIF, Font.PLAIN, 15));

                    return defaults;
                }
            });
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

}
