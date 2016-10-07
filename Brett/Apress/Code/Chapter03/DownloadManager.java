import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.*;

public class DownloadManager extends JPanel {

  protected Downloader downloader;

  protected JButton startButton;
  protected JButton sleepButton;
  protected JButton suspendButton;
  protected JButton resumeButton;
  protected JButton stopButton;

  public static void main(String[] args) throws Exception {
    URL url = new URL(args[0]);
    FileOutputStream fos = new FileOutputStream(args[1]);
    JFrame f = new JFrame();
    DownloadManager dm = new DownloadManager(url, fos);
    f.getContentPane().add(dm);
    f.setSize(600, 400);
    f.setVisible(true);
  }

  public DownloadManager(URL source, OutputStream os)
      throws IOException {
    downloader = new Downloader(source, os);
    buildLayout();
    Border border = new BevelBorder(BevelBorder.RAISED);
    String name = source.toString();
    int index = name.lastIndexOf('/');
    border = new TitledBorder(border,
        name.substring(index + 1));
    setBorder(border);
  }

  protected void buildLayout() {
    setLayout(new BorderLayout());
    downloader.setBorder(new BevelBorder(BevelBorder.RAISED));
    add(downloader, BorderLayout.CENTER);

    add(getButtonPanel(), BorderLayout.SOUTH);
  }

  protected JPanel getButtonPanel() {
    JPanel outerPanel;
    JPanel innerPanel;

    innerPanel = new JPanel();
    innerPanel.setLayout(new GridLayout(1, 5, 10, 0));

    startButton = new JButton("Start");
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        startButton.setEnabled(false);
        sleepButton.setEnabled(true);
        resumeButton.setEnabled(false);
        suspendButton.setEnabled(true);
        stopButton.setEnabled(true);
        downloader.startDownload();
      }
    });
    innerPanel.add(startButton);

    sleepButton = new JButton("Sleep");
    sleepButton.setEnabled(false);
    sleepButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        downloader.setSleepScheduled(true);
      }
    });
    innerPanel.add(sleepButton);

    suspendButton = new JButton("Suspend");
    suspendButton.setEnabled(false);
    suspendButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        suspendButton.setEnabled(false);
        resumeButton.setEnabled(true);
        stopButton.setEnabled(true);
        downloader.setSuspended(true);
      }
    });
    innerPanel.add(suspendButton);

    resumeButton = new JButton("Resume");
    resumeButton.setEnabled(false);
    resumeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        resumeButton.setEnabled(false);
        suspendButton.setEnabled(true);
        stopButton.setEnabled(true);
        downloader.resumeDownload();
      }
    });
    innerPanel.add(resumeButton);

    stopButton = new JButton("Stop");
    stopButton.setEnabled(false);
    stopButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        stopButton.setEnabled(false);
        sleepButton.setEnabled(false);
        suspendButton.setEnabled(false);
        resumeButton.setEnabled(false);
        downloader.stopDownload();
      }
    });
    innerPanel.add(stopButton);

    outerPanel = new JPanel();
    outerPanel.add(innerPanel);
    return outerPanel;
  }

}