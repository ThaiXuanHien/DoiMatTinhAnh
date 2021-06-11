import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DialogHuongdan extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextArea txtaHuongdan;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            DialogHuongdan dialog = new DialogHuongdan();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public DialogHuongdan() {
        setFont(new Font("Dialog", Font.BOLD, 12));
        setTitle("Hướng dẫn");
        setBounds(100, 100, 600, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 582, 253);
        contentPanel.add(scrollPane);

        txtaHuongdan = new JTextArea();
        txtaHuongdan.setForeground(Color.WHITE);
        txtaHuongdan.setBackground(Color.BLACK);
        txtaHuongdan.setFont(new Font("Monospaced", Font.BOLD, 18));
        txtaHuongdan.setEditable(false);
        txtaHuongdan.setLineWrap(true);
        txtaHuongdan.setWrapStyleWord(true);
        txtaHuongdan.setBorder(new TitledBorder(null, "Hướng dẫn", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 25), Color.white));
        txtaHuongdan.setText("* KẾT NỐI : Người chơi phải nhập : Tên,IP,Port .\r\n  trong đó Port và IP cả 2 bên phải giống nhau để Kết nối với nhau.\r\n\r\n* CHƠI : 2 người chơi lần lượt chọn từ 1 -> 100 , khi đến 100 , người chiến thắng là người có điểm lớn hơn.");
        scrollPane.setViewportView(txtaHuongdan);
    }
}
