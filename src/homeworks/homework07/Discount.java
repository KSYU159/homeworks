package homeworks.homework07;

import java.time.LocalDateTime; // Импортируем класс для работы с датой и временем

class RegularProduct extends Product { // Обычный товар без скидок
    public RegularProduct(String name, double price) { // Конструктор обычного товара
        super(name, price); // Вызываем родительский конструктор
    }

    @Override // Переопределяем получение текущей цены
    public double getCurrentPrice() { // Просто возвращаем стандартную цену
        return currentPrice;
    }
}

class DiscountProduct extends Product { // Специальный товар со скидкой
    private LocalDateTime startTime; // Время начала акции
    private static final long DISCOUNT_DURATION_DAYS = 2; // Срок действия скидки (2 дня)

    public DiscountProduct(Product baseProduct) { // Конструктор товара со скидкой
        super(baseProduct.getName(), baseProduct.getOriginalPrice()); // Получаем данные из стандартного товара
        applyDiscount(); // Применяем скидку
        startTime = LocalDateTime.now(); // Запоминаем начало акции
    }

    private void applyDiscount() { // Внутренний метод расчета скидки
        currentPrice = originalPrice * 0.3; // Устанавливаем новую цену (70% скидка)
    }

    @Override // Переопределяем получение текущей цены
    public double getCurrentPrice() { // Определяем актуальность скидки
        if (isDiscountActive()) { // Если акция действует
            return currentPrice; // Возвращаем цену со скидкой
        } else { // Иначе восстанавливаем обычную цену
            currentPrice = originalPrice;
            return originalPrice;
        }
    }

    public boolean isDiscountActive() { // Проверяет активность скидки
        return !startTime.plusDays(DISCOUNT_DURATION_DAYS).isBefore(LocalDateTime.now()); // Сравниваем сроки
    }

    @Override // Переопределяем представление товара
    public String toString() { // Указываем статус скидки
        return name + " (скидка 70%)"; // Строка представления товара
    }
}