import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Random;

public class ParserTester {

    Random random = new Random(0);

    private String generateWord() {
        return RandomStringUtils.randomAlphabetic(random.nextInt(10) + 1);
    }

    @Test
    public void singleVarSingleType() throws ParseException {
        baseTest(1, 1, 1, 1);
    }

    @Test
    public void manyVarsSingleType() throws ParseException {
        baseTest(1, 1, 1, 10);
    }

    @Test
    public void singleVarsManyType() throws ParseException {
        baseTest(1, 10, 1, 1);
    }

    @Test
    public void manyVarsManyType() throws ParseException {
        baseTest(1, 10, 1, 10);
    }

    @Test
    public void linearComplexityTest() throws ParseException {
        baseTest(600, 800, 600, 800);
    }

    private StringBuilder generateTypeGroups(int typesAmount, int wordsMin, int wordsMax) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < typesAmount; j++) {
            builder.append(typeGroup(random.nextInt(wordsMax - wordsMin + 1) + wordsMin));
        }
        return builder;
    }

    private String declarationWrapper(StringBuilder stringBuilder) {
        return "var " + stringBuilder.toString();
    }

    private StringBuilder typeGroup(int varsAmount) {
        return varGroup(varsAmount).append(generateWord()).append(';');
    }

    private StringBuilder varGroup(int varsAmount) {
        StringBuilder result = new StringBuilder();
        while (varsAmount-- > 0) {
            result.append(generateWord())
                    .append(varsAmount == 0 ? ':' : ',');
        }
        return result;
    }

    private void baseTest(int tMin, int tMax, int wMin, int wMax) throws ParseException {
        for (int i = 0; i < 10; i++) {
            int typesAmount = random.nextInt(tMax - tMin + 1) + tMin;
            String test = declarationWrapper(generateTypeGroups(typesAmount, wMin, wMax));
//            System.out.println(test);
            System.out.println(test.length());
            new Parser(test).S();
            System.out.println("\tdone");
        }
    }
}
