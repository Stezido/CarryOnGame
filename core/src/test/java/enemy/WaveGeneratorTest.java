package enemy;

import com.badlogic.gdx.math.Vector2;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Collection;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import static org.junit.Assert.*;

/**
 * Created by stefan.ziffer on 08.03.2018.
 */
public class WaveGeneratorTest {

    private static WaveGeneratorMock myWaveGenerator;

    @Before
    public void setUp() throws Exception {

        myWaveGenerator = new WaveGeneratorMock(1);
    }


    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    @Test
    public void getDocumentFromFileTest() throws Exception {
        Document dom = myWaveGenerator.getDocumentFromFile(1);
        assertNotNull(dom);
    }

    @Ignore
    @Test
    public void getEnemyElementsTest() throws Exception {
        Document dom = myWaveGenerator.getDocumentFromFile(1);
        NodeList nList = dom.getElementsByTagName("Enemy");
        assertNotNull(nList);
        System.out.println("Number of Enemy Elements in XML-File " + nList.getLength());
        for (int i = 0; i < nList.getLength(); i++) {
            System.out.println("Enemytype: " + nList.item(i).getAttributes().getNamedItem("type") + " | Number(value): " + nList.item(i).getTextContent());
        }
    }

    @Test
    public void getLevelFromDomTest() throws Exception {
        Document dom = myWaveGenerator.getDocumentFromFile(1);
        Collection<Wave> level = myWaveGenerator.getLevelFromDom(dom);
        for (Wave w : level) {
            System.out.println("Wave " + w);
            for (Enemy e : w.getEnemies()) {
                System.out.println("Position of " + e + " = " + e.position);
            }
        }

    }

    @Ignore
    @Test
    public void randomPositionTest() throws Exception {
        Random random = new Random();
        int randomNumberX = random.nextInt((int) 1000);
        int randomNumberY = random.nextInt((int) 600);
        int i = 0;
        while (i < 10) {
            switch (random.nextInt(4)) {
                case 0:
                    System.out.println(new Vector2(-200, randomNumberY));
                case 1:
                    System.out.println(new Vector2(1000, randomNumberY));
                case 2:
                    System.out.println(new Vector2(randomNumberX, -200));
                case 3:
                    System.out.println(new Vector2(randomNumberX, 600 + 200));
            }
            i++;
        }

    }


}