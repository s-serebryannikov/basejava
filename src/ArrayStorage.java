import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getStorage() {
        return Arrays.copyOf(storage, size);
    }

    void save(Resume r) {
        if (size == 0) {
            storage[size] = r;
            size++;
        } else {
            int count = 0;
            for (int i = 0; i < getStorage().length; i++) {
                if (getStorage()[i].getUuid().equals(r.getUuid())) {
                    count++;
                }
            }
            if(count == 0){
                storage[size] = r;
                size++;
            } else System.out.println("Резюме с таким uuid уже существует.");
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < getStorage().length; i++) {
            if (getStorage()[i].getUuid().equals(uuid)) {
                return getStorage()[i];
            }
        }
        throw new NumberFormatException();
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (getStorage()[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return getStorage();
    }

    int size() {
        return size;
    }
}