package homeworks.homework07;

import java.util.Objects;

abstract class Product { // Абстрактный родительский класс для всех типов товаров - Класс "Продукт"
    protected final String name; // название продукта (не меняется после создания)
    protected final double originalPrice; //Исходная цена товара (фиксированная)
    protected double currentPrice; // Текущая цена товара (может отличаться при скидке)

    public Product(String name, double price) { // Базовый конструктор товаров
        validateName(name); // Валидация названия товара
        validatePrice(price); // Валидация цены товара
        this.name = name.trim(); // Сохраняем название товара
        this.originalPrice = price; // Запоминаем оригинальную цену
        this.currentPrice = price; // Изначально устанавливаем текущую цену равной оригиналу
    }

    private void validateName(String name) { // Вспомогательный метод проверки правильности названия
        if (name == null || name.trim().isEmpty()) //проверка на ошибки названия "на пустоту"
            throw new IllegalArgumentException("Ошибка названия! Название не может быть пустой строкой."); // ошибка пустое название
        if (!name.matches(".*\\D+.*")) // Проверка на наличие хотя бы одной буквы
            System.err.println("Ошибка названия! Название не может состоять только из цифр.");
        if (name.trim().length() < 3) //проверка на минимальную длину названия
            System.err.println("Ошибка названия! Название продукта не может быть короче, чем 3 символа.");
    }

    private void validatePrice(double price) { // Вспомогательный метод проверки цены
        if (price <= 0) //проверка цены - цена должна быть положительной
            System.err.println("Ошибка валидации! Цена не может быть меньше или равна нулю.");
    }

    public abstract double getCurrentPrice(); // Абстрактный метод для определения актуальной цены

    public String getName() { return name; } // Геттер для названия товара
    public double getOriginalPrice() { return originalPrice; } // Геттер для изначальной цены

    @Override // Переопределяем метод toString()
    public String toString() { return name; } // Простой вывод названия товара

    @Override // Переопределяем сравнение объектов
    public boolean equals(Object obj) { // Логика сравнения экземпляров Product
        if (this == obj) return true; // Самосравнение
        if (!(obj instanceof Product)) return false; // Проверка типа
        Product other = (Product) obj; // Приведение типа
        return Objects.equals(name, other.name) && Double.compare(originalPrice, other.originalPrice) == 0; // Сравниваем ключевые поля
    }

    @Override // Переопределяем хэш-код
    public int hashCode() { // Генерация уникального хэша
        return Objects.hash(name, originalPrice);
    }
}

