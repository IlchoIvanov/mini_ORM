package orm;

import com.mysql.cj.protocol.Resultset;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());
        String fieldList = this.getDBFields(entity);
        String valueList = this.getDBValues(entity);
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, fieldList, valueList);
        return this.connection.prepareStatement(sql).execute();
    }


    @Override
    public Iterable<E> find(Class<E> table) {
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException {
        String tableName = this.getTableName(table);
        String sql = String.format("SELECT * FROM %s LIMIT 1", tableName);
        Resultset resultset = (Resultset) this.connection.prepareStatement(sql).executeQuery();
        return this.fillEntity(table, resultset);
    }

    private String getTableName(Class<?> entity) {
        Entity annotation = entity.getClass().getAnnotation(Entity.class);
        if (annotation == null) {
            throw new ORMException("Provided class does not have entity annotation");
        }
        return annotation.name();
    }

    private String getDBFields(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields()).filter(f -> f.getAnnotation(Column.class) != null).map(f -> f.getAnnotation(Column.class).name())
                .collect(Collectors.joining(","));
    }

    private String getDBValues(E entity) throws IllegalAccessException {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        List<String> result = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            if (declaredField.getAnnotation(Column.class) == null) {
                continue;
            }
            declaredField.setAccessible(true);
            Object value = declaredField.get(entity);
            result.add("\"" + value.toString() + "\"");

        }

        return String.join(",", result);

    }

}