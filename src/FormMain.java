import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FormMain extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    public JPanel panelNumber;
    private JPanel panelThongTin;
    private JButton btnChoiMoi;
    private JButton btnThoat;
    private JLabel lblDiemNgChoi1;
    private JLabel lblDiemNgChoi2;
    private JPanel panelChat;
    private JTextField txtNhapTinNhan;
    private JButton btnGuiTinNhan;
    private JScrollPane scrollPane;
    private JTextArea txtANoiDungTinNhan;
    private JLabel lblSoTiepTheo_number;

    private JTextField txtPort;
    private JButton btnKetNoi;
    private JTextField txtNhapTen;

    Thread threadServer;
    ServerSocket serverSocket;
    Socket socket = null;

    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;

    private DataOutputStream dos;
    private DataInputStream dis;

    int soTiepTheo = 1;

    ArrayList<Integer> listNumber;
    ArrayList<JButton> listButton;
    String str = "";
    private JLabel lblNgChoi2;
    private JLabel lblNgChoi1;
    int x; // vị trí button
    int diem;
    private JRadioButton radioDark;
    private JRadioButton radioLight;
    int isNew = 0;
    private JLabel lblSoTiepTheo_title;

    // String ip = "localhost";
    // int port = 3000;
    boolean accepted = false;
    private JTextField txtIP;
    private JLabel lblKetNoi;
    private JLabel lblDiemSo;
    private JLabel lblCheDo;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FormMain frame = new FormMain();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                    frame.disableComponent();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     *
     * @throws UnknownHostException
     */
    public FormMain() throws UnknownHostException {
        setResizable(false);
        setTitle("Server");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 890, 1080);
        getContentPane().setLayout(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Bạn có muốn Thoát ?", "Thoát", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                    System.exit(0);
                }
            }
        });

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("cursor.png");
        Point point = new Point(0, 0);
        Cursor cursor = toolkit.createCustomCursor(image, point, "cursor");
        setCursor(cursor);

        panelNumber = new JPanel();
        panelNumber.setBorder(new LineBorder(Color.WHITE));
        panelNumber.setBounds(0, 0, 700, 845);
        getContentPane().add(panelNumber);
        panelNumber.setLayout(new GridLayout(10, 10));

        panelThongTin = new JPanel();
        panelThongTin.setBorder(new LineBorder(Color.WHITE));
        panelThongTin.setBounds(702, 0, 182, 1045);
        getContentPane().add(panelThongTin);
        panelThongTin.setLayout(null);

        btnChoiMoi = new JButton("Ch\u01A1i m\u1EDBi");
        btnChoiMoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                str = "";

                // panelNumber.repaint();
                // panelNumber.removeAll();
                // panelNumber.revalidate();
                // panelNumber.updateUI();
                listNumber = new ArrayList<>();
                for (int i = 0; i < 100; ) {
                    listNumber.add(++i);
                }
                Random rd = new Random();
                for (int i = 0; i < 100; i++) {
                    int r = rd.nextInt(listNumber.size());
                    // listNumberRandom.add(listNumber.get(r));
                    str += listNumber.get(r) + " ";
                    listNumber.remove(r);
                }
                try {
                    // oos.writeObject(listNumberRandom);
                    dos.writeUTF("choiMoi," + str + "," + isNew);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(str);
                System.out.println(isNew);
                // getContentPane().add(panelNumber);
                // panelNumber.setVisible(true);
                // panelNumber.updateUI();
                // choiMoi(listNumberRandom);

                // choiMoi(str);
            }
        });

        btnChoiMoi.setFont(new Font("Times New Roman", Font.BOLD, 25));
        btnChoiMoi.setBounds(12, 877, 158, 43);
        panelThongTin.add(btnChoiMoi);

        btnThoat = new JButton("Tho\u00E1t");
        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Bạn có muốn Thoát ?", "Thoát", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        System.out.println(isNew);
                        dos.writeUTF("thoat," + isNew);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });
        btnThoat.setFont(new Font("Times New Roman", Font.BOLD, 25));
        btnThoat.setBounds(12, 989, 158, 43);
        panelThongTin.add(btnThoat);

        lblNgChoi1 = new JLabel("");

        lblNgChoi1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNgChoi1.setForeground(Color.BLUE);
        lblNgChoi1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNgChoi1.setBounds(12, 46, 158, 30);
        panelThongTin.add(lblNgChoi1);

        lblNgChoi2 = new JLabel("");
        lblNgChoi2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNgChoi2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNgChoi2.setForeground(Color.RED);
        lblNgChoi2.setBounds(12, 235, 158, 30);
        panelThongTin.add(lblNgChoi2);

        lblDiemNgChoi1 = new JLabel("0");
        lblDiemNgChoi1.setForeground(Color.BLUE);
        lblDiemNgChoi1.setHorizontalAlignment(SwingConstants.CENTER);
        lblDiemNgChoi1.setFont(new Font("Times New Roman", Font.BOLD, 70));
        lblDiemNgChoi1.setBounds(12, 89, 158, 60);
        panelThongTin.add(lblDiemNgChoi1);

        lblDiemNgChoi2 = new JLabel("0");
        lblDiemNgChoi2.setForeground(Color.RED);
        lblDiemNgChoi2.setHorizontalAlignment(SwingConstants.CENTER);
        lblDiemNgChoi2.setFont(new Font("Times New Roman", Font.BOLD, 70));
        lblDiemNgChoi2.setBounds(12, 162, 158, 60);
        panelThongTin.add(lblDiemNgChoi2);

        lblSoTiepTheo_title = new JLabel("S\u1ED1 ti\u1EBFp theo");
        lblSoTiepTheo_title.setHorizontalAlignment(SwingConstants.CENTER);
        lblSoTiepTheo_title.setForeground(Color.BLACK);
        lblSoTiepTheo_title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblSoTiepTheo_title.setBounds(12, 291, 158, 30);
        panelThongTin.add(lblSoTiepTheo_title);

        lblSoTiepTheo_number = new JLabel("0");
        lblSoTiepTheo_number.setHorizontalAlignment(SwingConstants.CENTER);
        lblSoTiepTheo_number.setForeground(Color.BLACK);
        lblSoTiepTheo_number.setFont(new Font("Times New Roman", Font.BOLD, 70));
        lblSoTiepTheo_number.setBounds(12, 334, 158, 60);
        panelThongTin.add(lblSoTiepTheo_number);

        txtPort = new JTextField();
        txtPort.setHorizontalAlignment(SwingConstants.CENTER);
        txtPort.setBorder(new TitledBorder(null, "Nhập Port", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 18), null));
        txtPort.setFont(new Font("Times New Roman", Font.BOLD, 30));
        txtPort.setBounds(12, 745, 158, 50);
        panelThongTin.add(txtPort);
        txtPort.setColumns(10);
        txtPort.setBackground(null);

        btnKetNoi = new JButton("K\u1EBFt n\u1ED1i");
        btnKetNoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // enableComponent();
                //
                // lblNgChoi1.setText(txtNhapTen.getText());
                // try {
                // serverSocket = new
                // ServerSocket(Integer.parseInt(txtPort.getText()));
                // socket = serverSocket.accept();
                // ois = new ObjectInputStream(socket.getInputStream());
                // oos = new ObjectOutputStream(socket.getOutputStream());
                // oos.writeObject("ten," + txtNhapTen.getText());
                // getDataClient();
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
                try {
                    lblNgChoi1.setText(txtNhapTen.getText());
                    if (!connect()) {
                        initializeServer();
                    }

                    getDataClient();

                    dos.writeUTF("ten," + txtNhapTen.getText());

                } catch (IOException e) {
                    e.printStackTrace();

                }

            }

        });
        btnKetNoi.setFont(new Font("Times New Roman", Font.BOLD, 25));
        btnKetNoi.setBounds(12, 821, 158, 43);
        panelThongTin.add(btnKetNoi);

        txtNhapTen = new JTextField();
        txtNhapTen.setHorizontalAlignment(SwingConstants.CENTER);
        txtNhapTen.setFont(new Font("Times New Roman", Font.BOLD, 30));
        txtNhapTen.setColumns(10);
        txtNhapTen.setBorder(new TitledBorder(null, "Nhập Tên", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 17), null));
        txtNhapTen.setBounds(12, 584, 158, 60);
        txtNhapTen.setBackground(null);

        panelThongTin.add(txtNhapTen);

        radioDark = new JRadioButton("Dark Mode");
        radioDark.setBackground(null);
        radioDark.setFocusable(false);
        radioDark.addActionListener(this);
        radioDark.setHorizontalAlignment(SwingConstants.CENTER);
        radioDark.setFont(new Font("Times New Roman", Font.BOLD, 20));
        radioDark.setBounds(12, 480, 158, 25);
        panelThongTin.add(radioDark);

        radioLight = new JRadioButton("Light Mode", true);
        radioLight.setBackground(null);
        radioLight.setFocusable(false);
        radioLight.addActionListener(this);

        radioLight.setFont(new Font("Times New Roman", Font.BOLD, 20));
        radioLight.setHorizontalAlignment(SwingConstants.CENTER);
        radioLight.setBounds(12, 450, 158, 25);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioDark);
        buttonGroup.add(radioLight);

        panelThongTin.add(radioLight);

        txtIP = new JTextField(InetAddress.getLocalHost().getHostAddress());
        txtIP.setHorizontalAlignment(SwingConstants.CENTER);
        txtIP.setFont(new Font("Times New Roman", Font.BOLD, 30));
        txtIP.setColumns(10);
        txtIP.setBorder(new TitledBorder(null, "Nhập IP", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 18), null));
        txtIP.setBackground((Color) null);
        txtIP.setBounds(12, 657, 158, 75);
        panelThongTin.add(txtIP);

        JButton btnHuongDan = new JButton("Hướng dẫn");
        btnHuongDan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                DialogHuongdan dialogHuongdan = new DialogHuongdan();
                dialogHuongdan.setVisible(true);
            }
        });
        btnHuongDan.setFont(new Font("Times New Roman", Font.BOLD, 25));
        btnHuongDan.setBounds(12, 933, 158, 43);
        panelThongTin.add(btnHuongDan);

        lblKetNoi = new JLabel("");
        lblKetNoi.setBounds(12, 538, 158, 270);
        lblKetNoi.setBorder(new TitledBorder(null, "Kết nối", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 25), null));
        panelThongTin.add(lblKetNoi);

        lblDiemSo = new JLabel("");
        lblDiemSo.setBounds(12, 13, 158, 265);
        lblDiemSo.setBorder(new TitledBorder(null, "Điểm số", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 25), null));
        panelThongTin.add(lblDiemSo);

        lblCheDo = new JLabel("");
        lblCheDo.setBounds(12, 407, 158, 118);
        lblCheDo.setBorder(new TitledBorder(null, "Chế độ", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 25), null));
        panelThongTin.add(lblCheDo);
        panelChat = new JPanel();
        panelChat.setBorder(new LineBorder(Color.WHITE));
        panelChat.setBounds(0, 845, 700, 200);
        getContentPane().add(panelChat);
        panelChat.setLayout(null);

        txtNhapTinNhan = new JTextField();
        txtNhapTinNhan.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnGuiTinNhan.doClick();
                }
            }
        });

        txtNhapTinNhan.setFont(new Font("Times New Roman", Font.BOLD, 15));
        txtNhapTinNhan.setBounds(12, 162, 600, 25);
        panelChat.add(txtNhapTinNhan);
        txtNhapTinNhan.setColumns(10);

        btnGuiTinNhan = new JButton("G\u1EEDi");
        btnGuiTinNhan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (txtNhapTinNhan.getText() != "") {
                    try {
                        dos.writeUTF("Chat," + txtNhapTen.getText() + " : " + txtNhapTinNhan.getText());
                        txtANoiDungTinNhan.append("\nTôi : " + txtNhapTinNhan.getText());
                        txtNhapTinNhan.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnGuiTinNhan.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnGuiTinNhan.setBounds(624, 162, 64, 25);
        panelChat.add(btnGuiTinNhan);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 13, 678, 136);
        scrollPane.setBorder(new TitledBorder(null, "Chat", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 20), null));
        panelChat.add(scrollPane);

        txtANoiDungTinNhan = new JTextArea();
        txtANoiDungTinNhan.setFont(new Font("Times New Roman", Font.BOLD, 15));
        txtANoiDungTinNhan.setEditable(false);
        scrollPane.setViewportView(txtANoiDungTinNhan);

    }

    private void listenForServerRequest() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // connect server
    private boolean connect() {
        try {
            socket = new Socket(txtIP.getText(), Integer.parseInt(txtPort.getText()));
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
        } catch (IOException e) {
            System.out.println("Server được khởi tạo");
            return false;
        }
        System.out.println("Kết nối tới Server thành công !");
        return true;
    }

    // khoi tao server
    private void initializeServer() {
        try {
//            serverSocket = new ServerSocket(Integer.parseInt(txtPort.getText()), 8,
//                    InetAddress.getByName(txtIP.getText()));
            serverSocket = new ServerSocket(Integer.parseInt(txtPort.getText()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // xu ly truyen du lieu
    void getDataClient() {
        threadServer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (!accepted) {
                            listenForServerRequest();
                            enableComponent();
                            dos.writeUTF("ten," + txtNhapTen.getText());
                        }

                        String stream = (String) dis.readUTF();
                        String[] data = stream.split(",");
                        if (data[0].equals("choiMoi")) {
                            if (data[2].equals("0")) {
                                int n = JOptionPane.showConfirmDialog(null, lblNgChoi2.getText() + " muốn Chơi với Bạn",
                                        "Thông Báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                                if (n == JOptionPane.YES_OPTION) {
                                    choiMoi(data[1]);
                                    dos.writeUTF("dongY," + data[1]);
                                }
                            }
                            if (Integer.parseInt(data[2]) >= 1) {
                                int n = JOptionPane.showConfirmDialog(null,
                                        lblNgChoi2.getText() + " muốn Chơi lại với Bạn", "Thông Báo",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                                if (n == JOptionPane.YES_OPTION) {
                                    choiMoi(data[1]);
                                    dos.writeUTF("dongY," + data[1]);
                                }
                            }

                        } else if (data[0].equals("Chat")) {
                            txtANoiDungTinNhan.append("\n" + data[1]);
                        } else if (data[0].equals("ten")) {
                            lblNgChoi2.setText(data[1]);
                            JOptionPane.showMessageDialog(null, data[1] + " đã Kết nối với Bạn");
                            enableComponent();
                            btnKetNoi.setEnabled(false);

                        } else if (data[0].equals("click")) {
                            click(data[1], data[2]);
                        } else if (data[0].equals("thongBao")) {
                            JOptionPane.showMessageDialog(null, data[1]);
                        } else if (data[0].equals("dongY")) {
                            choiMoi(data[1]);
                        } else if (data[0].equals("thoat")) {
                            JOptionPane.showMessageDialog(null, lblNgChoi2.getText() + " đã Thoát vì Quá sợ Bạn");
                            lblNgChoi2.setText("");
                            if (Integer.parseInt(data[1]) > 0) {
                                JOptionPane.showMessageDialog(null, "Bạn đã thắng");
                            }
                            socket.close();
                            serverSocket.close();
                        } else if (data[0].equals("endGame")) {
                            isNew = Integer.parseInt(data[1]);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        threadServer.start();
    }

    void choiMoi(String str) {
        btnKetNoi.setEnabled(false);
        lblDiemNgChoi1.setText("0");
        lblDiemNgChoi2.setText("0");
        // panelNumber.repaint();
        panelNumber.removeAll();
        // panelNumber.revalidate();

        isNew++;

        String[] data = str.split(" ");
        ArrayList<String> arrlist = new ArrayList<>(Arrays.asList(data));
        soTiepTheo = 1;
        diem = 0;
        lblSoTiepTheo_number.setText(soTiepTheo + "");
        listButton = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int a = i;
            JButton btn = new JButton();
            btn.setFont(new Font("Times New Roman", Font.BOLD, 22));
            btn.setText("" + arrlist.get(i));
            if (radioDark.isSelected()) {
                btn.setBackground(Color.BLACK);
                btn.setForeground(Color.WHITE);
            }
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Integer.parseInt(btn.getText()) == soTiepTheo) {
                        btn.setVisible(false);
                        diem++;
                        try {
                            dos.writeUTF("click," + a + "," + diem); // a : vị trí nút
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        if (soTiepTheo != 10) {
                            soTiepTheo++;
                            lblSoTiepTheo_number.setText(soTiepTheo + "");
                            lblDiemNgChoi1.setText(diem + "");
                        }
                        lblDiemNgChoi1.setText(diem + "");
                    }
                }
            });
            listButton.add(btn);
            panelNumber.add(btn);
        }
        // getContentPane().add(panelNumber);
        // panelNumber.setVisible(true);
        panelNumber.updateUI();
    }

    void click(String viTri, String diemDoiThu) {
        try {
            x = Integer.parseInt(viTri);
            listButton.get(x).setVisible(false);
            soTiepTheo++;
            lblDiemNgChoi2.setText(diemDoiThu);
            if (listButton.get(x).getText().equals("10")) {
                if (diem > Integer.parseInt(diemDoiThu)) {
                    dos.writeUTF("thongBao," + "Bạn đã Thua " + lblNgChoi1.getText());
                    JOptionPane.showMessageDialog(null, "Bạn đã Thắng " + lblNgChoi2.getText());
                    isNew = 0;
                    --soTiepTheo;
                    dos.writeUTF("endGame," + isNew);
                } else if (diem == Integer.parseInt(diemDoiThu)) {
                    dos.writeUTF("thongBao," + "Bạn và " + lblNgChoi1.getText() + " đã Hòa nhau");
                    JOptionPane.showMessageDialog(null, "Bạn và " + lblNgChoi2.getText() + " đã Hòa nhau");
                    isNew = 0;
                    --soTiepTheo;
                    dos.writeUTF("endGame," + isNew);
                } else {
                    dos.writeUTF("thongBao," + "Bạn đã Thắng " + lblNgChoi1.getText());
                    JOptionPane.showMessageDialog(null, "Bạn đã Thua " + lblNgChoi2.getText());
                    isNew = 0;
                    --soTiepTheo;
                    dos.writeUTF("endGame," + isNew);
                }

            }
            lblSoTiepTheo_number.setText(soTiepTheo + "");

        } catch (Exception e) {
        }
    }

    void disableComponent() {
        panelChat.setEnabled(false);
        btnChoiMoi.setEnabled(false);
        btnThoat.setEnabled(false);
        btnGuiTinNhan.setEnabled(false);
        txtNhapTinNhan.setEnabled(false);
    }

    void enableComponent() {
        panelChat.setEnabled(true);
        btnChoiMoi.setEnabled(true);
        btnThoat.setEnabled(true);
        btnGuiTinNhan.setEnabled(true);
        txtNhapTinNhan.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (radioDark.isSelected()) {

            panelChat.setBackground(Color.BLACK);
            panelThongTin.setBackground(Color.BLACK);
            panelNumber.setBackground(Color.BLACK);
            lblSoTiepTheo_number.setForeground(Color.WHITE);
            lblSoTiepTheo_title.setForeground(Color.WHITE);

            scrollPane.setBorder(new TitledBorder(null, "Chat", TitledBorder.LEADING, TitledBorder.TOP,
                    new Font("Times New Roman", Font.BOLD, 20), Color.WHITE));
            scrollPane.setBackground(Color.BLACK);
            txtANoiDungTinNhan.setBackground(Color.BLACK);
            txtANoiDungTinNhan.setForeground(Color.WHITE);
            txtNhapTinNhan.setBackground(Color.BLACK);
            txtNhapTinNhan.setForeground(Color.WHITE);
            txtANoiDungTinNhan.setForeground(Color.WHITE);
            btnGuiTinNhan.setBackground(Color.BLACK);
            btnGuiTinNhan.setForeground(Color.WHITE);
            txtNhapTen.setBorder(new TitledBorder(null, "Nhập Tên", TitledBorder.LEADING, TitledBorder.TOP,
                    new Font("Times New Roman", Font.BOLD, 17), Color.WHITE));
            txtNhapTen.setForeground(Color.WHITE);
            txtPort.setBorder(new TitledBorder(null, "Nhập Port", TitledBorder.LEADING, TitledBorder.TOP,
                    new Font("Times New Roman", Font.BOLD, 18), Color.WHITE));
            txtNhapTen.setForeground(Color.WHITE);
            txtPort.setForeground(Color.WHITE);
            radioLight.setForeground(Color.WHITE);
            radioDark.setForeground(Color.WHITE);
            txtIP.setForeground(Color.WHITE);
            for (int i = 0; i < listButton.size(); i++) {
                listButton.get(i).setBackground(Color.BLACK);
                listButton.get(i).setForeground(Color.WHITE);
            }
        }
        if (radioLight.isSelected()) {

            panelChat.setBackground(null);
            panelThongTin.setBackground(null);
            panelNumber.setBackground(null);
            lblSoTiepTheo_number.setForeground(null);
            lblSoTiepTheo_title.setForeground(null);

            scrollPane.setBorder(new TitledBorder(null, "Chat", TitledBorder.LEADING, TitledBorder.TOP,
                    new Font("Times New Roman", Font.BOLD, 20), null));
            scrollPane.setBackground(null);
            txtANoiDungTinNhan.setBackground(Color.WHITE);
            txtANoiDungTinNhan.setForeground(null);
            txtNhapTinNhan.setBackground(null);
            txtNhapTinNhan.setForeground(null);
            btnGuiTinNhan.setBackground(null);
            btnGuiTinNhan.setForeground(null);

            txtNhapTen.setForeground(null);
            txtNhapTen.setBorder(new TitledBorder(null, "Nhập Tên", TitledBorder.LEADING, TitledBorder.TOP,
                    new Font("Times New Roman", Font.BOLD, 17), null));
            txtNhapTen.setForeground(Color.WHITE);
            txtPort.setBorder(new TitledBorder(null, "Nhập Port", TitledBorder.LEADING, TitledBorder.TOP,
                    new Font("Times New Roman", Font.BOLD, 18), null));


            radioLight.setBackground(null);
            radioLight.setForeground(null);
            radioDark.setBackground(null);
            radioDark.setForeground(null);

            txtNhapTen.setForeground(null);
            txtPort.setForeground(null);
            txtIP.setForeground(null);
            for (int i = 0; i < listButton.size(); i++) {
                listButton.get(i).setBackground(null);
                listButton.get(i).setForeground(null);
            }
        }

    }
}
