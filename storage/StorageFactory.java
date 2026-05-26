package storage;

public class StorageFactory {

    public static StorageStrategy get(StorageOptions option) {
        switch (option) {
            case MEMORY:
                return new MemoryStorage();
            case TEXT:
                return new TextFileStorage();
            case EXCEL:
                return new ExcelFileStorage();
            default:
                throw new IllegalArgumentException("Unknown storage option: " + option);
        }
    }
}
