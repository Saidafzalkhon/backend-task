package uz.java.backendtask.specification;

import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Locale;

public final class SpecBuilder<T> {

    private Specification<T> spec = Specification.where(null);

    public <V> SpecBuilder<T> eq(String field, V value) {
        return value == null ? this : add((r, q, cb) -> cb.equal(r.get(field), value));
    }

    public SpecBuilder<T> like(String field, String value) {
        return (value == null || value.isEmpty()) ? this
                : add((r, q, cb) ->
                cb.like(cb.lower(r.get(field)), "%" + value.toLowerCase(Locale.ROOT) + "%"));
    }

    public <V extends Comparable<? super V>> SpecBuilder<T> between(
            String field, V from, V to) {

        if (from == null && to == null) return this;
        if (from != null && to != null)
            return add((r, q, cb) -> cb.between(r.get(field), from, to));
        if (from != null)
            return add((r, q, cb) -> cb.greaterThanOrEqualTo(r.get(field), from));
        return add((r, q, cb) -> cb.lessThanOrEqualTo(r.get(field), to));
    }

    public SpecBuilder<T> in(String field, Collection<?> values) {
        return (values == null || values.isEmpty()) ? this
                : add((r, q, cb) -> r.get(field).in(values));
    }

    public SpecBuilder<T> or(Specification<T> other) {
        spec = spec.or(other);
        return this;
    }

    public Specification<T> build() {
        return spec;
    }

    private SpecBuilder<T> add(Specification<T> part) {
        spec = spec.and(part);
        return this;
    }

    public SpecBuilder<T> fts(String searchQuery) {
        return (searchQuery == null || searchQuery.isBlank()) ? this
                : add((root, query, cb) ->
                cb.isTrue(cb.function(
                        "search_fts @@ plainto_tsquery",
                        Boolean.class,
                        cb.literal(searchQuery)
                ))
        );
    }

    public <J, V> SpecBuilder<T> joinLike(String joinField, String nestedField, String value) {
        return value == null ? this : add((root, query, cb) ->
                cb.like(root.join(joinField, JoinType.INNER).get(nestedField), "%" + value.toLowerCase(Locale.ROOT) + "%")
        );
    }

    public <J, V> SpecBuilder<T> joinEq(String joinField, String nestedField, V value) {
        return value == null ? this : add((root, query, cb) ->
                cb.equal(root.join(joinField, JoinType.INNER).get(nestedField), value)
        );
    }

    public <V> SpecBuilder<T> joinPathEq(String[] joinPath, String field, V value) {
        return value == null ? this : add((root, query, cb) -> {
            From<?, ?> join = root;
            for (String path : joinPath) {
                join = join.join(path, JoinType.INNER);
            }
            return cb.equal(join.get(field), value);
        });
    }


}
