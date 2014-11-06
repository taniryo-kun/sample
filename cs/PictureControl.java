package cs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PictureControl extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = PictureView.SIZE;
    private static final int HEIGHT = PictureView.SIZE;

    private PictureView view;

    public static void main(String[] args) {
        PictureView v = new PictureView();
        PictureControl pc = new PictureControl(v);
        pc.runScenario();
    }

    public PictureControl(PictureView v) {
        JFrame app = new JFrame("Computer Science");
        app.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        view = v;
        app.setSize(WIDTH, HEIGHT);
        app.setResizable(false);
        app.getContentPane().add(this);
        app.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        view.draw(g);
    }

    /**
     * PictureView の draw メソッドを呼んで画面を描き直した後、
     * 100 ミリ秒一時停止する。
     */
    public void pause() {
        repaint();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 絵の動きのシナリオ
     */
    @SuppressWarnings("unused")
    public void runScenario() {
        int id = view.create(100, 100, 0, 20, PictureView.ROUND, Color.YELLOW);
        int id2 = view.create(-100, -150, 0, 20, PictureView.DIAMOND, Color.BLUE);
        int id3 = view.create(30, 0, 400, 40, PictureView.DIAMOND, Color.RED);
        pause();
        for (int i = 0; true; i++) {
            double theta = 2.0 * Math.PI * i / 50.0;
            view.move(id, 300.0 * Math.cos(theta + 1.0), 10, 300.0 * Math.sin(theta + 1.0) + 300);
            view.move(id2, 30, 300.0 * Math.cos(theta), 250.0 * Math.sin(theta) + 250);
            pause();
        }
    }
}
