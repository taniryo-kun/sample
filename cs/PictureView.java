package cs;

import java.awt.Color;
import java.awt.Graphics;

public class PictureView {
    /** 画面の一辺の長さ */
    public static final int SIZE = 600;

    /** 図形の形：丸 */
    public static final int ROUND = 0;
    /** 図形の形：四角形 */
    public static final int DIAMOND = 1;

    /** 図形の最大個数 */
    public static final int MAX_FIGURES = 10;

    /** 発行された図形の個数 */
    public int figureCount;

    /** X座標 */
    public double[] xs;
    /** Y座標 */
    public double[] ys;
    /** Z座標 */
    public double[] zs;
    /** 大きさ */
    public double[] rs;
    /** 図形の形 */
    public int[] shapes;
    /** 図形の色 */
    public Color[] colors;

    /**
     * PictureView オブジェクトを作る
     */
    public PictureView() {
        this.figureCount = 0;
        this.xs = new double[MAX_FIGURES];
        this.ys = new double[MAX_FIGURES];
        this.zs = new double[MAX_FIGURES];
        this.rs = new double[MAX_FIGURES];
        this.shapes = new int[MAX_FIGURES];
        this.colors = new Color[MAX_FIGURES];
    }

    /**
     * 全ての図形を現在位置に合わせて画面上に描画する
     * 
     * @param g
     *            グラフィック・コンテキスト
     */
    public void draw(Graphics g) {
        int[] order = new int[figureCount];
        for (int i = 0; i < figureCount; ++i) {
            order[i] = i;
        }

        // Z軸の順番によるソート(大きい順)
        for (int i = 0; i < figureCount; ++i) {
            for (int j = i + 1; j < figureCount; ++j) {
                if (zs[order[i]] < zs[order[j]]) {
                    int temp = order[i];
                    order[i] = order[j];
                    order[j] = temp;
                }
            }
        }

        // 登録されている全てのオブジェクトを描画する
        for (int i = 0; i < figureCount; ++i) {
            int id = order[i];

            // 一点透視図法による描画
            final double CENTER = SIZE / 2.0;
            double d = CENTER / (zs[id] + CENTER);
            double r = rs[id] * d;
            double x = xs[id] * d - r + CENTER;
            double y = ys[id] * d - r + CENTER;

            g.setColor(colors[id]);

            if (shapes[id] == ROUND) {
                g.fillOval((int) x, (int) y, (int) r * 2, (int) r * 2);
            } else if (shapes[id] == DIAMOND) {
                g.fillRect((int) x, (int) y, (int) r * 2, (int) r * 2);
            }
        }
    }

    /**
     * 新しい図形を作成し、その図形の識別番号を返す
     * 
     * @param x
     *            X 座標
     * @param y
     *            Y 座標
     * @param z
     *            Z 座標
     * @param radius
     *            半径 (大きさ)
     * @param shape
     *            形: ROUND または DIAMOND
     * @param color
     *            色
     * @return 作成した図形の番号
     */
    public int create(double x, double y, double z, double radius, int shape, Color color) {
        int id = figureCount;
        ++figureCount;

        xs[id] = x;
        ys[id] = y;
        zs[id] = z;
        rs[id] = radius;
        shapes[id] = shape;
        colors[id] = color;

        return id;
    }

    /**
     * 図形の位置を動かす
     * 
     * @param id
     *            動かす図形の識別番号
     * @param x
     *            X 座標
     * @param y
     *            Y 座標
     * @param z
     *            Z 座標
     */
    public void move(int id, double x, double y, double z) {
        xs[id] = x;
        ys[id] = y;
        zs[id] = z;
    }
}
