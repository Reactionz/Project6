import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Button;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class DiningPhilosophers extends JFrame {
    private static JFrame frame;
    static DPMonitor dpMtr = new DPMonitor();
    static JLabel[] comments = new JLabel[5];
    static DiningPhilosophers window;
    private static boolean exit = false;

    public DiningPhilosophers() {
        initialize();
    } 
    public static void main(String[] args) {
        try {
            window = new DiningPhilosophers();
            window.frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Return scaled ImageIcon to getContentPane().add to JLabel
    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = DinningPhilosophers.class.getResource(path);

        if (imgURL != null) {
            return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        } else {
            System.err.println("Couldn't find file:" + path);
            return null;
        }
    }

    private static void initialize() {
        frame = new JFrame("Dining Philosophers");
        ImageIcon philo1 = createImageIcon("./images/philo1.jpeg");
        ImageIcon philo2 = createImageIcon("images/philo2.jpg");
        ImageIcon philo3 = createImageIcon("images/philo3.jpg");
        ImageIcon philo4 = createImageIcon("images/philo4.jpg");
        ImageIcon philo5 = createImageIcon("images/philo5.jpg");

        // TODO: Add Forks next to the philosophers and maybe like food on plates.

        ImageIcon forkLeft = createImageIcon("images/forkLeft.jpg");
        ImageIcon forkRight = createImageIcon("images/forkRight.jpg");
        ImageIcon table = new ImageIcon(
                new ImageIcon("images/table.jpg").getImage().getScaledInstance(240, 240, Image.SCALE_DEFAULT)
        );

        // Setting Window Size and Background Color
        frame.setSize(720, 600);
        frame.setBackground(Color.WHITE);

        // Add the Start and exit Buttons
        Button start = new Button("Start");
        start.setBounds(325, 450, 77, 22);
        start.addActionListener(e -> start()); // Lambda Expression to getContentPane().add event listener to button
        frame.getContentPane().add(start);
        
        Button exit = new Button("Exit");
        exit.setBounds(325, 500, 77, 22);
        exit.addActionListener(e -> System.exit(0));
        frame.getContentPane().add(exit);

        // Initialize all JLabels with ImageIcons or Text Labels to Change
        // Setting placement or all the photos.

        JLabel philoLabel1 = new JLabel(philo1);
        JLabel philoTextLabel0 = new JLabel("Philosopher 0");
        philoLabel1.setBounds(150, 150, 80, 80);
        philoTextLabel0.setBounds(135, 135, 200, 15);
        frame.getContentPane().add(philoTextLabel0);
        frame.getContentPane().add(philoLabel1);

        JLabel philoLabel2 = new JLabel(philo3);
        JLabel philoTextLabel1 = new JLabel("Philosopher 1");
        philoLabel2.setBounds(315, 50, 80, 80);
        philoTextLabel1.setBounds(300, 35, 200, 15);
        frame.getContentPane().add(philoTextLabel1);
        frame.getContentPane().add(philoLabel2);

        JLabel philoLabel3 = new JLabel(philo2);
        JLabel philoTextLabel2 = new JLabel("Philosopher 2");
        philoLabel3.setBounds(500, 150, 80, 80);
        philoTextLabel2.setBounds(485, 135, 200, 15);
        frame.getContentPane().add(philoTextLabel2);
        frame.getContentPane().add(philoLabel3);


        JLabel philoLabel4 = new JLabel(philo5);
        JLabel philoTextLabel3 = new JLabel("Philosopher 3");
        philoLabel4.setBounds(500, 350, 80, 80);
        philoTextLabel3.setBounds(485, 335, 200, 15);
        frame.getContentPane().add(philoTextLabel3);
        frame.getContentPane().add(philoLabel4);


        JLabel philoLabel5 = new JLabel(philo4);
        JLabel philoTextLabel4 = new JLabel("Philosopher 4");
        philoLabel5.setBounds(150, 350, 80, 80);
        philoTextLabel4.setBounds(135, 335, 200, 15);
        frame.getContentPane().add(philoTextLabel4);
        frame.getContentPane().add(philoLabel5);

        JLabel tableLabel = new JLabel(table);
        tableLabel.setBounds(250, 125, 240, 240);
        frame.getContentPane().add(tableLabel);

        frame.setLayout(null);

        // Display the Window
        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        comments[0] = philoTextLabel0;
		comments[1] = philoTextLabel1;
		comments[2] = philoTextLabel2;
		comments[3] = philoTextLabel3;
		comments[4] = philoTextLabel4;
    }

    public static void start() {
        for (int i = 0; i < 5; i++) {
            new Thread(new Philosopher(i, dpMtr, comments[i])).start();
        }
    }

}

class Philosopher implements Runnable {

    int k; // ID Of a philosopher.
    int left, right;
    DPMonitor mtr = null;
    String name = null;
    JLabel comments;

    public Philosopher(int id, DPMonitor mtr, JLabel comments) {
        k = id;
        this.mtr = mtr;
        this.comments = comments;
        name = String.format("Philosopher-%d: ", k);

        switch (k) {
            case 0:
                left = 0;
                right = 4;
                break;
            case 1:
                left = 1;
                right = 0;
                break;
            case 2:
                left = 2;
                right = 1;
                break;
            case 3:
                left = 3;
                right = 2;
                break;
            case 4:
                left = 4;
                right = 3;
                break;
            default:
                break;
        }
    }

    public void run() {
        while (true) {
            mtr.simulate(String.format("%s thinking", name), 4000, comments);
            mtr.simulate(String.format("%s hungry.", name), 800, comments);
            mtr.pick(right, left, name, comments);
            mtr.simulate(String.format("%s [%d] eating [%d]", name, right, left), 3000, comments);
            mtr.put(right, left, name, comments);
            mtr.simulate(String.format("%s relaxing", name), 2000, comments);
        }
    }

}

class DPMonitor {
    // static int[] xUten = { 9, 14, 19, 13, 8 };
    // static int[] yUten = { 50, 50, 43, 35, 35 };
    int[] tableware = { 0, 1, 2, 3, 4 }; // silverware, utensils, cuetery
    static Random rnd = new Random();
    static JLabel comments;

    // A philosopher will look at is left and writh tableware, if both avaible.
    // pick. I any one tableware is not avaialble (-1), he will wait.

    public synchronized void pick(int right, int left, String name,JLabel comments) {
        while (tableware[right] == -1 || tableware[left] == -1) { // need both utensils to each
            simulate(String.format("%s waiting [%d,%d]", name, tableware[right], tableware[left]), 1000, comments);
            notifyAll();
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        simulate(String.format("%s get [%s,%d]", name, tableware[right], tableware[left]), 500, comments);
        tableware[right] = -1;
        tableware[left] = -1;
    }

    // put() needs no mutual exclusived since no one utensil will be put back by
    // more than one philosophy at the same locaion.

    public synchronized void put(int right, int left, String name, JLabel comments) {
        tableware[right] = right;
        tableware[left] = left;
        simulate(String.format("%s [%d] put [%d]", name, right, left), 500, comments);
        notifyAll();
    }

    synchronized void simulate(String msg, int ms, JLabel comments) {
        try {
            comments.setText(msg);
            Thread.sleep(rnd.nextInt(ms) + 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
