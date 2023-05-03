package project.semsark.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import project.semsark.model.request_body.UserSearchParameters;
import project.semsark.model.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Builder
@AllArgsConstructor
public class UserSpecifications implements Specification<User> {

    private UserSearchParameters userSearchParameters;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (userSearchParameters.getEmail() != null) {
            predicate.getExpressions().add(criteriaBuilder
                    .and(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), userSearchParameters.getEmail())));
        }

        return predicate;
    }
}
