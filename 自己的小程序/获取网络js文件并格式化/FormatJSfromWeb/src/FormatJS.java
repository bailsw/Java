import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;

/**
 * Created by Administrator on 2018/09/13.
 */
public class FormatJS {
    private static JFrame frame;
    private JTextField textField1;
    private JButton 浏览Button;
    private JTextField textField2;
    private JButton 下载并转换Button;
    private JButton 退出Button;
    private JPanel panel1;


    public FormatJS() {
        浏览Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setCurrentDirectory(new File("C:\\"));
                int result = chooser.showOpenDialog(panel1);
                if (result == JFileChooser.APPROVE_OPTION) {
                    textField2.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        退出Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
        下载并转换Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String url = textField1.getText();
                String filepath = textField2.getText();
                FileWriter fos = null;
                InputStreamReader isr = null;
                if (!url.equals("")) {
                    if (filepath.equals("")) {
                        JOptionPane.showMessageDialog(panel1, "请选择存放位置", "提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    DataInputStream dis = null;
                    try {
                        dis = new DataInputStream(new URL(url).openStream());
                        String filename = url.split("/")[url.split("/").length - 1];
                        fos = new FileWriter(filepath + "\\" + filename);
                        isr = new InputStreamReader(dis);
                        char[] buf = new char[1024 * 5];
                        int len = 0;
                        StringBuffer sb = new StringBuffer("");
                        while ((len = isr.read(buf)) != -1) {
                            sb.append(buf, 0, len);
                        }

                        String result = Format.format(sb);
                        fos.write(result);
                        JOptionPane.showMessageDialog(panel1, "存储成功！", "提示", JOptionPane.INFORMATION_MESSAGE);


                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(panel1, "存放位置无效", "提示", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        JOptionPane.showMessageDialog(panel1, "无效Url或网络错误", "提示", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        try {
                            fos.close();
                            isr.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }


                } else {
                    JOptionPane.showMessageDialog(panel1, "请输入Url", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("FormatJS");
        frame.setLocation(new Point(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2));
        frame.setSize(720, 360);
        frame.setContentPane(new FormatJS().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
