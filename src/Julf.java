import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Julf extends JApplet {
    protected WorldPainter painter;
    private WorldMap worldMap;
    private Player player;
    private Config conf;
    private GameEventBroadcaster gameEventBroadcaster;
    private TextureHandler textureHandler;

    public static void main(String[] args) {
        JFrame f = new JFrame("Julf");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JApplet ap = new Julf();
        ap.init();
        ap.start();
        f.add("Center", ap);
        f.pack();
        f.setVisible(true);
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public Player getPlayer() {
        return player;
    }

    public void start() {
        initComponents();
    }

    public void initComponents() {
        initGameElements();

        setupPanel();

        startRepaintTimer();

        startKeyListener();
    }

    private void initGameElements() {
        BufferedImage mapFile = ImageHelper.loadImage("map3");
        BufferedImage worldObjectMapFile = ImageHelper.loadImage("map3_o");
        conf = Config.getInstance();
        worldMap = new WorldMap(this, mapFile);
        WorldObjectHandler.getInstance().addObjectsFromMap(worldObjectMapFile);
        player = Player.getInstance();
        player.setPos(6d,6d);
        player.setMap(worldMap);
        gameEventBroadcaster = GameEventBroadcaster.getInstance();
        textureHandler = TextureHandler.getInstance();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
//        JPanel p = new JPanel();
//        p.setBackground(Color.black);
//        add("Center", p);
        setBackground(Color.black);
        painter = new WorldPainter(this);
        painter.setBackground(Color.black);
        add("Center", painter);
//        p.add("Center", painter);
    }

    private void startRepaintTimer() {
        Timer timer = new Timer();
        timer.schedule(new UpdateTask(this), 16, 16);
    }

    private void startKeyListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new GameControlKeyDispatcher(player));
    }

}

class UpdateTask extends TimerTask {
    private Julf main;

    public UpdateTask(Julf parent) {
        main = parent;
    }

    public void run() {
        main.painter.repaint();
    }
}


