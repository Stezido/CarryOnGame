package enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by stefan.ziffer on 08.03.2018.
 */

public class WaveGeneratorMock {

    int waveCount = 0;
    private final static String FILENAME = "level";
    private final static String CLASSPATH_OF_ENEMY = "enemy";
    private final static int MAX_ENEMY_SIZE = 480 / 5;


    Map<String, Enemy> enemyPrototypes = new HashMap<String, Enemy>();

    Collection<Wave> level = new ArrayList<Wave>();
    Wave currentWave;

    /**
     * Generate all different Enemies available to clone them later
     */
    WaveGeneratorMock(int difficulty) {
//        try {
//            Document dom = getDocumentFromFile(difficulty);
//            level = getLevelFromDom(dom);
//
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
    }

    public Collection<Wave> getLevelFromDom(Document dom) {
        NodeList waveElements = dom.getElementsByTagName("Wave");
        String classInclPath;
        NodeList enemyElements;
        Collection<Enemy> enemies = new ArrayList<Enemy>();
        ;
        String type;
        int numberOfEnemies;
        for (int i = 0; i < waveElements.getLength(); i++) {
            enemyElements = (NodeList) waveElements.item(i);
            for (int x = 0; x < enemyElements.getLength(); x++) {
                //if type attribute exists
                if (enemyElements.item(x).getAttributes() != null && enemyElements.item(x).getAttributes().getNamedItem("type") != null) {
                    type = enemyElements.item(x).getAttributes().getNamedItem("type").getTextContent();
                    type = makeFirstLetterCapital(type);
                    classInclPath = CLASSPATH_OF_ENEMY + "." + type;
                    numberOfEnemies = Integer.valueOf(enemyElements.item(x).getTextContent());
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
        int randomNumberX = random.nextInt((int) 480 * 2);
        int randomNumberY = random.nextInt((int) 480 * 2);

        switch (random.nextInt(4)) {
            case 0:
                return new Vector2(-MAX_ENEMY_SIZE, randomNumberY);
            case 1:
                return new Vector2(480 * 2, randomNumberY);
            case 2:
                return new Vector2(randomNumberX, -MAX_ENEMY_SIZE);
            case 3:
                return new Vector2(randomNumberX, 320 * 2 + MAX_ENEMY_SIZE);
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


    //classes will be saved in Prototypes
    public Document getDocumentFromFile(int difficulty) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setValidating(false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        String filename = "Levels/" + FILENAME + Integer.toString(difficulty) + ".xml";
        System.out.println();
        return builder.parse(new File(filename));
    }

    /**
     * Adds a number of Enemies to next Wave depending on how many Waves were sent before
     * You have to call drawNextWave() to actually draw them on Screen
     */
//    public void generateNextWave() {
//        waveCount++;
//        for (int enemiesAdded = 0; enemiesAdded < waveCount; enemiesAdded++) {
//            currentWaveObjects.add((Enemy) enemyClones.get("Dog").clone());
//        }
//    }
    public Wave getCurrentWave() {
        return currentWave;
    }

    public Map<String, Enemy> getEnemyPrototypes() {
        return enemyPrototypes;
    }
}
