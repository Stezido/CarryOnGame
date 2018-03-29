package enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import com.stezido.carryown.World;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by stefan.ziffer on 27.02.2018.
 */

public class WaveGenerator {

    public int waveCount = 0;
    private final static String FILENAME = "level";
    private final static String CLASSPATH_OF_ENEMY = "enemy";
    private final static int MAX_ENEMY_SIZE = Gdx.graphics.getWidth() / 5;


    public Map<String, Enemy> enemyPrototypes = new HashMap<String, Enemy>();

    Collection<Wave> level = new ArrayList<Wave>();
    Wave currentWave;

    /**
     * Generate all different Enemies available to clone them later
     */
    public WaveGenerator(int difficulty) {
        try {
            Document dom = getDocumentFromFile(difficulty);
            level = getLevelFromDom(dom);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public Collection<Wave> getLevelFromDom(Document dom) {
        NodeList waveElements = dom.getElementsByTagName("Wave");
        String classInclPath;
        NodeList enemyElements;
        Collection<Enemy> enemies = new ArrayList<Enemy>();
        String type;
        int numberOfEnemies;
        for (int i = 0; i < waveElements.getLength(); i++) {
            enemyElements = (NodeList) waveElements.item(i);
            System.out.println("getLevelFromDom(): waveElements.item(i) = " + enemyElements.getLength());
            for (int x = 0; x < enemyElements.getLength(); x++) {
                //if type attribute exists
                if (enemyElements.item(x).getAttributes() != null && enemyElements.item(x).getAttributes().getNamedItem("type") != null) {
                    type = enemyElements.item(x).getAttributes().getNamedItem("type").getTextContent();
                    type = makeFirstLetterCapital(type);
                    classInclPath = CLASSPATH_OF_ENEMY + "." + type;
                    if (!(enemyPrototypes.containsKey(type))) {
                        enemyPrototypes.put(type, getEnemyObjectfromString(classInclPath));
                    }
                    numberOfEnemies = Integer.valueOf(enemyElements.item(x).getTextContent());
                    System.out.println("numberOfEnemies = " + numberOfEnemies);
                    for (int y = 0; y < numberOfEnemies; y++) {
                        enemies.add(getEnemyObjectfromString(classInclPath));
                    }
                }
            }
            level.add(new Wave(enemies));
            enemies = new ArrayList<Enemy>();
        }

        return level;
    }

    private Enemy getEnemyObjectfromString(String enemyType) {

        try {
            Class enemyClass = Class.forName(enemyType);
            Class[] cArg = new Class[2];
            cArg[0] = Float.TYPE;
            cArg[1] = Float.TYPE;
            Vector2 randomPosition = getRandomPosition();
            return (Enemy) enemyClass.getDeclaredConstructor(cArg).newInstance(randomPosition.x, randomPosition.y);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Vector2 getRandomPosition() {
        Random random = new Random();
        int randomNumberX = random.nextInt((int) World.WORLDS_WIDTH);
        int randomNumberY = random.nextInt((int) World.WORLDS_WIDTH);

        switch (random.nextInt(4)) {
            case 0:
                return new Vector2(-MAX_ENEMY_SIZE, randomNumberY);
            case 1:
                return new Vector2(World.WORLDS_WIDTH, randomNumberY);
            case 2:
                return new Vector2(randomNumberX, -MAX_ENEMY_SIZE);
            case 3:
                return new Vector2(randomNumberX, World.WORLD_HEIGHT + MAX_ENEMY_SIZE);
        }
        return null;
    }

    public void setEnemyPrototypesFromDom(Document dom) {
        NodeList myNodeList = dom.getElementsByTagName("Enemy");
        String classInclPath = null;
        for (int i = 0; i < myNodeList.getLength(); i++) {
            String type = myNodeList.item(i).getAttributes().getNamedItem("type").getTextContent();
            if (this.enemyPrototypes == null || this.enemyPrototypes.get(type) == null) {
                type = makeFirstLetterCapital(type);
                classInclPath = CLASSPATH_OF_ENEMY + "." + type;
                Class enemyClass = null;
                try {
                    enemyClass = Class.forName(classInclPath);
                    //TODO: params should be read out from xml
                    Class[] cArg = new Class[5];
                    cArg[0] = Float.TYPE;
                    cArg[1] = Float.TYPE;
                    cArg[2] = Float.TYPE;
                    cArg[3] = Float.TYPE;
                    cArg[4] = Float.TYPE;
//                    System.out.println("enemyClass = " + enemyClass.getName());
//                    System.out.println("enemyClassConstructor = " + enemyClass.getDeclaredConstructor(cArg));
                    enemyPrototypes.put(type,
                            (Enemy) enemyClass.getDeclaredConstructor(cArg).newInstance((float) 5, (float) 5, (float) 5, (float) 5, (float) 5));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String makeFirstLetterCapital(String input) {
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }

    public Document getDocumentFromFile(int difficulty) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setValidating(false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        String filename = "Levels/" + FILENAME + Integer.toString(difficulty) + ".xml";
        System.out.println("getDocumentFromFile(): " + filename + " loaded");
        return builder.parse(new File(filename));
    }

    /**
     * Adds a number of Enemies to next Wave depending on how many Waves were sent before
     * You have to call drawNextWave() to actually draw them on Screen
     */
    public Collection<Enemy> generateNextWave() {

        if (level == null || level.size() <= waveCount) {
            return null;
        }
        currentWave = ((Wave) ((List) level).get(waveCount));
        System.out.println("curretnwave = " + currentWave);
        System.out.println("curretnwave.getEnemies = " + currentWave.getEnemies());
        waveCount++;
        return currentWave.getEnemies();
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

    public Map<String, Enemy> getEnemyPrototypes() {
        return enemyPrototypes;
    }
}
