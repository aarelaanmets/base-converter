package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static Map<String, Integer> hexDem = new HashMap<>();
    static Map<Integer, String> demHex = new HashMap<>();

    static void convert(String bases, String convertable) {
        String baseFrom = bases.split(" ")[0];
        String baseTo = bases.split(" ")[1];

        String convertableFullPart = convertable.split("\\.")[0];
        String convertableFractionPart = convertable.split("\\.").length > 1 ? convertable.split("\\.")[1] : "";

        BigInteger inFromBase = new BigInteger(convertableFullPart, Integer.parseInt(baseFrom));
        String res = new BigInteger(inFromBase.toByteArray()).toString(Integer.parseInt(baseTo));

        int lenFraction = convertableFractionPart.length();
        BigDecimal ansFraction = new BigDecimal("0.00000");
        int k = -1;

        for (int i = 0; i < lenFraction; i++) {
            ansFraction = ansFraction.add(BigDecimal.valueOf(Math.pow(Double.parseDouble(baseFrom), k) * hexDem.get(convertableFractionPart.substring(i, i + 1).toLowerCase())));
            k--;
        }
        BigDecimal calcNum = ansFraction.multiply(BigDecimal.valueOf(Math.pow(Double.parseDouble(baseTo), 6))).setScale(0, RoundingMode.HALF_UP);
        StringBuilder digits = new StringBuilder();
        BigDecimal reminder = new BigDecimal("1");
        do {
            reminder = calcNum.remainder(BigDecimal.valueOf(Integer.parseInt(baseTo)));
            digits.append(demHex.get(Integer.parseInt(String.valueOf(reminder))));
            calcNum = calcNum.divideToIntegralValue(BigDecimal.valueOf(Integer.parseInt(baseTo)));
        } while (calcNum.compareTo(BigDecimal.ZERO) > 0);

        if (digits.length() < 6) {
            digits.append("0");
        }
        if (convertableFractionPart.length() > 0) {
            digits.append(".");
            digits.reverse();
            digits.append("000000");
            digits = new StringBuilder(digits.substring(0, Math.min(convertableFractionPart.length(), 5) + 1));
        } else {
            digits = new StringBuilder();
        }
        System.out.println("Conversion result: " + res + digits);
    }

    public static void main(String[] args) {
        hexDem.put("0", 0);
        hexDem.put("1", 1);
        hexDem.put("2", 2);
        hexDem.put("3", 3);
        hexDem.put("4", 4);
        hexDem.put("5", 5);
        hexDem.put("6", 6);
        hexDem.put("7", 7);
        hexDem.put("8", 8);
        hexDem.put("9", 9);
        hexDem.put("a", 10);
        hexDem.put("b", 11);
        hexDem.put("c", 12);
        hexDem.put("d", 13);
        hexDem.put("e", 14);
        hexDem.put("f", 15);
        hexDem.put("g", 16);
        hexDem.put("h", 17);
        hexDem.put("i", 18);
        hexDem.put("j", 19);
        hexDem.put("k", 20);
        hexDem.put("l", 21);
        hexDem.put("m", 22);
        hexDem.put("n", 23);
        hexDem.put("o", 24);
        hexDem.put("p", 25);
        hexDem.put("q", 26);
        hexDem.put("r", 27);
        hexDem.put("s", 28);
        hexDem.put("t", 29);
        hexDem.put("u", 30);
        hexDem.put("v", 31);
        hexDem.put("w", 32);
        hexDem.put("x", 33);
        hexDem.put("y", 34);
        hexDem.put("z", 35);

        hexDem.forEach((key, value) -> {
            demHex.put(value, key);
        });

        Scanner scanner = new Scanner(System.in);
        String mainInput;
        String convertInput;

        do {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            mainInput = scanner.nextLine();
            if (!mainInput.equals("/exit")) {
                do {
                    System.out.print("Enter number in base " + mainInput.split(" ")[0]);
                    System.out.print(" to convert to base " + mainInput.split(" ")[1]);
                    System.out.print(" (To go back type /back) ");
                    convertInput = scanner.nextLine();
                    if (!convertInput.equals("/back")) {
                        convert(mainInput, convertInput);
                    }
                } while (!convertInput.equals("/back"));
            }
        } while (!mainInput.equals("/exit"));
    }
}
