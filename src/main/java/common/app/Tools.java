package common.app;

import common.domain.AbstractPersistable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Tools {

    public static <T extends AbstractPersistable> T find(ArrayList<T> list, long id) {
        return list.stream().filter(z -> z.getId() == id).findFirst().orElse(null);
    }

    public static <T extends AbstractPersistable> Optional<T> findP(ArrayList<T> list, long id) {
        return list.stream().filter(z -> z.getId() == id).findFirst();
    }

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static float randomf(int min, int max) {
        return (float) ThreadLocalRandom.current().nextDouble(min, max);
    }
}
