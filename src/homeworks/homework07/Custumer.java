package homeworks.homework07;

import java.util.ArrayList; // Динамический массив
import java.util.Collections; // Утилиты для работы с коллекциями
import java.util.List; // Интерфейс списка
import java.util.Objects; // для работы с объектами
import java.util.stream.Collectors; // Используем Stream API для обработки коллекций

class Person { // Класс "Покупатель" - паспорт(карточка) клиента магазина
    private final String name; // Имя (как в паспорте) - нельзя изменить
    private double money; // Текущий баланс (кошелек)
    private final List<Product> products = new ArrayList<>(); // Личная корзина покупок

    // Конструктор класса Person - обеспечивает процесс регистрации покупателя
    public Person(String name, double money) {
        if (name == null || name.trim().isEmpty()) // Проверка 1: Проверка минимальной длины имени
            throw new IllegalArgumentException("Имя не может быть пустым!");
        if (name.trim().length() < 3) // Проверка 2: Проверка минимальной длины имени
            throw new IllegalArgumentException("Имя не может быть короче 3 символов!"); // выводим текс ошибки
        if (money < 0) // Проверка 3: Баланса (баланс не может быть отрицательным значением)
            throw new IllegalArgumentException("Деньги не могут быть отрицательными!"); // выводим текс ошибки
        this.name = name.trim(); // Сохраняем имя покупателя
        this.money = money; // Устанавливаем начальную сумму денег покупателя
    }

    // Геттеры - "просмотр через окошко" данных
    public String getName() { return name; } // Чтение имени
    public List<Product> getProducts() { return Collections.unmodifiableList(products); } // // Возвращает защищённый список товаров корзины

    public void buyProduct(Product product) { // Метод покупки товара
        if (product.getCurrentPrice() > money) { // Проверка бюджета покупателя
            System.out.printf("%s не может позволить себе %s%n", name, product.getName()); // Сообщение о недостатке средств
            return; // Уходим без покупки
        }
        money -= product.getCurrentPrice(); // Уменьшаем баланс покупателя на цену товара
        products.add(product); // Добавляем товар в список покупок
        System.out.printf("%s купил %s%n", name, product.getName()); // Сообщение-Результат покупки
    }

    @Override // Переопределяем метод toString()
    public String toString() { // Форматируем вывод информации о покупателе - Как будет выглядеть покупатель на экране
        if (products.isEmpty()) // Проверка наличия покупок
            return name + " - ничего не куплено."; // Выводим сообщение, если покупок нет
        return name + " - " + products.stream() // Преобразуем корзину в строку
                .map(Product::toString) // Берем только названия
                .collect(Collectors.joining(", ")); // Соединяем через запятую
    }

    @Override // Переопределяем методы сравнения объектов
    public boolean equals(Object obj) { // Логика сравнения экземпляров Person
        if (this == obj) return true; // Быстрая проверка на самость
        if (!(obj instanceof Person)) return false; // Проверка типа объекта
        Person other = (Person) obj; // Приведение типа
        return Objects.equals(name, other.name) && Double.compare(money, other.money) == 0; // Сравнение полей
    }

    @Override // Переопределяем хэш-код для корректной работы коллекции
    public int hashCode() {
        return Objects.hash(name, money); // Генерируем уникальный хэш-код
    }
}