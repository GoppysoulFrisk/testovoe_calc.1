import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

    class Main {
        public static void main(String[] args) {

        }

        public static String calc(@NotNull String input) {
            // Маппинг для римских чисел
            Map<Character, Integer> romanNumerals = new HashMap<>();
            romanNumerals.put('I', 1);
            romanNumerals.put('V', 5);
            romanNumerals.put('X', 10);
            romanNumerals.put('L', 50);
            romanNumerals.put('C', 100);

            // Разбиваем входную строку по пробелу
            String[] tokens = input.split("\\s+");

            // Проверяем корректность ввода и получаем числа и операцию
            if (tokens.length != 3) {
                throw new IllegalArgumentException("Неверный формат строки");
            }

            String x = tokens[0];
            String operation = tokens[1];
            String y = tokens[2];

            // Проверяем, являются ли числа арабскими или римскими
            boolean isRoman = x.matches("[IVXLCDM]+") && y.matches("[IVXLCDM]+");
            boolean isArabic = x.matches("\\d+") && y.matches("\\d+");

            if (!(isRoman || isArabic)) {
                throw new IllegalArgumentException("Строка содержит неверные символы");
            }

            int x1, y1;

            // Преобразование чисел в int
            if (isRoman) {
                x1 = romanToArabic(x, romanNumerals);
                y1 = romanToArabic(y, romanNumerals);
            } else {
                x1 = Integer.parseInt(x);
                y1 = Integer.parseInt(y);
            }

            // Выполнение операции
            int result;
            switch (operation) {
                case "+":
                    result = x1 + y1;
                    break;
                case "-":
                    result = x1 - y1;
                    break;
                case "*":
                    result = x1 * y1;
                    break;
                case "/":
                    if (y1 == 0) {
                        throw new ArithmeticException("Делить на ноль тут нельзя");
                    }
                    result = x1 / y1;
                    break;
                default:
                    throw new IllegalArgumentException("Недопустимая операция");
            }

            // Если работаем с римскими числами, проверяем результат на положительность
            if (isRoman && result <= 0) {
                throw new IllegalArgumentException("Результат римского числа меньше единицы");
            }

            // Преобразуем обратно в римские
            if (isRoman) {
                return arabicToRoman(result);
            }

            return String.valueOf(result);
        }

        // Метод для преобразования римских чисел в арабские
        static int romanToArabic(String roman, Map<Character, Integer> mapping) {
            int result = 0;
            int prevValue = 0;
            for (int i = roman.length() - 1; i >= 0; i--) {
                int value = mapping.get(roman.charAt(i));
                if (value < prevValue) {
                    result -= value;
                } else {
                    result += value;
                }
                prevValue = value;
            }
            return result;
        }

        // Метод для преобразования арабских чисел в римские
        private static String arabicToRoman(int number) {
            if (number <= 0) {
                throw new IllegalArgumentException("Невозможно преобразовать ноль или отрицательное число в римское");
            }

            String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
            int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

            StringBuilder roman = new StringBuilder();

            int i = 0;
            while (number > 0) {
                if (number - arabicValues[i] >= 0) {
                    roman.append(romanNumerals[i]);
                    number -= arabicValues[i];
                } else {
                    i++;
                }
            }

            return roman.toString();
        }
    }

