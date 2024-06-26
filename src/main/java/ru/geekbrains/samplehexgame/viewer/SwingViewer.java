package ru.geekbrains.samplehexgame.viewer;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import ru.geekbrains.hexcore.core.model.tiles.Hex;
import ru.geekbrains.hexcore.core.model.tiles.Terrain;
import ru.geekbrains.hexcore.core.model.tiles.Tile;
import ru.geekbrains.hexcore.core.model.tiles.Unit;
import ru.geekbrains.hexcore.core.service.Battlefield;
import ru.geekbrains.samplehexgame.players.RandomAIPlayer;
import ru.geekbrains.samplehexgame.utils.HexagonCalc;
import ru.geekbrains.samplehexgame.utils.TerrainColorMapper;
import ru.geekbrains.samplehexgame.utils.TerrainDrawer;
import ru.geekbrains.samplehexgame.utils.UnitDrawer;
import ru.geekbrains.viewer.interfaces.BattlefieldPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static java.lang.Math.sqrt;

@Setter
@Slf4j
public class SwingViewer implements BattlefieldPresenter {
    private Battlefield battlefield;
    private JFrame jFrame;
    private DrawingPanel drawingPanel;

    Color COLOUR_BACK = Color.GREEN;
    final int HEX_SIZE = 50;
    final int BORDERS = 15;
    int SCR_HEIGHT;
    int SCR_WIDTH;

    private String WINDOW_TITLE = "Battlefield Presenter";

    public SwingViewer(Battlefield battlefield) {
        this.battlefield = battlefield;
        battlefield.setBattlefieldPresenter(this);
    }

    public void setGUI() {
        SCR_HEIGHT = 2 * (HEX_SIZE * battlefield.getVerticalSize() - 1 + BORDERS * 2);
        SCR_WIDTH = 2 * (HEX_SIZE * battlefield.getHorizontalSize() + BORDERS * 2);
        {
            jFrame = new JFrame(WINDOW_TITLE);
            jFrame.setSize(SCR_WIDTH, SCR_HEIGHT);
            jFrame.setResizable(false);
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        {
            drawingPanel = new DrawingPanel();
            Container content = jFrame.getContentPane();
            content.add(drawingPanel);
        }
    }

    @Override
    public void draw() {
        drawingPanel.repaint();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(Hex hex, Graphics2D g2) {

        Point centerPoint = pointyHexToPixel(hex);
        Polygon polygon = HexagonCalc.getHexagon(centerPoint, HEX_SIZE);
        Terrain terrain = battlefield.getTerrainByCoordinate(hex);
        TerrainDrawer.draw(g2, TerrainColorMapper.getColorFor(terrain), HEX_SIZE, centerPoint);
        for (Unit unit : battlefield.getUnitsByCoordinate(hex)) {
            g2.setColor( ((RandomAIPlayer) unit.getOwner()).getColor());
            UnitDrawer.draw(g2, unit, HEX_SIZE, new Point(centerPoint.x - HEX_SIZE / 2, centerPoint.y + HEX_SIZE / 4));
        }
        g2.setColor(Color.BLACK);
        g2.drawPolygon(polygon);

        //g2.drawString(hex.toString(), centerPoint.x - HEX_SIZE / 2, centerPoint.y - HEX_SIZE / 3); //DEBUG: show coordinates on each Hex
    }

    /**
     * Get Hex coordinates on screen
     *
     * @param hex hex coordinates to be converted to pixels
     * @return java Point
     */
    public Point pointyHexToPixel(Hex hex) {
        int x = (int) (HEX_SIZE * (sqrt(3) * hex.getQ() + sqrt(3) / 2 * hex.getR()));
        int y = (int) (HEX_SIZE * (3. / 2 * hex.getR()));
        x += SCR_WIDTH / 2;
        y += SCR_HEIGHT / 2;
        return new Point(x, y);
    }


    class DrawingPanel extends JPanel {

        public DrawingPanel() {
            setBackground(COLOUR_BACK);
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Font f = new Font(Font.SERIF, Font.ITALIC, 15);
            g2.setFont(f);
            super.paintComponent(g2);

            //battlefield.getTiles().keySet().size();
            //List<List<Tile>> hexes = battlefield.getTiles().values().stream().toList();
            log.trace("Battlefield contains {} hexes to draw. Repainting...", battlefield.getTiles().keySet().size());
            for (Hex hex : battlefield.getTiles().keySet()) {
                draw(hex, g2);
            }
        }
    }
}
