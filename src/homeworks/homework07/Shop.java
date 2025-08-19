package homeworks.homework07;

import java.util.*;

public class Shop { // Основной класс приложения
    public static void main(String[] args) { // Ввод данных в магазин (Точка входа в приложение)
        Scanner scanner = new Scanner(System.in); // Сканер для чтения входных данных
        Map<String, Person> people = new HashMap<>(); // База покупателей
        Map<String, Product> products = new HashMap<>(); // База продуктов
        Random random = new Random(); // Генератор случайных чисел

        try { // процесс покупок - блок обработки исключений
            System.out.println("Введите список покупателей в формате: Имя = Сумма; ..."); // Инструкция для пользователя 1: Регистрация покупателей
            parseInput(scanner.nextLine(), people, true); // Читаем и парсим покупателей

            System.out.println("Введите список продуктов в формате: Продукт = Цена; ..."); // Инструкция для пользователя 2: Вод продуктов-товаров
            parseInput(scanner.nextLine(), products, false); // Читаем и парсим товары

            // Выбираем случайный продукт/товар для скидки
            List<Product> productList = new ArrayList<>(products.values());
            Product selectedProduct = productList.get(random.nextInt(productList.size()));
            DiscountProduct discountedProduct = new DiscountProduct(selectedProduct);
            products.put(discountedProduct.getName(), discountedProduct); // Добавляем товар со скидкой

            System.out.println("Введите список покупок в формате: Имя - Продукт; ..."); // Инструкция для пользователя 3: Процесс покупок
            processPurchases(scanner.nextLine(), people, products); // Обрабатываем покупки

        } catch (IllegalArgumentException e) { // Ловим исключения неправильного ввода
            System.out.println("Проблема: " + e.getMessage()); // Выводим ошибку
            return;
        }

        people.values().forEach(System.out::println); // Финансовый отчет - выводим итоговую информацию о покупках
    }

    private static void parseInput(String input, Map<String, ?> map, boolean isPerson) { // Парсер входных данных
        for (String item : input.split(";")) { // Разбиваем строку на элементы ";"
            String[] parts = item.split("="); // // Разделяем элемент на две части "="
            if (parts.length != 2) continue; // Пропускаем некорректные записи

            String key = parts[0].trim(); // Извлекаем ключевое слово
            double value = Double.parseDouble(parts[1].trim()); // Преобразуем число

            try { // Пытаемся создать объект
                if (isPerson) { // Если обрабатываем покупателей
                    ((Map<String, Person>) map).put(key, new Person(key, value)); // Создаем нового покупателя
                } else { // Иначе создаем новый товар
                    ((Map<String, Product>) map).put(key, new RegularProduct(key, value));
                }
            } catch (IllegalArgumentException e) { // Ловим возможные ошибки
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    private static void processPurchases(String input, Map<String, Person> people, Map<String, Product> products) { // Процессор покупок
        for (String purchase : input.split(";")) { // Проходим по списку покупок - разбиваем покупки через ";"
            String[] parts = purchase.split("-"); // разделяем покупателя и товар через "-"
            if (parts.length != 2) continue; // Пропускаем некорректные записи

            String personName = parts[0].trim(); // Имя покупателя
            String productName = parts[1].trim(); // Название товара

            Person person = people.get(personName); // Достаем из базы покупателя
            Product product = products.get(productName); // Получаем товар

            if (person != null && product != null) { // Если покупатель и товар найдены
                person.buyProduct(product); // Покупатель совершает покупку
            }
        }
    }
}