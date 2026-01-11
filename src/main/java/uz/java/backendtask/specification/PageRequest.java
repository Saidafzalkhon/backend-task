package uz.java.backendtask.specification;

import lombok.Setter;
import lombok.ToString;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Sort;

@Setter
@ToString
public class PageRequest {

    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private Sort.Direction sortDirection = Sort.Direction.ASC;


    public Integer getPageNumber() {
        return pageNumber != null ? Math.max(pageNumber - 1, 0) : 0;
    }

    public Integer getPageSize() {
        return pageSize != null ? Math.max(pageSize, 1) : 10;
    }

    public String getSortBy() {
        return sortBy != null ? sortBy : "createdAt";
    }

    public Sort.Direction getSortDirection() {
        return sortDirection != null ? sortDirection : Sort.Direction.ASC;
    }
}
