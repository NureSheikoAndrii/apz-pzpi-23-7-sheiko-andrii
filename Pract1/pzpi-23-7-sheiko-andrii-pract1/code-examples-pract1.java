// 1. Інтерфейс Iterator — контракт для всіх ітераторів
interface Iterator {
    boolean hasNext();   // Чи є ще елементи
    Object next();       // Повернути наступний елемент
}

// 2. Конкретний ітератор для масиву рядків
class NameIterator implements Iterator {
    private String[] names;
    private int index = 0;   // поточна позиція
    
    public NameIterator(String[] names) {
        this.names = names;
    }
    
    @Override
    public boolean hasNext() {
        return index < names.length;
    }
    
    @Override
    public Object next() {
        if (hasNext()) {
            return names[index++];
        }
        return null;
    }
}

// 3. Інтерфейс колекції (Aggregate)
interface Container {
    Iterator getIterator();
}

// 4. Конкретна колекція
class NameRepository implements Container {
    // Внутрішні дані — масив рядків
    private String[] names = {"Іван", "Петро", "Олена"};
    
    @Override
    public Iterator getIterator() {
        return new NameIterator(names);
    }
}

// 5. Клієнтський код
public class Main {
    public static void main(String[] args) {
        NameRepository repository = new NameRepository();
        
        // Отримуємо ітератор і обходимо колекцію
        Iterator iterator = repository.getIterator();
        while (iterator.hasNext()) {
            System.out.println("Ім'я: " + iterator.next());
        }
    }
}
