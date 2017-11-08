import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import parser.Parser;
import parser.Tree;

import javax.management.BadStringOperationException;
import java.text.ParseException;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTester {
    private Random random = new Random(0);

    private String generateWord(int errorMeasure) {
        if (random.nextInt(10) < errorMeasure) {
            return "";
        }
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(random.nextInt(10) + 1);
        return randomAlphabetic.equals("var") ? randomAlphabetic + "a" : randomAlphabetic;
    }

    @Test
    public void singleVarSingleType() throws ParseException, BadStringOperationException {
        baseTest(1, 1, 1, 1, 0);
    }

    @Test
    public void manyVarsSingleType() throws ParseException, BadStringOperationException {
        baseTest(1, 1, 1, 10, 0);
    }

    @Test
    public void singleVarsManyType() throws ParseException, BadStringOperationException {
        baseTest(1, 10, 1, 1, 0);
    }

    @Test
    public void manyVarsManyType() throws ParseException, BadStringOperationException {
        baseTest(1, 10, 1, 10, 0);
    }

    @Test
    public void linearComplexityTest() throws ParseException, BadStringOperationException {
        baseTest(600, 800, 600, 800, 0);
    }

    @Test
    public void badStringsTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println("test number #" + i);
            int typesAmount = random.nextInt(200 - 100 + 1) + 100;
            String test = declarationWrapper(generateTypeGroups(typesAmount, 4, 10, i + 1));
            try {
                new Parser(test).S();
                assertTrue(false);
            } catch (ParseException ignored) {
                System.out.println(ignored.getMessage() + " " + test.charAt(ignored.getErrorOffset() - 1));
                System.out.println(test.substring(max(0, ignored.getErrorOffset() - 20), min(test.length(), ignored.getErrorOffset() + 10)));

            }
        }
    }

    private StringBuilder generateTypeGroups(int typesAmount, int wordsMin, int wordsMax, int errorMeasure) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < typesAmount; j++) {
            builder.append(typeGroup(random.nextInt(wordsMax - wordsMin + 1) + wordsMin, errorMeasure));
        }
        return builder;
    }

    private String declarationWrapper(StringBuilder stringBuilder) {
        return spaces() + "var " + spaces() + stringBuilder.toString() + spaces();
    }

    private String spaces() {
        return RandomStringUtils.random(random.nextInt(5) + 1, new char[]{' '});
    }

    private StringBuilder typeGroup(int varsAmount, int errorMeasure) {
        return varGroup(varsAmount, errorMeasure).append(generateWord(errorMeasure)).append(';');
    }

    private StringBuilder varGroup(int varsAmount, int errorMeasure) {
        StringBuilder result = new StringBuilder();
        while (varsAmount-- > 0) {
            result.append(generateWord(errorMeasure));
            if (random.nextInt(10) >= errorMeasure) {
                result.append(varsAmount == 0 ? ':' : ',');
            }
        }
        return result;
    }

    private void baseTest(int tMin, int tMax, int wMin, int wMax, int errorMeasure) throws ParseException, BadStringOperationException {
        for (int i = 0; i < 10; i++) {
            System.out.println("test number #" + i);
            int typesAmount = random.nextInt(tMax - tMin + 1) + tMin;
            String test = declarationWrapper(generateTypeGroups(typesAmount, wMin, wMax, errorMeasure));
            System.out.println(test);
            Tree tree = new Parser(test).S();
            assertEquals(Main.toCanonical(test), tree.toString());
        }
    }
}
