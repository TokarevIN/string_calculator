import java.util.Scanner; //используется для считывания ввода с консоли.

public class Main { //Объявление публичного класса Main, который является точкой входа для программы.
    // при старте программы. throws Exception указывает,
    // что метод может выбрасывать исключения типа Exception.

    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in); //Создаем объект Scanner для чтения ввода с консоли.
        String exp = scn.nextLine(); //Читаем строку, введенную в консоль, и сохраняем её в переменную exp.
        printInQuotes(processExpression(exp)); // Вызываем метод processExpression
        // для обработки введенной строки и передаем результат в метод printInQuotes для вывода на экран.
    }

    static String processExpression(String exp) throws Exception { //Объявление метода processExpression,
        // который принимает строку exp и возвращает строку. Метод может выбрасывать исключения.
        String[] data; //Объявляем массив строк data.
        char action = detectAction(exp); //Вызываем метод detectAction,
        // который определяет действие (операцию) в выражении и возвращает символ действия, сохраняя его в переменной action.

        data = splitExpression(exp, action); //Вызываем метод splitExpression, который разделяет выражение на две части по
        // найденной операции, и сохраняет результат в массиве data.

        validateFirstArgument(data[0]); //Вызываем метод validateFirstArgument для проверки,
        // что первый аргумент (строка) заключен в кавычки.
        removeQuotesAndValidateLength(data); //Вызывает метод removeQuotesAndValidateLength
        // для удаления кавычек из строк и проверки их длины.

        if (action == '*' || action == '/') { //Проверяет, является ли операция умножением или делением.
            validateNumericArgument(data[1]); //Если операция умножение или деление, вызывает метод validateNumericArgument
            // для проверки, что второй аргумент является числом.
        }

        String result = performAction(data, action); //Вызывает метод performAction, который выполняет операцию над строками,
        // и сохраняет результат в переменной result.

        return limitResultLength(result); //Вызывает метод limitResultLength для ограничения длины результата до 40 символов
        // и возвращает итоговую строку.
    }

    static char detectAction(String exp) throws Exception { //Объявление метода detectAction, который принимает строку exp
        // и возвращает символ действия. Метод может выбрасывать исключения.
        if (exp.contains(" + ")) { //Проверяем, содержит ли строка exp символ операции "+"
            return '+'; //Если содержит, возвращает символ "+"
        } else if (exp.contains(" - ")) { //Проверяем, содержит ли строка exp символ операции "-"
            return '-'; //Если содержит, возвращает символ "-"
        } else if (exp.contains(" * ")) { //Проверяем, содержит ли строка exp символ операции "*"
            return '*'; //Если содержит, возвращает символ "*"
        } else if (exp.contains(" / ")) { //Проверяем, содержит ли строка exp символ операции "/"
            return '/'; //Если содержит, возвращает символ "/".
        } else { //Если ни одна из операций не найдена,
            throw new Exception("Некорректный знак действия"); //выбрасывает исключение с сообщением "Некорректный знак действия".
        }
    }

    static String[] splitExpression(String exp, char action) { //Объявление метода splitExpression,
        // который принимает строку exp и символ действия action, возвращает массив строк.
        switch (action) { //Начало конструкции switch для выбора действия в зависимости от символа action.
            case '+':
                return exp.split(" \\+ "); //Если action равен +, разделяем строку exp по символу + и возвращает массив строк.
            case '-':
                return exp.split(" - "); //Если action равен -, разделяем строку exp по символу - и возвращает массив строк.
            case '*':
                return exp.split(" \\* "); //Если action равен *, разделяем строку exp по символу * и возвращает массив строк.
            case '/':
                return exp.split(" / "); //Если action равен /, разделяем строку exp по символу / и возвращает массив строк.
            default: //по умолчанию
                return new String[]{}; //Если action не соответствует ни одному из символов, возвращает пустой массив строк.
        }
    }

    static void validateFirstArgument(String arg) throws Exception { //Объявление метода validateFirstArgument, который принимает
        // строку arg и проверяет, заключена ли она в кавычки. Метод может выбрасывать исключения.

        if (!arg.startsWith("\"") || !arg.endsWith("\"")) { //Проверяет, начинается ли строка arg с кавычки и заканчивается ли она кавычкой.
            throw new Exception("Первым аргументом должна быть строка"); //Если строка не начинается или не заканчивается кавычками,
            // выбрасывает исключение с сообщением "Первым аргументом должна быть строка".
        }
    }

    static void removeQuotesAndValidateLength(String[] data) throws Exception { //Объявление метода removeQuotesAndValidateLength,
        // который принимает массив строк data, удаляет кавычки и проверяет длину строк. Метод может выбрасывать исключения.

        for (int i = 0; i < data.length; i++) { //Начало цикла for, который проходит по всем элементам массива data.

            data[i] = data[i].replace("\"", ""); //Удаляет все кавычки из текущей строки data[i].
            if (data[i].length() > 10) { //Проверяет, превышает ли длина текущей строки 10 символов.
                throw new Exception("Длина строки не должна превышать 10 символов");
            }
            //Если длина строки превышает 10 символов, выбрасывает исключение с сообщением "Длина строки не должна превышать 10 символов".
        }
    }

    static void validateNumericArgument(String arg) throws Exception { //Объявление метода validateNumericArgument, который принимает
        // строку arg и проверяет, является ли она числом в диапазоне от 1 до 10. Метод может выбрасывать исключения.
        int number; //Объявление переменной number типа int.
        try {
            number = Integer.parseInt(arg); //Пробует преобразовать строку arg в число и сохраняет результат в переменной number.
        } catch (NumberFormatException e) {
            throw new Exception("Строчку можно делить или умножать только на число");
        } //Если преобразование не удалось (выброшено исключение NumberFormatException), выбрасывает исключение с сообщением
        // "Строчку можно делить или умножать только на число".

        if (number < 1 || number > 10) { //Проверяет, находится ли число в диапазоне от 1 до 10 включительно.
            throw new Exception("Число должно быть в диапазоне от 1 до 10 включительно");
        } //Если число не находится в указанном диапазоне, выбрасывает исключение с сообщением "Число должно быть в диапазоне от 1 до 10 включительно".
    }

    static String performAction(String[] data, char action) throws Exception {
        //Объявление метода performAction, который принимает массив строк data и символ действия action, возвращает строку. Метод может выбрасывать исключения.
        switch (action) { //Начало конструкции switch для выбора действия в зависимости от символа action.
            case '+':
                return data[0] + data[1]; //Если action равен +, конкатенирует (объединяет) две строки data[0] и data[1] и возвращает результат.
            case '*':
                int multiplier = Integer.parseInt(data[1]); //Если action равен *, преобразует строку data[1] в целое число и сохраняет его в переменной multiplier.
                StringBuilder result = new StringBuilder(); //Создает новый объект StringBuilder для построения результирующей строки.
                for (int i = 0; i < multiplier; i++) {
                    result.append(data[0]); //Цикл for добавляет строку data[0] к объекту StringBuilder multiplier раз.
                }
                return result.toString(); //Преобразует объект StringBuilder в строку и возвращает результат.
            case '-':
                int index = data[0].indexOf(data[1]);
                if (index == -1) {
                    return data[0];
                } else {
                    return data[0].substring(0, index) + data[0].substring(index + data[1].length());
                } //Если action равен -, ищет индекс первого вхождения строки data[1] в строке data[0].
                // Если подстрока не найдена, возвращает data[0].
                // Иначе возвращает строку data[0] без первой найденной подстроки data[1].

            case '/':
                int divisor = Integer.parseInt(data[1]);
                int newLen = data[0].length() / divisor;
                return data[0].substring(0, newLen); //Если action равен /, преобразует строку data[1] в целое число и сохраняет его в переменной
            // divisor. Делит длину строки data[0] на divisor и возвращает подстроку data[0] с нулевого символа
            // до нового значения длины.
            default:
                throw new Exception("Некорректный знак действия");
        } //Если action не соответствует ни одному из символов, выбрасывает исключение с сообщением "Некорректный знак действия".
    }

    static String limitResultLength(String result) { //Объявление метода limitResultLength, который принимает
        // строку result и возвращает строку, ограниченную по длине.
        if (result.length() > 40) { //Проверяет, превышает ли длина строки result 40 символов.
            result = result.substring(0, 40) + "..."; //Если длина строки превышает 40 символов, обрезает строку до 40 символов и добавляет ....
        }
        return result; //Возвращаем результат.
    }

    static void printInQuotes(String text) {//Объявление метода printInQuotes, который принимает строку text и выводит её в кавычках.
        System.out.println("\"" + text + "\""); //Печатает строку text, обернутую в кавычки, на консоль.
    }
}
